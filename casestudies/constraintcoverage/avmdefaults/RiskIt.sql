/**********************************
 * Constraint coverage for RiskIt *
 **********************************/
DROP TABLE IF EXISTS ziptable;
DROP TABLE IF EXISTS youth;
DROP TABLE IF EXISTS wage;
DROP TABLE IF EXISTS stateabbv;
DROP TABLE IF EXISTS migration;
DROP TABLE IF EXISTS job;
DROP TABLE IF EXISTS occupation;
DROP TABLE IF EXISTS investment;
DROP TABLE IF EXISTS industry;
DROP TABLE IF EXISTS geo;
DROP TABLE IF EXISTS employmentstat;
DROP TABLE IF EXISTS education;
DROP TABLE IF EXISTS userrecord;
CREATE TABLE userrecord (
	NAME	CHAR(50),
	ZIP	CHAR(5),
	SSN	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	AGE	INT,
	SEX	CHAR(50),
	MARITAL	CHAR(50),
	RACE	CHAR(50),
	TAXSTAT	CHAR(50),
	DETAIL	CHAR(100),
	HOUSEHOLDDETAIL	CHAR(100),
	FATHERORIGIN	CHAR(50),
	MOTHERORIGIN	CHAR(50),
	BIRTHCOUNTRY	CHAR(50),
	CITIZENSHIP	CHAR(50)
);
CREATE TABLE education (
	SSN	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null  REFERENCES userrecord (SSN)	CONSTRAINT null NOT NULL,
	EDUCATION	CHAR(50),
	EDUENROLL	CHAR(50)
);
CREATE TABLE employmentstat (
	SSN	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null  REFERENCES userrecord (SSN)	CONSTRAINT null NOT NULL,
	UNEMPLOYMENTREASON	CHAR(50),
	EMPLOYMENTSTAT	CHAR(50)
);
CREATE TABLE geo (
	REGION	CHAR(50)	CONSTRAINT null NOT NULL,
	RESSTATE	CHAR(50)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL
);
CREATE TABLE industry (
	INDUSTRYCODE	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	INDUSTRY	CHAR(50),
	STABILITY	INT
);
CREATE TABLE investment (
	SSN	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null  REFERENCES userrecord (SSN)	CONSTRAINT null NOT NULL,
	CAPITALGAINS	INT,
	CAPITALLOSSES	INT,
	STOCKDIVIDENDS	INT
);
CREATE TABLE occupation (
	OCCUPATIONCODE	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	OCCUPATION	CHAR(50),
	STABILITY	INT
);
CREATE TABLE job (
	SSN	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null  REFERENCES userrecord (SSN)	CONSTRAINT null NOT NULL,
	WORKCLASS	CHAR(50),
	INDUSTRYCODE	INT	CONSTRAINT null  REFERENCES industry (INDUSTRYCODE),
	OCCUPATIONCODE	INT	CONSTRAINT null  REFERENCES occupation (OCCUPATIONCODE),
	UNIONMEMBER	CHAR(50),
	EMPLOYERSIZE	INT,
	WEEKWAGE	INT,
	SELFEMPLOYED	SMALLINT,
	WORKWEEKS	INT
);
CREATE TABLE migration (
	SSN	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null  REFERENCES userrecord (SSN)	CONSTRAINT null NOT NULL,
	MIGRATIONCODE	CHAR(50),
	MIGRATIONDISTANCE	CHAR(50),
	MIGRATIONMOVE	CHAR(50),
	MIGRATIONFROMSUNBELT	CHAR(50)
);
CREATE TABLE stateabbv (
	ABBV	CHAR(2)	CONSTRAINT null NOT NULL,
	NAME	CHAR(50)	CONSTRAINT null NOT NULL
);
CREATE TABLE wage (
	INDUSTRYCODE	INT	CONSTRAINT null  REFERENCES industry (INDUSTRYCODE)	CONSTRAINT null NOT NULL,
	OCCUPATIONCODE	INT	CONSTRAINT null  REFERENCES occupation (OCCUPATIONCODE)	CONSTRAINT null NOT NULL,
	MEANWEEKWAGE	INT,
	CONSTRAINT null PRIMARY KEY (INDUSTRYCODE, OCCUPATIONCODE)
);
CREATE TABLE youth (
	SSN	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null  REFERENCES userrecord (SSN)	CONSTRAINT null NOT NULL,
	PARENTS	CHAR(50)
);
CREATE TABLE ziptable (
	ZIP	CHAR(5),
	CITY	CHAR(20),
	STATENAME	CHAR(20),
	COUNTY	CHAR(20)
);
-- Coverage: 72/72 (100.00000%) 
-- Time to generate: 1977ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 768ms 
-- * Number of objective function evaluations: 286
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{SSN}" on table "userrecord"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{SSN}" on table "education"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{SSN}" on table "employmentstat"
-- * Success: true
-- * Time: 65ms 
-- * Number of objective function evaluations: 204
-- * Number of restarts: 1

