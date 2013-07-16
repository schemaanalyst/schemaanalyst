package org.schemaanalyst.util.runner;

@RequiredOptions("schema_name dbms")
public class RunnerTest extends Runner {
    
    @Option("The name of the schema")
    private String schema_name;

    @Option(value="The name of the DBMS", 
            choicesMethod="org.schemaanalyst.dbms.DBMSFactory.getDBMSChoices")
    private String dbms;
    
    @Option("The number of repetitions for the experiment")
    private int num_repetitions;
    
    public RunnerTest(String... args) {
        super(args);
    }
    
    public void run() {
        System.out.println("schema_name is " + schema_name);
        System.out.println("dbms is " + dbms);
        System.out.println("num_repetitions is " + num_repetitions);
        usage();
    }
    
    public static void main(String... args) {
        new RunnerTest(args).run();
    }
}
