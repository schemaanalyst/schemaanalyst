package org.schemaanalyst.unittest.mutation.supplier;

import org.junit.Test;
import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.mutation.supplier.AbstractSupplier;
import org.schemaanalyst.mutation.supplier.schema.TableSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;

import static org.junit.Assert.*;

public class TestAbstractSupplier {

	class MockAbstractSupplier extends AbstractSupplier<Schema, Object> {

		// make a hole so that we can test more features
		public void setHaveCurrentToTrue() {
			haveCurrent = true;
		}
		
		public void setupDuplicator() {
			duplicator = new Schema.Duplicator();
		}
		
		@Override
		public Object getNextComponent() {
			return null;
		}

		@Override
		public Object getDuplicateComponent() {
			return null;
		}

		@Override
		public void putComponentBackInDuplicate(Object component) {
		}
		
	}
	
	@Test
	public void testInitialisation() {
		Schema schema = new Schema("schema");
		MockAbstractSupplier supplier = new MockAbstractSupplier();
		
		assertFalse(
				"isInitialised() should return false after construction",
				supplier.isInitialised());		
		
		
		supplier.initialise(schema);	
		
		assertSame(
				"The original artefact and that returned by getOriginalArtefact should be the same",
				schema, supplier.getOriginalArtefact());

		assertTrue(
				"After a call to initialise(), isInitialised() should return true",
				supplier.isInitialised());	
		
		// calls to these two methods should not throw exceptions now ...
		supplier.hasNext();
		supplier.hasCurrent();
	}
	
	@Test(expected=MutationException.class)
	public void testNotInitialisedHasNext() {
		MockAbstractSupplier supplier = new MockAbstractSupplier();
		supplier.hasNext();
	}
	
	@Test(expected=MutationException.class)
	public void testNotInitialisedHaveCurrent() {
		MockAbstractSupplier supplier = new MockAbstractSupplier();
		supplier.hasCurrent();
	}
	
	@Test(expected=MutationException.class)
	public void testNotInitialisedGetOriginalArtefact() {
		MockAbstractSupplier supplier = new MockAbstractSupplier();
		supplier.getOriginalArtefact();
	}	

	@Test(expected=MutationException.class)
	public void testNotInitialisedMakeDuplicate() {
		MockAbstractSupplier supplier = new MockAbstractSupplier();
		supplier.makeDuplicate();
	}	

	@Test(expected=MutationException.class)
	public void testNotInitialisedSetDuplicate() {
		MockAbstractSupplier supplier = new MockAbstractSupplier();
		supplier.setDuplicate(new Schema("test"));
	}		
	
	@Test(expected=MutationException.class)
	public void testNoTablesMakeDuplicateException() {
		Schema schema = new Schema("schema");
		TableSupplier supplier = new TableSupplier();
		supplier.initialise(schema);	
		supplier.getNextComponent();
		
		// the schema has no tables, so the following should throw an exception:
		supplier.makeDuplicate();
	}
	
	@Test(expected=MutationException.class)
	public void testCannotMakeDuplicatesWhenNoDuplicator() {
		MockAbstractSupplier supplier = new MockAbstractSupplier();
		supplier.initialise(new Schema("schema"));
		supplier.setHaveCurrentToTrue();
		
		// the mock class sets the duplicator to null, so this method
		// call should throw an exception
		supplier.makeDuplicate();
	}
	
	public void testMakeDuplicateWithDuplicator() {
		MockAbstractSupplier supplier = new MockAbstractSupplier();
		Schema schema = new Schema("schema");
		supplier.initialise(schema);
		supplier.setHaveCurrentToTrue();
		
		Schema duplicatedSchema = supplier.makeDuplicate();
		assertEquals(
				"Duplicate component should return a duplicated schema",
				schema, duplicatedSchema);
	}
}
