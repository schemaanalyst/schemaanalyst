package org.schemaanalyst.datageneration;

import java.util.ArrayList;
import java.util.List;

public class TestSuite<E> {
	
	private String description;
	private List<TestCase<E>> testCases;
	private List<TestCase<E>> usefulTestCases;
	
	public TestSuite(String description) {
		this.description = description;
		testCases = new ArrayList<>();
	}
	
	public void addTestCase(TestCase<E> testCase) {
		testCases.add(testCase);
		if (testCase.getNumCoveredElements() > 0) {
			usefulTestCases.add(testCase);
		}
	}
	
	public String getDescription() {
		return description;
	}
}
