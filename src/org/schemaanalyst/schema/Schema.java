package org.schemaanalyst.schema;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.schemaanalyst.util.Duplicable;

/**
 * Represents a database schema.
 * @author Phil McMinn
 *
 */
public class Schema implements Serializable, Duplicable<Schema> {

	private static final long serialVersionUID = 7338170433995168952L;

	protected String name;
	protected List<String> comments; 
	protected List<Table> tables;
	
	/**
	 * Constructs the schema.
	 * @param name The name of the schema.
	 */
	public Schema(String name) {
		this.name = name;
		this.comments = new ArrayList<String>();
		this.tables = new ArrayList<Table>();
	}
	
	/**
	 * Returns the name of the schema.
	 * @return The name of the schema.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the schema.
	 * @param name The new schema name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Adds a comment string for the schema (useful in mutation to denote what the mutation actually was).
	 * @param comment The comment to be added.
	 */
	public void addComment(String comment) {
		comments.add(comment);
	}
	
	/**
	 * Retrieves the description string for the schema
	 * @return A description string for the schema 
	 */
	public List<String> getComments() {
		return Collections.unmodifiableList(comments);
	}
	
	/**
	 * Creates a table on the schema
	 * @param name The name of the table
	 * @return The table created
	 */
	public Table createTable(String name) {
		Table table = new Table(this, name);
		if (tables.contains(table)) {
			throw new SchemaException("Table "+table+" already exists in this schema");
		}
		tables.add(table);
		orderTables();
		return table;
	}
	
	/** 
	 * Causes the table list to be ordered in terms of dependencies.
	 */
	protected void orderTables() {
		tables = orderByDependencies(tables);
	}

	/**
	 * Looks up a table by its name, returning null if the table was not found.
	 * @param name The name of the table.
	 * @return The table, or null if no table was found for the name.
	 */
	public Table getTable(String name) {
		for (Table table : tables) {
			if (table.getName().equalsIgnoreCase(name)) {
				return table;
			}
		}
		return null;
	}
	
	/**
	 * Returns an unmodifiable list of tables that the schema contains.
	 * Tables referenced via foreign keys appear in the list before the tables that reference them. 
	 * @return An unmodifiable list of tables that the schema contains.
	 */
	public List<Table> getTables() {
		return Collections.unmodifiableList(tables);
	}	
	
	/**
	 * Returns a list of tables in the schema, in reverse order to getTables.
	 * @return An unmodifiable list of tables that the schema contains (in reverse order to getTables).
	 */
	public List<Table> getTablesInReverseOrder() {
		List<Table> reverseOrder = new ArrayList<Table>(tables);
		Collections.copy(reverseOrder, tables);
		Collections.reverse(reverseOrder);
		return reverseOrder;
	}	
	
	/**
	 * Returns a list of all the integrity constraints present in the schema
	 * @return A list of integrity constraints.
	 */
	public List<Constraint> getConstraints() {
		List<Constraint> constraints = new ArrayList<>();
		for (Table table : getTables()) {
			constraints.addAll(table.getConstraints());
		}
		return constraints;
	}
	
	/**
	 * Deep copies the schema.
	 * @return A deep copy of the schema.
	 */
	public Schema duplicate() {
		Schema duplicate = new Schema(name);

		// important -- must be done in this order so that 
		// foreign keys are remapped correctly
		for (Table table : tables) {
			table.copyTo(duplicate);
		}
		
		return duplicate;
	}		
	
	/**
	 * Orders a list of tables such that each table's dependencies appear before it
	 * in the list.
	 * @param tables The list of tables to be put in order.
	 * @return The ordered list.
	 */		
	public static List<Table> orderByDependencies(List<Table> tables) {
		return new TableDependencyOrderer().order(tables);
	}
}
