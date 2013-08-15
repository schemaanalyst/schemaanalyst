package org.schemaanalyst.test.sqlrepresentation.constraint;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DoubleDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;

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
    
    @Test(expected=SQLRepresentationException.class)
    public void testMultiColumnConstraintColumnNameClash() {
        Column column1 = new Column("column", new IntDataType());
        Column column2 = new Column("column", new DoubleDataType());
        new PrimaryKeyConstraint(column1, column2);        
    } 
    
    @Test
    public void testMultiColumnConstraintRemapping() {
        Table t1 = new Table("table");
        Column ca1 = t1.createColumn("ca", new IntDataType());
        Column cb1 = t1.createColumn("cb", new IntDataType());
        
        Table t2 = new Table("table");
        Column ca2 = t2.createColumn("ca", new IntDataType());
        Column cb2 = t2.createColumn("cb", new IntDataType());
        
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(ca1, cb1);
        pk.remap(t2);
        
        assertSame(
                "The first column should be that from t2 after the remapping",
                pk.getColumns().get(0), ca2);

        assertSame(
                "The second column should be that from t2 after the remapping",
                pk.getColumns().get(1), cb2);    

        assertNotSame(
                "The first column should not be that from t1 after the remapping",
                pk.getColumns().get(0), ca1);

        assertNotSame(
                "The second column should not be that from t1 after the remapping",
                pk.getColumns().get(1), cb1);       
    }
    
    @Test(expected=SQLRepresentationException.class)
    public void testMultiColumnConstraintRemappingNotIdenticalNames() {
        Table t1 = new Table("table");
        Column ca1 = t1.createColumn("ca1", new IntDataType());
        Column cb1 = t1.createColumn("cb1", new IntDataType());
        
        Table t2 = new Table("table");
        t2.createColumn("ca2", new IntDataType());
        t2.createColumn("cb2", new IntDataType());
        
        new PrimaryKeyConstraint(ca1, cb1).remap(t2);
    }    

    @Test(expected=SQLRepresentationException.class)
    public void testMultiColumnConstraintRemappingNotIdenticalDataTypes() {
        Table t1 = new Table("table");
        Column ca1 = t1.createColumn("ca", new IntDataType());
        Column cb1 = t1.createColumn("cb", new IntDataType());
        
        Table t2 = new Table("table");
        t2.createColumn("ca", new DoubleDataType());
        t2.createColumn("cb", new DoubleDataType());
        
        new PrimaryKeyConstraint(ca1, cb1).remap(t2);
    }    
    
    @Ignore("Not Ready to Run")
    @Test
    public void testCheckConstraint() {
        Table table = new Table("table");
        Column column = table.createColumn("column", new IntDataType());
        ColumnExpression ce = new ColumnExpression(table, column);
        CheckConstraint cc1 = new CheckConstraint(
                "cc", new ColumnExpression(table, column));
        CheckConstraint cc2 = cc1.duplicate();
        
        assertNotSame(
                "Duplicated check constraints should not refer to the same object", cc1, cc2);
        
        assertEquals(
                "Duplicated check constraints should be equal", cc1, cc2);      
        
        assertNotSame(
                "Duplicated check constraints should deep copy subexpressions", 
                ce, cc2.getExpression());        
        
        assertSame(
                "Duplicated check constraints should not deep copy columns", 
                column, ((ColumnExpression) cc2.getExpression()).getColumn());
        
        assertSame(
                "Duplicated check constraints should not deep copy tables", 
                table, ((ColumnExpression) cc2.getExpression()).getTable());        
    }      
    
    @Test
    public void testForeignKeyConstraint() {
        Table table = new Table("table");
        Column column = table.createColumn("column1", new IntDataType());
        
        ForeignKeyConstraint fk1 = new ForeignKeyConstraint("fk", column, table, column);
        ForeignKeyConstraint fk2 = fk1.duplicate();
        
        assertNotSame(
                "Duplicated foreign keys should not refer to the same object", fk1, fk2);
        
        assertEquals(
                "Duplicated foreign keys should be equal", fk1, fk2);      
        
        assertSame(
                "Duplicated foreign keys should not deep copy columns", 
                fk1.getColumns().get(0), fk2.getColumns().get(0));
    }  
    
    @Test 
    public void testForeignKeyConstraintRemap() {
        Column column1 = new Column("column", new IntDataType());
        
        Table table = new Table("test");
        Column column2 = table.createColumn("column", new IntDataType());
        
        ForeignKeyConstraint fk = new ForeignKeyConstraint("fk", column1, table, column1);
        fk.remapReferenceColumns(table);
        
        assertSame(
                "The first column should be that from table after the remapping",
                fk.getReferenceColumns().get(0), column2);


        assertNotSame(
                "The first column should not be the same as the original after the remapping",
                fk.getReferenceColumns().get(0), column1);
    }
    
    @Test(expected=SQLRepresentationException.class)
    public void testForeignKeyConstraintRemapFail() {
        Column column1 = new Column("column", new IntDataType());
        
        Table table = new Table("test");
        table.createColumn("column", new DoubleDataType());
        
         new ForeignKeyConstraint("fk", column1, table, column1).remapReferenceColumns(table);
    }    
    
    @Test
    public void testPrimaryKeyConstraint() {
        Column column = new Column("column1", new IntDataType());
        PrimaryKeyConstraint pk1 = new PrimaryKeyConstraint("pk", column);
        PrimaryKeyConstraint pk2 = pk1.duplicate();
        
        assertNotSame(
                "Duplicated primary keys should not refer to the same object", pk1, pk2);
        
        assertEquals(
                "Duplicated primary keys should be equal", pk1, pk2);      
        
        assertSame(
                "Duplicated primary keys should not deep copy columns", 
                pk1.getColumns().get(0), pk2.getColumns().get(0));
    }
    
    @Test
    public void testNotNullConstraint() {
        Column column = new Column("column1", new IntDataType());
        NotNullConstraint nn1 = new NotNullConstraint("nn", column);
        NotNullConstraint nn2 = nn1.duplicate();
        
        assertNotSame(
                "Duplicated NOT NULL constraints should not refer to the same object", nn1, nn2);
        
        assertEquals(
                "Duplicated NOT NULL constraints should be equal", nn1, nn2);      
        
        assertSame(
                "Duplicated primary keys should not deep copy columns", 
                nn1.getColumn(), nn2.getColumn());
    }  
    
    @Test
    public void testNotNullConstraintRemap() {
        Column column1 = new Column("column", new IntDataType());
        
        Table table = new Table("test");
        Column column2 = table.createColumn("column", new IntDataType());
        
        NotNullConstraint nn = new NotNullConstraint("nn", column1);
        nn.remap(table);
        
        assertSame(
                "The NOT NULL column should be that from table after the remapping",
                nn.getColumn(), column2);


        assertNotSame(
                "The NOT NULL column should not be the same as the original after the remapping",
                nn.getColumn(), column1);
    }
    
    @Test(expected=SQLRepresentationException.class)
    public void testNotNullConstraintRemapFail() {
        Column column1 = new Column("column", new IntDataType());
        
        Table table = new Table("test");
        table.createColumn("column", new DoubleDataType());
        
        new NotNullConstraint("nn", column1).remap(table);
    }    
    
    @Test
    public void testUniqueConstraint() {
        Column column = new Column("column1", new IntDataType());
        UniqueConstraint uc1 = new UniqueConstraint("uc", column);
        UniqueConstraint uc2 = uc1.duplicate();
        
        assertNotSame(
                "Duplicated unique constraints should not refer to the same object", uc1, uc2);
        
        assertEquals(
                "Duplicated unique constraints should be equal", uc1, uc2);      
        
        assertSame(
                "Duplicated unique constraints should not deep copy columns", 
                uc1.getColumns().get(0), uc2.getColumns().get(0));
    }    
}
