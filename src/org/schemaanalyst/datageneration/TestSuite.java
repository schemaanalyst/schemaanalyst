package org.schemaanalyst.datageneration;

import java.util.ArrayList;
import java.util.List;

public class TestSuite<G> {
	
	private String description;
	private List<TestCase<G>> testCases;
	private List<TestCase<G>> usefulTestCases;
	
	public TestSuite(String description) {
		this.description = description;
		testCases = new ArrayList<>();
		usefulTestCases = new ArrayList<>();
	}
	
	public void addTestCase(TestCase<G> testCase) {
		testCases.add(testCase);
		if (testCase.getNumCoveredElements() > 0) {
			usefulTestCases.add(testCase);
		}
	}
	
	public List<TestCase<G>> getTestCases() {
		return new ArrayList<>(testCases);
	}
	
	public List<TestCase<G>> getUsefulTestCases() {
		return new ArrayList<>(usefulTestCases);
	}
	
	public String getDescription() {
		return description;
	}
}
