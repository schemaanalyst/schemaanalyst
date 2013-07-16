package org.schemaanalyst.util.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.FolderConfiguration;
import org.schemaanalyst.configuration.LoggingConfiguration;

public abstract class Runner {
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
        for (String arg : args) {
            if (arg.equals("-help")) {
                usage();  // exits
            }

            if (!arg.startsWith("-")) {
                System.out.println("Unknown option \"" + arg + "\"");
                System.exit(1);
            }

            // extract option name and value pair
            String name = "", value = "";
            int equalsPos = arg.indexOf("=");
            if (equalsPos == -1) {
                name = arg.substring(1);
                value = "true";
            } else {
                name = arg.substring(1, equalsPos);
                value = arg.substring(equalsPos + 1);
            }

            // get hold of the instance field
            Field field = null;
            try {
                field = this.getClass().getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                System.out.println("Unknown option \"" + name + "\"");
                System.exit(1);
            }

            // get hold of the option instance for the field
            Option option = getOptionForField(field);
            if (option == null) {
                System.out.println("Unknown option \"" + name + "\"");
                System.exit(1);
            }

            // parse the value into the field
            Class<?> fieldClass = field.getType();
            if (fieldClass.equals(String.class)) {
                try {
                    field.set(this, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else if (fieldClass.equals(Integer.TYPE)) {
                try {
                    int intValue = Integer.parseInt(value);
                    field.setInt(this, intValue);
                } catch (NumberFormatException e) {
                    System.out.println("Unable to parse value \"" + value + "\" for " + name + " as an integer");
                    System.exit(1);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected Field getFieldForArgName(String name) {
        Field field = null;
        try {
            field = this.getClass().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            System.out.println("Unknown option \"" + name + "\"");
            System.exit(1);
        }
        return field;
    }

    protected Option getOptionForField(Field field) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Option) {
                return (Option) annotation;
            }
        }
        return null;
    }

    protected void cannotParse(String name, String value, String type) {
        System.out.println("Unable to parse " + name + " value \"" + value + "\" as " + type);
        System.exit(1);
    }

    protected void usage() {
        Class<?> thisClass = this.getClass();
        StringBuilder options = new StringBuilder();
        thisClass.getFields();

        for (Field field : thisClass.getDeclaredFields()) {
            Option option = getOptionForField(field);
            if (option != null) {
                String name = field.getName();
                String valueString = "<value>";
                String descriptionString = option.value();
                appendOption(options, name, valueString, descriptionString);
            }
        }

        System.out.println("Usage: java " + thisClass.getCanonicalName() + " <options>");
        System.out.println("where options include:");
        System.out.println(options);
        System.exit(1);
    }

    protected void appendOption(StringBuilder sb, String name, String valueString, String descriptionString) {
        sb.append("\t-" + name);

        if (!valueString.isEmpty()) {
            sb.append("=" + valueString);
        }

        sb.append("\n");

        if (!descriptionString.isEmpty()) {
            sb.append("\t\t\t" + descriptionString + "\n");
        }
    }

    public abstract void run();
}
