package org.schemaanalyst.util.runner;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.configuration.DataCapturerConfiguration;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.LogManager;

/**
 * <p>Represents an entry point to the SchemaAnalyst system, parses in key
 * configuration files and allows instance fields to take on values specified
 * from the command line with the help of the {@link Description @Description},
 * {@link RequiredParameters @RequiredParameters} and {@link Parameter @Parameter} 
 * annotations.</p>
 *
 * <p>Important nomenclature:
 * <ul>
 * <li><strong>Arguments (args) </strong>&emdash;raw values passed to a main method at
 * the command line.</li>
 * <li><strong>Parameters</strong>&emdash;name/value pairs for which a value may be
 * required or optional. For required parameters, only the value is passed at
 * the command line whereas for optional parameters, the name must be passed
 * with the value. (Furthermore, some optional parameters may be switches
 * (boolean flags) in which case no value is passed, just the name, in order to
 * set the switch.) Required parameters are specified in the space-delimited
 * value of a {@link RequiredParameters @RequiredParameters} annotation for a class. 
 * Every other parameter is assumed to be optional.</li>
 * <li><strong>Fields</strong> -- instance fields of a class which are mapped to
 * parameters through the {@link Parameter @Parameter} annotation.</li>
 * </ul>
 * </p>
 * 
 * @author Phil McMinn
 */
public abstract class Runner {

    /** The prefix for long options on the command line. */
    public static final String LONG_OPTION_PREFIX = "--";
    
    /** The option string for help. */
    public static final String HELP_OPTION = LONG_OPTION_PREFIX + "help";
    
    /** The string used as an indent for printed usage information. */    
    protected static final String USAGE_INDENT = StringUtils.repeat(" ", 4);
    
    /** The string used as a hanging indent for options in printed usage information. */
    protected static final String USAGE_HANGING_INDENT = StringUtils.repeat(USAGE_INDENT, 4);
    
    /** The locations configuration field. */
    protected LocationsConfiguration locationsConfiguration;
    
    /** The databases configuration field. */
    protected DatabaseConfiguration databaseConfiguration;
    
    /** The data capture configuration field. */
    protected DataCapturerConfiguration capturerConfiguration;
    
    /** Parameter/field/defaults introspection information. */    
    protected List<String> requiredParameterNames;
    
    /** Parameter/field/defaults introspection information. */
    protected Map<String, Field> parameterFields;
    
    /** Parameter/field/defaults introspection information. */
    protected Map<String, Parameter> parameters;
    
    /** Parameter/field/defaults introspection information. */
    protected Map<String, Object> optionalParameterDefaults;

    /** The set of parameters parsed in from {@code args}. */
    protected Set<String> parsedParameters;
    
    /** 
     * Stores the command line output stream as an instance variable so that it
     * can be mocked or redirected.
     */
    protected PrintStream out = System.out;

    /** ensures the process does not abnormally terminate by catching all exceptions. */
    protected boolean catchExceptions;
    
    /** Sets whether the configuration is to be loaded on initialisation or not. **/
    protected boolean loadConfiguration;

    /**
     * Constructor.
     */
    public Runner() {
        this(true, true);
    }

    /**
     * Constructor.
     * @param loadConfiguration Set whether to load the configuration or not
     * during initialisation.
     * @param catchExceptions If set to true, the Runner handles all
     * RuntimeExceptions rather than letting them terminate the current Java
     * process.
     */
    public Runner(boolean loadConfiguration, boolean catchExceptions) {
        this.loadConfiguration = loadConfiguration;
        this.catchExceptions = catchExceptions;
    }

    /**
     * Describes the main steps of the task of the runner
     */
    protected abstract void task();

    /**
     * This method should be overridden to perform any additional validation
     * required of the field values passed in through the values of args to the
     * run method.
     */
    protected abstract void validateParameters();

