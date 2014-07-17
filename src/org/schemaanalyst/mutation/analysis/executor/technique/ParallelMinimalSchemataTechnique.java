package org.schemaanalyst.mutation.analysis.executor.technique;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

/**
 *
 * @author Chris J. Wright
 */
public class ParallelMinimalSchemataTechnique extends Technique {

    final protected static int TRANSACTION_SIZE = 100;
    private final SQLWriter sqlWriter;
    private List<String> createStmts;
    private List<String> dropStmts;
    private List<String> deleteStmts;
    private Map<Integer, TestSuiteResult> resultMap;
    private Map<String, List<Integer>> changedTableMap;
    protected Map<String, DatabaseInteractor> threadInteractors;
    ExecutorService executor;

    public ParallelMinimalSchemataTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
        this.sqlWriter = dbms.getSQLWriter();
        threadInteractors = new HashMap<>();
    }

    @Override
    public AnalysisResult analyse(TestSuiteResult originalResults) {
        // Setup execution pool
        executor = Executors.newFixedThreadPool(4);
        
        // Get normal results
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
        executeDropStmts(databaseInteractor);
        executeCreateStmts(databaseInteractor);
        for (TestCase testCase : testSuite.getTestCases()) {
            executeDeleteStmts(databaseInteractor);
            TestCaseResult normalTestResult = null;
            Map<Integer, TestCaseResult> failedMutants = new HashMap<>();

            Data data = testCase.getState();
            normalTestResult = executeInserts(data, normalTestResult, failedMutants, testCase);
            data = testCase.getData();
            normalTestResult = executeInserts(data, normalTestResult, failedMutants, testCase);
            if (normalTestResult == null) {
                for (int i = 0; i < mutants.size(); i++) {
                    if (!failedMutants.containsKey(i)) {
                        resultMap.get(i).add(testCase, TestCaseResult.SuccessfulTestCaseResult);
                    }
                }
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

        executeDropStmts(databaseInteractor);
        executor.shutdown();
        
        return result;
    }

    private TestCaseResult executeInserts(Data data, TestCaseResult normalTestResult, Map<Integer, TestCaseResult> failedMutants, TestCase testCase) {
        for (Table table : schema.getTablesInOrder()) {
            if (data.getTables().contains(table)) {
                List<Integer> applicableMutants = changedTableMap.get(table.getIdentifier().get());
                applicableMutants = applicableMutants == null ? new ArrayList<Integer>() : applicableMutants;
                for (Row row : data.getRows(table)) {
                    String insert = sqlWriter.writeInsertStatement(row);
                    // Only insert if we haven't failed yet
                    if (normalTestResult == null) {
                        Integer normalResult = databaseInteractor.executeUpdate(insert);
                        if (normalResult != 1) {
                            normalTestResult = new TestCaseResult(new InsertStatementException("Failed, result was: " + normalResult, insert));
                        }
                    }

                    // Setup for parallel execution
                    Map<Integer, Future<Integer>> execResults = new HashMap<>();
                    for (Integer mutantId : applicableMutants) {
                        // Only insert if we haven't failed yet
                        if (!failedMutants.containsKey(mutantId)) {
                            MutationAnalysisCallable callable = new MutationAnalysisCallable(insert, mutantId);
                            Future<Integer> future = executor.submit(callable);
                            execResults.put(mutantId, future);
                        }
                    }

                    // Collate from parallel execution
                    for (Map.Entry<Integer, Future<Integer>> mutantEntry : execResults.entrySet()) {
                        try {
                            int mutResult = mutantEntry.getValue().get();
                            Integer mutantId = mutantEntry.getKey();
                            if (mutResult != 1) {
                                TestCaseResult mutantResult = new TestCaseResult(new InsertStatementException("Failed, result was: " + mutResult, insert));
                                failedMutants.put(mutantId, mutantResult);
                                resultMap.get(mutantId).add(testCase, mutantResult);
                            }
                        } catch (InterruptedException | ExecutionException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    // If a mutant isn't applicable, then it should 'inherit' the normal result
                    for (int i = 0; i < mutants.size(); i++) {
                        if (!applicableMutants.contains(i) && normalTestResult != null) {
                            resultMap.get(i).add(testCase, normalTestResult);
                            failedMutants.put(i, normalTestResult);
                        }
                    }
                }
            }
        }
        return normalTestResult;
    }

    private void executeDropStmts(DatabaseInteractor databaseInteractor) {
        if (!useTransactions) {
            for (String stmt : dropStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
        } else {
            databaseInteractor.executeDropsAsTransaction(dropStmts, TRANSACTION_SIZE);
        }
    }

    private void executeCreateStmts(DatabaseInteractor databaseInteractor) {
        if (!useTransactions) {
            for (String stmt : createStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
        } else {
            databaseInteractor.executeCreatesAsTransaction(createStmts, TRANSACTION_SIZE);
        }
    }

    private void executeDeleteStmts(DatabaseInteractor databaseInteractor) {
        if (!useTransactions) {
            for (String stmt : deleteStmts) {
                databaseInteractor.executeUpdate(stmt);
            }
        } else {
            databaseInteractor.executeUpdatesAsTransaction(deleteStmts);
        }
    }

    protected DatabaseInteractor getInteractorForThread(Thread thread) {
        String threadName = thread.getName();
        if (!threadInteractors.containsKey(threadName)) {
            DatabaseInteractor interactor = databaseInteractor.duplicate();
            threadInteractors.put(threadName, interactor);
            if (dbms.getName().equals("SQLite")) {
                executeDropStmts(databaseInteractor);
                executeCreateStmts(databaseInteractor);
            }
        }
        return threadInteractors.get(threadName);
    }

    private class MutationAnalysisCallable implements Callable<Integer> {

        private final String insert;
        private final Integer mutantId;
        private final DatabaseInteractor interactor;

        public MutationAnalysisCallable(String insert, Integer mutantId) {
            this.insert = insert;
            this.mutantId = mutantId;
            this.interactor = getInteractorForThread(Thread.currentThread());
        }

        @Override
        public Integer call() throws Exception {
            String mutInsert = insert.replace("INSERT INTO \"", "INSERT INTO \"mutant_" + mutantId + "_");
            Integer mutResult = interactor.executeUpdate(mutInsert);
            return mutResult;
        }

    }

    /**
     * Class representing the status of a mutant after mutation analysis.
     */
    private enum MutantStatus {

        /**
         * Mutant was killed
         */
        KILLED,
        /**
         * Mutant was not killed
         */
        ALIVE
    };
}
