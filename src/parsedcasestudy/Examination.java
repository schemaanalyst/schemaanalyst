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
 * Java code originally generated: 2013/07/11 14:08:01
 *
 */
@SuppressWarnings("serial")
public class Examination extends Schema {

    public Examination() {
        super("Examination");

        Table tableExam = this.createTable("Exam");
        tableExam.addColumn("ekey", new IntDataType());
        tableExam.addColumn("fn", new VarCharDataType(15));
        tableExam.addColumn("ln", new VarCharDataType(30));
        tableExam.addColumn("exam", new IntDataType());
        tableExam.addColumn("score", new IntDataType());
        tableExam.addColumn("timeEnter", new DateDataType());
        tableExam.setPrimaryKeyConstraint(tableExam.getColumn("ekey"));
        tableExam.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableExam.getColumn("score")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
        tableExam.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableExam.getColumn("score")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(100))));

        Table tableExamlog = this.createTable("Examlog");
        tableExamlog.addColumn("lkey", new IntDataType());
        tableExamlog.addColumn("ekey", new IntDataType());
        tableExamlog.addColumn("ekeyOLD", new IntDataType());
        tableExamlog.addColumn("fnNEW", new VarCharDataType(15));
        tableExamlog.addColumn("fnOLD", new VarCharDataType(15));
        tableExamlog.addColumn("lnNEW", new VarCharDataType(30));
        tableExamlog.addColumn("lnOLD", new VarCharDataType(30));
        tableExamlog.addColumn("examNEW", new IntDataType());
        tableExamlog.addColumn("examOLD", new IntDataType());
        tableExamlog.addColumn("scoreNEW", new IntDataType());
        tableExamlog.addColumn("scoreOLD", new IntDataType());
        tableExamlog.addColumn("sqlAction", new VarCharDataType(15));
        tableExamlog.addColumn("examtimeEnter", new DateDataType());
        tableExamlog.addColumn("examtimeUpdate", new DateDataType());
        tableExamlog.addColumn("timeEnter", new DateDataType());
        tableExamlog.setPrimaryKeyConstraint(tableExamlog.getColumn("lkey"));
        tableExamlog.addForeignKeyConstraint(tableExamlog.getColumn("ekey"), tableExam, tableExam.getColumn("ekey"));
        tableExamlog.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableExamlog.getColumn("scoreNEW")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
        tableExamlog.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableExamlog.getColumn("scoreNEW")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(100))));
        tableExamlog.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableExamlog.getColumn("scoreOLD")), RelationalOperator.GREATER_OR_EQUALS, new ConstantExpression(new NumericValue(0))));
        tableExamlog.addCheckConstraint(new RelationalExpression(new ColumnExpression(tableExamlog.getColumn("scoreOLD")), RelationalOperator.LESS_OR_EQUALS, new ConstantExpression(new NumericValue(100))));
    }
}
