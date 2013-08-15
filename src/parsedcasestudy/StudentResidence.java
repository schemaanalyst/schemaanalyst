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
 * Java code originally generated: 2013/08/15 10:52:18
 *
 */

@SuppressWarnings("serial")
public class StudentResidence extends Schema {

	public StudentResidence() {
		super("StudentResidence");

		Table tableResidence = this.createTable("Residence");
		tableResidence.createColumn("name", new VarCharDataType(50));
		tableResidence.createColumn("capacity", new IntDataType());
		tableResidence.createPrimaryKeyConstraint(tableResidence.getColumn("name"));
		tableResidence.createNotNullConstraint(tableResidence.getColumn("name"));
		tableResidence.createNotNullConstraint(tableResidence.getColumn("capacity"));
		tableResidence.createCheckConstraint(new RelationalExpression(new ColumnExpression(tableResidence, tableResidence.getColumn("capacity")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(1))));
		tableResidence.createCheckConstraint(new RelationalExpression(new ColumnExpression(tableResidence, tableResidence.getColumn("capacity")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(10))));

		Table tableStudent = this.createTable("Student");
		tableStudent.createColumn("id", new IntDataType());
		tableStudent.createColumn("firstName", new VarCharDataType(50));
		tableStudent.createColumn("lastName", new VarCharDataType(50));
		tableStudent.createColumn("residence", new VarCharDataType(50));
		tableStudent.createPrimaryKeyConstraint(tableStudent.getColumn("id"));
		tableStudent.createForeignKeyConstraint(tableStudent.getColumn("residence"), tableResidence, tableStudent.getColumn("name"));
		tableStudent.createCheckConstraint(new RelationalExpression(new ColumnExpression(tableStudent, tableStudent.getColumn("id")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
	}
}

