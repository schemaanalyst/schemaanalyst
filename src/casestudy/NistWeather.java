package casestudy;

import org.schemaanalyst.schema.BetweenCheckPredicate;
import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.CharColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;

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
		
		Column stationId = station.addColumn("ID", new IntegerColumnType());
		stationId.setPrimaryKey();
		
		station.addColumn("CITY", new CharColumnType(20));
		station.addColumn("STATE", new CharColumnType(2));
                
		Column lat_n = station.addColumn("LAT_N", new IntegerColumnType());
                lat_n.setNotNull();
                station.addCheckConstraint(new BetweenCheckPredicate(lat_n, 0, 90));
                
		Column long_w = station.addColumn("LONG_W", new IntegerColumnType());
                long_w.setNotNull();
                station.addCheckConstraint(new BetweenCheckPredicate(long_w, -180, 180));
		
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
		
		Column statsId = stats.addColumn("ID", new IntegerColumnType());
		Column month = stats.addColumn("MONTH", new IntegerColumnType());
		Column tempF = stats.addColumn("TEMP_F", new IntegerColumnType());
		Column rainI = stats.addColumn("RAIN_I", new IntegerColumnType());
		
		stats.setPrimaryKeyConstraint(statsId, month);
		stats.addCheckConstraint(new BetweenCheckPredicate(month, 1, 12));
		stats.addCheckConstraint(new BetweenCheckPredicate(tempF, -80, 150));
		stats.addCheckConstraint(new BetweenCheckPredicate(rainI, 0, 100));
	}
}
