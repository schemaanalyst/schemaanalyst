package org.schemaanalyst.test.mutation.supplier;

import java.util.List;

import org.junit.Test;
import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.mutation.supplier.IteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import static org.junit.Assert.*;

public class TestIteratingSupplier {

	class MockTableIteratingSupplier extends IteratingSupplier<Schema, Table> {

	    public MockTableIteratingSupplier() {
	        super(new Schema.Duplicator());
	    }
	    
        @Override
        public void putComponentBackInDuplicate(Table component) {
        }

        @Override
        protected List<Table> getComponents(Schema schema) {
            return schema.getTables();
        }
	}

	@Test 
	public void testNoTables() {
		Schema schema = new Schema("schema");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier();	
		supplier.initialise(schema);
		
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());
	}
	
	@Test(expected=MutationException.class) 
	public void testNoTablesMakeDuplicateException() {
		Schema schema = new Schema("schema");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier();	
		supplier.initialise(schema);		
		
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());
	
		supplier.makeDuplicate();
	}	
	
	@Test(expected=MutationException.class) 
	public void testNoTablesGetDuplicateComponentException() {
		Schema schema = new Schema("schema");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier();	
		supplier.initialise(schema);			
		
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());
	
		supplier.getDuplicateComponent();
	}	
	
	@Test 
	public void testOneTable() {
		Schema schema = new Schema("schema");
		schema.createTable("table");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier();	
		supplier.initialise(schema);
		
		assertTrue(supplier.hasNext());
		assertNotNull(supplier.getNextComponent());		
		assertTrue(supplier.haveCurrent());
		
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());		
	}
	
	@Test(expected=MutationException.class) 
	public void testTableSupplyExhaustedMakeDuplicateException() {
		Schema schema = new Schema("schema");
		schema.createTable("table");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier();
		supplier.initialise(schema);
		
		assertTrue(supplier.hasNext());
		assertNotNull(supplier.getNextComponent());		
		assertTrue(supplier.haveCurrent());
	
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());		
		
		supplier.makeDuplicate();
	}	
	
	@Test(expected=MutationException.class) 
	public void testTableSupplyExhaustedGetDuplicateComponentException() {
		Schema schema = new Schema("schema");
		schema.createTable("table");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier();	
		supplier.initialise(schema);			

		assertTrue(supplier.hasNext());
		assertNotNull(supplier.getNextComponent());		
		assertTrue(supplier.haveCurrent());
	
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());	
	
		supplier.getDuplicateComponent();
	}		
	
	@Test 
	public void testTwoTables() {
		Schema schema = new Schema("schema");
		schema.createTable("table1");
		schema.createTable("table2");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier();	
		supplier.initialise(schema);			
		
		assertTrue(supplier.hasNext());
		assertNotNull(supplier.getNextComponent());		
		assertTrue(supplier.haveCurrent());
		
		assertTrue(supplier.hasNext());
		assertNotNull(supplier.getNextComponent());		
		assertTrue(supplier.haveCurrent());		
		
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());		
	}		
	
	@Test 
	public void testCanMakeSeveralDuplicates() {
		Schema schema = new Schema("schema");
		schema.createTable("table");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier();	
		supplier.initialise(schema);	
		
		assertTrue(supplier.hasNext());
		assertNotNull(supplier.getNextComponent());		
		assertTrue(supplier.haveCurrent());
				
		Schema duplicate1 = supplier.makeDuplicate();
		Schema duplicate2 = supplier.makeDuplicate();		
		
		assertNotNull(duplicate1);
		assertNotNull(duplicate2);
		
		assertNotSame(schema, duplicate1);
		assertNotSame(schema, duplicate2);
		assertNotSame(duplicate1, duplicate2);
		
		assertEquals(schema, duplicate1);
		assertEquals(schema, duplicate2);
		assertEquals(duplicate1, duplicate2);
	}	
}
