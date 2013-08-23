/*************************************
 * Constraint coverage for UnixUsage *
 *************************************/
DROP TABLE IF EXISTS USAGE_HISTORY;
DROP TABLE IF EXISTS UNIX_COMMAND;
DROP TABLE IF EXISTS TRANSCRIPT;
DROP TABLE IF EXISTS USER_INFO;
DROP TABLE IF EXISTS RACE_INFO;
DROP TABLE IF EXISTS OFFICE_INFO;
DROP TABLE IF EXISTS COURSE_INFO;
DROP TABLE IF EXISTS DEPT_INFO;
CREATE TABLE DEPT_INFO (
	DEPT_ID	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	DEPT_NAME	VARCHAR(50)
);
CREATE TABLE COURSE_INFO (
	COURSE_ID	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	COURSE_NAME	VARCHAR(50),
	OFFERED_DEPT	INT	CONSTRAINT null  REFERENCES DEPT_INFO (DEPT_ID),
	GRADUATE_LEVEL	SMALLINT
);
CREATE TABLE OFFICE_INFO (
	OFFICE_ID	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	OFFICE_NAME	VARCHAR(50),
	HAS_PRINTER	SMALLINT
);
CREATE TABLE RACE_INFO (
	RACE_CODE	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	RACE	VARCHAR(50)
);
CREATE TABLE USER_INFO (
	USER_ID	VARCHAR(50)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	FIRST_NAME	VARCHAR(50),
	LAST_NAME	VARCHAR(50),
	SEX	VARCHAR(1),
	DEPT_ID	INT	CONSTRAINT null  REFERENCES DEPT_INFO (DEPT_ID),
	OFFICE_ID	INT	CONSTRAINT null  REFERENCES OFFICE_INFO (OFFICE_ID),
	GRADUATE	SMALLINT,
	RACE	INT	CONSTRAINT null  REFERENCES RACE_INFO (RACE_CODE),
	PASSWORD	VARCHAR(50)	CONSTRAINT null NOT NULL,
	YEARS_USING_UNIX	INT,
	ENROLL_DATE	DATE
);
CREATE TABLE TRANSCRIPT (
	USER_ID	VARCHAR(50)	CONSTRAINT null  REFERENCES USER_INFO (USER_ID)	CONSTRAINT null NOT NULL,
	COURSE_ID	INT	CONSTRAINT null  REFERENCES COURSE_INFO (COURSE_ID)	CONSTRAINT null NOT NULL,
	SCORE	INT,
	CONSTRAINT null PRIMARY KEY (USER_ID, COURSE_ID)
);
CREATE TABLE UNIX_COMMAND (
	UNIX_COMMAND	VARCHAR(50)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	CATEGORY	VARCHAR(50)
);
CREATE TABLE USAGE_HISTORY (
	USER_ID	VARCHAR(50)	CONSTRAINT null  REFERENCES USER_INFO (USER_ID)	CONSTRAINT null NOT NULL,
	SESSION_ID	INT,
	LINE_NO	INT,
	COMMAND_SEQ	INT,
	COMMAND	VARCHAR(50)
);
-- Coverage: 48/48 (100.00000%) 
-- Time to generate: 1795ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 506ms 
-- * Number of objective function evaluations: 165
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{DEPT_ID}" on table "DEPT_INFO"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{COURSE_ID}" on table "COURSE_INFO"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{OFFICE_ID}" on table "OFFICE_INFO"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{RACE_CODE}" on table "RACE_INFO"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{USER_ID}" on table "USER_INFO"
-- * Success: true
-- * Time: 125ms 
-- * Number of objective function evaluations: 252
-- * Number of restarts: 1

-- Negating "PRIMARY KEY{USER_ID, COURSE_ID}" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 348ms 
-- * Number of objective function evaluations: 869
-- * Number of restarts: 3

-- Negating "PRIMARY KEY{UNIX_COMMAND}" on table "UNIX_COMMAND"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{OFFERED_DEPT}" on table "COURSE_INFO"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 33
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{DEPT_ID}" on table "USER_INFO"
-- * Success: true
-- * Time: 35ms 
-- * Number of objective function evaluations: 164
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{OFFICE_ID}" on table "USER_INFO"
-- * Success: true
-- * Time: 37ms 
-- * Number of objective function evaluations: 187
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{RACE}" on table "USER_INFO"
-- * Success: true
-- * Time: 37ms 
-- * Number of objective function evaluations: 198
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{USER_ID}" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 107ms 
-- * Number of objective function evaluations: 284
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{COURSE_ID}" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 107ms 
-- * Number of objective function evaluations: 359
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{USER_ID}" on table "USAGE_HISTORY"
-- * Success: true
-- * Time: 52ms 
-- * Number of objective function evaluations: 198
-- * Number of restarts: 1

-- Negating "NOT NULL(DEPT_ID)" on table "DEPT_INFO"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(COURSE_ID)" on table "COURSE_INFO"
-- * Success: true
-- * Time: 8ms 
-- * Number of objective function evaluations: 67
-- * Number of restarts: 1

-- Negating "NOT NULL(OFFICE_ID)" on table "OFFICE_INFO"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(RACE_CODE)" on table "RACE_INFO"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(USER_ID)" on table "USER_INFO"
-- * Success: true
-- * Time: 49ms 
-- * Number of objective function evaluations: 157
-- * Number of restarts: 1

-- Negating "NOT NULL(PASSWORD)" on table "USER_INFO"
-- * Success: true
-- * Time: 44ms 
-- * Number of objective function evaluations: 200
-- * Number of restarts: 1

-- Negating "NOT NULL(USER_ID)" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 104ms 
-- * Number of objective function evaluations: 340
-- * Number of restarts: 1

-- Negating "NOT NULL(COURSE_ID)" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 174ms 
-- * Number of objective function evaluations: 390
-- * Number of restarts: 1

-- Negating "NOT NULL(UNIX_COMMAND)" on table "UNIX_COMMAND"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(USER_ID)" on table "USAGE_HISTORY"
-- * Success: true
-- * Time: 58ms 
-- * Number of objective function evaluations: 234
-- * Number of restarts: 1

