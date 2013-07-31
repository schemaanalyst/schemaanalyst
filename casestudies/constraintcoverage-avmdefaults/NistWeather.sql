/***************************************
 * Constraint coverage for NistWeather *
 ***************************************/
DROP TABLE IF EXISTS Stats;
DROP TABLE IF EXISTS Station;
CREATE TABLE Station (
	ID	INT	PRIMARY KEY,
	CITY	CHAR(20),
	STATE	CHAR(2),
	LAT_N	INT	NOT NULL,
	LONG_W	INT	NOT NULL,
	CHECK (LAT_N BETWEEN 0 AND 90),
	CHECK (LONG_W BETWEEN -180 AND 180)
);
CREATE TABLE Stats (
	ID	INT	 REFERENCES Station (ID),
	MONTH	INT	NOT NULL,
	TEMP_F	INT	NOT NULL,
	RAIN_I	INT	NOT NULL,
	PRIMARY KEY (ID, MONTH),
	CHECK (MONTH BETWEEN 1 AND 12),
	CHECK (TEMP_F BETWEEN 80 AND 150),
	CHECK (RAIN_I BETWEEN 0 AND 100)
);
-- Coverage: 26/26 (100.00000%) 
-- Time to generate: 674ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 192ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(1, '', '', 0, 0);
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(0, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(1, 1, 127, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 1, 127, 0);
-- * Number of objective function evaluations: 74
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "Station"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(0, '', '', 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(LAT_N)" on table "Station"
-- * Success: true
-- * Time: 10ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-1, '', '', NULL, 0);
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "NOT NULL(LONG_W)" on table "Station"
-- * Success: true
-- * Time: 13ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-1, '', '', 0, NULL);
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "CHECK[LAT_N BETWEEN 0 AND 90]" on table "Station"
-- * Success: true
-- * Time: 11ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-1, '', '', -1, 0);
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "CHECK[LONG_W BETWEEN -180 AND 180]" on table "Station"
-- * Success: true
-- * Time: 23ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-1, '', '', 0, 255);
-- * Number of objective function evaluations: 23
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID, MONTH]" on table "Stats"
-- * Success: true
-- * Time: 44ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-1, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 1, 127, 0);
-- * Number of objective function evaluations: 34
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[ID]" on table "Stats"
-- * Success: true
-- * Time: 128ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(46, 'phctgpyae', 'dd', 31, 9);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(92, 11, 88, 13);
-- * Number of objective function evaluations: 151
-- * Number of restarts: 1

-- Negating "NOT NULL(MONTH)" on table "Stats"
-- * Success: true
-- * Time: 47ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(67, '', 'ms', 46, 63);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(67, NULL, 80, 53);
-- * Number of objective function evaluations: 97
-- * Number of restarts: 1

-- Negating "NOT NULL(TEMP_F)" on table "Stats"
-- * Success: true
-- * Time: 46ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-84, 'hqbigqr', '', 30, 71);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(NULL, 10, NULL, 62);
-- * Number of objective function evaluations: 106
-- * Number of restarts: 1

-- Negating "NOT NULL(RAIN_I)" on table "Stats"
-- * Success: true
-- * Time: 38ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-57, 'ckg', 'rf', 56, 89);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(NULL, 9, 85, NULL);
-- * Number of objective function evaluations: 106
-- * Number of restarts: 1

-- Negating "CHECK[MONTH BETWEEN 1 AND 12]" on table "Stats"
-- * Success: true
-- * Time: 36ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(98, 'ldewhq', '', 7, 70);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 98, 93, 1);
-- * Number of objective function evaluations: 105
-- * Number of restarts: 1

-- Negating "CHECK[TEMP_F BETWEEN 80 AND 150]" on table "Stats"
-- * Success: true
-- * Time: 44ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-91, 'acamirp', 'hv', 37, -98);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(NULL, 11, 70, 27);
-- * Number of objective function evaluations: 121
-- * Number of restarts: 1

-- Negating "CHECK[RAIN_I BETWEEN 0 AND 100]" on table "Stats"
-- * Success: true
-- * Time: 41ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-39, 'hh', 'v', 21, 81);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(NULL, 11, 126, -2);
-- * Number of objective function evaluations: 119
-- * Number of restarts: 1

