package org.schemaanalyst.test.sqlrepresentation;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.util.JavaUtils;

public class TestSchema {
    
    @Test
    public void testCreateTable() {
        Schema s = new Schema("schema");
        Table t = s.createTable("table");
        assertSame(
                "The table created should be the same as that returned",
                t, s.getTable("table"));
    }
    
    @Test
    public void testAddTable() {
        Schema s = new Schema("schema");
        Table t = new Table("table");
        s.addTable(t);
        assertSame(
                "The table added should be the same as that returned",
                t, s.getTable("table"));
    }
    
    @Test
    public void testAddMultipleTables() {
        Schema s = new Schema("schema");
        Table t1 = new Table("table1");
        Table t2 = new Table("table2");
        s.addTable(t1);
        s.addTable(t2);
        
        assertSame(
                "The first table should be added and correctly returned",
                t1, s.getTable("table1"));
        
        assertSame(
                "The second table should be added and correctly returned",
                t2, s.getTable("table2"));
        
        assertEquals(
                "There should be 2 tables in the schema",
                2, s.getTables().size());
        
        assertSame(
                "The first table returned by getTables() should be the first table added",
                t1, s.getTables().get(0));
        
        assertSame(
                "The second table returned by getTables() should be the second table added",
                t2, s.getTables().get(1));
    }    
    
    @Test(expected=SQLRepresentationException.class)
    public void testTableNameClash() {
        Schema s = new Schema("schema");
        Table t1 = new Table("table");
        Table t2 = new Table("table");
        s.addTable(t1);
        s.addTable(t2);
    }
    
    public void testDuplication() {
        Schema s1 = new Schema("schema");
        Table t1 = s1.createTable("table1");
        Column c1 = t1.createColumn("t1column", new IntDataType());
        Table t2 = s1.createTable("table2");
        Column c2 = t1.createColumn("t2column", new IntDataType());
        t1.createForeignKeyConstraint(c1, t2, c2);
        
        Schema s2 = s1.duplicate();
        
        assertNotSame(
                "The duplicate of a schema should not be the same object",
                s1, s2);

        assertEquals(
                "The duplicate of a schema should be equal to the original",
                s1, s2);        
        
        assertEquals(
                "The duplicate of a schema should have the same number of tables",
                2, s2.getTables().size());
        
        assertNotNull(
                "The duplicate of a schema should have the same tables",
                s2.getTable("table1"));
        
        assertNotNull(
                "The duplicate of a schema should have the same tables",
                s2.getTable("table2"));   
        
        // test remapping of FK
        assertNotSame(
                "The FOREIGN KEY of s1.t1 and s2.t1 should not refer to the same object",
                s1.getTable("table1").getForeignKeyConstraints().get(0), 
                s2.getTable("table1").getForeignKeyConstraints().get(0));
        
        assertEquals(
                "The FOREIGN KEY of s1.t1 and s2.t1 should be equal",
                s1.getTable("table1").getForeignKeyConstraints().get(0), 
                s2.getTable("table1").getForeignKeyConstraints().get(0));
        
        assertNotSame(
                "The FOREIGN KEY column of s1.t1 and s2.t1 should not refer to the same object",
                s1.getTable("table1").getForeignKeyConstraints().get(0).getColumns().get(0), 
                s2.getTable("table1").getForeignKeyConstraints().get(0).getColumns().get(0));                

        assertEquals(
                "The FOREIGN KEY column of s1.t1 and s2.t1 should be equal",
                s1.getTable("table1").getForeignKeyConstraints().get(0).getColumns().get(0), 
                s2.getTable("table1").getForeignKeyConstraints().get(0).getColumns().get(0));    

        assertNotSame(
                "The FOREIGN KEY reference table of s1.t1 and s2.t1 should not refer to the same object",
                s1.getTable("table1").getForeignKeyConstraints().get(0).getReferenceTable(), 
                s2.getTable("table1").getForeignKeyConstraints().get(0).getReferenceTable());                

        assertEquals(
                "The FOREIGN KEY reference table of s1.t1 and s2.t1 should not refer to the same object",
                s1.getTable("table1").getForeignKeyConstraints().get(0).getReferenceTable(), 
                s2.getTable("table1").getForeignKeyConstraints().get(0).getReferenceTable());    
        
        
        assertNotSame(
                "The FOREIGN KEY reference column of s1.t1 and s2.t1 should not refer to the same object",
                s1.getTable("table1").getForeignKeyConstraints().get(0).getReferenceColumns().get(0), 
                s2.getTable("table1").getForeignKeyConstraints().get(0).getReferenceColumns().get(0));                

        assertEquals(
                "The FOREIGN KEY reference column of s1.t1 and s2.t1 should be equal",
                s1.getTable("table1").getForeignKeyConstraints().get(0).getReferenceColumns().get(0), 
                s2.getTable("table1").getForeignKeyConstraints().get(0).getReferenceColumns().get(0));        
    }
    
    @Test 
    public void testParsedCaseStudies() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        LocationsConfiguration locationsConfiguration = new LocationsConfiguration();
        File caseStudySrcDir = new File(locationsConfiguration.getCaseStudySrcDir());
        String caseStudyPackage = locationsConfiguration.getCaseStudyPackage();

        List<Schema> caseStudies = new ArrayList<>();
        String[] entries = caseStudySrcDir.list();
        for (String entry : entries) {
            if (entry.endsWith(JavaUtils.JAVA_FILE_SUFFIX)) {
                String className = caseStudyPackage 
                        + JavaUtils.PACKAGE_SEPARATOR 
                        + JavaUtils.fileNameToClassName(entry);  
                caseStudies.add((Schema) Class.forName(className).newInstance());
            }            
        }
        
        for (Schema caseStudy : caseStudies) {
            Schema duplicate = caseStudy.duplicate();            
            assertEquals(
                    "The tables of " + caseStudy.getName() + " should be equal to its duplicate",
                    caseStudy.getTables(), duplicate.getTables());           
        }
    }
}
