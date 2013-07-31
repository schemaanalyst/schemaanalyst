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
-- Time to generate: 3483ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 737ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 1, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 0, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(1, '', '');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(0, '', '');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(1, '', '');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(0, '', '');
INSERT INTO geo(REGION, RESSTATE) VALUES('', 'a');
INSERT INTO geo(REGION, RESSTATE) VALUES('', '');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(1, '', 0);
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(0, '', 0);
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(1, 0, 0, 0);
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(0, 0, 0, 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(1, '', 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(0, '', 0);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(1, '', 0, 0, '', 0, 0, 0, 0);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(0, '', 0, 0, '', 0, 0, 0, 0);
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(1, '', '', '', '');
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(0, '', '', '', '');
INSERT INTO stateabbv(ABBV, NAME) VALUES('', '');
INSERT INTO stateabbv(ABBV, NAME) VALUES('', '');
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(1, 0, 0);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(0, 0, 0);
INSERT INTO youth(SSN, PARENTS) VALUES(1, '');
INSERT INTO youth(SSN, PARENTS) VALUES(0, '');
INSERT INTO ziptable(ZIP, CITY, STATENAME, COUNTY) VALUES('', '', '', '');
INSERT INTO ziptable(ZIP, CITY, STATENAME, COUNTY) VALUES('', '', '', '');
-- * Number of objective function evaluations: 286
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
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[SSN]" on table "education"
-- * Success: true
-- * Time: 4ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', -1, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(0, '', '');
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[SSN]" on table "education"
-- * Success: true
-- * Time: 14ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 3, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(-3, '', '');
-- * Number of objective function evaluations: 39
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "education"
-- * Success: true
-- * Time: 92ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', 'gpnoi', -23, 96, 'aehj', '', 'aao', 'pktymss', 'ak', 'wdcysr', 'i', 'abiivyvp', 'hfckgxrbj', 'ajvtywe');
INSERT INTO education(SSN, EDUCATION, EDUENROLL) VALUES(NULL, '', 'qullv');
-- * Number of objective function evaluations: 212
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[SSN]" on table "employmentstat"
-- * Success: true
-- * Time: 373ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('pmeif', 'ehsbc', 78, 6, 's', 'b', 'yvxabof', 'xfwagq', 'wix', 'psh', 'dfgawcou', 'ligsf', 'rfglffu', 'i');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(0, 'b', 'q');
-- * Number of objective function evaluations: 569
-- * Number of restarts: 2

-- Negating "FOREIGN KEY[SSN]" on table "employmentstat"
-- * Success: true
-- * Time: 23ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', '', 2, 0, '', '', '', '', '', '', '', '', '', '');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(-3, '', '');
-- * Number of objective function evaluations: 41
-- * Number of restarts: 0

-- Negating "NOT NULL(SSN)" on table "employmentstat"
-- * Success: true
-- * Time: 67ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('gke', 'oojar', 42, -87, '', 'rusibin', '', 'rdmweir', 'fcu', '', 'smjhwuo', 'huqcx', 'bjitphsvo', 'khgd');
INSERT INTO employmentstat(SSN, UNEMPLOYMENTREASON, EMPLOYMENTSTAT) VALUES(NULL, 'oruof', 'kvub');
-- * Number of objective function evaluations: 207
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[RESSTATE]" on table "geo"
-- * Success: true
-- * Time: 0ms 
INSERT INTO geo(REGION, RESSTATE) VALUES('', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(REGION)" on table "geo"
-- * Success: true
-- * Time: 1ms 
INSERT INTO geo(REGION, RESSTATE) VALUES(NULL, 'b');
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(RESSTATE)" on table "geo"
-- * Success: true
-- * Time: 1ms 
INSERT INTO geo(REGION, RESSTATE) VALUES('', NULL);
-- * Number of objective function evaluations: 4
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
-- * Time: 43ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('qidfos', '', 65, 54, 'v', 'gga', 'ttqnape', 'ykmadvg', 'eon', 'nmnjdf', 'tgn', 'qmkqa', 'plw', 'jbdnxa');
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(0, 16, -28, -92);
-- * Number of objective function evaluations: 198
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[SSN]" on table "investment"
-- * Success: true
-- * Time: 18ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('k', 'njqpg', -22, 98, 'ormei', 'mnroxjbe', 'jorebf', 'a', 'mgq', 'exafpt', '', 'd', 'yxrmjrlc', 'u');
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(-84, 53, 9, 5);
-- * Number of objective function evaluations: 84
-- * Number of restarts: 1

-- Negating "NOT NULL(SSN)" on table "investment"
-- * Success: true
-- * Time: 38ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('l', 'pcvuh', -12, 48, 'ovbttr', 'rcslyhfu', 'pl', '', 'rv', 'hnjey', 'ajseqrgae', 'nnng', 'xp', 'dreby');
INSERT INTO investment(SSN, CAPITALGAINS, CAPITALLOSSES, STOCKDIVIDENDS) VALUES(NULL, 71, -12, -38);
-- * Number of objective function evaluations: 212
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[OCCUPATIONCODE]" on table "occupation"
-- * Success: true
-- * Time: 1ms 
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
-- * Time: 187ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-93, 'co', -64);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('tjokhvi', 'ujvho', -30, 70, 'ohq', 'ewesulpky', 'qhsht', '', 'k', 'abqpvuh', '', 'q', 'gebjjf', 'gowgvban');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(83, 'cdropte', 87);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(0, 'fpw', NULL, NULL, '', -7, -69, -19, -13);
-- * Number of objective function evaluations: 1063
-- * Number of restarts: 3

-- Negating "FOREIGN KEY[OCCUPATIONCODE]" on table "job"
-- * Success: true
-- * Time: 75ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-86, 'svubqp', -14);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('xfdf', 'w', 28, 80, 'lefcxth', 'rnto', 'w', 'g', 'cjqr', 'bbrmdy', 'qnnrhl', 'yr', 'wxcck', '');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-66, 'woayn', -24);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(78, 'ufekp', NULL, 91, 'jfilwur', -95, -21, 71, 43);
-- * Number of objective function evaluations: 334
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[SSN]" on table "job"
-- * Success: true
-- * Time: 63ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(2, 't', 93);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('cnvttt', 'cqilu', 41, -77, 'eqriwh', 'gn', 'qit', '', '', 'u', 'qe', 'rohddwyv', 'luu', 'ayvs');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-80, '', -79);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(-38, '', NULL, NULL, 'elwlioas', -59, 67, -85, 30);
-- * Number of objective function evaluations: 283
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[INDUSTRYCODE]" on table "job"
-- * Success: true
-- * Time: 50ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(43, 'ct', -65);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('t', 'diwl', 52, 64, 'yfg', 'dmh', '', 'acemaymai', '', 'notpemwwh', 'qinshla', 'drug', '', 'bgdobylsm');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(76, '', 26);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(65, 'r', 93, NULL, 'n', 54, 0, 36, -19);
-- * Number of objective function evaluations: 314
-- * Number of restarts: 1

