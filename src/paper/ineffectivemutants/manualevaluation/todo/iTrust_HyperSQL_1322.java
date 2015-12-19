package paper.ineffectivemutants.manualevaluation.todo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

//import static org.junit.Assert.*;

public class iTrust_HyperSQL_1322 extends ManualAnalysisTestSuite {
	
	@BeforeClass
	public static void initialise() throws ClassNotFoundException, SQLException {
		// load the JDBC driver and create the connection and statement object used by this test suite
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		connection = DriverManager.getConnection("jdbc:hsqldb:mem:/database;hsqldb.write_delay=false");

		// tell HyperSQL to always persist the data right away
		connection.setAutoCommit(true);
		// create the statement
		statement = connection.createStatement();
	}
	
	@AfterClass
	public static void close() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
	protected String getSchemaName() {
	    return "iTrust";
	}
	
	protected String getDBMSName() {
	    return "HyperSQL";
	}
	
	protected int getMutantNumberBeingEvaluated() {
	    return 1322;
	}
	
	protected int getLastMutantNumber() {
	    return 1407;
	}
	
	public void dropTables() throws SQLException {
		statement.executeUpdate("DROP TABLE IF EXISTS \"ReferralMessage\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"PatientSpecificInstructions\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"ProfilePhotos\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"AdverseEvents\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"RemoteMonitoringLists\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"RemoteMonitoringData\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"referrals\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"AppointmentType\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Appointment\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"message\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"LabProcedure\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"LOINC\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"OVSurvey\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"ReportRequests\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"FakeEmail\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"GlobalVariables\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"OVDiagnosis\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"OVReactionOverride\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"OVMedication\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"OVProcedure\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Allergies\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"PersonalAllergies\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"PersonalHealthInformation\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"OfficeVisits\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"DeclaredHCP\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"HCPAssignedHos\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Representatives\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"PersonalRelations\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"HCPRelations\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"TransactionLog\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"DrugInteractions\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"NDCodes\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"DrugReactionOverrideCodes\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"CPTCodes\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"icdcodes\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"ResetPasswordFailures\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"LoginFailures\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"HistoryPatients\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Patients\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Personnel\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Hospitals\"");
		statement.executeUpdate("DROP TABLE IF EXISTS \"Users\"");
	}

	/*****************************/
	/*** BEGIN MANUAL ANALYSIS ***/
	/*****************************/

	// String statement1 = "INSERT INTO " " VALUES( )";
	// String statement2 = "INSERT INTO " " VALUES( )";
	// String statement3 = "INSERT INTO " " VALUES( )";
	// String statement4 = "INSERT INTO " " VALUES( )";
	// String statement5 = "INSERT INTO " " VALUES( )";


	@Test
	public void notImpaired() throws SQLException {
	    // ... or maybe it is ...
	    // assertTrue(insertToMutant(statement1, ...));
	}

	@Test
	public void notEquivalent() throws SQLException {
	    // ... or maybe it is ...
	    // assertTrue(originalAndMutantHaveDifferentBehavior(statement1, ...));
	}

	@Test
	public void notRedundant() throws SQLException {
	    // ... or maybe it is ...
	    // assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement1, ...), SUCCESS);
	}

	// ENTER END VERDICT (delete as appropriate): impaired/equivalent/redundant/normal

	/*****************************/
	/***  END MANUAL ANALYSIS  ***/
	/*****************************/

}

