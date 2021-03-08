package org.schemaanalyst.reduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

/**
 * Created by Abdullah on 09/2019.
 */

public class RandomReduction extends Reduction {

	public RandomReduction(TestSuite originalTestSuite, Schema schema) {
		super(originalTestSuite, schema);
	}
	
	public RandomReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema) {
		super(originalTestSuite, testRequirements, schema);
	}

	public RandomReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema,
			List<TestRequirement> failedTestRequirements) {
		super(originalTestSuite, testRequirements, schema, failedTestRequirements);
	}
	
	public boolean reduceTestSuite(long randomseed) {
		Random random = new Random(randomseed);
		this.contextTableCreator();
		List<TestCase> testCases = this.getContextTableTestCases();
		this.pickedTestCases = new ArrayList<TestCase>();

		boolean coveredAll = false;
		while (!coveredAll) {
			TestCase aTestCase = testCases.get(random.nextInt(testCases.size()));
			if (!this.pickedTestCases.contains(aTestCase)) {
				boolean addToTestSuie = false;
				for (TestRequirement tr : contextTable.get(aTestCase)) {
					if (!this.covered.get(tr)) {
						this.covered.put(tr, true);
						addToTestSuie = true;
					}
				}
				if (addToTestSuie) {
					this.pickedTestCases.add(aTestCase);
				}
			}

			if (!this.covered.containsValue(false)) {
				coveredAll = true;
				break;
			}
		}
		//System.out.println("Are all the requirments covered == " + coveredAll);
		return coveredAll;
	}

}
