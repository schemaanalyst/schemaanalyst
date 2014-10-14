package org.schemaanalyst.unittest.mutation.equivalence;

import org.junit.Test;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.mutation.equivalence.ChangedTableFinder;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;

import static org.junit.Assert.*;

public class TestChangedTableFinder {
    
    private Schema makeBaseSchema() {
        Schema s = new Schema("schema");
        Table t1 = s.createTable("t1");
        t1.addColumn(new Column("a", new IntDataType()));
        t1.addColumn(new Column("b", new IntDataType()));
        t1.addColumn(new Column("c", new IntDataType()));
        Table t2 = s.createTable("t2");
        t2.addColumn(new Column("d", new IntDataType()));
        t2.addColumn(new Column("e", new IntDataType()));
        t2.addColumn(new Column("f", new IntDataType()));
        Table t3 = s.createTable("t3");
        t3.addColumn(new Column("g", new IntDataType()));
        t3.addColumn(new Column("h", new IntDataType()));
        t3.addColumn(new Column("i", new IntDataType()));
        return s;
    }

    @Test
    public void testIdenticalSchemas() {
        new ChangedTableFinder();
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        assertNull("The changed table between identical schemas should be null",
                ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testDifferentFirstTableNames() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        Table table = s2.getTable("t1");
        table.setName("t0");
        assertSame("The changed table should be the renamed table in two "
                + "otherwise identical schemas", table,
                ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testDifferentMiddleTableNames() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        Table table = s2.getTable("t2");
        table.setName("t0");
        assertSame("The changed table should be the renamed table in two "
                + "otherwise identical schemas", table,
                ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testDifferentLastTableNames() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        Table table = s2.getTable("t3");
        table.setName("t0");
        assertSame("The changed table should be the renamed table in two "
                + "otherwise identical schemas", table,
                ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testDifferentColumnName() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        Table table = s2.getTable("t1");
        Column column = table.getColumn("b");
        column.setName("bb");
        assertSame("The changed table should be the table with a renamed column "
                + "in two otherwise identical schemas", table,
                ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testDifferentColumnType() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        Table table = s2.getTable("t1");
        Column column = table.getColumn("b");
        column.setDataType(new VarCharDataType(10));
        assertSame("The changed table should be the table with a retyped column"
                + " in two otherwise identical schemas", table,
                ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testMissingPK() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        s2.setPrimaryKeyConstraint(new PrimaryKeyConstraint(s2.getTable("t2"), s2.getTable("t2").getColumn("e")));
        assertNotNull("A table with a primary key should be changed compared "
                + "to a table without that primary key",
                ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testMissingColumnInPK() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        s1.setPrimaryKeyConstraint(new PrimaryKeyConstraint(s1.getTable("t2"), s1.getTable("t2").getColumn("e")));
        s2.setPrimaryKeyConstraint(new PrimaryKeyConstraint(s2.getTable("t2"), s2.getTable("t2").getColumn("e")));
        assertNull("Two identical schemas with primary keys should have no "
                + "changed table", ChangedTableFinder.getDifferentTable(s1, s2));
        s2.setPrimaryKeyConstraint(new PrimaryKeyConstraint(s2.getTable("t2"), s2.getTable("t2").getColumn("e"), s2.getTable("t2").getColumn("f")));
        assertNotNull("A table with a 2 column primary key should be changed "
                + "compared to a table with a 1 column primary key",
                ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testMissingColumnInPK2() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        s1.setPrimaryKeyConstraint(new PrimaryKeyConstraint(s1.getTable("t2"), s1.getTable("t2").getColumn("e")));
        s2.setPrimaryKeyConstraint(new PrimaryKeyConstraint(s2.getTable("t2"), s2.getTable("t2").getColumn("e")));
        assertNull("Two identical schemas with primary keys should have no "
                + "changed table", ChangedTableFinder.getDifferentTable(s1, s2));
        s1.setPrimaryKeyConstraint(new PrimaryKeyConstraint(s1.getTable("t2"), s1.getTable("t2").getColumn("e"), s1.getTable("t2").getColumn("f")));
        assertNotNull("A table with a 2 column primary key should be changed "
                + "compared to a table with a 1 column primary key",
                ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testMissingUnique() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        s1.addUniqueConstraint(new UniqueConstraint(s1.getTable("t3"), s1.getTable("t3").getColumn("h")));
        assertSame("The changed table should be the table with the added unique"
                + " constraint", s1.getTable("t3"), ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testMissingColumnInUnique() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        s1.addUniqueConstraint(new UniqueConstraint(s1.getTable("t3"), s1.getTable("t3").getColumn("h"), s1.getTable("t3").getColumn("i")));
        s2.addUniqueConstraint(new UniqueConstraint(s2.getTable("t3"), s2.getTable("t3").getColumn("h")));
        assertSame("The changed table should be the table with the missing "
                + "column in the unique constraint", s2.getTable("t3"), ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testMissingCheck() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        s2.addCheckConstraint(new CheckConstraint(s2.getTable("t1"), new ConstantExpression(new StringValue("Test"))));
        assertSame("The changed table should be the table with the added check "
                + "constraint", s2.getTable("t1"), ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testMultipleCheck() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        s1.addCheckConstraint(new CheckConstraint(s1.getTable("t1"), new ConstantExpression(new StringValue("Test"))));
        s2.addCheckConstraint(new CheckConstraint(s2.getTable("t1"), new ConstantExpression(new StringValue("Test"))));
        s2.addCheckConstraint(new CheckConstraint(s2.getTable("t2"), new ConstantExpression(new StringValue("Test2"))));
        assertSame("The changed table should be the table with the added check "
                + "constraint", s2.getTable("t2"), ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testMissingNotNull() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        s2.addNotNullConstraint(new NotNullConstraint(s2.getTable("t3"), s2.getTable("t3").getColumn("g")));
        assertSame("The changed table should be the table with the added not "
                + "null constraint", s2.getTable("t3"), ChangedTableFinder.getDifferentTable(s1, s2));
    }
    
    @Test
    public void testMissingColumnInNotNull() {
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        s1.addNotNullConstraint(new NotNullConstraint(s1.getTable("t3"), s1.getTable("t3").getColumn("g")));
        s2.addNotNullConstraint(new NotNullConstraint(s2.getTable("t3"), s2.getTable("t3").getColumn("g")));
        s2.addNotNullConstraint(new NotNullConstraint(s2.getTable("t3"), s2.getTable("t3").getColumn("h")));
        assertSame("The changed table should be the table with the added not "
                + "null constraint", s2.getTable("t3"), ChangedTableFinder.getDifferentTable(s1, s2));
    }
}
