package org.schemaanalyst.unittest.mutation.supplier;

import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.mutation.supplier.LinkedSupplier;
import org.schemaanalyst.mutation.supplier.SolitaryComponentSupplier;
import org.schemaanalyst.util.Duplicator;

import static org.junit.Assert.*;

public class TestLinkedSupplier {

	private static class MockObject {
		String label;
		boolean mutated;
		MockObject innerObject;

		MockObject(String label) {
			this.label = label;
			mutated = false;
			innerObject = null;
		}
		
		MockObject duplicate() {
			MockObject duplicate = new MockObject(label);
			duplicate.mutated = mutated;
			if (innerObject != null) {
				duplicate.innerObject = innerObject.duplicate();
			}
			return duplicate;
		}	

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MockObject other = (MockObject) obj;
			if (innerObject == null) {
				if (other.innerObject != null)
					return false;
			} else if (!innerObject.equals(other.innerObject))
				return false;
			if (label == null) {
				if (other.label != null)
					return false;
			} else if (!label.equals(other.label))
				return false;
			return mutated == other.mutated;
		}

		public String toString() {
			return label;
		}
	}

	private static class MockObjectDuplicator implements Duplicator<MockObject> {
		@Override
		public MockObject duplicate(MockObject object) {
			return object.duplicate();
		}
	}

	private static class MockObjectSupplier extends
			SolitaryComponentSupplier<MockObject, MockObject> {

		public MockObjectSupplier() {
			super(new MockObjectDuplicator());
		}
		
		@Override
		public void putComponentBackInDuplicate(MockObject component) {
			currentDuplicate.mutated = true;
			currentDuplicate.innerObject = component;
		}

		@Override
		protected MockObject getComponent(MockObject artefact) {
			return artefact.innerObject;
		}

	}
	
	MockObjectSupplier topLevelSupplier;
	MockObjectSupplier bottomLevelSupplier;
	LinkedSupplier<MockObject, MockObject, MockObject> supplier;
	MockObject original;	
	
	@Before 
	public void setup() {
		topLevelSupplier = new MockObjectSupplier();
		bottomLevelSupplier = new MockObjectSupplier();

		supplier = new LinkedSupplier<>(
				topLevelSupplier, bottomLevelSupplier);

		original = new MockObject("artefact");
		original.innerObject = new MockObject("top level component");
		original.innerObject.innerObject = new MockObject("bottom level component");
	}

	@Test
	public void testNormalUsage() {

		supplier.initialise(original);
		
		assertTrue(
				"hasNext() should return true on the first call",
				supplier.hasNext());
		
		assertSame(
				"getOriginalArtefact should return the artefact",
				original, supplier.getOriginalArtefact());
		
		MockObject bottomLevelComponent = supplier.getNextComponent();
		assertSame(
				"getNextComponent() should return the bottom level component",
				original.innerObject.innerObject, bottomLevelComponent); 
	
		assertTrue(
				"hasCurrent() should return true as there is a component to mutate",
				supplier.hasCurrent());
		
		MockObject duplicate = supplier.makeDuplicate();
		assertNotSame(
				"The duplicate should not be the same as the original",
				original, duplicate);
		assertEquals(
				"The duplicate should be identical (equals) to the original",
				original, duplicate);	
		
		MockObject duplicateComponent = supplier.getDuplicateComponent();
		assertNotSame(
				"The duplicate component should not be the same as the bottom level component",
				original.innerObject.innerObject, duplicateComponent); 
		assertEquals(
				"The duplicate should be identical (equals) to the bottom level component",
				original.innerObject.innerObject, duplicateComponent); 
		
		supplier.putComponentBackInDuplicate(duplicateComponent);

		assertTrue(
				"The duplicate component's mutated flag should be true as a result of putComponentBackInDuplicate()",
				duplicate.innerObject.mutated);		
		
		assertTrue(
				"The duplicate's mutated flag should be true as a result of putComponentBackInDuplicate()",
				duplicate.mutated);
		
		assertFalse(
				"hasNext() should return false on the second call as there is only one component",
				supplier.hasNext());

		assertNull(
				"getNextComponent() should return null on the second call as there is only one component",
				supplier.getNextComponent());
		
		assertFalse(
				"hasCurrent() should return false after the second call to getNextComponent() as there is only one component",
				supplier.hasCurrent());	
	}
	
	@Test(expected=MutationException.class)
	public void testNotInitialisedHasNext() {
		supplier.hasNext();
	}
	
	@Test(expected=MutationException.class)
	public void testNotInitialisedHaveCurrent() {
		supplier.hasCurrent();
	}
	
	@Test(expected=MutationException.class)
	public void testNotInitialisedGetOriginalArtefact() {
		supplier.getOriginalArtefact();
	}	

	@Test(expected=MutationException.class)
	public void testNotInitialisedMakeDuplicate() {
		supplier.makeDuplicate();
	}	

	@Test(expected=MutationException.class)
	public void testNotInitialisedSetDuplicate() {
		supplier.setDuplicate(original);
	}	
	
	@Test(expected=MutationException.class)
	public void testDuplicateComponentButNoDuplicateCreated() {
		supplier.initialise(original);
		supplier.getNextComponent();
		supplier.getDuplicateComponent();	
	}	
}
