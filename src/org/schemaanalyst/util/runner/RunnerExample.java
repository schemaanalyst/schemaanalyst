package org.schemaanalyst.util.runner;

// This annotation specifies a short description printed out when the user
// runs the class from the command line using the --help parameter
@Description("This class doesn't do anything, it's purpose is to show by example how " + 
             "entry points to the SchemaAnalyst tool can be created by extending " + 
             "the Runner class, and demonstrate the possible features.")

// This annotation sets up the parameters that must be passed from the command line
// They are specified without switches and must be supplied in the order specified
@RequiredParameters("schema dbms search")
public class RunnerExample extends Runner {
    
    // This annotation specifies a parameter
    @Parameter("The name of the schema")
    private String schema;

    // This annotation specifies a parameter along with a method that returns a String array
    // of possible values.  Runner checks that the value passed at the command line is a 
    // member of this array.  
    @Parameter(value="The name of the DBMS", 
               choicesMethod="org.schemaanalyst.dbms.DBMSFactory.getDBMSChoices")
    private String dbms;
    
    // A further parameter
    @Parameter("The name of the search")
    private String search;
    
    // Parameters are not limited to Strings, they can also be of any primitive type. 
    // Runner checks the value passed at the command line are actual ints, doubles, longs etc.
    @Parameter("The number of repetitions of the search")
    private int numrepetitions = 5;
    
    // A parameter of type long
    @Parameter("The seed needed to start the search")
    private long seed = 0;

    // An integer parameter
    @Parameter("The number of tries to generate data, only needed if the search is set to random ")
    private int numtries = 10000;    
    
    // A constructor must be supplied in this format, i.e. with the args parameter and a call
    // to super.
    public RunnerExample(String... args) {
        super(args);
    }
    
    // This method contains the actual high-level steps of the task to be executed.
    public void run() {
        System.out.println("Parsed parameters:");
        System.out.println("schema is " + schema);
        System.out.println("dbms is " + dbms);
        System.out.println("search is " + search);
        System.out.println("numrepetitions is " + numrepetitions);
        System.out.println("seed is " + seed);
        System.out.println("numtries is " + numtries);
    }

    // This method provides further validation steps on the parameter values.
    protected void validateParameters() {
        check(numrepetitions > 0, "numrepetitions should be 1 or greater");
        
        // use the isParameterSet method to check whether an optional parameter
        // was actually passed as an argument
        check(wasParameterSpecified("numtries") && search.equals("random"), 
              "numtries should be set if and only if the search is set to random");
    }    
    
    // A main method must be provided in this format (i.e. construction with args and 
    // a call to the run method).
    public static void main(String... args) {
        new RunnerExample(args).run();
    }
}
