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

public class NaiveGreedyReduction extends Reduction {
	
	public NaiveGreedyReduction(TestSuite originalTestSuite, Schema schema) {
		super(originalTestSuite, schema);
	}
	
	public NaiveGreedyReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema) {
		super(originalTestSuite, testRequirements, schema);
	}

	public NaiveGreedyReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema,
			List<TestRequirement> failedTestRequirements) {
		super(originalTestSuite, testRequirements, schema, failedTestRequirements);
	}
	
	
	public boolean reduceTestSuite() {
		this.contextTableCreator();
		HashMap<TestCase, Integer> sorted = this.sortTestCasesByCoveredTRs();
		this.pickedTestCases = new ArrayList<TestCase>();

		boolean coveredAll = false;
		for (TestCase testcase : sorted.keySet()) {
			if (!this.pickedTestCases.contains(testcase)) {
				boolean addToTestSuie = false;
				for (TestRequirement tr : this.contextTable.get(testcase)) {
					if (!this.covered.get(tr)) {
						this.covered.put(tr, true);
						addToTestSuie = true;
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
