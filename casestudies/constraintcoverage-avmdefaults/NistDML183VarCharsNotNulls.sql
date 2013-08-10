/******************************************************
 * Constraint coverage for NistDML183VarCharsNotNulls *
 ******************************************************/
DROP TABLE IF EXISTS S;
DROP TABLE IF EXISTS T;
CREATE TABLE T (
	A	VARCHAR(10)	NOT NULL,
	B	VARCHAR(10)	NOT NULL,
	C	VARCHAR(10)	NOT NULL,
	CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
);
CREATE TABLE S (
	X	VARCHAR(10)	NOT NULL,
	Y	VARCHAR(10)	NOT NULL,
	Z	VARCHAR(10)	NOT NULL,
	CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y) REFERENCES T (A, B)
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 140ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 10ms 
INSERT INTO T(A, B, C) VALUES('a', '', '');
INSERT INTO T(A, B, C) VALUES('', '', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(A)" on table "T"
-- * Success: true
-- * Time: 1ms 
INSERT INTO T(A, B, C) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(B)" on table "T"
-- * Success: true
-- * Time: 12ms 
INSERT INTO T(A, B, C) VALUES('b', NULL, '');
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(C)" on table "T"
-- * Success: true
-- * Time: 12ms 
INSERT INTO T(A, B, C) VALUES('b', '', NULL);
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "UNIQUE[A, B]" on table "T"
-- * Success: true
-- * Time: 0ms 
INSERT INTO T(A, B, C) VALUES('', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[X, Y]" on table "S"
-- * Success: true
-- * Time: 25ms 
INSERT INTO T(A, B, C) VALUES('b', '', '');
INSERT INTO S(X, Y, Z) VALUES('`', '', '');
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "NOT NULL(X)" on table "S"
-- * Success: true
-- * Time: 26ms 
INSERT INTO T(A, B, C) VALUES('`', '', '');
INSERT INTO S(X, Y, Z) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 19
-- * Number of restarts: 0

-- Negating "NOT NULL(Y)" on table "S"
-- * Success: true
-- * Time: 27ms 
INSERT INTO T(A, B, C) VALUES('aa', '', '');
INSERT INTO S(X, Y, Z) VALUES('', NULL, '');
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

-- Negating "NOT NULL(Z)" on table "S"
-- * Success: true
-- * Time: 27ms 
INSERT INTO T(A, B, C) VALUES('a', 'a', '');
INSERT INTO S(X, Y, Z) VALUES('', '', NULL);
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

