/**************************************
 * Constraint coverage for NistDML181 *
 **************************************/
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS LONG_NAMED_PEOPLE;
CREATE TABLE LONG_NAMED_PEOPLE (
	FIRSTNAME	VARCHAR(373),
	LASTNAME	VARCHAR(373),
	AGE	INT,
	PRIMARY KEY (FIRSTNAME, LASTNAME)
);
CREATE TABLE ORDERS (
	FIRSTNAME	VARCHAR(373),
	LASTNAME	VARCHAR(373),
	TITLE	VARCHAR(80),
	COST	NUMERIC(5, 2),
	FOREIGN KEY (FIRSTNAME, LASTNAME) REFERENCES LONG_NAMED_PEOPLE (FIRSTNAME, LASTNAME)
);
-- Coverage: 4/4 (100.00000%) 
-- Time to generate: 26ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 13ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('', '', 0);
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('phctgpyae', 'danycpk', 0);
INSERT INTO ORDERS(FIRSTNAME, LASTNAME, TITLE, COST) VALUES('', '', '', 0);
INSERT INTO ORDERS(FIRSTNAME, LASTNAME, TITLE, COST) VALUES('', '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[FIRSTNAME, LASTNAME]" on table "LONG_NAMED_PEOPLE"
-- * Success: true
-- * Time: 2ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES('', '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[FIRSTNAME, LASTNAME]" on table "ORDERS"
-- * Success: true
-- * Time: 11ms 
INSERT INTO LONG_NAMED_PEOPLE(FIRSTNAME, LASTNAME, AGE) VALUES(NULL, 'pxsnbhf', 0);
INSERT INTO ORDERS(FIRSTNAME, LASTNAME, TITLE, COST) VALUES('oaks', 'cysrdla', '', 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

