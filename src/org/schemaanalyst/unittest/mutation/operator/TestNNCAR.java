/*
 */
package org.schemaanalyst.unittest.mutation.operator;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.NNCAR;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Chris J. Wright
 */
public class TestNNCAR {

    private class SchemaNoNN extends Schema {

        Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new IntDataType());
        Table t2 = new Table("t2");
        Column d = new Column("d", new IntDataType());
        Column e = new Column("e", new IntDataType());
        Column f = new Column("f", new IntDataType());

        public SchemaNoNN() {
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
    SchemaNoNN schemaNoNN;
    List<Mutant<Schema>> schemaNoNNmutants;

    private void initSchemaNoNN() {
        schemaNoNN = new SchemaNoNN();
        schemaNoNNmutants = new NNCAR(schemaNoNN).mutate();
    }

    @Test
    public void testSchemaNoNNMutantNumber() {
        initSchemaNoNN();
        assertEquals("Mutating a schema with 6 columns and no NOT NULL "
                + "constraints should produce 6 mutants", 6, schemaNoNNmutants.size());
    }

    public void testSchemaNoNNMutant(int mutantNumber, Column col) {
        Schema mutant = schemaNoNNmutants.get(mutantNumber).getMutatedArtefact();
        assertEquals("The mutant of a schema with no NOT NULL constraints "
                + "should have 1 NOT NULL constraint", 1, mutant.getNotNullConstraints().size());
        assertEquals("In mutant " + mutantNumber + " of a schema with no NOT NULL "
                + "constraints, the new NOT NULL constraint should be on "
                + "column " + col.getName(),
                col, mutant.getNotNullConstraints().get(0).getColumn());
    }

    @Test
    public void testSchemaNoNNMutant1() {
        initSchemaNoNN();
        testSchemaNoNNMutant(0, schemaNoNN.a);
    }

    @Test
    public void testSchemaNoNNMutant2() {
        initSchemaNoNN();
        testSchemaNoNNMutant(1, schemaNoNN.b);
    }

    @Test
    public void testSchemaNoNNMutant3() {
        initSchemaNoNN();
        testSchemaNoNNMutant(2, schemaNoNN.c);
    }

    @Test
    public void testSchemaNoNNMutant4() {
        initSchemaNoNN();
        testSchemaNoNNMutant(3, schemaNoNN.d);
    }

    @Test
    public void testSchemaNoNNMutant5() {
        initSchemaNoNN();
        testSchemaNoNNMutant(4, schemaNoNN.e);
    }

    @Test
    public void testSchemaNoNNMutant6() {
        initSchemaNoNN();
        testSchemaNoNNMutant(5, schemaNoNN.f);
    }

    private class SchemaOneNN extends Schema {

        Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new IntDataType());
        Table t2 = new Table("t2");
        Column d = new Column("d", new IntDataType());
        Column e = new Column("e", new IntDataType());
        Column f = new Column("f", new IntDataType());

        public SchemaOneNN() {
            super("schema");
            t1.addColumn(a);
            t1.addColumn(b);
            t1.addColumn(c);
            t2.addColumn(d);
            t2.addColumn(e);
            t2.addColumn(f);
            addTable(t1);
            addTable(t2);
            addNotNullConstraint(new NotNullConstraint(t1, a));
        }
    }
    SchemaOneNN schemaOneNN;
    List<Mutant<Schema>> schemaOneNNmutants;

    private void initSchemaOneNN() {
        schemaOneNN = new SchemaOneNN();
        schemaOneNNmutants = new NNCAR(schemaOneNN).mutate();
    }

    @Test
    public void testSchemaOneNNMutantNumber() {
        initSchemaOneNN();
        assertEquals("Mutating a schema with 6 columns and one NOT NULL "
                + "constraints should produce 6 mutants", 6, schemaOneNNmutants.size());
    }

    public void testSchemaOneNNMutant(int mutantNumber, Column c1, Column c2) {
        Schema mutant = schemaOneNNmutants.get(mutantNumber).getMutatedArtefact();
        assertEquals("Mutant " + mutantNumber + " of a schema with one NOT NULL"
                + " constraint should have 2 NOT NULL constraints", 2,
                mutant.getNotNullConstraints().size());
        assertEquals("In mutant " + mutantNumber + " of a schema with one NOT "
                + "NULL constraint, the first NOT NULL should be on column "
                + c1.getName(), c1, mutant.getNotNullConstraints().get(0).getColumn());
        assertEquals("In mutant " + mutantNumber + " of a schema with one NOT "
                + "NULL constraint, the second NOT NULL should be on column "
                + c2.getName(), c2, mutant.getNotNullConstraints().get(1).getColumn());
    }

    @Test
    public void testSchemaOneNNMutant1() {
        initSchemaOneNN();
        assertEquals("The first mutant of a schema with no NOT NULL constraints "
                + "should have no NOT NULL constraints", 0,
                schemaOneNNmutants.get(0).getMutatedArtefact().getNotNullConstraints().size());
    }
    
    @Test
    public void testSchemaOneNNMutant2() {
        initSchemaOneNN();
        testSchemaOneNNMutant(1, schemaOneNN.a, schemaOneNN.b);
    }
    
    @Test
    public void testSchemaOneNNMutant3() {
        initSchemaOneNN();
        testSchemaOneNNMutant(2, schemaOneNN.a, schemaOneNN.c);
    }
    
    @Test
    public void testSchemaOneNNMutant4() {
        initSchemaOneNN();
        testSchemaOneNNMutant(3, schemaOneNN.a, schemaOneNN.d);
    }
    
    @Test
    public void testSchemaOneNNMutant5() {
        initSchemaOneNN();
        testSchemaOneNNMutant(4, schemaOneNN.a, schemaOneNN.e);
    }
    
    @Test
    public void testSchemaOneNNMutant6() {
        initSchemaOneNN();
        testSchemaOneNNMutant(5, schemaOneNN.a, schemaOneNN.f);
    }
}
