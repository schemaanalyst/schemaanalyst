/**********************************************
 * Constraint coverage for NistDML183VarChars *
 **********************************************/
DROP TABLE IF EXISTS S;
DROP TABLE IF EXISTS T;
CREATE TABLE T (
	A	VARCHAR(1),
	B	VARCHAR(1),
	C	VARCHAR(1),
	CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
);
CREATE TABLE S (
	X	VARCHAR(1),
	Y	VARCHAR(1),
	Z	VARCHAR(1),
	CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y) REFERENCES T (A, B)
);
-- Coverage: 4/4 (100.00000%) 
-- Time to generate: 19ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 12ms 
INSERT INTO T(A, B, C) VALUES('', '', '');
INSERT INTO T(A, B, C) VALUES('p', 'o', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "UNIQUE[A, B]" on table "T"
-- * Success: true
-- * Time: 0ms 
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

