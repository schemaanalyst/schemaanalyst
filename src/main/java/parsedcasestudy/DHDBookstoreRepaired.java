package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * DHDBookstoreRepaired schema.
 * Java code originally generated: 2014/02/12 12:48:03
 *
 */

@SuppressWarnings("serial")
public class DHDBookstoreRepaired extends Schema {

	public DHDBookstoreRepaired() {
		super("DHDBookstoreRepaired");

		Table tableAuthors = this.createTable("Authors");
		tableAuthors.createColumn("author_name", new TextDataType());
		this.createUniqueConstraint("Authors_PrimaryKey", tableAuthors, tableAuthors.getColumn("author_name"));

		Table tableBooks = this.createTable("Books");
		tableBooks.createColumn("isbn", new TextDataType());
		tableBooks.createColumn("author_name", new TextDataType());
		tableBooks.createColumn("title", new TextDataType());
		tableBooks.createColumn("publisher_name", new TextDataType());
		tableBooks.createColumn("publication_year", new IntDataType());
		tableBooks.createColumn("binding", new TextDataType());
		tableBooks.createColumn("source_numb", new IntDataType());
		tableBooks.createColumn("retail_price", new RealDataType());
		tableBooks.createColumn("number_on_hand", new IntDataType());
		this.createUniqueConstraint("Books_PrimaryKey", tableBooks, tableBooks.getColumn("isbn"));

		Table tableCustomers = this.createTable("Customers");
		tableCustomers.createColumn("customer_numb", new IntDataType());
		tableCustomers.createColumn("customer_first_name", new TextDataType());
		tableCustomers.createColumn("customer_last_name", new TextDataType());
		tableCustomers.createColumn("customer_street", new TextDataType());
		tableCustomers.createColumn("customer_city", new TextDataType());
		tableCustomers.createColumn("customer_state", new TextDataType());
		tableCustomers.createColumn("customer_phone", new TextDataType());
		tableCustomers.createColumn("customer_email", new TextDataType());
		tableCustomers.createColumn("customer_zip", new TextDataType());
		this.createUniqueConstraint("Customers_PrimaryKey", tableCustomers, tableCustomers.getColumn("customer_numb"));

		Table tableOrderlines = this.createTable("Orderlines");
		tableOrderlines.createColumn("order_numb", new IntDataType());
		tableOrderlines.createColumn("isbn", new TextDataType());
		tableOrderlines.createColumn("quantity", new IntDataType());
		tableOrderlines.createColumn("cost_each", new RealDataType());
		tableOrderlines.createColumn("cost_line", new RealDataType());
		tableOrderlines.createColumn("shipped", new TextDataType());
		this.createUniqueConstraint("Orderlines_PrimaryKey", tableOrderlines, tableOrderlines.getColumn("order_numb"), tableOrderlines.getColumn("isbn"));

		Table tableOrders = this.createTable("Orders");
		tableOrders.createColumn("order_numb", new IntDataType());
		tableOrders.createColumn("customer_numb", new IntDataType());
		tableOrders.createColumn("order_date", new DateTimeDataType());
		tableOrders.createColumn("credit_card_numb", new TextDataType());
		tableOrders.createColumn("order_filled", new TextDataType());
		tableOrders.createColumn("credit card exp date", new DateTimeDataType());
		this.createUniqueConstraint("Orders_PrimaryKey", tableOrders, tableOrders.getColumn("order_numb"));

		Table tablePublishers = this.createTable("Publishers");
		tablePublishers.createColumn("publisher_name", new TextDataType());
		this.createUniqueConstraint("Publishers_PrimaryKey", tablePublishers, tablePublishers.getColumn("publisher_name"));

		Table tableSources = this.createTable("Sources");
		tableSources.createColumn("source_numb", new IntDataType());
		tableSources.createColumn("source_name", new TextDataType());
		tableSources.createColumn("source_street", new TextDataType());
		tableSources.createColumn("source_city", new TextDataType());
		tableSources.createColumn("source_state", new TextDataType());
		tableSources.createColumn("source_zip", new IntDataType());
		tableSources.createColumn("source_phone", new TextDataType());
		this.createUniqueConstraint("Sources_PrimaryKey", tableSources, tableSources.getColumn("source_numb"));
	}
}

