package org.schemaanalyst.testgeneration.tool;

import java.util.List;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

@RequiredParameters("schema dbms criterion datagenerator")
public class PrintTestSuite extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String schema;
    /**
     * The coverage criterion to use to generate data.
     */
    @Parameter("The coverage criterion to use to generate data.")
    protected String criterion = "CondAICC";
    /**
     * The data generator to use.
     */
    @Parameter("The data generator to use.")
    protected String datagenerator = "avsDefaults";
    /**
     * The maximum fitness evaluations when generating data.
     */
    @Parameter("The maximum fitness evaluations when generating data.")
    protected int maxevaluations = 100000;
    /**
     * The name of the DBMS to use.
     */
    @Parameter("The name of the DBMS to use.")
    protected String dbms;
    /**
     * The random seed.
     */
    @Parameter("The random seed.")
    protected long randomseed = 0;
    
    @Override
    protected void task() {
        Schema schemaObject = instantiateSchema();
        DBMS dbmsObject = DBMSFactory.instantiate(dbms);
        TestRequirements testRequirements = CoverageCriterionFactory.instantiateSchemaCriterion(criterion, schemaObject, dbmsObject).generateRequirements();
        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, randomseed, maxevaluations, schemaObject);

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
        
        SQLWriter writer = dbmsObject.getSQLWriter();
        List<TestCase> testCases = testSuite.getTestCases();
        for (int i = 0; i < testCases.size(); i++) {
            /* System.out.println("------------------------------"); */
            /* System.out.println("Test case " + (i + 1)); */
            /* System.out.println(); */
            TestCase testCase = testCases.get(i);
            System.out.println("Test requirement: ");
            System.out.println(testCase.getTestRequirement().getPredicate());
            System.out.println();
            System.out.println("Insert statements:");
            printInserts(writer, schemaObject, testCase.getState());
            printInserts(writer, schemaObject, testCase.getData());
        }
    }

    private Schema instantiateSchema() {
        try {
            return (Schema) Class.forName(schema).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void printInserts(SQLWriter writer, Schema schema, Data data) {
        List<String> stmts = writer.writeInsertStatements(schema, data);
        for (String stmt : stmts) {
            System.out.println(stmt);
        }
    }
    
    @Override
    protected void validateParameters() {
        // Intentionally blank
    }
    
    public static void main(String[] args) {
        new PrintTestSuite().run(args);
    }
    
}
