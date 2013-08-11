/**************************************
 * Constraint coverage for NistXTS748 *
 **************************************/
DROP TABLE IF EXISTS TEST12549;
CREATE TABLE TEST12549 (
	TNUM1	NUMERIC(5)	CONSTRAINT CND12549A NOT NULL,
	TNUM2	NUMERIC(5)	CONSTRAINT CND12549B UNIQUE,
	TNUM3	NUMERIC(5),
	CONSTRAINT CND12549C CHECK (TNUM3 > 0)
);
-- Coverage: 6/6 (100.00000%) 
-- Time to generate: 31ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 21ms 
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(0, 1, 1);
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(0, 0, 1);
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

-- Negating "NOT NULL(TNUM1)" on table "TEST12549"
-- * Success: true
-- * Time: 2ms 
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(NULL, NULL, NULL);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "UNIQUE[TNUM2]" on table "TEST12549"
-- * Success: true
-- * Time: 5ms 
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(0, 0, NULL);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "CHECK[TNUM3 > 0]" on table "TEST12549"
-- * Success: true
-- * Time: 3ms 
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(0, NULL, 0);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

