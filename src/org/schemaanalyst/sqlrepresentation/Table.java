package org.schemaanalyst.sqlrepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.checkcondition.CheckCondition;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.expression.ExpressionTree;


/**
 * Represents a table in a database schema.  Tables should be constructed using the <tt>createTable</tt> method of Schema.
 * @author Phil McMinn
 * 
 */
public class Table implements Serializable {

	private static final long serialVersionUID = 781185006248617033L;
	
	protected Schema schema;	
	protected String name;
	
	protected List<Column> columns;

	protected List<CheckConstraint> checkConstraints;
	protected List<ForeignKeyConstraint> foreignKeyConstraints;
	protected List<NotNullConstraint> notNullConstraints;
	protected PrimaryKeyConstraint primaryKeyConstraint;
	protected List<UniqueConstraint> uniqueConstraints;
	
	/**
	 * Constructs a Table.
	 * @param name The name of the schema.
	 * @param schema The schema to which this table belongs.
	 */
	protected Table(String name, Schema schema) {
		this.schema = schema;
		this.name = name;

		columns = new ArrayList<Column>();
		
		// constraints
		checkConstraints = new ArrayList<CheckConstraint>();
		foreignKeyConstraints = new ArrayList<ForeignKeyConstraint>();
		notNullConstraints = new ArrayList<NotNullConstraint>();
		uniqueConstraints = new ArrayList<UniqueConstraint>();
		
		// the primary key is null until one is created through setPrimaryKey
		primaryKeyConstraint = null;
	}
	
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
	 * Retrieves the schema to which this table belongs.
	 * @return The table's schema.
	 */
	public Schema getSchema() {
		return schema;
	}
	
	/**
	 * Creates a column in this table.
	 * @param name The name of the column.
	 * @param type The type of the column (see the types subpackage), for example VarChar.
	 * @return The column just created.
	 */
	public Column addColumn(String name, DataType type) {
		Column column = new Column(name, this, type);
		if (columns.contains(column)) {
			throw new SchemaConstructionException("Table \""+this.name+"\" already has a column named \""+name+"\"");
		}
		columns.add(column);
		return column;
	}
	
	/**
	 * Gets a reference to one of the table's columns by its name.
	 * @param name The name of the column.
	 * @return The column, or null if a column wasn't found for the name given.
	 */
	public Column getColumn(String name) {
		for (Column column : columns) {
			if (column.getName().equalsIgnoreCase(name)) {
				return column;
			}
		}
		return null;
	}	
	
	/**
	 * Returns whether a column is present in a table or not
	 * @param column The column
	 * @return True if the column is present in the table, else false
	 */
	public boolean hasColumn(Column column) {
		return columns.contains(column);
	}
	
	/**
	 * Retrieves the list of columns associated with this table, in the order they were created.
	 * @return A list of the table's columns.
	 */
	public List<Column> getColumns() {
		return Collections.unmodifiableList(columns);
	}
	
	/**
	 * Helper method for constructing column lists
	 * @param firstColumn A column to add to the list.
	 * @param remainingColumns An array of columns to add to the list.
	 * @return A list of columns containing first column and remaining columns 
	 */	
	public static List<Column> makeColumnList(Column firstColumn, Column... remainingColumns) {
        List<Column> allColumns = new ArrayList<Column>();
        allColumns.add(firstColumn);
        allColumns.addAll(Arrays.asList(remainingColumns));
        return allColumns;		
	}
	
	/**
	 * Adds a check constraint to the table.
	 * @param name A name for the check constraint (optional -- a version of this method exists without the requirement to specify a name).
	 * @param checkCondition The check constraint's condition.
	 * @return A reference to the Check object created as a result of the add
	 */
	public CheckConstraint addCheckConstraint(String name, ExpressionTree expressionTree) {
		return addCheckConstraint(new CheckConstraint(name, this, expressionTree));
	}	
	
	/**
	 * Adds a check constraint to the table.
	 * @param checkCondition The check constraint's condition.
	 * @return A reference to the Check object created as a result of the add
	 */
	public CheckConstraint addCheckConstraint(ExpressionTree expressionTree) {
		return addCheckConstraint(null, expressionTree);
	}	

