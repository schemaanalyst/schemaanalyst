/********************************************
 * Constraint coverage for StudentResidence *
 ********************************************/
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Residence;
CREATE TABLE Residence (
	name	VARCHAR(50)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	capacity	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT null CHECK (capacity > 1),
	CONSTRAINT null CHECK (capacity <= 10)
);
CREATE TABLE Student (
	id	INT	CONSTRAINT null PRIMARY KEY,
	firstName	VARCHAR(50),
	lastName	VARCHAR(50),
	residence	VARCHAR(50)	CONSTRAINT null  REFERENCES Residence (name),
	CONSTRAINT null CHECK (id >= 0)
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 409ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 65ms 
-- * Number of objective function evaluations: 27
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{name}" on table "Residence"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "Student"
-- * Success: true
-- * Time: 25ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "CHECK[capacity > 1]" on table "Residence"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "CHECK[capacity <= 10]" on table "Residence"
-- * Success: true
-- * Time: 12ms 
-- * Number of objective function evaluations: 19
-- * Number of restarts: 0

-- Negating "CHECK[id >= 0]" on table "Student"
-- * Success: true
-- * Time: 35ms 
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{residence}" on table "Student"
-- * Success: true
-- * Time: 261ms 
-- * Number of objective function evaluations: 290
-- * Number of restarts: 3

-- Negating "NOT NULL(name)" on table "Residence"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(capacity)" on table "Residence"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

