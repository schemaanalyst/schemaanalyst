/*
 */
package experiment.mutation2013;

import experiment.results.XMLSerialiser;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.schemaanalyst.configuration.Configuration;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.database.Database;
import org.schemaanalyst.databaseinteraction.DatabaseInteractor;
import org.schemaanalyst.datageneration.CoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.GoalReport;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizationProfiles;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizer;
import org.schemaanalyst.datageneration.search.AlternatingValueSearch;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.SearchConstraintCoverer;
import org.schemaanalyst.datageneration.search.datainitialization.RandomDataInitializer;
import org.schemaanalyst.datageneration.search.termination.CombinedTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.OptimumTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.TerminationCriterion;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;
import plume.Options;

/**
 *
 * @author chris
 */
public class GenerateData {

    public static void main(String[] args) {
        Options options = new Options("GenerateData [options]", new Configuration());
        options.parse_or_usage(args);

        // print the debugging information
        if (Configuration.debug) {
            System.out.println(options.settings());
        }

        // print the help screen to see the commands
        if (Configuration.help) {
            options.print_usage();
        }

        // create the database using reflection; this is based on the
        // type of the database provided in the configuration (i.e.,
        // the user could request the Postres database in FQN)
        Database database = null;
        try {
            database = (Database) Class.forName(Configuration.type).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Could not construct database type \"" + Configuration.type + "\"");
        }
        SQLWriter sqlWriter = database.getSQLWriter();

        // initialize the connection to the real relational database
        DatabaseInteractor databaseInteraction = database.getDatabaseInteraction();

        // create the schema using reflection; this is based on the
        // name of the database provided in the configuration
        Schema schema = null;
        try {
            schema = (Schema) Class.forName(Configuration.database).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Could not construct schema \"" + Configuration.database + "\"");
        }

        // drop existing tables
        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        for (String statement : dropTableStatements) {
            databaseInteraction.executeUpdate(statement);
        }

        // create the schema inside of the real database
        List<String> createTableStatements = sqlWriter.writeCreateTableStatements(schema);
        for (String statement : createTableStatements) {
            int returnCount = databaseInteraction.executeUpdate(statement);
        }

        // generate test data
        ValueFactory valueFactory = database.getValueFactory();
        DataGenerator dataGenerator = constructDataGenerator(schema, valueFactory);
        CoverageReport report = dataGenerator.generate();

        XMLSerialiser.save(report, "coverageReport.xml");
        CoverageReport report2 = XMLSerialiser.load("coverageReport.xml");

        ArrayList<SQLInsertRecord> records = new ArrayList<>();
        for (GoalReport goalReport : report.getSuccessfulGoalReports()) {
            Data data = goalReport.getData();
            List<String> statements = sqlWriter.writeInsertStatements(data);
            for (String stmt : statements) {
                int result = databaseInteraction.executeUpdate(stmt);
                SQLInsertRecord record = new SQLInsertRecord(stmt, result);
                records.add(record);
            }
        }
        XMLSerialiser.save(records, "records.xml");
        
        // drop existing tables
        //List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        for (String statement : dropTableStatements) {
            databaseInteraction.executeUpdate(statement);
        }

        // create the schema inside of the real database
        //List<String> createTableStatements = sqlWriter.writeCreateTableStatements(schema);
        for (String statement : createTableStatements) {
            int returnCount = databaseInteraction.executeUpdate(statement);
        }
        
        ArrayList<SQLInsertRecord> records2 = new ArrayList<>();
        for (GoalReport goalReport : report2.getSuccessfulGoalReports()) {
            Data data = goalReport.getData();
            List<String> statements = sqlWriter.writeInsertStatements(data);
            for (String stmt : statements) {
                int result = databaseInteraction.executeUpdate(stmt);
                SQLInsertRecord record = new SQLInsertRecord(stmt, result);
                records2.add(record);
            }
        }
        XMLSerialiser.save(records2, "records2.xml");

//        ArrayList<SQLInsertRecord> records = new ArrayList<>();
//        for (GoalReport goalReport: report.getSuccessfulGoalReports()) {
//            Data data = goalReport.getData();
//            List<String> statements = sqlWriter.writeInsertStatements(data);
//            for (String stmt : statements) {
//                int result = databaseInteraction.executeUpdate(stmt);
//                SQLInsertRecord record = new SQLInsertRecord(stmt, result);
//                records.add(record);
//            }
//        }
//        
//        for (SQLInsertRecord record : records) {
//            
//        }
    }

    private static DataGenerator constructDataGenerator(Schema schema, ValueFactory valueFactory) {
        Random random = new SimpleRandom(Configuration.randomseed);
        CellRandomizer cellRandomizer = constructCellRandomizationProfile(random);
        Search<Data> search = new AlternatingValueSearch(random, new RandomDataInitializer(cellRandomizer), new RandomDataInitializer(cellRandomizer));

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(
                search.getEvaluationsCounter(),
                Configuration.maxevaluations),
                new OptimumTerminationCriterion<>(search));
        search.setTerminationCriterion(terminationCriterion);
        return new SearchConstraintCoverer(
                search,
                schema,
                valueFactory,
                Configuration.satisfyrows,
                Configuration.negaterows);
    }

    public static CellRandomizer constructCellRandomizationProfile(Random random) {
        switch (Configuration.randomprofile) {
            case "small":
                return CellRandomizationProfiles.small(random);
            case "large":
                return CellRandomizationProfiles.large(random);
            default:
                throw new RuntimeException("Unknown random profile \"" + Configuration.randomprofile + "\"");
        }
    }
}
