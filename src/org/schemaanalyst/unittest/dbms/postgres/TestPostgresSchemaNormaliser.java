package org.schemaanalyst.unittest.dbms.postgres;

import static org.junit.Assert.*;
import org.junit.Test;
import org.schemaanalyst.dbms.postgres.PostgresSchemaNormaliser;
import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceChecker;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;

public class TestPostgresSchemaNormaliser {
    
    PostgresSchemaNormaliser norm = new PostgresSchemaNormaliser();

    private Schema createBaseSchema() {
        Schema s = new Schema("schema");
        Table table = s.createTable("table");
        table.addColumn(new Column("a", new IntDataType()));
        table.addColumn(new Column("b", new IntDataType()));
        table.addColumn(new Column("c", new IntDataType()));
        return s;
    }
    
    private Schema createSchemaWithPK() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createPrimaryKeyConstraint(table, table.getColumn("a"));
        return s;
    }
    
    private Schema createSchemaWithPKNormalised() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createNotNullConstraint(table, table.getColumn("a"));
        return s;
    }
    
    private Schema createSchemaWithPKAndUnique() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createPrimaryKeyConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("a"));
        return s;
    }
    
    private Schema createSchemaWithPKAndUniqueAndNotNull() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createPrimaryKeyConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createNotNullConstraint(table, table.getColumn("a"));
        return s;
    }
    
    private Schema createSchemaWithNotNull() {
        Schema s = createBaseSchema();
        Table table = s.getTable("table");
        s.createCheckConstraint(table, 
                new NullExpression(
                        new ColumnExpression(table, table.getColumn("b")), true));
        return s;
    }
    
    @Test
    public void testSchemaWithPKNotSame() {
        Schema original = createSchemaWithPK();
        Schema normalised = norm.normalise(original);
        assertTrue("Normaliser should always return a copy of the schema",
                original != normalised);
        assertFalse("The original and normalised schema should not be equivalent if the schema contains a PK",
                new SchemaEquivalenceChecker().areEquivalent(original, normalised));
    }
    
    @Test
    public void testSchemaWithPKConstraintCounts() {
        Schema original = createSchemaWithPK();
        Schema normalised = norm.normalise(original);
        assertEquals("The normalised schema should contain no primary key constraints",
                0, normalised.getPrimaryKeyConstraints().size());
        assertEquals("The normalised schema with one PK should contain one unique constraint",
                1, normalised.getUniqueConstraints().size());
        assertEquals("The normalised schema with one PK should contain one not null constraint",
                1, normalised.getNotNullConstraints().size());
    }
    
    @Test
    public void testSchemaWithPKNormalised() {
        Schema original = createSchemaWithPK();
        Schema normalised = norm.normalise(original);
        assertTrue("The normalised schema should be equivalent to a manually normalised schema",
                new SchemaEquivalenceChecker().areEquivalent(normalised, createSchemaWithPKNormalised()));
    }

    @Test
    public void testSchemaWithPKAndUniqueNotSame() {
        Schema original = createSchemaWithPKAndUnique();
        Schema normalised = norm.normalise(original);
        assertTrue("Normaliser should always return a copy of the schema",
                original != normalised);
        assertFalse("The original and normalised schema should not be equivalent if the schema contains a PK",
                new SchemaEquivalenceChecker().areEquivalent(original, normalised));
    }
    
    @Test
    public void testSchemaWithPKAndUniqueConstraintCounts() {
        Schema original = createSchemaWithPKAndUnique();
        Schema normalised = norm.normalise(original);
        assertEquals("The normalised schema should contain no primary key constraints",
                0, normalised.getPrimaryKeyConstraints().size());
        assertEquals("The normalised schema with one PK and one Unique should contain one unique constraint",
                1, normalised.getUniqueConstraints().size());
        assertEquals("The normalised schema with one PK and one Unique should contain one not null constraint",
                1, normalised.getNotNullConstraints().size());
    }
    
    @Test
    public void testSchemaWithPKAndUniqueNormalised() {
        Schema original = createSchemaWithPKAndUnique();
        Schema normalised = norm.normalise(original);
        assertTrue("The normalised schema should be equivalent to a manually normalised schema",
                new SchemaEquivalenceChecker().areEquivalent(normalised, createSchemaWithPKNormalised()));
    }
    
    @Test
    public void testSchemaWithPKAndUniqueAndNotNullNotSame() {
        Schema original = createSchemaWithPKAndUniqueAndNotNull();
        Schema normalised = norm.normalise(original);
        assertTrue("Normaliser should always return a copy of the schema",
                original != normalised);
        assertFalse("The original and normalised schema should not be equivalent if the schema contains a PK",
                new SchemaEquivalenceChecker().areEquivalent(original, normalised));
    }
    
    @Test
    public void testSchemaWithPKAndUniqueAndNotNullConstraintCounts() {
        Schema original = createSchemaWithPKAndUniqueAndNotNull();
        Schema normalised = norm.normalise(original);
        assertEquals("The normalised schema should contain no primary key constraints",
                0, normalised.getPrimaryKeyConstraints().size());
        assertEquals("The normalised schema with one PK and one Unique and one Not Null should contain one unique constraint",
                1, normalised.getUniqueConstraints().size());
        assertEquals("The normalised schema with one PK and one Unique and one Not Null should contain one not null constraint",
                1, normalised.getNotNullConstraints().size());
    }
    
    @Test
    public void testSchemaWithPKAndUniqueAndNotNullNormalised() {
        Schema original = createSchemaWithPKAndUniqueAndNotNull();
        Schema normalised = norm.normalise(original);
        assertTrue("The normalised schema should be equivalent to a manually normalised schema",
                new SchemaEquivalenceChecker().areEquivalent(normalised, createSchemaWithPKNormalised()));
    }
    
    @Test
    public void testSchemaWithCheck() {
        Schema original = createSchemaWithNotNull();
        Schema normalised = norm.normalise(original);
        assertTrue("Normaliser should always return a copy of the schema",
                original != normalised);
        assertFalse("The original and normalised schema should not be equivalent if the schema contains a check",
                new SchemaEquivalenceChecker().areEquivalent(original, normalised));
        assertEquals("The normalised schema should contain no primary key constraints",
                0, normalised.getPrimaryKeyConstraints().size());
        assertEquals("The normalised schema should contain no check constraints",
                0, normalised.getCheckConstraints().size());
         assertEquals("The normalised schema with one Not Null check should contain one not null constraint",
                1, normalised.getNotNullConstraints().size());
    }
    
}
