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
-- Coverage: 18/18 (100.00000%) 
-- Time to generate: 879ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 89ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(1, '', '', 0, 0, '1000-01-01');
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(0, '', '', 0, 0, '1000-01-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(1, 0, 0, '', '', '', '', 0, 0, 0, 0, '', '1000-01-01', '1000-01-01', '1000-01-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(0, 0, 0, '', '', '', '', 0, 0, 0, 0, '', '1000-01-01', '1000-01-01', '1000-01-01');
-- * Number of objective function evaluations: 46
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ekey]" on table "Exam"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(0, '', '', 0, 0, '1000-01-01');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "CHECK[score >= 0]" on table "Exam"
-- * Success: true
-- * Time: 6ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-1, '', '', 0, -1, '1000-01-01');
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "CHECK[score <= 100]" on table "Exam"
-- * Success: true
-- * Time: 9ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-1, '', '', 0, 127, '1000-01-01');
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[lkey]" on table "Examlog"
-- * Success: true
-- * Time: 4ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-1, '', '', 0, 0, '1000-01-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(0, 0, 0, '', '', '', '', 0, 0, 0, 0, '', '1000-01-01', '1000-01-01', '1000-01-01');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[ekey]" on table "Examlog"
-- * Success: true
-- * Time: 118ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(46, 'phctgpyae', 'ddanycpk', 55, 98, '2004-09-22');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(63, 67, 5, 'ynehqbi', 'abiivyvp', 'hfckgxrbj', 'ajvtywe', 92, 44, 35, 60, 'ic', '2004-08-18', '2006-05-24', '1990-02-01');
-- * Number of objective function evaluations: 104
-- * Number of restarts: 1

-- Negating "CHECK[scoreNEW >= 0]" on table "Examlog"
-- * Success: true
-- * Time: 214ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-4, 'tmghvba', 'u', 85, 67, '2012-08-10');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(-38, NULL, 54, 'wb', 'ifm', 'jlriarsp', 'ocioobsfj', -25, 36, -67, NULL, 'womhny', '2008-01-21', '2008-09-23', '2008-04-23');
-- * Number of objective function evaluations: 237
-- * Number of restarts: 1

-- Negating "CHECK[scoreNEW <= 100]" on table "Examlog"
-- * Success: true
-- * Time: 171ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-58, 'lcrkup', 'i', -9, 46, '2006-11-01');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(41, -58, -45, 'aeoyidcu', 'hgke', 'oojar', 'dyuv', 91, 9, 160, 57, 'nxrd', '2005-10-06', '2000-08-01', '1997-06-13');
-- * Number of objective function evaluations: 470
-- * Number of restarts: 2

-- Negating "CHECK[scoreOLD >= 0]" on table "Examlog"
-- * Success: true
-- * Time: 46ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-63, '', 'oejfn', -30, NULL, '2015-08-04');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(87, NULL, 70, 'itphsv', 'skhgddiu', 'uofwl', 'ubgv', -15, 48, 75, -42, 'ast', '2015-03-06', '2016-04-24', '2013-08-17');
-- * Number of objective function evaluations: 162
-- * Number of restarts: 1

-- Negating "CHECK[scoreOLD <= 100]" on table "Examlog"
-- * Success: true
-- * Time: 222ms 
INSERT INTO Exam(ekey, fn, ln, exam, score, timeEnter) VALUES(-88, 'gxhlceqg', 'vmo', -89, 18, '2019-02-16');
INSERT INTO Examlog(lkey, ekey, ekeyOLD, fnNEW, fnOLD, lnNEW, lnOLD, examNEW, examOLD, scoreNEW, scoreOLD, sqlAction, examtimeEnter, examtimeUpdate, timeEnter) VALUES(37, NULL, -51, 'mklea', 'iplo', 'kf', 'pba', 82, -52, 59, 102, '', '1998-10-06', '1993-08-05', '2003-06-15');
-- * Number of objective function evaluations: 895
-- * Number of restarts: 4