	/**
	 * Adds a check constraint to the table.
	 * @param name A name for the check constraint (optional -- a version of this method exists without the requirement to specify a name).
	 * @param checkCondition The check constraint's condition.
	 * @return A reference to the Check object created as a result of the add
	 */
	public CheckConstraint addCheckConstraint(String name, CheckCondition checkCondition) {
		return addCheckConstraint(new CheckConstraint(name, this, checkCondition));
	}	
	
	/**
	 * Adds a check constraint to the table.
	 * @param checkCondition The check constraint's condition.
	 * @return A reference to the Check object created as a result of the add
	 */
	public CheckConstraint addCheckConstraint(CheckCondition checkCondition) {
		return addCheckConstraint(null, checkCondition);
	}
	
	/**
	 * Adds a check constraint to the table.
	 * @param checkConstraint The check constraint to add.
	 * @return A reference to the Check object created as a result of the add 
	 */
	protected CheckConstraint addCheckConstraint(CheckConstraint checkConstraint) {
		if (!checkConstraints.contains(checkConstraint)) {
			checkConstraints.add(checkConstraint);
		}
		return checkConstraint;
	}

	/**
	 * Removes a check constraint from the table.
	 * @param checkConstraint The check constraint to remove.
	 * @return True if the check constraint was already on the table and was successfully removed, else false.
	 */
	public boolean removeCheckConstraint(CheckConstraint checkConstraint) {
		return checkConstraints.remove(checkConstraint);
	}
	
	/**
	 * Returns a unmodifiable list of the check constraints on this table, in the order they were added to the table.
	 * @return A unmodifiable list of the check constraints on the table.
	 */
	public List<CheckConstraint> getCheckConstraints() {
		return Collections.unmodifiableList(checkConstraints);
	}	
	
	/**
	 * Adds a foreign key to the table.
	 * @param name The name of the foreign key (optional -- a version of this method exists without the need to specify this).
	 * @param columns The table column involved in the foreign key. 
	 * @param referenceTable The table which the foreign key column references.
	 * @param referenceColumn The column in the reference table involved in the foreign key. 
	 * @return A reference to the ForeignKey object created as a result of the add 
	 */		
	public ForeignKeyConstraint addForeignKeyConstraint(String name, Column column, Table referenceTable, Column referenceColumn) {
		return addForeignKeyConstraint(name, makeColumnList(column), referenceTable, makeColumnList(referenceColumn));
	}		
	
	/**
	 * Adds a foreign key to the table.
	 * @param columns The table column involved in the foreign key. 
	 * @param referenceTable The table which the foreign key column references.
	 * @param referenceColumn The column in the reference table involved in the foreign key. 
	 * @return A reference to the ForeignKey object created as a result of the add 
	 */	
	public ForeignKeyConstraint addForeignKeyConstraint(Column column, Table referenceTable, Column referenceColumn) {
		return addForeignKeyConstraint(null, column, referenceTable, referenceColumn);
	}			

	/**
	 * Adds a foreign key to the table.
	 * @param name The name of the foreign key (optional -- a version of this method exists without the need to specify this).	 * 
	 * @param column1 The first table column involved in the foreign key. 
	 * @param column2 The second table column involved in the foreign key.
	 * @param referenceTable The table which the foreign key column references.
	 * @param referenceColumn1 The first column in the reference table involved in the foreign key (paired with column1 in the original table) 
	 * @param referenceColumn2 The second column in the reference table involved in the foreign key (paired with column2 in the original table)
	 * @return A reference to the ForeignKey object created as a result of the add 
	 */		
	public ForeignKeyConstraint addForeignKeyConstraint(String name, 
														Column column1, Column column2, 
														Table referenceTable, Column referenceColumn1, Column referenceColumn2) {
		return addForeignKeyConstraint(name, 
							           makeColumnList(column1, column2), 
									   referenceTable, makeColumnList(referenceColumn1, referenceColumn2));
	}	

