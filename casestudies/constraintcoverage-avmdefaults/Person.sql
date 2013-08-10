/**********************************
 * Constraint coverage for Person *
 **********************************/
DROP TABLE IF EXISTS person;
CREATE TABLE person (
	id	INT	PRIMARY KEY	NOT NULL,
	last_name	VARCHAR(45)	NOT NULL,
	first_name	VARCHAR(45)	NOT NULL,
	gender	VARCHAR(6)	NOT NULL,
	date_of_birth	DATE	NOT NULL,
	CHECK (gender IN ('Male', 'Female', 'Uknown'))
);
-- Coverage: 14/14 (100.00000%) 
-- Time to generate: 543ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 295ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(1, '', '', 'Male', '1000-01-01');
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(0, '', '', 'Male', '1000-01-01');
-- * Number of objective function evaluations: 181
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "person"
-- * Success: true
-- * Time: 67ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(0, '', '', 'Male', '1000-01-01');
-- * Number of objective function evaluations: 75
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "person"
-- * Success: true
-- * Time: 40ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(NULL, '', '', 'Male', '1000-01-01');
-- * Number of objective function evaluations: 73
-- * Number of restarts: 0

-- Negating "NOT NULL(last_name)" on table "person"
-- * Success: true
-- * Time: 48ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(-1, NULL, '', 'Male', '1000-01-01');
-- * Number of objective function evaluations: 77
-- * Number of restarts: 0

-- Negating "NOT NULL(first_name)" on table "person"
-- * Success: true
-- * Time: 41ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(-1, '', NULL, 'Male', '1000-01-01');
-- * Number of objective function evaluations: 77
-- * Number of restarts: 0

-- Negating "NOT NULL(gender)" on table "person"
-- * Success: true
-- * Time: 5ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(-1, '', '', NULL, '1000-01-01');
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "NOT NULL(date_of_birth)" on table "person"
-- * Success: true
-- * Time: 46ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(-1, '', '', 'Male', NULL);
-- * Number of objective function evaluations: 101
-- * Number of restarts: 0

-- Negating "CHECK[gender IN ('Male', 'Female', 'Uknown')]" on table "person"
-- * Success: true
-- * Time: 1ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(-1, '', '', '', '1000-01-01');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

