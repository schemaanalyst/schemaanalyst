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
-- Time to generate: 441ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 47ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(-52, 10, -34, 96, 88, -75, -96, 92, 25, 55, -3, 46, 67, 79, -84);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15]" on table "ID_CODES"
-- * Success: true
-- * Time: 4ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE1)" on table "ID_CODES"
-- * Success: true
-- * Time: 3ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE2)" on table "ID_CODES"
-- * Success: true
-- * Time: 4ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE3)" on table "ID_CODES"
-- * Success: true
-- * Time: 4ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE4)" on table "ID_CODES"
-- * Success: true
-- * Time: 3ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE5)" on table "ID_CODES"
-- * Success: true
-- * Time: 2ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE6)" on table "ID_CODES"
-- * Success: true
-- * Time: 3ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE7)" on table "ID_CODES"
-- * Success: true
-- * Time: 3ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE8)" on table "ID_CODES"
-- * Success: true
-- * Time: 4ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE9)" on table "ID_CODES"
-- * Success: true
-- * Time: 4ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE10)" on table "ID_CODES"
-- * Success: true
-- * Time: 4ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE11)" on table "ID_CODES"
-- * Success: true
-- * Time: 3ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE12)" on table "ID_CODES"
-- * Success: true
-- * Time: 2ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE13)" on table "ID_CODES"
-- * Success: true
-- * Time: 9ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE14)" on table "ID_CODES"
-- * Success: true
-- * Time: 3ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE15)" on table "ID_CODES"
-- * Success: true
-- * Time: 2ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15]" on table "ORDERS"
-- * Success: true
-- * Time: 18ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(44, -72, -100, 0, -33, 94, 22, -57, -99, 54, -58, 89, 0, 70, 98);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(-66, 44, 60, -8, 0, 93, -91, 0, 0, 94, 53, -49, 69, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE1)" on table "ORDERS"
-- * Success: true
-- * Time: 15ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(-83, -100, 6, -45, 21, -92, -1, 4, -5, -36, 0, 49, -53, 82, -100);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE2)" on table "ORDERS"
-- * Success: true
-- * Time: 18ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(78, -15, -19, 34, 78, 2, 8, 22, 0, 26, 48, -38, -62, 42, -38);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE3)" on table "ORDERS"
-- * Success: true
-- * Time: 22ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(-65, 36, 18, 6, -34, 47, 0, -63, 51, 0, 0, 6, 0, -86, 76);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE4)" on table "ORDERS"
-- * Success: true
-- * Time: 37ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(97, 20, 2, -57, -97, 33, -88, -72, 45, 25, 0, -82, -14, 20, -10);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE5)" on table "ORDERS"
-- * Success: true
-- * Time: 25ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(-47, -59, 84, 83, 0, 15, -34, -81, 33, -5, 7, 19, -100, 42, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE6)" on table "ORDERS"
-- * Success: true
-- * Time: 27ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(38, 44, -88, 4, 38, 1, -67, 39, 0, -8, -63, 15, -26, 7, -38);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE7)" on table "ORDERS"
-- * Success: true
-- * Time: 25ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(34, 87, 70, -25, -100, 0, -46, -17, -52, -71, 67, -48, -2, 70, -91);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE8)" on table "ORDERS"
-- * Success: true
-- * Time: 29ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(68, 48, -42, 65, -28, -46, -100, -52, 57, -59, -91, -19, -98, 70, -57);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE9)" on table "ORDERS"
-- * Success: true
-- * Time: 20ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(62, 7, 9, -71, -25, 13, 30, -17, -97, 48, -23, -86, 6, -97, 16);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE10)" on table "ORDERS"
-- * Success: true
-- * Time: 19ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(-92, -95, -23, 22, -22, -42, -13, -82, -1, 40, 87, -89, -18, 42, 89);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE11)" on table "ORDERS"
-- * Success: true
-- * Time: 20ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(44, 0, -82, 50, 91, 0, 26, -28, 16, 34, -20, -56, -42, -84, 9);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE12)" on table "ORDERS"
-- * Success: true
-- * Time: 16ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(18, 20, 0, 62, -12, 92, 45, -16, 48, -80, -10, -39, 66, 22, 0);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE13)" on table "ORDERS"
-- * Success: true
-- * Time: 17ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(66, -44, -25, 95, -85, 0, -22, 0, 44, 94, 4, -51, -95, -69, -63);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE14)" on table "ORDERS"
-- * Success: true
-- * Time: 15ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(0, -5, -12, 38, 0, 2, 16, 0, 17, -49, -1, -89, 95, 1, 94);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(CODE15)" on table "ORDERS"
-- * Success: true
-- * Time: 14ms 
INSERT INTO ID_CODES(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15) VALUES(61, -16, -43, -31, -8, -36, -27, -88, 0, -52, 39, -85, 30, 20, 82);
INSERT INTO ORDERS(CODE1, CODE2, CODE3, CODE4, CODE5, CODE6, CODE7, CODE8, CODE9, CODE10, CODE11, CODE12, CODE13, CODE14, CODE15, TITLE, COST) VALUES(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