-- Negating "PRIMARY KEY{RESSTATE}" on table "geo"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{INDUSTRYCODE}" on table "industry"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{SSN}" on table "investment"
-- * Success: true
-- * Time: 211ms 
-- * Number of objective function evaluations: 554
-- * Number of restarts: 2

-- Negating "PRIMARY KEY{OCCUPATIONCODE}" on table "occupation"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{SSN}" on table "job"
-- * Success: true
-- * Time: 197ms 
-- * Number of objective function evaluations: 1016
-- * Number of restarts: 3

-- Negating "PRIMARY KEY{SSN}" on table "migration"
-- * Success: true
-- * Time: 332ms 
-- * Number of objective function evaluations: 2690
-- * Number of restarts: 9

-- Negating "PRIMARY KEY{INDUSTRYCODE, OCCUPATIONCODE}" on table "wage"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{SSN}" on table "youth"
-- * Success: true
-- * Time: 64ms 
-- * Number of objective function evaluations: 560
-- * Number of restarts: 2

-- Negating "FOREIGN KEY{SSN}" on table "education"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 39
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{SSN}" on table "employmentstat"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 41
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{SSN}" on table "investment"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 84
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{OCCUPATIONCODE}" on table "job"
-- * Success: true
-- * Time: 72ms 
-- * Number of objective function evaluations: 361
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{SSN}" on table "job"
-- * Success: true
-- * Time: 59ms 
-- * Number of objective function evaluations: 332
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{INDUSTRYCODE}" on table "job"
-- * Success: true
-- * Time: 63ms 
-- * Number of objective function evaluations: 326
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{SSN}" on table "migration"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 83
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{INDUSTRYCODE}" on table "wage"
-- * Success: true
-- * Time: 8ms 
-- * Number of objective function evaluations: 101
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{OCCUPATIONCODE}" on table "wage"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 100
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{SSN}" on table "youth"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 77
-- * Number of restarts: 1

-- Negating "NOT NULL(SSN)" on table "userrecord"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "education"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "employmentstat"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "NOT NULL(REGION)" on table "geo"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(RESSTATE)" on table "geo"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(INDUSTRYCODE)" on table "industry"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "investment"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "NOT NULL(OCCUPATIONCODE)" on table "occupation"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "job"
-- * Success: true
-- * Time: 60ms 
-- * Number of objective function evaluations: 357
-- * Number of restarts: 1

-- Negating "NOT NULL(SSN)" on table "migration"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "NOT NULL(ABBV)" on table "stateabbv"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(NAME)" on table "stateabbv"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(INDUSTRYCODE)" on table "wage"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 82
-- * Number of restarts: 1

-- Negating "NOT NULL(OCCUPATIONCODE)" on table "wage"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 79
-- * Number of restarts: 1

-- Negating "NOT NULL(SSN)" on table "youth"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

