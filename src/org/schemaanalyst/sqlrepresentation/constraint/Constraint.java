package org.schemaanalyst.sqlrepresentation.constraint;

import java.io.Serializable;

import org.schemaanalyst.util.Duplicable;

/**
 * Abstract super class of all integrity constraints.
 *
 * @author Phil McMinn
 *
 */
public abstract class Constraint implements Duplicable<Constraint>, Serializable {

    private static final long serialVersionUID = -1552612200017351725L;
   
    protected String name;

    /**
     * Constructor.
     *
     * @param name An identifying name for the constraint (optional - can be
     * null).
     * @param table The table on which the constraint holds.
     */
    public Constraint(String name) {
        this.name = name;
    }

    /**
     * Gets the identification name of the constraint (may be null).
     * @return The name of the constraint.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if the constraint has a name.
     * @return True if the constraint has a name else false;
     */
    public boolean hasName() {
        return name != null;
    }
    
    /**
     * Duplicates the constraint.
     */
    public abstract Constraint duplicate();
    
    /**
     * Allows instances of the class to be visited by a
     * ConstraintVisitor.
     * @param visitor The visitor instance.
     */
    public abstract void accept(ConstraintVisitor visitor);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constraint other = (Constraint) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
