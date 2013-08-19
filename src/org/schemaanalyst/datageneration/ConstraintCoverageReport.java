package org.schemaanalyst.datageneration;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;

public class ConstraintCoverageReport extends CoverageReport {

    protected Schema schema;
    protected int numConstraints;

    public ConstraintCoverageReport(Schema schema) {
        super("Constraint coverage for " + schema.getName());
        this.schema = schema;
        
        numConstraints = 0;
        for (Table table : schema.getTables()) {
            // TODO: PSM commented out to get to compile quickly ... 19/08/13
            //numConstraints += table.getAllConstraints().size();
        }
    }

    @Override
    public int getTotalCovered() {
        int total = 0;
        for (GoalReport goalReport : goalReports) {
            if (goalReport.wasSuccess()) {
                // if the constraint was null, we were trying to satisfy all the 
                // schema constraints
                if (((ConstraintGoalReport) goalReport).getConstraint() == null) {
                    total += numConstraints;
                } else {
                    total++;
                }
            }
        }
        return total;
    }

    @Override
    public int getNumGoals() {
        return numConstraints * 2;
    }

    @Override
    protected void appendDescriptionToStringBuilder(StringBuilder sb) {
        super.appendDescriptionToStringBuilder(sb);
        appendSchemaToStringBuilder(sb);
    }

    protected void appendSchemaToStringBuilder(StringBuilder sb) {
        SQLWriter sqlWriter = new SQLWriter();

        /*
        // comments removed from schema due to being abused!
        List<String> comments = sqlWriter.writeComments(schema.getComments());
        for (String comment : comments) {
            sb.append(comment);
            sb.append("\n");
        }
        */ 
        
        List<String> statements = sqlWriter.writeDropTableStatements(schema, true);
        statements.addAll(sqlWriter.writeCreateTableStatements(schema));
        for (String statement : statements) {
            sb.append(statement);
            sb.append(";\n");
        }
    }
}
