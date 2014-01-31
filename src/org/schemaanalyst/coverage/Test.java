package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.criterion.RestrictedActiveClauseCoverage;
import org.schemaanalyst.coverage.search.AlternatingValueSearch;
import org.schemaanalyst.coverage.search.cellinitialization.NoCellInitialization;
import org.schemaanalyst.coverage.search.cellinitialization.RandomCellInitializer;
import org.schemaanalyst.coverage.testgeneration.DataGenerator;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiserFactory;

import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.termination.CombinedTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.OptimumTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.TerminationCriterion;
import org.schemaanalyst.dbms.postgres.PostgresDBMS;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;
import parsedcasestudy.Flights;

/**
 * Created by phil on 21/01/2014.
 */
public class Test {

    public static void main(String[] args) {

        Flights flights = new Flights();

        Random random = new SimpleRandom(0);
        CellRandomiser cellRandomiser = CellRandomiserFactory.small(random);

        Search<Row> avs = new AlternatingValueSearch(random,
                new NoCellInitialization(),
                new RandomCellInitializer(cellRandomiser));

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(avs.getEvaluationsCounter(), 1000),
                new OptimumTerminationCriterion<>(avs));

        avs.setTerminationCriterion(terminationCriterion);

        DataGenerator dg = new DataGenerator(
                flights,
                new RestrictedActiveClauseCoverage(),
                new PostgresDBMS(),
                avs);

        dg.generate();

        /*
        Table flightsTable = flights.getTable("flights");
        PrimaryKeyConstraint pkConstraint = flights.getPrimaryKeyConstraint(flightsTable);
        List<Column> pkColumns = pkConstraint.getColumns();

        MatchRequirementsGenerator reqGen1 = new MatchRequirementsGenerator(
                flights,
                flightsTable, pkConstraint,
                pkColumns
        );


        System.out.println(reqGen1.generateRequirements());
        */

        /*
        NullColumnRequirementsGenerator reqGen2 = new NullColumnRequirementsGenerator(
                flights, flights.getTable("flights")
        );

        System.out.println(reqGen2.generateRequirements());

        UniqueColumnRequirementsGenerator reqGen3 = new UniqueColumnRequirementsGenerator(
                flights, flights.getTable("flights")
        );

        System.out.println(reqGen3.generateRequirements());
        */
    }

}
