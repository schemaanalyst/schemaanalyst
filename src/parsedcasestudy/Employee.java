package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/*
 * Employee schema.
 * Java code originally generated: 2013/07/11 14:07:55
 *
 */
@SuppressWarnings("serial")
public class Employee extends Schema {

    public Employee() {
        super("Employee");

        Table tableEmployee = this.createTable("Employee");
        tableEmployee.addColumn("id", new IntDataType());
        tableEmployee.addColumn("first", new VarCharDataType(15));
        tableEmployee.addColumn("last", new VarCharDataType(20));
        tableEmployee.addColumn("age", new IntDataType());
        tableEmployee.addColumn("address", new VarCharDataType(30));
        tableEmployee.addColumn("city", new VarCharDataType(20));
        tableEmployee.addColumn("state", new VarCharDataType(20));
        tableEmployee.setPrimaryKeyConstraint(tableEmployee.getColumn("id"));
        tableEmployee.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableEmployee.getColumn("id")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
        tableEmployee.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableEmployee.getColumn("age")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
        tableEmployee.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableEmployee.getColumn("age")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(150))));
    }
}
