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
-- Time to generate: 21ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 11ms 
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(0, 0, 10);
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(0, -52, 96);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(TNUM1)" on table "TEST12549"
-- * Success: true
-- * Time: 2ms 
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(NULL, 75, 88);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "UNIQUE[TNUM2]" on table "TEST12549"
-- * Success: true
-- * Time: 7ms 
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(0, 0, 9);
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "CHECK[TNUM3 > 0]" on table "TEST12549"
-- * Success: true
-- * Time: 1ms 
INSERT INTO TEST12549(TNUM1, TNUM2, TNUM3) VALUES(0, 92, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