    /**
     * Executes the runner. All exceptions thrown are caught, so that the Java
     * process remains unterminated.
     * @param args The arguments passed from the command line.
     */
    public void run(String... args) {
        if (catchExceptions) {
            try {
                doRun(args);
            } catch (ArgumentException | ExitException e) {
                // do nothing -- information already logged
            } catch (RuntimeException e) {
                e.printStackTrace(out);
            }
        } else {
            try {
                doRun(args);
            } catch (ExitException e) {
                System.exit(e.getCode());
            }
        }
    }

    /**
     * Initialises the runner and executes the Runner's task.
     * @param args The arguments passed from the command line.
     */
    protected void doRun(String... args) {
        initialise(args);
        task();
    }

    /**
     * Initialises the Runner by performing introspection of parameters, parsing
     * args, validating parameters and loading general configuration
     * information.
     * @param args The arguments passed from the command line.
     */
    protected void initialise(String... args) {
        inspectParameters();
        parseArgs(args);
        validateParameters();
        if (loadConfiguration) {
            loadConfiguration();
        }
    }

    /**
     * Loads the properties files from their default locations.
     */
    protected void loadConfiguration() {
        // load configurations wrt locations and databases
        locationsConfiguration = new LocationsConfiguration();
        databaseConfiguration = new DatabaseConfiguration();
        capturerConfiguration = new DataCapturerConfiguration();
        loadLoggingConfiguration();
    }

    /**
     * Loads the logging properties file. Uses the priority ordering: 1) Command
     * line argument, 2) .local properties file, 3) Properties file
     */
    private void loadLoggingConfiguration() {
        // load logging properties
        Properties prop = System.getProperties();
        if (!prop.contains("java.util.logging.config.file")) {
            String loggingProperties = locationsConfiguration.getConfigDir() + File.separator + "logging.properties";
            if (new File(loggingProperties + ".local").exists()) {
                loggingProperties += ".local";
            }
            prop.setProperty("java.util.logging.config.file", loggingProperties);
        }
        try {
            LogManager.getLogManager().readConfiguration();
        } catch (IOException | SecurityException ex) {
            throw new RunnerException("Failed to load logging configuration.", ex);
        }
    }

    /**
     * Furnishes parameter/field information through reflection.
     */
    protected void inspectParameters() {
        acquireRequiredParameterNames();
        acquireParametersAndFieldInformation();
        crosscheckRequiredParameters();
    }

