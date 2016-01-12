package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/*
 * Examination schema.
 * Java code originally generated: 2013/08/17 00:30:35
 *
 */

@SuppressWarnings("serial")
public class Examination extends Schema {

	public Examination() {
		super("Examination");

		Table tableExam = this.createTable("Exam");
		tableExam.createColumn("ekey", new IntDataType());
		tableExam.createColumn("fn", new VarCharDataType(15));
		tableExam.createColumn("ln", new VarCharDataType(30));
		tableExam.createColumn("exam", new IntDataType());
		tableExam.createColumn("score", new IntDataType());
		tableExam.createColumn("timeEnter", new DateDataType());
		this.createPrimaryKeyConstraint(tableExam, tableExam.getColumn("ekey"));
		this.createCheckConstraint(tableExam, new RelationalExpression(new ColumnExpression(tableExam, tableExam.getColumn("score")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
		this.createCheckConstraint(tableExam, new RelationalExpression(new ColumnExpression(tableExam, tableExam.getColumn("score")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(100))));

		Table tableExamlog = this.createTable("Examlog");
		tableExamlog.createColumn("lkey", new IntDataType());
		tableExamlog.createColumn("ekey", new IntDataType());
		tableExamlog.createColumn("ekeyOLD", new IntDataType());
		tableExamlog.createColumn("fnNEW", new VarCharDataType(15));
		tableExamlog.createColumn("fnOLD", new VarCharDataType(15));
		tableExamlog.createColumn("lnNEW", new VarCharDataType(30));
		tableExamlog.createColumn("lnOLD", new VarCharDataType(30));
		tableExamlog.createColumn("examNEW", new IntDataType());
		tableExamlog.createColumn("examOLD", new IntDataType());
		tableExamlog.createColumn("scoreNEW", new IntDataType());
		tableExamlog.createColumn("scoreOLD", new IntDataType());
		tableExamlog.createColumn("sqlAction", new VarCharDataType(15));
		tableExamlog.createColumn("examtimeEnter", new DateDataType());
		tableExamlog.createColumn("examtimeUpdate", new DateDataType());
		tableExamlog.createColumn("timeEnter", new DateDataType());
		this.createPrimaryKeyConstraint(tableExamlog, tableExamlog.getColumn("lkey"));
		this.createCheckConstraint(tableExamlog, new RelationalExpression(new ColumnExpression(tableExamlog, tableExamlog.getColumn("scoreNEW")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
		this.createCheckConstraint(tableExamlog, new RelationalExpression(new ColumnExpression(tableExamlog, tableExamlog.getColumn("scoreNEW")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(100))));
		this.createCheckConstraint(tableExamlog, new RelationalExpression(new ColumnExpression(tableExamlog, tableExamlog.getColumn("scoreOLD")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
		this.createCheckConstraint(tableExamlog, new RelationalExpression(new ColumnExpression(tableExamlog, tableExamlog.getColumn("scoreOLD")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(100))));
		this.createForeignKeyConstraint(tableExamlog, tableExamlog.getColumn("ekey"), tableExam, tableExam.getColumn("ekey"));
	}
}

