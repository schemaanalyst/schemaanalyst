package org.schemaanalyst.coverage;

import parsedcasestudy.Flights;

/**
 * Created by phil on 21/01/2014.
 */
public class Test {

    public static void main(String[] args) {

        Flights flights = new Flights();

        PrimaryKeyTestRequirementsGenerator reqGen = new PrimaryKeyTestRequirementsGenerator(
                flights, flights.getTable("flights")
        );

    }

}
