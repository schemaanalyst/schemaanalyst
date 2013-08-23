/******************************************
 * Constraint coverage for NistDML183Ints *
 ******************************************/
DROP TABLE IF EXISTS S;
DROP TABLE IF EXISTS T;
CREATE TABLE T (
	A	INT,
	B	INT,
	C	INT,
	CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
);
CREATE TABLE S (
	X	INT,
	Y	INT,
	Z	INT,
	CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y) REFERENCES T (A, B)
);
-- Coverage: 4/4 (100.00000%) 
-- Time to generate: 26ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{X, Y}" on table "S"
-- * Success: true
-- * Time: 15ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "UNIQUE{A, B}" on table "T"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

