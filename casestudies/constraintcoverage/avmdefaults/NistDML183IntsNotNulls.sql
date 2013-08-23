/**************************************************
 * Constraint coverage for NistDML183IntsNotNulls *
 **************************************************/
DROP TABLE IF EXISTS S;
DROP TABLE IF EXISTS T;
CREATE TABLE T (
	A	INT	CONSTRAINT null NOT NULL,
	B	INT	CONSTRAINT null NOT NULL,
	C	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
);
CREATE TABLE S (
	X	INT	CONSTRAINT null NOT NULL,
	Y	INT	CONSTRAINT null NOT NULL,
	Z	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y) REFERENCES T (A, B)
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 130ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 13ms 
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{X, Y}" on table "S"
-- * Success: true
-- * Time: 29ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "NOT NULL(A)" on table "T"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(B)" on table "T"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(C)" on table "T"
-- * Success: true
-- * Time: 10ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(X)" on table "S"
-- * Success: true
-- * Time: 18ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(Y)" on table "S"
-- * Success: true
-- * Time: 26ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "NOT NULL(Z)" on table "S"
-- * Success: true
-- * Time: 28ms 
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "UNIQUE{A, B}" on table "T"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

