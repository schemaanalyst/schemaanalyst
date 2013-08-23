/*************************************
 * Constraint coverage for Inventory *
 *************************************/
DROP TABLE IF EXISTS Inventory;
CREATE TABLE Inventory (
	id	INT	CONSTRAINT null PRIMARY KEY,
	product	VARCHAR(50)	CONSTRAINT null UNIQUE,
	quantity	INT,
	price	DECIMAL(18, 2)
);
-- Coverage: 4/4 (100.00000%) 
-- Time to generate: 26ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 15ms 
-- * Number of objective function evaluations: 9
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{id}" on table "Inventory"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "UNIQUE{product}" on table "Inventory"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

