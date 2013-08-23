/*********************************
 * Constraint coverage for World *
 *********************************/
DROP TABLE IF EXISTS countrylanguage;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS city;
CREATE TABLE city (
	id	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	name	VARCHAR(100)	CONSTRAINT null NOT NULL,
	countrycode	CHAR(3)	CONSTRAINT null NOT NULL,
	district	VARCHAR(100)	CONSTRAINT null NOT NULL,
	population	INT	CONSTRAINT null NOT NULL
);
CREATE TABLE country (
	code	VARCHAR(3)	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	name	VARCHAR(100)	CONSTRAINT null NOT NULL,
	continent	VARCHAR(100)	CONSTRAINT null NOT NULL,
	region	VARCHAR(100)	CONSTRAINT null NOT NULL,
	surfacearea	INT	CONSTRAINT null NOT NULL,
	indepyear	INT,
	population	INT	CONSTRAINT null NOT NULL,
	lifeexpectancy	INT,
	gnp	INT,
	gnpold	INT,
	localname	VARCHAR(100)	CONSTRAINT null NOT NULL,
	governmentform	VARCHAR(100)	CONSTRAINT null NOT NULL,
	headofstate	VARCHAR(100),
	capital	INT	CONSTRAINT country_capital_fkey  REFERENCES city (id),
	code2	VARCHAR(2)	CONSTRAINT null NOT NULL,
	CONSTRAINT country_continent_check CHECK ((((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica')))
);
CREATE TABLE countrylanguage (
	countrycode	CHAR(3)	CONSTRAINT countrylanguage_countrycode_fkey  REFERENCES country (code)	CONSTRAINT null NOT NULL,
	language	VARCHAR(100)	CONSTRAINT null NOT NULL,
	isofficial	BOOLEAN	CONSTRAINT null NOT NULL,
	percentage	REAL	CONSTRAINT null NOT NULL,
	CONSTRAINT countrylanguage_pkey PRIMARY KEY (countrycode, language)
);
-- Coverage: 48/48 (100.00000%) 
-- Time to generate: 2625ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 642ms 
-- * Number of objective function evaluations: 258
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "city"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{code}" on table "country"
-- * Success: true
-- * Time: 50ms 
-- * Number of objective function evaluations: 78
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{countrycode, language}" on table "countrylanguage"
-- * Success: true
-- * Time: 268ms 
-- * Number of objective function evaluations: 436
-- * Number of restarts: 1

-- Negating "CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]" on table "country"
-- * Success: true
-- * Time: 56ms 
-- * Number of objective function evaluations: 198
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{capital}" on table "country"
-- * Success: true
-- * Time: 70ms 
-- * Number of objective function evaluations: 305
-- * Number of restarts: 1

-- Negating "FOREIGN KEY{countrycode}" on table "countrylanguage"
-- * Success: true
-- * Time: 91ms 
-- * Number of objective function evaluations: 289
-- * Number of restarts: 1

-- Negating "NOT NULL(id)" on table "city"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "city"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 21
-- * Number of restarts: 1

-- Negating "NOT NULL(countrycode)" on table "city"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 25
-- * Number of restarts: 1

-- Negating "NOT NULL(district)" on table "city"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 39
-- * Number of restarts: 1

-- Negating "NOT NULL(population)" on table "city"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 50
-- * Number of restarts: 1

-- Negating "NOT NULL(code)" on table "country"
-- * Success: true
-- * Time: 82ms 
-- * Number of objective function evaluations: 326
-- * Number of restarts: 1

-- Negating "NOT NULL(name)" on table "country"
-- * Success: true
-- * Time: 104ms 
-- * Number of objective function evaluations: 349
-- * Number of restarts: 1

-- Negating "NOT NULL(continent)" on table "country"
-- * Success: true
-- * Time: 36ms 
-- * Number of objective function evaluations: 177
-- * Number of restarts: 1

-- Negating "NOT NULL(region)" on table "country"
-- * Success: true
-- * Time: 75ms 
-- * Number of objective function evaluations: 328
-- * Number of restarts: 1

-- Negating "NOT NULL(surfacearea)" on table "country"
-- * Success: true
-- * Time: 104ms 
-- * Number of objective function evaluations: 413
-- * Number of restarts: 1

-- Negating "NOT NULL(population)" on table "country"
-- * Success: true
-- * Time: 87ms 
-- * Number of objective function evaluations: 354
-- * Number of restarts: 1

-- Negating "NOT NULL(localname)" on table "country"
-- * Success: true
-- * Time: 136ms 
-- * Number of objective function evaluations: 371
-- * Number of restarts: 1

-- Negating "NOT NULL(governmentform)" on table "country"
-- * Success: true
-- * Time: 105ms 
-- * Number of objective function evaluations: 345
-- * Number of restarts: 1

-- Negating "NOT NULL(code2)" on table "country"
-- * Success: true
-- * Time: 101ms 
-- * Number of objective function evaluations: 419
-- * Number of restarts: 1

-- Negating "NOT NULL(countrycode)" on table "countrylanguage"
-- * Success: true
-- * Time: 115ms 
-- * Number of objective function evaluations: 383
-- * Number of restarts: 1

-- Negating "NOT NULL(language)" on table "countrylanguage"
-- * Success: true
-- * Time: 136ms 
-- * Number of objective function evaluations: 420
-- * Number of restarts: 1

-- Negating "NOT NULL(isofficial)" on table "countrylanguage"
-- * Success: true
-- * Time: 183ms 
-- * Number of objective function evaluations: 546
-- * Number of restarts: 1

-- Negating "NOT NULL(percentage)" on table "countrylanguage"
-- * Success: true
-- * Time: 174ms 
-- * Number of objective function evaluations: 522
-- * Number of restarts: 1

