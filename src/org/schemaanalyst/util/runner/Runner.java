package org.schemaanalyst.util.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
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
    protected static final String USAGE_PARAM_INDENT = 
            StringUtils.repeat(" ", 4);
    
    // a repetition of the above
    protected static final String USAGE_PARAM_DESCRIPTION_INDENT = 
            StringUtils.repeat(USAGE_PARAM_INDENT, 4);
    
    // various configurations
    protected FolderConfiguration folderConfiguration;
    protected DatabaseConfiguration databaseConfiguration;
    protected LoggingConfiguration loggingConfiguration;

    public Runner(String... args) {
        parseArgs(args);
        loadConfiguration();
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
        String[] requiredParams = getRequriedParamFieldNames(); 
        int numRequiredParamsProcessed = 0;
        
        for (String arg : args) {
            if (arg.equals(HELP_OPTION)) {
                quitWithUsage();
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
                processParam(fieldName, value);                
            } else {
                if (numRequiredParamsProcessed < requiredParams.length) {
                    String fieldName = requiredParams[numRequiredParamsProcessed];
                    
                    if (!isField(fieldName)) {
                        throw new RuntimeException(
                                "Required option \"" + fieldName + 
                                "\" is not a field of " +
                                getClass().getCanonicalName());
                    }
                    
                    if (!isParam(fieldName)) {
                        throw new RuntimeException(
                                "Required option field \"" + fieldName + 
                                "\" is not specified as an option for " +
                                getClass().getCanonicalName());
                    }
                    
                    processParam(fieldName, arg);
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
    
    protected String getRequiredParamsString() {
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
    
    protected String[] getRequriedParamFieldNames() {
        return getRequiredParamsString().split(" ");
    }
    
    protected boolean isField(String fieldName) {
        return getField(fieldName) != null;
    }
    
    protected Field getField(String fieldName) {
        try {
            return this.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {           
        } 
        return null;  
    }
    
    protected boolean isParam(String fieldName) {
        return getParam(fieldName) != null;
    }
    
    protected Parameter getParam(String fieldName) {        
        return getParam(getField(fieldName));        
    }
    
    protected Parameter getParam(Field field) {
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

    protected void processParam(String fieldName, String value) {
        // get hold of the instance field
        Field field = getField(fieldName);
        if (field == null) {
            quitWithError("Unknown option \"" + fieldName + "\"");
        }

        // get hold of the option instance for the field
        Parameter param = getParam(field);
        if (param == null) {
            quitWithError("Unknown option \"" + fieldName + "\"");
        }

        // parse the value into the field
        field.setAccessible(true);
        try {
            if (field.getType().equals(String.class)) {
                field.set(this, value);
            } else if (field.getType().equals(Integer.TYPE)) {
                try {
                    int intValue = Integer.parseInt(value);
                    field.setInt(this, intValue);
                } catch (NumberFormatException e) {
                    quitWithError("Unable to parse value \"" + value + "\" for " + fieldName + " as an integer");
                }
            } else if (field.getType().equals(Long.TYPE)) {
                try {
                    long longValue = Long.parseLong(value);
                    field.setLong(this, longValue);
                } catch (NumberFormatException e) {
                    quitWithError("Unable to parse value \"" + value + "\" for " + fieldName + " as a long");
                }
            }   
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
        
    protected void quitWithError(String errorMessage) {
        System.out.println(errorMessage);
        quitWithUsage();
    }

    protected void quitWithUsage() {
        usage();
        System.exit(1);
    }
    
    protected void usage() {
        StringBuilder usage = new StringBuilder();
        
        String requiredParamsList = getRequiredParamsList();        
        String nonRequiredParamsList = getNonRequiredParamsList();
        
        usage.append("Usage: java " + getClass().getCanonicalName());
        if (requiredParamsList.length() > 0) {
            usage.append(" " + getRequiredParamsString());
        }
        if (nonRequiredParamsList.length() > 0) {
            usage.append(" <options>");
        }
        usage.append(System.lineSeparator());
        
        if (requiredParamsList.length() > 0) {
            usage.append("where:");
            usage.append(System.lineSeparator());
            usage.append(requiredParamsList);
        }
        if (nonRequiredParamsList.length() > 0) {
            usage.append(((requiredParamsList.length() > 0) ? "and" : "where"));
            usage.append(" possible options include:");
            usage.append(System.lineSeparator());
            usage.append(nonRequiredParamsList);
        }
     
        System.out.println(usage);
    }    
    
    protected String getRequiredParamsList() {
        StringBuilder list = new StringBuilder();
        
        for (String fieldName : getRequriedParamFieldNames()) {
            Parameter param = getParam(fieldName);
            if (param == null) {
                throw new RuntimeException("xxx to complete xxx");
            }              
            list.append(getParamInfo(fieldName, "", param));
        }        
        
        return list.toString();
    }
    
    protected String getNonRequiredParamsList() {
        // sort fields        
        Field[] fields = getClass().getDeclaredFields();
        List<String> fieldsList = new ArrayList<String>();
        for (Field field : fields) {
            fieldsList.add(field.getName());
        }
        Collections.sort(fieldsList);
        
        // put required fields into a set
        String[] requiredParamFieldNames = getRequriedParamFieldNames();
        Set<String> requiredParamFieldNamesSet = new HashSet<>();
        for (String fieldName : requiredParamFieldNames) {
            requiredParamFieldNamesSet.add(fieldName);
        }
        
        StringBuilder list = new StringBuilder();
        // find which have fields have options and are not required, and append
        for (String fieldName : fieldsList) {
            Parameter param = getParam(fieldName);
            if (param != null && !requiredParamFieldNamesSet.contains(fieldName)) {
                String name = "--" + fieldName;
                String value = "<value>";
                list.append(getParamInfo(name, value, param));
            }
        }        
        
        return list.toString();
    }
    
    protected String getParamInfo(String name, String value, Parameter option) {
        String info = USAGE_PARAM_INDENT + name;
        
        if (value.length() > 0) {
            info += "=" + value;
        }

        String description = option.value();
        if (description.length() > 0) {        
            if (info.length() > USAGE_PARAM_DESCRIPTION_INDENT.length()) {
                info += "\n" + USAGE_PARAM_DESCRIPTION_INDENT;
            } else {
                int spacesToAdd = USAGE_PARAM_DESCRIPTION_INDENT.length() - info.length();
                for (int i=0; i < spacesToAdd; i++) {                    
                    info += " ";
                }
            }
            
            String choicesMethod = option.choicesMethod();
            if (choicesMethod.length() > 0) {
                int methodDot = choicesMethod.lastIndexOf(".");
                String className = choicesMethod.substring(0, methodDot);
                String methodName = choicesMethod.substring(methodDot + 1);
                
 
                try {
                    String[] choices = (String[]) Class.forName(className).getMethod(methodName).invoke(null);
                    if (choices.length > 0) {
                        String allChoices = StringUtils.implode(choices, " | ");
                        description += ". Possible choices are: " + allChoices;
                    }                    
                } catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
                        IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException("Could not invoke \"" + choicesMethod + "\" to get choices for option \"" + name + "\"");
                }
            }
            
            info += description + "\n";
        }
        
        return info;
    }

    public abstract void run();
}
