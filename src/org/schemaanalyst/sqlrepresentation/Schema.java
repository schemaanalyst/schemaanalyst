package org.schemaanalyst.sqlrepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.schemaanalyst.util.Duplicable;

/**
 * Represents a database schema.
 *
 * @author Phil McMinn
 *
 */
public class Schema implements Serializable, Duplicable<Schema> {

    private static final long serialVersionUID = 7338170433995168952L;
    private String name;
    private List<Table> tables;

    /**
     * Constructs the schema.
     * @param name The name of the schema.
     */
    public Schema(String name) {
        this.name = name;
        this.tables = new ArrayList<>();
    }

    /**
     * Returns the name of the schema.
     * @return The name of the schema.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Creates a table and adds it to the schema, returning the table created.
     *  @param name The name of the table to be added.
     */
    public Table createTable(String name) {
        Table table = new Table(name);
        addTable(table);
        return table;
    }
    /**
     * Adds a table to the schema.
     * @param table The table to be added.
     */
    public void addTable(Table table) {
    	if (getTable(table.getName()) == null) {
    		tables.add(table);
    	} else {
    		throw new SQLRepresentationException("Table " + table + " already exists in this schema");
    	}    	
    }

    /**
     * Looks up a table by its name (ignoring case), returning null if the table was not found.
     * @param name The name of the table.
     * @return The table, or null if no table was found for the name.
     */
    public Table getTable(String name) {
        for (Table table : tables) {
            String tableName = table.getName();
            if (tableName.equalsIgnoreCase(name)) {
                return table;
            }
        }
        return null;
    }    
    
    /**
     * Returns an unmodifiable list of tables in the order they were added to the schema.
     * @return An unmodifiable list of tables that the schema contains.
     */
    public List<Table> getTables() {
        return Collections.unmodifiableList(tables);
    }    

    /**
     * Returns a list of tables that the schema contains. Tables
     * referenced via foreign keys appear in the list before the tables that
     * reference them.
     * @return A list of tables that the schema contains.
     */
    public List<Table> getTablesInOrder() {
        return new TableDependencyOrderer().order(tables);
    }

    /**
     * Returns a list of tables in the schema, in reverse order to getTablesInOrder.
     * @return A list of tables that the schema contains (in reverse order to getTablesInOrder).
     */
    public List<Table> getTablesInReverseOrder() {
        return new TableDependencyOrderer().reverseOrder(tables);
    }
    
    /**
     * Deep copies the schema.
     * @return A deep copy of the schema.
     */
    @Override
    public Schema duplicate() {
        Schema copy = new Schema(name);
        for (Table table : tables) {
        	copy.addTable(table.duplicate());
        }
        // TODO - foreign keys ...
        return copy;
    }

    /**
     * Generates a hash code for the schema.
     * @return the hash code generated.
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tables == null) ? 0 : tables.hashCode());
		return result;
	}

	/**
	 * Checks whether the schema equals another object.
	 * @param obj The object to compare with.
	 * @return true if the schema is equal to obj, else false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schema other = (Schema) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (tables == null) {
			if (other.tables != null)
				return false;
		} else if (!tables.equals(other.tables))
			return false;
		return true;
	}
}
