package org.schemaanalyst.unittest.mutation.supplier;

import org.junit.Test;
import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.mutation.supplier.schema.TableSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import static org.junit.Assert.*;

/**
 * The intention of this class is to test
 * {@link org.schemaanalyst.mutation.supplier.IteratingSupplier}, but it uses
 * {@link org.schemaanalyst.mutation.supplier.schema.TableSupplier} (a subclass)
 * to do so.
 * 
 * @author Phil McMinn
 * 
 */
public class TestIteratingSupplier {

	@Test(expected = MutationException.class)
	public void testNotInitialisedException() {
		TableSupplier supplier = new TableSupplier();
		// If the supplier has not been initialised, hasNext() should throw an
		// exception
		supplier.hasNext();
	}

	@Test
	public void testNoTables() {
		Schema schema = new Schema("schema");
		TableSupplier supplier = new TableSupplier();
		supplier.initialise(schema);

		assertFalse(
				"hasNext() should return false -- there no tables to iterate through",
				supplier.hasNext());
		assertNull(
				"getNextComponent() should return null -- there are no tables to iterate through",
				supplier.getNextComponent());
		assertFalse(
				"There should be no current component as there are no tables in the schema",
				supplier.hasCurrent());
	}

	@Test(expected = MutationException.class)
	public void testNoTablesGetDuplicateComponentException() {
		Schema schema = new Schema("schema");
		TableSupplier supplier = new TableSupplier();
		supplier.initialise(schema);
		supplier.getNextComponent();

		// the schema has no tables, so the following should throw an exception:
		supplier.getDuplicateComponent();
	}

	@Test
	public void testOneTable() {
		Schema schema = new Schema("schema");
		Table table = schema.createTable("table");
		TableSupplier supplier = new TableSupplier();
		supplier.initialise(schema);

		assertTrue("There is one table, so hasNext() should return true",
				supplier.hasNext());
		assertSame(
				"The first time getNextComponent() is called, the schema's table should be returned",
				table, supplier.getNextComponent());
		assertTrue(
				"getNextComponent() returned a table, so haveCurrent() should return true",
				supplier.hasCurrent());

		assertFalse(
				"There is only one table in the schema, so after it has been returned, hasNext() should then return false",
				supplier.hasNext());
		assertNull(
				"The result of getNextComponent() after the first table has been returned should be null",
				supplier.getNextComponent());
		assertFalse(
				"The result of haveCurrent() following the second call to getNextComponent() should be false",
				supplier.hasCurrent());
	}

	@Test(expected = MutationException.class)
	public void testTableSupplyExhaustedMakeDuplicateException() {
		Schema schema = new Schema("schema");
		schema.createTable("table");
		TableSupplier supplier = new TableSupplier();
		supplier.initialise(schema);
		supplier.getNextComponent();

		assertNull(
				"The result of getNextComponent() after the first table has been returned should be null",
				supplier.getNextComponent());

		// There are no more tables -- the last call to getNextComponent()
		// returned null,
		// so makeDuplicate() should throw an exception
		supplier.makeDuplicate();
	}

	@Test(expected = MutationException.class)
	public void testTableSupplyExhaustedGetDuplicateComponentException() {
		Schema schema = new Schema("schema");
		schema.createTable("table");
		TableSupplier supplier = new TableSupplier();
		supplier.initialise(schema);
		supplier.getNextComponent();

		assertNull(
				"The result of getNextComponent() after the first table has been returned should be null",
				supplier.getNextComponent());

		// There are no more tables -- the last call to getNextComponent()
		// returned null, so getDuplicateComponent() should throw an exception
		supplier.getDuplicateComponent();
	}

