package org.schemaanalyst.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.schemaanalyst.test.mutation.*;

/**
 * AllTests JUnit Suite
 * @author Phil McMinn
 *
 */
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestMutationAnalysisProcess.class,
})

public class AllIntegrationTests {
}