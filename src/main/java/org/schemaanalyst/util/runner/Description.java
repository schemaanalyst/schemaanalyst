package org.schemaanalyst.util.runner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})

/**
 * A simple annotation that provides a description for a Runner
 * class when the --help option is invoked.
 * @author phil
 *
 */
public @interface Description {
    String value() default "";
}
