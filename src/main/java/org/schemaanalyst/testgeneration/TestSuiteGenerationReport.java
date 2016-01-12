package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;

import java.util.*;

/**
 * Created by phil on 24/07/2014.
 */
public class TestSuiteGenerationReport {

    private Map<Table, DataGenerationResult> initialTableDataResults;
    private Map<TestRequirement, DataGenerationResult> testRequirementResults;

    public TestSuiteGenerationReport() {
        initialTableDataResults = new HashMap<>();
        testRequirementResults = new HashMap<>();
    }

    public void addInitialTableDataResult(Table table, DataGenerationResult result) {
        initialTableDataResults.put(table, result);
    }

    public void addTestRequirementResult(TestRequirement testRequirement, DataGenerationResult result) {
        testRequirementResults.put(testRequirement, result);
    }

    public DataGenerationResult getDataGenerationResult(TestRequirement testRequirement) {
        return testRequirementResults.get(testRequirement);
    }

    public List<Table> getFailedInitialTableDataGenerationAttempts() {
        List<Table> failed = new ArrayList<>();
        for (Table table : initialTableDataResults.keySet()) {
            DataGenerationResult result = initialTableDataResults.get(table);
            if (result == null || !result.getReport().isSuccess()) {
                failed.add(table);
            }
        }
        return failed;
    }

    public int getInitialTableDataGenerationAttemptsFailed() {
        return getFailedInitialTableDataGenerationAttempts().size();
    }

    public List<TestRequirement> getFailedTestRequirements() {
        List<TestRequirement> failed = new ArrayList<>();
        for (TestRequirement testRequirement : testRequirementResults.keySet()) {
            DataGenerationResult result = testRequirementResults.get(testRequirement);
            if (result == null || !result.getReport().isSuccess()) {
                failed.add(testRequirement);
            }
        }
        return failed;
    }

    public int getNumTestRequirementsCovered() {
        int total = 0;
        for (TestRequirement testRequirement : testRequirementResults.keySet()) {
            DataGenerationResult result = testRequirementResults.get(testRequirement);
            if (result != null && result.getReport().isSuccess()) {
                total ++;
            }
        }
        return total;
    }

    public int getNumTestRequirementsFailed() {
        return getNumTestRequirementsAttempted() - getNumTestRequirementsCovered();
    }

    public int getNumTestRequirementsAttempted() {
        return testRequirementResults.keySet().size();
    }

    public double coverage() {
        return 100 * (getNumTestRequirementsCovered() / (double) getNumTestRequirementsAttempted());
    }

    public int getNumStateEvaluations(boolean successfulTestCasesOnly) {
        int evaluations = 0;
        Set<Table> tablesUsed = new HashSet<>();

        for (TestRequirement testRequirement : testRequirementResults.keySet()) {
            DataGenerationResult result = testRequirementResults.get(testRequirement);
            if (result != null) {
                if (!successfulTestCasesOnly || (successfulTestCasesOnly && result.getReport().isSuccess())) {
                    Data state = result.getState();
                    tablesUsed.addAll(state.getTables());
                }
            }
        }

        for (Table table : initialTableDataResults.keySet()) {
            if (tablesUsed.contains(table)) {
                evaluations += initialTableDataResults.get(table).getReport().getNumEvaluations();
            }
        }

        return evaluations;
    }

    public int getNumDataEvaluations(boolean successfulTestCasesOnly) {
        int evaluations = 0;
        for (TestRequirement testRequirement : testRequirementResults.keySet()) {
            DataGenerationResult result = testRequirementResults.get(testRequirement);
            if (result != null) {
                if (!successfulTestCasesOnly || (successfulTestCasesOnly && result.getReport().isSuccess())) {
                    evaluations += result.getReport().getNumEvaluations();
                }
            }
        }
        return evaluations;
    }

    public int getNumEvaluations(boolean successfulTestCasesOnly) {
        return getNumStateEvaluations(successfulTestCasesOnly) + getNumDataEvaluations(successfulTestCasesOnly);
    }
}
