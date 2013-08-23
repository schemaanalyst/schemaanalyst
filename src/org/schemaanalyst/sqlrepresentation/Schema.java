package org.schemaanalyst.sqlrepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.util.Duplicable;
import org.schemaanalyst.util.collection.IdentifiableEntity;
import org.schemaanalyst.util.collection.IdentifiableEntitySet;

/**
 * Represents a database schema.
 * 
 * @author Phil McMinn
 * 
 */
public class Schema extends IdentifiableEntity implements Serializable,
        Duplicable<Schema> {

    private static final long serialVersionUID = 7338170433995168952L;
    private IdentifiableEntitySet<Table> tables;

    private List<PrimaryKeyConstraint> primaryKeyConstraints;
    private LinkedHashSet<CheckConstraint> checkConstraints;
    private LinkedHashSet<ForeignKeyConstraint> foreignKeyConstraints;
    private LinkedHashSet<NotNullConstraint> notNullConstraints;
    private LinkedHashSet<UniqueConstraint> uniqueConstraints;

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

        primaryKeyConstraints = new ArrayList<>();
        checkConstraints = new LinkedHashSet<>();
        foreignKeyConstraints = new LinkedHashSet<>();
        notNullConstraints = new LinkedHashSet<>();
        uniqueConstraints = new LinkedHashSet<>();
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
     * Looks up a table by its name (ignoring case), returning null if the table
     * was not found.
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
     * Returns a list of tables in th)e schema, in reverse order to
     * getTablesInOrder.
     * 
     * @return a list of tables that the schema contains (in reverse order to
     *         getTablesInOrder).
     */
    public List<Table> getTablesInReverseOrder() {
        return new TableDependencyOrderer().reverseOrder(getTables(), this);
    }

    /**
     * Gets a list of all tables transitively connected to a table via a FOREIGN
     * KEY relationship.
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

            // UNIQUE constraints
            for (ForeignKeyConstraint foreignKeyConstraint : getForeignKeyConstraints(table)) {
                ForeignKeyConstraint duplicateForeignKeyConstraint = foreignKeyConstraint
                        .duplicate();
                duplicateForeignKeyConstraint.remap(duplicateTable);
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
     * Creates a CHECK constraint and adds it to the schema.
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
     * Creates a CHECK constraint and adds it to the schema.
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
     * Adds a CHECK constraint to the schema.
     * 
     * @param constraint
     *            the CHECK constraint to be added.
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
     * Removes a CHECK constraint from the schema.
     * 
     * @param constraint
     *            the CHECK constraint to remove.
     * @return true if the CHECK constraint was already on the table and was
     *         successfully removed, else false.
     */
    public boolean removeCheckConstraint(CheckConstraint constraint) {
        return checkConstraints.remove(constraint);
    }

    /**
     * Returns a list of the CHECK constraints on a table, in the order they
     * were added to the table.
     * 
     * @param table
     *            the table for which the constraints are required.
     * @return a list of the CHECK constraints on the table.
     */
    public List<CheckConstraint> getCheckConstraints(Table table) {
        return getConstraints(table, checkConstraints);
    }

    /**
     * Returns a list of all CHECK constraints defined on a schema.
     * 
     * @param table
     *            the table for which the constraints are required.
     * @return a list of the CHECK constraints for this schema.
     */
    public List<CheckConstraint> getCheckConstraints() {
        return new ArrayList<>(checkConstraints);
    }

    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * 
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
    public ForeignKeyConstraint createForeignKeyConstraint(Table table,
            Column column, Table referenceTable, Column referenceColumn) {
        ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(
                table, column, referenceTable, referenceColumn);
        addForeignKeyConstraint(foreignKeyConstraint);
        return foreignKeyConstraint;
    }

    /**
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * 
     * @param name
     *            the name of the constraint.
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the list of columns that are the subject of the FOREIGN KEY.
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
     * Creates a FOREIGN KEY constraint and adds it to the table.
     * 
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the list of columns that are the subject of the FOREIGN KEY.
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
     * Creates a FOREIGN KEY constraint and adds it to the table.
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
     * Adds a FOREIGN KEY to the table.
     * 
     * @param foreignKeyConstraint
     *            The FOREIGN KEY to be added.
     */
    public void addForeignKeyConstraint(ForeignKeyConstraint constraint) {
        Table table = constraint.getTable();
        if (!tables.contains(table)) {
            throw new SQLRepresentationException("No such table \"" + table
                    + "\" in this schema for constraint");
        }
        foreignKeyConstraints.add(constraint);
    }

    /**
     * Removes a FOREIGN KEY from the table.
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
     * Returns a list of the FOREIGN KEYs on this table, in the order they were
     * created.
     * 
     * @param table
     *            the table for which the constraints are required.
     * @return a list of the FOREIGN KEYs defined on table.
     */
    public List<ForeignKeyConstraint> getForeignKeyConstraints(Table table) {
        return getConstraints(table, foreignKeyConstraints);
    }

    /**
     * Returns a list of all FOREIGN KEYs defined on the schema.
     * 
     * @param table
     *            the table for which the constraints are required.
     * @return a list of the FOREIGN KEYs on this schema.
     */
    public List<ForeignKeyConstraint> getForeignKeyConstraints() {
        return new ArrayList<>(foreignKeyConstraints);
    }

    /**
     * Creates a NOT NULL constraint and adds it to the table.
     * 
     * @param table
     *            the table on which the constraint is to be applied.
     * @param column
     *            the column to be the subject of the constraint.
     * @return the NOT NULL constraint created.
     */
    public NotNullConstraint createNotNullConstraint(Table table, Column column) {
        NotNullConstraint notNullConstraint = new NotNullConstraint(table,
                column);
        addNotNullConstraint(notNullConstraint);
        return notNullConstraint;
    }

    /**
     * Creates a NOT NULL constraint and adds it to the table.
     * 
     * @param constraintName
     *            the name of the constraint.
     * @param table
     *            the table on which the constraint is to be applied.
     * @param column
     *            the column to be the subject of the constraint.
     * @return the NOT NULL constraint created.
     */
    public NotNullConstraint createNotNullConstraint(String constraintName,
            Table table, Column column) {
        NotNullConstraint notNullConstraint = new NotNullConstraint(
                constraintName, table, column);
        addNotNullConstraint(notNullConstraint);
        return notNullConstraint;
    }

    /**
     * Adds a NOT NULL constraint to the table.
     * 
     * @param notNullConstraint
     *            the NOT NULL constraint to add.
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
     * Removes a NOT NULL constraint.
     * 
     * @param constraint
     *            the constraint to remove.
     * @return true if the NOT NULL constraint existed on the table and was
     *         removed, else false.
     */
    public boolean removeNotNullConstraint(NotNullConstraint constraint) {
        return notNullConstraints.remove(constraint);
    }

    /**
     * Returns a list of NOT NULL constraints on the table, in order of
     * creation.
     * 
     * @param table
     *            the table for which the constraints are required.
     * @return a list of NOT NULL constraints on the table.
     */
    public List<NotNullConstraint> getNotNullConstraints(Table table) {
        return getConstraints(table, notNullConstraints);
    }

    /**
     * Returns a list of all NOT NULL constraints defined on the schema.
     * 
     * @param table
     *            the table for which the constraints are required.
     * @return a list of the NOT NULLs on this schema.
     */
    public List<NotNullConstraint> getAllNotNullConstraints() {
        return new ArrayList<>(notNullConstraints);
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * 
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the PRIMARY KEY columns.
     * @return the PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(Table table,
            Column... columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(
                table, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * 
     * @param name
     *            the name of the constraint.
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the PRIMARY KEY columns.
     * @return the PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(String name,
            Table table, Column... columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(
                name, table, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * 
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the PRIMARY KEY columns.
     * @return the PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(Table table,
            List<Column> columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(
                table, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }

    /**
     * Creates a PRIMARY KEY and sets it on the table
     * 
     * @param name
     *            the name of the constraint.
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the PRIMARY KEY columns.
     * @return the PRIMARY KEY constraint created.
     */
    public PrimaryKeyConstraint createPrimaryKeyConstraint(String name,
            Table table, List<Column> columns) {
        PrimaryKeyConstraint primaryKeyConstraint = new PrimaryKeyConstraint(
                name, table, columns);
        setPrimaryKeyConstraint(primaryKeyConstraint);
        return primaryKeyConstraint;
    }

    /**
     * Sets the PRIMARY KEY for the table defined in the constraint.
     * 
     * @param primaryKeyConstraint
     *            The PRIMARY KEY for the table.
     */
    public void setPrimaryKeyConstraint(
            PrimaryKeyConstraint primaryKeyConstraint) {
        Table table = primaryKeyConstraint.getTable();
        if (!tables.contains(table)) {
            throw new SQLRepresentationException("No such table \"" + table
                    + "\" in this schema for constraint");
        }
        removePrimaryKeyConstraint(table);
        primaryKeyConstraints.add(primaryKeyConstraint);
    }

    /**
     * Removes the PRIMARY KEY for a table.
     * 
     * @param table
     *            The table whose PRIMARY KEY is to be removed.
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
     * Returns the PRIMARY KEY for a table (null if no PRIMARY KEY is set).
     * 
     * @param table
     *            the table whose PRIMARY KEY is sought.
     * @return the table's PRIMARY KEY (null if no PRIMARY KEY is set).
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
     * Returns a list of all the PRIMARY KEY constraints for this schema.
     * 
     * @return a list of all the PRIMARY KEY constraints for this schema.
     */
    public List<PrimaryKeyConstraint> getAllPrimaryKeyConstraints() {
        return new ArrayList<>(primaryKeyConstraints);
    }

    /**
     * Checks whether a PRIMARY KEY is set on the table.
     * 
     * @param table
     *            the table to check.
     * @return true if a PRIMARY KEY is set, else false.
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
     * Creates a UNIQUE constraint and sets it on the table
     * 
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the UNIQUE columns.
     * @return the UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(Table table,
            Column... columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(table, columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * 
     * @param constraintName
     *            the name of the constraint.
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the UNIQUE columns.
     * @return the UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(String constraintName,
            Table table, Column... columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(
                constraintName, table, columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * 
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the UNIQUE columns.
     * @return the UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(Table table,
            List<Column> columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(table, columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }

    /**
     * Creates a UNIQUE constraint and sets it on the table
     * 
     * @param name
     *            the name of the constraint.
     * @param table
     *            the table on which the constraint is to be applied.
     * @param columns
     *            the UNIQUE columns.
     * @return the UNIQUE constraint created.
     */
    public UniqueConstraint createUniqueConstraint(String name, Table table,
            List<Column> columns) {
        UniqueConstraint UniqueConstraint = new UniqueConstraint(name, table,
                columns);
        addUniqueConstraint(UniqueConstraint);
        return UniqueConstraint;
    }

    /**
     * Sets a UNIQUE constraint on the table.
     * 
     * @param uniqueConstraint
     *            The UNIQUE constraint to be added.
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
     * Removes a UNIQUE constraint on the table.
     * 
     * @param constraint
     *            the unique constraint to be removed.
     * @return true if the unique constraint existed for the table and was
     *         successfully removed, else false.
     */
    public boolean removeUniqueConstraint(UniqueConstraint constraint) {
        return uniqueConstraints.remove(constraint);
    }

    /**
     * Returns the list of UNIQUE constraints on the table, in the order they
     * were created.
     * 
     * @param table
     *            The table whose constraints are sought.
     * @return The list of UNIQUE constraints on the table.
     */
    public List<UniqueConstraint> getUniqueConstraints(Table table) {
        return getConstraints(table, uniqueConstraints);
    }

    /**
     * Returns a list of all UNIQUE constraints defined on the schema.
     * 
     * @param table
     *            the table for which the constraints are required.
     * @return a list of the UNIQUE constraints on this schema.
     */
    public List<UniqueConstraint> getAllUniqueConstraints() {
        return new ArrayList<>(uniqueConstraints);
    }

    /**
     * Returns a list of all constraints for a table.
     * 
     * @param table
     *            the table whose constraints are sought.
     * @return the list of constraints defined on the table.
     */
    public List<Constraint> getConstraints(Table table) {
        List<Constraint> constraints = new ArrayList<Constraint>();
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
     * Returns a list of all constraints defined on the schema.
     * 
     * @return a list of all constraints.
     */
    public List<Constraint> getConstraints() {
        List<Constraint> constraints = new ArrayList<Constraint>();
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
            LinkedHashSet<C> constraintSet) {
        if (!tables.contains(table)) {
            throw new SQLRepresentationException("No such table \"" + table
                    + "\" in this schema for constraint");
        }

        List<C> constraintsForTable = new ArrayList<C>();
        for (C constraint : constraintSet) {
            if (constraint.getTable().equals(table)) {
                constraintsForTable.add(constraint);
            }
        }
        return constraintsForTable;
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
        if (!(obj instanceof Schema)) {
            System.out.println("here1");
            return false;
        }
        Schema other = (Schema) obj;
        if (checkConstraints == null) {
            if (other.checkConstraints != null) {
                System.out.println("here2");
                return false;
            }
        } else if (!checkConstraints.equals(other.checkConstraints)) {
            System.out.println("here3");

            System.out.println(checkConstraints);
            System.out.println("other:");
            System.out.println(other.checkConstraints);

            System.out.println(getCheckConstraints().get(0).equals(
                    other.getCheckConstraints().get(0)));

            return false;

        }
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
