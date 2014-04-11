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

                    for (String techniqueName : TECHNIQUES) {

                        for (long seed : SEEDS) {

                            DataGenerator dataGeneratorObject =
                                    DataGeneratorFactory.instantiate(techniqueName, seed, MAX_EVALUATIONS, subject);

                            TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                                subject,
                                criterion,
                                new ValueFactory(),
                                dataGeneratorObject);

                            // TestSuite testSuite = testSuiteGenerator.generate();

                            System.out.println("DOING " + subjectName + " " + criterionName + " " + techniqueName);
                            // output stats to files ...

                            coveredTargetsOut.flush();
                            evaluationsOut.flush();
                        }
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
