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

/*
 * Flights schema.
 * Java code originally generated: 2013/07/11 14:08:08
 *
 */
@SuppressWarnings("serial")
public class Flights extends Schema {

    public Flights() {
        super("Flights");

        Table tableFlights = this.createTable("Flights");
        tableFlights.addColumn("FLIGHT_ID", new CharDataType(6));
        tableFlights.addColumn("SEGMENT_NUMBER", new IntDataType());
        tableFlights.addColumn("ORIG_AIRPORT", new CharDataType(3));
        tableFlights.addColumn("DEPART_TIME", new TimeDataType());
        tableFlights.addColumn("DEST_AIRPORT", new CharDataType(3));
        tableFlights.addColumn("ARRIVE_TIME", new TimeDataType());
        tableFlights.addColumn("MEAL", new CharDataType(1));
        tableFlights.setPrimaryKeyConstraint(tableFlights.getColumn("FLIGHT_ID"), tableFlights.getColumn("SEGMENT_NUMBER"));
        tableFlights.addNotNullConstraint(tableFlights.getColumn("FLIGHT_ID"));
        tableFlights.addNotNullConstraint(tableFlights.getColumn("SEGMENT_NUMBER"));
        tableFlights.addNotNullConstraint(tableFlights.getColumn("MEAL"));
        tableFlights.addCheckConstraint("MEAL_CONSTRAINT", new InExpression(new ColumnExpression(tableFlights.getColumn("MEAL")), new ListExpression(new ConstantExpression(new StringValue("B")), new ConstantExpression(new StringValue("L")), new ConstantExpression(new StringValue("D")), new ConstantExpression(new StringValue("S"))), false));

        Table tableFlightavailable = this.createTable("FlightAvailable");
        tableFlightavailable.addColumn("FLIGHT_ID", new CharDataType(6));
        tableFlightavailable.addColumn("SEGMENT_NUMBER", new IntDataType());
        tableFlightavailable.addColumn("FLIGHT_DATE", new DateDataType());
        tableFlightavailable.addColumn("ECONOMY_SEATS_TAKEN", new IntDataType());
        tableFlightavailable.addColumn("BUSINESS_SEATS_TAKEN", new IntDataType());
        tableFlightavailable.addColumn("FIRSTCLASS_SEATS_TAKEN", new IntDataType());
        tableFlightavailable.setPrimaryKeyConstraint("FLTAVAIL_PK", tableFlightavailable.getColumn("FLIGHT_ID"), tableFlightavailable.getColumn("SEGMENT_NUMBER"));
        tableFlightavailable.addForeignKeyConstraint("FLTS_FK", tableFlightavailable.getColumn("FLIGHT_ID"), tableFlightavailable.getColumn("SEGMENT_NUMBER"), tableFlights, tableFlights.getColumn("FLIGHT_ID"), tableFlights.getColumn("SEGMENT_NUMBER"));
        tableFlightavailable.addNotNullConstraint(tableFlightavailable.getColumn("FLIGHT_ID"));
        tableFlightavailable.addNotNullConstraint(tableFlightavailable.getColumn("SEGMENT_NUMBER"));
        tableFlightavailable.addNotNullConstraint(tableFlightavailable.getColumn("FLIGHT_DATE"));
    }
}
