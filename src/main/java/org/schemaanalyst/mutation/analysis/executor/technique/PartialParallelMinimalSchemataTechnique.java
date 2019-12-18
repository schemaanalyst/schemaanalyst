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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>The Minimal Schemata implementation with parallelisation used for setup
 * processes but not for INSERT statements.</p>
 * 
 * @author Chris J. Wright
 */
public class PartialParallelMinimalSchemataTechnique extends Technique {

    final protected static int TRANSACTION_SIZE = 100;
    private final SQLWriter sqlWriter;
    private List<String> createStmts;
    private List<String> dropStmts;
    private List<String> deleteStmts;
    private Map<Integer, TestSuiteResult> resultMap;
    private Map<String, List<Integer>> changedTableMap;

    public PartialParallelMinimalSchemataTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions, String dataGenerator, String criterion, long randomseed) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed);
        this.sqlWriter = dbms.getSQLWriter();
    }

    private class ChangedTableTask implements Runnable {

        private final Map<String, List<Integer>> changedTableMap;
        private final int id;
        private final Mutant<Schema> mutant;

        public ChangedTableTask(Map<String, List<Integer>> changedTableMap, int id, Mutant<Schema> mutant) {
            this.changedTableMap = changedTableMap;
            this.id = id;
            this.mutant = mutant;
        }

        @Override
        public void run() {
            String changedTable = MutationAnalysisUtils.computeChangedTable(schema, mutant);
            updateChangedTableMap(id, changedTable);
            MutationAnalysisUtils.renameChangedTable(mutant, id, changedTable);
            MutationAnalysisUtils.renameMutantConstraints("mutant_" + id + "_", mutant);
        }
        
        private void updateChangedTableMap(int id, String changedTable) {
            synchronized (changedTableMap) {
                List<Integer> idList;
                if (changedTableMap.containsKey(changedTable)) {
                    idList = changedTableMap.get(changedTable);
                } else {
                    idList = new ArrayList<>();
                    changedTableMap.put(changedTable, idList);
                }
                idList.add(id);
            }
        }

    }

    @Override
    public AnalysisResult analyse(TestSuiteResult originalResults) {
        // Get normal results
        AnalysisResult result = new AnalysisResult();

        // Build map of changed tables
        this.changedTableMap = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int id = 0; id < mutants.size(); id++) {
            Mutant<Schema> mutant = mutants.get(id);
            executor.submit(new ChangedTableTask(changedTableMap, id, mutant));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        // Build the meta-mutant schema and SQL statements
        Schema metamutant = MutationAnalysisUtils.mergeMutantsParallel(schema, mutants);
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
        for (TestCase testCase : testSuite.getTestCases()) {
            executeDeleteStmts();
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
        executeDropStmts();

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

                    for (Integer mutantId : applicableMutants) {
                        // Only insert if we haven't failed yet
                        if (!failedMutants.containsKey(mutantId)) {
                            String mutInsert = insert.replace("INSERT INTO \"", "INSERT INTO \"mutant_" + mutantId + "_");
                            Integer mutResult = databaseInteractor.executeUpdate(mutInsert);
                            if (mutResult != 1) {
                                TestCaseResult mutantResult = new TestCaseResult(new InsertStatementException("Failed, result was: " + mutResult, insert));
                                failedMutants.put(mutantId, mutantResult);
                                resultMap.get(mutantId).add(testCase, mutantResult);
                            }
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
