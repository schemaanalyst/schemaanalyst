package parsedcasestudy;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.BooleanDataType;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/*
 * BookTown schema.
 * Java code originally generated: 2013/07/11 14:01:14
 *
 */

@SuppressWarnings("serial")
public class BookTown extends Schema {

	public BookTown() {
		super("BookTown");

		Table tableBooks = this.createTable("books");
		tableBooks.addColumn("id", new IntDataType());
		tableBooks.addColumn("title", new VarCharDataType(100));
		tableBooks.addColumn("author_id", new IntDataType());
		tableBooks.addColumn("subject_id", new IntDataType());
		tableBooks.setPrimaryKeyConstraint("books_id_pkey", tableBooks.getColumn("id"));
		tableBooks.addNotNullConstraint(tableBooks.getColumn("id"));
		tableBooks.addNotNullConstraint(tableBooks.getColumn("title"));

		Table tablePublishers = this.createTable("publishers");
		tablePublishers.addColumn("id", new IntDataType());
		tablePublishers.addColumn("name", new VarCharDataType(100));
		tablePublishers.addColumn("address", new VarCharDataType(100));
		tablePublishers.setPrimaryKeyConstraint("publishers_pkey", tablePublishers.getColumn("id"));
		tablePublishers.addNotNullConstraint(tablePublishers.getColumn("id"));

		Table tableAuthors = this.createTable("authors");
		tableAuthors.addColumn("id", new IntDataType());
		tableAuthors.addColumn("last_name", new VarCharDataType(100));
		tableAuthors.addColumn("first_name", new VarCharDataType(100));
		tableAuthors.setPrimaryKeyConstraint("authors_pkey", tableAuthors.getColumn("id"));
		tableAuthors.addNotNullConstraint(tableAuthors.getColumn("id"));

		Table tableStates = this.createTable("states");
		tableStates.addColumn("id", new IntDataType());
		tableStates.addColumn("name", new VarCharDataType(100));
		tableStates.addColumn("abbreviation", new CharDataType(2));
		tableStates.setPrimaryKeyConstraint("state_pkey", tableStates.getColumn("id"));
		tableStates.addNotNullConstraint(tableStates.getColumn("id"));

		Table tableMyList = this.createTable("my_list");
		tableMyList.addColumn("todos", new VarCharDataType(100));

		Table tableStock = this.createTable("stock");
		tableStock.addColumn("isbn", new VarCharDataType(100));
		tableStock.addColumn("cost", new NumericDataType(5, 2));
		tableStock.addColumn("retail", new NumericDataType(5, 2));
		tableStock.addColumn("stock", new IntDataType());
		tableStock.setPrimaryKeyConstraint("stock_pkey", tableStock.getColumn("isbn"));
		tableStock.addNotNullConstraint(tableStock.getColumn("isbn"));

		Table tableNumericValues = this.createTable("numeric_values");
		tableNumericValues.addColumn("num", new NumericDataType(30, 6));

		Table tableDailyInventory = this.createTable("daily_inventory");
		tableDailyInventory.addColumn("isbn", new VarCharDataType(100));
		tableDailyInventory.addColumn("is_stocked", new BooleanDataType());

		Table tableMoneyExample = this.createTable("money_example");
		tableMoneyExample.addColumn("money_cash", new NumericDataType(6, 2));
		tableMoneyExample.addColumn("numeric_cash", new NumericDataType(6, 2));

		Table tableShipments = this.createTable("shipments");
		tableShipments.addColumn("id", new IntDataType());
		tableShipments.addColumn("customer_id", new IntDataType());
		tableShipments.addColumn("isbn", new VarCharDataType(100));
		tableShipments.addColumn("ship_date", new TimestampDataType());
		tableShipments.addNotNullConstraint(tableShipments.getColumn("id"));

		Table tableCustomers = this.createTable("customers");
		tableCustomers.addColumn("id", new IntDataType());
		tableCustomers.addColumn("last_name", new VarCharDataType(100));
		tableCustomers.addColumn("first_name", new VarCharDataType(100));
		tableCustomers.setPrimaryKeyConstraint("customers_pkey", tableCustomers.getColumn("id"));
		tableCustomers.addNotNullConstraint(tableCustomers.getColumn("id"));

		Table tableBookQueue = this.createTable("book_queue");
		tableBookQueue.addColumn("title", new VarCharDataType(100));
		tableBookQueue.addColumn("author_id", new IntDataType());
		tableBookQueue.addColumn("subject_id", new IntDataType());
		tableBookQueue.addColumn("approved", new BooleanDataType());
		tableBookQueue.addNotNullConstraint(tableBookQueue.getColumn("title"));

		Table tableStockBackup = this.createTable("stock_backup");
		tableStockBackup.addColumn("isbn", new VarCharDataType(100));
		tableStockBackup.addColumn("cost", new NumericDataType(5, 2));
		tableStockBackup.addColumn("retail", new NumericDataType(5, 2));
		tableStockBackup.addColumn("stock", new IntDataType());

		Table tableFavoriteBooks = this.createTable("favorite_books");
		tableFavoriteBooks.addColumn("employee_id", new IntDataType());
		tableFavoriteBooks.addColumn("books", new VarCharDataType(100));

		Table tableEmployees = this.createTable("employees");
		tableEmployees.addColumn("id", new IntDataType());
		tableEmployees.addColumn("last_name", new VarCharDataType(100));
		tableEmployees.addColumn("first_name", new VarCharDataType(100));
		tableEmployees.setPrimaryKeyConstraint(tableEmployees.getColumn("id"));
		tableEmployees.addNotNullConstraint(tableEmployees.getColumn("id"));
		tableEmployees.addNotNullConstraint(tableEmployees.getColumn("last_name"));
		tableEmployees.addCheckConstraint("employees_id", new ParenthesisedExpression(new RelationalExpression(new ColumnExpression(tableEmployees.getColumn("id")), RelationalOperator.GREATER, new ConstantExpression(new NumericValue(100)))));

		Table tableEditions = this.createTable("editions");
		tableEditions.addColumn("isbn", new VarCharDataType(100));
		tableEditions.addColumn("book_id", new IntDataType());
		tableEditions.addColumn("edition", new IntDataType());
		tableEditions.addColumn("publisher_id", new IntDataType());
		tableEditions.addColumn("publication", new DateDataType());
		tableEditions.addColumn("type", new CharDataType(1));
		tableEditions.setPrimaryKeyConstraint(tableEditions.getColumn("isbn"));
		tableEditions.addNotNullConstraint(tableEditions.getColumn("isbn"));
		tableEditions.addCheckConstraint("integrity", new ParenthesisedExpression(new AndExpression(new ParenthesisedExpression(new NullExpression(new ColumnExpression(tableEditions.getColumn("book_id")), true)), new ParenthesisedExpression(new NullExpression(new ColumnExpression(tableEditions.getColumn("edition")), true)))));

		Table tableDistinguishedAuthors = this.createTable("distinguished_authors");
		tableDistinguishedAuthors.addColumn("id", new IntDataType());
		tableDistinguishedAuthors.addColumn("last_name", new VarCharDataType(100));
		tableDistinguishedAuthors.addColumn("first_name", new VarCharDataType(100));
		tableDistinguishedAuthors.addColumn("award", new VarCharDataType(100));
		tableDistinguishedAuthors.setPrimaryKeyConstraint("authors_pkey", tableDistinguishedAuthors.getColumn("id"));
		tableDistinguishedAuthors.addNotNullConstraint(tableDistinguishedAuthors.getColumn("id"));

		Table tableVarchar100Sorting = this.createTable("varchar(100)_sorting");
		tableVarchar100Sorting.addColumn("letter", new CharDataType(1));

		Table tableSubjects = this.createTable("subjects");
		tableSubjects.addColumn("id", new IntDataType());
		tableSubjects.addColumn("subject", new VarCharDataType(100));
		tableSubjects.addColumn("location", new VarCharDataType(100));
		tableSubjects.setPrimaryKeyConstraint("subjects_pkey", tableSubjects.getColumn("id"));
		tableSubjects.addNotNullConstraint(tableSubjects.getColumn("id"));

		Table tableAlternateStock = this.createTable("alternate_stock");
		tableAlternateStock.addColumn("isbn", new VarCharDataType(100));
		tableAlternateStock.addColumn("cost", new NumericDataType(5, 2));
		tableAlternateStock.addColumn("retail", new NumericDataType(5, 2));
		tableAlternateStock.addColumn("stock", new IntDataType());

		Table tableBookBackup = this.createTable("book_backup");
		tableBookBackup.addColumn("id", new IntDataType());
		tableBookBackup.addColumn("title", new VarCharDataType(100));
		tableBookBackup.addColumn("author_id", new IntDataType());
		tableBookBackup.addColumn("subject_id", new IntDataType());

		Table tableSchedules = this.createTable("schedules");
		tableSchedules.addColumn("employee_id", new IntDataType());
		tableSchedules.addColumn("schedule", new VarCharDataType(100));
		tableSchedules.setPrimaryKeyConstraint("schedules_pkey", tableSchedules.getColumn("employee_id"));
		tableSchedules.addNotNullConstraint(tableSchedules.getColumn("employee_id"));
	}
}

