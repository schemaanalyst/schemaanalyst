/**************************************
 * Constraint coverage for NistXTS749 *
 **************************************/
DROP TABLE IF EXISTS TEST12649;
DROP TABLE IF EXISTS STAFF;
CREATE TABLE STAFF (
	SALARY	INT,
	EMPNAME	CHAR(20),
	GRADE	DECIMAL,
	EMPNUM	CHAR(3)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL
);
CREATE TABLE TEST12649 (
	TNUM1	NUMERIC(5)	CONSTRAINT null NOT NULL,
	TNUM2	NUMERIC(5)	CONSTRAINT null NOT NULL,
	TCHAR	CHAR(3)	CONSTRAINT CND12649C  REFERENCES STAFF (EMPNUM),
	CONSTRAINT CND12649A PRIMARY KEY (TNUM1, TNUM2),
	CONSTRAINT CND12649B CHECK (TNUM2 > 0)
);
-- Coverage: 14/14 (100.00000%) 
-- Time to generate: 393ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 94ms 
-- * Number of objective function evaluations: 46
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{EMPNUM}" on table "STAFF"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{TNUM1, TNUM2}" on table "TEST12649"
-- * Success: true
-- * Time: 37ms 
-- * Number of objective function evaluations: 27
-- * Number of restarts: 0

-- Negating "CHECK[TNUM2 > 0]" on table "TEST12649"
-- * Success: true
-- * Time: 17ms 
-- * Number of objective function evaluations: 13
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{TCHAR}" on table "TEST12649"
-- * Success: true
-- * Time: 137ms 
-- * Number of objective function evaluations: 116
-- * Number of restarts: 1

-- Negating "NOT NULL(EMPNUM)" on table "STAFF"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(TNUM1)" on table "TEST12649"
-- * Success: true
-- * Time: 30ms 
-- * Number of objective function evaluations: 28
-- * Number of restarts: 0

-- Negating "NOT NULL(TNUM2)" on table "TEST12649"
-- * Success: true
-- * Time: 77ms 
-- * Number of objective function evaluations: 73
-- * Number of restarts: 1

