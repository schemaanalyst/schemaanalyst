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
import org.schemaanalyst.mutation.equivalence.ChangedConstraintFinder;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.util.tuple.MixedPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.operator.*;

/**
 *
 * @author Chris J. Wright
 */
public class MinimalMinimalSchemata2Technique extends Technique {

    private static class MutantFilter {

        final protected static Set<Class<? extends MutantProducer>> SUPPORTED_OPERATORS = new HashSet<>();

        static {
            SUPPORTED_OPERATORS.add(PKCColumnA.class);
            SUPPORTED_OPERATORS.add(UCColumnA.class);
            SUPPORTED_OPERATORS.add(NNCA.class);
        }

        public static boolean isSupported(Schema original, Mutant<Schema> mutant) {
            return SUPPORTED_OPERATORS.contains(mutant.getMutantProducer().getClass()) &&
                    (original.getPrimaryKeyConstraints().size() != mutant.getMutatedArtefact().getPrimaryKeyConstraints().size() ||
                    original.getUniqueConstraints().size() != mutant.getMutatedArtefact().getUniqueConstraints().size());
        }
    }

    final protected static int TRANSACTION_SIZE = 100;
    private final SQLWriter sqlWriter;
    private List<String> createStmts;
    private List<String> dropStmts;
    private List<String> deleteStmts;
    private Map<Integer, TestSuiteResult> resultMap;
    private Map<String, List<Integer>> changedTableMap;

    public MinimalMinimalSchemata2Technique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions, String dataGenerator, String criterion, long randomseed) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed);
        this.sqlWriter = dbms.getSQLWriter();
    }

    @Override
    public AnalysisResult analyse(TestSuiteResult originalResults) {
        AnalysisResult result = new AnalysisResult();

        // Split based on those suitable for MinimalMinimal
        List<Mutant<Schema>> unsuitableMutants = new ArrayList<>();
        List<Mutant<Schema>> suitableMutants = new ArrayList<>();
        for (Mutant<Schema> mutant : mutants) {
            if (MutantFilter.isSupported(schema, mutant)) {
                suitableMutants.add(mutant);
            } else {
                unsuitableMutants.add(mutant);
            }
        }

        // Analyse suitable mutants with this technique
        mutants = suitableMutants;

        // Analyse unsuitable mutants with Minimal technique
        AnalysisResult removalResults = new MinimalSchemataTechnique(schema.duplicate(), unsuitableMutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed).analyse(originalResults);
        for (Mutant<Schema> mutant : removalResults.getKilled()) {
            result.addKilled(mutant);
        }
        for (Mutant<Schema> mutant : removalResults.getLive()) {
            result.addLive(mutant);
        }
        // Analyse suitable mutants as normal

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
        removeOriginalConstraintsAndRename(schema, mutants);
        Schema metamutant = MutationAnalysisUtils.mergeMutants(schema, mutants);
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

        // Build the TestSuiteResult objects
        for (int i = 0; i < mutants.size(); i++) {
            TestSuiteResult mutantResult = resultMap.get(i);
            boolean killed = false;
            if (originalTestSuiteResult.getResults().size() == mutantResult.getResults().size()) {
                for (int j = 0; j < originalTestSuiteResult.getResults().size(); j++) {
                    MixedPair<TestCase, TestCaseResult> original = originalTestSuiteResult.getResults().get(j);
                    MixedPair<TestCase, TestCaseResult> mutant = mutantResult.getResults().get(j);
                    if (original.getSecond().wasSuccessful() && !mutant.getSecond().wasSuccessful()) {
                        killed = true;
                        break;
                    }
                }
            } else {
                killed = true;
            }
            if (killed) {
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

    /**
     * Removes all constraints from a schema except the mutated constraint, and
     * renames the table in the mutant.
     *
     * @param original The original schema
     * @param mutants The collection of mutated schemas
     */
    private void removeOriginalConstraintsAndRename(Schema original, List<Mutant<Schema>> mutants) {
        for (int i = 0; i < mutants.size(); i++) {
            Mutant<Schema> mutant = mutants.get(i);

            // Find the mutated constraint
            Schema mutantSchema = mutant.getMutatedArtefact();
            Constraint mutation = ChangedConstraintFinder.getDifferentConstraint(original, mutantSchema);

            // Remove the constraints, except the mutated one
            for (PrimaryKeyConstraint primaryKeyConstraint : mutantSchema.getPrimaryKeyConstraints()) {
                if (!mutation.equals(primaryKeyConstraint)) {
                    mutantSchema.removePrimaryKeyConstraint(primaryKeyConstraint.getTable());
                }
            }
            for (ForeignKeyConstraint foreignKeyConstraint : mutantSchema.getForeignKeyConstraints()) {
                if (!mutation.equals(foreignKeyConstraint)) {
                    mutantSchema.removeForeignKeyConstraint(foreignKeyConstraint);
                }
            }
            for (CheckConstraint checkConstraint : mutantSchema.getCheckConstraints()) {
                if (!mutation.equals(checkConstraint)) {
                    mutantSchema.removeCheckConstraint(checkConstraint);
                }
            }
            for (NotNullConstraint notNullConstraint : mutantSchema.getNotNullConstraints()) {
                if (!mutation.equals(notNullConstraint)) {
                    mutantSchema.removeNotNullConstraint(notNullConstraint);
                }
            }
            for (UniqueConstraint uniqueConstraint : mutantSchema.getUniqueConstraints()) {
                if (!mutation.equals(uniqueConstraint)) {
                    mutantSchema.removeUniqueConstraint(uniqueConstraint);
                }
            }

            // Rename the remaining constraints and tables
            MutationAnalysisUtils.renameChangedTableConstraints(mutant, i, mutation.getTable().getName());
            MutationAnalysisUtils.renameChangedTable(mutant, i, mutation.getTable().getName());
        }
    }

}
