package org.schemaanalyst.mutation.analysis.executor.technique;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlwriter.ConstraintAsCheckSQLWriter;
import org.schemaanalyst.sqlwriter.SQLWriter;
import static org.schemaanalyst.sqlwriter.SQLWriter.quoteIdentifier;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.util.IndentableStringBuilder;

/**
 *
 * @author Chris J. Wright
 */
public class ChecksTechnique extends Technique {

    public ChecksTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
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
        addDBMSFunctions();

        // Create meta-mutant selection table
        addSelectionTable();

        // Create meta-mutant in database
        ChecksSQLWriter sqlWriter = new ChecksSQLWriter();
        List<String> createStmts = sqlWriter.writeMetaMutantCreateTableStatements(mutants);
        
        
        // Clear database
        return result;
    }

    private void addSelectionTable() {
        databaseInteractor.executeUpdate("DROP TABLE IF EXISTS schemaanalyst_activemutant");
        databaseInteractor.executeUpdate("CREATE TABLE IF NOT EXISTS schemaanalyst_activemutant(id text)");
        databaseInteractor.executeUpdate("INSERT INTO schemaanalyst_activemutant VALUES (0)");
    }

    private void addDBMSFunctions() throws RuntimeException {
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
