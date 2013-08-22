/*
 */
package org.schemaanalyst.test.mutation.operator;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.operator.CCInExpressionRHSListExpressionElementR;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

/**
 *
 * @author Chris J. Wright
 */
public class TestCCInExpressionRHSListExpressionElementR {

    Schema schema3ListItems = new TestSchema3ListItems("schema");
    List<Mutant<Schema>> schema3ListItemsMutants = new CCInExpressionRHSListExpressionElementR(schema3ListItems).mutate();

    /**
     * Schema with one table, with one constraint, with one list, with 3 items
     */
    class TestSchema3ListItems extends Schema {

        public TestSchema3ListItems(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            t1.addColumn(c1);
            this.addTable(t1);
            Expression expr = new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(2)),
                    new ConstantExpression(new NumericValue(3))),
                    false);
            this.addCheckConstraint(new CheckConstraint(t1, expr));
        }
    }

    @Test
    public void testSchema3ListItemsMutantNumber() {
        assertEquals("Three mutants should be produced for a constraint with a list of three items",
                3, schema3ListItemsMutants.size());
    }

    @Test
    public void testSchema3ListItemsMutant1() {
        Schema mutant = schema3ListItemsMutants.get(0).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expected = new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(2)),
                new ConstantExpression(new NumericValue(3))),
                false);
        assertEquals("The first mutant expression should have the first list element removed, leaving '(2, 3)'",
                expected, mutant.getAllCheckConstraints().get(0).getExpression());
    }

    @Test
    public void testSchema3ListItemsMutant2() {
        Schema mutant = schema3ListItemsMutants.get(1).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expected = new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(3))),
                false);
        assertEquals("The second mutant expression should have the second list element removed, leaving (1, 3)",
                expected, mutant.getAllCheckConstraints().get(0).getExpression());
    }

    @Test
    public void testSchema3ListItemsMutant3() {
        Schema mutant = schema3ListItemsMutants.get(2).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expected = new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(2))),
                false);
        assertEquals("The third mutant expression should have the third list element removed, leaving (1, 2)",
                expected, mutant.getAllCheckConstraints().get(0).getExpression());
    }
    Schema schema1ListItem = new TestSchema1ListItem("schema");
    List<Mutant<Schema>> schema1ListItemMutants = new CCInExpressionRHSListExpressionElementR(schema1ListItem).mutate();

    /**
     * Schema with one table, with one constraint, with one list, with 1 item
     */
    class TestSchema1ListItem extends Schema {

        public TestSchema1ListItem(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            t1.addColumn(c1);
            this.addTable(t1);
            Expression expr = new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(
                    new ConstantExpression(new NumericValue(1))),
                    false);
            this.addCheckConstraint(new CheckConstraint(t1, expr));
        }
    }

    @Test
    public void testSchema1ListItemMutantNumber() {
        assertEquals("One mutant should be produced for a constraint with a list of one item",
                1, schema1ListItemMutants.size());
    }

    @Test
    public void testSchema1ListItemMutant1() {
        Schema mutant = schema1ListItemMutants.get(0).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expected = new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(),
                false);
        assertEquals("The first mutant expression should have the first (and only) list element removed, leaving ()",
                expected, mutant.getAllCheckConstraints().get(0).getExpression());
    }
    Schema schemaMultipleLists = new TestSchemaMultipleLists("schema");
    List<Mutant<Schema>> schemaMultipleListsMutants = new CCInExpressionRHSListExpressionElementR(schemaMultipleLists).mutate();

    /**
     * Schema with one table, with one constraint, with two lists, with two items
     */
    class TestSchemaMultipleLists extends Schema {

        public TestSchemaMultipleLists(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            t1.addColumn(c1);
            this.addTable(t1);
            Expression andExpr = new AndExpression(
                    new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(
                    new ConstantExpression(new NumericValue(1)),
                    new ConstantExpression(new NumericValue(2))),
                    false),
                    new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(
                    new ConstantExpression(new NumericValue(3)),
                    new ConstantExpression(new NumericValue(4))),
                    false));
            this.addCheckConstraint(new CheckConstraint(t1, andExpr));
        }
    }

    @Test
    public void testSchemaMultipleListsMutantNumber() {
        assertEquals("Four mutants should be produced for a constraint with two"
                + " lists that each have two items",
                4, schemaMultipleListsMutants.size());
    }

    @Test
    public void testSchemaMultipleListsMutant1() {
        Schema mutant = schemaMultipleListsMutants.get(0).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expected = new AndExpression(
                new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(2))),
                false),
                new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(4))),
                false));
        assertEquals("The first mutant expression should have the first element"
                + " of the  first list expression removed, leaving (2), and the"
                + " second list left unchanged",
                expected, mutant.getAllCheckConstraints().get(0).getExpression());
    }
    
    @Test
    public void testSchemaMultipleListsMutant2() {
        Schema mutant = schemaMultipleListsMutants.get(1).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expected = new AndExpression(
                new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(1))),
                false),
                new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(3)),
                new ConstantExpression(new NumericValue(4))),
                false));
        assertEquals("The second mutant expression should have the second"
                + " element of the first list expression removed, leaving (1), "
                + "and the second list left unchanged",
                expected, mutant.getAllCheckConstraints().get(0).getExpression());
    }
    
    @Test
    public void testSchemaMultipleListsMutant3() {
        Schema mutant = schemaMultipleListsMutants.get(2).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expected = new AndExpression(
                new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(2))),
                false),
                new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(4))),
                false));
        assertEquals("The third mutant expression should have the first element"
                + " of the second  list expression removed, leaving (4), and "
                + "the first list left unchanged",
                expected, mutant.getAllCheckConstraints().get(0).getExpression());
    }
    
    @Test
    public void testSchemaMultipleListsMutant4() {
        Schema mutant = schemaMultipleListsMutants.get(3).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expected = new AndExpression(
                new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(1)),
                new ConstantExpression(new NumericValue(2))),
                false),
                new InExpression(
                new ColumnExpression(t1, c1),
                new ListExpression(
                new ConstantExpression(new NumericValue(3))),
                false));
        assertEquals("The fourth mutant expression should have the first "
                + "element of the second  list expression removed, leaving (4),"
                + " and the first list left unchanged",
                expected, mutant.getAllCheckConstraints().get(0).getExpression());
    }
    
    Schema schemaMultipleChecks = new TestSchemaMultipleChecks("schema");
    List<Mutant<Schema>> schemaMultipleChecksMutants = new CCInExpressionRHSListExpressionElementR(schemaMultipleChecks).mutate();
    
    /**
     * Schema with one table, with two constraints, with one list, with 1 item
     */
    class TestSchemaMultipleChecks extends Schema {

        public TestSchemaMultipleChecks(String name) {
            super(name);
            Table t1 = new Table("t1");
            Column c1 = new Column("c1", new IntDataType());
            t1.addColumn(c1);
            this.addTable(t1);
            Expression expr = new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(
                    new ConstantExpression(new NumericValue(1))),
                    false);
            this.addCheckConstraint(new CheckConstraint(t1, expr));
            Expression expr2 = new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(
                    new ConstantExpression(new NumericValue(2))),
                    false);
            this.addCheckConstraint(new CheckConstraint(t1, expr2));
        }
    }
    
    @Test
    public void testSchemaMultipleChecksMutant1() {
        Schema mutant = schemaMultipleChecksMutants.get(0).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expectedExpr1 = new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(),
                    false);
        Expression expectedExpr2 = new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(
                    new ConstantExpression(new NumericValue(2))),
                    false);
        assertEquals("The expression in the first check constraint should have "
                + "the first (and only) element removed, leaving ()",
                expectedExpr1, mutant.getAllCheckConstraints().get(0).getExpression());
        assertEquals("The expression in the second check constraint should be "
                + "unchanged, leaving (2)",
                expectedExpr2, mutant.getAllCheckConstraints().get(1).getExpression());
    }
    
    @Test
    public void testSchemaMultipleChecksMutant2() {
        Schema mutant = schemaMultipleChecksMutants.get(1).getMutatedArtefact();
        Table t1 = mutant.getTable("t1");
        Column c1 = t1.getColumn("c1");
        Expression expectedExpr1 = new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(
                    new ConstantExpression(new NumericValue(1))),
                    false);
        Expression expectedExpr2 = new InExpression(
                    new ColumnExpression(t1, c1),
                    new ListExpression(),
                    false);
        assertEquals("The expression in the second check constraint should have"
                + "the first (and only) element removed, leaving ()",
                expectedExpr2, mutant.getAllCheckConstraints().get(1).getExpression());
        assertEquals("The expression in the first check constraint should be "
                + "unchanged, leaving (1)",
                expectedExpr1, mutant.getAllCheckConstraints().get(0).getExpression());
    }
}
