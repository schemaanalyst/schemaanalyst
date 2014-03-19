package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.exceptions.InsertStatementException;
import org.schemaanalyst.mutation.analysis.executor.testcase.DeletingTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseResult;
import org.schemaanalyst.mutation.analysis.executor.testsuite.DeletingTestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteExecutor;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.mutation.analysis.executor.util.MutationAnalysisUtils;
import org.schemaanalyst.mutation.equivalence.ChangedTableFinder;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;

import java.util.*;

/**
 *
 * @author Chris J. Wright
 */
public class MinimalSchemataTechnique extends Technique {

    private final SQLWriter sqlWriter;
    private List<String> createStmts;
    private List<String> dropStmts;
    private List<String> deleteStmts;
    private Map<Integer, TestSuiteResult> resultMap;
    private Map<String, List<Integer>> changedTableMap;

    public MinimalSchemataTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor) {
        super(schema, mutants, testSuite, dbms, databaseInteractor);
        this.sqlWriter = dbms.getSQLWriter();
    }

    @Override
    public AnalysisResult analyse() {
        // Get normal results
        AnalysisResult result = new AnalysisResult();
        TestSuiteResult originalResults = executeTestSuite(schema, testSuite);

        // Build map of changed tables
        this.changedTableMap = new HashMap<>();
        for (int id = 0; id < mutants.size(); id++) {
            Mutant<Schema> mutant = mutants.get(id);
            String differentTable = ChangedTableFinder.getDifferentTable(schema, mutant.getMutatedArtefact()).getIdentifier().get();
            if (changedTableMap.containsKey(differentTable)) {
                List<Integer> list = changedTableMap.get(differentTable);
                list.add(id);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(id);
                changedTableMap.put(differentTable, list);
            }
        }
        
        // Build the meta-mutant schema and SQL statements
        Schema metamutant = MutationAnalysisUtils.renameAndMergeMutants(schema, mutants);
        createStmts = sqlWriter.writeCreateTableStatements(metamutant);
        dropStmts = sqlWriter.writeDropTableStatements(metamutant,true);
        deleteStmts = sqlWriter.writeDeleteFromTableStatements(metamutant);

        // Build map of results
        this.resultMap = new HashMap<>();
        for (int i = 0; i < mutants.size(); i++) {
            resultMap.put(i, new TestSuiteResult());
        }

        // Execute test suite
        executeDropStmts();
        executeCreateStmts();
        for (TestCase testCase : testSuite.getTestCases()) {
            executeDeleteStmts();
            // State inserts
            Map<Integer, TestCaseResult> failedInserts = new HashMap<>();
            Set<Integer> affectedMutants = new HashSet<>();
            Data state = testCase.getState();
            TestCaseResult normalTestResult = null;
            normalTestResult = executeTestCase(state, affectedMutants, normalTestResult, failedInserts);
            // Data inserts
            Data data = testCase.getData();
            normalTestResult = executeTestCase(data, affectedMutants, normalTestResult, failedInserts);
            if (normalTestResult == null) {
                normalTestResult = TestCaseResult.SuccessfulTestCaseResult;
            }
            for (int id = 0; id < mutants.size(); id++) {
                TestCaseResult testCaseResult;
                if (!affectedMutants.contains(id)) {
                    testCaseResult = normalTestResult;
                } else if (failedInserts.containsKey(id)) {
                    testCaseResult = failedInserts.get(id);
                } else {
                    testCaseResult = TestCaseResult.SuccessfulTestCaseResult;
                }
                resultMap.get(id).add(testCase, testCaseResult);
            }
        }
        // Build the TestSuiteResult objects
        for (int i = 0; i < mutants.size(); i++) {
            TestSuiteResult mutantResult = resultMap.get(i);
            if (!originalResults.equals(mutantResult)) {
                result.addKilled(mutants.get(i));
            } else {
                result.addLive(mutants.get(i));
            }
        }
        
        executeDropStmts();

        return result;
    }

    private TestCaseResult executeTestCase(Data state, Set<Integer> affectedMutants, TestCaseResult normalTestResult, Map<Integer, TestCaseResult> failedInserts) {
        for (Table table : schema.getTablesInOrder()) {
            if (state.getTables().contains(table)) {
                List<Integer> applicableMutants = changedTableMap.get(table.getIdentifier().get());
                applicableMutants = applicableMutants == null ? new ArrayList<Integer>() : applicableMutants;
                affectedMutants.addAll(applicableMutants);
                for (Row row : state.getRows(table)) {
                    String insert = sqlWriter.writeInsertStatement(row);
                    // Only insert for normal if we haven't failed yet
                    if (normalTestResult == null) {
                        Integer normalResult = databaseInteractor.executeUpdate(insert);
                        if (normalResult != 1) {
                            normalTestResult = new TestCaseResult(new InsertStatementException("Failed, result was: " + normalResult, insert));
                        }
                    }
                    for (Integer mutantId : applicableMutants) {
                        // Only insert for mutant if we haven't failed yet
                        if (!failedInserts.containsKey(mutantId)) {
                            String mutInsert = insert.replace("INSERT INTO \"", "INSERT INTO \"mutant_" + mutantId + "_");
                            Integer mutResult = databaseInteractor.executeUpdate(mutInsert);
                            if (mutResult != 1) {
                                failedInserts.put(mutantId, new TestCaseResult(new InsertStatementException("Failed, result was: " + mutResult, insert)));
                            }
                        }
                    }
                }
            }
        }
        return normalTestResult;
    }

    private void executeDropStmts() {
        for (String stmt : dropStmts) {
            databaseInteractor.executeUpdate(stmt);
        }
    }

    private void executeCreateStmts() {
        for (String stmt : createStmts) {
            databaseInteractor.executeUpdate(stmt);
        }
    }

    private void executeDeleteStmts() {
        for (String stmt : deleteStmts) {
            databaseInteractor.executeUpdate(stmt);
        }
    }

    /**
     * Executes all {@link TestCase}s in a {@link TestSuite} for a given
     * {@link Schema}.
     *
     * @param schema The schema
     * @param suite The test suite
     * @return The execution results
     */
    protected TestSuiteResult executeTestSuite(Schema schema, TestSuite suite) {
        TestCaseExecutor caseExecutor = new DeletingTestCaseExecutor(schema, dbms, databaseInteractor);
        TestSuiteExecutor suiteExecutor = new DeletingTestSuiteExecutor();
        return suiteExecutor.executeTestSuite(caseExecutor, suite);
    }
    
}
