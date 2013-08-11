/*********************************
 * Constraint coverage for World *
 *********************************/
DROP TABLE IF EXISTS countrylanguage;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS city;
CREATE TABLE city (
	id	INT	PRIMARY KEY	NOT NULL,
	name	VARCHAR(100)	NOT NULL,
	countrycode	CHAR(3)	NOT NULL,
	district	VARCHAR(100)	NOT NULL,
	population	INT	NOT NULL
);
CREATE TABLE country (
	code	VARCHAR(3)	PRIMARY KEY	NOT NULL,
	name	VARCHAR(100)	NOT NULL,
	continent	VARCHAR(100)	NOT NULL,
	region	VARCHAR(100)	NOT NULL,
	surfacearea	INT	NOT NULL,
	indepyear	INT,
	population	INT	NOT NULL,
	lifeexpectancy	INT,
	gnp	INT,
	gnpold	INT,
	localname	VARCHAR(100)	NOT NULL,
	governmentform	VARCHAR(100)	NOT NULL,
	headofstate	VARCHAR(100),
	capital	INT	CONSTRAINT country_capital_fkey  REFERENCES city (id),
	code2	VARCHAR(2)	NOT NULL,
	CONSTRAINT country_continent_check CHECK ((((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica')))
);
CREATE TABLE countrylanguage (
	countrycode	CHAR(3)	CONSTRAINT countrylanguage_countrycode_fkey  REFERENCES country (code)	NOT NULL,
	language	VARCHAR(100)	NOT NULL,
	isofficial	BOOLEAN	NOT NULL,
	percentage	REAL	NOT NULL,
	CONSTRAINT countrylanguage_pkey PRIMARY KEY (countrycode, language)
);
-- Coverage: 48/48 (100.00000%) 
-- Time to generate: 2031ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 552ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(1, '', '', '', 0);
INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', 0);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('a', '', 'Asia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'Asia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('a', '', FALSE, 0);
INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('', '', FALSE, 0);
-- * Number of objective function evaluations: 258
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "city"
-- * Success: true
-- * Time: 0ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(id)" on table "city"
-- * Success: true
-- * Time: 0ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(NULL, '', '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "city"
-- * Success: true
-- * Time: 1ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-1, NULL, '', '', 0);
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(countrycode)" on table "city"
-- * Success: true
-- * Time: 0ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-1, '', NULL, '', 0);
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(district)" on table "city"
-- * Success: true
-- * Time: 1ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-1, '', '', NULL, 0);
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "NOT NULL(population)" on table "city"
-- * Success: true
-- * Time: 1ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-1, '', '', '', NULL);
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[code]" on table "country"
-- * Success: true
-- * Time: 34ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-1, '', '', '', 0);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'Asia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- * Number of objective function evaluations: 78
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[capital]" on table "country"
-- * Success: true
-- * Time: 98ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(46, 'phctgpyae', 'dda', 'ao', -50);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('kt', '', 'Asia', 'synehq', -88, -29, -46, 33, 37, 63, 'vy', 'nbhfckg', 'rf', 56, 'tp');
-- * Number of objective function evaluations: 262
-- * Number of restarts: 1

-- Negating "NOT NULL(code)" on table "country"
-- * Success: true
-- * Time: 125ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(22, 'tywey', '', 'qullv', 0);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES(NULL, 'bam', 'Africa', 'baicvahnw', -45, 74, 21, 81, -92, 29, 'fmf', 'lr', '', NULL, 'rs');
-- * Number of objective function evaluations: 328
-- * Number of restarts: 1

-- Negating "NOT NULL(name)" on table "country"
-- * Success: true
-- * Time: 114ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(21, 'ocioobsfj', 'kiq', 'vuabq', 22);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('par', NULL, 'Asia', 'qoanhtubg', -92, 24, 3, -61, -36, -55, 'ehsbcwnc', '', 'b', NULL, 'vx');
-- * Number of objective function evaluations: 353
-- * Number of restarts: 1

-- Negating "NOT NULL(continent)" on table "country"
-- * Success: true
-- * Time: 45ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-99, 'pgmxfwagq', 'wix', 'psh', 25);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('fga', 'kupqligs', NULL, 'waeoyid', -81, 63, 33, -15, -5, 65, 'pvd', 'a', 'ydyuvxnqt', NULL, 'xr');
-- * Number of objective function evaluations: 224
-- * Number of restarts: 1

-- Negating "NOT NULL(region)" on table "country"
-- * Success: true
-- * Time: 80ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-76, 'apfodfc', 'keh', 'jhwu', 18);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('huq', 'dlb', 'Oceania', NULL, -26, 80, 28, 17, 39, 62, '', 'fwlemtq', 'ks', NULL, '');
-- * Number of objective function evaluations: 370
-- * Number of restarts: 1

-- Negating "NOT NULL(surfacearea)" on table "country"
-- * Success: true
-- * Time: 57ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(45, '', 'stv', 'gga', -47);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('tqn', 'fxbykmad', 'Oceania', 'mnjdfjwov', NULL, -1, -17, 33, -97, -68, 'lw', 'jbdnxa', 'u', NULL, '');
-- * Number of objective function evaluations: 370
-- * Number of restarts: 1

