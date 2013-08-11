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
-- Time to generate: 22ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 14ms 
INSERT INTO T(A, B, C) VALUES('', '', '');
INSERT INTO T(A, B, C) VALUES('p', 'o', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "UNIQUE[A, B]" on table "T"
-- * Success: true
-- * Time: 1ms 
INSERT INTO T(A, B, C) VALUES('', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[X, Y]" on table "S"
-- * Success: true
-- * Time: 7ms 
INSERT INTO T(A, B, C) VALUES('g', 'y', '');
INSERT INTO S(X, Y, Z) VALUES('e', 'd', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

