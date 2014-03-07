package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.testcase.DeletingTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testcase.FullSchemataDeletingTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.DeletingTestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlwriter.SQLWriter;

import java.util.List;

/**
 * <p>
 * The 'Full Schemata' mutation analysis approach.</p>
 *
 * @author Chris J. Wright
 */
public class FullSchemataTechnique extends Technique {

    private String createStmt;
    private String dropStmt;
    private final SQLWriter sqlWriter;

    public FullSchemataTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor) {
        super(schema, mutants, testSuite, dbms, databaseInteractor);
        sqlWriter = dbms.getSQLWriter();
    }

    @Override
    public AnalysisResult analyse() {
        // Get normal results
        AnalysisResult result = new AnalysisResult();
        TestSuiteResult originalResults = executeTestSuite(schema, testSuite);

        // Get mutant results with schemata changes
        schemataSteps();
        databaseInteractor.executeUpdate(dropStmt);
        databaseInteractor.executeUpdate(createStmt);
        int mutantId = 0;
        for (Mutant<Schema> mutant : mutants) {
            String schemataPrefix = "mutant_" + (mutantId + 1) + "_";
            TestSuiteResult mutantResults = executeTestSuiteSchemata(mutant.getMutatedArtefact(), testSuite, schemataPrefix);
            if (!originalResults.equals(mutantResults)) {
                result.addKilled(mutant);
            } else {
                result.addLive(mutant);
            }
            mutantId++;
        }
        databaseInteractor.executeUpdate(dropStmt);
        return result;
    }

    /**
     * Executes all {@link TestCase}s in a {@link TestSuite} for a given
     * {@link Schema}.
     *
     * @param schema The schema
     * @param suite The test suite
     * @return The execution results
     */
    private TestSuiteResult executeTestSuite(Schema schema, TestSuite suite) {
        TestCaseExecutor caseExecutor = new DeletingTestCaseExecutor(schema, dbms, databaseInteractor);
        TestSuiteExecutor suiteExecutor = new DeletingTestSuiteExecutor();
        return suiteExecutor.executeTestSuite(caseExecutor, suite);
    }

    private TestSuiteResult executeTestSuiteSchemata(Schema schema, TestSuite suite, String schemataPrefix) {
        TestCaseExecutor caseExecutor = new FullSchemataDeletingTestCaseExecutor(schema, dbms, databaseInteractor, schemataPrefix);
        TestSuiteExecutor suiteExecutor = new TestSuiteExecutor();
        return suiteExecutor.executeTestSuite(caseExecutor, suite);
    }

    private void schemataSteps() {
        renameMutants(mutants);
        dropStmt = buildDrop(mutants);
        createStmt = buildCreate(mutants);
    }

    private static void renameMutants(List<Mutant<Schema>> mutants) {
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

    private String buildDrop(List<Mutant<Schema>> mutants) {
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

    private String buildCreate(List<Mutant<Schema>> mutants) {
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
