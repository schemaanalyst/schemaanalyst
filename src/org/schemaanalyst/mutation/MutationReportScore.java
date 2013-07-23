package org.schemaanalyst.mutation;

public class MutationReportScore {

    /**
     * The name of the score
     */
    private String name;
    /**
     * The numerator of the score
     */
    private int numerator;
    /**
     * The denominator of the score
     */
    private int denominator;
    /**
     * The computed score
     */
    private double score;

    public MutationReportScore(String name, int numerator, int denominator) {
        this.name = name;
        this.numerator = numerator;
        this.denominator = denominator;
        this.score = computeScore();
    }

    public double getValue(String description) {
        double answer;
        switch (description) {
            case "numerator":
                answer = (new Double(numerator)).doubleValue();
                break;
            case "denominator":
                answer = (new Double(denominator)).doubleValue();
                break;
            case "score":
                computeScore();
                answer = score;
                break;
            default:
                throw new RuntimeException("Unrecognized value: " + description);
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
        computeScore();
        return score;
    }

    private double computeScore() {
        return ((double) numerator / (double) denominator);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "(" + name + ", (" + numerator + " / " + denominator + " = " + score + "))";
    }
}