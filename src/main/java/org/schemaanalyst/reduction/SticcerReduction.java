package org.schemaanalyst.reduction;

import java.util.List;

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

/**
 * Created by Abdullah on 09/2019.
 */

public class SticcerReduction extends AdditionalGreedyReduction {
	
	public SticcerReduction(TestSuite originalTestSuite, Schema schema) {
		super(originalTestSuite, schema);
	}
	
	public SticcerReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema) {
		super(originalTestSuite, testRequirements, schema);
	}

	public SticcerReduction(TestSuite originalTestSuite, TestRequirements testRequirements, Schema schema,
			List<TestRequirement> failedTestRequirements) {
		super(originalTestSuite, testRequirements, schema, failedTestRequirements);
	}
	
	public boolean mergeTestCases() {
		// Additional Greedy reduction
		boolean reduced = super.reduceTestSuite();
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
