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

import static paper.datagenerationjv.Setup.*;

/**
 * Created by phil on 07/04/2014.
 */
public class CoveredTargetsData extends Runner {

    private static final int MAX_EVALUATIONS = 100000;

    @Override
    protected void task() {

        for (String subjectName : SUBJECTS) {
            Schema subject = instantiateSchema(subjectName);

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

                        TestSuite testSuite = testSuiteGenerator.generate();

                        // output stats to files ...

                    }
                }
            }
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
