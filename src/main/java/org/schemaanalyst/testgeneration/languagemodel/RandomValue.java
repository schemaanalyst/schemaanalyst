package org.schemaanalyst.testgeneration.languagemodel;

public class RandomValue {
	public String value;
	public double rank;
	
	
	public RandomValue(String value, double rank) {
		this.value = value;
		this.rank = rank;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}

	public String toString() {
		return "Value = " + this.getValue() + "\t Rank = " + this.getRank();
	}
	
}
