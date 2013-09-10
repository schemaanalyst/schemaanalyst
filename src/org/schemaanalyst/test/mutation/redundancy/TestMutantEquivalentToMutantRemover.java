/*
 */
package org.schemaanalyst.test.mutation.redundancy;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.redundancy.MutantEquivalentToMutantRemover;
import org.schemaanalyst.mutation.redundancy.SchemaEquivalenceTester;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

/**
 *
 * @author Chris J. Wright
 */
public class TestMutantEquivalentToMutantRemover {

    @Test
    public void testEmptyList() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        MutantEquivalentToMutantRemover remover = new MutantEquivalentToMutantRemover(new SchemaEquivalenceTester());
        List result = remover.removeMutants(mutants);
        assertTrue("MEM removal on an empty list should result in an empty list", result.isEmpty());
    }

    @Test
    public void testOneItemList() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Mutant m1 = new Mutant(s1, "m1");
        mutants.add(m1);
        MutantEquivalentToMutantRemover remover = new MutantEquivalentToMutantRemover(new SchemaEquivalenceTester());
        List result = remover.removeMutants(mutants);
        assertTrue("MEM removal on a list with one item should result in a list"
                + " with one item", result.size() == 1);
    }

    @Test
    public void testMultipleNonEquivalentItemList() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        s1.addTable(new Table("firstTable"));
        Mutant m1 = new Mutant(s1, "m1");
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        s1.addTable(new Table("secondTable"));
        Mutant m2 = new Mutant(s2, "m1");
        Schema s3 = new Schema("s");
        Table t3 = s3.createTable("t");
        Column a3 = t3.createColumn("a", new IntDataType());
        s3.addTable(new Table("thirdTable"));
        Mutant m3 = new Mutant(s3, "m1");
        mutants.add(m1);
        mutants.add(m2);
        mutants.add(m3);
        MutantEquivalentToMutantRemover remover = new MutantEquivalentToMutantRemover(new SchemaEquivalenceTester());
        List result = remover.removeMutants(mutants);
        assertTrue("MEM removal on a list with three non-equivalent items "
                + "should result in a list of three items", result.size() == 3);
    }
    
    @Test
    public void testMultipleMixedItemList() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        Schema s1 = new Schema("s");
        Table t1 = s1.createTable("t");
        Column a1 = t1.createColumn("a", new IntDataType());
        Mutant m1 = new Mutant(s1, "m1");
        s1.addNotNullConstraint(new NotNullConstraint(t1, a1));
        
        Schema s2 = new Schema("s");
        Table t2 = s2.createTable("t");
        Column a2 = t2.createColumn("a", new IntDataType());
        Mutant m2 = new Mutant(s2, "m1");
        s2.addNotNullConstraint(new NotNullConstraint(t2, a2));
        
        Schema s3 = new Schema("s");
        Table t3 = s3.createTable("t");
        Column a3 = t3.createColumn("a", new IntDataType());
        Mutant m3 = new Mutant(s3, "m1");
        
        mutants.add(m1);
        mutants.add(m2);
        mutants.add(m3);
        MutantEquivalentToMutantRemover remover = new MutantEquivalentToMutantRemover(new SchemaEquivalenceTester());
        List result = remover.removeMutants(mutants);
        assertTrue("MEM removal on a list with 2 equivalent items and one "
                + "non-equivalent item should result in a list of three items",
                result.size() == 2);
    }
}
