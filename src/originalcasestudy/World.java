package originalcasestudy;

import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.InCheckCondition;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.BooleanDataType;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

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

        Table cityTable = createTable("city");

        Column id = cityTable.addColumn("id", new IntDataType());
        id.setPrimaryKey();
        id.setNotNull();

        Column name = cityTable.addColumn("name", new VarCharDataType(100));
        name.setNotNull();

        Column countrycode = cityTable.addColumn("countrycode", new CharDataType(3));
        countrycode.setNotNull();

        Column district = cityTable.addColumn("district", new VarCharDataType(100));
        district.setNotNull();

        Column population = cityTable.addColumn("population", new IntDataType());
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

        Table countryTable = createTable("country");

        Column code = countryTable.addColumn("code", new VarCharDataType(3));
        code.setPrimaryKey();
        code.setNotNull();

        Column nameCountry = countryTable.addColumn("name", new VarCharDataType(100));
        nameCountry.setNotNull();

        Column continent = countryTable.addColumn("continent", new VarCharDataType(100));
        continent.setNotNull();

        Column region = countryTable.addColumn("region", new VarCharDataType(100));
        region.setNotNull();

        Column surfacearea = countryTable.addColumn("surfacearea", new IntDataType());
        surfacearea.setNotNull();

        Column indepyear = countryTable.addColumn("indepyear", new IntDataType());

        Column populationCountry = countryTable.addColumn("population", new IntDataType());
        populationCountry.setNotNull();

        Column lifeexpectancy = countryTable.addColumn("lifeexpectancy", new IntDataType());
        Column gnp = countryTable.addColumn("gnp", new IntDataType());
        Column gnpold = countryTable.addColumn("gnpold", new IntDataType());

        Column localname = countryTable.addColumn("localname", new VarCharDataType(100));
        localname.setNotNull();

        Column governmentform = countryTable.addColumn("governmentform", new VarCharDataType(100));
        governmentform.setNotNull();

        Column capital = countryTable.addColumn("capital", new IntDataType());
        capital.setNotNull();

        Column codeTwo = countryTable.addColumn("code2", new VarCharDataType(2));
        codeTwo.setNotNull();

        countryTable.addCheckConstraint(new InCheckCondition(continent, "Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America"));

        /*
		  
         CREATE TABLE countrylanguage (
         countrycode character(3) NOT NULL,
         "language" varchar(100) NOT NULL,
         isofficial boolean NOT NULL,
         percentage real NOT NULL
         );

         */

        Table countryLanguageTable = createTable("countrylanguage");

        Column countrycodeNext = countryLanguageTable.addColumn("countrycode", new CharDataType(3));
        countrycodeNext.setNotNull();

        Column language = countryLanguageTable.addColumn("language", new VarCharDataType(100));
        language.setNotNull();

        Column isofficial = countryLanguageTable.addColumn("isofficial", new BooleanDataType());
        isofficial.setNotNull();

        Column percentage = countryLanguageTable.addColumn("percentage", new RealDataType());
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
