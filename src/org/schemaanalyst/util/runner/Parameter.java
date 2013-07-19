package org.schemaanalyst.util.runner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})

/**
 * Marks up fields as parameters which can be set from the command line, using the
 * Runner class.
 * @author phil
 *
 */
public @interface Parameter {
    
    /**
     * A description of the parameter.
     */
    String value() default "";
    
    /**
     * The name of a static method for returning a limited list of 
     * potential values for the parameter.  The method should
     * be returned in the form "class.method".
     */
    String choicesMethod() default "";
    
    /**
     * When set, use an optional parameter as a switch.  
     * That is, no value needs to be specified on the command line
     * along with the parameter name, with the value being set
     * to that specified below.  If the parameter is listed as 
     * required for the class, using the @RequiredParameters
     * annotation, this field is ignored.
     */
    String valueAsSwitch() default "";    
}

