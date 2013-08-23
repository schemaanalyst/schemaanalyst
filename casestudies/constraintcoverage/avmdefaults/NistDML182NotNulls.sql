/**********************************************
 * Constraint coverage for NistDML182NotNulls *
 **********************************************/
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS ID_CODES;
CREATE TABLE ID_CODES (
	CODE1	INT	CONSTRAINT null NOT NULL,
	CODE2	INT	CONSTRAINT null NOT NULL,
	CODE3	INT	CONSTRAINT null NOT NULL,
	CODE4	INT	CONSTRAINT null NOT NULL,
	CODE5	INT	CONSTRAINT null NOT NULL,
	CODE6	INT	CONSTRAINT null NOT NULL,
	CODE7	INT	CONSTRAINT null NOT NULL,
	CODE8	INT	CONSTRAINT null NOT NULL,
	CODE9	INT	CONSTRAINT null NOT NULL,
	CODE10	INT	CONSTRAINT null NOT NULL,
	CODE11	INT	CONSTRAINT null NOT NULL,
	CODE12	INT	CONSTRAINT null NOT NULL,
	CODE13	INT	CONSTRAINT null NOT NULL,
	CODE14	INT	CONSTRAINT null NOT NULL,
	CODE15	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT null PRIMARY KEY (CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15)
);
CREATE TABLE ORDERS (
	CODE1	INT	CONSTRAINT null NOT NULL,
	CODE2	INT	CONSTRAINT null NOT NULL,
	CODE3	INT	CONSTRAINT null NOT NULL,
	CODE4	INT	CONSTRAINT null NOT NULL,
	CODE5	INT	CONSTRAINT null NOT NULL,
	CODE6	INT	CONSTRAINT null NOT NULL,
	CODE7	INT	CONSTRAINT null NOT NULL,
	CODE8	INT	CONSTRAINT null NOT NULL,
	CODE9	INT	CONSTRAINT null NOT NULL,
	CODE10	INT	CONSTRAINT null NOT NULL,
	CODE11	INT	CONSTRAINT null NOT NULL,
	CODE12	INT	CONSTRAINT null NOT NULL,
	CODE13	INT	CONSTRAINT null NOT NULL,
	CODE14	INT	CONSTRAINT null NOT NULL,
	CODE15	INT	CONSTRAINT null NOT NULL,
	TITLE	VARCHAR(80),
	COST	NUMERIC(5, 2),
	CONSTRAINT null FOREIGN KEY (CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) REFERENCES ID_CODES (CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15)
);
-- Coverage: 64/64 (100.00000%) 
-- Time to generate: 1113ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 41ms 
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15}" on table "ID_CODES"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15}" on table "ORDERS"
-- * Success: true
-- * Time: 228ms 
-- * Number of objective function evaluations: 53
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE1)" on table "ID_CODES"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE2)" on table "ID_CODES"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE3)" on table "ID_CODES"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE4)" on table "ID_CODES"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE5)" on table "ID_CODES"
-- * Success: true
-- * Time: 8ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE6)" on table "ID_CODES"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE7)" on table "ID_CODES"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE8)" on table "ID_CODES"
-- * Success: true
-- * Time: 10ms 
-- * Number of objective function evaluations: 23
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE9)" on table "ID_CODES"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 26
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE10)" on table "ID_CODES"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 29
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE11)" on table "ID_CODES"
-- * Success: true
-- * Time: 13ms 
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE12)" on table "ID_CODES"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 35
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE13)" on table "ID_CODES"
-- * Success: true
-- * Time: 10ms 
-- * Number of objective function evaluations: 38
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE14)" on table "ID_CODES"
-- * Success: true
-- * Time: 12ms 
-- * Number of objective function evaluations: 41
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE15)" on table "ID_CODES"
-- * Success: true
-- * Time: 13ms 
-- * Number of objective function evaluations: 44
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE1)" on table "ORDERS"
-- * Success: true
-- * Time: 68ms 
-- * Number of objective function evaluations: 47
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE2)" on table "ORDERS"
-- * Success: true
-- * Time: 54ms 
-- * Number of objective function evaluations: 50
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE3)" on table "ORDERS"
-- * Success: true
-- * Time: 49ms 
-- * Number of objective function evaluations: 53
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE4)" on table "ORDERS"
-- * Success: true
-- * Time: 54ms 
-- * Number of objective function evaluations: 56
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE5)" on table "ORDERS"
-- * Success: true
-- * Time: 54ms 
-- * Number of objective function evaluations: 59
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE6)" on table "ORDERS"
-- * Success: true
-- * Time: 52ms 
-- * Number of objective function evaluations: 62
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE7)" on table "ORDERS"
-- * Success: true
-- * Time: 42ms 
-- * Number of objective function evaluations: 65
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE8)" on table "ORDERS"
-- * Success: true
-- * Time: 63ms 
-- * Number of objective function evaluations: 68
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE9)" on table "ORDERS"
-- * Success: true
-- * Time: 54ms 
-- * Number of objective function evaluations: 71
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE10)" on table "ORDERS"
-- * Success: true
-- * Time: 61ms 
-- * Number of objective function evaluations: 74
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE11)" on table "ORDERS"
-- * Success: true
-- * Time: 44ms 
-- * Number of objective function evaluations: 77
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE12)" on table "ORDERS"
-- * Success: true
-- * Time: 30ms 
-- * Number of objective function evaluations: 80
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE13)" on table "ORDERS"
-- * Success: true
-- * Time: 25ms 
-- * Number of objective function evaluations: 83
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE14)" on table "ORDERS"
-- * Success: true
-- * Time: 28ms 
-- * Number of objective function evaluations: 86
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE15)" on table "ORDERS"
-- * Success: true
-- * Time: 26ms 
-- * Number of objective function evaluations: 89
-- * Number of restarts: 0

