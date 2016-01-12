package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.*;
import org.schemaanalyst.sqlrepresentation.expression.*;

/*
 * BookTown schema.
 * Java code originally generated: 2014/03/03 11:22:35
 *
 */

@SuppressWarnings("serial")
public class BookTown extends Schema {

	public BookTown() {
		super("BookTown");

		Table tableBooks = this.createTable("books");
		tableBooks.createColumn("id", new IntDataType());
		tableBooks.createColumn("title", new VarCharDataType(100));
		tableBooks.createColumn("author_id", new IntDataType());
		tableBooks.createColumn("subject_id", new IntDataType());
		this.createPrimaryKeyConstraint("books_id_pkey", tableBooks, tableBooks.getColumn("id"));
		this.createNotNullConstraint(tableBooks, tableBooks.getColumn("id"));
		this.createNotNullConstraint(tableBooks, tableBooks.getColumn("title"));

		Table tablePublishers = this.createTable("publishers");
		tablePublishers.createColumn("id", new IntDataType());
		tablePublishers.createColumn("name", new VarCharDataType(100));
		tablePublishers.createColumn("address", new VarCharDataType(100));
		this.createPrimaryKeyConstraint("publishers_pkey", tablePublishers, tablePublishers.getColumn("id"));
		this.createNotNullConstraint(tablePublishers, tablePublishers.getColumn("id"));

		Table tableAuthors = this.createTable("authors");
		tableAuthors.createColumn("id", new IntDataType());
		tableAuthors.createColumn("last_name", new VarCharDataType(100));
		tableAuthors.createColumn("first_name", new VarCharDataType(100));
		this.createPrimaryKeyConstraint("authors_pkey", tableAuthors, tableAuthors.getColumn("id"));
		this.createNotNullConstraint(tableAuthors, tableAuthors.getColumn("id"));

		Table tableStates = this.createTable("states");
		tableStates.createColumn("id", new IntDataType());
		tableStates.createColumn("name", new VarCharDataType(100));
		tableStates.createColumn("abbreviation", new CharDataType(2));
		this.createPrimaryKeyConstraint("state_pkey", tableStates, tableStates.getColumn("id"));
		this.createNotNullConstraint(tableStates, tableStates.getColumn("id"));

		Table tableMyList = this.createTable("my_list");
		tableMyList.createColumn("todos", new VarCharDataType(100));

		Table tableStock = this.createTable("stock");
		tableStock.createColumn("isbn", new VarCharDataType(100));
		tableStock.createColumn("cost", new NumericDataType(5, 2));
		tableStock.createColumn("retail", new NumericDataType(5, 2));
		tableStock.createColumn("stock", new IntDataType());
		this.createPrimaryKeyConstraint("stock_pkey", tableStock, tableStock.getColumn("isbn"));
		this.createNotNullConstraint(tableStock, tableStock.getColumn("isbn"));

		Table tableNumericValues = this.createTable("numeric_values");
		tableNumericValues.createColumn("num", new NumericDataType(30, 6));

		Table tableDailyInventory = this.createTable("daily_inventory");
		tableDailyInventory.createColumn("isbn", new VarCharDataType(100));
		tableDailyInventory.createColumn("is_stocked", new BooleanDataType());

		Table tableMoneyExample = this.createTable("money_example");
		tableMoneyExample.createColumn("money_cash", new NumericDataType(6, 2));
		tableMoneyExample.createColumn("numeric_cash", new NumericDataType(6, 2));

		Table tableShipments = this.createTable("shipments");
		tableShipments.createColumn("id", new IntDataType());
		tableShipments.createColumn("customer_id", new IntDataType());
		tableShipments.createColumn("isbn", new VarCharDataType(100));
		tableShipments.createColumn("ship_date", new TimestampDataType());
		this.createNotNullConstraint(tableShipments, tableShipments.getColumn("id"));

		Table tableCustomers = this.createTable("customers");
		tableCustomers.createColumn("id", new IntDataType());
		tableCustomers.createColumn("last_name", new VarCharDataType(100));
		tableCustomers.createColumn("first_name", new VarCharDataType(100));
		this.createPrimaryKeyConstraint("customers_pkey", tableCustomers, tableCustomers.getColumn("id"));
		this.createNotNullConstraint(tableCustomers, tableCustomers.getColumn("id"));

		Table tableBookQueue = this.createTable("book_queue");
		tableBookQueue.createColumn("title", new VarCharDataType(100));
		tableBookQueue.createColumn("author_id", new IntDataType());
		tableBookQueue.createColumn("subject_id", new IntDataType());
		tableBookQueue.createColumn("approved", new BooleanDataType());
		this.createNotNullConstraint(tableBookQueue, tableBookQueue.getColumn("title"));

		Table tableStockBackup = this.createTable("stock_backup");
		tableStockBackup.createColumn("isbn", new VarCharDataType(100));
		tableStockBackup.createColumn("cost", new NumericDataType(5, 2));
		tableStockBackup.createColumn("retail", new NumericDataType(5, 2));
		tableStockBackup.createColumn("stock", new IntDataType());

		Table tableFavoriteBooks = this.createTable("favorite_books");
		tableFavoriteBooks.createColumn("employee_id", new IntDataType());
		tableFavoriteBooks.createColumn("books", new VarCharDataType(100));

		Table tableEmployees = this.createTable("employees");
		tableEmployees.createColumn("id", new IntDataType());
		tableEmployees.createColumn("last_name", new VarCharDataType(100));
		tableEmployees.createColumn("first_name", new VarCharDataType(100));
		this.createPrimaryKeyConstraint(tableEmployees, tableEmployees.getColumn("id"));
		this.createCheckConstraint("employees_id", tableEmployees, new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableEmployees, tableEmployees.getColumn("id")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(100)))));
		this.createNotNullConstraint(tableEmployees, tableEmployees.getColumn("id"));
		this.createNotNullConstraint(tableEmployees, tableEmployees.getColumn("last_name"));

		Table tableEditions = this.createTable("editions");
		tableEditions.createColumn("isbn", new VarCharDataType(100));
		tableEditions.createColumn("book_id", new IntDataType());
		tableEditions.createColumn("edition", new IntDataType());
		tableEditions.createColumn("publisher_id", new IntDataType());
		tableEditions.createColumn("publication", new DateDataType());
		tableEditions.createColumn("type", new CharDataType(1));
		this.createPrimaryKeyConstraint(tableEditions, tableEditions.getColumn("isbn"));
		this.createCheckConstraint("integrity", tableEditions, new ParenthesisedExpression(new AndExpression(new ParenthesisedExpression(new NullExpression(new ColumnExpression(tableEditions, tableEditions.getColumn("book_id")), true)), new ParenthesisedExpression(new NullExpression(new ColumnExpression(tableEditions, tableEditions.getColumn("edition")), true)))));
		this.createNotNullConstraint(tableEditions, tableEditions.getColumn("isbn"));

		Table tableDistinguishedAuthors = this.createTable("distinguished_authors");
		tableDistinguishedAuthors.createColumn("id", new IntDataType());
		tableDistinguishedAuthors.createColumn("last_name", new VarCharDataType(100));
		tableDistinguishedAuthors.createColumn("first_name", new VarCharDataType(100));
		tableDistinguishedAuthors.createColumn("award", new VarCharDataType(100));
		this.createPrimaryKeyConstraint("distinguished_authors_pkey", tableDistinguishedAuthors, tableDistinguishedAuthors.getColumn("id"));
		this.createNotNullConstraint(tableDistinguishedAuthors, tableDistinguishedAuthors.getColumn("id"));

		Table tableVarchar100Sorting = this.createTable("varchar(100)_sorting");
		tableVarchar100Sorting.createColumn("letter", new CharDataType(1));

		Table tableSubjects = this.createTable("subjects");
		tableSubjects.createColumn("id", new IntDataType());
		tableSubjects.createColumn("subject", new VarCharDataType(100));
		tableSubjects.createColumn("location", new VarCharDataType(100));
		this.createPrimaryKeyConstraint("subjects_pkey", tableSubjects, tableSubjects.getColumn("id"));
		this.createNotNullConstraint(tableSubjects, tableSubjects.getColumn("id"));

		Table tableAlternateStock = this.createTable("alternate_stock");
		tableAlternateStock.createColumn("isbn", new VarCharDataType(100));
		tableAlternateStock.createColumn("cost", new NumericDataType(5, 2));
		tableAlternateStock.createColumn("retail", new NumericDataType(5, 2));
		tableAlternateStock.createColumn("stock", new IntDataType());

		Table tableBookBackup = this.createTable("book_backup");
		tableBookBackup.createColumn("id", new IntDataType());
		tableBookBackup.createColumn("title", new VarCharDataType(100));
		tableBookBackup.createColumn("author_id", new IntDataType());
		tableBookBackup.createColumn("subject_id", new IntDataType());

		Table tableSchedules = this.createTable("schedules");
		tableSchedules.createColumn("employee_id", new IntDataType());
		tableSchedules.createColumn("schedule", new VarCharDataType(100));
		this.createPrimaryKeyConstraint("schedules_pkey", tableSchedules, tableSchedules.getColumn("employee_id"));
		this.createNotNullConstraint(tableSchedules, tableSchedules.getColumn("employee_id"));
	}
}

