package paper.datagenerationjv;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.*;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;

import java.io.*;

import static paper.datagenerationjv.Instantiator.instantiateCoverageCriterion;
import static paper.datagenerationjv.Instantiator.instantiateDBMS;
import static paper.datagenerationjv.Instantiator.instantiateSchema;

/**
 * Created by phil on 05/09/2014.
 */
public class RunCoverageExptCluster {

    protected String resultsDir;
    protected int maxEvaluations = 100000;

    protected long[] seeds = {
            -1116206204814428231L, 7985954954880731531L, 3611094813579055564L, 4060776535588632553L, -6282041857351115261L, -7753126579393043552L, 7301670248648733814L, 1415212444129790595L, 2056109940551094279L, -2200648009882847974L, 2083899252996892155L, -8446221986149258676L, 6487470329507455693L, -3188839645469611430L, 1107647187144990782L, -7172454676826905329L, -6350374941054523216L, 7238962508293136181L, -6118606874157917260L, -4279319149851968693L, 1152127634058037217L, 4977817611943876980L, 343476588327669223L, -2889233434285636943L, -6496068917548900125L, 5599934909352710994L, 5938267508396193780L, -6267000089346451287L, -257943939492821747L, -2185319758834608639L
    };


    public RunCoverageExptCluster() {
        resultsDir = new LocationsConfiguration().getResultsDir();
    }

    public void runExpt(String schemaName,
                        String coverageCriterionName,
                        String dataGeneratorName,
                        String dbmsName) {
        for (int i = 1; i <= 30; i++) {
            try {
                runExpt(schemaName, coverageCriterionName, dataGeneratorName, dbmsName, i);
            } catch (Exception e) {
                // don't let the termination of one experiment kill the whole job ...
                e.printStackTrace();
            }
        }
    }

    public void runExpt(String schemaName,
                        String coverageCriterionName,
                        String dataGeneratorName,
                        String dbmsName,
                        int runNo) {
        Schema schema = instantiateSchema(schemaName);
        DBMS dbms = instantiateDBMS(dbmsName);
        CoverageCriterion coverageCriterion = instantiateCoverageCriterion(coverageCriterionName, schema, dbms);

        long seed = seeds[runNo-1];

        TestRequirements testRequirements = coverageCriterion.generateRequirements();

        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(
                dataGeneratorName, seed, maxEvaluations, schema);

        // filter and reduce test requirements
        testRequirements.filterInfeasible();
        testRequirements.reduce();

        // generate the test suite
        TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                schema,
                testRequirements,
                dbms.getValueFactory(),
                dataGeneratorObject);

        TestSuite testSuite = testSuiteGenerator.generate();

        // execute each test case to see what the DBMS result is for each row generated (accept / row)
        TestCaseExecutor executor = new TestCaseExecutor(
                schema,
                dbms,
                new DatabaseConfiguration(),
                new LocationsConfiguration());
        executor.execute(testSuite);

        // check the results
        int numWarnings = 0;
        for (TestCase testCase : testSuite.getTestCases()) {
            Boolean result = testCase.getTestRequirement().getResult();
            Boolean dbmsResult = testCase.getLastDBMSResult();
            if (result != null && result != dbmsResult) {
                numWarnings ++;
            }
        }

        // get the stats
        TestSuiteGenerationReport report = testSuiteGenerator.getTestSuiteGenerationReport();

        int numReqsCovered = report.getNumTestRequirementsCovered();
        int numReqs = numReqsCovered + report.getNumTestRequirementsFailed();
        int successfulEvaluations = report.getNumEvaluations(true);
        int allEvaluations = report.getNumEvaluations(false);

        String data = "\"" + schemaName + "\", \"" + coverageCriterionName + "\", \"" + dataGeneratorName + "\", "
                + "\"" + dbmsName + "\", " + runNo + ", " + numReqsCovered + ", " + numReqs + ", "
                + successfulEvaluations + ", " + allEvaluations + ", " + numWarnings;

        // output the data
        try {
            String fileName = resultsDir + "/" + schemaName + "-" + coverageCriterionName
                    + "-" + dataGeneratorName + "-" + dbmsName + "-" + runNo + ".txt";
            FileWriter fw = new FileWriter(fileName);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(data);
            fw.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        // serialize the test suite
        try {
            String fileName = resultsDir + "/" + schemaName + "-" + coverageCriterionName
                    + "-" + dataGeneratorName + "-" + dbmsName + "-" + runNo + ".testsuite";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(testSuite);
            out.close();
            fileOut.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        RunCoverageExptCluster rce = new RunCoverageExptCluster();
        if (args.length == 5) {
            // expt with run number
            rce.runExpt(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]));
        } else if (args.length == 4) {
            // expt with all runs
            rce.runExpt(args[0], args[1], args[2], args[3]);
        } else {
            System.out.println("Wrong number of args!");
        }

    }
}
