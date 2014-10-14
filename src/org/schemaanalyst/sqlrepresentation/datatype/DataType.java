package org.schemaanalyst.sqlrepresentation.datatype;

import java.io.Serializable;

/**
 * Root class for representing a data type in SQL
 * @author Phil McMinn
 *
 */
public abstract class DataType implements Serializable {

    private static final long serialVersionUID = -7105047166176083429L;

    public abstract void accept(DataTypeVisitor typeVisitor);

    public abstract void accept(DataTypeCategoryVisitor categoryVisitor);
    
    public abstract DataType duplicate();
    
    @Override
    public int hashCode() {
        return getClass().getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass();
    }
}
