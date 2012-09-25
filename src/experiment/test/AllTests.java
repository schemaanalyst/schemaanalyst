package experiment.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import experiment.test.TestExperimentalResults;

/**
 * AllTests JUnit Suite
 */
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
    // experiment.test
    TestExperimentalResults.class
})

public class AllTests {
}