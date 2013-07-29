package org.schemaanalyst.deprecated.test.datageneration.search.objective.row;

import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import static org.junit.Assert.*;
import static junitparams.JUnitParamsRunner.*;
@RunWith(JUnitParamsRunner.class)
public class TestJUnitParams {

    class Person {
        private int age;

         public Person(int age) {
             this.age = age;
         }

         public boolean isAdult() {
             return age >= 18;
         }

         @Override
         public String toString() {
             return "Person of age: " + age;
         }
     }    
    
    @Test
    @Parameters(method = "adultValues")
    public void personIsAdult(int age, boolean valid) throws Exception {
        assertEquals(valid, new Person(age).isAdult());
    }

    private Object[] adultValues() {
        return $(
                     $(13, false),
                     $(17, false),
                     $(18, true),
                     $(22, true)
                );
    }    
    
}
