
package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.testcase.FullSchemataDeletingTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.TestSuite;

import java.util.List;

/**
 *
 * @author Chris J. Wright
 */
public abstract class AbstractSchemataTechnique extends Technique {

    protected String createStmt;
    protected String dropStmt;
    protected final SQLWriter sqlWriter;

    public AbstractSchemataTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions, String dataGenerator, String criterion, long randomseed) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed);
        sqlWriter = dbms.getSQLWriter();
    }
    
    protected static void renameMutants(List<Mutant<Schema>> mutants) {
        for (int i = 0; i < mutants.size(); i++) {
            Schema mutantSchema = mutants.get(i).getMutatedArtefact();
            for (Table table : mutantSchema.getTablesInOrder()) {
                table.setName("mutant_" + (i + 1) + "_" + table.getName());
            }
            for (Constraint constraint : mutantSchema.getConstraints()) {
                if (constraint.hasIdentifier() && constraint.getIdentifier().get() != null) {
                    String name = constraint.getIdentifier().get();
                    constraint.setName("mutant_" + (i + 1) + "_" + name);
                }
            }
        }
    }

    protected TestSuiteResult executeTestSuiteSchemata(Schema schema, TestSuite suite, String schemataPrefix, TestSuiteResult originalResults) {
        TestCaseExecutor caseExecutor = new FullSchemataDeletingTestCaseExecutor(schema, dbms, databaseInteractor, schemataPrefix);
        TestSuiteExecutor suiteExecutor = new TestSuiteExecutor();
        if (!useTransactions || originalResults == null) {
            return suiteExecutor.executeTestSuite(caseExecutor, suite);
        } else {
            return suiteExecutor.executeTestSuite(caseExecutor, suite, originalResults);
        }
    }

    protected void doSchemataSteps() {
        renameMutants(mutants);
        dropStmt = buildDrop(mutants);
        createStmt = buildCreate(mutants);
    }

    protected String buildDrop(List<Mutant<Schema>> mutants) {
        StringBuilder dropBuilder = new StringBuilder();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            for (String statement : sqlWriter.writeDropTableStatements(mutantSchema, true)) {
                dropBuilder.append(statement);
                dropBuilder.append("; ");
                dropBuilder.append(System.lineSeparator());
            }
        }
        return dropBuilder.toString();
    }

    protected String buildCreate(List<Mutant<Schema>> mutants) {
        StringBuilder createBuilder = new StringBuilder();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            for (String statement : sqlWriter.writeCreateTableStatements(mutantSchema)) {
                createBuilder.append(statement);
                createBuilder.append("; ");
                createBuilder.append(System.lineSeparator());
            }
        }
        return createBuilder.toString();
    }
    
}
