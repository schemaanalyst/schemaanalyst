/**************************************
 * Constraint coverage for NistDML183 *
 **************************************/
DROP TABLE IF EXISTS S;
DROP TABLE IF EXISTS T;
CREATE TABLE T (
	A	CHAR,
	B	CHAR,
	C	CHAR,
	CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
);
CREATE TABLE S (
	X	CHAR,
	Y	CHAR,
	Z	CHAR,
	CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y) REFERENCES T (A, B)
);
-- Coverage: 4/4 (100.00000%) 
-- Time to generate: 33ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 12ms 
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{X, Y}" on table "S"
-- * Success: true
-- * Time: 20ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "UNIQUE{A, B}" on table "T"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

