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
-- Time to generate: 59ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 15ms 
INSERT INTO T(A, B, C) VALUES('', '', '');
INSERT INTO T(A, B, C) VALUES('phctgpyae', 'danycpk', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(A)" on table "T"
-- * Success: true
-- * Time: 3ms 
INSERT INTO T(A, B, C) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(B)" on table "T"
-- * Success: true
-- * Time: 2ms 
INSERT INTO T(A, B, C) VALUES('', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(C)" on table "T"
-- * Success: true
-- * Time: 3ms 
INSERT INTO T(A, B, C) VALUES('yrvyoaks', 'dcysrd', NULL);
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
INSERT INTO T(A, B, C) VALUES('gqrupxsn', 'ef', '');
INSERT INTO S(X, Y, Z) VALUES('kgxrbjtp', 'tywey', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(X)" on table "S"
-- * Success: true
-- * Time: 8ms 
INSERT INTO T(A, B, C) VALUES('', 'qullv', '');
INSERT INTO S(X, Y, Z) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Y)" on table "S"
-- * Success: true
-- * Time: 8ms 
INSERT INTO T(A, B, C) VALUES('r', 'yi', '');
INSERT INTO S(X, Y, Z) VALUES('', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Z)" on table "S"
-- * Success: true
-- * Time: 12ms 
INSERT INTO T(A, B, C) VALUES('t', 'amyytmgh', '');
INSERT INTO S(X, Y, Z) VALUES('', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

