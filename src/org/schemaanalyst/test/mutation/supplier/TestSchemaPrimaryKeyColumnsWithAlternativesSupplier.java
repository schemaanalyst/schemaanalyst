package org.schemaanalyst.test.mutation.supplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.schemaanalyst.mutation.MutationException;
import org.schemaanalyst.mutation.supplier.schema.PrimaryKeyColumnsWithAlternativesSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.util.Pair;

import static org.junit.Assert.*;

public class TestSchemaPrimaryKeyColumnsWithAlternativesSupplier {
    
    @Test
    public void testNoTables() {
        Schema schema = new Schema("test");
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        
        assertFalse(
                "If there are no tables in the schema getNextComponent should return null",
                supplier.hasNext());
        
        
        assertNull(
                "If there are no tables in the schema getNextComponent should return null",
                supplier.getNextComponent());
    }
    
    @Test(expected=MutationException.class) 
    public void testNoTablesException1() {
        Schema schema = new Schema("test");
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        supplier.getNextComponent();
        supplier.getDuplicateComponent();
    }  
    
    @Test(expected=MutationException.class) 
    public void testNoTablesException2() {
        Schema schema = new Schema("test");
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        supplier.getNextComponent();
        supplier.makeDuplicate();
    }  

    @Test(expected=MutationException.class) 
    public void testNoTablesException3() {
        Schema schema = new Schema("test");
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        supplier.getNextComponent();
        supplier.getDuplicateComponent();
    }  
    
    @Test(expected=MutationException.class) 
    public void testNoTablesException4() {
        Schema schema = new Schema("test");
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        supplier.getNextComponent();
        supplier.putComponentBackInDuplicate(null);
    }    
    
    @Test(expected=MutationException.class) 
    public void testNotInitialisedException1() {
        Schema schema = new Schema("test");
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        supplier.getDuplicateComponent();
    }  
    
    @Test(expected=MutationException.class) 
    public void testNotInitialisedException2() {
        Schema schema = new Schema("test");
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        supplier.makeDuplicate();
    }  

    @Test(expected=MutationException.class) 
    public void testNotInitialisedException3() {
        Schema schema = new Schema("test");
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        supplier.getDuplicateComponent();
    }  
    
    @Test(expected=MutationException.class) 
    public void testNotInitialisedException4() {
        Schema schema = new Schema("test");
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        supplier.putComponentBackInDuplicate(null);
    }      
    
    @Test
    public void testOneTableNoPrimaryKey() {
        Schema schema = new Schema("schema");
        Table table = schema.createTable("table");
        Column column = table.createColumn("column", new IntDataType());
        
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        
        assertTrue(
                "There is one table, so hasNext() should return true",
                supplier.hasNext());          

        Pair<List<Column>> nextComponent = supplier.getNextComponent(); 
        
        assertNotNull(
                "If there is one table, so although there is no primary key the result of getNextComponent should not be null",
                nextComponent);        
        
        List<Column> constraintColumns = nextComponent.getFirst();
        List<Column> alternativeColumns = nextComponent.getSecond();
        
        assertEquals(
                "If there is no PRIMARY KEY for the table, the next component columns should be empty",
                0, constraintColumns.size());

        assertEquals(
                "The number of alternative columns should be 1",
                1, alternativeColumns.size());        
        
        assertSame(
                "The alternative column should be the same object as column",
                column, alternativeColumns.get(0));        
        
        // Need to get an artefact duplicate before the component
        // the return object of this call would normally go to the mutation to add to the 
        // set of mutants
        Schema duplicate = supplier.makeDuplicate();

        assertNotSame(
                "The original schema and its duplicate should not be the same object",
                schema, duplicate);        
        
        assertEquals(
                "The original schema and its duplicate should be equal",
                schema, duplicate);
        
        assertNotNull(
                "Even if there are no PRIMARY KEYs in the schema, getComponentCopy should not return null",
                supplier.getDuplicateComponent());
                
        // set the primary key column to be the alternatives
        supplier.putComponentBackInDuplicate(new Pair<>(alternativeColumns, null));
       
        
        assertFalse(
                "The original schema and its duplicate should not be equal following the change",
                schema.equals(duplicate));
        
        assertTrue(
                "The duplicate should now have a primary key for table",
                duplicate.hasPrimaryKeyConstraint(table));         
        
        assertNull(
                "There are no more tables, thus getNextComponent should return null",
                supplier.getNextComponent()); 
    }

