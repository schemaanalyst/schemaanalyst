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
	MID	INT	CONSTRAINT null PRIMARY KEY,
	Password	VARCHAR(20),
	Role	VARCHAR(20)	CONSTRAINT null NOT NULL,
	sQuestion	VARCHAR(100),
	sAnswer	VARCHAR(30),
	CONSTRAINT null CHECK (Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt'))
);
CREATE TABLE Hospitals (
	HospitalID	VARCHAR(10)	CONSTRAINT null PRIMARY KEY,
	HospitalName	VARCHAR(30)	CONSTRAINT null NOT NULL
);
CREATE TABLE Personnel (
	MID	INT	CONSTRAINT null PRIMARY KEY,
	AMID	INT,
	role	VARCHAR(20)	CONSTRAINT null NOT NULL,
	enabled	INT	CONSTRAINT null NOT NULL,
	lastName	VARCHAR(20)	CONSTRAINT null NOT NULL,
	firstName	VARCHAR(20)	CONSTRAINT null NOT NULL,
	address1	VARCHAR(20)	CONSTRAINT null NOT NULL,
	address2	VARCHAR(20)	CONSTRAINT null NOT NULL,
	city	VARCHAR(15)	CONSTRAINT null NOT NULL,
	state	VARCHAR(2)	CONSTRAINT null NOT NULL,
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
	CONSTRAINT null CHECK (role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')),
	CONSTRAINT null CHECK (state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'))
);
CREATE TABLE Patients (
	MID	INT	CONSTRAINT null PRIMARY KEY,
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
	CONSTRAINT null CHECK (state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')),
	CONSTRAINT null CHECK (ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'))
);
CREATE TABLE HistoryPatients (
	ID	INT	CONSTRAINT null PRIMARY KEY,
	changeDate	DATE	CONSTRAINT null NOT NULL,
	changeMID	INT	CONSTRAINT null NOT NULL,
	MID	INT	CONSTRAINT null NOT NULL,
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
	CONSTRAINT null CHECK (state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')),
	CONSTRAINT null CHECK (ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY'))
);
CREATE TABLE LoginFailures (
	ipaddress	VARCHAR(100)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	failureCount	INT	CONSTRAINT null NOT NULL,
	lastFailure	TIMESTAMP	CONSTRAINT null NOT NULL
);
CREATE TABLE ResetPasswordFailures (
	ipaddress	VARCHAR(128)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	failureCount	INT	CONSTRAINT null NOT NULL,
	lastFailure	TIMESTAMP	CONSTRAINT null NOT NULL
);
CREATE TABLE icdcodes (
	Code	NUMERIC(5, 2)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	Description	VARCHAR(50)	CONSTRAINT null NOT NULL,
	Chronic	VARCHAR(3)	CONSTRAINT null NOT NULL,
	CONSTRAINT null CHECK (Chronic IN ('no', 'yes'))
);
CREATE TABLE CPTCodes (
	Code	VARCHAR(5)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	Description	VARCHAR(30)	CONSTRAINT null NOT NULL,
	Attribute	VARCHAR(30)
);
CREATE TABLE DrugReactionOverrideCodes (
	Code	VARCHAR(5)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	Description	VARCHAR(80)	CONSTRAINT null NOT NULL
);
CREATE TABLE NDCodes (
	Code	VARCHAR(10)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	Description	VARCHAR(100)	CONSTRAINT null NOT NULL
);
CREATE TABLE DrugInteractions (
	FirstDrug	VARCHAR(9)	CONSTRAINT null NOT NULL,
	SecondDrug	VARCHAR(9)	CONSTRAINT null NOT NULL,
	Description	VARCHAR(100)	CONSTRAINT null NOT NULL,
	CONSTRAINT null PRIMARY KEY (FirstDrug, SecondDrug)
);
CREATE TABLE TransactionLog (
	transactionID	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	loggedInMID	INT	CONSTRAINT null NOT NULL,
	secondaryMID	INT	CONSTRAINT null NOT NULL,
	transactionCode	INT	CONSTRAINT null NOT NULL,
	timeLogged	TIMESTAMP	CONSTRAINT null NOT NULL,
	addedInfo	VARCHAR(255)
);
CREATE TABLE HCPRelations (
	HCP	INT	CONSTRAINT null NOT NULL,
	UAP	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT null PRIMARY KEY (HCP, UAP)
);
CREATE TABLE PersonalRelations (
	PatientID	INT	CONSTRAINT null NOT NULL,
	RelativeID	INT	CONSTRAINT null NOT NULL,
	RelativeType	VARCHAR(35)	CONSTRAINT null NOT NULL
);
CREATE TABLE Representatives (
	representerMID	INT,
	representeeMID	INT,
	CONSTRAINT null PRIMARY KEY (representerMID, representeeMID)
);
CREATE TABLE HCPAssignedHos (
	hosID	VARCHAR(10)	CONSTRAINT null NOT NULL,
	HCPID	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT null PRIMARY KEY (hosID, HCPID)
);
CREATE TABLE DeclaredHCP (
	PatientID	INT	CONSTRAINT null NOT NULL,
	HCPID	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT null PRIMARY KEY (PatientID, HCPID)
);
CREATE TABLE OfficeVisits (
	ID	INT	CONSTRAINT null PRIMARY KEY,
	visitDate	DATE,
	HCPID	INT,
	notes	VARCHAR(50),
	PatientID	INT,
	HospitalID	VARCHAR(10)
);
CREATE TABLE PersonalHealthInformation (
	PatientID	INT	CONSTRAINT null NOT NULL,
	Height	INT,
	Weight	INT,
	Smoker	INT	CONSTRAINT null NOT NULL,
	SmokingStatus	INT	CONSTRAINT null NOT NULL,
	BloodPressureN	INT,
	BloodPressureD	INT,
	CholesterolHDL	INT,
	CholesterolLDL	INT,
	CholesterolTri	INT,
	HCPID	INT,
	AsOfDate	TIMESTAMP	CONSTRAINT null NOT NULL
);
CREATE TABLE PersonalAllergies (
	PatientID	INT	CONSTRAINT null NOT NULL,
	Allergy	VARCHAR(50)	CONSTRAINT null NOT NULL
);
CREATE TABLE Allergies (
	ID	INT	CONSTRAINT null PRIMARY KEY,
	PatientID	INT	CONSTRAINT null NOT NULL,
	Description	VARCHAR(50)	CONSTRAINT null NOT NULL,
	FirstFound	TIMESTAMP	CONSTRAINT null NOT NULL
);
CREATE TABLE OVProcedure (
	ID	INT	CONSTRAINT null PRIMARY KEY,
	VisitID	INT	CONSTRAINT null NOT NULL,
	CPTCode	VARCHAR(5)	CONSTRAINT null NOT NULL,
	HCPID	VARCHAR(10)	CONSTRAINT null NOT NULL
);
CREATE TABLE OVMedication (
	ID	INT	CONSTRAINT null PRIMARY KEY,
	VisitID	INT	CONSTRAINT null NOT NULL,
	NDCode	VARCHAR(9)	CONSTRAINT null NOT NULL,
	StartDate	DATE,
	EndDate	DATE,
	Dosage	INT,
	Instructions	VARCHAR(500)
);
CREATE TABLE OVReactionOverride (
	ID	INT	CONSTRAINT null PRIMARY KEY,
	OVMedicationID	INT	CONSTRAINT null  REFERENCES OVMedication (ID)	CONSTRAINT null NOT NULL,
	OverrideCode	VARCHAR(5),
	OverrideComment	VARCHAR(255)
);
CREATE TABLE OVDiagnosis (
	ID	INT	CONSTRAINT null PRIMARY KEY,
	VisitID	INT	CONSTRAINT null NOT NULL,
	ICDCode	DECIMAL(5, 2)	CONSTRAINT null NOT NULL
);
CREATE TABLE GlobalVariables (
	Name	VARCHAR(20)	CONSTRAINT null PRIMARY KEY,
	Value	VARCHAR(20)
);
CREATE TABLE FakeEmail (
	ID	INT	CONSTRAINT null PRIMARY KEY,
	ToAddr	VARCHAR(100),
	FromAddr	VARCHAR(100),
	Subject	VARCHAR(500),
	Body	VARCHAR(2000),
	AddedDate	TIMESTAMP	CONSTRAINT null NOT NULL
);
CREATE TABLE ReportRequests (
	ID	INT	CONSTRAINT null PRIMARY KEY,
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
	VisitID	INT	CONSTRAINT null PRIMARY KEY,
	SurveyDate	TIMESTAMP	CONSTRAINT null NOT NULL,
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
	LaboratoryProcedureID	INT	CONSTRAINT null PRIMARY KEY,
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
	ViewedByPatient	BOOLEAN	CONSTRAINT null NOT NULL,
	UpdatedDate	TIMESTAMP	CONSTRAINT null NOT NULL
);
CREATE TABLE message (
	message_id	INT,
	parent_msg_id	INT,
	from_id	INT	CONSTRAINT null NOT NULL,
	to_id	INT	CONSTRAINT null NOT NULL,
	sent_date	TIMESTAMP	CONSTRAINT null NOT NULL,
	message	VARCHAR(50),
	subject	VARCHAR(50),
	been_read	INT
);
CREATE TABLE Appointment (
	appt_id	INT	CONSTRAINT null PRIMARY KEY,
	doctor_id	INT	CONSTRAINT null NOT NULL,
	patient_id	INT	CONSTRAINT null NOT NULL,
	sched_date	TIMESTAMP	CONSTRAINT null NOT NULL,
	appt_type	VARCHAR(30)	CONSTRAINT null NOT NULL,
	comment	VARCHAR(50)
);
CREATE TABLE AppointmentType (
	apptType_id	INT	CONSTRAINT null PRIMARY KEY,
	appt_type	VARCHAR(30)	CONSTRAINT null NOT NULL,
	duration	INT	CONSTRAINT null NOT NULL
);
CREATE TABLE referrals (
	id	INT	CONSTRAINT null PRIMARY KEY,
	PatientID	INT	CONSTRAINT null NOT NULL,
	SenderID	INT	CONSTRAINT null NOT NULL,
	ReceiverID	INT	CONSTRAINT null NOT NULL,
	ReferralDetails	VARCHAR(50),
	OVID	INT	CONSTRAINT null NOT NULL,
	viewed_by_patient	BOOLEAN	CONSTRAINT null NOT NULL,
	viewed_by_HCP	BOOLEAN	CONSTRAINT null NOT NULL,
	TimeStamp	TIMESTAMP	CONSTRAINT null NOT NULL,
	PriorityCode	INT
);
CREATE TABLE RemoteMonitoringData (
	id	INT	CONSTRAINT null PRIMARY KEY,
	PatientID	INT	CONSTRAINT null NOT NULL,
	systolicBloodPressure	INT,
	diastolicBloodPressure	INT,
	glucoseLevel	INT,
	height	INT,
	weight	INT,
	pedometerReading	INT,
	timeLogged	TIMESTAMP	CONSTRAINT null NOT NULL,
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
	CONSTRAINT null PRIMARY KEY (PatientMID, HCPMID)
);
CREATE TABLE AdverseEvents (
	id	INT	CONSTRAINT null PRIMARY KEY,
	Status	VARCHAR(10),
	PatientMID	INT,
	PresImmu	VARCHAR(50),
	Code	VARCHAR(20),
	Comment	VARCHAR(2000),
	Prescriber	INT,
	TimeLogged	TIMESTAMP
);
CREATE TABLE ProfilePhotos (
	MID	INT	CONSTRAINT null PRIMARY KEY,
	Photo	VARCHAR(50),
	UpdatedDate	TIMESTAMP	CONSTRAINT null NOT NULL
);
CREATE TABLE PatientSpecificInstructions (
	id	INT	CONSTRAINT null PRIMARY KEY,
	VisitID	INT,
	Modified	TIMESTAMP	CONSTRAINT null NOT NULL,
	Name	VARCHAR(100),
	URL	VARCHAR(250),
	Comment	VARCHAR(500)
);
CREATE TABLE ReferralMessage (
	messageID	INT	CONSTRAINT null NOT NULL,
	referralID	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT null PRIMARY KEY (messageID, referralID)
);
-- Coverage: 268/268 (100.00000%) 
-- Time to generate: 10103ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 9677ms 
-- * Number of objective function evaluations: 2230
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{MID}" on table "Users"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 33
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{HospitalID}" on table "Hospitals"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{MID}" on table "Personnel"
-- * Success: true
-- * Time: 12ms 
-- * Number of objective function evaluations: 34
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{MID}" on table "Patients"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 44
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "HistoryPatients"
-- * Success: true
-- * Time: 23ms 
-- * Number of objective function evaluations: 57
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ipaddress}" on table "LoginFailures"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ipaddress}" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{Code}" on table "icdcodes"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{Code}" on table "CPTCodes"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{Code}" on table "DrugReactionOverrideCodes"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{Code}" on table "NDCodes"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{FirstDrug, SecondDrug}" on table "DrugInteractions"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{transactionID}" on table "TransactionLog"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{HCP, UAP}" on table "HCPRelations"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{representerMID, representeeMID}" on table "Representatives"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{hosID, HCPID}" on table "HCPAssignedHos"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{PatientID, HCPID}" on table "DeclaredHCP"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "OfficeVisits"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "Allergies"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "OVProcedure"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "OVMedication"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "OVReactionOverride"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "OVDiagnosis"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{Name}" on table "GlobalVariables"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "FakeEmail"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "ReportRequests"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{VisitID}" on table "OVSurvey"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{LaboratoryProcedureID}" on table "LabProcedure"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{appt_id}" on table "Appointment"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{apptType_id}" on table "AppointmentType"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "referrals"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "RemoteMonitoringData"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{PatientMID, HCPMID}" on table "RemoteMonitoringLists"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "AdverseEvents"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{MID}" on table "ProfilePhotos"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "PatientSpecificInstructions"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{messageID, referralID}" on table "ReferralMessage"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "CHECK[Role IN ('patient', 'admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]" on table "Users"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "CHECK[role IN ('admin', 'hcp', 'uap', 'er', 'tester', 'pha', 'lt')]" on table "Personnel"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "CHECK[state IN ('', 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "Personnel"
-- * Success: true
-- * Time: 23ms 
-- * Number of objective function evaluations: 66
-- * Number of restarts: 0

-- Negating "CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "Patients"
-- * Success: true
-- * Time: 14ms 
-- * Number of objective function evaluations: 48
-- * Number of restarts: 0

-- Negating "CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "Patients"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "CHECK[state IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "HistoryPatients"
-- * Success: true
-- * Time: 31ms 
-- * Number of objective function evaluations: 61
-- * Number of restarts: 0

-- Negating "CHECK[ICState IN ('AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DE', 'DC', 'FL', 'GA', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY')]" on table "HistoryPatients"
-- * Success: true
-- * Time: 14ms 
-- * Number of objective function evaluations: 33
-- * Number of restarts: 0

-- Negating "CHECK[Chronic IN ('no', 'yes')]" on table "icdcodes"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{OVMedicationID}" on table "OVReactionOverride"
-- * Success: true
-- * Time: 8ms 
-- * Number of objective function evaluations: 76
-- * Number of restarts: 1

-- Negating "NOT NULL(Role)" on table "Users"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(HospitalName)" on table "Hospitals"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(role)" on table "Personnel"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(enabled)" on table "Personnel"
-- * Success: true
-- * Time: 17ms 
-- * Number of objective function evaluations: 52
-- * Number of restarts: 0

-- Negating "NOT NULL(lastName)" on table "Personnel"
-- * Success: true
-- * Time: 16ms 
-- * Number of objective function evaluations: 55
-- * Number of restarts: 0

-- Negating "NOT NULL(firstName)" on table "Personnel"
-- * Success: true
-- * Time: 20ms 
-- * Number of objective function evaluations: 57
-- * Number of restarts: 0

-- Negating "NOT NULL(address1)" on table "Personnel"
-- * Success: true
-- * Time: 18ms 
-- * Number of objective function evaluations: 59
-- * Number of restarts: 0

-- Negating "NOT NULL(address2)" on table "Personnel"
-- * Success: true
-- * Time: 20ms 
-- * Number of objective function evaluations: 61
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "Personnel"
-- * Success: true
-- * Time: 21ms 
-- * Number of objective function evaluations: 63
-- * Number of restarts: 0

-- Negating "NOT NULL(state)" on table "Personnel"
-- * Success: true
-- * Time: 21ms 
-- * Number of objective function evaluations: 65
-- * Number of restarts: 0

-- Negating "NOT NULL(changeDate)" on table "HistoryPatients"
-- * Success: true
-- * Time: 21ms 
-- * Number of objective function evaluations: 54
-- * Number of restarts: 0

-- Negating "NOT NULL(changeMID)" on table "HistoryPatients"
-- * Success: true
-- * Time: 24ms 
-- * Number of objective function evaluations: 58
-- * Number of restarts: 0

-- Negating "NOT NULL(MID)" on table "HistoryPatients"
-- * Success: true
-- * Time: 24ms 
-- * Number of objective function evaluations: 58
-- * Number of restarts: 0

-- Negating "NOT NULL(ipaddress)" on table "LoginFailures"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(failureCount)" on table "LoginFailures"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(lastFailure)" on table "LoginFailures"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

-- Negating "NOT NULL(ipaddress)" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(failureCount)" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(lastFailure)" on table "ResetPasswordFailures"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

-- Negating "NOT NULL(Code)" on table "icdcodes"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 30
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "icdcodes"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 34
-- * Number of restarts: 0

-- Negating "NOT NULL(Chronic)" on table "icdcodes"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(Code)" on table "CPTCodes"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "CPTCodes"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(Code)" on table "DrugReactionOverrideCodes"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "DrugReactionOverrideCodes"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(Code)" on table "NDCodes"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "NDCodes"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(FirstDrug)" on table "DrugInteractions"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SecondDrug)" on table "DrugInteractions"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "DrugInteractions"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "NOT NULL(transactionID)" on table "TransactionLog"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(loggedInMID)" on table "TransactionLog"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(secondaryMID)" on table "TransactionLog"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(transactionCode)" on table "TransactionLog"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(timeLogged)" on table "TransactionLog"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "NOT NULL(HCP)" on table "HCPRelations"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(UAP)" on table "HCPRelations"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "PersonalRelations"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(RelativeID)" on table "PersonalRelations"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(RelativeType)" on table "PersonalRelations"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(hosID)" on table "HCPAssignedHos"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(HCPID)" on table "HCPAssignedHos"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "DeclaredHCP"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(HCPID)" on table "DeclaredHCP"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Smoker)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(SmokingStatus)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(AsOfDate)" on table "PersonalHealthInformation"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 35
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "PersonalAllergies"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Allergy)" on table "PersonalAllergies"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "Allergies"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(Description)" on table "Allergies"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(FirstFound)" on table "Allergies"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 13
-- * Number of restarts: 0

-- Negating "NOT NULL(VisitID)" on table "OVProcedure"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(CPTCode)" on table "OVProcedure"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(HCPID)" on table "OVProcedure"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 13
-- * Number of restarts: 0

-- Negating "NOT NULL(VisitID)" on table "OVMedication"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 35
-- * Number of restarts: 1

-- Negating "NOT NULL(NDCode)" on table "OVMedication"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 42
-- * Number of restarts: 1

-- Negating "NOT NULL(OVMedicationID)" on table "OVReactionOverride"
-- * Success: true
-- * Time: 21ms 
-- * Number of objective function evaluations: 253
-- * Number of restarts: 2

-- Negating "NOT NULL(VisitID)" on table "OVDiagnosis"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(ICDCode)" on table "OVDiagnosis"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(AddedDate)" on table "FakeEmail"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 16
-- * Number of restarts: 0

-- Negating "NOT NULL(SurveyDate)" on table "OVSurvey"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(ViewedByPatient)" on table "LabProcedure"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 38
-- * Number of restarts: 0

-- Negating "NOT NULL(UpdatedDate)" on table "LabProcedure"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 40
-- * Number of restarts: 0

-- Negating "NOT NULL(from_id)" on table "message"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(to_id)" on table "message"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(sent_date)" on table "message"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(doctor_id)" on table "Appointment"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(patient_id)" on table "Appointment"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(sched_date)" on table "Appointment"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(appt_type)" on table "Appointment"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "NOT NULL(appt_type)" on table "AppointmentType"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(duration)" on table "AppointmentType"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "referrals"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(SenderID)" on table "referrals"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(ReceiverID)" on table "referrals"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(OVID)" on table "referrals"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 19
-- * Number of restarts: 0

-- Negating "NOT NULL(viewed_by_patient)" on table "referrals"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "NOT NULL(viewed_by_HCP)" on table "referrals"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

-- Negating "NOT NULL(TimeStamp)" on table "referrals"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 26
-- * Number of restarts: 0

-- Negating "NOT NULL(PatientID)" on table "RemoteMonitoringData"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(timeLogged)" on table "RemoteMonitoringData"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 29
-- * Number of restarts: 0

-- Negating "NOT NULL(UpdatedDate)" on table "ProfilePhotos"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(Modified)" on table "PatientSpecificInstructions"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(messageID)" on table "ReferralMessage"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(referralID)" on table "ReferralMessage"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

