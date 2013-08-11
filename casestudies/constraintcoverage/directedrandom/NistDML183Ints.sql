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
-- Time to generate: 17ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 11ms 
INSERT INTO T(A, B, C) VALUES(0, 0, 0);
INSERT INTO T(A, B, C) VALUES(-52, 10, 0);
INSERT INTO S(X, Y, Z) VALUES(0, 0, 0);
INSERT INTO S(X, Y, Z) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "UNIQUE[A, B]" on table "T"
-- * Success: true
-- * Time: 1ms 
INSERT INTO T(A, B, C) VALUES(0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[X, Y]" on table "S"
-- * Success: true
-- * Time: 5ms 
INSERT INTO T(A, B, C) VALUES(19, -34, 0);
INSERT INTO S(X, Y, Z) VALUES(96, 88, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

