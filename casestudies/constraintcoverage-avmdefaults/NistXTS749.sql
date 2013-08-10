/**************************************
 * Constraint coverage for NistXTS749 *
 **************************************/
DROP TABLE IF EXISTS TEST12649;
DROP TABLE IF EXISTS STAFF;
CREATE TABLE STAFF (
	SALARY	INT,
	EMPNAME	CHAR(20),
	GRADE	DECIMAL,
	EMPNUM	CHAR(3)	PRIMARY KEY	NOT NULL
);
CREATE TABLE TEST12649 (
	TNUM1	NUMERIC(5)	NOT NULL,
	TNUM2	NUMERIC(5)	NOT NULL,
	TCHAR	CHAR(3)	CONSTRAINT CND12649C  REFERENCES STAFF (EMPNUM),
	CONSTRAINT CND12649A PRIMARY KEY (TNUM1, TNUM2),
	CONSTRAINT CND12649B CHECK (TNUM2 > 0)
);
-- Coverage: 14/14 (100.00000%) 
-- Time to generate: 436ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 76ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, 'a');
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, '');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(1, 1, '');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(0, 1, '');
-- * Number of objective function evaluations: 46
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[EMPNUM]" on table "STAFF"
-- * Success: true
-- * Time: 1ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(EMPNUM)" on table "STAFF"
-- * Success: true
-- * Time: 1ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, NULL);
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[TNUM1, TNUM2]" on table "TEST12649"
-- * Success: true
-- * Time: 27ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, 'b');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(0, 1, '');
-- * Number of objective function evaluations: 27
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[TCHAR]" on table "TEST12649"
-- * Success: true
-- * Time: 32ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, '`');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(0, 3, 'aa');
-- * Number of objective function evaluations: 38
-- * Number of restarts: 0

-- Negating "NOT NULL(TNUM1)" on table "TEST12649"
-- * Success: true
-- * Time: 21ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, 'aa');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(NULL, 1, '');
-- * Number of objective function evaluations: 28
-- * Number of restarts: 0

-- Negating "NOT NULL(TNUM2)" on table "TEST12649"
-- * Success: true
-- * Time: 148ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(46, 'phctgpyae', -38, 'ha');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(92, NULL, 'ha');
-- * Number of objective function evaluations: 96
-- * Number of restarts: 1

-- Negating "CHECK[TNUM2 > 0]" on table "TEST12649"
-- * Success: true
-- * Time: 130ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(95, 'mssuu', 5, 'dla');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(-39, -29, 'dla');
-- * Number of objective function evaluations: 117
-- * Number of restarts: 1

