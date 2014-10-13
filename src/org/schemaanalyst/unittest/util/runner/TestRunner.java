package org.schemaanalyst.unittest.util.runner;

import org.junit.Test;
import org.schemaanalyst.util.runner.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class TestRunner {

    /*
     * A runner with some "holes" poked into it so that we can
     * observe its state for testing.
     * 
     */         
    private class TestableRunner extends Runner {
        
        public TestableRunner() {
            super(false, false); 
            out = new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    // stop output being written to the
                    // console for cleaner testing...
                }
            });             
        }
        
        // we want all raw exceptions for testing, so just
        // setup and run the task, don't catch the exceptions
        // on our behalf
        public void run(String... args) {
            doRun(args);
        }
        
        @Override
        public void task() {}
        @Override
        public void validateParameters() {}
                
        public List<String> getRequiredParameterNames() {
            return requiredParameterNames;
        }   
        
        public Set<String> getParameterNames() {
            return parameters.keySet();
        }
        
        public Map<String, Object> getOptionalParameterDefaults() {
            return optionalParameterDefaults;
        }
        
        @Override
        public boolean wasParameterPassed(String name) {
            return super.wasParameterPassed(name);
        }
    }
    
    /*
     * Various runners in different states of configuration for testing purposes
     */    
    private class R1 extends TestableRunner {}
        
    @RequiredParameters("   test1 test2  test3 ")
    private class R2 extends TestableRunner {
        @Parameter String test1;
        @Parameter String test2;
        @Parameter String test3;
    }
    
    private class R3 extends R2 {}
    
    private class R4 extends R2 {
        @Parameter String test4 = "default";        
    }    

    private class R4a extends R2 {
        @Parameter String test4;        
    }
    
    private class R4b extends TestableRunner {
        @Parameter String test;        
    }     
    
    private class R5 extends TestableRunner {
        @Parameter(value="test param", valueAsSwitch="true")
        boolean test = false;
    }
    
    @RequiredParameters("testing")
    private class R6 extends TestableRunner {
        @Parameter String test;
    }
    
    private class R7 extends TestableRunner {
        @Parameter(value="test", 
                   choicesMethod="org.schemaanalyst.unittest.util.runner.TestRunner.choices")
        String test;
    }    

    private class R8 extends TestableRunner {
        @Parameter(value="test", 
                   choicesMethod="org.schemaanalyst.unittest.util.runner.TestRunner.choicesWrongReturnType")
        String test;
    }  
    
    private class R9 extends TestableRunner {
        @Parameter(value="test", 
                   choicesMethod="org.schemaanalyst.unittest.util.runner.TestRunner.noSuchMethod")
        String test;
    }
    
    private class R10 extends TestableRunner {
        @Parameter(value="test", 
                   choicesMethod="NoSuchClass.noSuchMethod")   
        String test;
    }         
    
    /*
     * methods for testing the returning of different choices
     * 
     */    
    public static List<String> choices() {
        List<String> choices = new ArrayList<>();
        choices.add("one");
        choices.add("two");
        choices.add("three");
        return choices;
    }
    
    public static String choicesWrongReturnType() {
        return "hello";
    }
    
    @Test
    public void testNoRequiredParameter() {
        R1 r = new R1(); r.run();
        
        assertEquals("If a class has no @RequirementParameters annotation, " + 
                     "getRequriedParameterFieldNames should return an empty array",
                     0, r.getRequiredParameterNames().size());
    }
    
    @Test
    public void testRequiredParameters() {
        String[] args = {"a", "b", "c"};
        R2 r = new R2(); r.run(args);
                
        // check required param names are parsed correctly        
        List<String> names = r.getRequiredParameterNames();
        assertEquals("R2 should have 3 required parameters",
                     3, names.size());        
        assertEquals("R2 required param 1 should be test1",
                     "test1", names.get(0));
        assertEquals("R2 required param 2 should be test2",
                     "test2", names.get(1));
        assertEquals("R2 required param 3 should be test3",
                     "test3", names.get(2));

        // check params are in the parameters map
        Set<String> params = r.getParameterNames();        
        assertEquals("R2 should have 3 parameters",
                     3, params.size());        
        assertTrue("R2 required param 1 should be in the parameters map",
                   params.contains("test1"));
        assertTrue("R2 required param 2 should be in the parameters map",
                   params.contains("test1"));
        assertTrue("R2 required param 3 should be in the parameters map",
                   params.contains("test3"));        
        
        // check required param fields are set correctly
        assertEquals("R2 required param 1 should be 'a'",
                     "a", r.test1);
        assertEquals("R2 required param 2 should be 'b'",
                     "b", r.test2);
        assertEquals("R2 required param 3 should be 'c;",
                     "c", r.test3);        
    }    
    
    @Test
    public void testInheritanceOfParameters() {
        String[] args = {"a", "b", "c"};
        R3 r = new R3(); r.run(args);
                
        // check required param names are parsed correctly        
        List<String> names = r.getRequiredParameterNames();
        assertEquals("R3 should have 3 required parameters",
                     3, names.size());        

        // check params are in the parameters map
        Set<String> params = r.getParameterNames();        
        assertEquals("R3 should have 3 parameters",
                     3, params.size());        
        
        // check required param fields are set correctly
        assertEquals("R3 required param 1 should be 'a'",
                     "a", r.test1);
        assertEquals("R3 required param 2 should be 'b'",
                     "b", r.test2);
        assertEquals("R3 required param 3 should be 'c'",
                     "c", r.test3);           
    }
    
    // an exception should be thrown if a parameter value is passed without
    // a key but there are no required parameters
    @Test(expected=RunnerException.class)
    public void testNonExistantRequiredParameter() {
        String[] args1 = {"a"};
        R6 r = new R6(); 
        r.run(args1); 
    }
    
    // an exception should be thrown if a parameter value is passed without
    // a key but there are no required parameters (there are required parameters
    // however, making this different from the previous test)
    @Test(expected=ArgumentException.class)
    public void testOptionalParameterNoDefaultNoRequiredParametersPresent() {
        String[] args = {"hello"};
        R4b r = new R4b(); r.run(args);              
    }       
    
    @Test
    public void testOptionalParameter() {
        String[] args1 = {"a", "b", "c", "--test4=hello"};
        R4 r = new R4(); r.run(args1);      
        
        // check params are in the parameters map
        Set<String> params = r.getParameterNames();        
        assertEquals("R4 should have 4 parameters",
                     4, params.size());        
        assertTrue("R4 optional param 4 should be in the parameters map",
                   params.contains("test4"));  
        
        // check param is set
        assertTrue("R4 optional param 4 should have been passed",
                   r.wasParameterPassed("test4"));
        
        assertEquals("R4 optional param 4 should be 'hello'",
                     "hello", r.test4);
        
        // check param default
        assertEquals("R4 optional param 4 default should be 'defaul'",
                     "default", r.getOptionalParameterDefaults().get("test4"));        

        // ensure nothing bad happens if option not present in args
        String[] args2 = {"a", "b", "c"};
        r = new R4(); r.run(args2);              

        // check param is not set
        assertFalse("R4 optional param 4 should not have been passed",
                   r.wasParameterPassed("test4"));        
        
        assertEquals("R4 optional param 4 was not set and should be 'default'",
                     "default", r.test4);    
    }
    
    @Test
    public void testOptionalParameterNoDefaultRequiredParametersPresent() {
        String[] args = {"a", "b", "c"};
        R4a r = new R4a(); r.run(args);              

        // check param is not set
        assertFalse("R4a optional param 4 should not have been passed",
                    r.wasParameterPassed("test4"));        
        
        assertNull("R4 optional param 4 was not set and there is no default",
                   r.test4);    
    }    
    
    // ArgumentException should be thrown if too few parameters passed at the command line 
    @Test(expected=ArgumentException.class)
    public void testTooFewRequiredParametersPassed() {
        String[] args1 = {"a", "b"};
        R4 r = new R4(); r.run(args1);        
    }    
    
    // ArgumentException thrown if too many parameters passed at the command line
    @Test(expected=ArgumentException.class)
    public void testTooManyRequiredParametersPassed() {
        String[] args1 = {"a", "b", "c", "d"};
        R4 r = new R4(); r.run(args1);        
    }        
    
    // ArgumentException thrown if an unknown optional parameter is passed at the command line
    @Test(expected=ArgumentException.class)
    public void testUnknownParameterPassed() {
        String[] args1 = {"a", "b", "c", "--test2000=hello"};
        R4 r = new R4(); r.run(args1);        
    }
    
    @Test
    public void testSwitch() {
        String[] args1 = {"--test"};
        R5 r = new R5(); r.run(args1);      

        // check param is set
        assertTrue("R5 switch should have been passed",
                   r.wasParameterPassed("test"));
        
        assertTrue("R5 switch should be true",
                   r.test);        
        
        String[] args2 = {};
        r = new R5(); r.run(args2);      

        // check param is not set
        assertFalse("R5 switch should not have been passed",
                   r.wasParameterPassed("test"));
        
        assertFalse("R5 switch should be false",
                   r.test);        
    }
    
    // ArgumentException thrown if a value is passed for a switch    
    @Test(expected=ArgumentException.class)
    public void testPassedValueForSwitch() {
        String[] args1 = {"--test=hello"};
        R5 r = new R5(); r.run(args1);        
    }    
        
    public void testNotSwitchNoValue() {
        String[] args1 = {"a", "b", "c", "--test4"};
        R4 r = new R4(); r.run(args1);    
        
        assertEquals("R4 optional param 4 was passed but not set so should be null",
                     null, r.test4);
        
        // another variant...
        String[] args2 = {"a", "b", "c", "--test4="};
        r = new R4(); r.run(args2);    
        
        assertEquals("R4 optional param 4 was passed but not set so should be null",
                     null, r.test4);        
    }   
    
    @Test
    public void testChoicesCorrectChoice() {
        String[] args = {"--test=two"};
        R7 r = new R7(); r.run(args);
        
        assertEquals("R8 passed allowable choice",
                     "two", r.test);     
    }
    
    // ArgumentException thrown if an illegal value is passed for a choice
    @Test(expected=ArgumentException.class)
    public void testChoicesIncorrectChoice() {
        String[] args = {"--test=tenthousand"};
        R7 r = new R7(); r.run(args);    
    }
    
    // RunnerException is expected since R8 specifies a method with the wrong return type
    @Test(expected=RunnerException.class)
    public void testChoicesWrongChoicesMethodReturnType() {
        String[] args = {"--test=two"};
        R8 r = new R8(); r.run(args);    
    }    
    
    @Test(expected=RunnerException.class)
    public void testChoicesUnknownChoicesMethod() {
        String[] args = {"--test=two"};
        R9 r = new R9(); r.run(args);    
    }

    @Test(expected=RunnerException.class)
    public void testChoicesUnknownClassForChoicesMethod() {
        String[] args = {"--test=two"};
        R10 r = new R10(); r.run(args);    
    }    
}
