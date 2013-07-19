package org.schemaanalyst.util.runner;

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

public abstract class Runner {
    
    public static final String LONG_OPTION_PREFIX = "--";
    public static final String HELP_OPTION = LONG_OPTION_PREFIX + "help";
    
    // what to make the value of an option if no value is specified on the command line
    protected static final String NO_OPTION_CLI_VALUE_DEFAULT = "true";

    // must be spaces, not tabs to work properly:
    protected static final String USAGE_INDENT = StringUtils.repeat(" ", 4);
    protected static final String USAGE_HANGING_INDENT = StringUtils.repeat(USAGE_INDENT, 4);
    
    // a store of overwritten default values 
    protected HashMap<String, Object> overwrittenDefaults;
    
    // various configurations
    protected FolderConfiguration folderConfiguration;
    protected DatabaseConfiguration databaseConfiguration;
    protected LoggingConfiguration loggingConfiguration;

    public Runner() {
        this(true);
    }
    
    public Runner(boolean loadConfiguration) {
        if (loadConfiguration) {
            loadConfiguration();
        }
    }
    
    public abstract void run(String... args);
    
    protected abstract void validateParameters();    
    
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

    protected void parseArgs(String... args) {
        // record which parameters were parsed and their original values
        overwrittenDefaults = new HashMap<>();
        
        String[] requiredParams = getRequriedParameterFieldNames(); 
        int numRequiredParamsProcessed = 0;
        
        for (String arg : args) {
            if (arg.equals(HELP_OPTION)) {
                quitWithHelp();
            }

            if (arg.startsWith(LONG_OPTION_PREFIX)) {                               
                String fieldName = "";
                String value = "";
                int equalsPos = arg.indexOf("=");
                if (equalsPos == -1) {
                    fieldName = arg.substring(1);
                    value = NO_OPTION_CLI_VALUE_DEFAULT;
                } else {
                    fieldName = arg.substring(LONG_OPTION_PREFIX.length(), equalsPos);
                    value = arg.substring(equalsPos + 1);
                }
                setFieldWithParameterValue(fieldName, value);                
            } else {
                if (numRequiredParamsProcessed < requiredParams.length) {
                    String fieldName = requiredParams[numRequiredParamsProcessed];
                    
                    if (!isField(fieldName)) {
                        throw new RuntimeException(
                                "Required option \"" + fieldName + 
                                "\" is not a field of " +
                                getClass().getCanonicalName());
                    }
                    
                    if (!isParameter(fieldName)) {
                        throw new RuntimeException(
                                "Required option field \"" + fieldName + 
                                "\" is not specified as an option for " +
                                getClass().getCanonicalName());
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
    
    protected String getRequiredParametersString() {
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof RequiredParameters) {
                RequiredParameters requiredParams = (RequiredParameters) annotation;
                String allParams = requiredParams.value();
                return allParams.replace("\\s+", " ");
            }
        }
        return "";
    }
    
    protected String[] getRequriedParameterFieldNames() {
        return getRequiredParametersString().split(" ");
    }
    
    protected boolean isField(String name) {
        return getField(name) != null;
    }
    
    protected Field getField(String name) {
        try {
            return this.getClass().getDeclaredField(name);
        } catch (NoSuchFieldException e) {           
        } 
        return null;  
    }
    
    protected boolean isParameter(String name) {
        return getParameter(name) != null;
    }
    
    protected Parameter getParameter(String name) {        
        return getParameter(getField(name));        
    }
    
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

    protected void setFieldWithParameterValue(String name, String value) {
        // get hold of the instance field
        Field field = getField(name);
        check(field != null, "Unknown parameter \"" + name + "\"");

        // get hold of the option instance for the field
        Parameter param = getParameter(field);
        check(param != null, "Unknown parameter \"" + name + "\"");

        // parse the value into the field
        field.setAccessible(true);
        try {
            // store the original value of the field
            overwrittenDefaults.put(name, field.get(this));        
                        
            if (field.getType().equals(Integer.TYPE)) {
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
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected boolean wasOptionalParameterSpecified(String name) {
        // if there's an entry in the overwrittenParamDefaults, the parameter was set 
        return overwrittenDefaults.containsKey(name);
    }
    
    protected void check(boolean test, String errorMessage) {
        if (!test) {
            quitWithError(errorMessage);
        }
    }

    protected void quitWithHelp() {
        printDescription();
        System.out.println();
        quitWithUsage();
    }    
    
    protected void quitWithUsage() {
        printUsage();
        System.exit(1);
    }
    
    protected void quitWithError(String errorMessage) {
        System.out.println("ERROR: " + errorMessage + ".");
        System.out.println("Please check your usage.  Here's Graham with a quick reminder:\n");
        quitWithUsage();
    }
    
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
    
    protected String getRequiredParametersUsageList() {
        StringBuilder list = new StringBuilder();
        
        for (String fieldName : getRequriedParameterFieldNames()) {
            Parameter param = getParameter(fieldName);
            if (param == null) {
                throw new RuntimeException(
                        "Field \"" + fieldName + 
                        "\" specified in RequiredParameters annotation " + 
                        "is not annotated as a parameter in " + getClass().getCanonicalName());
            } 
            String paramUsageInfo = formatRequiredParameterUsageInfo(fieldName, param); 
            list.append(paramUsageInfo);            
        }        
        
        return list.toString();
    }
    
    protected String formatRequiredParameterUsageInfo(String name, Parameter param) {
        String formattedName = name;
        String formattedValue = "";
        String description = param.value();
        String[] choices = getParameterChoices(name, param);
        String defaultValue = "";
        
        return formatParameterUsageInfo(formattedName, formattedValue, description, choices, defaultValue);        
    }    
    
    protected String getOptionalParamsUsageList() {
        // sort fields        
        Field[] fields = getClass().getDeclaredFields();
        List<String> fieldsList = new ArrayList<String>();
        for (Field field : fields) {
            fieldsList.add(field.getName());
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
    
    protected String formatOptionalParameterUsageInfo(String name, Parameter param) {
        String formattedName = "--" + name;
        String formattedValue = "<value>";
        String description = param.value();
        String[] choices = getParameterChoices(name, param);
        String defaultValue = getParameterDefaultAsString(name);
        
        return formatParameterUsageInfo(formattedName, formattedValue, description, choices, defaultValue);        
    }
    
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
    
    protected String getParameterDefaultAsString(String name) {
        Object defaultValue = getParameterDefault(name);
        return (defaultValue != null) ? defaultValue.toString() : "";        
    }
    
    protected Object getParameterDefault(String name) {
        // default information
        Object defaultValue = null;
        if (wasOptionalParameterSpecified(name)) {
            defaultValue = overwrittenDefaults.get(name);
        } else {
            Field field = getField(name);
            check(field != null, "Unknown field \"" + name + "\"");
            try {
                field.setAccessible(true);
                defaultValue = field.get(this);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("Cannot access field \"" + name + "\"");
            }
        } 
        return defaultValue;
    }
    
    protected String[] getParameterChoices(String name, Parameter param) {
        String[] choices = new String[0];
        
        String choicesMethod = param.choicesMethod();        
        if (choicesMethod.length() > 0) {
            int methodDot = choicesMethod.lastIndexOf(".");
            String className = choicesMethod.substring(0, methodDot);
            String methodName = choicesMethod.substring(methodDot + 1);

            try {
                choices = (String[]) Class.forName(className).getMethod(methodName).invoke(null);
            } catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
                    IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException("Could not invoke \"" + choicesMethod + 
                                           "\" to get choices for option \"" + name + "\"");
            }
        }
        return choices;
    }
}
