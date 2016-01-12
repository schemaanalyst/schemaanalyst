package org.schemaanalyst.unittest.sqlrepresentation;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTableDependencyOrderer {

	TableDependencyOrderer tdo = new TableDependencyOrderer();
	
	@Test
	public void testNoDependencies() {
    	Schema s = new Schema("s");    	
    	s.createTable("t1");
    	List<Table> tables = tdo.order(s.getTables(), s);
    	assertEquals(
    			"There is only one table, so the list of ordered tables should be of length 1",
    			1, tables.size());
	}
	
	@Test
	public void testDependencyChain() {
    	Schema s = new Schema("s");
    	
    	Table t1 = s.createTable("t1");
    	Column c1 = t1.createColumn("c1", new IntDataType());
    	
    	Table t3 = s.createTable("t3");
    	Column c3 = t3.createColumn("c3", new IntDataType());    	
    	
    	Table t2 = s.createTable("t2");
    	Column c2 = t2.createColumn("c2", new IntDataType());
    	    	    	
    	
    	s.createForeignKeyConstraint(t1, c1, t2, c2);
    	s.createForeignKeyConstraint(t2, c2, t3, c3);
    	
    	List<Table> tables = tdo.order(s.getTables(), s);
    	assertEquals(
    			"All tables should be in the list",
    			s.getTables().size(), tables.size());    	
    	
    	assertTrue(
    			"t1 should appear in the list after t2",
    			tables.indexOf(t1) > tables.indexOf(t2));
    	
    	assertTrue(
    			"t2 should appear in the list after t3",
    			tables.indexOf(t2) > tables.indexOf(t3));     	
	}

	@Test
	public void testMultipleDependencyChains() {
    	Schema s = new Schema("s");
    	
    	Table t1 = s.createTable("t1");
    	Column c1 = t1.createColumn("c1", new IntDataType());
    	
    	Table t3 = s.createTable("t3");
    	Column c3 = t3.createColumn("c3", new IntDataType());    	
    	
    	Table t4 = s.createTable("t4");
    	Column c4 = t4.createColumn("c4", new IntDataType());    	
    	
    	Table t2 = s.createTable("t2");
    	Column c2 = t2.createColumn("c2", new IntDataType());    	    	
    	
    	s.createForeignKeyConstraint(t1, c1, t2, c2);
    	s.createForeignKeyConstraint(t2, c2, t3, c3);
    	s.createForeignKeyConstraint(t1, c1, t4, c4);
    	
    	List<Table> tables = tdo.order(s.getTables(), s);
    	assertEquals(
    			"All tables should be in the list",
    			s.getTables().size(), tables.size());    	
    	
    	assertTrue(
    			"t1 should appear in the list after t2",
    			tables.indexOf(t1) > tables.indexOf(t2));
    	
    	assertTrue(
    			"t2 should appear in the list after t3",
    			tables.indexOf(t2) > tables.indexOf(t3));
    	
    	assertTrue(
    			"t1 should appear in the list after t4",
    			tables.indexOf(t1) > tables.indexOf(t4));    	
	}	
	
	@Test(expected=SQLRepresentationException.class)
	public void testCircularDependency() {
    	Schema s = new Schema("s");
    	
    	Table t1 = s.createTable("t1");
    	Column c1 = t1.createColumn("c1", new IntDataType());
    	
    	Table t2 = s.createTable("t2");
    	Column c2 = t2.createColumn("c2", new IntDataType());
    	
    	Table t3 = s.createTable("t3");
    	Column c3 = t3.createColumn("c3", new IntDataType());    	
    	
    	Table t4 = s.createTable("t4");
    	Column c4 = t4.createColumn("c4", new IntDataType()); 
    	
    	s.createForeignKeyConstraint(t1, c1, t2, c2);
    	s.createForeignKeyConstraint(t2, c2, t3, c3);
    	s.createForeignKeyConstraint(t3, c1, t4, c4);
    	s.createForeignKeyConstraint(t4, c4, t2, c2);
    	
    	tdo.order(s.getTables(), s);
	}
}
