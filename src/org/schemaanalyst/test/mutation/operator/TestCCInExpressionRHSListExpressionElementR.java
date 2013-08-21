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

    Schema schema1 = new TestSchema1("schema");
    List<Mutant<Schema>> schema1Mutants = new CCInExpressionRHSListExpressionElementR(schema1).mutate();

    class TestSchema1 extends Schema {

        public TestSchema1(String name) {
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
    public void testSchema1MutantNumber() {
        assertEquals("Three mutants should be produced", 3, schema1Mutants.size());
    }

    @Test
    public void testSchema1Mutant1() {
        assertEquals("Mutant produced is not as expected", "c1 IN (2, 3)",
                schema1Mutants.get(0).getMutatedArtefact().getAllCheckConstraints().get(0).getExpression().toString());
    }

    @Test
    public void testSchema1Mutant2() {
        assertEquals("Mutant produced is not as expected", "c1 IN (1, 3)",
                schema1Mutants.get(1).getMutatedArtefact().getAllCheckConstraints().get(0).getExpression().toString());
    }

    @Test
    public void testSchema1Mutant3() {
        assertEquals("Mutant produced is not as expected", "c1 IN (1, 2)",
                schema1Mutants.get(2).getMutatedArtefact().getAllCheckConstraints().get(0).getExpression().toString());
    }
}
