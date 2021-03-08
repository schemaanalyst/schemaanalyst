package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.exceptions.InsertStatementException;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseResult;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.mutation.analysis.executor.util.MutationAnalysisUtils;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A re-implementation of the Minimal Schemata technique, where all inserts are
 * represented in the test suite result objects. While this produces a greater
 * size of result object, it also allows for a more simple implementation.
 *
 * @author Chris J. Wright
 */
public class MinimalSchemata2Technique extends Technique {

    final protected static int TRANSACTION_SIZE = 100;
    private final SQLWriter sqlWriter;
    private List<String> createStmts;
    private List<String> dropStmts;
    private List<String> deleteStmts;
    private Map<Integer, TestSuiteResult> resultMap;
    private Map<String, List<Integer>> changedTableMap;

    public MinimalSchemata2Technique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions, String dataGenerator, String criterion, long randomseed) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed);
        this.sqlWriter = dbms.getSQLWriter();
    }

    @Override
    public AnalysisResult analyse(TestSuiteResult originalResults) {
        AnalysisResult result = new AnalysisResult();

        // Build map of changed tables
        this.changedTableMap = new HashMap<>();
        for (int id = 0; id < mutants.size(); id++) {
            Mutant<Schema> mutant = mutants.get(id);
            String differentTable = MutationAnalysisUtils.computeChangedTable(schema, mutant);
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
        dropStmts = sqlWriter.writeDropTableStatements(metamutant, true);
        deleteStmts = sqlWriter.writeDeleteFromTableStatements(metamutant);

        // Build map of results
        this.resultMap = new HashMap<>();
        for (int i = 0; i < mutants.size(); i++) {
            resultMap.put(i, new TestSuiteResult());
        }

        // Execute test suite
        executeDropStmts();
        executeCreateStmts();
        TestSuiteResult originalTestSuiteResult = new TestSuiteResult();
        for (TestCase testCase : testSuite.getTestCases()) {
            executeDeleteStmts();

            Map<Integer, TestCaseResult> failedMutants = new HashMap<>();
            boolean originalFailed = false;

            Data data = testCase.getState();
            executeInserts(data, testCase, originalFailed, originalTestSuiteResult, failedMutants);
            data = testCase.getData();
            executeInserts(data, testCase, originalFailed, originalTestSuiteResult, failedMutants);
        }

        // Determine which mutants have been killed
        for (int i = 0; i < mutants.size(); i++) {
            TestSuiteResult mutantResult = resultMap.get(i);
            if (!originalTestSuiteResult.equals(mutantResult)) {
                result.addKilled(mutants.get(i));
            } else {
                result.addLive(mutants.get(i));
            }
        }
        
        executeDropStmts();

        return result;
    }

    private void executeInserts(Data data, TestCase testCase, boolean originalFailed, TestSuiteResult originalTestSuiteResult, Map<Integer, TestCaseResult> failedMutants) {
        for (Table table : schema.getTablesInOrder()) {
            if (data.getTables().contains(table)) {
                // Find which mutants should have the insert applied
                List<Integer> applicableMutants = changedTableMap.get(table.getIdentifier().get());
                applicableMutants = applicableMutants == null ? new ArrayList<Integer>() : applicableMutants;

                for (Row row : data.getRows(table)) {
                    // Build the inserts from the data
                    String insert = sqlWriter.writeInsertStatement(row);

                    // Only insert into original if we haven't failed yet
                    if (!originalFailed) {
                        Integer originalResult = databaseInteractor.executeUpdate(insert);
                        if (originalResult != 1) {
                            originalFailed = true;
                            originalTestSuiteResult.add(testCase, new TestCaseResult(new InsertStatementException("Failed, result was: " + originalResult, insert)));
                        } else {
                            originalTestSuiteResult.add(testCase, TestCaseResult.SuccessfulTestCaseResult);
                        }

                        // If a mutant isn't applicable, then it should 'inherit' the normal result
                        for (int mutantId = 0; mutantId < mutants.size(); mutantId++) {
                            if (!applicableMutants.contains(mutantId)) {
                                TestCaseResult mutantResult;
                                if (originalResult != 1) {
                                    mutantResult = new TestCaseResult(new InsertStatementException("Failed, result was: " + originalResult, insert));
                                    failedMutants.put(mutantId, mutantResult);
                                } else {
                                    mutantResult = TestCaseResult.SuccessfulTestCaseResult;
                                }
                                resultMap.get(mutantId).add(testCase, mutantResult);
                            }
                        }
                    }

                    // Apply for each mutant
                    for (Integer mutantId : applicableMutants) {
                        // Only insert if we haven't failed yet
                        if (!failedMutants.containsKey(mutantId)) {
                            String mutInsert = insert.replace("INSERT INTO \"", "INSERT INTO \"mutant_" + mutantId + "_");
                            Integer mutResult = databaseInteractor.executeUpdate(mutInsert);
                            TestCaseResult mutantResult;
                            if (mutResult != 1) {
                                mutantResult = new TestCaseResult(new InsertStatementException("Failed, result was: " + mutResult, insert));
                                failedMutants.put(mutantId, mutantResult);
                            } else {
                                mutantResult = TestCaseResult.SuccessfulTestCaseResult;
                            }
                            resultMap.get(mutantId).add(testCase, mutantResult);
                        }
                    }
                }
            }
        }
    }

    private void executeDropStmts() {
        if (!useTransactions) {
            for (String stmt : dropStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
        } else {
            databaseInteractor.executeDropsAsTransaction(dropStmts, TRANSACTION_SIZE);
        }
    }

    private void executeCreateStmts() {
        if (!useTransactions) {
            for (String stmt : createStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
        } else {
            databaseInteractor.executeCreatesAsTransaction(createStmts, TRANSACTION_SIZE);
        }
    }

    private void executeDeleteStmts() {
        if (!useTransactions) {
            for (String stmt : deleteStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
        } else {
            databaseInteractor.executeUpdatesAsTransaction(deleteStmts);
        }
    }

}
