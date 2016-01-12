/*
 */
package org.schemaanalyst.unittest.mutation.operator;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.FKCColumnPairR;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Chris J. Wright
 */
public class TestFKCColumnPairR {

    @SuppressWarnings("serial")
    private class SchemaBase extends Schema {

        Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new IntDataType());
        Table t2 = new Table("t2");
        Column d = new Column("d", new IntDataType());
        Column e = new Column("e", new IntDataType());
        Column f = new Column("f", new IntDataType());

        public SchemaBase(String name) {
            super(name);
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

        public SchemaNoFK(String name) {
            super(name);
        }
    }

    @Test
    public void testSchemaNoFKMutantNumber() {
        SchemaNoFK schemaNoFK = new SchemaNoFK("schema");
        List<Mutant<Schema>> schemaNoFKMutants = new FKCColumnPairR(schemaNoFK).mutate();
        assertEquals("No mutants should be produced for a schema with no FKs",
                0, schemaNoFKMutants.size());
        assertEquals("The original schema given to the operator should be unchanged",
                new SchemaNoFK("schema"), schemaNoFK);
    }

    @SuppressWarnings("serial")
    private class SchemaOneColFK extends SchemaBase {

        public SchemaOneColFK(String name) {
            super(name);
            addForeignKeyConstraint(new ForeignKeyConstraint(t1, a, t2, d));
        }
    }
    SchemaOneColFK schemaOneColFK;
    List<Mutant<Schema>> schemaOneColFKMutants;

    public void initSchemaOneFK() {
        schemaOneColFK = new SchemaOneColFK("schema");
        schemaOneColFKMutants = new FKCColumnPairR(schemaOneColFK).mutate();
    }

    @Test
    public void testSchemaOneColFKMutantNumber() {
        initSchemaOneFK();
        assertEquals("One mutant should be produced for a schema with one FK "
                + "that contains one column pair", 1, schemaOneColFKMutants.size());
        assertEquals("The original schema given to the operator should be unchanged",
                new SchemaOneColFK("schema"), schemaOneColFK);
    }

    @Test
    public void testSchemaOneColFKMutant1() {
        initSchemaOneFK();
        Schema mutant = schemaOneColFKMutants.get(0).getMutatedArtefact();
        assertEquals("The first mutant produced for a schema with one FK that "
                + "contains one column pair should contain no FKs",
                0, mutant.getForeignKeyConstraints().size());
    }

    @SuppressWarnings("serial")
    private class SchemaTwoColFK extends SchemaBase {

        public SchemaTwoColFK(String name) {
            super(name);
            List<Column> t1Cols = Arrays.asList(a, b);
            List<Column> t2Cols = Arrays.asList(d, e);
            addForeignKeyConstraint(new ForeignKeyConstraint(t1, t1Cols, t2, t2Cols));
        }
    }
    SchemaTwoColFK schemaTwoColFK;
    List<Mutant<Schema>> schemaTwoColFKMutants;

    public void initSchemaTwoColFK() {
        schemaTwoColFK = new SchemaTwoColFK("schema");
        schemaTwoColFKMutants = new FKCColumnPairR(schemaTwoColFK).mutate();
    }

    @Test
    public void testSchemaTwoColFKMutantNumber() {
        initSchemaTwoColFK();
        assertEquals("Two mutants should be produced for a schema with one FK "
                + "that contains two column pairs", 2, schemaTwoColFKMutants.size());
        assertEquals("The original schema given to the operator should be unchanged",
                new SchemaTwoColFK("schema"), schemaTwoColFK);
    }

    @Test
    public void testSchemaTwoColFKMutantNumber1() {
        initSchemaTwoColFK();
        Schema mutant = schemaTwoColFKMutants.get(0).getMutatedArtefact();
        assertEquals("The first mutant produced for a schema with one FK that "
                + "contains two column pairs should contain one FK",
                1, mutant.getForeignKeyConstraints().size());
        Table t1 = mutant.getTable("t1");
        Column b = t1.getColumn("b");
        Table t2 = mutant.getTable("t2");
        Column e = t2.getColumn("e");
        ForeignKeyConstraint expected = new ForeignKeyConstraint(t1, b, t2, e);
        assertEquals("The first mutant produced for a schema with one FK that "
                + "contains two column pairs should contain one FK with the "
                + "first column pair removed",
                expected, mutant.getForeignKeyConstraints().get(0));
    }

    @Test
    public void testSchemaTwoColFKMutantNumber2() {
        initSchemaTwoColFK();
        Schema mutant = schemaTwoColFKMutants.get(1).getMutatedArtefact();
        assertEquals("The second mutant produced for a schema with one FK that "
                + "contains two column pairs should contain one FK",
                1, mutant.getForeignKeyConstraints().size());
        Table t1 = mutant.getTable("t1");
        Column a = t1.getColumn("a");
        Table t2 = mutant.getTable("t2");
        Column d = t2.getColumn("d");
        ForeignKeyConstraint expected = new ForeignKeyConstraint(t1, a, t2, d);
        assertEquals("The second mutant produced for a schema with one FK that "
                + "contains two column pairs should contain one FK with the "
                + "second column pair removed",
                expected, mutant.getForeignKeyConstraints().get(0));
    }
}
