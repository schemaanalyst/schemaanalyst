package org.schemaanalyst.sqlrepresentation;

import java.io.Serializable;

import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.Operand;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.OperandVisitor;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;

/**
 * Represents a table column in a database schema. Table columns are constructed
 * by calling the <tt>createColumn</tt> method of Table.
 *
 * @author Phil McMinn
 *
 */
public class Column implements Operand, Serializable {

    private static final long serialVersionUID = -2680046452756410766L;
    protected Table table;
    protected String name;
    protected DataType dataType;

    /**
     * Constructor.
     *
     * @param name The name of the column.
     * @param table The table to which this column belongs.
     * @param type The type of the column (see the types sub-package).
     */
    protected Column(String name, Table table, DataType dataType) {
        this.table = table;
        this.name = name;
        this.dataType = dataType;
    }

    /**
     * Returns the name of the column.
     *
     * @return The name of the column.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the column's table.
     *
     * @return The table of the column.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Returns the data type of the column (for example VarChar).
     *
     * @return The data type of the column.
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Changes the data type of the column.
     *
     * @param type The new data type of the column.
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Sets the column as having a not null constraint.
     */
    public void setNotNull() {
        table.addNotNullConstraint(this);
    }

    /**
     * Returns whether or not the column has a not null constraint on it.
     *
     * @return True if the column has a not null constraint on it, else false.
     */
    public boolean isNotNull() {
        return table.isNotNull(this);
    }

    /**
     * Sets the column as being unique.
     */
    public void setUnique() {
        table.addUniqueConstraint(this);
    }

    /**
     * Sets the column as being the primary key of its table.
     */
    public void setPrimaryKey() {
        table.setPrimaryKeyConstraint(this);
    }

    /**
     * Sets the column as being the subject of a primary key.
     *
     * @param referenceTable The table which the foreign key references (that
     * is, the table that contains the reference column).
     * @param referenceColumn The reference column with which the primary key is
     * paired with in another table (the reference table).
     */
    public void setForeignKey(Table referenceTable, Column referenceColumn) {
        table.addForeignKeyConstraint(this, referenceTable, referenceColumn);
    }

    /**
     * @deprecated Accepts an operand visitor on this column.
     * @param operandVisitor The operand visitor to accept.
     */
    @Override
    public void accept(OperandVisitor operandVisitor) {
        operandVisitor.visit(this);
    }

    /**
     * Checks whether the current instance is equal to another object.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Column other = (Column) obj;
        return this.getFullyQualifiedName().equals(other.getFullyQualifiedName());
    }

    /**
     * Gets the fully qualified name of the column. The form is
     * "<tt>schema_name.table_name.column_name</tt>".
     *
     * @return The fully qualified name.
     */
    public String getFullyQualifiedName() {
        return table.getFullyQualifiedName() + "." + name;
    }

    /**
     * Returns the name of the column.
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Copies the column to another table. Please note that attributes are not
     * deep-copied yet.
     *
     * @param table The table to copy the column to/
     * @return The copied column.
     */
    // Note that type and attributes are only shallow copied
    public Column copyTo(Table table) {
        return table.addColumn(this.name, this.dataType);
    }
}
