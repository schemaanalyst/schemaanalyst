package org.schemaanalyst.unittest.sqlrepresentation;

import org.junit.Test;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.util.java.JavaUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestSchema {

    @Test
    public void testCreateTable() {
        Schema s = new Schema("schema");
        Table t = s.createTable("table");
        assertSame("The table created should be the same as that returned", t,
                s.getTable("table"));
    }

    @Test
    public void testAddTable() {
        Schema s = new Schema("schema");
        Table t = new Table("table");
        s.addTable(t);
        assertSame("The table added should be the same as that returned", t,
                s.getTable("table"));
    }

    @Test
    public void testAddMultipleTables() {
        Schema s = new Schema("schema");
        Table t1 = new Table("table1");
        Table t2 = new Table("table2");
        s.addTable(t1);
        s.addTable(t2);

        assertSame("The first table should be added and correctly returned",
                t1, s.getTable("table1"));

        assertSame("The second table should be added and correctly returned",
                t2, s.getTable("table2"));

        assertEquals("There should be 2 tables in the schema", 2, s.getTables()
                .size());

        assertSame(
                "The first table returned by getTables() should be the first table added",
                t1, s.getTables().get(0));

        assertSame(
                "The second table returned by getTables() should be the second table added",
                t2, s.getTables().get(1));
    }

    @Test(expected = SQLRepresentationException.class)
    public void testTableNameClash() {
        Schema s = new Schema("schema");
        Table t1 = new Table("table");
        Table t2 = new Table("table");
        s.addTable(t1);
        s.addTable(t2);
    }

    @Test
    public void testPrimaryKeyManagement() {
        Schema s = new Schema("schema");
        Table t = s.createTable("table");
        Column c = t.createColumn("column", new IntDataType());

        assertFalse("Table should not have a primary key after initialisation",
                s.hasPrimaryKeyConstraint(t));

        PrimaryKeyConstraint pk = s.createPrimaryKeyConstraint(t, c);

        assertNotNull("Created primary key should not be null", pk);

        assertTrue("Table should have a primary key once one is set",
                s.hasPrimaryKeyConstraint(t));

        assertSame("Table's primary key should be the same as that passed",
                s.getPrimaryKeyConstraint(t), pk);
    }

    @Test(expected = SQLRepresentationException.class)
    public void testPrimaryKeyManagementNoSuchTable1() {
        Schema s = new Schema("schema");
        Table t = new Table("table");
        Column c = t.createColumn("column", new IntDataType());
        s.createPrimaryKeyConstraint(t, c);
    }

    @Test(expected = SQLRepresentationException.class)
    public void testPrimaryKeyManagementNoSuchTable2() {
        Schema s = new Schema("schema");
        Table t = new Table("table");
        s.hasPrimaryKeyConstraint(t);
    }

    @Test(expected = SQLRepresentationException.class)
    public void testPrimaryKeyManagementNoSuchTable3() {
        Schema s = new Schema("schema");
        Table t = new Table("table");
        s.getPrimaryKeyConstraint(t);
    }

    @Test
    public void testCheckConstraintManagement() {
        Schema s = new Schema("schema");
        Table t1 = s.createTable("table1");
        Table t2 = s.createTable("table2");
        Expression e = new ConstantExpression(new NumericValue(1));

        assertEquals(
                "Table should not have a check constraint after initialisation",
                s.getCheckConstraints(t1).size(), 0);

        assertEquals(
                "Table should not have a check constraint after initialisation",
                s.getCheckConstraints(t2).size(), 0);

        CheckConstraint cc1 = s.createCheckConstraint(t1, e);
        CheckConstraint cc2 = s.createCheckConstraint(t2, e);

        assertNotNull("Created CHECK constraint should not be null", cc1);

        assertEquals("Table should have a check constraint", s
                .getCheckConstraints(t1).size(), 1);

        assertSame(
                "Table's CHECK constraint should be the same as that passed", s
                        .getCheckConstraints(t1).get(0), cc1);

        assertNotNull("Created CHECK constraint should not be null", cc2);

        assertEquals("Table should have a check constraint", s
                .getCheckConstraints(t2).size(), 1);

        assertSame(
                "Table's CHECK constraint should be the same as that passed", s
                        .getCheckConstraints(t2).get(0), cc2);
    }

    @Test
    public void testForeignKeyConstraintManagement() {
        Schema s = new Schema("schema");
        Table t1 = s.createTable("table1");
        Table t2 = s.createTable("table2");
        Column c1 = t1.createColumn("column1", new IntDataType());
        Column c2 = t2.createColumn("column2", new IntDataType());

        assertEquals(
                "Table should not have a check constraint after initialisation",
                s.getForeignKeyConstraints(t1).size(), 0);

        assertEquals(
                "Table should not have a check constraint after initialisation",
                s.getForeignKeyConstraints(t2).size(), 0);

        ForeignKeyConstraint fk1 = s.createForeignKeyConstraint(t1, c1, t1, c1);
        ForeignKeyConstraint fk2 = s.createForeignKeyConstraint(t2, c2, t2, c2);

        assertNotNull("Created FOREIGN KEY constraint should not be null", fk1);

        assertEquals("Table should have a FOREIGN KEY constraint", s
                .getForeignKeyConstraints(t1).size(), 1);

        assertSame("Table's FOREIGN KEY should be the same as that passed", s
                .getForeignKeyConstraints(t1).get(0), fk1);

        assertNotNull("Created FOREIGN KEY constraint should not be null", fk2);

        assertEquals("Table should have a FOREIGN KEY constraint", s
                .getForeignKeyConstraints(t2).size(), 1);

        assertSame(
                "Table's FOREIGN KEY constraint should be the same as that passed",
                s.getForeignKeyConstraints(t2).get(0), fk2);
    }

    @Test
    public void testNotNullConstraintManagement() {
        Schema s = new Schema("schema");
        Table t1 = s.createTable("table1");
        Table t2 = s.createTable("table2");
        Column c1 = t1.createColumn("column1", new IntDataType());
        Column c2 = t2.createColumn("column2", new IntDataType());

        assertEquals(
                "Table should not have a check constraint after initialisation",
                s.getNotNullConstraints(t1).size(), 0);

        assertEquals(
                "Table should not have a check constraint after initialisation",
                s.getNotNullConstraints(t2).size(), 0);

        NotNullConstraint nn1 = s.createNotNullConstraint(t1, c1);
        NotNullConstraint nn2 = s.createNotNullConstraint(t2, c2);

        assertNotNull("Created NOT NULL constraint should not be null", nn1);

        assertEquals("Table should have a NOT NULL  constraint", s
                .getNotNullConstraints(t1).size(), 1);

        assertSame("Table's NOT NULL should be the same as that passed", s
                .getNotNullConstraints(t1).get(0), nn1);

        assertNotNull("Created NOT NULL constraint should not be null", nn2);

        assertEquals("Table should have a NOT NULL constraint", s
                .getNotNullConstraints(t2).size(), 1);

        assertSame(
                "Table's NOT NULL constraint should be the same as that passed",
                s.getNotNullConstraints(t2).get(0), nn2);
    }

    @Test
    public void testUniqueConstraintManagement() {
        Schema s = new Schema("schema");
        Table t1 = s.createTable("table1");
        Table t2 = s.createTable("table2");
        Column c1 = t1.createColumn("column1", new IntDataType());
        Column c2 = t2.createColumn("column2", new IntDataType());

        assertEquals(
                "Table should not have a check constraint after initialisation",
                s.getUniqueConstraints(t1).size(), 0);

        assertEquals(
                "Table should not have a check constraint after initialisation",
                s.getUniqueConstraints(t2).size(), 0);

        UniqueConstraint uc1 = s.createUniqueConstraint(t1, c1);
        UniqueConstraint uc2 = s.createUniqueConstraint(t2, c2);

        assertNotNull("Created UNIQUE constraint should not be null", uc1);

        assertEquals("Table should have a UNIQUE  constraint", s
                .getUniqueConstraints(t1).size(), 1);

        assertSame("Table's UNIQUE  should be the same as that passed", s
                .getUniqueConstraints(t1).get(0), uc1);

        assertNotNull("Created UNIQUE  constraint should not be null", uc2);

        assertEquals("Table should have a UNIQUE  constraint", s
                .getUniqueConstraints(t2).size(), 1);

        assertSame(
                "Table's UNIQUE  constraint should be the same as that passed",
                s.getUniqueConstraints(t2).get(0), uc2);
    }

    @Test
    public void testDuplication() {
        Schema s1 = new Schema("schema");
        Table t1 = s1.createTable("table1");
        Table t2 = s1.createTable("table2");
        Table t3 = s1.createTable("table3");
        Column c1 = t1.createColumn("column1", new IntDataType());
        Column c2 = t2.createColumn("column2", new IntDataType());
        Column c3 = t3.createColumn("column3", new IntDataType());

        s1.createPrimaryKeyConstraint(t1, c1);
        s1.createPrimaryKeyConstraint(t2, c2);

        s1.createCheckConstraint(t1, new ConstantExpression(new NumericValue(1)));
        s1.createCheckConstraint(t2, new ConstantExpression(new NumericValue(1)));

        s1.createForeignKeyConstraint(t1, c1, t3, c3);
        s1.createForeignKeyConstraint(t2, c2, t3, c3);

        s1.createNotNullConstraint(t1, c1);
        s1.createNotNullConstraint(t2, c2);
        
        s1.createUniqueConstraint(t1, c1);
        s1.createUniqueConstraint(t2, c2);

        Schema s2 = s1.duplicate();

        assertNotSame(
                "The duplicated schema should not be the same object as the original",
                s1, s2);

        assertEquals("The duplicated schema should be equal to the original",
                s1, s2);

        assertEquals(
                "The duplicated schema should have the same hashcode as the original",
                s1.hashCode(), s2.hashCode());
        
        // test PRIMARY KEY constraint remapping
        assertEquals(
        		"First PRIMARY KEY constraint table should be equal to duplicate",
        		s2.getPrimaryKeyConstraints().get(0).getTable(), t1);
        assertNotSame(
        		"First PRIMARY KEY constraint table should not be the same as duplicate",
        		s2.getPrimaryKeyConstraints().get(0).getTable(), t1);
        assertEquals(
        		"Second PRIMARY KEY constraint table should be equal to duplicate",
        		s2.getPrimaryKeyConstraints().get(1).getTable(), t2);
        assertNotSame(
        		"Second PRIMARY KEY constraint table should not be the same as duplicate",
        		s2.getPrimaryKeyConstraints().get(1).getTable(), t2);    
        
        // test CHECK constraint remapping
        assertEquals(
        		"First CHECK constraint table should be equal to duplicate",
        		s2.getCheckConstraints().get(0).getTable(), t1);
        assertNotSame(
        		"First CHECK constraint table should not be the same as duplicate",
        		s2.getCheckConstraints().get(0).getTable(), t1);
        assertEquals(
        		"Second CHECK constraint table should be equal to duplicate",
        		s2.getCheckConstraints().get(1).getTable(), t2);
        assertNotSame(
        		"Second CHECK constraint table should not be the same as duplicate",
        		s2.getCheckConstraints().get(1).getTable(), t2);        	

        // test FOREIGN KEY constraint remapping
        assertEquals(
        		"First FOREIGN KEY constraint table should be equal to duplicate",
        		s2.getForeignKeyConstraints().get(0).getTable(), t1);
        assertNotSame(
        		"First FOREIGN KEY constraint table should not be the same as duplicate",
        		s2.getForeignKeyConstraints().get(0).getTable(), t1);
        assertEquals(
        		"First FOREIGN KEY constraint reference table should be equal to duplicate",
        		s2.getForeignKeyConstraints().get(0).getReferenceTable(), t3);
        assertNotSame(
        		"First FOREIGN KEY constraint table should not be the same as duplicate",
        		s2.getForeignKeyConstraints().get(0).getReferenceTable(), t3);
        assertEquals(
        		"Second FOREIGN KEY constraint table should be equal to duplicate",
        		s2.getForeignKeyConstraints().get(1).getTable(), t2);
        assertNotSame(
        		"Second FOREIGN KEY constraint table should not be the same as duplicate",
        		s2.getForeignKeyConstraints().get(1).getTable(), t2);            
        assertEquals(
        		"Second FOREIGN KEY constraint reference table should be equal to duplicate",
        		s2.getForeignKeyConstraints().get(1).getReferenceTable(), t3);
        assertNotSame(
        		"Second FOREIGN KEY constraint table should not be the same as duplicate",
        		s2.getForeignKeyConstraints().get(1).getReferenceTable(), t3);
        
        // test NOT NULL constraint remapping
        assertEquals(
        		"First NOT NULL constraint table should be equal to duplicate",
        		s2.getNotNullConstraints().get(0).getTable(), t1);
        assertNotSame(
        		"First NOT NULL constraint table should not be the same as duplicate",
        		s2.getNotNullConstraints().get(0).getTable(), t1);
        assertEquals(
        		"Second NOT NULL constraint table should be equal to duplicate",
        		s2.getNotNullConstraints().get(1).getTable(), t2);
        assertNotSame(
        		"Second NOT NULL constraint table should not be the same as duplicate",
        		s2.getNotNullConstraints().get(1).getTable(), t2);         
     
        // test UNIQUE constraint remapping
        assertEquals(
        		"First UNIQUE constraint table should be equal to duplicate",
        		s2.getUniqueConstraints().get(0).getTable(), t1);
        assertNotSame(
        		"First UNIQUE constraint table should not be the same as duplicate",
        		s2.getUniqueConstraints().get(0).getTable(), t1);
        assertEquals(
        		"Second UNIQUE constraint table should be equal to duplicate",
        		s2.getUniqueConstraints().get(1).getTable(), t2);
        assertNotSame(
        		"Second UNIQUE constraint table should not be the same as duplicate",
        		s2.getUniqueConstraints().get(1).getTable(), t2);           
    }
    
    @Test
    public void testConnectedTablesNoConnections() { 
    	Schema s = new Schema("s");    	
    	Table t1 = s.createTable("t1");
    	assertTrue(
    			"There should be no connected tables for t1",
    			s.getConnectedTables(t1).isEmpty());
    }	
    	
    @Test
    public void testConnectedTables() {
    	Schema s = new Schema("s");
    	
    	Table t1 = s.createTable("t1");
    	Column c1 = t1.createColumn("c1", new IntDataType());
    	
    	Table t2 = s.createTable("t2");
    	Column c2 = t2.createColumn("c2", new IntDataType());
    	
    	Table t3 = s.createTable("t3");
    	Column c3 = t3.createColumn("c3", new IntDataType());

    	Table t4 = s.createTable("t4");
    	t4.createColumn("c4", new IntDataType());    	
    	
    	s.createForeignKeyConstraint(t1, c1, t2, c2);
    	s.createForeignKeyConstraint(t2, c2, t3, c3);
    	
    	List<Table> tables = s.getConnectedTables(t1);
    	
    	assertFalse(
    			"The list of connected tables should not include t1",
    			tables.contains(t1));
    	
    	assertTrue(
    			"The list of connected tables should include t2",
    			tables.contains(t2));
    	
    	assertTrue(
    			"The list of connected tables should include t3",
    			tables.contains(t3));    	

    	assertFalse(
    			"The list of connected tables should not include t4",
    			tables.contains(t4));    
    }

    @Test
    public void testParsedCaseStudies() throws InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        LocationsConfiguration locationsConfiguration = new LocationsConfiguration();
        File caseStudySrcDir = new File(
                locationsConfiguration.getCaseStudySrcDir());
        String caseStudyPackage = locationsConfiguration.getCaseStudyPackage();

        List<Schema> caseStudies = new ArrayList<>();
        String[] entries = caseStudySrcDir.list();
        for (String entry : entries) {
            if (entry.endsWith(JavaUtils.JAVA_FILE_SUFFIX)) {
                String className = caseStudyPackage
                        + JavaUtils.PACKAGE_SEPARATOR
                        + JavaUtils.fileNameToClassName(entry);
                caseStudies
                        .add((Schema) Class.forName(className).newInstance());
            }
        }

        for (Schema caseStudy : caseStudies) {

            Schema duplicate = caseStudy.duplicate();
            assertTrue(caseStudy.getClass().getSimpleName()
                    + " should equal its duplicate",
                    duplicate.equals(caseStudy));
        }
    }    
}
