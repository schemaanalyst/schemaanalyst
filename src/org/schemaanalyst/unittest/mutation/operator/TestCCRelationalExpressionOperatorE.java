/*
 */
package org.schemaanalyst.unittest.mutation.operator;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.CCRelationalExpressionOperatorE;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.*;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Chris J. Wright
 */
public class TestCCRelationalExpressionOperatorE {

    @SuppressWarnings("serial")
    private class SchemaNoRelational extends Schema {

        public SchemaNoRelational(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            t1.addColumn(c1);
            this.addTable(t1);
            Expression expr = new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(new ConstantExpression(new NumericValue(1)), new ConstantExpression(new NumericValue(2))), false);
            this.addCheckConstraint(new CheckConstraint(t1, expr));
        }
    }
    Schema schemaNoRelational;
    List<Mutant<Schema>> schemaNoRelationalMutants;

    public void initSchemaNoRelational() {
        schemaNoRelational = new SchemaNoRelational("schema");
        schemaNoRelationalMutants = new CCRelationalExpressionOperatorE(schemaNoRelational).mutate();
    }

    @Test
    public void testSchemaNoRelationalMutantNumber() {
        initSchemaNoRelational();
        assertEquals("Mutating a schema with no constraints with relational "
                + "expressions should produce 0 mutants", 0, schemaNoRelationalMutants.size());
    }

    @SuppressWarnings("serial")
    private class SchemaOneRelational extends Schema {

        public SchemaOneRelational(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            t1.addColumn(c1);
            this.addTable(t1);
            Expression expr = new RelationalExpression(
                    new ColumnExpression(t1, c1),
                    RelationalOperator.LESS,
                    new ConstantExpression(new NumericValue(5)));
            this.addCheckConstraint(new CheckConstraint(t1, expr));
        }
    }
    Schema schemaOneRelational;
    List<Mutant<Schema>> schemaOneRelationalMutants;

    private void initSchemaOneRelational() {
        schemaOneRelational = new SchemaOneRelational("schema");
        schemaOneRelationalMutants = new CCRelationalExpressionOperatorE(schemaOneRelational).mutate();
    }

    @Test
    public void testSchemaOneRelationalMutantNumber() {
        initSchemaOneRelational();
        assertEquals("Mutating a schema with one constraint with one relational"
                + " expression should produce 5 mutants", 5, schemaOneRelationalMutants.size());
    }

    public void testSchemaOneRelationalMutant(int mutantNumber, RelationalOperator op) {
        initSchemaOneRelational();
        Schema mutant = schemaOneRelationalMutants.get(mutantNumber).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expr = new RelationalExpression(
                new ColumnExpression(t1, c1),
                op,
                new ConstantExpression(new NumericValue(5)));
        assertEquals("Mutating a schema with one check constraint should produce"
                + "a mutant with one check constraint",
                1, mutant.getCheckConstraints().size());
        assertEquals("The mutated expression in mutant " + mutantNumber
                + " should replace < with " + op,
                expr, mutant.getCheckConstraints().get(0).getExpression());
    }

    @Test
    public void testSchemaOneRelationalMutant1() {
        testSchemaOneRelationalMutant(0, RelationalOperator.EQUALS);
    }

    @Test
    public void testSchemaOneRelationalMutant2() {
        testSchemaOneRelationalMutant(1, RelationalOperator.NOT_EQUALS);
    }

    @Test
    public void testSchemaOneRelationalMutant3() {
        testSchemaOneRelationalMutant(2, RelationalOperator.GREATER);
    }

    @Test
    public void testSchemaOneRelationalMutant4() {
        testSchemaOneRelationalMutant(3, RelationalOperator.GREATER_OR_EQUALS);
    }

    @Test
    public void testSchemaOneRelationalMutant5() {
        testSchemaOneRelationalMutant(4, RelationalOperator.LESS_OR_EQUALS);
    }

    @SuppressWarnings("serial")
    private class SchemaTwoRelational extends Schema {

        public SchemaTwoRelational(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            t1.addColumn(c1);
            this.addTable(t1);
            Expression expr = new AndExpression(
                    new RelationalExpression(
                    new ColumnExpression(t1, c1),
                    RelationalOperator.LESS,
                    new ConstantExpression(new NumericValue(5))),
                    new RelationalExpression(
                    new ColumnExpression(t1, c1),
                    RelationalOperator.GREATER_OR_EQUALS,
                    new ConstantExpression(new NumericValue(0))));
            this.addCheckConstraint(new CheckConstraint(t1, expr));
        }
    }
    Schema schemaTwoRelational;
    List<Mutant<Schema>> schemaTwoRelationalMutants;

    private void initSchemaTwoRelational() {
        schemaTwoRelational = new SchemaTwoRelational("schema");
        schemaTwoRelationalMutants = new CCRelationalExpressionOperatorE(schemaTwoRelational).mutate();
    }

    @Test
    public void testSchemaTwoRelationalMutantNumber() {
        initSchemaTwoRelational();
        assertEquals("Mutating a schema with one constraint with two relational"
                + " expressions should produce 10 mutants", 10, schemaTwoRelationalMutants.size());
    }

    public void testSchemaTwoRelationalMutant(int mutantNumber, RelationalOperator op1, RelationalOperator op2) {
        initSchemaTwoRelational();
        Schema mutant = schemaTwoRelationalMutants.get(mutantNumber).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expr = new AndExpression(
                new RelationalExpression(
                new ColumnExpression(t1, c1),
                op1,
                new ConstantExpression(new NumericValue(5))),
                new RelationalExpression(
                new ColumnExpression(t1, c1),
                op2,
                new ConstantExpression(new NumericValue(0))));
        assertEquals("Mutating a schema with one check constraint should produce"
                + "a mutant with one check constraint",
                1, mutant.getCheckConstraints().size());
        assertEquals("The mutated expression in mutant " + mutantNumber + " should "
                + "have a first operator of " + op1 + " and a second operator of " + op2,
                expr, mutant.getCheckConstraints().get(0).getExpression());
    }

    @Test
    public void testSchemaTwoRelationalMutant1() {
        testSchemaTwoRelationalMutant(0, RelationalOperator.EQUALS, RelationalOperator.GREATER_OR_EQUALS);
    }

    @Test
    public void testSchemaTwoRelationalMutant2() {
        testSchemaTwoRelationalMutant(1, RelationalOperator.NOT_EQUALS, RelationalOperator.GREATER_OR_EQUALS);
    }

    @Test
    public void testSchemaTwoRelationalMutant3() {
        testSchemaTwoRelationalMutant(2, RelationalOperator.GREATER, RelationalOperator.GREATER_OR_EQUALS);
    }

    @Test
    public void testSchemaTwoRelationalMutant4() {
        testSchemaTwoRelationalMutant(3, RelationalOperator.GREATER_OR_EQUALS, RelationalOperator.GREATER_OR_EQUALS);
    }

    @Test
    public void testSchemaTwoRelationalMutant5() {
        testSchemaTwoRelationalMutant(4, RelationalOperator.LESS_OR_EQUALS, RelationalOperator.GREATER_OR_EQUALS);
    }

    @Test
    public void testSchemaTwoRelationalMutant6() {
        testSchemaTwoRelationalMutant(5, RelationalOperator.LESS, RelationalOperator.EQUALS);
    }

    @Test
    public void testSchemaTwoRelationalMutant7() {
        testSchemaTwoRelationalMutant(6, RelationalOperator.LESS, RelationalOperator.NOT_EQUALS);
    }

    @Test
    public void testSchemaTwoRelationalMutant8() {
        testSchemaTwoRelationalMutant(7, RelationalOperator.LESS, RelationalOperator.GREATER);
    }

    @Test
    public void testSchemaTwoRelationalMutant9() {
        testSchemaTwoRelationalMutant(8, RelationalOperator.LESS, RelationalOperator.LESS);
    }

    @Test
    public void testSchemaTwoRelationalMutant10() {
        testSchemaTwoRelationalMutant(9, RelationalOperator.LESS, RelationalOperator.LESS_OR_EQUALS);
    }
}
