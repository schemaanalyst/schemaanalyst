/*
 */
package org.schemaanalyst.unittest.mutation.operator;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.PKCColumnARE;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 *
 * @author Chris J. Wright
 */
public class TestPKCColumnARE {

    /**
     * Schema with single column PK and no spare columns
     */
    @SuppressWarnings("serial")
    private class SchemaSinglePKNoExchange extends Schema {

        public SchemaSinglePKNoExchange(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            t1.addColumn(c1);
            this.addTable(t1);
            setPrimaryKeyConstraint(new PrimaryKeyConstraint(t1, c1));
        }
    }

    /**
     * Schema with single column PK and two spare columns
     */
    @SuppressWarnings("serial")
    private class SchemaSinglePKTwoExchange extends Schema {

        public SchemaSinglePKTwoExchange(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            Column c2 = new Column("c2", new IntDataType());
            Column c3 = new Column("c3", new IntDataType());
            t1.addColumn(c1);
            t1.addColumn(c2);
            t1.addColumn(c3);
            this.addTable(t1);
            setPrimaryKeyConstraint(new PrimaryKeyConstraint(t1, c1));
        }
    }

    /**
     * Schema with double column PK and no spare columns
     */
    @SuppressWarnings("serial")
    private class SchemaDoublePKNoExchange extends Schema {

        public SchemaDoublePKNoExchange(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            Column c2 = new Column("c2", new IntDataType());
            t1.addColumn(c1);
            t1.addColumn(c2);
            this.addTable(t1);
            setPrimaryKeyConstraint(new PrimaryKeyConstraint(t1, c1, c2));
        }
    }

    /**
     * Schema with double column PK and two spare columns
     */
    @SuppressWarnings("serial")    
    private class SchemaDoublePKTwoExchange extends Schema {

        public SchemaDoublePKTwoExchange(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            Column c2 = new Column("c2", new IntDataType());
            Column c3 = new Column("c3", new IntDataType());
            Column c4 = new Column("c4", new IntDataType());
            t1.addColumn(c1);
            t1.addColumn(c2);
            t1.addColumn(c3);
            t1.addColumn(c4);
            this.addTable(t1);
            setPrimaryKeyConstraint(new PrimaryKeyConstraint(t1, c1, c2));
        }
    }
    
    /**
     * Schema with two tables, both with PKs
     */
    @SuppressWarnings("serial")
    private class SchemaTwoTable extends Schema {

        public SchemaTwoTable(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            Column c2 = new Column("c2", new IntDataType());
            t1.addColumn(c1);
            t1.addColumn(c2);
            this.addTable(t1);
            setPrimaryKeyConstraint(new PrimaryKeyConstraint(t1, c1));
            Table t2 = new Table("t2");
            Column c3 = new Column("c3", new IntDataType());
            Column c4 = new Column("c4", new IntDataType());
            t2.addColumn(c3);
            t2.addColumn(c4);
            this.addTable(t2);
            setPrimaryKeyConstraint(new PrimaryKeyConstraint(t2, c3));
        }
    }

    @Test
    public void testSinglePKNoExchange() {
        Schema schema = new SchemaSinglePKNoExchange("schema");
        List<Mutant<Schema>> mutants = new PKCColumnARE(schema).mutate();
        assertEquals("ARE should produce 1 mutant for SinglePKNoExchange", 1, mutants.size());
        Set<Schema> mutantSet = new HashSet<>();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            assertNotSame("Mutation should create a new object", schema, mutantSchema);
            assertNotEquals("Mutant should make a change to the schema", schema, mutantSchema);
            assertTrue("No equivalent mutants should be produced", mutantSet.add(mutantSchema));
        }
    }

    @Test
    public void testSinglePKTwoExchange() {
        Schema schema = new SchemaSinglePKTwoExchange("schema");
        List<Mutant<Schema>> mutants = new PKCColumnARE(schema).mutate();
        assertEquals("ARE should produce 5 mutants for SinglePKTwoExchange", 5, mutants.size());
        Set<Schema> mutantSet = new HashSet<>();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            assertNotSame("Mutation should create a new object", schema, mutantSchema);
            assertNotEquals("Mutant should make a change to the schema", schema, mutantSchema);
            assertTrue("No equivalent mutants should be produced", mutantSet.add(mutantSchema));
        }
    }
    
    @Test
    public void testDoublePKNoExchange() {
        Schema schema = new SchemaDoublePKNoExchange("schema");
        List<Mutant<Schema>> mutants = new PKCColumnARE(schema).mutate();
        assertEquals("ARE should produce 2 mutants for DoublePKNoExchange", 2, mutants.size());
        Set<Schema> mutantSet = new HashSet<>();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            assertNotSame("Mutation should create a new object", schema, mutantSchema);
            assertNotEquals("Mutant should make a change to the schema", schema, mutantSchema);
            assertTrue("No equivalent mutants should be produced", mutantSet.add(mutantSchema));
        }
    }
    
    @Test
    public void testDoublePKTwoExchange() {
        Schema schema = new SchemaDoublePKTwoExchange("schema");
        List<Mutant<Schema>> mutants = new PKCColumnARE(schema).mutate();
        assertEquals("ARE should produce 8 mutants for DoublePKTwoExchange", 8, mutants.size());
        Set<Schema> mutantSet = new HashSet<>();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            assertNotSame("Mutation should create a new object", schema, mutantSchema);
            assertNotEquals("Mutant should make a change to the schema", schema, mutantSchema);
            assertTrue("No equivalent mutants should be produced", mutantSet.add(mutantSchema));
        }
    }
    
    @Test
    public void testTwoTables() {
        Schema schema = new SchemaTwoTable("schema");
        List<Mutant<Schema>> mutants = new PKCColumnARE(schema).mutate();
        assertEquals("ARE should produce 6 mutants for TestSchemaTwoTable", 6, mutants.size());
        Set<Schema> mutantSet = new HashSet<>();
        for (Mutant<Schema> mutant : mutants) {
            Schema mutantSchema = mutant.getMutatedArtefact();
            assertNotSame("Mutation should create a new object", schema, mutantSchema);
            assertNotEquals("Mutant should make a change to the schema", schema, mutantSchema);
            assertTrue("No equivalent mutants should be produced", mutantSet.add(mutantSchema));
        }
    }
}
