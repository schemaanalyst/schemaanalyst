/***************************************
 * Constraint coverage for NistWeather *
 ***************************************/
DROP TABLE IF EXISTS Stats;
DROP TABLE IF EXISTS Station;
CREATE TABLE Station (
	ID	INT	CONSTRAINT null PRIMARY KEY,
	CITY	CHAR(20),
	STATE	CHAR(2),
	LAT_N	INT	CONSTRAINT null NOT NULL,
	LONG_W	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT null CHECK (LAT_N BETWEEN 0 AND 90),
	CONSTRAINT null CHECK (LONG_W BETWEEN SYMMETRIC 180 AND -180)
);
CREATE TABLE Stats (
	ID	INT	CONSTRAINT null  REFERENCES Station (ID),
	MONTH	INT	CONSTRAINT null NOT NULL,
	TEMP_F	INT	CONSTRAINT null NOT NULL,
	RAIN_I	INT	CONSTRAINT null NOT NULL,
	CONSTRAINT null PRIMARY KEY (ID, MONTH),
	CONSTRAINT null CHECK (MONTH BETWEEN 1 AND 12),
	CONSTRAINT null CHECK (TEMP_F BETWEEN 80 AND 150),
	CONSTRAINT null CHECK (RAIN_I BETWEEN 0 AND 100)
);
-- Coverage: 26/26 (100.00000%) 
-- Time to generate: 799ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 212ms 
-- * Number of objective function evaluations: 74
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID}" on table "Station"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{ID, MONTH}" on table "Stats"
-- * Success: true
-- * Time: 48ms 
-- * Number of objective function evaluations: 34
-- * Number of restarts: 0

-- Negating "CHECK[LAT_N BETWEEN 0 AND 90]" on table "Station"
-- * Success: true
-- * Time: 19ms 
-- * Number of objective function evaluations: 28
-- * Number of restarts: 1

-- Negating "CHECK[LONG_W BETWEEN 180 AND -180]" on table "Station"
-- * Success: true
-- * Time: 44ms 
-- * Number of objective function evaluations: 75
-- * Number of restarts: 1

-- Negating "CHECK[MONTH BETWEEN 1 AND 12]" on table "Stats"
-- * Success: true
-- * Time: 92ms 
-- * Number of objective function evaluations: 100
-- * Number of restarts: 1

-- Negating "CHECK[TEMP_F BETWEEN 80 AND 150]" on table "Stats"
-- * Success: true
-- * Time: 88ms 
-- * Number of objective function evaluations: 115
-- * Number of restarts: 1

-- Negating "CHECK[RAIN_I BETWEEN 0 AND 100]" on table "Stats"
-- * Success: true
-- * Time: 56ms 
-- * Number of objective function evaluations: 120
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{ID}" on table "Stats"
-- * Success: true
-- * Time: 70ms 
-- * Number of objective function evaluations: 145
-- * Number of restarts: 1

-- Negating "NOT NULL(LAT_N)" on table "Station"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 37
-- * Number of restarts: 1

-- Negating "NOT NULL(LONG_W)" on table "Station"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 46
-- * Number of restarts: 1

-- Negating "NOT NULL(MONTH)" on table "Stats"
-- * Success: true
-- * Time: 48ms 
-- * Number of objective function evaluations: 100
-- * Number of restarts: 1

-- Negating "NOT NULL(TEMP_F)" on table "Stats"
-- * Success: true
-- * Time: 40ms 
-- * Number of objective function evaluations: 96
-- * Number of restarts: 1

-- Negating "NOT NULL(RAIN_I)" on table "Stats"
-- * Success: true
-- * Time: 66ms 
-- * Number of objective function evaluations: 124
-- * Number of restarts: 1

