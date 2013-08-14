package org.schemaanalyst.sqlrepresentation.constraint;

import java.util.ArrayList;
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
    protected Table referenceTable;
    protected List<Column> referenceColumns;

    /**
     * Constructor.
     *
     * @param columns Columns over which the foreign key is defined
     * @param referenceTable The table containing the columns that the foreign
     * keys reference.
     * @param referenceColumns Columns in the reference table paired with each
     * column in columns
     */
    public ForeignKeyConstraint(List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
        this(null, columns, referenceTable, referenceColumns);
    }    
    
    /**
     * Constructor.
     *
     * @param name A name for the constraint (can be null).
     * @param columns Columns over which the foreign key is defined
     * @param referenceTable The table containing the columns that the foreign
     * keys reference.
     * @param referenceColumns Columns in the reference table paired with each
     * column in columns
     */
    public ForeignKeyConstraint(String name, List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
        super(name, columns);
        setReferenceTable(referenceTable);
        setReferenceColumns(referenceColumns);
    }

    /**
     * Sets the reference table for the foreign key.
     * @param table The reference table.
     */
    public void setReferenceTable(Table referenceTable) {
    	this.referenceTable = referenceTable;
    }
    
    /**
     * Sets the reference table and reference columns involved in the foreign
     * key.
     *
     * @param referenceColumns The columns in the reference table that define
     * the foreign key relationship.
     */
    protected void setReferenceColumns(List<Column> referenceColumns) {
        if (referenceColumns.size() != columns.size()) {
            throw new SQLRepresentationException("Foreign key constraints must have matching column numbers");
        }

        this.referenceColumns = new ArrayList<>();
        for (Column referenceColumn : referenceColumns) {
            this.referenceColumns.add(referenceColumn);
        }
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
     * Gets the reference columns of the foreign key.
     *
     * @return A list containing the reference columns of the foreign key.
     */
    public List<Column> getReferenceColumns() {
        return Collections.unmodifiableList(referenceColumns);
    }

    /**
     * Method for accepting visitors of type IntegrityConstraintVisitor.
     *
     * @param visitor The IntegrityConstraintVisitor instance visiting this
     * constraint.
     */
    @Override
    public void accept(ConstraintVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ForeignKeyConstraint duplicate() {
        return new ForeignKeyConstraint(
                new ArrayList<>(columns),
                referenceTable,
                new ArrayList<>(referenceColumns));
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
