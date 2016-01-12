package org.schemaanalyst.faultlocalization;

public class Calculator {

	public static void calculateOchiai(ResultMatrixRow r) {

		int totFailed = r.getTotFailed();
		int failed = r.getFailed();
		int passed = r.getPassed();

		double score = failed / (Math.sqrt(totFailed * (failed + passed)));
		r.setOchiaiScore(score);

	}

	public static void calculateTarantula(ResultMatrixRow r) {

		int totFailed = r.getTotFailed();
		int totPassed = r.getTotPassed();
		double failed = (double) r.getFailed();
		double passed = (double) r.getPassed();

		double score = ((failed / totFailed) / ((passed / totPassed) + (failed / totFailed)));
		r.setTarantulaScore(score);
	}
	
	public static void calculateJaccard(ResultMatrixRow r){
		double failedCovered = (double)r.getFailed();
		double passedCovered = (double)r.getPassed();
		double failedNotCovered = (double) r.getTotFailed() - r.getFailed();
		
			double score = failedCovered / (failedNotCovered + passedCovered);
			r.setJaccardScore(score);
		
		
		
	}

}
