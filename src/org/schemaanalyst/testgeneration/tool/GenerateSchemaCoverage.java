package org.schemaanalyst.testgeneration.tool;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.*;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.util.runner.Runner;
import parsedcasestudy.UnixUsage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static org.schemaanalyst.util.java.JavaUtils.JAVA_FILE_SUFFIX;

/**
 * Created by phil on 21/01/2014.
 */
public class GenerateSchemaCoverage extends Runner {

    boolean printUncoveredPredicates = true;

    @Override
    protected void task() {

        // these are parameters of the task (TODO: formalize these as per Runner ...)
        // Schema schema = new BankAccount();
        // Schema schema = new BookTown();
        // Schema schema = new Cloc();
        // Schema schema = new CoffeeOrders();
        // Schema schema = new CustomerOrder();
        // Schema schema = new DellStore();
        // Schema schema = new Employee();
        // Schema schema = new Examination();
        // Schema schema = new Flights();
        // Schema schema = new FrenchTowns();
        // Schema schema = new Inventory();
        // Schema schema = new Iso3166();
        // Schema schema = new JWhoisServer();
        // Schema schema = new NistDML181();
        // Schema schema = new NistDML182();
        // Schema schema = new NistDML183();
        // Schema schema = new NistWeather();
        // Schema schema = new NistXTS748();
        // Schema schema = new NistXTS749();
        // Schema schema = new Person();
        // Schema schema = new Products();
        // Schema schema = new RiskIt();
        // Schema schema = new StudentResidence();
        Schema schema = new UnixUsage();
        // Schema schema = new Usda();

        DBMS dbms = new SQLiteDBMS();
        CoverageCriterion criterion = CoverageCriterionFactory.instantiate("amplifiedConstraintCACWithNullAndUniqueColumnCACCoverage");

        DataGenerator dataGenerator = DataGeneratorFactory.instantiate("directedRandom", 0L, 500, schema);

        // instantiate the test suite generator and generate the test suite
        TestSuiteGenerator dg = new TestSuiteGenerator(
                schema,
                criterion,
                dbms.getValueFactory(),
                dataGenerator);

        TestSuite testSuite = dg.generate();

        // execute each test case to see what the DBMS result is for each row generated (accept / row)
        TestCaseExecutor executor = new TestCaseExecutor(
                schema,
                dbms,
                new DatabaseConfiguration(),
                new LocationsConfiguration());

        executor.execute(testSuite);

        // write report to console
        printReport(schema, criterion, testSuite, dg.getFailedTestCases());

        // write JUnit test suite to file
        writeTestSuite(schema, dbms, testSuite, "generatedtest");
    }

    private void printReport(Schema schema,
                             CoverageCriterion criterionUsed,
                             TestSuite testSuite,
                             List<TestCase> failedTestCases) {

        // print out each test suite test case
        System.out.println("SUCCESSFUL TEST CASES:");
        for (TestCase testCase : testSuite.getTestCases()) {
            printTestCase(testCase, true);
        }

        // print out each failed test case
        System.out.println("FAILED TEST CASES:");
        for (TestCase testCase : failedTestCases) {
            printTestCase(testCase, false);
        }

        printTestSuiteStats(schema, criterionUsed, testSuite);
    }

    private void printTestCase(TestCase testCase, boolean success) {
        System.out.println("\n" + testCase);

        if (!success) {
            // print details of the objective value computed by the generation
            //System.out.println("FAIL – INFO DUMP:");
            System.out.println(testCase.getInfo("info"));
        }
    }

    private void printTestSuiteStats(Schema schema, CoverageCriterion criterionUsed, TestSuite testSuite) {
        System.out.println("\nTEST SUITE STATS:");
        System.out.println("Number of test cases: " + testSuite.getNumTestCases());
        System.out.println("Number of inserts: " + testSuite.getNumInserts());

        for (CoverageCriterion criterion : CoverageCriterionFactory.allCriteria()) {
            String name = criterion.getName();
            String starred = "";
            if (name.equals(criterionUsed.getName())) {
                starred = " (*)";
            }

            CoverageReport coverageReport =
                    new CoverageReport(testSuite, criterion.generateRequirements(schema));
            System.out.println(name + starred + ": " + coverageReport.getCoverage());

            if (printUncoveredPredicates) {
                List<Predicate> uncovered = coverageReport.getUncoveredRequirements();
                if (uncovered.size() > 0) {
                    System.out.println("Uncovered predicates: ");
                    for (Predicate predicate : uncovered) {
                        System.out.println(predicate.getPurposes() + ": " + predicate);
                    }
                }
            }
        }
    }

    private void writeTestSuite(Schema schema, DBMS dbms, TestSuite testSuite, String packageName) {
        String className = "Test" + schema.getName();

        String javaCode = new TestSuiteJavaWriter(schema, dbms, testSuite)
                .writeTestSuite(packageName, className);

        File javaFile = new File(locationsConfiguration.getSrcDir()
                + "/" + packageName + "/" + className + JAVA_FILE_SUFFIX);
        try (PrintWriter fileOut = new PrintWriter(javaFile)) {
            fileOut.println(javaCode);
            System.out.println("\n[JUnit test suite written to " + javaFile + "]");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void validateParameters() {
        // no params to validate
    }

    public static void main(String... args) {
        new GenerateSchemaCoverage().run(args);
    }
}
