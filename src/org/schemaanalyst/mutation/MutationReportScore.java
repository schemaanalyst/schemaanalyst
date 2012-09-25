package org.schemaanalyst.mutation;

public class MutationReportScore {

    /** The name of the score */
    private String name;

    /** The numerator of the score */
    private int numerator;

    /** The denominator of the score */
    private int denominator;

    /** The computed score */
    private double score;

    public MutationReportScore() {
	name = "";
	numerator = 0;
	denominator = 0;
	score = 0;
    }

    public MutationReportScore(String name, int numerator, int denominator) {
	this.name = name;
	this.numerator = numerator;
	this.denominator = denominator;
	this.score = computeScore();
    }

    public double getValue(String description) {
	double answer = -1.0;
	if(description.equals("numerator")) {
	    answer = (new Double(numerator)).doubleValue();
	}
	else if(description.equals("denominator")) {
	    answer = (new Double(denominator)).doubleValue();
	}
	else if(description.equals("score")) {
	    answer = score;
	}
	return answer;
    }

    public void setNumerator(int numerator) {
	this.numerator = numerator;
    }

    public int getNumerator() {
	return numerator;
    }

    public void setDenominator(int denominator) {
	this.denominator = denominator;
    }

    public int getDenominator() {
	return denominator;
    }

    public double getScore() {
	return score;
    }

    public double computeScore() {
	double score = ((double)numerator / (double)denominator);
	return score;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public String toString() {
	return "(" + name + ", (" + numerator + " / " + denominator + " = " + score + "))"; 
    }
}