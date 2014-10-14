package org.schemaanalyst.unittest.mutation.analysis.util;

import org.junit.Test;
import org.schemaanalyst.mutation.analysis.util.SchemaMerger;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;

import static org.junit.Assert.*;

/**
 *
 * @author Chris J. Wright
 */
public class TestSchemaMerger {

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
    public void mergeSame() {
        Schema a = makeBaseSchema();
        Schema merge = SchemaMerger.merge(a, a);
        assertFalse(a == merge);
        assertTrue(a.equals(merge));
    }

    @Test
    public void mergeMatching() {
        Schema a = makeBaseSchema();
        Schema b = makeBaseSchema();
        Schema merge = SchemaMerger.merge(a, b);
        assertFalse(a == merge);
        assertFalse(b == merge);
        assertTrue(a.equals(merge));
        assertTrue(b.equals(merge));
    }

    @Test
    public void mergeDuplicate() {
        Schema a = makeBaseSchema();
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(a.getTable("t1"), a.getTable("t1").getColumn("a"));
        a.setPrimaryKeyConstraint(pk);
        Schema b = makeBaseSchema();
        Schema merge = SchemaMerger.merge(a, b);
        merge.removePrimaryKeyConstraint(merge.getTable("t1"));
        assertEquals(1, a.getPrimaryKeyConstraints().size());
        assertEquals(0, merge.getPrimaryKeyConstraints().size());
    }

    @Test
    public void mergeDifferingA() {
        Schema a = makeBaseSchema();
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(a.getTable("t1"), a.getTable("t1").getColumn("a"));
        a.setPrimaryKeyConstraint(pk);
        Schema b = makeBaseSchema();
        Schema merge = SchemaMerger.merge(a, b);
        PrimaryKeyConstraint mergePk = merge.getPrimaryKeyConstraint(merge.getTable("t1"));
        assertFalse(a == merge);
        assertFalse(b == merge);
        assertEquals(pk, mergePk);
        assertFalse(pk == mergePk);
        assertEquals(0, b.getPrimaryKeyConstraints().size());
        assertEquals(1, a.getPrimaryKeyConstraints().size());
        assertEquals(1, merge.getPrimaryKeyConstraints().size());
    }

    @Test
    public void mergeDifferingB() {
        Schema b = makeBaseSchema();
        PrimaryKeyConstraint pk = new PrimaryKeyConstraint(b.getTable("t1"), b.getTable("t1").getColumn("a"));
        b.setPrimaryKeyConstraint(pk);
        Schema a = makeBaseSchema();
        Schema merge = SchemaMerger.merge(a, b);
        assertEquals(1, b.getPrimaryKeyConstraints().size());
        assertEquals(0, a.getPrimaryKeyConstraints().size());
        assertEquals(0, merge.getPrimaryKeyConstraints().size());
    }

    @Test
    public void mergeExtraTableA() {
        Schema a = makeBaseSchema();
        a.addTable(new Table("t4"));
        Schema b = makeBaseSchema();
        Schema merge = SchemaMerger.merge(a, b);
        assertEquals(4, a.getTables().size());
        assertEquals(3, b.getTables().size());
        assertEquals(4, merge.getTables().size());
    }

    @Test
    public void mergeExtraTableB() {
        Schema b = makeBaseSchema();
        b.addTable(new Table("t4"));
        Schema a = makeBaseSchema();
        Schema merge = SchemaMerger.merge(b, a);
        assertEquals(4, b.getTables().size());
        assertEquals(3, a.getTables().size());
        assertEquals(4, merge.getTables().size());
    }

    @Test
    public void mergeTableWithNotNull() {
        Schema a = makeBaseSchema();
        Schema b = makeBaseSchema();
        Table t4 = new Table("t4");
        Column j = new Column("j", new IntDataType());
        t4.addColumn(j);
        b.addTable(t4);
        NotNullConstraint constraint = new NotNullConstraint(t4, j);
        b.addNotNullConstraint(constraint);
        Schema merge = SchemaMerger.merge(a, b);
        assertNotNull(merge.getTable("t4"));
        assertEquals(1, merge.getNotNullConstraints(merge.getTable("t4")).size());
    }
    
    @Test
    public void mergeTableWithUnique() {
        Schema a = makeBaseSchema();
        Schema b = makeBaseSchema();
        Table t4 = new Table("t4");
        Column j = new Column("j", new IntDataType());
        t4.addColumn(j);
        b.addTable(t4);
        UniqueConstraint constraint = new UniqueConstraint(t4, j);
        b.addUniqueConstraint(constraint);
        Schema merge = SchemaMerger.merge(a, b);
        assertNotNull(merge.getTable("t4"));
        assertEquals(1, merge.getUniqueConstraints(merge.getTable("t4")).size());
    }
    
    @Test
    public void mergeTableWithPK() {
        Schema a = makeBaseSchema();
        Schema b = makeBaseSchema();
        Table t4 = new Table("t4");
        Column j = new Column("j", new IntDataType());
        t4.addColumn(j);
        b.addTable(t4);
        PrimaryKeyConstraint constraint = new PrimaryKeyConstraint(t4, j);
        b.setPrimaryKeyConstraint(constraint);
        Schema merge = SchemaMerger.merge(a, b);
        assertNotNull(merge.getTable("t4"));
        assertNotNull(merge.getPrimaryKeyConstraint(merge.getTable("t4")));
    }
    
    @Test
    public void mergeTableWithCheck() {
        Schema a = makeBaseSchema();
        Schema b = makeBaseSchema();
        Table t4 = new Table("t4");
        Column j = new Column("j", new IntDataType());
        t4.addColumn(j);
        b.addTable(t4);
        CheckConstraint check = new CheckConstraint(t4, new NullExpression(new ColumnExpression(t4, j), true));
        b.addCheckConstraint(check);
        Schema merge = SchemaMerger.merge(a, b);
        assertNotNull(merge.getTable("t4"));
        assertEquals(1, merge.getCheckConstraints(merge.getTable("t4")).size());
    }
    
    @Test
    public void mergeTableWithFK() {
        Schema a = makeBaseSchema();
        Schema b = makeBaseSchema();
        Table t4 = new Table("t4");
        Column j = new Column("j", new IntDataType());
        t4.addColumn(j);
        b.addTable(t4);
        ForeignKeyConstraint fk = new ForeignKeyConstraint(t4, j, b.getTable("t3"), b.getTable("t3").getColumn("i"));
        b.addForeignKeyConstraint(fk);
        Schema merge = SchemaMerger.merge(a, b);
        assertNotNull(merge.getTable("t4"));
        assertEquals(1, merge.getForeignKeyConstraints(merge.getTable("t4")).size());
    }

}
