package org.schemaanalyst.sqlrepresentation;

import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.util.collection.IdentifiableEntity;

/**
 * Represents a table column in a database schema.
 * 
 * @author Phil McMinn
 *
 */
public class Column extends IdentifiableEntity {

    private static final long serialVersionUID = -2680046452756410766L;
    private DataType dataType;

    /**
     * Constructor.
     * @param name The name of the column.
     * @param dataType The type of the column
     */
    public Column(String name, DataType dataType) {
        if (name == null) {
            throw new SQLRepresentationException(
                    "Column names cannot be null");
        }        
        setName(name);
        this.dataType = dataType;
    }
    
	/**
	 * Sets the name of the column. If the column belongs to a table, and there
	 * is a name-clash with another column, an {@link SQLRepresentationException}
	 * will be thrown.
	 * 
	 * @param name
	 *            The name of the table.
	 */
	public void setName(String name) {
		super.setName(name);
		if (!getName().equals(name)) {
			throw new SQLRepresentationException("Cannot rename column to \""
					+ name
					+ "\" as another column in the table has the same name");
		}
	}    

    /**
     * Returns the data type of the column.
     * @return The data type of the column.
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Changes the data type of the column.
     * @param dataType The new data type of the column.
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Duplicates the column.
     * @return A duplicated version of the column.
     */
    public Column duplicate() {
    	return new Column(getName(), dataType.duplicate());
    }
    
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((dataType == null) ? 0 : dataType.hashCode());
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
        Column other = (Column) obj;
        if (dataType == null) {
            if (other.dataType != null)
                return false;
        } else if (!dataType.equals(other.dataType))
            return false;
        return true;
    }

    /**
	 * Returns the column's name.
	 */
	public String toString() {
	    return getName();
	}
}
