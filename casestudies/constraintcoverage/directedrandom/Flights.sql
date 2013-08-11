/***********************************
 * Constraint coverage for Flights *
 ***********************************/
DROP TABLE IF EXISTS FlightAvailable;
DROP TABLE IF EXISTS Flights;
CREATE TABLE Flights (
	FLIGHT_ID	CHAR(6)	NOT NULL,
	SEGMENT_NUMBER	INT	NOT NULL,
	ORIG_AIRPORT	CHAR(3),
	DEPART_TIME	TIME,
	DEST_AIRPORT	CHAR(3),
	ARRIVE_TIME	TIME,
	MEAL	CHAR(1)	NOT NULL,
	PRIMARY KEY (FLIGHT_ID, SEGMENT_NUMBER),
	CONSTRAINT MEAL_CONSTRAINT CHECK (MEAL IN ('B', 'L', 'D', 'S'))
);
CREATE TABLE FlightAvailable (
	FLIGHT_ID	CHAR(6)	NOT NULL,
	SEGMENT_NUMBER	INT	NOT NULL,
	FLIGHT_DATE	DATE	NOT NULL,
	ECONOMY_SEATS_TAKEN	INT,
	BUSINESS_SEATS_TAKEN	INT,
	FIRSTCLASS_SEATS_TAKEN	INT,
	CONSTRAINT FLTAVAIL_PK PRIMARY KEY (FLIGHT_ID, SEGMENT_NUMBER),
	CONSTRAINT FLTS_FK FOREIGN KEY (FLIGHT_ID, SEGMENT_NUMBER) REFERENCES Flights (FLIGHT_ID, SEGMENT_NUMBER)
);
-- Coverage: 2/20 (10.00000%) 
-- Time to generate: 52241ms 

-- Satisfying all constraints
-- * Success: false
-- * Time: 15048ms 
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'a');
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('phctgp', -96, '', '00:00:00', '', '00:00:00', 'a');
-- INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, '1000-01-01', 0, 0, 0);
-- INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, '1000-01-01', 0, 0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all constraints. Value: 0.42588235294117647060 [Sum: 0.74180327868852459018]
 	 	* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['phctgp', -96] != ['', 0]. Value: 0 [Best: 0]
 				 				* 'phctgp' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 				 				* -96 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'phctgp', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* -96, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MEAL). Value: 0E-20 [Sum: 0]
 		 		* 'a', allowNull: false. Value: 0
 		 		* 'a', allowNull: false. Value: 0
 	 	* Satisfy CHECK[MEAL IN ('B', 'L', 'D', 'S')]. Value: 0.49180327868852459017 [Sum: 0.96774193548387096776]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: false. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: false. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 	 	* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0.25000000000000000001 [Sum: 0.33333333333333333334]
 		 		* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 			 			* ['', 0] != ['', 0]. Value: 0.50000000000000000000 [Best: 0.50000000000000000000]
 				 				* '' != ''. Value: 1 [Best: 1]
 					 					* Compound values have same size. Value: 1
 				 				* 0 != 0. Value: 0.50000000000000000000 [Distance: 1]
 	 	* Satisfy FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* ['', 0] = ['', 0]. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* ['', 0] = ['phctgp', -96]. Value: 0.64874551971326164875 [Sum: 1.84693877551020408164]
 				 				* '' = 'phctgp'. Value: 0.85714285714285714286 [Sum: 6]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 				 				* 0 = -96. Value: 0.98979591836734693878 [Distance: 97]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* ['', 0] = ['', 0]. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* ['', 0] = ['phctgp', -96]. Value: 0.64874551971326164875 [Sum: 1.84693877551020408164]
 				 				* '' = 'phctgp'. Value: 0.85714285714285714286 [Sum: 6]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 				 				* 0 = -96. Value: 0.98979591836734693878 [Distance: 97]
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_DATE). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 		 		* '1000-01-01', allowNull: false. Value: 0*/ 

-- Negating "PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]" on table "Flights"
-- * Success: false
-- * Time: 3149ms 
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'a');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0.45238095238095238096 [Sum: 0.82608695652173913044]
 	 	* Violate PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 1
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MEAL). Value: 0E-20 [Sum: 0]
 		 		* 'a', allowNull: false. Value: 0
 	 	* Satisfy CHECK[MEAL IN ('B', 'L', 'D', 'S')]. Value: 0.32608695652173913044 [Sum: 0.48387096774193548388]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: true. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]*/ 

-- Negating "NOT NULL(FLIGHT_ID)" on table "Flights"
-- * Success: false
-- * Time: 2819ms 
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES(NULL, 0, '', '00:00:00', '', '00:00:00', 'a');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(FLIGHT_ID). Value: 0.24590163934426229509 [Sum: 0.32608695652173913044]
 	 	* Violate NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MEAL). Value: 0E-20 [Sum: 0]
 		 		* 'a', allowNull: false. Value: 0
 	 	* Satisfy CHECK[MEAL IN ('B', 'L', 'D', 'S')]. Value: 0.32608695652173913044 [Sum: 0.48387096774193548388]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: true. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]*/ 

-- Negating "NOT NULL(SEGMENT_NUMBER)" on table "Flights"
-- * Success: false
-- * Time: 2886ms 
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', NULL, '', '00:00:00', '', '00:00:00', 'a');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(SEGMENT_NUMBER). Value: 0.24590163934426229509 [Sum: 0.32608695652173913044]
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(MEAL). Value: 0E-20 [Sum: 0]
 		 		* 'a', allowNull: false. Value: 0
 	 	* Satisfy CHECK[MEAL IN ('B', 'L', 'D', 'S')]. Value: 0.32608695652173913044 [Sum: 0.48387096774193548388]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: true. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]*/ 

