package casestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.checkcondition.InCheckCondition;
import org.schemaanalyst.sqlrepresentation.datatype.BooleanDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

public class ITrust extends Schema {

	private static final long serialVersionUID = -5210032013399175458L;

	@SuppressWarnings("unused")
	public ITrust() {
		super("iTrust");
		
		/* 
			CREATE TABLE Users(
                                MID                 INT ,
                                Password            VARCHAR(20),
                                Role                VARCHAR(20) NOT NULL,
                                sQuestion           VARCHAR(100), 
                                sAnswer             VARCHAR(30),
                                PRIMARY KEY (MID),
                                CHECK (Role IN ('patient','admin','hcp','uap','er','tester','pha', 'lt'))	
                        );
		 */
		
		Table users = createTable("Users");
		
		Column usersMid = users.addColumn("MID", new IntDataType());
		
		users.addColumn("Password", new VarCharDataType(20));
		
		Column usersRole = users.addColumn("Role", new VarCharDataType(20));
		usersRole.setNotNull();
		
		Column userSQuestion = users.addColumn("sQuestion", new VarCharDataType(100));
		
		Column userSAnswer = users.addColumn("sAnswer", new VarCharDataType(30));
		
		users.setPrimaryKeyConstraint(usersMid);
		users.addCheckConstraint(new InCheckCondition(usersRole, "patient", "admin", "hcp", "uap", "er", "tester", "pha", "lt"));
		
	
		/* 
			CREATE TABLE Hospitals(
				HospitalID   varchar(10),
				HospitalName varchar(30) NOT NULL, 
				PRIMARY KEY (hospitalID)
			);
		 */
		
		Table hospitals = createTable("hospitals");
		
		Column hospitalID = hospitals.addColumn("HospitalID", new VarCharDataType(10));
		
                Column hospitalName = hospitals.addColumn("HospitalName", new VarCharDataType(30));
		hospitalName.setNotNull();
		
		hospitals.setPrimaryKeyConstraint(hospitalID);
		
		/*
                        CREATE TABLE Personnel(
                                MID INT default NULL,
                                AMID INT default NULL,
                                role VARCHAR(20) NOT NULL,
                                enabled int NOT NULL,
                                lastName varchar(20) NOT NULL,
                                firstName varchar(20) NOT NULL,
                                address1 varchar(20) NOT NULL,
                                address2 varchar(20) NOT NULL,
                                city varchar(15) NOT NULL,
                                state VARCHAR(2) NOT NULL,
                                zip varchar(10),
                                zip1 varchar(5),
                                zip2 varchar(4),
                                phone varchar(12),
                                phone1 varchar(3),
                                phone2 varchar(3),
                                phone3 varchar(4),
                                specialty varchar(40),
                                email varchar(55),
                                MessageFilter varchar(60),
                                PRIMARY KEY  (MID),
                                CHECK (role IN ('admin','hcp','uap','er','tester','pha', 'lt')),
                                CHECK (state IN ('','AK','AL','AR','AZ','CA','CO','CT','DE','DC','FL','GA','HI','IA','ID','IL','IN','KS','KY','LA','MA','MD','ME','MI','MN','MO','MS','MT','NC','ND','NE','NH','NJ','NM','NV','NY','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VA','VT','WA','WI','WV','WY'))	
                        );
		 */
		
		Table personnel = createTable("Personnel");
		
		Column personnelMid = personnel.addColumn("MID", new IntDataType());
		
		Column personnelAMid = personnel.addColumn("AMID", new IntDataType());
		
		Column personnelRole = personnel.addColumn("role", new VarCharDataType(20));
		
		Column personnelEnabled = personnel.addColumn("enabled", new IntDataType());
		personnelEnabled.setNotNull();
		
		Column personnelLastName = personnel.addColumn("lastName", new VarCharDataType(20));
		personnelLastName.setNotNull();
		
		Column personnelFirstName = personnel.addColumn("firstName", new VarCharDataType(20));
		personnelFirstName.setNotNull();
		
		Column personnelAddress1 = personnel.addColumn("address1", new VarCharDataType(20));
		personnelAddress1.setNotNull();			
		
		Column personnelAddress2 = personnel.addColumn("address2", new VarCharDataType(20));
		personnelAddress2.setNotNull();
		
		Column personnelCity = personnel.addColumn("city", new VarCharDataType(15));
		personnelCity.setNotNull();
		
		Column personnelState = personnel.addColumn("state", new VarCharDataType(2));
		personnelState.setNotNull();
		
		Column personnelZip = personnel.addColumn("zip", new VarCharDataType(10));
		personnelZip.setNotNull();
		
		Column personnelZip1 = personnel.addColumn("zip1", new VarCharDataType(5));
		
		Column personnelZip2 = personnel.addColumn("zip2", new VarCharDataType(4));
		
		Column personnelPhone = personnel.addColumn("phone", new VarCharDataType(12));
		
		Column personnelPhone1 = personnel.addColumn("phone1", new VarCharDataType(3));
		
		Column personnelPhone2 = personnel.addColumn("phone2", new VarCharDataType(3));

		Column personnelPhone3 = personnel.addColumn("phone3", new VarCharDataType(4));
		
		Column personnelSpeciality = personnel.addColumn("speciality", new VarCharDataType(40));
		
		Column personnelEmail = personnel.addColumn("email", new VarCharDataType(55));
		
		Column personnelMessageFilter = personnel.addColumn("MessageFilter", new VarCharDataType(60));
		
		personnel.setPrimaryKeyConstraint(personnelMid);
		personnel.addCheckConstraint(new InCheckCondition(personnelRole, "admin", "hcp", "uap", "er", "tester", "pha", "lt"));
		personnel.addCheckConstraint(new InCheckCondition(personnelState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));
                
                /*
                        CREATE TABLE Patients(
                            MID INT   , 
                            lastName varchar(20), 
                            firstName varchar(20),
                            email varchar(55),
                            address1 varchar(20),
                            address2 varchar(20),
                            city varchar(15),
                            state VARCHAR(2),
                            zip1 varchar(5),
                            zip2 varchar(4),
                            phone1 varchar(3),
                            phone2 varchar(3),
                            phone3 varchar(4),
                            eName varchar(40),
                            ePhone1 varchar(3),
                            ePhone2 varchar(3),
                            ePhone3 varchar(4),
                            iCName varchar(20),
                            iCAddress1 varchar(20),
                            iCAddress2 varchar(20),
                            iCCity varchar(15),
                            ICState VARCHAR(2),
                            iCZip1 varchar(5),
                            iCZip2 varchar(4),
                            iCPhone1 varchar(3),
                            iCPhone2 varchar(3),
                            iCPhone3 varchar(4),			
                            iCID varchar(20), 
                            DateOfBirth DATE,
                            DateOfDeath DATE,
                            CauseOfDeath VARCHAR(10),
                            MotherMID INTEGER,
                            FatherMID INTEGER,
                            BloodType VARCHAR(3),
                            Ethnicity VARCHAR(20),
                            Gender VARCHAR(13),
                            TopicalNotes VARCHAR(200),
                            CreditCardType VARCHAR(20),
                            CreditCardNumber VARCHAR(19),
                            MessageFilter varchar(60),
                            DirectionsToHome varchar(512),
                            Religion varchar(64),
                            Language varchar(32),
                            SpiritualPractices varchar(100),
                            AlternateName varchar(32),
                            PRIMARY KEY (MID),
                            CHECK (state IN ('AK','AL','AR','AZ','CA','CO','CT','DE','DC','FL','GA','HI','IA','ID','IL','IN','KS','KY','LA','MA','MD','ME','MI','MN','MO','MS','MT','NC','ND','NE','NH','NJ','NM','NV','NY','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VA','VT','WA','WI','WV','WY')),		
                            CHECK (ICstate IN ('AK','AL','AR','AZ','CA','CO','CT','DE','DC','FL','GA','HI','IA','ID','IL','IN','KS','KY','LA','MA','MD','ME','MI','MN','MO','MS','MT','NC','ND','NE','NH','NJ','NM','NV','NY','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VA','VT','WA','WI','WV','WY'))
                        );
		*/
                
                Table patients = createTable("Patients");
                
                Column patientsMid = patients.addColumn("MID", new IntDataType());
                
                Column patientsLastName = patients.addColumn("lastName", new VarCharDataType(20));
                
                Column patientsfirstName = patients.addColumn("firstName", new VarCharDataType(20));
                
                Column patientsEmail = patients.addColumn("email", new VarCharDataType(55));
                
                Column patientsAddress1 = patients.addColumn("address1", new VarCharDataType(20));

                Column patientsAddress2 = patients.addColumn("address2", new VarCharDataType(20));

		Column patientsCity = patients.addColumn("city", new VarCharDataType(15));

		Column patientsState = patients.addColumn("state", new VarCharDataType(2));

		Column patientsZip1 = patients.addColumn("zip1", new VarCharDataType(5));

		Column patientsZip2 = patients.addColumn("zip2", new VarCharDataType(4));
               
		Column patientsPhone1 = patients.addColumn("phone1", new VarCharDataType(3));
                
		Column patientsPhone2 = patients.addColumn("phone2", new VarCharDataType(3));

		Column patientsPhone3 = patients.addColumn("phone3", new VarCharDataType(4));

		Column patientsEName = patients.addColumn("eName", new VarCharDataType(40));

		Column patientsEPhone1 = patients.addColumn("ePhone1", new VarCharDataType(3));

		Column patientsEPhone2 = patients.addColumn("ePhone2", new VarCharDataType(3));

		Column patientsEPhone3 = patients.addColumn("ePhone3", new VarCharDataType(4));

		Column patientsICName = patients.addColumn("iCName", new VarCharDataType(20));

		Column patientsICAddress1 = patients.addColumn("iCAddress1", new VarCharDataType(20));

		Column patientsICAddress2 = patients.addColumn("iCAddress2", new VarCharDataType(20));

		Column patientsICCity = patients.addColumn("iCCity", new VarCharDataType(15));

		Column patientsICState = patients.addColumn("ICState", new VarCharDataType(2));

		Column patientsICZip1 = patients.addColumn("iCZip1", new VarCharDataType(5));

		Column patientsICZip2 = patients.addColumn("iCZip2", new VarCharDataType(4));

		Column patientsICPhone1 = patients.addColumn("iCPhone1", new VarCharDataType(3));

		Column patientsICPhone2 = patients.addColumn("iCPhone2", new VarCharDataType(3));

		Column patientsICPhone3 = patients.addColumn("iCPhone3", new VarCharDataType(4));
					
		Column patientsICID = patients.addColumn("iCID", new VarCharDataType(20));
		 
		Column patientsDateOfBirth = patients.addColumn("DateOfBirth", new DateDataType());

		Column patientsDateOfDeath = patients.addColumn("DateOfDeath", new DateDataType());

		Column patientsCauseOfDeath = patients.addColumn("CauseOfDeath", new VarCharDataType(10));

		Column patientsMotherMID = patients.addColumn("MotherMID", new IntDataType());

		Column patientsFatherMID = patients.addColumn("FatherMID", new IntDataType());

		Column patientsBloodType = patients.addColumn("BloodType", new VarCharDataType(3));

		Column patientsEthnicity = patients.addColumn("Ethnicity", new VarCharDataType(20));

		Column patientsGender = patients.addColumn("Gender", new VarCharDataType(13));

		Column patientsTopicalNotes = patients.addColumn("TopicalNotes", new VarCharDataType(200));

		Column patientsCreditCardType = patients.addColumn("CreditCardType", new VarCharDataType(20));

		Column patientsCreditCardNumber = patients.addColumn("CreditCardNumber", new VarCharDataType(19));

		Column patientsMessageFilter = patients.addColumn("MessageFilter", new VarCharDataType(60));

		Column patientsDirectionsToHome = patients.addColumn("DirectionsToHome", new VarCharDataType(512));

		Column patientsReligion = patients.addColumn("Religion", new VarCharDataType(64));

		Column patientsLanguage = patients.addColumn("Language", new VarCharDataType(32));

		Column patientsSpiritualPractices = patients.addColumn("SpiritualPractices", new VarCharDataType(100));

		Column patientsAlternateName = patients.addColumn("AlternateName", new VarCharDataType(32));
                
                patients.setPrimaryKeyConstraint(patientsMid);
                patients.addCheckConstraint(new InCheckCondition(patientsState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));
                patients.addCheckConstraint(new InCheckCondition(patientsICState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));

                /*
                    CREATE TABLE HistoryPatients(
                        ID INT   ,
                        changeDate date NOT NULL,
                        changeMID INT  NOT NULL,
                        MID INT  NOT NULL, 
                        lastName varchar(20)  , 
                        firstName varchar(20)  , 
                        email varchar(55)  , 
                        address1 varchar(20)  , 
                        address2 varchar(20)  , 
                        city varchar(15)  , 
                        state CHAR(2) , 
                        zip1 varchar(5)  , 
                        zip2 varchar(4)  ,
                        phone1 varchar(3) ,
                        phone2 varchar(3) ,
                        phone3 varchar(4) ,
                        eName varchar(40)  , 
                        ePhone1 varchar(3)  , 
                        ePhone2 varchar(3)  , 		
                        ePhone3 varchar(4)  , 	
                        iCName varchar(20)  , 
                        iCAddress1 varchar(20)  , 
                        iCAddress2 varchar(20)  , 
                        iCCity varchar(15)  , 
                        ICState VARCHAR(2) , 
                        iCZip1 varchar(5)  , 
                        iCZip2 varchar(4)  ,
                        iCPhone1 varchar(3)  ,
                        iCPhone2 varchar(3)  ,
                        iCPhone3 varchar(4)  ,			
                        iCID varchar(20)  , 
                        DateOfBirth date,
                        DateOfDeath date,
                        CauseOfDeath VARCHAR(10) ,
                        MotherMID INTEGER,
                        FatherMID INTEGER,
                        BloodType VARCHAR(3) ,
                        Ethnicity VARCHAR(20) ,
                        Gender VARCHAR(13),
                        TopicalNotes VARCHAR(200) ,
                        CreditCardType VARCHAR(20) ,
                        CreditCardNumber VARCHAR(19) ,
                        MessageFilter varchar(60) ,
                        DirectionsToHome varchar(100) ,
                        Religion varchar(64) ,
                        Language varchar(32) ,
                        SpiritualPractices varchar(100) ,
                        AlternateName varchar(32),
                        PRIMARY KEY (ID),
                        CHECK (state IN ('AK','AL','AR','AZ','CA','CO','CT','DE','DC','FL','GA','HI','IA','ID','IL','IN','KS','KY','LA','MA','MD','ME','MI','MN','MO','MS','MT','NC','ND','NE','NH','NJ','NM','NV','NY','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VA','VT','WA','WI','WV','WY')),		
                        CHECK (ICstate IN ('AK','AL','AR','AZ','CA','CO','CT','DE','DC','FL','GA','HI','IA','ID','IL','IN','KS','KY','LA','MA','MD','ME','MI','MN','MO','MS','MT','NC','ND','NE','NH','NJ','NM','NV','NY','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VA','VT','WA','WI','WV','WY'))	
                    );
		*/
                
                Table historyPatients = createTable("HistoryPatients");
                
                Column historyPatientsId = historyPatients.addColumn("ID", new IntDataType());
                
                Column historyPatientsChangeDate = historyPatients.addColumn("changeDate", new DateDataType());
                historyPatientsChangeDate.setNotNull();
                
                Column historyPatientsChangeMid = historyPatients.addColumn("changeMID", new IntDataType());
                historyPatientsChangeMid.setNotNull();
                
                Column historyPatientsMid = historyPatients.addColumn("MID", new IntDataType());
                historyPatientsMid.setNotNull();
                
                Column historyPatientsLastName = historyPatients.addColumn("lastName", new VarCharDataType(20));

                Column historyPatientsfirstName = historyPatients.addColumn("firstName", new VarCharDataType(20));

                Column historyPatientsEmail = historyPatients.addColumn("email", new VarCharDataType(55));

                Column historyPatientsAddress1 = historyPatients.addColumn("address1", new VarCharDataType(20));

                Column historyPatientsAddress2 = historyPatients.addColumn("address2", new VarCharDataType(20));

                Column historyPatientsCity = historyPatients.addColumn("city", new VarCharDataType(15));

                Column historyPatientsState = historyPatients.addColumn("state", new VarCharDataType(2));

                Column historyPatientsZip1 = historyPatients.addColumn("zip1", new VarCharDataType(5));

                Column historyPatientsZip2 = historyPatients.addColumn("zip2", new VarCharDataType(4));

                Column historyPatientsPhone1 = historyPatients.addColumn("phone1", new VarCharDataType(3));

                Column historyPatientsPhone2 = historyPatients.addColumn("phone2", new VarCharDataType(3));

                Column historyPatientsPhone3 = historyPatients.addColumn("phone3", new VarCharDataType(4));

                Column historyPatientsEName = historyPatients.addColumn("eName", new VarCharDataType(40));

                Column historyPatientsEPhone1 = historyPatients.addColumn("ePhone1", new VarCharDataType(3));

                Column historyPatientsEPhone2 = historyPatients.addColumn("ePhone2", new VarCharDataType(3));

                Column historyPatientsEPhone3 = historyPatients.addColumn("ePhone3", new VarCharDataType(4));

                Column historyPatientsICName = historyPatients.addColumn("iCName", new VarCharDataType(20));

                Column historyPatientsICAddress1 = historyPatients.addColumn("iCAddress1", new VarCharDataType(20));

                Column historyPatientsICAddress2 = historyPatients.addColumn("iCAddress2", new VarCharDataType(20));

                Column historyPatientsICCity = historyPatients.addColumn("iCCity", new VarCharDataType(15));

                Column historyPatientsICState = historyPatients.addColumn("ICState", new VarCharDataType(2));

                Column historyPatientsICZip1 = historyPatients.addColumn("iCZip1", new VarCharDataType(5));

                Column historyPatientsICZip2 = historyPatients.addColumn("iCZip2", new VarCharDataType(4));

                Column historyPatientsICPhone1 = historyPatients.addColumn("iCPhone1", new VarCharDataType(3));

                Column historyPatientsICPhone2 = historyPatients.addColumn("iCPhone2", new VarCharDataType(3));

                Column historyPatientsICPhone3 = historyPatients.addColumn("iCPhone3", new VarCharDataType(4));

                Column historyPatientsICID = historyPatients.addColumn("iCID", new VarCharDataType(20));

                Column historyPatientsDateOfBirth = historyPatients.addColumn("DateOfBirth", new DateDataType());

                Column historyPatientsDateOfDeath = historyPatients.addColumn("DateOfDeath", new DateDataType());

                Column historyPatientsCauseOfDeath = historyPatients.addColumn("CauseOfDeath", new VarCharDataType(10));

                Column historyPatientsMotherMID = historyPatients.addColumn("MotherMID", new IntDataType());

                Column historyPatientsFatherMID = historyPatients.addColumn("FatherMID", new IntDataType());

                Column historyPatientsBloodType = historyPatients.addColumn("BloodType", new VarCharDataType(3));

                Column historyPatientsEthnicity = historyPatients.addColumn("Ethnicity", new VarCharDataType(20));

                Column historyPatientsGender = historyPatients.addColumn("Gender", new VarCharDataType(13));

                Column historyPatientsTopicalNotes = historyPatients.addColumn("TopicalNotes", new VarCharDataType(200));

                Column historyPatientsCreditCardType = historyPatients.addColumn("CreditCardType", new VarCharDataType(20));

                Column historyPatientsCreditCardNumber = historyPatients.addColumn("CreditCardNumber", new VarCharDataType(19));

                Column historyPatientsMessageFilter = historyPatients.addColumn("MessageFilter", new VarCharDataType(60));

                Column historyPatientsDirectionsToHome = historyPatients.addColumn("DirectionsToHome", new VarCharDataType(512));

                Column historyPatientsReligion = historyPatients.addColumn("Religion", new VarCharDataType(64));

                Column historyPatientsLanguage = historyPatients.addColumn("Language", new VarCharDataType(32));

                Column historyPatientsSpiritualPractices = historyPatients.addColumn("SpiritualPractices", new VarCharDataType(100));

                Column historyPatientsAlternateName = historyPatients.addColumn("AlternateName", new VarCharDataType(32));

                historyPatients.setPrimaryKeyConstraint(historyPatientsId);
                historyPatients.addCheckConstraint(new InCheckCondition(historyPatientsState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));
                historyPatients.addCheckConstraint(new InCheckCondition(historyPatientsICState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));

                /*     
                    CREATE TABLE LoginFailures(
                        ipaddress varchar(100) NOT NULL, 
                        failureCount int NOT NULL, 
                        lastFailure TIMESTAMP NOT NULL,
                        PRIMARY KEY (ipaddress)
                    );
                */
                
                Table loginFailures = createTable("LoginFailures");
                
                Column loginFailuresIpAddress = loginFailures.addColumn("ipaddress", new VarCharDataType(100));
                loginFailuresIpAddress.setNotNull();
                
                Column loginFailuresFailureCount = loginFailures.addColumn("failureCount", new IntDataType());
                loginFailuresFailureCount.setNotNull();
                
                Column loginFailuresLastFailure = loginFailures.addColumn("lastFailure", new TimestampDataType());
                loginFailuresLastFailure.setNotNull();
                
                loginFailures.setPrimaryKeyConstraint(loginFailuresIpAddress);
                
                /*     
                    CREATE TABLE ResetPasswordFailures(
                        ipaddress varchar(128) NOT NULL, 
                        failureCount int NOT NULL, 
                        lastFailure TIMESTAMP NOT NULL,
                        PRIMARY KEY (ipaddress)
                    );
                */
                
                Table passwordResetFailures = createTable("passwordResetFailures");

                Column passwordResetFailuresIpAddress = passwordResetFailures.addColumn("ipaddress", new VarCharDataType(128));
                passwordResetFailuresIpAddress.setNotNull();

                Column passwordResetFailuresFailureCount = passwordResetFailures.addColumn("failureCount", new IntDataType());
                passwordResetFailuresFailureCount.setNotNull();

                Column passwordResetFailuresLastFailure = passwordResetFailures.addColumn("lastFailure", new TimestampDataType());
                passwordResetFailuresLastFailure.setNotNull();

                passwordResetFailures.setPrimaryKeyConstraint(passwordResetFailuresIpAddress);
                
                
                /*     
                    CREATE TABLE icdcodes (
                        Code numeric(5,2) NOT NULL,
                        Description VARCHAR(50) NOT NULL,
                        Chronic VARCHAR(3) NOT NULL,
                        PRIMARY KEY (Code),
                        CHECK (Chronic IN ('no','yes'))		
                    );
                */
                
                Table icdCodes = createTable("icdcodes");
                
                Column icdCodesCode = icdCodes.addColumn("Code", new NumericDataType(5, 2));
                icdCodesCode.setNotNull();
                
                Column icdCodesDescription = icdCodes.addColumn("Description", new VarCharDataType(50));
                icdCodesDescription.setNotNull();
                
                Column icdCodesChronic = icdCodes.addColumn("Chronic", new VarCharDataType(3));
                icdCodesChronic.setNotNull();
                
                icdCodes.setPrimaryKeyConstraint(icdCodesCode);
                icdCodes.addCheckConstraint(new InCheckCondition(icdCodesChronic, "no", "yes"));
                
                /*     
                    CREATE TABLE CPTCodes(
                        Code varchar(5) NOT NULL,
                        Description varchar(30) NOT NULL,
                        Attribute varchar(30),
                        PRIMARY KEY (Code)
                    );
                */
                
                Table cptCodes = createTable("CPTCodes");
                
                Column cptCodesCode = cptCodes.addColumn("Code", new VarCharDataType(5));
                cptCodesCode.setNotNull();
                
                Column cptCodesDescription = cptCodes.addColumn("Description", new VarCharDataType(30));
                cptCodesDescription.setNotNull();
                
                Column cptCodesAttribute = cptCodes.addColumn("Attribute", new VarCharDataType(30));
                
                cptCodes.setPrimaryKeyConstraint(cptCodesCode);
                
                /*     
                    CREATE TABLE DrugReactionOverrideCodes(
                        Code varchar(5) NOT NULL,
                        Description varchar(80) NOT NULL,
                        PRIMARY KEY (Code)
                    );
                */
                
                Table drugReactionOverrideCodes = createTable("DrugReactionOverrideCode");
                
                Column drugReactionOverrideCodesCode = drugReactionOverrideCodes.addColumn("Code", new VarCharDataType(5));
                drugReactionOverrideCodesCode.setNotNull();
                
                Column drugReactionOverrideCodesDescription = drugReactionOverrideCodes.addColumn("Desription", new VarCharDataType(80));
                drugReactionOverrideCodesDescription.setNotNull();
                
                drugReactionOverrideCodes.setPrimaryKeyConstraint(drugReactionOverrideCodesCode);
                
                /*     
                    CREATE TABLE NDCodes(
                        Code varchar(10) NOT NULL, 
                        Description varchar(100) NOT NULL, 
                        PRIMARY KEY  (Code)
                    );
                */
                
                Table ndCodes = createTable("NDCodes");
                
                Column ndCodesCode = ndCodes.addColumn("Code", new VarCharDataType(10));
                ndCodesCode.setNotNull();
                
                Column ndCodesDescription = ndCodes.addColumn("Description", new VarCharDataType(100));
                ndCodesDescription.setNotNull();
                
                ndCodes.setPrimaryKeyConstraint(ndCodesCode);
                
                /*     
                    CREATE TABLE DrugInteractions(
                        FirstDrug varchar(9) NOT NULL,
                        SecondDrug varchar(9) NOT NULL,
                        Description varchar(100) NOT NULL,
                        PRIMARY KEY  (FirstDrug,SecondDrug)
                    );
                */
                
                Table drugInteractions = createTable("DrugInteractions");
                
                Column drugInteractionsFirstDrug = drugInteractions.addColumn("FirstDrug", new VarCharDataType(9));
                drugInteractionsFirstDrug.setNotNull();
                
                Column drugInteractionsSecondDrug = drugInteractions.addColumn("SecondDrug", new VarCharDataType(9));
                drugInteractionsSecondDrug.setNotNull();
                
                Column drugInteractionsDescription = drugInteractions.addColumn("Description", new VarCharDataType(100));
                drugInteractionsDescription.setNotNull();
                
                drugInteractions.setPrimaryKeyConstraint(drugInteractionsFirstDrug, drugInteractionsSecondDrug);
                
                /*     
                    CREATE TABLE TransactionLog(
                        transactionID int NOT NULL, 
                        loggedInMID INT NOT NULL, 
                        secondaryMID INT NOT NULL, 
                        transactionCode int NOT NULL, 
                        timeLogged timestamp NOT NULL,
                        addedInfo VARCHAR(255),
                        PRIMARY KEY (transactionID)
                    );
                */
                
                Table transactionLog = createTable("TransactionLog");
                
                Column transactionLogtransactionID = transactionLog.addColumn("transactionID", new IntDataType());
                transactionLogtransactionID.setNotNull();
                
                Column transactionLogLoggedInMID = transactionLog.addColumn("loggedInMID", new IntDataType());
                transactionLogLoggedInMID.setNotNull();
                
                Column transactionLogSecondaryMID = transactionLog.addColumn("secondaryMID", new IntDataType());
                transactionLogSecondaryMID.setNotNull();
                
                Column transactionLogTransactionCode = transactionLog.addColumn("transactionCode", new IntDataType());
                transactionLogTransactionCode.setNotNull();
                
                Column transactionLogTimeLogged = transactionLog.addColumn("timeLogged", new TimestampDataType());
                transactionLogTimeLogged.setNotNull();
                
                Column transactionLogAddedInfo = transactionLog.addColumn("addedInfo", new VarCharDataType(255));
                
                transactionLog.setPrimaryKeyConstraint(transactionLogtransactionID);
                
                /*     
                    CREATE TABLE HCPRelations(
                        HCP INT NOT NULL, 
                        UAP INT NOT NULL,
                        PRIMARY KEY (HCP, UAP)
                    );
                */
                
                Table hcpRelations = createTable("HCPRelations");
                
                Column hcpRelationsHCP = hcpRelations.addColumn("HCP", new IntDataType());
                hcpRelationsHCP.setNotNull();
                
                Column hcpRelationsUAP = hcpRelations.addColumn("UAP", new IntDataType());
                hcpRelationsUAP.setNotNull();
                
                hcpRelations.setPrimaryKeyConstraint(hcpRelationsHCP, hcpRelationsUAP);
                
                /*     
                    CREATE TABLE PersonalRelations(
                        PatientID INT NOT NULL,
                        RelativeID INT NOT NULL,
                        RelativeType VARCHAR( 35 ) NOT NULL 
                    );
                */
                
                Table personalRelations = createTable("PersonalRelations");
                
                Column personalRelationsPatientID = personalRelations.addColumn("PatientID", new IntDataType());
                personalRelationsPatientID.setNotNull();
                
                Column personalRelationsRelativeID = personalRelations.addColumn("RelativeID", new IntDataType());
                personalRelationsRelativeID.setNotNull();
                
                Column personalRelationsRelativeType = personalRelations.addColumn("RelativeType", new VarCharDataType(35));
                personalRelationsRelativeType.setNotNull();
                
                /*     
                    CREATE TABLE Representatives(
                        representerMID INT, 
                        representeeMID INT,
                        PRIMARY KEY  (representerMID,representeeMID)
                    );
                */
                
                Table representatives = createTable("Representatives");
                
                Column representativesRepresenterMID = representatives.addColumn("representerMID", new IntDataType());
                
                Column representativesRepresenteeMID = representatives.addColumn("representeeMID", new IntDataType());
                
                representatives.setPrimaryKeyConstraint(representativesRepresenterMID, representativesRepresenteeMID);
                
                /*     
                    CREATE TABLE HCPAssignedHos(
                        hosID VARCHAR(10) NOT NULL, 
                        HCPID INT  NOT NULL, 
                        PRIMARY KEY (hosID,HCPID)
                    );
                */
                
                Table hcpAssignedHos = createTable("HCPAssignedHos");
                
                Column hcpAssignedHosHosID = hcpAssignedHos.addColumn("hosID", new VarCharDataType(10));
                hcpAssignedHosHosID.setNotNull();
                
                Column hcpAssignedHosHcpID = hcpAssignedHos.addColumn("HCPID", new IntDataType());
                hcpAssignedHosHcpID.setNotNull();
                
                hcpAssignedHos.setPrimaryKeyConstraint(hcpAssignedHosHosID, hcpAssignedHosHcpID);
                
                /*     
                    CREATE TABLE DeclaredHCP(
                        PatientID INT NOT NULL, 
                        HCPID INT NOT NULL, 
                        PRIMARY KEY  (PatientID,HCPID)
                    );
                */
                
                Table declaredHCP = createTable("DeclaredHCP");
                
                Column declaredHCPPatientID = declaredHCP.addColumn("PatientID", new IntDataType());
                declaredHCPPatientID.setNotNull();
                
                Column declaredHCPHCPID = declaredHCP.addColumn("HPCID", new IntDataType());
                declaredHCPHCPID.setNotNull();
                
                declaredHCP.setPrimaryKeyConstraint(declaredHCPPatientID, declaredHCPHCPID);
                
                /*     
                    CREATE TABLE OfficeVisits(
                        ID int primary key,
                        visitDate date,  
                        HCPID INT, 
                        notes varchar(50), 
                        PatientID INT, 
                        HospitalID VARCHAR(10)
                    );
                */
                
                Table officeVisits = createTable("OfficeVisits");
                
                Column officeVisitsID = officeVisits.addColumn("ID", new IntDataType());
                
                Column officeVisitsVisitDate = officeVisits.addColumn("visitDate", new DateDataType());
                
                Column officeVisitsHCPID = officeVisits.addColumn("HCPID", new IntDataType());
                
                Column officeVisitsNotes = officeVisits.addColumn("notes", new VarCharDataType(50));
                
                Column officeVisitsPatientID = officeVisits.addColumn("PatientID", new IntDataType());
                
                Column officeVisitsHospitalID = officeVisits.addColumn("HospitalID", new VarCharDataType(10));
                
                officeVisits.setPrimaryKeyConstraint(officeVisitsID);
                
                /*     
                    CREATE TABLE PersonalHealthInformation (
                        PatientID INT  NOT NULL,
                        Height int,  
                        Weight int,  
                        Smoker int NOT NULL,
                        SmokingStatus int NOT NULL,
                        BloodPressureN int,  
                        BloodPressureD int,  
                        CholesterolHDL int,
                        CholesterolLDL int,
                        CholesterolTri int,
                        HCPID int,  
                        AsOfDate timestamp NOT NULL 
                    );
                */
                
                Table personalHealthInformation = createTable("PersonalHealthInformation");
                
                Column personalHealthInformationPatientID = personalHealthInformation.addColumn("PatientID", new IntDataType());
                personalHealthInformationPatientID.setNotNull();
                
                Column personalHealthInformationHeight = personalHealthInformation.addColumn("Height", new IntDataType());
                
                Column personalHealthInformationWeight = personalHealthInformation.addColumn("Weight", new IntDataType());
                
                Column personalHealthInformationSmoker = personalHealthInformation.addColumn("Smoker", new IntDataType());
                personalHealthInformationSmoker.setNotNull();
                
                Column personalHealthInformationSmokingStatus = personalHealthInformation.addColumn("SmokingStatus", new IntDataType());
                personalHealthInformationSmokingStatus.setNotNull();
                
                Column personalHealthInformationBloodPressureN = personalHealthInformation.addColumn("BloodPressureN", new IntDataType());
                
                Column personalHealthInformationBloodPressureD = personalHealthInformation.addColumn("BloodPressureD", new IntDataType());
                
                Column personalHealthInformationCholesterolHDL = personalHealthInformation.addColumn("CholesterolHDL", new IntDataType());
                
                Column personalHealthInformationCholesterolLDL = personalHealthInformation.addColumn("CholesterolLDL", new IntDataType());
                
                Column personalHealthInformationCholesterolTri = personalHealthInformation.addColumn("CholesterolTri", new IntDataType());
                
                Column personalHealthInformationHCPID = personalHealthInformation.addColumn("HCPID", new IntDataType());
                
                Column personalHealthInformationAsOfDate = personalHealthInformation.addColumn("AsOfDate", new TimestampDataType());
                personalHealthInformationAsOfDate.setNotNull();
                
                /*     
                    CREATE TABLE PersonalAllergies(
                        PatientID INT  NOT NULL,
                        Allergy VARCHAR( 50 ) NOT NULL 
                    );
                */
                
                Table personalAllergies = createTable("PersonalAllergies");
                
                Column personalAllergiesPatientID = personalAllergies.addColumn("PatientID", new IntDataType());
                personalAllergiesPatientID.setNotNull();
                
                Column personalAllergiesAllergy = personalAllergies.addColumn("Allergy", new VarCharDataType(50));
                personalAllergiesAllergy.setNotNull();
                
                /*     
                    CREATE TABLE Allergies(
                        ID INT primary key,
                        PatientID INT NOT NULL,
                        Description VARCHAR( 50 ) NOT NULL,
                        FirstFound TIMESTAMP NOT NULL
                    );
                */
                
                Table allergies = createTable("Allergies");
                
                Column allergiesID = allergies.addColumn("ID", new IntDataType());
                allergies.setPrimaryKeyConstraint(allergiesID);
                
                Column allergiesPatientID = allergies.addColumn("PatientID", new IntDataType());
                allergiesPatientID.setNotNull();
                
                Column allergiesDescription = allergies.addColumn("Description", new VarCharDataType(50));
                allergiesDescription.setNotNull();
                
                Column allergiesFirstFound = allergies.addColumn("FirstFound", new TimestampDataType());
                allergiesFirstFound.setNotNull();
                
                /*     
                    CREATE TABLE OVProcedure(
                        ID INT primary key,
                        VisitID INT  NOT NULL,
                        CPTCode VARCHAR( 5 ) NOT NULL,
                        HCPID VARCHAR( 10 ) NOT NULL
                    );
                */
                
                Table ovProcedure = createTable("OVProcedure");
                
                Column ovProcedureID = ovProcedure.addColumn("ID", new IntDataType());
                ovProcedure.setPrimaryKeyConstraint(ovProcedureID);
                
                Column ovProcedureVisitID = ovProcedure.addColumn("VisitID", new IntDataType());
                ovProcedureVisitID.setNotNull();
                
                Column ovProcedureCPTCode = ovProcedure.addColumn("CPTCode", new VarCharDataType(5));
                ovProcedureCPTCode.setNotNull();
                
                Column ovProcedureHCPID = ovProcedure.addColumn("HCPID", new VarCharDataType(10));
                ovProcedureHCPID.setNotNull();
                
                /*     
                    CREATE TABLE OVMedication (
                        ID INT primary key,
                        VisitID INT  NOT NULL,
                        NDCode VARCHAR( 9 ) NOT NULL,
                        StartDate DATE,
                        EndDate DATE,
                        Dosage INT,
                        Instructions VARCHAR(500)
                    );
                */
                
                Table ovMedication = createTable("OVMedication");
                
                Column ovMedicationID = ovMedication.addColumn("ID", new IntDataType());
                ovMedication.setPrimaryKeyConstraint(ovMedicationID);
                
                Column ovMedicationVisitID = ovMedication.addColumn("VisitID", new IntDataType());
                ovMedicationVisitID.setNotNull();
                
                Column ovMedicationNDCode = ovMedication.addColumn("NDCode", new VarCharDataType(9));
                ovMedicationNDCode.setNotNull();
                
                Column ovMedicationStartDate = ovMedication.addColumn("StartDate", new DateDataType());
                
                Column ovMedicationEndDate = ovMedication.addColumn("EndDate", new DateDataType());
                
                Column ovMedicationDosage = ovMedication.addColumn("Dosage", new IntDataType());
                
                Column ovMedicationInstructions = ovMedication.addColumn("Instructions", new VarCharDataType(500));
                
                /*     
                    CREATE TABLE OVReactionOverride (
                        ID INT primary key,
                        OVMedicationID INT NOT NULL,
                        OverrideCode VARCHAR(5),
                        OverrideComment VARCHAR(255),
                        FOREIGN KEY (OVMedicationID) REFERENCES OVMedication (ID)
                    );
                */
                
                Table ovReactionOverride = createTable("OVReactionOverride");
                
                Column ovReactionOverrideID = ovReactionOverride.addColumn("ID", new IntDataType());
                ovReactionOverride.setPrimaryKeyConstraint(ovReactionOverrideID);
                
                Column ovReactionOverrideOVMedicationID = ovReactionOverride.addColumn("OVMedicationID", new IntDataType());
                ovReactionOverrideOVMedicationID.setNotNull();
                
                Column ovReactionOverrideOverrideCode = ovReactionOverride.addColumn("OverrideCode", new VarCharDataType(5));
                
                Column ovReactionOverrideOverrideComment = ovReactionOverride.addColumn("OverrideComment", new VarCharDataType(255));
                
                ovReactionOverride.addForeignKeyConstraint(ovReactionOverrideOVMedicationID, ovMedication, ovMedicationID);
                
                /*     
                    CREATE TABLE OVDiagnosis (
                        ID INT primary key,
                        VisitID INT  NOT NULL,
                        ICDCode DECIMAL( 5, 2 ) NOT NULL
                    );
                */
                
                Table ovDiagnosis = createTable("OVDiagnosis");
                
                Column ovDiagnosisID = ovDiagnosis.addColumn("ID", new IntDataType());
                ovDiagnosis.setPrimaryKeyConstraint(ovDiagnosisID);
                
                Column ovDiagnosisVisitID = ovDiagnosis.addColumn("VisitID", new IntDataType());
                ovDiagnosisVisitID.setNotNull();
                
                Column ovDiagnosisICDCode = ovDiagnosis.addColumn("ICDCode", new DecimalDataType(5, 2));
                ovDiagnosisICDCode.setNotNull();
                
                /*     
                    CREATE TABLE GlobalVariables (
                        Name VARCHAR(20) primary key,
                        Value VARCHAR(20)
                    );
                */
                
                Table globalVariables = createTable("GlobalVariables");
                
                Column globalVariablesName = globalVariables.addColumn("Name", new VarCharDataType(20));
                globalVariables.setPrimaryKeyConstraint(globalVariablesName);
                
                Column globalVariablesValue = globalVariables.addColumn("Value", new VarCharDataType(20));
                
                /*     
                    CREATE TABLE FakeEmail(
                        ID INT primary key,
                        ToAddr VARCHAR(100),
                        FromAddr VARCHAR(100),
                        Subject VARCHAR(500),
                        Body VARCHAR(2000),
                        AddedDate timestamp NOT NULL
                    );
                */
                
                Table fakeEmail = createTable("FakeEmail");
                
                Column fakeEmailID = fakeEmail.addColumn("ID", new IntDataType());
                fakeEmail.setPrimaryKeyConstraint(fakeEmailID);
                
                Column fakeEmailToAddr = fakeEmail.addColumn("ToAddr", new VarCharDataType(100));
                
                Column fakeEmailFromAddr = fakeEmail.addColumn("FromAddr", new VarCharDataType(100));
                
                Column fakeEmailSubject = fakeEmail.addColumn("Subject", new VarCharDataType(500));
                
                Column fakeEmailBody = fakeEmail.addColumn("Body", new VarCharDataType(2000));
                
                Column fakeEmailAddedDate = fakeEmail.addColumn("AddedDate", new TimestampDataType());
                fakeEmailAddedDate.setNotNull();
                
                /*     
                    CREATE TABLE ReportRequests (
                        ID INT primary key,
                        RequesterMID INT ,
                        PatientMID INT ,
                        ApproverMID INT ,
                        RequestedDate timestamp,
                        ApprovedDate timestamp,
                        ViewedDate timestamp,
                        Status varchar(30),
                        Comment VARCHAR(50)
                    );
                */
                
                Table reportRequests = createTable("ReportRequests");
                
                Column reportRequestsID = reportRequests.addColumn("ID", new IntDataType());
                reportRequests.setPrimaryKeyConstraint(reportRequestsID);
                
                Column reportRequestsRequesterMID = reportRequests.addColumn("RequesterMID", new IntDataType());
                
                Column reportRequestsPatientMID = reportRequests.addColumn("PatientMID", new IntDataType());
                
                Column reportRequestsApproverMID = reportRequests.addColumn("ApproverMID", new IntDataType());
                
                Column reportRequestsRequestedDate = reportRequests.addColumn("RequestedDate", new TimestampDataType());
                
                Column reportRequestsApprovedDate = reportRequests.addColumn("ApprovedDate", new TimestampDataType());
                
                Column reportRequestsViewedDate = reportRequests.addColumn("ViewedDate", new TimestampDataType());
                
                Column reportRequestsStatus = reportRequests.addColumn("Status", new VarCharDataType(30));
                
                Column reportRequestsComment = reportRequests.addColumn("Comment", new VarCharDataType(50));
                
                /*     
                    CREATE TABLE OVSurvey (
                        VisitID int  primary key,
                        SurveyDate timestamp not null,
                        WaitingRoomMinutes int,
                        ExamRoomMinutes int,
                        VisitSatisfaction int,	
                        TreatmentSatisfaction int
                    );
                */
                
                Table ovSurvey = createTable("OVSurvey");
                
                Column ovSurveyVisitID = ovSurvey.addColumn("VisitID", new IntDataType());
                ovSurvey.setPrimaryKeyConstraint(ovSurveyVisitID);
                
                Column ovSurveySurveyDate = ovSurvey.addColumn("SurveyDate", new TimestampDataType());
                ovSurveySurveyDate.setNotNull();
                
                Column ovSurveyWaitingRoomMinutes = ovSurvey.addColumn("WaitingRoomMinutes", new IntDataType());
                
                Column ovSurveyExamRoomMinutes = ovSurvey.addColumn("ExamRoomMinutes", new IntDataType());
                
                Column ovSurveyVisitSatisfcation = ovSurvey.addColumn("VisitSatisfaction", new IntDataType());
                
                Column ovSurveyTreatmentSatisfaction = ovSurvey.addColumn("TreatmentSatisfaction", new IntDataType());
                
                /*     
                    CREATE TABLE LOINC (
                        LaboratoryProcedureCode VARCHAR (7), 
                        Component VARCHAR(100),
                        KindOfProperty VARCHAR(100),
                        TimeAspect VARCHAR(100),
                        System VARCHAR(100),
                        ScaleType VARCHAR(100),
                        MethodType VARCHAR(100)
                    );
                */
                
                Table loinc = createTable("LOINC");
                
                Column loincLaboratoryProcedureCode = loinc.addColumn("LaboratoryProcedureCode", new VarCharDataType(7));
                
                Column loincComponent = loinc.addColumn("Component", new VarCharDataType(100));
                
                Column loincKindOfPropery = loinc.addColumn("KindOfProperty", new VarCharDataType(100));
                
                Column loincTimeAspect = loinc.addColumn("TimeAspect", new VarCharDataType(100));
                
                Column loincSystem = loinc.addColumn("System", new VarCharDataType(100));
                
                Column loincScaleType = loinc.addColumn("ScaleType", new VarCharDataType(100));
                
                Column loincMethodType = loinc.addColumn("MethodType", new VarCharDataType(100));
                
                /*     
                    CREATE TABLE LabProcedure (
                        LaboratoryProcedureID INT primary key,
                        PatientMID int , 
                        LaboratoryProcedureCode VARCHAR (7), 
                        Rights VARCHAR(10),
                        Status VARCHAR(20),
                        Commentary VARCHAR(50),
                        Results VARCHAR(50),
                        NumericalResults VARCHAR(20),
                        NumericalResultsUnit VARCHAR(20),
                        UpperBound VARCHAR(20),
                        LowerBound VARCHAR(20),	
                        OfficeVisitID INT ,
                        LabTechID INT,
                        PriorityCode INT ,
                        ViewedByPatient BOOLEAN NOT NULL,
                        UpdatedDate timestamp NOT NULL
                    );
                */
                
                Table labProcedure = createTable("LabProcedure");
                
                Column labProcedureLaboratoryProcedureID = labProcedure.addColumn("LaboratoryProcedureID", new IntDataType());
                labProcedure.setPrimaryKeyConstraint(labProcedureLaboratoryProcedureID);
                
                Column labProcedurePatientMID = labProcedure.addColumn("PatientMID", new IntDataType());
                
                Column labProcedureLaboratoryProcedureCode = labProcedure.addColumn("LaboratoryProcedureCode", new VarCharDataType(7));
                
                Column labProcedureRights = labProcedure.addColumn("Rights", new VarCharDataType(10));
                
                Column labProcedureStatus = labProcedure.addColumn("Status", new VarCharDataType(20));
                
                Column labProcedureCommentary = labProcedure.addColumn("Commentary", new VarCharDataType(50));
                
                Column labProcedureResults = labProcedure.addColumn("Results", new VarCharDataType(50));
                
                Column labProcedureNumericalResults = labProcedure.addColumn("NumericalResults", new VarCharDataType(20));
                
                Column labProcedureNumericalResultsUnit = labProcedure.addColumn("NumericalResultsUnit", new VarCharDataType(20));
                
                Column labProcedureUpperBound = labProcedure.addColumn("UpperBound", new VarCharDataType(20));
                
                Column labProcedureLowerBound = labProcedure.addColumn("LowerBound", new VarCharDataType(20));
                
                Column labProcedureOfficeVisitID = labProcedure.addColumn("OfficeVisitID", new IntDataType());
                
                Column labProcedureLabTechID = labProcedure.addColumn("LabTechID", new IntDataType());
                
                Column labProcedurePriorityCode = labProcedure.addColumn("PriorityCode", new IntDataType());
                
                Column labProcedureViewedByPatient = labProcedure.addColumn("ViewedByPatient", new BooleanDataType());
                labProcedureViewedByPatient.setNotNull();
                
                Column labProcedureUpdatedDate = labProcedure.addColumn("UpdatedDate", new TimestampDataType());
                labProcedureUpdatedDate.setNotNull();
                
                /*     
                    CREATE TABLE message (
                        message_id          INT ,
                        parent_msg_id       INT ,
                        from_id             INT  NOT NULL,
                        to_id               INT  NOT NULL,
                        sent_date           TIMESTAMP NOT NULL,
                        message             VARCHAR(50),
                        subject				VARCHAR(50),
                        been_read			INT 
                    );
                */
                
                Table message = createTable("message");
                
                Column messageMessage_id = message.addColumn("message_id", new IntDataType());
                
                Column messageParent_msg_id = message.addColumn("parent_msg_id", new IntDataType());
                
                Column messageFrom_id = message.addColumn("from_id", new IntDataType());
                messageFrom_id.setNotNull();
                
                Column messageTo_id = message.addColumn("to_id", new IntDataType());
                messageTo_id.setNotNull();
                
                Column messageSent_date = message.addColumn("sent_date", new TimestampDataType());
                messageSent_date.setNotNull();
                
                Column messageMessage = message.addColumn("message", new VarCharDataType(50));
                
                Column messageSubject = message.addColumn("subject", new VarCharDataType(50));
                
                Column messageBeen_read = message.addColumn("been_read", new IntDataType());
                               
                /*     
                    CREATE TABLE Appointment (
                        appt_id				INT primary key,
                        doctor_id           INT  NOT NULL,
                        patient_id          INT  NOT NULL,
                        sched_date          TIMESTAMP NOT NULL,
                        appt_type           VARCHAR(30) NOT NULL,
                        comment				VARCHAR(50)
                    );
                */
                
                Table appointment = createTable("Appointment");
                
                Column appointmentAppt_id = appointment.addColumn("appt_id", new IntDataType());
                appointment.setPrimaryKeyConstraint(appointmentAppt_id);
                
                Column appointmentDoctor_id = appointment.addColumn("doctor_id", new IntDataType());
                appointmentDoctor_id.setNotNull();
                
                Column appointmentPatient_id = appointment.addColumn("patient_id", new IntDataType());
                appointmentPatient_id.setNotNull();
                
                Column appointmentSched_date = appointment.addColumn("sched_date", new TimestampDataType());
                appointmentSched_date.setNotNull();
                
                Column appointmentAppt_type = appointment.addColumn("appt_type", new VarCharDataType(30));
                appointmentAppt_type.setNotNull();
                
                Column appointmentComment = appointment.addColumn("comment", new VarCharDataType(50));
                
                /*     
                    CREATE TABLE AppointmentType (
                        apptType_id			INT primary key,
                        appt_type           VARCHAR(30) NOT NULL,
                        duration			INT  NOT NULL
                    );
                */
                
                Table appointmentType = createTable("AppointmentType");
                
                Column appointmentTypeApptType_id = appointmentType.addColumn("apptType_id", new IntDataType());
                appointmentType.setPrimaryKeyConstraint(appointmentTypeApptType_id);
                
                Column appointmentTypeAppt_type = appointmentType.addColumn("appt_type", new VarCharDataType(30));
                appointmentTypeAppt_type.setNotNull();
                
                Column appointmentTypeDuration = appointmentType.addColumn("duration", new IntDataType());
                appointmentTypeDuration.setNotNull();
                
                /*     
                    CREATE TABLE referrals (
                        id          INT,
                        PatientID          INT  NOT NULL,
                        SenderID               INT  NOT NULL,
                        ReceiverID           INT  NOT NULL,
                        ReferralDetails             VARCHAR(50),
                        OVID		INT  NOT NULL,
                        viewed_by_patient 	boolean NOT NULL,
                        viewed_by_HCP 	boolean NOT NULL,
                        TimeStamp TIMESTAMP NOT NULL,
                        PriorityCode INT ,
                        PRIMARY KEY (id)
                    );
                */
                
                Table referrals = createTable("referrals");
                
                Column referralsID = referrals.addColumn("id", new IntDataType());
                
                Column referralsPatientID = referrals.addColumn("PatientID", new IntDataType());
                referralsPatientID.setNotNull();
                
                Column referralsSenderID = referrals.addColumn("SenderID", new IntDataType());
                referralsSenderID.setNotNull();
                
                Column referralsReceiverID = referrals.addColumn("ReceiverID", new IntDataType());
                referralsReceiverID.setNotNull();
                
                Column referralsReferralDetails = referrals.addColumn("ReferralDetails", new VarCharDataType(50));
                
                Column referralsOVID = referrals.addColumn("OVID", new IntDataType());
                referralsOVID.setNotNull();
                
                Column referralsViewed_by_patient = referrals.addColumn("viewed_by_patient", new BooleanDataType());
                referralsViewed_by_patient.setNotNull();
                
                Column referralsViewed_by_HCP = referrals.addColumn("viewed_by_hcp", new BooleanDataType());
                referralsViewed_by_HCP.setNotNull();
                
                Column referralsTimeStamp = referrals.addColumn("TimeStamp", new TimestampDataType());
                referralsTimeStamp.setNotNull();
                
                Column referralsPriorityCode = referrals.addColumn("PriorityCode", new IntDataType());
                
                referrals.setPrimaryKeyConstraint(referralsID);
                
                /*     
                    CREATE TABLE RemoteMonitoringData (
                        id          INT,
                        PatientID          INT  NOT NULL,
                        systolicBloodPressure int,
                        diastolicBloodPressure int,
                        glucoseLevel int,
                        height int,
                        weight int,
                        pedometerReading int,
                        timeLogged timestamp NOT NULL,
                        ReporterRole		varchar(50),
                        ReporterID INT,
                        PRIMARY KEY (id)
                    );
                */
                
                Table remoteMonitoringData = createTable("RemoteMonitoringData");
                
                Column remoteMonitoringDataID = remoteMonitoringData.addColumn("id", new IntDataType());
                
                Column remoteMonitoringDataPatientID = remoteMonitoringData.addColumn("PatientID", new IntDataType());
                remoteMonitoringDataPatientID.setNotNull();
                
                Column remoteMonitoringDataSystolicBloodPressure = remoteMonitoringData.addColumn("systolicBloodPressure", new IntDataType());
                
                Column remoteMonitoringDataDiastolicBloodPressure = remoteMonitoringData.addColumn("diastolicBloodPressure", new IntDataType());
                
                Column remoteMonitoringDataGlucoseLevel = remoteMonitoringData.addColumn("glucoseLevel", new IntDataType());
                
                Column remoteMonitoringDataHeight = remoteMonitoringData.addColumn("height", new IntDataType());
                
                Column remoteMonitoringDataWeight = remoteMonitoringData.addColumn("weight", new IntDataType());
                
                Column remoteMonitoringDataPedometerReading = remoteMonitoringData.addColumn("pedometerReading", new IntDataType());
                
                Column remoteMonitoringDataTimeLogged = remoteMonitoringData.addColumn("timeLogged", new TimestampDataType());
                
                Column remoteMonitoringDataReporterRole = remoteMonitoringData.addColumn("ReporterRole", new VarCharDataType(50));
                
                Column remoteMonitoringDataReporterID = remoteMonitoringData.addColumn("ReporterID", new IntDataType());
                
                remoteMonitoringData.setPrimaryKeyConstraint(remoteMonitoringDataID);
                
                /*     
                    CREATE TABLE RemoteMonitoringLists (
                        PatientMID INT  default 0, 
                        HCPMID INT  default 0,
                        SystolicBloodPressure BOOLEAN,
                        DiastolicBloodPressure BOOLEAN,
                        GlucoseLevel BOOLEAN,
                        Height BOOLEAN,
                        Weight BOOLEAN,
                        PedometerReading BOOLEAN,
                        PRIMARY KEY  (PatientMID,HCPMID)
                    );
                */
                
                Table remoteMonitoringLists = createTable("RemoteMonitoringLists");
                
                Column remoteMonitoringListsPatientMID = remoteMonitoringLists.addColumn("PatientMID", new IntDataType());
                
                Column remoteMonitoringListsHCPMID = remoteMonitoringLists.addColumn("HCPMID", new IntDataType());
                
                Column remoteMonitoringListsSystolicBloodPressure = remoteMonitoringLists.addColumn("SystolicBloodPressure", new BooleanDataType());
                
                Column remoteMonitoringListsDiastolicBloodPressure = remoteMonitoringLists.addColumn("DiastolicBloodPressure", new BooleanDataType());
                
                Column remoteMonitoringListsGlucoseLevel = remoteMonitoringLists.addColumn("GlucoseLevel", new BooleanDataType());
                
                Column remoteMonitoringListsHeight = remoteMonitoringLists.addColumn("Height", new BooleanDataType());
                
                Column remoteMonitoringListsWeight = remoteMonitoringLists.addColumn("Weight", new BooleanDataType());
                
                Column remoteMonitoringListsPedometerReading = remoteMonitoringLists.addColumn("PedometerReading", new BooleanDataType());
                
                remoteMonitoringLists.setPrimaryKeyConstraint(remoteMonitoringListsPatientMID, remoteMonitoringListsHCPMID);
                
                /*     
                    CREATE TABLE AdverseEvents (
                        id INT primary key,
                        Status VARCHAR(10),
                        PatientMID INT,
                        PresImmu VARCHAR(50),
                        Code VARCHAR(20),
                        Comment VARCHAR(2000),
                        Prescriber INT,
                        TimeLogged timestamp
                    );
                */
                
                Table adverseEvents = createTable("AdverseEvents");
                
                Column adverseEventsID = adverseEvents.addColumn("id", new IntDataType());
                adverseEvents.setPrimaryKeyConstraint(adverseEventsID);
                
                Column adverseEventsStatus = adverseEvents.addColumn("Status", new VarCharDataType(10));
                
                Column adverseEventsPatientMID = adverseEvents.addColumn("PatientMID", new IntDataType());
                
                Column adverseEventsPresImmu = adverseEvents.addColumn("PresImmu", new VarCharDataType(50));
                
                Column adverseEventsCode = adverseEvents.addColumn("Code", new VarCharDataType(20));
                
                Column adverseEventsComment = adverseEvents.addColumn("Comment", new VarCharDataType(2000));
                
                Column adverseEventsPresciber = adverseEvents.addColumn("Prescriber", new IntDataType());
                
                Column adverseEventsTimeLogged = adverseEvents.addColumn("TimeLogged", new TimestampDataType());
                
                /*     
                    CREATE TABLE ProfilePhotos (
                        MID int primary key,
                        Photo varchar(50),
                        UpdatedDate timestamp NOT NULL
                    );
                */
                
                Table profilePhotos = createTable("ProfilePhotos");
                
                Column profilePhotosMID = profilePhotos.addColumn("MID", new IntDataType());
                profilePhotos.setPrimaryKeyConstraint(profilePhotosMID);
                
                Column profilePhotosPhoto = profilePhotos.addColumn("Photo", new VarCharDataType(50));
                
                Column profilePhotosUpdateDate = profilePhotos.addColumn("UpdatedDate", new TimestampDataType());
                profilePhotosUpdateDate.setNotNull();
                
                /*     
                    CREATE TABLE PatientSpecificInstructions (
                        id INT primary key,
                        VisitID INT,
                        Modified TIMESTAMP NOT NULL,
                        Name VARCHAR(100),
                        URL VARCHAR(250),
                        Comment VARCHAR(500)
                    );
                */
                
                Table patientSpecificInstructions = createTable("PatientSpecificInstructions");
                
                Column patientSpecificInstructionsID = patientSpecificInstructions.addColumn("id", new IntDataType());
                patientSpecificInstructions.setPrimaryKeyConstraint(patientSpecificInstructionsID);
                
                Column patientSpecificInstructionsVisitID = patientSpecificInstructions.addColumn("VisitID", new IntDataType());
                
                Column patientSpecificInstructionsModified = patientSpecificInstructions.addColumn("Modified", new TimestampDataType());
                patientSpecificInstructionsModified.setNotNull();
                
                Column patientSpecificInstructionsName = patientSpecificInstructions.addColumn("Name", new VarCharDataType(100));
                
                Column patientSpecificInstructionsURL = patientSpecificInstructions.addColumn("URL", new VarCharDataType(250));
                
                Column patientSpecificInstructionsComment = patientSpecificInstructions.addColumn("Comment", new VarCharDataType(500));
                
                /*     
                    CREATE TABLE ReferralMessage(
                        messageID  INT  NOT NULL, 
                        referralID INT  NOT NULL, 
                        PRIMARY KEY (messageID,referralID)
                    );
                */
                
                Table referralMessage = createTable("ReferralMessage");
                
                Column referralMessageMessageID = referralMessage.addColumn("messageID", new IntDataType());
                referralMessageMessageID.setNotNull();
                
                Column referralMessageReferralID =  referralMessage.addColumn("referralID", new IntDataType());
                referralMessageReferralID.setNotNull();
                
                referralMessage.setPrimaryKeyConstraint(referralMessageMessageID, referralMessageReferralID);
                
        }
}
