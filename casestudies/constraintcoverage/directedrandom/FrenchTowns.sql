/***************************************
 * Constraint coverage for FrenchTowns *
 ***************************************/
DROP TABLE IF EXISTS Towns;
DROP TABLE IF EXISTS Departments;
DROP TABLE IF EXISTS Regions;
CREATE TABLE Regions (
	id	INT	UNIQUE	NOT NULL,
	code	VARCHAR(4)	UNIQUE	NOT NULL,
	capital	VARCHAR(10)	NOT NULL,
	name	VARCHAR(100)	UNIQUE	NOT NULL
);
CREATE TABLE Departments (
	id	INT	UNIQUE	NOT NULL,
	code	VARCHAR(4)	UNIQUE	NOT NULL,
	capital	VARCHAR(10)	UNIQUE	NOT NULL,
	region	VARCHAR(4)	 REFERENCES Regions (code)	NOT NULL,
	name	VARCHAR(100)	UNIQUE	NOT NULL
);
CREATE TABLE Towns (
	id	INT	UNIQUE	NOT NULL,
	code	VARCHAR(10)	NOT NULL,
	article	VARCHAR(100),
	name	VARCHAR(100)	NOT NULL,
	department	VARCHAR(4)	 REFERENCES Departments (code)	NOT NULL,
	UNIQUE (code, department)
);
-- Coverage: 48/48 (100.00000%) 
-- Time to generate: 411ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 81ms 
INSERT INTO Regions(id, code, capital, name) VALUES(0, '', '', '');
INSERT INTO Regions(id, code, capital, name) VALUES(-52, 'c', '', 'jyvxgdd');
INSERT INTO Departments(id, code, capital, region, name) VALUES(0, '', '', '', '');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-60, 'ogha', 'mssuu', '', 'ynehqbi');
INSERT INTO Towns(id, code, article, name, department) VALUES(0, '', '', '', '');
INSERT INTO Towns(id, code, article, name, department) VALUES(33, 'll', '', '', '');
-- * Number of objective function evaluations: 7
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "Regions"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Regions(id, code, capital, name) VALUES(NULL, 'ojta', '', 'm');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(code)" on table "Regions"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Regions(id, code, capital, name) VALUES(94, NULL, '', 'irp');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(capital)" on table "Regions"
-- * Success: true
-- * Time: 3ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-33, 'vbai', NULL, 'xusrhhxt');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "Regions"
-- * Success: true
-- * Time: 3ms 
INSERT INTO Regions(id, code, capital, name) VALUES(9, 'bqm', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "UNIQUE[id]" on table "Regions"
-- * Success: true
-- * Time: 5ms 
INSERT INTO Regions(id, code, capital, name) VALUES(0, 'arsp', '', 'njlr');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "UNIQUE[code]" on table "Regions"
-- * Success: true
-- * Time: 3ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-53, '', '', 'cioob');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "UNIQUE[name]" on table "Regions"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Regions(id, code, capital, name) VALUES(50, 'krki', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[region]" on table "Departments"
-- * Success: true
-- * Time: 7ms 
INSERT INTO Regions(id, code, capital, name) VALUES(34, 'vuab', '', 'nyparp');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-96, 'drxh', 'anhtu', 'hkt', 'xnfi');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "Departments"
-- * Success: true
-- * Time: 12ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-50, 'if', '', 'ehsbcwnc');
INSERT INTO Departments(id, code, capital, region, name) VALUES(NULL, 'xfwa', 'b', '', 'yvxabof');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(code)" on table "Departments"
-- * Success: true
-- * Time: 6ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-46, 'xw', '', 'fdpshpalc');
INSERT INTO Departments(id, code, capital, region, name) VALUES(42, NULL, 'c', '', 'pq');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(capital)" on table "Departments"
-- * Success: true
-- * Time: 8ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-10, 'snxa', '', 'xrwaeoyid');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-81, 'sh', NULL, '', 'lunoojarr');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(region)" on table "Departments"
-- * Success: true
-- * Time: 12ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-87, 'keho', '', 'rusibin');
INSERT INTO Departments(id, code, capital, region, name) VALUES(91, 'hwuo', 'd', NULL, 'apfodfc');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "Departments"
-- * Success: true
-- * Time: 14ms 
INSERT INTO Regions(id, code, capital, name) VALUES(77, 'gddi', '', 'nldl');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-91, 'jjna', 'svose', '', NULL);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "UNIQUE[id]" on table "Departments"
-- * Success: true
-- * Time: 23ms 
INSERT INTO Regions(id, code, capital, name) VALUES(67, 'uofw', '', 'vubgv');
INSERT INTO Departments(id, code, capital, region, name) VALUES(0, 'dfos', 'gga', '', 'stv');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "UNIQUE[code]" on table "Departments"
-- * Success: true
-- * Time: 9ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-47, 'tqn', '', 'fxbykmad');
INSERT INTO Departments(id, code, capital, region, name) VALUES(70, '', 'feontfylk', '', 'fjwov');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "UNIQUE[capital]" on table "Departments"
-- * Success: true
-- * Time: 20ms 
INSERT INTO Regions(id, code, capital, name) VALUES(30, 'ikvn', '', 'sxjjbd');
INSERT INTO Departments(id, code, capital, region, name) VALUES(6, 'dpwg', '', '', 'aiojbta');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "UNIQUE[name]" on table "Departments"
-- * Success: true
-- * Time: 20ms 
INSERT INTO Regions(id, code, capital, name) VALUES(28, 'yhdk', '', 'eifqsg');
INSERT INTO Departments(id, code, capital, region, name) VALUES(28, 'sadc', 'dcdjoreb', '', '');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[department]" on table "Towns"
-- * Success: true
-- * Time: 15ms 
INSERT INTO Regions(id, code, capital, name) VALUES(37, 'kofx', '', 'tu');
INSERT INTO Departments(id, code, capital, region, name) VALUES(87, 'peer', 'ptj', '', 'tyx');
INSERT INTO Towns(id, code, article, name, department) VALUES(9, 'll', '', '', 'pcvu');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "Towns"
-- * Success: true
-- * Time: 29ms 
INSERT INTO Regions(id, code, capital, name) VALUES(43, 'qpap', '', 'vhaftk');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-42, 'ubov', 'l', '', 'trbuivuji');
INSERT INTO Towns(id, code, article, name, department) VALUES(NULL, 'jbbf', '', '', 'ogha');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(code)" on table "Towns"
-- * Success: true
-- * Time: 27ms 
INSERT INTO Regions(id, code, capital, name) VALUES(96, 'ebyl', '', 'gvmhmgxh');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-68, 'to', 'fo', '', 'pgxpx');
INSERT INTO Towns(id, code, article, name, department) VALUES(-10, NULL, '', '', '');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "Towns"
-- * Success: true
-- * Time: 28ms 
INSERT INTO Regions(id, code, capital, name) VALUES(83, 'plo', '', 'kf');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-49, 'nfxt', 'yemrygu', '', 'klea');
INSERT INTO Towns(id, code, article, name, department) VALUES(93, 'bawgtrv', '', NULL, '');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(department)" on table "Towns"
-- * Success: true
-- * Time: 24ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-11, 'ujg', '', 't');
INSERT INTO Departments(id, code, capital, region, name) VALUES(30, 'ekwc', 'p', '', 'ocrseaoo');
INSERT INTO Towns(id, code, article, name, department) VALUES(-63, '', '', '', NULL);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "UNIQUE[id]" on table "Towns"
-- * Success: true
-- * Time: 45ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-36, 'cu', '', 'je');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-95, 'rgoi', 'usm', '', 'ngk');
INSERT INTO Towns(id, code, article, name, department) VALUES(0, 'poqgtby', '', '', 'ogha');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "UNIQUE[code, department]" on table "Towns"
-- * Success: true
-- * Time: 11ms 
INSERT INTO Regions(id, code, capital, name) VALUES(51, 'x', '', 'hoj');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-18, 'tlcj', 'leumtd', '', 'iqv');
INSERT INTO Towns(id, code, article, name, department) VALUES(42, '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

