package experiment;

import org.schemaanalyst.util.ReflectiveToString;

/**
 * Represents classes used to store parameters that specify behavior at runtime.
 * 
 * @author Chris J. Wright
 */
public abstract class Parameters {

    /**
     * Sets the default values for the parameters.
     */
    public abstract void setDefaultParameters();
    
    /**
     * Create the String representation of this object.
     *
     * @return The representation.
     */
    @Override
    public String toString() {
        return ReflectiveToString.toString(this);
    }
}