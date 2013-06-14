package org.schemaanalyst.datageneration.search;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.SimpleConstraintCoverageReport;
import org.schemaanalyst.datageneration.SimpleConstraintGoalReport;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.constraint.ConstraintObjectiveFunctionFactory;
import org.schemaanalyst.datageneration.search.objective.constraint.SchemaConstraintSystemObjectiveFunction;
import org.schemaanalyst.representation.Constraint;
import org.schemaanalyst.representation.PrimaryKeyConstraint;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;

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
		for (Constraint constraint :  schema.getConstraints()) {
			
			if (constraint.getTable().equals(table)) {
			
				boolean[] satisfyModes = {true, false};
			
				for (boolean mode : satisfyModes) {
					ObjectiveValue objVal = evaluateConstraint(data, constraint, mode); 
					
					if (objVal.isOptimal()) {
						//if (Configuration.debug){ 
						//	System.out.println("Covered "+constraint+ " " + mode);
						//}
						
						SimpleConstraintGoalReport goalReport = new SimpleConstraintGoalReport(constraint, mode);
						goalReport.setData(data);
						goalReport.setSuccess(true);
						report.addGoalReport(goalReport);
					}
				}
			}
		}
				
		// test if the row should go into the state
		ObjectiveFunction<Data> objFun = new SchemaConstraintSystemObjectiveFunction(schema, state, null);
		ObjectiveValue objVal = objFun.evaluate(data);
			
		boolean success = objVal.isOptimal();
		if (success) {
			state.addRow(table, row);
		}
		return success;
	}
	
	protected ObjectiveValue evaluateConstraint(Data data, Constraint constraint, boolean satisfyConstraint) {
		boolean considerNull = satisfyConstraint;
		
		if (constraint instanceof PrimaryKeyConstraint) {
			considerNull = !considerNull;
		}
		
		ConstraintObjectiveFunctionFactory factory = new ConstraintObjectiveFunctionFactory(
				constraint, state, satisfyConstraint, considerNull);
		
		ObjectiveFunction<Data> objFun = factory.create();
		
		return objFun.evaluate(data);
	}		
	
	public SimpleConstraintCoverageReport getCoverageReport() {
		return report;
	}
}
