package org.schemaanalyst.faultlocalization;

import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

public class ResultMatrixRow {

	public Constraint DBConstraint;
	public String Mutant;
	public int totFailed;
	public int totPassed;
	public int passed;
	public int failed;
	public double oScore;
	public boolean fault;
	public double OchiaiScore;
	public double TarantulaScore;
	public double JaccardScore;
	public double jScore;
	public double tScore;
	public int ochiaiRank;
	public int tarantulaRank;
	public int jaccardRank;
	
	public ResultMatrixRow(Constraint dbc, String m, int tf, int tp, int p, int f, boolean fau){
		this.DBConstraint = dbc;
		this.Mutant = m;
		this.totFailed = tf;
		this.totPassed = tp;
		this.passed = p;
		this.failed = f;
		this.fault = fau;
	}

	public Constraint getDBConstraint() {
		return DBConstraint;
	}
	
	public String getDBConstraintName(){
		return DBConstraint.getName();
	}

	public void setDBConstraint(Constraint dBConstraint) {
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

	public double getScoreOchiai() {
		return oScore;
	}

	public void setScoreOchiai(double score) {
		this.oScore = score;
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
	
	
	public double getScoreTarantula() {
		return tScore;
	}

	public void setScoreTarantula(double score) {
		this.tScore = score;
	}
	
	public double getScoreJaccard() {
		return jScore;
	}

	public void setScoreJaccard(double score) {
		this.jScore = score;
	}
	public int getOchiaiRank() {
		return ochiaiRank;
	}

	public void setOchiaiRank(int ochiaiRank) {
		this.ochiaiRank = ochiaiRank;
	}

	public int getTarantulaRank() {
		return tarantulaRank;
	}

	public void setTarantulaRank(int tarantulaRank) {
		this.tarantulaRank = tarantulaRank;
	}

	public int getJaccardRank() {
		return jaccardRank;
	}

	public void setJaccardRank(int jaccardRank) {
		this.jaccardRank = jaccardRank;
	}
	
}
