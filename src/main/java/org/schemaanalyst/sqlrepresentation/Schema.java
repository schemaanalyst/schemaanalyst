package org.schemaanalyst.sqlrepresentation;

import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.util.collection.IdentifiableEntity;
import org.schemaanalyst.util.collection.IdentifiableEntitySet;

import java.util.*;

/**
 * Represents a database schema.
 * 
 * @author Phil McMinn
 * 
 */
public class Schema extends IdentifiableEntity {

	public static class Duplicator implements
			org.schemaanalyst.util.Duplicator<Schema> {

		public Schema duplicate(Schema schema) {
			return schema.duplicate();
		}
	}

    private static final long serialVersionUID = -212628589124201466L;

	private IdentifiableEntitySet<Table> tables;
	private List<PrimaryKeyConstraint> primaryKeyConstraints;
	private List<CheckConstraint> checkConstraints;
	private List<ForeignKeyConstraint> foreignKeyConstraints;
	private List<NotNullConstraint> notNullConstraints;
	private List<UniqueConstraint> uniqueConstraints;

	/**
	 * Constructs the schema.
	 * 
	 * @param name
	 *            the name of the schema.
	 */
	public Schema(String name) {
		if (name == null) {
			throw new SQLRepresentationException("Schema names cannot be null");
		}
		setName(name);
		this.tables = new IdentifiableEntitySet<>();

		primaryKeyConstraints = new LinkedList<>();
		checkConstraints = new LinkedList<>();
		foreignKeyConstraints = new LinkedList<>();
		notNullConstraints = new LinkedList<>();
		uniqueConstraints = new LinkedList<>();
	}

	/**
	 * Creates a table and adds it to the schema, returning the table created.
	 * 
	 * @param name
	 *            the name of the table to be added.
	 */
	public Table createTable(String name) {
		Table table = new Table(name);
		addTable(table);
		return table;
	}

	/**
	 * Adds a table to the schema.
	 * 
	 * @param table
	 *            The table to be added.
	 */
	public void addTable(Table table) {
		boolean success = tables.add(table);
		if (!success) {
			throw new SQLRepresentationException("Table " + table
					+ " already exists in this schema");
		}
	}

	/**
	 * Looks up a table by its name, returning null if the table was not found.
	 * 
	 * @param name
	 *            the name of the table.
	 * @return the table, or null if no table was found for the name.
	 */
	public Table getTable(String name) {
		return tables.get(name);
	}

	/**
	 * Checks if a table exists in the schema.
	 * 
	 * @param name
	 *            the name of the table.
	 * @return true if the table exists, else null.
	 */
	public boolean hasTable(String name) {
		return tables.contains(name);
	}

	/**
	 * Returns a list of tables in the order they were added to the schema.
	 * 
	 * @return a list of tables that the schema contains.
	 */
	public List<Table> getTables() {
		return tables.toList();
	}

	/**
	 * Returns a list of tables that the schema contains. Tables referenced via
	 * foreign keys appear in the list before the tables that reference them.
	 * 
	 * @return a list of tables that the schema contains.
	 */
	public List<Table> getTablesInOrder() {
		return new TableDependencyOrderer().order(getTables(), this);
	}

	/**
	 * Returns a list of tables in the schema, in reverse order to
	 * getTablesInOrder.
	 * 
	 * @return a list of tables that the schema contains (in reverse order to
	 *         getTablesInOrder).
	 */
	public List<Table> getTablesInReverseOrder() {
		return new TableDependencyOrderer().reverseOrder(getTables(), this);
	}

