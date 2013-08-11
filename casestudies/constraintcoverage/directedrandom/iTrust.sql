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
-- Coverage: 90/268 (33.58209%) 
-- Time to generate: 637652ms 

-- Satisfying all constraints
-- * Success: false
-- * Time: 175986ms 
-- INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(0, '', 'lt', '', '');
-- INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(-52, '', 'lt', '', '');
-- INSERT INTO Hospitals(HospitalID, HospitalName) VALUES('', '');
-- INSERT INTO Hospitals(HospitalID, HospitalName) VALUES(NULL, '');
-- INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'er', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(92, 0, 'lt', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(79, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '1000-01-01', 0, 0, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(-33, '1000-01-01', 0, 0, '', '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, '1970-01-01 00:00:00');
-- INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('xrbjtpp', 0, '1970-01-01 00:00:00');
-- INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, '1970-01-01 00:00:00');
-- INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('wix', 0, '1970-01-01 00:00:00');
-- INSERT INTO icdcodes(Code, Description, Chronic) VALUES(0, '', 'no');
-- INSERT INTO icdcodes(Code, Description, Chronic) VALUES(-37, '', 'no');
-- INSERT INTO CPTCodes(Code, Description, Attribute) VALUES('', '', '');
-- INSERT INTO CPTCodes(Code, Description, Attribute) VALUES('hnmnj', '', '');
-- INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES('', '');
-- INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES('ejfni', '');
-- INSERT INTO NDCodes(Code, Description) VALUES('', '');
-- INSERT INTO NDCodes(Code, Description) VALUES('ojta', '');
-- INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('', '', '');
-- INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('y', 'rpisseeu', '');
-- INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '');
-- INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(67, 0, 0, 0, '1970-01-01 00:00:00', '');
-- INSERT INTO HCPRelations(HCP, UAP) VALUES(0, 0);
-- INSERT INTO HCPRelations(HCP, UAP) VALUES(42, -38);
-- INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(0, 0, '');
-- INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(0, 0, '');
-- INSERT INTO Representatives(representerMID, representeeMID) VALUES(0, 0);
-- INSERT INTO Representatives(representerMID, representeeMID) VALUES(54, -71);
-- INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES('', 0);
-- INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES('m', 4);
-- INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(0, 0);
-- INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(-5, -36);
-- INSERT INTO OfficeVisits(ID, visitDate, HCPID, notes, PatientID, HospitalID) VALUES(0, '1000-01-01', 0, '', 0, '');
-- INSERT INTO OfficeVisits(ID, visitDate, HCPID, notes, PatientID, HospitalID) VALUES(NULL, '1000-01-01', 0, '', 0, '');
-- INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
-- INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
-- INSERT INTO PersonalAllergies(PatientID, Allergy) VALUES(0, '');
-- INSERT INTO PersonalAllergies(PatientID, Allergy) VALUES(0, '');
-- INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(0, 0, '', '1970-01-01 00:00:00');
-- INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(49, 0, '', '1970-01-01 00:00:00');
-- INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(0, 0, '', '');
-- INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(-53, 0, '', '');
-- INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(0, 0, '', '1000-01-01', '1000-01-01', 0, '');
-- INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(82, 0, '', '1000-01-01', '1000-01-01', 0, '');
-- INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(0, 0, '', '');
-- INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(-100, 0, '', '');
-- INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(0, 0, 0);
-- INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(78, 0, 0);
-- INSERT INTO GlobalVariables(Name, Value) VALUES('', '');
-- INSERT INTO GlobalVariables(Name, Value) VALUES('jj', '');
-- INSERT INTO FakeEmail(ID, ToAddr, FromAddr, Subject, Body, AddedDate) VALUES(0, '', '', '', '', '1970-01-01 00:00:00');
-- INSERT INTO FakeEmail(ID, ToAddr, FromAddr, Subject, Body, AddedDate) VALUES(-67, '', '', '', '', '1970-01-01 00:00:00');
-- INSERT INTO ReportRequests(ID, RequesterMID, PatientMID, ApproverMID, RequestedDate, ApprovedDate, ViewedDate, Status, Comment) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', '');
-- INSERT INTO ReportRequests(ID, RequesterMID, PatientMID, ApproverMID, RequestedDate, ApprovedDate, ViewedDate, Status, Comment) VALUES(69, 0, 0, 0, '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', '');
-- INSERT INTO OVSurvey(VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction) VALUES(0, '1970-01-01 00:00:00', 0, 0, 0, 0);
-- INSERT INTO OVSurvey(VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction) VALUES(-100, '1970-01-01 00:00:00', 0, 0, 0, 0);
-- INSERT INTO LOINC(LaboratoryProcedureCode, Component, KindOfProperty, TimeAspect, System, ScaleType, MethodType) VALUES('', '', '', '', '', '', '');
-- INSERT INTO LOINC(LaboratoryProcedureCode, Component, KindOfProperty, TimeAspect, System, ScaleType, MethodType) VALUES('', '', '', '', '', '', '');
-- INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(0, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, FALSE, '1970-01-01 00:00:00');
-- INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(NULL, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, FALSE, '1970-01-01 00:00:00');
-- INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '', '', 0);
-- INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '', '', 0);
-- INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(0, 0, 0, '1970-01-01 00:00:00', '', '');
-- INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(22, 0, 0, '1970-01-01 00:00:00', '', '');
-- INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(0, '', 0);
-- INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(NULL, '', 0);
-- INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, 0, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(75, 0, 0, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00', '', 0);
-- INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(-40, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00', '', 0);
-- INSERT INTO RemoteMonitoringLists(PatientMID, HCPMID, SystolicBloodPressure, DiastolicBloodPressure, GlucoseLevel, Height, Weight, PedometerReading) VALUES(0, 0, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE);
-- INSERT INTO RemoteMonitoringLists(PatientMID, HCPMID, SystolicBloodPressure, DiastolicBloodPressure, GlucoseLevel, Height, Weight, PedometerReading) VALUES(-37, 58, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE);
-- INSERT INTO AdverseEvents(id, Status, PatientMID, PresImmu, Code, Comment, Prescriber, TimeLogged) VALUES(0, '', 0, '', '', '', 0, '1970-01-01 00:00:00');
-- INSERT INTO AdverseEvents(id, Status, PatientMID, PresImmu, Code, Comment, Prescriber, TimeLogged) VALUES(NULL, '', 0, '', '', '', 0, '1970-01-01 00:00:00');
-- INSERT INTO ProfilePhotos(MID, Photo, UpdatedDate) VALUES(0, '', '1970-01-01 00:00:00');
-- INSERT INTO ProfilePhotos(MID, Photo, UpdatedDate) VALUES(85, '', '1970-01-01 00:00:00');
-- INSERT INTO PatientSpecificInstructions(id, VisitID, Modified, Name, URL, Comment) VALUES(0, 0, '1970-01-01 00:00:00', '', '', '');
-- INSERT INTO PatientSpecificInstructions(id, VisitID, Modified, Name, URL, Comment) VALUES(31, 0, '1970-01-01 00:00:00', '', '', '');
-- INSERT INTO ReferralMessage(messageID, referralID) VALUES(0, 0);
-- INSERT INTO ReferralMessage(messageID, referralID) VALUES(-96, -40);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all constraints. Value: 0.81250000000000000001 [Sum: 4.33333333333333333338]
 	 	* Satisfy PRIMARY KEY[MID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[MID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-52] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -52 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(Role). Value: 0E-20 [Sum: 0]
 		 		* 'lt', allowNull: false. Value: 0
 		 		* 'lt', allowNull: false. Value: 0
 	 	* Satisfy CHECK[Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]. Value: 0E-20 [Sum: 0E-20]
 		 		* Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt') goalIsToSatisfy: true allowNull: false. Value: 0E-20 [Best: 0E-20]
 			 			* 'lt' = 'patient'. Value: 0.87155963302752293579 [Sum: 6.78571428571428571430]
 				 				* 108 = 112. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 116 = 97. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 			 			* 'lt' = 'admin'. Value: 0.82957028404952658413 [Sum: 4.86752136752136752138]
 				 				* 108 = 97. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 116 = 100. Value: 0.94444444444444444445 [Distance: 17]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 			 			* 'lt' = 'hcp'. Value: 0.73549883990719257541 [Sum: 2.78070175438596491229]
 				 				* 108 = 104. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 116 = 99. Value: 0.94736842105263157895 [Distance: 18]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'lt' = 'uap'. Value: 0.74103139013452914799 [Sum: 2.86147186147186147187]
 				 				* 108 = 117. Value: 0.90909090909090909091 [Distance: 10]
 				 				* 116 = 97. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'lt' = 'er'. Value: 0.62105263157894736843 [Sum: 1.63888888888888888889]
 				 				* 108 = 101. Value: 0.88888888888888888889 [Distance: 8]
 				 				* 116 = 114. Value: 0.75000000000000000000 [Distance: 3]
 			 			* 'lt' = 'tester'. Value: 0.85382631126397248496 [Sum: 5.84117647058823529412]
 				 				* 108 = 116. Value: 0.90000000000000000000 [Distance: 9]
 				 				* 116 = 101. Value: 0.94117647058823529412 [Distance: 16]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 			 			* 'lt' = 'pha'. Value: 0.73417721518987341773 [Sum: 2.76190476190476190477]
 				 				* 108 = 112. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 116 = 104. Value: 0.92857142857142857143 [Distance: 13]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'lt' = 'lt'. Value: 0E-20 [Sum: 0E-20]
 				 				* 108 = 108. Value: 0E-20 [Distance: 0]
 				 				* 116 = 116. Value: 0E-20 [Distance: 0]
 		 		* Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt') goalIsToSatisfy: true allowNull: false. Value: 0E-20 [Best: 0E-20]
 			 			* 'lt' = 'patient'. Value: 0.87155963302752293579 [Sum: 6.78571428571428571430]
 				 				* 108 = 112. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 116 = 97. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 			 			* 'lt' = 'admin'. Value: 0.82957028404952658413 [Sum: 4.86752136752136752138]
 				 				* 108 = 97. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 116 = 100. Value: 0.94444444444444444445 [Distance: 17]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 			 			* 'lt' = 'hcp'. Value: 0.73549883990719257541 [Sum: 2.78070175438596491229]
 				 				* 108 = 104. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 116 = 99. Value: 0.94736842105263157895 [Distance: 18]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'lt' = 'uap'. Value: 0.74103139013452914799 [Sum: 2.86147186147186147187]
 				 				* 108 = 117. Value: 0.90909090909090909091 [Distance: 10]
 				 				* 116 = 97. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'lt' = 'er'. Value: 0.62105263157894736843 [Sum: 1.63888888888888888889]
 				 				* 108 = 101. Value: 0.88888888888888888889 [Distance: 8]
 				 				* 116 = 114. Value: 0.75000000000000000000 [Distance: 3]
 			 			* 'lt' = 'tester'. Value: 0.85382631126397248496 [Sum: 5.84117647058823529412]
 				 				* 108 = 116. Value: 0.90000000000000000000 [Distance: 9]
 				 				* 116 = 101. Value: 0.94117647058823529412 [Distance: 16]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 			 			* 'lt' = 'pha'. Value: 0.73417721518987341773 [Sum: 2.76190476190476190477]
 				 				* 108 = 112. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 116 = 104. Value: 0.92857142857142857143 [Distance: 13]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'lt' = 'lt'. Value: 0E-20 [Sum: 0E-20]
 				 				* 108 = 108. Value: 0E-20 [Distance: 0]
 				 				* 116 = 116. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[HospitalID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 		 		* Satisfy PRIMARY KEY[HospitalID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0.50000000000000000000 [Sum: 1]
 			 			* [null] != ['']. Value: 1 [Best: 1]
 				 				* null != ''. Value: 1
 	 	* Satisfy NOT NULL(HospitalName). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[MID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[MID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [92] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 92 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(role). Value: 0E-20 [Sum: 0]
 		 		* 'er', allowNull: false. Value: 0
 		 		* 'lt', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(enabled). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(lastName). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(firstName). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(address1). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(address2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(city). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(state). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]. Value: 0E-20 [Sum: 0E-20]
 		 		* role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt') goalIsToSatisfy: true allowNull: false. Value: 0E-20 [Best: 0E-20]
 			 			* 'er' = 'admin'. Value: 0.82671480144404332130 [Sum: 4.77083333333333333334]
 				 				* 101 = 97. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 114 = 100. Value: 0.93750000000000000000 [Distance: 15]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 			 			* 'er' = 'hcp'. Value: 0.73270440251572327045 [Sum: 2.74117647058823529412]
 				 				* 101 = 104. Value: 0.80000000000000000000 [Distance: 4]
 				 				* 114 = 99. Value: 0.94117647058823529412 [Distance: 16]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'er' = 'uap'. Value: 0.74305033809166040572 [Sum: 2.89181286549707602340]
 				 				* 101 = 117. Value: 0.94444444444444444445 [Distance: 17]
 				 				* 114 = 97. Value: 0.94736842105263157895 [Distance: 18]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'er' = 'er'. Value: 0E-20 [Sum: 0E-20]
 				 				* 101 = 101. Value: 0E-20 [Distance: 0]
 				 				* 114 = 114. Value: 0E-20 [Distance: 0]
 			 			* 'er' = 'tester'. Value: 0.85453508271534512265 [Sum: 5.87450980392156862746]
 				 				* 101 = 116. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 114 = 101. Value: 0.93333333333333333334 [Distance: 14]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 			 			* 'er' = 'pha'. Value: 0.73956594323873121870 [Sum: 2.83974358974358974360]
 				 				* 101 = 112. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 114 = 104. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'er' = 'lt'. Value: 0.62105263157894736843 [Sum: 1.63888888888888888889]
 				 				* 101 = 108. Value: 0.88888888888888888889 [Distance: 8]
 				 				* 114 = 116. Value: 0.75000000000000000000 [Distance: 3]
 		 		* role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt') goalIsToSatisfy: true allowNull: false. Value: 0E-20 [Best: 0E-20]
 			 			* 'lt' = 'admin'. Value: 0.82957028404952658413 [Sum: 4.86752136752136752138]
 				 				* 108 = 97. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 116 = 100. Value: 0.94444444444444444445 [Distance: 17]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 			 			* 'lt' = 'hcp'. Value: 0.73549883990719257541 [Sum: 2.78070175438596491229]
 				 				* 108 = 104. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 116 = 99. Value: 0.94736842105263157895 [Distance: 18]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'lt' = 'uap'. Value: 0.74103139013452914799 [Sum: 2.86147186147186147187]
 				 				* 108 = 117. Value: 0.90909090909090909091 [Distance: 10]
 				 				* 116 = 97. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'lt' = 'er'. Value: 0.62105263157894736843 [Sum: 1.63888888888888888889]
 				 				* 108 = 101. Value: 0.88888888888888888889 [Distance: 8]
 				 				* 116 = 114. Value: 0.75000000000000000000 [Distance: 3]
 			 			* 'lt' = 'tester'. Value: 0.85382631126397248496 [Sum: 5.84117647058823529412]
 				 				* 108 = 116. Value: 0.90000000000000000000 [Distance: 9]
 				 				* 116 = 101. Value: 0.94117647058823529412 [Distance: 16]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 			 			* 'lt' = 'pha'. Value: 0.73417721518987341773 [Sum: 2.76190476190476190477]
 				 				* 108 = 112. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 116 = 104. Value: 0.92857142857142857143 [Distance: 13]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'lt' = 'lt'. Value: 0E-20 [Sum: 0E-20]
 				 				* 108 = 108. Value: 0E-20 [Distance: 0]
 				 				* 116 = 116. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0E-20 [Sum: 0E-20]
 		 		* state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 0E-20 [Best: 0E-20]
 			 			* '' = ''. Value: 0E-20 [Sum: 0]
 			 			* '' = 'AK'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'AL'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'AR'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'AZ'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'CA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'CO'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'CT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'DE'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'DC'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'FL'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'GA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'HI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'IA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'ID'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'IL'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'IN'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'KS'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'KY'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'LA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MD'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'ME'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MN'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MO'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MS'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NC'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'ND'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NE'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NH'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NJ'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NM'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NV'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NY'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'OH'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'OK'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'OR'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'PA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'RI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'SC'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'SD'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'TN'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'TX'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'UT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'VA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'VT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WV'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WY'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 		 		* state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 0E-20 [Best: 0E-20]
 			 			* '' = ''. Value: 0E-20 [Sum: 0]
 			 			* '' = 'AK'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'AL'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'AR'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'AZ'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'CA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'CO'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'CT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'DE'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'DC'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'FL'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'GA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'HI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'IA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'ID'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'IL'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'IN'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'KS'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'KY'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'LA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MD'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'ME'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MN'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MO'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MS'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NC'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'ND'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NE'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NH'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NJ'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NM'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NV'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NY'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'OH'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'OK'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'OR'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'PA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'RI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'SC'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'SD'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'TN'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'TX'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'UT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'VA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'VT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WV'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WY'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 	 	* Satisfy PRIMARY KEY[MID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[MID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [79] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 79 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.66666666666666666667 [Sum: 2]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 1 [Best: 1]
 			 			* null = 'AK'. Value: 1
 			 			* null = 'AL'. Value: 1
 			 			* null = 'AR'. Value: 1
 			 			* null = 'AZ'. Value: 1
 			 			* null = 'CA'. Value: 1
 			 			* null = 'CO'. Value: 1
 			 			* null = 'CT'. Value: 1
 			 			* null = 'DE'. Value: 1
 			 			* null = 'DC'. Value: 1
 			 			* null = 'FL'. Value: 1
 			 			* null = 'GA'. Value: 1
 			 			* null = 'HI'. Value: 1
 			 			* null = 'IA'. Value: 1
 			 			* null = 'ID'. Value: 1
 			 			* null = 'IL'. Value: 1
 			 			* null = 'IN'. Value: 1
 			 			* null = 'KS'. Value: 1
 			 			* null = 'KY'. Value: 1
 			 			* null = 'LA'. Value: 1
 			 			* null = 'MA'. Value: 1
 			 			* null = 'MD'. Value: 1
 			 			* null = 'ME'. Value: 1
 			 			* null = 'MI'. Value: 1
 			 			* null = 'MN'. Value: 1
 			 			* null = 'MO'. Value: 1
 			 			* null = 'MS'. Value: 1
 			 			* null = 'MT'. Value: 1
 			 			* null = 'NC'. Value: 1
 			 			* null = 'ND'. Value: 1
 			 			* null = 'NE'. Value: 1
 			 			* null = 'NH'. Value: 1
 			 			* null = 'NJ'. Value: 1
 			 			* null = 'NM'. Value: 1
 			 			* null = 'NV'. Value: 1
 			 			* null = 'NY'. Value: 1
 			 			* null = 'OH'. Value: 1
 			 			* null = 'OK'. Value: 1
 			 			* null = 'OR'. Value: 1
 			 			* null = 'PA'. Value: 1
 			 			* null = 'RI'. Value: 1
 			 			* null = 'SC'. Value: 1
 			 			* null = 'SD'. Value: 1
 			 			* null = 'TN'. Value: 1
 			 			* null = 'TX'. Value: 1
 			 			* null = 'UT'. Value: 1
 			 			* null = 'VA'. Value: 1
 			 			* null = 'VT'. Value: 1
 			 			* null = 'WA'. Value: 1
 			 			* null = 'WI'. Value: 1
 			 			* null = 'WV'. Value: 1
 			 			* null = 'WY'. Value: 1
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 1 [Best: 1]
 			 			* null = 'AK'. Value: 1
 			 			* null = 'AL'. Value: 1
 			 			* null = 'AR'. Value: 1
 			 			* null = 'AZ'. Value: 1
 			 			* null = 'CA'. Value: 1
 			 			* null = 'CO'. Value: 1
 			 			* null = 'CT'. Value: 1
 			 			* null = 'DE'. Value: 1
 			 			* null = 'DC'. Value: 1
 			 			* null = 'FL'. Value: 1
 			 			* null = 'GA'. Value: 1
 			 			* null = 'HI'. Value: 1
 			 			* null = 'IA'. Value: 1
 			 			* null = 'ID'. Value: 1
 			 			* null = 'IL'. Value: 1
 			 			* null = 'IN'. Value: 1
 			 			* null = 'KS'. Value: 1
 			 			* null = 'KY'. Value: 1
 			 			* null = 'LA'. Value: 1
 			 			* null = 'MA'. Value: 1
 			 			* null = 'MD'. Value: 1
 			 			* null = 'ME'. Value: 1
 			 			* null = 'MI'. Value: 1
 			 			* null = 'MN'. Value: 1
 			 			* null = 'MO'. Value: 1
 			 			* null = 'MS'. Value: 1
 			 			* null = 'MT'. Value: 1
 			 			* null = 'NC'. Value: 1
 			 			* null = 'ND'. Value: 1
 			 			* null = 'NE'. Value: 1
 			 			* null = 'NH'. Value: 1
 			 			* null = 'NJ'. Value: 1
 			 			* null = 'NM'. Value: 1
 			 			* null = 'NV'. Value: 1
 			 			* null = 'NY'. Value: 1
 			 			* null = 'OH'. Value: 1
 			 			* null = 'OK'. Value: 1
 			 			* null = 'OR'. Value: 1
 			 			* null = 'PA'. Value: 1
 			 			* null = 'RI'. Value: 1
 			 			* null = 'SC'. Value: 1
 			 			* null = 'SD'. Value: 1
 			 			* null = 'TN'. Value: 1
 			 			* null = 'TX'. Value: 1
 			 			* null = 'UT'. Value: 1
 			 			* null = 'VA'. Value: 1
 			 			* null = 'VT'. Value: 1
 			 			* null = 'WA'. Value: 1
 			 			* null = 'WI'. Value: 1
 			 			* null = 'WV'. Value: 1
 			 			* null = 'WY'. Value: 1
 	 	* Satisfy CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.66666666666666666667 [Sum: 2]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 1 [Best: 1]
 			 			* null = 'AK'. Value: 1
 			 			* null = 'AL'. Value: 1
 			 			* null = 'AR'. Value: 1
 			 			* null = 'AZ'. Value: 1
 			 			* null = 'CA'. Value: 1
 			 			* null = 'CO'. Value: 1
 			 			* null = 'CT'. Value: 1
 			 			* null = 'DE'. Value: 1
 			 			* null = 'DC'. Value: 1
 			 			* null = 'FL'. Value: 1
 			 			* null = 'GA'. Value: 1
 			 			* null = 'HI'. Value: 1
 			 			* null = 'IA'. Value: 1
 			 			* null = 'ID'. Value: 1
 			 			* null = 'IL'. Value: 1
 			 			* null = 'IN'. Value: 1
 			 			* null = 'KS'. Value: 1
 			 			* null = 'KY'. Value: 1
 			 			* null = 'LA'. Value: 1
 			 			* null = 'MA'. Value: 1
 			 			* null = 'MD'. Value: 1
 			 			* null = 'ME'. Value: 1
 			 			* null = 'MI'. Value: 1
 			 			* null = 'MN'. Value: 1
 			 			* null = 'MO'. Value: 1
 			 			* null = 'MS'. Value: 1
 			 			* null = 'MT'. Value: 1
 			 			* null = 'NC'. Value: 1
 			 			* null = 'ND'. Value: 1
 			 			* null = 'NE'. Value: 1
 			 			* null = 'NH'. Value: 1
 			 			* null = 'NJ'. Value: 1
 			 			* null = 'NM'. Value: 1
 			 			* null = 'NV'. Value: 1
 			 			* null = 'NY'. Value: 1
 			 			* null = 'OH'. Value: 1
 			 			* null = 'OK'. Value: 1
 			 			* null = 'OR'. Value: 1
 			 			* null = 'PA'. Value: 1
 			 			* null = 'RI'. Value: 1
 			 			* null = 'SC'. Value: 1
 			 			* null = 'SD'. Value: 1
 			 			* null = 'TN'. Value: 1
 			 			* null = 'TX'. Value: 1
 			 			* null = 'UT'. Value: 1
 			 			* null = 'VA'. Value: 1
 			 			* null = 'VT'. Value: 1
 			 			* null = 'WA'. Value: 1
 			 			* null = 'WI'. Value: 1
 			 			* null = 'WV'. Value: 1
 			 			* null = 'WY'. Value: 1
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 1 [Best: 1]
 			 			* null = 'AK'. Value: 1
 			 			* null = 'AL'. Value: 1
 			 			* null = 'AR'. Value: 1
 			 			* null = 'AZ'. Value: 1
 			 			* null = 'CA'. Value: 1
 			 			* null = 'CO'. Value: 1
 			 			* null = 'CT'. Value: 1
 			 			* null = 'DE'. Value: 1
 			 			* null = 'DC'. Value: 1
 			 			* null = 'FL'. Value: 1
 			 			* null = 'GA'. Value: 1
 			 			* null = 'HI'. Value: 1
 			 			* null = 'IA'. Value: 1
 			 			* null = 'ID'. Value: 1
 			 			* null = 'IL'. Value: 1
 			 			* null = 'IN'. Value: 1
 			 			* null = 'KS'. Value: 1
 			 			* null = 'KY'. Value: 1
 			 			* null = 'LA'. Value: 1
 			 			* null = 'MA'. Value: 1
 			 			* null = 'MD'. Value: 1
 			 			* null = 'ME'. Value: 1
 			 			* null = 'MI'. Value: 1
 			 			* null = 'MN'. Value: 1
 			 			* null = 'MO'. Value: 1
 			 			* null = 'MS'. Value: 1
 			 			* null = 'MT'. Value: 1
 			 			* null = 'NC'. Value: 1
 			 			* null = 'ND'. Value: 1
 			 			* null = 'NE'. Value: 1
 			 			* null = 'NH'. Value: 1
 			 			* null = 'NJ'. Value: 1
 			 			* null = 'NM'. Value: 1
 			 			* null = 'NV'. Value: 1
 			 			* null = 'NY'. Value: 1
 			 			* null = 'OH'. Value: 1
 			 			* null = 'OK'. Value: 1
 			 			* null = 'OR'. Value: 1
 			 			* null = 'PA'. Value: 1
 			 			* null = 'RI'. Value: 1
 			 			* null = 'SC'. Value: 1
 			 			* null = 'SD'. Value: 1
 			 			* null = 'TN'. Value: 1
 			 			* null = 'TX'. Value: 1
 			 			* null = 'UT'. Value: 1
 			 			* null = 'VA'. Value: 1
 			 			* null = 'VT'. Value: 1
 			 			* null = 'WA'. Value: 1
 			 			* null = 'WI'. Value: 1
 			 			* null = 'WV'. Value: 1
 			 			* null = 'WY'. Value: 1
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-33] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -33 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(changeDate). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(changeMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.66666666666666666667 [Sum: 2]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 1 [Best: 1]
 			 			* null = 'AK'. Value: 1
 			 			* null = 'AL'. Value: 1
 			 			* null = 'AR'. Value: 1
 			 			* null = 'AZ'. Value: 1
 			 			* null = 'CA'. Value: 1
 			 			* null = 'CO'. Value: 1
 			 			* null = 'CT'. Value: 1
 			 			* null = 'DE'. Value: 1
 			 			* null = 'DC'. Value: 1
 			 			* null = 'FL'. Value: 1
 			 			* null = 'GA'. Value: 1
 			 			* null = 'HI'. Value: 1
 			 			* null = 'IA'. Value: 1
 			 			* null = 'ID'. Value: 1
 			 			* null = 'IL'. Value: 1
 			 			* null = 'IN'. Value: 1
 			 			* null = 'KS'. Value: 1
 			 			* null = 'KY'. Value: 1
 			 			* null = 'LA'. Value: 1
 			 			* null = 'MA'. Value: 1
 			 			* null = 'MD'. Value: 1
 			 			* null = 'ME'. Value: 1
 			 			* null = 'MI'. Value: 1
 			 			* null = 'MN'. Value: 1
 			 			* null = 'MO'. Value: 1
 			 			* null = 'MS'. Value: 1
 			 			* null = 'MT'. Value: 1
 			 			* null = 'NC'. Value: 1
 			 			* null = 'ND'. Value: 1
 			 			* null = 'NE'. Value: 1
 			 			* null = 'NH'. Value: 1
 			 			* null = 'NJ'. Value: 1
 			 			* null = 'NM'. Value: 1
 			 			* null = 'NV'. Value: 1
 			 			* null = 'NY'. Value: 1
 			 			* null = 'OH'. Value: 1
 			 			* null = 'OK'. Value: 1
 			 			* null = 'OR'. Value: 1
 			 			* null = 'PA'. Value: 1
 			 			* null = 'RI'. Value: 1
 			 			* null = 'SC'. Value: 1
 			 			* null = 'SD'. Value: 1
 			 			* null = 'TN'. Value: 1
 			 			* null = 'TX'. Value: 1
 			 			* null = 'UT'. Value: 1
 			 			* null = 'VA'. Value: 1
 			 			* null = 'VT'. Value: 1
 			 			* null = 'WA'. Value: 1
 			 			* null = 'WI'. Value: 1
 			 			* null = 'WV'. Value: 1
 			 			* null = 'WY'. Value: 1
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 1 [Best: 1]
 			 			* null = 'AK'. Value: 1
 			 			* null = 'AL'. Value: 1
 			 			* null = 'AR'. Value: 1
 			 			* null = 'AZ'. Value: 1
 			 			* null = 'CA'. Value: 1
 			 			* null = 'CO'. Value: 1
 			 			* null = 'CT'. Value: 1
 			 			* null = 'DE'. Value: 1
 			 			* null = 'DC'. Value: 1
 			 			* null = 'FL'. Value: 1
 			 			* null = 'GA'. Value: 1
 			 			* null = 'HI'. Value: 1
 			 			* null = 'IA'. Value: 1
 			 			* null = 'ID'. Value: 1
 			 			* null = 'IL'. Value: 1
 			 			* null = 'IN'. Value: 1
 			 			* null = 'KS'. Value: 1
 			 			* null = 'KY'. Value: 1
 			 			* null = 'LA'. Value: 1
 			 			* null = 'MA'. Value: 1
 			 			* null = 'MD'. Value: 1
 			 			* null = 'ME'. Value: 1
 			 			* null = 'MI'. Value: 1
 			 			* null = 'MN'. Value: 1
 			 			* null = 'MO'. Value: 1
 			 			* null = 'MS'. Value: 1
 			 			* null = 'MT'. Value: 1
 			 			* null = 'NC'. Value: 1
 			 			* null = 'ND'. Value: 1
 			 			* null = 'NE'. Value: 1
 			 			* null = 'NH'. Value: 1
 			 			* null = 'NJ'. Value: 1
 			 			* null = 'NM'. Value: 1
 			 			* null = 'NV'. Value: 1
 			 			* null = 'NY'. Value: 1
 			 			* null = 'OH'. Value: 1
 			 			* null = 'OK'. Value: 1
 			 			* null = 'OR'. Value: 1
 			 			* null = 'PA'. Value: 1
 			 			* null = 'RI'. Value: 1
 			 			* null = 'SC'. Value: 1
 			 			* null = 'SD'. Value: 1
 			 			* null = 'TN'. Value: 1
 			 			* null = 'TX'. Value: 1
 			 			* null = 'UT'. Value: 1
 			 			* null = 'VA'. Value: 1
 			 			* null = 'VT'. Value: 1
 			 			* null = 'WA'. Value: 1
 			 			* null = 'WI'. Value: 1
 			 			* null = 'WV'. Value: 1
 			 			* null = 'WY'. Value: 1
 	 	* Satisfy CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.66666666666666666667 [Sum: 2]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 1 [Best: 1]
 			 			* null = 'AK'. Value: 1
 			 			* null = 'AL'. Value: 1
 			 			* null = 'AR'. Value: 1
 			 			* null = 'AZ'. Value: 1
 			 			* null = 'CA'. Value: 1
 			 			* null = 'CO'. Value: 1
 			 			* null = 'CT'. Value: 1
 			 			* null = 'DE'. Value: 1
 			 			* null = 'DC'. Value: 1
 			 			* null = 'FL'. Value: 1
 			 			* null = 'GA'. Value: 1
 			 			* null = 'HI'. Value: 1
 			 			* null = 'IA'. Value: 1
 			 			* null = 'ID'. Value: 1
 			 			* null = 'IL'. Value: 1
 			 			* null = 'IN'. Value: 1
 			 			* null = 'KS'. Value: 1
 			 			* null = 'KY'. Value: 1
 			 			* null = 'LA'. Value: 1
 			 			* null = 'MA'. Value: 1
 			 			* null = 'MD'. Value: 1
 			 			* null = 'ME'. Value: 1
 			 			* null = 'MI'. Value: 1
 			 			* null = 'MN'. Value: 1
 			 			* null = 'MO'. Value: 1
 			 			* null = 'MS'. Value: 1
 			 			* null = 'MT'. Value: 1
 			 			* null = 'NC'. Value: 1
 			 			* null = 'ND'. Value: 1
 			 			* null = 'NE'. Value: 1
 			 			* null = 'NH'. Value: 1
 			 			* null = 'NJ'. Value: 1
 			 			* null = 'NM'. Value: 1
 			 			* null = 'NV'. Value: 1
 			 			* null = 'NY'. Value: 1
 			 			* null = 'OH'. Value: 1
 			 			* null = 'OK'. Value: 1
 			 			* null = 'OR'. Value: 1
 			 			* null = 'PA'. Value: 1
 			 			* null = 'RI'. Value: 1
 			 			* null = 'SC'. Value: 1
 			 			* null = 'SD'. Value: 1
 			 			* null = 'TN'. Value: 1
 			 			* null = 'TX'. Value: 1
 			 			* null = 'UT'. Value: 1
 			 			* null = 'VA'. Value: 1
 			 			* null = 'VT'. Value: 1
 			 			* null = 'WA'. Value: 1
 			 			* null = 'WI'. Value: 1
 			 			* null = 'WV'. Value: 1
 			 			* null = 'WY'. Value: 1
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: false. Value: 1 [Best: 1]
 			 			* null = 'AK'. Value: 1
 			 			* null = 'AL'. Value: 1
 			 			* null = 'AR'. Value: 1
 			 			* null = 'AZ'. Value: 1
 			 			* null = 'CA'. Value: 1
 			 			* null = 'CO'. Value: 1
 			 			* null = 'CT'. Value: 1
 			 			* null = 'DE'. Value: 1
 			 			* null = 'DC'. Value: 1
 			 			* null = 'FL'. Value: 1
 			 			* null = 'GA'. Value: 1
 			 			* null = 'HI'. Value: 1
 			 			* null = 'IA'. Value: 1
 			 			* null = 'ID'. Value: 1
 			 			* null = 'IL'. Value: 1
 			 			* null = 'IN'. Value: 1
 			 			* null = 'KS'. Value: 1
 			 			* null = 'KY'. Value: 1
 			 			* null = 'LA'. Value: 1
 			 			* null = 'MA'. Value: 1
 			 			* null = 'MD'. Value: 1
 			 			* null = 'ME'. Value: 1
 			 			* null = 'MI'. Value: 1
 			 			* null = 'MN'. Value: 1
 			 			* null = 'MO'. Value: 1
 			 			* null = 'MS'. Value: 1
 			 			* null = 'MT'. Value: 1
 			 			* null = 'NC'. Value: 1
 			 			* null = 'ND'. Value: 1
 			 			* null = 'NE'. Value: 1
 			 			* null = 'NH'. Value: 1
 			 			* null = 'NJ'. Value: 1
 			 			* null = 'NM'. Value: 1
 			 			* null = 'NV'. Value: 1
 			 			* null = 'NY'. Value: 1
 			 			* null = 'OH'. Value: 1
 			 			* null = 'OK'. Value: 1
 			 			* null = 'OR'. Value: 1
 			 			* null = 'PA'. Value: 1
 			 			* null = 'RI'. Value: 1
 			 			* null = 'SC'. Value: 1
 			 			* null = 'SD'. Value: 1
 			 			* null = 'TN'. Value: 1
 			 			* null = 'TX'. Value: 1
 			 			* null = 'UT'. Value: 1
 			 			* null = 'VA'. Value: 1
 			 			* null = 'VT'. Value: 1
 			 			* null = 'WA'. Value: 1
 			 			* null = 'WI'. Value: 1
 			 			* null = 'WV'. Value: 1
 			 			* null = 'WY'. Value: 1
 	 	* Satisfy PRIMARY KEY[ipaddress]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ipaddress]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['xrbjtpp'] != ['']. Value: 0 [Best: 0]
 				 				* 'xrbjtpp' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(ipaddress). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'xrbjtpp', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(failureCount). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(lastFailure). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[ipaddress]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ipaddress]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['wix'] != ['']. Value: 0 [Best: 0]
 				 				* 'wix' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(ipaddress). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'wix', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(failureCount). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(lastFailure). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[Code]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[Code]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-37] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -37 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(Code). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* -37, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Chronic). Value: 0E-20 [Sum: 0]
 		 		* 'no', allowNull: false. Value: 0
 		 		* 'no', allowNull: false. Value: 0
 	 	* Satisfy CHECK[Chronic IN ('no', 'yes')]. Value: 0E-20 [Sum: 0E-20]
 		 		* Chronic IN ('no', 'yes') goalIsToSatisfy: true allowNull: false. Value: 0E-20 [Best: 0E-20]
 			 			* 'no' = 'no'. Value: 0E-20 [Sum: 0E-20]
 				 				* 110 = 110. Value: 0E-20 [Distance: 0]
 				 				* 111 = 111. Value: 0E-20 [Distance: 0]
 			 			* 'no' = 'yes'. Value: 0.73956594323873121870 [Sum: 2.83974358974358974360]
 				 				* 110 = 121. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 111 = 101. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1
 		 		* Chronic IN ('no', 'yes') goalIsToSatisfy: true allowNull: false. Value: 0E-20 [Best: 0E-20]
 			 			* 'no' = 'no'. Value: 0E-20 [Sum: 0E-20]
 				 				* 110 = 110. Value: 0E-20 [Distance: 0]
 				 				* 111 = 111. Value: 0E-20 [Distance: 0]
 			 			* 'no' = 'yes'. Value: 0.73956594323873121870 [Sum: 2.83974358974358974360]
 				 				* 110 = 121. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 111 = 101. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1
 	 	* Satisfy PRIMARY KEY[Code]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[Code]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['hnmnj'] != ['']. Value: 0 [Best: 0]
 				 				* 'hnmnj' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(Code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'hnmnj', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[Code]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[Code]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['ejfni'] != ['']. Value: 0 [Best: 0]
 				 				* 'ejfni' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(Code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'ejfni', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[Code]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[Code]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['ojta'] != ['']. Value: 0 [Best: 0]
 				 				* 'ojta' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(Code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'ojta', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[FirstDrug, SecondDrug]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[FirstDrug, SecondDrug]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['y', 'rpisseeu'] != ['', '']. Value: 0 [Best: 0]
 				 				* 'y' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 				 				* 'rpisseeu' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(FirstDrug). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'y', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SecondDrug). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'rpisseeu', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[transactionID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[transactionID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [67] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 67 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(transactionID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 67, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(loggedInMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(secondaryMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(transactionCode). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(timeLogged). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[HCP, UAP]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[HCP, UAP]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [42, -38] != [0, 0]. Value: 0E-20 [Best: 0E-20]
 				 				* 42 != 0. Value: 0E-20 [Distance: 0]
 				 				* -38 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(HCP). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 42, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(UAP). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* -38, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(RelativeID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(RelativeType). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[representerMID, representeeMID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[representerMID, representeeMID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [54, -71] != [0, 0]. Value: 0E-20 [Best: 0E-20]
 				 				* 54 != 0. Value: 0E-20 [Distance: 0]
 				 				* -71 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[hosID, HCPID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[hosID, HCPID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['m', 4] != ['', 0]. Value: 0 [Best: 0]
 				 				* 'm' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 				 				* 4 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(hosID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'm', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(HCPID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 4, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[PatientID, HCPID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[PatientID, HCPID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-5, -36] != [0, 0]. Value: 0E-20 [Best: 0E-20]
 				 				* -5 != 0. Value: 0E-20 [Distance: 0]
 				 				* -36 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* -5, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(HCPID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* -36, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0.50000000000000000000 [Sum: 1]
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Smoker). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SmokingStatus). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(AsOfDate). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Allergy). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [49] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 49 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(FirstFound). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-53] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -53 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(VisitID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(CPTCode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(HCPID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [82] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 82 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(VisitID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(NDCode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-100] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -100 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy FOREIGN KEY[OVMedicationID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [82]. Value: 0.49700598802395209581 [Sum: 0.98809523809523809524]
 				 				* 0 = 82. Value: 0.98809523809523809524 [Distance: 83]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [82]. Value: 0.49700598802395209581 [Sum: 0.98809523809523809524]
 				 				* 0 = 82. Value: 0.98809523809523809524 [Distance: 83]
 	 	* Satisfy NOT NULL(OVMedicationID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [78] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 78 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(VisitID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(ICDCode). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[Name]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[Name]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['jj'] != ['']. Value: 0 [Best: 0]
 				 				* 'jj' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-67] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -67 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(AddedDate). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [69] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 69 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[VisitID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[VisitID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-100] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -100 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(SurveyDate). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[LaboratoryProcedureID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 		 		* Satisfy PRIMARY KEY[LaboratoryProcedureID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0.50000000000000000000 [Sum: 1]
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 	 	* Satisfy NOT NULL(ViewedByPatient). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(UpdatedDate). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(from_id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(to_id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(sent_date). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[appt_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[appt_id]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [22] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 22 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(doctor_id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(patient_id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(sched_date). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(appt_type). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[apptType_id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 		 		* Satisfy PRIMARY KEY[apptType_id]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0.50000000000000000000 [Sum: 1]
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 	 	* Satisfy NOT NULL(appt_type). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(duration). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [75] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 75 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SenderID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(ReceiverID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(OVID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(viewed_by_patient). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(viewed_by_HCP). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(TimeStamp). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-40] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -40 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(timeLogged). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[PatientMID, HCPMID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[PatientMID, HCPMID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-37, 58] != [0, 0]. Value: 0E-20 [Best: 0E-20]
 				 				* -37 != 0. Value: 0E-20 [Distance: 0]
 				 				* 58 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0.50000000000000000000 [Sum: 1]
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 	 	* Satisfy PRIMARY KEY[MID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[MID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [85] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 85 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(UpdatedDate). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [31] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 31 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(Modified). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[messageID, referralID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[messageID, referralID]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-96, -40] != [0, 0]. Value: 0E-20 [Best: 0E-20]
 				 				* -96 != 0. Value: 0E-20 [Distance: 0]
 				 				* -40 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(messageID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* -96, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(referralID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* -40, allowNull: false. Value: 0*/ 

-- Negating "PRIMARY KEY[MID]" on table "Users"
-- * Success: false
-- * Time: 5622ms 
-- INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(0, '', 'er', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[MID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[MID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[MID]. Value: 1
 	 	* Satisfy NOT NULL(Role). Value: 0E-20 [Sum: 0]
 		 		* 'er', allowNull: false. Value: 0
 	 	* Satisfy CHECK[Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]. Value: 0E-20 [Sum: 0E-20]
 		 		* Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt') goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Best: 0E-20]
 			 			* 'er' = 'patient'. Value: 0.87294238683127572017 [Sum: 6.87044534412955465588]
 				 				* 101 = 112. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 114 = 97. Value: 0.94736842105263157895 [Distance: 18]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 			 			* 'er' = 'admin'. Value: 0.82671480144404332130 [Sum: 4.77083333333333333334]
 				 				* 101 = 97. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 114 = 100. Value: 0.93750000000000000000 [Distance: 15]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 			 			* 'er' = 'hcp'. Value: 0.73270440251572327045 [Sum: 2.74117647058823529412]
 				 				* 101 = 104. Value: 0.80000000000000000000 [Distance: 4]
 				 				* 114 = 99. Value: 0.94117647058823529412 [Distance: 16]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'er' = 'uap'. Value: 0.74305033809166040572 [Sum: 2.89181286549707602340]
 				 				* 101 = 117. Value: 0.94444444444444444445 [Distance: 17]
 				 				* 114 = 97. Value: 0.94736842105263157895 [Distance: 18]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'er' = 'er'. Value: 0E-20 [Sum: 0E-20]
 				 				* 101 = 101. Value: 0E-20 [Distance: 0]
 				 				* 114 = 114. Value: 0E-20 [Distance: 0]
 			 			* 'er' = 'tester'. Value: 0.85453508271534512265 [Sum: 5.87450980392156862746]
 				 				* 101 = 116. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 114 = 101. Value: 0.93333333333333333334 [Distance: 14]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 			 			* 'er' = 'pha'. Value: 0.73956594323873121870 [Sum: 2.83974358974358974360]
 				 				* 101 = 112. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 114 = 104. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'er' = 'lt'. Value: 0.62105263157894736843 [Sum: 1.63888888888888888889]
 				 				* 101 = 108. Value: 0.88888888888888888889 [Distance: 8]
 				 				* 114 = 116. Value: 0.75000000000000000000 [Distance: 3]*/ 

-- Negating "NOT NULL(Role)" on table "Users"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(0, '', NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]" on table "Users"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Users(MID, Password, Role, sQuestion, sAnswer) VALUES(0, '', '', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[HospitalID]" on table "Hospitals"
-- * Success: false
-- * Time: 411ms 
-- INSERT INTO Hospitals(HospitalID, HospitalName) VALUES('', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[HospitalID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[HospitalID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[HospitalID]. Value: 1
 	 	* Satisfy NOT NULL(HospitalName). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(HospitalName)" on table "Hospitals"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Hospitals(HospitalID, HospitalName) VALUES('', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[MID]" on table "Personnel"
-- * Success: false
-- * Time: 15989ms 
-- INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'er', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[MID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[MID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[MID]. Value: 1
 	 	* Satisfy NOT NULL(role). Value: 0E-20 [Sum: 0]
 		 		* 'er', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(enabled). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(lastName). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(firstName). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(address1). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(address2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(city). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(state). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]. Value: 0E-20 [Sum: 0E-20]
 		 		* role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt') goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Best: 0E-20]
 			 			* 'er' = 'admin'. Value: 0.82671480144404332130 [Sum: 4.77083333333333333334]
 				 				* 101 = 97. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 114 = 100. Value: 0.93750000000000000000 [Distance: 15]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 			 			* 'er' = 'hcp'. Value: 0.73270440251572327045 [Sum: 2.74117647058823529412]
 				 				* 101 = 104. Value: 0.80000000000000000000 [Distance: 4]
 				 				* 114 = 99. Value: 0.94117647058823529412 [Distance: 16]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'er' = 'uap'. Value: 0.74305033809166040572 [Sum: 2.89181286549707602340]
 				 				* 101 = 117. Value: 0.94444444444444444445 [Distance: 17]
 				 				* 114 = 97. Value: 0.94736842105263157895 [Distance: 18]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'er' = 'er'. Value: 0E-20 [Sum: 0E-20]
 				 				* 101 = 101. Value: 0E-20 [Distance: 0]
 				 				* 114 = 114. Value: 0E-20 [Distance: 0]
 			 			* 'er' = 'tester'. Value: 0.85453508271534512265 [Sum: 5.87450980392156862746]
 				 				* 101 = 116. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 114 = 101. Value: 0.93333333333333333334 [Distance: 14]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 			 			* 'er' = 'pha'. Value: 0.73956594323873121870 [Sum: 2.83974358974358974360]
 				 				* 101 = 112. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 114 = 104. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1
 			 			* 'er' = 'lt'. Value: 0.62105263157894736843 [Sum: 1.63888888888888888889]
 				 				* 101 = 108. Value: 0.88888888888888888889 [Distance: 8]
 				 				* 114 = 116. Value: 0.75000000000000000000 [Distance: 3]
 	 	* Satisfy CHECK[state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0E-20 [Sum: 0E-20]
 		 		* state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Best: 0E-20]
 			 			* '' = ''. Value: 0E-20 [Sum: 0]
 			 			* '' = 'AK'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'AL'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'AR'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'AZ'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'CA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'CO'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'CT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'DE'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'DC'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'FL'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'GA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'HI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'IA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'ID'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'IL'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'IN'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'KS'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'KY'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'LA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MD'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'ME'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MN'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MO'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MS'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'MT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NC'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'ND'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NE'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NH'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NJ'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NM'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NV'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'NY'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'OH'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'OK'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'OR'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'PA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'RI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'SC'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'SD'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'TN'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'TX'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'UT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'VA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'VT'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WA'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WI'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WV'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* '' = 'WY'. Value: 0.66666666666666666667 [Sum: 2]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1*/ 

-- Negating "NOT NULL(role)" on table "Personnel"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, NULL, 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(enabled)" on table "Personnel"
-- * Success: true
-- * Time: 99ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'lt', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 511
-- * Number of restarts: 0

-- Negating "NOT NULL(lastName)" on table "Personnel"
-- * Success: true
-- * Time: 153ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'er', 0, NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 874
-- * Number of restarts: 0

-- Negating "NOT NULL(firstName)" on table "Personnel"
-- * Success: true
-- * Time: 348ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'pha', 0, '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 1985
-- * Number of restarts: 0

-- Negating "NOT NULL(address1)" on table "Personnel"
-- * Success: true
-- * Time: 568ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'er', 0, '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 3240
-- * Number of restarts: 0

-- Negating "NOT NULL(address2)" on table "Personnel"
-- * Success: true
-- * Time: 1370ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'lt', 0, '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 7787
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "Personnel"
-- * Success: true
-- * Time: 136ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'er', 0, '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 765
-- * Number of restarts: 0

-- Negating "NOT NULL(state)" on table "Personnel"
-- * Success: true
-- * Time: 207ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'er', 0, '', '', '', '', '', NULL, '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 1797
-- * Number of restarts: 0

-- Negating "CHECK[role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]" on table "Personnel"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, '', 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "CHECK[state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "Personnel"
-- * Success: true
-- * Time: 566ms 
INSERT INTO Personnel(MID, AMID, role, enabled, lastName, firstName, address1, address2, city, state, zip, zip1, zip2, phone, phone1, phone2, phone3, specialty, email, MessageFilter) VALUES(0, 0, 'er', 0, '', '', '', '', '', 'nc', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 2588
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[MID]" on table "Patients"
-- * Success: false
-- * Time: 52097ms 
-- INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '', '', '', '', '', '', 'ab', '', '', '', '', '', '', '', '', '', '', '', '', '', 'aa', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[MID]. Value: 0.56228555602853611378 [Sum: 1.28459447425772736861]
 	 	* Violate PRIMARY KEY[MID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[MID]. Value: 1
 	 	* Satisfy CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39250814332247557004 [Sum: 0.64611260053619302950]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64611260053619302950 [Best: 0.64611260053619302950]
 			 			* 'ab' = 'AK'. Value: 0.65877157767964672823 [Sum: 1.93058823529411764706]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 98 = 75. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'ab' = 'AL'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'ab' = 'AR'. Value: 0.65695067264573991032 [Sum: 1.91503267973856209151]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 98 = 82. Value: 0.94444444444444444445 [Distance: 17]
 			 			* 'ab' = 'AZ'. Value: 0.65163934426229508197 [Sum: 1.87058823529411764706]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 98 = 90. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'ab' = 'CA'. Value: 0.65988460370482842394 [Sum: 1.94017857142857142858]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'CO'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'ab' = 'CT'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ab' = 'DE'. Value: 0.65934065934065934067 [Sum: 1.93548387096774193550]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ab' = 'DC'. Value: 0.65956738768718801997 [Sum: 1.93743890518084066472]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ab' = 'FL'. Value: 0.65798525798525798526 [Sum: 1.92385057471264367817]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'ab' = 'GA'. Value: 0.65936739659367396594 [Sum: 1.93571428571428571430]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'HI'. Value: 0.65822784810126582279 [Sum: 1.92592592592592592594]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ab' = 'IA'. Value: 0.65904833270887973024 [Sum: 1.93296703296703296705]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'ID'. Value: 0.65873666940114848237 [Sum: 1.93028846153846153847]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ab' = 'IL'. Value: 0.65751920965971459935 [Sum: 1.91987179487179487181]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'ab' = 'IN'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'ab' = 'KS'. Value: 0.65511411665257819105 [Sum: 1.89950980392156862746]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ab' = 'KY'. Value: 0.65125495376486129459 [Sum: 1.86742424242424242425]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 98 = 89. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'ab' = 'LA'. Value: 0.65846414934238438694 [Sum: 1.92795031055900621119]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'MA'. Value: 0.65823346648912561030 [Sum: 1.92597402597402597404]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'MD'. Value: 0.65792031098153547134 [Sum: 1.92329545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ab' = 'ME'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ab' = 'MI'. Value: 0.65724177726485862667 [Sum: 1.91750841750841750843]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ab' = 'MN'. Value: 0.65625000000000000001 [Sum: 1.90909090909090909092]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'ab' = 'MO'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'ab' = 'MS'. Value: 0.65466297322253000924 [Sum: 1.89572192513368983958]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ab' = 'MT'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ab' = 'NC'. Value: 0.65777777777777777778 [Sum: 1.92207792207792207793]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ab' = 'ND'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ab' = 'NE'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ab' = 'NH'. Value: 0.65714285714285714286 [Sum: 1.91666666666666666668]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 72. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'ab' = 'NJ'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 74. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'ab' = 'NM'. Value: 0.65622775800711743773 [Sum: 1.90890269151138716357]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'ab' = 'NV'. Value: 0.65289256198347107439 [Sum: 1.88095238095238095239]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 86. Value: 0.92857142857142857143 [Distance: 13]
 			 			* 'ab' = 'NY'. Value: 0.65052950075642965205 [Sum: 1.86147186147186147187]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 89. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'ab' = 'OH'. Value: 0.65686274509803921569 [Sum: 1.91428571428571428572]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 98 = 72. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'ab' = 'OK'. Value: 0.65635738831615120275 [Sum: 1.91000000000000000000]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 98 = 75. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'ab' = 'OR'. Value: 0.65451055662188099809 [Sum: 1.89444444444444444445]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 98 = 82. Value: 0.94444444444444444445 [Distance: 17]
 			 			* 'ab' = 'PA'. Value: 0.65739309634209170531 [Sum: 1.91879699248120300753]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'RI'. Value: 0.65566391597899474869 [Sum: 1.90413943355119825709]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ab' = 'SC'. Value: 0.65602605863192182411 [Sum: 1.90719696969696969697]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ab' = 'SD'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ab' = 'TN'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'ab' = 'TX'. Value: 0.64912280701754385966 [Sum: 1.85000000000000000001]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 98 = 88. Value: 0.91666666666666666667 [Distance: 11]
 			 			* 'ab' = 'UT'. Value: 0.65109034267912772586 [Sum: 1.86607142857142857143]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ab' = 'VA'. Value: 0.65451784358390280942 [Sum: 1.89450549450549450551]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'VT'. Value: 0.65042016806722689076 [Sum: 1.86057692307692307693]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ab' = 'WA'. Value: 0.65375103050288540809 [Sum: 1.88809523809523809525]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'WI'. Value: 0.65273311897106109325 [Sum: 1.87962962962962962964]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ab' = 'WV'. Value: 0.64853556485355648536 [Sum: 1.84523809523809523810]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 98 = 86. Value: 0.92857142857142857143 [Distance: 13]
 			 			* 'ab' = 'WY'. Value: 0.64611260053619302950 [Sum: 1.82575757575757575758]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 98 = 89. Value: 0.90909090909090909091 [Distance: 10]
 	 	* Satisfy CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39208633093525179857 [Sum: 0.64497041420118343196]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64497041420118343196 [Best: 0.64497041420118343196]
 			 			* 'aa' = 'AK'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'AL'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'AR'. Value: 0.65656565656565656566 [Sum: 1.91176470588235294118]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'AZ'. Value: 0.65028571428571428572 [Sum: 1.85947712418300653595]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'aa' = 'CA'. Value: 0.65978736710444027518 [Sum: 1.93933823529411764706]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'CO'. Value: 0.65738758029978586724 [Sum: 1.91875000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'CT'. Value: 0.65541995692749461594 [Sum: 1.90208333333333333334]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'DE'. Value: 0.65921582997434957861 [Sum: 1.93440860215053763442]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'DC'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'FL'. Value: 0.65777321703437660339 [Sum: 1.92203898050974512744]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'GA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'HI'. Value: 0.65806137359961032636 [Sum: 1.92450142450142450144]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'IA'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'ID'. Value: 0.65861922914019483270 [Sum: 1.92928039702233250622]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'IL'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'IN'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'KS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'KY'. Value: 0.65014577259475218659 [Sum: 1.85833333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'LA'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MA'. Value: 0.65813528336380255942 [Sum: 1.92513368983957219252]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MD'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'ME'. Value: 0.65767634854771784233 [Sum: 1.92121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'MI'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'MN'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'MO'. Value: 0.65571205007824726135 [Sum: 1.90454545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'MS'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'MT'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'NC'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'ND'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'NE'. Value: 0.65742251223491027733 [Sum: 1.91904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'NH'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'NJ'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'aa' = 'NM'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'aa' = 'NV'. Value: 0.65222929936305732485 [Sum: 1.87545787545787545789]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'NY'. Value: 0.64941569282136894825 [Sum: 1.85238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'OH'. Value: 0.65670692943420216148 [Sum: 1.91296296296296296297]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'OK'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'OR'. Value: 0.65412004069175991862 [Sum: 1.89117647058823529412]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'PA'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'RI'. Value: 0.65549493374902572097 [Sum: 1.90271493212669683259]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'SC'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'SD'. Value: 0.65579458709229701597 [Sum: 1.90524193548387096775]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'TN'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'TX'. Value: 0.64818763326226012794 [Sum: 1.84242424242424242425]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'aa' = 'UT'. Value: 0.65058236272878535774 [Sum: 1.86190476190476190477]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'VA'. Value: 0.65441751368256450352 [Sum: 1.89366515837104072399]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'VT'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'WA'. Value: 0.65365025466893039050 [Sum: 1.88725490196078431373]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'WI'. Value: 0.65256124721603563475 [Sum: 1.87820512820512820514]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'WV'. Value: 0.64785553047404063206 [Sum: 1.83974358974358974360]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'WY'. Value: 0.64497041420118343196 [Sum: 1.81666666666666666667]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]*/ 

-- Negating "CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "Patients"
-- * Success: false
-- * Time: 31222ms 
-- INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'aa', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.28165374677002583980 [Sum: 0.39208633093525179857]
 	 	* Satisfy PRIMARY KEY[MID]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[MID]. Value: 0
 	 	* Violate CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0E-20 [Sum: 0E-20]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: false allowNull: false. Value: 0E-20 [Sum: 0]
 			 			* '' != 'AK'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AR'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AZ'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CO'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'DE'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'DC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'FL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'GA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'HI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ID'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'KS'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'KY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'LA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MD'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ME'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MO'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MS'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ND'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NE'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NH'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NJ'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NM'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NV'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OH'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OK'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OR'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'PA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'RI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'SC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'SD'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'TN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'TX'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'UT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'VA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'VT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WV'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 	 	* Satisfy CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39208633093525179857 [Sum: 0.64497041420118343196]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64497041420118343196 [Best: 0.64497041420118343196]
 			 			* 'aa' = 'AK'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'AL'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'AR'. Value: 0.65656565656565656566 [Sum: 1.91176470588235294118]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'AZ'. Value: 0.65028571428571428572 [Sum: 1.85947712418300653595]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'aa' = 'CA'. Value: 0.65978736710444027518 [Sum: 1.93933823529411764706]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'CO'. Value: 0.65738758029978586724 [Sum: 1.91875000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'CT'. Value: 0.65541995692749461594 [Sum: 1.90208333333333333334]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'DE'. Value: 0.65921582997434957861 [Sum: 1.93440860215053763442]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'DC'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'FL'. Value: 0.65777321703437660339 [Sum: 1.92203898050974512744]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'GA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'HI'. Value: 0.65806137359961032636 [Sum: 1.92450142450142450144]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'IA'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'ID'. Value: 0.65861922914019483270 [Sum: 1.92928039702233250622]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'IL'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'IN'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'KS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'KY'. Value: 0.65014577259475218659 [Sum: 1.85833333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'LA'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MA'. Value: 0.65813528336380255942 [Sum: 1.92513368983957219252]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MD'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'ME'. Value: 0.65767634854771784233 [Sum: 1.92121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'MI'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'MN'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'MO'. Value: 0.65571205007824726135 [Sum: 1.90454545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'MS'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'MT'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'NC'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'ND'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'NE'. Value: 0.65742251223491027733 [Sum: 1.91904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'NH'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'NJ'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'aa' = 'NM'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'aa' = 'NV'. Value: 0.65222929936305732485 [Sum: 1.87545787545787545789]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'NY'. Value: 0.64941569282136894825 [Sum: 1.85238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'OH'. Value: 0.65670692943420216148 [Sum: 1.91296296296296296297]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'OK'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'OR'. Value: 0.65412004069175991862 [Sum: 1.89117647058823529412]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'PA'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'RI'. Value: 0.65549493374902572097 [Sum: 1.90271493212669683259]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'SC'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'SD'. Value: 0.65579458709229701597 [Sum: 1.90524193548387096775]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'TN'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'TX'. Value: 0.64818763326226012794 [Sum: 1.84242424242424242425]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'aa' = 'UT'. Value: 0.65058236272878535774 [Sum: 1.86190476190476190477]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'VA'. Value: 0.65441751368256450352 [Sum: 1.89366515837104072399]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'VT'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'WA'. Value: 0.65365025466893039050 [Sum: 1.88725490196078431373]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'WI'. Value: 0.65256124721603563475 [Sum: 1.87820512820512820514]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'WV'. Value: 0.64785553047404063206 [Sum: 1.83974358974358974360]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'WY'. Value: 0.64497041420118343196 [Sum: 1.81666666666666666667]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]*/ 

-- Negating "CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "Patients"
-- * Success: false
-- * Time: 30838ms 
-- INSERT INTO Patients(MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '', '', '', '', '', '', 'aa', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.28165374677002583980 [Sum: 0.39208633093525179857]
 	 	* Satisfy PRIMARY KEY[MID]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[MID]. Value: 0
 	 	* Satisfy CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39208633093525179857 [Sum: 0.64497041420118343196]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64497041420118343196 [Best: 0.64497041420118343196]
 			 			* 'aa' = 'AK'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'AL'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'AR'. Value: 0.65656565656565656566 [Sum: 1.91176470588235294118]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'AZ'. Value: 0.65028571428571428572 [Sum: 1.85947712418300653595]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'aa' = 'CA'. Value: 0.65978736710444027518 [Sum: 1.93933823529411764706]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'CO'. Value: 0.65738758029978586724 [Sum: 1.91875000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'CT'. Value: 0.65541995692749461594 [Sum: 1.90208333333333333334]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'DE'. Value: 0.65921582997434957861 [Sum: 1.93440860215053763442]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'DC'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'FL'. Value: 0.65777321703437660339 [Sum: 1.92203898050974512744]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'GA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'HI'. Value: 0.65806137359961032636 [Sum: 1.92450142450142450144]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'IA'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'ID'. Value: 0.65861922914019483270 [Sum: 1.92928039702233250622]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'IL'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'IN'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'KS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'KY'. Value: 0.65014577259475218659 [Sum: 1.85833333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'LA'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MA'. Value: 0.65813528336380255942 [Sum: 1.92513368983957219252]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MD'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'ME'. Value: 0.65767634854771784233 [Sum: 1.92121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'MI'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'MN'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'MO'. Value: 0.65571205007824726135 [Sum: 1.90454545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'MS'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'MT'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'NC'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'ND'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'NE'. Value: 0.65742251223491027733 [Sum: 1.91904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'NH'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'NJ'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'aa' = 'NM'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'aa' = 'NV'. Value: 0.65222929936305732485 [Sum: 1.87545787545787545789]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'NY'. Value: 0.64941569282136894825 [Sum: 1.85238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'OH'. Value: 0.65670692943420216148 [Sum: 1.91296296296296296297]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'OK'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'OR'. Value: 0.65412004069175991862 [Sum: 1.89117647058823529412]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'PA'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'RI'. Value: 0.65549493374902572097 [Sum: 1.90271493212669683259]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'SC'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'SD'. Value: 0.65579458709229701597 [Sum: 1.90524193548387096775]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'TN'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'TX'. Value: 0.64818763326226012794 [Sum: 1.84242424242424242425]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'aa' = 'UT'. Value: 0.65058236272878535774 [Sum: 1.86190476190476190477]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'VA'. Value: 0.65441751368256450352 [Sum: 1.89366515837104072399]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'VT'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'WA'. Value: 0.65365025466893039050 [Sum: 1.88725490196078431373]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'WI'. Value: 0.65256124721603563475 [Sum: 1.87820512820512820514]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'WV'. Value: 0.64785553047404063206 [Sum: 1.83974358974358974360]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'WY'. Value: 0.64497041420118343196 [Sum: 1.81666666666666666667]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 	 	* Violate CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0E-20 [Sum: 0E-20]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: false allowNull: false. Value: 0E-20 [Sum: 0]
 			 			* '' != 'AK'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AR'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AZ'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CO'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'DE'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'DC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'FL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'GA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'HI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ID'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'KS'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'KY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'LA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MD'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ME'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MO'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MS'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ND'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NE'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NH'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NJ'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NM'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NV'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OH'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OK'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OR'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'PA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'RI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'SC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'SD'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'TN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'TX'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'UT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'VA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'VT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WV'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0*/ 

-- Negating "PRIMARY KEY[ID]" on table "HistoryPatients"
-- * Success: false
-- * Time: 53624ms 
-- INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '1000-01-01', 0, 0, '', '', '', '', '', '', 'ac', '', '', '', '', '', '', '', '', '', '', '', '', '', 'aa', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID]. Value: 0.56235241200944563140 [Sum: 1.28494347379239465572]
 	 	* Violate PRIMARY KEY[ID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ID]. Value: 1
 	 	* Satisfy NOT NULL(changeDate). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(changeMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39285714285714285715 [Sum: 0.64705882352941176471]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64705882352941176471 [Best: 0.64705882352941176471]
 			 			* 'ac' = 'AK'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 99 = 75. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'ac' = 'AL'. Value: 0.65877157767964672823 [Sum: 1.93058823529411764706]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 99 = 76. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'ac' = 'AR'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 99 = 82. Value: 0.94736842105263157895 [Distance: 18]
 			 			* 'ac' = 'AZ'. Value: 0.65273909006499535748 [Sum: 1.87967914438502673797]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 99 = 90. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'ac' = 'CA'. Value: 0.65997638724911452185 [Sum: 1.94097222222222222223]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 			 			* 'ac' = 'CO'. Value: 0.65792031098153547134 [Sum: 1.92329545454545454546]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 99 = 79. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'ac' = 'CT'. Value: 0.65634870499052432091 [Sum: 1.90992647058823529412]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 99 = 84. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ac' = 'DE'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 99 = 69. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ac' = 'DC'. Value: 0.65967064901517597676 [Sum: 1.93833017077798861481]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 99 = 67. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ac' = 'FL'. Value: 0.65818010372465818011 [Sum: 1.92551724137931034483]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 99 = 76. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'ac' = 'GA'. Value: 0.65945945945945945947 [Sum: 1.93650793650793650795]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 			 			* 'ac' = 'HI'. Value: 0.65838228648892905559 [Sum: 1.92724867724867724869]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 99 = 73. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'ac' = 'IA'. Value: 0.65914056809905316825 [Sum: 1.93376068376068376070]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 			 			* 'ac' = 'ID'. Value: 0.65884691848906560637 [Sum: 1.93123543123543123544]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 99 = 68. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ac' = 'IL'. Value: 0.65771458662453923118 [Sum: 1.92153846153846153847]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 99 = 76. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'ac' = 'IN'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'ac' = 'KS'. Value: 0.65550239234449760766 [Sum: 1.90277777777777777779]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 99 = 83. Value: 0.94444444444444444445 [Distance: 17]
 			 			* 'ac' = 'KY'. Value: 0.65217391304347826088 [Sum: 1.87500000000000000001]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 99 = 89. Value: 0.91666666666666666667 [Distance: 11]
 			 			* 'ac' = 'LA'. Value: 0.65855670103092783506 [Sum: 1.92874396135265700484]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 			 			* 'ac' = 'MA'. Value: 0.65832614322691975842 [Sum: 1.92676767676767676769]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 			 			* 'ac' = 'MD'. Value: 0.65803108808290155441 [Sum: 1.92424242424242424243]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 99 = 68. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ac' = 'ME'. Value: 0.65792031098153547134 [Sum: 1.92329545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 99 = 69. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ac' = 'MI'. Value: 0.65739710789766407120 [Sum: 1.91883116883116883118]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 99 = 73. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'ac' = 'MN'. Value: 0.65648336727766463001 [Sum: 1.91106719367588932807]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'ac' = 'MO'. Value: 0.65625000000000000001 [Sum: 1.90909090909090909092]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 99 = 79. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'ac' = 'MS'. Value: 0.65505226480836236934 [Sum: 1.89898989898989898991]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 99 = 83. Value: 0.94444444444444444445 [Distance: 17]
 			 			* 'ac' = 'MT'. Value: 0.65466297322253000924 [Sum: 1.89572192513368983958]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 99 = 84. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ac' = 'NC'. Value: 0.65788212745567800671 [Sum: 1.92296918767507002802]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 99 = 67. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ac' = 'ND'. Value: 0.65777777777777777778 [Sum: 1.92207792207792207793]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 99 = 68. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ac' = 'NE'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 99 = 69. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ac' = 'NH'. Value: 0.65728756330894766461 [Sum: 1.91789819376026272579]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 99 = 72. Value: 0.96551724137931034483 [Distance: 28]
 			 			* 'ac' = 'NJ'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 99 = 74. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ac' = 'NM'. Value: 0.65644171779141104295 [Sum: 1.91071428571428571430]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 99 = 77. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'ac' = 'NV'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 99 = 86. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'ac' = 'NY'. Value: 0.65145228215767634855 [Sum: 1.86904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 99 = 89. Value: 0.91666666666666666667 [Distance: 11]
 			 			* 'ac' = 'OH'. Value: 0.65700768775872264933 [Sum: 1.91551724137931034483]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 99 = 72. Value: 0.96551724137931034483 [Distance: 28]
 			 			* 'ac' = 'OK'. Value: 0.65653896961690885073 [Sum: 1.91153846153846153847]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 99 = 75. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'ac' = 'OR'. Value: 0.65485921889191643961 [Sum: 1.89736842105263157895]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 99 = 82. Value: 0.94736842105263157895 [Distance: 18]
 			 			* 'ac' = 'PA'. Value: 0.65748622934401602404 [Sum: 1.91959064327485380118]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 			 			* 'ac' = 'RI'. Value: 0.65582067968185104845 [Sum: 1.90546218487394957984]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 99 = 73. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'ac' = 'SC'. Value: 0.65613147914032869786 [Sum: 1.90808823529411764706]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 99 = 67. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ac' = 'SD'. Value: 0.65602605863192182411 [Sum: 1.90719696969696969697]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 99 = 68. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ac' = 'TN'. Value: 0.65396188565697091274 [Sum: 1.88985507246376811595]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'ac' = 'TX'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 99 = 88. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'ac' = 'UT'. Value: 0.65153733528550512446 [Sum: 1.86974789915966386555]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 99 = 84. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ac' = 'VA'. Value: 0.65461254612546125462 [Sum: 1.89529914529914529916]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 			 			* 'ac' = 'VT'. Value: 0.65086887835703001580 [Sum: 1.86425339366515837105]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 99 = 84. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ac' = 'WA'. Value: 0.65384615384615384616 [Sum: 1.88888888888888888890]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 			 			* 'ac' = 'WI'. Value: 0.65289256198347107439 [Sum: 1.88095238095238095239]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 99 = 73. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'ac' = 'WV'. Value: 0.64912280701754385966 [Sum: 1.85000000000000000001]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 99 = 86. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'ac' = 'WY'. Value: 0.64705882352941176471 [Sum: 1.83333333333333333334]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 99 = 89. Value: 0.91666666666666666667 [Distance: 11]
 	 	* Satisfy CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39208633093525179857 [Sum: 0.64497041420118343196]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64497041420118343196 [Best: 0.64497041420118343196]
 			 			* 'aa' = 'AK'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'AL'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'AR'. Value: 0.65656565656565656566 [Sum: 1.91176470588235294118]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'AZ'. Value: 0.65028571428571428572 [Sum: 1.85947712418300653595]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'aa' = 'CA'. Value: 0.65978736710444027518 [Sum: 1.93933823529411764706]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'CO'. Value: 0.65738758029978586724 [Sum: 1.91875000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'CT'. Value: 0.65541995692749461594 [Sum: 1.90208333333333333334]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'DE'. Value: 0.65921582997434957861 [Sum: 1.93440860215053763442]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'DC'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'FL'. Value: 0.65777321703437660339 [Sum: 1.92203898050974512744]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'GA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'HI'. Value: 0.65806137359961032636 [Sum: 1.92450142450142450144]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'IA'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'ID'. Value: 0.65861922914019483270 [Sum: 1.92928039702233250622]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'IL'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'IN'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'KS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'KY'. Value: 0.65014577259475218659 [Sum: 1.85833333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'LA'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MA'. Value: 0.65813528336380255942 [Sum: 1.92513368983957219252]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MD'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'ME'. Value: 0.65767634854771784233 [Sum: 1.92121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'MI'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'MN'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'MO'. Value: 0.65571205007824726135 [Sum: 1.90454545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'MS'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'MT'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'NC'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'ND'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'NE'. Value: 0.65742251223491027733 [Sum: 1.91904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'NH'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'NJ'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'aa' = 'NM'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'aa' = 'NV'. Value: 0.65222929936305732485 [Sum: 1.87545787545787545789]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'NY'. Value: 0.64941569282136894825 [Sum: 1.85238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'OH'. Value: 0.65670692943420216148 [Sum: 1.91296296296296296297]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'OK'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'OR'. Value: 0.65412004069175991862 [Sum: 1.89117647058823529412]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'PA'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'RI'. Value: 0.65549493374902572097 [Sum: 1.90271493212669683259]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'SC'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'SD'. Value: 0.65579458709229701597 [Sum: 1.90524193548387096775]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'TN'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'TX'. Value: 0.64818763326226012794 [Sum: 1.84242424242424242425]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'aa' = 'UT'. Value: 0.65058236272878535774 [Sum: 1.86190476190476190477]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'VA'. Value: 0.65441751368256450352 [Sum: 1.89366515837104072399]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'VT'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'WA'. Value: 0.65365025466893039050 [Sum: 1.88725490196078431373]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'WI'. Value: 0.65256124721603563475 [Sum: 1.87820512820512820514]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'WV'. Value: 0.64785553047404063206 [Sum: 1.83974358974358974360]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'WY'. Value: 0.64497041420118343196 [Sum: 1.81666666666666666667]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]*/ 

-- Negating "NOT NULL(changeDate)" on table "HistoryPatients"
-- * Success: false
-- * Time: 52868ms 
-- INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, NULL, 0, 0, '', '', '', '', '', '', 'aa', '', '', '', '', '', '', '', '', '', '', '', '', '', 'ab', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(changeDate). Value: 0.43964860677049137275 [Sum: 0.78459447425772736861]
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 	 	* Violate NOT NULL(changeDate). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(changeMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39208633093525179857 [Sum: 0.64497041420118343196]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64497041420118343196 [Best: 0.64497041420118343196]
 			 			* 'aa' = 'AK'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'AL'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'AR'. Value: 0.65656565656565656566 [Sum: 1.91176470588235294118]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'AZ'. Value: 0.65028571428571428572 [Sum: 1.85947712418300653595]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'aa' = 'CA'. Value: 0.65978736710444027518 [Sum: 1.93933823529411764706]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'CO'. Value: 0.65738758029978586724 [Sum: 1.91875000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'CT'. Value: 0.65541995692749461594 [Sum: 1.90208333333333333334]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'DE'. Value: 0.65921582997434957861 [Sum: 1.93440860215053763442]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'DC'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'FL'. Value: 0.65777321703437660339 [Sum: 1.92203898050974512744]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'GA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'HI'. Value: 0.65806137359961032636 [Sum: 1.92450142450142450144]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'IA'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'ID'. Value: 0.65861922914019483270 [Sum: 1.92928039702233250622]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'IL'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'IN'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'KS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'KY'. Value: 0.65014577259475218659 [Sum: 1.85833333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'LA'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MA'. Value: 0.65813528336380255942 [Sum: 1.92513368983957219252]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MD'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'ME'. Value: 0.65767634854771784233 [Sum: 1.92121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'MI'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'MN'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'MO'. Value: 0.65571205007824726135 [Sum: 1.90454545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'MS'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'MT'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'NC'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'ND'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'NE'. Value: 0.65742251223491027733 [Sum: 1.91904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'NH'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'NJ'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'aa' = 'NM'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'aa' = 'NV'. Value: 0.65222929936305732485 [Sum: 1.87545787545787545789]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'NY'. Value: 0.64941569282136894825 [Sum: 1.85238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'OH'. Value: 0.65670692943420216148 [Sum: 1.91296296296296296297]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'OK'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'OR'. Value: 0.65412004069175991862 [Sum: 1.89117647058823529412]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'PA'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'RI'. Value: 0.65549493374902572097 [Sum: 1.90271493212669683259]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'SC'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'SD'. Value: 0.65579458709229701597 [Sum: 1.90524193548387096775]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'TN'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'TX'. Value: 0.64818763326226012794 [Sum: 1.84242424242424242425]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'aa' = 'UT'. Value: 0.65058236272878535774 [Sum: 1.86190476190476190477]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'VA'. Value: 0.65441751368256450352 [Sum: 1.89366515837104072399]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'VT'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'WA'. Value: 0.65365025466893039050 [Sum: 1.88725490196078431373]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'WI'. Value: 0.65256124721603563475 [Sum: 1.87820512820512820514]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'WV'. Value: 0.64785553047404063206 [Sum: 1.83974358974358974360]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'WY'. Value: 0.64497041420118343196 [Sum: 1.81666666666666666667]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 	 	* Satisfy CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39250814332247557004 [Sum: 0.64611260053619302950]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64611260053619302950 [Best: 0.64611260053619302950]
 			 			* 'ab' = 'AK'. Value: 0.65877157767964672823 [Sum: 1.93058823529411764706]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 98 = 75. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'ab' = 'AL'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'ab' = 'AR'. Value: 0.65695067264573991032 [Sum: 1.91503267973856209151]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 98 = 82. Value: 0.94444444444444444445 [Distance: 17]
 			 			* 'ab' = 'AZ'. Value: 0.65163934426229508197 [Sum: 1.87058823529411764706]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 98 = 90. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'ab' = 'CA'. Value: 0.65988460370482842394 [Sum: 1.94017857142857142858]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'CO'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'ab' = 'CT'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ab' = 'DE'. Value: 0.65934065934065934067 [Sum: 1.93548387096774193550]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ab' = 'DC'. Value: 0.65956738768718801997 [Sum: 1.93743890518084066472]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ab' = 'FL'. Value: 0.65798525798525798526 [Sum: 1.92385057471264367817]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'ab' = 'GA'. Value: 0.65936739659367396594 [Sum: 1.93571428571428571430]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'HI'. Value: 0.65822784810126582279 [Sum: 1.92592592592592592594]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ab' = 'IA'. Value: 0.65904833270887973024 [Sum: 1.93296703296703296705]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'ID'. Value: 0.65873666940114848237 [Sum: 1.93028846153846153847]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ab' = 'IL'. Value: 0.65751920965971459935 [Sum: 1.91987179487179487181]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'ab' = 'IN'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'ab' = 'KS'. Value: 0.65511411665257819105 [Sum: 1.89950980392156862746]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ab' = 'KY'. Value: 0.65125495376486129459 [Sum: 1.86742424242424242425]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 98 = 89. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'ab' = 'LA'. Value: 0.65846414934238438694 [Sum: 1.92795031055900621119]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'MA'. Value: 0.65823346648912561030 [Sum: 1.92597402597402597404]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'MD'. Value: 0.65792031098153547134 [Sum: 1.92329545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ab' = 'ME'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ab' = 'MI'. Value: 0.65724177726485862667 [Sum: 1.91750841750841750843]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ab' = 'MN'. Value: 0.65625000000000000001 [Sum: 1.90909090909090909092]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'ab' = 'MO'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'ab' = 'MS'. Value: 0.65466297322253000924 [Sum: 1.89572192513368983958]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ab' = 'MT'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ab' = 'NC'. Value: 0.65777777777777777778 [Sum: 1.92207792207792207793]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ab' = 'ND'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ab' = 'NE'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ab' = 'NH'. Value: 0.65714285714285714286 [Sum: 1.91666666666666666668]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 72. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'ab' = 'NJ'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 74. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'ab' = 'NM'. Value: 0.65622775800711743773 [Sum: 1.90890269151138716357]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'ab' = 'NV'. Value: 0.65289256198347107439 [Sum: 1.88095238095238095239]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 86. Value: 0.92857142857142857143 [Distance: 13]
 			 			* 'ab' = 'NY'. Value: 0.65052950075642965205 [Sum: 1.86147186147186147187]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 89. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'ab' = 'OH'. Value: 0.65686274509803921569 [Sum: 1.91428571428571428572]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 98 = 72. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'ab' = 'OK'. Value: 0.65635738831615120275 [Sum: 1.91000000000000000000]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 98 = 75. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'ab' = 'OR'. Value: 0.65451055662188099809 [Sum: 1.89444444444444444445]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 98 = 82. Value: 0.94444444444444444445 [Distance: 17]
 			 			* 'ab' = 'PA'. Value: 0.65739309634209170531 [Sum: 1.91879699248120300753]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'RI'. Value: 0.65566391597899474869 [Sum: 1.90413943355119825709]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ab' = 'SC'. Value: 0.65602605863192182411 [Sum: 1.90719696969696969697]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'ab' = 'SD'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ab' = 'TN'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'ab' = 'TX'. Value: 0.64912280701754385966 [Sum: 1.85000000000000000001]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 98 = 88. Value: 0.91666666666666666667 [Distance: 11]
 			 			* 'ab' = 'UT'. Value: 0.65109034267912772586 [Sum: 1.86607142857142857143]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ab' = 'VA'. Value: 0.65451784358390280942 [Sum: 1.89450549450549450551]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'VT'. Value: 0.65042016806722689076 [Sum: 1.86057692307692307693]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ab' = 'WA'. Value: 0.65375103050288540809 [Sum: 1.88809523809523809525]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'ab' = 'WI'. Value: 0.65273311897106109325 [Sum: 1.87962962962962962964]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ab' = 'WV'. Value: 0.64853556485355648536 [Sum: 1.84523809523809523810]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 98 = 86. Value: 0.92857142857142857143 [Distance: 13]
 			 			* 'ab' = 'WY'. Value: 0.64611260053619302950 [Sum: 1.82575757575757575758]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 98 = 89. Value: 0.90909090909090909091 [Distance: 10]*/ 

-- Negating "NOT NULL(changeMID)" on table "HistoryPatients"
-- * Success: false
-- * Time: 54892ms 
-- INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '1000-01-01', NULL, 0, '', '', '', '', '', '', 'aa', '', '', '', '', '', '', '', '', '', '', '', '', '', 'ca', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(changeMID). Value: 0.43968938953668096894 [Sum: 0.78472436774506774950]
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 	 	* Satisfy NOT NULL(changeDate). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Violate NOT NULL(changeMID). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(MID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39208633093525179857 [Sum: 0.64497041420118343196]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64497041420118343196 [Best: 0.64497041420118343196]
 			 			* 'aa' = 'AK'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'AL'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'AR'. Value: 0.65656565656565656566 [Sum: 1.91176470588235294118]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'AZ'. Value: 0.65028571428571428572 [Sum: 1.85947712418300653595]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'aa' = 'CA'. Value: 0.65978736710444027518 [Sum: 1.93933823529411764706]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'CO'. Value: 0.65738758029978586724 [Sum: 1.91875000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'CT'. Value: 0.65541995692749461594 [Sum: 1.90208333333333333334]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'DE'. Value: 0.65921582997434957861 [Sum: 1.93440860215053763442]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'DC'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'FL'. Value: 0.65777321703437660339 [Sum: 1.92203898050974512744]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'GA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'HI'. Value: 0.65806137359961032636 [Sum: 1.92450142450142450144]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'IA'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'ID'. Value: 0.65861922914019483270 [Sum: 1.92928039702233250622]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'IL'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'IN'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'KS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'KY'. Value: 0.65014577259475218659 [Sum: 1.85833333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'LA'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MA'. Value: 0.65813528336380255942 [Sum: 1.92513368983957219252]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MD'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'ME'. Value: 0.65767634854771784233 [Sum: 1.92121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'MI'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'MN'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'MO'. Value: 0.65571205007824726135 [Sum: 1.90454545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'MS'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'MT'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'NC'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'ND'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'NE'. Value: 0.65742251223491027733 [Sum: 1.91904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'NH'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'NJ'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'aa' = 'NM'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'aa' = 'NV'. Value: 0.65222929936305732485 [Sum: 1.87545787545787545789]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'NY'. Value: 0.64941569282136894825 [Sum: 1.85238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'OH'. Value: 0.65670692943420216148 [Sum: 1.91296296296296296297]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'OK'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'OR'. Value: 0.65412004069175991862 [Sum: 1.89117647058823529412]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'PA'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'RI'. Value: 0.65549493374902572097 [Sum: 1.90271493212669683259]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'SC'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'SD'. Value: 0.65579458709229701597 [Sum: 1.90524193548387096775]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'TN'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'TX'. Value: 0.64818763326226012794 [Sum: 1.84242424242424242425]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'aa' = 'UT'. Value: 0.65058236272878535774 [Sum: 1.86190476190476190477]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'VA'. Value: 0.65441751368256450352 [Sum: 1.89366515837104072399]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'VT'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'WA'. Value: 0.65365025466893039050 [Sum: 1.88725490196078431373]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'WI'. Value: 0.65256124721603563475 [Sum: 1.87820512820512820514]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'WV'. Value: 0.64785553047404063206 [Sum: 1.83974358974358974360]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'WY'. Value: 0.64497041420118343196 [Sum: 1.81666666666666666667]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 	 	* Satisfy CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39263803680981595093 [Sum: 0.64646464646464646465]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64646464646464646465 [Best: 0.64646464646464646465]
 			 			* 'ca' = 'AK'. Value: 0.65876777251184834124 [Sum: 1.93055555555555555557]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'ca' = 'AL'. Value: 0.65855670103092783506 [Sum: 1.92874396135265700484]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'ca' = 'AR'. Value: 0.65675827257431295570 [Sum: 1.91339869281045751635]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ca' = 'AZ'. Value: 0.65048543689320388350 [Sum: 1.86111111111111111112]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'ca' = 'CA'. Value: 0.66000000000000000001 [Sum: 1.94117647058823529412]
 				 				* 99 = 67. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ca' = 'CO'. Value: 0.65760322255790533737 [Sum: 1.92058823529411764706]
 				 				* 99 = 67. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'ca' = 'CT'. Value: 0.65563808237677245105 [Sum: 1.90392156862745098040]
 				 				* 99 = 67. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'ca' = 'DE'. Value: 0.65944272445820433437 [Sum: 1.93636363636363636364]
 				 				* 99 = 68. Value: 0.96969696969696969697 [Distance: 32]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'ca' = 'DC'. Value: 0.65968417660328714148 [Sum: 1.93844696969696969697]
 				 				* 99 = 68. Value: 0.96969696969696969697 [Distance: 32]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ca' = 'FL'. Value: 0.65803357314148681056 [Sum: 1.92426367461430575036]
 				 				* 99 = 70. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'ca' = 'GA'. Value: 0.65954606141522029373 [Sum: 1.93725490196078431373]
 				 				* 99 = 71. Value: 0.96666666666666666667 [Distance: 29]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ca' = 'HI'. Value: 0.65835976438604440417 [Sum: 1.92705570291777188330]
 				 				* 99 = 72. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'ca' = 'IA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 99 = 73. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ca' = 'ID'. Value: 0.65893909626719056975 [Sum: 1.93202764976958525347]
 				 				* 99 = 73. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ca' = 'IL'. Value: 0.65762892078681552366 [Sum: 1.92080745341614906833]
 				 				* 99 = 73. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'ca' = 'IN'. Value: 0.65714285714285714286 [Sum: 1.91666666666666666668]
 				 				* 99 = 73. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'ca' = 'KS'. Value: 0.65505804311774461029 [Sum: 1.89903846153846153847]
 				 				* 99 = 75. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ca' = 'KY'. Value: 0.65053763440860215054 [Sum: 1.86153846153846153847]
 				 				* 99 = 75. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'ca' = 'LA'. Value: 0.65877157767964672823 [Sum: 1.93058823529411764706]
 				 				* 99 = 76. Value: 0.96000000000000000000 [Distance: 24]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ca' = 'MA'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 99 = 77. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ca' = 'MD'. Value: 0.65824529168580615527 [Sum: 1.92607526881720430109]
 				 				* 99 = 77. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ca' = 'ME'. Value: 0.65811965811965811966 [Sum: 1.92500000000000000001]
 				 				* 99 = 77. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'ca' = 'MI'. Value: 0.65751920965971459935 [Sum: 1.91987179487179487181]
 				 				* 99 = 77. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'ca' = 'MN'. Value: 0.65644171779141104295 [Sum: 1.91071428571428571430]
 				 				* 99 = 77. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'ca' = 'MO'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 99 = 77. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'ca' = 'MS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 99 = 77. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'ca' = 'MT'. Value: 0.65417867435158501442 [Sum: 1.89166666666666666668]
 				 				* 99 = 77. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'ca' = 'NC'. Value: 0.65815141662796098468 [Sum: 1.92527173913043478261]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ca' = 'ND'. Value: 0.65803357314148681056 [Sum: 1.92426367461430575036]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ca' = 'NE'. Value: 0.65790778383738225087 [Sum: 1.92318840579710144928]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'ca' = 'NH'. Value: 0.65747380033094318809 [Sum: 1.91948470209339774558]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ca' = 'NJ'. Value: 0.65712581991651759094 [Sum: 1.91652173913043478261]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'ca' = 'NM'. Value: 0.65648336727766463001 [Sum: 1.91106719367588932807]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'ca' = 'NV'. Value: 0.65272938443670150988 [Sum: 1.87959866220735785954]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'ca' = 'NY'. Value: 0.64992389649923896500 [Sum: 1.85652173913043478261]
 				 				* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'ca' = 'OH'. Value: 0.65724177726485862667 [Sum: 1.91750841750841750843]
 				 				* 99 = 79. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'ca' = 'OK'. Value: 0.65669700910273081925 [Sum: 1.91287878787878787880]
 				 				* 99 = 79. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'ca' = 'OR'. Value: 0.65466297322253000924 [Sum: 1.89572192513368983958]
 				 				* 99 = 79. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'ca' = 'PA'. Value: 0.65788212745567800671 [Sum: 1.92296918767507002802]
 				 				* 99 = 80. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ca' = 'RI'. Value: 0.65622825330549756438 [Sum: 1.90890688259109311742]
 				 				* 99 = 82. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'ca' = 'SC'. Value: 0.65673420738974970203 [Sum: 1.91319444444444444445]
 				 				* 99 = 83. Value: 0.94444444444444444445 [Distance: 17]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'ca' = 'SD'. Value: 0.65661538461538461539 [Sum: 1.91218637992831541220]
 				 				* 99 = 83. Value: 0.94444444444444444445 [Distance: 17]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'ca' = 'TN'. Value: 0.65440464666021297193 [Sum: 1.89355742296918767508]
 				 				* 99 = 84. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'ca' = 'TX'. Value: 0.64915572232645403378 [Sum: 1.85026737967914438503]
 				 				* 99 = 84. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'ca' = 'UT'. Value: 0.65166908563134978230 [Sum: 1.87083333333333333334]
 				 				* 99 = 85. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'ca' = 'VA'. Value: 0.65563808237677245105 [Sum: 1.90392156862745098040]
 				 				* 99 = 86. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ca' = 'VT'. Value: 0.65116279069767441861 [Sum: 1.86666666666666666668]
 				 				* 99 = 86. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'ca' = 'WA'. Value: 0.65507246376811594203 [Sum: 1.89915966386554621849]
 				 				* 99 = 87. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'ca' = 'WI'. Value: 0.65399239543726235742 [Sum: 1.89010989010989010990]
 				 				* 99 = 87. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'ca' = 'WV'. Value: 0.64932562620423892101 [Sum: 1.85164835164835164836]
 				 				* 99 = 87. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'ca' = 'WY'. Value: 0.64646464646464646465 [Sum: 1.82857142857142857143]
 				 				* 99 = 87. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]*/ 

-- Negating "NOT NULL(MID)" on table "HistoryPatients"
-- * Success: false
-- * Time: 54577ms 
-- INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '1000-01-01', 0, NULL, '', '', '', '', '', '', 'aa', '', '', '', '', '', '', '', '', '', '', '', '', '', 'bb', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(MID). Value: 0.43974136237970782410 [Sum: 0.78488992913615134880]
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 	 	* Satisfy NOT NULL(changeDate). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(changeMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Violate NOT NULL(MID). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39208633093525179857 [Sum: 0.64497041420118343196]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64497041420118343196 [Best: 0.64497041420118343196]
 			 			* 'aa' = 'AK'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'AL'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'AR'. Value: 0.65656565656565656566 [Sum: 1.91176470588235294118]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'AZ'. Value: 0.65028571428571428572 [Sum: 1.85947712418300653595]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'aa' = 'CA'. Value: 0.65978736710444027518 [Sum: 1.93933823529411764706]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'CO'. Value: 0.65738758029978586724 [Sum: 1.91875000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'CT'. Value: 0.65541995692749461594 [Sum: 1.90208333333333333334]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'DE'. Value: 0.65921582997434957861 [Sum: 1.93440860215053763442]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'DC'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'FL'. Value: 0.65777321703437660339 [Sum: 1.92203898050974512744]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'GA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'HI'. Value: 0.65806137359961032636 [Sum: 1.92450142450142450144]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'IA'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'ID'. Value: 0.65861922914019483270 [Sum: 1.92928039702233250622]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'IL'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'IN'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'KS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'KY'. Value: 0.65014577259475218659 [Sum: 1.85833333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'LA'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MA'. Value: 0.65813528336380255942 [Sum: 1.92513368983957219252]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MD'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'ME'. Value: 0.65767634854771784233 [Sum: 1.92121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'MI'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'MN'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'MO'. Value: 0.65571205007824726135 [Sum: 1.90454545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'MS'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'MT'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'NC'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'ND'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'NE'. Value: 0.65742251223491027733 [Sum: 1.91904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'NH'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'NJ'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'aa' = 'NM'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'aa' = 'NV'. Value: 0.65222929936305732485 [Sum: 1.87545787545787545789]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'NY'. Value: 0.64941569282136894825 [Sum: 1.85238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'OH'. Value: 0.65670692943420216148 [Sum: 1.91296296296296296297]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'OK'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'OR'. Value: 0.65412004069175991862 [Sum: 1.89117647058823529412]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'PA'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'RI'. Value: 0.65549493374902572097 [Sum: 1.90271493212669683259]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'SC'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'SD'. Value: 0.65579458709229701597 [Sum: 1.90524193548387096775]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'TN'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'TX'. Value: 0.64818763326226012794 [Sum: 1.84242424242424242425]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'aa' = 'UT'. Value: 0.65058236272878535774 [Sum: 1.86190476190476190477]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'VA'. Value: 0.65441751368256450352 [Sum: 1.89366515837104072399]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'VT'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'WA'. Value: 0.65365025466893039050 [Sum: 1.88725490196078431373]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'WI'. Value: 0.65256124721603563475 [Sum: 1.87820512820512820514]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'WV'. Value: 0.64785553047404063206 [Sum: 1.83974358974358974360]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'WY'. Value: 0.64497041420118343196 [Sum: 1.81666666666666666667]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 	 	* Satisfy CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39280359820089955023 [Sum: 0.64691358024691358025]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64691358024691358025 [Best: 0.64691358024691358025]
 			 			* 'bb' = 'AK'. Value: 0.65886939571150097466 [Sum: 1.93142857142857142858]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 				 				* 98 = 75. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'bb' = 'AL'. Value: 0.65867533522958147095 [Sum: 1.92976190476190476192]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'bb' = 'AR'. Value: 0.65704953728905824715 [Sum: 1.91587301587301587303]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 				 				* 98 = 82. Value: 0.94444444444444444445 [Distance: 17]
 			 			* 'bb' = 'AZ'. Value: 0.65174129353233830846 [Sum: 1.87142857142857142858]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 				 				* 98 = 90. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'bb' = 'CA'. Value: 0.65999411245216367384 [Sum: 1.94112554112554112555]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'bb' = 'CO'. Value: 0.65777777777777777778 [Sum: 1.92207792207792207793]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 				 				* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'bb' = 'CT'. Value: 0.65602605863192182411 [Sum: 1.90719696969696969697]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'bb' = 'DE'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'bb' = 'DC'. Value: 0.65968417660328714148 [Sum: 1.93844696969696969697]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'bb' = 'FL'. Value: 0.65811965811965811966 [Sum: 1.92500000000000000001]
 				 				* 98 = 70. Value: 0.96666666666666666667 [Distance: 29]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'bb' = 'GA'. Value: 0.65951023146595102315 [Sum: 1.93694581280788177341]
 				 				* 98 = 71. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'bb' = 'HI'. Value: 0.65838228648892905559 [Sum: 1.92724867724867724869]
 				 				* 98 = 72. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'bb' = 'IA'. Value: 0.65921384781824738551 [Sum: 1.93439153439153439155]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'bb' = 'ID'. Value: 0.65890248716936439006 [Sum: 1.93171296296296296297]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'bb' = 'IL'. Value: 0.65768621236133122029 [Sum: 1.92129629629629629631]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'bb' = 'IN'. Value: 0.65724177726485862667 [Sum: 1.91750841750841750843]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'bb' = 'KS'. Value: 0.65531224655312246554 [Sum: 1.90117647058823529412]
 				 				* 98 = 75. Value: 0.96000000000000000000 [Distance: 24]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'bb' = 'KY'. Value: 0.65145754119138149557 [Sum: 1.86909090909090909091]
 				 				* 98 = 75. Value: 0.96000000000000000000 [Distance: 24]
 				 				* 98 = 89. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'bb' = 'LA'. Value: 0.65867533522958147095 [Sum: 1.92976190476190476192]
 				 				* 98 = 76. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'bb' = 'MA'. Value: 0.65846414934238438694 [Sum: 1.92795031055900621119]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'bb' = 'MD'. Value: 0.65815141662796098468 [Sum: 1.92527173913043478261]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'bb' = 'ME'. Value: 0.65803357314148681056 [Sum: 1.92426367461430575036]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'bb' = 'MI'. Value: 0.65747380033094318809 [Sum: 1.91948470209339774558]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'bb' = 'MN'. Value: 0.65648336727766463001 [Sum: 1.91106719367588932807]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'bb' = 'MO'. Value: 0.65622775800711743773 [Sum: 1.90890269151138716357]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'bb' = 'MS'. Value: 0.65489849955869373346 [Sum: 1.89769820971867007673]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'bb' = 'MT'. Value: 0.65446009389671361503 [Sum: 1.89402173913043478261]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'bb' = 'NC'. Value: 0.65803108808290155441 [Sum: 1.92424242424242424243]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'bb' = 'ND'. Value: 0.65792031098153547134 [Sum: 1.92329545454545454546]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'bb' = 'NE'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'bb' = 'NH'. Value: 0.65739710789766407120 [Sum: 1.91883116883116883118]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 72. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'bb' = 'NJ'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 74. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'bb' = 'NM'. Value: 0.65648336727766463001 [Sum: 1.91106719367588932807]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'bb' = 'NV'. Value: 0.65315315315315315316 [Sum: 1.88311688311688311689]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 86. Value: 0.92857142857142857143 [Distance: 13]
 			 			* 'bb' = 'NY'. Value: 0.65079365079365079366 [Sum: 1.86363636363636363637]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 98 = 89. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'bb' = 'OH'. Value: 0.65714285714285714286 [Sum: 1.91666666666666666668]
 				 				* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 72. Value: 0.96428571428571428572 [Distance: 27]
 			 			* 'bb' = 'OK'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 75. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'bb' = 'OR'. Value: 0.65479452054794520549 [Sum: 1.89682539682539682541]
 				 				* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 98 = 82. Value: 0.94444444444444444445 [Distance: 17]
 			 			* 'bb' = 'PA'. Value: 0.65770171149144254279 [Sum: 1.92142857142857142858]
 				 				* 98 = 80. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'bb' = 'RI'. Value: 0.65605095541401273886 [Sum: 1.90740740740740740742]
 				 				* 98 = 82. Value: 0.94444444444444444445 [Distance: 17]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'bb' = 'SC'. Value: 0.65646050214329454991 [Sum: 1.91087344028520499109]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 98 = 67. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'bb' = 'SD'. Value: 0.65634870499052432091 [Sum: 1.90992647058823529412]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 98 = 68. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'bb' = 'TN'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'bb' = 'TX'. Value: 0.64963503649635036497 [Sum: 1.85416666666666666667]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 98 = 88. Value: 0.91666666666666666667 [Distance: 11]
 			 			* 'bb' = 'UT'. Value: 0.65166908563134978230 [Sum: 1.87083333333333333334]
 				 				* 98 = 85. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'bb' = 'VA'. Value: 0.65517241379310344828 [Sum: 1.90000000000000000001]
 				 				* 98 = 86. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'bb' = 'VT'. Value: 0.65109034267912772586 [Sum: 1.86607142857142857143]
 				 				* 98 = 86. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 98 = 84. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'bb' = 'WA'. Value: 0.65451784358390280942 [Sum: 1.89450549450549450551]
 				 				* 98 = 87. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 			 			* 'bb' = 'WI'. Value: 0.65350444225074037513 [Sum: 1.88603988603988603990]
 				 				* 98 = 87. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 98 = 73. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'bb' = 'WV'. Value: 0.64932562620423892101 [Sum: 1.85164835164835164836]
 				 				* 98 = 87. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 98 = 86. Value: 0.92857142857142857143 [Distance: 13]
 			 			* 'bb' = 'WY'. Value: 0.64691358024691358025 [Sum: 1.83216783216783216784]
 				 				* 98 = 87. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 98 = 89. Value: 0.90909090909090909091 [Distance: 10]*/ 

-- Negating "CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "HistoryPatients"
-- * Success: false
-- * Time: 33994ms 
-- INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '1000-01-01', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'aa', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.28165374677002583980 [Sum: 0.39208633093525179857]
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 	 	* Satisfy NOT NULL(changeDate). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(changeMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Violate CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0E-20 [Sum: 0E-20]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: false allowNull: false. Value: 0E-20 [Sum: 0]
 			 			* '' != 'AK'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AR'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AZ'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CO'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'DE'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'DC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'FL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'GA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'HI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ID'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'KS'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'KY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'LA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MD'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ME'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MO'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MS'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ND'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NE'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NH'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NJ'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NM'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NV'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OH'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OK'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OR'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'PA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'RI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'SC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'SD'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'TN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'TX'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'UT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'VA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'VT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WV'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 	 	* Satisfy CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39208633093525179857 [Sum: 0.64497041420118343196]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64497041420118343196 [Best: 0.64497041420118343196]
 			 			* 'aa' = 'AK'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'AL'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'AR'. Value: 0.65656565656565656566 [Sum: 1.91176470588235294118]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'AZ'. Value: 0.65028571428571428572 [Sum: 1.85947712418300653595]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'aa' = 'CA'. Value: 0.65978736710444027518 [Sum: 1.93933823529411764706]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'CO'. Value: 0.65738758029978586724 [Sum: 1.91875000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'CT'. Value: 0.65541995692749461594 [Sum: 1.90208333333333333334]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'DE'. Value: 0.65921582997434957861 [Sum: 1.93440860215053763442]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'DC'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'FL'. Value: 0.65777321703437660339 [Sum: 1.92203898050974512744]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'GA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'HI'. Value: 0.65806137359961032636 [Sum: 1.92450142450142450144]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'IA'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'ID'. Value: 0.65861922914019483270 [Sum: 1.92928039702233250622]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'IL'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'IN'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'KS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'KY'. Value: 0.65014577259475218659 [Sum: 1.85833333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'LA'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MA'. Value: 0.65813528336380255942 [Sum: 1.92513368983957219252]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MD'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'ME'. Value: 0.65767634854771784233 [Sum: 1.92121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'MI'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'MN'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'MO'. Value: 0.65571205007824726135 [Sum: 1.90454545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'MS'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'MT'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'NC'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'ND'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'NE'. Value: 0.65742251223491027733 [Sum: 1.91904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'NH'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'NJ'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'aa' = 'NM'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'aa' = 'NV'. Value: 0.65222929936305732485 [Sum: 1.87545787545787545789]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'NY'. Value: 0.64941569282136894825 [Sum: 1.85238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'OH'. Value: 0.65670692943420216148 [Sum: 1.91296296296296296297]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'OK'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'OR'. Value: 0.65412004069175991862 [Sum: 1.89117647058823529412]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'PA'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'RI'. Value: 0.65549493374902572097 [Sum: 1.90271493212669683259]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'SC'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'SD'. Value: 0.65579458709229701597 [Sum: 1.90524193548387096775]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'TN'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'TX'. Value: 0.64818763326226012794 [Sum: 1.84242424242424242425]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'aa' = 'UT'. Value: 0.65058236272878535774 [Sum: 1.86190476190476190477]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'VA'. Value: 0.65441751368256450352 [Sum: 1.89366515837104072399]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'VT'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'WA'. Value: 0.65365025466893039050 [Sum: 1.88725490196078431373]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'WI'. Value: 0.65256124721603563475 [Sum: 1.87820512820512820514]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'WV'. Value: 0.64785553047404063206 [Sum: 1.83974358974358974360]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'WY'. Value: 0.64497041420118343196 [Sum: 1.81666666666666666667]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]*/ 

-- Negating "CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "HistoryPatients"
-- * Success: false
-- * Time: 32631ms 
-- INSERT INTO HistoryPatients(ID, changeDate, changeMID, MID, lastName, firstName, email, address1, address2, city, state, zip1, zip2, phone1, phone2, phone3, eName, ePhone1, ePhone2, ePhone3, iCName, iCAddress1, iCAddress2, iCCity, ICState, iCZip1, iCZip2, iCPhone1, iCPhone2, iCPhone3, iCID, DateOfBirth, DateOfDeath, CauseOfDeath, MotherMID, FatherMID, BloodType, Ethnicity, Gender, TopicalNotes, CreditCardType, CreditCardNumber, MessageFilter, DirectionsToHome, Religion, Language, SpiritualPractices, AlternateName) VALUES(0, '1000-01-01', 0, 0, '', '', '', '', '', '', 'aa', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '1000-01-01', '1000-01-01', '', 0, 0, '', '', '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.28165374677002583980 [Sum: 0.39208633093525179857]
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 	 	* Satisfy NOT NULL(changeDate). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(changeMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0.39208633093525179857 [Sum: 0.64497041420118343196]
 		 		* state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: true allowNull: true. Value: 0.64497041420118343196 [Best: 0.64497041420118343196]
 			 			* 'aa' = 'AK'. Value: 0.65857740585774058578 [Sum: 1.92892156862745098040]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'AL'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'AR'. Value: 0.65656565656565656566 [Sum: 1.91176470588235294118]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'AZ'. Value: 0.65028571428571428572 [Sum: 1.85947712418300653595]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 90. Value: 0.88888888888888888889 [Distance: 8]
 			 			* 'aa' = 'CA'. Value: 0.65978736710444027518 [Sum: 1.93933823529411764706]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'CO'. Value: 0.65738758029978586724 [Sum: 1.91875000000000000000]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'CT'. Value: 0.65541995692749461594 [Sum: 1.90208333333333333334]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'DE'. Value: 0.65921582997434957861 [Sum: 1.93440860215053763442]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'DC'. Value: 0.65945760384483350498 [Sum: 1.93649193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'FL'. Value: 0.65777321703437660339 [Sum: 1.92203898050974512744]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'GA'. Value: 0.65926986399427344310 [Sum: 1.93487394957983193278]
 				 				* 97 = 71. Value: 0.96428571428571428572 [Distance: 27]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'HI'. Value: 0.65806137359961032636 [Sum: 1.92450142450142450144]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'IA'. Value: 0.65895061728395061729 [Sum: 1.93212669683257918553]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'ID'. Value: 0.65861922914019483270 [Sum: 1.92928039702233250622]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'IL'. Value: 0.65730659025787965617 [Sum: 1.91806020066889632108]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'aa' = 'IN'. Value: 0.65681961030798240101 [Sum: 1.91391941391941391943]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'KS'. Value: 0.65467625899280575540 [Sum: 1.89583333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'KY'. Value: 0.65014577259475218659 [Sum: 1.85833333333333333334]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'LA'. Value: 0.65836609873307121014 [Sum: 1.92710997442455242967]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MA'. Value: 0.65813528336380255942 [Sum: 1.92513368983957219252]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'MD'. Value: 0.65780230807827395886 [Sum: 1.92228739002932551321]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'ME'. Value: 0.65767634854771784233 [Sum: 1.92121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'MI'. Value: 0.65707434052757793766 [Sum: 1.91608391608391608393]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'MN'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'MO'. Value: 0.65571205007824726135 [Sum: 1.90454545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 			 			* 'aa' = 'MS'. Value: 0.65422396856581532417 [Sum: 1.89204545454545454546]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 			 			* 'aa' = 'MT'. Value: 0.65372507869884575027 [Sum: 1.88787878787878787880]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'NC'. Value: 0.65766683647478349466 [Sum: 1.92113095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'ND'. Value: 0.65754865860073645450 [Sum: 1.92012288786482334871]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'NE'. Value: 0.65742251223491027733 [Sum: 1.91904761904761904763]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 69. Value: 0.96666666666666666667 [Distance: 29]
 			 			* 'aa' = 'NH'. Value: 0.65698729582577132487 [Sum: 1.91534391534391534393]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'NJ'. Value: 0.65663832570307390452 [Sum: 1.91238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 74. Value: 0.96000000000000000000 [Distance: 24]
 			 			* 'aa' = 'NM'. Value: 0.65599404318689501118 [Sum: 1.90692640692640692642]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 			 			* 'aa' = 'NV'. Value: 0.65222929936305732485 [Sum: 1.87545787545787545789]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'NY'. Value: 0.64941569282136894825 [Sum: 1.85238095238095238096]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 			 			* 'aa' = 'OH'. Value: 0.65670692943420216148 [Sum: 1.91296296296296296297]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 72. Value: 0.96296296296296296297 [Distance: 26]
 			 			* 'aa' = 'OK'. Value: 0.65616045845272206304 [Sum: 1.90833333333333333334]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 75. Value: 0.95833333333333333334 [Distance: 23]
 			 			* 'aa' = 'OR'. Value: 0.65412004069175991862 [Sum: 1.89117647058823529412]
 				 				* 97 = 79. Value: 0.95000000000000000000 [Distance: 19]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 			 			* 'aa' = 'PA'. Value: 0.65729442970822281168 [Sum: 1.91795665634674922601]
 				 				* 97 = 80. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'RI'. Value: 0.65549493374902572097 [Sum: 1.90271493212669683259]
 				 				* 97 = 82. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'SC'. Value: 0.65591397849462365592 [Sum: 1.90625000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 67. Value: 0.96875000000000000000 [Distance: 31]
 			 			* 'aa' = 'SD'. Value: 0.65579458709229701597 [Sum: 1.90524193548387096775]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'aa' = 'TN'. Value: 0.65346534653465346535 [Sum: 1.88571428571428571430]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 78. Value: 0.95238095238095238096 [Distance: 20]
 			 			* 'aa' = 'TX'. Value: 0.64818763326226012794 [Sum: 1.84242424242424242425]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 88. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'aa' = 'UT'. Value: 0.65058236272878535774 [Sum: 1.86190476190476190477]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'VA'. Value: 0.65441751368256450352 [Sum: 1.89366515837104072399]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'VT'. Value: 0.64991023339317773789 [Sum: 1.85641025641025641027]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 84. Value: 0.93333333333333333334 [Distance: 14]
 			 			* 'aa' = 'WA'. Value: 0.65365025466893039050 [Sum: 1.88725490196078431373]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 65. Value: 0.97058823529411764706 [Distance: 33]
 			 			* 'aa' = 'WI'. Value: 0.65256124721603563475 [Sum: 1.87820512820512820514]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 73. Value: 0.96153846153846153847 [Distance: 25]
 			 			* 'aa' = 'WV'. Value: 0.64785553047404063206 [Sum: 1.83974358974358974360]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 86. Value: 0.92307692307692307693 [Distance: 12]
 			 			* 'aa' = 'WY'. Value: 0.64497041420118343196 [Sum: 1.81666666666666666667]
 				 				* 97 = 87. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 97 = 89. Value: 0.90000000000000000000 [Distance: 9]
 	 	* Violate CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]. Value: 0E-20 [Sum: 0E-20]
 		 		* ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY') goalIsToSatisfy: false allowNull: false. Value: 0E-20 [Sum: 0]
 			 			* '' != 'AK'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AR'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'AZ'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CO'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'CT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'DE'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'DC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'FL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'GA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'HI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ID'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IL'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'IN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'KS'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'KY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'LA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MD'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ME'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MO'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MS'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'MT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'ND'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NE'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NH'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NJ'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NM'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NV'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'NY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OH'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OK'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'OR'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'PA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'RI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'SC'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'SD'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'TN'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'TX'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'UT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'VA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'VT'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WA'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WI'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WV'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0
 			 			* '' != 'WY'. Value: 0 [Best: 0]
 				 				* Compound values are of different sizes. Value: 0*/ 

-- Negating "PRIMARY KEY[ipaddress]" on table "LoginFailures"
-- * Success: false
-- * Time: 2006ms 
-- INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ipaddress]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[ipaddress]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ipaddress]. Value: 1
 	 	* Satisfy NOT NULL(ipaddress). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(failureCount). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(lastFailure). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(ipaddress)" on table "LoginFailures"
-- * Success: true
-- * Time: 0ms 
INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES(NULL, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(failureCount)" on table "LoginFailures"
-- * Success: true
-- * Time: 0ms 
INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('', NULL, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(lastFailure)" on table "LoginFailures"
-- * Success: true
-- * Time: 1ms 
INSERT INTO LoginFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ipaddress]" on table "ResetPasswordFailures"
-- * Success: false
-- * Time: 1984ms 
-- INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ipaddress]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[ipaddress]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ipaddress]. Value: 1
 	 	* Satisfy NOT NULL(ipaddress). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(failureCount). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(lastFailure). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(ipaddress)" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES(NULL, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(failureCount)" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('', NULL, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(lastFailure)" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ResetPasswordFailures(ipaddress, failureCount, lastFailure) VALUES('', 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Code]" on table "icdcodes"
-- * Success: false
-- * Time: 2213ms 
-- INSERT INTO icdcodes(Code, Description, Chronic) VALUES(0, '', 'no');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[Code]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[Code]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[Code]. Value: 1
 	 	* Satisfy NOT NULL(Code). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Chronic). Value: 0E-20 [Sum: 0]
 		 		* 'no', allowNull: false. Value: 0
 	 	* Satisfy CHECK[Chronic IN ('no', 'yes')]. Value: 0E-20 [Sum: 0E-20]
 		 		* Chronic IN ('no', 'yes') goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Best: 0E-20]
 			 			* 'no' = 'no'. Value: 0E-20 [Sum: 0E-20]
 				 				* 110 = 110. Value: 0E-20 [Distance: 0]
 				 				* 111 = 111. Value: 0E-20 [Distance: 0]
 			 			* 'no' = 'yes'. Value: 0.73956594323873121870 [Sum: 2.83974358974358974360]
 				 				* 110 = 121. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 111 = 101. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1*/ 

-- Negating "NOT NULL(Code)" on table "icdcodes"
-- * Success: true
-- * Time: 31ms 
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(NULL, '', 'yes');
-- * Number of objective function evaluations: 1137
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "icdcodes"
-- * Success: true
-- * Time: 53ms 
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(0, NULL, 'yes');
-- * Number of objective function evaluations: 2088
-- * Number of restarts: 0

-- Negating "NOT NULL(Chronic)" on table "icdcodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(0, '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[Chronic IN ('no', 'yes')]" on table "icdcodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO icdcodes(Code, Description, Chronic) VALUES(0, '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Code]" on table "CPTCodes"
-- * Success: false
-- * Time: 524ms 
-- INSERT INTO CPTCodes(Code, Description, Attribute) VALUES('', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[Code]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[Code]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[Code]. Value: 1
 	 	* Satisfy NOT NULL(Code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(Code)" on table "CPTCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO CPTCodes(Code, Description, Attribute) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "CPTCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO CPTCodes(Code, Description, Attribute) VALUES('', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Code]" on table "DrugReactionOverrideCodes"
-- * Success: false
-- * Time: 540ms 
-- INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES('', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[Code]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[Code]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[Code]. Value: 1
 	 	* Satisfy NOT NULL(Code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(Code)" on table "DrugReactionOverrideCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "DrugReactionOverrideCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES('', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Code]" on table "NDCodes"
-- * Success: false
-- * Time: 537ms 
-- INSERT INTO NDCodes(Code, Description) VALUES('', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[Code]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[Code]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[Code]. Value: 1
 	 	* Satisfy NOT NULL(Code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(Code)" on table "NDCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO NDCodes(Code, Description) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "NDCodes"
-- * Success: true
-- * Time: 0ms 
INSERT INTO NDCodes(Code, Description) VALUES('', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[FirstDrug, SecondDrug]" on table "DrugInteractions"
-- * Success: false
-- * Time: 931ms 
-- INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[FirstDrug, SecondDrug]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[FirstDrug, SecondDrug]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[FirstDrug, SecondDrug]. Value: 1
 	 	* Satisfy NOT NULL(FirstDrug). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SecondDrug). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(FirstDrug)" on table "DrugInteractions"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SecondDrug)" on table "DrugInteractions"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "DrugInteractions"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DrugInteractions(FirstDrug, SecondDrug, Description) VALUES('', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[transactionID]" on table "TransactionLog"
-- * Success: false
-- * Time: 2474ms 
-- INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[transactionID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[transactionID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[transactionID]. Value: 1
 	 	* Satisfy NOT NULL(transactionID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(loggedInMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(secondaryMID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(transactionCode). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(timeLogged). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(transactionID)" on table "TransactionLog"
-- * Success: true
-- * Time: 0ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(NULL, 0, 0, 0, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(loggedInMID)" on table "TransactionLog"
-- * Success: true
-- * Time: 0ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(0, NULL, 0, 0, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(secondaryMID)" on table "TransactionLog"
-- * Success: true
-- * Time: 0ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(0, 0, NULL, 0, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(transactionCode)" on table "TransactionLog"
-- * Success: true
-- * Time: 0ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(0, 0, 0, NULL, '1970-01-01 00:00:00', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(timeLogged)" on table "TransactionLog"
-- * Success: true
-- * Time: 0ms 
INSERT INTO TransactionLog(transactionID, loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) VALUES(0, 0, 0, 0, NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[HCP, UAP]" on table "HCPRelations"
-- * Success: false
-- * Time: 629ms 
-- INSERT INTO HCPRelations(HCP, UAP) VALUES(0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[HCP, UAP]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[HCP, UAP]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[HCP, UAP]. Value: 1
 	 	* Satisfy NOT NULL(HCP). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(UAP). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

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
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "PersonalRelations"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(NULL, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(RelativeID)" on table "PersonalRelations"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(0, NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(RelativeType)" on table "PersonalRelations"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalRelations(PatientID, RelativeID, RelativeType) VALUES(0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[representerMID, representeeMID]" on table "Representatives"
-- * Success: false
-- * Time: 303ms 
-- INSERT INTO Representatives(representerMID, representeeMID) VALUES(0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[representerMID, representeeMID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[representerMID, representeeMID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[representerMID, representeeMID]. Value: 1*/ 

-- Negating "PRIMARY KEY[hosID, HCPID]" on table "HCPAssignedHos"
-- * Success: false
-- * Time: 661ms 
-- INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES('', 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[hosID, HCPID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[hosID, HCPID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[hosID, HCPID]. Value: 1
 	 	* Satisfy NOT NULL(hosID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(HCPID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(hosID)" on table "HCPAssignedHos"
-- * Success: true
-- * Time: 0ms 
INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES(NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(HCPID)" on table "HCPAssignedHos"
-- * Success: true
-- * Time: 0ms 
INSERT INTO HCPAssignedHos(hosID, HCPID) VALUES('', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[PatientID, HCPID]" on table "DeclaredHCP"
-- * Success: false
-- * Time: 634ms 
-- INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[PatientID, HCPID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[PatientID, HCPID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[PatientID, HCPID]. Value: 1
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(HCPID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(PatientID)" on table "DeclaredHCP"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(HCPID)" on table "DeclaredHCP"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES(0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "OfficeVisits"
-- * Success: false
-- * Time: 193ms 
-- INSERT INTO OfficeVisits(ID, visitDate, HCPID, notes, PatientID, HospitalID) VALUES(0, '1000-01-01', 0, '', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[ID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ID]. Value: 1*/ 

-- Negating "NOT NULL(PatientID)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Smoker)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SmokingStatus)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(AsOfDate)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalHealthInformation(PatientID, Height, Weight, Smoker, SmokingStatus, BloodPressureN, BloodPressureD, CholesterolHDL, CholesterolLDL, CholesterolTri, HCPID, AsOfDate) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "PersonalAllergies"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalAllergies(PatientID, Allergy) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Allergy)" on table "PersonalAllergies"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PersonalAllergies(PatientID, Allergy) VALUES(0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "Allergies"
-- * Success: false
-- * Time: 2083ms 
-- INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(0, 0, '', '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[ID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ID]. Value: 1
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(Description). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(FirstFound). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(PatientID)" on table "Allergies"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(0, NULL, '', '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "Allergies"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(0, 0, NULL, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(FirstFound)" on table "Allergies"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Allergies(ID, PatientID, Description, FirstFound) VALUES(0, 0, '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "OVProcedure"
-- * Success: false
-- * Time: 886ms 
-- INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(0, 0, '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[ID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ID]. Value: 1
 	 	* Satisfy NOT NULL(VisitID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(CPTCode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(HCPID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(VisitID)" on table "OVProcedure"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(0, NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CPTCode)" on table "OVProcedure"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(0, 0, NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(HCPID)" on table "OVProcedure"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVProcedure(ID, VisitID, CPTCode, HCPID) VALUES(0, 0, '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "OVMedication"
-- * Success: false
-- * Time: 632ms 
-- INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(0, 0, '', '1000-01-01', '1000-01-01', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[ID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ID]. Value: 1
 	 	* Satisfy NOT NULL(VisitID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(NDCode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(VisitID)" on table "OVMedication"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(0, NULL, '', '1000-01-01', '1000-01-01', 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(NDCode)" on table "OVMedication"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(0, 0, NULL, '1000-01-01', '1000-01-01', 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "OVReactionOverride"
-- * Success: false
-- * Time: 1593ms 
-- INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(0, 0, '', '1000-01-01', '1000-01-01', 0, '');
-- INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(0, 0, '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[ID]. Value: 0
 	 	* Satisfy NOT NULL(VisitID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(NDCode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate PRIMARY KEY[ID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ID]. Value: 1
 	 	* Satisfy FOREIGN KEY[OVMedicationID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(OVMedicationID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "FOREIGN KEY[OVMedicationID]" on table "OVReactionOverride"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(0, 0, '', '1000-01-01', '1000-01-01', 0, '');
INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(0, 36, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(OVMedicationID)" on table "OVReactionOverride"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVMedication(ID, VisitID, NDCode, StartDate, EndDate, Dosage, Instructions) VALUES(43, 0, '', '1000-01-01', '1000-01-01', 0, '');
INSERT INTO OVReactionOverride(ID, OVMedicationID, OverrideCode, OverrideComment) VALUES(0, NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "OVDiagnosis"
-- * Success: false
-- * Time: 594ms 
-- INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[ID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ID]. Value: 1
 	 	* Satisfy NOT NULL(VisitID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(ICDCode). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(VisitID)" on table "OVDiagnosis"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(ICDCode)" on table "OVDiagnosis"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OVDiagnosis(ID, VisitID, ICDCode) VALUES(0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[Name]" on table "GlobalVariables"
-- * Success: false
-- * Time: 188ms 
-- INSERT INTO GlobalVariables(Name, Value) VALUES('', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[Name]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[Name]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[Name]. Value: 1*/ 

-- Negating "PRIMARY KEY[ID]" on table "FakeEmail"
-- * Success: false
-- * Time: 1696ms 
-- INSERT INTO FakeEmail(ID, ToAddr, FromAddr, Subject, Body, AddedDate) VALUES(0, '', '', '', '', '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[ID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ID]. Value: 1
 	 	* Satisfy NOT NULL(AddedDate). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(AddedDate)" on table "FakeEmail"
-- * Success: true
-- * Time: 0ms 
INSERT INTO FakeEmail(ID, ToAddr, FromAddr, Subject, Body, AddedDate) VALUES(0, '', '', '', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "ReportRequests"
-- * Success: false
-- * Time: 193ms 
-- INSERT INTO ReportRequests(ID, RequesterMID, PatientMID, ApproverMID, RequestedDate, ApprovedDate, ViewedDate, Status, Comment) VALUES(0, 0, 0, 0, '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[ID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[ID]. Value: 1*/ 

-- Negating "PRIMARY KEY[VisitID]" on table "OVSurvey"
-- * Success: false
-- * Time: 1533ms 
-- INSERT INTO OVSurvey(VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction) VALUES(0, '1970-01-01 00:00:00', 0, 0, 0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[VisitID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[VisitID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[VisitID]. Value: 1
 	 	* Satisfy NOT NULL(SurveyDate). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(SurveyDate)" on table "OVSurvey"
-- * Success: true
-- * Time: 1ms 
INSERT INTO OVSurvey(VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction) VALUES(0, NULL, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[LaboratoryProcedureID]" on table "LabProcedure"
-- * Success: false
-- * Time: 3022ms 
-- INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(0, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, FALSE, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[LaboratoryProcedureID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[LaboratoryProcedureID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[LaboratoryProcedureID]. Value: 1
 	 	* Satisfy NOT NULL(ViewedByPatient). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(UpdatedDate). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(ViewedByPatient)" on table "LabProcedure"
-- * Success: true
-- * Time: 0ms 
INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(0, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, NULL, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(UpdatedDate)" on table "LabProcedure"
-- * Success: true
-- * Time: 0ms 
INSERT INTO LabProcedure(LaboratoryProcedureID, PatientMID, LaboratoryProcedureCode, Rights, Status, Commentary, Results, NumericalResults, NumericalResultsUnit, UpperBound, LowerBound, OfficeVisitID, LabTechID, PriorityCode, ViewedByPatient, UpdatedDate) VALUES(0, 0, '', '', '', '', '', '', '', '', '', 0, 0, 0, FALSE, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(from_id)" on table "message"
-- * Success: true
-- * Time: 1ms 
INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, NULL, 0, '1970-01-01 00:00:00', '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(to_id)" on table "message"
-- * Success: true
-- * Time: 0ms 
INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, 0, NULL, '1970-01-01 00:00:00', '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(sent_date)" on table "message"
-- * Success: true
-- * Time: 0ms 
INSERT INTO message(message_id, parent_msg_id, from_id, to_id, sent_date, message, subject, been_read) VALUES(0, 0, 0, 0, NULL, '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[appt_id]" on table "Appointment"
-- * Success: false
-- * Time: 2391ms 
-- INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(0, 0, 0, '1970-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[appt_id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[appt_id]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[appt_id]. Value: 1
 	 	* Satisfy NOT NULL(doctor_id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(patient_id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(sched_date). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(appt_type). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(doctor_id)" on table "Appointment"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(0, NULL, 0, '1970-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(patient_id)" on table "Appointment"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(0, 0, NULL, '1970-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(sched_date)" on table "Appointment"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(0, 0, 0, NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(appt_type)" on table "Appointment"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Appointment(appt_id, doctor_id, patient_id, sched_date, appt_type, comment) VALUES(0, 0, 0, '1970-01-01 00:00:00', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[apptType_id]" on table "AppointmentType"
-- * Success: false
-- * Time: 634ms 
-- INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(0, '', 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[apptType_id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[apptType_id]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[apptType_id]. Value: 1
 	 	* Satisfy NOT NULL(appt_type). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(duration). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(appt_type)" on table "AppointmentType"
-- * Success: true
-- * Time: 0ms 
INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(duration)" on table "AppointmentType"
-- * Success: true
-- * Time: 0ms 
INSERT INTO AppointmentType(apptType_id, appt_type, duration) VALUES(0, '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "referrals"
-- * Success: false
-- * Time: 3748ms 
-- INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, 0, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[id]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[id]. Value: 1
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SenderID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(ReceiverID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(OVID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(viewed_by_patient). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(viewed_by_HCP). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(TimeStamp). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(PatientID)" on table "referrals"
-- * Success: true
-- * Time: 0ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, NULL, 0, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SenderID)" on table "referrals"
-- * Success: true
-- * Time: 0ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, NULL, 0, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(ReceiverID)" on table "referrals"
-- * Success: true
-- * Time: 0ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, 0, NULL, '', 0, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(OVID)" on table "referrals"
-- * Success: true
-- * Time: 0ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, 0, 0, '', NULL, FALSE, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(viewed_by_patient)" on table "referrals"
-- * Success: true
-- * Time: 1ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, 0, 0, '', 0, NULL, FALSE, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(viewed_by_HCP)" on table "referrals"
-- * Success: true
-- * Time: 0ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, 0, 0, '', 0, FALSE, NULL, '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(TimeStamp)" on table "referrals"
-- * Success: true
-- * Time: 0ms 
INSERT INTO referrals(id, PatientID, SenderID, ReceiverID, ReferralDetails, OVID, viewed_by_patient, viewed_by_HCP, TimeStamp, PriorityCode) VALUES(0, 0, 0, 0, '', 0, FALSE, FALSE, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "RemoteMonitoringData"
-- * Success: false
-- * Time: 2172ms 
-- INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(0, 0, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00', '', 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[id]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[id]. Value: 1
 	 	* Satisfy NOT NULL(PatientID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(timeLogged). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(PatientID)" on table "RemoteMonitoringData"
-- * Success: true
-- * Time: 0ms 
INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(0, NULL, 0, 0, 0, 0, 0, 0, '1970-01-01 00:00:00', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(timeLogged)" on table "RemoteMonitoringData"
-- * Success: true
-- * Time: 0ms 
INSERT INTO RemoteMonitoringData(id, PatientID, systolicBloodPressure, diastolicBloodPressure, glucoseLevel, height, weight, pedometerReading, timeLogged, ReporterRole, ReporterID) VALUES(0, 0, 0, 0, 0, 0, 0, 0, NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[PatientMID, HCPMID]" on table "RemoteMonitoringLists"
-- * Success: false
-- * Time: 345ms 
-- INSERT INTO RemoteMonitoringLists(PatientMID, HCPMID, SystolicBloodPressure, DiastolicBloodPressure, GlucoseLevel, Height, Weight, PedometerReading) VALUES(0, 0, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[PatientMID, HCPMID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[PatientMID, HCPMID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[PatientMID, HCPMID]. Value: 1*/ 

-- Negating "PRIMARY KEY[id]" on table "AdverseEvents"
-- * Success: false
-- * Time: 190ms 
-- INSERT INTO AdverseEvents(id, Status, PatientMID, PresImmu, Code, Comment, Prescriber, TimeLogged) VALUES(0, '', 0, '', '', '', 0, '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[id]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[id]. Value: 1*/ 

-- Negating "PRIMARY KEY[MID]" on table "ProfilePhotos"
-- * Success: false
-- * Time: 1577ms 
-- INSERT INTO ProfilePhotos(MID, Photo, UpdatedDate) VALUES(0, '', '1970-01-01 00:00:00');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[MID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[MID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[MID]. Value: 1
 	 	* Satisfy NOT NULL(UpdatedDate). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(UpdatedDate)" on table "ProfilePhotos"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ProfilePhotos(MID, Photo, UpdatedDate) VALUES(0, '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "PatientSpecificInstructions"
-- * Success: false
-- * Time: 1620ms 
-- INSERT INTO PatientSpecificInstructions(id, VisitID, Modified, Name, URL, Comment) VALUES(0, 0, '1970-01-01 00:00:00', '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[id]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[id]. Value: 1
 	 	* Satisfy NOT NULL(Modified). Value: 0E-20 [Sum: 0]
 		 		* '1970-01-01 00:00:00', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(Modified)" on table "PatientSpecificInstructions"
-- * Success: true
-- * Time: 0ms 
INSERT INTO PatientSpecificInstructions(id, VisitID, Modified, Name, URL, Comment) VALUES(0, 0, NULL, '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[messageID, referralID]" on table "ReferralMessage"
-- * Success: false
-- * Time: 639ms 
-- INSERT INTO ReferralMessage(messageID, referralID) VALUES(0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[messageID, referralID]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[messageID, referralID]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[messageID, referralID]. Value: 1
 	 	* Satisfy NOT NULL(messageID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(referralID). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(messageID)" on table "ReferralMessage"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ReferralMessage(messageID, referralID) VALUES(NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(referralID)" on table "ReferralMessage"
-- * Success: true
-- * Time: 0ms 
INSERT INTO ReferralMessage(messageID, referralID) VALUES(0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

