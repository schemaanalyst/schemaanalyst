package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.InCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.CharColumnType;
import org.schemaanalyst.schema.columntype.DateColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.TimeColumnType;

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
		
		Column flightsFlightId = flights.addColumn("FLIGHT_ID", new CharColumnType(6));
		flightsFlightId.setNotNull();
		
		Column flightsSegmentNumber = flights.addColumn("SEGMENT_NUMBER", new IntegerColumnType());
		flightsSegmentNumber.setNotNull();
		
		flights.addColumn("ORIGINAL_AIRPORT", new CharColumnType(3));		
		flights.addColumn("DEPART_TIME", new TimeColumnType());
		flights.addColumn("DEST_AIRPORT", new CharColumnType(3));		
		flights.addColumn("ARRIVE_TIME", new TimeColumnType());
		
		Column meal = flights.addColumn("MEAL", new CharColumnType(1));
                meal.setNotNull();
		
		flights.addCheckConstraint("MEAL_CONSTRAINT", new InCheckPredicate(meal, "B", "L", "D", "S"));
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
		Column flightAvailableFlightId = flightAvailable.addColumn("FLIGHT_ID", new CharColumnType(6));
		flightAvailableFlightId.setNotNull();
		
		Column flightAvailableSegmentNumber = flightAvailable.addColumn("SEGMENT_NUMBER", new IntColumnType());
		flightAvailableSegmentNumber.setNotNull();		
		
		Column flightDate = flightAvailable.addColumn("FLIGHT_DATE", new DateColumnType());
		flightDate.setNotNull();
		
		flightAvailable.addColumn("ECONOMY_SEATS_TAKEN", new IntColumnType());
		flightAvailable.addColumn("BUSINESS_SEATS_TAKEN", new IntColumnType());
		flightAvailable.addColumn("FIRSTCLASS_SEATS_TAKEN", new IntColumnType());
		
		flightAvailable.setPrimaryKeyConstraint("FLTAVAIL_PK", flightAvailableFlightId, flightAvailableSegmentNumber);
		
		flightAvailable.addForeignKeyConstraint("FLTS_FK", flights, flightAvailableFlightId, flightAvailableSegmentNumber, flightsFlightId, flightsSegmentNumber);
	}
}
