/*
 */
package org.schemaanalyst.unittest.mutation.operator;

import java.util.List;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.UCColumnE;

/**
 *
 * @author Chris J. Wright
 */
public class TestUCColumnE {

    @SuppressWarnings("serial")
    private class SchemaBase extends Schema {

        Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new IntDataType());
        Column d = new Column("d", new IntDataType());

        public SchemaBase() {
            super("schema");
            t1.addColumn(a);
            t1.addColumn(b);
            t1.addColumn(c);
            t1.addColumn(d);
            addTable(t1);
        }
    }

    @SuppressWarnings("serial")
    private class SchemaNoUC extends SchemaBase {

        public SchemaNoUC() {
            super();
        }
    }
    
    @SuppressWarnings("serial")
    private class SchemaOneColUC extends SchemaBase {

        public SchemaOneColUC() {
            super();
            addUniqueConstraint(new UniqueConstraint(t1, a));
        }
    }
    
    @SuppressWarnings("serial")
    private class SchemaTwoColUC extends SchemaBase {

        public SchemaTwoColUC() {
            super();
            addUniqueConstraint(new UniqueConstraint(t1, a, b));
        }
    }
    
    @SuppressWarnings("serial")
    private class SchemaThreeColUC extends SchemaBase {

        public SchemaThreeColUC() {
            super();
            addUniqueConstraint(new UniqueConstraint(t1, a, b, c));
        }
    }
    
    @SuppressWarnings("serial")
    private class SchemaTwoOneColUC extends SchemaBase {

        public SchemaTwoOneColUC() {
            super();
            addUniqueConstraint(new UniqueConstraint(t1, a));
            addUniqueConstraint(new UniqueConstraint(t1, b));
        }
    }
    
    @Test
    public void testSchemaNoUC() {
        SchemaNoUC schema = new SchemaNoUC();
        List<Mutant<Schema>> mutants = new UCColumnE(schema).mutate();
        assertEquals("No mutants should be produced for a schema with no UCs",
                0, mutants.size());
    }
    
    @Test
    public void testSchemaOneColUC() {
        SchemaOneColUC schema = new SchemaOneColUC();
        List<Mutant<Schema>> mutants = new UCColumnE(schema).mutate();
        assertEquals("The UCColumnE operator should produce 3 mutants for a "
                + "schema with 1 one column UC", 3, mutants.size());
        // Mutant 1
        Schema m1 = mutants.get(0).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m1.getUniqueConstraints().size(), 1);
        assertArrayEquals("The first mutant produced should refer to the first "
                + "alternative column", new Column[] {schema.b}, m1.getUniqueConstraints().get(0).getColumns().toArray());
        // Mutant 2
        Schema m2 = mutants.get(1).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m2.getUniqueConstraints().size(), 1);
        assertArrayEquals("The second mutant produced should refer to the second "
                + "alternative column", new Column[] {schema.c}, m2.getUniqueConstraints().get(0).getColumns().toArray());
        // Mutant 3
        Schema m3 = mutants.get(2).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m3.getUniqueConstraints().size(), 1);
        assertArrayEquals("The third mutant produced should refer to the third "
                + "alternative column", new Column[] {schema.d}, m3.getUniqueConstraints().get(0).getColumns().toArray());
    }
    
    @Test
    public void testSchemaTwoColUC() {
        SchemaTwoColUC schema = new SchemaTwoColUC();
        List<Mutant<Schema>> mutants = new UCColumnE(schema).mutate();
        assertEquals("The UCColumnE operator should produce 4 mutants for a "
                + "schema with 1 two column UCs", 4, mutants.size());
        // Mutant 1
        Schema m1 = mutants.get(0).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m1.getUniqueConstraints().size(), 1);
        assertArrayEquals(new Column[] {schema.c,schema.b}, m1.getUniqueConstraints().get(0).getColumns().toArray());
        // Mutant 2
        Schema m2 = mutants.get(1).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m2.getUniqueConstraints().size(), 1);
        assertArrayEquals(new Column[] {schema.d,schema.b}, m2.getUniqueConstraints().get(0).getColumns().toArray());
        // Mutant 3
        Schema m3 = mutants.get(2).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m3.getUniqueConstraints().size(), 1);
        assertArrayEquals(new Column[] {schema.a,schema.c}, m3.getUniqueConstraints().get(0).getColumns().toArray());
        // Mutant 4
        Schema m4 = mutants.get(3).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m4.getUniqueConstraints().size(), 1);
        assertArrayEquals(new Column[] {schema.a,schema.d}, m4.getUniqueConstraints().get(0).getColumns().toArray());
    }
    
    @Test
    public void testSchemaThreeColUC() {
        SchemaThreeColUC schema = new SchemaThreeColUC();
        List<Mutant<Schema>> mutants = new UCColumnE(schema).mutate();
        assertEquals("The UCColumnE operator should produce 3 mutants for a "
                + "schema with 1 three column UCs", 3, mutants.size());
        // Mutant 1
        Schema m1 = mutants.get(0).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m1.getUniqueConstraints().size(), 1);
        assertArrayEquals(new Column[] {schema.d,schema.b,schema.c}, m1.getUniqueConstraints().get(0).getColumns().toArray());
        // Mutant 2
        Schema m2 = mutants.get(1).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m2.getUniqueConstraints().size(), 1);
        assertArrayEquals(new Column[] {schema.a,schema.d,schema.c}, m2.getUniqueConstraints().get(0).getColumns().toArray());
        // Mutant 3
        Schema m3 = mutants.get(2).getMutatedArtefact();
        assertEquals("The UCColumnE operator should not increase the number of "
                + "unique constraints", m3.getUniqueConstraints().size(), 1);
        assertArrayEquals(new Column[] {schema.a,schema.b,schema.d}, m3.getUniqueConstraints().get(0).getColumns().toArray());
    }
    
    @Test
    public void testSchemaTwoOneColUC() {
        SchemaTwoOneColUC schema = new SchemaTwoOneColUC();
        List<Mutant<Schema>> mutants = new UCColumnE(schema).mutate();
        assertEquals("The UCColumnE operator should produce 6 mutants for a "
                + "schema with 2 one column UCs", 6, mutants.size());
//        for (Mutant<Schema> mutant : mutants) {
//            System.out.println(org.schemaanalyst.util.ReflectiveToString.toString(mutant.getMutatedArtefact()));
//            for (String stmt : new SQLWriter().writeCreateTableStatements(mutant.getMutatedArtefact())) {
//                System.out.println(stmt);
//            }
//        }
//        // Mutant 1
//        Schema m1 = mutants.get(0).getMutatedArtefact();
//        assertEquals("The UCColumnE operator should not increase the number of "
//                + "unique constraints", m1.getUniqueConstraints().size(), 2);
//        assertArrayEquals(new Column[] {schema.b}, m1.getUniqueConstraints().get(0).getColumns().toArray());
//        assertArrayEquals(new Column[] {schema.b}, m1.getUniqueConstraints().get(1).getColumns().toArray());
//        System.out.println(org.schemaanalyst.util.ReflectiveToString.toString(mutants.get(0).getMutatedArtefact()));
//        // IS THIS A PROBLEM?
    }
}
