package parsedcasestudy;

import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimeDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

import java.util.Arrays;

/*
 * Flights schema.
 * Java code originally generated: 2013/08/17 00:30:36
 *
 */

@SuppressWarnings("serial")
public class Flights extends Schema {

	public Flights() {
		super("Flights");

		Table tableFlights = this.createTable("Flights");
		tableFlights.createColumn("FLIGHT_ID", new CharDataType(6));
		tableFlights.createColumn("SEGMENT_NUMBER", new IntDataType());
		tableFlights.createColumn("ORIG_AIRPORT", new CharDataType(3));
		tableFlights.createColumn("DEPART_TIME", new TimeDataType());
		tableFlights.createColumn("DEST_AIRPORT", new CharDataType(3));
		tableFlights.createColumn("ARRIVE_TIME", new TimeDataType());
		tableFlights.createColumn("MEAL", new CharDataType(1));
		this.createPrimaryKeyConstraint(tableFlights, tableFlights.getColumn("FLIGHT_ID"), tableFlights.getColumn("SEGMENT_NUMBER"));
		this.createCheckConstraint("MEAL_CONSTRAINT", tableFlights, new InExpression(new ColumnExpression(tableFlights, tableFlights.getColumn("MEAL")), new ListExpression(new ConstantExpression(new StringValue("B")), new ConstantExpression(new StringValue("L")), new ConstantExpression(new StringValue("D")), new ConstantExpression(new StringValue("S"))), false));
		this.createNotNullConstraint(tableFlights, tableFlights.getColumn("FLIGHT_ID"));
		this.createNotNullConstraint(tableFlights, tableFlights.getColumn("SEGMENT_NUMBER"));
		this.createNotNullConstraint(tableFlights, tableFlights.getColumn("MEAL"));

		Table tableFlightavailable = this.createTable("FlightAvailable");
		tableFlightavailable.createColumn("FLIGHT_ID", new CharDataType(6));
		tableFlightavailable.createColumn("SEGMENT_NUMBER", new IntDataType());
		tableFlightavailable.createColumn("FLIGHT_DATE", new DateDataType());
		tableFlightavailable.createColumn("ECONOMY_SEATS_TAKEN", new IntDataType());
		tableFlightavailable.createColumn("BUSINESS_SEATS_TAKEN", new IntDataType());
		tableFlightavailable.createColumn("FIRSTCLASS_SEATS_TAKEN", new IntDataType());
		this.createPrimaryKeyConstraint("FLTAVAIL_PK", tableFlightavailable, tableFlightavailable.getColumn("FLIGHT_ID"), tableFlightavailable.getColumn("SEGMENT_NUMBER"));
		this.createForeignKeyConstraint("FLTS_FK", tableFlightavailable, Arrays.asList(tableFlightavailable.getColumn("FLIGHT_ID"), tableFlightavailable.getColumn("SEGMENT_NUMBER")), tableFlights, Arrays.asList(tableFlights.getColumn("FLIGHT_ID"), tableFlights.getColumn("SEGMENT_NUMBER")));
		this.createNotNullConstraint(tableFlightavailable, tableFlightavailable.getColumn("FLIGHT_ID"));
		this.createNotNullConstraint(tableFlightavailable, tableFlightavailable.getColumn("SEGMENT_NUMBER"));
		this.createNotNullConstraint(tableFlightavailable, tableFlightavailable.getColumn("FLIGHT_DATE"));
	}
}

