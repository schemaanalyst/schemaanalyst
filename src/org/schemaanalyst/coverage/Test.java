package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.predicate.Predicate;
import parsedcasestudy.Flights;

import java.util.LinkedHashSet;

/**
 * Created by phil on 21/01/2014.
 */
public class Test {

    public static void main(String[] args) {

        Flights flights = new Flights();

        PrimaryKeyTestRequirementsGenerator reqGen1 = new PrimaryKeyTestRequirementsGenerator(
                flights, flights.getTable("flights")
        );

        LinkedHashSet<Predicate> predicates1 = reqGen1.generateRequirements();
        for (Predicate p : predicates1) {
            System.out.println(p);
        }

        NullTestRequirementsGenerator reqGen2 = new NullTestRequirementsGenerator(
                flights, flights.getTable("flights")
        );

        LinkedHashSet<Predicate> predicates2 = reqGen2.generateRequirements();
        for (Predicate p : predicates2) {
            System.out.println(p);
        }

        UniqueColumnTestRequirementsGenerator reqGen3 = new UniqueColumnTestRequirementsGenerator(
                flights, flights.getTable("flights")
        );

        LinkedHashSet<Predicate> predicates3 = reqGen3.generateRequirements();
        for (Predicate p : predicates3) {
            System.out.println(p);
        }
    }

}
