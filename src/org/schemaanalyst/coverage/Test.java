package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.criterion.ConstraintRACC;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.testgeneration.TestCaseDataGenerator;
import org.schemaanalyst.coverage.testgeneration.TestCase;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiserFactory;

import org.schemaanalyst.datageneration.search.AlternatingValueSearch;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.datainitialization.NoDataInitialization;
import org.schemaanalyst.datageneration.search.datainitialization.RandomDataInitializer;
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

        Search<Data> avs = new AlternatingValueSearch(random,
                new NoDataInitialization(),
                new RandomDataInitializer(cellRandomiser));

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(avs.getEvaluationsCounter(), 100000),
                new OptimumTerminationCriterion<>(avs));

        avs.setTerminationCriterion(terminationCriterion);

        TestCaseDataGenerator dg = new TestCaseDataGenerator(
                flights,
                new ConstraintRACC(),
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
            System.out.println("DATA:      " + testCase.getData());
            System.out.println("OBJ VAL:   " + testCase.getInfo("objval"));
        }
    }
}
