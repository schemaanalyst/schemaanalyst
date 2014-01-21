package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.requirements.NullColumn;
import org.schemaanalyst.coverage.requirements.UniqueColumn;
import org.schemaanalyst.coverage.requirements.primarykey.primarykey;
import parsedcasestudy.Flights;

import java.util.LinkedHashSet;

/**
 * Created by phil on 21/01/2014.
 */
public class Test {

    public static void main(String[] args) {

        Flights flights = new Flights();

        primarykey.PrimaryKeyTestRequirementsGenerator reqGen1 = new primarykey.PrimaryKeyTestRequirementsGenerator(
                flights, flights.getTable("flights")
        );

        LinkedHashSet<Predicate> predicates1 = reqGen1.generateRequirements();
        for (Predicate p : predicates1) {
            System.out.println(p);
        }

        NullColumn reqGen2 = new NullColumn(
                flights, flights.getTable("flights")
        );

        LinkedHashSet<Predicate> predicates2 = reqGen2.generateRequirements();
        for (Predicate p : predicates2) {
            System.out.println(p);
        }

        UniqueColumn reqGen3 = new UniqueColumn(
                flights, flights.getTable("flights")
        );

        LinkedHashSet<Predicate> predicates3 = reqGen3.generateRequirements();
        for (Predicate p : predicates3) {
            System.out.println(p);
        }
    }

}
