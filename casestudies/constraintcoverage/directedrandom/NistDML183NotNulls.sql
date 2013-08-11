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
-- Time to generate: 47ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 13ms 
INSERT INTO T(A, B, C) VALUES('', '', '');
INSERT INTO T(A, B, C) VALUES('p', 'o', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
INSERT INTO S(X, Y, Z) VALUES('', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(A)" on table "T"
-- * Success: true
-- * Time: 2ms 
INSERT INTO T(A, B, C) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(B)" on table "T"
-- * Success: true
-- * Time: 3ms 
INSERT INTO T(A, B, C) VALUES('', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(C)" on table "T"
-- * Success: true
-- * Time: 4ms 
INSERT INTO T(A, B, C) VALUES('g', 'y', NULL);
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
-- * Time: 4ms 
INSERT INTO T(A, B, C) VALUES('a', '', '');
INSERT INTO S(X, Y, Z) VALUES('j', 'n', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(X)" on table "S"
-- * Success: true
-- * Time: 5ms 
INSERT INTO T(A, B, C) VALUES('o', 'p', '');
INSERT INTO S(X, Y, Z) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Y)" on table "S"
-- * Success: true
-- * Time: 7ms 
INSERT INTO T(A, B, C) VALUES('a', 'y', '');
INSERT INTO S(X, Y, Z) VALUES('', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(Z)" on table "S"
-- * Success: true
-- * Time: 8ms 
INSERT INTO T(A, B, C) VALUES('v', 's', '');
INSERT INTO S(X, Y, Z) VALUES('', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

