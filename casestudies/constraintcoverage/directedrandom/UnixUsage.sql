/*************************************
 * Constraint coverage for UnixUsage *
 *************************************/
DROP TABLE IF EXISTS USAGE_HISTORY;
DROP TABLE IF EXISTS UNIX_COMMAND;
DROP TABLE IF EXISTS TRANSCRIPT;
DROP TABLE IF EXISTS USER_INFO;
DROP TABLE IF EXISTS RACE_INFO;
DROP TABLE IF EXISTS OFFICE_INFO;
DROP TABLE IF EXISTS COURSE_INFO;
DROP TABLE IF EXISTS DEPT_INFO;
CREATE TABLE DEPT_INFO (
	DEPT_ID	INT	PRIMARY KEY	NOT NULL,
	DEPT_NAME	VARCHAR(50)
);
CREATE TABLE COURSE_INFO (
	COURSE_ID	INT	PRIMARY KEY	NOT NULL,
	COURSE_NAME	VARCHAR(50),
	OFFERED_DEPT	INT	 REFERENCES DEPT_INFO (DEPT_ID),
	GRADUATE_LEVEL	SMALLINT
);
CREATE TABLE OFFICE_INFO (
	OFFICE_ID	INT	PRIMARY KEY	NOT NULL,
	OFFICE_NAME	VARCHAR(50),
	HAS_PRINTER	SMALLINT
);
CREATE TABLE RACE_INFO (
	RACE_CODE	INT	PRIMARY KEY	NOT NULL,
	RACE	VARCHAR(50)
);
CREATE TABLE USER_INFO (
	USER_ID	VARCHAR(50)	PRIMARY KEY	NOT NULL,
	FIRST_NAME	VARCHAR(50),
	LAST_NAME	VARCHAR(50),
	SEX	VARCHAR(1),
	DEPT_ID	INT	 REFERENCES DEPT_INFO (DEPT_ID),
	OFFICE_ID	INT	 REFERENCES OFFICE_INFO (OFFICE_ID),
	GRADUATE	SMALLINT,
	RACE	INT	 REFERENCES RACE_INFO (RACE_CODE),
	PASSWORD	VARCHAR(50)	NOT NULL,
	YEARS_USING_UNIX	INT,
	ENROLL_DATE	DATE
);
CREATE TABLE TRANSCRIPT (
	USER_ID	VARCHAR(50)	 REFERENCES USER_INFO (USER_ID)	NOT NULL,
	COURSE_ID	INT	 REFERENCES COURSE_INFO (COURSE_ID)	NOT NULL,
	SCORE	INT,
	PRIMARY KEY (USER_ID, COURSE_ID)
);
CREATE TABLE UNIX_COMMAND (
	UNIX_COMMAND	VARCHAR(50)	PRIMARY KEY	NOT NULL,
	CATEGORY	VARCHAR(50)
);
CREATE TABLE USAGE_HISTORY (
	USER_ID	VARCHAR(50)	 REFERENCES USER_INFO (USER_ID)	NOT NULL,
	SESSION_ID	INT,
	LINE_NO	INT,
	COMMAND_SEQ	INT,
	COMMAND	VARCHAR(50)
);
-- Coverage: 47/48 (97.91667%) 
-- Time to generate: 17488ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 551ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(0, '');
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-52, '');
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(0, '', 0, 0);
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(10, '', 0, 0);
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(0, '', 0);
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(-34, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(0, '');
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(96, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('e', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
INSERT INTO TRANSCRIPT(USER_ID, COURSE_ID, SCORE) VALUES('', 0, 0);
INSERT INTO TRANSCRIPT(USER_ID, COURSE_ID, SCORE) VALUES('e', 0, 0);
INSERT INTO UNIX_COMMAND(UNIX_COMMAND, CATEGORY) VALUES('', '');
INSERT INTO UNIX_COMMAND(UNIX_COMMAND, CATEGORY) VALUES('oaks', '');
INSERT INTO USAGE_HISTORY(USER_ID, SESSION_ID, LINE_NO, COMMAND_SEQ, COMMAND) VALUES('', 0, 0, 0, '');
INSERT INTO USAGE_HISTORY(USER_ID, SESSION_ID, LINE_NO, COMMAND_SEQ, COMMAND) VALUES('', 0, 0, 0, '');
-- * Number of objective function evaluations: 67
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[DEPT_ID]" on table "DEPT_INFO"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(0, '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(DEPT_ID)" on table "DEPT_INFO"
-- * Success: true
-- * Time: 0ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[COURSE_ID]" on table "COURSE_INFO"
-- * Success: true
-- * Time: 2ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-50, '');
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(0, '', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[OFFERED_DEPT]" on table "COURSE_INFO"
-- * Success: true
-- * Time: 2ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-90, '');
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(-22, '', -18, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(COURSE_ID)" on table "COURSE_INFO"
-- * Success: true
-- * Time: 2ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-39, '');
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(NULL, '', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[OFFICE_ID]" on table "OFFICE_INFO"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(0, '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(OFFICE_ID)" on table "OFFICE_INFO"
-- * Success: true
-- * Time: 0ms 
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[RACE_CODE]" on table "RACE_INFO"
-- * Success: true
-- * Time: 1ms 
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(0, '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(RACE_CODE)" on table "RACE_INFO"
-- * Success: true
-- * Time: 0ms 
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[USER_ID]" on table "USER_INFO"
-- * Success: true
-- * Time: 6ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(42, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(16, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(-18, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[DEPT_ID]" on table "USER_INFO"
-- * Success: true
-- * Time: 5ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(15, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(-98, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(-10, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('vporiqv', '', '', '', -80, 0, 0, 0, '', 0, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[OFFICE_ID]" on table "USER_INFO"
-- * Success: true
-- * Time: 12ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-88, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(14, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(42, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('yk', '', '', '', 0, 75, 0, 0, '', 0, '1000-01-01');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[RACE]" on table "USER_INFO"
-- * Success: false
-- * Time: 16892ms 
-- INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(48, '');
-- INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(35, '', 0);
-- INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(-94, '');
-- INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('ed', '', '', '', 0, 0, 0, NULL, '', 0, '1000-01-01');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except FOREIGN KEY[RACE]. Value: 0.31578947368421052632 [Sum: 0.46153846153846153847]
 	 	* Satisfy PRIMARY KEY[DEPT_ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [48] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 48 != 0. Value: 0E-20 [Distance: 0]
 			 			* [48] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* 48 != -52. Value: 0E-20 [Distance: 0]
 			 			* [48] != [-50]. Value: 0E-20 [Best: 0E-20]
 				 				* 48 != -50. Value: 0E-20 [Distance: 0]
 			 			* [48] != [-90]. Value: 0E-20 [Best: 0E-20]
 				 				* 48 != -90. Value: 0E-20 [Distance: 0]
 			 			* [48] != [-39]. Value: 0E-20 [Best: 0E-20]
 				 				* 48 != -39. Value: 0E-20 [Distance: 0]
 			 			* [48] != [42]. Value: 0E-20 [Best: 0E-20]
 				 				* 48 != 42. Value: 0E-20 [Distance: 0]
 			 			* [48] != [15]. Value: 0E-20 [Best: 0E-20]
 				 				* 48 != 15. Value: 0E-20 [Distance: 0]
 			 			* [48] != [-88]. Value: 0E-20 [Best: 0E-20]
 				 				* 48 != -88. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(DEPT_ID). Value: 0E-20 [Sum: 0]
 		 		* 48, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[OFFICE_ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [35] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 35 != 0. Value: 0E-20 [Distance: 0]
 			 			* [35] != [-34]. Value: 0E-20 [Best: 0E-20]
 				 				* 35 != -34. Value: 0E-20 [Distance: 0]
 			 			* [35] != [16]. Value: 0E-20 [Best: 0E-20]
 				 				* 35 != 16. Value: 0E-20 [Distance: 0]
 			 			* [35] != [-98]. Value: 0E-20 [Best: 0E-20]
 				 				* 35 != -98. Value: 0E-20 [Distance: 0]
 			 			* [35] != [14]. Value: 0E-20 [Best: 0E-20]
 				 				* 35 != 14. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(OFFICE_ID). Value: 0E-20 [Sum: 0]
 		 		* 35, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[RACE_CODE]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-94] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -94 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-94] != [96]. Value: 0E-20 [Best: 0E-20]
 				 				* -94 != 96. Value: 0E-20 [Distance: 0]
 			 			* [-94] != [-18]. Value: 0E-20 [Best: 0E-20]
 				 				* -94 != -18. Value: 0E-20 [Distance: 0]
 			 			* [-94] != [-10]. Value: 0E-20 [Best: 0E-20]
 				 				* -94 != -10. Value: 0E-20 [Distance: 0]
 			 			* [-94] != [42]. Value: 0E-20 [Best: 0E-20]
 				 				* -94 != 42. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(RACE_CODE). Value: 0E-20 [Sum: 0]
 		 		* -94, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[USER_ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['ed'] != ['']. Value: 0 [Best: 0]
 				 				* 'ed' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 			 			* ['ed'] != ['e']. Value: 0 [Best: 0]
 				 				* 'ed' != 'e'. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy FOREIGN KEY[DEPT_ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [48]. Value: 0.49494949494949494950 [Sum: 0.98000000000000000000]
 				 				* 0 = 48. Value: 0.98000000000000000000 [Distance: 49]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-52]. Value: 0.49532710280373831776 [Sum: 0.98148148148148148149]
 				 				* 0 = -52. Value: 0.98148148148148148149 [Distance: 53]
 			 			* [0] = [-50]. Value: 0.49514563106796116506 [Sum: 0.98076923076923076924]
 				 				* 0 = -50. Value: 0.98076923076923076924 [Distance: 51]
 			 			* [0] = [-90]. Value: 0.49726775956284153006 [Sum: 0.98913043478260869566]
 				 				* 0 = -90. Value: 0.98913043478260869566 [Distance: 91]
 			 			* [0] = [-39]. Value: 0.49382716049382716050 [Sum: 0.97560975609756097561]
 				 				* 0 = -39. Value: 0.97560975609756097561 [Distance: 40]
 			 			* [0] = [42]. Value: 0.49425287356321839081 [Sum: 0.97727272727272727273]
 				 				* 0 = 42. Value: 0.97727272727272727273 [Distance: 43]
 			 			* [0] = [15]. Value: 0.48484848484848484849 [Sum: 0.94117647058823529412]
 				 				* 0 = 15. Value: 0.94117647058823529412 [Distance: 16]
 			 			* [0] = [-88]. Value: 0.49720670391061452514 [Sum: 0.98888888888888888889]
 				 				* 0 = -88. Value: 0.98888888888888888889 [Distance: 89]
 	 	* Satisfy FOREIGN KEY[OFFICE_ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [35]. Value: 0.49315068493150684932 [Sum: 0.97297297297297297298]
 				 				* 0 = 35. Value: 0.97297297297297297298 [Distance: 36]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-34]. Value: 0.49295774647887323944 [Sum: 0.97222222222222222223]
 				 				* 0 = -34. Value: 0.97222222222222222223 [Distance: 35]
 			 			* [0] = [16]. Value: 0.48571428571428571429 [Sum: 0.94444444444444444445]
 				 				* 0 = 16. Value: 0.94444444444444444445 [Distance: 17]
 			 			* [0] = [-98]. Value: 0.49748743718592964825 [Sum: 0.99000000000000000000]
 				 				* 0 = -98. Value: 0.99000000000000000000 [Distance: 99]
 			 			* [0] = [14]. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 0 = 14. Value: 0.93750000000000000000 [Distance: 15]
 	 	* Violate FOREIGN KEY[RACE]. Value: 0.46153846153846153847 [Sum: 0.85714285714285714286]
 		 		* Evaluating row with reference rows. Value: 0.85714285714285714286 [Sum: 6]
 			 			* [null] != [-94]. Value: 1 [Best: 1]
 				 				* null != -94. Value: 1
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 			 			* [null] != [96]. Value: 1 [Best: 1]
 				 				* null != 96. Value: 1
 			 			* [null] != [-18]. Value: 1 [Best: 1]
 				 				* null != -18. Value: 1
 			 			* [null] != [-10]. Value: 1 [Best: 1]
 				 				* null != -10. Value: 1
 			 			* [null] != [42]. Value: 1 [Best: 1]
 				 				* null != 42. Value: 1
 	 	* Satisfy NOT NULL(USER_ID). Value: 0E-20 [Sum: 0]
 		 		* 'ed', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(PASSWORD). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(USER_ID)" on table "USER_INFO"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-37, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(-37, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(-37, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES(NULL, '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(PASSWORD)" on table "USER_INFO"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(4, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(28, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(-67, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('a', '', '', '', 0, 0, 0, 0, NULL, 0, '1000-01-01');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[USER_ID, COURSE_ID]" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-58, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(-65, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(79, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('kjqnhu', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(-23, '', 0, 0);
INSERT INTO TRANSCRIPT(USER_ID, COURSE_ID, SCORE) VALUES('', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[USER_ID]" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-49, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(12, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(2, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('bbvsdpjr', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(-89, '', 0, 0);
INSERT INTO TRANSCRIPT(USER_ID, COURSE_ID, SCORE) VALUES('fonflqvs', -89, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[COURSE_ID]" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(76, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(-48, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(14, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('dwilmss', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(31, '', 0, 0);
INSERT INTO TRANSCRIPT(USER_ID, COURSE_ID, SCORE) VALUES('e', -50, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(USER_ID)" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 4ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-89, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(35, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(-39, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('bgcgellxk', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(-31, '', 0, 0);
INSERT INTO TRANSCRIPT(USER_ID, COURSE_ID, SCORE) VALUES(NULL, 0, 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(COURSE_ID)" on table "TRANSCRIPT"
-- * Success: true
-- * Time: 2ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-13, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(4, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(-65, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('wkb', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
INSERT INTO COURSE_INFO(COURSE_ID, COURSE_NAME, OFFERED_DEPT, GRADUATE_LEVEL) VALUES(-85, '', 0, 0);
INSERT INTO TRANSCRIPT(USER_ID, COURSE_ID, SCORE) VALUES('', NULL, 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[UNIX_COMMAND]" on table "UNIX_COMMAND"
-- * Success: true
-- * Time: 1ms 
INSERT INTO UNIX_COMMAND(UNIX_COMMAND, CATEGORY) VALUES('', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(UNIX_COMMAND)" on table "UNIX_COMMAND"
-- * Success: true
-- * Time: 0ms 
INSERT INTO UNIX_COMMAND(UNIX_COMMAND, CATEGORY) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[USER_ID]" on table "USAGE_HISTORY"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(30, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(64, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(-75, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('uisnowrbv', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
INSERT INTO USAGE_HISTORY(USER_ID, SESSION_ID, LINE_NO, COMMAND_SEQ, COMMAND) VALUES('flr', 0, 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(USER_ID)" on table "USAGE_HISTORY"
-- * Success: true
-- * Time: 1ms 
INSERT INTO DEPT_INFO(DEPT_ID, DEPT_NAME) VALUES(-12, '');
INSERT INTO OFFICE_INFO(OFFICE_ID, OFFICE_NAME, HAS_PRINTER) VALUES(-60, '', 0);
INSERT INTO RACE_INFO(RACE_CODE, RACE) VALUES(-100, '');
INSERT INTO USER_INFO(USER_ID, FIRST_NAME, LAST_NAME, SEX, DEPT_ID, OFFICE_ID, GRADUATE, RACE, PASSWORD, YEARS_USING_UNIX, ENROLL_DATE) VALUES('gbc', '', '', '', 0, 0, 0, 0, '', 0, '1000-01-01');
INSERT INTO USAGE_HISTORY(USER_ID, SESSION_ID, LINE_NO, COMMAND_SEQ, COMMAND) VALUES(NULL, 0, 0, 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