-- Negating "NOT NULL(SSN)" on table "job"
-- * Success: true
-- * Time: 56ms 
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-17, 'jrbfebk', 71);
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('shdrcu', 'jh', 45, -40, 'jjkuvypv', 'vjgkuiva', 'c', 'qdv', 'mqdrmem', 'ksrsl', 'ru', 'onj', 'iuey', 'myl');
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(41, 'fvprtoxmg', -12);
INSERT INTO job(SSN, WORKCLASS, INDUSTRYCODE, OCCUPATIONCODE, UNIONMEMBER, EMPLOYERSIZE, WEEKWAGE, SELFEMPLOYED, WORKWEEKS) VALUES(NULL, 'nehs', 41, -93, 'l', -73, -16, -3, -43);
-- * Number of objective function evaluations: 333
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[SSN]" on table "migration"
-- * Success: true
-- * Time: 345ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', 'tvitf', 88, 15, 'ismj', 'pcmad', 'aefosoh', 'rcdwakasb', 'bicmbx', 'wdswsgnsi', 'gf', 'tcwxwjr', 'ykjpid', 'hpjt');
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(1, 'wyje', 'lv', '', 'kvaobnmj');
-- * Number of objective function evaluations: 2484
-- * Number of restarts: 7

-- Negating "FOREIGN KEY[SSN]" on table "migration"
-- * Success: true
-- * Time: 13ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('ji', 'cxgfe', -94, 79, 'mihc', 'qitu', 'snnoe', 'huaoapfl', 'yxvnmpgbc', 'plovjwitc', 'xvmnuy', 'ddkgpqk', 'soji', 'dlfx');
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(76, 'g', 'sa', 'gcg', 'mbssasqyb');
-- * Number of objective function evaluations: 83
-- * Number of restarts: 1

