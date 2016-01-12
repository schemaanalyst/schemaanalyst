package org.schemaanalyst.unittest.data.generation;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.ValueLibrary;
import org.schemaanalyst.data.ValueMiner;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by phil on 26/02/2014.
 */
public class TestExpressionConstantMiner {

    @Test
    public void testSimpleConstant() {
        int value = 10;
        ConstantExpression constantExpression = new ConstantExpression(new NumericValue(value));
        ValueLibrary library = new ValueMiner().mine(constantExpression);

        assertTrue(library.getNumericValues().contains(new NumericValue((value))));
        assertFalse(library.getNumericValues().contains(new NumericValue((value + 1))));
    }

    @Test
    public void testComplexExpression() {
        ListExpression listExpression = new ListExpression(
                new ConstantExpression(new StringValue("P")),
                new ConstantExpression(new StringValue("H")),
                new ConstantExpression(new StringValue("I")),
                new ConstantExpression(new StringValue("L"))
        );
        Table table = new Table("test");
        Column column = new Column("initials", new VarCharDataType());
        InExpression inExpression = new InExpression(new ColumnExpression(table, column), listExpression, true);
        ValueLibrary library = new ValueMiner().mine(new ParenthesisedExpression(inExpression));

        assertTrue(library.getStringValues().contains(new StringValue("L")));
        assertTrue(library.getStringValues().contains(new StringValue("I")));
        assertTrue(library.getStringValues().contains(new StringValue("H")));
        assertTrue(library.getStringValues().contains(new StringValue("P")));
        assertFalse(library.getStringValues().contains(new StringValue("S")));
    }

    @Test
    public void testSchema() {
        Schema schema = new Schema("Test");
        Table tableResidence = schema.createTable("Residence");
        tableResidence.createColumn("name", new VarCharDataType(50));
        tableResidence.createColumn("capacity", new IntDataType());
        schema.createCheckConstraint(tableResidence,
                new RelationalExpression(
                        new ColumnExpression(tableResidence, tableResidence.getColumn("capacity")),
                        RelationalOperator.GREATER,
                        new ConstantExpression(new NumericValue(1))));
        schema.createCheckConstraint(tableResidence,
                new RelationalExpression(
                        new ColumnExpression(tableResidence, tableResidence.getColumn("capacity")),
                        RelationalOperator.LESS_OR_EQUALS,
                        new ConstantExpression(new NumericValue(10))));


        ValueLibrary library = new ValueMiner().mine(schema);

        assertTrue(library.getNumericValues().contains(new NumericValue(10)));
        assertTrue(library.getNumericValues().contains(new NumericValue(1)));
    }

}
