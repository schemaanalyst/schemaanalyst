/********************************************
 * Constraint coverage for StudentResidence *
 ********************************************/
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Residence;
CREATE TABLE Residence (
	name	VARCHAR(50)	PRIMARY KEY	NOT NULL,
	capacity	INT	NOT NULL,
	CHECK (capacity > 1),
	CHECK (capacity <= 10)
);
CREATE TABLE Student (
	id	INT	PRIMARY KEY,
	firstName	VARCHAR(50),
	lastName	VARCHAR(50),
	residence	VARCHAR(50)	 REFERENCES Residence (name),
	CHECK (id >= 0)
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 238ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 57ms 
INSERT INTO Residence(name, capacity) VALUES('a', 3);
INSERT INTO Residence(name, capacity) VALUES('', 3);
INSERT INTO Student(id, firstName, lastName, residence) VALUES(1, '', '', '');
INSERT INTO Student(id, firstName, lastName, residence) VALUES(0, '', '', '');
-- * Number of objective function evaluations: 27
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[name]" on table "Residence"
-- * Success: true
-- * Time: 4ms 
INSERT INTO Residence(name, capacity) VALUES('', 3);
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "Residence"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Residence(name, capacity) VALUES(NULL, 3);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(capacity)" on table "Residence"
-- * Success: true
-- * Time: 10ms 
INSERT INTO Residence(name, capacity) VALUES('b', NULL);
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "CHECK[capacity > 1]" on table "Residence"
-- * Success: true
-- * Time: 4ms 
INSERT INTO Residence(name, capacity) VALUES('b', 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "CHECK[capacity <= 10]" on table "Residence"
-- * Success: true
-- * Time: 11ms 
INSERT INTO Residence(name, capacity) VALUES('b', 15);
-- * Number of objective function evaluations: 19
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "Student"
-- * Success: true
-- * Time: 16ms 
INSERT INTO Residence(name, capacity) VALUES('b', 3);
INSERT INTO Student(id, firstName, lastName, residence) VALUES(0, '', '', '');
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[residence]" on table "Student"
-- * Success: true
-- * Time: 108ms 
INSERT INTO Residence(name, capacity) VALUES('`', 3);
INSERT INTO Student(id, firstName, lastName, residence) VALUES(21, 'noi', 'pya', 'aa');
-- * Number of objective function evaluations: 118
-- * Number of restarts: 1

-- Negating "CHECK[id >= 0]" on table "Student"
-- * Success: true
-- * Time: 26ms 
INSERT INTO Residence(name, capacity) VALUES('aa', 3);
INSERT INTO Student(id, firstName, lastName, residence) VALUES(-1, '', '', '');
-- * Number of objective function evaluations: 27
-- * Number of restarts: 0

