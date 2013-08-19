package org.schemaanalyst.test.mutation.artefactsupplier;

import org.junit.Test;
import org.schemaanalyst.mutation.artefactsupplier.TableIteratingSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static org.junit.Assert.*;

public class TestIteratingSupplier {

	class MockTableIteratingSupplier extends TableIteratingSupplier<Column> {

		public MockTableIteratingSupplier(Schema schema) {
			super(schema);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Column getComponentFromIntermediary(Schema schema, Table table) {
			return new Column("dummy", new IntDataType());
		}

		@Override
		public void putComponentBackInDuplicate(Column component) {
			// TODO Auto-generated method stub			
		}
	}

	@Test 
	public void testNoTables() {
		Schema schema = new Schema("schema");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier(schema);			
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());
	}
	
	@Ignore
	@Test 
	public void testNoTablesException1() {
		Schema schema = new Schema("schema");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier(schema);			
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());
		
	}	
	
	@Test 
	public void testOneTable() {
		Schema schema = new Schema("schema");
		schema.createTable("table");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier(schema);			
		
		assertTrue(supplier.hasNext());
		assertNotNull(supplier.getNextComponent());		
		assertTrue(supplier.haveCurrent());
		
		assertFalse(supplier.hasNext());
		assertNull(supplier.getNextComponent());
		assertFalse(supplier.haveCurrent());		
	}
	
	@Test 
	public void testTwoTables() {
		Schema schema = new Schema("schema");
		schema.createTable("table1");
		schema.createTable("table2");
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier(schema);			
		
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
		MockTableIteratingSupplier supplier = new MockTableIteratingSupplier(schema);			
		
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
