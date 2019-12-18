package org.schemaanalyst.testgeneration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/07/2014.
 */
public class TestSuite implements Serializable {

    private static final long serialVersionUID = -7221865547415541154L;

    public List<TestCase> testCases;
    private int generatedInserts = 0;
	private int reducedInsertsCount = 0;
	
    public TestSuite() {
        testCases = new ArrayList<>();
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }

    public List<TestCase> getTestCases() {
        return new ArrayList<>(testCases);
    }
    

    public void removeTestCase(int index) {
	testCases.remove(index);
    }

    public void removeTestCase(TestCase tc) {
    	testCases.remove(tc);
    }

	public int getGeneratedInserts() {
		return generatedInserts;
	}

	public void addGeneratedInserts(int originalInsertsCount) {
		this.generatedInserts = originalInsertsCount + this.generatedInserts;
	}

	public int getReducedInsertsCount() {
		return reducedInsertsCount;
	}

	public void addReducedInsertsCount(int reducedInsertsCount) {
		this.reducedInsertsCount = reducedInsertsCount + this.reducedInsertsCount;
	}

	public int countNumberOfInserts() {
		int counter = 0;

		for (TestCase tc : testCases) {
			counter = counter + tc.getData().getNumRows() + tc.getState().getNumRows();
		}

		return counter;
	}

    // ========= For Calculating Readability Using the Language Model
    private double readableScore = 0;
    private int numberOfEmptyStrings = 0;
    private List<Integer> lengthOfStrings = new ArrayList<Integer>();
    // Readable Score
    public void addReadableScore(Double score) {
    	this.readableScore += score;
    }
    
    // Getting Readable Score
    public double getReadableScore() {
    	return this.readableScore;
    }
    
    // Add number of empty string
    public void addnumberOfEmptyStrings(int total) {
    	this.numberOfEmptyStrings += total;
    }
    
    // Getting number of empty strings
    public int getnumberOfEmptyStrings() {
    	return this.numberOfEmptyStrings;
    }
    
    // add one length of a string
    public void addlengthOfStrings(int lengthPerString) {
    	this.lengthOfStrings.add(lengthPerString);
    }
    
    // Getting aver Score
    public double getlengthOfStringsAverage() {
    	  double sum = 0;
    	  if(!this.lengthOfStrings.isEmpty()) {
    	    for (Integer mark : lengthOfStrings) {
    	        sum += mark;
    	    }
    	    sum = sum / lengthOfStrings.size();
    	  }
    	  return sum;
    }
}
