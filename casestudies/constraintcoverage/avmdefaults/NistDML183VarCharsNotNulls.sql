/******************************************************
 * Constraint coverage for NistDML183VarcharsNotNulls *
 ******************************************************/
DROP TABLE IF EXISTS S;
DROP TABLE IF EXISTS T;
CREATE TABLE T (
	A	VARCHAR(10)	CONSTRAINT null NOT NULL,
	B	VARCHAR(10)	CONSTRAINT null NOT NULL,
	C	VARCHAR(10)	CONSTRAINT null NOT NULL,
	CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
);
CREATE TABLE S (
	X	VARCHAR(10)	CONSTRAINT null NOT NULL,
	Y	VARCHAR(10)	CONSTRAINT null NOT NULL,
	Z	VARCHAR(10)	CONSTRAINT null NOT NULL,
	CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y) REFERENCES T (A, B)
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 193ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 17ms 
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{X, Y}" on table "S"
-- * Success: true
-- * Time: 49ms 
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "NOT NULL(A)" on table "T"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(B)" on table "T"
-- * Success: true
-- * Time: 10ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(C)" on table "T"
-- * Success: true
-- * Time: 12ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "NOT NULL(X)" on table "S"
-- * Success: true
-- * Time: 28ms 
-- * Number of objective function evaluations: 19
-- * Number of restarts: 0

-- Negating "NOT NULL(Y)" on table "S"
-- * Success: true
-- * Time: 37ms 
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

-- Negating "NOT NULL(Z)" on table "S"
-- * Success: true
-- * Time: 37ms 
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

-- Negating "UNIQUE{A, B}" on table "T"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

