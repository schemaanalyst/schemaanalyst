/**********************************************
 * Constraint coverage for NistDML181NotNulls *
 **********************************************/
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS LONG_NAMED_PEOPLE;
CREATE TABLE LONG_NAMED_PEOPLE (
	FIRSTNAME	VARCHAR(373)	CONSTRAINT null NOT NULL,
	LASTNAME	VARCHAR(373)	CONSTRAINT null NOT NULL,
	AGE	INT,
	CONSTRAINT null PRIMARY KEY (FIRSTNAME, LASTNAME)
);
CREATE TABLE ORDERS (
	FIRSTNAME	VARCHAR(373)	CONSTRAINT null NOT NULL,
	LASTNAME	VARCHAR(373)	CONSTRAINT null NOT NULL,
	TITLE	VARCHAR(80),
	COST	NUMERIC(5, 2),
	CONSTRAINT null FOREIGN KEY (FIRSTNAME, LASTNAME) REFERENCES LONG_NAMED_PEOPLE (FIRSTNAME, LASTNAME)
);
-- Coverage: 12/12 (100.00000%) 
-- Time to generate: 86ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 16ms 
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{FIRSTNAME, LASTNAME}" on table "LONG_NAMED_PEOPLE"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{FIRSTNAME, LASTNAME}" on table "ORDERS"
-- * Success: true
-- * Time: 42ms 
-- * Number of objective function evaluations: 23
-- * Number of restarts: 0

-- Negating "NOT NULL(FIRSTNAME)" on table "LONG_NAMED_PEOPLE"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(LASTNAME)" on table "LONG_NAMED_PEOPLE"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(FIRSTNAME)" on table "ORDERS"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 9
-- * Number of restarts: 0

-- Negating "NOT NULL(LASTNAME)" on table "ORDERS"
-- * Success: true
-- * Time: 13ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

