package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.exceptions.CreateStatementException;
import org.schemaanalyst.mutation.analysis.executor.exceptions.DropStatementException;
import org.schemaanalyst.mutation.analysis.executor.testcase.ChecksTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.ChecksTestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlwriter.ConstraintAsCheckSQLWriter;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.util.IndentableStringBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris J. Wright
 */
public class ChecksTechnique extends Technique {
    
    private static final String META_MUTANT_SELECTION_TABLE = "schemaanalyst_activemutant";

    public ChecksTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions, String dataGenerator, String criterion, long randomseed) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed);
    }

    @Override
    public AnalysisResult analyse(TestSuiteResult originalResults) {
        // Check Postgres is being used (only supported DBMS currently)
        if (!dbms.getName().equals("Postgres")) {
            throw new RuntimeException("ChecksTechnique only supports Postgres."
                    + " Use another technique, or change the configuration to"
                    + " use Postgres.");
        }
        
        // Create results object
        AnalysisResult result = new AnalysisResult();

        // Add functions to DBMS
        createDBMSFunctions();

        // Create meta-mutant selection table
        createSelectionTable();

        // Create meta-mutant in database
        ChecksSQLWriter sqlWriter = new ChecksSQLWriter();
        createMetaMutant(sqlWriter);
        
        // Execute test suite
        ChecksTestCaseExecutor caseExecutor = new ChecksTestCaseExecutor(schema, dbms, databaseInteractor);
        ChecksTestSuiteExecutor suiteExecutor = new ChecksTestSuiteExecutor();
        for (int i = 0; i < mutants.size(); i++) {
//            System.out.println("MUTANT " + i);
            caseExecutor.setMutantId(i);
            TestSuiteResult mutantResult = suiteExecutor.executeTestSuite(caseExecutor, testSuite);
            if (!originalResults.equals(mutantResult)) {
//                System.out.println("Killed (" + mutants.get(i).getMutantProducer() + "," + mutants.get(i).getDescription() + ")");
//                System.out.println("\t Original: " + originalResults);
//                System.out.println("\t Mutant: " + mutantResult);
                result.addKilled(mutants.get(i));
            } else {
                result.addLive(mutants.get(i));
            }
        }
        
        // Drop meta-mutant
        dropMetaMutant(sqlWriter);
        
        // Drop meta-mutant selection table
        dropSelectionTable();
        
        return result;
    }

    private void createSelectionTable() {
        dropSelectionTable();
        databaseInteractor.executeUpdate("CREATE TABLE IF NOT EXISTS " + META_MUTANT_SELECTION_TABLE + "(id text)");
        databaseInteractor.executeUpdate("INSERT INTO " + META_MUTANT_SELECTION_TABLE + " VALUES (0)");
    }
    
    private void dropSelectionTable() {
        databaseInteractor.executeUpdate("DROP TABLE IF EXISTS " + META_MUTANT_SELECTION_TABLE);
    }

    private void createDBMSFunctions() throws RuntimeException {
        try {
            Path path = FileSystems.getDefault().getPath("scripts/checks/functions.sql");
            List<String> functions = Files.readAllLines(path, Charset.defaultCharset());
            String definition = "";
            for (String statement : functions) {
                if (!statement.isEmpty()) {
                    definition += statement + "\n";
                } else {
                    databaseInteractor.execute(definition);
                    definition = "";
                }
            }
            if (!definition.isEmpty()) {
                databaseInteractor.execute(definition);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Could not find 'functions.sql' to load", ex);
        }
    }
    
    private void createMetaMutant(ChecksSQLWriter sqlWriter) {
        List<String> createStmts = sqlWriter.writeMetaMutantCreateTableStatements(mutants);
        for (String create : createStmts) {
            Integer createResult = databaseInteractor.executeUpdate(create);
            if (createResult < 0) {
                throw new CreateStatementException("Failed, result was: " + createResult, create);
            }
        }
    }
    
    private void dropMetaMutant(ChecksSQLWriter sqlWriter) throws DropStatementException {
        // Drop meta-mutant in database
        List<String> dropStmts = sqlWriter.writeDropTableStatements(schema);
        for (String drop : dropStmts) {
            Integer dropResult = databaseInteractor.executeUpdate(drop);
            if (dropResult < 0) {
                throw new DropStatementException("Failed, result was: " + dropResult, drop);
            }
        }
    }

    private class ChecksSQLWriter extends SQLWriter {

        /**
         * Writes all constraints of a table as SQL CHECK constraints.
         *
         * @param schema The schema
         * @param table The table in the schema
         * @return SQL CHECK constraints for all constraints.
         */
        private String writeConstraintsAsChecks(Schema schema, Table table) {
            IndentableStringBuilder sql = new IndentableStringBuilder();

            // write primary key
            PrimaryKeyConstraint primaryKey = schema.getPrimaryKeyConstraint(table);
            if (primaryKey != null) {
                sql.appendln(0, ",");
                sql.append(1, constraintSQLWriter.writeConstraint(primaryKey));
            }

            // write foreign keys
            for (ForeignKeyConstraint foreignKey : schema.getForeignKeyConstraints(table)) {
                sql.appendln(0, ",");
                sql.append(1, constraintSQLWriter.writeConstraint(foreignKey));
            }

            // write unique constraints
            for (UniqueConstraint unique : schema.getUniqueConstraints(table)) {
                sql.appendln(0, ",");
                sql.append(1, constraintSQLWriter.writeConstraint(unique));
            }

            // write not nulls
            for (NotNullConstraint notNullConstraint : schema.getNotNullConstraints(table)) {
                sql.appendln(0, ",");
                sql.append(1, constraintSQLWriter.writeConstraint(notNullConstraint));
            }
            
            // write check constraints
            for (CheckConstraint check : schema.getCheckConstraints(table)) {
                sql.appendln(0, ",");
                sql.append(1, constraintSQLWriter.writeConstraint(check));
            }
            return sql.toString();
        }

        public List<String> writeMetaMutantCreateTableStatements(List<Mutant<Schema>> mutants) {
            // Check there is at least 1 mutant
            if (mutants.isEmpty()) {
                throw new RuntimeException("Cannot write meta-mutant for empty list of mutants");
            }
            
            List<String> statements = new ArrayList<>();

            Schema mutant0 = mutants.get(0).getMutatedArtefact();
            for (Table table : mutant0.getTablesInOrder()) {
                IndentableStringBuilder sql = new IndentableStringBuilder();
                
                // Write start of CREATE TABLE statement
                sql.append("CREATE TABLE ");
                sql.append(quoteIdentifier(table.getName()));
                sql.appendln(" (");

                // Write columns once
                boolean first = true;
                for (Column column : table.getColumns()) {
                    if (first) {
                        first = false;
                    } else {
                        sql.appendln(0, ",");
                    }

                    // Write column name
                    sql.append(1, quoteIdentifier(column.getName()));

                    // Write column type			
                    sql.appendTabbed(dataTypeSQLWriter.writeDataType(column));
                }
                
                // Write constraints for all mutants
                for (int id = 0; id < mutants.size(); id++) {
                    Schema schema = mutants.get(id).getMutatedArtefact();
                    setMutant(id);
                    sql.append(writeConstraintsAsChecks(schema, schema.getTable(table.getName())));
                }
                
                // Write end of CREATE TABLE statement and add to list
                sql.appendln();
                sql.append(")");
                statements.add(sql.toString());
            }
            
            return statements;
        }

        private void setMutant(int id) {
            constraintSQLWriter = new ConstraintAsCheckSQLWriter(String.valueOf(id));
        }
    }

}
