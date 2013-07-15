package org.schemaanalyst.util.runner;

public class RunnerTest extends Runner {

    @Option("An integer value")
    protected int intValue;
    
    @Option("A string value")
    protected String strValue;
    
    public RunnerTest(String... args) {
        super(args);
    }
    
    public void run() {
        System.out.println("intValue is " + intValue);
        System.out.println("strValue is " + strValue);
        usage();
    }
    
    public static void main(String... args) {
        RunnerTest rt = new RunnerTest(args);
        rt.run();
    }
}
