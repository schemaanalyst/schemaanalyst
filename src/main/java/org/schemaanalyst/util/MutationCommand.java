/**
 * Front-end for conducting mutation testing of SchemaAnalyst
 * @author Cody Kinneer
 */
package org.schemaanalyst.util;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "Perform mutation testing of SchemaAnalyst")
public class MutationCommand {

  @Parameter(names = "--maxEvaluations", description = "The maximum fitness evaluations for the search algorithm to use.")
  public int maxEvaluations = 100000;

  @Parameter(names = "--seed", description = "The random seed.")
  public int seed = 0;

  @Parameter(names = "--pipeline", description = "The mutation pipeline to use to generate mutants.")
  public String pipeline = "AllOperatorsWithRemovers";

  @Parameter(names = "--technique", description = "Which mutation analysis technique to use.")
  public String technique = "original";

  @Parameter(names = "--transactions", description = "Whether to use transactions with this technique (if possible).")
  public boolean transactions = false;

}
