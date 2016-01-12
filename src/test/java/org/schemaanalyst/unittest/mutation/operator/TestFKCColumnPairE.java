/*
 */
package org.schemaanalyst.unittest.mutation.operator;

import java.util.Arrays;
import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.FKCColumnPairE;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DoubleDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.tuple.Pair;

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
    private class SchemaOneColFKNoAlternatives extends SchemaBase {

        public SchemaOneColFKNoAlternatives() {
            super();
            addForeignKeyConstraint(new ForeignKeyConstraint(t1, c, t2, f));
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

    @SuppressWarnings("serial")
    private class SchemaThreeColFKNoAlternatives extends SchemaBase {

        public SchemaThreeColFKNoAlternatives() {
            super();
            List<Column> t1Cols = Arrays.asList(a, b, c);
            List<Column> t2Cols = Arrays.asList(d, e, f);
            addForeignKeyConstraint(new ForeignKeyConstraint(t1, t1Cols, t2, t2Cols));
        }
    }

    @SuppressWarnings("serial")
    private class SchemaThreeColFK extends Schema {

        Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new IntDataType());
        Table t2 = new Table("t2");
        Column d = new Column("d", new IntDataType());
        Column e = new Column("e", new IntDataType());
        Column f = new Column("f", new IntDataType());

        public SchemaThreeColFK() {
            super("schema");

            t1.addColumn(a);
            t1.addColumn(b);
            t1.addColumn(c);
            t2.addColumn(d);
            t2.addColumn(e);
            t2.addColumn(f);
            addTable(t1);
            addTable(t2);

            List<Column> t1Cols = Arrays.asList(a, b, c);
            List<Column> t2Cols = Arrays.asList(d, e, f);
            addForeignKeyConstraint(new ForeignKeyConstraint(t1, t1Cols, t2, t2Cols));
        }
    }

    @Test
    public void testNoFKey() {
        SchemaNoFK schemaNoFK = new SchemaNoFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaNoFK, true);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with no foreign keys should produce no "
                + "mutants when types are on", 0, mutants.size());

        exchanger = new FKCColumnPairE(schemaNoFK, false);
        mutants = exchanger.mutate();
        assertEquals("Mutating a schema with no foreign keys should produce no "
                + "mutants when types are off", 0, mutants.size());
    }

    @Test
    public void testOneColFKeyTypesOn() {
        SchemaOneColFK schemaOneColFK = new SchemaOneColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaOneColFK, true);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with one foreign key should produce 4 "
                + "mutants when types are off", 2, mutants.size());

        for (Mutant<Schema> mutant : mutants) {
            Schema schema = mutant.getMutatedArtefact();
            List<ForeignKeyConstraint> fks = schema.getForeignKeyConstraints();
            assertEquals("Mutating a schema with one foreign key constraint "
                    + "should produce mutants that only contain one foreign "
                    + "key constraint", 1, fks.size());
            ForeignKeyConstraint fk = fks.get(0);
            List<Pair<Column>> pairs = fk.getColumnPairs();
            assertTrue("Mutating a schema with one foreign key column "
                    + "should produce mutants that have at most one foreign "
                    + "key column (the number of columns cannot increase): "
                    + "\n Value " + pairs.size() + " > 1 for \n"
                    + mutant.getDescription() + "\n", pairs.size() <= 1);
        }
    }

    @Test
    public void testOneColFKeyTypesOff() {
        SchemaOneColFK schemaOneColFK = new SchemaOneColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaOneColFK, false);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with one foreign key should produce 2 "
                + "mutants", 4, mutants.size());

        for (Mutant<Schema> mutant : mutants) {
            Schema schema = mutant.getMutatedArtefact();
            List<ForeignKeyConstraint> fks = schema.getForeignKeyConstraints();
            assertEquals("Mutating a schema with one foreign key constraint "
                    + "should produce mutants that only contain one foreign "
                    + "key constraint", 1, fks.size());
            ForeignKeyConstraint fk = fks.get(0);
            List<Pair<Column>> pairs = fk.getColumnPairs();
            assertTrue("Mutating a schema with one foreign key column "
                    + "should produce mutants that have at most one foreign "
                    + "key column (the number of columns cannot increase): "
                    + "\n Value " + pairs.size() + " > 1 for \n"
                    + mutant.getDescription() + "\n", pairs.size() <= 1);
        }
    }

    @Test
    public void testOneColFKeyMutantsTypesOn() {
        SchemaOneColFK schemaOneColFK = new SchemaOneColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaOneColFK, true);
        List<Mutant<Schema>> mutants = exchanger.mutate();

        // Check mutant 1
        Mutant<Schema> m1 = mutants.get(0);
        ForeignKeyConstraint m1fk = m1.getMutatedArtefact().getForeignKeyConstraints().get(0);
        assertEquals("The mutant of an FK should refer to the same local table",
                "t1", m1fk.getTable().getName());
        assertEquals("The mutant of an FK should refer to the same reference table",
                "t2", m1fk.getReferenceTable().getName());
        assertEquals("The first FK mutant should refer to local column a",
                "a", m1fk.getColumns().get(0).getName());
        assertEquals("The first FK mutant should refer to reference column e",
                "e", m1fk.getReferenceColumns().get(0).getName());

        // Check mutant 2
        Mutant<Schema> m2 = mutants.get(1);
        ForeignKeyConstraint m2fk = m2.getMutatedArtefact().getForeignKeyConstraints().get(0);
        assertEquals("The mutant of an FK should refer to the same local table",
                "t1", m2fk.getTable().getName());
        assertEquals("The mutant of an FK should refer to the same reference table",
                "t2", m2fk.getReferenceTable().getName());
        assertEquals("The second FK mutant should refer to local column b",
                "b", m2fk.getColumns().get(0).getName());
        assertEquals("The second FK mutant should refer to reference column d",
                "d", m2fk.getReferenceColumns().get(0).getName());
    }

    @Test
    public void testOneColFKeyNoAlternativesTypesOn() {
        SchemaOneColFKNoAlternatives schemaOneColFK = new SchemaOneColFKNoAlternatives();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaOneColFK, true);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with one foreign key but no alternative "
                + "columns should produce 0 mutants", 0, mutants.size());
    }

    @Test
    public void testOneColFKeyAlternativesTypesOff() {
        SchemaOneColFKNoAlternatives schemaOneColFK = new SchemaOneColFKNoAlternatives();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaOneColFK, false);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with one foreign key but no alternative "
               + "columns should produce 4 mutants", 4, mutants.size());
    }

    @Test
    public void testTwoColFKeyTypesOn() {
        SchemaTwoColFK schemaTwoColFK = new SchemaTwoColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaTwoColFK, true);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with two foreign key columns should "
                + "produce 2 mutants", 2, mutants.size());
        for (Mutant<Schema> mutant : mutants) {
            Schema schema = mutant.getMutatedArtefact();
            List<ForeignKeyConstraint> fks = schema.getForeignKeyConstraints();
            assertEquals("Mutating a schema with one foreign key constraint "
                    + "should produce mutants that only contain one foreign "
                    + "key constraint", 1, fks.size());
            ForeignKeyConstraint fk = fks.get(0);
            List<Pair<Column>> pairs = fk.getColumnPairs();
            assertTrue("Mutating a schema with two foreign key columns "
                    + "should produce mutants that have at most two foreign "
                    + "key columns (the number of columns cannot increase): "
                    + "\n Value " + pairs.size() + " > 2 for \n"
                    + mutant.getDescription() + "\n", pairs.size() <= 2);
        }
    }

    @Test
    public void testTwoColFKeyTypesOff() {
        SchemaTwoColFK schemaTwoColFK = new SchemaTwoColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaTwoColFK, false);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with two foreign key columns should "
                + "produce 6 mutants when types are off", 6, mutants.size());
        for (Mutant<Schema> mutant : mutants) {
            Schema schema = mutant.getMutatedArtefact();
            List<ForeignKeyConstraint> fks = schema.getForeignKeyConstraints();
            assertEquals("Mutating a schema with one foreign key constraint "
                    + "should produce mutants that only contain one foreign "
                    + "key constraint", 1, fks.size());
            ForeignKeyConstraint fk = fks.get(0);
            List<Pair<Column>> pairs = fk.getColumnPairs();
            assertTrue("Mutating a schema with two foreign key columns "
                    + "should produce mutants that have at most two foreign "
                    + "key columns (the number of columns cannot increase): "
                    + "\n Value " + pairs.size() + " > 2 for \n"
                    + mutant.getDescription() + "\n", pairs.size() <= 2);
        }
    }

    @Test
    public void testTwoColFKeyMutantsTypesOn() {
        SchemaTwoColFK schemaTwoColFK = new SchemaTwoColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaTwoColFK, true);
        List<Mutant<Schema>> mutants = exchanger.mutate();

        // Check mutant 1
        Mutant<Schema> m1 = mutants.get(0);
        ForeignKeyConstraint m1fk = m1.getMutatedArtefact().getForeignKeyConstraints().get(0);
        assertEquals("The mutant of an FK should refer to the same local table",
                "t1", m1fk.getTable().getName());
        assertEquals("The mutant of an FK should refer to the same reference table",
                "t2", m1fk.getReferenceTable().getName());
        assertEquals("The first FK mutant should refer to local column a",
                "a", m1fk.getColumns().get(0).getName());
        assertEquals("The first FK mutant should refer to reference column e",
                "e", m1fk.getReferenceColumns().get(0).getName());

        // Check mutant 2
        Mutant<Schema> m2 = mutants.get(1);
        ForeignKeyConstraint m2fk = m2.getMutatedArtefact().getForeignKeyConstraints().get(0);
        assertEquals("The mutant of an FK should refer to the same local table",
                "t1", m2fk.getTable().getName());
        assertEquals("The mutant of an FK should refer to the same reference table",
                "t2", m2fk.getReferenceTable().getName());
        assertEquals("The first FK mutant should refer to local column b",
                "b", m2fk.getColumns().get(0).getName());
        assertEquals("The first FK mutant should refer to reference column d",
                "d", m2fk.getReferenceColumns().get(0).getName());
    }

    @Test
    public void testSchemaThreeColFKNoAlternativesTypesOn() {
        SchemaThreeColFKNoAlternatives schemaThreeColFK = new SchemaThreeColFKNoAlternatives();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaThreeColFK, true);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with three foreign key columns but no "
                + "alternative columns should produce 2 mutants when types are on", 2, mutants.size());
    }

    @Test
    public void testSchemaThreeColFKNoAlternativesTypesOff() {
        SchemaThreeColFKNoAlternatives schemaThreeColFK = new SchemaThreeColFKNoAlternatives();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaThreeColFK, false);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with three foreign key columns "
                + "alternative columns should produce 6 mutants when types are off", 6, mutants.size());

        for (Mutant<Schema> mutant : mutants) {
            System.out.println(mutant);
            System.out.println(new SQLWriter().writeCreateTableStatements(mutant.getMutatedArtefact()));
        }
    }

    @Test
    public void testSchemaThreeColFKTypesOn() {
        SchemaThreeColFK schemaThreeColFK = new SchemaThreeColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaThreeColFK, true);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with three foreign key columns should "
                + "produce 6 mutants", 6, mutants.size());
    }

    @Test
    public void testSchemaThreeColFK() {
        SchemaThreeColFK schemaThreeColFK = new SchemaThreeColFK();
        FKCColumnPairE exchanger = new FKCColumnPairE(schemaThreeColFK, false);
        List<Mutant<Schema>> mutants = exchanger.mutate();
        assertEquals("Mutating a schema with three foreign key columns should "
                + "produce 6 mutants when types are on", 6, mutants.size());

        exchanger = new FKCColumnPairE(schemaThreeColFK, false);
        mutants = exchanger.mutate();
        assertEquals("Mutating a schema with three foreign key columns should "
                + "produce 6 mutants when types are off", 6, mutants.size());
    }
}
