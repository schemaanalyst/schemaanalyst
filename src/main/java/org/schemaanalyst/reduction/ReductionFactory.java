package org.schemaanalyst.reduction;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

public class ReductionFactory {
	
	public int numOfMerges = 0;
	public int numberOfSatisfiedRequirements = 0;
	
	public TestSuite reduceTestSuite(TestSuite originalTestSuite, 
			TestRequirements allTestRequirements, 
			Schema schema,
			List<TestRequirement> failedTestRequirements,
			int totalFulfilledRequirements,
			long randomseed,
			String reductionTechnique) {
		
		boolean unFulfilledReqs = false;
		boolean reductionFailed = false;
		//Reduction reduction = null;
		TestSuite reducedTestSuite = null;
		if (reductionTechnique.equals("additionalGreedy")) {
			System.out.println("Reduced with Additional Greedy");
			// Reduced with Additional Greedy
			AdditionalGreedyReduction reduction = new AdditionalGreedyReduction(originalTestSuite, allTestRequirements, schema, failedTestRequirements);
			reduction.removeEqualTestCases();
			
			if (!reduction.reduceTestSuite()) {
				reductionFailed = true;
			}
			
			if (!reduction.checkCoverage(totalFulfilledRequirements)) {
				unFulfilledReqs = true;
			}
			
			numberOfSatisfiedRequirements = reduction.numberOfSatisfiedRequirements;
			reducedTestSuite = reduction.getReducedTestSuite();
			
		} else if (reductionTechnique.equals("simpleGreedy")) {
			System.out.println("Reduced with Simple Greedy");
			// Reduced with Simple Greedy
			NaiveGreedyReduction reduction = new NaiveGreedyReduction(originalTestSuite, allTestRequirements, schema, failedTestRequirements);
			reduction.removeEqualTestCases();
			
			if (!reduction.reduceTestSuite()) {
				reductionFailed = true;
			}
			
			if (!reduction.checkCoverage(totalFulfilledRequirements)) {
				unFulfilledReqs = true;
			}
			
			numberOfSatisfiedRequirements = reduction.numberOfSatisfiedRequirements;
			reducedTestSuite = reduction.getReducedTestSuite();
			
		} else if (reductionTechnique.equals("HGS")) {
			System.out.println("Reduced with HGS");
			// Reduced with HGS
			HGSReduction reduction = new HGSReduction(originalTestSuite, allTestRequirements, schema, failedTestRequirements);
			reduction.removeEqualTestCases();
			
			if (!reduction.reduceTestSuite()) {
				reductionFailed = true;
			}
			
			if (!reduction.checkCoverage(totalFulfilledRequirements)) {
				unFulfilledReqs = true;
			}
			
			numberOfSatisfiedRequirements = reduction.numberOfSatisfiedRequirements;
			reducedTestSuite = reduction.getReducedTestSuite();
			
		} else if (reductionTechnique.equals("random")) {
			System.out.println("Reduced with Random Selection");
			// Reduced with Random
			RandomReduction reduction = new RandomReduction(originalTestSuite, allTestRequirements, schema, failedTestRequirements);
			reduction.removeEqualTestCases();
			
			if (!reduction.reduceTestSuite(randomseed)) {
				reductionFailed = true;
			}
			
			if (!reduction.checkCoverage(totalFulfilledRequirements)) {
				unFulfilledReqs = true;
			}
			numberOfSatisfiedRequirements = reduction.numberOfSatisfiedRequirements;
			reducedTestSuite = reduction.getReducedTestSuite();
			
		} else if (reductionTechnique.equals("sticcer")) {
			System.out.println("Reduced with STICCER");
			// Reduced with STICCER
			SticcerReduction reduction = new SticcerReduction(originalTestSuite, allTestRequirements, schema, failedTestRequirements);
			reduction.removeEqualTestCases();
			
			if (!reduction.mergeTestCases()) {
				reductionFailed = true;
			}
			
			if (!reduction.checkCoverage(totalFulfilledRequirements)) {
				unFulfilledReqs = true;
			}
			numberOfSatisfiedRequirements = reduction.numberOfSatisfiedRequirements;
			reducedTestSuite = reduction.getReducedTestSuite();
			
			this.numOfMerges = reduction.numberOfMerges;
			
			System.out.println("Number of merges == " + reduction.numberOfMerges);
		} else if (reductionTechnique.equals("sticcerD")) {
			System.out.println("Reduced with STICCER-D");
			// Reduced with STICCER
			DiverseSticcerReduction reduction = new DiverseSticcerReduction(originalTestSuite, allTestRequirements, schema, failedTestRequirements);
			reduction.removeEqualTestCases();
			
			if (!reduction.mergeTestCases()) {
				reductionFailed = true;
			}
			
			if (!reduction.checkCoverage(totalFulfilledRequirements)) {
				unFulfilledReqs = true;
			}
			numberOfSatisfiedRequirements = reduction.numberOfSatisfiedRequirements;
			reducedTestSuite = reduction.getReducedTestSuite();
			
			this.numOfMerges = reduction.numberOfMerges;
			
			System.out.println("Number of merges == " + reduction.numberOfMerges);
		} else {
			System.err.println("Incorrect Reduction Technique Entered -- you have the following options: simpleGreedy, additionalGreedy, HGS, random, sticcer");
		}
		
		if (unFulfilledReqs)
			System.out.println("Number of fulfilled TRs after reduction = " + numberOfSatisfiedRequirements);
		
		if (reductionFailed)
			System.out.println("Reduction Failed");
		
		if (reducedTestSuite != null)
			System.out.println("No. of TCs Post-reduction= " + reducedTestSuite.getTestCases().size());
		
		return reducedTestSuite;
		
	}

}
