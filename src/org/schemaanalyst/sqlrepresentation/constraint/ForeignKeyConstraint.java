package org.schemaanalyst.sqlrepresentation.constraint;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents foreign key constraints.
 * 
 * @author Phil McMinn
 * 
 */
public class ForeignKeyConstraint extends MultiColumnConstraint {

    private static final long serialVersionUID = 373214484205495500L;
    private Table referenceTable;
    private List<Column> referenceColumns;

    /**
     * Constructor.
     * 
     * @param table
     *            The source table on which the FOREIGN KEY is defined.
     * @param column
     *            The column in the source table on which the FOREIGN KEY is
     *            defined.
     * @param referenceTable
     *            The table containing the column that the FOREIGN KEY
     *            references.
     * @param referenceColumn
     *            The column in the reference table paired with the column in
     *            the source table.
     */
    public ForeignKeyConstraint(Table table, Column column,
            Table referenceTable, Column referenceColumn) {
        this(null, table, Arrays.asList(column), referenceTable, Arrays
                .asList(referenceColumn));
    }

    /**
     * Constructor.
     * 
     * @param name
     *            The name of the constraint.
     * @param table
     *            The source table on which the FOREIGN KEY is defined.
     * @param column
     *            The column in the source table on which the FOREIGN KEY is
     *            defined
     * @param referenceTable
     *            The table containing the column that the FOREIGN KEY
     *            references.
     * @param referenceColumn
     *            The column in the reference table paired with the column in
     *            the source table.
     */
    public ForeignKeyConstraint(String name, Table table, Column column,
            Table referenceTable, Column referenceColumn) {
        this(name, table, Arrays.asList(column), referenceTable, Arrays
                .asList(referenceColumn));
    }

    /**
     * Constructor.
     * 
     * @param table
     *            The source table on which the FOREIGN KEY is defined.
     * @param columns
     *            Columns over which the FOREIGN KEY is defined
     * @param referenceTable
     *            The table containing the columns that the FOREIGN KEY
     *            reference.
     * @param referenceColumns
     *            Columns in the reference table paired with each column in
     *            columns
     */
    public ForeignKeyConstraint(Table table, List<Column> columns,
            Table referenceTable, List<Column> referenceColumns) {
        this(null, table, columns, referenceTable, referenceColumns);
    }

    /**
     * Constructor.
     * 
     * @param table
     *            The source table on which the FOREIGN KEY is defined.
     * @param name
     *            A name for the constraint (can be null).
     * @param columns
     *            Columns over which the foreign key is defined
     * @param referenceTable
     *            The table containing the columns that the FOREIGN KEY
     *            reference.
     * @param referenceColumns
     *            Columns in the reference table paired with each column in
     *            columns
     */
    public ForeignKeyConstraint(String name, Table table,
            List<Column> columns, Table referenceTable,
            List<Column> referenceColumns) {
        super(name, table, columns);
        this.referenceTable = referenceTable;
        setReferenceColumns(referenceColumns);
    }

    /**
     * Sets the reference table and reference columns involved in the FOREIGN
     * KEY.
     * 
     * @param referenceColumns
     *            The columns in the reference table that define the FOREIGN KEY
     *            relationship.
     */
    public void setReferenceColumns(Iterable<Column> referenceColumns) {

        this.referenceColumns = new ArrayList<>();
        for (Column referenceColumn : referenceColumns) {
            if (!referenceTable.hasColumn(referenceColumn)) {
                throw new SQLRepresentationException("No such column \""
                        + referenceColumn + "\" in table " + referenceTable
                        + " for ForeignKeyConstraint");
            }

            this.referenceColumns.add(referenceColumn);
        }

        if (this.referenceColumns.size() != columns.size()) {
            throw new SQLRepresentationException(
                    "Foreign key constraints must have matching numbers of columns in the subject and reference tables");
        }

    }

    /**
     * Gets the reference table of the FOREIGN KEY.
     * 
     * @return The reference table instance.
     */
    public Table getReferenceTable() {
        return referenceTable;
    }

    /**
     * Gets the reference columns of the FOREIGN KEY.
     * 
     * @return A list containing the reference columns of the FOREIGN KEY.
     */
    public List<Column> getReferenceColumns() {
        return new ArrayList<>(referenceColumns);
    }
    
    /**
     * Gets the pairs of columns of the FOREIGN KEY.
     * 
     * @return A list containing the column pairings.
     */
    public List<Pair<Column>> getColumnPairs() {
        List<Pair<Column>> pairs = new ArrayList<>(columns.size());
        for (int i = 0; i < columns.size(); i++) {
            pairs.add(new Pair<>(columns.get(i), referenceColumns.get(i)));
        }
        return pairs;
    }
    
    /**
     * Sets the local and reference columns of the FOREIGN KEY using a list of pairs.
     * 
     * @param pairs A list containing the column pairings.
     */
    public void setColumnPairs(List<Pair<Column>> pairs) {
        List<Column> local = new ArrayList<>(pairs.size());
        List<Column> reference = new ArrayList<>(pairs.size());
        for (Pair<Column> pair : pairs) {
            local.add(pair.getFirst());
            reference.add(pair.getSecond());
        }
        setColumns(local);
        setReferenceColumns(reference);
    }

    /**
     * Remaps the reference table columns to another table
     * 
     * @param table
     * 			  The table to remap columns to.
     * @param referenceTable
     *            The table to remap reference columns to.
     */
    public void remap(Table table, Table referenceTable) {
    	remap(table);
        this.referenceTable = referenceTable;
        setReferenceColumns(remapColumns(referenceTable, referenceColumns));
    }

    /**
     * Method for accepting visitors of type ConstraintVisitor.
     * 
     * @param visitor
     *            The ConstraintVisitor instance visiting this constraint.
     */
    @Override
    public void accept(ConstraintVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Performs a shallow copy of the FOREIGN KEY constraint.
     * 
     * @return A duplicate version of the constraint.
     */
    @Override
    public ForeignKeyConstraint duplicate() {
        return new ForeignKeyConstraint(getName(), table, columns,
                referenceTable, referenceColumns);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime
                * result
                + ((referenceColumns == null) ? 0 : referenceColumns.hashCode());
        result = prime * result
                + ((referenceTable == null) ? 0 : referenceTable.hashCode());
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
        ForeignKeyConstraint other = (ForeignKeyConstraint) obj;
        if (referenceColumns == null) {
            if (other.referenceColumns != null)
                return false;
        } else if (!referenceColumns.equals(other.referenceColumns))
            return false;
        if (referenceTable == null) {
            if (other.referenceTable != null)
                return false;
        } else if (!referenceTable.equals(other.referenceTable))
            return false;
        return true;
    }

    /**
     * Returns an informative string about the foreign key instance.
     * 
     * @return An informative string.
     */
    @Override
    public String toString() {
        return "FOREIGN KEY" + columns.toString();
    }
}
