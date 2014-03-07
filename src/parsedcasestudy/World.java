package parsedcasestudy;

import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.*;
import org.schemaanalyst.sqlrepresentation.expression.*;

/*
 * World schema.
 * Java code originally generated: 2013/08/17 00:31:09
 *
 */

@SuppressWarnings("serial")
public class World extends Schema {

	public World() {
		super("World");

		Table tableCity = this.createTable("city");
		tableCity.createColumn("id", new IntDataType());
		tableCity.createColumn("name", new VarCharDataType(100));
		tableCity.createColumn("countrycode", new CharDataType(3));
		tableCity.createColumn("district", new VarCharDataType(100));
		tableCity.createColumn("population", new IntDataType());
		this.createPrimaryKeyConstraint(tableCity, tableCity.getColumn("id"));
		this.createNotNullConstraint(tableCity, tableCity.getColumn("id"));
		this.createNotNullConstraint(tableCity, tableCity.getColumn("name"));
		this.createNotNullConstraint(tableCity, tableCity.getColumn("countrycode"));
		this.createNotNullConstraint(tableCity, tableCity.getColumn("district"));
		this.createNotNullConstraint(tableCity, tableCity.getColumn("population"));

		Table tableCountry = this.createTable("country");
		tableCountry.createColumn("code", new VarCharDataType(3));
		tableCountry.createColumn("name", new VarCharDataType(100));
		tableCountry.createColumn("continent", new VarCharDataType(100));
		tableCountry.createColumn("region", new VarCharDataType(100));
		tableCountry.createColumn("surfacearea", new IntDataType());
		tableCountry.createColumn("indepyear", new IntDataType());
		tableCountry.createColumn("population", new IntDataType());
		tableCountry.createColumn("lifeexpectancy", new IntDataType());
		tableCountry.createColumn("gnp", new IntDataType());
		tableCountry.createColumn("gnpold", new IntDataType());
		tableCountry.createColumn("localname", new VarCharDataType(100));
		tableCountry.createColumn("governmentform", new VarCharDataType(100));
		tableCountry.createColumn("headofstate", new VarCharDataType(100));
		tableCountry.createColumn("capital", new IntDataType());
		tableCountry.createColumn("code2", new VarCharDataType(2));
		this.createPrimaryKeyConstraint(tableCountry, tableCountry.getColumn("code"));
		this.createCheckConstraint("country_continent_check", tableCountry, new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Asia")))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Europe")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("NorthAAmerica")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Africa")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Oceania")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Antarctica")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("SouthAAmerica")))))));
		this.createForeignKeyConstraint("country_capital_fkey", tableCountry, tableCountry.getColumn("capital"), tableCity, tableCity.getColumn("id"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("code"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("name"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("continent"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("region"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("surfacearea"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("population"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("localname"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("governmentform"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("code2"));

		Table tableCountrylanguage = this.createTable("countrylanguage");
		tableCountrylanguage.createColumn("countrycode", new CharDataType(3));
		tableCountrylanguage.createColumn("language", new VarCharDataType(100));
		tableCountrylanguage.createColumn("isofficial", new BooleanDataType());
		tableCountrylanguage.createColumn("percentage", new RealDataType());
		this.createPrimaryKeyConstraint("countrylanguage_pkey", tableCountrylanguage, tableCountrylanguage.getColumn("countrycode"), tableCountrylanguage.getColumn("language"));
		this.createForeignKeyConstraint("countrylanguage_countrycode_fkey", tableCountrylanguage, tableCountrylanguage.getColumn("countrycode"), tableCountry, tableCountry.getColumn("code"));
		this.createNotNullConstraint(tableCountrylanguage, tableCountrylanguage.getColumn("countrycode"));
		this.createNotNullConstraint(tableCountrylanguage, tableCountrylanguage.getColumn("language"));
		this.createNotNullConstraint(tableCountrylanguage, tableCountrylanguage.getColumn("isofficial"));
		this.createNotNullConstraint(tableCountrylanguage, tableCountrylanguage.getColumn("percentage"));
	}
}

