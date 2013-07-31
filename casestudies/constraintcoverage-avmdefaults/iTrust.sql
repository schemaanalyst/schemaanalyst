/**********************************
 * Constraint coverage for iTrust *
 **********************************/
DROP TABLE IF EXISTS ReferralMessage;
DROP TABLE IF EXISTS PatientSpecificInstructions;
DROP TABLE IF EXISTS ProfilePhotos;
DROP TABLE IF EXISTS AdverseEvents;
DROP TABLE IF EXISTS RemoteMonitoringLists;
DROP TABLE IF EXISTS RemoteMonitoringData;
DROP TABLE IF EXISTS referrals;
DROP TABLE IF EXISTS AppointmentType;
DROP TABLE IF EXISTS Appointment;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS LabProcedure;
DROP TABLE IF EXISTS LOINC;
DROP TABLE IF EXISTS OVSurvey;
DROP TABLE IF EXISTS ReportRequests;
DROP TABLE IF EXISTS FakeEmail;
DROP TABLE IF EXISTS GlobalVariables;
DROP TABLE IF EXISTS OVDiagnosis;
DROP TABLE IF EXISTS OVReactionOverride;
DROP TABLE IF EXISTS OVMedication;
DROP TABLE IF EXISTS OVProcedure;
DROP TABLE IF EXISTS Allergies;
DROP TABLE IF EXISTS PersonalAllergies;
DROP TABLE IF EXISTS PersonalHealthInformation;
DROP TABLE IF EXISTS OfficeVisits;
DROP TABLE IF EXISTS DeclaredHCP;
DROP TABLE IF EXISTS HCPAssignedHos;
DROP TABLE IF EXISTS Representatives;
DROP TABLE IF EXISTS PersonalRelations;
DROP TABLE IF EXISTS HCPRelations;
DROP TABLE IF EXISTS TransactionLog;
DROP TABLE IF EXISTS DrugInteractions;
DROP TABLE IF EXISTS NDCodes;
DROP TABLE IF EXISTS DrugReactionOverrideCodes;
DROP TABLE IF EXISTS CPTCodes;
DROP TABLE IF EXISTS icdcodes;
DROP TABLE IF EXISTS ResetPasswordFailures;
DROP TABLE IF EXISTS LoginFailures;
DROP TABLE IF EXISTS HistoryPatients;
DROP TABLE IF EXISTS Patients;
DROP TABLE IF EXISTS Personnel;
DROP TABLE IF EXISTS Hospitals;
DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
	MID	INT	PRIMARY KEY,
	Password	VARCHAR(20),
	Role	VARCHAR(20)	NOT NULL,
	sQuestion	VARCHAR(100),
	sAnswer	VARCHAR(30),
	CHECK (Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt'))
);
CREATE TABLE Hospitals (
	HospitalID	VARCHAR(10)	PRIMARY KEY,
	HospitalName	VARCHAR(30)	NOT NULL
);
CREATE TABLE Personnel (
	MID	INT	PRIMARY KEY,
	AMID	INT,
	role	VARCHAR(20)	NOT NULL,
	enabled	INT	NOT NULL,
	lastName	VARCHAR(20)	NOT NULL,
	firstName	VARCHAR(20)	NOT NULL,
	address1	VARCHAR(20)	NOT NULL,
	address2	VARCHAR(20)	NOT NULL,
	city	VARCHAR(15)	NOT NULL,
	state	VARCHAR(2)	NOT NULL,
	zip	VARCHAR(10),
	zip1	VARCHAR(5),
	zip2	VARCHAR(4),
	phone	VARCHAR(12),
	phone1	VARCHAR(3),
	phone2	VARCHAR(3),
	phone3	VARCHAR(4),
	specialty	VARCHAR(40),
	email	VARCHAR(55),
	MessageFilter	VARCHAR(60),
	CHECK (role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')),
	CHECK (state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'))
);
CREATE TABLE Patients (
	MID	INT	PRIMARY KEY,
	lastName	VARCHAR(20),
	firstName	VARCHAR(20),
	email	VARCHAR(55),
	address1	VARCHAR(20),
	address2	VARCHAR(20),
	city	VARCHAR(15),
	state	VARCHAR(2),
	zip1	VARCHAR(5),
	zip2	VARCHAR(4),
	phone1	VARCHAR(3),
	phone2	VARCHAR(3),
	phone3	VARCHAR(4),
	eName	VARCHAR(40),
	ePhone1	VARCHAR(3),
	ePhone2	VARCHAR(3),
	ePhone3	VARCHAR(4),
	iCName	VARCHAR(20),
	iCAddress1	VARCHAR(20),
	iCAddress2	VARCHAR(20),
	iCCity	VARCHAR(15),
	ICState	VARCHAR(2),
	iCZip1	VARCHAR(5),
	iCZip2	VARCHAR(4),
	iCPhone1	VARCHAR(3),
	iCPhone2	VARCHAR(3),
	iCPhone3	VARCHAR(4),
	iCID	VARCHAR(20),
	DateOfBirth	DATE,
	DateOfDeath	DATE,
	CauseOfDeath	VARCHAR(10),
	MotherMID	INT,
	FatherMID	INT,
	BloodType	VARCHAR(3),
	Ethnicity	VARCHAR(20),
	Gender	VARCHAR(13),
	TopicalNotes	VARCHAR(200),
	CreditCardType	VARCHAR(20),
	CreditCardNumber	VARCHAR(19),
	MessageFilter	VARCHAR(60),
	DirectionsToHome	VARCHAR(512),
	Religion	VARCHAR(64),
	Language	VARCHAR(32),
	SpiritualPractices	VARCHAR(100),
	AlternateName	VARCHAR(32),
	CHECK (state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')),
	CHECK (ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'))
);
CREATE TABLE HistoryPatients (
	ID	INT	PRIMARY KEY,
	changeDate	DATE	NOT NULL,
	changeMID	INT	NOT NULL,
	MID	INT	NOT NULL,
	lastName	VARCHAR(20),
	firstName	VARCHAR(20),
	email	VARCHAR(55),
	address1	VARCHAR(20),
	address2	VARCHAR(20),
	city	VARCHAR(15),
	state	CHAR(2),
	zip1	VARCHAR(5),
	zip2	VARCHAR(4),
	phone1	VARCHAR(3),
	phone2	VARCHAR(3),
	phone3	VARCHAR(4),
	eName	VARCHAR(40),
	ePhone1	VARCHAR(3),
	ePhone2	VARCHAR(3),
	ePhone3	VARCHAR(4),
	iCName	VARCHAR(20),
	iCAddress1	VARCHAR(20),
	iCAddress2	VARCHAR(20),
	iCCity	VARCHAR(15),
	ICState	VARCHAR(2),
	iCZip1	VARCHAR(5),
	iCZip2	VARCHAR(4),
	iCPhone1	VARCHAR(3),
	iCPhone2	VARCHAR(3),
	iCPhone3	VARCHAR(4),
	iCID	VARCHAR(20),
	DateOfBirth	DATE,
	DateOfDeath	DATE,
	CauseOfDeath	VARCHAR(10),
	MotherMID	INT,
	FatherMID	INT,
	BloodType	VARCHAR(3),
	Ethnicity	VARCHAR(20),
	Gender	VARCHAR(13),
	TopicalNotes	VARCHAR(200),
	CreditCardType	VARCHAR(20),
	CreditCardNumber	VARCHAR(19),
	MessageFilter	VARCHAR(60),
	DirectionsToHome	VARCHAR(100),
	Religion	VARCHAR(64),
	Language	VARCHAR(32),
	SpiritualPractices	VARCHAR(100),
	AlternateName	VARCHAR(32),
	CHECK (state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')),
	CHECK (ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'))
);
CREATE TABLE LoginFailures (
	ipaddress	VARCHAR(100)	PRIMARY KEY	NOT NULL,
	failureCount	INT	NOT NULL,
	lastFailure	TIMESTAMP	NOT NULL
);
CREATE TABLE ResetPasswordFailures (
	ipaddress	VARCHAR(128)	PRIMARY KEY	NOT NULL,
	failureCount	INT	NOT NULL,
	lastFailure	TIMESTAMP	NOT NULL
);
CREATE TABLE icdcodes (
	Code	NUMERIC(5, 2)	PRIMARY KEY	NOT NULL,
	Description	VARCHAR(50)	NOT NULL,
	Chronic	VARCHAR(3)	NOT NULL,
	CHECK (Chronic IN ('no', 'yes'))
);
CREATE TABLE CPTCodes (
	Code	VARCHAR(5)	PRIMARY KEY	NOT NULL,
	Description	VARCHAR(30)	NOT NULL,
	Attribute	VARCHAR(30)
);
CREATE TABLE DrugReactionOverrideCodes (
	Code	VARCHAR(5)	PRIMARY KEY	NOT NULL,
	Description	VARCHAR(80)	NOT NULL
);
CREATE TABLE NDCodes (
	Code	VARCHAR(10)	PRIMARY KEY	NOT NULL,
	Description	VARCHAR(100)	NOT NULL
);
CREATE TABLE DrugInteractions (
	FirstDrug	VARCHAR(9)	NOT NULL,
	SecondDrug	VARCHAR(9)	NOT NULL,
	Description	VARCHAR(100)	NOT NULL,
	PRIMARY KEY (FirstDrug, SecondDrug)
);
CREATE TABLE TransactionLog (
	transactionID	INT	PRIMARY KEY	NOT NULL,
	loggedInMID	INT	NOT NULL,
	secondaryMID	INT	NOT NULL,
	transactionCode	INT	NOT NULL,
	timeLogged	TIMESTAMP	NOT NULL,
	addedInfo	VARCHAR(255)
);
CREATE TABLE HCPRelations (
	HCP	INT	NOT NULL,
	UAP	INT	NOT NULL,
	PRIMARY KEY (HCP, UAP)
);
CREATE TABLE PersonalRelations (
	PatientID	INT	NOT NULL,
	RelativeID	INT	NOT NULL,
	RelativeType	VARCHAR(35)	NOT NULL
);
CREATE TABLE Representatives (
	representerMID	INT,
	representeeMID	INT,
	PRIMARY KEY (representerMID, representeeMID)
);
CREATE TABLE HCPAssignedHos (
	hosID	VARCHAR(10)	NOT NULL,
	HCPID	INT	NOT NULL,
	PRIMARY KEY (hosID, HCPID)
);
CREATE TABLE DeclaredHCP (
	PatientID	INT	NOT NULL,
	HCPID	INT	NOT NULL,
	PRIMARY KEY (PatientID, HCPID)
);
CREATE TABLE OfficeVisits (
	ID	INT	PRIMARY KEY,
	visitDate	DATE,
	HCPID	INT,
	notes	VARCHAR(50),
	PatientID	INT,
	HospitalID	VARCHAR(10)
);
CREATE TABLE PersonalHealthInformation (
	PatientID	INT	NOT NULL,
	Height	INT,
	Weight	INT,
	Smoker	INT	NOT NULL,
	SmokingStatus	INT	NOT NULL,
	BloodPressureN	INT,
	BloodPressureD	INT,
	CholesterolHDL	INT,
	CholesterolLDL	INT,
	CholesterolTri	INT,
	HCPID	INT,
	AsOfDate	TIMESTAMP	NOT NULL
);
CREATE TABLE PersonalAllergies (
	PatientID	INT	NOT NULL,
	Allergy	VARCHAR(50)	NOT NULL
);
CREATE TABLE Allergies (
	ID	INT	PRIMARY KEY,
	PatientID	INT	NOT NULL,
	Description	VARCHAR(50)	NOT NULL,
	FirstFound	TIMESTAMP	NOT NULL
);
CREATE TABLE OVProcedure (
	ID	INT	PRIMARY KEY,
	VisitID	INT	NOT NULL,
	CPTCode	VARCHAR(5)	NOT NULL,
	HCPID	VARCHAR(10)	NOT NULL
);
CREATE TABLE OVMedication (
	ID	INT	PRIMARY KEY,
	VisitID	INT	NOT NULL,
	NDCode	VARCHAR(9)	NOT NULL,
	StartDate	DATE,
	EndDate	DATE,
	Dosage	INT,
	Instructions	VARCHAR(500)
);
CREATE TABLE OVReactionOverride (
	ID	INT	PRIMARY KEY,
	OVMedicationID	INT	 REFERENCES OVMedication (ID)	NOT NULL,
	OverrideCode	VARCHAR(5),
	OverrideComment	VARCHAR(255)
);
CREATE TABLE OVDiagnosis (
	ID	INT	PRIMARY KEY,
	VisitID	INT	NOT NULL,
	ICDCode	DECIMAL(5, 2)	NOT NULL
);
CREATE TABLE GlobalVariables (
	Name	VARCHAR(20)	PRIMARY KEY,
	Value	VARCHAR(20)
);
CREATE TABLE FakeEmail (
	ID	INT	PRIMARY KEY,
	ToAddr	VARCHAR(100),
	FromAddr	VARCHAR(100),
	Subject	VARCHAR(500),
	Body	VARCHAR(2000),
	AddedDate	TIMESTAMP	NOT NULL
);
CREATE TABLE ReportRequests (
	ID	INT	PRIMARY KEY,
	RequesterMID	INT,
	PatientMID	INT,
	ApproverMID	INT,
	RequestedDate	TIMESTAMP,
	ApprovedDate	TIMESTAMP,
	ViewedDate	TIMESTAMP,
	Status	VARCHAR(30),
	Comment	VARCHAR(50)
);
CREATE TABLE OVSurvey (
	VisitID	INT	PRIMARY KEY,
	SurveyDate	TIMESTAMP	NOT NULL,
	WaitingRoomMinutes	INT,
	ExamRoomMinutes	INT,
	VisitSatisfaction	INT,
	TreatmentSatisfaction	INT
);
CREATE TABLE LOINC (
	LaboratoryProcedureCode	VARCHAR(7),
	Component	VARCHAR(100),
	KindOfProperty	VARCHAR(100),
	TimeAspect	VARCHAR(100),
	System	VARCHAR(100),
	ScaleType	VARCHAR(100),
	MethodType	VARCHAR(100)
);
CREATE TABLE LabProcedure (
	LaboratoryProcedureID	INT	PRIMARY KEY,
	PatientMID	INT,
	LaboratoryProcedureCode	VARCHAR(7),
	Rights	VARCHAR(10),
	Status	VARCHAR(20),
	Commentary	VARCHAR(50),
	Results	VARCHAR(50),
	NumericalResults	VARCHAR(20),
	NumericalResultsUnit	VARCHAR(20),
	UpperBound	VARCHAR(20),
	LowerBound	VARCHAR(20),
	OfficeVisitID	INT,
	LabTechID	INT,
	PriorityCode	INT,
	ViewedByPatient	BOOLEAN	NOT NULL,
	UpdatedDate	TIMESTAMP	NOT NULL
);
CREATE TABLE message (
	message_id	INT,
	parent_msg_id	INT,
	from_id	INT	NOT NULL,
	to_id	INT	NOT NULL,
	sent_date	TIMESTAMP	NOT NULL,
	message	VARCHAR(50),
	subject	VARCHAR(50),
	been_read	INT
);
CREATE TABLE Appointment (
	appt_id	INT	PRIMARY KEY,
	doctor_id	INT	NOT NULL,
	patient_id	INT	NOT NULL,
	sched_date	TIMESTAMP	NOT NULL,
	appt_type	VARCHAR(30)	NOT NULL,
	comment	VARCHAR(50)
);
CREATE TABLE AppointmentType (
	apptType_id	INT	PRIMARY KEY,
	appt_type	VARCHAR(30)	NOT NULL,
	duration	INT	NOT NULL
);
CREATE TABLE referrals (
	id	INT	PRIMARY KEY,
	PatientID	INT	NOT NULL,
	SenderID	INT	NOT NULL,
	ReceiverID	INT	NOT NULL,
	ReferralDetails	VARCHAR(50),
	OVID	INT	NOT NULL,
	viewed_by_patient	BOOLEAN	NOT NULL,
	viewed_by_HCP	BOOLEAN	NOT NULL,
	TimeStamp	TIMESTAMP	NOT NULL,
	PriorityCode	INT
);
CREATE TABLE RemoteMonitoringData (
	id	INT	PRIMARY KEY,
	PatientID	INT	NOT NULL,
	systolicBloodPressure	INT,
	diastolicBloodPressure	INT,
	glucoseLevel	INT,
	height	INT,
	weight	INT,
	pedometerReading	INT,
	timeLogged	TIMESTAMP	NOT NULL,
	ReporterRole	VARCHAR(50),
	ReporterID	INT
);
CREATE TABLE RemoteMonitoringLists (
	PatientMID	INT,
	HCPMID	INT,
	SystolicBloodPressure	BOOLEAN,
	DiastolicBloodPressure	BOOLEAN,
	GlucoseLevel	BOOLEAN,
	Height	BOOLEAN,
	Weight	BOOLEAN,
	PedometerReading	BOOLEAN,
	PRIMARY KEY (PatientMID, HCPMID)
);
CREATE TABLE AdverseEvents (
	id	INT	PRIMARY KEY,
	Status	VARCHAR(10),
	PatientMID	INT,
	PresImmu	VARCHAR(50),
	Code	VARCHAR(20),
	Comment	VARCHAR(2000),
	Prescriber	INT,
	TimeLogged	TIMESTAMP
);
CREATE TABLE ProfilePhotos (
	MID	INT	PRIMARY KEY,
	Photo	VARCHAR(50),
	UpdatedDate	TIMESTAMP	NOT NULL
);
CREATE TABLE PatientSpecificInstructions (
	id	INT	PRIMARY KEY,
	VisitID	INT,
	Modified	TIMESTAMP	NOT NULL,
	Name	VARCHAR(100),
	URL	VARCHAR(250),
	Comment	VARCHAR(500)
);
CREATE TABLE ReferralMessage (
	messageID	INT	NOT NULL,
	referralID	INT	NOT NULL,
	PRIMARY KEY (messageID, referralID)
);
-- Coverage: 268/268 (100.00000%) 
-- Time to generate: 6599ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 6202ms 
INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(1, '', 'er', '', '');
INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(0, '', 'er', '', '');
INSERT INTO Hospitals(HospitalID, HospitalName) VALUES('a', '');
INSERT INTO Hospitals(HospitalID, HospitalName) VALUES('', '');
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(1, 0, 'er', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'er', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(1, '', '', '', '', '', '', 'OR', '', '', '', '', '', '', '', '', '', '', '', '', '', 'OR', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '', '', '', '', '', '', 'OR', '', '', '', '', '', '', '', '', '', '', '', '', '', 'OR', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(1, '1000-01-01', 0, 0, '', '', '', '', '', '', 'OR', '', '', '', '', '', '', '', '', '', '', '', '', '', 'OR', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '1000-01-01', 0, 0, '', '', '', '', '', '', 'OR', '', '', '', '', '', '', '', '', '', '', '', '', '', 'OR', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('a', 0, '1970-01-01 00:00:00');
INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, '1970-01-01 00:00:00');
INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('a', 0, '1970-01-01 00:00:00');
INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, '1970-01-01 00:00:00');
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(1, '', 'no');
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(0, '', 'no');
INSERT INTO CPTCodes(Code, Description, Attribute) VALUES('a', '', '');
INSERT INTO CPTCodes(Code, Description, Attribute) VALUES('', '', '');
INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES('a', '');
INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES('', '');
INSERT INTO NDCodes(Code, Description) VALUES('a', '');
INSERT INTO NDCodes(Code, Description) VALUES('', '');
INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('a', '', '');
INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('', '', '');
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(1, 0, 0, 0, '1970-01-01 00:00:00', '');
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '');
INSERT INTO HCPRelations(HCP, UAP) VALUES(1, 0);
INSERT INTO HCPRelations(HCP, UAP) VALUES(0, 0);
INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(0, 0, '');
INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(0, 0, '');
INSERT INTO Representatives(representerMID, representeeMID) VALUES(1, 0);
INSERT INTO Representatives(representerMID, representeeMID) VALUES(0, 0);
INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES('a', 0);
INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES('', 0);
INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(1, 0);
INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(0, 0);
INSERT INTO OfficeVisits(ID, visitDate, HCPID, notes, PatientID, HospitalID) VALUES(1, '1000-01-01', 0, '', 0, '');
INSERT INTO OfficeVisits(ID, visitDate, HCPID, notes, PatientID, HospitalID) VALUES(0, '1000-01-01', 0, '', 0, '');
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
INSERT INTO PersonalAllergies(PatientID, Allergy) VALUES(0, '');
INSERT INTO PersonalAllergies(PatientID, Allergy) VALUES(0, '');
INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(1, 0, '', '1970-01-01 00:00:00');
INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(0, 0, '', '1970-01-01 00:00:00');
INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(1, 0, '', '');
INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(0, 0, '', '');
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(1, 0, '', '1000-01-01', '1000-01-01', 0, '');
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(0, 0, '', '1000-01-01', '1000-01-01', 0, '');
INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(1, 0, '', '');
INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(0, 0, '', '');
INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(1, 0, 0);
INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(0, 0, 0);
INSERT INTO GlobalVariables(Name, Value) VALUES('a', '');
INSERT INTO GlobalVariables(Name, Value) VALUES('', '');
INSERT INTO FakeEmail(ID, ToAddr, FromAddr, Subject, Body, AddedDate) VALUES(1, '', '', '', '', '1970-01-01 00:00:00');
INSERT INTO FakeEmail(ID, ToAddr, FromAddr, Subject, Body, AddedDate) VALUES(0, '', '', '', '', '1970-01-01 00:00:00');
INSERT INTO ReportRequests(ID, RequesterMID, PatientMID, ApproverMID, RequestedDate, ApprovedDate, ViewedDate, Status, Comment) VALUES(1, 0, 0, 0, '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', '');
INSERT INTO ReportRequests(ID, RequesterMID, PatientMID, ApproverMID, RequestedDate, ApprovedDate, ViewedDate, Status, Comment) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', '');
INSERT INTO OVSurvey(VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction) VALUES(1, '1970-01-01 00:00:00', 0, 0, 0, 0);
INSERT INTO OVSurvey(VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction) VALUES(0, '1970-01-01 00:00:00', 0, 0, 0, 0);
INSERT INTO LOINC(LaboratoryProcedureCode, Component, KindOfProperty, TimeAspect, System, ScaleType, MethodType) VALUES('', '', '', '', '', '', '');
INSERT INTO LOINC(LaboratoryProcedureCode, Component, KindOfProperty, TimeAspect, System, ScaleType, MethodType) VALUES('', '', '', '', '', '', '');
INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(1, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, FALSE, '1970-01-01 00:00:00');
INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(0, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, FALSE, '1970-01-01 00:00:00');
INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '', '', 0);
INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '', '', 0);
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(1, 0, 0, '1970-01-01 00:00:00', '', '');
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(0, 0, 0, '1970-01-01 00:00:00', '', '');
INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(1, '', 0);
INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(0, '', 0);
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(1, 0, 0, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, 0, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(1, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00', '', 0);
INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00', '', 0);
INSERT INTO RemoteMonitoringLists(PatientMID, HCPMID, SystolicBloodPressure, DiastolicBloodPressure, GlucoseLevel, Height, Weight, PedometerReading) VALUES(1, 0, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE);
INSERT INTO RemoteMonitoringLists(PatientMID, HCPMID, SystolicBloodPressure, DiastolicBloodPressure, GlucoseLevel, Height, Weight, PedometerReading) VALUES(0, 0, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE);
INSERT INTO AdverseEvents(id, Status, PatientMID, PresImmu, Code, Comment, Prescriber, TimeLogged) VALUES(1, '', 0, '', '', '', 0, '1970-01-01 00:00:00');
INSERT INTO AdverseEvents(id, Status, PatientMID, PresImmu, Code, Comment, Prescriber, TimeLogged) VALUES(0, '', 0, '', '', '', 0, '1970-01-01 00:00:00');
INSERT INTO ProfilePhotos(MID, Photo, UpdatedDate) VALUES(1, '', '1970-01-01 00:00:00');
INSERT INTO ProfilePhotos(MID, Photo, UpdatedDate) VALUES(0, '', '1970-01-01 00:00:00');
INSERT INTO PatientSpecificInstructions(id, VisitID, Modified, Name, URL, Comment) VALUES(1, 0, '1970-01-01 00:00:00', '', '', '');
INSERT INTO PatientSpecificInstructions(id, VisitID, Modified, Name, URL, Comment) VALUES(0, 0, '1970-01-01 00:00:00', '', '', '');
INSERT INTO ReferralMessage(messageID, referralID) VALUES(1, 0);
INSERT INTO ReferralMessage(messageID, referralID) VALUES(0, 0);
-- * Number of objective function evaluations: 2230
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[MID]" on table "Users"
-- * Success: true
-- * Time: 5ms 
INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(0, '', 'er', '', '');
-- * Number of objective function evaluations: 33
-- * Number of restarts: 0

-- Negating "NOT NULL(Role)" on table "Users"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(-1, '', NULL, '', '');
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "CHECK[Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]" on table "Users"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(-1, '', '', '', '');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[HospitalID]" on table "Hospitals"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Hospitals(HospitalID, HospitalName) VALUES('', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(HospitalName)" on table "Hospitals"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Hospitals(HospitalID, HospitalName) VALUES('b', NULL);
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[MID]" on table "Personnel"
-- * Success: true
-- * Time: 8ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'er', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 34
-- * Number of restarts: 0

-- Negating "NOT NULL(role)" on table "Personnel"
-- * Success: true
-- * Time: 3ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, NULL, 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(enabled)" on table "Personnel"
-- * Success: true
-- * Time: 16ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, 'er', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 52
-- * Number of restarts: 0

-- Negating "NOT NULL(lastName)" on table "Personnel"
-- * Success: true
-- * Time: 13ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, 'er', 0, NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 55
-- * Number of restarts: 0

-- Negating "NOT NULL(firstName)" on table "Personnel"
-- * Success: true
-- * Time: 16ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, 'er', 0, '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 57
-- * Number of restarts: 0

-- Negating "NOT NULL(address1)" on table "Personnel"
-- * Success: true
-- * Time: 18ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, 'er', 0, '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 59
-- * Number of restarts: 0

-- Negating "NOT NULL(address2)" on table "Personnel"
-- * Success: true
-- * Time: 19ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, 'er', 0, '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 61
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "Personnel"
-- * Success: true
-- * Time: 19ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, 'er', 0, '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 63
-- * Number of restarts: 0

-- Negating "NOT NULL(state)" on table "Personnel"
-- * Success: true
-- * Time: 17ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, 'er', 0, '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 65
-- * Number of restarts: 0

-- Negating "CHECK[role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]" on table "Personnel"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, '', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "CHECK[state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "Personnel"
-- * Success: true
-- * Time: 15ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(-1, 0, 'er', 0, '', '', '', '', '', 'a', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 66
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[MID]" on table "Patients"
-- * Success: true
-- * Time: 8ms 
INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 44
-- * Number of restarts: 0

-- Negating "CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "Patients"
-- * Success: true
-- * Time: 8ms 
INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(-1, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 48
-- * Number of restarts: 0

-- Negating "CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "Patients"
-- * Success: true
-- * Time: 3ms 
INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(-1, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "HistoryPatients"
-- * Success: true
-- * Time: 11ms 
INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '1000-01-01', 0, 0, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 57
-- * Number of restarts: 0

-- Negating "NOT NULL(changeDate)" on table "HistoryPatients"
-- * Success: true
-- * Time: 10ms 
INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(-1, NULL, 0, 0, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 54
-- * Number of restarts: 0

-- Negating "NOT NULL(changeMID)" on table "HistoryPatients"
-- * Success: true
-- * Time: 12ms 
INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(-1, '1000-01-01', NULL, 0, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 58
-- * Number of restarts: 0

-- Negating "NOT NULL(MID)" on table "HistoryPatients"
-- * Success: true
-- * Time: 12ms 
INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(-1, '1000-01-01', 0, NULL, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 58
-- * Number of restarts: 0

-- Negating "CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "HistoryPatients"
-- * Success: true
-- * Time: 11ms 
INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(-1, '1000-01-01', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 61
-- * Number of restarts: 0

-- Negating "CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "HistoryPatients"
-- * Success: true
-- * Time: 9ms 
INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(-1, '1000-01-01', 0, 0, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 33
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ipaddress]" on table "LoginFailures"
-- * Success: true
-- * Time: 0ms 
INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(ipaddress)" on table "LoginFailures"
-- * Success: true
-- * Time: 0ms 
INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES(NULL, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(failureCount)" on table "LoginFailures"
-- * Success: true
-- * Time: 3ms 
INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('b', NULL, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(lastFailure)" on table "LoginFailures"
-- * Success: true
-- * Time: 2ms 
INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('b', 0, NULL);
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ipaddress]" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(ipaddress)" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES(NULL, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(failureCount)" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 2ms 
INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('b', NULL, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(lastFailure)" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 2ms 
INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('b', 0, NULL);
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Code]" on table "icdcodes"
-- * Success: true
-- * Time: 6ms 
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(0, '', 'no');
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "NOT NULL(Code)" on table "icdcodes"
-- * Success: true
-- * Time: 5ms 
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(NULL, '', 'no');
-- * Number of objective function evaluations: 30
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "icdcodes"
-- * Success: true
-- * Time: 8ms 
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(-1, NULL, 'no');
-- * Number of objective function evaluations: 34
-- * Number of restarts: 0

-- Negating "NOT NULL(Chronic)" on table "icdcodes"
-- * Success: true
-- * Time: 1ms 
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(-1, '', NULL);
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "CHECK[Chronic IN ('no', 'yes')]" on table "icdcodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(-1, '', '');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Code]" on table "CPTCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO CPTCodes(Code, Description, Attribute) VALUES('', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(Code)" on table "CPTCodes"
-- * Success: true
-- * Time: 1ms 
INSERT INTO CPTCodes(Code, Description, Attribute) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "CPTCodes"
-- * Success: true
-- * Time: 2ms 
INSERT INTO CPTCodes(Code, Description, Attribute) VALUES('b', NULL, '');
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Code]" on table "DrugReactionOverrideCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES('', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(Code)" on table "DrugReactionOverrideCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "DrugReactionOverrideCodes"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES('b', NULL);
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Code]" on table "NDCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO NDCodes(Code, Description) VALUES('', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(Code)" on table "NDCodes"
-- * Success: true
-- * Time: 1ms 
INSERT INTO NDCodes(Code, Description) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "NDCodes"
-- * Success: true
-- * Time: 2ms 
INSERT INTO NDCodes(Code, Description) VALUES('b', NULL);
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[FirstDrug, SecondDrug]" on table "DrugInteractions"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(FirstDrug)" on table "DrugInteractions"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SecondDrug)" on table "DrugInteractions"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('', NULL, '');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "DrugInteractions"
-- * Success: true
-- * Time: 2ms 
INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('b', '', NULL);
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[transactionID]" on table "TransactionLog"
-- * Success: true
-- * Time: 0ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(transactionID)" on table "TransactionLog"
-- * Success: true
-- * Time: 1ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(NULL, 0, 0, 0, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(loggedInMID)" on table "TransactionLog"
-- * Success: true
-- * Time: 1ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(-1, NULL, 0, 0, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(secondaryMID)" on table "TransactionLog"
-- * Success: true
-- * Time: 1ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(-1, 0, NULL, 0, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(transactionCode)" on table "TransactionLog"
-- * Success: true
-- * Time: 1ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(-1, 0, 0, NULL, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(timeLogged)" on table "TransactionLog"
-- * Success: true
-- * Time: 2ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(-1, 0, 0, 0, NULL, '');
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[HCP, UAP]" on table "HCPRelations"
-- * Success: true
-- * Time: 1ms 
INSERT INTO HCPRelations(HCP, UAP) VALUES(0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(HCP)" on table "HCPRelations"
-- * Success: true
-- * Time: 0ms 
INSERT INTO HCPRelations(HCP, UAP) VALUES(NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(UAP)" on table "HCPRelations"
-- * Success: true
-- * Time: 0ms 
INSERT INTO HCPRelations(HCP, UAP) VALUES(0, NULL);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "PersonalRelations"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(NULL, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(RelativeID)" on table "PersonalRelations"
-- * Success: true
-- * Time: 1ms 
INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(0, NULL, '');
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(RelativeType)" on table "PersonalRelations"
-- * Success: true
-- * Time: 1ms 
INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(0, 0, NULL);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[representerMID, representeeMID]" on table "Representatives"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Representatives(representerMID, representeeMID) VALUES(0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[hosID, HCPID]" on table "HCPAssignedHos"
-- * Success: true
-- * Time: 0ms 
INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES('', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(hosID)" on table "HCPAssignedHos"
-- * Success: true
-- * Time: 0ms 
INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES(NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(HCPID)" on table "HCPAssignedHos"
-- * Success: true
-- * Time: 1ms 
INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES('', NULL);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[PatientID, HCPID]" on table "DeclaredHCP"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "DeclaredHCP"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(HCPID)" on table "DeclaredHCP"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(0, NULL);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "OfficeVisits"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OfficeVisits(ID, visitDate, HCPID, notes, PatientID, HospitalID) VALUES(0, '1000-01-01', 0, '', 0, '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Smoker)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 2ms 
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(SmokingStatus)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 3ms 
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(AsOfDate)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 7ms 
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL);
-- * Number of objective function evaluations: 35
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "PersonalAllergies"
-- * Success: true
-- * Time: 1ms 
INSERT INTO PersonalAllergies(PatientID, Allergy) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Allergy)" on table "PersonalAllergies"
-- * Success: true
-- * Time: 1ms 
INSERT INTO PersonalAllergies(PatientID, Allergy) VALUES(0, NULL);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "Allergies"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(0, 0, '', '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "Allergies"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(-1, NULL, '', '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "Allergies"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(-1, 0, NULL, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(FirstFound)" on table "Allergies"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(-1, 0, '', NULL);
-- * Number of objective function evaluations: 13
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "OVProcedure"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(0, 0, '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(VisitID)" on table "OVProcedure"
-- * Success: true
-- * Time: 1ms 
INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(-1, NULL, '', '');
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(CPTCode)" on table "OVProcedure"
-- * Success: true
-- * Time: 1ms 
INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(-1, 0, NULL, '');
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(HCPID)" on table "OVProcedure"
-- * Success: true
-- * Time: 2ms 
INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(-1, 0, '', NULL);
-- * Number of objective function evaluations: 13
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "OVMedication"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(0, 0, '', '1000-01-01', '1000-01-01', 0, '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(VisitID)" on table "OVMedication"
-- * Success: true
-- * Time: 1ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(-1, NULL, '', '1000-01-01', '1000-01-01', 0, '');
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(NDCode)" on table "OVMedication"
-- * Success: true
-- * Time: 1ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(-1, 0, NULL, '1000-01-01', '1000-01-01', 0, '');
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "OVReactionOverride"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(-1, 0, '', '1000-01-01', '1000-01-01', 0, '');
INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(0, 0, '', '');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[OVMedicationID]" on table "OVReactionOverride"
-- * Success: true
-- * Time: 10ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(46, -52, 'hctgp', '2019-01-06', '1999-05-22', -100, 'ycp');
INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(-18, 55, '', 'mssuu');
-- * Number of objective function evaluations: 76
-- * Number of restarts: 1

-- Negating "NOT NULL(OVMedicationID)" on table "OVReactionOverride"
-- * Success: true
-- * Time: 16ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(5, 79, 'nehqbigq', '2010-09-19', '2017-09-17', -92, 'fva');
INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(38, NULL, 'rbjtp', 'vty');
-- * Number of objective function evaluations: 126
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[ID]" on table "OVDiagnosis"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(VisitID)" on table "OVDiagnosis"
-- * Success: true
-- * Time: 1ms 
INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(-1, NULL, 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(ICDCode)" on table "OVDiagnosis"
-- * Success: true
-- * Time: 2ms 
INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(-1, 0, NULL);
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Name]" on table "GlobalVariables"
-- * Success: true
-- * Time: 0ms 
INSERT INTO GlobalVariables(Name, Value) VALUES('', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "FakeEmail"
-- * Success: true
-- * Time: 0ms 
INSERT INTO FakeEmail(ID, ToAddr, FromAddr, Subject, Body, AddedDate) VALUES(0, '', '', '', '', '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(AddedDate)" on table "FakeEmail"
-- * Success: true
-- * Time: 2ms 
INSERT INTO FakeEmail(ID, ToAddr, FromAddr, Subject, Body, AddedDate) VALUES(-1, '', '', '', '', NULL);
-- * Number of objective function evaluations: 16
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "ReportRequests"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ReportRequests(ID, RequesterMID, PatientMID, ApproverMID, RequestedDate, ApprovedDate, ViewedDate, Status, Comment) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[VisitID]" on table "OVSurvey"
-- * Success: true
-- * Time: 1ms 
INSERT INTO OVSurvey(VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction) VALUES(0, '1970-01-01 00:00:00', 0, 0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(SurveyDate)" on table "OVSurvey"
-- * Success: true
-- * Time: 1ms 
INSERT INTO OVSurvey(VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction) VALUES(-1, NULL, 0, 0, 0, 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[LaboratoryProcedureID]" on table "LabProcedure"
-- * Success: true
-- * Time: 1ms 
INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(0, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, FALSE, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(ViewedByPatient)" on table "LabProcedure"
-- * Success: true
-- * Time: 5ms 
INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(-1, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, NULL, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 38
-- * Number of restarts: 0

-- Negating "NOT NULL(UpdatedDate)" on table "LabProcedure"
-- * Success: true
-- * Time: 5ms 
INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(-1, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, FALSE, NULL);
-- * Number of objective function evaluations: 40
-- * Number of restarts: 0

-- Negating "NOT NULL(from_id)" on table "message"
-- * Success: true
-- * Time: 1ms 
INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, NULL, 0, '1970-01-01 00:00:00', '', '', 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(to_id)" on table "message"
-- * Success: true
-- * Time: 2ms 
INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, 0, NULL, '1970-01-01 00:00:00', '', '', 0);
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(sent_date)" on table "message"
-- * Success: true
-- * Time: 2ms 
INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, 0, 0, NULL, '', '', 0);
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[appt_id]" on table "Appointment"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(0, 0, 0, '1970-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(doctor_id)" on table "Appointment"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(-1, NULL, 0, '1970-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(patient_id)" on table "Appointment"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(-1, 0, NULL, '1970-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(sched_date)" on table "Appointment"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(-1, 0, 0, NULL, '', '');
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(appt_type)" on table "Appointment"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(-1, 0, 0, '1970-01-01 00:00:00', NULL, '');
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[apptType_id]" on table "AppointmentType"
-- * Success: true
-- * Time: 0ms 
INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(0, '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(appt_type)" on table "AppointmentType"
-- * Success: true
-- * Time: 1ms 
INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(-1, NULL, 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(duration)" on table "AppointmentType"
-- * Success: true
-- * Time: 2ms 
INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(-1, '', NULL);
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "referrals"
-- * Success: true
-- * Time: 1ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, 0, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "referrals"
-- * Success: true
-- * Time: 2ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(-1, NULL, 0, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(SenderID)" on table "referrals"
-- * Success: true
-- * Time: 1ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(-1, 0, NULL, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(ReceiverID)" on table "referrals"
-- * Success: true
-- * Time: 2ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(-1, 0, 0, NULL, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(OVID)" on table "referrals"
-- * Success: true
-- * Time: 2ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(-1, 0, 0, 0, '', NULL, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 19
-- * Number of restarts: 0

-- Negating "NOT NULL(viewed_by_patient)" on table "referrals"
-- * Success: true
-- * Time: 3ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(-1, 0, 0, 0, '', 0, NULL, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "NOT NULL(viewed_by_HCP)" on table "referrals"
-- * Success: true
-- * Time: 3ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(-1, 0, 0, 0, '', 0, FALSE, NULL, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

-- Negating "NOT NULL(TimeStamp)" on table "referrals"
-- * Success: true
-- * Time: 3ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(-1, 0, 0, 0, '', 0, FALSE, FALSE, NULL, 0);
-- * Number of objective function evaluations: 26
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "RemoteMonitoringData"
-- * Success: true
-- * Time: 1ms 
INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00', '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "RemoteMonitoringData"
-- * Success: true
-- * Time: 1ms 
INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(-1, NULL, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00', '', 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(timeLogged)" on table "RemoteMonitoringData"
-- * Success: true
-- * Time: 4ms 
INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(-1, 0, 0, 0, 0, 0, 0, 0, NULL, '', 0);
-- * Number of objective function evaluations: 29
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[PatientMID, HCPMID]" on table "RemoteMonitoringLists"
-- * Success: true
-- * Time: 0ms 
INSERT INTO RemoteMonitoringLists(PatientMID, HCPMID, SystolicBloodPressure, DiastolicBloodPressure, GlucoseLevel, Height, Weight, PedometerReading) VALUES(0, 0, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "AdverseEvents"
-- * Success: true
-- * Time: 0ms 
INSERT INTO AdverseEvents(id, Status, PatientMID, PresImmu, Code, Comment, Prescriber, TimeLogged) VALUES(0, '', 0, '', '', '', 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[MID]" on table "ProfilePhotos"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ProfilePhotos(MID, Photo, UpdatedDate) VALUES(0, '', '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(UpdatedDate)" on table "ProfilePhotos"
-- * Success: true
-- * Time: 1ms 
INSERT INTO ProfilePhotos(MID, Photo, UpdatedDate) VALUES(-1, '', NULL);
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "PatientSpecificInstructions"
-- * Success: true
-- * Time: 1ms 
INSERT INTO PatientSpecificInstructions(id, VisitID, Modified, Name, URL, Comment) VALUES(0, 0, '1970-01-01 00:00:00', '', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(Modified)" on table "PatientSpecificInstructions"
-- * Success: true
-- * Time: 1ms 
INSERT INTO PatientSpecificInstructions(id, VisitID, Modified, Name, URL, Comment) VALUES(-1, 0, NULL, '', '', '');
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[messageID, referralID]" on table "ReferralMessage"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ReferralMessage(messageID, referralID) VALUES(0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(messageID)" on table "ReferralMessage"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ReferralMessage(messageID, referralID) VALUES(NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(referralID)" on table "ReferralMessage"
-- * Success: true
-- * Time: 1ms 
INSERT INTO ReferralMessage(messageID, referralID) VALUES(0, NULL);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