	/**
	 * Gets a list of all tables transitively connected to a table via a
	 * <tt>FOREIGN KEY</tt> relationship.
	 * 
	 * @param table
	 *            the table for which to get connected tables.
	 * @return a list of connected tables.
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
	 * 
	 * @return a deep copy of the schema.
	 */
	public Schema duplicate() {
		Schema duplicateSchema = new Schema(getName());

		// tables
		for (Table table : tables) {
			duplicateSchema.addTable(table.duplicate());
		}

		for (Table table : tables) {
			Table duplicateTable = duplicateSchema.getTable(table.getName());

			// PRIMARY KEY
			if (hasPrimaryKeyConstraint(table)) {
				PrimaryKeyConstraint duplicatePrimaryKeyConstraint = getPrimaryKeyConstraint(
						table).duplicate();
				duplicatePrimaryKeyConstraint.remap(duplicateTable);
				duplicateSchema
						.setPrimaryKeyConstraint(duplicatePrimaryKeyConstraint);
			}

			// CHECK constraints
			for (CheckConstraint checkConstraint : getCheckConstraints(table)) {
				CheckConstraint duplicateCheckConstraint = checkConstraint
						.duplicate();
				duplicateCheckConstraint.remap(duplicateTable);
				duplicateSchema.addCheckConstraint(duplicateCheckConstraint);
			}

			// FOREIGN KEY constraints
			for (ForeignKeyConstraint foreignKeyConstraint : getForeignKeyConstraints(table)) {
				Table referenceTable = foreignKeyConstraint.getReferenceTable();
				Table duplicateReferenceTable = duplicateSchema
						.getTable(referenceTable.getName());

				ForeignKeyConstraint duplicateForeignKeyConstraint = foreignKeyConstraint
						.duplicate();

				duplicateForeignKeyConstraint.remap(duplicateTable,
						duplicateReferenceTable);

				duplicateSchema
						.addForeignKeyConstraint(duplicateForeignKeyConstraint);
			}

			// NOT NULL constraints
			for (NotNullConstraint notNullConstraint : getNotNullConstraints(table)) {
				NotNullConstraint duplicateNotNullConstraint = notNullConstraint
						.duplicate();
				duplicateNotNullConstraint.remap(duplicateTable);
				duplicateSchema
						.addNotNullConstraint(duplicateNotNullConstraint);
			}

			// UNIQUE constraints
			for (UniqueConstraint uniqueConstraint : getUniqueConstraints(table)) {
				UniqueConstraint duplicateUniqueConstraint = uniqueConstraint
						.duplicate();
				duplicateUniqueConstraint.remap(duplicateTable);
				duplicateSchema.addUniqueConstraint(duplicateUniqueConstraint);
			}
		}

		return duplicateSchema;
	}

	/**
	 * Creates a <tt>CHECK</tt> constraint and adds it to the schema.
	 * 
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param expression
	 *            the expression underpinning the constraint.
	 */
	public CheckConstraint createCheckConstraint(Table table,
			Expression expression) {
		CheckConstraint checkConstraint = new CheckConstraint(table, expression);
		addCheckConstraint(checkConstraint);
		return checkConstraint;
	}

	/**
	 * Creates a <tt>CHECK</tt> constraint and adds it to the schema.
	 * 
	 * @param name
	 *            the name of the constraint.
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param expression
	 *            the expression underpinning the constraint.
	 */
	public CheckConstraint createCheckConstraint(String name, Table table,
			Expression expression) {
		CheckConstraint checkConstraint = new CheckConstraint(name, table,
				expression);
		addCheckConstraint(checkConstraint);
		return checkConstraint;
	}

	/**
	 * Adds a <tt>CHECK</tt> constraint to the schema.
	 * 
	 * @param constraint
	 *            the <tt>CHECK</tt> constraint to be added.
	 */
	public void addCheckConstraint(CheckConstraint constraint) {
		Table table = constraint.getTable();
		if (!tables.contains(table)) {
			throw new SQLRepresentationException("No such table \"" + table
					+ "\" in this schema for constraint");
		}

		checkConstraints.add(constraint);
	}

	/**
	 * Removes a <tt>CHECK</tt> constraint from the schema.
	 * 
	 * @param constraint
	 *            the <tt>CHECK</tt> constraint to remove.
	 * @return true if the <tt>CHECK</tt> constraint was already on the table
	 *         and was successfully removed, else false.
	 */
	public boolean removeCheckConstraint(CheckConstraint constraint) {
		return checkConstraints.remove(constraint);
	}

	/**
	 * Returns a list of the <tt>CHECK</tt> constraints on a table, in the order
	 * they were added to the table.
	 * 
	 * @param table
	 *            the table for which the constraints are required.
	 * @return a list of the <tt>CHECK</tt> constraints on the table.
	 */
	public List<CheckConstraint> getCheckConstraints(Table table) {
		return getConstraints(table, checkConstraints);
	}

