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

  @Parameter(names = {"--seed","-rs","--randomseed"}, description = "A long random seed")
  protected long randomseed = -0L;
  
  @Parameter(names = {"--showReadability","--readability","--read"}, description = "Calculates Readability of Character/String Values using a Language Model")
  protected boolean readability = false;
  
  @Parameter(names = {"--saveStats"}, description = "Save the stats info into a file results/generationOutput.dat Or results/readable.dat if any of these options selected --showReadability --readability --read")
  protected boolean saveStats = false;
  
  // @Parameter(names = {"--reduce"}, description = "Post generation test suite reduction. Options: none (default), eqltc (Equal Test Cases), eqltr (Equal Test Requirements), reduceTC (Reduce Test Cases INSERTS)")
  //@Parameter(names = {"--reduce"}, description = "Post generation test suite reduction. For Debugging only.")
  public String reduce = "none";

  //@Parameter(names = {"--reduceP","-rp","--reducePredicates"}, description = "If added it will reduce the generated predicates generated for each test requirments")
  //public boolean reducePredicates = false;
  
  @Parameter(names = {"--fullreduce","-fr"}, description = "Full Test Suite Reduction with the option of --reducewith techniques. Default is deactivated")
  protected boolean fullreduce = false;

  @Parameter(names = {"--reducewith","-r"}, description = "The reduction techniques: simpleGreedy, additionalGreedy (default), HGS, random, combo")
  protected String reducewith = "additionalGreedy";
  
  @Parameter(names = {"--printTR","-ptr","--printTestRequriments"}, description = "Print Test Requriments")
  protected boolean printTR = false;
  
  /* @Parameter(names = {"--help","-h"}, description = "Print the help menu") */
  /* protected boolean help = false; */

  @Parameter(names = {"--help", "-h"}, help = true, description = "Prints this help menu")
  protected boolean help;


}