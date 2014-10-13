/*
 */
package org.schemaanalyst.unittest.mutation.operator;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.UCColumnARE;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Chris J. Wright
 */
public class TestUCColumnARE {

    private class SchemaNoUniqueC extends Schema {

        Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new IntDataType());

        public SchemaNoUniqueC() {
            super("schema");
            t1.addColumn(a);
            t1.addColumn(b);
            t1.addColumn(c);
            addTable(t1);
        }
    }
    SchemaNoUniqueC schemaNoUniqueC;
    List<Mutant<Schema>> schemaNoUniqueCMutants;

    private void initSchemaNoUniqueC() {
        schemaNoUniqueC = new SchemaNoUniqueC();
        schemaNoUniqueCMutants = new UCColumnARE(schemaNoUniqueC).mutate();
    }

    @Test
    public void testSchemaNoUniqueCMutantNumber() {
        initSchemaNoUniqueC();
        assertEquals("Mutating a 3 column schema with no UNIQUE constraints "
                + "should produce 3 mutants", 3, schemaNoUniqueCMutants.size());
    }

    public void testSchemaNoUniqueCMutant(int mutantNumber, Column column) {
        Schema mutant = schemaNoUniqueCMutants.get(mutantNumber).getMutatedArtefact();
        assertEquals("Mutant " + mutantNumber + " of a 3 column schema with no "
                + "UNIQUE constraints should have 1 unique constraint", 1,
                mutant.getUniqueConstraints().size());
        UniqueConstraint constraint = mutant.getUniqueConstraints().get(0);
        assertEquals("The unique constraint of mutant " + mutantNumber + " "
                + "should contain a single column", 1,
                constraint.getNumColumns());
        assertEquals("The unique constraint of mutant " + mutantNumber + " "
                + "should apply to column " + column.getName(), column,
                constraint.getColumns().get(0));
    }

    @Test
    public void testSchemaNoUniqueCMutant1() {
        initSchemaNoUniqueC();
        testSchemaNoUniqueCMutant(0, schemaNoUniqueC.a);
    }

    @Test
    public void testSchemaNoUniqueCMutant2() {
        initSchemaNoUniqueC();
        testSchemaNoUniqueCMutant(1, schemaNoUniqueC.b);
    }

    @Test
    public void testSchemaNoUniqueCMutant3() {
        initSchemaNoUniqueC();
        testSchemaNoUniqueCMutant(2, schemaNoUniqueC.c);
    }

    private class SchemaOneUniqueC extends Schema {

        Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new IntDataType());

        public SchemaOneUniqueC() {
            super("schema");
            t1.addColumn(a);
            t1.addColumn(b);
            t1.addColumn(c);
            addTable(t1);
            addUniqueConstraint(new UniqueConstraint(t1, a));
        }
    }
    SchemaOneUniqueC schemaOneUniqueC;
    List<Mutant<Schema>> schemaOneUniqueCMutants;

    private void initSchemaOneUniqueC() {
        schemaOneUniqueC = new SchemaOneUniqueC();
        schemaOneUniqueCMutants = new UCColumnARE(schemaOneUniqueC).mutate();
    }

    @Test
    public void testSchemaOneUniqueCMutants() {
        initSchemaOneUniqueC();
        assertEquals("Mutating a 3 column schema with one UNIQUE constraint "
                + "should produce 7 mutants", 7, schemaOneUniqueCMutants.size());
        Set<Mutant<Schema>> mutants = new HashSet<>();
        for (Mutant<Schema> mutant : schemaOneUniqueCMutants) {
            assertTrue("No syntactically equivalent mutants should be produced",
                    mutants.add(mutant));
        }
        assertEquals("Mutating a 3 column schema with one UNIQUE constraint "
                + "should produce 7 mutants that are not syntactically equivalent",
                7, mutants.size());
    }
}
