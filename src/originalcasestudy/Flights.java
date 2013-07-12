package originalcasestudy;

import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.InCheckCondition;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimeDataType;

public class Flights extends Schema {

    private static final long serialVersionUID = 4791284656976316511L;

    public Flights() {
        super("Flights");

        /*
         CREATE TABLE Flights
         (
         FLIGHT_ID CHAR(6) NOT NULL,
         SEGMENT_NUMBER INTEGER NOT NULL,
         ORIG_AIRPORT CHAR(3),
         DEPART_TIME TIME,
         DEST_AIRPORT CHAR(3),
         ARRIVE_TIME TIME,
         MEAL CHAR(1) CONSTRAINT MEAL_CONSTRAINT 
         CHECK (MEAL IN ('B', 'L', 'D', 'S')),
         PRIMARY KEY (FLIGHT_ID, SEGMENT_NUMBER)
         );
         */

        Table flights = createTable("Flights");

        Column flightsFlightId = flights.addColumn("FLIGHT_ID", new CharDataType(6));
        flightsFlightId.setNotNull();

        Column flightsSegmentNumber = flights.addColumn("SEGMENT_NUMBER", new IntDataType());
        flightsSegmentNumber.setNotNull();

        flights.addColumn("ORIGINAL_AIRPORT", new CharDataType(3));
        flights.addColumn("DEPART_TIME", new TimeDataType());
        flights.addColumn("DEST_AIRPORT", new CharDataType(3));
        flights.addColumn("ARRIVE_TIME", new TimeDataType());

        Column meal = flights.addColumn("MEAL", new CharDataType(1));
        meal.setNotNull();

        flights.addCheckConstraint("MEAL_CONSTRAINT", new InCheckCondition(meal, "B", "L", "D", "S"));
        flights.setPrimaryKeyConstraint(flightsFlightId, flightsSegmentNumber);

        /*
         CREATE TABLE FlightAvailable
         (
         FLIGHT_ID CHAR(6) NOT NULL, 
         SEGMENT_NUMBER INT NOT NULL, 
         FLIGHT_DATE DATE NOT NULL, 
         ECONOMY_SEATS_TAKEN INT,
         BUSINESS_SEATS_TAKEN INT,
         FIRSTCLASS_SEATS_TAKEN INT, 
         CONSTRAINT FLTAVAIL_PK PRIMARY KEY (FLIGHT_ID, SEGMENT_NUMBER), 
         CONSTRAINT FLTS_FK
         FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER)
         REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)
         );
         */

        Table flightAvailable = createTable("FlightAvailable");
        Column flightAvailableFlightId = flightAvailable.addColumn("FLIGHT_ID", new CharDataType(6));
        flightAvailableFlightId.setNotNull();

        Column flightAvailableSegmentNumber = flightAvailable.addColumn("SEGMENT_NUMBER", new IntDataType());
        flightAvailableSegmentNumber.setNotNull();

        Column flightDate = flightAvailable.addColumn("FLIGHT_DATE", new DateDataType());
        flightDate.setNotNull();

        flightAvailable.addColumn("ECONOMY_SEATS_TAKEN", new IntDataType());
        flightAvailable.addColumn("BUSINESS_SEATS_TAKEN", new IntDataType());
        flightAvailable.addColumn("FIRSTCLASS_SEATS_TAKEN", new IntDataType());

        flightAvailable.setPrimaryKeyConstraint("FLTAVAIL_PK", flightAvailableFlightId, flightAvailableSegmentNumber);

        flightAvailable.addForeignKeyConstraint("FLTS_FK", flightAvailableFlightId, flightAvailableSegmentNumber, flights, flightsFlightId, flightsSegmentNumber);
    }
}
