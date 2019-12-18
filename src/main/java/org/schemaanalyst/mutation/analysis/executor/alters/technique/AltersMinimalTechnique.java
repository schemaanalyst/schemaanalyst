package org.schemaanalyst.mutation.analysis.executor.alters.technique;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.alters.sqlwriter.AlterTableConstraintWriter;
import org.schemaanalyst.mutation.analysis.executor.alters.testcase.AltersTestCaseExecutor;
import org.schemaanalyst.mutation.analysis.executor.exceptions.StatementException;
import org.schemaanalyst.mutation.analysis.executor.technique.AnalysisResult;
import org.schemaanalyst.mutation.analysis.executor.technique.Technique;
import org.schemaanalyst.mutation.analysis.executor.testcase.TestCaseResult;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.mutation.analysis.executor.util.MutationAnalysisUtils;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.util.tuple.MixedPair;

import java.util.*;

/**
 *
 * @author Chris J. Wright
 */
public class AltersMinimalTechnique extends Technique {

    public AltersMinimalTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, 
    		String dataGenerator, String criterion, long randomseed) {
    	super(schema, mutants, testSuite, dbms, databaseInteractor, false, dataGenerator, criterion, randomseed);
    }

    @Override
    public AnalysisResult analyse(TestSuiteResult originalResults) {
        // Setup the map of results
        Map<Mutant<Schema>, TestSuiteResult> resultMap = new HashMap<>();
        for (Mutant<Schema> mutant : mutants) {
            resultMap.put(mutant, new TestSuiteResult());
        }
        
        // Add the tables
        AltersTestCaseExecutor testCaseExecutor = new AltersTestCaseExecutor(schema, databaseInteractor, dbms.getSQLWriter());
        testCaseExecutor.executeCreates();
        
        // Insert the test data
        for (TestCase testCase : testSuite.getTestCases()) {

            // Insert the test data
            testCaseExecutor.executeInserts(testCase.getState());
            testCaseExecutor.executeInserts(testCase.getData());

            for (Mutant<Schema> mutant : mutants) {
                // Add the constraints
                executeAlters(schema, mutant, testCase, resultMap.get(mutant));

                // Drop the constraints
                testCaseExecutor.executeDropAlters(mutant.getMutatedArtefact());
            }

            // Delete the test data
            testCaseExecutor.executeDeletes();
        }

        // Drop the tables
        testCaseExecutor.executeDrops();
        
        // Compare results
        AnalysisResult result = compareResults(originalResults, resultMap);
        
        return result;
    }

    private AnalysisResult compareResults(TestSuiteResult originalResult, Map<Mutant<Schema>, TestSuiteResult> resultMap) {
        AnalysisResult result = new AnalysisResult();
        for (Map.Entry<Mutant<Schema>, TestSuiteResult> entry : resultMap.entrySet()) {
            TestSuiteResult mutantResult = entry.getValue();
            List<MixedPair<TestCase, TestCaseResult>> originalResults = new ArrayList<>(originalResult.getResults());
            List<MixedPair<TestCase, TestCaseResult>> mutantResults = new ArrayList<>(mutantResult.getResults());

            // If the results sets are the same size, then we compare like-for-like
            if (originalResults.size() == mutantResults.size()) {
                boolean killed = false;
                for (int i = 0; i < originalResults.size(); i++) {
                    if (originalResults.get(i).getSecond().wasSuccessful() != mutantResults.get(i).getSecond().wasSuccessful()) {
                        killed = true;
                        break;
                    }
                }
                if (killed) {
                    result.addKilled(entry.getKey());
                } else {
                    result.addLive(entry.getKey());
                }
            } // If the results are mismatched, we find the difference and check for rejection
            else {
                boolean killed = false;
                Set<MixedPair<TestCase, TestCaseResult>> set = new HashSet<>();
                if (originalResults.size() > mutantResults.size()) {
                    set.addAll(originalResults);
                    set.removeAll(mutantResults);
                } else {
                    set.addAll(mutantResults);
                    set.removeAll(originalResults);
                }
                for (MixedPair<TestCase, TestCaseResult> mixedPair : set) {
                    if (!mixedPair.getSecond().wasSuccessful()) {
                        killed = true;
                        break;
                    }
                }
                if (killed) {
                    result.addKilled(entry.getKey());
                } else {
                    result.addLive(entry.getKey());
                }
            }
        }
        return result;
    }

    private void executeAlters(Schema original, Mutant<Schema> mutant, TestCase testCase, TestSuiteResult result) {
        Constraint constraint = MutationAnalysisUtils.computeChangedConstraint(original, mutant);
        
        // Build the statements
        AlterTableConstraintWriter writer = new AlterTableConstraintWriter();
        String alterAdd = "ALTER TABLE " + constraint.getTable().getName() + " " + writer.writeConstraint(constraint);
        String alterDrop = "ALTER TABLE " + constraint.getTable().getName() + " " + writer.writeDropConstraint(constraint);
        
        // Execute add
        Integer response = databaseInteractor.executeUpdate(alterAdd);
        if (response == 0) {
            result.add(testCase, TestCaseResult.SuccessfulTestCaseResult);
        } else {
            result.add(testCase, new TestCaseResult(new StatementException("Failed, result was: " + response, alterAdd)));
        }
        
        // Execute drop
        databaseInteractor.executeUpdate(alterDrop);
    }
}
