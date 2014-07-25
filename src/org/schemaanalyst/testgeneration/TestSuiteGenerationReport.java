package org.schemaanalyst.testgeneration;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;

import java.util.HashMap;
import java.util.Map;

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

}
