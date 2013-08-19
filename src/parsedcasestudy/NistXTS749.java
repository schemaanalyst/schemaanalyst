package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/*
 * NistXTS749 schema.
 * Java code originally generated: 2013/08/17 00:30:58
 *
 */

@SuppressWarnings("serial")
public class NistXTS749 extends Schema {

	public NistXTS749() {
		super("NistXTS749");

		Table tableStaff = this.createTable("STAFF");
		tableStaff.createColumn("SALARY", new IntDataType());
		tableStaff.createColumn("EMPNAME", new CharDataType(20));
		tableStaff.createColumn("GRADE", new DecimalDataType());
		tableStaff.createColumn("EMPNUM", new CharDataType(3));
		this.createPrimaryKeyConstraint(tableStaff, tableStaff.getColumn("EMPNUM"));
		this.createNotNullConstraint(tableStaff, tableStaff.getColumn("EMPNUM"));

		Table tableTest12649 = this.createTable("TEST12649");
		tableTest12649.createColumn("TNUM1", new NumericDataType(5));
		tableTest12649.createColumn("TNUM2", new NumericDataType(5));
		tableTest12649.createColumn("TCHAR", new CharDataType(3));
		this.createPrimaryKeyConstraint("CND12649A", tableTest12649, tableTest12649.getColumn("TNUM1"), tableTest12649.getColumn("TNUM2"));
		this.createCheckConstraint("CND12649B", tableTest12649, new RelationalExpression(new ColumnExpression(tableTest12649, tableTest12649.getColumn("TNUM2")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
		this.createForeignKeyConstraint("CND12649C", tableTest12649, tableTest12649.getColumn("TCHAR"), tableStaff, tableStaff.getColumn("EMPNUM"));
		this.createNotNullConstraint(tableTest12649, tableTest12649.getColumn("TNUM1"));
		this.createNotNullConstraint(tableTest12649, tableTest12649.getColumn("TNUM2"));
	}
}

