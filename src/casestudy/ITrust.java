package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.InCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.BooleanColumnType;
import org.schemaanalyst.schema.columntype.DateColumnType;
import org.schemaanalyst.schema.columntype.DecimalColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.NumericColumnType;
import org.schemaanalyst.schema.columntype.TimestampColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class ITrust extends Schema {

	private static final long serialVersionUID = -5210032013399175458L;

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
		
		Column usersMid = users.addColumn("MID", new IntColumnType());
		
		users.addColumn("Password", new VarCharColumnType(20));
		
		Column usersRole = users.addColumn("Role", new VarCharColumnType(20));
		usersRole.setNotNull();
		
		Column userSQuestion = users.addColumn("sQuestion", new VarCharColumnType(100));
		
		Column userSAnswer = users.addColumn("sAnswer", new VarCharColumnType(30));
		
		users.setPrimaryKeyConstraint(usersMid);
		users.addCheckConstraint(new InCheckPredicate(usersRole, "patient", "admin", "hcp", "uap", "er", "tester", "pha", "lt"));
		
	
		/* 
			CREATE TABLE Hospitals(
				HospitalID   varchar(10),
				HospitalName varchar(30) NOT NULL, 
				PRIMARY KEY (hospitalID)
			);
		 */
		
		Table hospitals = createTable("hospitals");
		
		Column hospitalID = hospitals.addColumn("HospitalID", new VarCharColumnType(10));
		
                Column hospitalName = hospitals.addColumn("HospitalName", new VarCharColumnType(30));
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
		
		Column personnelMid = personnel.addColumn("MID", new IntColumnType());
		personnelMid.setDefaultToNull();
		
		Column personnelAMid = personnel.addColumn("AMID", new IntColumnType());
		personnelAMid.setDefaultToNull();
		
		Column personnelRole = personnel.addColumn("role", new VarCharColumnType(20));
		
		Column personnelEnabled = personnel.addColumn("enabled", new IntColumnType());
		personnelEnabled.setNotNull();
		
		Column personnelLastName = personnel.addColumn("lastName", new VarCharColumnType(20));
		personnelLastName.setNotNull();
		
		Column personnelFirstName = personnel.addColumn("firstName", new VarCharColumnType(20));
		personnelFirstName.setNotNull();
		
		Column personnelAddress1 = personnel.addColumn("address1", new VarCharColumnType(20));
		personnelAddress1.setNotNull();			
		
		Column personnelAddress2 = personnel.addColumn("address2", new VarCharColumnType(20));
		personnelAddress2.setNotNull();
		
		Column personnelCity = personnel.addColumn("city", new VarCharColumnType(15));
		personnelCity.setNotNull();
		
		Column personnelState = personnel.addColumn("state", new VarCharColumnType(2));
		personnelState.setNotNull();
		
		Column personnelZip = personnel.addColumn("zip", new VarCharColumnType(10));
		personnelZip.setNotNull();
		
		Column personnelZip1 = personnel.addColumn("zip1", new VarCharColumnType(5));
		
		Column personnelZip2 = personnel.addColumn("zip2", new VarCharColumnType(4));
		
		Column personnelPhone = personnel.addColumn("phone", new VarCharColumnType(12));
		
		Column personnelPhone1 = personnel.addColumn("phone1", new VarCharColumnType(3));
		
		Column personnelPhone2 = personnel.addColumn("phone2", new VarCharColumnType(3));

		Column personnelPhone3 = personnel.addColumn("phone3", new VarCharColumnType(4));
		
		Column personnelSpeciality = personnel.addColumn("speciality", new VarCharColumnType(40));
		
		Column personnelEmail = personnel.addColumn("email", new VarCharColumnType(55));
		
		Column personnelMessageFilter = personnel.addColumn("MessageFilter", new VarCharColumnType(60));
		
		personnel.setPrimaryKeyConstraint(personnelMid);
		personnel.addCheckConstraint(new InCheckPredicate(personnelRole, "admin", "hcp", "uap", "er", "tester", "pha", "lt"));
		personnel.addCheckConstraint(new InCheckPredicate(personnelState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));
                
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
                
                Column patientsMid = patients.addColumn("MID", new IntColumnType());
                
                Column patientsLastName = patients.addColumn("lastName", new VarCharColumnType(20));
                
                Column patientsfirstName = patients.addColumn("firstName", new VarCharColumnType(20));
                
                Column patientsEmail = patients.addColumn("email", new VarCharColumnType(55));
                
                Column patientsAddress1 = patients.addColumn("address1", new VarCharColumnType(20));

                Column patientsAddress2 = patients.addColumn("address2", new VarCharColumnType(20));

		Column patientsCity = patients.addColumn("city", new VarCharColumnType(15));

		Column patientsState = patients.addColumn("state", new VarCharColumnType(2));

		Column patientsZip1 = patients.addColumn("zip1", new VarCharColumnType(5));

		Column patientsZip2 = patients.addColumn("zip2", new VarCharColumnType(4));
               
		Column patientsPhone1 = patients.addColumn("phone1", new VarCharColumnType(3));
                
		Column patientsPhone2 = patients.addColumn("phone2", new VarCharColumnType(3));

		Column patientsPhone3 = patients.addColumn("phone3", new VarCharColumnType(4));

		Column patientsEName = patients.addColumn("eName", new VarCharColumnType(40));

		Column patientsEPhone1 = patients.addColumn("ePhone1", new VarCharColumnType(3));

		Column patientsEPhone2 = patients.addColumn("ePhone2", new VarCharColumnType(3));

		Column patientsEPhone3 = patients.addColumn("ePhone3", new VarCharColumnType(4));

		Column patientsICName = patients.addColumn("iCName", new VarCharColumnType(20));

		Column patientsICAddress1 = patients.addColumn("iCAddress1", new VarCharColumnType(20));

		Column patientsICAddress2 = patients.addColumn("iCAddress2", new VarCharColumnType(20));

		Column patientsICCity = patients.addColumn("iCCity", new VarCharColumnType(15));

		Column patientsICState = patients.addColumn("ICState", new VarCharColumnType(2));

		Column patientsICZip1 = patients.addColumn("iCZip1", new VarCharColumnType(5));

		Column patientsICZip2 = patients.addColumn("iCZip2", new VarCharColumnType(4));

		Column patientsICPhone1 = patients.addColumn("iCPhone1", new VarCharColumnType(3));

		Column patientsICPhone2 = patients.addColumn("iCPhone2", new VarCharColumnType(3));

		Column patientsICPhone3 = patients.addColumn("iCPhone3", new VarCharColumnType(4));
					
		Column patientsICID = patients.addColumn("iCID", new VarCharColumnType(20));
		 
		Column patientsDateOfBirth = patients.addColumn("DateOfBirth", new DateColumnType());

		Column patientsDateOfDeath = patients.addColumn("DateOfDeath", new DateColumnType());

		Column patientsCauseOfDeath = patients.addColumn("CauseOfDeath", new VarCharColumnType(10));

		Column patientsMotherMID = patients.addColumn("MotherMID", new IntegerColumnType());

		Column patientsFatherMID = patients.addColumn("FatherMID", new IntegerColumnType());

		Column patientsBloodType = patients.addColumn("BloodType", new VarCharColumnType(3));

		Column patientsEthnicity = patients.addColumn("Ethnicity", new VarCharColumnType(20));

		Column patientsGender = patients.addColumn("Gender", new VarCharColumnType(13));

		Column patientsTopicalNotes = patients.addColumn("TopicalNotes", new VarCharColumnType(200));

		Column patientsCreditCardType = patients.addColumn("CreditCardType", new VarCharColumnType(20));

		Column patientsCreditCardNumber = patients.addColumn("CreditCardNumber", new VarCharColumnType(19));

		Column patientsMessageFilter = patients.addColumn("MessageFilter", new VarCharColumnType(60));

		Column patientsDirectionsToHome = patients.addColumn("DirectionsToHome", new VarCharColumnType(512));

		Column patientsReligion = patients.addColumn("Religion", new VarCharColumnType(64));

		Column patientsLanguage = patients.addColumn("Language", new VarCharColumnType(32));

		Column patientsSpiritualPractices = patients.addColumn("SpiritualPractices", new VarCharColumnType(100));

		Column patientsAlternateName = patients.addColumn("AlternateName", new VarCharColumnType(32));
                
                patients.setPrimaryKeyConstraint(patientsMid);
                patients.addCheckConstraint(new InCheckPredicate(patientsState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));
                patients.addCheckConstraint(new InCheckPredicate(patientsICState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));

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
                
                Column historyPatientsId = historyPatients.addColumn("ID", new IntColumnType());
                
                Column historyPatientsChangeDate = historyPatients.addColumn("changeDate", new DateColumnType());
                historyPatientsChangeDate.setNotNull();
                
                Column historyPatientsChangeMid = historyPatients.addColumn("changeMID", new IntColumnType());
                historyPatientsChangeMid.setNotNull();
                
                Column historyPatientsMid = historyPatients.addColumn("MID", new IntColumnType());
                historyPatientsMid.setNotNull();
                
                Column historyPatientsLastName = historyPatients.addColumn("lastName", new VarCharColumnType(20));

                Column historyPatientsfirstName = historyPatients.addColumn("firstName", new VarCharColumnType(20));

                Column historyPatientsEmail = historyPatients.addColumn("email", new VarCharColumnType(55));

                Column historyPatientsAddress1 = historyPatients.addColumn("address1", new VarCharColumnType(20));

                Column historyPatientsAddress2 = historyPatients.addColumn("address2", new VarCharColumnType(20));

                Column historyPatientsCity = historyPatients.addColumn("city", new VarCharColumnType(15));

                Column historyPatientsState = historyPatients.addColumn("state", new VarCharColumnType(2));

                Column historyPatientsZip1 = historyPatients.addColumn("zip1", new VarCharColumnType(5));

                Column historyPatientsZip2 = historyPatients.addColumn("zip2", new VarCharColumnType(4));

                Column historyPatientsPhone1 = historyPatients.addColumn("phone1", new VarCharColumnType(3));

                Column historyPatientsPhone2 = historyPatients.addColumn("phone2", new VarCharColumnType(3));

                Column historyPatientsPhone3 = historyPatients.addColumn("phone3", new VarCharColumnType(4));

                Column historyPatientsEName = historyPatients.addColumn("eName", new VarCharColumnType(40));

                Column historyPatientsEPhone1 = historyPatients.addColumn("ePhone1", new VarCharColumnType(3));

                Column historyPatientsEPhone2 = historyPatients.addColumn("ePhone2", new VarCharColumnType(3));

                Column historyPatientsEPhone3 = historyPatients.addColumn("ePhone3", new VarCharColumnType(4));

                Column historyPatientsICName = historyPatients.addColumn("iCName", new VarCharColumnType(20));

                Column historyPatientsICAddress1 = historyPatients.addColumn("iCAddress1", new VarCharColumnType(20));

                Column historyPatientsICAddress2 = historyPatients.addColumn("iCAddress2", new VarCharColumnType(20));

                Column historyPatientsICCity = historyPatients.addColumn("iCCity", new VarCharColumnType(15));

                Column historyPatientsICState = historyPatients.addColumn("ICState", new VarCharColumnType(2));

                Column historyPatientsICZip1 = historyPatients.addColumn("iCZip1", new VarCharColumnType(5));

                Column historyPatientsICZip2 = historyPatients.addColumn("iCZip2", new VarCharColumnType(4));

                Column historyPatientsICPhone1 = historyPatients.addColumn("iCPhone1", new VarCharColumnType(3));

                Column historyPatientsICPhone2 = historyPatients.addColumn("iCPhone2", new VarCharColumnType(3));

                Column historyPatientsICPhone3 = historyPatients.addColumn("iCPhone3", new VarCharColumnType(4));

                Column historyPatientsICID = historyPatients.addColumn("iCID", new VarCharColumnType(20));

                Column historyPatientsDateOfBirth = historyPatients.addColumn("DateOfBirth", new DateColumnType());

                Column historyPatientsDateOfDeath = historyPatients.addColumn("DateOfDeath", new DateColumnType());

                Column historyPatientsCauseOfDeath = historyPatients.addColumn("CauseOfDeath", new VarCharColumnType(10));

                Column historyPatientsMotherMID = historyPatients.addColumn("MotherMID", new IntegerColumnType());

                Column historyPatientsFatherMID = historyPatients.addColumn("FatherMID", new IntegerColumnType());

                Column historyPatientsBloodType = historyPatients.addColumn("BloodType", new VarCharColumnType(3));

                Column historyPatientsEthnicity = historyPatients.addColumn("Ethnicity", new VarCharColumnType(20));

                Column historyPatientsGender = historyPatients.addColumn("Gender", new VarCharColumnType(13));

                Column historyPatientsTopicalNotes = historyPatients.addColumn("TopicalNotes", new VarCharColumnType(200));

                Column historyPatientsCreditCardType = historyPatients.addColumn("CreditCardType", new VarCharColumnType(20));

                Column historyPatientsCreditCardNumber = historyPatients.addColumn("CreditCardNumber", new VarCharColumnType(19));

                Column historyPatientsMessageFilter = historyPatients.addColumn("MessageFilter", new VarCharColumnType(60));

                Column historyPatientsDirectionsToHome = historyPatients.addColumn("DirectionsToHome", new VarCharColumnType(512));

                Column historyPatientsReligion = historyPatients.addColumn("Religion", new VarCharColumnType(64));

                Column historyPatientsLanguage = historyPatients.addColumn("Language", new VarCharColumnType(32));

                Column historyPatientsSpiritualPractices = historyPatients.addColumn("SpiritualPractices", new VarCharColumnType(100));

                Column historyPatientsAlternateName = historyPatients.addColumn("AlternateName", new VarCharColumnType(32));

                historyPatients.setPrimaryKeyConstraint(historyPatientsId);
                historyPatients.addCheckConstraint(new InCheckPredicate(historyPatientsState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));
                historyPatients.addCheckConstraint(new InCheckPredicate(historyPatientsICState, "", "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));

                /*     
                    CREATE TABLE LoginFailures(
                        ipaddress varchar(100) NOT NULL, 
                        failureCount int NOT NULL, 
                        lastFailure TIMESTAMP NOT NULL,
                        PRIMARY KEY (ipaddress)
                    );
                */
                
                Table loginFailures = createTable("LoginFailures");
                
                Column loginFailuresIpAddress = loginFailures.addColumn("ipaddress", new VarCharColumnType(100));
                loginFailuresIpAddress.setNotNull();
                
                Column loginFailuresFailureCount = loginFailures.addColumn("failureCount", new IntColumnType());
                loginFailuresFailureCount.setNotNull();
                
                Column loginFailuresLastFailure = loginFailures.addColumn("lastFailure", new TimestampColumnType());
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

                Column passwordResetFailuresIpAddress = passwordResetFailures.addColumn("ipaddress", new VarCharColumnType(128));
                passwordResetFailuresIpAddress.setNotNull();

                Column passwordResetFailuresFailureCount = passwordResetFailures.addColumn("failureCount", new IntColumnType());
                passwordResetFailuresFailureCount.setNotNull();

                Column passwordResetFailuresLastFailure = passwordResetFailures.addColumn("lastFailure", new TimestampColumnType());
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
                
                Column icdCodesCode = icdCodes.addColumn("Code", new NumericColumnType(5, 2));
                icdCodesCode.setNotNull();
                
                Column icdCodesDescription = icdCodes.addColumn("Description", new VarCharColumnType(50));
                icdCodesDescription.setNotNull();
                
                Column icdCodesChronic = icdCodes.addColumn("Chronic", new VarCharColumnType(3));
                icdCodesChronic.setNotNull();
                
                icdCodes.setPrimaryKeyConstraint(icdCodesCode);
                icdCodes.addCheckConstraint(new InCheckPredicate(icdCodesChronic, "no", "yes"));
                
                /*     
                    CREATE TABLE CPTCodes(
                        Code varchar(5) NOT NULL,
                        Description varchar(30) NOT NULL,
                        Attribute varchar(30),
                        PRIMARY KEY (Code)
                    );
                */
                
                Table cptCodes = createTable("CPTCodes");
                
                Column cptCodesCode = cptCodes.addColumn("Code", new VarCharColumnType(5));
                cptCodesCode.setNotNull();
                
                Column cptCodesDescription = cptCodes.addColumn("Description", new VarCharColumnType(30));
                cptCodesDescription.setNotNull();
                
                Column cptCodesAttribute = cptCodes.addColumn("Attribute", new VarCharColumnType(30));
                
                cptCodes.setPrimaryKeyConstraint(cptCodesCode);
                
                /*     
                    CREATE TABLE DrugReactionOverrideCodes(
                        Code varchar(5) NOT NULL,
                        Description varchar(80) NOT NULL,
                        PRIMARY KEY (Code)
                    );
                */
                
                Table drugReactionOverrideCodes = createTable("DrugReactionOverrideCode");
                
                Column drugReactionOverrideCodesCode = drugReactionOverrideCodes.addColumn("Code", new VarCharColumnType(5));
                drugReactionOverrideCodesCode.setNotNull();
                
                Column drugReactionOverrideCodesDescription = drugReactionOverrideCodes.addColumn("Desription", new VarCharColumnType(80));
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
                
                Column ndCodesCode = ndCodes.addColumn("Code", new VarCharColumnType(10));
                ndCodesCode.setNotNull();
                
                Column ndCodesDescription = ndCodes.addColumn("Description", new VarCharColumnType(100));
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
                
                Column drugInteractionsFirstDrug = drugInteractions.addColumn("FirstDrug", new VarCharColumnType(9));
                drugInteractionsFirstDrug.setNotNull();
                
                Column drugInteractionsSecondDrug = drugInteractions.addColumn("SecondDrug", new VarCharColumnType(9));
                drugInteractionsSecondDrug.setNotNull();
                
                Column drugInteractionsDescription = drugInteractions.addColumn("Description", new VarCharColumnType(100));
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
                
                Column transactionLogtransactionID = transactionLog.addColumn("transactionID", new IntColumnType());
                transactionLogtransactionID.setNotNull();
                
                Column transactionLogLoggedInMID = transactionLog.addColumn("loggedInMID", new IntColumnType());
                transactionLogLoggedInMID.setNotNull();
                
                Column transactionLogSecondaryMID = transactionLog.addColumn("secondaryMID", new IntColumnType());
                transactionLogSecondaryMID.setNotNull();
                
                Column transactionLogTransactionCode = transactionLog.addColumn("transactionCode", new IntColumnType());
                transactionLogTransactionCode.setNotNull();
                
                Column transactionLogTimeLogged = transactionLog.addColumn("timeLogged", new TimestampColumnType());
                transactionLogTimeLogged.setNotNull();
                
                Column transactionLogAddedInfo = transactionLog.addColumn("addedInfo", new VarCharColumnType(255));
                
                transactionLog.setPrimaryKeyConstraint(transactionLogtransactionID);
                
                /*     
                    CREATE TABLE HCPRelations(
                        HCP INT NOT NULL, 
                        UAP INT NOT NULL,
                        PRIMARY KEY (HCP, UAP)
                    );
                */
                
                Table hcpRelations = createTable("HCPRelations");
                
                Column hcpRelationsHCP = hcpRelations.addColumn("HCP", new IntColumnType());
                hcpRelationsHCP.setNotNull();
                
                Column hcpRelationsUAP = hcpRelations.addColumn("UAP", new IntColumnType());
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
                
                Column personalRelationsPatientID = personalRelations.addColumn("PatientID", new IntColumnType());
                personalRelationsPatientID.setNotNull();
                
                Column personalRelationsRelativeID = personalRelations.addColumn("RelativeID", new IntColumnType());
                personalRelationsRelativeID.setNotNull();
                
                Column personalRelationsRelativeType = personalRelations.addColumn("RelativeType", new VarCharColumnType(35));
                personalRelationsRelativeType.setNotNull();
                
                /*     
                    CREATE TABLE Representatives(
                        representerMID INT, 
                        representeeMID INT,
                        PRIMARY KEY  (representerMID,representeeMID)
                    );
                */
                
                Table representatives = createTable("Representatives");
                
                Column representativesRepresenterMID = representatives.addColumn("representerMID", new IntColumnType());
                
                Column representativesRepresenteeMID = representatives.addColumn("representeeMID", new IntColumnType());
                
                representatives.setPrimaryKeyConstraint(representativesRepresenterMID, representativesRepresenteeMID);
                
                /*     
                    CREATE TABLE HCPAssignedHos(
                        hosID VARCHAR(10) NOT NULL, 
                        HCPID INT  NOT NULL, 
                        PRIMARY KEY (hosID,HCPID)
                    );
                */
                
                Table hcpAssignedHos = createTable("HCPAssignedHos");
                
                Column hcpAssignedHosHosID = hcpAssignedHos.addColumn("hosID", new VarCharColumnType(10));
                hcpAssignedHosHosID.setNotNull();
                
                Column hcpAssignedHosHcpID = hcpAssignedHos.addColumn("HCPID", new IntColumnType());
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
                
                Column declaredHCPPatientID = declaredHCP.addColumn("PatientID", new IntColumnType());
                declaredHCPPatientID.setNotNull();
                
                Column declaredHCPHCPID = declaredHCP.addColumn("HPCID", new IntColumnType());
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
                
                Column officeVisitsID = officeVisits.addColumn("ID", new IntColumnType());
                
                Column officeVisitsVisitDate = officeVisits.addColumn("visitDate", new DateColumnType());
                
                Column officeVisitsHCPID = officeVisits.addColumn("HCPID", new IntColumnType());
                
                Column officeVisitsNotes = officeVisits.addColumn("notes", new VarCharColumnType(50));
                
                Column officeVisitsPatientID = officeVisits.addColumn("PatientID", new IntColumnType());
                
                Column officeVisitsHospitalID = officeVisits.addColumn("HospitalID", new VarCharColumnType(10));
                
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
                
                Column personalHealthInformationPatientID = personalHealthInformation.addColumn("PatientID", new IntColumnType());
                personalHealthInformationPatientID.setNotNull();
                
                Column personalHealthInformationHeight = personalHealthInformation.addColumn("Height", new IntColumnType());
                
                Column personalHealthInformationWeight = personalHealthInformation.addColumn("Weight", new IntColumnType());
                
                Column personalHealthInformationSmoker = personalHealthInformation.addColumn("Smoker", new IntColumnType());
                personalHealthInformationSmoker.setNotNull();
                
                Column personalHealthInformationSmokingStatus = personalHealthInformation.addColumn("SmokingStatus", new IntColumnType());
                personalHealthInformationSmokingStatus.setNotNull();
                
                Column personalHealthInformationBloodPressureN = personalHealthInformation.addColumn("BloodPressureN", new IntColumnType());
                
                Column personalHealthInformationBloodPressureD = personalHealthInformation.addColumn("BloodPressureD", new IntColumnType());
                
                Column personalHealthInformationCholesterolHDL = personalHealthInformation.addColumn("CholesterolHDL", new IntColumnType());
                
                Column personalHealthInformationCholesterolLDL = personalHealthInformation.addColumn("CholesterolLDL", new IntColumnType());
                
                Column personalHealthInformationCholesterolTri = personalHealthInformation.addColumn("CholesterolTri", new IntColumnType());
                
                Column personalHealthInformationHCPID = personalHealthInformation.addColumn("HCPID", new IntColumnType());
                
                Column personalHealthInformationAsOfDate = personalHealthInformation.addColumn("AsOfDate", new TimestampColumnType());
                personalHealthInformationAsOfDate.setNotNull();
                
                /*     
                    CREATE TABLE PersonalAllergies(
                        PatientID INT  NOT NULL,
                        Allergy VARCHAR( 50 ) NOT NULL 
                    );
                */
                
                Table personalAllergies = createTable("PersonalAllergies");
                
                Column personalAllergiesPatientID = personalAllergies.addColumn("PatientID", new IntColumnType());
                personalAllergiesPatientID.setNotNull();
                
                Column personalAllergiesAllergy = personalAllergies.addColumn("Allergy", new VarCharColumnType(50));
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
                
                Column allergiesID = allergies.addColumn("ID", new IntColumnType());
                allergies.setPrimaryKeyConstraint(allergiesID);
                
                Column allergiesPatientID = allergies.addColumn("PatientID", new IntColumnType());
                allergiesPatientID.setNotNull();
                
                Column allergiesDescription = allergies.addColumn("Description", new VarCharColumnType(50));
                allergiesDescription.setNotNull();
                
                Column allergiesFirstFound = allergies.addColumn("FirstFound", new TimestampColumnType());
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
                
                Column ovProcedureID = ovProcedure.addColumn("ID", new IntColumnType());
                ovProcedure.setPrimaryKeyConstraint(ovProcedureID);
                
                Column ovProcedureVisitID = ovProcedure.addColumn("VisitID", new IntColumnType());
                ovProcedureVisitID.setNotNull();
                
                Column ovProcedureCPTCode = ovProcedure.addColumn("CPTCode", new VarCharColumnType(5));
                ovProcedureCPTCode.setNotNull();
                
                Column ovProcedureHCPID = ovProcedure.addColumn("HCPID", new VarCharColumnType(10));
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
                
                Column ovMedicationID = ovMedication.addColumn("ID", new IntColumnType());
                ovMedication.setPrimaryKeyConstraint(ovMedicationID);
                
                Column ovMedicationVisitID = ovMedication.addColumn("VisitID", new IntColumnType());
                ovMedicationVisitID.setNotNull();
                
                Column ovMedicationNDCode = ovMedication.addColumn("NDCode", new VarCharColumnType(9));
                ovMedicationNDCode.setNotNull();
                
                Column ovMedicationStartDate = ovMedication.addColumn("StartDate", new DateColumnType());
                
                Column ovMedicationEndDate = ovMedication.addColumn("EndDate", new DateColumnType());
                
                Column ovMedicationDosage = ovMedication.addColumn("Dosage", new IntColumnType());
                
                Column ovMedicationInstructions = ovMedication.addColumn("Instructions", new VarCharColumnType(500));
                
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
                
                Column ovReactionOverrideID = ovReactionOverride.addColumn("ID", new IntColumnType());
                ovReactionOverride.setPrimaryKeyConstraint(ovReactionOverrideID);
                
                Column ovReactionOverrideOVMedicationID = ovReactionOverride.addColumn("OVMedicationID", new IntColumnType());
                ovReactionOverrideOVMedicationID.setNotNull();
                
                Column ovReactionOverrideOverrideCode = ovReactionOverride.addColumn("OverrideCode", new VarCharColumnType(5));
                
                Column ovReactionOverrideOverrideComment = ovReactionOverride.addColumn("OverrideComment", new VarCharColumnType(255));
                
                ovReactionOverride.addForeignKeyConstraint(ovMedication, ovReactionOverrideOVMedicationID, ovMedicationID);
                
                /*     
                    CREATE TABLE OVDiagnosis (
                        ID INT primary key,
                        VisitID INT  NOT NULL,
                        ICDCode DECIMAL( 5, 2 ) NOT NULL
                    );
                */
                
                Table ovDiagnosis = createTable("OVDiagnosis");
                
                Column ovDiagnosisID = ovDiagnosis.addColumn("ID", new IntColumnType());
                ovDiagnosis.setPrimaryKeyConstraint(ovDiagnosisID);
                
                Column ovDiagnosisVisitID = ovDiagnosis.addColumn("VisitID", new IntColumnType());
                ovDiagnosisVisitID.setNotNull();
                
                Column ovDiagnosisICDCode = ovDiagnosis.addColumn("ICDCode", new DecimalColumnType(5, 2));
                ovDiagnosisICDCode.setNotNull();
                
                /*     
                    CREATE TABLE GlobalVariables (
                        Name VARCHAR(20) primary key,
                        Value VARCHAR(20)
                    );
                */
                
                Table globalVariables = createTable("GlobalVariables");
                
                Column globalVariablesName = globalVariables.addColumn("Name", new VarCharColumnType(20));
                globalVariables.setPrimaryKeyConstraint(globalVariablesName);
                
                Column globalVariablesValue = globalVariables.addColumn("Value", new VarCharColumnType(20));
                
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
                
                Column fakeEmailID = fakeEmail.addColumn("ID", new IntColumnType());
                fakeEmail.setPrimaryKeyConstraint(fakeEmailID);
                
                Column fakeEmailToAddr = fakeEmail.addColumn("ToAddr", new VarCharColumnType(100));
                
                Column fakeEmailFromAddr = fakeEmail.addColumn("FromAddr", new VarCharColumnType(100));
                
                Column fakeEmailSubject = fakeEmail.addColumn("Subject", new VarCharColumnType(500));
                
                Column fakeEmailBody = fakeEmail.addColumn("Body", new VarCharColumnType(2000));
                
                Column fakeEmailAddedDate = fakeEmail.addColumn("AddedDate", new TimestampColumnType());
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
                
                Column reportRequestsID = reportRequests.addColumn("ID", new IntColumnType());
                reportRequests.setPrimaryKeyConstraint(reportRequestsID);
                
                Column reportRequestsRequesterMID = reportRequests.addColumn("RequesterMID", new IntColumnType());
                
                Column reportRequestsPatientMID = reportRequests.addColumn("PatientMID", new IntColumnType());
                
                Column reportRequestsApproverMID = reportRequests.addColumn("ApproverMID", new IntColumnType());
                
                Column reportRequestsRequestedDate = reportRequests.addColumn("RequestedDate", new TimestampColumnType());
                
                Column reportRequestsApprovedDate = reportRequests.addColumn("ApprovedDate", new TimestampColumnType());
                
                Column reportRequestsViewedDate = reportRequests.addColumn("ViewedDate", new TimestampColumnType());
                
                Column reportRequestsStatus = reportRequests.addColumn("Status", new VarCharColumnType(30));
                
                Column reportRequestsComment = reportRequests.addColumn("Comment", new VarCharColumnType(50));
                
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
                
                Column ovSurveyVisitID = ovSurvey.addColumn("VisitID", new IntColumnType());
                ovSurvey.setPrimaryKeyConstraint(ovSurveyVisitID);
                
                Column ovSurveySurveyDate = ovSurvey.addColumn("SurveyDate", new TimestampColumnType());
                ovSurveySurveyDate.setNotNull();
                
                Column ovSurveyWaitingRoomMinutes = ovSurvey.addColumn("WaitingRoomMinutes", new IntColumnType());
                
                Column ovSurveyExamRoomMinutes = ovSurvey.addColumn("ExamRoomMinutes", new IntColumnType());
                
                Column ovSurveyVisitSatisfcation = ovSurvey.addColumn("VisitSatisfaction", new IntColumnType());
                
                Column ovSurveyTreatmentSatisfaction = ovSurvey.addColumn("TreatmentSatisfaction", new IntColumnType());
                
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
                
                Column loincLaboratoryProcedureCode = loinc.addColumn("LaboratoryProcedureCode", new VarCharColumnType(7));
                
                Column loincComponent = loinc.addColumn("Component", new VarCharColumnType(100));
                
                Column loincKindOfPropery = loinc.addColumn("KindOfProperty", new VarCharColumnType(100));
                
                Column loincTimeAspect = loinc.addColumn("TimeAspect", new VarCharColumnType(100));
                
                Column loincSystem = loinc.addColumn("System", new VarCharColumnType(100));
                
                Column loincScaleType = loinc.addColumn("ScaleType", new VarCharColumnType(100));
                
                Column loincMethodType = loinc.addColumn("MethodType", new VarCharColumnType(100));
                
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
                
                Column labProcedureLaboratoryProcedureID = labProcedure.addColumn("LaboratoryProcedureID", new IntColumnType());
                labProcedure.setPrimaryKeyConstraint(labProcedureLaboratoryProcedureID);
                
                Column labProcedurePatientMID = labProcedure.addColumn("PatientMID", new IntColumnType());
                
                Column labProcedureLaboratoryProcedureCode = labProcedure.addColumn("LaboratoryProcedureCode", new VarCharColumnType(7));
                
                Column labProcedureRights = labProcedure.addColumn("Rights", new VarCharColumnType(10));
                
                Column labProcedureStatus = labProcedure.addColumn("Status", new VarCharColumnType(20));
                
                Column labProcedureCommentary = labProcedure.addColumn("Commentary", new VarCharColumnType(50));
                
                Column labProcedureResults = labProcedure.addColumn("Results", new VarCharColumnType(50));
                
                Column labProcedureNumericalResults = labProcedure.addColumn("NumericalResults", new VarCharColumnType(20));
                
                Column labProcedureNumericalResultsUnit = labProcedure.addColumn("NumericalResultsUnit", new VarCharColumnType(20));
                
                Column labProcedureUpperBound = labProcedure.addColumn("UpperBound", new VarCharColumnType(20));
                
                Column labProcedureLowerBound = labProcedure.addColumn("LowerBound", new VarCharColumnType(20));
                
                Column labProcedureOfficeVisitID = labProcedure.addColumn("OfficeVisitID", new IntColumnType());
                
                Column labProcedureLabTechID = labProcedure.addColumn("LabTechID", new IntColumnType());
                
                Column labProcedurePriorityCode = labProcedure.addColumn("PriorityCode", new IntColumnType());
                
                Column labProcedureViewedByPatient = labProcedure.addColumn("ViewedByPatient", new BooleanColumnType());
                labProcedureViewedByPatient.setNotNull();
                
                Column labProcedureUpdatedDate = labProcedure.addColumn("UpdatedDate", new TimestampColumnType());
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
                
                Column messageMessage_id = message.addColumn("message_id", new IntColumnType());
                
                Column messageParent_msg_id = message.addColumn("parent_msg_id", new IntColumnType());
                
                Column messageFrom_id = message.addColumn("from_id", new IntColumnType());
                messageFrom_id.setNotNull();
                
                Column messageTo_id = message.addColumn("to_id", new IntColumnType());
                messageTo_id.setNotNull();
                
                Column messageSent_date = message.addColumn("sent_date", new TimestampColumnType());
                messageSent_date.setNotNull();
                
                Column messageMessage = message.addColumn("message", new VarCharColumnType(50));
                
                Column messageSubject = message.addColumn("subject", new VarCharColumnType(50));
                
                Column messageBeen_read = message.addColumn("been_read", new IntColumnType());
                               
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
                
                Column appointmentAppt_id = appointment.addColumn("appt_id", new IntColumnType());
                appointment.setPrimaryKeyConstraint(appointmentAppt_id);
                
                Column appointmentDoctor_id = appointment.addColumn("doctor_id", new IntColumnType());
                appointmentDoctor_id.setNotNull();
                
                Column appointmentPatient_id = appointment.addColumn("patient_id", new IntColumnType());
                appointmentPatient_id.setNotNull();
                
                Column appointmentSched_date = appointment.addColumn("sched_date", new TimestampColumnType());
                appointmentSched_date.setNotNull();
                
                Column appointmentAppt_type = appointment.addColumn("appt_type", new VarCharColumnType(30));
                appointmentAppt_type.setNotNull();
                
                Column appointmentComment = appointment.addColumn("comment", new VarCharColumnType(50));
                
                /*     
                    CREATE TABLE AppointmentType (
                        apptType_id			INT primary key,
                        appt_type           VARCHAR(30) NOT NULL,
                        duration			INT  NOT NULL
                    );
                */
                
                Table appointmentType = createTable("AppointmentType");
                
                Column appointmentTypeApptType_id = appointmentType.addColumn("apptType_id", new IntColumnType());
                appointmentType.setPrimaryKeyConstraint(appointmentTypeApptType_id);
                
                Column appointmentTypeAppt_type = appointmentType.addColumn("appt_type", new VarCharColumnType(30));
                appointmentTypeAppt_type.setNotNull();
                
                Column appointmentTypeDuration = appointmentType.addColumn("duration", new IntColumnType());
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
                
                Column referralsID = referrals.addColumn("id", new IntColumnType());
                
                Column referralsPatientID = referrals.addColumn("PatientID", new IntColumnType());
                referralsPatientID.setNotNull();
                
                Column referralsSenderID = referrals.addColumn("SenderID", new IntColumnType());
                referralsSenderID.setNotNull();
                
                Column referralsReceiverID = referrals.addColumn("ReceiverID", new IntColumnType());
                referralsReceiverID.setNotNull();
                
                Column referralsReferralDetails = referrals.addColumn("ReferralDetails", new VarCharColumnType(50));
                
                Column referralsOVID = referrals.addColumn("OVID", new IntColumnType());
                referralsOVID.setNotNull();
                
                Column referralsViewed_by_patient = referrals.addColumn("viewed_by_patient", new BooleanColumnType());
                referralsViewed_by_patient.setNotNull();
                
                Column referralsViewed_by_HCP = referrals.addColumn("viewed_by_hcp", new BooleanColumnType());
                referralsViewed_by_HCP.setNotNull();
                
                Column referralsTimeStamp = referrals.addColumn("TimeStamp", new TimestampColumnType());
                referralsTimeStamp.setNotNull();
                
                Column referralsPriorityCode = referrals.addColumn("PriorityCode", new IntColumnType());
                
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
                
                Column remoteMonitoringDataID = remoteMonitoringData.addColumn("id", new IntColumnType());
                
                Column remoteMonitoringDataPatientID = remoteMonitoringData.addColumn("PatientID", new IntColumnType());
                remoteMonitoringDataPatientID.setNotNull();
                
                Column remoteMonitoringDataSystolicBloodPressure = remoteMonitoringData.addColumn("systolicBloodPressure", new IntColumnType());
                
                Column remoteMonitoringDataDiastolicBloodPressure = remoteMonitoringData.addColumn("diastolicBloodPressure", new IntColumnType());
                
                Column remoteMonitoringDataGlucoseLevel = remoteMonitoringData.addColumn("glucoseLevel", new IntColumnType());
                
                Column remoteMonitoringDataHeight = remoteMonitoringData.addColumn("height", new IntColumnType());
                
                Column remoteMonitoringDataWeight = remoteMonitoringData.addColumn("weight", new IntColumnType());
                
                Column remoteMonitoringDataPedometerReading = remoteMonitoringData.addColumn("pedometerReading", new IntColumnType());
                
                Column remoteMonitoringDataTimeLogged = remoteMonitoringData.addColumn("timeLogged", new TimestampColumnType());
                
                Column remoteMonitoringDataReporterRole = remoteMonitoringData.addColumn("ReporterRole", new VarCharColumnType(50));
                
                Column remoteMonitoringDataReporterID = remoteMonitoringData.addColumn("ReporterID", new IntColumnType());
                
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
                
                Column remoteMonitoringListsPatientMID = remoteMonitoringLists.addColumn("PatientMID", new IntColumnType());
                remoteMonitoringListsPatientMID.setDefault(0);
                
                Column remoteMonitoringListsHCPMID = remoteMonitoringLists.addColumn("HCPMID", new IntColumnType());
                remoteMonitoringListsHCPMID.setDefault(0);
                
                Column remoteMonitoringListsSystolicBloodPressure = remoteMonitoringLists.addColumn("SystolicBloodPressure", new BooleanColumnType());
                
                Column remoteMonitoringListsDiastolicBloodPressure = remoteMonitoringLists.addColumn("DiastolicBloodPressure", new BooleanColumnType());
                
                Column remoteMonitoringListsGlucoseLevel = remoteMonitoringLists.addColumn("GlucoseLevel", new BooleanColumnType());
                
                Column remoteMonitoringListsHeight = remoteMonitoringLists.addColumn("Height", new BooleanColumnType());
                
                Column remoteMonitoringListsWeight = remoteMonitoringLists.addColumn("Weight", new BooleanColumnType());
                
                Column remoteMonitoringListsPedometerReading = remoteMonitoringLists.addColumn("PedometerReading", new BooleanColumnType());
                
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
                
                Column adverseEventsID = adverseEvents.addColumn("id", new IntColumnType());
                adverseEvents.setPrimaryKeyConstraint(adverseEventsID);
                
                Column adverseEventsStatus = adverseEvents.addColumn("Status", new VarCharColumnType(10));
                
                Column adverseEventsPatientMID = adverseEvents.addColumn("PatientMID", new IntColumnType());
                
                Column adverseEventsPresImmu = adverseEvents.addColumn("PresImmu", new VarCharColumnType(50));
                
                Column adverseEventsCode = adverseEvents.addColumn("Code", new VarCharColumnType(20));
                
                Column adverseEventsComment = adverseEvents.addColumn("Comment", new VarCharColumnType(2000));
                
                Column adverseEventsPresciber = adverseEvents.addColumn("Prescriber", new IntColumnType());
                
                Column adverseEventsTimeLogged = adverseEvents.addColumn("TimeLogged", new TimestampColumnType());
                
                /*     
                    CREATE TABLE ProfilePhotos (
                        MID int primary key,
                        Photo varchar(50),
                        UpdatedDate timestamp NOT NULL
                    );
                */
                
                Table profilePhotos = createTable("ProfilePhotos");
                
                Column profilePhotosMID = profilePhotos.addColumn("MID", new IntColumnType());
                profilePhotos.setPrimaryKeyConstraint(profilePhotosMID);
                
                Column profilePhotosPhoto = profilePhotos.addColumn("Photo", new VarCharColumnType(50));
                
                Column profilePhotosUpdateDate = profilePhotos.addColumn("UpdatedDate", new TimestampColumnType());
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
                
                Column patientSpecificInstructionsID = patientSpecificInstructions.addColumn("id", new IntColumnType());
                patientSpecificInstructions.setPrimaryKeyConstraint(patientSpecificInstructionsID);
                
                Column patientSpecificInstructionsVisitID = patientSpecificInstructions.addColumn("VisitID", new IntColumnType());
                
                Column patientSpecificInstructionsModified = patientSpecificInstructions.addColumn("Modified", new TimestampColumnType());
                patientSpecificInstructionsModified.setNotNull();
                
                Column patientSpecificInstructionsName = patientSpecificInstructions.addColumn("Name", new VarCharColumnType(100));
                
                Column patientSpecificInstructionsURL = patientSpecificInstructions.addColumn("URL", new VarCharColumnType(250));
                
                Column patientSpecificInstructionsComment = patientSpecificInstructions.addColumn("Comment", new VarCharColumnType(500));
                
                /*     
                    CREATE TABLE ReferralMessage(
                        messageID  INT  NOT NULL, 
                        referralID INT  NOT NULL, 
                        PRIMARY KEY (messageID,referralID)
                    );
                */
                
                Table referralMessage = createTable("ReferralMessage");
                
                Column referralMessageMessageID = referralMessage.addColumn("messageID", new IntColumnType());
                referralMessageMessageID.setNotNull();
                
                Column referralMessageReferralID =  referralMessage.addColumn("referralID", new IntColumnType());
                referralMessageReferralID.setNotNull();
                
                referralMessage.setPrimaryKeyConstraint(referralMessageMessageID, referralMessageReferralID);
                
        }
}
