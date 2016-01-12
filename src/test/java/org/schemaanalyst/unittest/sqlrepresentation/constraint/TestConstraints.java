package org.schemaanalyst.unittest.sqlrepresentation.constraint;

import org.junit.Test;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlrepresentation.datatype.DoubleDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import static org.junit.Assert.*;

public class TestConstraints {
    
    @Test
    public void testMultiColumnConstraintOneColumn() {
        Table table = new Table("test");
        Column column = table.createColumn("column1", new IntDataType());
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(table, column);
        
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
        Table table = new Table("test");
        Column column1 = table.createColumn("column1", new IntDataType());
        Column column2 = table.createColumn("column2", new IntDataType());
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(table, column1, column2);
        
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
    public void testMultiColumnConstraintColumnDefinedTwice() {
        Table table = new Table("test");
        Column column = table.createColumn("column", new IntDataType());
        new PrimaryKeyConstraint(table, column, column);        
    } 
    
    @Test
    public void testMultiColumnConstraintRemapping() {
        Table t1 = new Table("table");
        Column ca1 = t1.createColumn("ca", new IntDataType());
        Column cb1 = t1.createColumn("cb", new IntDataType());
        
        Table t2 = new Table("table");
        Column ca2 = t2.createColumn("ca", new IntDataType());
        Column cb2 = t2.createColumn("cb", new IntDataType());
        
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(t1, ca1, cb1);
        pk.remap(t2);

        assertSame(
                "The table of the constraint should be t2 after the remapping",
                t2, pk.getTable());        
        
        assertSame(
                "The first column should be that from t2 after the remapping",
                ca2, pk.getColumns().get(0));

        assertSame(
                "The second column should be that from t2 after the remapping",
                cb2, pk.getColumns().get(1));    

        assertNotSame(
                "The first column should not be that from t1 after the remapping",
                ca1, pk.getColumns().get(0));

        assertNotSame(
                "The second column should not be that from t1 after the remapping",
                cb1, pk.getColumns().get(1));       
    }
    
    @Test(expected=SQLRepresentationException.class)
    public void testMultiColumnConstraintRemappingNotIdenticalNames() {
        Table t1 = new Table("table");
        Column ca1 = t1.createColumn("ca1", new IntDataType());
        Column cb1 = t1.createColumn("cb1", new IntDataType());
        
        Table t2 = new Table("table");
        t2.createColumn("ca2", new IntDataType());
        t2.createColumn("cb2", new IntDataType());
        
        new PrimaryKeyConstraint(t1, ca1, cb1).remap(t2);
    }    

    @Test(expected=SQLRepresentationException.class)
    public void testMultiColumnConstraintRemappingNotIdenticalDataTypes() {
        Table t1 = new Table("table");
        Column ca1 = t1.createColumn("ca", new IntDataType());
        Column cb1 = t1.createColumn("cb", new IntDataType());
        
        Table t2 = new Table("table");
        t2.createColumn("ca", new DoubleDataType());
        t2.createColumn("cb", new DoubleDataType());
        
        new PrimaryKeyConstraint(t1, ca1, cb1).remap(t2);
    }    
    
    @Test
    public void testMultiColumnEquals() {
    	Table t = new Table("table");
        Column c1 = t.createColumn("ca", new IntDataType());
        Column c2 = t.createColumn("cb", new IntDataType());
    	
        UniqueConstraint uc1 = new UniqueConstraint(t, c1, c2);
        UniqueConstraint uc2 = new UniqueConstraint(t, c1, c2);
    	
    	assertEquals(
    			"Identical constraints should be equal",
    			uc1, uc2);
    	
    	UniqueConstraint uc4 = new UniqueConstraint("MyConstraint", t, c1, c2);
    	assertEquals(
    			"Identifying names should not be taken into account in the equals operator",
    			uc1, uc4);    	
    	
    	assertFalse(
    			"A constraint should never be equal to null", uc1 == null);
    	
    	PrimaryKeyConstraint pk1 = new PrimaryKeyConstraint(t, c1, c2);
    	assertNotEquals(
    			"UNIQUE constraints and PRIMARY KEY constraints of the same columns should not be equal",
    			uc1, pk1);    	
    }     
    
    
    @Test
    public void testCheckConstraint() {
        Table table = new Table("table");
        Column column = table.createColumn("column", new IntDataType());
        ColumnExpression ce = new ColumnExpression(table, column);
        CheckConstraint cc1 = new CheckConstraint(
                "cc", table, new ColumnExpression(table, column));
        CheckConstraint cc2 = cc1.duplicate();
        
        assertNotSame(
                "Duplicated CHECK constraints should not refer to the same object", cc1, cc2);
        
        assertEquals(
                "Duplicated CHECK constraints should be equal", cc1, cc2);  
        
        assertEquals(
                "Duplicated CHECK constraints should have the same hashcode", 
                cc1.hashCode(), cc2.hashCode());          
        
        assertNotSame(
                "Duplicated CHECK constraints should deep copy subexpressions", 
                ce, cc2.getExpression());        
        
        assertSame(
                "Duplicated CHECK constraints should not deep copy columns", 
                column, ((ColumnExpression) cc2.getExpression()).getColumn());
        
        assertSame(
                "Duplicated CHECK constraints should not deep copy tables", 
                table, ((ColumnExpression) cc2.getExpression()).getTable());        
    }      
    
    @Test
    public void testCheckConstraintRemap() {
        Table table1 = new Table("table");
        Column table1Column = table1.createColumn("column", new IntDataType());
        
        Table table2 = new Table("table");
        Column table2Column = table2.createColumn("column", new IntDataType());
        
        ColumnExpression colExp = new ColumnExpression(table1, table1Column);
        
        CheckConstraint cc = new CheckConstraint(table1, colExp);
        cc.remap(table2);
        
        assertSame(
                "The table of the CHECK constraint should be table2",
                table2, colExp.getTable());        
        
        assertSame(
                "The table of colExp should be remapped to table2",
                table2, colExp.getTable());
        
        assertSame(
                "The column of colExp should be remapped to table2Column",
                table2Column, colExp.getColumn());        
    }
    
    @Test 
    public void testCheckConstraintDuplication() {
        Column c_a = new Column("a", new IntDataType());
        Column c_b = new Column("b", new IntDataType());
        Table t_one = new Table("one");
        t_one.addColumn(c_a);
        t_one.addColumn(c_b);
        
        
        Schema schema1 = new Schema("schema");
        schema1.addTable(t_one);
        
        schema1.addCheckConstraint(new CheckConstraint(t_one,
                new RelationalExpression(
                    new ColumnExpression(t_one, c_a),
                    RelationalOperator.EQUALS,
                    new ColumnExpression(t_one, c_b))
                ));
        
        Schema schema2 = schema1.duplicate();
        
        assertNotSame(schema1, schema2);
        assertEquals(schema1, schema2);
        
        assertNotSame(
        		((CheckConstraint) schema1.getCheckConstraints().get(0)), 
        		((CheckConstraint) schema2.getCheckConstraints().get(0)));        

        assertEquals(
        		((CheckConstraint) schema1.getCheckConstraints().get(0)), 
        		((CheckConstraint) schema2.getCheckConstraints().get(0)));         
        
        assertNotSame(
        		((CheckConstraint) schema1.getCheckConstraints().get(0)).getExpression(), 
        		((CheckConstraint) schema2.getCheckConstraints().get(0)).getExpression());

        assertEquals(
        		((CheckConstraint) schema1.getCheckConstraints().get(0)).getExpression(), 
        		((CheckConstraint) schema2.getCheckConstraints().get(0)).getExpression());
        
        
        assertNotSame(
        		((RelationalExpression) ((CheckConstraint) schema1.getCheckConstraints().get(0)).getExpression()).getLHS(), 
        		((RelationalExpression) ((CheckConstraint) schema2.getCheckConstraints().get(0)).getExpression()).getLHS());

        assertEquals(
        		((RelationalExpression) ((CheckConstraint) schema1.getCheckConstraints().get(0)).getExpression()).getLHS(), 
        		((RelationalExpression) ((CheckConstraint) schema2.getCheckConstraints().get(0)).getExpression()).getLHS());
        
        assertNotSame(
        		((RelationalExpression) ((CheckConstraint) schema1.getCheckConstraints().get(0)).getExpression()).getRHS(), 
        		((RelationalExpression) ((CheckConstraint) schema2.getCheckConstraints().get(0)).getExpression()).getRHS());        
        
        assertEquals(
        		((RelationalExpression) ((CheckConstraint) schema1.getCheckConstraints().get(0)).getExpression()).getRHS(), 
        		((RelationalExpression) ((CheckConstraint) schema2.getCheckConstraints().get(0)).getExpression()).getRHS());        
        
    }
    
    @Test
    public void testForeignKeyConstraint() {
        Table table = new Table("table");
        Column column = table.createColumn("column1", new IntDataType());
        
        ForeignKeyConstraint fk1 = new ForeignKeyConstraint("fk", table, column, table, column);
        ForeignKeyConstraint fk2 = fk1.duplicate();
        
        assertNotSame(
                "Duplicated FOREIGN KEYs should not refer to the same object", fk1, fk2);
        
        assertEquals(
                "Duplicated FOREIGN KEYs should be equal", fk1, fk2);      

        assertEquals(
                "Duplicated FOREIGN KEYs should have the same hashcode", 
                fk1.hashCode(), fk2.hashCode());          
        
        assertSame(
                "Duplicated FOREIGN KEYs should not deep copy tables", 
                fk1.getTable(), fk2.getTable());        

        assertSame(
                "Duplicated FOREIGN KEYs should not deep copy tables", 
                fk1.getReferenceTable(), fk2.getReferenceTable());        
        
        assertSame(
                "Duplicated FOREIGN KEYs should not deep copy columns", 
                fk1.getColumns().get(0), fk2.getColumns().get(0));

        assertSame(
                "Duplicated FOREIGN KEYs should not deep copy columns", 
                fk1.getReferenceColumns().get(0), fk2.getReferenceColumns().get(0));    
    }  
    
    @Test 
    public void testForeignKeyConstraintRemap() {
        Table table1 = new Table("test");
        Column column1 = table1.createColumn("column", new IntDataType());
        
        Table table2 = new Table("test");
        Column column2 = table2.createColumn("column", new IntDataType());
        
        ForeignKeyConstraint fk = new ForeignKeyConstraint("fk", table1, column1, table1, column1);
        
        
        fk.remap(table2, table2);
        
        assertSame(
                "The remapped table should be table2",
                table2, fk.getReferenceTable());
        
        assertSame(
                "The first column should be that from table after the remapping",
                column2, fk.getColumns().get(0));


        assertNotSame(
                "The first column should not be the same as the original after the remapping",
                column1, fk.getReferenceColumns().get(0));        
        
        assertSame(
                "The remapped reference table should be table2",
                table2, fk.getReferenceTable());
        
        assertSame(
                "The first column should be that from table after the remapping",
                column2, fk.getReferenceColumns().get(0));


        assertNotSame(
                "The first column should not be the same as the original after the remapping",
                column1, fk.getReferenceColumns().get(0));
    }
    
    @Test(expected=SQLRepresentationException.class)
    public void testForeignKeyConstraintRemapFail() {
        Table table1 = new Table("test");
        Column column1 = table1.createColumn("column", new IntDataType());
        
        Table table2 = new Table("test");
        table2.createColumn("column", new DoubleDataType());
        
         new ForeignKeyConstraint("fk", table1, column1, table1, column1).remap(table2, table2);
    }    
    
    @Test
    public void testPrimaryKeyConstraint() {
        Table table = new Table("table");
        Column column = table.createColumn("column1", new IntDataType());
        PrimaryKeyConstraint pk1 = new PrimaryKeyConstraint("pk", table, column);
        PrimaryKeyConstraint pk2 = pk1.duplicate();
        
        assertNotSame(
                "Duplicated PRIMARY KEYs should not refer to the same object", pk1, pk2);
        
        assertEquals(
                "Duplicated PRIMARY KEYs should be equal", pk1, pk2);      

        assertEquals(
                "Duplicated PRIMARY KEYs should have the same hashcode", 
                pk1.hashCode(), pk1.hashCode());           
        
        assertSame(
                "Duplicated PRIMARY KEYs should not deep copy tables", 
                pk1.getTable(), pk2.getTable());        
        
        assertSame(
                "Duplicated PRIMARY KEYs should not deep copy columns", 
                pk1.getColumns().get(0), pk2.getColumns().get(0));
    }
    
    @Test
    public void testNotNullConstraint() {
        Table table = new Table("table");
        Column column = table.createColumn("column1", new IntDataType());
        NotNullConstraint nn1 = new NotNullConstraint("nn", table, column);
        NotNullConstraint nn2 = nn1.duplicate();
        
        assertNotSame(
                "Duplicated NOT NULL constraints should not refer to the same object", nn1, nn2);
        
        assertEquals(
                "Duplicated NOT NULL constraints should be equal", nn1, nn2);      
        
        assertEquals(
                "Duplicated NOT NULL constraints should have the same hashcode", 
                nn1.hashCode(), nn2.hashCode());           
        
        assertSame(
                "Duplicated NOT NULL constraints should not deep copy tables", 
                nn1.getTable(), nn2.getTable());        

        assertSame(
                "Duplicated NOT NULL constraints should not deep copy columns", 
                nn1.getColumn(), nn2.getColumn());
    }  
    
    @Test
    public void testNotNullConstraintRemap() {
        Table table1 = new Table("table");
        Column column1 = table1.createColumn("column", new IntDataType());
        
        Table table2 = new Table("table");
        Column column2 = table2.createColumn("column", new IntDataType());
        
        NotNullConstraint nn = new NotNullConstraint("nn", table1, column1);
        nn.remap(table2);
        
        assertSame(
                "The NOT NULL column should be that from table after the remapping",
                column2, nn.getColumn());

        assertSame(
                "The remapped table should be table2",
                table2, nn.getTable());          
        
        assertNotSame(
                "The NOT NULL column should not be the same as the original after the remapping",
                column1, nn.getColumn());
    }
    
    @Test(expected=SQLRepresentationException.class)
    public void testNotNullConstraintRemapFail() {
        Table table1 = new Table("table");
        Column column1 = table1.createColumn("column", new IntDataType());
        
        Table table2 = new Table("test");
        table2.createColumn("column", new DoubleDataType());
        
        new NotNullConstraint("nn", table1, column1).remap(table2);
    }    
    
    @Test
    public void testUniqueConstraint() {
        Table table = new Table("table");
        Column column = table.createColumn("column", new IntDataType());
        UniqueConstraint uc1 = new UniqueConstraint("uc", table, column);
        UniqueConstraint uc2 = uc1.duplicate();
        
        assertNotSame(
                "Duplicated UNIQUE constraints should not refer to the same object", uc1, uc2);
        
        assertEquals(
                "Duplicated UNIQUE constraints should be equal", uc1, uc2);      
        
        assertEquals(
                "Duplicated UNIQUE constraints should have the same hashcode", 
                uc1.hashCode(), uc2.hashCode());         
        
        assertSame(
                "Duplicated UNIQUE constraints should not deep copy tables", 
                uc1.getTable(), uc2.getTable());           
        
        assertSame(
                "Duplicated UNIQUE constraints should not deep copy columns", 
                uc1.getColumns().get(0), uc2.getColumns().get(0));
    }    
}
