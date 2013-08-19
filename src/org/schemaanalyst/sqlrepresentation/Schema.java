package org.schemaanalyst.sqlrepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.util.Duplicable;
import org.schemaanalyst.util.collection.Name;
import org.schemaanalyst.util.collection.NamedEntity;
import org.schemaanalyst.util.collection.NamedEntityInsertOrderedSet;

/**
 * Represents a database schema.
 *
 * @author Phil McMinn
 *
 */
public class Schema extends NamedEntity 
                    implements Serializable, Duplicable<Schema> {

    private static final long serialVersionUID = 7338170433995168952L;
    private NamedEntityInsertOrderedSet<Table> tables;

    private HashMap<Name, PrimaryKeyConstraint> primaryKeyConstraints;
    private HashMap<Name, LinkedHashSet<CheckConstraint>> checkConstraints;
    private HashMap<Name, LinkedHashSet<ForeignKeyConstraint>> foreignKeyConstraints;
    private HashMap<Name, LinkedHashSet<NotNullConstraint>> notNullConstraints;
    private HashMap<Name, LinkedHashSet<UniqueConstraint>> uniqueConstraints;
    
    /**
     * Constructs the schema.
     * @param name The name of the schema.
     */
    public Schema(String name) {
        if (name == null) {
            throw new SQLRepresentationException(
                    "Schema names cannot be null");
        }
        setName(name);
        this.tables = new NamedEntityInsertOrderedSet<>();
        
        primaryKeyConstraints = new HashMap<>();
        checkConstraints = new HashMap<>();
        foreignKeyConstraints = new HashMap<>();
        notNullConstraints = new HashMap<>();
        uniqueConstraints = new HashMap<>();
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
     * Checks if a table exists in the schema.
     * @param name The name of the table.
     * @return True if the table exists, else null.
     */
    public boolean hasTable(String name) {
        return tables.contains(name);
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
        return new TableDependencyOrderer().order(getTables(), this);
    }

    /**
     * Returns a list of tables in th)e schema, in reverse order to getTablesInOrder.
     * @return A list of tables that the schema contains (in reverse order to getTablesInOrder).
     */
    public List<Table> getTablesInReverseOrder() {
        return new TableDependencyOrderer().reverseOrder(getTables(), this);
    }
    
    /**
     * Gets a list of all tables transitively connected to a table via a FOREIGN KEY
     * relationship.
     * @param table The table for which to get connected tables.  
     * @return A list of connected tables.
     */
    public List<Table> getConnectedTables(Table table) {
        List<Table> referencedTables = new ArrayList<>();

        List<Table> toVisit = new ArrayList<>();
        toVisit.add(table);

        while (toVisit.size() > 0) {
            Table current = toVisit.remove(0);
            for (ForeignKeyConstraint foreignKey : getForeignKeyConstraints(current)) {
                Table referenceTable = foreignKey.getReferenceTable();
                if (!referencedTables.contains(referenceTable)) {
                    referencedTables.add(referenceTable);
                    toVisit.add(referenceTable);
                }
            }
        }

        return referencedTables;
    }    
    
    /**
     * Deep copies the schema.
     * @return A deep copy of the schema.
     */
    @Override
    public Schema duplicate() {
        Schema duplicateSchema = new Schema(getName());
        
        // tables
        for (Table table : tables) {
            duplicateSchema.addTable(table.duplicate());
        }
        
        for (Table table : tables) {
            Table duplicateTable = duplicateSchema.getTable(table.getName());
            // PRIMARY KEYs
            if (hasPrimaryKeyConstraint(table)) {
                PrimaryKeyConstraint duplicatePrimaryKeyConstraint = getPrimaryKeyConstraint(table).duplicate();
                duplicatePrimaryKeyConstraint.remap(duplicateTable);
                duplicateSchema.setPrimaryKeyConstraint(duplicatePrimaryKeyConstraint);
            }
            
            // CHECK constraints
            for (CheckConstraint checkConstraint : getCheckConstraints(table)) {
                CheckConstraint duplicateCheckConstraint = checkConstraint.duplicate();
                duplicateCheckConstraint.remap(duplicateTable);
                duplicateSchema.addCheckConstraint(duplicateCheckConstraint);
            }

            // UNIQUE constraints
            for (ForeignKeyConstraint foreignKeyConstraint : getForeignKeyConstraints(table)) {
                ForeignKeyConstraint duplicateForeignKeyConstraint = foreignKeyConstraint.duplicate();
                duplicateForeignKeyConstraint.remap(duplicateTable);
                duplicateSchema.addForeignKeyConstraint(duplicateForeignKeyConstraint);
            }            
            
            // NOT NULL constraints
            for (NotNullConstraint notNullConstraint : getNotNullConstraints(table)) {
                NotNullConstraint duplicateNotNullConstraint = notNullConstraint.duplicate();
                duplicateNotNullConstraint.remap(duplicateTable);
                duplicateSchema.addNotNullConstraint(duplicateNotNullConstraint);
            }
            
            // UNIQUE constraints
            for (UniqueConstraint uniqueConstraint : getUniqueConstraints(table)) {
                UniqueConstraint duplicateUniqueConstraint = uniqueConstraint.duplicate();
                duplicateUniqueConstraint.remap(duplicateTable);
                duplicateSchema.addUniqueConstraint(duplicateUniqueConstraint);
            }
        }    

        return duplicateSchema;
    }

    /**
     * Creates a CHECK constraint and adds it to the schema.
     * @param table The table on which the constraint is to be applied.
     * @param expression The expression underpinning the constraint.
     */
    public CheckConstraint createCheckConstraint(Table table, Expression expression) {
        CheckConstraint checkConstraint = new CheckConstraint(table, expression);
        addCheckConstraint(checkConstraint);
        return checkConstraint;
    }      

    /**
     * Creates a CHECK constraint and adds it to the schema.
     * @param constraintName The name of the constraint.
     * @param table The table on which the constraint is to be applied.
     * @param expression The expression underpinning the constraint.
     */
    public CheckConstraint createCheckConstraint(String constraintName, Table table, Expression expression) {
        CheckConstraint checkConstraint = new CheckConstraint(constraintName, table, expression);
        addCheckConstraint(checkConstraint);
        return checkConstraint;
    } 

    /**
     * Adds a CHECK constraint to the schema. 
     * @param checkConstraint The CHECK constraint to be added.
     */
    public void addCheckConstraint(CheckConstraint checkConstraint) {
        addConstraint(checkConstraint, checkConstraints);        
    }

    /**
     * Removes a CHECK constraint from the schema.
     * @param checkConstraint The CHECK constraint to remove.
     * @return True if the CHECK constraint was already on the table and was
     * successfully removed, else false.
     */
    public boolean removeCheckConstraint(CheckConstraint checkConstraint) {
        return removeConstraint(checkConstraint, checkConstraints);
    }

    /**
     * Returns a list of the CHECK constraints on a table, in
     * the order they were added to the table.
     * @param table The table for which the constraints are required. 
     * @return A unmodifiable list of the CHECK constraints on the table.
     */
    public List<CheckConstraint> getCheckConstraints(Table table) {
        return getConstraints(table, checkConstraints);
    }

    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * @param table The table on which the constraint is to be applied.
     * @param column The column of this table that is the subject of the FOREIGN KEY.
     * @param referenceTable The table being referenced.
     * @param referenceColumn The column in the reference table.
     */
    public ForeignKeyConstraint createForeignKeyConstraint(
            Table table, Column column, Table referenceTable, Column referenceColumn) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
                table, column, referenceTable, referenceColumn);
        addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
    }

    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * @param constraintName The name of the constraint.   
     * @param table The table on which the constraint is to be applied.  
     * @param columns The list of columns that are the subject of the FOREIGN KEY.
     * @param referenceTable The table being referenced.
     * @param referenceColumns The list of column in the reference table.
     */
    public ForeignKeyConstraint createForeignKeyConstraint(
            String constraintName, Table table, List<Column> columns, 
            Table referenceTable, List<Column> referenceColumns) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
                constraintName, table, columns, referenceTable, referenceColumns);
        addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
    }    

    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * @param table The table on which the constraint is to be applied.
     * @param columns The list of columns that are the subject of the FOREIGN KEY.
     * @param referenceTable The table being referenced.
     * @param referenceColumns The list of column in the reference table.
     */
    public ForeignKeyConstraint createForeignKeyConstraint(
            Table table, List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
                table, columns, referenceTable, referenceColumns);
        addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
    }

    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * @param constraintName The name of the constraint.
     * @param table The table on which the constraint is to be applied.
     * @param column The column of this table that is the subject of the FOREIGN KEY.
     * @param referenceTable The table being referenced.
     * @param referenceColumn The column in the reference table.
     */
    public ForeignKeyConstraint createForeignKeyConstraint(
            String constraintName, Table table, Column column, Table referenceTable, Column referenceColumn) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
                constraintName, table, column, referenceTable, referenceColumn);
        addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
    }      

    /**
     * Adds a FOREIGN KEY to the table.
     * @param foreignKeyConstraint The FOREIGN KEY to be added.
     */
    public void addForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) {
        addConstraint(foreignKeyConstraint, foreignKeyConstraints);
    }

    /**
     * Removes a FOREIGN KEY from the table.
     * @param foreignKeyConstraint The foreign key to remove.
     * @return True if the foreign key was defined for the table and was removed
     * successfully, else false.
     */
    public boolean removeForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) {
        return removeConstraint(foreignKeyConstraint, foreignKeyConstraints);
    }

    /**
     * Returns a list of the FOREIGN KEYs on this table, in the
     * order they were created.
     * @param table The table for which the constraints are required.  
     * @return An unmodifiable list of the FOREIGN KEYs on this table.
     */
    public List<ForeignKeyConstraint> getForeignKeyConstraints(Table table) {
        return getConstraints(table, foreignKeyConstraints);
    }
    
    /**
     * Returns a list of all FOREIGN KEYs defined on a schema.
     * @param table The table for which the constraints are required.  
     * @return An unmodifiable list of the FOREIGN KEYs on this table.
     */
    public List<ForeignKeyConstraint> getAllForeignKeyConstraints() {
        List<ForeignKeyConstraint> allConstraints = new ArrayList<ForeignKeyConstraint>();
        for (Table table : tables) {
            allConstraints.addAll(getConstraints(table, foreignKeyConstraints));
        }
        return allConstraints;
    }    

    /**
     * Creates a NOT NULL constraint and adds it to the table.
     * @param table The table on which the constraint is to be applied.
     * @param column The column to be the subject of the constraint.
     * @return The NOT NULL constraint created.
     */
    public NotNullConstraint createNotNullConstraint(Table table, Column column) {
        NotNullConstraint notNullConstraint = new NotNullConstraint(table, column);
        addNotNullConstraint(notNullConstraint);
        return notNullConstraint;
    }

    /**
     * Creates a NOT NULL constraint and adds it to the table.
     * @param constraintName The name of the constraint.
     * @param table The table on which the constraint is to be applied.
     * @param column The column to be the subject of the constraint.
     * @return The NOT NULL constraint created.
     */
    public NotNullConstraint createNotNullConstraint(String constraintName, Table table, Column column) {
        NotNullConstraint notNullConstraint = new NotNullConstraint(constraintName, table, column);
        addNotNullConstraint(notNullConstraint);
        return notNullConstraint;
    }    

    /**
     * Adds a NOT NULL constraint to the table.
     * @param notNullConstraint The NOT NULL constraint to add.
     */
    public void addNotNullConstraint(NotNullConstraint notNullConstraint) {
        addConstraint(notNullConstraint, notNullConstraints);
    }

    /**
     * Removes a NOT NULL constraint.
     * @param notNullConstraint The constraint to remove.
     * @return True if the not null constraint existed on the table and was
     * removed, else false.
     */
    public boolean removeNotNullConstraint(NotNullConstraint notNullConstraint) {
        return removeConstraint(notNullConstraint, notNullConstraints);        
    }

    /**
     * Returns a list of NOT NULL constraints on the table, in order of
     * creation.
     * @param table The table for which the constraints are required.  
     * @return A list of NOT NULL constraints on the table.
     */
    public List<NotNullConstraint> getNotNullConstraints(Table table) {
        return getConstraints(table, notNullConstraints);
    }

    /**
     * Checks whether a PRIMARY KEY is set on the table.
     * @param table The table to check.
     * @return True if a PRIMARY KEY is set, else false.
     */
    public boolean hasPrimaryKeyConstraint(Table table) {
        checkTableExists(table);
        return primaryKeyConstraints.containsKey(table.getNameInstance());
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * @param table The table on which the constraint is to be applied. 
     * @param columns The PRIMARY KEY columns.
     * @return The PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(Table table, Column... columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(table, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * @param constraintName The name of the constraint.
     * @param table The table on which the constraint is to be applied.
     * @param columns The PRIMARY KEY columns.
     * @return The PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(String constraintName, Table table, Column... columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(constraintName, table, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }    

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * @param table The table on which the constraint is to be applied.
     * @param columns The PRIMARY KEY columns.
     * @return The PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(Table table, List<Column> columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(table, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * @param constraintName The name of the constraint.
     * @param table The table on which the constraint is to be applied.
     * @param columns The PRIMARY KEY columns.
     * @return The PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(String constraintName, Table table, List<Column> columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(constraintName, table, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }   

    /**
     * Sets the PRIMARY KEY for the table defined in the constraint.
     * @param primaryKeyConstraint The PRIMARY KEY for the table.
     */
    public void setPrimaryKeyConstraint(PrimaryKeyConstraint primaryKeyConstraint) {
        Name tableName = getConstraintTable(primaryKeyConstraint).getNameInstance();
        primaryKeyConstraints.put(tableName, primaryKeyConstraint);
    }

    /**
     * Removes the PRIMARY KEY for a table.
     * @param table The table whose PRIMARY KEY is to be removed.
     */
    public void removePrimaryKeyConstraint(Table table) {
        checkTableExists(table);
        primaryKeyConstraints.remove(table.getNameInstance());
    }

    /**
     * Returns the PRIMARY KEY for a table (null if no PRIMARY KEY is set).
     * @param table The table whose PRIMARY KEY is sought.
     * @return The table's PRIMARY KEY (null if no PRIMARY KEY is set).
     */
    public PrimaryKeyConstraint getPrimaryKeyConstraint(Table table) {
        checkTableExists(table);
        return primaryKeyConstraints.get(table.getNameInstance());
    }
    
    /**
     * Creates a UNIQUE constraint and sets it on the table
     * @param table The table on which the constraint is to be applied.
     * @param columns The UNIQUE columns.
     * @return The UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(Table table, Column... columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(table, columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * @param constraintName The name of the constraint.
     * @param table The table on which the constraint is to be applied.
     * @param columns The UNIQUE columns.
     * @return The UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(String constraintName, Table table, Column... columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(constraintName, table, columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }    

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * @param table The table on which the constraint is to be applied.
     * @param columns The UNIQUE columns.
     * @return The UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(Table table, List<Column> columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(table, columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * @param constraintName The name of the constraint.
     * @param table The table on which the constraint is to be applied.
     * @param columns The UNIQUE columns.
     * @return The UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(String constraintName, Table table, List<Column> columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(constraintName, table, columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }    

    /**
     * Sets a UNIQUE constraint on the table.
     * @param uniqueConstraint The UNIQUE constraint to be added.
     */
    public void addUniqueConstraint(UniqueConstraint uniqueConstraint) {
        addConstraint(uniqueConstraint, uniqueConstraints);         
    }

    /**
     * Removes a UNIQUE constraint on the table.
     * @param uniqueConstraint The unique constraint to be removed.
     * @return True if the unique constraint existed for the table and was
     * successfully removed, else false.
     */
    public boolean removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
        return removeConstraint(uniqueConstraint, uniqueConstraints);
    }

    /**
     * Returns the list of UNIQUE constraints on the table, in the order they
     * were created.
     * @param table The table whose constraints are sought.
     * @return The list of UNIQUE constraints on the table.
     */
    public List<UniqueConstraint> getUniqueConstraints(Table table) {
        return getConstraints(table, uniqueConstraints);
    }
    
    /**
     * Returns A list of all constraints for a table.
     * @param table The table whose constraints are sought.
     * @return The list of constraints defined on the table.
     */
    public List<Constraint> getAllConstraints(Table table) {
        List<Constraint> constraints = new LinkedList<Constraint>();
        if (hasPrimaryKeyConstraint(table)) {
            constraints.add(getPrimaryKeyConstraint(table));
        }
        constraints.addAll(getCheckConstraints(table));
        constraints.addAll(getForeignKeyConstraints(table));
        constraints.addAll(getNotNullConstraints(table));
        constraints.addAll(getUniqueConstraints(table));
        return constraints;
    }
    
    /**
     * Adds a constraint to the schema.
     * @param constraint The constraint to be added.
     * @param constraintMap The constraint map to add the constraint to
     */
    private <C extends Constraint> void addConstraint(C constraint, HashMap<Name, LinkedHashSet<C>> constraintMap) {
        Name tableName = getConstraintTable(constraint).getNameInstance();
        LinkedHashSet<C> constraintsForTable = constraintMap.get(tableName);
        if (constraintsForTable == null) {
            constraintsForTable = new LinkedHashSet<>();
            constraintMap.put(tableName, constraintsForTable);
        }
        constraintsForTable.add(constraint);         
    }
    
    /**
     * Removes a constraint from the schema.
     * @param constraint The constraint to remove.
     * @return True if the not null constraint existed on the table and was
     * removed, else false.
     */
    private <C extends Constraint> boolean removeConstraint(C constraint, HashMap<Name, LinkedHashSet<C>> constraintMap) {
        Name tableName = getConstraintTable(constraint).getNameInstance();
        LinkedHashSet<C> constraintsForTable = constraintMap.get(tableName);
        if (constraintsForTable == null) {
            return false;
        }
        return constraintsForTable.remove(constraint);        
    }    
    
    /**
     * Returns a list of NOT NULL constraints on the table, in order of
     * creation.
     * @param table The table for which the constraints are required.  
     * @return A list of NOT NULL constraints on the table.
     */
    private <C extends Constraint> List<C> getConstraints(Table table, HashMap<Name, LinkedHashSet<C>> constraintMap) {
        checkTableExists(table);
        Name tableName = table.getNameInstance();
        LinkedHashSet<C> constraintsForTable = constraintMap.get(tableName);
        if (constraintsForTable == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(constraintsForTable);
    }
    
    /**
     * Returns the table of a constraint, throwing an SQLRepresentationException if it is not found.
     * creation.
     * @param contraint The constraint for which the table is required.  
     * @return The table of the constraint.
     */    
    private Table getConstraintTable(Constraint constraint) {
        Table table = constraint.getTable();
        checkTableExists(table);
        return table;
    }
    
    /**
     * Checks a table exists in the schema, throwing an SQLRepresentationException if it is not found.
     * creation.
     * @param table The table to check.  
     */  
    private void checkTableExists(Table table) {
        if (!tables.contains(table)) {
            throw new SQLRepresentationException(
                    "No such table \"" + table + "\" in this schema for constraint");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime
                * result
                + ((checkConstraints == null) ? 0 : checkConstraints.hashCode());
        result = prime
                * result
                + ((foreignKeyConstraints == null) ? 0 : foreignKeyConstraints
                        .hashCode());
        result = prime
                * result
                + ((notNullConstraints == null) ? 0 : notNullConstraints
                        .hashCode());
        result = prime
                * result
                + ((primaryKeyConstraints == null) ? 0 : primaryKeyConstraints
                        .hashCode());
        result = prime * result + ((tables == null) ? 0 : tables.hashCode());
        result = prime
                * result
                + ((uniqueConstraints == null) ? 0 : uniqueConstraints
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Schema))
            return false;
        Schema other = (Schema) obj;
        if (checkConstraints == null) {
            if (other.checkConstraints != null)
                return false;
        } else if (!checkConstraints.equals(other.checkConstraints))
            return false;
        if (foreignKeyConstraints == null) {
            if (other.foreignKeyConstraints != null)
                return false;
        } else if (!foreignKeyConstraints.equals(other.foreignKeyConstraints))
            return false;
        if (notNullConstraints == null) {
            if (other.notNullConstraints != null) 
                return false;
        } else if (!notNullConstraints.equals(other.notNullConstraints))
            return false;
        if (primaryKeyConstraints == null) {
            if (other.primaryKeyConstraints != null)
                return false;
        } else if (!primaryKeyConstraints.equals(other.primaryKeyConstraints))
            return false;
        if (tables == null) {
            if (other.tables != null)
                return false;
        } else if (!tables.equals(other.tables)) 
            return false;
        if (uniqueConstraints == null) {
            if (other.uniqueConstraints != null)
                return false;
        } else if (!uniqueConstraints.equals(other.uniqueConstraints))
            return false;
        return true;
    }

  
}
