package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.RestrictedActiveClauseCoverage;
import org.schemaanalyst.coverage.search.AlternatingValueSearch;
import org.schemaanalyst.coverage.search.cellinitialization.NoCellInitialization;
import org.schemaanalyst.coverage.search.cellinitialization.RandomCellInitializer;
import org.schemaanalyst.coverage.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.data.Data;
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

        TestSuiteGenerator dg = new TestSuiteGenerator(
                flights,
                new RestrictedActiveClauseCoverage(),
                new PostgresDBMS(),
                avs);

        TestSuite testSuite = dg.generate();

        boolean first = true;
        for (TestCase testCase : testSuite.getTestCases()) {
            if (first) {
                System.out.println();
            } else {
                first = false;
            }
            for (Predicate predicate : testCase.getPredicates()) {
                System.out.println("PURPOSE:   " + predicate.getPurpose());
                System.out.println("PREDICATE: " + predicate);
            }
            Data state = testCase.getState();
            if (state.getCells().size() > 0) {
                System.out.println("STATE:     " + testCase.getState());
            }
            System.out.println("ROW:       " + testCase.getRow());
        }
    }
}
