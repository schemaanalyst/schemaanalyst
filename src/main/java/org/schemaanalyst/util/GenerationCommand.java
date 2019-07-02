/**
 * Front-end for generating test data generation with SchemaAnalyst
 * @author Cody Kinneer
 */
package org.schemaanalyst.util;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "Generate test data with SchemaAnalyst")
public class GenerationCommand {

  @Parameter(names = {"--sql","--inserts"}, description = "Enable writing INSERT statements")
  protected boolean sql = false;

  @Parameter(names = {"--testSuitePackage","-p"}, description = "Target package for writing JUnit test suite")
  protected String testSuitePackage = "generatedtest";

  // should default to Test + schema + .java
  @Parameter(names = {"--testSuite","-t"}, description = "Target file for writing JUnit test suite")
  protected String testSuite = "TestSchema";

  // should default to Test + schema + .java
  @Parameter(names = {"--seed","-seed","--randomseed"}, description = "Random Seed")
  protected long seed = -0L;
  
  @Parameter(names = {"--showReadability","--readability","--read"}, description = "Calculates Readability of Character/String Values using a Language Model")
  protected boolean readability = false;
  
  @Parameter(names = {"--saveStats"}, description = "Save the stats info into a file results/generationOutput.dat Or results/readable.dat if any of these options selected --showReadability --readability --read")
  protected boolean saveStats = false;
}
