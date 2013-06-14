package casestudy;

import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.datatype.CharDataType;
import org.schemaanalyst.representation.datatype.IntDataType;
import org.schemaanalyst.representation.expression.BetweenExpression;

public class NistWeather extends Schema {
	
	private static final long serialVersionUID = -7445375531349380933L;

	public NistWeather() {
		super("NistWeather");
		
		/*
                        CREATE TABLE Station 
                        (
                           ID INTEGER PRIMARY KEY, 
                           CITY CHAR(20), 
                           STATE CHAR(2), 
                           LAT_N INTEGER NOT NULL CHECK (LAT_N BETWEEN 0 and 90), 
                           LONG_W INTEGER NOT NULL CHECK (LONG_W BETWEEN 180 and -180)
                        );
		 */
		
		Table station = createTable("Station");
		
		Column stationId = station.addColumn("ID", new IntDataType());
		stationId.setPrimaryKey();
		
		station.addColumn("CITY", new CharDataType(20));
		station.addColumn("STATE", new CharDataType(2));
                
		Column lat_n = station.addColumn("LAT_N", new IntDataType());
                lat_n.setNotNull();
                station.addCheckConstraint(new BetweenExpression(lat_n, 0, 90));
                
		Column long_w = station.addColumn("LONG_W", new IntDataType());
                long_w.setNotNull();
                station.addCheckConstraint(new BetweenExpression(long_w, -180, 180));
		
		/*
                        CREATE TABLE Stats
                        (
                          ID INTEGER REFERENCES STATION(ID), 
                          MONTH INTEGER NOT NULL CHECK (MONTH BETWEEN 1 AND 12), 
                          TEMP_F INTEGER NOT NULL CHECK (TEMP_F BETWEEN -80 AND 150), 
                          RAIN_I INTEGER NOT NULL CHECK (RAIN_I BETWEEN 0 AND 100), 
                          PRIMARY KEY (ID, MONTH)
                        );
		 */
		
		Table stats = createTable("Stats");
		
		Column statsId = stats.addColumn("ID", new IntDataType());
		Column month = stats.addColumn("MONTH", new IntDataType());
		Column tempF = stats.addColumn("TEMP_F", new IntDataType());
		Column rainI = stats.addColumn("RAIN_I", new IntDataType());
		
		stats.setPrimaryKeyConstraint(statsId, month);
		stats.addCheckConstraint(new BetweenExpression(month, 1, 12));
		stats.addCheckConstraint(new BetweenExpression(tempF, -80, 150));
		stats.addCheckConstraint(new BetweenExpression(rainI, 0, 100));
	}
}
