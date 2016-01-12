/*
 */
package org.schemaanalyst.unittest.mutation.equivalence;

import org.junit.Test;
import org.schemaanalyst.mutation.equivalence.EquivalenceChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author Chris J. Wright
 */
public class TestEquivalenceChecker {
    private class TestChecker extends EquivalenceChecker<String> {

        @Override
        public boolean areEquivalent(String a, String b) {
            return a.equals(b);
        }
    }
    
    @Test
    public void testAreEquivalent() {
        TestChecker tester = new TestChecker();
        assertTrue("Two identical items should be equivalent",
                tester.areEquivalent("a", "a"));
        assertFalse("Two different items should not be equivalent",
                tester.areEquivalent("a", "b"));
    }
    
    @Test
    public void testAreEquivalentLists() {
        TestChecker tester = new TestChecker();
        List<String> a = Arrays.asList("a","b","c");
        List<String> b = Arrays.asList("a","b","c");
        assertTrue("Two identical lists should be equivalent",
                tester.areEquivalent(a, b));
    }
    
    @Test
    public void testSubtractSame() {
        TestChecker tester = new TestChecker();
        List<String> a = Arrays.asList("a","b","c");
        List<String> b = Arrays.asList("a","b","c");
        assertTrue("'a,b,c' subtract 'a,b,c' should result in an empty list",
                tester.subtract(a, b).isEmpty());
    }
    
    @Test
    public void testSubtractDifferent() {
        TestChecker tester = new TestChecker();
        List<String> a = Arrays.asList("a","b","c");
        List<String> b = Arrays.asList("a","b");
        assertEquals("'a,b,c' subtract 'a,b' should result in a list of length 1",
                1, tester.subtract(a, b).size());
        assertEquals("'a,b' subtract 'a,b,c' should result in a list of length 0",
                0, tester.subtract(b, a).size());
    }
    
    @Test
    public void testSubtractEmpty() {
        TestChecker tester = new TestChecker();
        List<String> a = Arrays.asList("a","b","c");
        List<String> b = new ArrayList<>();
        assertEquals("Subtracting empty from a list of three should give a list"
                + " of three", 3, tester.subtract(a, b).size());
        assertEquals("Subtracting a list of 3 from empty should give an empty "
                + "list", 0, tester.subtract(b, a).size());
    }
}
