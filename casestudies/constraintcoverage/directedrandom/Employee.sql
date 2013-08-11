/************************************
 * Constraint coverage for Employee *
 ************************************/
DROP TABLE IF EXISTS Employee;
CREATE TABLE Employee (
	id	INT	PRIMARY KEY,
	first	VARCHAR(15),
	last	VARCHAR(20),
	age	INT,
	address	VARCHAR(30),
	city	VARCHAR(20),
	state	VARCHAR(20),
	CHECK (id >= 0),
	CHECK (age > 0),
	CHECK (age <= 150)
);
-- Coverage: 7/8 (87.50000%) 
-- Time to generate: 2697ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 13ms 
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(0, '', '', 88, '', '', '');
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(10, '', '', 96, '', '', '');
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "Employee"
-- * Success: true
-- * Time: 9ms 
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(0, '', '', 9, '', '', '');
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "CHECK[id >= 0]" on table "Employee"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(-80, '', '', 25, '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[age > 0]" on table "Employee"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(55, '', '', 0, '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[age <= 150]" on table "Employee"
-- * Success: false
-- * Time: 2671ms 
-- INSERT INTO Employee(id, first, last, age, address, city, state) VALUES(49, '', '', 79, '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[age <= 150]. Value: 0.33179723502304147466 [Sum: 0.49655172413793103449]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [49] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 49 != 0. Value: 0E-20 [Distance: 0]
 			 			* [49] != [10]. Value: 0E-20 [Best: 0E-20]
 				 				* 49 != 10. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[id >= 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 49 >= 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[age > 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 79 > 0. Value: 0E-20 [Distance: 0]
 	 	* Violate CHECK[age <= 150]. Value: 0.49655172413793103449 [Sum: 0.98630136986301369864]
 		 		* 79 > 150. Value: 0.98630136986301369864 [Distance: 72]*/ 

