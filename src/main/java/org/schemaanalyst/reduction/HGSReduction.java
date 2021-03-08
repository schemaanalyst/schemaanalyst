package org.schemaanalyst.reduction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateCheckerFactory;

/**
 * Created by Abdullah on 09/2019.
 */

public class HGSReduction extends Reduction {

	public HGSReduction(TestSuite originalTestSuite, Schema schema) {
		super(originalTestSuite, schema);
	}
	
	public HGSReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema) {
		super(originalTestSuite, testRequirements, schema);
	}

	public HGSReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema,
			List<TestRequirement> failedTestRequirements) {
		super(originalTestSuite, testRequirements, schema, failedTestRequirements);
	}
	
	private int getMaxCardinality(HashMap<TestRequirement, TestSuite> trTS) {
		int max = 0;
		for (TestRequirement i : trTS.keySet()) {
			int size = trTS.get(i).getTestCases().size();
			if (size > max)
				max = size;
		}
		return max;
	}
	
	public boolean reduceTestSuite() {
		// T1 = tc1, tc2
		// T2 = tc1, tc3
		// T3 = tc2, tc3
		// TS = T1 & T2
		HashMap<TestRequirement, TestSuite> trTS = new HashMap<TestRequirement, TestSuite>();
		HashMap<TestSuite, Boolean> marks = new HashMap<TestSuite, Boolean>();
		for (TestRequirement tr : allTestRequirements.getTestRequirements()) {
			TestSuite newTS = new TestSuite();
			for (TestCase tc : originalTestSuite.getTestCases()) {
				boolean linkedTablesCheck = this.checkRequiredTables(tc, tr);
				boolean equalComparsion = tr.getRequiresComparisonRow() == tc.getTestRequirement()
						.getRequiresComparisonRow();
				boolean equalResults = tr.getResult() == tc.getTestRequirement().getResult();
				boolean passedAllChecks = linkedTablesCheck && equalComparsion && equalResults;
				if (passedAllChecks) {
					PredicateChecker predicateChecker = PredicateCheckerFactory.instantiate(tr.getPredicate(), true,
							tc.getData(), tc.getState());

					Predicate pr = tr.getPredicate();
					Predicate tcPR = tc.getTestRequirement().getPredicate();
					boolean checkStringChecker = pr.toString().equals(tcPR.toString());

					if (predicateChecker.check() || checkStringChecker) {
						newTS.addTestCase(tc);
					}
				}
			}
			trTS.put(tr, newTS);
			marks.put(newTS, false);
		}
		int currentCardinality = 1;
		int maxCardinality = this.getMaxCardinality(trTS);
		TestSuite newTS = new TestSuite();
		
		for (TestRequirement i : trTS.keySet()) {
			int size = trTS.get(i).getTestCases().size();
			if (size == currentCardinality) {
				newTS.addTestCase(trTS.get(i).getTestCases().get(0));
				marks.put(trTS.get(i), true);
			}
		}
		while (currentCardinality <= maxCardinality) {
			currentCardinality = currentCardinality + 1;
			for (TestRequirement i : trTS.keySet()) {
				if (!marks.get(trTS.get(i))) {
					if (trTS.get(i).getTestCases().size() == currentCardinality) {
						HashMap<TestCase, Integer> tcCounter = new HashMap<TestCase, Integer>();
						for (TestCase tc : trTS.get(i).getTestCases()) {
							int tc_counter = 1;
							for (TestRequirement tr : trTS.keySet()) {
								if (trTS.get(tr).getTestCases().contains(tc) || newTS.getTestCases().contains(tc)) {
									tc_counter++;
								}
							}
							tcCounter.put(tc, tc_counter);
						}
	
						Map.Entry<TestCase, Integer> maxEntry = null;
	
						for (Map.Entry<TestCase, Integer> entry : tcCounter.entrySet()) {
							if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
								maxEntry = entry;
							}
						}
						if (!newTS.getTestCases().contains(maxEntry.getKey())) {
							newTS.addTestCase(maxEntry.getKey());
							for (TestRequirement tr : trTS.keySet()) {
								if (trTS.get(tr).getTestCases().contains(maxEntry.getKey())) {
									marks.put(trTS.get(tr), true);
								}
							}
						}
					}
				}
			}
		}
	
		this.pickedTestCases = new ArrayList<TestCase>();
		for (TestCase tc : newTS.getTestCases()) {
			pickedTestCases.add(tc);
		}
		return true;
	}
	
}
