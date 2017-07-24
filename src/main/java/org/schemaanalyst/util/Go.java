/**
 * SchemaAnalyst Command-line Front-end
 * @author Cody Kinneer
 */

package org.schemaanalyst.util;

import com.beust.jcommander.JCommander;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.configuration.*;
import org.schemaanalyst.data.generation.*;
import org.schemaanalyst.dbms.*;
import org.schemaanalyst.mutation.analysis.executor.testcase.VirtualTestCaseExecutor;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.testgeneration.*;
import org.schemaanalyst.testgeneration.coveragecriterion.*;
import org.schemaanalyst.util.runner.*;
import org.schemaanalyst.mutation.analysis.executor.MutationAnalysis;

import java.io.*;
import java.util.*;

public class Go {

    public static void main(String[] args){

        JCommanderParams jcp = new JCommanderParams();
        JCommander cmd = new JCommander(jcp);

        MutationCommand mc = new MutationCommand();
        GenerationCommand gc = new GenerationCommand();

        cmd.addCommand("generation",gc);        
        cmd.addCommand("mutation",mc);

        cmd.parse(args);

        String command = cmd.getParsedCommand();

        // set arguments

        String schema = jcp.schema;

        String criterion = jcp.criterion;

        String datagenerator = jcp.generator;

        String dbms = jcp.dbms;

        String classname;

        if (jcp.help) {
            cmd.usage();
            return;
        }

        if (command != null && command.equals("mutation")){
            
            /* ArrayList<String> pargs = new ArrayList<String>(); */

            String[] pargs = new String[] {
                schema,
                "--criterion="+criterion,
                "--dataGenerator="+datagenerator,	
                "--maxevaluations="+mc.maxEvaluations,	
                "--randomseed="+mc.seed, 		
                "--mutationPipeline="+ mc.pipeline,
                "--technique="+mc.technique,
                "--useTransactions="+	mc.transactions};
                   
            MutationAnalysis.main(pargs);

            return;

        } else if (command != null && command.equals("generation")){

        if (gc.testSuite == "TestSchema")
            classname = "Test" + schema;
        else
            classname = gc.testSuite;

        String packagename = gc.testSuitePackage;

        boolean sql = gc.sql;

        // Copied code from GenerateTestSuite and PrintTestSuite

        // instantiate objects for parameters
        Schema schemaObject = instantiateSchema(schema);
        DBMS dbmsObject = DBMSFactory.instantiate(dbms);
        TestRequirements testRequirements = CoverageCriterionFactory.instantiateSchemaCriterion(criterion, schemaObject, dbmsObject).generateRequirements();
        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, -0L, 100000, schemaObject);

        // filter and reduce test requirements
        testRequirements.filterInfeasible();
        testRequirements.reduce();

        // generate the test suite
        TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                schemaObject,
                testRequirements,
                dbmsObject.getValueFactory(),
                dataGeneratorObject);
        TestSuite testSuite = testSuiteGenerator.generate();

        // if desired, write the INSERTs to a file for inspection
        if (sql){

            File file = new File(packagename + "/" + schema + ".sql");

            writeInserts(testSuite, dbmsObject, file, schemaObject);

        }

        // print some stats
        TestSuiteGenerationReport report = testSuiteGenerator.getTestSuiteGenerationReport();
        System.out.println("Test requirements covered: " + report.getNumTestRequirementsCovered() + "/" + report.getNumTestRequirementsAttempted());
        System.out.println("Coverage: " + report.coverage() + "%");
        System.out.println("Num Evaluations (test cases only): " + report.getNumDataEvaluations(true));
        System.out.println("Num Evaluations (all): " + report.getNumEvaluations(false));

        // failed initial table data generation attempts
        if (report.getInitialTableDataGenerationAttemptsFailed() > 0) {
            System.out.println("Initial table data generation attempts that failed:");
            int i = 1;
            for (Table table : report.getFailedInitialTableDataGenerationAttempts()) {
                System.out.println(i + ") " + table);
                i ++;
            }
        }