    @Test
    public void testOneTableWithPrimaryKey() {
        Schema schema = new Schema("schema");
        Table table = schema.createTable("table");
        Column column = table.createColumn("column", new IntDataType());
        schema.createPrimaryKeyConstraint(table, column);
        
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        
        Pair<List<Column>> nextComponent = supplier.getNextComponent(); 
        List<Column> constraintColumns = nextComponent.getFirst();
        List<Column> alternativeColumns = nextComponent.getSecond();
        
        assertEquals(
                "There is a PRIMARY KEY for the table, the component columns should be one",
                1, constraintColumns.size());

        assertEquals(
                "The number of alternative columns should be 1 (there are no other columns in the table)",
                0, alternativeColumns.size());        
        
        assertSame(
                "The PRIAMRY KEY column should be the same object as column",
                column, constraintColumns.get(0));        
        
        // Need to get an artefact duplicate before the component
        // the return object of this call would normally go to the mutation to add to the 
        // set of mutants
        Schema duplicate = supplier.makeDuplicate();
        
        assertNotSame(
                "The original schema and its duplicate should not be the same object",
                schema, duplicate);         
        
        assertEquals(
                "The original schema and its duplicate should be equal",
                schema, duplicate);
        
        assertNotNull(
                "The component duplicate should not return null",
                supplier.getDuplicateComponent());
                
        // set the primary key column to be empty
        List<Column> emptyList = new ArrayList<>();
        supplier.putComponentBackInDuplicate(new Pair<>(emptyList, null));
        
        //assertFalse(
        //        "The original schema and its duplicate should not be equal following the change",
        //        schema.equals(duplicate));
        
        assertFalse(
                "The duplicate should now NOT have a primary key for table",
                duplicate.hasPrimaryKeyConstraint(table));         
        
        assertNull(
                "There are no more tables, thus getNextComponent should return null",
                supplier.getNextComponent());         
    }
    
    @Test
    public void testMultipleChanges() {
        Schema schema = new Schema("schema");
        Table table = schema.createTable("table");
        Column column1 = table.createColumn("column1", new IntDataType());
        Column column2 = table.createColumn("column2", new RealDataType());
        schema.createPrimaryKeyConstraint(table, column1);
        
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        
        Pair<List<Column>> nextComponent = supplier.getNextComponent(); 
        
        assertNotNull(
                "If there is one table, so although there is no primary key the result of getNextComponent should not be null",
                nextComponent);           

        List<Column> emptyList = new ArrayList<>();
        List<Column> singletonList = Collections.singletonList(column2);
        
        Schema duplicate1 = supplier.makeDuplicate();        
        
        assertNotSame(
                "The original schema and its duplicate should not be the same object",
                schema, duplicate1);   
        
        assertEquals(
                "The original schema and its duplicate should be equal",
                schema, duplicate1);        
        
        supplier.putComponentBackInDuplicate(new Pair<>(emptyList, null));
        
        assertFalse(
                "The original schema and its duplicate should not be equal following the change",
                schema.equals(duplicate1));        
        
        Schema duplicate2 = supplier.makeDuplicate();

        assertEquals(
                "The original schema and its duplicate should be equal",
                schema, duplicate2);                
        
        assertNotSame(
                "The duplicate and the previous duplicate should not be the same object",
                duplicate2, duplicate1);   

        assertFalse(
                "The duplicate and the previous duplicate should not be equal (since the previous duplicate has changed)",
                duplicate2.equals(duplicate1));         
        
        
        supplier.putComponentBackInDuplicate(new Pair<>(singletonList, null));
        
        assertFalse(
                "The original schema and its duplicate should not be equal following the change",
                schema.equals(duplicate2));       
        
        assertFalse(
                "The duplicate and the previous duplicate should not be equal (since both duplicates have now changed)",
                duplicate1.equals(duplicate2));  
        
        assertNull(
                "There are no more tables, thus getNextComponent should return null",
                supplier.getNextComponent());         
    }
    
    @Test
    public void testMultiplePrimaryKeys() {
        Schema schema = new Schema("schema");
        Table table1 = schema.createTable("table1");
        Column column1 = table1.createColumn("colum1", new IntDataType());
        schema.createPrimaryKeyConstraint(table1, column1);
        
        Table table2 = schema.createTable("table2");
        Column column2 = table2.createColumn("column2", new IntDataType());
        schema.createPrimaryKeyConstraint(table2, column2);     
        
        PrimaryKeyColumnsWithAlternativesSupplier supplier = new PrimaryKeyColumnsWithAlternativesSupplier();
        supplier.initialise(schema);
        
        Pair<List<Column>> nextComponent = supplier.getNextComponent(); 
        
        assertNotNull(
                "If there are two tables, first nextComponent call should not be null",
                nextComponent);
        
        nextComponent = supplier.getNextComponent(); 
        
        assertNotNull(
                "If there are two tables, second nextComponent call should not be null",
                nextComponent);        
        
        nextComponent = supplier.getNextComponent(); 
        
        assertNull(
                "If there are two tables, third nextComponent call should be null",
                nextComponent);
    }

}
