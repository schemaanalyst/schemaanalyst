package org.schemaanalyst.unittest.mutation.equivalence;

import org.junit.Test;
import org.schemaanalyst.mutation.equivalence.ChangedConstraintFinder;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *
 * @author Chris J. Wright
 */
public class TestChangedConstraintFinder {
    
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
        Schema s1 = makeBaseSchema();
        Schema s2 = makeBaseSchema();
        assertNull("The changed constraint between identical schemas should be "
                + "null", ChangedConstraintFinder.getDifferentConstraint(s1, s2));
    }
    
    @Test
    public void testIdenticalSchemasWithPk() {
        Schema s1 = makeBaseSchema();
        Table s1t1 = s1.getTable("t1");
        PrimaryKeyConstraint s1constraint = new PrimaryKeyConstraint(s1t1, s1t1.getColumn("a"));
        s1.setPrimaryKeyConstraint(s1constraint);
        Schema s2 = makeBaseSchema();
        Table s2t1 = s2.getTable("t1");
        PrimaryKeyConstraint s2constraint = new PrimaryKeyConstraint(s2t1, s2t1.getColumn("a"));
        s2.setPrimaryKeyConstraint(s2constraint);
        assertNull("The changed constraint between identical schemas should be "
                + "null", ChangedConstraintFinder.getDifferentConstraint(s1, s2));
    }
    
    @Test
    public void testIdenticalSchemasWithUnique() {
        Schema s1 = makeBaseSchema();
        Table s1t1 = s1.getTable("t1");
        UniqueConstraint s1constraint = new UniqueConstraint(s1t1, s1t1.getColumn("a"));
        s1.addUniqueConstraint(s1constraint);
        Schema s2 = makeBaseSchema();
        Table s2t1 = s2.getTable("t1");
        UniqueConstraint s2constraint = new UniqueConstraint(s2t1, s2t1.getColumn("a"));
        s2.addUniqueConstraint(s2constraint);
        assertNull("The changed constraint between identical schemas should be "
                + "null", ChangedConstraintFinder.getDifferentConstraint(s1, s2));
    }
    
    @Test
    public void testAddedPK() {
        Schema s1 = makeBaseSchema();
        Table t1 = s1.getTable("t1");
        PrimaryKeyConstraint constraint = new PrimaryKeyConstraint(t1, t1.getColumn("a"));
        s1.setPrimaryKeyConstraint(constraint);
        Schema s2 = makeBaseSchema();
        assertEquals("The changed constraint when adding a PK should be the PK",
                constraint, ChangedConstraintFinder.getDifferentConstraint(s1, s2));
    }
    
    @Test
    public void testAddedMultiColPK() {
        Schema s1 = makeBaseSchema();
        Table t1 = s1.getTable("t1");
        PrimaryKeyConstraint constraint = new PrimaryKeyConstraint(t1, t1.getColumn("a"), t1.getColumn("b"));
        s1.setPrimaryKeyConstraint(constraint);
        Schema s2 = makeBaseSchema();
        assertEquals("The changed constraint when adding a PK should be the PK",
                constraint, ChangedConstraintFinder.getDifferentConstraint(s1, s2));
    }
    
    @Test
    public void testPKMismatch() {
        Schema s1 = makeBaseSchema();
        Table s1t1 = s1.getTable("t1");
        PrimaryKeyConstraint s1constraint = new PrimaryKeyConstraint(s1t1, s1t1.getColumn("a"), s1t1.getColumn("b"));
        s1.setPrimaryKeyConstraint(s1constraint);
        Schema s2 = makeBaseSchema();
        Table s2t1 = s2.getTable("t1");
        PrimaryKeyConstraint s2constraint = new PrimaryKeyConstraint(s2t1, s2t1.getColumn("a"));
        s2.setPrimaryKeyConstraint(s2constraint);
        assertEquals("The changed constraint with mismatched PKs should be the PK",
                s2constraint, ChangedConstraintFinder.getDifferentConstraint(s1, s2));
    }
    
}
