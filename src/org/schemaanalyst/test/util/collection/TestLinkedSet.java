package org.schemaanalyst.test.util.collection;

import org.junit.Test;
import org.schemaanalyst.util.collection.LinkedSet;

import static org.junit.Assert.*;

public class TestLinkedSet {

	@Test
	public void testEmpty() {
		LinkedSet<Integer> set = new LinkedSet<>();
		assertEquals(
			"The set should be empty on initialisation",
			0, set.size());
	}
	
	@Test
	public void testAdd() {
		LinkedSet<Integer> set = new LinkedSet<>();	
		set.add(1);

		assertEquals(
				"The set should have one element",
				1, set.size());
		
		assertTrue(
				"The set should contain the element added",
				set.contains(1));
	}		
	
	@Test
	public void testReAdd() {
		LinkedSet<Integer> set = new LinkedSet<>();	
		set.add(1);
		set.add(1);
		
		assertEquals(
				"The set should have one element",
				1, set.size());
		
		assertTrue(
				"The set should contain the element added",
				set.contains(1));
	}	
	
	@Test
	public void testDistinctMultiAdd() {
		LinkedSet<Integer> set = new LinkedSet<>();	
		set.add(1);
		set.add(2);
		
		assertEquals(
				"The set should have two elements",
				2, set.size());
		
		assertTrue(
				"The set should contain the element 1",
				set.contains(1));
		
		assertTrue(
				"The set should contain the element 2",
				set.contains(2));		
	}	
	
	@Test
	public void testRemove() {
		LinkedSet<Integer> set = new LinkedSet<>();	
		set.add(1);
		set.add(2);
		set.remove(1);
		
		assertEquals(
				"The set should have one element",
				1, set.size());
		
		assertTrue(
				"The set should contain the element 2",
				set.contains(2));		
	}	
	
	@Test
	public void testDuplicate() {
		LinkedSet<Integer> set = new LinkedSet<>();	
		set.add(1);
		set.add(2);
		
		LinkedSet<Integer> duplicate = set.duplicate();
		
		assertNotSame(
				"The two sets should not be the same",
				set, duplicate);
		
		assertEquals(
				"The two sets should be equal",
				set, duplicate);
	}
	
	@Test
	public void testEquals() {
		LinkedSet<Integer> set1 = new LinkedSet<>();	
		set1.add(1024);
		set1.add(256);
		set1.add(512);
		
		LinkedSet<Integer> set2 = new LinkedSet<>();	
		set2.add(512);
		set2.add(1024);
		set2.add(256);
		
		assertEquals(
				"The two sets should be equal",
				set1, set2);	
		
		assertEquals(
				"The two sets should have the same hashcode",
				set1.hashCode(), set2.hashCode());	
	}
	
	@Test
	public void testNotEquals() {
		LinkedSet<Integer> set1 = new LinkedSet<>();	
		set1.add(1024);
		set1.add(256);
		set1.add(512);
		
		LinkedSet<Integer> set2 = new LinkedSet<>();	
		set2.add(512);
		set2.add(1024);
		set2.add(257);
		
		assertNotEquals(
				"The two sets should not be equal",
				set1, set2);
		
		assertNotEquals(
				"The two sets should not have the same hashcode",
				set1.hashCode(), set2.hashCode());			
	}	
}
