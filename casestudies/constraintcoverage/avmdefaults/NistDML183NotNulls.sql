/**********************************************
 * Constraint coverage for NistDML183NotNulls *
 **********************************************/
DROP TABLE IF EXISTS S;
DROP TABLE IF EXISTS T;
CREATE TABLE T (
	A	CHAR	CONSTRAINT null NOT NULL,
	B	CHAR	CONSTRAINT null NOT NULL,
	C	CHAR	CONSTRAINT null NOT NULL,
	CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
);
CREATE TABLE S (
	X	CHAR	CONSTRAINT null NOT NULL,
	Y	CHAR	CONSTRAINT null NOT NULL,
	Z	CHAR	CONSTRAINT null NOT NULL,
	CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y) REFERENCES T (A, B)
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 246ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 17ms 
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{X, Y}" on table "S"
-- * Success: true
-- * Time: 41ms 
-- * Number of objective function evaluations: 21
-- * Number of restarts: 0

-- Negating "NOT NULL(A)" on table "T"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(B)" on table "T"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(C)" on table "T"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 16
-- * Number of restarts: 0

-- Negating "NOT NULL(X)" on table "S"
-- * Success: true
-- * Time: 25ms 
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

-- Negating "NOT NULL(Y)" on table "S"
-- * Success: true
-- * Time: 33ms 
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "NOT NULL(Z)" on table "S"
-- * Success: true
-- * Time: 105ms 
-- * Number of objective function evaluations: 64
-- * Number of restarts: 1

-- Negating "UNIQUE{A, B}" on table "T"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

