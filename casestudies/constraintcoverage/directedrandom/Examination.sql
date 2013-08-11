/***************************************
 * Constraint coverage for Examination *
 ***************************************/
DROP TABLE IF EXISTS Examlog;
DROP TABLE IF EXISTS Exam;
CREATE TABLE Exam (
	ekey	INT	PRIMARY KEY,
	fn	VARCHAR(15),
	ln	VARCHAR(30),
	exam	INT,
	score	INT,
	timeEnter	DATE,
	CHECK (score >= 0),
	CHECK (score <= 100)
);
CREATE TABLE Examlog (
	lkey	INT	PRIMARY KEY,
	ekey	INT	 REFERENCES Exam (ekey),
	ekeyOLD	INT,
	fnNEW	VARCHAR(15),
	fnOLD	VARCHAR(15),
	lnNEW	VARCHAR(30),
	lnOLD	VARCHAR(30),
	examNEW	INT,
	examOLD	INT,
	scoreNEW	INT,
	scoreOLD	INT,
	sqlAction	VARCHAR(15),
	examtimeEnter	DATE,
	examtimeUpdate	DATE,
	timeEnter	DATE,
	CHECK (scoreNEW >= 0),
	CHECK (scoreNEW <= 100),
	CHECK (scoreOLD >= 0),
	CHECK (scoreOLD <= 100)
);
-- Coverage: 15/18 (83.33333%) 
-- Time to generate: 18830ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 20ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(0, '', '', 0, 0, '1000-01-01');
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-52, '', '', 0, 0, '1000-01-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(0, 0, 0, '', '', '', '', 0, 0, 0, 0, '', '1000-01-01', '1000-01-01', '1000-01-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(10, 0, 0, '', '', '', '', 0, 0, 0, 0, '', '1000-01-01', '1000-01-01', '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ekey]" on table "Exam"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(0, '', '', 0, 0, '1000-01-01');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "CHECK[score >= 0]" on table "Exam"
-- * Success: true
-- * Time: 6ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-34, '', '', 0, -75, '1000-01-01');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "CHECK[score <= 100]" on table "Exam"
-- * Success: false
-- * Time: 2320ms 
-- INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-96, '', '', 0, 92, '1000-01-01');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[score <= 100]. Value: 0.32142857142857142858 [Sum: 0.47368421052631578948]
 	 	* Satisfy PRIMARY KEY[ekey]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-96] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -96 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-96] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* -96 != -52. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[score >= 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 92 >= 0. Value: 0E-20 [Distance: 0]
 	 	* Violate CHECK[score <= 100]. Value: 0.47368421052631578948 [Sum: 0.90000000000000000000]
 		 		* 92 > 100. Value: 0.90000000000000000000 [Distance: 9]*/ 

-- Negating "PRIMARY KEY[lkey]" on table "Examlog"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-48, '', '', 0, 0, '1000-01-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(0, 0, 0, '', '', '', '', 0, 0, 0, 0, '', '1000-01-01', '1000-01-01', '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[ekey]" on table "Examlog"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-63, '', '', 0, 0, '1000-01-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(-55, -96, 0, '', '', '', '', 0, 0, 0, 0, '', '1000-01-01', '1000-01-01', '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[scoreNEW >= 0]" on table "Examlog"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(72, '', '', 0, 0, '1000-01-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(8, 0, 0, '', '', '', '', 0, 0, -30, 0, '', '1000-01-01', '1000-01-01', '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[scoreNEW <= 100]" on table "Examlog"
-- * Success: false
-- * Time: 8100ms 
-- INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-99, '', '', 0, 0, '1000-01-01');
-- INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(39, 0, 0, '', '', '', '', 0, 0, 93, 0, '', '1000-01-01', '1000-01-01', '1000-01-01');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[scoreNEW <= 100]. Value: 0.32000000000000000001 [Sum: 0.47058823529411764706]
 	 	* Satisfy PRIMARY KEY[ekey]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-99] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -99 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-99] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* -99 != -52. Value: 0E-20 [Distance: 0]
 			 			* [-99] != [-48]. Value: 0E-20 [Best: 0E-20]
 				 				* -99 != -48. Value: 0E-20 [Distance: 0]
 			 			* [-99] != [-63]. Value: 0E-20 [Best: 0E-20]
 				 				* -99 != -63. Value: 0E-20 [Distance: 0]
 			 			* [-99] != [72]. Value: 0E-20 [Best: 0E-20]
 				 				* -99 != 72. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[score >= 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 >= 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[score <= 100]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 <= 100. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[lkey]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [39] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 39 != 0. Value: 0E-20 [Distance: 0]
 			 			* [39] != [10]. Value: 0E-20 [Best: 0E-20]
 				 				* 39 != 10. Value: 0E-20 [Distance: 0]
 	 	* Satisfy FOREIGN KEY[ekey]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [-99]. Value: 0.49751243781094527364 [Sum: 0.99009900990099009901]
 				 				* 0 = -99. Value: 0.99009900990099009901 [Distance: 100]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-52]. Value: 0.49532710280373831776 [Sum: 0.98148148148148148149]
 				 				* 0 = -52. Value: 0.98148148148148148149 [Distance: 53]
 			 			* [0] = [-48]. Value: 0.49494949494949494950 [Sum: 0.98000000000000000000]
 				 				* 0 = -48. Value: 0.98000000000000000000 [Distance: 49]
 			 			* [0] = [-63]. Value: 0.49612403100775193799 [Sum: 0.98461538461538461539]
 				 				* 0 = -63. Value: 0.98461538461538461539 [Distance: 64]
 			 			* [0] = [72]. Value: 0.49659863945578231293 [Sum: 0.98648648648648648649]
 				 				* 0 = 72. Value: 0.98648648648648648649 [Distance: 73]
 	 	* Satisfy CHECK[scoreNEW >= 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 93 >= 0. Value: 0E-20 [Distance: 0]
 	 	* Violate CHECK[scoreNEW <= 100]. Value: 0.47058823529411764706 [Sum: 0.88888888888888888889]
 		 		* 93 > 100. Value: 0.88888888888888888889 [Distance: 8]
 	 	* Satisfy CHECK[scoreOLD >= 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 >= 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[scoreOLD <= 100]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 <= 100. Value: 0E-20 [Distance: 0]*/ 

-- Negating "CHECK[scoreOLD >= 0]" on table "Examlog"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-51, '', '', 0, 0, '1000-01-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(-41, 0, 0, '', '', '', '', 0, 0, 0, -73, '', '1000-01-01', '1000-01-01', '1000-01-01');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "CHECK[scoreOLD <= 100]" on table "Examlog"
-- * Success: false
-- * Time: 8379ms 
-- INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(77, '', '', 0, 0, '1000-01-01');
-- INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(80, 0, 0, '', '', '', '', 0, 0, 0, NULL, '', '1000-01-01', '1000-01-01', '1000-01-01');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[scoreOLD <= 100]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Satisfy PRIMARY KEY[ekey]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [77] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 77 != 0. Value: 0E-20 [Distance: 0]
 			 			* [77] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* 77 != -52. Value: 0E-20 [Distance: 0]
 			 			* [77] != [-48]. Value: 0E-20 [Best: 0E-20]
 				 				* 77 != -48. Value: 0E-20 [Distance: 0]
 			 			* [77] != [-63]. Value: 0E-20 [Best: 0E-20]
 				 				* 77 != -63. Value: 0E-20 [Distance: 0]
 			 			* [77] != [72]. Value: 0E-20 [Best: 0E-20]
 				 				* 77 != 72. Value: 0E-20 [Distance: 0]
 			 			* [77] != [-51]. Value: 0E-20 [Best: 0E-20]
 				 				* 77 != -51. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[score >= 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 >= 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[score <= 100]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 <= 100. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[lkey]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [80] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 80 != 0. Value: 0E-20 [Distance: 0]
 			 			* [80] != [10]. Value: 0E-20 [Best: 0E-20]
 				 				* 80 != 10. Value: 0E-20 [Distance: 0]
 	 	* Satisfy FOREIGN KEY[ekey]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [77]. Value: 0.49681528662420382166 [Sum: 0.98734177215189873418]
 				 				* 0 = 77. Value: 0.98734177215189873418 [Distance: 78]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-52]. Value: 0.49532710280373831776 [Sum: 0.98148148148148148149]
 				 				* 0 = -52. Value: 0.98148148148148148149 [Distance: 53]
 			 			* [0] = [-48]. Value: 0.49494949494949494950 [Sum: 0.98000000000000000000]
 				 				* 0 = -48. Value: 0.98000000000000000000 [Distance: 49]
 			 			* [0] = [-63]. Value: 0.49612403100775193799 [Sum: 0.98461538461538461539]
 				 				* 0 = -63. Value: 0.98461538461538461539 [Distance: 64]
 			 			* [0] = [72]. Value: 0.49659863945578231293 [Sum: 0.98648648648648648649]
 				 				* 0 = 72. Value: 0.98648648648648648649 [Distance: 73]
 			 			* [0] = [-51]. Value: 0.49523809523809523810 [Sum: 0.98113207547169811321]
 				 				* 0 = -51. Value: 0.98113207547169811321 [Distance: 52]
 	 	* Satisfy CHECK[scoreNEW >= 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 >= 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[scoreNEW <= 100]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 <= 100. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[scoreOLD >= 0]. Value: 0E-20 [Sum: 0]
 		 		* null >= 0. Value: 0
 	 	* Violate CHECK[scoreOLD <= 100]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* null > 100. Value: 1*/ 

