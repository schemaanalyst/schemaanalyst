package org.schemaanalyst.sqlrepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.util.Duplicable;

/**
 * Represents a database table.
 * @author Phil McMinn
 */
public class Table implements Duplicable<Table>, Serializable {

    private static final long serialVersionUID = 781185006248617033L;
    
    private String name;
    private List<Column> columns;

    private PrimaryKeyConstraint primaryKeyConstraint;    
    private List<CheckConstraint> checkConstraints;
    private List<ForeignKeyConstraint> foreignKeyConstraints;
    private List<NotNullConstraint> notNullConstraints;
    private List<UniqueConstraint> uniqueConstraints;

    /**
     * Constructs a Table.
     * @param name The name of the schema.
     * @param schema The schema to which this table belongs.
     */
    public Table(String name) {
        this.name = name;
        columns = new ArrayList<>();

        // the primary key is null until one is created through setPrimaryKey
        primaryKeyConstraint = null;        
        
        // constraints
        checkConstraints = new ArrayList<>();
        foreignKeyConstraints = new ArrayList<>();
        notNullConstraints = new ArrayList<>();
        uniqueConstraints = new ArrayList<>();
    }

    /**
     * Sets the name of the table.
     * @param The name of the table.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the table.
     * @return The name of the table.
     */
    public String getName() {
        return name;
    }

    /** 
     * Creates a column and adds it to the table
     * @columnName The name of the column.
     * @dataType The data type of the column.
     */
    public Column createColumn(String name, DataType dataType) {
        Column column = new Column(name, dataType);
        addColumn(column);
        return column;
    }
    
    /**
     * Adds a column to the table
     * @param column The column to be added.
     */
    public void addColumn(Column column) {
        if (hasColumn(column)) {
            throw new SQLRepresentationException(
            		"Table \"" + name + "\" already has a column named \"" + column + "\"");
        }
        columns.add(column);
    }

    /**
     * Gets a reference to one of the table's columns by its name (ignoring case).
     * @param name The name of the column.
     * @return The column, or null if a column wasn't found for the name given.
     */
    public Column getColumn(String columnName) {
        for (Column column : columns) {
            if (columnName.equalsIgnoreCase(column.getName())) {
                return column;
            }
        }
        return null;
    }
    
    /**
     * Returns whether a column is present in a table or not
     * @param column The column.
     * @return True if the column is present in the table, else false.
     */
    public boolean hasColumn(Column column) {
    	return hasColumn(column.getName());
    }    
    
    /**
     * Returns whether a column is present in a table or not.
     * @param columnName The column name.
     * @return True if the column is present in the table, else false.
     */
    public boolean hasColumn(String columnName) {
    	return getColumn(columnName) != null;
    }