-- Negating "NOT NULL(SSN)" on table "migration"
-- * Success: true
-- * Time: 30ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('mdaq', 'ct', 84, -100, 'yutibni', '', 'k', 'qemjcwgeo', 'muvmip', 'we', 'qbov', 'eyk', 'vjkkumwco', 'rksvgtif');
INSERT INTO migration(SSN, MIGRATIONCODE, MIGRATIONDISTANCE, MIGRATIONMOVE, MIGRATIONFROMSUNBELT) VALUES(NULL, 'n', 'omdcqh', 'jjgnfpl', 'ubu');
-- * Number of objective function evaluations: 224
-- * Number of restarts: 1

-- Negating "NOT NULL(ABBV)" on table "stateabbv"
-- * Success: true
-- * Time: 0ms 
INSERT INTO stateabbv(ABBV, NAME) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(NAME)" on table "stateabbv"
-- * Success: true
-- * Time: 1ms 
INSERT INTO stateabbv(ABBV, NAME) VALUES('', NULL);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[INDUSTRYCODE, OCCUPATIONCODE]" on table "wage"
-- * Success: true
-- * Time: 2ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-1, '', 0);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-1, '', 0);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[INDUSTRYCODE]" on table "wage"
-- * Success: true
-- * Time: 13ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-17, 'hlwfsuqr', -57);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(74, 'cs', -25);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(3, 43, 35);
-- * Number of objective function evaluations: 99
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[OCCUPATIONCODE]" on table "wage"
-- * Success: true
-- * Time: 14ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-56, 'vxtaj', 2);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(63, 'xptnp', 76);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(1, -42, -73);
-- * Number of objective function evaluations: 101
-- * Number of restarts: 1

-- Negating "NOT NULL(INDUSTRYCODE)" on table "wage"
-- * Success: true
-- * Time: 12ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(29, 'a', 35);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-81, 'dhj', 44);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(NULL, -17, 23);
-- * Number of objective function evaluations: 75
-- * Number of restarts: 1

-- Negating "NOT NULL(OCCUPATIONCODE)" on table "wage"
-- * Success: true
-- * Time: 17ms 
INSERT INTO industry(INDUSTRYCODE, INDUSTRY, STABILITY) VALUES(-9, 'bflxnutwa', -34);
INSERT INTO occupation(OCCUPATIONCODE, OCCUPATION, STABILITY) VALUES(-83, 'olijclxq', 12);
INSERT INTO wage(INDUSTRYCODE, OCCUPATIONCODE, MEANWEEKWAGE) VALUES(29, NULL, 13);
-- * Number of objective function evaluations: 104
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[SSN]" on table "youth"
-- * Success: true
-- * Time: 1148ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('ymqetcln', 'hyxg', 98, 85, '', 'mu', 'csggc', 'prlsctlso', 'fpbr', 'vjunupib', 'njhknjyp', 'f', 'w', 'fpqiyxgv');
INSERT INTO youth(SSN, PARENTS) VALUES(1, 'oc');
-- * Number of objective function evaluations: 7389
-- * Number of restarts: 23

-- Negating "FOREIGN KEY[SSN]" on table "youth"
-- * Success: true
-- * Time: 12ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('gfb', 'tydfh', -52, -82, 'vnonjtdfv', 'rex', 'bnofqv', 'maxrmwc', 'lfioykg', 'lawtyuqms', 'rvybk', 'bv', 'ytin', 'glhvh');
INSERT INTO youth(SSN, PARENTS) VALUES(-79, 'm');
-- * Number of objective function evaluations: 77
-- * Number of restarts: 1

-- Negating "NOT NULL(SSN)" on table "youth"
-- * Success: true
-- * Time: 32ms 
INSERT INTO userrecord(NAME, ZIP, SSN, AGE, SEX, MARITAL, RACE, TAXSTAT, DETAIL, HOUSEHOLDDETAIL, FATHERORIGIN, MOTHERORIGIN, BIRTHCOUNTRY, CITIZENSHIP) VALUES('', 'jkaog', 37, 92, 'ptmrrqo', 'ghhuvt', 'e', 'skontvaq', 'vxjsqcyv', '', 'hmpodat', '', 'efx', 'jqqhcyx');
INSERT INTO youth(SSN, PARENTS) VALUES(NULL, 'pf');
-- * Number of objective function evaluations: 209
-- * Number of restarts: 1

