package org.schemaanalyst.datageneration;

import java.util.HashSet;
import java.util.Set;

import org.schemaanalyst.schema.Constraint;
import org.schemaanalyst.schema.Schema;

public class SimpleConstraintCoverageReport extends CoverageReport {

	protected Schema schema;
	protected Set<Constraint> satisfiedConstraints, negatedConstraints;
	protected int numAttempts;
	
	public SimpleConstraintCoverageReport(Schema schema) {
		super("Constraint coverage for " + schema.getName());
		this.schema = schema;
		satisfiedConstraints = new HashSet<>();
		negatedConstraints = new HashSet<>();
	}
	
	public void addGoalReport(SimpleConstraintGoalReport report) {
		addGoalReport((GoalReport) report);
		
		Set<Constraint> toUse =  report.isSatisfied() ? satisfiedConstraints : negatedConstraints;
		toUse.add(report.getConstraint()); 
	}
	
	public int getTotalCovered() {
		return satisfiedConstraints.size() + negatedConstraints.size();	
	}
	
	public int getNumGoals() {
		return schema.getConstraints().size() * 2;
	}
	
	public void setNumAttempts(int numAttempts) {
		this.numAttempts = numAttempts;
	}
	
	public int getNumAttempts() {
		return numAttempts;
	}

	public Set<Constraint> getSatisfiedConstraints() {
		return satisfiedConstraints;
	}
	
	public Set<Constraint> getNegatedConstraints() {
		return negatedConstraints;
	}
}
