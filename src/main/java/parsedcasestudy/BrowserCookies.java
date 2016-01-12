package parsedcasestudy;

import java.util.Arrays;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/*
 * BrowserCookies schema.
 * Java code originally generated: 2014/07/17 10:13:22
 *
 */

@SuppressWarnings("serial")
public class BrowserCookies extends Schema {

	public BrowserCookies() {
		super("BrowserCookies");

		Table tablePlaces = this.createTable("places");
		tablePlaces.createColumn("host", new TextDataType());
		tablePlaces.createColumn("path", new TextDataType());
		tablePlaces.createColumn("title", new TextDataType());
		tablePlaces.createColumn("visit_count", new IntDataType());
		tablePlaces.createColumn("fav_icon_url", new TextDataType());
		this.createPrimaryKeyConstraint(tablePlaces, tablePlaces.getColumn("host"), tablePlaces.getColumn("path"));
		this.createNotNullConstraint(tablePlaces, tablePlaces.getColumn("host"));
		this.createNotNullConstraint(tablePlaces, tablePlaces.getColumn("path"));

		Table tableCookies = this.createTable("cookies");
		tableCookies.createColumn("id", new IntDataType());
		tableCookies.createColumn("name", new TextDataType());
		tableCookies.createColumn("value", new TextDataType());
		tableCookies.createColumn("expiry", new IntDataType());
		tableCookies.createColumn("last_accessed", new IntDataType());
		tableCookies.createColumn("creation_time", new IntDataType());
		tableCookies.createColumn("host", new TextDataType());
		tableCookies.createColumn("path", new TextDataType());
		this.createPrimaryKeyConstraint(tableCookies, tableCookies.getColumn("id"));
		this.createCheckConstraint(tableCookies, new OrExpression(new RelationalExpression(new ColumnExpression(tableCookies, tableCookies.getColumn("expiry")), RelationalOperator.EQUALS, new ConstantExpression(new NumericValue(0))), new RelationalExpression(new ColumnExpression(tableCookies, tableCookies.getColumn("expiry")), RelationalOperator.GREATER, new ColumnExpression(tableCookies, tableCookies.getColumn("last_accessed")))));
		this.createCheckConstraint(tableCookies, new RelationalExpression(new ColumnExpression(tableCookies, tableCookies.getColumn("last_accessed")), RelationalOperator.GREATER_OR_EQUALS, new ColumnExpression(tableCookies, tableCookies.getColumn("creation_time"))));
		this.createForeignKeyConstraint(tableCookies, Arrays.asList(tableCookies.getColumn("host"), tableCookies.getColumn("path")), tablePlaces, Arrays.asList(tablePlaces.getColumn("host"), tablePlaces.getColumn("path")));
		this.createNotNullConstraint(tableCookies, tableCookies.getColumn("id"));
		this.createNotNullConstraint(tableCookies, tableCookies.getColumn("name"));
		this.createUniqueConstraint(tableCookies, tableCookies.getColumn("name"), tableCookies.getColumn("host"), tableCookies.getColumn("path"));
	}
}

