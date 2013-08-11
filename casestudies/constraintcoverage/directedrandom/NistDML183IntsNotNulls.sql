/**************************************************
 * Constraint coverage for NistDML183IntsNotNulls *
 **************************************************/
DROP TABLE IF EXISTS S;
DROP TABLE IF EXISTS T;
CREATE TABLE T (
	A	INT	NOT NULL,
	B	INT	NOT NULL,
	C	INT	NOT NULL,
	CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
);
CREATE TABLE S (
	X	INT	NOT NULL,
	Y	INT	NOT NULL,
	Z	INT	NOT NULL,
	CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y) REFERENCES T (A, B)
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 43ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 14ms 
INSERT INTO T(A, B, C) VALUES(0, 0, 0);
INSERT INTO T(A, B, C) VALUES(-52, 10, 0);
INSERT INTO S(X, Y, Z) VALUES(0, 0, 0);
INSERT INTO S(X, Y, Z) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(A)" on table "T"
-- * Success: true
-- * Time: 2ms 
INSERT INTO T(A, B, C) VALUES(NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(B)" on table "T"
-- * Success: true
-- * Time: 3ms 
INSERT INTO T(A, B, C) VALUES(0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(C)" on table "T"
-- * Success: true
-- * Time: 2ms 
INSERT INTO T(A, B, C) VALUES(19, -34, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "UNIQUE[A, B]" on table "T"
-- * Success: true
-- * Time: 0ms 
INSERT INTO T(A, B, C) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[X, Y]" on table "S"
-- * Success: true
-- * Time: 4ms 
INSERT INTO T(A, B, C) VALUES(-23, 96, 0);
INSERT INTO S(X, Y, Z) VALUES(88, -75, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(X)" on table "S"
-- * Success: true
-- * Time: 6ms 
INSERT INTO T(A, B, C) VALUES(-71, -96, 0);
INSERT INTO S(X, Y, Z) VALUES(NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Y)" on table "S"
-- * Success: true
-- * Time: 6ms 
INSERT INTO T(A, B, C) VALUES(9, 92, 0);
INSERT INTO S(X, Y, Z) VALUES(0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Z)" on table "S"
-- * Success: true
-- * Time: 6ms 
INSERT INTO T(A, B, C) VALUES(-80, 25, 0);
INSERT INTO S(X, Y, Z) VALUES(0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

