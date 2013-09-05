/*
 */
package org.schemaanalyst.test.mutation.equivalence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.redundancy.GeneralRedundantMutantRemover;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

/**
 *
 * @author Chris J. Wright
 */
public class TestGeneralEquivalenceReducer {

    {
        assertTrue(true);
    }

    private class SchemaA extends Schema {

        public Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new IntDataType());
        Table t2 = new Table("t2");
        Column d = new Column("d", new IntDataType());
        Column e = new Column("e", new IntDataType());
        Column f = new Column("f", new IntDataType());

        public SchemaA() {
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

    @Test
    public void TestSameSchemaNoChanges() {
        Schema original = new SchemaA();
        Schema instance1 = new SchemaA();
        Schema instance2 = new SchemaA();
        assertEquals("Two unchanged instances of the same schema should be "
                + "equal", original, instance1);
        assertEquals("Two unchanged instances of the same schema should be "
                + "equal", instance1, instance2);
        Mutant<Schema> mutant1 = new Mutant<>(instance1, "");
        Mutant<Schema> mutant2 = new Mutant<>(instance2, "");
        List<Mutant<Schema>> list = new ArrayList<>();
        list.add(mutant1);
        list.add(mutant2);
        assertEquals("Prior to GeneralEquivalenceReducer usage, the input list "
                + "should contain two items", 2, list.size());
        GeneralRedundantMutantRemover<Schema> reducer = new GeneralRedundantMutantRemover<>(original);
        List<Mutant<Schema>> reducedList = reducer.removeMutants(list);
        assertEquals("After GeneralEquivalenceReducer usage, the original list "
                + "should be unchanged in length", 2, list.size());
        assertTrue("After GeneralEquivalenceReducer usage, the first item in "
                + "the original list should be the same object", mutant1 == list.get(0));
        assertTrue("After GeneralEquivalenceReducer usage, the second item in "
                + "the original list should be the same object", mutant2 == list.get(1));
        assertEquals("The list returned by the GeneralEquivalenceReducer when "
                + "provided two mutants equal to the original should be empty",
                0, reducedList.size());
    }

    @Test
    public void TestSameSchemaOneWithChanges() {
        SchemaA original = new SchemaA();
        SchemaA instance1 = new SchemaA();
        SchemaA instance2 = new SchemaA();
        instance1.addNotNullConstraint(new NotNullConstraint(instance1.t1, instance1.a));
        assertNotEquals("A changed schema should not be equal the the original",
                original, instance1);
        Mutant<Schema> mutant1 = new Mutant<>((Schema) instance1, "");
        Mutant<Schema> mutant2 = new Mutant<>((Schema) instance2, "");
        List<Mutant<Schema>> list = new ArrayList<>();
        list.add(mutant1);
        list.add(mutant2);
        assertEquals("Prior to GeneralEquivalenceReducer usage, the input list "
                + "should contain two items", 2, list.size());
        GeneralRedundantMutantRemover<Schema> reducer = new GeneralRedundantMutantRemover<>((Schema)original);
        List<Mutant<Schema>> reducedList = reducer.removeMutants(list);
        assertEquals("After GeneralEquivalenceReducer usage, the original list "
                + "should be unchanged in length", 2, list.size());
        assertEquals("The list returned by the GeneralEquivalenceReducer when "
                + "provided one mutant equal to the original and one not equal "
                + "should contain one item", 1, reducedList.size());
        assertTrue("The first item of the list returned by the reducer when "
                + "provided one mutant equal to the original and one not equal "
                + "should be the latter mutant", mutant1 == reducedList.get(0));
    }
    
    @Test
    public void TestSameSchemaTwoWithSameChanges() {
        SchemaA original = new SchemaA();
        SchemaA instance1 = new SchemaA();
        SchemaA instance2 = new SchemaA();
        instance1.addNotNullConstraint(new NotNullConstraint(instance1.t1, instance1.a));
        assertNotEquals("A changed schema should not be equal to the original",
                original, instance1);
        instance2.addNotNullConstraint(new NotNullConstraint(instance2.t1, instance1.a));
        assertNotEquals("A changed schema should not be equal to the original",
                original, instance2);
        assertEquals("Two schemas with the same change should be equal to each "
                + "other", instance1, instance2);
        Mutant<Schema> mutant1 = new Mutant<>((Schema) instance1, "");
        Mutant<Schema> mutant2 = new Mutant<>((Schema) instance2, "");
        List<Mutant<Schema>> list = new ArrayList<>();
        list.add(mutant1);
        list.add(mutant2);
        assertEquals("Prior to GeneralEquivalenceReducer usage, the input list "
                + "should contain two items", 2, list.size());
        GeneralRedundantMutantRemover<Schema> reducer = new GeneralRedundantMutantRemover<>((Schema)original);
        List<Mutant<Schema>> reducedList = reducer.removeMutants(list);
        assertEquals("After GeneralEquivalenceReducer usage, the original list "
                + "should be unchanged in length", 2, list.size());
        assertEquals("The list returned by the GeneralEquivalenceReducer when "
                + "provided two mutants different to the original but equal to "
                + "each other should contain one item", 1, reducedList.size());
    }
    
    @Test
    public void TestUniqueOrderEquality() {
        SchemaA original = new SchemaA();
        SchemaA instance1 = new SchemaA();
        original.addUniqueConstraint(new UniqueConstraint(original.t1, original.a, original.b));
        instance1.addUniqueConstraint(new UniqueConstraint(instance1.t1, original.a, original.b));
        GeneralRedundantMutantRemover<Schema> reducer = new GeneralRedundantMutantRemover<>((Schema)original);
        List<Mutant<Schema>> list = new ArrayList<>();
        list.add(new Mutant<>((Schema) instance1, ""));
        List<Mutant<Schema>> reducedList = reducer.removeMutants(list);
        assertEquals("The reduced list should contain no mutants",
                0, reducedList.size());
    }
}
