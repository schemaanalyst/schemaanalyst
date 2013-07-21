package org.schemaanalyst.test.util.runner;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

import org.schemaanalyst.util.runner.ArgumentException;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.runner.RequiredParameters;

public class TestRunner {

    class TestableRunner extends Runner {
        
        public TestableRunner() {
            super(false, false); 
            out = new PrintStream(new OutputStream() {
                public void write(int b) throws IOException {
                    // stop usage output
                }
            });             
        }
        
        // get all raw exceptions
        public void Run(String... args) {
            doRun(args);
        }
        
        public void task() {}
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
        
        public boolean wasParameterPassed(String name) {
            return super.wasParameterPassed(name);
        }
    }

    
    class R1 extends TestableRunner {}
        
    @RequiredParameters("   test1 test2  test3 ")
    class R2 extends TestableRunner {
        @Parameter String test1;
        @Parameter String test2;
        @Parameter String test3;
    }
    
    class R3 extends R2 {}
    
    class R4 extends R2 {
        @Parameter String test4 = "default";        
    }    
    
    class R5 extends TestableRunner {
        @Parameter(value="test param", valueAsSwitch="true")
        boolean test = false;
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
        
        assertEquals("R4 optional param 3 was not set and should be 'default'",
                     "default", r.test4);    
    }
    
    @Test(expected=ArgumentException.class)
    public void testTooFewRequiredParametersPassed() {
        String[] args1 = {"a", "b"};
        R4 r = new R4(); r.run(args1);        
    }    
    
    @Test(expected=ArgumentException.class)
    public void testTooManyRequiredParametersPassed() {
        String[] args1 = {"a", "b", "c", "d"};
        R4 r = new R4(); r.run(args1);        
    }        
    
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
    
    // TODO: test choices
}
