package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * ProductSalesRepaired schema.
 * Java code originally generated: 2014/02/11 15:52:48
 *
 */

@SuppressWarnings("serial")
public class ProductSalesRepaired extends Schema {

	public ProductSalesRepaired() {
		super("ProductSalesRepaired");

		Table tableDivisions = this.createTable("Divisions");
		tableDivisions.createColumn("DivisionId", new IntDataType());
		tableDivisions.createColumn("Name", new TextDataType());
		this.createUniqueConstraint("Divisions_PrimaryKey", tableDivisions, tableDivisions.getColumn("DivisionId"));

		Table tableProducts = this.createTable("Products");
		tableProducts.createColumn("ProductId", new IntDataType());
		tableProducts.createColumn("SKU", new TextDataType());
		tableProducts.createColumn("ProductDescription", new TextDataType());
		tableProducts.createColumn("Price", new TextDataType());
		tableProducts.createColumn("QuantityInStock", new IntDataType());
		this.createUniqueConstraint("Products_PrimaryKey", tableProducts, tableProducts.getColumn("ProductId"));

		Table tableProductssales = this.createTable("ProductsSales");
		tableProductssales.createColumn("SellerId", new IntDataType());
		tableProductssales.createColumn("ProductId", new IntDataType());
		tableProductssales.createColumn("QuantitySold", new IntDataType());
		tableProductssales.createColumn("UnitSalesPrice", new TextDataType());
		tableProductssales.createColumn("DateOfSale", new DateTimeDataType());
		this.createUniqueConstraint("ProductsSales_ProductsProductsSales", tableProductssales, tableProductssales.getColumn("SellerId"), tableProductssales.getColumn("ProductId"));

		Table tableSalesperformance = this.createTable("SalesPerformance");
		tableSalesperformance.createColumn("SellerId", new IntDataType());
		tableSalesperformance.createColumn("TotalSales", new TextDataType());
		tableSalesperformance.createColumn("Commission", new TextDataType());

		Table tableSalesmen = this.createTable("Salesmen");
		tableSalesmen.createColumn("SalesmanId", new IntDataType());
		tableSalesmen.createColumn("Division", new IntDataType());
		tableSalesmen.createColumn("FirstName", new TextDataType());
		tableSalesmen.createColumn("LastName", new TextDataType());
		tableSalesmen.createColumn("SSNum", new TextDataType());
		this.createUniqueConstraint("Salesmen_PrimaryKey", tableSalesmen, tableSalesmen.getColumn("SalesmanId"));

		Table tableSwitchboarditems = this.createTable("Switchboard_Items");
		tableSwitchboarditems.createColumn("SwitchboardID", new IntDataType());
		tableSwitchboarditems.createColumn("ItemNumber", new IntDataType());
		tableSwitchboarditems.createColumn("ItemText", new TextDataType());
		tableSwitchboarditems.createColumn("Command", new IntDataType());
		tableSwitchboarditems.createColumn("Argument", new TextDataType());
		this.createUniqueConstraint("Switchboard_Items_PrimaryKey", tableSwitchboarditems, tableSwitchboarditems.getColumn("SwitchboardID"), tableSwitchboarditems.getColumn("ItemNumber"));
	}
}

