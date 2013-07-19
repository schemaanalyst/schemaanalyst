package org.schemaanalyst.util.runner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface Parameter {
    String value() default "";
    String choicesMethod() default "";
}

// TODO:
// * show default value in usage
// * if boolean don't show value
// * validate based on enum strings

