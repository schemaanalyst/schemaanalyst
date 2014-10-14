/*
 */
package org.schemaanalyst.unittest.mutation.redundancy;

import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceChecker;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.mutation.redundancy.EquivalentMutantRemover;
import org.schemaanalyst.mutation.redundancy.RedundantMutantRemover;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Chris J. Wright
 */
public class TestRedundancyRemovers {

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
        Mutant<Schema> mutant1 = new Mutant<>(instance1, "");
        Mutant<Schema> mutant2 = new Mutant<>(instance2, "");
        List<Mutant<Schema>> list = new ArrayList<>();
        list.add(mutant1);
        list.add(mutant2);
        assertEquals("Prior to removal, the input list should contain two items", 2, list.size());
        MutantRemover<Schema> reducer1 = new EquivalentMutantRemover<>(new SchemaEquivalenceChecker(), original);
        MutantRemover<Schema> reducer2 = new RedundantMutantRemover<>(new SchemaEquivalenceChecker());
        List<Mutant<Schema>> reducedList = reducer1.removeMutants(reducer2.removeMutants(list));
        assertEquals("The list returned by when provided two mutants equal to "
                + "the original should be empty", 0, reducedList.size());
    }

    @Test
    public void TestSameSchemaOneWithChanges() {
        SchemaA original = new SchemaA();
        SchemaA instance1 = new SchemaA();
        SchemaA instance2 = new SchemaA();
        instance1.addNotNullConstraint(new NotNullConstraint(instance1.t1, instance1.a));
        Mutant<Schema> mutant1 = new Mutant<>((Schema) instance1, "mutant1");
        Mutant<Schema> mutant2 = new Mutant<>((Schema) instance2, "mutant2");
        List<Mutant<Schema>> list = new ArrayList<>();
        list.add(mutant1);
        list.add(mutant2);
        assertEquals("Prior to removal, the input list should contain two items", 2, list.size());
        MutantRemover<Schema> mEquivO = new EquivalentMutantRemover<>(new SchemaEquivalenceChecker(), original);
        MutantRemover<Schema> mEquivM = new RedundantMutantRemover<>(new SchemaEquivalenceChecker());
        List<Mutant<Schema>> reducedList = mEquivM.removeMutants(list);
        reducedList = mEquivO.removeMutants(reducedList);
        assertEquals("The list returned after removal when provided one "
                + "mutant equal to the original and one not equal should "
                + "contain one item", 1, reducedList.size());
        assertTrue("The first item of the list returned by the remover when "
                + "provided one mutant equal to the original and one not equal "
                + "should be the latter mutant", mutant1 == reducedList.get(0));
    }
    
    @Test
    public void TestSameSchemaTwoWithSameChanges() {
        SchemaA original = new SchemaA();
        SchemaA instance1 = new SchemaA();
        SchemaA instance2 = new SchemaA();
        instance1.addNotNullConstraint(new NotNullConstraint(instance1.t1, instance1.a));
        Mutant<Schema> mutant1 = new Mutant<>((Schema) instance1, "");
        Mutant<Schema> mutant2 = new Mutant<>((Schema) instance2, "");
        List<Mutant<Schema>> list = new ArrayList<>();
        list.add(mutant1);
        list.add(mutant2);
        assertEquals("Prior to removal, the input list should contain two items", 2, list.size());
        MutantRemover<Schema> reducer1 = new EquivalentMutantRemover<>(new SchemaEquivalenceChecker(), original);
        MutantRemover<Schema> reducer2 = new RedundantMutantRemover<>(new SchemaEquivalenceChecker());
        List<Mutant<Schema>> reducedList = reducer1.removeMutants(reducer2.removeMutants(list));
        assertEquals("The list returned after removal when provided two "
                + "mutants different to the original but equal to "
                + "each other should contain one item", 1, reducedList.size());
    }
    
    @Test
    public void TestUniqueOrderEquality() {
        SchemaA original = new SchemaA();
        SchemaA instance1 = new SchemaA();
        original.addUniqueConstraint(new UniqueConstraint(original.t1, original.a, original.b));
        instance1.addUniqueConstraint(new UniqueConstraint(instance1.t1, original.b, original.a));
        MutantRemover<Schema> reducer1 = new EquivalentMutantRemover<>(new SchemaEquivalenceChecker(), original);
        MutantRemover<Schema> reducer2 = new RedundantMutantRemover<>(new SchemaEquivalenceChecker());
        List<Mutant<Schema>> list = new ArrayList<>();
        list.add(new Mutant<>((Schema) instance1, ""));
        List<Mutant<Schema>> reducedList = reducer1.removeMutants(reducer2.removeMutants(list));
        assertEquals("The reduced list should contain no mutants",
                0, reducedList.size());
    }
}
