package org.schemaanalyst.faultlocalization;

public class TarantulaResultMatrixRow {
	public String DBConstraint;
	public String Mutant;
	public int totFailed;
	public int totPassed;
	public int passed;
	public int failed;
	public double score;
	public boolean fault;
	
	public TarantulaResultMatrixRow(String dbC, String m, int tf, int tp,  int p, int f, boolean fault){
		this.DBConstraint = dbC;
		this.Mutant = m;
		this.totFailed = tf;
		this.totPassed = tp;
		this.passed= p;
		this.failed = f;
		this.fault = fault;
	}
	
	public void setFault(boolean f){
		this.fault = f;
	}
	
	public boolean isFault(){
		return this.fault;
	}
	
	public void setScore(double s){
		this.score = s;
	}
	
	public double getScore(){
		return this.score;
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
	
	

}
