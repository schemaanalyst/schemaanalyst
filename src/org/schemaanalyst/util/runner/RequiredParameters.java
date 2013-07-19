package org.schemaanalyst.util.runner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})

/**
 * A list of field names, separated by spaces, that MUST be set from the command line.
 * These fields do not require their name to paired with their value when passed in from
 * the command line -- the value alone is used, and must be supplied in order of field
 * as specified in this annotation string.
 * @author phil
 *
 */
public @interface RequiredParameters {
    String value();
}
