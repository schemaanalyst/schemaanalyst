package casestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

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

		Column name = countryTable .addColumn("name" , new VarCharDataType(100));
		name.setNotNull();
		
		Column twoLetter = countryTable .addColumn("two_letter" , new VarCharDataType(100));
		twoLetter.setPrimaryKey();
		
		Column countryId = countryTable .addColumn("country_id" , new IntDataType());
		countryId.setNotNull();
	}
}
