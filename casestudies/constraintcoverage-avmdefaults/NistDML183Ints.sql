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
-- * Time: 9ms 
INSERT INTO T(A, B, C) VALUES(1, 0, 0);
INSERT INTO T(A, B, C) VALUES(0, 0, 0);
INSERT INTO S(X, Y, Z) VALUES(0, 0, 0);
INSERT INTO S(X, Y, Z) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "UNIQUE[A, B]" on table "T"
-- * Success: true
-- * Time: 1ms 
INSERT INTO T(A, B, C) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[X, Y]" on table "S"
-- * Success: true
-- * Time: 16ms 
INSERT INTO T(A, B, C) VALUES(NULL, 1, 0);
INSERT INTO S(X, Y, Z) VALUES(-1, 0, 0);
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

