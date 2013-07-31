/**********************************************
 * Constraint coverage for NistDML183NotNulls *
 **********************************************/
DROP TABLE IF EXISTS S;
DROP TABLE IF EXISTS T;
CREATE TABLE T (
	A	CHAR	NOT NULL,
	B	CHAR	NOT NULL,
	C	CHAR	NOT NULL,
	CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
);
CREATE TABLE S (
	X	CHAR	NOT NULL,
	Y	CHAR	NOT NULL,
	Z	CHAR	NOT NULL,
	CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y) REFERENCES T (A, B)
);
-- Coverage: 16/16 (100.00000%) 
-- Time to generate: 192ms 

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
-- * Time: 2ms 
INSERT INTO T(A, B, C) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(B)" on table "T"
-- * Success: true
-- * Time: 11ms 
INSERT INTO T(A, B, C) VALUES('b', NULL, '');
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(C)" on table "T"
-- * Success: true
-- * Time: 12ms 
INSERT INTO T(A, B, C) VALUES('b', '', NULL);
-- * Number of objective function evaluations: 16
-- * Number of restarts: 0

-- Negating "UNIQUE[A, B]" on table "T"
-- * Success: true
-- * Time: 1ms 
INSERT INTO T(A, B, C) VALUES('', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[X, Y]" on table "S"
-- * Success: true
-- * Time: 19ms 
INSERT INTO T(A, B, C) VALUES('b', '', '');
INSERT INTO S(X, Y, Z) VALUES('`', '', '');
-- * Number of objective function evaluations: 21
-- * Number of restarts: 0

-- Negating "NOT NULL(X)" on table "S"
-- * Success: true
-- * Time: 21ms 
INSERT INTO T(A, B, C) VALUES('`', '', '');
INSERT INTO S(X, Y, Z) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

-- Negating "NOT NULL(Y)" on table "S"
-- * Success: true
-- * Time: 25ms 
INSERT INTO T(A, B, C) VALUES('a', 'a', '');
INSERT INTO S(X, Y, Z) VALUES('', NULL, '');
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "NOT NULL(Z)" on table "S"
-- * Success: true
-- * Time: 91ms 
INSERT INTO T(A, B, C) VALUES('o', 'g', 'h');
INSERT INTO S(X, Y, Z) VALUES('o', 'g', NULL);
-- * Number of objective function evaluations: 64
-- * Number of restarts: 1

