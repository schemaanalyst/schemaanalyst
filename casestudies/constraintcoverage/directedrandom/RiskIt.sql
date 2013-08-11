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
	SSN	INT	PRIMARY KEY	NOT NULL,
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
	SSN	INT	PRIMARY KEY	 REFERENCES userrecord (SSN)	NOT NULL,
	EDUCATION	CHAR(50),
	EDUENROLL	CHAR(50)
);
CREATE TABLE employmentstat (
	SSN	INT	PRIMARY KEY	 REFERENCES userrecord (SSN)	NOT NULL,
	UNEMPLOYMENTREASON	CHAR(50),
	EMPLOYMENTSTAT	CHAR(50)
);
CREATE TABLE geo (
	REGION	CHAR(50)	NOT NULL,
	RESSTATE	CHAR(50)	PRIMARY KEY	NOT NULL
);
CREATE TABLE industry (
	INDUSTRYCODE	INT	PRIMARY KEY	NOT NULL,
	INDUSTRY	CHAR(50),
	STABILITY	INT
);
CREATE TABLE investment (
	SSN	INT	PRIMARY KEY	 REFERENCES userrecord (SSN)	NOT NULL,
	CAPITALGAINS	INT,
	CAPITALLOSSES	INT,
	STOCKDIVIDENDS	INT
);
CREATE TABLE occupation (
	OCCUPATIONCODE	INT	PRIMARY KEY	NOT NULL,
	OCCUPATION	CHAR(50),
	STABILITY	INT
);
CREATE TABLE job (
	SSN	INT	PRIMARY KEY	 REFERENCES userrecord (SSN)	NOT NULL,
	WORKCLASS	CHAR(50),
	INDUSTRYCODE	INT	 REFERENCES industry (INDUSTRYCODE),
	OCCUPATIONCODE	INT	 REFERENCES occupation (OCCUPATIONCODE),
	UNIONMEMBER	CHAR(50),
	EMPLOYERSIZE	INT,
	WEEKWAGE	INT,
	SELFEMPLOYED	SMALLINT,
	WORKWEEKS	INT
);
CREATE TABLE migration (
	SSN	INT	PRIMARY KEY	 REFERENCES userrecord (SSN)	NOT NULL,
	MIGRATIONCODE	CHAR(50),
	MIGRATIONDISTANCE	CHAR(50),
	MIGRATIONMOVE	CHAR(50),
	MIGRATIONFROMSUNBELT	CHAR(50)
);
CREATE TABLE stateabbv (
	ABBV	CHAR(2)	NOT NULL,
	NAME	CHAR(50)	NOT NULL
);
CREATE TABLE wage (
	INDUSTRYCODE	INT	 REFERENCES industry (INDUSTRYCODE)	NOT NULL,
	OCCUPATIONCODE	INT	 REFERENCES occupation (OCCUPATIONCODE)	NOT NULL,
	MEANWEEKWAGE	INT,
	PRIMARY KEY (INDUSTRYCODE, OCCUPATIONCODE)
);
CREATE TABLE youth (
	SSN	INT	PRIMARY KEY	 REFERENCES userrecord (SSN)	NOT NULL,
	PARENTS	CHAR(50)
);
CREATE TABLE ziptable (
	ZIP	CHAR(5),
	CITY	CHAR(20),
	STATENAME	CHAR(20),
	COUNTY	CHAR(20)
);
-- Coverage: 72/72 (100.00000%) 
-- Time to generate: 1033ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 997ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 0, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -52, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(0, '', '');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(-52, '', '');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(0, '', '');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(-52, '', '');
INSERT INTO geo(REGION, RESSTATE) VALUES('', '');
INSERT INTO geo(REGION, RESSTATE) VALUES('', 'aehj');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(0, '', 0);
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-100, '', 0);
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(0, 0, 0, 0);
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(-52, 0, 0, 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(0, '', 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(25, '', 0);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(0, '', 0, 0, '', 0, 0, 0, 0);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(-52, '', 0, 0, '', 0, 0, 0, 0);
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(0, '', '', '', '');
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(-52, '', '', '', '');
INSERT INTO stateabbv(ABBV, NAME) VALUES('', '');
INSERT INTO stateabbv(ABBV, NAME) VALUES('', '');
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(0, 0, 0);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(-100, 0, 0);
INSERT INTO youth(SSN, PARENTS) VALUES(0, '');
INSERT INTO youth(SSN, PARENTS) VALUES(-52, '');
INSERT INTO ziptable(ZIP, CITY, STATENAME, COUNTY) VALUES('', '', '', '');
INSERT INTO ziptable(ZIP, CITY, STATENAME, COUNTY) VALUES('', '', '', '');
-- * Number of objective function evaluations: 219
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[SSN]" on table "userrecord"
-- * Success: true
-- * Time: 0ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 0, 0, '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "userrecord"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', NULL, 0, '', '', '', '', '', '', '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[SSN]" on table "education"
-- * Success: true
-- * Time: 0ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -31, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(0, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[SSN]" on table "education"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 45, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(-59, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "education"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -22, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[SSN]" on table "employmentstat"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 89, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(0, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[SSN]" on table "employmentstat"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 25, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(34, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "employmentstat"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 28, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[RESSTATE]" on table "geo"
-- * Success: true
-- * Time: 0ms 
INSERT INTO geo(REGION, RESSTATE) VALUES('', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(REGION)" on table "geo"
-- * Success: true
-- * Time: 0ms 
INSERT INTO geo(REGION, RESSTATE) VALUES(NULL, 'tme');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(RESSTATE)" on table "geo"
-- * Success: true
-- * Time: 0ms 
INSERT INTO geo(REGION, RESSTATE) VALUES('', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[INDUSTRYCODE]" on table "industry"
-- * Success: true
-- * Time: 0ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(0, '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(INDUSTRYCODE)" on table "industry"
-- * Success: true
-- * Time: 0ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[SSN]" on table "investment"
-- * Success: true
-- * Time: 0ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -76, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[SSN]" on table "investment"
-- * Success: true
-- * Time: 0ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -71, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(-99, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "investment"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -26, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(NULL, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[OCCUPATIONCODE]" on table "occupation"
-- * Success: true
-- * Time: 0ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(0, '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(OCCUPATIONCODE)" on table "occupation"
-- * Success: true
-- * Time: 0ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[SSN]" on table "job"
-- * Success: true
-- * Time: 2ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-47, '', 0);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -11, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-49, '', 0);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(0, '', 0, 0, '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[OCCUPATIONCODE]" on table "job"
-- * Success: true
-- * Time: 3ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(27, '', 0);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 39, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(83, '', 0);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(-71, '', 0, 35, '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[SSN]" on table "job"
-- * Success: true
-- * Time: 3ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(8, '', 0);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -57, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-79, '', 0);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(-25, '', 0, 0, '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[INDUSTRYCODE]" on table "job"
-- * Success: true
-- * Time: 2ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-27, '', 0);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 81, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(36, '', 0);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(81, '', -48, 0, '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "job"
-- * Success: true
-- * Time: 2ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-28, '', 0);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 70, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(41, '', 0);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(NULL, '', 0, 0, '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[SSN]" on table "migration"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -82, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(0, '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[SSN]" on table "migration"
-- * Success: true
-- * Time: 2ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 72, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(76, '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "migration"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 57, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(NULL, '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(ABBV)" on table "stateabbv"
-- * Success: true
-- * Time: 0ms 
INSERT INTO stateabbv(ABBV, NAME) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(NAME)" on table "stateabbv"
-- * Success: true
-- * Time: 0ms 
INSERT INTO stateabbv(ABBV, NAME) VALUES('', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[INDUSTRYCODE, OCCUPATIONCODE]" on table "wage"
-- * Success: true
-- * Time: 2ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-90, '', 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(53, '', 0);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[INDUSTRYCODE]" on table "wage"
-- * Success: true
-- * Time: 1ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(43, '', 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-93, '', 0);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(-57, 8, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[OCCUPATIONCODE]" on table "wage"
-- * Success: true
-- * Time: 2ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(2, '', 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(9, '', 0);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(0, 23, 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(INDUSTRYCODE)" on table "wage"
-- * Success: true
-- * Time: 2ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-1, '', 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(48, '', 0);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(OCCUPATIONCODE)" on table "wage"
-- * Success: true
-- * Time: 1ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-22, '', 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-37, '', 0);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[SSN]" on table "youth"
-- * Success: true
-- * Time: 2ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 37, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO youth(SSN, PARENTS) VALUES(0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[SSN]" on table "youth"
-- * Success: true
-- * Time: 2ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 88, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO youth(SSN, PARENTS) VALUES(31, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "youth"
-- * Success: true
-- * Time: 1ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -1, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO youth(SSN, PARENTS) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

