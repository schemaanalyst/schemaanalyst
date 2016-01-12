package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.*;

/*
 * Test schema.
 */

@SuppressWarnings("serial")
public class Test extends Schema {

	// Minimal not-working example
	// Fails with exception:
	// "org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.CheckerException: ''(type + StringValue) is of a different type to 0(type + NumericValue)"
	public Test() {
		super("Test");
		Table tableA = this.createTable("TableA");
		tableA.createColumn("a", new IntDataType());
		this.createPrimaryKeyConstraint(tableA, tableA.getColumn("a"));
		Table tableB = this.createTable("TableB");
		tableB.createColumn("x", new IntDataType());
		tableB.createColumn("y", new VarCharDataType(10));
		this.createForeignKeyConstraint(tableB, tableB.getColumn("x"), tableA, tableA.getColumn("a"));
	}

	// Replacing type of column y with IntDataType
	// Successful execution
	// public Test() {
	// 	super("Test");
	// 	Table tableA = this.createTable("TableA");
	// 	tableA.createColumn("a", new IntDataType());
	// 	this.createPrimaryKeyConstraint(tableA, tableA.getColumn("a"));
	// 	Table tableB = this.createTable("TableB");
	// 	tableB.createColumn("x", new IntDataType());
	// 	tableB.createColumn("y", new IntDataType()); // VarCharDataType -> IntDataType
	// 	this.createForeignKeyConstraint(tableB, tableB.getColumn("x"), tableA, tableA.getColumn("a"));
	// }

	// Replacing type of column y with CharDataType
	// Fails with exception:
	// "org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.CheckerException: ''(type + StringValue) is of a different type to 0(type + NumericValue)"
	// public Test() {
	// 	super("Test");
	// 	Table tableA = this.createTable("TableA");
	// 	tableA.createColumn("a", new IntDataType());
	// 	this.createPrimaryKeyConstraint(tableA, tableA.getColumn("a"));
	// 	Table tableB = this.createTable("TableB");
	// 	tableB.createColumn("x", new IntDataType());
	// 	tableB.createColumn("y", new CharDataType(5)); // VarCharDataType -> CharDataType
	// 	this.createForeignKeyConstraint(tableB, tableB.getColumn("x"), tableA, tableA.getColumn("a"));
	// }

	// Replacing type of column y with DateDataType
	// Fails with exception:
	// "org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.CheckerException: '2000-01-01'(type + DateValue) is of a different type to 0(type + NumericValue)"
	// public Test() {
	// 	super("Test");
	// 	Table tableA = this.createTable("TableA");
	// 	tableA.createColumn("a", new IntDataType());
	// 	this.createPrimaryKeyConstraint(tableA, tableA.getColumn("a"));
	// 	Table tableB = this.createTable("TableB");
	// 	tableB.createColumn("x", new IntDataType());
	// 	tableB.createColumn("y", new DateDataType()); // VarCharDataType -> DateDataType
	// 	this.createForeignKeyConstraint(tableB, tableB.getColumn("x"), tableA, tableA.getColumn("a"));
	// }

	// Replacing a and x types with VarCharDataType (needed for FK type alignment
	// Successful execution
	// public Test() {
	// 	super("Test");
	// 	Table tableA = this.createTable("TableA");
	// 	tableA.createColumn("a", new VarCharDataType(10));
	// 	this.createPrimaryKeyConstraint(tableA, tableA.getColumn("a"));
	// 	Table tableB = this.createTable("TableB");
	// 	tableB.createColumn("x", new VarCharDataType(10));
	// 	tableB.createColumn("y", new VarCharDataType(10));
	// 	this.createForeignKeyConstraint(tableB, tableB.getColumn("x"), tableA, tableA.getColumn("a"));
	// }

	// Removing column y
	// Successful execution
	// public Test() {
	// 	super("Test");
	// 	Table tableA = this.createTable("TableA");
	// 	tableA.createColumn("a", new IntDataType());
	// 	this.createPrimaryKeyConstraint(tableA, tableA.getColumn("a"));
	// 	Table tableB = this.createTable("TableB");
	// 	tableB.createColumn("x", new IntDataType());
	// 	// Removed column y
	// 	this.createForeignKeyConstraint(tableB, tableB.getColumn("x"), tableA, tableA.getColumn("a"));
	// }

	// Removing FK constraint
	// Successful execution
	// public Test() {
	// 	super("Test");
	// 	Table tableA = this.createTable("TableA");
	// 	tableA.createColumn("a", new IntDataType());
	// 	this.createPrimaryKeyConstraint(tableA, tableA.getColumn("a"));
	// 	Table tableB = this.createTable("TableB");
	// 	tableB.createColumn("x", new IntDataType());
	// 	tableB.createColumn("y", new VarCharDataType(10));
	// 	// Removed FK constraint
	// }

	// Removing PK constraint (not certain this is a problem)
	// Fails with exception:
	// "org.schemaanalyst.testgeneration.TestCaseExecutionException: INSERT statement for setting database state "INSERT INTO "TableB"("x", "y") VALUES (0, '')" should affect exactly one row, was 0""
	// public Test() {
	// 	super("Test");
	// 	Table tableA = this.createTable("TableA");
	// 	tableA.createColumn("a", new IntDataType());
	// 	// Removed PK constraint
	// 	Table tableB = this.createTable("TableB");
	// 	tableB.createColumn("x", new IntDataType());
	// 	tableB.createColumn("y", new VarCharDataType(10));
	// 	this.createForeignKeyConstraint(tableB, tableB.getColumn("x"), tableA, tableA.getColumn("a"));
	// }
}

