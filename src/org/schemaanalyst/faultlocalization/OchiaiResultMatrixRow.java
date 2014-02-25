package org.schemaanalyst.faultlocalization;

public class OchiaiResultMatrixRow {

	public String DBConstraint;
	public String Mutant;
	public int totFailed;
	public int passed;
	public int failed;
	public double score;
	public boolean fault;

	public OchiaiResultMatrixRow(String dbC, String m, int tf, int p, int f, boolean fault) {
		this.DBConstraint = dbC;
		this.Mutant = m;
		this.totFailed = tf;
		this.passed = p;
		this.failed = f;
		this.fault = fault;
	}
	
	public boolean isFault() {
		return fault;
	}

	public void setFault(boolean fault) {
		this.fault = fault;
	}

	public void setScore(double s){
		this.score = s;
	}
	
	public double getScore(){
		return score;
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

}
