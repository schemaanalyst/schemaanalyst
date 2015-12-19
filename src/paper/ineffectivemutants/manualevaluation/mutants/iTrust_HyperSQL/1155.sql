-- 1155
-- UCColumnA
-- Added UNIQUE to column email in table Patients

CREATE TABLE "Users" (
	"MID"	INT	PRIMARY KEY,
	"Password"	VARCHAR(20),
	"Role"	VARCHAR(20)	NOT NULL,
	"sQuestion"	VARCHAR(100),
	"sAnswer"	VARCHAR(30),
	CHECK ("Role" IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt'))
)

CREATE TABLE "Hospitals" (
	"HospitalID"	VARCHAR(10)	PRIMARY KEY,
	"HospitalName"	VARCHAR(30)	NOT NULL
)

CREATE TABLE "Personnel" (
	"MID"	INT	PRIMARY KEY,
	"AMID"	INT,
	"role"	VARCHAR(20)	NOT NULL,
	"enabled"	INT	NOT NULL,
	"lastName"	VARCHAR(20)	NOT NULL,
	"firstName"	VARCHAR(20)	NOT NULL,
	"address1"	VARCHAR(20)	NOT NULL,
	"address2"	VARCHAR(20)	NOT NULL,
	"city"	VARCHAR(15)	NOT NULL,
	"state"	VARCHAR(2)	NOT NULL,
	"zip"	VARCHAR(10),
	"zip1"	VARCHAR(5),
	"zip2"	VARCHAR(4),
	"phone"	VARCHAR(12),
	"phone1"	VARCHAR(3),
	"phone2"	VARCHAR(3),
	"phone3"	VARCHAR(4),
	"specialty"	VARCHAR(40),
	"email"	VARCHAR(55),
	"MessageFilter"	VARCHAR(60),
	CHECK ("role" IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')),
	CHECK ("state" IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'))
)

CREATE TABLE "Patients" (
	"MID"	INT	PRIMARY KEY,
	"lastName"	VARCHAR(20),
	"firstName"	VARCHAR(20),
	"email"	VARCHAR(55)	UNIQUE,
	"address1"	VARCHAR(20),
	"address2"	VARCHAR(20),
	"city"	VARCHAR(15),
	"state"	VARCHAR(2),
	"zip1"	VARCHAR(5),
	"zip2"	VARCHAR(4),
	"phone1"	VARCHAR(3),
	"phone2"	VARCHAR(3),
	"phone3"	VARCHAR(4),
	"eName"	VARCHAR(40),
	"ePhone1"	VARCHAR(3),
	"ePhone2"	VARCHAR(3),
	"ePhone3"	VARCHAR(4),
	"iCName"	VARCHAR(20),
	"iCAddress1"	VARCHAR(20),
	"iCAddress2"	VARCHAR(20),
	"iCCity"	VARCHAR(15),
	"ICState"	VARCHAR(2),
	"iCZip1"	VARCHAR(5),
	"iCZip2"	VARCHAR(4),
	"iCPhone1"	VARCHAR(3),
	"iCPhone2"	VARCHAR(3),
	"iCPhone3"	VARCHAR(4),
	"iCID"	VARCHAR(20),
	"DateOfBirth"	DATE,
	"DateOfDeath"	DATE,
	"CauseOfDeath"	VARCHAR(10),
	"MotherMID"	INT,
	"FatherMID"	INT,
	"BloodType"	VARCHAR(3),
	"Ethnicity"	VARCHAR(20),
	"Gender"	VARCHAR(13),
	"TopicalNotes"	VARCHAR(200),
	"CreditCardType"	VARCHAR(20),
	"CreditCardNumber"	VARCHAR(19),
	"MessageFilter"	VARCHAR(60),
	"DirectionsToHome"	VARCHAR(512),
	"Religion"	VARCHAR(64),
	"Language"	VARCHAR(32),
	"SpiritualPractices"	VARCHAR(100),
	"AlternateName"	VARCHAR(32),
	CHECK ("state" IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')),
	CHECK ("ICState" IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'))
)

CREATE TABLE "HistoryPatients" (
	"ID"	INT	PRIMARY KEY,
	"changeDate"	DATE	NOT NULL,
	"changeMID"	INT	NOT NULL,
	"MID"	INT	NOT NULL,
	"lastName"	VARCHAR(20),
	"firstName"	VARCHAR(20),
	"email"	VARCHAR(55),
	"address1"	VARCHAR(20),
	"address2"	VARCHAR(20),
	"city"	VARCHAR(15),
	"state"	CHAR(2),
	"zip1"	VARCHAR(5),
	"zip2"	VARCHAR(4),
	"phone1"	VARCHAR(3),
	"phone2"	VARCHAR(3),
	"phone3"	VARCHAR(4),
	"eName"	VARCHAR(40),
	"ePhone1"	VARCHAR(3),
	"ePhone2"	VARCHAR(3),
	"ePhone3"	VARCHAR(4),
	"iCName"	VARCHAR(20),
	"iCAddress1"	VARCHAR(20),
	"iCAddress2"	VARCHAR(20),
	"iCCity"	VARCHAR(15),
	"ICState"	VARCHAR(2),
	"iCZip1"	VARCHAR(5),
	"iCZip2"	VARCHAR(4),
	"iCPhone1"	VARCHAR(3),
	"iCPhone2"	VARCHAR(3),
	"iCPhone3"	VARCHAR(4),
	"iCID"	VARCHAR(20),
	"DateOfBirth"	DATE,
	"DateOfDeath"	DATE,
	"CauseOfDeath"	VARCHAR(10),
	"MotherMID"	INT,
	"FatherMID"	INT,
	"BloodType"	VARCHAR(3),
	"Ethnicity"	VARCHAR(20),
	"Gender"	VARCHAR(13),
	"TopicalNotes"	VARCHAR(200),
	"CreditCardType"	VARCHAR(20),
	"CreditCardNumber"	VARCHAR(19),
	"MessageFilter"	VARCHAR(60),
	"DirectionsToHome"	VARCHAR(100),
	"Religion"	VARCHAR(64),
	"Language"	VARCHAR(32),
	"SpiritualPractices"	VARCHAR(100),
	"AlternateName"	VARCHAR(32),
	CHECK ("state" IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')),
	CHECK ("ICState" IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'))
)

CREATE TABLE "LoginFailures" (
	"ipaddress"	VARCHAR(100)	PRIMARY KEY	NOT NULL,
	"failureCount"	INT	NOT NULL,
	"lastFailure"	TIMESTAMP	NOT NULL
)

CREATE TABLE "ResetPasswordFailures" (
	"ipaddress"	VARCHAR(128)	PRIMARY KEY	NOT NULL,
	"failureCount"	INT	NOT NULL,
	"lastFailure"	TIMESTAMP	NOT NULL
)

CREATE TABLE "icdcodes" (
	"Code"	NUMERIC(5, 2)	PRIMARY KEY	NOT NULL,
	"Description"	VARCHAR(50)	NOT NULL,
	"Chronic"	VARCHAR(3)	NOT NULL,
	CHECK ("Chronic" IN ('no', 'yes'))
)

CREATE TABLE "CPTCodes" (
	"Code"	VARCHAR(5)	PRIMARY KEY	NOT NULL,
	"Description"	VARCHAR(30)	NOT NULL,
	"Attribute"	VARCHAR(30)
)

CREATE TABLE "DrugReactionOverrideCodes" (
	"Code"	VARCHAR(5)	PRIMARY KEY	NOT NULL,
	"Description"	VARCHAR(80)	NOT NULL
)

CREATE TABLE "NDCodes" (
	"Code"	VARCHAR(10)	PRIMARY KEY	NOT NULL,
	"Description"	VARCHAR(100)	NOT NULL
)

CREATE TABLE "DrugInteractions" (
	"FirstDrug"	VARCHAR(9)	NOT NULL,
	"SecondDrug"	VARCHAR(9)	NOT NULL,
	"Description"	VARCHAR(100)	NOT NULL,
	PRIMARY KEY ("FirstDrug", "SecondDrug")
)

CREATE TABLE "TransactionLog" (
	"transactionID"	INT	PRIMARY KEY	NOT NULL,
	"loggedInMID"	INT	NOT NULL,
	"secondaryMID"	INT	NOT NULL,
	"transactionCode"	INT	NOT NULL,
	"timeLogged"	TIMESTAMP	NOT NULL,
	"addedInfo"	VARCHAR(255)
)

CREATE TABLE "HCPRelations" (
	"HCP"	INT	NOT NULL,
	"UAP"	INT	NOT NULL,
	PRIMARY KEY ("HCP", "UAP")
)

CREATE TABLE "PersonalRelations" (
	"PatientID"	INT	NOT NULL,
	"RelativeID"	INT	NOT NULL,
	"RelativeType"	VARCHAR(35)	NOT NULL
)

CREATE TABLE "Representatives" (
	"representerMID"	INT,
	"representeeMID"	INT,
	PRIMARY KEY ("representerMID", "representeeMID")
)

CREATE TABLE "HCPAssignedHos" (
	"hosID"	VARCHAR(10)	NOT NULL,
	"HCPID"	INT	NOT NULL,
	PRIMARY KEY ("hosID", "HCPID")
)

CREATE TABLE "DeclaredHCP" (
	"PatientID"	INT	NOT NULL,
	"HCPID"	INT	NOT NULL,
	PRIMARY KEY ("PatientID", "HCPID")
)

CREATE TABLE "OfficeVisits" (
	"ID"	INT	PRIMARY KEY,
	"visitDate"	DATE,
	"HCPID"	INT,
	"notes"	VARCHAR(50),
	"PatientID"	INT,
	"HospitalID"	VARCHAR(10)
)

CREATE TABLE "PersonalHealthInformation" (
	"PatientID"	INT	NOT NULL,
	"Height"	INT,
	"Weight"	INT,
	"Smoker"	INT	NOT NULL,
	"SmokingStatus"	INT	NOT NULL,
	"BloodPressureN"	INT,
	"BloodPressureD"	INT,
	"CholesterolHDL"	INT,
	"CholesterolLDL"	INT,
	"CholesterolTri"	INT,
	"HCPID"	INT,
	"AsOfDate"	TIMESTAMP	NOT NULL
)

CREATE TABLE "PersonalAllergies" (
	"PatientID"	INT	NOT NULL,
	"Allergy"	VARCHAR(50)	NOT NULL
)

CREATE TABLE "Allergies" (
	"ID"	INT	PRIMARY KEY,
	"PatientID"	INT	NOT NULL,
	"Description"	VARCHAR(50)	NOT NULL,
	"FirstFound"	TIMESTAMP	NOT NULL
)

CREATE TABLE "OVProcedure" (
	"ID"	INT	PRIMARY KEY,
	"VisitID"	INT	NOT NULL,
	"CPTCode"	VARCHAR(5)	NOT NULL,
	"HCPID"	VARCHAR(10)	NOT NULL
)

CREATE TABLE "OVMedication" (
	"ID"	INT	PRIMARY KEY,
	"VisitID"	INT	NOT NULL,
	"NDCode"	VARCHAR(9)	NOT NULL,
	"StartDate"	DATE,
	"EndDate"	DATE,
	"Dosage"	INT,
	"Instructions"	VARCHAR(500)
)

CREATE TABLE "OVReactionOverride" (
	"ID"	INT	PRIMARY KEY,
	"OVMedicationID"	INT	 REFERENCES "OVMedication" ("ID")	NOT NULL,
	"OverrideCode"	VARCHAR(5),
	"OverrideComment"	VARCHAR(255)
)

CREATE TABLE "OVDiagnosis" (
	"ID"	INT	PRIMARY KEY,
	"VisitID"	INT	NOT NULL,
	"ICDCode"	DECIMAL(5, 2)	NOT NULL
)

CREATE TABLE "GlobalVariables" (
	"Name"	VARCHAR(20)	PRIMARY KEY,
	"Value"	VARCHAR(20)
)

CREATE TABLE "FakeEmail" (
	"ID"	INT	PRIMARY KEY,
	"ToAddr"	VARCHAR(100),
	"FromAddr"	VARCHAR(100),
	"Subject"	VARCHAR(500),
	"Body"	VARCHAR(2000),
	"AddedDate"	TIMESTAMP	NOT NULL
)

CREATE TABLE "ReportRequests" (
	"ID"	INT	PRIMARY KEY,
	"RequesterMID"	INT,
	"PatientMID"	INT,
	"ApproverMID"	INT,
	"RequestedDate"	TIMESTAMP,
	"ApprovedDate"	TIMESTAMP,
	"ViewedDate"	TIMESTAMP,
	"Status"	VARCHAR(30),
	"Comment"	VARCHAR(50)
)

CREATE TABLE "OVSurvey" (
	"VisitID"	INT	PRIMARY KEY,
	"SurveyDate"	TIMESTAMP	NOT NULL,
	"WaitingRoomMinutes"	INT,
	"ExamRoomMinutes"	INT,
	"VisitSatisfaction"	INT,
	"TreatmentSatisfaction"	INT
)

CREATE TABLE "LOINC" (
	"LaboratoryProcedureCode"	VARCHAR(7),
	"Component"	VARCHAR(100),
	"KindOfProperty"	VARCHAR(100),
	"TimeAspect"	VARCHAR(100),
	"System"	VARCHAR(100),
	"ScaleType"	VARCHAR(100),
	"MethodType"	VARCHAR(100)
)

CREATE TABLE "LabProcedure" (
	"LaboratoryProcedureID"	INT	PRIMARY KEY,
	"PatientMID"	INT,
	"LaboratoryProcedureCode"	VARCHAR(7),
	"Rights"	VARCHAR(10),
	"Status"	VARCHAR(20),
	"Commentary"	VARCHAR(50),
	"Results"	VARCHAR(50),
	"NumericalResults"	VARCHAR(20),
	"NumericalResultsUnit"	VARCHAR(20),
	"UpperBound"	VARCHAR(20),
	"LowerBound"	VARCHAR(20),
	"OfficeVisitID"	INT,
	"LabTechID"	INT,
	"PriorityCode"	INT,
	"ViewedByPatient"	BOOLEAN	NOT NULL,
	"UpdatedDate"	TIMESTAMP	NOT NULL
)

CREATE TABLE "message" (
	"message_id"	INT,
	"parent_msg_id"	INT,
	"from_id"	INT	NOT NULL,
	"to_id"	INT	NOT NULL,
	"sent_date"	TIMESTAMP	NOT NULL,
	"message"	VARCHAR(50),
	"subject"	VARCHAR(50),
	"been_read"	INT
)

CREATE TABLE "Appointment" (
	"appt_id"	INT	PRIMARY KEY,
	"doctor_id"	INT	NOT NULL,
	"patient_id"	INT	NOT NULL,
	"sched_date"	TIMESTAMP	NOT NULL,
	"appt_type"	VARCHAR(30)	NOT NULL,
	"comment"	VARCHAR(50)
)

CREATE TABLE "AppointmentType" (
	"apptType_id"	INT	PRIMARY KEY,
	"appt_type"	VARCHAR(30)	NOT NULL,
	"duration"	INT	NOT NULL
)

CREATE TABLE "referrals" (
	"id"	INT	PRIMARY KEY,
	"PatientID"	INT	NOT NULL,
	"SenderID"	INT	NOT NULL,
	"ReceiverID"	INT	NOT NULL,
	"ReferralDetails"	VARCHAR(50),
	"OVID"	INT	NOT NULL,
	"viewed_by_patient"	BOOLEAN	NOT NULL,
	"viewed_by_HCP"	BOOLEAN	NOT NULL,
	"TimeStamp"	TIMESTAMP	NOT NULL,
	"PriorityCode"	INT
)

CREATE TABLE "RemoteMonitoringData" (
	"id"	INT	PRIMARY KEY,
	"PatientID"	INT	NOT NULL,
	"systolicBloodPressure"	INT,
	"diastolicBloodPressure"	INT,
	"glucoseLevel"	INT,
	"height"	INT,
	"weight"	INT,
	"pedometerReading"	INT,
	"timeLogged"	TIMESTAMP	NOT NULL,
	"ReporterRole"	VARCHAR(50),
	"ReporterID"	INT
)

CREATE TABLE "RemoteMonitoringLists" (
	"PatientMID"	INT,
	"HCPMID"	INT,
	"SystolicBloodPressure"	BOOLEAN,
	"DiastolicBloodPressure"	BOOLEAN,
	"GlucoseLevel"	BOOLEAN,
	"Height"	BOOLEAN,
	"Weight"	BOOLEAN,
	"PedometerReading"	BOOLEAN,
	PRIMARY KEY ("PatientMID", "HCPMID")
)

CREATE TABLE "AdverseEvents" (
	"id"	INT	PRIMARY KEY,
	"Status"	VARCHAR(10),
	"PatientMID"	INT,
	"PresImmu"	VARCHAR(50),
	"Code"	VARCHAR(20),
	"Comment"	VARCHAR(2000),
	"Prescriber"	INT,
	"TimeLogged"	TIMESTAMP
)

CREATE TABLE "ProfilePhotos" (
	"MID"	INT	PRIMARY KEY,
	"Photo"	VARCHAR(50),
	"UpdatedDate"	TIMESTAMP	NOT NULL
)

CREATE TABLE "PatientSpecificInstructions" (
	"id"	INT	PRIMARY KEY,
	"VisitID"	INT,
	"Modified"	TIMESTAMP	NOT NULL,
	"Name"	VARCHAR(100),
	"URL"	VARCHAR(250),
	"Comment"	VARCHAR(500)
)

CREATE TABLE "ReferralMessage" (
	"messageID"	INT	NOT NULL,
	"referralID"	INT	NOT NULL,
	PRIMARY KEY ("messageID", "referralID")
)

