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
-- Time to generate: 1683ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 219ms 
INSERT INTO Regions(id, code, capital, name) VALUES(1, 'a', '', 'a');
INSERT INTO Regions(id, code, capital, name) VALUES(0, '', '', '');
INSERT INTO Departments(id, code, capital, region, name) VALUES(1, 'a', 'a', '', 'a');
INSERT INTO Departments(id, code, capital, region, name) VALUES(0, '', '', '', '');
INSERT INTO Towns(id, code, article, name, department) VALUES(1, 'a', '', '', '');
INSERT INTO Towns(id, code, article, name, department) VALUES(0, '', '', '', '');
-- * Number of objective function evaluations: 85
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "Regions"
-- * Success: true
-- * Time: 17ms 
INSERT INTO Regions(id, code, capital, name) VALUES(NULL, 'b', '', 'b');
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "NOT NULL(code)" on table "Regions"
-- * Success: true
-- * Time: 10ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-1, NULL, '', 'b');
-- * Number of objective function evaluations: 13
-- * Number of restarts: 0

-- Negating "NOT NULL(capital)" on table "Regions"
-- * Success: true
-- * Time: 21ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-1, 'b', NULL, 'b');
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "Regions"
-- * Success: true
-- * Time: 18ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-1, 'b', '', NULL);
-- * Number of objective function evaluations: 23
-- * Number of restarts: 0

-- Negating "UNIQUE[id]" on table "Regions"
-- * Success: true
-- * Time: 13ms 
INSERT INTO Regions(id, code, capital, name) VALUES(0, 'b', '', 'b');
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "UNIQUE[code]" on table "Regions"
-- * Success: true
-- * Time: 10ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-1, '', '', 'b');
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "UNIQUE[name]" on table "Regions"
-- * Success: true
-- * Time: 9ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-1, 'b', '', '');
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[region]" on table "Departments"
-- * Success: true
-- * Time: 86ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-1, 'b', '', 'b');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-1, 'b', 'b', '`', 'b');
-- * Number of objective function evaluations: 83
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "Departments"
-- * Success: true
-- * Time: 149ms 
INSERT INTO Regions(id, code, capital, name) VALUES(46, 'mssu', 'jyvxgdd', 'aao');
INSERT INTO Departments(id, code, capital, region, name) VALUES(NULL, 'kt', 'b', 'mssu', 'ksynehqbi');
-- * Number of objective function evaluations: 203
-- * Number of restarts: 1

-- Negating "NOT NULL(code)" on table "Departments"
-- * Success: true
-- * Time: 73ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-46, 'bi', 'pxsnbhf', '`');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-99, NULL, 'xaj', '', 'tywey');
-- * Number of objective function evaluations: 143
-- * Number of restarts: 1

-- Negating "NOT NULL(capital)" on table "Departments"
-- * Success: true
-- * Time: 62ms 
INSERT INTO Regions(id, code, capital, name) VALUES(44, 'hqic', 'myyib', 'acamirp');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-33, 'vbai', NULL, 'a', 'ifm');
-- * Number of objective function evaluations: 174
-- * Number of restarts: 1

-- Negating "NOT NULL(region)" on table "Departments"
-- * Success: true
-- * Time: 83ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-60, 'uabq', '', 'arspfj');
INSERT INTO Departments(id, code, capital, region, name) VALUES(82, 'oobs', 'krkiqs', NULL, 'yparpusp');
-- * Number of objective function evaluations: 244
-- * Number of restarts: 1

-- Negating "NOT NULL(name)" on table "Departments"
-- * Success: true
-- * Time: 107ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-38, 'scge', 'hn', 'n');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-40, 'j', 'bgbpm', 'scge', NULL);
-- * Number of objective function evaluations: 219
-- * Number of restarts: 1

-- Negating "UNIQUE[id]" on table "Departments"
-- * Success: true
-- * Time: 33ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-74, 'cwnc', '', 'ba');
INSERT INTO Departments(id, code, capital, region, name) VALUES(1, 'vxa', 'pgmxfwagq', 'bi', 'psh');
-- * Number of objective function evaluations: 205
-- * Number of restarts: 1

-- Negating "UNIQUE[code]" on table "Departments"
-- * Success: true
-- * Time: 33ms 
INSERT INTO Regions(id, code, capital, name) VALUES(25, 'fgaw', 'upqli', 'nxarfglf');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-53, '', 'w', 'bi', 'gke');
-- * Number of objective function evaluations: 179
-- * Number of restarts: 1

