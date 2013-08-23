/***************************************
 * Constraint coverage for FrenchTowns *
 ***************************************/
DROP TABLE IF EXISTS Towns;
DROP TABLE IF EXISTS Departments;
DROP TABLE IF EXISTS Regions;
CREATE TABLE Regions (
	id	INT	CONSTRAINT null UNIQUE	CONSTRAINT null NOT NULL,
	code	VARCHAR(4)	CONSTRAINT null UNIQUE	CONSTRAINT null NOT NULL,
	capital	VARCHAR(10)	CONSTRAINT null NOT NULL,
	name	VARCHAR(100)	CONSTRAINT null UNIQUE	CONSTRAINT null NOT NULL
);
CREATE TABLE Departments (
	id	INT	CONSTRAINT null UNIQUE	CONSTRAINT null NOT NULL,
	code	VARCHAR(4)	CONSTRAINT null UNIQUE	CONSTRAINT null NOT NULL,
	capital	VARCHAR(10)	CONSTRAINT null UNIQUE	CONSTRAINT null NOT NULL,
	region	VARCHAR(4)	CONSTRAINT null  REFERENCES Regions (code)	CONSTRAINT null NOT NULL,
	name	VARCHAR(100)	CONSTRAINT null UNIQUE	CONSTRAINT null NOT NULL
);
CREATE TABLE Towns (
	id	INT	CONSTRAINT null UNIQUE	CONSTRAINT null NOT NULL,
	code	VARCHAR(10)	CONSTRAINT null NOT NULL,
	article	VARCHAR(100),
	name	VARCHAR(100)	CONSTRAINT null NOT NULL,
	department	VARCHAR(4)	CONSTRAINT null  REFERENCES Departments (code)	CONSTRAINT null NOT NULL,
	CONSTRAINT null UNIQUE (code, department)
);
-- Coverage: 48/48 (100.00000%) 
-- Time to generate: 2386ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 286ms 
-- * Number of objective function evaluations: 85
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{region}" on table "Departments"
-- * Success: true
-- * Time: 109ms 
-- * Number of objective function evaluations: 83
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{department}" on table "Towns"
-- * Success: true
-- * Time: 223ms 
-- * Number of objective function evaluations: 206
-- * Number of restarts: 1

-- Negating "NOT NULL(id)" on table "Regions"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 21
-- * Number of restarts: 0

-- Negating "NOT NULL(code)" on table "Regions"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 36
-- * Number of restarts: 1

-- Negating "NOT NULL(capital)" on table "Regions"
-- * Success: true
-- * Time: 12ms 
-- * Number of objective function evaluations: 60
-- * Number of restarts: 1

-- Negating "NOT NULL(name)" on table "Regions"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 45
-- * Number of restarts: 1

-- Negating "NOT NULL(id)" on table "Departments"
-- * Success: true
-- * Time: 104ms 
-- * Number of objective function evaluations: 193
-- * Number of restarts: 1

-- Negating "NOT NULL(code)" on table "Departments"
-- * Success: true
-- * Time: 61ms 
-- * Number of objective function evaluations: 177
-- * Number of restarts: 1

-- Negating "NOT NULL(capital)" on table "Departments"
-- * Success: true
-- * Time: 61ms 
-- * Number of objective function evaluations: 168
-- * Number of restarts: 1

-- Negating "NOT NULL(region)" on table "Departments"
-- * Success: true
-- * Time: 132ms 
-- * Number of objective function evaluations: 233
-- * Number of restarts: 1

-- Negating "NOT NULL(name)" on table "Departments"
-- * Success: true
-- * Time: 58ms 
-- * Number of objective function evaluations: 193
-- * Number of restarts: 1

-- Negating "NOT NULL(id)" on table "Towns"
-- * Success: true
-- * Time: 188ms 
-- * Number of objective function evaluations: 575
-- * Number of restarts: 2

-- Negating "NOT NULL(code)" on table "Towns"
-- * Success: true
-- * Time: 93ms 
-- * Number of objective function evaluations: 323
-- * Number of restarts: 1

-- Negating "NOT NULL(name)" on table "Towns"
-- * Success: true
-- * Time: 104ms 
-- * Number of objective function evaluations: 314
-- * Number of restarts: 1

-- Negating "NOT NULL(department)" on table "Towns"
-- * Success: true
-- * Time: 146ms 
-- * Number of objective function evaluations: 322
-- * Number of restarts: 1

-- Negating "UNIQUE{id}" on table "Regions"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 26
-- * Number of restarts: 0

-- Negating "UNIQUE{code}" on table "Regions"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 56
-- * Number of restarts: 1

-- Negating "UNIQUE{name}" on table "Regions"
-- * Success: true
-- * Time: 12ms 
-- * Number of objective function evaluations: 87
-- * Number of restarts: 1

-- Negating "UNIQUE{id}" on table "Departments"
-- * Success: true
-- * Time: 60ms 
-- * Number of objective function evaluations: 218
-- * Number of restarts: 1

-- Negating "UNIQUE{code}" on table "Departments"
-- * Success: true
-- * Time: 101ms 
-- * Number of objective function evaluations: 336
-- * Number of restarts: 2

-- Negating "UNIQUE{capital}" on table "Departments"
-- * Success: true
-- * Time: 63ms 
-- * Number of objective function evaluations: 211
-- * Number of restarts: 1

-- Negating "UNIQUE{name}" on table "Departments"
-- * Success: true
-- * Time: 70ms 
-- * Number of objective function evaluations: 228
-- * Number of restarts: 1

-- Negating "UNIQUE{id}" on table "Towns"
-- * Success: true
-- * Time: 158ms 
-- * Number of objective function evaluations: 435
-- * Number of restarts: 2

-- Negating "UNIQUE{code, department}" on table "Towns"
-- * Success: true
-- * Time: 319ms 
-- * Number of objective function evaluations: 836
-- * Number of restarts: 3