-- Negating "NOT NULL(MEAL)" on table "Flights"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[MEAL IN ('B', 'L', 'D', 'S')]" on table "Flights"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]" on table "FlightAvailable"
-- * Success: false
-- * Time: 6147ms 
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'a');
-- INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, '1000-01-01', 0, 0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0.45238095238095238096 [Sum: 0.82608695652173913044]
 	 	* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MEAL). Value: 0E-20 [Sum: 0]
 		 		* 'a', allowNull: false. Value: 0
 	 	* Satisfy CHECK[MEAL IN ('B', 'L', 'D', 'S')]. Value: 0.32608695652173913044 [Sum: 0.48387096774193548388]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: true. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 	 	* Violate PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 1
 	 	* Satisfy FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* ['', 0] = ['', 0]. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_DATE). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0*/ 

-- Negating "FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]" on table "FlightAvailable"
-- * Success: false
-- * Time: 5984ms 
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'a');
-- INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('tbentk', -33, '1000-01-01', 0, 0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0.24590163934426229509 [Sum: 0.32608695652173913044]
 	 	* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MEAL). Value: 0E-20 [Sum: 0]
 		 		* 'a', allowNull: false. Value: 0
 	 	* Satisfy CHECK[MEAL IN ('B', 'L', 'D', 'S')]. Value: 0.32608695652173913044 [Sum: 0.48387096774193548388]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: true. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 	 	* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0
 	 	* Violate FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Sum: 0]
 			 			* ['tbentk', -33] != ['', 0]. Value: 0 [Best: 0]
 				 				* 'tbentk' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 				 				* -33 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* 'tbentk', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* -33, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_DATE). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(FLIGHT_ID)" on table "FlightAvailable"
-- * Success: false
-- * Time: 5615ms 
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'a');
-- INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES(NULL, 0, '1000-01-01', 0, 0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(FLIGHT_ID). Value: 0.24590163934426229509 [Sum: 0.32608695652173913044]
 	 	* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MEAL). Value: 0E-20 [Sum: 0]
 		 		* 'a', allowNull: false. Value: 0
 	 	* Satisfy CHECK[MEAL IN ('B', 'L', 'D', 'S')]. Value: 0.32608695652173913044 [Sum: 0.48387096774193548388]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: true. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 	 	* Satisfy FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [null, 0] = ['', 0]. Value: 0E-20 [Sum: 0E-20]
 				 				* null = ''. Value: 0
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Violate NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_DATE). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(SEGMENT_NUMBER)" on table "FlightAvailable"
-- * Success: false
-- * Time: 5608ms 
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'a');
-- INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', NULL, '1000-01-01', 0, 0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(SEGMENT_NUMBER). Value: 0.24590163934426229509 [Sum: 0.32608695652173913044]
 	 	* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MEAL). Value: 0E-20 [Sum: 0]
 		 		* 'a', allowNull: false. Value: 0
 	 	* Satisfy CHECK[MEAL IN ('B', 'L', 'D', 'S')]. Value: 0.32608695652173913044 [Sum: 0.48387096774193548388]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: true. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 	 	* Satisfy FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* ['', null] = ['', 0]. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 				 				* null = 0. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_DATE). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(FLIGHT_DATE)" on table "FlightAvailable"
-- * Success: false
-- * Time: 4984ms 
-- INSERT INTO Flights(FLIGHT_ID, SEGMENT_NUMBER, ORIG_AIRPORT, DEPART_TIME, DEST_AIRPORT, ARRIVE_TIME, MEAL) VALUES('', 0, '', '00:00:00', '', '00:00:00', 'a');
-- INSERT INTO FlightAvailable(FLIGHT_ID, SEGMENT_NUMBER, FLIGHT_DATE, ECONOMY_SEATS_TAKEN, BUSINESS_SEATS_TAKEN, FIRSTCLASS_SEATS_TAKEN) VALUES('', 0, NULL, 0, 0, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(FLIGHT_DATE). Value: 0.24590163934426229509 [Sum: 0.32608695652173913044]
 	 	* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(MEAL). Value: 0E-20 [Sum: 0]
 		 		* 'a', allowNull: false. Value: 0
 	 	* Satisfy CHECK[MEAL IN ('B', 'L', 'D', 'S')]. Value: 0.32608695652173913044 [Sum: 0.48387096774193548388]
 		 		* MEAL IN ('B', 'L', 'D', 'S') goalIsToSatisfy: true allowNull: true. Value: 0.48387096774193548388 [Best: 0.48387096774193548388]
 			 			* 'a' = 'B'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 97 = 66. Value: 0.96969696969696969697 [Distance: 32]
 			 			* 'a' = 'L'. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 97 = 76. Value: 0.95652173913043478261 [Distance: 22]
 			 			* 'a' = 'D'. Value: 0.49180327868852459017 [Sum: 0.96774193548387096775]
 				 				* 97 = 68. Value: 0.96774193548387096775 [Distance: 30]
 			 			* 'a' = 'S'. Value: 0.48387096774193548388 [Sum: 0.93750000000000000000]
 				 				* 97 = 83. Value: 0.93750000000000000000 [Distance: 15]
 	 	* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0
 	 	* Satisfy FOREIGN KEY[FLIGHT_ID, SEGMENT_NUMBER]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* ['', 0] = ['', 0]. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(FLIGHT_ID). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(SEGMENT_NUMBER). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Violate NOT NULL(FLIGHT_DATE). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0*/ 

