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
 * Java code originally generated: 2013/07/11 20:12:22
 *
 */
@SuppressWarnings("serial")
public class World extends Schema {

    public World() {
        super("World");

        Table tableCity = this.createTable("city");
        tableCity.addColumn("id", new IntDataType());
        tableCity.addColumn("name", new VarCharDataType(100));
        tableCity.addColumn("countrycode", new CharDataType(3));
        tableCity.addColumn("district", new VarCharDataType(100));
        tableCity.addColumn("population", new IntDataType());
        tableCity.setPrimaryKeyConstraint(tableCity.getColumn("id"));
        tableCity.addNotNullConstraint(tableCity.getColumn("id"));
        tableCity.addNotNullConstraint(tableCity.getColumn("name"));
        tableCity.addNotNullConstraint(tableCity.getColumn("countrycode"));
        tableCity.addNotNullConstraint(tableCity.getColumn("district"));
        tableCity.addNotNullConstraint(tableCity.getColumn("population"));

        Table tableCountry = this.createTable("country");
        tableCountry.addColumn("code", new VarCharDataType(3));
        tableCountry.addColumn("name", new VarCharDataType(100));
        tableCountry.addColumn("continent", new VarCharDataType(100));
        tableCountry.addColumn("region", new VarCharDataType(100));
        tableCountry.addColumn("surfacearea", new IntDataType());
        tableCountry.addColumn("indepyear", new IntDataType());
        tableCountry.addColumn("population", new IntDataType());
        tableCountry.addColumn("lifeexpectancy", new IntDataType());
        tableCountry.addColumn("gnp", new IntDataType());
        tableCountry.addColumn("gnpold", new IntDataType());
        tableCountry.addColumn("localname", new VarCharDataType(100));
        tableCountry.addColumn("governmentform", new VarCharDataType(100));
        tableCountry.addColumn("headofstate", new VarCharDataType(100));
        tableCountry.addColumn("capital", new IntDataType());
        tableCountry.addColumn("code2", new VarCharDataType(2));
        tableCountry.setPrimaryKeyConstraint(tableCountry.getColumn("code"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("code"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("name"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("continent"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("region"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("surfacearea"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("population"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("localname"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("governmentform"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("code2"));
        tableCountry.addCheckConstraint("country_continent_check", new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new OrExpression(new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Asia")))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Europe")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("NorthAAmerica")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Africa")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Oceania")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("Antarctica")))))), new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableCountry.getColumn("continent")), RelationalOperator.EQUALS, new ConstantExpression(new StringValue("SouthAAmerica")))))));

        Table tableCountrylanguage = this.createTable("countrylanguage");
        tableCountrylanguage.addColumn("countrycode", new CharDataType(3));
        tableCountrylanguage.addColumn("language", new VarCharDataType(100));
        tableCountrylanguage.addColumn("isofficial", new BooleanDataType());
        tableCountrylanguage.addColumn("percentage", new RealDataType());
        tableCountrylanguage.addNotNullConstraint(tableCountrylanguage.getColumn("countrycode"));
        tableCountrylanguage.addNotNullConstraint(tableCountrylanguage.getColumn("language"));
        tableCountrylanguage.addNotNullConstraint(tableCountrylanguage.getColumn("isofficial"));
        tableCountrylanguage.addNotNullConstraint(tableCountrylanguage.getColumn("percentage"));
    }
}
