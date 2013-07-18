package org.schemaanalyst.util.runner;

@Description("This class doesn't do anything, it's purpose is to show by example how " + 
             "entry points to the SchemaAnalyst tool can be created by extending " + 
             "the Runner class, and demonstrate the possible features.")
@RequiredParameters("schema_name dbms")
public class RunnerExample extends Runner {
    
    @Parameter("The name of the schema")
    private String schema_name;

    @Parameter(value="The name of the DBMS", 
               choicesMethod="org.schemaanalyst.dbms.DBMSFactory.getDBMSChoices")
    private String dbms;
    
    @Parameter("The number of repetitions for the experiment")
    private int num_repetitions;
    
    public RunnerExample(String... args) {
        super(args);
    }
    
    public void run() {
        System.out.println("schema_name is " + schema_name);
        System.out.println("dbms is " + dbms);
        System.out.println("num_repetitions is " + num_repetitions);
        printUsage();
    }

    protected void validateParameters() {
        if (num_repetitions <= 0) {
            quitWithError("num_repetitions should be 1 or greater");
        }
    }    
    
    public static void main(String... args) {
        new RunnerExample(args).run();
    }
}
