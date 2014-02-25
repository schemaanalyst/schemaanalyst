package org.schemaanalyst.faultlocalization;

public class Calculator {

	public static void calculate(OchiaiResultMatrixRow r) {

		int totFailed = r.getTotFailed();
		int failed = r.getFailed();
		int passed = r.getPassed();

		double score = failed / (Math.sqrt(totFailed * (failed + passed)));
		r.setScore(score);

	}

	public static void calculate(TarantulaResultMatrixRow r) {

		int totFailed = r.getTotFailed();
		int totPassed = r.getTotPassed();
		double failed = (double) r.getFailed();
		double passed = (double) r.getPassed();

		double score = ((failed / totFailed) / ((passed / totPassed) + (failed / totFailed)));
		r.setScore(score);
	}

}