	@Test
	public void testTwoTables() {
		Schema schema = new Schema("schema");
		Table table1 = schema.createTable("table1");
		Table table2 = schema.createTable("table2");
		TableSupplier supplier = new TableSupplier();
		supplier.initialise(schema);

		assertTrue(
				"There are two tables, so hasNext() should return true before any tables have been obtained",
				supplier.hasNext());
		assertSame(
				"The first time getNextComponent() is called, the schema's table should be returned",
				table1, supplier.getNextComponent());
		assertTrue(
				"getNextComponent() returned a table, so haveCurrent() should return true",
				supplier.hasCurrent());

		assertTrue(
				"There are two tables, so hasNext() should return true after the first table has been obtained",
				supplier.hasNext());
		assertSame(
				"The second time getNextComponent() is called, the schema's second table should be returned",
				table2, supplier.getNextComponent());
		assertTrue(
				"getNextComponent() returned a table, so haveCurrent() should return true",
				supplier.hasCurrent());

		assertFalse(
				"There are only two tables in the schema, so after they have been returned, hasNext() should return false",
				supplier.hasNext());
		assertNull(
				"The result of getNextComponent() after the second table has been returned should be null",
				supplier.getNextComponent());
		assertFalse(
				"The result of haveCurrent() following the third call to getNextComponent() should be false",
				supplier.hasCurrent());
	}

	@Test
	public void testCanMakeSeveralDuplicates() {
		Schema schema = new Schema("schema");
		schema.createTable("table");
		TableSupplier supplier = new TableSupplier();
		supplier.initialise(schema);
		supplier.getNextComponent();

		Schema duplicate1 = supplier.makeDuplicate();
		Schema duplicate2 = supplier.makeDuplicate();

		assertNotNull(
				"The first duplicate should not be null, as multiple duplicates can be made",
				duplicate1);
		assertNotNull(
				"The second duplicate should not be null, as multiple duplicates can be made",
				duplicate2);

		assertNotSame(
				"The first duplicate should not be the same object as the original schema",
				schema, duplicate1);
		assertNotSame(
				"The second duplicate should not be the same object as the original schema",
				schema, duplicate2);
		assertNotSame("The duplicate objects should not be the same object",
				duplicate1, duplicate2);

		assertEquals(
				"The first duplicate should be identical (equal) to the original schema",
				schema, duplicate1);
		assertEquals(
				"The second duplicate should be identical (equal) to the original schema",
				schema, duplicate2);
		assertEquals(
				"The two duplicate should be identical (equal) to eachother",
				duplicate1, duplicate2);
	}

	@Test
	public void testGetDuplicateComponent() {
		TableSupplier supplier = new TableSupplier();
		Schema schema = new Schema("schema");
		Table table = schema.createTable("table");
		supplier.initialise(schema);
		supplier.getNextComponent();
		Schema duplicatedSchema = supplier.makeDuplicate();
		assertSame(
				"Duplicate component should return the table of the duplicated schema",
				duplicatedSchema.getTables().get(0),
				supplier.getDuplicateComponent());
		assertEquals(
				"Duplicate component should return an identical table to that of the original schema",
				table, supplier.getDuplicateComponent());
	}
	
	@Test(expected=MutationException.class)
	public void testGetDuplicateComponentNoDuplicateMadeException() {
		TableSupplier supplier = new TableSupplier();
		Schema schema = new Schema("schema");
		schema.createTable("table");
		supplier.initialise(schema);
		supplier.getNextComponent();
		supplier.getDuplicateComponent();
	}

	@Test
	public void testSetDuplicateWhenNoDuplicator() {
		class NonDuplicatingTableSupplier extends TableSupplier {
			public NonDuplicatingTableSupplier() {
				super();
				duplicator = null;
			}
		}

		NonDuplicatingTableSupplier supplier = new NonDuplicatingTableSupplier();
		Schema schema = new Schema("schema");
		Table table = schema.createTable("table");
		supplier.initialise(schema);
		supplier.getNextComponent();

		Schema duplicatedSchema = schema.duplicate();
		supplier.setDuplicate(duplicatedSchema);
		assertSame(
				"Duplicate component should return the table of the duplicated schema",
				duplicatedSchema.getTables().get(0),
				supplier.getDuplicateComponent());
		assertEquals(
				"Duplicate component should return an identical table to that of the original schema",
				table, supplier.getDuplicateComponent());
	}
}