    /**
     * Retrieves the list of columns associated with this table, in the order
     * they were created.
     * @return A list of the table's columns.
     */
    public List<Column> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    /**
     * Checks that a constraint doesn't have the same name as another method in the supplied list
     * @param constraint The constraint whose name is to be checked.
     * @param constraints The list of constraints to be checked against.
     * @return True if the list of constraints has a constraint with a matching name, else false.
     */
    private <E extends Constraint> boolean constraintNameClash(
            Constraint constraint, List<E> constraints) {
        String name = constraint.getName();
        if (name != null) {
            for (Constraint otherConstraint : constraints) {
                if (name.equals(otherConstraint.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Creates a CHECK constraint and adds it to the table.
     * @param expression The expression underpinning the constraint.
     */
    public CheckConstraint createCheckConstraint(Expression expression) {
        CheckConstraint checkConstraint = new CheckConstraint(expression);
        addCheckConstraint(checkConstraint);
        return checkConstraint;
    }      
    
    /**
     * Creates a CHECK constraint and adds it to the table.
     * @param constraintName The name of the constraint.
     * @param expression The expression underpinning the constraint.
     */
    public CheckConstraint createCheckConstraint(String constraintName, Expression expression) {
        CheckConstraint checkConstraint = new CheckConstraint(constraintName, expression);
        addCheckConstraint(checkConstraint);
        return checkConstraint;
    } 
    
    /**
     * Adds a CHECK constraint to the table.
     * @param checkConstriant The CHECK constraint to be added.
     */
    public void addCheckConstraint(CheckConstraint checkConstraint) {
    	if (constraintNameClash(checkConstraint, checkConstraints)) {
    	    throw new SQLRepresentationException(
    	            "Table " + name + " already has a CHECK constraint named \"" + 
    	                    checkConstraint.getName() + "\"");
    	}
        checkConstraints.add(checkConstraint);
    }

    /**
     * Removes a CHECK constraint from the table.
     * @param checkConstraint The CHECK constraint to remove.
     * @return True if the CHECK constraint was already on the table and was
     * successfully removed, else false.
     */
    public boolean removeCheckConstraint(CheckConstraint checkConstraint) {
        return checkConstraints.remove(checkConstraint);
    }

    /**
     * Returns a unmodifiable list of the CHECK constraints on this table, in
     * the order they were added to the table.
     * @return A unmodifiable list of the CHECK constraints on the table.
     */
    public List<CheckConstraint> getCheckConstraints() {
        return Collections.unmodifiableList(checkConstraints);
    }

    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * @param column The column of this table that is the subject of the FOREIGN KEY.
     * @param referenceTable The table being referenced.
     * @param referenceColumn The column in the reference table.
     */
    public ForeignKeyConstraint createForeignKeyConstraint(
            Column column, Table referenceTable, Column referenceColumn) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
                column, referenceTable, referenceColumn);
        addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
    }
    
    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * @param constraintName The name of the constraint.     
     * @param columns The list of columns that are the subject of the FOREIGN KEY.
     * @param referenceTable The table being referenced.
     * @param referenceColumns The list of column in the reference table.
     */
    public ForeignKeyConstraint createForeignKeyConstraint(
            String constraintName, List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
                constraintName, columns, referenceTable, referenceColumns);
        addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
    }    
    
    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * @param columns The list of columns that are the subject of the FOREIGN KEY.
     * @param referenceTable The table being referenced.
     * @param referenceColumns The list of column in the reference table.
     */
    public ForeignKeyConstraint createForeignKeyConstraint(
            List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
                columns, referenceTable, referenceColumns);
        addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
    }
    
    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * @param constraintName The name of the constraint.
     * @param column The column of this table that is the subject of the FOREIGN KEY.
     * @param referenceTable The table being referenced.
     * @param referenceColumn The column in the reference table.
     */
    public ForeignKeyConstraint createForeignKeyConstraint(
            String constraintName, Column column, Table referenceTable, Column referenceColumn) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
                constraintName, column, referenceTable, referenceColumn);
        addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
    }      
    
    /**
     * Adds a FOREIGN KEY to the table.
     * @param foreignKeyConstraint The FOREIGN KEY to be added.
     */
    public void addForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) {
        List<Column> columns = foreignKeyConstraint.getColumns();
        for (Column column : columns) {
            if (!hasColumn(column)) {
                throw new SQLRepresentationException(
                        "No such column \"" + column.getName() + "\" in table " + name + 
                        " for ForeignKeyConstraint");
            }        
        }
        
        if (constraintNameClash(foreignKeyConstraint, foreignKeyConstraints)) {
            throw new SQLRepresentationException(
                    "Table " + name + " already has a FOREIGN KEY constraint named \"" + 
                            foreignKeyConstraint.getName() + "\"");
        }
    	foreignKeyConstraints.add(foreignKeyConstraint);
    }

    /**
     * Removes a FOREIGN KEY from the table.
     * @param foreignKeyConstraint The foreign key to remove.
     * @return True if the foreign key was defined for the table and was removed
     * successfully, else false.
     */
    public boolean removeForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) {
        return foreignKeyConstraints.remove(foreignKeyConstraint);
    }

    /**
     * Returns an unmodifiable list of the FOREIGN KEYs on this table, in the
     * order they were created.
     * @return An unmodifiable list of the FOREIGN KEYs on this table.
     */
    public List<ForeignKeyConstraint> getForeignKeyConstraints() {
        return Collections.unmodifiableList(foreignKeyConstraints);
    }
    
    /**
     * Creates a NOT NULL constraint and adds it to the table.
     * @param column The column to be the subject of the constraint.
     * @return The NOT NULL constraint created.
     */
    public NotNullConstraint createNotNullConstraint(Column column) {
        NotNullConstraint notNullConstraint = new NotNullConstraint(column);
        addNotNullConstraint(notNullConstraint);
        return notNullConstraint;
    }

    /**
     * Creates a NOT NULL constraint and adds it to the table.
     * @param constraintName The name of the constraint.
     * @param column The column to be the subject of the constraint.
     * @return The NOT NULL constraint created.
     */
    public NotNullConstraint createNotNullConstraint(String constraintName, Column column) {
        NotNullConstraint notNullConstraint = new NotNullConstraint(constraintName, column);
        addNotNullConstraint(notNullConstraint);
        return notNullConstraint;
    }    
    
    /**
     * Adds a NOT NULL constraint to the table.
     * @param notNullConstraint The NOT NULL constraint to add.
     */
    public void addNotNullConstraint(NotNullConstraint notNullConstraint) {
        Column column = notNullConstraint.getColumn();
        if (!hasColumn(column)) {
            throw new SQLRepresentationException(
                    "No such column \"" + column.getName() + "\" in table " + name + 
                    " for NotNullConstraint");
        }

        if (constraintNameClash(notNullConstraint, notNullConstraints)) {
            throw new SQLRepresentationException(
                    "Table " + name + " already has a NOT NULL constraint named \"" + 
                            notNullConstraint.getName() + "\"");
        }
        
        notNullConstraints.add(notNullConstraint);
    }
    
    /**
     * Removes a NOT NULL constraint.
     * @param notNullConstraint The constraint to remove.
     * @return True if the not null constraint existed on the table and was
     * removed, else false.
     */
    public boolean removeNotNullConstraint(NotNullConstraint notNullConstraint) {
        return notNullConstraints.remove(notNullConstraint);
    }

    /**
     * Returns a list of NOT NULL constraints on the table, in order of
     * creation.
     * @return A list of NOT NULL constraints on the table.
     */
    public List<NotNullConstraint> getNotNullConstraints() {
        return Collections.unmodifiableList(notNullConstraints);
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * @param columns The PRIMARY KEY columns.
     * @return The PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(Column... columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * @param constraintName The name of the constraint.
     * @param columns The PRIMARY KEY columns.
     * @return The PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(String constraintName, Column... columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(constraintName, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }    

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * @param columns The PRIMARY KEY columns.
     * @return The PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(List<Column> columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * @param constraintName The name of the constraint.
     * @param columns The PRIMARY KEY columns.
     * @return The PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(String constraintName, List<Column> columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(constraintName, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }   
    
    /**
     * Sets the PRIMARY KEY for the table.
     * @param primaryKeyConstraint The PRIMARY KEY for the table.
     */
    public void setPrimaryKeyConstraint(PrimaryKeyConstraint primaryKeyConstraint) {
        List<Column> columns = primaryKeyConstraint.getColumns();
        for (Column column : columns) {
            if (!hasColumn(column)) {
                throw new SQLRepresentationException(
                        "No such column \"" + column.getName() + "\" in table " + name + 
                        " for PrimaryKeyConstraint");                
            }
        }
        this.primaryKeyConstraint = primaryKeyConstraint;
    }

    /**
     * Removes the PRIMARY KEY for this table.
     */
    public void removePrimaryKeyConstraint() {
        primaryKeyConstraint = null;
    }

    /**
     * Returns the PRIMARY KEY for the table (null if no PRIMARY KEY is set).
     * @return The table's PRIMARY KEY (null if no PRIMARY KEY is set).
     */
    public PrimaryKeyConstraint getPrimaryKeyConstraint() {
        return primaryKeyConstraint;
    }

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * @param columns The UNIQUE columns.
     * @return The UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(Column... columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * @param constraintName The name of the constraint.
     * @param columns The UNIQUE columns.
     * @return The UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(String constraintName, Column... columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(constraintName, columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }    

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * @param columns The UNIQUE columns.
     * @return The UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(List<Column> columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * @param constraintName The name of the constraint.
     * @param columns The UNIQUE columns.
     * @return The UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(String constraintName, List<Column> columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(constraintName, columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }    
    
    /**
     * Sets a UNIQUE constraint on the table.
     * @param uniqueConstraint The UNIQUE constraint to be added.
     */
    public void addUniqueConstraint(UniqueConstraint uniqueConstraint) {
        if (constraintNameClash(uniqueConstraint, uniqueConstraints)) {
            throw new SQLRepresentationException(
                    "Table " + name + " already has a UNIQUE constraint named \"" + 
                            uniqueConstraint.getName() + "\"");
        }
        uniqueConstraints.add(uniqueConstraint);
    }

    /**
     * Removes a UNIQUE constraint on the table.
     * @param uniqueConstraint The unique constraint to be removed.
     * @return True if the unique constraint existed for the table and was
     * successfully removed, else false.
     */
    public boolean removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
        return uniqueConstraints.remove(uniqueConstraint);
    }

    /**
     * Returns the list of UNIQUE constraints on the table, in the order they
     * were created.
     * @return The list of UNIQUE constraints on the table.
     */
    public List<UniqueConstraint> getUniqueConstraints() {
        return Collections.unmodifiableList(uniqueConstraints);
    }

    /**
     * Returns a list of all constraints on the table.
     * @return A list containing all the constraints on the table.
     */
    public List<Constraint> getConstraints() {
        List<Constraint> constraints = new ArrayList<>();
        if (primaryKeyConstraint != null) {
            constraints.add(primaryKeyConstraint);
        }
        constraints.addAll(foreignKeyConstraints);
        constraints.addAll(notNullConstraints);
        constraints.addAll(uniqueConstraints);
        constraints.addAll(checkConstraints);
        return constraints;
    }

    /**
     * Gets a list of all tables connected to this table via some foreign key
     * relationship (possibly involving intermediate tables).
     * @return A list of connected tables.
     */
    public List<Table> getConnectedTables() {
        List<Table> referencedTables = new ArrayList<>();

        List<Table> toVisit = new ArrayList<>();
        toVisit.add(this);

        while (toVisit.size() > 0) {
            Table current = toVisit.remove(0);
            for (ForeignKeyConstraint foreignKey : current.getForeignKeyConstraints()) {
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
     * Returns a duplicated version of this table.
     * @return The duplicate.
     */
    public Table duplicate() {
    	//TODO -- fix up references etc.
    	return null;
    }    
    
    /**
     * Return a hash code for the table.
     * @return The table's generated hash code.
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((checkConstraints == null) ? 0 : checkConstraints.hashCode());
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime
				* result
				+ ((foreignKeyConstraints == null) ? 0 : foreignKeyConstraints
						.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((notNullConstraints == null) ? 0 : notNullConstraints
						.hashCode());
		result = prime
				* result
				+ ((primaryKeyConstraint == null) ? 0 : primaryKeyConstraint
						.hashCode());
		result = prime
				* result
				+ ((uniqueConstraints == null) ? 0 : uniqueConstraints
						.hashCode());
		return result;
	}

    /**
     * Checks if a table is equal to another.
     * @param obj The object for comparison
     * @return Whether the table is equal to obj
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (checkConstraints == null) {
			if (other.checkConstraints != null)
				return false;
		} else if (!checkConstraints.equals(other.checkConstraints))
			return false;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (foreignKeyConstraints == null) {
			if (other.foreignKeyConstraints != null)
				return false;
		} else if (!foreignKeyConstraints.equals(other.foreignKeyConstraints))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notNullConstraints == null) {
			if (other.notNullConstraints != null)
				return false;
		} else if (!notNullConstraints.equals(other.notNullConstraints))
			return false;
		if (primaryKeyConstraint == null) {
			if (other.primaryKeyConstraint != null)
				return false;
		} else if (!primaryKeyConstraint.equals(other.primaryKeyConstraint))
			return false;
		if (uniqueConstraints == null) {
			if (other.uniqueConstraints != null)
				return false;
		} else if (!uniqueConstraints.equals(other.uniqueConstraints))
			return false;
		return true;
	}

    /**
     * Returns the table name.
     */
    @Override
    public String toString() {
        return getName();
    }    
}
