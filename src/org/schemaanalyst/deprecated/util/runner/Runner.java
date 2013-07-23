package org.schemaanalyst.deprecated.util.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.FolderConfiguration;
import org.schemaanalyst.configuration.LoggingConfiguration;
import org.schemaanalyst.util.StringUtils;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;

/**
 * The older version of Runner, before I refactored it to make it more testable.
 * Use new version -- requires you to override task() rather than run()
 */
@Deprecated
public abstract class Runner {
    
    public static final String LONG_OPTION_PREFIX = "--";
    public static final String HELP_OPTION = LONG_OPTION_PREFIX + "help";

    // must be spaces, not tabs to work properly:
    protected static final String USAGE_INDENT = StringUtils.repeat(" ", 4);
    protected static final String USAGE_HANGING_INDENT = StringUtils.repeat(USAGE_INDENT, 4);
    
    // a store of overwritten default values 
    protected HashMap<String, Object> overwrittenDefaults;
    
    // various configurations
    protected FolderConfiguration folderConfiguration;
    protected DatabaseConfiguration databaseConfiguration;
    protected LoggingConfiguration loggingConfiguration;
    
    /**
     * Constructor.  Instantiates the Runner and loads configuration files.
     */
    public Runner() {
        this(true);
    }
    
    /**
     * Constructor.  
     * @param loadConfiguration If true, loads the configuration file.
     */
    public Runner(boolean loadConfiguration) {
        if (loadConfiguration) {
            loadConfiguration();
        }
    }
    
    /**
     * Executes the main code of the runner
     * @param args The arguments passed from the command line.
     */
    public abstract void run(String... args);
    
    /**
     * This method should be overridden to perform any additional validation
     * required of the field values passed in through the values of args 
     * to the run method.
     */
    protected abstract void validateParameters();    
    
    /**
     * Intialises the Runner by parsing args into field values and then
     * validating them through a call to validateParameters.
     * @param args The arguments passed in from the command line.
     */    
    protected void initialise(String... args) {
        parseArgs(args);
        validateParameters();
    }

    /**
     * Loads the properties files from their default locations.
     */
    protected void loadConfiguration() {
        folderConfiguration = new FolderConfiguration();
        databaseConfiguration = new DatabaseConfiguration();
        loggingConfiguration = new LoggingConfiguration();
    }

    /**
     * Parses args passed in from the command line into field values.
     * @param args The arguments passed in from the command line.
     */
    protected void parseArgs(String... args) {
        // record which options parameters were parsed and their original defaults
        // this is in case there is an error in parsing values and the usage must
        // be printed with default values for each field (which may have been
        // set to new values in the parsing process).
        overwrittenDefaults = new HashMap<>();
        
        String[] requiredParams = getRequriedParameterFieldNames(); 
        int numRequiredParamsProcessed = 0;
        
        for (String arg : args) {
            // if the user wants help, give it to them
            if (arg.equals(HELP_OPTION)) {
                quitWithHelp();
            }

            if (arg.startsWith(LONG_OPTION_PREFIX)) {     
                // parse an optional parameter 
                
                String fieldName = "";
                String value = "";
                int startPos = LONG_OPTION_PREFIX.length();
                int equalsPos = arg.indexOf("=");
                if (equalsPos == -1) {
                    fieldName = arg.substring(startPos);
                } else {
                    fieldName = arg.substring(startPos, equalsPos);
                    value = arg.substring(equalsPos + 1);
                }
                
                setFieldWithParameterValue(fieldName, value);                
            } else {
                // parse a required parameter
                if (numRequiredParamsProcessed < requiredParams.length) {
                    String fieldName = requiredParams[numRequiredParamsProcessed];
                    
                    // ensure there's been no errors in setting up @RequiredParameters  
                    if (!isField(fieldName)) {
                        throw new RuntimeException(
                                fieldName + " is specified in @RequiredParameters for " + 
                                getClass().getCanonicalName() + " but no such field exists " + 
                                "in that class or its superclasses");
                    }
                    
                    if (!isParameter(fieldName)) {
                        throw new RuntimeException(
                                fieldName + " is specified in @RequiredParameters for " + 
                                getClass().getCanonicalName() + " but is not marked with @Parameter " + 
                                "for that class or its superclasses");
                    }
                    
                    setFieldWithParameterValue(fieldName, arg);
                    numRequiredParamsProcessed ++;
                } else {
                    quitWithError("Too many arguments");
                }
            }
        }
        
        if (numRequiredParamsProcessed < requiredParams.length) {            
            quitWithError("No value supplied for " + requiredParams[numRequiredParamsProcessed]);
        }
    }
    