    /**
     * Goes through class and back through subclasses collecting parameter-field
     * information.
     */
    protected void acquireParametersAndFieldInformation() {
        parameters = new HashMap<>();
        parameterFields = new HashMap<>();
        optionalParameterDefaults = new HashMap<>();

        // get all fields with Parameter annotations
        // and put them in the appropriate data structure
        // (requiredParameters & requiredParameterFields or
        // (optionalParameters & optionalParameterFields)
        Class<?> currentClass = getClass();
        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {

                // Go through the field's annotations to see 
                // if it is a parameter
                Annotation[] annotations = field.getAnnotations();

                for (Annotation annotation : annotations) {
                    if (annotation instanceof Parameter) {

                        // it's a parameter
                        String name = field.getName();
                        Parameter parameter = (Parameter) annotation;

                        // if we already have this parameter from an
                        // earlier sub-class, ignore it.
                        if (!parameters.containsKey(name)) {
                            parameters.put(name, parameter);
                            parameterFields.put(name, field);

                            // we need to be able to set the field
                            field.setAccessible(true);
                        }

                        boolean optional = isOptionalParameter(name);
                        if (optional) {
                            // save the default value
                            try {
                                optionalParameterDefaults.put(name, field.get(this));
                            } catch (IllegalAccessException e) {
                                throw new RunnerException(
                                        "Property field \"" + name + "\" cannot be accessed", e);
                            }
                        }
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    /**
     * Acquires the list of required parameter names from the
     * {@link RequiredParameters @RequiredParameters} annotation. 
     * If one does not exist for the current class, parent classes 
     * are explored by moving back through the class's
     * inheritance hierarchy.
     */
    protected void acquireRequiredParameterNames() {
        requiredParameterNames = new ArrayList<>();

        Class<?> currentClass = getClass();
        while (currentClass != null) {
            Annotation[] annotations = currentClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequiredParameters) {
                    String str = ((RequiredParameters) annotation).value();
                    requiredParameterNames = Arrays.asList(StringUtils.split(str));
                    return;
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    /**
     * Ensure each field name extracted from the 
     * {@link RequiredParameters @RequiredParameters} annotation 
     * was found as an actual parameter.
     */
    protected void crosscheckRequiredParameters() {
        for (String name : requiredParameterNames) {
            if (!parameters.containsKey(name)) {
                throw new RunnerException(
                        "\"" + name + "\" specified in @RequiredParameters is not "
                        + "a parameter for " + getClass());
            }
        }
    }

    /**
     * Parses args passed in from the command line into field values.
     * @param args The arguments passed in from the command line.
     */
    protected void parseArgs(String... args) {
        parsedParameters = new HashSet<>();
        int numReqProcessed = 0;
        for (String arg : args) {
            // if the user wants help, give it to them
            if (arg.equals(HELP_OPTION)) {
                exitWithHelp();
            }

            if (arg.startsWith(LONG_OPTION_PREFIX)) {
                parseOptionalParameter(arg);
            } else {
                // parse a required parameter
                if (numReqProcessed < requiredParameterNames.size()) {
                    parseRequiredParameter(numReqProcessed, arg);
                    numReqProcessed++;
                } else {
                    out.println(numReqProcessed + " " + requiredParameterNames.size());
                    exitWithArgumentException("Too many arguments");
                }
            }
        }

        if (numReqProcessed < requiredParameterNames.size()) {
            exitWithArgumentException("No value supplied for "
                    + requiredParameterNames.get(numReqProcessed));
        }
    }

    /**
     * Parses a required parameter from the args list.
     * @param n The number of the required parameter as it appears in the
     * {@link RequiredParameters @RequiredParameters} declaration.
     * @param arg The argument string passed in from args.
     */
    protected void parseRequiredParameter(int n, String arg) {
        String name = requiredParameterNames.get(n);
        setField(name, arg);
    }

    /**
     * Parses an optional parameter from the args list.
     * @param arg The argument string passed in from args.
     */
    protected void parseOptionalParameter(String arg) {
        String name;
        String value = "";
        int startPos = LONG_OPTION_PREFIX.length();
        int equalsPos = arg.indexOf('=');
        if (equalsPos == -1) {
            name = arg.substring(startPos);
        } else {
            name = arg.substring(startPos, equalsPos);
            value = arg.substring(equalsPos + 1);
        }

        // this could be a user error (mistyped parameter name), so no exception thrown
        if (!parameters.containsKey(name)) {
            exitWithArgumentException("No such property \"" + name + "\" for " + getClass());
        }

        // if it's a required property, it cannot be set using a long option name
        if (isRequiredParameter(name)) {
            exitWithArgumentException("Property \"" + name + "\" is required and cannot be set using a long option name");
        }

        setField(name, value);
    }

    /**
     * Sets a field specified by the string name with the value specified by the
     * string value. If the field is not a string (i.e., an int, double etc.),
     * the value is parsed.
     * @param name The name of the field to set.
     * @param value The value with which to set it.
     */
    protected void setField(String name, String value) {
        // if choices are available for this method, ensure that the value 
        // is equal to one of them
        List<String> choices = acquireParameterChoices(name);
        if (choices.size() > 0) {
            boolean foundMatch = false;
            for (String choice : choices) {
                if (choice.equals(value)) {
                    foundMatch = true;
                } else if (value.contains(":")) {
                    if (value.split(":")[0].equals(choice)) {
                        foundMatch = true;
                    }
                }
            }
            check(foundMatch,
                    name + " value \"" + value
                    + "\" is not one of the list of pre-specified choices");
        }

        // is the value a switch?
        String valueAsSwitch = parameters.get(name).valueAsSwitch();
        if (valueAsSwitch.length() > 0) {
            check(value.length() == 0,
                    name + " is a switch, so no value is requried");
            value = valueAsSwitch;
        }

        // parse the value into the field
        Field field = parameterFields.get(name);
        Class<?> type = field.getType();
        try {
            if (type.equals(Boolean.TYPE)) {
                try {
                    boolean booleanValue = Boolean.parseBoolean(value);
                    field.setBoolean(this, booleanValue);
                } catch (NumberFormatException e) {
                    exitWithArgumentException(name + " value \"" + value + "\" is not a boolean");
                }
            } else if (type.equals(Double.TYPE)) {
                try {
                    double doubleValue = Double.parseDouble(value);
                    field.setDouble(this, doubleValue);
                } catch (NumberFormatException e) {
                    exitWithArgumentException(name + " value \"" + value
                            + "\" is not a double-precision floating point value");
                }
            } else if (type.equals(Integer.TYPE)) {
                try {
                    int intValue = Integer.parseInt(value);
                    field.setInt(this, intValue);
                } catch (NumberFormatException e) {
                    exitWithArgumentException(name + " value \"" + value + "\" is not an integer");
                }
            } else if (type.equals(Long.TYPE)) {
                try {
                    long longValue = Long.parseLong(value);
                    field.setLong(this, longValue);
                } catch (NumberFormatException e) {
                    exitWithArgumentException(name + " value \"" + value + "\" is not a long integer");
                }
            } else if (type.equals(String.class)) {
                field.set(this, value);
            }
        } catch (IllegalAccessException e) {
            throw new RunnerException(
                    "Could not access field \"" + field
                    + "\" in order to set with property value", e);
        }

        parsedParameters.add(name);
    }

    /**
     * Get the possible limited list of choices for a parameter, as a string
     * array. The string array is constructed from a method call. The method is
     * specified by the parameter annotation in the form "{@code class.method}".
     * @param name The name of the parameter.
     * @return A string list denoting the enumeration of possible choices for
     * values of the parameter.
     */
    @SuppressWarnings("unchecked")
    protected List<String> acquireParameterChoices(String name) {
        List<String> choices = new ArrayList<>();

        String choicesMethod = parameters.get(name).choicesMethod();
        if (choicesMethod.length() > 0) {
            int methodDot = choicesMethod.lastIndexOf('.');
            String className = choicesMethod.substring(0, methodDot);
            String methodName = choicesMethod.substring(methodDot + 1);

            try {
                choices = (List<String>) Class.forName(className).getMethod(methodName).invoke(null);
            } catch (NoSuchMethodException | ClassNotFoundException |
                    IllegalAccessException | InvocationTargetException e) {
                throw new RunnerException(
                        "Could not invoke \"" + choicesMethod
                        + "\" to get choices for option \"" + name + "\"", e);
            } catch (ClassCastException e) {
                throw new RunnerException(
                        "Incorrect return type for choices method \"" + choicesMethod
                        + "\" for \"" + name + "\" (should be List<String>)", e);
            }
        }

        return choices;
    }

    /**
     * Check whether a parameter is a required parameter.
     * @return True if the parameter is required, else false.
     */
    protected boolean isRequiredParameter(String name) {
        return requiredParameterNames.contains(name);
    }

    /**
     * Check whether a parameter is an optional parameter.
     * @return True if the parameter is optional, else false.
     */
    protected boolean isOptionalParameter(String name) {
        return !isRequiredParameter(name);
    }

    /**
     * Check whether the value for a parameter was parsed at the command line or
     * not.
     * @param name The name of the parameter.
     * @return True if the parameter was set, else false.
     */
    protected boolean wasParameterPassed(String name) {
        return parsedParameters.contains(name);
    }

    /**
     * Performs a check specified by an expression evaluating to a boolean
     * ("test"), and if the check fails, quits with an errorMessage. This method
     * can be used to help to validate parameters in the validateParameters
     * method, which must be overridden.
     * @param test The expression to test.
     * @param errorMessage The message to be printed if the check does not pass.
     */
    protected void check(boolean test, String errorMessage) {
        if (!test) {
            exitWithArgumentException(errorMessage);
        }
    }

    /**
     * Prints the description and usage and quits.
     */
    protected void exitWithHelp() {
        String description = getDescription();
        if (description != null) {
            out.println(getDescription());
            out.println();
        }
        out.println(getUsage());
        throw new ExitException(1);
    }

    /**
     * Quits with a error message and usage.
     * @param message The error message to relay to the user.
     */
    protected void exitWithArgumentException(String message) {
        out.println("ERROR: " + message + ".\n"
                + "Please check your usage.  "
                + "Here's Graham with a quick reminder:\n");
        out.println(getUsage());
        throw new ArgumentException(message);
    }

    /**
     * Get the value of the {@link Description} annotation of this 
     * Runner class, if one exists.
     * @return The description of the Runner.
     */
    protected String getDescription() {
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Description) {
                return ((Description) annotation).value();
            }
        }
        return null;
    }

    /**
     * Gets the usage message for the class's parameters.
     */
    protected String getUsage() {
        StringBuilder usage = new StringBuilder();

        boolean haveRequired = requiredParameterNames.size() > 0;
        boolean haveOptional = parameters.size() > requiredParameterNames.size();

        usage.append("USAGE: java ").append(getClass().getCanonicalName()).append(" ");
        if (haveRequired) {
            usage.append(StringUtils.join(requiredParameterNames, " ")).append(" ");
        }
        if (haveOptional) {
            usage.append("<options>");
        }
        usage.append(System.lineSeparator());

        if (haveRequired) {
            usage.append("with:");
            usage.append(System.lineSeparator());
            usage.append(getRequiredParametersUsage());
        }
        if (haveOptional) {
            if (haveRequired) {
                usage.append("and ");
            }
            usage.append("where possible options include:");
            usage.append(System.lineSeparator());
            usage.append(getOptionalParametersUsage());
        }

        return usage.toString();
    }

    /**
     * Builds the usage list for required parameters.
     * @return A string reflecting the usage requirements of required
     * parameters.
     */
    protected String getRequiredParametersUsage() {
        StringBuilder list = new StringBuilder();

        for (String name : requiredParameterNames) {
            String usage = getParameterUsage(name);
            list.append(usage);
        }

        return list.toString();
    }

    /**
     * Builds the usage list for optional parameters.
     * @return A string reflecting the usage requirements of optional
     * parameters.
     */
    protected String getOptionalParametersUsage() {
        List<String> names = new ArrayList<>();
        names.addAll(parameters.keySet());
        Collections.sort(names);

        StringBuilder list = new StringBuilder();
        for (String name : names) {
            // don't re-include required parameters in the list
            if (isOptionalParameter(name)) {
                String usage = getParameterUsage(name);
                list.append(usage);
            }
        }

        return list.toString();
    }

    /**
     * Formats a parameter for the usage list.
     * @param name The name of the parameter.
     * @return A string denoting the usage of this parameter.
     */
    protected String getParameterUsage(String name) {
        Parameter parameter = parameters.get(name);
        boolean required = isRequiredParameter(name);

        String formattedName = name;
        if (!required) {
            formattedName = LONG_OPTION_PREFIX + name;
        }

        String value = "";
        if (!required) {
            if (parameter.valueAsSwitch().length() == 0) {
                value = "=<value>";
            }
        }

        String description = parameter.value();

        String choices = StringUtils.join(acquireParameterChoices(name), " | ");

        String defaultValue = "";
        if (!required) {
            Object defaultObject = optionalParameterDefaults.get(name);
            if (defaultObject != null) {
                defaultValue = optionalParameterDefaults.get(name).toString();
            }
        }

        String info = USAGE_INDENT + formattedName + value;

        if (description.length() > 0) {
            if (info.length() > USAGE_HANGING_INDENT.length()) {
                info += "\n" + USAGE_HANGING_INDENT;
            } else {
                int spacesToAdd = USAGE_HANGING_INDENT.length() - info.length();
                info += StringUtils.repeat(" ", spacesToAdd);
            }

            if (choices.length() > 0) {
                description += ". Possible choices are: " + choices;
            }

            if (defaultValue.length() > 0) {
                description += ". Default: " + defaultValue;
            }

            info += description;
        }

        info += "\n";
        return info;
    }
}
