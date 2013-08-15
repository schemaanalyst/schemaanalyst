package org.schemaanalyst.sqlrepresentation.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.SQLRepresentationException;
import org.schemaanalyst.sqlrepresentation.Table;

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
     * @param column The column in the source table on which the foreign key is defined
     * @param referenceTable The table containing the column that the foreign
     * keys references.
     * @param referenceColumn The column in the reference table paired with the column
     * in the source table.
     */
    public ForeignKeyConstraint(
            Column column, Table referenceTable, Column referenceColumn) {
        this(null, Arrays.asList(column), referenceTable, Arrays.asList(referenceColumn));
    }     
    
    /**
     * Constructor.
     * @param name The name of the constraint.
     * @param column The column in the source table on which the foreign key is defined
     * @param referenceTable The table containing the column that the foreign
     * keys references.
     * @param referenceColumn The column in the reference table paired with the column
     * in the source table.
     */
    public ForeignKeyConstraint(
            String name, Column column, Table referenceTable, Column referenceColumn) {
        this(name, Arrays.asList(column), referenceTable, Arrays.asList(referenceColumn));
    }     
    
    /**
     * Constructor.
     * @param columns Columns over which the foreign key is defined
     * @param referenceTable The table containing the columns that the foreign
     * keys reference.
     * @param referenceColumns Columns in the reference table paired with each
     * column in columns
     */
    public ForeignKeyConstraint(
            List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
        this(null, columns, referenceTable, referenceColumns);
    }    
    
    /**
     * Constructor.
     * @param name A name for the constraint (can be null).
     * @param columns Columns over which the foreign key is defined
     * @param referenceTable The table containing the columns that the foreign
     * keys reference.
     * @param referenceColumns Columns in the reference table paired with each
     * column in columns
     */
    public ForeignKeyConstraint(
            String name, List<Column> columns, 
            Table referenceTable, List<Column> referenceColumns) {
        super(name, columns);
        setReferenceTable(referenceTable);
        setReferenceColumns(referenceColumns);
    }

    /**
     * Sets the reference table for the foreign key.
     * @param referenceTable The reference table.
     */
    public void setReferenceTable(Table referenceTable) {
    	this.referenceTable = referenceTable;
    }
    
    /**
     * Sets the reference table and reference columns involved in the foreign
     * key.
     * @param referenceColumns The columns in the reference table that define
     * the foreign key relationship.
     */
    public void setReferenceColumns(List<Column> referenceColumns) {
        if (referenceColumns.size() != columns.size()) {
            throw new SQLRepresentationException(
                    "Foreign key constraints must have matching numbers of columns in the subject and reference tables");
        }

        this.referenceColumns = new ArrayList<>();
        for (Column referenceColumn : referenceColumns) {
            if (!referenceTable.hasColumn(referenceColumn)) {
                throw new SQLRepresentationException(
                        "No such column \"" + referenceColumn.getName() + 
                        "\" in table " + referenceTable.getName() + 
                        " for ForeignKeyConstraint");
            }
            
            this.referenceColumns.add(referenceColumn);
        }
    }

    /**
     * Gets the reference table of the foreign key.
     * @return The reference table instance.
     */
    public Table getReferenceTable() {
        return referenceTable;
    }

    /**
     * Gets the reference columns of the foreign key.
     * @return A list containing the reference columns of the foreign key.
     */
    public List<Column> getReferenceColumns() {
        return Collections.unmodifiableList(referenceColumns);
    }

    /**
     * Remaps the reference table columns to another table
     * @param table The table to remap reference columns to.  
     */
    public void remapReferenceColumns(Table table) {
        setReferenceColumns(remapColumns(referenceColumns, table));
    }
    
    /**
     * Method for accepting visitors of type IntegrityConstraintVisitor.
     * @param visitor The ConstraintVisitor instance visiting this constraint.
     */
    @Override
    public void accept(ConstraintVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Performs a shallow copy of the foreign key constraint.
     * @return A duplicate version of the foreign key.
     */
    @Override
    public ForeignKeyConstraint duplicate() {
        return new ForeignKeyConstraint(
                name,
                new ArrayList<>(columns),
                referenceTable,
                new ArrayList<>(referenceColumns));
    }
    
    /**
     * Generates a hash code for this instance.
     * @return The hash code.
     */    
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

    /**
     * Checks if this instance is equal to another.
     * @param obj Another object.
     * @return True if the objects are equal, else false.
     */
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
     * @return An informative string.
     */
    @Override
    public String toString() {
        return "FOREIGN KEY" + columns.toString();
    }
}
