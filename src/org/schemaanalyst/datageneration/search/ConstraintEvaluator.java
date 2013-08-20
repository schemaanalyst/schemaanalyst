package org.schemaanalyst.datageneration.search;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.SimpleConstraintCoverageReport;
import org.schemaanalyst.datageneration.SimpleConstraintGoalReport;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.constraint.ConstraintObjectiveFunctionFactory;
import org.schemaanalyst.datageneration.search.objective.constraint.SchemaConstraintSystemObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

public class ConstraintEvaluator {

    protected Schema schema;
    protected Data state;
    protected SimpleConstraintCoverageReport report;

    public void initialize(Schema schema) {
        this.schema = schema;
        state = new Data();
        report = new SimpleConstraintCoverageReport(schema);
    }

    public boolean evaluate(Row row, Table table) {
        Data data = new Data();
        data.addRow(table, row);

        // go through each of the schema's constraints and check the row
            for (Constraint constraint : schema.getAllConstraints()) {

                if (table.equals(constraint.getTable())) {
    
                    boolean[] satisfyConstraintModes = {true, false};
    
                    for (boolean satisfyConstraint : satisfyConstraintModes) {
                        ObjectiveValue objVal = evaluateConstraint(constraint, data, satisfyConstraint);
    
                        if (objVal.isOptimal()) {
                            SimpleConstraintGoalReport goalReport = new SimpleConstraintGoalReport(schema, constraint, satisfyConstraint);
                            goalReport.setData(data);
                            goalReport.setSuccess(true);
                            report.addGoalReport(goalReport);
                        }
                    }
                }
            }
        
        // test if the row should go into the state
        ObjectiveFunction<Data> objFun = 
                new SchemaConstraintSystemObjectiveFunction(schema, state, null, null);
        ObjectiveValue objVal = objFun.evaluate(data);

        boolean success = objVal.isOptimal();
        if (success) {
            state.addRow(table, row);
        }
        return success;
    }

    protected ObjectiveValue evaluateConstraint(Constraint constraint, Data data, boolean satisfyConstraint) {
        boolean considerNull = satisfyConstraint;

        if (constraint instanceof PrimaryKeyConstraint) {
            considerNull = !considerNull;
        }

        ConstraintObjectiveFunctionFactory factory = 
                new ConstraintObjectiveFunctionFactory(
                        constraint, state, satisfyConstraint, considerNull);

        ObjectiveFunction<Data> objFun = factory.create();

        return objFun.evaluate(data);
    }

    public SimpleConstraintCoverageReport getCoverageReport() {
        return report;
    }
}
