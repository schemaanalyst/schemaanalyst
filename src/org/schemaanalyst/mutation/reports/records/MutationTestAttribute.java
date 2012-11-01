/*
 */
package org.schemaanalyst.mutation.reports.records;

/**
 * The types of attributes to record about a mutation test.
 */
public enum MutationTestAttribute {

    KILLED, 
    NOT_KILLED, 
    STILL_BORN, 
    INTERSECTION;

    /**
     * Get the Enum value with the associated name. Throws a RuntimeException if
     * a value cannot be found, ensuring only valid values can ever be returned.
     *
     * @param attributeName The name of the attribute to find an Enum value for.
     * @return The Enum value
     */
    public static MutationTestAttribute getByName(String attributeName) {
        MutationTestAttribute attribute = valueOf(attributeName);
        if (attribute != null) {
            return attribute;
        } else {
            throw new RuntimeException("Failed to retrieve attribute with name "
                    + "'" + attributeName + "'");
        }
    }
}
