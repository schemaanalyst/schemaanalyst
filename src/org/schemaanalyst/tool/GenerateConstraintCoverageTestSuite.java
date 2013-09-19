package org.schemaanalyst.tool;

import java.io.File;

import org.schemaanalyst.datageneration.ConstraintGoal;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.ConstraintCovererFactory;
import org.schemaanalyst.datageneration.TestSuite;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.javawriter.ConstraintCoverageTestSuiteJavaWriter;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.sqlparser.Parser;
import org.schemaanalyst.sqlparser.SchemaMapper;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;

@Description("Generates constraint covering data for a schema, printing the INSERTs to the screen.")
@RequiredParameters("schema dbms datagenerator")
public class GenerateConstraintCoverageTestSuite extends Runner {

	@Parameter("The name of the schema to be processed. "
			+ "The SQL must be placed in the schemas subdirectory of casestudies")
	private String schema;

	@Parameter(value = "The identification string of the DBMS to be used", choicesMethod = "org.schemaanalyst.dbms.DBMSFactory.getDBMSChoices")
	private String dbms;

	@Parameter(value = "The identification string of the data generator to be used", choicesMethod = "org.schemaanalyst.datageneration.ConstraintCovererFactory.getConstraintCovererChoices")
	private String datagenerator;

	@Parameter(value = "The identification string of the cell randomisation profile to be used", choicesMethod = "org.schemaanalyst.datageneration.cellrandomisation.CellRandomiserFactory.getCellRandomiserChoices")
	private String cellrandomisationprofile = "Small";

	@Parameter("The random seed to start the search algorithm")
	private long seed = 0;

	@Parameter("The maximum number of evaluations for each step of the search algorithm "
			+ "(where the definition of 'step' is specific to each algorithm)")
	private int maxevaluations = 100000;

	@Override
	protected void task() {
		try {
			// get a DBMS instance
			DBMS dbmsObject = DBMSFactory.instantiate(dbms);

			// instantiate a parser
			Parser parser = new Parser(dbmsObject);

			// instantiate a mapper
			SchemaMapper mapper = new SchemaMapper();

			// get the file
			File sqlFile = new File(locationsConfiguration.getSchemaSrcDir()
					+ File.separator + schema + ".sql");

			// get the schema
			Schema schemaObject = mapper.getSchema(schema,
					parser.parse(sqlFile));

			// invocate factory method
			DataGenerator<ConstraintGoal> generator = 
					ConstraintCovererFactory.instantiate(datagenerator, schemaObject, dbmsObject,
							cellrandomisationprofile, seed, maxevaluations);

			// generate data
			TestSuite<ConstraintGoal> testSuite = generator.generate();
			ConstraintCoverageTestSuiteJavaWriter writer = new ConstraintCoverageTestSuiteJavaWriter(
					schemaObject, dbmsObject, testSuite);
			writer.writeTestSuite();

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void validateParameters() {
		check(maxevaluations > 0, "maxevaluations should be greater than 0");
	}

	public static void main(String... args) {
		new GenerateConstraintCoverageTestSuite().run(args);
	}
}
