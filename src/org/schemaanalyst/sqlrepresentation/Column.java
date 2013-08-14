package org.schemaanalyst.sqlrepresentation;

import java.io.Serializable;

import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.util.Duplicable;

/**
 * Represents a table column in a database schema.
 * 
 * @author Phil McMinn
 *
 */
public class Column implements Duplicable<Column>, Serializable {

    private static final long serialVersionUID = -2680046452756410766L;

    private String name;
    private DataType dataType;

    /**
     * Constructor.
     * @param name The name of the column.
     * @param dataType The type of the column (see the datatype subpackage).
     */
    public Column(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    /**
     * Returns the name of the column.
     * @return The name of the column.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the data type of the column (for example VarChar).
     * @return The data type of the column.
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Changes the data type of the column.
     * @param type The new data type of the column.
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Duplicates the column.
     * @return A duplicated version of the column.
     */
    public Column duplicate() {
    	return new Column(name, dataType.duplicate());
    }
    
    /**
     * Returns a hashcode value for the column.
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Checks whether the column equals another.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Column other = (Column) obj;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