    /**
     * Pulls out the value of the @RequiredParameters annotation for the Runner class,
     * if one has been specified, going back through superclasses if one not defined
     * for the current class.
     * @return The value of the @RequiredParameters annotation, if specified in the class.
     */
    protected String getRequiredParametersString() {
        Class<?> currentClass = getClass();
        while (currentClass != null) {
            Annotation[] annotations = currentClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequiredParameters) {
                    RequiredParameters requiredParams = (RequiredParameters) annotation;
                    return requiredParams.value();
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }
    
    /**
     * Returns an array of strings representing fields specified (in the same order) in 
     * the @RequiredFields annotation.
     */
    protected String[] getRequriedParameterFieldNames() {
        String requiredParametersString = getRequiredParametersString();
        if (requiredParametersString == null) {
            return new String[0];
        } else {
            List<String> list = StringUtils.explode(getRequiredParametersString(), " ");
            String[] arr = new String[list.size()];
            return list.toArray(arr);
        }
    }
    
    /**
     * Checks whether a string is the name of a field in the extension of the class.
     * @param name The name string to be checked.
     * @return True if the string is a field of the extension of this Runner class, else false.
     */
    protected boolean isField(String name) {
        return getField(name) != null;
    }
    
    /**
     * Using reflection, gets the Field object for the field of the extension of this class,
     * as specfied by the name string. 
     * @param name The name of the field to be obtained.
     * @return A Field instance, or null if there is no field corresponding to name 
     */
    protected Field getField(String name) {
        Class<?> currentClass = getClass();
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField(name);
            } catch (NoSuchFieldException e) {           
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;  
    }
    
    /**
     * Checks whether the field specified by name is a parameter, that is, whether it is
     * marked up with a @Parameter annotation.
     * @param name The name of the field to check.
     * @return True if the field specified by name is a parameter, else false.
     */
    protected boolean isParameter(String name) {
        return getParameter(name) != null;
    }
    
    /** 
     * Gets the @Parameter annotation for the field specified by the string name, if such an
     * annotation exists.
     * @param name The name of the field for which the annotation is requested.
     * @return The annotation, if it exists, else false.
     */
    protected Parameter getParameter(String name) {        
        return getParameter(getField(name));        
    }
    
    /** 
     * Gets the @Parameter annotation for Field, if such an annotation exists.
     * @param name The field for which the annotation is requested.
     * @return The annotation, if it exists, else false.
     */    
    protected Parameter getParameter(Field field) {
        if (field != null) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Parameter) {
                    return (Parameter) annotation;
                }
            }
        }        
        return null;     
    }

    /**
     * Sets a field specified by the string name with the value specified by the string value.
     * If the field is not a string (i.e., an int, double etc.), the value is parsed.       
     * @param name The name of the field to set. 
     * @param value The value with which to set it.
     */    
    protected void setFieldWithParameterValue(String name, String value) {
        // get hold of the instance field
        Field field = getField(name);
        check(field != null, "Unknown parameter \"" + name + "\"");

        // get hold of the option instance for the field
        Parameter param = getParameter(field);
        check(param != null, "Unknown parameter \"" + name + "\"");

        // if choices are available for this method, ensure that the value 
        // is equal to one of them
        String choices[] = getParameterChoices(name, param);
        if (choices.length > 0) {
            boolean foundMatch = false;
            for (String choice : choices) {
                if (choice.equals(value)) {
                    foundMatch = true;
                }
            }
            check(foundMatch, 
                  name + " value \"" + value + 
                  "\" is not one of a list of pre-specified choices");
        }
        
        // is the value a switch?
        String valueAsSwitch = param.valueAsSwitch();
        if (valueAsSwitch.length() > 0) {
            check(value.length() == 0, 
                  name + " is a switch, so no value is requried");
            value = valueAsSwitch;
        }        
        
        // parse the value into the field
        field.setAccessible(true);
        try {
            // store the original value of the field
            overwrittenDefaults.put(name, field.get(this));        
                        
            if (field.getType().equals(Boolean.TYPE)) {
                try {
                    boolean booleanValue = Boolean.parseBoolean(value);
                    field.setBoolean(this, booleanValue);
                } catch (NumberFormatException e) {
                    quitWithError(name + " value \"" + value + "\" is not a boolean");
                }
            } else if (field.getType().equals(Double.TYPE)) {
                try {
                    double doubleValue = Double.parseDouble(value);
                    field.setDouble(this, doubleValue);
                } catch (NumberFormatException e) {
                    quitWithError(name + " value \"" + value + 
                                  "\" is not a double-precision floating point value");
                }                
            } else if (field.getType().equals(Integer.TYPE)) {
                try {
                    int intValue = Integer.parseInt(value);
                    field.setInt(this, intValue);
                } catch (NumberFormatException e) {
                    quitWithError(name + " value \"" + value + "\" is not an integer");
                }
            } else if (field.getType().equals(Long.TYPE)) {
                try {
                    long longValue = Long.parseLong(value);
                    field.setLong(this, longValue);
                } catch (NumberFormatException e) {
                    quitWithError(name + " value \"" + value + "\" is not a long integer");
                }
            } else if (field.getType().equals(String.class)) {
                field.set(this, value);
            } 
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Could not access field \"" + field + 
                    "\" in order to set with property value", e);
        }
    }
    
    /**
     * Check whether a parameter was specified at the command line or not.
     * @param name The name of the parameter.
     * @return True if the parameter was set, else false.
     */
    protected boolean wasParameterPassed(String name) {
        // if there's an entry in the overwrittenParamDefaults, the parameter was set 
        return overwrittenDefaults.containsKey(name);
    }
    
    /**
     * Performs a check specified by an expression evaluating to a boolean ("test"), and
     * if the check fails, quits with an errorMessage. This method can be used to help to 
     * validate parameters in the validateParameters method, which must be overridden.
     * @param test The expression to test.
     * @param errorMessage The message to be printed if the check does not pass.
     */
    protected void check(boolean test, String errorMessage) {
        if (!test) {
            quitWithError(errorMessage);
        }
    }

    /**
     * Prints the description and usage and quits.
     */
    protected void quitWithHelp() {
        printDescription();
        System.out.println();
        quitWithUsage();
    }    

    /**
     * Quits with the usage message.
     */
    protected void quitWithUsage() {
        printUsage();
        System.exit(1);
    }
    
    /**
     * Quits with a error message and usage.
     * @param errorMessage The error message to relay to the user.
     */
    protected void quitWithError(String errorMessage) {
        System.out.println("ERROR: " + errorMessage + ".");
        System.out.println("Please check your usage.  Here's Graham with a quick reminder:\n");
        quitWithUsage();
    }
    
    /**
     * Prints the contents of the @Description annotation, if one is specified
     * for the class.
     */
    protected void printDescription() {
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Description) {
                String description = ((Description) annotation).value();
                if (description.length() > 0) {
                    System.out.println("DESCRIPTION: " + description);
                }
            }
        }        
    }
    
    /**
     * Prints the usage message for the class's parameters.
     */
    protected void printUsage() {
        StringBuilder usage = new StringBuilder();
        
        String requiredParamsList = getRequiredParametersUsageList();        
        String nonRequiredParamsList = getOptionalParamsUsageList();
        
        usage.append("USAGE: java " + getClass().getCanonicalName());
        if (requiredParamsList.length() > 0) {
            usage.append(" " + getRequiredParametersString());
        }
        if (nonRequiredParamsList.length() > 0) {
            usage.append(" <options>");
        }
        usage.append(System.lineSeparator());
        
        if (requiredParamsList.length() > 0) {
            usage.append("with:");
            usage.append(System.lineSeparator());
            usage.append(requiredParamsList);
        }
        if (nonRequiredParamsList.length() > 0) {
            usage.append(((requiredParamsList.length() > 0) ? "and " : ""));
            usage.append("where possible options include:");
            usage.append(System.lineSeparator());
            usage.append(nonRequiredParamsList);
        }
     
        System.out.println(usage);
    }    
    
    /**
     * Builds the usage list for required parameters
     * @return A string reflecting the usage requirements of required parameters.
     */            
    protected String getRequiredParametersUsageList() {
        StringBuilder list = new StringBuilder();
        
        for (String fieldName : getRequriedParameterFieldNames()) {
            Parameter param = getParameter(fieldName);
            if (param == null) {
                throw new RuntimeException(
                        "Field \"" + fieldName + 
                        "\" specified in RequiredParameters annotation " + 
                        "is not annotated as a parameter in " + 
                        getClass().getCanonicalName());
            } 
            String paramUsageInfo = formatRequiredParameterUsageInfo(fieldName, param); 
            list.append(paramUsageInfo);            
        }        
        
        return list.toString();
    }
    
    /**
     * Format a required parameter for the usage list.
     * @param name The name of the parameter.
     * @param param The parameter annotation for the field corresponding to the parameter
     * @return A string denoting the usage of this parameter.
     */
    protected String formatRequiredParameterUsageInfo(String name, Parameter param) {
        String formattedName = name;
        String formattedValue = "";
        String description = param.value();
        String[] choices = getParameterChoices(name, param);
        String defaultValue = "";
        
        return formatParameterUsageInfo(formattedName, formattedValue, description, 
                                        choices, defaultValue);        
    }    
    
    /**
     * Builds the usage list for optional parameters
     * @return A string reflecting the usage requirements of optional parameters.
     */             
    protected String getOptionalParamsUsageList() {        
        // get and sort fields
        List<String> fieldsList = new ArrayList<>();
        Class<?> currentClass = getClass();
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                fieldsList.add(field.getName());
            }            
            currentClass = currentClass.getSuperclass();
        }
        
        Collections.sort(fieldsList);
        
        // put required fields into a set
        String[] requiredParamFieldNames = getRequriedParameterFieldNames();
        Set<String> requiredParamFieldNamesSet = new HashSet<>();
        for (String fieldName : requiredParamFieldNames) {
            requiredParamFieldNamesSet.add(fieldName);
        }
        
        StringBuilder list = new StringBuilder();
        // find which have fields have options and are not required, and append
        for (String fieldName : fieldsList) {
            Parameter param = getParameter(fieldName);
            if (param != null && !requiredParamFieldNamesSet.contains(fieldName)) {
                String paramUsageInfo = formatOptionalParameterUsageInfo(fieldName, param); 
                list.append(paramUsageInfo);
            }
        }        
        
        return list.toString();
    }
    
    /**
     * Format an optional parameter for the usage list.
     * @param name The name of the parameter.
     * @param param The parameter annotation for the field corresponding to the parameter
     * @return A string denoting the usage of this parameter.
     */    
    protected String formatOptionalParameterUsageInfo(String name, Parameter param) {
        String formattedName = "--" + name;
        String formattedValue = (param.valueAsSwitch().length() > 0) ? "" : "value";
        String description = param.value();
        String[] choices = getParameterChoices(name, param);
        String defaultValue = (param.valueAsSwitch().length() > 0) 
                                  ? "" :  getParameterDefaultAsString(name);
        
        return formatParameterUsageInfo(formattedName, formattedValue, description, 
                                        choices, defaultValue);        
    }
    
    /**
     * Format usage information for a parameter
     * @param name The parameter name as it should appear in the list (e.g. with initial hyphens).
     * @param value The indicative value string.
     * @param description A short description of the parameter.
     * @param choices A String array of possible choices for the parameter. 
     * @param defaultValue The default value of the parameter, if it is optional and not specified.
     * @return Usage information for the parameter as a string.
     */
    protected String formatParameterUsageInfo(String name, String value, String description, 
                                              String[] choices, String defaultValue) {
        String info = USAGE_INDENT + name;
        
        if (value.length() > 0) {
            info += "=" + value;
        }

        if (description.length() > 0) {        
            if (info.length() > USAGE_HANGING_INDENT.length()) {
                info += "\n" + USAGE_HANGING_INDENT;
            } else {
                int spacesToAdd = USAGE_HANGING_INDENT.length() - info.length();
                info += StringUtils.repeat(" ", spacesToAdd);
            }
            
            if (choices.length > 0) {
                description += ". Possible choices are: " + StringUtils.implode(choices, " | ");
            }                    
                        
            if (defaultValue.length() > 0) {
                description += ". Default: " + defaultValue;
            }
            
            info += description;
        }
        
        info += "\n";
        return info;
    }
    
    /**
     * Get the default value of a parameter, converted to a string.
     * @param name The name of the parameter whose default is sought.
     * @return The default value of the parameter in string format.
     */
    protected String getParameterDefaultAsString(String name) {
        Object defaultValue = getParameterDefault(name);
        return (defaultValue != null) ? defaultValue.toString() : "";        
    }
    
    /**
     * Get the default value of a parameter, as an object (could be a 
     * wrapper for a primitive type, e.g. Integer).
     * @param name The name of the parameter.
     * @return The default value of the parameter as an object.
     */
    protected Object getParameterDefault(String name) {
        // default information
        Object defaultValue = null;
        if (wasParameterPassed(name)) {
            defaultValue = overwrittenDefaults.get(name);
        } else {
            Field field = getField(name);
            check(field != null, "Unknown field \"" + name + "\"");
            try {
                field.setAccessible(true);
                defaultValue = field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(
                        "Could not access field \"" + name + "\" to obtain default value", e);
            }
        } 
        return defaultValue;
    }
    
    /**
     * Get the possible limited list of choices for a parameter, as a string array.
     * The string array is constructed from a method call.  The method is specified
     * by the parameter annotation.
     * @param name The name of the parameter.
     * @param param A reference to the annotation of the parameter's instance field.
     * @return A string array denoting the enumeration of possible choices for values 
     * of the parameter.
     */    
    protected String[] getParameterChoices(String name, Parameter param) {
        String[] choices = new String[0];
        
        String choicesMethod = param.choicesMethod();        
        if (choicesMethod.length() > 0) {
            int methodDot = choicesMethod.lastIndexOf(".");
            String className = choicesMethod.substring(0, methodDot);
            String methodName = choicesMethod.substring(methodDot + 1);

            try {
                choices = (String[]) Class.forName(className).getMethod(methodName).invoke(null);
            } catch (NoSuchMethodException | ClassNotFoundException | 
                     IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(
                        "Could not invoke \"" + choicesMethod + 
                        "\" to get choices for option \"" + name + "\"", e);
            }
        }
        return choices;
    }
}
