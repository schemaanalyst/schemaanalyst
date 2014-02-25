/*
 */

package org.schemaanalyst.test.mutation.operator;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.FKCColumnPairE;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DoubleDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

public class TestFKCColumnPairE {
    
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
    public void dummy() {
        assertTrue(true);
    }
    
    
    @Test
    public void testNoFKey() {
        SchemaNoFK schemaNoFK = new SchemaNoFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaNoFK);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with no foreign keys should produce no "
                + "mutants", 0, mutants.size());
    }
    
    @Test
    public void testOneColFKey() {
        SchemaOneColFK schemaOneColFK = new SchemaOneColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaOneColFK);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with one foreign key should produce 4 "
                + "mutants", 4, mutants.size());
    }
    
    @Test
    public void testTwoColFKey() {
        SchemaTwoColFK schemaTwoColFK = new SchemaTwoColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaTwoColFK);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with two foreign key columns should "
                + "produce 6 mutants", 6, mutants.size());
    }
    
}
