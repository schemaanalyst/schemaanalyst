package paper.datagenerationjv;

import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.CoverageReport;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import static org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory.*;

/**
 * Created by phil on 26/03/2014.
 */
@RequiredParameters("schema criterion datagenerator")
public class CompareCoverage extends Runner {

    @Parameter("The name of the schema to use.")
    protected String schema;

    @Parameter("The name of the coverage criterion to use.")
    protected String criterion;

    @Parameter("The name of the data generator to use.")
    protected String datagenerator;

    private final long[] SEEDS = {
            -6181362485482196776L,
            243443477776231231L,
            1340847941370272450L,
            5458989235855898393L,
            2939701355095800577L,
            6484906673192518171L,
            -8731806375254350556L,
            -2300178095271637156L,
            -8299013972373879204L,
            3873154117010072995L,
            2721864725811999143L,
            263873105683231276L,
            -4391962627882235344L,
            4813164561805775788L,
            3861146338642194421L,
            2867705452563272081L,
            3749886693097886378L,
            -5888707902786354291L,
            -4039825805697282339L,
            6556750550333549909L,
            -1857561719688464038L,
            443748979985863333L,
            3905887548956196048L,
            8529715942460868488L,
            -9000133138077012557L,
            -4472693651568852779L,
            -4326962704210003868L,
            -7143959934763734788L,
            -4987052559603593841L,
            5715025643124161794L
    };

    private CoverageCriterion[] criteria = {
            amplifiedConstraintCACWithNullAndUniqueColumnCACCoverage(),
            amplifiedConstraintCACCoverage(),
            constraintCACWithNullAndUniqueColumnCACCoverage(),
            constraintCACCoverage(),
            nullAndUniqueColumnCACCoverage()
    };

    @Override
    protected void task() {

        Schema schemaObject = instantiateSchema();
        CoverageCriterion criterionObject = CoverageCriterionFactory.instantiate(criterion);

        double[] summedCoverage = new double[criteria.length];

        for (int i=0; i < SEEDS.length; i++) {
            double[] coverage = runExpt(schemaObject, criterionObject, SEEDS[i]);
            for (int j=0; j < criteria.length; j++) {
                summedCoverage[j] += coverage[j];
            }
        }

        System.out.print(schema + ", " + criterion + ", " + datagenerator);
        for (int j=0; j < criteria.length; j++) {
            double average = summedCoverage[j] / SEEDS.length;
            System.out.print(", " + average);
        }
        System.out.println();
    }

    private double[] runExpt(Schema schemaObject, CoverageCriterion criterionObject, long seed) {
        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, seed, 10000, schemaObject);

        // generate the test suites
        TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                schemaObject,
                criterionObject,
                new ValueFactory(),
                dataGeneratorObject);

        TestSuite testSuite = testSuiteGenerator.generate();

        double[] coverage = new double[criteria.length];
        int i = 0;
        for (CoverageCriterion criterion : criteria) {

            CoverageReport report = new CoverageReport(testSuite, criterion.generateRequirements(schemaObject));
            coverage[i] = report.getCoverage();
            i++;
        }

        return coverage;
    }

    private Schema instantiateSchema() {
        try {
            return (Schema) Class.forName(schema).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void validateParameters() {
        // no params to validate
    }

    public static void main(String... args) {
        new CompareCoverage().run(args);
    }
}
