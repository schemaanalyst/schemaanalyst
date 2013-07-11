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
 * Java code originally generated: 2013/07/11 14:10:42
 *
 */

@SuppressWarnings("serial")
public class NistXTS749 extends Schema {

	public NistXTS749() {
		super("NistXTS749");

		Table tableStaff = this.createTable("STAFF");
		tableStaff.addColumn("SALARY", new IntDataType());
		tableStaff.addColumn("EMPNAME", new CharDataType(20));
		tableStaff.addColumn("GRADE", new DecimalDataType());
		tableStaff.addColumn("EMPNUM", new CharDataType(3));
		tableStaff.setPrimaryKeyConstraint(tableStaff.getColumn("EMPNUM"));
		tableStaff.addNotNullConstraint(tableStaff.getColumn("EMPNUM"));

		Table tableTest12649 = this.createTable("TEST12649");
		tableTest12649.addColumn("TNUM1", new NumericDataType(5));
		tableTest12649.addColumn("TNUM2", new NumericDataType(5));
		tableTest12649.addColumn("TCHAR", new CharDataType(3));
		tableTest12649.setPrimaryKeyConstraint("CND12649A", tableTest12649.getColumn("TNUM1"), tableTest12649.getColumn("TNUM2"));
		tableTest12649.addForeignKeyConstraint("CND12649C", tableTest12649.getColumn("TCHAR"), tableStaff, tableStaff.getColumn("EMPNUM"));
		tableTest12649.addNotNullConstraint(tableTest12649.getColumn("TNUM1"));
		tableTest12649.addNotNullConstraint(tableTest12649.getColumn("TNUM2"));
		tableTest12649.addCheckConstraint("CND12649B", new RelationalExpression(new ColumnExpression(tableTest12649.getColumn("TNUM2")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
	}
}