-- Negating "UNIQUE[capital]" on table "Departments"
-- * Success: true
-- * Time: 58ms 
INSERT INTO Regions(id, code, capital, name) VALUES(20, 'ojar', 'dyuv', 'si');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-88, 'huhj', 'a', 'cwnc', 'oejfn');
-- * Number of objective function evaluations: 245
-- * Number of restarts: 1

-- Negating "UNIQUE[name]" on table "Departments"
-- * Success: true
-- * Time: 48ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-30, 'gnld', 'vvjjna', 'svose');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-71, 'd', 'qoruofwle', 'bi', 'a');
-- * Number of objective function evaluations: 249
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[department]" on table "Towns"
-- * Success: true
-- * Time: 46ms 
INSERT INTO Regions(id, code, capital, name) VALUES(31, 'kmad', 'jfeont', 'mnjdfjwov');
INSERT INTO Departments(id, code, capital, region, name) VALUES(53, 'b', 'fewittqna', '', 'b');
INSERT INTO Towns(id, code, article, name, department) VALUES(30, 'ikvnp', 'x', 'qdxhu', 'aioj');
-- * Number of objective function evaluations: 217
-- * Number of restarts: 1

-- Negating "NOT NULL(id)" on table "Towns"
-- * Success: true
-- * Time: 96ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-18, '`', 'rxxpsa', 'mgq');
INSERT INTO Departments(id, code, capital, region, name) VALUES(-92, 'jrlc', 'njqpgjyh', 'scge', 'mnroxjbe');
INSERT INTO Towns(id, code, article, name, department) VALUES(NULL, 'xafptj', 'tyx', 'k', 'jrlc');
-- * Number of objective function evaluations: 365
-- * Number of restarts: 1

-- Negating "NOT NULL(code)" on table "Towns"
-- * Success: true
-- * Time: 102ms 
INSERT INTO Regions(id, code, capital, name) VALUES(64, 'y', 'vbttrbu', 'sl');
INSERT INTO Departments(id, code, capital, region, name) VALUES(57, 'clse', 'sql', 'hqic', 'fm');
INSERT INTO Towns(id, code, article, name, department) VALUES(94, NULL, 'apughn', 'tkiajse', 'clse');
-- * Number of objective function evaluations: 330
-- * Number of restarts: 1

-- Negating "NOT NULL(name)" on table "Towns"
-- * Success: true
-- * Time: 83ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-5, 'gvmh', 'hm', 'hlce');
INSERT INTO Departments(id, code, capital, region, name) VALUES(94, 'o', 'b', 'bi', 'dreby');
INSERT INTO Towns(id, code, article, name, department) VALUES(35, 'gvmoboy', 'mvtql', NULL, 'o');
-- * Number of objective function evaluations: 327
-- * Number of restarts: 1

-- Negating "NOT NULL(department)" on table "Towns"
-- * Success: true
-- * Time: 92ms 
INSERT INTO Regions(id, code, capital, name) VALUES(18, 'vbx', '', 'wppwujg');
INSERT INTO Departments(id, code, capital, region, name) VALUES(2, 'lo', 'kf', 'bi', 'ld');
INSERT INTO Towns(id, code, article, name, department) VALUES(48, 'etrcqdocr', 'lcsqmvvbu', 'gkuyhcumm', NULL);
-- * Number of objective function evaluations: 318
-- * Number of restarts: 1

-- Negating "UNIQUE[id]" on table "Towns"
-- * Success: true
-- * Time: 141ms 
INSERT INTO Regions(id, code, capital, name) VALUES(6, 'ikqo', 'cjmvnv', 'tdupnwq');
INSERT INTO Departments(id, code, capital, region, name) VALUES(56, 'sk', 'ykn', 'ikqo', 'kk');
INSERT INTO Towns(id, code, article, name, department) VALUES(0, 'hoj', 'doyklv', 'habnvxebe', 'sk');
-- * Number of objective function evaluations: 314
-- * Number of restarts: 1

-- Negating "UNIQUE[code, department]" on table "Towns"
-- * Success: true
-- * Time: 74ms 
INSERT INTO Regions(id, code, capital, name) VALUES(-41, 'shvo', '', 'qepy');
INSERT INTO Departments(id, code, capital, region, name) VALUES(64, 'sch', 'gcoef', '', 'cmvgbmujv');
INSERT INTO Towns(id, code, article, name, department) VALUES(-53, 'a', 'dyqhsht', '', '');
-- * Number of objective function evaluations: 275
-- * Number of restarts: 1

