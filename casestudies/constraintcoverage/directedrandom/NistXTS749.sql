/**************************************
 * Constraint coverage for NistXTS749 *
 **************************************/
DROP TABLE IF EXISTS TEST12649;
DROP TABLE IF EXISTS STAFF;
CREATE TABLE STAFF (
	SALARY	INT,
	EMPNAME	CHAR(20),
	GRADE	DECIMAL,
	EMPNUM	CHAR(3)	PRIMARY KEY	NOT NULL
);
CREATE TABLE TEST12649 (
	TNUM1	NUMERIC(5)	NOT NULL,
	TNUM2	NUMERIC(5)	NOT NULL,
	TCHAR	CHAR(3)	CONSTRAINT CND12649C  REFERENCES STAFF (EMPNUM),
	CONSTRAINT CND12649A PRIMARY KEY (TNUM1, TNUM2),
	CONSTRAINT CND12649B CHECK (TNUM2 > 0)
);
-- Coverage: 13/14 (92.85714%) 
-- Time to generate: 10315ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 32ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, '');
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, 'phc');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(37, 14, '');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(-50, 96, '');
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[EMPNUM]" on table "STAFF"
-- * Success: true
-- * Time: 1ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(EMPNUM)" on table "STAFF"
-- * Success: true
-- * Time: 0ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[TNUM1, TNUM2]" on table "TEST12649"
-- * Success: true
-- * Time: 9ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, 'yne');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(37, 14, '');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[TCHAR]" on table "TEST12649"
-- * Success: false
-- * Time: 10270ms 
-- INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, 'rdl');
-- INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(0, 94, NULL);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except FOREIGN KEY[TCHAR]. Value: 0.30769230769230769232 [Sum: 0.44444444444444444445]
 	 	* Satisfy PRIMARY KEY[EMPNUM]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* ['rdl'] != ['']. Value: 0 [Best: 0]
 				 				* 'rdl' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['rdl'] != ['phc']. Value: 0E-20 [Best: 0E-20]
 				 				* 'rdl' != 'phc'. Value: 0E-20 [Best: 0E-20]
 					 					* 114 != 112. Value: 0E-20 [Distance: 0]
 			 			* ['rdl'] != ['yne']. Value: 0E-20 [Best: 0E-20]
 				 				* 'rdl' != 'yne'. Value: 0E-20 [Best: 0E-20]
 					 					* 114 != 121. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(EMPNUM). Value: 0E-20 [Sum: 0]
 		 		* 'rdl', allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[TNUM1, TNUM2]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [0, 94] != [37, 14]. Value: 0E-20 [Best: 0E-20]
 				 				* 0 != 37. Value: 0E-20 [Distance: 0]
 				 				* 94 != 14. Value: 0E-20 [Distance: 0]
 			 			* [0, 94] != [-50, 96]. Value: 0E-20 [Best: 0E-20]
 				 				* 0 != -50. Value: 0E-20 [Distance: 0]
 				 				* 94 != 96. Value: 0E-20 [Distance: 0]
 	 	* Violate FOREIGN KEY[TCHAR]. Value: 0.44444444444444444445 [Sum: 0.80000000000000000000]
 		 		* Evaluating row with reference rows. Value: 0.80000000000000000000 [Sum: 4]
 			 			* [null] != ['rdl']. Value: 1 [Best: 1]
 				 				* null != 'rdl'. Value: 1
 			 			* [null] != ['']. Value: 1 [Best: 1]
 				 				* null != ''. Value: 1
 			 			* [null] != ['phc']. Value: 1 [Best: 1]
 				 				* null != 'phc'. Value: 1
 			 			* [null] != ['yne']. Value: 1 [Best: 1]
 				 				* null != 'yne'. Value: 1
 	 	* Satisfy NOT NULL(TNUM1). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(TNUM2). Value: 0E-20 [Sum: 0]
 		 		* 94, allowNull: false. Value: 0
 	 	* Satisfy CHECK[TNUM2 > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 94 > 0. Value: 0E-20 [Distance: 0]*/ 

-- Negating "NOT NULL(TNUM1)" on table "TEST12649"
-- * Success: true
-- * Time: 2ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, 'gr');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(NULL, 77, '');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(TNUM2)" on table "TEST12649"
-- * Success: true
-- * Time: 1ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, 'eiq');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(0, NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[TNUM2 > 0]" on table "TEST12649"
-- * Success: true
-- * Time: 0ms 
INSERT INTO STAFF(SALARY, EMPNAME, GRADE, EMPNUM) VALUES(0, '', 0, 'oky');
INSERT INTO TEST12649(TNUM1, TNUM2, TCHAR) VALUES(0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

