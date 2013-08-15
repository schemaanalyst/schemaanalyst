package parsedcasestudy;

import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.BooleanDataType;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/*
 * World schema.
 * Java code originally generated: 2013/08/15 10:52:23
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
		tableCity.createPrimaryKeyConstraint(tableCity.getColumn("id"));
		tableCity.createNotNullConstraint(tableCity.getColumn("id"));
		tableCity.createNotNullConstraint(tableCity.getColumn("name"));
		tableCity.createNotNullConstraint(tableCity.getColumn("countrycode"));
		tableCity.createNotNullConstraint(tableCity.getColumn("district"));
		tableCity.createNotNullConstraint(tableCity.getColumn("population"));

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
		tableCountry.createPrimaryKeyConstraint(tableCountry.getColumn("code"));
		tableCountry.createForeignKeyConstraint("country_capital_fkey", tableCountry.getColumn("capital"), tableCity, tableCountry.getColumn("id"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("code"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("name"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("continent"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("region"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("surfacearea"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("population"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("localname"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("governmentform"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("code2"));
		tableCountry.createCheckConstraint("country_continent_check", new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Asia")))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Europe")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("NorthAAmerica")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Africa")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Oceania")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Antarctica")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry, tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("SouthAAmerica")))))));

		Table tableCountrylanguage = this.createTable("countrylanguage");
		tableCountrylanguage.createColumn("countrycode", new CharDataType(3));
		tableCountrylanguage.createColumn("language", new VarCharDataType(100));
		tableCountrylanguage.createColumn("isofficial", new BooleanDataType());
		tableCountrylanguage.createColumn("percentage", new RealDataType());
		tableCountrylanguage.createPrimaryKeyConstraint("countrylanguage_pkey", tableCountrylanguage.getColumn("countrycode"), tableCountrylanguage.getColumn("language"));
		tableCountrylanguage.createForeignKeyConstraint("countrylanguage_countrycode_fkey", tableCountrylanguage.getColumn("countrycode"), tableCountry, tableCountrylanguage.getColumn("code"));
		tableCountrylanguage.createNotNullConstraint(tableCountrylanguage.getColumn("countrycode"));
		tableCountrylanguage.createNotNullConstraint(tableCountrylanguage.getColumn("language"));
		tableCountrylanguage.createNotNullConstraint(tableCountrylanguage.getColumn("isofficial"));
		tableCountrylanguage.createNotNullConstraint(tableCountrylanguage.getColumn("percentage"));
	}
}

