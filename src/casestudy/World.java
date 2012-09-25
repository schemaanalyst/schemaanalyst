package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.InCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.BooleanColumnType;
import org.schemaanalyst.schema.columntype.CharColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.RealColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class World extends Schema {

    static final long serialVersionUID = 4439845824724485332L;
	
	@SuppressWarnings("unused")
	public World() {
		super("World");
		
		/*
		  
		  CREATE TABLE city (
		  id int PRIMARY KEY NOT NULL,
		  name varchar(100) NOT NULL,
		  countrycode character(3) NOT NULL,
		  district varchar(100) NOT NULL,
		  population integer NOT NULL
		  );

		*/

		Table cityTable = createTable( "city");
		
		Column id = cityTable.addColumn("id", new IntColumnType());
		id.setPrimaryKey();
		id.setNotNull();

		Column name = cityTable.addColumn("name", new VarCharColumnType(100));
		name.setNotNull();

		Column countrycode = cityTable.addColumn("countrycode", new CharColumnType(3));
		countrycode.setNotNull();

		Column district = cityTable.addColumn("district", new VarCharColumnType(100));
		district.setNotNull();
		
		Column population = cityTable.addColumn("population", new IntegerColumnType());
		population.setNotNull();

		/*

		  CREATE TABLE country (
		  code varchar(3) PRIMARY KEY NOT NULL,
		  name varchar(100) NOT NULL,
		  continent varchar(100) NOT NULL,
		  region varchar(100) NOT NULL,
		  surfacearea int NOT NULL,
		  indepyear int,
		  population integer NOT NULL,
		  lifeexpectancy int,
		  gnp int,
		  gnpold int,
		  localname varchar(100) NOT NULL,
		  governmentform varchar(100) NOT NULL,
		  headofstate varchar(100),
		  capital integer,
		  code2 varchar(2) NOT NULL,
		  CONSTRAINT country_continent_check CHECK ((((((((continent = 'Asia'::text) OR (continent = 'Europe'::text)) OR (continent = 'North America'::text)) OR (continent = 'Africa'::text)) OR (continent = 'Oceania'::text)) OR (continent = 'Antarctica'::text)) OR (continent = 'South America'::text)))
);
		 */

		Table countryTable = createTable( "country");
		
		Column code = countryTable.addColumn("code", new VarCharColumnType(3));
		code.setPrimaryKey();
		code.setNotNull();

		Column nameCountry = countryTable.addColumn("name", new VarCharColumnType(100));
		nameCountry.setNotNull();

		Column continent = countryTable.addColumn("continent", new VarCharColumnType(100));
		continent.setNotNull();
		
		Column region = countryTable.addColumn("region", new VarCharColumnType(100));
		region.setNotNull();

		Column surfacearea = countryTable.addColumn("surfacearea", new IntColumnType());
		surfacearea.setNotNull();

		Column indepyear = countryTable.addColumn("indepyear", new IntColumnType());
		
		Column populationCountry = countryTable.addColumn("population", new IntegerColumnType());
		populationCountry.setNotNull();

		Column lifeexpectancy = countryTable.addColumn("lifeexpectancy", new IntColumnType());
		Column gnp = countryTable.addColumn("gnp", new IntColumnType());
		Column gnpold = countryTable.addColumn("gnpold", new IntColumnType());
		
		Column localname = countryTable.addColumn("localname", new VarCharColumnType(100));
		localname.setNotNull();

		Column governmentform = countryTable.addColumn("governmentform", new VarCharColumnType(100));
		governmentform.setNotNull();
		
		Column capital = countryTable.addColumn("capital", new IntegerColumnType());
		capital.setNotNull();

		Column codeTwo = countryTable.addColumn("code2", new VarCharColumnType(2));
		codeTwo.setNotNull();
		
		countryTable.addCheckConstraint(new InCheckPredicate(continent, "Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America"));
		
		/*
		  
		  CREATE TABLE countrylanguage (
		  countrycode character(3) NOT NULL,
		  "language" varchar(100) NOT NULL,
		  isofficial boolean NOT NULL,
		  percentage real NOT NULL
		  );

		 */

		Table countryLanguageTable = createTable( "countrylanguage");
		
		Column countrycodeNext = countryLanguageTable.addColumn("countrycode", new CharColumnType(3));
		countrycodeNext.setNotNull();
		
		Column language = countryLanguageTable.addColumn("language", new VarCharColumnType(100));
		language.setNotNull();
		
		Column isofficial = countryLanguageTable.addColumn("isofficial", new BooleanColumnType());
		isofficial.setNotNull();

		Column percentage = countryLanguageTable.addColumn("percentage", new RealColumnType());
		percentage.setNotNull();

		/*
		  
		  ALTER TABLE ONLY countrylanguage
		  ADD CONSTRAINT countrylanguage_pkey PRIMARY KEY (countrycode, "language");

		  ALTER TABLE ONLY country
		  ADD CONSTRAINT country_capital_fkey FOREIGN KEY (capital) REFERENCES city(id);
		  
		  ALTER TABLE ONLY countrylanguage
		  ADD CONSTRAINT countrylanguage_countrycode_fkey FOREIGN KEY (countrycode) REFERENCES country(code);

		 */

		countryLanguageTable.setPrimaryKeyConstraint(countrycodeNext, language);

		capital.setForeignKey(cityTable, id);

		countrycodeNext.setForeignKey(countryTable, code);

	}
}
