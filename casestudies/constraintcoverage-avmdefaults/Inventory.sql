/*************************************
 * Constraint coverage for Inventory *
 *************************************/
DROP TABLE IF EXISTS Inventory;
CREATE TABLE Inventory (
	id	INT	PRIMARY KEY,
	product	VARCHAR(50)	UNIQUE,
	quantity	INT,
	price	DECIMAL(18, 2)
);
-- Coverage: 4/4 (100.00000%) 
-- Time to generate: 14ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 9ms 
INSERT INTO Inventory(id, product, quantity, price) VALUES(1, 'a', 0, 0);
INSERT INTO Inventory(id, product, quantity, price) VALUES(0, '', 0, 0);
-- * Number of objective function evaluations: 9
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "Inventory"
-- * Success: true
-- * Time: 3ms 
INSERT INTO Inventory(id, product, quantity, price) VALUES(0, NULL, 0, 0);
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "UNIQUE[product]" on table "Inventory"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Inventory(id, product, quantity, price) VALUES(-1, '', 0, 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

