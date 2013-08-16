package org.schemaanalyst.sqlrepresentation;

import java.io.Serializable;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.util.Duplicable;
import org.schemaanalyst.util.collection.AbstractNamedEntity;
import org.schemaanalyst.util.collection.NamedEntityInsertOrderedSet;

/**
 * Represents a database schema.
 *
 * @author Phil McMinn
 *
 */
public class Schema extends AbstractNamedEntity 
                    implements Serializable, Duplicable<Schema> {

    private static final long serialVersionUID = 7338170433995168952L;
    private NamedEntityInsertOrderedSet<Table> tables;

    /**
     * Constructs the schema.
     * @param name The name of the schema.
     */
    public Schema(String name) {
        setName(name);
        this.tables = new NamedEntityInsertOrderedSet<>();
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
        boolean success = tables.add(table);
        if (!success) {
            throw new SQLRepresentationException(
                    "Table " + table + " already exists in this schema");
        }
    }

    /**
     * Looks up a table by its name (ignoring case), returning null if the table was not found.
     * @param name The name of the table.
     * @return The table, or null if no table was found for the name.
     */
    public Table getTable(String name) {
        return tables.get(name);
    }    
    
    /**
     * Returns a list of tables in the order they were added to the schema.
     * @return A list of tables that the schema contains.
     */
    public List<Table> getTables() {
        return tables.toList();
    }    

    /**
     * Returns a list of tables that the schema contains. Tables
     * referenced via foreign keys appear in the list before the tables that
     * reference them.
     * @return A list of tables that the schema contains.
     */
    public List<Table> getTablesInOrder() {
        return new TableDependencyOrderer().order(getTables());
    }

    /**
     * Returns a list of tables in th)e schema, in reverse order to getTablesInOrder.
     * @return A list of tables that the schema contains (in reverse order to getTablesInOrder).
     */
    public List<Table> getTablesInReverseOrder() {
        return new TableDependencyOrderer().reverseOrder(getTables());
    }
    
    /**
     * Deep copies the schema.
     * @return A deep copy of the schema.
     */
    @Override
    public Schema duplicate() {
        Schema duplicateSchema = new Schema(getName());
        for (Table table : tables) {
            duplicateSchema.addTable(table.duplicate());
        }
        
        // remap foreign keys to duplicated tables
        for (Table duplicatedTable : duplicateSchema.getTables()) {
            List<ForeignKeyConstraint> foreignKeyConstraints =
                    duplicatedTable.getForeignKeyConstraints();
            for (ForeignKeyConstraint foreignKeyConstraint : foreignKeyConstraints) {
                Table referenceTable = foreignKeyConstraint.getReferenceTable();
                Table duplicateReferenceTable = 
                        duplicateSchema.getTable(referenceTable.getName());
                foreignKeyConstraint.setReferenceTable(duplicateReferenceTable);
                foreignKeyConstraint.remapReferenceColumns(duplicateReferenceTable);
            }
        }
        return duplicateSchema;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((tables == null) ? 0 : tables.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Schema other = (Schema) obj;
        if (tables == null) {
            if (other.tables != null)
                return false;
        } else if (!tables.equals(other.tables))
            return false;
        return true;
    }
}
