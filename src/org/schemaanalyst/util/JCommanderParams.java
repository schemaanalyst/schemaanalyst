/**
 * Cody Kinneer
 * JCommander Parameters Class
 */

package org.schemaanalyst.util;

import com.beust.jcommander.Parameter;
 
public class JCommanderParams {

  @Parameter(names = { "--schema", "-s" }, description = "Target Schema",required = true)
  protected String schema = "";
 
  @Parameter(names = {"--criterion","-c"}, description = "Coverage Criterion")
  protected String criterion = "ICC";
 
  @Parameter(names = {"--generator","-g","--dataGenerator"}, description = "Data Generation Algorithm")
  protected String generator = "avsDefaults";

  @Parameter(names = {"--dbms","-d","--database"}, description = "Database Management System")
  protected String dbms = "SQLite";

  @Parameter(names = {"--sql","--inserts"}, description = "Target file for writing INSERT statements")
  protected String sql = null;

  @Parameter(names = {"--mutation","-m"}, description = "Perform mutation testing")
  protected boolean mutation = false;

  @Parameter(names = {"--testSuitePackage","-p"}, description = "Target package for writing JUnit test suite")
  protected String testSuitePackage = "generatedtest";

  // should default to Test + schema + .java
  @Parameter(names = {"--testSuite","-t"}, description = "Target file for writing JUnit test suite")
  protected String testSuite = null;

  /* @Parameter(names = {"--help","-h"}, description = "Print the help menu") */
  /* protected boolean help = false; */

  @Parameter(names = {"--help", "-h"}, help = true, description = "Prints this help menu")
  protected boolean help;


}
