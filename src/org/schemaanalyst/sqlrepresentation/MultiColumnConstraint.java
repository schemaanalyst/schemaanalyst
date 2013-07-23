package org.schemaanalyst.sqlrepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract superclass for integrity constrains that potentially involve
 * multiple columns (PrimaryKey, ForeignKey and Unique).
 *
 * @author Phil McMinn
 *
 */
public abstract class MultiColumnConstraint extends Constraint {

    private static final long serialVersionUID = 173815859465417000L;
    protected List<Column> columns;

    /**
     * Constructor.
     *
     * @param name A name for the constraint (optional - can be null).
     * @param table The table on which the integrity constraint holds.
     * @param columns The columns involved in the integrity constraint.
     */
    protected MultiColumnConstraint(String name, Table table, List<Column> columns) {
        super(name, table);
        setColumns(columns);
    }

    /**
     * Sets the columns involved in the integrity constraint.
     *
     * @param columns The columns involved in the integrity constraint.
     */
    protected void setColumns(List<Column> columns) {

        if (columns.size() < 1) {
            throw new SchemaConstructionException("Constraints must be defined over one or more columns for table \"" + table + "\"");
        }

        this.columns = new ArrayList<>();

        for (Column column : columns) {
            if (!table.hasColumn(column)) {
                throw new SchemaConstructionException("Column \"" + column + "\" does not exist in table \"" + table + "\"");
            }
            this.columns.add(column);
        }
    }

    /**
     * Gets the columns involved in the integrity constraint.
     *
     * @return A list of the columns involved in the integrity constraint.
     */
    public List<Column> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    /**
     * Checks if a column is involved in the integrity constraint or not.
     *
     * @param column The column whose status is to be checked.
     * @return True if the column is involved, else false.
     */
    public boolean involvesColumn(Column column) {
        return columns.contains(column);
    }

    /**
     * Gets the number of columns involved in the integrity constraint.
     *
     * @return The number of columns involved in the integrity constraint.
     */
    public int getNumColumns() {
        return columns.size();
    }

    /**
     * Returns true if more than one column involved in this constraint.
     *
     * @return True if more than one column involved in this constraint, else
     * false.
     */
    public boolean hasMultipleColumns() {
        return columns.size() > 1;
    }
}
