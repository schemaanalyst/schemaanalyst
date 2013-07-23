package org.schemaanalyst.sqlrepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 * Represents foreign key constraints.
 *
 * @author Phil McMinn
 *
 */
public class ForeignKeyConstraint extends MultiColumnConstraint {

    private static final long serialVersionUID = 373214484205495500L;
    protected Table referenceTable;
    protected List<Column> referenceColumns;

    /**
     * Constructor.
     *
     * @param name A name for the constraint (can be null).
     * @param table The table containing the foreign key(s).
     * @param columns Columns over which the foreign key is defined
     * @param referenceTable The table containing the columns that the foreign
     * keys reference.
     * @param referenceColumns Columns in the reference table paired with each
     * column in columns
     */
    protected ForeignKeyConstraint(String name, Table table, List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
        super(name, table, columns);
        setReferenceColumns(referenceTable, referenceColumns);
    }

    /**
     * Constructor.
     *
     * @param name A name for the constraint (can be null).
     * @param table The table containing the foreign key(s).
     * @param columns Columns over which the foreign key is defined
     * @param referenceTable The table containing the columns that the foreign
     * keys reference.
     * @param referenceColumns Columns in the reference table paired with each
     * column in columns
     */
    protected ForeignKeyConstraint(String name, Table table, Column[] columns, Table referenceTable, Column[] referenceColumns) {
        super(name, table, Arrays.asList(columns));
        setReferenceColumns(referenceTable, Arrays.asList(referenceColumns));
    }

    /**
     * Sets the reference table and reference columns involved in the foreign
     * key.
     *
     * @param referenceTable The referenced table.
     * @param referenceColumns The columns in the reference table that define
     * the foreign key relationship.
     */
    protected void setReferenceColumns(Table referenceTable, List<Column> referenceColumns) {
        this.referenceTable = referenceTable;
        if (referenceColumns.size() != columns.size()) {
            throw new SchemaConstructionException("Foreign key constraints must have matching column numbers \"" + table + "\"");
        }

        this.referenceColumns = new ArrayList<>();

        for (Column referenceColumn : referenceColumns) {
            if (!referenceTable.hasColumn(referenceColumn)) {
                throw new SchemaConstructionException(
                        "Reference column \"" + referenceColumn
                        + "\" does not exist in reference table \"" + referenceTable + "\"");
            }
            this.referenceColumns.add(referenceColumn);
        }
    }

    /**
     * Adds a foreign key column / reference column pair to the foreign key
     * constraint.
     *
     * @param column The foreign key column to add from the instance's table.
     * @param referenceColumn The referencing column to add from the reference
     * table.
     */
    public void addColumnPair(Column column, Column referenceColumn) {
        if (!table.hasColumn(column)) {
            throw new SchemaConstructionException("Column \"" + column.getFullyQualifiedName() + "\" does not exist in table \"" + table + "\"");
        }
        if (!referenceTable.hasColumn(referenceColumn)) {
            throw new SchemaConstructionException("Column \"" + referenceColumn.getFullyQualifiedName() + "\" does not exist in table \"" + referenceTable + "\"");
        }
        columns.add(column);
        referenceColumns.add(referenceColumn);
    }

    /**
     * Removes a column from the foreign key, and its pairing reference column
     * in the reference table. Only the column from the foreign key table needs
     * to be specified, however.
     *
     * @param column The column of the foreign key table.
     */
    public void removeColumnPair(Column column) {
        int index = -1;
        for (int i = 0; i < columns.size(); i++) {
            if (column.equals(columns.get(i))) {
                index = i;
            }
        }
        if (index != -1) {
            columns.remove(index);
            referenceColumns.remove(index);
        }
    }

    /**
     * Gets the reference columns of the foreign key.
     *
     * @return A list containing the reference columns of the foreign key.
     */
    public List<Column> getReferenceColumns() {
        return Collections.unmodifiableList(referenceColumns);
    }

    /**
     * Gets the reference table of the foreign key.
     *
     * @return The reference table instance.
     */
    public Table getReferenceTable() {
        return referenceTable;
    }

    /**
     * Method for accepting visitors of type IntegrityConstraintVisitor.
     *
     * @param visitor The IntegrityConstraintVisitor instance visiting this
     * constraint.
     */
    public void accept(ConstraintVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Copies the foreign key to another table. The table must have the same
     * column names as this instance's table. If the table is from another
     * schema, the schema must have another table with the same name as the
     * reference table with the same reference column names.
     *
     * @param targetTable The table to copy the foreign key to.
     * @return The instance of the ForeignKey copy created.
     */
    public ForeignKeyConstraint copyTo(Table targetTable) {
        // map the reference table to that of the schema of the new table		
        Table targetReferenceTable = targetTable.getSchema().getTable(this.referenceTable.getName());
        if (targetReferenceTable == null) {
            throw new SchemaConstructionException("Cannot copy ForeignKey to table \"" + targetTable + "\" "
                    + "as its schema does not have the reference table \"" + referenceTable + "\"");

        }

        // copy columns, but mapped to those of the new table
        List<Column> targetTableColumns = new ArrayList<>();
        for (Column column : this.columns) {
            Column targetTableColumn = targetTable.getColumn(column.getName());

            if (targetTableColumn == null) {
                throw new SchemaConstructionException("Cannot copy ForeignKey to table \"" + targetTable + "\" "
                        + "as it does not have the column \"" + column + "\"");
            }

            targetTableColumns.add(targetTableColumn);
        }


        // copy reference columns, but mapped to those of the new reference table
        List<Column> targetReferenceTableColumns = new ArrayList<>();
        for (Column column : this.referenceColumns) {
            Column targetReferenceTableColumn = targetReferenceTable.getColumn(column.getName());

            if (targetReferenceTableColumn == null) {
                throw new SchemaConstructionException("Cannot copy ForeignKey to table \"" + targetTable + "\" "
                        + "as its reference table \"" + targetReferenceTable + "\" "
                        + "does not hve the column \"" + targetReferenceTableColumn + "\"");
            }

            targetReferenceTableColumns.add(targetReferenceTableColumn);
        }

        ForeignKeyConstraint copy = new ForeignKeyConstraint(
                name, targetTable, targetTableColumns,
                targetReferenceTable, targetReferenceTableColumns);

        targetTable.addForeignKeyConstraint(copy);
        return copy;
    }

    /**
     * Checks whether this ForeignKey instance is equal to another object. The
     * comparison compares columns and reference table only and ignores the
     * constraint's name.
     *
     * @param obj The object to compare this instance with.
     * @return True if the other object is a ForeignKey object with the same
     * columns and reference table, else false.
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!super.equals(obj)) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        ForeignKeyConstraint other = (ForeignKeyConstraint) obj;

        if (columns == null) {
            if (other.columns != null) {
                return false;
            }
        } else if (!columns.equals(other.columns)) {
            return false;
        }

        if (referenceTable == null) {
            if (other.referenceTable != null) {
                return false;
            }
        } else if (!referenceTable.equals(other.referenceTable)) {
            return false;
        }

        if (referenceColumns == null) {
            if (other.referenceColumns != null) {
                return false;
            }
        } else if (!referenceColumns.equals(other.referenceColumns)) {
            return false;
        }

        return true;
    }

    /**
     * Returns an informative string about the foreign key instance.
     *
     * @return An informative string.
     */
    public String toString() {
        return "FOREIGN KEY" + columns.toString();
    }
}
