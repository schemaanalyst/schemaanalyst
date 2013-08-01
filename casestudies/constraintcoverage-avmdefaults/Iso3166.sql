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
-- Time to generate: 20ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 7ms 
INSERT INTO country(name, two_letter, country_id) VALUES('', 'a', 0);
INSERT INTO country(name, two_letter, country_id) VALUES('', '', 0);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[two_letter]" on table "country"
-- * Success: true
-- * Time: 0ms 
INSERT INTO country(name, two_letter, country_id) VALUES('', '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "country"
-- * Success: true
-- * Time: 4ms 
INSERT INTO country(name, two_letter, country_id) VALUES(NULL, 'b', 0);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(country_id)" on table "country"
-- * Success: true
-- * Time: 9ms 
INSERT INTO country(name, two_letter, country_id) VALUES('', 'b', NULL);
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

