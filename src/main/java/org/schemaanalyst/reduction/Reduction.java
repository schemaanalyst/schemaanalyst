package org.schemaanalyst.reduction;

import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateCheckerFactory;

/**
 * Created by Abdullah on 09/2019.
 */

public class Reduction {
	protected TestSuite originalTestSuite;
	protected TestSuite reducedTestSuite;
	protected TestRequirements allTestRequirements;
	protected List<TestRequirement> failedTestRequirements;
	protected int numberOfSatisfiedRequirements;
	protected Schema schema;
	
	// The following properties are used to reduce the contextTable
	protected HashMap<TestCase, List<TestRequirement>> contextTable;
	// A tracker for covered test requirements
	protected HashMap<TestRequirement, Boolean> covered;
	// Each test case with the number (counted) of test requirements (TRs) covered
	protected HashMap<TestCase, Integer> testCaseTRCounter;
	// Selected test case for the reduced test suite
	protected List<TestCase> pickedTestCases;
	public int numberOfMerges = 0;
	
	public Reduction(TestSuite originalTestSuite, Schema schema) {
		this.originalTestSuite = originalTestSuite;
		this.allTestRequirements = null;
		this.schema = schema;
	}

	public Reduction(TestSuite originalTestSuite, TestRequirements allTestRequirements, Schema schema) {
		this.originalTestSuite = originalTestSuite;
		this.allTestRequirements = allTestRequirements;
		this.schema = schema;
	}

	public Reduction(TestSuite originalTestSuite, TestRequirements allTestRequirements, Schema schema,
			List<TestRequirement> failedTestRequirements) {
		this.originalTestSuite = originalTestSuite;
		this.allTestRequirements = allTestRequirements;
		this.schema = schema;
		this.failedTestRequirements = failedTestRequirements;
	}
	
	public void copyOriginalTestSuiteIntoReducedTestSuite() {
		reducedTestSuite = new TestSuite();

		for (TestCase tc : originalTestSuite.getTestCases()) {
			reducedTestSuite.addTestCase(tc);
		}
	}
	
	public boolean checkRequiredTables(TestCase tc, TestRequirement tr) {
		for (Table table : tr.getTables()) {
			if (!tc.getTestRequirement().getTables().contains(table))
				return false;
		}
		for (Table table : tc.getTestRequirement().getTables()) {
			if (!tr.getTables().contains(table))
				return false;
		}
		return true;
	}

	public boolean checkRequiresComparisonRow(TestCase tc1, TestCase tc2) {
		return tc1.getTestRequirement().getRequiresComparisonRow() == tc2.getTestRequirement()
				.getRequiresComparisonRow();
	}

	public boolean checkEqualResults(TestCase tc1, TestCase tc2) {
		return tc1.getTestRequirement().getResult() == tc2.getTestRequirement().getResult();
	}
	
	private void combiningDuplicateTestCases() {

		this.copyOriginalTestSuiteIntoReducedTestSuite();

		for (int i = 0; i < reducedTestSuite.getTestCases().size(); i++) {
			for (int j = i + 1; j < reducedTestSuite.getTestCases().size(); j++) {
				Data state1 = reducedTestSuite.getTestCases().get(i).getState();
				Data data1 = reducedTestSuite.getTestCases().get(i).getData();
				Data state2 = reducedTestSuite.getTestCases().get(j).getState();
				Data data2 = reducedTestSuite.getTestCases().get(j).getData();
				if (state1.toString().equals(state2.toString()) && data1.toString().equals(data2.toString())) {
					for (TestRequirementDescriptor d : reducedTestSuite.getTestCases().get(j).getTestRequirement()
							.getDescriptors()) {
						boolean notInDesc = true;
						for (TestRequirementDescriptor desc : reducedTestSuite.getTestCases().get(i).getTestRequirement()
								.getDescriptors()) {
							if (desc.toString().equals(d.toString()))
								notInDesc = false;
						}
						if (notInDesc) {
							String des = d.getMsg() + " - Reduced";
							TestRequirementDescriptor newD = new TestRequirementDescriptor(d.getID(), des);
							reducedTestSuite.getTestCases().get(i).getTestRequirement().addDescriptor(newD);
						}
					}
					reducedTestSuite.removeTestCase(j);
					j--;
				}
			}
		}
	}
	
