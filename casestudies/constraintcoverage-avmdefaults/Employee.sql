/************************************
 * Constraint coverage for Employee *
 ************************************/
DROP TABLE IF EXISTS Employee;
CREATE TABLE Employee (
	id	INT	PRIMARY KEY,
	first	VARCHAR(15),
	last	VARCHAR(20),
	age	INT,
	address	VARCHAR(30),
	city	VARCHAR(20),
	state	VARCHAR(20),
	CHECK (id >= 0),
	CHECK (age > 0),
	CHECK (age <= 150)
);
-- Coverage: 8/8 (100.00000%) 
-- Time to generate: 206ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 39ms 
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(1, '', '', 1, '', '', '');
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(0, '', '', 1, '', '', '');
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "Employee"
-- * Success: true
-- * Time: 5ms 
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(0, '', '', NULL, '', '', '');
-- * Number of objective function evaluations: 9
-- * Number of restarts: 0

-- Negating "CHECK[id >= 0]" on table "Employee"
-- * Success: true
-- * Time: 6ms 
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(-1, '', '', NULL, '', '', '');
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "CHECK[age > 0]" on table "Employee"
-- * Success: true
-- * Time: 41ms 
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(46, 'phctgpyae', 'ddanycpk', -8, '', 'mssuu', 'synehq');
-- * Number of objective function evaluations: 69
-- * Number of restarts: 1

-- Negating "CHECK[age <= 150]" on table "Employee"
-- * Success: true
-- * Time: 115ms 
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(78, 'ab', 'nyparp', 190, 'hkt', 'edrxhn', 'n');
-- * Number of objective function evaluations: 343
-- * Number of restarts: 4

