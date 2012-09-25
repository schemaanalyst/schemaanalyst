package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.SmallIntColumnType;
import org.schemaanalyst.schema.columntype.TimestampColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class JWhoisServer extends Schema {

    static final long serialVersionUID = 5421686623523749078L;
	
	@SuppressWarnings("unused")
	public JWhoisServer() {
		super("JWhoisServer");
		
		/*
		  
		  CREATE TABLE domain (
		  domain_key INTEGER NOT NULL
		  , domain VARCHAR(255) NOT NULL
		  , registered_date TIMESTAMP NOT NULL
		  , registerexpire_date TIMESTAMP NOT NULL
		  , changed TIMESTAMP(19) DEFAULT CURRENT_TIMESTAMP NOT NULL
		  , remarks VARCHAR(255)
		  , holder INTEGER NOT NULL
		  , admin_c INTEGER NOT NULL
		  , tech_c INTEGER NOT NULL
		  , zone_c INTEGER NOT NULL
		  , mntnr_fkey INTEGER NOT NULL
		  , publicviewabledata SMALLINT DEFAULT 1 NOT NULL
		  , disabled SMALLINT DEFAULT 0 NOT NULL
		  , PRIMARY KEY (domain_key)
		  );
		  
		  Timestamp --> Timestamp throughout ; we were not generating
		  correctly for timestamp.  Seems acceptable.

		*/

		Table domainTable = createTable( "domain");
		
		Column domainKey = domainTable.addColumn("domain_key" , new IntegerColumnType());
		domainKey.setNotNull();
		domainKey.setPrimaryKey();

		Column domain = domainTable.addColumn("domain" , new VarCharColumnType(255));
		domain.setNotNull();
		
		Column registered_date = domainTable.addColumn("registered_date" , new TimestampColumnType());
		registered_date.setNotNull();

		Column registerexpire_date = domainTable.addColumn("registerexpire_data" , new TimestampColumnType());
		registerexpire_date.setNotNull();

		// GMK: Timestampstamp does not have a parameter; postgres downgrades the 19 to a 6!

		Column changed = domainTable.addColumn("changed", new TimestampColumnType());
		changed.setNotNull();

		Column remarks = domainTable.addColumn("remarks" , new VarCharColumnType(255));
		Column holder = domainTable.addColumn("holder" , new IntegerColumnType());
		holder.setNotNull();
		
		Column admin_c = domainTable.addColumn("admin_c" , new IntegerColumnType());
		admin_c.setNotNull();

		Column tech_c = domainTable.addColumn("tech_c" , new IntegerColumnType());
		tech_c.setNotNull();

		Column zone_c = domainTable.addColumn("zone_c" , new IntegerColumnType());
		zone_c.setNotNull();
		
		Column mntnr_fkey = domainTable.addColumn("mntnr_fkey" , new IntegerColumnType());
		mntnr_fkey.setNotNull();

		Column publicviewabledata = domainTable.addColumn("publicviewabledata" , new SmallIntColumnType());
		publicviewabledata.setNotNull();

		Column disabled = domainTable.addColumn("disabled" , new SmallIntColumnType());
		disabled.setNotNull();

		/*
		  
		  CREATE TABLE mntnr (
		  mntnr_key INTEGER NOT NULL
		  , login VARCHAR(255) NOT NULL
		  , password VARCHAR(255) NOT NULL
		  , name VARCHAR(255) NOT NULL
		  , address VARCHAR(255) NOT NULL
		  , pcode VARCHAR(20) NOT NULL
		  , city VARCHAR(255) NOT NULL
		  , country_fkey INTEGER DEFAULT 0 NOT NULL
		  , phone VARCHAR(100) NOT NULL
		  , fax VARCHAR(100)
		  , email VARCHAR(255) NOT NULL
		  , remarks VARCHAR(255)
		  , changed TIMESTAMP(19) DEFAULT CURRENT_TIMESTAMP NOT NULL
		  , disabled SMALLINT DEFAULT 0 NOT NULL
		  , PRIMARY KEY (mntnr_key)
		  );

		 */

		Table mntnrTable = createTable( "mntnr");

		Column mntnr_key = mntnrTable.addColumn("mntnr_key" , new IntegerColumnType());
		mntnr_key.setNotNull();
		mntnr_key.setPrimaryKey();

		Column login = mntnrTable.addColumn("login" , new VarCharColumnType(255));
		login.setNotNull();

		Column password = mntnrTable.addColumn("password" , new VarCharColumnType(255));
		password.setNotNull();

		Column name = mntnrTable.addColumn("name" , new VarCharColumnType(255));
		name.setNotNull();

		Column address = mntnrTable.addColumn("address" , new VarCharColumnType(255));
		address.setNotNull();

		Column pcode = mntnrTable.addColumn("pcode", new VarCharColumnType(255));
		pcode.setNotNull();

		Column city = mntnrTable.addColumn("city" , new VarCharColumnType(255));
		city.setNotNull();

		Column country_fkey = mntnrTable.addColumn("country_fkey" , new IntegerColumnType());
		country_fkey.setNotNull();

		Column phone = mntnrTable.addColumn("phone", new VarCharColumnType(100));
		phone.setNotNull();

		Column fax = mntnrTable.addColumn("fax" , new VarCharColumnType(100));

		Column email = mntnrTable.addColumn("email", new VarCharColumnType(255));
		email.setNotNull();

		Column remarksAgain = mntnrTable.addColumn("remarks", new VarCharColumnType(255));
		
		// note, same issue with the timestamp(19) jazz
		Column changedAgain = mntnrTable.addColumn("changed" , new TimestampColumnType());
		changedAgain.setNotNull();

		Column disabledAgain = mntnrTable.addColumn("disabled" , new SmallIntColumnType());
		disabledAgain.setNotNull();

		/*

		  CREATE TABLE person (
		  person_key INTEGER NOT NULL
		  , type_fkey INTEGER DEFAULT 0 NOT NULL
		  , name VARCHAR(255) NOT NULL
		  , address VARCHAR(255) NOT NULL
		  , pcode VARCHAR(20) NOT NULL
		  , city VARCHAR(255) NOT NULL
		  , country_fkey INTEGER DEFAULT 0 NOT NULL
		  , phone VARCHAR(100) NOT NULL
		  , fax VARCHAR(100)
		  , email VARCHAR(255) NOT NULL
		  , remarks VARCHAR(255)
		  , changed TIMESTAMP(19) DEFAULT CURRENT_TIMESTAMP NOT NULL
		  , mntnr_fkey INTEGER DEFAULT 0 NOT NULL
		  , disabled SMALLINT DEFAULT 0 NOT NULL
		  , PRIMARY KEY (person_key)
		  );

		 */

		Table personTable = createTable( "person");
		
		Column person_key = personTable.addColumn("person_key", new IntegerColumnType());
		person_key.setNotNull();
		person_key.setPrimaryKey();

		Column type_fkey = personTable.addColumn("type_fkey" , new IntegerColumnType());
		type_fkey.setNotNull();

		Column namePerson = personTable.addColumn("name" , new VarCharColumnType(255));
		namePerson.setNotNull();

		Column addressPerson = personTable.addColumn("address" , new VarCharColumnType(255));
		addressPerson.setNotNull();

		Column pcodePerson = personTable.addColumn("pcode" , new VarCharColumnType(20));
		pcodePerson.setNotNull();

		Column cityPerson = personTable.addColumn("city" , new VarCharColumnType(255));
		cityPerson.setNotNull();

		Column country_fkeyPerson = personTable.addColumn("country_fkey" , new IntegerColumnType());
		country_fkeyPerson.setNotNull();
		
		Column phonePerson = personTable.addColumn("phone", new VarCharColumnType(100));
		phonePerson.setNotNull();

		Column faxPerson = personTable.addColumn("fax" , new VarCharColumnType(100));
		
		Column emailPerson = personTable.addColumn("email", new VarCharColumnType(255));
		emailPerson.setNotNull();

		Column remarksPerson = personTable.addColumn("remarks", new VarCharColumnType(255));
		
		// note, same issue with the timestamp(19) jazz
		Column changedPerson = personTable.addColumn("changed" , new TimestampColumnType());
		changedPerson.setNotNull();

		Column mntnr_fkeyPerson = personTable.addColumn("mntnr_fkey" , new IntegerColumnType());
		mntnr_fkeyPerson.setNotNull();

		Column disabledPerson = personTable.addColumn("disabled" , new SmallIntColumnType());
		disabledPerson.setNotNull();		

		/*
		  
		  CREATE TABLE type (
		  type_key INTEGER NOT NULL
		  , type VARCHAR(100) NOT NULL
		  , PRIMARY KEY (type_key)
		  );

		 */

		Table typeTable = createTable( "type");

		Column type_key = typeTable.addColumn("type_key" , new IntegerColumnType());
		type_key.setNotNull();
		type_key.setPrimaryKey();

		Column type = typeTable.addColumn("type" , new VarCharColumnType(100));
		type.setNotNull();

		/*
		  
		CREATE TABLE nameserver (
		nameserver_key INTEGER NOT NULL
		, nameserver VARCHAR(255) NOT NULL
		, domain_fkey INTEGER NOT NULL
		, PRIMARY KEY (nameserver_key)
		);  

		 */

		Table nameServerTable = createTable( "nameserver");

		Column nameserver_key = nameServerTable.addColumn("nameserver_key" , new IntegerColumnType());
		nameserver_key.setNotNull();
		nameserver_key.setPrimaryKey();

		Column nameserver = nameServerTable.addColumn("nameserver" , new VarCharColumnType(255));
		nameserver.setNotNull();

		Column domain_fkey = nameServerTable.addColumn("domain_fkey" , new IntegerColumnType());
		domain_fkey.setNotNull();
		
		/*
		  
		  CREATE TABLE country (
		  country_key INTEGER NOT NULL
		  , short VARCHAR(2) NOT NULL
		  , country VARCHAR(255) NOT NULL
		  , PRIMARY KEY (country_key)
		  );

		 */

		Table countryTable = createTable( "country");
		
		Column country_fkeyCountry = countryTable.addColumn("country_fkey" , new IntegerColumnType());
		country_fkeyCountry.setNotNull();
		country_fkeyCountry.setPrimaryKey();

		Column shortCountry = countryTable.addColumn("short" , new VarCharColumnType(2));
		shortCountry.setNotNull();

		Column countryCountry = countryTable.addColumn("country", new VarCharColumnType(255));
		countryCountry.setNotNull();

	}
}