	/**
	 * Adds a foreign key to the table. 
	 * @param column1 The first table column involved in the foreign key. 
	 * @param column2 The second table column involved in the foreign key.
	 * @param referenceTable The table which the foreign key column references.
	 * @param referenceColumn1 The first column in the reference table involved in the foreign key (paired with column1 in the original table) 
	 * @param referenceColumn2 The second column in the reference table involved in the foreign key (paired with column2 in the original table)
	 * @return A reference to the ForeignKey object created as a result of the add 
	 */			
	public ForeignKeyConstraint addForeignKeyConstraint(Column column1, Column column2, 
														Table referenceTable, Column referenceColumn1, Column referenceColumn2) {
		return addForeignKeyConstraint(null, column1, column2, referenceTable, referenceColumn1, referenceColumn2);
	}		
	
	/**
	 * Adds a foreign key to the table.
	 * @param name The name of the foreign key (optional -- a version of this method exists without the need to specify this).	 * 
	 * @param columns The table columns involved in the foreign key. 
	 * @param referenceTable The table which the foreign key column references.
	 * @param referenceColumns The columns in the reference table involved in the foreign key (paired with each column -- in order -- in the original table) 
	 * @return A reference to the ForeignKey object created as a result of the add 
	 */		
	public ForeignKeyConstraint addForeignKeyConstraint(String name, List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
		return addForeignKeyConstraint(new ForeignKeyConstraint(name, this, columns, referenceTable, referenceColumns));
	}	

	/**
	 * Adds a foreign key to the table. 
	 * @param columns The table columns involved in the foreign key. 
	 * @param referenceTable The table which the foreign key column references.
	 * @param referenceColumns The columns in the reference table involved in the foreign key (paired with eacch column -- in order -- in the original table) 
	 * @return A reference to the ForeignKey object created as a result of the add 
	 */			
	public ForeignKeyConstraint addForeignKeyConstraint(List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
		return addForeignKeyConstraint(null, columns, referenceTable, referenceColumns);
	}		

