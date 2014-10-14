package org.schemaanalyst.unittest.testgeneration;

import org.junit.Test;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import parsedcasestudy.UnixUsage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 28/07/2014.
 */
public class TestTestSuiteGenerator {

    public class TestSuiteGeneratorMock extends TestSuiteGenerator {

        public TestSuiteGeneratorMock(Schema schema) {
            super(schema, null, new ValueFactory(), null);
        }

        public Predicate addLinkedTableRowsToData(Data data, Predicate predicate, Table table) {
            return super.addLinkedTableRowsToData(data, predicate, table);
        }

        public boolean areRefColsUnique(Predicate predicate, ForeignKeyConstraint foreignKeyConstraint) {
            return super.areRefColsUnique(predicate, foreignKeyConstraint);
        }
    }

    public class TestSchema extends Schema {

        Table t1, t2, t3, t4;
        Column t1c, t2c, t3c, t4c1, t4c2;
        ForeignKeyConstraint fk1, fk2, fk3;

        public TestSchema() {
            super("TestSchema");

            t1 = this.createTable("t1");
            t1c = t1.createColumn("id", new CharDataType(6));
            this.createPrimaryKeyConstraint(t1, t1c);

            t2 = this.createTable("t2");
            t2c = t2.createColumn("id", new CharDataType(6));
            this.createPrimaryKeyConstraint(t2, t2c);
            fk1 = this.createForeignKeyConstraint(t2, t2c, t1, t1c);

            t3 = this.createTable("t3");
            t3c = t3.createColumn("id", new CharDataType(6));
            this.createPrimaryKeyConstraint(t3, t3c);

            t4 = this.createTable("t4");
            t4c1 = t4.createColumn("id1", new CharDataType(6));
            t4c2 = t4.createColumn("id2", new CharDataType(6));
            this.createUniqueConstraint(t4, t4c1);
            this.createUniqueConstraint(t4, t4c2);
            fk2 = this.createForeignKeyConstraint(t4, t4c1, t1, t1c);
            fk3 = this.createForeignKeyConstraint(t4, t4c2, t3, t3c);

        }
    }

    @Test
    public void testOneFK() {
        TestSchema testSchema = new TestSchema();
        TestSuiteGeneratorMock tsg = new TestSuiteGeneratorMock(testSchema);

        Predicate predicate = PredicateGenerator.generatePredicate(testSchema.getConstraints(testSchema.t2));
        assertTrue(tsg.areRefColsUnique(predicate, testSchema.fk1));

        Data data = new Data();
        tsg.addLinkedTableRowsToData(data, predicate, testSchema.t2);
        assertEquals(1, data.getNumRows(testSchema.t1));
    }

    @Test
    public void testTwoFlatFKs() {
        TestSchema testSchema = new TestSchema();
        TestSuiteGeneratorMock tsg = new TestSuiteGeneratorMock(testSchema);

        Predicate predicate = PredicateGenerator.generatePredicate(testSchema.getConstraints(testSchema.t4));
        assertTrue(tsg.areRefColsUnique(predicate, testSchema.fk2));
        assertTrue(tsg.areRefColsUnique(predicate, testSchema.fk3));

        Data data = new Data();
        tsg.addLinkedTableRowsToData(data, predicate, testSchema.t4);
        assertEquals(1, data.getNumRows(testSchema.t1));
        assertEquals(1, data.getNumRows(testSchema.t3));
    }

    @Test
    public void testUnixUsage() {
        Schema s = new UnixUsage();
        Table t = s.getTable("TRANSCRIPT");
        TestSuiteGeneratorMock tsg = new TestSuiteGeneratorMock(s);

        Predicate predicate = PredicateGenerator.generatePredicate(s.getConstraints(t));
        System.out.println(predicate);

        Data data = new Data();
        tsg.addLinkedTableRowsToData(data, predicate, t);
        System.out.println("DATA is " + data);
    }
}
