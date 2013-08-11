/***********************************
 * Constraint coverage for Iso3166 *
 ***********************************/
DROP TABLE IF EXISTS country;
CREATE TABLE country (
	name	VARCHAR(100)	NOT NULL,
	two_letter	VARCHAR(100)	PRIMARY KEY,
	country_id	INT	NOT NULL
);
-- Coverage: 6/6 (100.00000%) 
-- Time to generate: 16ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 7ms 
INSERT INTO country(name, two_letter, country_id) VALUES('', '', 0);
INSERT INTO country(name, two_letter, country_id) VALUES('', 'phctgpyae', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[two_letter]" on table "country"
-- * Success: true
-- * Time: 1ms 
INSERT INTO country(name, two_letter, country_id) VALUES('', '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "country"
-- * Success: true
-- * Time: 3ms 
INSERT INTO country(name, two_letter, country_id) VALUES(NULL, 'danycpk', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(country_id)" on table "country"
-- * Success: true
-- * Time: 5ms 
INSERT INTO country(name, two_letter, country_id) VALUES('', 'oaks', NULL);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

