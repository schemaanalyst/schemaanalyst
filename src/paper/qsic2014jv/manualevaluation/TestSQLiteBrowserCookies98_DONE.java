package paper.qsic2014jv.manualevaluation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSQLiteBrowserCookies98_DONE {
	
	private static final int SUCCESS = 0;
	private static final boolean QUIET = false;

	private static final int FIRST_MUTANT_NUMBER = 1;
	private static final int MUTANT_BEING_EVALUATED = 98;
	private static final int LAST_MUTANT_NUMBER = 132;

	private static final String JDBC_CLASS = "org.sqlite.JDBC";
	private static final String CONNECTION_URL = "jdbc:sqlite:BrowserCookies";

	private static Connection connection;
	private static Statement statement;
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName(JDBC_CLASS);
		connection = DriverManager.getConnection(CONNECTION_URL);
		statement = connection.createStatement();

		// enable FOREIGN KEY support
		statement.executeUpdate("PRAGMA foreign_keys = ON");
	}
	
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"cookies\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"places\"");
	}
	
	public void createOriginalSchema() throws SQLException {
		dropTables();
		
		statement.executeUpdate(
			"CREATE TABLE \"places\" (" + 
			"    \"host\"    TEXT    NOT NULL," + 
			"    \"path\"    TEXT    NOT NULL," + 
			"    \"title\"    TEXT," + 
			"    \"visit_count\"    INT," + 
			"    \"fav_icon_url\"    TEXT," + 
			"    PRIMARY KEY (\"host\", \"path\")" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"cookies\" (" + 
			"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
			"    \"name\"    TEXT    NOT NULL," + 
			"    \"value\"    TEXT," + 
			"    \"expiry\"    INT," + 
			"    \"last_accessed\"    INT," + 
			"    \"creation_time\"    INT," + 
			"    \"host\"    TEXT," + 
			"    \"path\"    TEXT," + 
			"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
			"    UNIQUE (\"name\", \"host\", \"path\")," + 
			"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
			"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
			")");
	}
	
	public void createMutantSchema() throws SQLException {
		dropTables();
		
		//98
		//UCColumnA
		//Added UNIQUE to column path in table places
		statement.executeUpdate(
			"CREATE TABLE \"places\" (" + 
			"    \"host\"    TEXT    NOT NULL," + 
			"    \"path\"    TEXT    UNIQUE    NOT NULL," + 
			"    \"title\"    TEXT," + 
			"    \"visit_count\"    INT," + 
			"    \"fav_icon_url\"    TEXT," + 
			"    PRIMARY KEY (\"host\", \"path\")" + 
			")");
		statement.executeUpdate(
			"CREATE TABLE \"cookies\" (" + 
			"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
			"    \"name\"    TEXT    NOT NULL," + 
			"    \"value\"    TEXT," + 
			"    \"expiry\"    INT," + 
			"    \"last_accessed\"    INT," + 
			"    \"creation_time\"    INT," + 
			"    \"host\"    TEXT," + 
			"    \"path\"    TEXT," + 
			"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
			"    UNIQUE (\"name\", \"host\", \"path\")," + 
			"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
			"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
			")");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	String statement1 = "INSERT INTO places VALUES('A', 'B', 'C', 1, 'D')";
	String statement2 = "INSERT INTO places VALUES('a', 'B', 'c', 2, 'd')";
	String statement3 = "INSERT INTO cookies VALUES(1, 'x', 'y', 0, 1, 1, 'A', 'B')";

	@Test
	public void notImpaired() throws SQLException {
		assertTrue(insertToMutant(statement1, statement3));
	}

	@Test
	public void notEquivalent() throws SQLException {
		assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2));
	}

	@Test
	public void notRedundant() throws SQLException {
		assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement1, statement2, statement3), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

	
	public void createOtherMutantSchema(int number) throws SQLException {
		dropTables();
		
		if (number == 1) {
			//1
			//CCNullifier
			//ElementNullifier with ChainedSupplier with CheckConstraintSupplier and CheckExpressionSupplier - set expiry = 0 OR expiry > last_accessed to null
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 2) {
			//2
			//CCNullifier
			//ElementNullifier with ChainedSupplier with CheckConstraintSupplier and CheckExpressionSupplier - set last_accessed >= creation_time to null
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")" + 
				")");
		} else if (number == 3) {
			//3
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - = replaced with !=
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" != 0 OR \"expiry\" > \"last_accessed\")" + 
				")");
		} else if (number == 4) {
			//4
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - = replaced with >
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" > 0 OR \"expiry\" > \"last_accessed\")" + 
				")");
		} else if (number == 5) {
			//5
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - = replaced with <
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" < 0 OR \"expiry\" > \"last_accessed\")" + 
				")");
		} else if (number == 6) {
			//6
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - = replaced with >=
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" >= 0 OR \"expiry\" > \"last_accessed\")" + 
				")");
		} else if (number == 7) {
			//7
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - = replaced with <=
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" <= 0 OR \"expiry\" > \"last_accessed\")" + 
				")");
		} else if (number == 8) {
			//8
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - > replaced with =
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" = \"last_accessed\")" + 
				")");
		} else if (number == 9) {
			//9
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - > replaced with !=
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" != \"last_accessed\")" + 
				")");
		} else if (number == 10) {
			//10
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - > replaced with <
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" < \"last_accessed\")" + 
				")");
		} else if (number == 11) {
			//11
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - > replaced with >=
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" >= \"last_accessed\")" + 
				")");
		} else if (number == 12) {
			//12
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - > replaced with <=
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" <= \"last_accessed\")" + 
				")");
		} else if (number == 13) {
			//13
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - >= replaced with =
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" = \"creation_time\")" + 
				")");
		} else if (number == 14) {
			//14
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - >= replaced with !=
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" != \"creation_time\")" + 
				")");
		} else if (number == 15) {
			//15
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - >= replaced with >
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" > \"creation_time\")" + 
				")");
		} else if (number == 16) {
			//16
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - >= replaced with <
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" < \"creation_time\")" + 
				")");
		} else if (number == 17) {
			//17
			//CCRelationalExpressionOperatorE
			//RelationalOperatorExchanger with ChainedSupplier with CheckConstraintSupplier and ChainedSupplier with CheckExpressionSupplier and ChainedSupplier with RelationalExpressionSupplier and RelationalExpressionOperatorSupplier - >= replaced with <=
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" <= \"creation_time\")" + 
				")");
		} else if (number == 18) {
			//18
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(id, title)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"id\") REFERENCES \"places\" (\"host\", \"path\", \"title\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 19) {
			//19
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(id, visit_count)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"id\") REFERENCES \"places\" (\"host\", \"path\", \"visit_count\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 20) {
			//20
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(id, fav_icon_url)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"id\") REFERENCES \"places\" (\"host\", \"path\", \"fav_icon_url\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 21) {
			//21
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(name, title)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"name\") REFERENCES \"places\" (\"host\", \"path\", \"title\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 22) {
			//22
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(name, visit_count)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"name\") REFERENCES \"places\" (\"host\", \"path\", \"visit_count\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 23) {
			//23
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(name, fav_icon_url)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"name\") REFERENCES \"places\" (\"host\", \"path\", \"fav_icon_url\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 24) {
			//24
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(value, title)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"value\") REFERENCES \"places\" (\"host\", \"path\", \"title\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 25) {
			//25
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(value, visit_count)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"value\") REFERENCES \"places\" (\"host\", \"path\", \"visit_count\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 26) {
			//26
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(value, fav_icon_url)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"value\") REFERENCES \"places\" (\"host\", \"path\", \"fav_icon_url\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 27) {
			//27
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(expiry, title)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"expiry\") REFERENCES \"places\" (\"host\", \"path\", \"title\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 28) {
			//28
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(expiry, visit_count)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"expiry\") REFERENCES \"places\" (\"host\", \"path\", \"visit_count\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 29) {
			//29
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(expiry, fav_icon_url)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"expiry\") REFERENCES \"places\" (\"host\", \"path\", \"fav_icon_url\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 30) {
			//30
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(last_accessed, title)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"last_accessed\") REFERENCES \"places\" (\"host\", \"path\", \"title\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 31) {
			//31
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(last_accessed, visit_count)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"last_accessed\") REFERENCES \"places\" (\"host\", \"path\", \"visit_count\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 32) {
			//32
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(last_accessed, fav_icon_url)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"last_accessed\") REFERENCES \"places\" (\"host\", \"path\", \"fav_icon_url\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 33) {
			//33
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(creation_time, title)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"creation_time\") REFERENCES \"places\" (\"host\", \"path\", \"title\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 34) {
			//34
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(creation_time, visit_count)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"creation_time\") REFERENCES \"places\" (\"host\", \"path\", \"visit_count\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 35) {
			//35
			//FKCColumnPairA
			//ListElementAdder with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairsWithAlternativesSupplier - Added Pair(creation_time, fav_icon_url)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\", \"creation_time\") REFERENCES \"places\" (\"host\", \"path\", \"fav_icon_url\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 36) {
			//36
			//FKCColumnPairR
			//ListElementRemover with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnSupplier - Removed Pair(host, host)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT     REFERENCES \"places\" (\"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 37) {
			//37
			//FKCColumnPairR
			//ListElementRemover with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnSupplier - Removed Pair(path, path)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT     REFERENCES \"places\" (\"host\")," + 
				"    \"path\"    TEXT," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 38) {
			//38
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(id, host)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"path\", \"id\") REFERENCES \"places\" (\"path\", \"host\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 39) {
			//39
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(name, host)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"path\", \"name\") REFERENCES \"places\" (\"path\", \"host\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 40) {
			//40
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(value, host)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"path\", \"value\") REFERENCES \"places\" (\"path\", \"host\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 41) {
			//41
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(expiry, host)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"path\", \"expiry\") REFERENCES \"places\" (\"path\", \"host\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 42) {
			//42
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(last_accessed, host)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"path\", \"last_accessed\") REFERENCES \"places\" (\"path\", \"host\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 43) {
			//43
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(creation_time, host)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"path\", \"creation_time\") REFERENCES \"places\" (\"path\", \"host\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 44) {
			//44
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(host, path)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT     REFERENCES \"places\" (\"path\")," + 
				"    \"path\"    TEXT," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 45) {
			//45
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(host, title)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"path\", \"host\") REFERENCES \"places\" (\"path\", \"title\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 46) {
			//46
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(host, visit_count)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"path\", \"host\") REFERENCES \"places\" (\"path\", \"visit_count\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 47) {
			//47
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(host, fav_icon_url)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"path\", \"host\") REFERENCES \"places\" (\"path\", \"fav_icon_url\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 48) {
			//48
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(host, host) with Pair(path, host)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT     REFERENCES \"places\" (\"host\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 49) {
			//49
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(path, path) with Pair(id, path)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"id\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 50) {
			//50
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(path, path) with Pair(name, path)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"name\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 51) {
			//51
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(path, path) with Pair(value, path)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"value\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 52) {
			//52
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(path, path) with Pair(expiry, path)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"expiry\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 53) {
			//53
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(path, path) with Pair(last_accessed, path)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"last_accessed\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 54) {
			//54
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(path, path) with Pair(creation_time, path)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"creation_time\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 55) {
			//55
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(path, path) with Pair(path, title)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"title\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 56) {
			//56
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(path, path) with Pair(path, visit_count)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"visit_count\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 57) {
			//57
			//FKCColumnPairE
			//ListElementExchanger with ChainedSupplier with ForeignKeyConstraintSupplier and ForeignKeyColumnPairWithAlternativesSupplier - Exchanged Pair(path, path) with Pair(path, fav_icon_url)
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"fav_icon_url\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 58) {
			//58
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added title
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\", \"title\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 59) {
			//59
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added visit_count
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\", \"visit_count\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 60) {
			//60
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added fav_icon_url
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\", \"fav_icon_url\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 61) {
			//61
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added name
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    PRIMARY KEY (\"id\", \"name\")," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 62) {
			//62
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added value
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    PRIMARY KEY (\"id\", \"value\")," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 63) {
			//63
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added expiry
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    PRIMARY KEY (\"id\", \"expiry\")," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 64) {
			//64
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added last_accessed
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    PRIMARY KEY (\"id\", \"last_accessed\")," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 65) {
			//65
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added creation_time
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    PRIMARY KEY (\"id\", \"creation_time\")," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 66) {
			//66
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added host
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    PRIMARY KEY (\"id\", \"host\")," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 67) {
			//67
			//PKCColumnA
			//ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added path
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    PRIMARY KEY (\"id\", \"path\")," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 68) {
			//68
			//PKCColumnR
			//ListElementRemover with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnSupplier - Removed host
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    PRIMARY KEY    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 69) {
			//69
			//PKCColumnR
			//ListElementRemover with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnSupplier - Removed path
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    PRIMARY KEY    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 70) {
			//70
			//PKCColumnR
			//ListElementRemover with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnSupplier - Removed id
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 71) {
			//71
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged host with title
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"title\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 72) {
			//72
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged host with visit_count
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"visit_count\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 73) {
			//73
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged host with fav_icon_url
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"fav_icon_url\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 74) {
			//74
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged path with title
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"title\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 75) {
			//75
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged path with visit_count
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"visit_count\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 76) {
			//76
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged path with fav_icon_url
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"fav_icon_url\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 77) {
			//77
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with name
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    PRIMARY KEY    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 78) {
			//78
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with value
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT    PRIMARY KEY," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 79) {
			//79
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with expiry
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT    PRIMARY KEY," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 80) {
			//80
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with last_accessed
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT    PRIMARY KEY," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 81) {
			//81
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with creation_time
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT    PRIMARY KEY," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 82) {
			//82
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with host
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT    PRIMARY KEY," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 83) {
			//83
			//PKCColumnE
			//ListElementExchanger with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Exchanged id with path
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT    PRIMARY KEY," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 84) {
			//84
			//NNCA
			//Added NOT NULL to column title in table places
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT    NOT NULL," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 85) {
			//85
			//NNCA
			//Added NOT NULL to column visit_count in table places
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT    NOT NULL," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 86) {
			//86
			//NNCA
			//Added NOT NULL to column fav_icon_url in table places
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT    NOT NULL," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 87) {
			//87
			//NNCA
			//Added NOT NULL to column value in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT    NOT NULL," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 88) {
			//88
			//NNCA
			//Added NOT NULL to column expiry in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT    NOT NULL," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 89) {
			//89
			//NNCA
			//Added NOT NULL to column last_accessed in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT    NOT NULL," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 90) {
			//90
			//NNCA
			//Added NOT NULL to column creation_time in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT    NOT NULL," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 91) {
			//91
			//NNCA
			//Added NOT NULL to column host in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 92) {
			//92
			//NNCA
			//Added NOT NULL to column path in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 93) {
			//93
			//NNCR
			//Removed NOT NULL to column host in table places
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 94) {
			//94
			//NNCR
			//Removed NOT NULL to column path in table places
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 95) {
			//95
			//NNCR
			//Removed NOT NULL to column id in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 96) {
			//96
			//NNCR
			//Removed NOT NULL to column name in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 97) {
			//97
			//UCColumnA
			//Added UNIQUE to column host in table places
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    UNIQUE    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 99) {
			//99
			//UCColumnA
			//Added UNIQUE to column title in table places
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT    UNIQUE," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 100) {
			//100
			//UCColumnA
			//Added UNIQUE to column visit_count in table places
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT    UNIQUE," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 101) {
			//101
			//UCColumnA
			//Added UNIQUE to column fav_icon_url in table places
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT    UNIQUE," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 102) {
			//102
			//UCColumnA
			//Added UNIQUE to column id in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    UNIQUE    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 103) {
			//103
			//UCColumnA
			//Added UNIQUE to column name in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    UNIQUE    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 104) {
			//104
			//UCColumnA
			//Added UNIQUE to column value in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT    UNIQUE," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 105) {
			//105
			//UCColumnA
			//Added UNIQUE to column expiry in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT    UNIQUE," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 106) {
			//106
			//UCColumnA
			//Added UNIQUE to column last_accessed in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT    UNIQUE," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 107) {
			//107
			//UCColumnA
			//Added UNIQUE to column creation_time in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT    UNIQUE," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 108) {
			//108
			//UCColumnA
			//Added UNIQUE to column host in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT    UNIQUE," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 109) {
			//109
			//UCColumnA
			//Added UNIQUE to column path in table cookies
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT    UNIQUE," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 110) {
			//110
			//UCColumnA
			//ListElementAdder with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Added id
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\", \"id\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 111) {
			//111
			//UCColumnA
			//ListElementAdder with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Added value
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\", \"value\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 112) {
			//112
			//UCColumnA
			//ListElementAdder with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Added expiry
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\", \"expiry\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 113) {
			//113
			//UCColumnA
			//ListElementAdder with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Added last_accessed
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\", \"last_accessed\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 114) {
			//114
			//UCColumnA
			//ListElementAdder with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Added creation_time
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"path\", \"creation_time\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 115) {
			//115
			//UCColumnR
			//ListElementRemover with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnSupplier - Removed name
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 116) {
			//116
			//UCColumnR
			//ListElementRemover with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnSupplier - Removed host
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 117) {
			//117
			//UCColumnR
			//ListElementRemover with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnSupplier - Removed path
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 118) {
			//118
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged name with id
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"id\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 119) {
			//119
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged name with value
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"value\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 120) {
			//120
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged name with expiry
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"expiry\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 121) {
			//121
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged name with last_accessed
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"last_accessed\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 122) {
			//122
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged name with creation_time
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"creation_time\", \"host\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 123) {
			//123
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged host with id
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"id\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 124) {
			//124
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged host with value
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"value\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 125) {
			//125
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged host with expiry
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"expiry\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 126) {
			//126
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged host with last_accessed
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"last_accessed\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 127) {
			//127
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged host with creation_time
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"creation_time\", \"path\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 128) {
			//128
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged path with id
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"id\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 129) {
			//129
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged path with value
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"value\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 130) {
			//130
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged path with expiry
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"expiry\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 131) {
			//131
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged path with last_accessed
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"last_accessed\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else if (number == 132) {
			//132
			//UCColumnE
			//ListElementExchanger with ChainedSupplier with UniqueConstraintSupplier and UniqueColumnsWithAlternativesSupplier - Exchanged path with creation_time
			statement.executeUpdate(
				"CREATE TABLE \"places\" (" + 
				"    \"host\"    TEXT    NOT NULL," + 
				"    \"path\"    TEXT    NOT NULL," + 
				"    \"title\"    TEXT," + 
				"    \"visit_count\"    INT," + 
				"    \"fav_icon_url\"    TEXT," + 
				"    PRIMARY KEY (\"host\", \"path\")" + 
				")");
			statement.executeUpdate(
				"CREATE TABLE \"cookies\" (" + 
				"    \"id\"    INT    PRIMARY KEY    NOT NULL," + 
				"    \"name\"    TEXT    NOT NULL," + 
				"    \"value\"    TEXT," + 
				"    \"expiry\"    INT," + 
				"    \"last_accessed\"    INT," + 
				"    \"creation_time\"    INT," + 
				"    \"host\"    TEXT," + 
				"    \"path\"    TEXT," + 
				"    FOREIGN KEY (\"host\", \"path\") REFERENCES \"places\" (\"host\", \"path\")," + 
				"    UNIQUE (\"name\", \"host\", \"creation_time\")," + 
				"    CHECK (\"expiry\" = 0 OR \"expiry\" > \"last_accessed\")," + 
				"    CHECK (\"last_accessed\" >= \"creation_time\")" + 
				")");
		} else {
			fail("No such mutant number -- " + number);
		}
	}
	public boolean doInsert(String insertStatement) {
		try {
			statement.executeUpdate(insertStatement);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean insertToMutant(String... insertStatements) throws SQLException {
	    createMutantSchema();
	    for (String insertStatement : insertStatements) {
	        if (!doInsert(insertStatement)) {
	            return false;
	        }
	    }
	    return true;
	}

	public boolean originalAndMutantHaveDifferentBehavior(String... insertStatements) throws SQLException {
	    List<Boolean> originalSchemaResults = new ArrayList<>();
	    List<Boolean> mutantSchemaResults = new ArrayList<>();

	    createOriginalSchema();
	    for (String insertStatement : insertStatements) {
	        originalSchemaResults.add(doInsert(insertStatement));
	    }

	    createMutantSchema();
	    for (String insertStatement : insertStatements) {
	        mutantSchemaResults.add(doInsert(insertStatement));
	    }

	    if (!QUIET) {
	        System.out.println("Orig/mutant: " + originalSchemaResults + "/" + mutantSchemaResults);
	    }

	    for (int i=0; i < insertStatements.length; i++) {
	        if (originalSchemaResults.get(i) != mutantSchemaResults.get(i)) {
	            return true;
	        }
	    }

	    return false;
	}

	public int mutantAndOtherMutantsHaveDifferentBehavior(String... insertStatements) throws SQLException {
	    return mutantAndOtherMutantsHaveDifferentBehavior(FIRST_MUTANT_NUMBER, LAST_MUTANT_NUMBER, insertStatements);
	}

	public int mutantAndOtherMutantsHaveDifferentBehaviorToLastFrom(int mutantNumber, String... insertStatements) throws SQLException {
	    return mutantAndOtherMutantsHaveDifferentBehavior(mutantNumber, LAST_MUTANT_NUMBER, insertStatements);
	}

	public int mutantAndOtherMutantsHaveDifferentBehaviorFromFirstTo(int mutantNumber, String... insertStatements) throws SQLException {
	    return mutantAndOtherMutantsHaveDifferentBehavior(FIRST_MUTANT_NUMBER, mutantNumber, insertStatements);
	}

	public int mutantAndOtherMutantsHaveDifferentBehavior(int mutantNumber, String... insertStatements) throws SQLException {
	    return mutantAndOtherMutantsHaveDifferentBehavior(mutantNumber, mutantNumber, insertStatements);
	}

	public int mutantAndOtherMutantsHaveDifferentBehavior(int firstMutantNumber, int lastMutantNumber, String... insertStatements) throws SQLException {
	    List<Boolean> mutantSchemaResults = new ArrayList<>();
	    createMutantSchema();
	    for (String insertStatement : insertStatements) {
	        mutantSchemaResults.add(doInsert(insertStatement));
	    }

	    for (int i=firstMutantNumber; i <= lastMutantNumber; i++) {
	        if (i == MUTANT_BEING_EVALUATED) {
	            continue;
	        }

	        List<Boolean> otherMutantSchemaResults = new ArrayList<>();
	        createOtherMutantSchema(i);

	        for (String insertStatement : insertStatements) {
	            otherMutantSchemaResults.add(doInsert(insertStatement));
	        }

	        if (!QUIET) {
	            System.out.println("Mutant/mutant" + i + ": " + mutantSchemaResults + "/" + otherMutantSchemaResults);
	        }

	        boolean different = false;
	        for (int j=0; j < insertStatements.length; j++) {
	            if (mutantSchemaResults.get(j) != otherMutantSchemaResults.get(j)) {
	                different = true;
	            }
	        }

	        if (!different) {
	            return i;
	        }
	    }

	    return SUCCESS;
	}

	
	@AfterClass
	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}

