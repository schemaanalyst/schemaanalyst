/**********************************************
 * Constraint coverage for NistDML182NotNulls *
 **********************************************/
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS ID_CODES;
CREATE TABLE ID_CODES (
	CODE1	INT	NOT NULL,
	CODE2	INT	NOT NULL,
	CODE3	INT	NOT NULL,
	CODE4	INT	NOT NULL,
	CODE5	INT	NOT NULL,
	CODE6	INT	NOT NULL,
	CODE7	INT	NOT NULL,
	CODE8	INT	NOT NULL,
	CODE9	INT	NOT NULL,
	CODE10	INT	NOT NULL,
	CODE11	INT	NOT NULL,
	CODE12	INT	NOT NULL,
	CODE13	INT	NOT NULL,
	CODE14	INT	NOT NULL,
	CODE15	INT	NOT NULL,
	PRIMARY KEY (CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15)
);
CREATE TABLE ORDERS (
	CODE1	INT	NOT NULL,
	CODE2	INT	NOT NULL,
	CODE3	INT	NOT NULL,
	CODE4	INT	NOT NULL,
	CODE5	INT	NOT NULL,
	CODE6	INT	NOT NULL,
	CODE7	INT	NOT NULL,
	CODE8	INT	NOT NULL,
	CODE9	INT	NOT NULL,
	CODE10	INT	NOT NULL,
	CODE11	INT	NOT NULL,
	CODE12	INT	NOT NULL,
	CODE13	INT	NOT NULL,
	CODE14	INT	NOT NULL,
	CODE15	INT	NOT NULL,
	TITLE	VARCHAR(80),
	COST	NUMERIC(5, 2),
	FOREIGN KEY (CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) REFERENCES ID_CODES (CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15)
);
-- Coverage: 64/64 (100.00000%) 
-- Time to generate: 1715ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 47ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15]" on table "ID_CODES"
-- * Success: true
-- * Time: 5ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE1)" on table "ID_CODES"
-- * Success: true
-- * Time: 4ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE2)" on table "ID_CODES"
-- * Success: true
-- * Time: 7ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE3)" on table "ID_CODES"
-- * Success: true
-- * Time: 8ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE4)" on table "ID_CODES"
-- * Success: true
-- * Time: 8ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE5)" on table "ID_CODES"
-- * Success: true
-- * Time: 9ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE6)" on table "ID_CODES"
-- * Success: true
-- * Time: 19ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE7)" on table "ID_CODES"
-- * Success: true
-- * Time: 13ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE8)" on table "ID_CODES"
-- * Success: true
-- * Time: 14ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 23
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE9)" on table "ID_CODES"
-- * Success: true
-- * Time: 12ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 26
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE10)" on table "ID_CODES"
-- * Success: true
-- * Time: 15ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 29
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE11)" on table "ID_CODES"
-- * Success: true
-- * Time: 19ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0);
-- * Number of objective function evaluations: 32
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE12)" on table "ID_CODES"
-- * Success: true
-- * Time: 17ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0);
-- * Number of objective function evaluations: 35
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE13)" on table "ID_CODES"
-- * Success: true
-- * Time: 10ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0);
-- * Number of objective function evaluations: 38
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE14)" on table "ID_CODES"
-- * Success: true
-- * Time: 10ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0);
-- * Number of objective function evaluations: 41
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE15)" on table "ID_CODES"
-- * Success: true
-- * Time: 13ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL);
-- * Number of objective function evaluations: 44
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15]" on table "ORDERS"
-- * Success: true
-- * Time: 165ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 53
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE1)" on table "ORDERS"
-- * Success: true
-- * Time: 135ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 50
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE2)" on table "ORDERS"
-- * Success: true
-- * Time: 96ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 53
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE3)" on table "ORDERS"
-- * Success: true
-- * Time: 69ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 56
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE4)" on table "ORDERS"
-- * Success: true
-- * Time: 92ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 59
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE5)" on table "ORDERS"
-- * Success: true
-- * Time: 114ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 62
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE6)" on table "ORDERS"
-- * Success: true
-- * Time: 71ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 65
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE7)" on table "ORDERS"
-- * Success: true
-- * Time: 76ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 68
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE8)" on table "ORDERS"
-- * Success: true
-- * Time: 94ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 71
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE9)" on table "ORDERS"
-- * Success: true
-- * Time: 83ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 74
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE10)" on table "ORDERS"
-- * Success: true
-- * Time: 59ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 77
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE11)" on table "ORDERS"
-- * Success: true
-- * Time: 65ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 80
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE12)" on table "ORDERS"
-- * Success: true
-- * Time: 84ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 83
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE13)" on table "ORDERS"
-- * Success: true
-- * Time: 110ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, '', 0);
-- * Number of objective function evaluations: 86
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE14)" on table "ORDERS"
-- * Success: true
-- * Time: 83ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, '', 0);
-- * Number of objective function evaluations: 89
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE15)" on table "ORDERS"
-- * Success: true
-- * Time: 89ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, '', 0);
-- * Number of objective function evaluations: 92
-- * Number of restarts: 0

