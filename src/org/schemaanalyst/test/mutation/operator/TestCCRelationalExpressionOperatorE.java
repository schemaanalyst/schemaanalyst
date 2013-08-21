/*
 */
package org.schemaanalyst.test.mutation.operator;

import java.util.List;
import static org.junit.Assert.*;
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
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/**
 *
 * @author Chris J. Wright
 */
public class TestCCRelationalExpressionOperatorE {

    Schema schema1 = new TestSchema1("schema");
    List<Mutant<Schema>> schema1Mutants = new CCRelationalExpressionOperatorE(schema1).mutate();

    class TestSchema1 extends Schema {

        public TestSchema1(String name) {
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

    @Test
    public void testSchema1MutantNumber() {
        assertEquals("Three mutants should be produced", 5, schema1Mutants.size());
    }

    @Test
    public void testSchema1Mutant1() {
        assertNotEquals("Mutation has not been applied in duplicate", "c1 < 5",
                schema1Mutants.get(0).getMutatedArtefact().getAllCheckConstraints().get(0).getExpression().toString());
    }

    @Test
    public void testSchema1Mutant2() {
        assertNotEquals("Mutation has not been applied in duplicate", "c1 < 5",
                schema1Mutants.get(1).getMutatedArtefact().getAllCheckConstraints().get(0).getExpression().toString());
    }

    @Test
    public void testSchema1Mutant3() {
        assertNotEquals("Mutation has not been applied in duplicate", "c1 < 5",
                schema1Mutants.get(2).getMutatedArtefact().getAllCheckConstraints().get(0).getExpression().toString());
    }
    
    @Test
    public void testSchema1Mutant4() {
        assertNotEquals("Mutation has not been applied in duplicate", "c1 < 5",
                schema1Mutants.get(3).getMutatedArtefact().getAllCheckConstraints().get(0).getExpression().toString());
    }
    
    @Test
    public void testSchema1Mutant5() {
        assertNotEquals("Mutation has not been applied in duplicate", "c1 < 5",
                schema1Mutants.get(4).getMutatedArtefact().getAllCheckConstraints().get(0).getExpression().toString());
    }
}
