package org.schemaanalyst.faultlocalization;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.ConstraintVisitor;

import java.util.List;

@SuppressWarnings("serial")
public class EmptyConstraint extends Constraint {
	
	public List<Column> columns;

	public EmptyConstraint(String name, Table table) {
		super(name, table);
		// TODO Auto-generated constructor stub
	}
	public EmptyConstraint(String name, Table table, List<Column> columns) {
		super(name, table);
		// TODO Auto-generated constructor stub
		this.columns = columns;
	}

	@Override
	public Constraint duplicate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(ConstraintVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString(){
		return "Empty Constraint";
	}

}