	/**
	 * Returns a list of all <tt>CHECK</tt> constraints defined on a schema.
	 * 
	 * @return a list of the <tt>CHECK</tt> constraints for this schema.
	 */
	public List<CheckConstraint> getCheckConstraints() {
		return new ArrayList<>(checkConstraints);
	}

	/**
	 * Creates a <tt>FOREIGN KEY</tt> constraint and adds it to the table.
	 * 
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param column
	 *            the column of this table that is the subject of the
	 *            <tt>FOREIGN KEY</tt>.
	 * @param referenceTable
	 *            the table being referenced.
	 * @param referenceColumn
	 *            the column in the reference table.
	 */
	public ForeignKeyConstraint createForeignKeyConstraint(Table table,
			Column column, Table referenceTable, Column referenceColumn) {
		ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
				table, column, referenceTable, referenceColumn);
		addForeignKeyConstraint(foreignKeyConstraint);
		return foreignKeyConstraint;
	}

	/**
	 * Creates a <tt>FOREIGN KEY</tt> constraint and adds it to the table.
	 * 
	 * @param name
	 *            the name of the constraint.
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param columns
	 *            the list of columns that are the subject of the
	 *            <tt>FOREIGN KEY</tt>.
	 * @param referenceTable
	 *            the table being referenced.
	 * @param referenceColumns
	 *            the list of column in the reference table.
	 */
	public ForeignKeyConstraint createForeignKeyConstraint(String name,
			Table table, List<Column> columns, Table referenceTable,
			List<Column> referenceColumns) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
				name, table, columns, referenceTable, referenceColumns);
		addForeignKeyConstraint(foreignKeyConstraint);
		return foreignKeyConstraint;
	}

	/**
	 * Creates a <tt>FOREIGN KEY</tt> constraint and adds it to the table.
	 * 
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param columns
	 *            the list of columns that are the subject of the
	 *            <tt>FOREIGN KEY</tt>.
	 * @param referenceTable
	 *            the table being referenced.
	 * @param referenceColumns
	 *            the list of column in the reference table.
	 */
	public ForeignKeyConstraint createForeignKeyConstraint(Table table,
			List<Column> columns, Table referenceTable,
			List<Column> referenceColumns) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
				table, columns, referenceTable, referenceColumns);
		addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
	}

	/**
	 * Creates a <tt>FOREIGN KEY</tt> constraint and adds it to the table.
	 * 
	 * @param name
	 *            the name of the constraint.
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param column
	 *            the column of this table that is the subject of the FOREIGN
	 *            KEY.
	 * @param referenceTable
	 *            the table being referenced.
	 * @param referenceColumn
	 *            the column in the reference table.
	 */
	public ForeignKeyConstraint createForeignKeyConstraint(String name,
			Table table, Column column, Table referenceTable,
			Column referenceColumn) {
		ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
				name, table, column, referenceTable, referenceColumn);
		addForeignKeyConstraint(foreignKeyConstraint);
		return foreignKeyConstraint;
	}

	/**
	 * Adds a <tt>FOREIGN KEY</tt> to the table.
	 * 
	 * @param constraint
	 *            The <tt>FOREIGN KEY</tt> constraint to be added.
	 */
	public void addForeignKeyConstraint(ForeignKeyConstraint constraint) {
		Table table = constraint.getTable();
		if (!tables.contains(table)) {
			throw new SQLRepresentationException("No such table \"" + table
					+ "\" in this schema for constraint");
		}
                if (foreignKeyConstraints.contains(constraint)) {
                    foreignKeyConstraints.remove(constraint);
                }
		foreignKeyConstraints.add(constraint);
	}

	/**
	 * Removes a <tt>FOREIGN KEY</tt> from the table.
	 * 
	 * @param constraint
	 *            the foreign key to remove.
	 * @return true if the foreign key was defined for the table and was removed
	 *         successfully, else false.
	 */
	public boolean removeForeignKeyConstraint(ForeignKeyConstraint constraint) {
		return foreignKeyConstraints.remove(constraint);
	}

	/**
	 * Returns a list of the <tt>FOREIGN KEY</tt>s on this table, in the order
	 * they were created.
	 * 
	 * @param table
	 *            the table for which the constraints are required.
	 * @return a list of the <tt>FOREIGN KEY</tt>s defined on table.
	 */
	public List<ForeignKeyConstraint> getForeignKeyConstraints(Table table) {
		return getConstraints(table, foreignKeyConstraints);
	}

	/**
	 * Returns a list of all <tt>FOREIGN KEY</tt>s defined on the schema.
	 * 
	 * @return a list of the <tt>FOREIGN KEY</tt>s on this schema.
	 */
	public List<ForeignKeyConstraint> getForeignKeyConstraints() {
		return new ArrayList<>(foreignKeyConstraints);
	}

	/**
	 * Creates a <tt>NOT NULL</tt> constraint and adds it to the table.
	 * 
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param column
	 *            the column to be the subject of the constraint.
	 * @return the <tt>NOT NULL</tt> constraint created.
	 */
	public NotNullConstraint createNotNullConstraint(Table table, Column column) {
		NotNullConstraint notNullConstraint = new NotNullConstraint(table,
				column);
		addNotNullConstraint(notNullConstraint);
		return notNullConstraint;
	}

	/**
	 * Creates a <tt>NOT NULL</tt> constraint and adds it to the table.
	 * 
	 * @param constraintName
	 *            the name of the constraint.
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param column
	 *            the column to be the subject of the constraint.
	 * @return the <tt>NOT NULL</tt> constraint created.
	 */
	public NotNullConstraint createNotNullConstraint(String constraintName,
			Table table, Column column) {
		NotNullConstraint notNullConstraint = new NotNullConstraint(
				constraintName, table, column);
		addNotNullConstraint(notNullConstraint);
		return notNullConstraint;
	}

	/**
	 * Adds a <tt>NOT NULL</tt> constraint to the table.
	 * 
	 * @param constraint
	 *            the <tt>NOT NULL</tt> constraint to add.
	 */
	public void addNotNullConstraint(NotNullConstraint constraint) {
		Table table = constraint.getTable();
		if (!tables.contains(table)) {
			throw new SQLRepresentationException("No such table \"" + table
					+ "\" in this schema for constraint");
		}
		notNullConstraints.add(constraint);
	}

	/**
	 * Removes a <tt>NOT NULL</tt> constraint.
	 * 
	 * @param constraint
	 *            the constraint to remove.
	 * @return true if the <tt>NOT NULL</tt> constraint existed on the table and
	 *         was removed, else false.
	 */
	public boolean removeNotNullConstraint(NotNullConstraint constraint) {
		return notNullConstraints.remove(constraint);
	}

	/**
	 * Returns a list of <tt>NOT NULL</tt> constraints on the table, in order of
	 * creation.
	 * 
	 * @param table
	 *            the table for which the constraints are required.
	 * @return a list of <tt>NOT NULL</tt> constraints on the table.
	 */
	public List<NotNullConstraint> getNotNullConstraints(Table table) {
		return getConstraints(table, notNullConstraints);
	}

	/**
	 * Returns a list of all <tt>NOT NULL</tt> constraints defined on the
	 * schema.
	 * 
	 * @return a list of the <tt>NOT NULL</tt>s on this schema.
	 */
	public List<NotNullConstraint> getNotNullConstraints() {
		return new ArrayList<>(notNullConstraints);
	}

	/**
	 * Creates a <tt>PRIMARY KEY</tt> and sets it on the table
	 * 
	 * @param columns
	 *            the <tt>PRIMARY KEY</tt> columns.
	 * @return the <tt>PRIMARY KEY</tt> constraint created.
	 */
	public PrimaryKeyConstraint createPrimaryKeyConstraint(Table table,
			Column... columns) {
		PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(
				table, columns);
		setPrimaryKeyConstraint(primaryKeyConstraint);
		return primaryKeyConstraint;
	}

	/**
	 * Creates a <tt>PRIMARY KEY</tt> and sets it on the table
	 * 
	 * @param name
	 *            the name of the constraint.
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param columns
	 *            the <tt>PRIMARY KEY</tt> columns.
	 * @return the <tt>PRIMARY KEY</tt> constraint created.
	 */
	public PrimaryKeyConstraint createPrimaryKeyConstraint(String name,
			Table table, Column... columns) {
		PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(
				name, table, columns);
		setPrimaryKeyConstraint(primaryKeyConstraint);
		return primaryKeyConstraint;
	}

	/**
	 * Creates a <tt>PRIMARY KEY</tt> and sets it on the table
	 * 
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param columns
	 *            the <tt>PRIMARY KEY</tt> columns.
	 * @return the <tt>PRIMARY KEY</tt> constraint created.
	 */
	public PrimaryKeyConstraint createPrimaryKeyConstraint(Table table,
			List<Column> columns) {
		PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(
				table, columns);
		setPrimaryKeyConstraint(primaryKeyConstraint);
		return primaryKeyConstraint;
	}

	/**
	 * Creates a <tt>PRIMARY KEY</tt> and sets it on the table
	 * 
	 * @param name
	 *            the name of the constraint.
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param columns
	 *            the <tt>PRIMARY KEY</tt> columns.
	 * @return the <tt>PRIMARY KEY</tt> constraint created.
	 */
	public PrimaryKeyConstraint createPrimaryKeyConstraint(String name,
			Table table, List<Column> columns) {
		PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(
				name, table, columns);
		setPrimaryKeyConstraint(primaryKeyConstraint);
		return primaryKeyConstraint;
	}

	/**
	 * Sets the <tt>PRIMARY KEY</tt> for the table defined in the constraint.
	 * 
	 * @param constraint
	 *            The <tt>PRIMARY KEY</tt> for the table.
	 */
	public void setPrimaryKeyConstraint(PrimaryKeyConstraint constraint) {
		Table table = constraint.getTable();
		if (!tables.contains(table)) {
			throw new SQLRepresentationException("No such table \"" + table
					+ "\" in this schema for constraint");
		}
		removePrimaryKeyConstraint(table);
		primaryKeyConstraints.add(constraint);
	}

	/**
	 * Removes the <tt>PRIMARY KEY</tt> for a table.
	 * 
	 * @param table
	 *            The table whose <tt>PRIMARY KEY</tt> is to be removed.
	 */
	public void removePrimaryKeyConstraint(Table table) {
		if (!tables.contains(table)) {
			throw new SQLRepresentationException("No such table \"" + table
					+ "\" in this schema for constraint");
		}
		ListIterator<PrimaryKeyConstraint> iterator = primaryKeyConstraints
				.listIterator();
		while (iterator.hasNext()) {
			PrimaryKeyConstraint primaryKeyConstraint = iterator.next();
			if (table.equals(primaryKeyConstraint.getTable())) {
				iterator.remove();
				return;
			}
		}
	}

	/**
	 * Returns the <tt>PRIMARY KEY</tt> for a table (null if no
	 * <tt>PRIMARY KEY</tt> is set).
	 * 
	 * @param table
	 *            the table whose <tt>PRIMARY KEY</tt> is sought.
	 * @return the table's <tt>PRIMARY KEY</tt> (null if no <tt>PRIMARY KEY</tt>
	 *         is set).
	 */
	public PrimaryKeyConstraint getPrimaryKeyConstraint(Table table) {
		if (!tables.contains(table)) {
			throw new SQLRepresentationException("No such table \"" + table
					+ "\" in this schema for constraint");
		}
		for (PrimaryKeyConstraint constraint : primaryKeyConstraints) {
			if (table.equals(constraint.getTable())) {
				return constraint;
			}
		}
		return null;
	}

	/**
	 * Returns a list of all the <tt>PRIMARY KEY</tt> constraints for this
	 * schema.
	 * 
	 * @return a list of all the <tt>PRIMARY KEY</tt> constraints for this
	 *         schema.
	 */
	public List<PrimaryKeyConstraint> getPrimaryKeyConstraints() {
		return new ArrayList<>(primaryKeyConstraints);
	}

	/**
	 * Checks whether a <tt>PRIMARY KEY</tt> is set on the table.
	 * 
	 * @param table
	 *            the table to check.
	 * @return true if a <tt>PRIMARY KEY</tt> is set, else false.
	 */
	public boolean hasPrimaryKeyConstraint(Table table) {
		if (!tables.contains(table)) {
			throw new SQLRepresentationException("No such table \"" + table
					+ "\" in this schema for constraint");
		}
		for (PrimaryKeyConstraint primaryKeyConstraint : primaryKeyConstraints) {
			if (table.equals(primaryKeyConstraint.getTable())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a <tt>UNIQUE</tt> constraint and sets it on the table
	 * 
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param columns
	 *            the <tt>UNIQUE</tt> columns.
	 * @return the <tt>UNIQUE</tt> constraint created.
	 */
	public UniqueConstraint createUniqueConstraint(Table table,
			Column... columns) {
		UniqueConstraint UniqueConstraint = new UniqueConstraint(table, columns);
		addUniqueConstraint(UniqueConstraint);
		return UniqueConstraint;
	}

	/**
	 * Creates a <tt>UNIQUE</tt> constraint and sets it on the table
	 * 
	 * @param constraintName
	 *            the name of the constraint.
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param columns
	 *            the <tt>UNIQUE</tt> columns.
	 * @return the <tt>UNIQUE</tt> constraint created.
	 */
	public UniqueConstraint createUniqueConstraint(String constraintName,
			Table table, Column... columns) {
		UniqueConstraint UniqueConstraint = new UniqueConstraint(
				constraintName, table, columns);
		addUniqueConstraint(UniqueConstraint);
		return UniqueConstraint;
	}

	/**
	 * Creates a <tt>UNIQUE</tt> constraint and sets it on the table
	 * 
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param columns
	 *            the <tt>UNIQUE</tt> columns.
	 * @return the <tt>UNIQUE</tt> constraint created.
	 */
	public UniqueConstraint createUniqueConstraint(Table table,
			List<Column> columns) {
		UniqueConstraint UniqueConstraint = new UniqueConstraint(table, columns);
		addUniqueConstraint(UniqueConstraint);
		return UniqueConstraint;
	}

	/**
	 * Creates a <tt>UNIQUE</tt> constraint and sets it on the table
	 * 
	 * @param name
	 *            the name of the constraint.
	 * @param table
	 *            the table on which the constraint is to be applied.
	 * @param columns
	 *            the <tt>UNIQUE</tt> columns.
	 * @return the <tt>UNIQUE</tt> constraint created.
	 */
	public UniqueConstraint createUniqueConstraint(String name, Table table,
			List<Column> columns) {
		UniqueConstraint UniqueConstraint = new UniqueConstraint(name, table,
				columns);
		addUniqueConstraint(UniqueConstraint);
		return UniqueConstraint;
	}

	/**
	 * Sets a <tt>UNIQUE</tt> constraint on the table.
	 * 
	 * @param constraint
	 *            The <tt>UNIQUE</tt> constraint to be added.
	 */
	public void addUniqueConstraint(UniqueConstraint constraint) {
		Table table = constraint.getTable();
		if (!tables.contains(table)) {
			throw new SQLRepresentationException("No such table \"" + table
					+ "\" in this schema for constraint");
		}

		uniqueConstraints.add(constraint);
	}

	/**
	 * Removes a <tt>UNIQUE</tt> constraint on the table.
	 * 
	 * @param constraint
	 *            the unique constraint to be removed.
	 * @return true if the unique constraint existed for the table and was
	 *         successfully removed, else false.
	 */
	public boolean removeUniqueConstraint(UniqueConstraint constraint) {
		boolean found = false;
		for (Iterator<UniqueConstraint> iter = uniqueConstraints.iterator(); iter
				.hasNext();) {
			UniqueConstraint cons = iter.next();
			if (cons.equals(constraint)) {
				found = true;
				iter.remove();
			}
		}
		return found;
	}

	/**
	 * Returns the list of <tt>UNIQUE</tt> constraints on the table, in the
	 * order they were created.
	 *
	 * @param table
	 *            The table whose constraints are sought.
	 * @return The list of <tt>UNIQUE</tt> constraints on the table.
	 */
	public List<UniqueConstraint> getUniqueConstraints(Table table) {
		return getConstraints(table, uniqueConstraints);
	}

	/**
	 * Returns a list of all <tt>UNIQUE</tt> constraints defined on the schema.
	 * 
	 * @return a list of the <tt>UNIQUE</tt> constraints on this schema.
	 */
	public List<UniqueConstraint> getUniqueConstraints() {
		return new ArrayList<>(uniqueConstraints);
	}

    /**
	 * Returns a list of all constraints for a table.
     * @param table The table whose constraints are sought.
	 *
	 * @return The list of constraints defined on the table.
	 */
	public List<Constraint> getConstraints(Table table) {
		List<Constraint> constraints = new ArrayList<>();
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
     * Gets the number of constraints for a table
     * @param table The table for which the number of constraints are sought.
     *
     * @return The number of constraints defined for the table.
     */
    public int getNumConstraints(Table table) {
        return getConstraints(table).size();
    }

	/**
	 * Returns a list of all constraints defined on the schema.
	 * 
	 * @return A list of all constraints.
	 */
	public List<Constraint> getConstraints() {
		List<Constraint> constraints = new ArrayList<>();
		constraints.addAll(primaryKeyConstraints);
		constraints.addAll(checkConstraints);
		constraints.addAll(foreignKeyConstraints);
		constraints.addAll(notNullConstraints);
		constraints.addAll(uniqueConstraints);
		return constraints;
	}

	/**
	 * Returns a list of constraints on a table, in order of creation, from a
	 * set.
	 * 
	 * @param table
	 *            the table for which the constraints are required.
	 * @param constraintSet
	 *            the set from which to take the constraints from for the table.
	 * @return a list of constraints defined on the table.
	 */
	private <C extends Constraint> List<C> getConstraints(Table table,
			List<C> constraintSet) {
		if (!tables.contains(table)) {
			throw new SQLRepresentationException("No such table \"" + table
					+ "\" in this schema for constraint");
		}

		List<C> constraintsForTable = new ArrayList<>();
		for (C constraint : constraintSet) {
			if (constraint.getTable().equals(table)) {
				constraintsForTable.add(constraint);
			}
		}
		return constraintsForTable;
	}

	/**
	 * Tests whether the provided set of columns are currently part of a UNIQUE
	 * constraint in the given table
	 * 
	 * @param table
	 *            The table
	 * @param column
	 *            The columns
	 * @return Whether they have UNIQUE
	 */
	public boolean isUnique(Table table, Column... column) {
		boolean found = false;
		HashSet<Column> columns = new HashSet<>(Arrays.asList(column));
		for (UniqueConstraint uniqueConstraint : getUniqueConstraints(table)) {
			HashSet<Column> ucColumns = new HashSet<>(
					uniqueConstraint.getColumns());
			if (columns.size() == ucColumns.size()
					&& columns.containsAll(ucColumns)) {
				found = true;
				break;
			}
		}
		return found;
	}

	/**
	 * Tests whether the provided column currently has a NOT NULL constraint
	 * 
	 * @param table
	 *            The table
	 * @param column
	 *            The column
	 * @return Whether it has NOT NULL
	 */
	public boolean isNotNull(Table table, Column column) {
		boolean found = false;
		for (NotNullConstraint constraint : getNotNullConstraints(table)) {
			if (constraint.getColumn() == column) {
				found = true;
				break;
			}
		}
		return found;
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Schema)) {
			return false;
		}
		Schema other = (Schema) obj;
		if (checkConstraints == null) {
			if (other.checkConstraints != null) {
				return false;
			}
		} else if (!checkConstraints.equals(other.checkConstraints)) {
			return false;
		}
		if (foreignKeyConstraints == null) {
			if (other.foreignKeyConstraints != null) {
				return false;
			}
		} else if (!foreignKeyConstraints.equals(other.foreignKeyConstraints)) {
			return false;
		}
		if (notNullConstraints == null) {
			if (other.notNullConstraints != null) {
				return false;
			}
		} else if (!notNullConstraints.equals(other.notNullConstraints)) {
			return false;
		}
		if (primaryKeyConstraints == null) {
			if (other.primaryKeyConstraints != null) {
				return false;
			}
		} else if (!primaryKeyConstraints.equals(other.primaryKeyConstraints)) {
			return false;
		}
		if (tables == null) {
			if (other.tables != null) {
				return false;
			}
		} else if (!tables.equals(other.tables)) {
			return false;
		}
		if (uniqueConstraints == null) {
			if (other.uniqueConstraints != null) {
				return false;
			}
		} else if (!uniqueConstraints.equals(other.uniqueConstraints)) {
			return false;
		}
		return true;
	}
}
