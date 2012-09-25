package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class Iso3166 extends Schema {

    static final long serialVersionUID = 5641447098609941805L;
	
	public Iso3166() {
		super("Iso3166");
		
		/*

		  CREATE TABLE country (
		  name varchar(100) NOT NULL,
		  two_letter varchar(100) PRIMARY KEY,
		  country_id integer NOT NULL
		  );

		*/

		Table countryTable = createTable( "country");

		Column name = countryTable .addColumn("name" , new VarCharColumnType(100));
		name.setNotNull();
		
		Column twoLetter = countryTable .addColumn("two_letter" , new VarCharColumnType(100));
		twoLetter.setPrimaryKey();
		
		Column countryId = countryTable .addColumn("country_id" , new IntegerColumnType());
		countryId.setNotNull();
	}
}