        if (report.getNumTestRequirementsFailed() > 0) {
            System.out.println("Failed test requirements:");
            int i = 1;
            for (TestRequirement testRequirement : report.getFailedTestRequirements()) {
                System.out.println(i + ") " + testRequirement);
                System.out.println((report.getDataGenerationResult(testRequirement).getState()));
                System.out.println((report.getDataGenerationResult(testRequirement).getData()));
                i ++;
            }
        }

        // execute each test case to see what the DBMS result is for each row generated (accept / row)
        TestCaseExecutor executor = new TestCaseExecutor(
                schemaObject,
                dbmsObject,
                new DatabaseConfiguration(),
                new LocationsConfiguration());
        executor.execute(testSuite);

        // check the results
        for (TestCase testCase : testSuite.getTestCases()) {
            Boolean result = testCase.getTestRequirement().getResult();
            Boolean dbmsResult = testCase.getLastDBMSResult();
            if (result != null && result != dbmsResult) {
                TestRequirement testRequirement = testCase.getTestRequirement();
                System.out.println("WARNING--test requirement result (" + result + ") differs from DBMS result (" + dbmsResult + "):");
                System.out.println(testRequirement);
            }

            // check the real test case executor against the virtual one
            VirtualTestCaseExecutor virtualTestCaseExecutor = new VirtualTestCaseExecutor(schemaObject, dbmsObject);
            List<Boolean> virtualResults = virtualTestCaseExecutor.executeTestCaseBoolean(testCase);
            List<Boolean> realResults = new ArrayList<>();
            for (int i=0; i < testCase.getState().getNumRows(); i++) {
                realResults.add(true);
            }
            realResults.addAll(testCase.getDBMSResults());

            if (virtualResults.size() != realResults.size()) {
                System.out.println("WARNING--Number of virtual results (" + virtualResults.size() +
                        ") does not match number of real results (" + realResults.size() + ")");
            }

            for (int i=0; i < Math.min(virtualResults.size(), realResults.size()); i++) {
                if (virtualResults.get(i) != realResults.get(i)) {
                    System.out.println("WARNING--real result differs from virtual result at INSERT statement " + i);
                }
            }
        }

        // write JUnit test suite to file
        if (classname.equals("")) {
            classname = "Test" + schemaObject.getName();
        }

        String javaCode = new TestSuiteJavaWriter(schemaObject, dbmsObject, testSuite, true)
                .writeTestSuite(packagename, classname);

        File javaFile = new File(packagename + "/" + classname + ".java");

        // make the package directory if it does not exist
        javaFile.getParentFile().mkdirs();

        try (PrintWriter fileOut = new PrintWriter(javaFile)) {
            fileOut.println(javaCode);
            System.out.println("JUnit test suite written to " + javaFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        }else{
            cmd.usage();
        }

    }

    private static Schema instantiateSchema(String schema) {
        try {
            return (Schema) Class.forName(schema).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeInserts(TestSuite testSuite, DBMS dbmsObject, File target, Schema schemaObject){
    SQLWriter writer = dbmsObject.getSQLWriter();
        List<TestCase> testCases = testSuite.getTestCases();
        try{
        BufferedWriter bufFile = new BufferedWriter(new FileWriter(target));
        for (int i = 0; i < testCases.size(); i++) {
            TestCase testCase = testCases.get(i);
            printInserts(writer, schemaObject, testCase.getState(),bufFile);
            printInserts(writer, schemaObject, testCase.getData(),bufFile);
        }
            bufFile.flush();
            bufFile.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

        private static void printInserts(SQLWriter writer, Schema schema, Data data,BufferedWriter bufWriter) throws IOException{
        List<String> stmts = writer.writeInsertStatements(schema, data);
        for (String stmt : stmts) {
            System.out.println(stmt);
            bufWriter.write(stmt);
        }
    }


}
