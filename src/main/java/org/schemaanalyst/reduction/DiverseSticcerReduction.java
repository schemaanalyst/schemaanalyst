package org.schemaanalyst.reduction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
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

import info.debatty.java.stringsimilarity.Cosine;

/**
 * Created by Abdullah on 12/2020.
 */

public class DiverseSticcerReduction extends Reduction {
	
	public DiverseSticcerReduction(TestSuite originalTestSuite, Schema schema) {
		super(originalTestSuite, schema);
	}
	
	public DiverseSticcerReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema) {
		super(originalTestSuite, testRequirements, schema);
	}

	public DiverseSticcerReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema,
			List<TestRequirement> failedTestRequirements) {
		super(originalTestSuite, testRequirements, schema, failedTestRequirements);
	}
	
	public boolean reduceTestSuite() {
		this.contextTableCreator();
		HashMap<TestCase, Integer> sorted = this.sortTestCasesByCoveredTRs();
		this.pickedTestCases = new ArrayList<TestCase>();

		boolean coveredAll = false;
		int counter = 0;
		while (!coveredAll) {
			TestCase testcase = sorted.entrySet().iterator().next().getKey();
			/*
			if (counter == 63) {
				System.out.println("Here");
			}
			*/
			if (!this.pickedTestCases.contains(testcase)) {
				/*
				System.out.println(counter);
				if (counter == 61) {
					System.out.println("Target");
				}
				*/
				boolean addToTestSuie = false;
				for (TestRequirement tr : this.contextTable.get(testcase)) {
					
					if (!this.covered.get(tr)) {
						// Here check for ties and then create a tie-breaker rather than selecting at random
						// Create a method to check this test case requirements against others
						// NOTE this might be very interesting.
						HashMap<TestCase, Double> equalTestCases = new HashMap<TestCase, Double>();
						equalTestCases.put(testcase, 0.0);
						// Get test cases that cover {tr}
						for (TestCase t : contextTable.keySet()) {
							if(contextTable.get(t).containsAll(contextTable.get(testcase)) && !t.equals(testcase)) {
								//System.out.println("HERE");
								equalTestCases.put(t, 1.0);
							}
						}
						// get the minimum
						if(equalTestCases.size() > 2) {
							// Score each test case Cosine similarity compared to the first
							// Get String of data of the main test case
							String mainTest = testcase.getAllValues().toString();
							Cosine meteric = new Cosine();
							for (TestCase aTestCase : equalTestCases.keySet()) {
								if (!aTestCase.equals(testcase)) {
									double diveristy = meteric.similarity(mainTest, aTestCase.getAllValues().toString());
									equalTestCases.put(aTestCase, diveristy);
								}
							}
							
							equalTestCases.remove(testcase);
							Entry<TestCase, Double> min = null;
							for (Entry<TestCase, Double> entry : equalTestCases.entrySet()) {
							    if ((min == null || min.getValue() > entry.getValue()) && !entry.getKey().equals(testcase)) {
							        min = entry;
							    }
							}
							//System.out.println("Found Something different");
							testcase = min.getKey();
						}
						// if new test case selected set as its requirment covered
						//this.covered.put(testcase.getTestRequirement(), true);
						// END of new tie breaker
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
					sorted = this.sortTestCasesByCoveredTRs();
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
	
	public boolean mergeTestCases() {
		// Additional Greedy reduction
		boolean reduced = this.reduceTestSuite();
		// merge
		for (int i = 0; i < this.pickedTestCases.size(); i++) {
			for (int j = i+1; j < this.pickedTestCases.size(); j++) {
				TestCase upperTC = this.pickedTestCases.get(i);
				TestCase lowerTC = this.pickedTestCases.get(j);
				if (!upperTC.equals(lowerTC)) {
					// Check if they have equal tables
					boolean linkedTablesCheck = this.checkRequiredTables(lowerTC, upperTC.getTestRequirement());
					// Check if they are equal in results
					boolean equalTestResults = this.checkEqualResults(upperTC, lowerTC);
					// Check row comparsion are equal
					boolean comparsionRowsRequired = this.checkRequiresComparisonRow(upperTC, lowerTC);

					// if all is success full check predicates on the current test data
					if (linkedTablesCheck && equalTestResults && comparsionRowsRequired) {
						Predicate upperTCpredicate = upperTC.getTestRequirement().getPredicate();
						Predicate lowerTCpredicate = lowerTC.getTestRequirement().getPredicate();
						// check data compatibility
						// If the upper subsums the lower test case then remove lower test
						// else check merge of states
						boolean removeLowerTestCase = false;
						boolean combained = false;

						// Prepare to check merge
						Data upperTCstate = upperTC.getState();
						Data upperTCdata = upperTC.getData();
						Data lowerTCdata = lowerTC.getData();

						// Check merge of upper state + upper test data against lower state test data
						Data combainedUpperStateAndData = new Data();
						combainedUpperStateAndData.appendData(upperTCstate);
						combainedUpperStateAndData.appendData(upperTCdata);

						// predicate check merge
						PredicateChecker combainedStateAndDataWithUpperTC;
						combainedStateAndDataWithUpperTC = PredicateCheckerFactory.instantiate(upperTCpredicate, true,
								lowerTCdata, combainedUpperStateAndData);

						PredicateChecker combainedStateAndDataWithLowerTC;
						combainedStateAndDataWithLowerTC = PredicateCheckerFactory.instantiate(lowerTCpredicate, true,
								lowerTCdata, combainedUpperStateAndData);

						boolean successComboUpper = combainedStateAndDataWithUpperTC.check();
						boolean successComboLower = combainedStateAndDataWithLowerTC.check();
						// All successful then merge and remove the test case
						if (successComboUpper || successComboLower) {
							// check each row of the upper test data as an state
							PredicateChecker combainedStateAndData1;
							PredicateChecker combainedStateAndData2;
							Data combochecker = new Data();
							combochecker.appendData(upperTC.getData());
							combochecker.appendData(lowerTC.getData());
							boolean compliant = true;

							for (Table t : upperTCdata.getTables()) {
								for (Row stateRow : upperTCdata.getRows(t)) {
									for (Row testRow : lowerTCdata.getRows(t)) {
										Data stateData = new Data();
										stateData.addRow(t, stateRow);

										Data testData = new Data();
										testData.addRow(t, testRow);

										combainedStateAndData1 = PredicateCheckerFactory.instantiate(upperTCpredicate,
												true, testData, stateData);

										combainedStateAndData2 = PredicateCheckerFactory.instantiate(lowerTCpredicate,
												true, testData, stateData);
										
										if (!combainedStateAndData1.check() && !combainedStateAndData2.check())
											compliant = false;
									}
								}
							}
							if (compliant) {
								upperTC.getData().appendData(lowerTCdata);
								Data wellFormatedData = new Data();
								for (Table tbl : schema.getTablesInOrder()) {
									if (upperTC.getData().getNumRows(tbl) > 0) {
										wellFormatedData.addRows(tbl, upperTCdata.getRows(tbl));
									}
								}

								upperTC.setData(wellFormatedData);

								removeLowerTestCase = true;
								combained = true;
							}
						}

						if (removeLowerTestCase) {
							for (TestRequirementDescriptor d : lowerTC.getTestRequirement().getDescriptors()) {
								boolean notInDesc = true;
								for (TestRequirementDescriptor desc : upperTC.getTestRequirement().getDescriptors()) {
									if (desc.getID().toString().equals(d.getID().toString()))
										notInDesc = false;
								}
								if (notInDesc) {
									String des = d.getMsg() + (combained ? " - Merged" : " - Reduced");
									TestRequirementDescriptor newD = new TestRequirementDescriptor(d.getID(), des);
									upperTC.getTestRequirement().addDescriptor(newD);
								}
							}

							pickedTestCases.remove(j);
							j--;
							numberOfMerges++;
						}
					}
				}
			}
		}
		return reduced;
	}

}
