package org.schemaanalyst.test.mutation.operator;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.FKCColumnPairA;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DoubleDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestFKCColumnPairA {

    @SuppressWarnings("serial")
    private class SchemaBase extends Schema {

        Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new DoubleDataType());
        Table t2 = new Table("t2");
        Column d = new Column("d", new IntDataType());
        Column e = new Column("e", new IntDataType());
        Column f = new Column("f", new DoubleDataType());

        public SchemaBase() {
            super("schema");
            t1.addColumn(a);
            t1.addColumn(b);
            t1.addColumn(c);
            t2.addColumn(d);
            t2.addColumn(e);
            t2.addColumn(f);
            addTable(t1);
            addTable(t2);
        }
    }

    @SuppressWarnings("serial")
    private class SchemaNoFK extends SchemaBase {

        public SchemaNoFK() {
            super();
        }
    }

    @SuppressWarnings("serial")
    private class SchemaOneColFK extends SchemaBase {

        public SchemaOneColFK() {
            super();
            addForeignKeyConstraint(new ForeignKeyConstraint(t1, a, t2, d));
        }
    }

    @SuppressWarnings("serial")
    private class SchemaTwoColFK extends SchemaBase {

        public SchemaTwoColFK() {
            super();
            List<Column> t1Cols = Arrays.asList(a, b);
            List<Column> t2Cols = Arrays.asList(d, e);
            addForeignKeyConstraint(new ForeignKeyConstraint(t1, t1Cols, t2, t2Cols));
        }
    }

    @Test
    public void testNoFKey() {
        SchemaNoFK schemaNoFK = new SchemaNoFK();
        FKCColumnPairA adder = new FKCColumnPairA(schemaNoFK);
        List<Mutant<Schema>> mutants = adder.mutate();
        assertEquals("Mutating a schema with no foreign keys should produce no "
                + "mutants", 0, mutants.size());
    }

    @Test
    public void testOneColFKey() {
        SchemaOneColFK schemaOneColFK = new SchemaOneColFK();
        FKCColumnPairA adder = new FKCColumnPairA(schemaOneColFK);
        List<Mutant<Schema>> mutants = adder.mutate();
        assertEquals("Mutating a schema with one foreign key should produce 2 "
                + "mutants", 2, mutants.size());
    }
    
    @Test
    public void testTwoColFKey() {
        SchemaTwoColFK schemaTwoColFK = new SchemaTwoColFK();
        FKCColumnPairA adder = new FKCColumnPairA(schemaTwoColFK);
        List<Mutant<Schema>> mutants = adder.mutate();
        assertEquals("Mutating a schema with one foreign key with two column "
                + "pairs should produce 1 mutant", 1, mutants.size());
    }

}
