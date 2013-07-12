package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/*
 * NistXTS748 schema.
 * Java code originally generated: 2013/07/11 14:10:39
 *
 */
@SuppressWarnings("serial")
public class NistXTS748 extends Schema {

    public NistXTS748() {
        super("NistXTS748");

        Table tableTest12549 = this.createTable("TEST12549");
        tableTest12549.addColumn("TNUM1", new NumericDataType(5));
        tableTest12549.addColumn("TNUM2", new NumericDataType(5));
        tableTest12549.addColumn("TNUM3", new NumericDataType(5));
        tableTest12549.addNotNullConstraint("CND12549A", tableTest12549.getColumn("TNUM1"));
        tableTest12549.addUniqueConstraint("CND12549B", tableTest12549.getColumn("TNUM2"));
        tableTest12549.addCheckConstraint("CND12549C", new RelationalExpression(new ColumnExpression(tableTest12549.getColumn("TNUM3")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(0))));
    }
}
