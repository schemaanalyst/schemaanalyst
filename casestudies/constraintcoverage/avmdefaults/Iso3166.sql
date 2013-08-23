/***********************************
 * Constraint coverage for Iso3166 *
 ***********************************/
DROP TABLE IF EXISTS country;
CREATE TABLE country (
	name	VARCHAR(100)	CONSTRAINT null NOT NULL,
	two_letter	VARCHAR(100)	CONSTRAINT null PRIMARY KEY,
	country_id	INT	CONSTRAINT null NOT NULL
);
-- Coverage: 6/6 (100.00000%) 
-- Time to generate: 28ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 10ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{two_letter}" on table "country"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "country"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(country_id)" on table "country"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

