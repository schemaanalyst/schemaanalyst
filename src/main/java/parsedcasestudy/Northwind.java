package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SingleCharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Northwind schema.
 * Java code originally generated: 2014/04/17 16:47:07
 *
 */

@SuppressWarnings("serial")
public class Northwind extends Schema {

	public Northwind() {
		super("Northwind");

		Table tableCategories = this.createTable("categories");
		tableCategories.createColumn("CategoryID", new SmallIntDataType());
		tableCategories.createColumn("CategoryName", new VarCharDataType(15));
		tableCategories.createColumn("Description", new TextDataType());
		tableCategories.createColumn("Picture", new TextDataType());
		this.createPrimaryKeyConstraint("pk_categories", tableCategories, tableCategories.getColumn("CategoryID"));
		this.createNotNullConstraint(tableCategories, tableCategories.getColumn("CategoryID"));
		this.createNotNullConstraint(tableCategories, tableCategories.getColumn("CategoryName"));

		Table tableCustomercustomerdemo = this.createTable("customercustomerdemo");
		tableCustomercustomerdemo.createColumn("CustomerID", new SingleCharDataType());
		tableCustomercustomerdemo.createColumn("CustomerTypeID", new SingleCharDataType());
		this.createPrimaryKeyConstraint("pk_customercustomerdemo", tableCustomercustomerdemo, tableCustomercustomerdemo.getColumn("CustomerID"), tableCustomercustomerdemo.getColumn("CustomerTypeID"));
		this.createNotNullConstraint(tableCustomercustomerdemo, tableCustomercustomerdemo.getColumn("CustomerID"));
		this.createNotNullConstraint(tableCustomercustomerdemo, tableCustomercustomerdemo.getColumn("CustomerTypeID"));

		Table tableCustomerdemographics = this.createTable("customerdemographics");
		tableCustomerdemographics.createColumn("CustomerTypeID", new SingleCharDataType());
		tableCustomerdemographics.createColumn("CustomerDesc", new TextDataType());
		this.createPrimaryKeyConstraint("pk_customerdemographics", tableCustomerdemographics, tableCustomerdemographics.getColumn("CustomerTypeID"));
		this.createNotNullConstraint(tableCustomerdemographics, tableCustomerdemographics.getColumn("CustomerTypeID"));

		Table tableCustomers = this.createTable("customers");
		tableCustomers.createColumn("CustomerID", new SingleCharDataType());
		tableCustomers.createColumn("CompanyName", new VarCharDataType(40));
		tableCustomers.createColumn("ContactName", new VarCharDataType(30));
		tableCustomers.createColumn("ContactTitle", new VarCharDataType(30));
		tableCustomers.createColumn("Address", new VarCharDataType(60));
		tableCustomers.createColumn("City", new VarCharDataType(15));
		tableCustomers.createColumn("Region", new VarCharDataType(15));
		tableCustomers.createColumn("PostalCode", new VarCharDataType(10));
		tableCustomers.createColumn("Country", new VarCharDataType(15));
		tableCustomers.createColumn("Phone", new VarCharDataType(24));
		tableCustomers.createColumn("Fax", new VarCharDataType(24));
		this.createPrimaryKeyConstraint("pk_customers", tableCustomers, tableCustomers.getColumn("CustomerID"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("CustomerID"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("CompanyName"));

		Table tableEmployees = this.createTable("employees");
		tableEmployees.createColumn("EmployeeID", new SmallIntDataType());
		tableEmployees.createColumn("LastName", new VarCharDataType(20));
		tableEmployees.createColumn("FirstName", new VarCharDataType(10));
		tableEmployees.createColumn("Title", new VarCharDataType(30));
		tableEmployees.createColumn("TitleOfCourtesy", new VarCharDataType(25));
		tableEmployees.createColumn("BirthDate", new DateDataType());
		tableEmployees.createColumn("HireDate", new DateDataType());
		tableEmployees.createColumn("Address", new VarCharDataType(60));
		tableEmployees.createColumn("City", new VarCharDataType(15));
		tableEmployees.createColumn("Region", new VarCharDataType(15));
		tableEmployees.createColumn("PostalCode", new VarCharDataType(10));
		tableEmployees.createColumn("Country", new VarCharDataType(15));
		tableEmployees.createColumn("HomePhone", new VarCharDataType(24));
		tableEmployees.createColumn("Extension", new VarCharDataType(4));
		tableEmployees.createColumn("Photo", new TextDataType());
		tableEmployees.createColumn("Notes", new TextDataType());
		tableEmployees.createColumn("ReportsTo", new SmallIntDataType());
		tableEmployees.createColumn("PhotoPath", new VarCharDataType(255));
		this.createPrimaryKeyConstraint("pk_employees", tableEmployees, tableEmployees.getColumn("EmployeeID"));
		this.createNotNullConstraint(tableEmployees, tableEmployees.getColumn("EmployeeID"));
		this.createNotNullConstraint(tableEmployees, tableEmployees.getColumn("LastName"));
		this.createNotNullConstraint(tableEmployees, tableEmployees.getColumn("FirstName"));

		Table tableEmployeeterritories = this.createTable("employeeterritories");
		tableEmployeeterritories.createColumn("EmployeeID", new SmallIntDataType());
		tableEmployeeterritories.createColumn("TerritoryID", new VarCharDataType(20));
		this.createPrimaryKeyConstraint("pk_employeeterritories", tableEmployeeterritories, tableEmployeeterritories.getColumn("EmployeeID"), tableEmployeeterritories.getColumn("TerritoryID"));
		this.createNotNullConstraint(tableEmployeeterritories, tableEmployeeterritories.getColumn("EmployeeID"));
		this.createNotNullConstraint(tableEmployeeterritories, tableEmployeeterritories.getColumn("TerritoryID"));

		Table tableOrderDetails = this.createTable("order_details");
		tableOrderDetails.createColumn("OrderID", new SmallIntDataType());
		tableOrderDetails.createColumn("ProductID", new SmallIntDataType());
		tableOrderDetails.createColumn("UnitPrice", new RealDataType());
		tableOrderDetails.createColumn("Quantity", new SmallIntDataType());
		tableOrderDetails.createColumn("Discount", new RealDataType());
		this.createPrimaryKeyConstraint("pk_order_details", tableOrderDetails, tableOrderDetails.getColumn("OrderID"), tableOrderDetails.getColumn("ProductID"));
		this.createNotNullConstraint(tableOrderDetails, tableOrderDetails.getColumn("OrderID"));
		this.createNotNullConstraint(tableOrderDetails, tableOrderDetails.getColumn("ProductID"));
		this.createNotNullConstraint(tableOrderDetails, tableOrderDetails.getColumn("UnitPrice"));
		this.createNotNullConstraint(tableOrderDetails, tableOrderDetails.getColumn("Quantity"));
		this.createNotNullConstraint(tableOrderDetails, tableOrderDetails.getColumn("Discount"));

		Table tableOrders = this.createTable("orders");
		tableOrders.createColumn("OrderID", new SmallIntDataType());
		tableOrders.createColumn("CustomerID", new SingleCharDataType());
		tableOrders.createColumn("EmployeeID", new SmallIntDataType());
		tableOrders.createColumn("OrderDate", new DateDataType());
		tableOrders.createColumn("RequiredDate", new DateDataType());
		tableOrders.createColumn("ShippedDate", new DateDataType());
		tableOrders.createColumn("ShipVia", new SmallIntDataType());
		tableOrders.createColumn("Freight", new RealDataType());
		tableOrders.createColumn("ShipName", new VarCharDataType(40));
		tableOrders.createColumn("ShipAddress", new VarCharDataType(60));
		tableOrders.createColumn("ShipCity", new VarCharDataType(15));
		tableOrders.createColumn("ShipRegion", new VarCharDataType(15));
		tableOrders.createColumn("ShipPostalCode", new VarCharDataType(10));
		tableOrders.createColumn("ShipCountry", new VarCharDataType(15));
		this.createPrimaryKeyConstraint("pk_orders", tableOrders, tableOrders.getColumn("OrderID"));
		this.createNotNullConstraint(tableOrders, tableOrders.getColumn("OrderID"));

		Table tableProducts = this.createTable("products");
		tableProducts.createColumn("ProductID", new SmallIntDataType());
		tableProducts.createColumn("ProductName", new VarCharDataType(40));
		tableProducts.createColumn("SupplierID", new SmallIntDataType());
		tableProducts.createColumn("CategoryID", new SmallIntDataType());
		tableProducts.createColumn("QuantityPerUnit", new VarCharDataType(20));
		tableProducts.createColumn("UnitPrice", new RealDataType());
		tableProducts.createColumn("UnitsInStock", new SmallIntDataType());
		tableProducts.createColumn("UnitsOnOrder", new SmallIntDataType());
		tableProducts.createColumn("ReorderLevel", new SmallIntDataType());
		tableProducts.createColumn("Discontinued", new IntDataType());
		this.createPrimaryKeyConstraint("pk_products", tableProducts, tableProducts.getColumn("ProductID"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("ProductID"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("ProductName"));
		this.createNotNullConstraint(tableProducts, tableProducts.getColumn("Discontinued"));

		Table tableRegion = this.createTable("region");
		tableRegion.createColumn("RegionID", new SmallIntDataType());
		tableRegion.createColumn("RegionDescription", new SingleCharDataType());
		this.createPrimaryKeyConstraint("pk_region", tableRegion, tableRegion.getColumn("RegionID"));
		this.createNotNullConstraint(tableRegion, tableRegion.getColumn("RegionID"));
		this.createNotNullConstraint(tableRegion, tableRegion.getColumn("RegionDescription"));

		Table tableShippers = this.createTable("shippers");
		tableShippers.createColumn("ShipperID", new SmallIntDataType());
		tableShippers.createColumn("CompanyName", new VarCharDataType(40));
		tableShippers.createColumn("Phone", new VarCharDataType(24));
		this.createPrimaryKeyConstraint("pk_shippers", tableShippers, tableShippers.getColumn("ShipperID"));
		this.createNotNullConstraint(tableShippers, tableShippers.getColumn("ShipperID"));
		this.createNotNullConstraint(tableShippers, tableShippers.getColumn("CompanyName"));

		Table tableShippersTmp = this.createTable("shippers_tmp");
		tableShippersTmp.createColumn("ShipperID", new SmallIntDataType());
		tableShippersTmp.createColumn("CompanyName", new VarCharDataType(40));
		tableShippersTmp.createColumn("Phone", new VarCharDataType(24));
		this.createPrimaryKeyConstraint("pk_shippers_tmp", tableShippersTmp, tableShippersTmp.getColumn("ShipperID"));
		this.createNotNullConstraint(tableShippersTmp, tableShippersTmp.getColumn("ShipperID"));
		this.createNotNullConstraint(tableShippersTmp, tableShippersTmp.getColumn("CompanyName"));

		Table tableSuppliers = this.createTable("suppliers");
		tableSuppliers.createColumn("SupplierID", new SmallIntDataType());
		tableSuppliers.createColumn("CompanyName", new VarCharDataType(40));
		tableSuppliers.createColumn("ContactName", new VarCharDataType(30));
		tableSuppliers.createColumn("ContactTitle", new VarCharDataType(30));
		tableSuppliers.createColumn("Address", new VarCharDataType(60));
		tableSuppliers.createColumn("City", new VarCharDataType(15));
		tableSuppliers.createColumn("Region", new VarCharDataType(15));
		tableSuppliers.createColumn("PostalCode", new VarCharDataType(10));
		tableSuppliers.createColumn("Country", new VarCharDataType(15));
		tableSuppliers.createColumn("Phone", new VarCharDataType(24));
		tableSuppliers.createColumn("Fax", new VarCharDataType(24));
		tableSuppliers.createColumn("HomePage", new TextDataType());
		this.createPrimaryKeyConstraint("pk_suppliers", tableSuppliers, tableSuppliers.getColumn("SupplierID"));
		this.createNotNullConstraint(tableSuppliers, tableSuppliers.getColumn("SupplierID"));
		this.createNotNullConstraint(tableSuppliers, tableSuppliers.getColumn("CompanyName"));

		Table tableTerritories = this.createTable("territories");
		tableTerritories.createColumn("TerritoryID", new VarCharDataType(20));
		tableTerritories.createColumn("TerritoryDescription", new SingleCharDataType());
		tableTerritories.createColumn("RegionID", new SmallIntDataType());
		this.createPrimaryKeyConstraint("pk_territories", tableTerritories, tableTerritories.getColumn("TerritoryID"));
		this.createNotNullConstraint(tableTerritories, tableTerritories.getColumn("TerritoryID"));
		this.createNotNullConstraint(tableTerritories, tableTerritories.getColumn("TerritoryDescription"));
		this.createNotNullConstraint(tableTerritories, tableTerritories.getColumn("RegionID"));

		Table tableUsstates = this.createTable("usstates");
		tableUsstates.createColumn("StateID", new SmallIntDataType());
		tableUsstates.createColumn("StateName", new VarCharDataType(100));
		tableUsstates.createColumn("StateAbbr", new VarCharDataType(2));
		tableUsstates.createColumn("StateRegion", new VarCharDataType(50));
		this.createNotNullConstraint(tableUsstates, tableUsstates.getColumn("StateID"));
	}
}