	public void contextTableCreator() {
		contextTable = new HashMap<TestCase, List<TestRequirement>>();
		testCaseTRCounter = new HashMap<TestCase, Integer>();
		covered = new HashMap<TestRequirement, Boolean>();
		
		for (TestCase tc : originalTestSuite.getTestCases()) {
			List<TestRequirement> coveredTestRequirements = new ArrayList<TestRequirement>();
			if (!coveredTestRequirements.contains(tc.getTestRequirement()))
				coveredTestRequirements.add(tc.getTestRequirement());
			for (TestRequirement tr : allTestRequirements.getTestRequirements()) {
				if (!failedTestRequirements.contains(tr)) {
					covered.put(tr, false);
					boolean linkedTablesCheck = this.checkRequiredTables(tc, tr);
					boolean equalComparsion = tr.getRequiresComparisonRow() == tc.getTestRequirement()
							.getRequiresComparisonRow();
					boolean equalResults = tr.getResult() == tc.getTestRequirement().getResult();
					boolean passedAllChecks = linkedTablesCheck && equalComparsion && equalResults;
					if (passedAllChecks) {
						PredicateChecker predicateChecker = PredicateCheckerFactory.instantiate(tr.getPredicate(), true,
								tc.getData(), tc.getState());
						if (predicateChecker.check()) {
							if (!coveredTestRequirements.contains(tr)) {
								coveredTestRequirements.add(tr);
								
								for (TestRequirementDescriptor d : tr.getDescriptors()) {
									boolean notInDesc = true;
									for (TestRequirementDescriptor desc : tc.getTestRequirement().getDescriptors()) {
										if (desc.getID().toString().equals(d.getID().toString()))
											notInDesc = false;
									}
									if (notInDesc) {
										String des = d.getMsg() + " - Subsumed - " + tr.getResult();
										TestRequirementDescriptor newD = new TestRequirementDescriptor(d.getID(), des);
										tc.getTestRequirement().addDescriptor(newD);
									}
								}
								
							}
						}
					}
					testCaseTRCounter.put(tc, coveredTestRequirements.size());
					contextTable.put(tc, coveredTestRequirements);
				}
			}
		}
	}
	
	public List<TestCase> getContextTableTestCases() {
		List<TestCase> testCases = new ArrayList<TestCase>();
		for (TestCase tc : contextTable.keySet()) {
			testCases.add(tc);
		}
		return testCases;
	}
	
	public HashMap<TestCase, Integer> sortTestCasesByCoveredTRs() {
		HashMap<TestCase, Integer> sortedTestCases = testCaseTRCounter.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		return sortedTestCases;
	}
	
	private void copyOriginalTSPropertiesIntoReducedTS() {
		reducedTestSuite.addGeneratedInserts(originalTestSuite.getGeneratedInserts());
		reducedTestSuite.addReducedInsertsCount(originalTestSuite.getReducedInsertsCount());
	}

	
	public TestSuite getReducedTestSuite() {
		reducedTestSuite = new TestSuite();
		for (TestCase tc : this.pickedTestCases) {
			reducedTestSuite.addTestCase(tc);
		}
		
		this.copyOriginalTSPropertiesIntoReducedTS();
		
		return reducedTestSuite;
	}
	
	public boolean checkCoverage(int numTestRequirementsCovered) {
		int numberOfStatisfied = 0;
		List<TestRequirement> nonSatisfiedTrs = new ArrayList<TestRequirement>();
		for (TestRequirement tr : allTestRequirements.getTestRequirements()) {
			boolean statisfied = false;
			for (TestCase tc : pickedTestCases) {
				boolean linkedTablesCheck = this.checkRequiredTables(tc, tr);
				boolean equalComparsion = tr.getRequiresComparisonRow() == tc.getTestRequirement()
						.getRequiresComparisonRow();
				boolean equalResults = tr.getResult() == tc.getTestRequirement().getResult();
				boolean passedAllChecks = linkedTablesCheck && equalComparsion && equalResults;
				if (passedAllChecks) {
					tr.getPredicate().reduce();
					PredicateChecker predicateChecker = PredicateCheckerFactory.instantiate(tr.getPredicate(), true,
							tc.getData(), tc.getState());
					Predicate pr = tr.getPredicate();
					Predicate tcPR = tc.getTestRequirement().getPredicate();
					boolean checkStringChecker = pr.toString().equals(tcPR.toString());

					for (TestRequirementDescriptor d : tr.getDescriptors()) {
						for (TestRequirementDescriptor desc : tc.getTestRequirement().getDescriptors()) {
							if (desc.getID().toString().equals(d.getID().toString()))
								statisfied = true;
						}
					}

					if (predicateChecker.check() || checkStringChecker) {
						statisfied = true;
						break;
					}
				}
			}
			if (statisfied) {
				numberOfStatisfied++;
				if (nonSatisfiedTrs.contains(tr))
					nonSatisfiedTrs.remove(tr);
			} else {
				if (!nonSatisfiedTrs.contains(tr)) {
					nonSatisfiedTrs.add(tr);
				}
			}
		}

		this.numberOfSatisfiedRequirements = numberOfStatisfied;
		return numberOfStatisfied == numTestRequirementsCovered;
	}
	
	public void removeEqualTestCases() {
		this.combiningDuplicateTestCases();
	}
	
}
