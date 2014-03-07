package org.schemaanalyst.datageneration;

import org.schemaanalyst.data.Data;

import java.util.ArrayList;
import java.util.List;

public class TestCase<G> {
	
	private String description;
	private Data data;
	private List<G> coveredElements;
	private long timeToGenerate, startTime, endTime;
	private int numEvaluations, numRestarts; 
	
	public TestCase(String description) {
		this.description = description;
		coveredElements = new ArrayList<>();
		startTime = 0;
        endTime = 0;
        timeToGenerate = 0;
	}
	
	public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public void endTimer() {
        endTime = System.currentTimeMillis();
        timeToGenerate = endTime - startTime;
    }	
    
    public long getTimeToGenerate() {
    	return timeToGenerate;
    }

	public void setData(Data data) {
		this.data = data;
	}
    
	public Data getData() {
		return data;
	}

	public void addCoveredElement(G element) {
		coveredElements.add(element);
	}
	
	public List<G> getCoveredElements() {
		return coveredElements;
	}
	
	public int getNumCoveredElements() {
		return coveredElements.size();
	}
	
	public String getDescription() {
		return description;
	}

	public int getNumEvaluations() {
		return numEvaluations;
	}

	public void setNumEvaluations(int numEvaluations) {
		this.numEvaluations = numEvaluations;
	}

	public int getNumRestarts() {
		return numRestarts;
	}

	public void setNumRestarts(int numRestarts) {
		this.numRestarts = numRestarts;
	}
}
