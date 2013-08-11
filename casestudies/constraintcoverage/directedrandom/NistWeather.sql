/***************************************
 * Constraint coverage for NistWeather *
 ***************************************/
DROP TABLE IF EXISTS Stats;
DROP TABLE IF EXISTS Station;
CREATE TABLE Station (
	ID	INT	PRIMARY KEY,
	CITY	CHAR(20),
	STATE	CHAR(2),
	LAT_N	INT	NOT NULL,
	LONG_W	INT	NOT NULL,
	CHECK (LAT_N BETWEEN 0 AND 90),
	CHECK (LONG_W BETWEEN SYMMETRIC 180 AND -180)
);
CREATE TABLE Stats (
	ID	INT	 REFERENCES Station (ID),
	MONTH	INT	NOT NULL,
	TEMP_F	INT	NOT NULL,
	RAIN_I	INT	NOT NULL,
	PRIMARY KEY (ID, MONTH),
	CHECK (MONTH BETWEEN 1 AND 12),
	CHECK (TEMP_F BETWEEN 80 AND 150),
	CHECK (RAIN_I BETWEEN 0 AND 100)
);
-- Coverage: 24/26 (92.30769%) 
-- Time to generate: 12474ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 228ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(0, '', '', 0, 0);
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-52, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 6, 89, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 11, 94, 0);
-- * Number of objective function evaluations: 47
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[ID]" on table "Station"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(0, '', '', 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(LAT_N)" on table "Station"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-96, '', '', NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(LONG_W)" on table "Station"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-58, '', '', 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[LAT_N BETWEEN 0 AND 90]" on table "Station"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-7, '', '', -53, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "CHECK[LONG_W BETWEEN 180 AND -180]" on table "Station"
-- * Success: false
-- * Time: 3811ms 
-- INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-70, '', '', 0, -100);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except CHECK[LONG_W BETWEEN 180 AND -180]. Value: 0.33196721311475409837 [Sum: 0.49693251533742331289]
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-70] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -70 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-70] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* -70 != -52. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(LAT_N). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(LONG_W). Value: 0E-20 [Sum: 0]
 		 		* -100, allowNull: false. Value: 0
 	 	* Satisfy CHECK[LAT_N BETWEEN 0 AND 90]. Value: 0E-20 [Sum: 0E-20]
 		 		* LAT_N BETWEEN 0 AND 90 goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Sum: 0E-20]
 			 			* 0 >= 0. Value: 0E-20 [Distance: 0]
 			 			* 0 <= 90. Value: 0E-20 [Distance: 0]
 	 	* Violate CHECK[LONG_W BETWEEN 180 AND -180]. Value: 0.49693251533742331289 [Sum: 0.98780487804878048781]
 		 		* LONG_W BETWEEN 180 AND -180 goalIsToSatisfy: false allowNull: false. Value: 0.98780487804878048781 [Best: 0.98780487804878048781]
 			 			* -100 < -180. Value: 0.98780487804878048781 [Distance: 81]
 			 			* -100 > 180. Value: 0.99645390070921985816 [Distance: 281]*/ 

