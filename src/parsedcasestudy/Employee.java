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
 * Java code originally generated: 2013/08/17 00:30:33
 *
 */

@SuppressWarnings("serial")
public class Employee extends Schema {

	public Employee() {
		super("Employee");

		Table tableEmployee = this.createTable("Employee");
		tableEmployee.createColumn("id", new IntDataType());
		tableEmployee.createColumn("first", new VarCharDataType(15));
		tableEmployee.createColumn("last", new VarCharDataType(20));
		tableEmployee.createColumn("age", new IntDataType());
		tableEmployee.createColumn("address", new VarCharDataType(30));
		tableEmployee.createColumn("city", new VarCharDataType(20));
		tableEmployee.createColumn("state", new VarCharDataType(20));
		this.createPrimaryKeyConstraint(tableEmployee, tableEmployee.getColumn("id"));
		this.createCheckConstraint(tableEmployee, new RelationalExpression(new ColumnExpression(tableEmployee, tableEmployee.getColumn("id")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
		this.createCheckConstraint(tableEmployee, new RelationalExpression(new ColumnExpression(tableEmployee, tableEmployee.getColumn("age")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
		this.createCheckConstraint(tableEmployee, new RelationalExpression(new ColumnExpression(tableEmployee, tableEmployee.getColumn("age")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(150))));
	}
}