-- Negating "NOT NULL(population)" on table "country"
-- * Success: true
-- * Time: 54ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-92, 'k', 'njq', 'gqhhorm', -66);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('vmn', 'gqddc', 'Africa', 'gqpeer', 33, 42, NULL, 92, 56, 99, 'qk', 'jrlctu', 'tnnogp', NULL, 'hf');
-- * Number of objective function evaluations: 424
-- * Number of restarts: 1

-- Negating "NOT NULL(localname)" on table "country"
-- * Success: true
-- * Time: 64ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-4, 'symslk', 'rbu', 'sl', 94);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('irk', 'pap', 'Africa', 'jbbf', -22, -82, -7, 44, -68, 94, NULL, '', 'pgxpx', NULL, 'eb');
-- * Number of objective function evaluations: 368
-- * Number of restarts: 1

-- Negating "NOT NULL(governmentform)" on table "country"
-- * Success: true
-- * Time: 96ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(72, 'cgvmhmgx', 'o', 'ce', 35);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('gvm', 'xt', 'Africa', 'klea', 83, 1, -88, 52, -90, -17, 'jpba', NULL, 'r', 72, 'v');
-- * Number of objective function evaluations: 387
-- * Number of restarts: 1

-- Negating "NOT NULL(code2)" on table "country"
-- * Success: true
-- * Time: 66ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-65, 'wppwujg', 't', 'aekwc', 89);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('crs', 'c', 'Asia', 'fngk', -36, -84, -16, -16, -26, -67, 'poqgtby', 'ucdpakk', 'oikq', NULL, NULL);
-- * Number of objective function evaluations: 405
-- * Number of restarts: 1

-- Negating "CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]" on table "country"
-- * Success: true
-- * Time: 26ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-2, 'eum', 'ori', 'nwqx', -85);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('b', 'obdoy', 'o', 'vuba', 19, 35, 79, -94, 56, 65, 'd', 'kug', 'cha', NULL, 'oe');
-- * Number of objective function evaluations: 197
-- * Number of restarts: 1

-- Negating "PRIMARY KEY[countrycode, language]" on table "countrylanguage"
-- * Success: true
-- * Time: 88ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-72, 'jj', 'tgo', 'rhahdswc', -72);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('tjo', 'vgbmujvho', 'Asia', 'ewesulpky', -1, 45, -56, -78, -24, -69, 'k', 'abqpvuh', '', NULL, 'nk');
INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('', '', TRUE, 15);
-- * Number of objective function evaluations: 378
-- * Number of restarts: 1

-- Negating "FOREIGN KEY[countrycode]" on table "countrylanguage"
-- * Success: true
-- * Time: 116ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-19, 't', 'yc', 'iqsbbrm', -75);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('fpw', 'b', 'Asia', 'bg', 39, 59, -41, 58, -46, -38, 'nsijxwq', '', 'cbpudodd', NULL, 'nc');
INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('oqn', 'uocty', TRUE, 68);
-- * Number of objective function evaluations: 369
-- * Number of restarts: 1

-- Negating "NOT NULL(countrycode)" on table "countrylanguage"
-- * Success: true
-- * Time: 127ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(26, 'mailnot', 'aut', 'whuyj', 75);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('y', 'jdvchxxj', 'Asia', '', 13, -95, 45, -59, 67, -85, 'rvctehrd', 'banndyfgt', 'mhia', NULL, 'md');
INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES(NULL, 'drug', TRUE, -89);
-- * Number of objective function evaluations: 817
-- * Number of restarts: 2

-- Negating "NOT NULL(language)" on table "countrylanguage"
-- * Success: true
-- * Time: 72ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-40, 'jjkuvypv', 'vjg', 'rmblg', -83);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('b', 'd', 'Asia', 'wut', -45, 43, 93, -74, 95, -87, 'mrk', 'kjrbfebkv', 'shdrcu', NULL, 'hs');
INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('y', NULL, TRUE, 99);
-- * Number of objective function evaluations: 443
-- * Number of restarts: 1

-- Negating "NOT NULL(isofficial)" on table "countrylanguage"
-- * Success: true
-- * Time: 93ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(51, 'r', 'lld', 'uibpt', 68);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('`', 'licrul', 'Asia', 'vmi', 66, -68, 93, -22, -26, 2, 'tqir', 'rtoxmgl', '', NULL, 'dg');
INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('b', 'e', NULL, 86);
-- * Number of objective function evaluations: 457
-- * Number of restarts: 1

-- Negating "NOT NULL(percentage)" on table "countrylanguage"
-- * Success: true
-- * Time: 121ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(-33, 'ttmi', 'dng', 'fd', -24);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('u', 'qhb', 'Asia', 'rumpwy', -35, 81, -75, 98, 66, -88, 'qy', 'asetuf', 'ttvluab', NULL, 'xk');
INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('u', 'eglwkb', TRUE, NULL);
-- * Number of objective function evaluations: 462
-- * Number of restarts: 1

