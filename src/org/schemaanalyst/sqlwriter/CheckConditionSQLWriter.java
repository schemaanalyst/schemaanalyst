package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.BetweenCheckCondition;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.CheckCondition;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.CheckConditionVisitor;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.InCheckCondition;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.RelationalCheckCondition;

public class CheckConditionSQLWriter {

    protected OperandSQLWriter operandSQLWriter;

    public void setOperandSQLWriter(OperandSQLWriter operandSQLWriter) {
        this.operandSQLWriter = operandSQLWriter;
    }

    public String writeCheckCondition(CheckCondition checkCondition) {

        class CheckConditionSQLWriterVisitor implements CheckConditionVisitor {

            String sql;

            public String writeExpression(CheckCondition expression) {
                sql = "";
                expression.accept(this);
                return sql;
            }

            public void visit(BetweenCheckCondition expression) {
                sql = writeBetweenCheckCondition(expression);
            }

            public void visit(InCheckCondition expression) {
                sql = writeInCheckCondition(expression);
            }

            public void visit(RelationalCheckCondition expression) {
                sql = writeRelationalCheckCondition(expression);
            }
        }

        return (new CheckConditionSQLWriterVisitor()).writeExpression(checkCondition);
    }

    public String writeBetweenCheckCondition(BetweenCheckCondition expression) {
        return expression.getColumn().getName()
                + " BETWEEN "
                + operandSQLWriter.writeOperand(expression.getLower())
                + " AND "
                + operandSQLWriter.writeOperand(expression.getUpper());
    }

    public String writeInCheckCondition(InCheckCondition checkCondition) {
        StringBuilder sb = new StringBuilder();
        sb.append(checkCondition.getColumn().getName());

        boolean first = true;
        sb.append(" IN (");
        for (Value value : checkCondition.getValues()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(operandSQLWriter.writeOperand(value));
        }
        sb.append(")");
        return sb.toString();
    }

    public String writeRelationalCheckCondition(RelationalCheckCondition checkCondition) {
        return operandSQLWriter.writeOperand(checkCondition.getLHS()) + " "
                + checkCondition.getOperator() + " "
                + operandSQLWriter.writeOperand(checkCondition.getRHS());
    }
}
