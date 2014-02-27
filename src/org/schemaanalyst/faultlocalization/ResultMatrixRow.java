package org.schemaanalyst.faultlocalization;

public class ResultMatrixRow {

	public String DBConstraint;
	public String Mutant;
	public int totFailed;
	public int totPassed;
	public int passed;
	public int failed;
	public double score;
	public boolean fault;
	public double OchiaiScore;
	public double TarantulaScore;
	public double JaccardScore;
	
	public ResultMatrixRow(String dbc, String m, int tf, int tp, int p, int f, boolean fau){
		this.DBConstraint = dbc;
		this.Mutant = m;
		this.totFailed = tf;
		this.totPassed = tp;
		this.passed = p;
		this.failed = f;
		this.fault = fau;
	}

	public String getDBConstraint() {
		return DBConstraint;
	}

	public void setDBConstraint(String dBConstraint) {
		DBConstraint = dBConstraint;
	}

	public String getMutant() {
		return Mutant;
	}

	public void setMutant(String mutant) {
		Mutant = mutant;
	}

	public int getTotFailed() {
		return totFailed;
	}

	public void setTotFailed(int totFailed) {
		this.totFailed = totFailed;
	}

	public int getTotPassed() {
		return totPassed;
	}

	public void setTotPassed(int totPassed) {
		this.totPassed = totPassed;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public boolean isFault() {
		return fault;
	}

	public void setFault(boolean fault) {
		this.fault = fault;
	}

	public double getOchiaiScore() {
		return OchiaiScore;
	}

	public void setOchiaiScore(double ochiaiScore) {
		OchiaiScore = ochiaiScore;
	}

	public double getTarantulaScore() {
		return TarantulaScore;
	}

	public void setTarantulaScore(double tarantulaScore) {
		TarantulaScore = tarantulaScore;
	}

	public double getJaccardScore() {
		return JaccardScore;
	}

	public void setJaccardScore(double jaccardScore) {
		JaccardScore = jaccardScore;
	}
	
	
	
}
