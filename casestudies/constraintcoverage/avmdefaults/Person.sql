/**********************************
 * Constraint coverage for Person *
 **********************************/
DROP TABLE IF EXISTS person;
CREATE TABLE person (
	id	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	last_name	VARCHAR(45)	CONSTRAINT null NOT NULL,
	first_name	VARCHAR(45)	CONSTRAINT null NOT NULL,
	gender	VARCHAR(6)	CONSTRAINT null NOT NULL,
	date_of_birth	DATE	CONSTRAINT null NOT NULL,
	CONSTRAINT null CHECK (gender IN ('Male', 'Female', 'Uknown'))
);
-- Coverage: 14/14 (100.00000%) 
-- Time to generate: 589ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 330ms 
-- * Number of objective function evaluations: 181
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "person"
-- * Success: true
-- * Time: 65ms 
-- * Number of objective function evaluations: 75
-- * Number of restarts: 0

-- Negating "CHECK[gender IN ('Male', 'Female', 'Uknown')]" on table "person"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "person"
-- * Success: true
-- * Time: 35ms 
-- * Number of objective function evaluations: 73
-- * Number of restarts: 0

-- Negating "NOT NULL(last_name)" on table "person"
-- * Success: true
-- * Time: 53ms 
-- * Number of objective function evaluations: 77
-- * Number of restarts: 0

-- Negating "NOT NULL(first_name)" on table "person"
-- * Success: true
-- * Time: 42ms 
-- * Number of objective function evaluations: 77
-- * Number of restarts: 0

-- Negating "NOT NULL(gender)" on table "person"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "NOT NULL(date_of_birth)" on table "person"
-- * Success: true
-- * Time: 57ms 
-- * Number of objective function evaluations: 101
-- * Number of restarts: 0

