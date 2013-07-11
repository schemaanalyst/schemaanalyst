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
 * StudentResidence schema.
 * Java code originally generated: 2013/07/11 21:49:36
 *
 */

@SuppressWarnings("serial")
public class StudentResidence extends Schema {

	public StudentResidence() {
		super("StudentResidence");

		Table tableResidence = this.createTable("Residence");
		tableResidence.addColumn("name", new VarCharDataType(50));
		tableResidence.addColumn("capacity", new IntDataType());
		tableResidence.setPrimaryKeyConstraint(tableResidence.getColumn("name"));
		tableResidence.addNotNullConstraint(tableResidence.getColumn("name"));
		tableResidence.addNotNullConstraint(tableResidence.getColumn("capacity"));
		tableResidence.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableResidence.getColumn("capacity")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(1))));
		tableResidence.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableResidence.getColumn("capacity")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(10))));

		Table tableStudent = this.createTable("Student");
		tableStudent.addColumn("id", new IntDataType());
		tableStudent.addColumn("firstName", new VarCharDataType(50));
		tableStudent.addColumn("lastName", new VarCharDataType(50));
		tableStudent.addColumn("residence", new VarCharDataType(50));
		tableStudent.setPrimaryKeyConstraint(tableStudent.getColumn("id"));
		tableStudent.addForeignKeyConstraint(tableStudent.getColumn("residence"), tableResidence, tableResidence.getColumn("name"));
		tableStudent.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableStudent.getColumn("id")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
	}
}

