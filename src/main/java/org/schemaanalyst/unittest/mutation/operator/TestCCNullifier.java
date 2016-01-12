/*
 */
package org.schemaanalyst.unittest.mutation.operator;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.CCNullifier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Chris J. Wright
 */
public class TestCCNullifier {

    @SuppressWarnings("serial")
    private class SchemaNoConstraints extends Schema {

        public SchemaNoConstraints(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            t1.addColumn(c1);
            this.addTable(t1);
        }
    }

    @Test
    public void testSchemaNoConstraintsMutantNumber() {
        List<Mutant<Schema>> mutants = new CCNullifier(new SchemaNoConstraints("schema")).mutate();
        assertEquals("No mutants should be produced for a schema with no checks",
                0, mutants.size());
    }

    @SuppressWarnings("serial")
    private class SchemaOneConstraint extends Schema {

        public SchemaOneConstraint(String name) {
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
    SchemaOneConstraint oneConstraintSchema;
    List<Mutant<Schema>> oneConstraintSchemaMutants;

    private void initOneConstraintSchema() {
        oneConstraintSchema = new SchemaOneConstraint("schema");
        oneConstraintSchemaMutants = new CCNullifier(oneConstraintSchema).mutate();
    }

    @Test
    public void testSchemaOneConstraintMutantNumber() {
        initOneConstraintSchema();
        assertEquals("One mutant should be produced for a schema with one check",
                1, oneConstraintSchemaMutants.size());
    }

    @Test
    public void testSchemaOneConstraintMutant1() {
        initOneConstraintSchema();
        assertEquals("The first mutant of a schema with one constraint should "
                + "have no constraints remaining",
                0, oneConstraintSchemaMutants.get(0).getMutatedArtefact().getCheckConstraints().size());
    }

    @SuppressWarnings("serial")
    private class SchemaTwoConstraints extends Schema {

        public SchemaTwoConstraints(String name) {
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
            Expression expr2 = new RelationalExpression(
                    new ColumnExpression(t1, c1),
                    RelationalOperator.GREATER,
                    new ConstantExpression(new NumericValue(10)));
            this.addCheckConstraint(new CheckConstraint(t1, expr2));
        }
    }
    SchemaTwoConstraints twoConstraintSchema;
    List<Mutant<Schema>> twoConstraintSchemaMutants;

    public void initTwoConstraintSchema() {
        twoConstraintSchema = new SchemaTwoConstraints("schema");
        twoConstraintSchemaMutants = new CCNullifier(twoConstraintSchema).mutate();
    }

    @Test
    public void testSchemaTwoConstraintMutantNumber() {
        initTwoConstraintSchema();
        assertEquals("Two mutants should be produced for a schema with two "
                + "checks",
                2, twoConstraintSchemaMutants.size());
    }

    @Test
    public void testSchemaTwoConstraintsMutant1() {
        initTwoConstraintSchema();
        Schema mutant = twoConstraintSchemaMutants.get(0).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expr = new RelationalExpression(
                new ColumnExpression(t1, c1),
                RelationalOperator.GREATER,
                new ConstantExpression(new NumericValue(10)));
        assertEquals("The first mutant of a schema with two constraints should "
                + "contain one check", 1, mutant.getCheckConstraints(t1).size());
        assertEquals("The expression of the first check in the first mutant "
                + "should be the expression of the second check constraint",
                expr, mutant.getCheckConstraints(t1).get(0).getExpression());
    }

    @Test
    public void testSchemaTwoConstraintsMutant2() {
        initTwoConstraintSchema();
        Schema mutant = twoConstraintSchemaMutants.get(1).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expr = new RelationalExpression(
                new ColumnExpression(t1, c1),
                RelationalOperator.LESS,
                new ConstantExpression(new NumericValue(5)));
        assertEquals("The second mutant of a schema with two constraints should "
                + "contain one check", 1, mutant.getCheckConstraints(t1).size());
        assertEquals("The expression of the first check in the second mutant"
                + "should be the expression of the first check constraint",
                expr, mutant.getCheckConstraints(t1).get(0).getExpression());
    }

    @SuppressWarnings("serial")
    private class SchemaTwoTables extends Schema {

        public SchemaTwoTables(String name) {
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
            Table t2 = new Table("t2");
            Column c2 = new Column("c2", new IntDataType());
            t2.addColumn(c2);
            this.addTable(t2);
            Expression expr2 = new RelationalExpression(
                    new ColumnExpression(t2, c2),
                    RelationalOperator.GREATER,
                    new ConstantExpression(new NumericValue(10)));
            this.addCheckConstraint(new CheckConstraint(t2, expr2));
        }
    }
    SchemaTwoTables twoTablesSchema;
    List<Mutant<Schema>> twoTablesSchemaMutants;

    public void initTwoTablesSchema() {
        twoTablesSchema = new SchemaTwoTables("schema");
        twoTablesSchemaMutants = new CCNullifier(twoTablesSchema).mutate();
    }

    @Test
    public void testSchemaTwoTablesMutantNumber() {
        initTwoTablesSchema();
        assertEquals("Two mutants should be produced for a schema with two "
                + "tables that each have one constraint",
                2, twoTablesSchemaMutants.size());
    }

    @Test
    public void testSchemaTwoTablesMutantOne() {
        initTwoTablesSchema();
        Schema mutant = twoTablesSchemaMutants.get(0).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Table t2 = mutant.getTable("t2");
        Column c2 = t2.getColumn("c2");
        Expression expr = new RelationalExpression(
                new ColumnExpression(t2, c2),
                RelationalOperator.GREATER,
                new ConstantExpression(new NumericValue(10)));
        assertEquals("The first mutant of a schema with two tables that each "
                + "have one constraint should contain one check",
                1, mutant.getCheckConstraints().size());
        assertEquals("The table that has been mutated should have no checks",
                0, mutant.getCheckConstraints(t1).size());
        assertEquals("The table that has not been mutated should have one check",
                1, mutant.getCheckConstraints(t2).size());
        assertEquals("The expression of the first check in the first mutant "
                + "should be the expression of the second check constraint",
                expr, mutant.getCheckConstraints().get(0).getExpression());
    }

    @Test
    public void testSchemaTwoTablesMutantTwo() {
        initTwoTablesSchema();
        Schema mutant = twoTablesSchemaMutants.get(1).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Table t2 = mutant.getTable("t2");
        t2.getColumn("c2");
        Expression expr = new RelationalExpression(
                new ColumnExpression(t1, c1),
                RelationalOperator.LESS,
                new ConstantExpression(new NumericValue(5)));
        assertEquals("The second mutant of a schema with two tables that each "
                + "have one constraint should contain one check",
                1, mutant.getCheckConstraints().size());
        assertEquals("The table that has been mutated should have no checks",
                0, mutant.getCheckConstraints(t2).size());
        assertEquals("The table that has not been mutated should have one check",
                1, mutant.getCheckConstraints(t1).size());
        assertEquals("The expression of the first check in the second mutant "
                + "should be the expression of the first check constraint",
                expr, mutant.getCheckConstraints().get(0).getExpression());
    }
}
