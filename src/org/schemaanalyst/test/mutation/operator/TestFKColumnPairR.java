/*
 */
package org.schemaanalyst.test.mutation.operator;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.FKColumnPairR;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

/**
 *
 * @author Chris J. Wright
 */
public class TestFKColumnPairR {

    private class TestSchemaBase extends Schema {

        Table t1 = new Table("t1");
        Column a = new Column("a", new IntDataType());
        Column b = new Column("b", new IntDataType());
        Column c = new Column("c", new IntDataType());
        Table t2 = new Table("t2");
        Column d = new Column("d", new IntDataType());
        Column e = new Column("e", new IntDataType());
        Column f = new Column("f", new IntDataType());

        public TestSchemaBase(String name) {
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
    
    private class TestSchemaNoFK extends TestSchemaBase {

        public TestSchemaNoFK(String name) {
            super(name);
        }
        
    }
    TestSchemaNoFK schemaNoFK = new TestSchemaNoFK("schema");
    List<Mutant<Schema>> schemaNoFKMutants = new FKColumnPairR(schemaNoFK).mutate();
    
    private class TestSchemaOneColFK extends TestSchemaBase {

        public TestSchemaOneColFK(String name) {
            super(name);
            addForeignKeyConstraint(new ForeignKeyConstraint(t1, a, t2, d));
        }
    }
    TestSchemaOneColFK schemaOneColFK = new TestSchemaOneColFK("schema");
    List<Mutant<Schema>> schemaOneColFKMutants = new FKColumnPairR(schemaOneColFK).mutate();
    
    private class TestSchemaTwoColFK extends TestSchemaBase {

        public TestSchemaTwoColFK(String name) {
            super(name);
            List<Column> t1Cols = Arrays.asList(new Column[] {a, b});
            List<Column> t2Cols = Arrays.asList(new Column[] {d, e});
            addForeignKeyConstraint(new ForeignKeyConstraint(t1, t1Cols, t2, t2Cols));
        }
    }
    TestSchemaTwoColFK schemaTwoColFK = new TestSchemaTwoColFK("schema");
    List<Mutant<Schema>> schemaTwoColFKMutants = new FKColumnPairR(schemaTwoColFK).mutate();
    
    @Test
    public void testSchemaNoFKMutantNumber() {
        assertEquals("No mutants should be produced for a schema with no FKs",
                0, schemaNoFKMutants.size());
        assertEquals("The original schema given to the operator should be unchanged",
                new TestSchemaNoFK("schema"), schemaNoFK);
    }
    
    @Test
    public void testSchemaOneColFKMutantNumber() {
        assertEquals("One mutant should be produced for a schema with one FK "
                + "that contains one column pair", 1, schemaOneColFKMutants.size());
        assertEquals("The original schema given to the operator should be unchanged",
                new TestSchemaOneColFK("schema"), schemaOneColFK);
    }
    
    @Test
    public void testSchemaTwoColFKMutantNumber() {
        assertEquals("Two mutants should be produced for a schema with one FK "
                + "tha contains two column pairs", 2, schemaTwoColFKMutants.size());
        assertEquals("The original schema given to the operator should be unchanged",
                new TestSchemaTwoColFK("schema"), schemaTwoColFK);
    }
    
}
