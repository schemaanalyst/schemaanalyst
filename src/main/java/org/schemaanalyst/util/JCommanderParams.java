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

  /* @Parameter(names = {"--help","-h"}, description = "Print the help menu") */
  /* protected boolean help = false; */

  @Parameter(names = {"--help", "-h"}, help = true, description = "Prints this help menu")
  protected boolean help;

  @Parameter(names = {"--seed","-seed","--randomseed"}, description = "Random Seed")
  protected long seed = -0L;
  
  @Parameter(names = {"--showReadability","--readability","--read"}, description = "Calculates Readability of Character/String Values using a Language Model")
  protected boolean readability = false;
  
  @Parameter(names = {"--saveStats"}, description = "Save the stats info into a file results/generationOutput.dat Or results/readable.dat if any of these options selected --showReadability --readability --read")
  protected boolean saveStats = false;

}
