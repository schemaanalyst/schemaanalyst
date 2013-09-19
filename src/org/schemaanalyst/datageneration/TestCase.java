package org.schemaanalyst.datageneration;

import java.util.List;

import org.schemaanalyst.data.Data;

public class TestCase<E> {
	
	private String description;
	private Data data;
	private List<E> coveredElements;
	private long timeToGenerate, startTime, endTime;
	
	public TestCase(String description) {
		this.description = description;
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

	public void addCoveredElement(E element) {
		coveredElements.add(element);
	}
	
	public List<E> getCoveredElements() {
		return coveredElements;
	}
	
	public int getNumCoveredElements() {
		return coveredElements.size();
	}
	
	public String getDescription() {
		return description;
	}	
}
