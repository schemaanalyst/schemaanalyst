package org.schemaanalyst.datageneration.domainspecific;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.schemaanalyst.schema.Constraint;
import org.schemaanalyst.schema.NotNullConstraint;
import org.schemaanalyst.schema.PrimaryKeyConstraint;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;

public class ConstraintProblem {

	protected List<Constraint> toSatisfy, toNegate;	
	
	public ConstraintProblem(List<Constraint> toSatisfy, List<Constraint> toNegate) {
		this.toSatisfy = toSatisfy;
		this.toNegate = toNegate;
	}
	
	public List<Constraint> getConstraintsToSatisfy() {
		return toSatisfy;
	}
	
	public List<Constraint> getConstraintsToNegate() {
		return toNegate;
	}	
	
	public List<Table> getTables() {
		Set<Table> tables = new LinkedHashSet<>(); 
		tables.addAll(getTablesForConstraintsToSatisfy());
		tables.addAll(getTablesForConstraintsToNegate());
		
		List<Table> tableList = new ArrayList<Table>();
		tableList.addAll(tables);
		return tableList;
	}
	
	protected Set<Table> getTablesForConstraints(List<Constraint> constraints) {
		Set<Table> tables = new LinkedHashSet<>();
		for (Constraint constraint : constraints) {
			Table table = constraint.getTable();
			if (!tables.contains(table)) {
				tables.add(table);
			}
		}
		return tables;
	}

	public Set<Table> getTablesForConstraintsToSatisfy() {
		return getTablesForConstraints(toSatisfy);
	}	
	
	public Set<Table> getTablesForConstraintsToNegate() {
		return getTablesForConstraints(toNegate);
	}
	
	public static ConstraintProblem satisfyAll(Schema schema) {
		List<Constraint> toSatisfy = schema.getConstraints();
		List<Constraint> toNegate = new ArrayList<>();
		
		return new ConstraintProblem(toSatisfy, toNegate);
	}
	
	public static ConstraintProblem negateOne(Schema schema, 
											  Constraint constraintToInvalidate) {
		Table constraintTable = constraintToInvalidate.getTable();
		List<Table> tables = constraintTable.getConnectedTables();
		tables.add(constraintTable);
		
		List<Constraint> toNegate = new ArrayList<>();
		toNegate.add(constraintToInvalidate);
		
		List<Constraint> toSatisfy = new ArrayList<>();
		for (Table table : tables) {
			List<Constraint> tableConstraints = table.getConstraints();
			for (Constraint tableConstraint : tableConstraints) {
				
				// sticking plaster
				if ((constraintToInvalidate instanceof NotNullConstraint) &&
						(tableConstraint instanceof PrimaryKeyConstraint)) {
					
					PrimaryKeyConstraint primaryKey = (PrimaryKeyConstraint) tableConstraint;
					NotNullConstraint notNull = (NotNullConstraint) constraintToInvalidate;
					
					if (primaryKey.getColumns().contains(notNull.getColumn())) {
						continue;
					}
				}				
				
				if (!constraintToInvalidate.equals(tableConstraint)) {
					toSatisfy.add(tableConstraint);
				}
			}
		}
		
		return new ConstraintProblem(toSatisfy, toNegate);
	}
}
