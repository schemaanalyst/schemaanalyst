package org.schemaanalyst.representation;

import java.io.Serializable;

/**
 * Abstract super class of all integrity constraints. 
 * @author Phil McMinn
 *
 */

public abstract class Constraint implements Serializable {

	private static final long serialVersionUID = -1552612200017351725L;
	
	protected Table table;
	protected String name;
	
	/**
	 * Constructor.
	 * @param table The table on which the constraint holds.
	 * @param name An identifying name for the constraint (optional - can be null).
	 */
	protected Constraint(Table table, String name) {		
		this.table = table;
		this.name = name;
	}	
	
	/**
	 * Gets the identification name of the constraint (may be null).
	 * @return The name of the constraint.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the table of the integrity constraint.
	 * @return The table of the constraint.
	 */
	public Table getTable() {
		return table;
	}
	
	/**
	 * Allows instances of the class to be visited by an IntegrityConstraintVisitor
	 * @param visitor The visitor instance.
	 */
	public abstract void accept(ConstraintVisitor visitor);
}
