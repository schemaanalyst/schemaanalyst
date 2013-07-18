package org.schemaanalyst.util.runner;

// This annotation specifies a short description printed out when the user
// runs the class from the command line using the --help parameter
@Description("This class doesn't do anything, it's purpose is to show by example how " + 
             "entry points to the SchemaAnalyst tool can be created by extending " + 
             "the Runner class, and demonstrate the possible features.")

// This annotation sets up the parameters that must be passed from the command line
// They are specified without switches and must be supplied in the order specified
@RequiredParameters("schema_name dbms")
public class RunnerExample extends Runner {
    
    // This annotation specifies a parameter
    @Parameter("The name of the schema")
    private String schema_name;

    // This annotation specifies a parameter along with a method that returns a String array
    // of possible values.  Runner checks that the value passed at the command line is a 
    // member of this array;    
    @Parameter(value="The name of the DBMS", 
               choicesMethod="org.schemaanalyst.dbms.DBMSFactory.getDBMSChoices")
    private String dbms;
    
    // Parameters are not limited to Strings, they can also be of any primitive type. 
    // Runner checks the value passed at the command line are actual ints, doubles, longs etc.
    @Parameter("The number of repetitions for the experiment")
    private int num_repetitions;
    
    // A constructor must be supplied in this format, i.e. with the args parameter and a call
    // to super.
    public RunnerExample(String... args) {
        super(args);
    }
    
    // This method contains the actual high-level steps of the task to be executed.
    public void run() {
        System.out.println("schema_name is " + schema_name);
        System.out.println("dbms is " + dbms);
        System.out.println("num_repetitions is " + num_repetitions);
        printUsage();
    }

    // This method provides further validation steps on the parameter values.
    protected void validateParameters() {
        if (num_repetitions <= 0) {
            quitWithError("num_repetitions should be 1 or greater");
        }
    }    
    
    // A main method must be provided in this format (i.e. construction with args and 
    // a call to the run method.
    public static void main(String... args) {
        new RunnerExample(args).run();
    }
}
