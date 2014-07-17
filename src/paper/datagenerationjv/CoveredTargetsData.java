package paper.datagenerationjv;

import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.util.runner.Runner;

import java.io.*;

import static paper.datagenerationjv.Setup.*;

/**
 * Created by phil on 07/04/2014.
 */
public class CoveredTargetsData extends Runner {

    private static final int MAX_EVALUATIONS = 100000;

    @Override
    protected void task() {
        try {
            File coveredTargetsFile = new File("covered-targets.csv");
            File evaluationsFile = new File("evaluations.csv");

            PrintWriter coveredTargetsOut = new PrintWriter(new FileWriter(coveredTargetsFile));
            PrintWriter evaluationsOut = new PrintWriter(new FileWriter(evaluationsFile));

            for (String subjectName : SUBJECTS) {
                Schema subject = instantiateSchema("parsedcasestudy." + subjectName);

                coveredTargetsOut.print(subject);
                evaluationsOut.print(subject);

                for (String criterionName : CRITERIA) {
                    CoverageCriterion criterion = CoverageCriterionFactory.instantiate(criterionName);
                    int numReqs = criterion.generateRequirements(subject).size();

                    for (String techniqueName : TECHNIQUES) {

                        System.out.println("DOING " + subjectName + " " + criterionName + " " + techniqueName);

                        int totalNumCovered = 0;
                        int totalNumFailed = 0;
                        int totalEvaluations = 0;

                        for (long seed : SEEDS) {

                            DataGenerator dataGeneratorObject =
                                    DataGeneratorFactory.instantiate(techniqueName, seed, MAX_EVALUATIONS, subject);

                            TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                                subject,
                                criterion,
                                new ValueFactory(),
                                dataGeneratorObject);

                            TestSuite testSuite = testSuiteGenerator.generate();

                            int numCovered = testSuite.getNumTestCases();
                            int numFailed = numReqs - numCovered;
                            int evaluations = testSuite.getNumEvaluations();

                            totalNumCovered += numCovered;
                            totalNumFailed += numFailed;
                            totalEvaluations += evaluations;
                        }

                        double coverageAverage = 100 * (totalNumCovered / (double) (totalNumCovered + totalNumFailed));
                        double evaluationsAverage = totalEvaluations / SEEDS.length;
                        coveredTargetsOut.print(", " + String.format( "%.1f", coverageAverage));
                        evaluationsOut.print(", " + String.format( "%.1f", evaluationsAverage));

                        coveredTargetsOut.flush();
                        evaluationsOut.flush();
                    }
                }

                coveredTargetsOut.println();
                evaluationsOut.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void validateParameters() {
        // nothing to validate
    }

    public static void main(String[] args) {
        new CoveredTargetsData().run(args);
    }
}
