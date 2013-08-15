package org.schemaanalyst.test.sqlrepresentation.constraint;

import java.util.ArrayList;

import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static org.junit.Assert.*;

public class TestConstraints {

    @Test(expected=SQLRepresentationException.class)
    public void testMultiColumnConstraintNoColumns() {
        new PrimaryKeyConstraint(new ArrayList<Column>());
    }

    @Test
    public void testMultiColumnConstraintOneColumn() {
        Column column = new Column("column1", new IntDataType());
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(column);
        
        assertTrue(
                "MultiColumnConstraint should involve the column assigned to it",
                pk.involvesColumn(column));
        
        assertEquals(
                "MultiColumnConstraint should have one column",
                1, pk.getNumColumns());        
        
        assertFalse(
                "hasMultipleColumns should return false if there is only one column",
                pk.hasMultipleColumns());
    }
    
    @Test
    public void testMultiColumnConstraintTwoColumns() {
        Column column1 = new Column("column1", new IntDataType());
        Column column2 = new Column("column2", new IntDataType());
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(column1, column2);
        
        assertTrue(
                "MultiColumnConstraint should involve the columns assigned to it",
                pk.involvesColumn(column1));

        assertTrue(
                "MultiColumnConstraint should involve the columns assigned to it",
                pk.involvesColumn(column2));        
        
        assertEquals(
                "MultiColumnConstraint should have two columns",
                2, pk.getNumColumns());        
        
        assertTrue(
                "hasMultipleColumns should return true if there is only one column",
                pk.hasMultipleColumns());
    }    
    
    
    @Test
    public void testPrimaryKeyConstraint() {
        Column column = new Column("column1", new IntDataType());
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(
                "pk", column);
        
        
        
    }
    
}