	/**
	 * Adds a foreign key to the table.
	 * @param foreignKeyConstraint The foreign key to be added.
	 * @return A reference to the ForeignKey object created as a result of the add 
	 */
	protected ForeignKeyConstraint addForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) {
		if (!foreignKeyConstraints.contains(foreignKeyConstraint)) {
			foreignKeyConstraints.add(foreignKeyConstraint);
			
			// foreign keys cause dependencies, so any changes may require
			// a schema table list update
			schema.orderTables();
		}
		return foreignKeyConstraint;
	}
	
	/** 
	 * Removes a foreign key from the table.
	 * @param foreignKeyConstraint The foreign key to remove.
	 * @return True if the foreign key was defined for the table and was removed successfully, else false.
	 */
	public boolean removeForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) { 
		return foreignKeyConstraints.remove(foreignKeyConstraint);
	}	
	
	/**
	 * Returns an unmodifiable list of the foreign keys on this table, in the order they were created.
	 * @return An unmodifiable list of the foreign keys on this table.
	 */
	public List<ForeignKeyConstraint> getForeignKeyConstraints() {
		return Collections.unmodifiableList(foreignKeyConstraints);
	}
		
	/**
	 * Adds a not null constraint to the table.	
	 * @param name The name of this integrity constraint.
	 * @param column The column which is to be declared NOT NULL.
	 * @return The NotNull instance created as a result of the add.
	 */
	public NotNullConstraint addNotNullConstraint(String name, Column column) {
		return addNotNullConstraint(new NotNullConstraint(name, this, column));	
	}

	/**
	 * Adds a not null constraint to the table.	
	 * @param column The column which is to be declared NOT NULL.
	 * @return The NotNull instance created as a result of the add.
	 */	
	public NotNullConstraint addNotNullConstraint(Column column) {
		return addNotNullConstraint(null, column);	
	}	
	
	/**
	 * Adds a not null constraint to the table.
	 * @param notNullConstraint The not null constraint to add.
	 */
	protected NotNullConstraint addNotNullConstraint(NotNullConstraint notNullConstraint) {
		if (!notNullConstraints.contains(notNullConstraint)) {
			notNullConstraints.add(notNullConstraint);
		}
		return notNullConstraint;
	}
	
	/**
	 * Removes a not null constraint.
	 * @param column The column involving the NOT NULL constraint to remove.
	 * @return True if the not null constraint existed on the table and was removed, else false.
	 */
	public boolean removeNotNullConstraint(Column column) {
		return notNullConstraints.remove(new NotNullConstraint(null, this, column));
	}	
	
	/**
	 * Removes a not null constraint.
	 * @param notNullConstraint The constraint to remove.
	 * @return True if the not null constraint existed on the table and was removed, else false.
	 */
	public boolean removeNotNullConstraint(NotNullConstraint notNullConstraint) {
		return notNullConstraints.remove(notNullConstraint);
	}	
	
	/**
	 * Checks if a column has a not null constraint on it.
	 * @param column The column to check.
	 * @return True if a not null constraint exists on the table, else false.
	 */
	public boolean isNotNull(Column column) {
		return notNullConstraints.contains(new NotNullConstraint(null, this, column));
	}
		
	/**
	 * Returns a list of not null constraints on the table, in order of creation.
	 * @return A list of not null constraints on the table.
	 */
	public List<NotNullConstraint> getNotNullConstraints() {
		return Collections.unmodifiableList(notNullConstraints);
	}	

	/**
	 * Sets the primary key for the table.
	 * @param name The name of the primary key constraint (optional -- a version of this method exists without the requirement to specify a name).
	 * @param columns The primary key columns.
	 */	
	public void setPrimaryKeyConstraint(String name, List<Column> columns) {		
		setPrimaryKeyConstraint(new PrimaryKeyConstraint(name, this, columns));
	}
	
	/**
	 * Sets the primary key for the table.
	 * @param name The name of the primary key constraint (optional -- a version of this method exists without the requirement to specify a name).
	 * @param columns The primary key columns.
	 */	
	public void setPrimaryKeyConstraint(List<Column> columns) {		
		this.setPrimaryKeyConstraint(null, columns);
	}		
	
	/**
	 * Sets the primary key for the table.
	 * @param name The name of the primary key constraint (optional -- a version of this method exists without the requirement to specify a name).
	 * @param firstColumn The first (or only column) involved in the primary key.
	 * @param remainingColumns Any remaining columns involved in the primary key.
	 */	
	public void setPrimaryKeyConstraint(String name, Column firstColumn, Column... remainingColumns) {
		this.setPrimaryKeyConstraint(new PrimaryKeyConstraint(name, this, makeColumnList(firstColumn, remainingColumns)));
	}	
	
	/**
	 * Sets the primary key for the table.
	 * @param firstColumn The first (or only column) involved in the primary key.
	 * @param remainingColumns Any remaining columns involved in the primary key.
	 */		
	public void setPrimaryKeyConstraint(Column firstColumn, Column... remainingColumns) {		
		this.setPrimaryKeyConstraint(null, firstColumn, remainingColumns);
	}	
	
	/**
	 * Sets the primary key for the table.
	 * @param primaryKeyConstraint The primary key for the table.
	 */		
	protected void setPrimaryKeyConstraint(PrimaryKeyConstraint primaryKeyConstraint) {
		this.primaryKeyConstraint = primaryKeyConstraint;
	}

	/**
	 * Removes the primary key for this table.
	 */
	public void removePrimaryKeyConstraint() {
		primaryKeyConstraint = null;
	}
		
	/**
	 * Returns the primary key for the table (null if no primary key is set).
	 * @return The table's primary key (null if no primary key is set).
	 */
	public PrimaryKeyConstraint getPrimaryKeyConstraint() {
		return primaryKeyConstraint;
	}		

	/**
	 * Sets a compound column unique constraint on the table.
	 * @param name The name of the unique constraint (optional -- a version of this method exists without the need to set a name).
	 * @param columns The column set that should (as a set, rather than individually) be unique.
	 */	
	public UniqueConstraint addUniqueConstraint(String name, List<Column> columns) {
		return addUniqueConstraint(new UniqueConstraint(name, this, columns));
	}			

	/**
	 * Sets a compound column unique constraint on the table.
	 * @param columns The column set that should (as a set, rather than individually) be unique.
	 */	
	public UniqueConstraint addUniqueConstraint(List<Column> columns) {
		return addUniqueConstraint(null, columns);
	}		
	
	/**
	 * Sets a compound column unique constraint on the table.
	 * @param name The name of the unique constraint (optional -- a version of this method exists without the need to set a name).
	 * @param firstColumn The first (or only column) involved in the unique constraint.
	 * @param remainingColumns Any remaining columns involved in the unique constraint.
	 */	
	public UniqueConstraint addUniqueConstraint(String name, Column firstColumn, Column... remainingColumns) {
		return addUniqueConstraint(new UniqueConstraint(name, this, makeColumnList(firstColumn, remainingColumns)));
	}			

	/**
	 * Sets a compound column unique constraint on the table.
	 * @param firstColumn The first (or only column) involved in the unique constraint.
	 * @param remainingColumns Any remaining columns involved in the unique constraint.
	 */	
	public UniqueConstraint addUniqueConstraint(Column firstColumn, Column... remainingColumns) {
		return addUniqueConstraint(null, firstColumn, remainingColumns);
	}			
	
	/**
	 * Sets a unique constraint on the table.	
	 * @param uniqueConstraint The unique constraint to be added.
	 */
	protected UniqueConstraint addUniqueConstraint(UniqueConstraint uniqueConstraint) {
		if (!uniqueConstraints.contains(uniqueConstraint)) {
			uniqueConstraints.add(uniqueConstraint);
		}
		return uniqueConstraint;
	}

	/**
	 * Removes a unique constraint on the table.
	 * @param uniqueConstraint The unique constraint to be removed.
	 * @return True if the unique constraint existed for the table and was successfully removed, else false.
	 */
	public boolean removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		return uniqueConstraints.remove(uniqueConstraint);
	}	
	
	/**
	 * Returns the list of unique constraints on the table, in the order they were created.
	 * @return The list of unique constraints on the table.
	 */
	public List<UniqueConstraint> getUniqueConstraints() {
		return Collections.unmodifiableList(uniqueConstraints);
	}		
	
	/**
	 * Returns a list of all constraints on the table == primary keys followed by foreign keys, not nulls, uniques and finally check constraints.
	 * @return A list containing all the constraints on the table.
	 */
	public List<Constraint> getConstraints() {
		List<Constraint> constraints = new ArrayList<Constraint>();
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
	 * Gets a list of all tables connected to this table via some foreign key relationship (possibly involving intermediate tables).
	 * @return A list of connected tables.
	 */
	public List<Table> getConnectedTables() {
		List<Table> referencedTables = new ArrayList<Table>();
		
		List<Table> toVisit = new ArrayList<Table>();
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
	 * Gets the fully qualified name of the table in the form "<tt>schema_name.table_name</tt>".
	 * @return The fully qualified name of the table.
	 */
	public String getFullyQualifiedName() {
		return schema.getName() + "." + name;
	}	
	
	/**
	 * Return's the table name.
	 */
	public String toString() {
		return getName();
	}
	
	/**
	 * Tests whether this table object equals another object.
	 */
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Table) {
			Table other = (Table) obj;
			return this.getFullyQualifiedName().equals(other.getFullyQualifiedName());
		} else {
			return false;
		}
	}	
	
	/** 
	 * Copies the table to another schema.
	 * @param schema The schema to copy the table to.
	 * @return The copied table in schema.
	 */
	public Table copyTo(Schema schema) {
		Table copy = schema.createTable(name);
		
		for (Column column : columns) {
			column.copyTo(copy);
		}
				
		for (CheckConstraint checkConstraint : checkConstraints) {
			checkConstraint.copyTo(copy);
		}		
		
		for (ForeignKeyConstraint foreignKeyConstraint : foreignKeyConstraints) {
			foreignKeyConstraint.copyTo(copy);
		}		
		
		for (NotNullConstraint notNullConstraint : notNullConstraints) {
			notNullConstraint.copyTo(copy);
		}		
		
		if (primaryKeyConstraint != null) {
			primaryKeyConstraint.copyTo(copy);
		}

		for (UniqueConstraint uniqueConstraint : uniqueConstraints) {
			uniqueConstraint.copyTo(copy);
		}
		
		return copy;
	}
}
