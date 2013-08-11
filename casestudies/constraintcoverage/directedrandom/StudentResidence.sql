/********************************************
 * Constraint coverage for StudentResidence *
 ********************************************/
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Residence;
CREATE TABLE Residence (
	name	VARCHAR(50)	PRIMARY KEY	NOT NULL,
	capacity	INT	NOT NULL,
	CHECK (capacity > 1),
	CHECK (capacity <= 10)
);
CREATE TABLE Student (
	id	INT	PRIMARY KEY,
	firstName	VARCHAR(50),
	lastName	VARCHAR(50),
	residence	VARCHAR(50)	 REFERENCES Residence (name),
	CHECK (id >= 0)
);
-- Coverage: 6/16 (37.50000%) 
-- Time to generate: 11536ms 

-- Satisfying all constraints
-- * Success: false
-- * Time: 7438ms 
-- INSERT INTO Residence(name, capacity) VALUES('', 2);
-- INSERT INTO Residence(name, capacity) VALUES('phctgpyae', 3);
-- INSERT INTO Student(id, firstName, lastName, residence) VALUES(0, '', '', '');
-- INSERT INTO Student(id, firstName, lastName, residence) VALUES(NULL, '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all constraints. Value: 0.45454545454545454546 [Sum: 0.83333333333333333334]
 	 	* Satisfy PRIMARY KEY[name]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[name]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['phctgpyae'] != ['']. Value: 0 [Best: 0]
 				 				* 'phctgpyae' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'phctgpyae', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(capacity). Value: 0E-20 [Sum: 0]
 		 		* 2, allowNull: false. Value: 0
 		 		* 3, allowNull: false. Value: 0
 	 	* Satisfy CHECK[capacity > 1]. Value: 0E-20 [Sum: 0E-20]
 		 		* 2 > 1. Value: 0E-20 [Distance: 0]
 		 		* 3 > 1. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[capacity <= 10]. Value: 0E-20 [Sum: 0E-20]
 		 		* 2 <= 10. Value: 0E-20 [Distance: 0]
 		 		* 3 <= 10. Value: 0E-20 [Distance: 0]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0.50000000000000000000 [Sum: 1]
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 	 	* Satisfy FOREIGN KEY[residence]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [''] = ['']. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 			 			* [''] = ['phctgpyae']. Value: 0.47368421052631578948 [Sum: 0.90000000000000000000]
 				 				* '' = 'phctgpyae'. Value: 0.90000000000000000000 [Sum: 9]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 					 					* Size difference penalty (7). Value: 1
 					 					* Size difference penalty (8). Value: 1
 					 					* Size difference penalty (9). Value: 1
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [''] = ['']. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 			 			* [''] = ['phctgpyae']. Value: 0.47368421052631578948 [Sum: 0.90000000000000000000]
 				 				* '' = 'phctgpyae'. Value: 0.90000000000000000000 [Sum: 9]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 					 					* Size difference penalty (7). Value: 1
 					 					* Size difference penalty (8). Value: 1
 					 					* Size difference penalty (9). Value: 1
 	 	* Satisfy CHECK[id >= 0]. Value: 0.50000000000000000000 [Sum: 1.00000000000000000000]
 		 		* 0 >= 0. Value: 0E-20 [Distance: 0]
 		 		* null >= 0. Value: 1*/ 

-- Negating "PRIMARY KEY[name]" on table "Residence"
-- * Success: false
-- * Time: 1403ms 
-- INSERT INTO Residence(name, capacity) VALUES('', 9);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[name]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[name]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[name]. Value: 1
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(capacity). Value: 0E-20 [Sum: 0]
 		 		* 9, allowNull: false. Value: 0
 	 	* Satisfy CHECK[capacity > 1]. Value: 0E-20 [Sum: 0E-20]
 		 		* 9 > 1. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[capacity <= 10]. Value: 0E-20 [Sum: 0E-20]
 		 		* 9 <= 10. Value: 0E-20 [Distance: 0]*/ 

-- Negating "NOT NULL(name)" on table "Residence"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Residence(name, capacity) VALUES(NULL, 3);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(capacity)" on table "Residence"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Residence(name, capacity) VALUES('', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[capacity > 1]" on table "Residence"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Residence(name, capacity) VALUES('', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "CHECK[capacity <= 10]" on table "Residence"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Residence(name, capacity) VALUES('', 16);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[id]" on table "Student"
-- * Success: false
-- * Time: 2686ms 
-- INSERT INTO Residence(name, capacity) VALUES('', 8);
-- INSERT INTO Student(id, firstName, lastName, residence) VALUES(0, '', '', '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Satisfy PRIMARY KEY[name]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[name]. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(capacity). Value: 0E-20 [Sum: 0]
 		 		* 8, allowNull: false. Value: 0
 	 	* Satisfy CHECK[capacity > 1]. Value: 0E-20 [Sum: 0E-20]
 		 		* 8 > 1. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[capacity <= 10]. Value: 0E-20 [Sum: 0E-20]
 		 		* 8 <= 10. Value: 0E-20 [Distance: 0]
 	 	* Violate PRIMARY KEY[id]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[id]. Value: 1
 	 	* Satisfy FOREIGN KEY[residence]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [''] = ['']. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 	 	* Satisfy CHECK[id >= 0]. Value: 0E-20 [Sum: 0E-20]
 		 		* 0 >= 0. Value: 0E-20 [Distance: 0]*/ 

-- Negating "FOREIGN KEY[residence]" on table "Student"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Residence(name, capacity) VALUES('', 9);
INSERT INTO Student(id, firstName, lastName, residence) VALUES(0, '', '', 'cs');
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "CHECK[id >= 0]" on table "Student"
-- * Success: true
-- * Time: 6ms 
INSERT INTO Residence(name, capacity) VALUES('bjhcawi', 6);
INSERT INTO Student(id, firstName, lastName, residence) VALUES(-9, '', '', '');
-- * Number of objective function evaluations: 72
-- * Number of restarts: 0