-- Negating "PRIMARY KEY[ID, MONTH]" on table "Stats"
-- * Success: false
-- * Time: 8408ms 
-- INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(NULL, '', '', 0, 0);
-- INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 6, 95, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[ID, MONTH]. Value: 0.28571428571428571430 [Sum: 0.40000000000000000001]
 	 	* Satisfy PRIMARY KEY[ID]. Value: 0.40000000000000000001 [Sum: 0.66666666666666666667]
 		 		* No rows to compare with - evaluating NULL. Value: 0.66666666666666666667 [Sum: 2]
 			 			* [null] != [0]. Value: 1 [Best: 1]
 				 				* null != 0. Value: 1
 			 			* [null] != [-52]. Value: 1 [Best: 1]
 				 				* null != -52. Value: 1
 	 	* Satisfy NOT NULL(LAT_N). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(LONG_W). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy CHECK[LAT_N BETWEEN 0 AND 90]. Value: 0E-20 [Sum: 0E-20]
 		 		* LAT_N BETWEEN 0 AND 90 goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Sum: 0E-20]
 			 			* 0 >= 0. Value: 0E-20 [Distance: 0]
 			 			* 0 <= 90. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[LONG_W BETWEEN 180 AND -180]. Value: 0E-20 [Sum: 0E-20]
 		 		* LONG_W BETWEEN 180 AND -180 goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Sum: 0E-20]
 			 			* 0 >= -180. Value: 0E-20 [Distance: 0]
 			 			* 0 <= 180. Value: 0E-20 [Distance: 0]
 	 	* Violate PRIMARY KEY[ID, MONTH]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Best: 0E-20]
 			 			* [0, 6] = [0, 6]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 				 				* 6 = 6. Value: 0E-20 [Distance: 0]
 			 			* [0, 6] = [0, 11]. Value: 0.46153846153846153847 [Sum: 0.85714285714285714286]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 				 				* 6 = 11. Value: 0.85714285714285714286 [Distance: 6]
 	 	* Satisfy FOREIGN KEY[ID]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [null]. Value: 0E-20 [Sum: 0]
 				 				* 0 = null. Value: 0
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-52]. Value: 0.49532710280373831776 [Sum: 0.98148148148148148149]
 				 				* 0 = -52. Value: 0.98148148148148148149 [Distance: 53]
 	 	* Satisfy NOT NULL(MONTH). Value: 0E-20 [Sum: 0]
 		 		* 6, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(TEMP_F). Value: 0E-20 [Sum: 0]
 		 		* 95, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(RAIN_I). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy CHECK[MONTH BETWEEN 1 AND 12]. Value: 0E-20 [Sum: 0E-20]
 		 		* MONTH BETWEEN 1 AND 12 goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Sum: 0E-20]
 			 			* 6 >= 1. Value: 0E-20 [Distance: 0]
 			 			* 6 <= 12. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[TEMP_F BETWEEN 80 AND 150]. Value: 0E-20 [Sum: 0E-20]
 		 		* TEMP_F BETWEEN 80 AND 150 goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Sum: 0E-20]
 			 			* 95 >= 80. Value: 0E-20 [Distance: 0]
 			 			* 95 <= 150. Value: 0E-20 [Distance: 0]
 	 	* Satisfy CHECK[RAIN_I BETWEEN 0 AND 100]. Value: 0E-20 [Sum: 0E-20]
 		 		* RAIN_I BETWEEN 0 AND 100 goalIsToSatisfy: true allowNull: true. Value: 0E-20 [Sum: 0E-20]
 			 			* 0 >= 0. Value: 0E-20 [Distance: 0]
 			 			* 0 <= 100. Value: 0E-20 [Distance: 0]*/ 

-- Negating "FOREIGN KEY[ID]" on table "Stats"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(10, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(14, 3, 81, 0);
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "NOT NULL(MONTH)" on table "Stats"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-22, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, NULL, 95, 0);
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(TEMP_F)" on table "Stats"
-- * Success: true
-- * Time: 8ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(22, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 2, NULL, 0);
-- * Number of objective function evaluations: 79
-- * Number of restarts: 0

-- Negating "NOT NULL(RAIN_I)" on table "Stats"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-26, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 8, 83, NULL);
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

-- Negating "CHECK[MONTH BETWEEN 1 AND 12]" on table "Stats"
-- * Success: true
-- * Time: 2ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(51, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 0, 93, 0);
-- * Number of objective function evaluations: 25
-- * Number of restarts: 0

-- Negating "CHECK[TEMP_F BETWEEN 80 AND 150]" on table "Stats"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-14, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 8, 0, 0);
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "CHECK[RAIN_I BETWEEN 0 AND 100]" on table "Stats"
-- * Success: true
-- * Time: 4ms 
INSERT INTO Station(ID, CITY, STATE, LAT_N, LONG_W) VALUES(-95, '', '', 0, 0);
INSERT INTO Stats(ID, MONTH, TEMP_F, RAIN_I) VALUES(0, 9, 84, -45);
-- * Number of objective function evaluations: 28
-- * Number of restarts: 0

