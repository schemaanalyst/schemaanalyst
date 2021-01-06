package org.schemaanalyst.reduction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

/**
 * Created by Abdullah on 09/2019.
 */

public class AdditionalGreedyReduction extends Reduction {
	
	public AdditionalGreedyReduction(TestSuite originalTestSuite, Schema schema) {
		super(originalTestSuite, schema);
	}
	
	public AdditionalGreedyReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema) {
		super(originalTestSuite, testRequirements, schema);
	}

	public AdditionalGreedyReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema,
			List<TestRequirement> failedTestRequirements) {
		super(originalTestSuite, testRequirements, schema, failedTestRequirements);
	}
	
	public boolean reduceTestSuite() {
		this.contextTableCreator();
		HashMap<TestCase, Integer> sorted = this.sortTestCasesByCoveredTRs();
		this.pickedTestCases = new ArrayList<TestCase>();

		boolean coveredAll = false;
		while (!coveredAll) {
			TestCase testcase = sorted.entrySet().iterator().next().getKey();
			if (!this.pickedTestCases.contains(testcase)) {
				boolean addToTestSuie = false;
				for (TestRequirement tr : this.contextTable.get(testcase)) {
					if (!this.covered.get(tr)) {
						this.covered.put(tr, true);
						addToTestSuie = true;
						// remove covered trs for the next iteration
						for (TestCase t : contextTable.keySet()) {
							if(contextTable.get(t).contains(tr)) {
								// remove the covered test case from the requirement
								int new_size = this.testCaseTRCounter.get(t) - 1;
								this.testCaseTRCounter.put(t, new_size);
							}
						}
						// sort again
						sorted = this.sortTestCasesByCoveredTRs();
					}
				}
				if (addToTestSuie) {
					this.pickedTestCases.add(testcase);
				}
			}

			if (!this.covered.containsValue(false)) {
				// All been covered
				coveredAll = true;
				break;
			}
		}
		
		return coveredAll;
	}
	
}