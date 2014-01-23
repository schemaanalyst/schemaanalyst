package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.requirements.MatchRequirementsGenerator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import parsedcasestudy.Flights;

import java.util.List;

/**
 * Created by phil on 21/01/2014.
 */
public class Test {

    public static void main(String[] args) {

        Flights flights = new Flights();
        Table flightsTable = flights.getTable("flights");
        PrimaryKeyConstraint pkConstraint = flights.getPrimaryKeyConstraint(flightsTable);
        List<Column> pkColumns = pkConstraint.getColumns();

        MatchRequirementsGenerator reqGen1 = new MatchRequirementsGenerator(
                flights,
                flightsTable, pkConstraint,
                pkColumns
        );

        System.out.println(reqGen1.generateRequirements());

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
