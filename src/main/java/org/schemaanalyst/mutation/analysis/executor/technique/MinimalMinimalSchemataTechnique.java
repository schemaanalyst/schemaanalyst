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

import java.util.*;

/**
 *
 * @author Chris J. Wright
 */
public class MinimalMinimalSchemataTechnique extends Technique {

    final protected static int TRANSACTION_SIZE = 100;
    private final SQLWriter sqlWriter;
    private List<String> createStmts;
    private List<String> dropStmts;
    private List<String> deleteStmts;
    private Map<Integer, TestSuiteResult> resultMap;
    private Map<String, List<Integer>> changedTableMap;

    public MinimalMinimalSchemataTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions, String dataGenerator, String criterion, long randomseed) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed);
        this.sqlWriter = dbms.getSQLWriter();
    }

    @Override
    public AnalysisResult analyse(TestSuiteResult originalResults) {
        AnalysisResult result = new AnalysisResult();
        
        // Split based on those suitable for MinimalMinimal
        List<Mutant<Schema>> removalMutants = new ArrayList<>();
        List<Mutant<Schema>> minimalMutants = new ArrayList<>();
        for (Mutant<Schema> mutant : mutants) {
            String operator = mutant.getSimpleDescription();
//            System.out.println(operator);
            if (operator.endsWith("R") || operator.endsWith("Nullifier") || operator.endsWith("E")) {
//                System.out.println("Removal mutant");
                removalMutants.add(mutant);
            } else {
//                System.out.println("Minimal mutant");
                minimalMutants.add(mutant);
            }
        }
        // Analyse suitable mutants with this technique
        mutants = minimalMutants;
        // Analyse removal mutants with Minimal technique
        AnalysisResult removalResults = new MinimalSchemataTechnique(schema, removalMutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed).analyse(originalResults);
        for (Mutant<Schema> mutant : removalResults.getKilled()) {
//            System.out.println("Killed by Minimal");
            result.addKilled(mutant);
        }
        for (Mutant<Schema> mutant : removalResults.getLive()) {
//            System.out.println("Alive by Minimal");
            result.addLive(mutant);
        }

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
        removeOriginalConstraints(schema, mutants);
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
        for (TestCase testCase : testSuite.getTestCases()) {
            executeDeleteStmts();
            TestCaseResult normalTestResult = null;
            Map<Integer, TestCaseResult> failedMutants = new HashMap<>();
            Set<Integer> testedMutants = new HashSet<>();

            Data data = testCase.getState();
            normalTestResult = executeInserts(data, normalTestResult, failedMutants, testedMutants, testCase);
            data = testCase.getData();
            normalTestResult = executeInserts(data, normalTestResult, failedMutants, testedMutants, testCase);
            for (Integer id : testedMutants) {
                if (!failedMutants.containsKey(id)) {
                    resultMap.get(id).add(testCase, TestCaseResult.SuccessfulTestCaseResult);
                }
            }

        }

        // Build the TestSuiteResult objects
        for (int i = 0; i < mutants.size(); i++) {
            TestSuiteResult mutantResult = resultMap.get(i);
            boolean killed = false;
            for (int j = 0; j < originalResults.getResults().size(); j++) {
                MixedPair<TestCase, TestCaseResult> original = originalResults.getResults().get(j);
                MixedPair<TestCase, TestCaseResult> mutant = mutantResult.getResults().get(j);
                if (original.getSecond().wasSuccessful() && !mutant.getSecond().wasSuccessful()) {
                    killed = true;
                    break;
                }
            }
            if (killed) {
//                System.out.println("-----");
//                System.out.println("MM K: " + mutants.get(i).getDescription() + " (" + mutants.get(i).getSimpleDescription() + ")");
//                System.out.println("Original:");
//                System.out.println(originalResults);
//                System.out.println("Mutant:");
//                System.out.println(resultMap.get(i));
                result.addKilled(mutants.get(i));
            } else {
//                System.out.println("-----");
//                System.out.println("MM A: " + mutants.get(i).getDescription() + " (" + mutants.get(i).getSimpleDescription() + ")");
//                System.out.println("Original:");
//                System.out.println(originalResults);
//                System.out.println("Mutant:");
//                System.out.println(resultMap.get(i));
                result.addLive(mutants.get(i));
            }
        }

        executeDropStmts();

        return result;
    }

    private TestCaseResult executeInserts(Data data, TestCaseResult normalTestResult, Map<Integer, TestCaseResult> failedMutants, Set<Integer> testedMutants, TestCase testCase) {
        for (Table table : schema.getTablesInOrder()) {
            if (data.getTables().contains(table)) {
                List<Integer> applicableMutants = changedTableMap.get(table.getIdentifier().get());
                applicableMutants = applicableMutants == null ? new ArrayList<Integer>() : applicableMutants;
                testedMutants.addAll(applicableMutants);
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

    /**
     * Removes all constraints from a schema except the mutated constraint.
     *
     * @param original The original schema
     * @param mutants The collection of mutated schemas
     */
    private void removeOriginalConstraints(Schema original, List<Mutant<Schema>> mutants) {
        for (Mutant<Schema> mutant : mutants) {
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
        }
    }

}
