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
-- Coverage: 7/48 (14.58333%) 
-- Time to generate: 281103ms 

-- Satisfying all constraints
-- * Success: false
-- * Time: 30965ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', 0);
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(-52, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'djnfqpc', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('c', '', 'psia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('', '', FALSE, 0);
-- INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('', 'clroojta', FALSE, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all constraints. Value: 0.36508453043183755323 [Sum: 0.57501281340671960574]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-52] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -52 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* -52, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['c'] != ['']. Value: 0 [Best: 0]
 				 				* 'c' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-52]. Value: 0.49532710280373831776 [Sum: 0.98148148148148148149]
 				 				* 0 = -52. Value: 0.98148148148148148149 [Distance: 53]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-52]. Value: 0.49532710280373831776 [Sum: 0.98148148148148148149]
 				 				* 0 = -52. Value: 0.98148148148148148149 [Distance: 53]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'c', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'djnfqpc', allowNull: false. Value: 0
 		 		* 'psia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.57501281340671960574 [Sum: 1.35301211788537088502]
 		 		* Value: 0.85816675706062861697 [Best: 0.85816675706062861697]
 			 			* Value: 0.85816675706062861697 [Best: 0.85816675706062861697]
 				 				* Value: 0.85816675706062861697 [Best: 0.85816675706062861697]
 					 					* Value: 0.86159057912566542991 [Best: 0.86159057912566542991]
 						 						* Value: 0.86159057912566542991 [Best: 0.86159057912566542991]
 							 							* Value: 0.86159057912566542991 [Best: 0.86159057912566542991]
 								 								* 'djnfqpc' = 'Asia'. Value: 0.86835782275205618705 [Sum: 6.59634959634959634961]
 									 									* 100 = 65. Value: 0.97297297297297297298 [Distance: 36]
 									 									* 106 = 115. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 110 = 105. Value: 0.85714285714285714286 [Distance: 6]
 									 									* 102 = 97. Value: 0.85714285714285714286 [Distance: 6]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 									 									* Size difference penalty (3). Value: 1
 								 								* 'djnfqpc' = 'Europe'. Value: 0.86159057912566542991 [Sum: 6.22494172494172494175]
 									 									* 100 = 69. Value: 0.96969696969696969697 [Distance: 32]
 									 									* 106 = 117. Value: 0.92307692307692307693 [Distance: 12]
 									 									* 110 = 114. Value: 0.83333333333333333334 [Distance: 5]
 									 									* 102 = 111. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 113 = 112. Value: 0.66666666666666666667 [Distance: 2]
 									 									* 112 = 101. Value: 0.92307692307692307693 [Distance: 12]
 									 									* Size difference penalty (1). Value: 1
 							 							* 'djnfqpc' = 'NorthAAmerica'. Value: 0.92563515658908192723 [Sum: 12.44721449185734900024]
 								 								* 100 = 78. Value: 0.95833333333333333334 [Distance: 23]
 								 								* 106 = 111. Value: 0.85714285714285714286 [Distance: 6]
 								 								* 110 = 114. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 102 = 116. Value: 0.93750000000000000000 [Distance: 15]
 								 								* 113 = 104. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 112 = 65. Value: 0.97959183673469387756 [Distance: 48]
 								 								* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 						 						* 'djnfqpc' = 'Africa'. Value: 0.86335654473814982807 [Sum: 6.31831611022787493378]
 							 							* 100 = 65. Value: 0.97297297297297297298 [Distance: 36]
 							 							* 106 = 102. Value: 0.83333333333333333334 [Distance: 5]
 							 							* 110 = 114. Value: 0.83333333333333333334 [Distance: 5]
 							 							* 102 = 105. Value: 0.80000000000000000000 [Distance: 4]
 							 							* 113 = 99. Value: 0.93750000000000000000 [Distance: 15]
 							 							* 112 = 97. Value: 0.94117647058823529412 [Distance: 16]
 							 							* Size difference penalty (1). Value: 1
 					 					* 'djnfqpc' = 'Oceania'. Value: 0.85816675706062861697 [Sum: 6.05053328314197879416]
 						 						* 100 = 79. Value: 0.95652173913043478261 [Distance: 22]
 						 						* 106 = 99. Value: 0.88888888888888888889 [Distance: 8]
 						 						* 110 = 101. Value: 0.90909090909090909091 [Distance: 10]
 						 						* 102 = 97. Value: 0.85714285714285714286 [Distance: 6]
 						 						* 113 = 110. Value: 0.80000000000000000000 [Distance: 4]
 						 						* 112 = 105. Value: 0.88888888888888888889 [Distance: 8]
 						 						* 99 = 97. Value: 0.75000000000000000000 [Distance: 3]
 				 				* 'djnfqpc' = 'Antarctica'. Value: 0.90085087385115575759 [Sum: 9.08581758450179502814]
 					 					* 100 = 65. Value: 0.97297297297297297298 [Distance: 36]
 					 					* 106 = 110. Value: 0.83333333333333333334 [Distance: 5]
 					 					* 110 = 116. Value: 0.87500000000000000000 [Distance: 7]
 					 					* 102 = 97. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 113 = 114. Value: 0.66666666666666666667 [Distance: 2]
 					 					* 112 = 99. Value: 0.93333333333333333334 [Distance: 14]
 					 					* 99 = 116. Value: 0.94736842105263157895 [Distance: 18]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 			 			* 'djnfqpc' = 'SouthAAmerica'. Value: 0.92588093364941701319 [Sum: 12.49180513513220280140]
 				 				* 100 = 83. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 106 = 111. Value: 0.85714285714285714286 [Distance: 6]
 				 				* 110 = 117. Value: 0.88888888888888888889 [Distance: 8]
 				 				* 102 = 116. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 113 = 104. Value: 0.90909090909090909091 [Distance: 10]
 				 				* 112 = 65. Value: 0.97959183673469387756 [Distance: 48]
 				 				* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 		 		* Value: 0.49484536082474226805 [Best: 0.49484536082474226805]
 			 			* Value: 0.49484536082474226805 [Best: 0.49484536082474226805]
 				 				* Value: 0.49484536082474226805 [Best: 0.49484536082474226805]
 					 					* Value: 0.49484536082474226805 [Best: 0.49484536082474226805]
 						 						* Value: 0.49484536082474226805 [Best: 0.49484536082474226805]
 							 							* Value: 0.49484536082474226805 [Best: 0.49484536082474226805]
 								 								* 'psia' = 'Asia'. Value: 0.49484536082474226805 [Sum: 0.97959183673469387756]
 									 									* 112 = 65. Value: 0.97959183673469387756 [Distance: 48]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'psia' = 'Europe'. Value: 0.84789414046745664407 [Sum: 5.57436868686868686869]
 									 									* 112 = 69. Value: 0.97777777777777777778 [Distance: 44]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'psia' = 'NorthAAmerica'. Value: 0.92683119968325194669 [Sum: 12.66702741702741702744]
 								 								* 112 = 78. Value: 0.97222222222222222223 [Distance: 35]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'psia' = 'Africa'. Value: 0.85123510741064446387 [Sum: 5.72201607915893630181]
 							 							* 112 = 65. Value: 0.97959183673469387756 [Distance: 48]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'psia' = 'Oceania'. Value: 0.85183443085606773284 [Sum: 5.74920634920634920637]
 						 						* 112 = 79. Value: 0.97142857142857142858 [Distance: 34]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'psia' = 'Antarctica'. Value: 0.89753900595142351617 [Sum: 8.75981161695447409735]
 					 					* 112 = 65. Value: 0.97959183673469387756 [Distance: 48]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'psia' = 'SouthAAmerica'. Value: 0.92691141798585382284 [Sum: 12.68202764976958525348]
 				 				* 112 = 83. Value: 0.96774193548387096775 [Distance: 30]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1
 	 	* Satisfy PRIMARY KEY[countrycode, language]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[countrycode, language]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0]
 			 			* ['', 'clroojta'] != ['', '']. Value: 0 [Best: 0]
 				 				* '' != ''. Value: 1 [Best: 1]
 					 					* Compound values have same size. Value: 1
 				 				* 'clroojta' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy FOREIGN KEY[countrycode]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [''] = ['']. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 			 			* [''] = ['c']. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 				 				* '' = 'c'. Value: 0.50000000000000000000 [Sum: 1]
 					 					* Size difference penalty (1). Value: 1
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [''] = ['']. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 			 			* [''] = ['c']. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 				 				* '' = 'c'. Value: 0.50000000000000000000 [Sum: 1]
 					 					* Size difference penalty (1). Value: 1
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(language). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* 'clroojta', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(isofficial). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(percentage). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "PRIMARY KEY[id]" on table "city"
-- * Success: false
-- * Time: 1147ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[id]. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 	 	* Violate PRIMARY KEY[id]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[id]. Value: 1
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(id)" on table "city"
-- * Success: true
-- * Time: 0ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(NULL, '', '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "city"
-- * Success: true
-- * Time: 1ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(0, NULL, '', '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(countrycode)" on table "city"
-- * Success: true
-- * Time: 0ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(district)" on table "city"
-- * Success: true
-- * Time: 0ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(population)" on table "city"
-- * Success: true
-- * Time: 0ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[code]" on table "country"
-- * Success: false
-- * Time: 13695ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'jsia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[code]. Value: 0.45376344086021505377 [Sum: 0.83070866141732283465]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Violate PRIMARY KEY[code]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[code]. Value: 1
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'jsia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33070866141732283465 [Sum: 0.49411764705882352942]
 		 		* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 			 			* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 				 				* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 					 					* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 						 						* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 							 							* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 								 								* 'jsia' = 'Asia'. Value: 0.49411764705882352942 [Sum: 0.97674418604651162791]
 									 									* 106 = 65. Value: 0.97674418604651162791 [Distance: 42]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'jsia' = 'Europe'. Value: 0.84781500121943107998 [Sum: 5.57094988344988344989]
 									 									* 106 = 69. Value: 0.97435897435897435898 [Distance: 38]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'jsia' = 'NorthAAmerica'. Value: 0.92680144495848913113 [Sum: 12.66147186147186147188]
 								 								* 106 = 78. Value: 0.96666666666666666667 [Distance: 29]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'jsia' = 'Africa'. Value: 0.85117205936336462322 [Sum: 5.71916842847075405216]
 							 							* 106 = 65. Value: 0.97674418604651162791 [Distance: 42]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'jsia' = 'Oceania'. Value: 0.85170454545454545455 [Sum: 5.74329501915708812262]
 						 						* 106 = 79. Value: 0.96551724137931034483 [Distance: 28]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'jsia' = 'Antarctica'. Value: 0.89750910186228031117 [Sum: 8.75696396626629184770]
 					 					* 106 = 65. Value: 0.97674418604651162791 [Distance: 42]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'jsia' = 'SouthAAmerica'. Value: 0.92687003760969494359 [Sum: 12.67428571428571428573]
 				 				* 106 = 83. Value: 0.96000000000000000000 [Distance: 24]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "FOREIGN KEY[capital]" on table "country"
-- * Success: false
-- * Time: 13919ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'osia', '', 0, 0, 0, 0, 0, 0, '', '', '', -71, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except FOREIGN KEY[capital]. Value: 0.24867724867724867726 [Sum: 0.33098591549295774649]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Violate FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Sum: 0E-20]
 			 			* [-71] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -71 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'osia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33098591549295774649 [Sum: 0.49473684210526315790]
 		 		* Value: 0.49473684210526315790 [Best: 0.49473684210526315790]
 			 			* Value: 0.49473684210526315790 [Best: 0.49473684210526315790]
 				 				* Value: 0.49473684210526315790 [Best: 0.49473684210526315790]
 					 					* Value: 0.49473684210526315790 [Best: 0.49473684210526315790]
 						 						* Value: 0.49473684210526315790 [Best: 0.49473684210526315790]
 							 							* Value: 0.49473684210526315790 [Best: 0.49473684210526315790]
 								 								* 'osia' = 'Asia'. Value: 0.49473684210526315790 [Sum: 0.97916666666666666667]
 									 									* 111 = 65. Value: 0.97916666666666666667 [Distance: 47]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'osia' = 'Europe'. Value: 0.84788245462402765774 [Sum: 5.57386363636363636364]
 									 									* 111 = 69. Value: 0.97727272727272727273 [Distance: 43]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'osia' = 'NorthAAmerica'. Value: 0.92682695048940416232 [Sum: 12.66623376623376623379]
 								 								* 111 = 78. Value: 0.97142857142857142858 [Distance: 34]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'osia' = 'Africa'. Value: 0.85122569737954353339 [Sum: 5.72159090909090909092]
 							 							* 111 = 65. Value: 0.97916666666666666667 [Distance: 47]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'osia' = 'Oceania'. Value: 0.85181598062953995158 [Sum: 5.74836601307189542485]
 						 						* 111 = 79. Value: 0.97058823529411764706 [Distance: 33]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'osia' = 'Antarctica'. Value: 0.89753454221304745596 [Sum: 8.75938644688644688646]
 					 					* 111 = 65. Value: 0.97916666666666666667 [Distance: 47]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'osia' = 'SouthAAmerica'. Value: 0.92690567351200835364 [Sum: 12.68095238095238095240]
 				 				* 111 = 83. Value: 0.96666666666666666667 [Distance: 29]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "NOT NULL(code)" on table "country"
-- * Success: false
-- * Time: 13471ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES(NULL, '', 'ssia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(code). Value: 0.24878048780487804879 [Sum: 0.33116883116883116884]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Violate NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'ssia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33116883116883116884 [Sum: 0.49514563106796116506]
 		 		* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 			 			* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 				 				* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 					 					* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 						 						* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 							 							* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 								 								* 'ssia' = 'Asia'. Value: 0.49514563106796116506 [Sum: 0.98076923076923076924]
 									 									* 115 = 65. Value: 0.98076923076923076924 [Distance: 51]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'ssia' = 'Europe'. Value: 0.84792626728110599079 [Sum: 5.57575757575757575758]
 									 									* 115 = 69. Value: 0.97916666666666666667 [Distance: 47]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'ssia' = 'NorthAAmerica'. Value: 0.92684263736799152223 [Sum: 12.66916416916416916419]
 								 								* 115 = 78. Value: 0.97435897435897435898 [Distance: 38]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'ssia' = 'Africa'. Value: 0.85126115974690127417 [Sum: 5.72319347319347319349]
 							 							* 115 = 65. Value: 0.98076923076923076924 [Distance: 51]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'ssia' = 'Oceania'. Value: 0.85188393243828497185 [Sum: 5.75146198830409356727]
 						 						* 115 = 79. Value: 0.97368421052631578948 [Distance: 37]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'ssia' = 'Antarctica'. Value: 0.89755136504362510555 [Sum: 8.76098901098901098903]
 					 					* 115 = 65. Value: 0.98076923076923076924 [Distance: 51]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'ssia' = 'SouthAAmerica'. Value: 0.92692661958857844643 [Sum: 12.68487394957983193279]
 				 				* 115 = 83. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "NOT NULL(name)" on table "country"
-- * Success: false
-- * Time: 13404ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', NULL, 'wsia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(name). Value: 0.24886877828054298644 [Sum: 0.33132530120481927712]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'wsia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33132530120481927712 [Sum: 0.49549549549549549550]
 		 		* Value: 0.49549549549549549550 [Best: 0.49549549549549549550]
 			 			* Value: 0.49549549549549549550 [Best: 0.49549549549549549550]
 				 				* Value: 0.49549549549549549550 [Best: 0.49549549549549549550]
 					 					* Value: 0.49549549549549549550 [Best: 0.49549549549549549550]
 						 						* Value: 0.49549549549549549550 [Best: 0.49549549549549549550]
 							 							* Value: 0.49549549549549549550 [Best: 0.49549549549549549550]
 								 								* 'wsia' = 'Asia'. Value: 0.49549549549549549550 [Sum: 0.98214285714285714286]
 									 									* 119 = 65. Value: 0.98214285714285714286 [Distance: 55]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'wsia' = 'Europe'. Value: 0.84796331982191507742 [Sum: 5.57736013986013986015]
 									 									* 119 = 69. Value: 0.98076923076923076924 [Distance: 51]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'wsia' = 'NorthAAmerica'. Value: 0.92685540079308097600 [Sum: 12.67154938085170643312]
 								 								* 119 = 78. Value: 0.97674418604651162791 [Distance: 42]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'wsia' = 'Africa'. Value: 0.85129154260883559991 [Sum: 5.72456709956709956711]
 							 							* 119 = 65. Value: 0.98214285714285714286 [Distance: 55]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'wsia' = 'Oceania'. Value: 0.85193889541715628673 [Sum: 5.75396825396825396827]
 						 						* 119 = 79. Value: 0.97619047619047619048 [Distance: 41]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'wsia' = 'Antarctica'. Value: 0.89756578021668777262 [Sum: 8.76236263736263736265]
 					 					* 119 = 65. Value: 0.98214285714285714286 [Distance: 55]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'wsia' = 'SouthAAmerica'. Value: 0.92694314748695413348 [Sum: 12.68796992481203007521]
 				 				* 119 = 83. Value: 0.97368421052631578948 [Distance: 37]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "NOT NULL(continent)" on table "country"
-- * Success: true
-- * Time: 0ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(0, '', '', '', 0);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', NULL, '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(region)" on table "country"
-- * Success: false
-- * Time: 14263ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(63, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'tsia', NULL, 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(region). Value: 0.24880382775119617226 [Sum: 0.33121019108280254778]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [63] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 63 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 63, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [63]. Value: 0.49612403100775193799 [Sum: 0.98461538461538461539]
 				 				* 0 = 63. Value: 0.98461538461538461539 [Distance: 64]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'tsia', allowNull: false. Value: 0
 	 	* Violate NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33121019108280254778 [Sum: 0.49523809523809523810]
 		 		* Value: 0.49523809523809523810 [Best: 0.49523809523809523810]
 			 			* Value: 0.49523809523809523810 [Best: 0.49523809523809523810]
 				 				* Value: 0.49523809523809523810 [Best: 0.49523809523809523810]
 					 					* Value: 0.49523809523809523810 [Best: 0.49523809523809523810]
 						 						* Value: 0.49523809523809523810 [Best: 0.49523809523809523810]
 							 							* Value: 0.49523809523809523810 [Best: 0.49523809523809523810]
 								 								* 'tsia' = 'Asia'. Value: 0.49523809523809523810 [Sum: 0.98113207547169811321]
 									 									* 116 = 65. Value: 0.98113207547169811321 [Distance: 52]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'tsia' = 'Europe'. Value: 0.84793609930703718725 [Sum: 5.57618274582560296847]
 									 									* 116 = 69. Value: 0.97959183673469387756 [Distance: 48]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'tsia' = 'NorthAAmerica'. Value: 0.92684606797615371827 [Sum: 12.66980519480519480521]
 								 								* 116 = 78. Value: 0.97500000000000000000 [Distance: 39]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'tsia' = 'Africa'. Value: 0.85126918661507717165 [Sum: 5.72355631789594053746]
 							 							* 116 = 65. Value: 0.98113207547169811321 [Distance: 52]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'tsia' = 'Oceania'. Value: 0.85189873417721518988 [Sum: 5.75213675213675213677]
 						 						* 116 = 79. Value: 0.97435897435897435898 [Distance: 38]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'tsia' = 'Antarctica'. Value: 0.89755517321948214703 [Sum: 8.76135185569147833300]
 					 					* 116 = 65. Value: 0.98113207547169811321 [Distance: 52]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'tsia' = 'SouthAAmerica'. Value: 0.92693110647181628393 [Sum: 12.68571428571428571431]
 				 				* 116 = 83. Value: 0.97142857142857142858 [Distance: 34]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "NOT NULL(surfacearea)" on table "country"
-- * Success: false
-- * Time: 14815ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(21, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'bsib', '', NULL, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(surfacearea). Value: 0.27697262479871175524 [Sum: 0.38307349665924276170]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [21] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 21 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 21, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [21]. Value: 0.48888888888888888889 [Sum: 0.95652173913043478261]
 				 				* 0 = 21. Value: 0.95652173913043478261 [Distance: 22]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'bsib', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.38307349665924276170 [Sum: 0.62093862815884476535]
 		 		* Value: 0.62093862815884476535 [Best: 0.62093862815884476535]
 			 			* Value: 0.62093862815884476535 [Best: 0.62093862815884476535]
 				 				* Value: 0.62093862815884476535 [Best: 0.62093862815884476535]
 					 					* Value: 0.62093862815884476535 [Best: 0.62093862815884476535]
 						 						* Value: 0.62093862815884476535 [Best: 0.62093862815884476535]
 							 							* Value: 0.62093862815884476535 [Best: 0.62093862815884476535]
 								 								* 'bsib' = 'Asia'. Value: 0.62093862815884476535 [Sum: 1.63809523809523809525]
 									 									* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 98 = 97. Value: 0.66666666666666666667 [Distance: 2]
 								 								* 'bsib' = 'Europe'. Value: 0.84756483709702654578 [Sum: 5.56016617790811339200]
 									 									* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 98 = 111. Value: 0.93333333333333333334 [Distance: 14]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'bsib' = 'NorthAAmerica'. Value: 0.92672365937604085712 [Sum: 12.64696969696969696971]
 								 								* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 98 = 116. Value: 0.95000000000000000000 [Distance: 19]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'bsib' = 'Africa'. Value: 0.85080731969860064586 [Sum: 5.70274170274170274172]
 							 							* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 98 = 105. Value: 0.88888888888888888889 [Distance: 8]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'bsib' = 'Oceania'. Value: 0.86480686695278969958 [Sum: 6.39682539682539682542]
 						 						* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 98 = 97. Value: 0.66666666666666666667 [Distance: 2]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'bsib' = 'Antarctica'. Value: 0.90401518880528795444 [Sum: 9.41831501831501831504]
 					 					* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 98 = 97. Value: 0.66666666666666666667 [Distance: 2]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'bsib' = 'SouthAAmerica'. Value: 0.92675646010073551286 [Sum: 12.65308123249299719889]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 98 = 116. Value: 0.95000000000000000000 [Distance: 19]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "NOT NULL(population)" on table "country"
-- * Success: false
-- * Time: 14397ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(-68, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'dsia', '', 0, 0, NULL, 0, 0, 0, '', '', '', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(population). Value: 0.24827586206896551725 [Sum: 0.33027522935779816514]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-68] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -68 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* -68, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [-68]. Value: 0.49640287769784172662 [Sum: 0.98571428571428571429]
 				 				* 0 = -68. Value: 0.98571428571428571429 [Distance: 69]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'dsia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Violate NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33027522935779816514 [Sum: 0.49315068493150684932]
 		 		* Value: 0.49315068493150684932 [Best: 0.49315068493150684932]
 			 			* Value: 0.49315068493150684932 [Best: 0.49315068493150684932]
 				 				* Value: 0.49315068493150684932 [Best: 0.49315068493150684932]
 					 					* Value: 0.49315068493150684932 [Best: 0.49315068493150684932]
 						 						* Value: 0.49315068493150684932 [Best: 0.49315068493150684932]
 							 							* Value: 0.49315068493150684932 [Best: 0.49315068493150684932]
 								 								* 'dsia' = 'Asia'. Value: 0.49315068493150684932 [Sum: 0.97297297297297297298]
 									 									* 100 = 65. Value: 0.97297297297297297298 [Distance: 36]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'dsia' = 'Europe'. Value: 0.84770695125468704933 [Sum: 5.56628787878787878788]
 									 									* 100 = 69. Value: 0.96969696969696969697 [Distance: 32]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'dsia' = 'NorthAAmerica'. Value: 0.92675676746859022631 [Sum: 12.65313852813852813855]
 								 								* 100 = 78. Value: 0.95833333333333333334 [Distance: 23]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'dsia' = 'Africa'. Value: 0.85108848100493932557 [Sum: 5.71539721539721539723]
 							 							* 100 = 65. Value: 0.97297297297297297298 [Distance: 36]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'dsia' = 'Oceania'. Value: 0.85150645624103299857 [Sum: 5.73429951690821256040]
 						 						* 100 = 79. Value: 0.95652173913043478261 [Distance: 22]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'dsia' = 'Antarctica'. Value: 0.89746947227382076190 [Sum: 8.75319275319275319277]
 					 					* 100 = 65. Value: 0.97297297297297297298 [Distance: 36]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'dsia' = 'SouthAAmerica'. Value: 0.92680242157402311503 [Sum: 12.66165413533834586468]
 				 				* 100 = 83. Value: 0.94736842105263157895 [Distance: 18]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "NOT NULL(localname)" on table "country"
-- * Success: false
-- * Time: 15610ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(-30, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'fsja', '', 0, 0, 0, 0, 0, 0, NULL, '', '', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(localname). Value: 0.27705627705627705628 [Sum: 0.38323353293413173653]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-30] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -30 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* -30, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [-30]. Value: 0.49206349206349206350 [Sum: 0.96875000000000000000]
 				 				* 0 = -30. Value: 0.96875000000000000000 [Distance: 31]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'fsja', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Violate NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.38323353293413173653 [Sum: 0.62135922330097087379]
 		 		* Value: 0.62135922330097087379 [Best: 0.62135922330097087379]
 			 			* Value: 0.62135922330097087379 [Best: 0.62135922330097087379]
 				 				* Value: 0.62135922330097087379 [Best: 0.62135922330097087379]
 					 					* Value: 0.62135922330097087379 [Best: 0.62135922330097087379]
 						 						* Value: 0.62135922330097087379 [Best: 0.62135922330097087379]
 							 							* Value: 0.62135922330097087379 [Best: 0.62135922330097087379]
 								 								* 'fsja' = 'Asia'. Value: 0.62135922330097087379 [Sum: 1.64102564102564102565]
 									 									* 102 = 65. Value: 0.97435897435897435898 [Distance: 38]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 106 = 105. Value: 0.66666666666666666667 [Distance: 2]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'fsja' = 'Europe'. Value: 0.84753607405390688811 [Sum: 5.55892857142857142858]
 									 									* 102 = 69. Value: 0.97142857142857142858 [Distance: 34]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 106 = 114. Value: 0.90000000000000000000 [Distance: 9]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'fsja' = 'NorthAAmerica'. Value: 0.92672517916096304051 [Sum: 12.64725274725274725277]
 								 								* 102 = 78. Value: 0.96153846153846153847 [Distance: 25]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 106 = 114. Value: 0.90000000000000000000 [Distance: 9]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'fsja' = 'Africa'. Value: 0.85091743119266055046 [Sum: 5.70769230769230769232]
 							 							* 102 = 65. Value: 0.97435897435897435898 [Distance: 38]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 106 = 114. Value: 0.90000000000000000000 [Distance: 9]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'fsja' = 'Oceania'. Value: 0.85210573266350532889 [Sum: 5.76158730158730158731]
 						 						* 102 = 79. Value: 0.96000000000000000000 [Distance: 24]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 106 = 101. Value: 0.85714285714285714286 [Distance: 6]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'fsja' = 'Antarctica'. Value: 0.89741662752465946454 [Sum: 8.74816849816849816851]
 					 					* 102 = 65. Value: 0.97435897435897435898 [Distance: 38]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 106 = 116. Value: 0.91666666666666666667 [Distance: 11]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'fsja' = 'SouthAAmerica'. Value: 0.92679983912052553962 [Sum: 12.66117216117216117219]
 				 				* 102 = 83. Value: 0.95238095238095238096 [Distance: 20]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 106 = 117. Value: 0.92307692307692307693 [Distance: 12]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "NOT NULL(governmentform)" on table "country"
-- * Success: false
-- * Time: 14961ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(-13, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'csib', '', 0, 0, 0, 0, 0, 0, '', NULL, '', 0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(governmentform). Value: 0.27699530516431924883 [Sum: 0.38311688311688311689]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-13] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -13 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* -13, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [-13]. Value: 0.48275862068965517242 [Sum: 0.93333333333333333334]
 				 				* 0 = -13. Value: 0.93333333333333333334 [Distance: 14]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'csib', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.38311688311688311689 [Sum: 0.62105263157894736843]
 		 		* Value: 0.62105263157894736843 [Best: 0.62105263157894736843]
 			 			* Value: 0.62105263157894736843 [Best: 0.62105263157894736843]
 				 				* Value: 0.62105263157894736843 [Best: 0.62105263157894736843]
 					 					* Value: 0.62105263157894736843 [Best: 0.62105263157894736843]
 						 						* Value: 0.62105263157894736843 [Best: 0.62105263157894736843]
 							 							* Value: 0.62105263157894736843 [Best: 0.62105263157894736843]
 								 								* 'csib' = 'Asia'. Value: 0.62105263157894736843 [Sum: 1.63888888888888888890]
 									 									* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 98 = 97. Value: 0.66666666666666666667 [Distance: 2]
 								 								* 'csib' = 'Europe'. Value: 0.84758825736801085357 [Sum: 5.56117424242424242425]
 									 									* 99 = 69. Value: 0.96875000000000000000 [Distance: 31]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 98 = 111. Value: 0.93333333333333333334 [Distance: 14]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'csib' = 'NorthAAmerica'. Value: 0.92673426934567621181 [Sum: 12.64894598155467720686]
 								 								* 99 = 78. Value: 0.95652173913043478261 [Distance: 22]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 98 = 116. Value: 0.95000000000000000000 [Distance: 19]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'csib' = 'Africa'. Value: 0.85082498304829352822 [Sum: 5.70353535353535353537]
 							 							* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 98 = 105. Value: 0.88888888888888888889 [Distance: 8]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'csib' = 'Oceania'. Value: 0.86484641638225255973 [Sum: 6.39898989898989898992]
 						 						* 99 = 79. Value: 0.95454545454545454546 [Distance: 21]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 98 = 97. Value: 0.66666666666666666667 [Distance: 2]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'csib' = 'Antarctica'. Value: 0.90402250021972870829 [Sum: 9.41910866910866910869]
 					 					* 99 = 65. Value: 0.97222222222222222223 [Distance: 35]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 98 = 97. Value: 0.66666666666666666667 [Distance: 2]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'csib' = 'SouthAAmerica'. Value: 0.92677398733073749056 [Sum: 12.65634920634920634922]
 				 				* 99 = 83. Value: 0.94444444444444444445 [Distance: 17]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 98 = 116. Value: 0.95000000000000000000 [Distance: 19]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "NOT NULL(code2)" on table "country"
-- * Success: false
-- * Time: 15566ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(76, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'jsia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, NULL);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(code2). Value: 0.24852071005917159764 [Sum: 0.33070866141732283465]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [76] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 76 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 76, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [76]. Value: 0.49677419354838709678 [Sum: 0.98717948717948717949]
 				 				* 0 = 76. Value: 0.98717948717948717949 [Distance: 77]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'jsia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33070866141732283465 [Sum: 0.49411764705882352942]
 		 		* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 			 			* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 				 				* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 					 					* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 						 						* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 							 							* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 								 								* 'jsia' = 'Asia'. Value: 0.49411764705882352942 [Sum: 0.97674418604651162791]
 									 									* 106 = 65. Value: 0.97674418604651162791 [Distance: 42]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'jsia' = 'Europe'. Value: 0.84781500121943107998 [Sum: 5.57094988344988344989]
 									 									* 106 = 69. Value: 0.97435897435897435898 [Distance: 38]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'jsia' = 'NorthAAmerica'. Value: 0.92680144495848913113 [Sum: 12.66147186147186147188]
 								 								* 106 = 78. Value: 0.96666666666666666667 [Distance: 29]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'jsia' = 'Africa'. Value: 0.85117205936336462322 [Sum: 5.71916842847075405216]
 							 							* 106 = 65. Value: 0.97674418604651162791 [Distance: 42]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'jsia' = 'Oceania'. Value: 0.85170454545454545455 [Sum: 5.74329501915708812262]
 						 						* 106 = 79. Value: 0.96551724137931034483 [Distance: 28]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'jsia' = 'Antarctica'. Value: 0.89750910186228031117 [Sum: 8.75696396626629184770]
 					 					* 106 = 65. Value: 0.97674418604651162791 [Distance: 42]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'jsia' = 'SouthAAmerica'. Value: 0.92687003760969494359 [Sum: 12.67428571428571428573]
 				 				* 106 = 83. Value: 0.96000000000000000000 [Distance: 24]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1*/ 

-- Negating "CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]" on table "country"
-- * Success: true
-- * Time: 0ms 
INSERT INTO city(id, name, countrycode, district, population) VALUES(22, '', '', '', 0);
INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', '', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[countrycode, language]" on table "countrylanguage"
-- * Success: false
-- * Time: 18438ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(89, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'osha', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('', '', FALSE, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[countrycode, language]. Value: 0.46907216494845360825 [Sum: 0.88349514563106796117]
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [89]. Value: 0.49723756906077348067 [Sum: 0.98901098901098901099]
 				 				* 0 = 89. Value: 0.98901098901098901099 [Distance: 90]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [22]. Value: 0.48936170212765957447 [Sum: 0.95833333333333333334]
 				 				* 0 = 22. Value: 0.95833333333333333334 [Distance: 23]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'osha', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.38349514563106796117 [Sum: 0.62204724409448818898]
 		 		* Value: 0.62204724409448818898 [Best: 0.62204724409448818898]
 			 			* Value: 0.62204724409448818898 [Best: 0.62204724409448818898]
 				 				* Value: 0.62204724409448818898 [Best: 0.62204724409448818898]
 					 					* Value: 0.62204724409448818898 [Best: 0.62204724409448818898]
 						 						* Value: 0.62204724409448818898 [Best: 0.62204724409448818898]
 							 							* Value: 0.62204724409448818898 [Best: 0.62204724409448818898]
 								 								* 'osha' = 'Asia'. Value: 0.62204724409448818898 [Sum: 1.64583333333333333334]
 									 									* 111 = 65. Value: 0.97916666666666666667 [Distance: 47]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 104 = 105. Value: 0.66666666666666666667 [Distance: 2]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'osha' = 'Europe'. Value: 0.84805755395683453238 [Sum: 5.58143939393939393940]
 									 									* 111 = 69. Value: 0.97727272727272727273 [Distance: 43]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 104 = 114. Value: 0.91666666666666666667 [Distance: 11]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'osha' = 'NorthAAmerica'. Value: 0.92686749085843635731 [Sum: 12.67380952380952380955]
 								 								* 111 = 78. Value: 0.97142857142857142858 [Distance: 34]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 104 = 114. Value: 0.91666666666666666667 [Distance: 11]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'osha' = 'Africa'. Value: 0.85139318885448916409 [Sum: 5.72916666666666666668]
 							 							* 111 = 65. Value: 0.97916666666666666667 [Distance: 47]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 104 = 114. Value: 0.91666666666666666667 [Distance: 11]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'osha' = 'Oceania'. Value: 0.85108039711894101616 [Sum: 5.71503267973856209151]
 						 						* 111 = 79. Value: 0.97058823529411764706 [Distance: 33]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 104 = 101. Value: 0.80000000000000000000 [Distance: 4]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'osha' = 'Antarctica'. Value: 0.89759219750076196282 [Sum: 8.76488095238095238096]
 					 					* 111 = 65. Value: 0.97916666666666666667 [Distance: 47]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 104 = 116. Value: 0.92857142857142857143 [Distance: 13]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'osha' = 'SouthAAmerica'. Value: 0.92693110647181628393 [Sum: 12.68571428571428571431]
 				 				* 111 = 83. Value: 0.96666666666666666667 [Distance: 29]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 104 = 117. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [89] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 89 != 0. Value: 0E-20 [Distance: 0]
 			 			* [89] != [22]. Value: 0E-20 [Best: 0E-20]
 				 				* 89 != 22. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 89, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Violate PRIMARY KEY[countrycode, language]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[countrycode, language]. Value: 1
 	 	* Satisfy FOREIGN KEY[countrycode]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [''] = ['']. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(language). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(isofficial). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(percentage). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "FOREIGN KEY[countrycode]" on table "countrylanguage"
-- * Success: false
-- * Time: 18078ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(2, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'jsia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('gqt', '', FALSE, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except FOREIGN KEY[countrycode]. Value: 0.24852071005917159764 [Sum: 0.33070866141732283465]
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [2]. Value: 0.42857142857142857143 [Sum: 0.75000000000000000000]
 				 				* 0 = 2. Value: 0.75000000000000000000 [Distance: 3]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [22]. Value: 0.48936170212765957447 [Sum: 0.95833333333333333334]
 				 				* 0 = 22. Value: 0.95833333333333333334 [Distance: 23]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'jsia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33070866141732283465 [Sum: 0.49411764705882352942]
 		 		* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 			 			* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 				 				* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 					 					* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 						 						* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 							 							* Value: 0.49411764705882352942 [Best: 0.49411764705882352942]
 								 								* 'jsia' = 'Asia'. Value: 0.49411764705882352942 [Sum: 0.97674418604651162791]
 									 									* 106 = 65. Value: 0.97674418604651162791 [Distance: 42]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'jsia' = 'Europe'. Value: 0.84781500121943107998 [Sum: 5.57094988344988344989]
 									 									* 106 = 69. Value: 0.97435897435897435898 [Distance: 38]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'jsia' = 'NorthAAmerica'. Value: 0.92680144495848913113 [Sum: 12.66147186147186147188]
 								 								* 106 = 78. Value: 0.96666666666666666667 [Distance: 29]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'jsia' = 'Africa'. Value: 0.85117205936336462322 [Sum: 5.71916842847075405216]
 							 							* 106 = 65. Value: 0.97674418604651162791 [Distance: 42]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'jsia' = 'Oceania'. Value: 0.85170454545454545455 [Sum: 5.74329501915708812262]
 						 						* 106 = 79. Value: 0.96551724137931034483 [Distance: 28]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'jsia' = 'Antarctica'. Value: 0.89750910186228031117 [Sum: 8.75696396626629184770]
 					 					* 106 = 65. Value: 0.97674418604651162791 [Distance: 42]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'jsia' = 'SouthAAmerica'. Value: 0.92687003760969494359 [Sum: 12.67428571428571428573]
 				 				* 106 = 83. Value: 0.96000000000000000000 [Distance: 24]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [2] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 2 != 0. Value: 0E-20 [Distance: 0]
 			 			* [2] != [22]. Value: 0E-20 [Best: 0E-20]
 				 				* 2 != 22. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 2, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[countrycode, language]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[countrycode, language]. Value: 0
 	 	* Violate FOREIGN KEY[countrycode]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Sum: 0]
 			 			* ['gqt'] != ['']. Value: 0 [Best: 0]
 				 				* 'gqt' != ''. Value: 0 [Best: 0]
 					 					* Compound values are of different sizes. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* 'gqt', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(language). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(isofficial). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(percentage). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(countrycode)" on table "countrylanguage"
-- * Success: false
-- * Time: 17486ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(56, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'lsia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES(NULL, '', FALSE, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(countrycode). Value: 0.24858757062146892656 [Sum: 0.33082706766917293234]
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [56]. Value: 0.49565217391304347827 [Sum: 0.98275862068965517242]
 				 				* 0 = 56. Value: 0.98275862068965517242 [Distance: 57]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [22]. Value: 0.48936170212765957447 [Sum: 0.95833333333333333334]
 				 				* 0 = 22. Value: 0.95833333333333333334 [Distance: 23]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'lsia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33082706766917293234 [Sum: 0.49438202247191011237]
 		 		* Value: 0.49438202247191011237 [Best: 0.49438202247191011237]
 			 			* Value: 0.49438202247191011237 [Best: 0.49438202247191011237]
 				 				* Value: 0.49438202247191011237 [Best: 0.49438202247191011237]
 					 					* Value: 0.49438202247191011237 [Best: 0.49438202247191011237]
 						 						* Value: 0.49438202247191011237 [Best: 0.49438202247191011237]
 							 							* Value: 0.49438202247191011237 [Best: 0.49438202247191011237]
 								 								* 'lsia' = 'Asia'. Value: 0.49438202247191011237 [Sum: 0.97777777777777777778]
 									 									* 108 = 65. Value: 0.97777777777777777778 [Distance: 44]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'lsia' = 'Europe'. Value: 0.84784396415392725356 [Sum: 5.57220066518847006652]
 									 									* 108 = 69. Value: 0.97560975609756097561 [Distance: 40]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'lsia' = 'NorthAAmerica'. Value: 0.92681260581578400214 [Sum: 12.66355519480519480521]
 								 								* 108 = 78. Value: 0.96875000000000000000 [Distance: 31]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'lsia' = 'Africa'. Value: 0.85119494964677589058 [Sum: 5.72020202020202020203]
 							 							* 108 = 65. Value: 0.97777777777777777778 [Distance: 44]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'lsia' = 'Oceania'. Value: 0.85175345377258235920 [Sum: 5.74551971326164874554]
 						 						* 108 = 79. Value: 0.96774193548387096775 [Distance: 30]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'lsia' = 'Antarctica'. Value: 0.89751995795690582848 [Sum: 8.75799755799755799757]
 					 					* 108 = 65. Value: 0.97777777777777777778 [Distance: 44]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'lsia' = 'SouthAAmerica'. Value: 0.92688588007736943908 [Sum: 12.67724867724867724870]
 				 				* 108 = 83. Value: 0.96296296296296296297 [Distance: 26]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [56] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 56 != 0. Value: 0E-20 [Distance: 0]
 			 			* [56] != [22]. Value: 0E-20 [Best: 0E-20]
 				 				* 56 != 22. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 56, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy FOREIGN KEY[countrycode]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [null] = ['']. Value: 0E-20 [Sum: 0]
 				 				* null = ''. Value: 0
 	 	* Violate NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(language). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(isofficial). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(percentage). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(language)" on table "countrylanguage"
-- * Success: false
-- * Time: 16806ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(62, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'bsia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('', NULL, FALSE, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(language). Value: 0.24817518248175182483 [Sum: 0.33009708737864077671]
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [62]. Value: 0.49606299212598425197 [Sum: 0.98437500000000000000]
 				 				* 0 = 62. Value: 0.98437500000000000000 [Distance: 63]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [22]. Value: 0.48936170212765957447 [Sum: 0.95833333333333333334]
 				 				* 0 = 22. Value: 0.95833333333333333334 [Distance: 23]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'bsia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33009708737864077671 [Sum: 0.49275362318840579711]
 		 		* Value: 0.49275362318840579711 [Best: 0.49275362318840579711]
 			 			* Value: 0.49275362318840579711 [Best: 0.49275362318840579711]
 				 				* Value: 0.49275362318840579711 [Best: 0.49275362318840579711]
 					 					* Value: 0.49275362318840579711 [Best: 0.49275362318840579711]
 						 						* Value: 0.49275362318840579711 [Best: 0.49275362318840579711]
 							 							* Value: 0.49275362318840579711 [Best: 0.49275362318840579711]
 								 								* 'bsia' = 'Asia'. Value: 0.49275362318840579711 [Sum: 0.97142857142857142858]
 									 									* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'bsia' = 'Europe'. Value: 0.84766159430406254363 [Sum: 5.56433284457478005866]
 									 									* 98 = 69. Value: 0.96774193548387096775 [Distance: 30]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'bsia' = 'NorthAAmerica'. Value: 0.92673644148430066604 [Sum: 12.64935064935064935067]
 								 								* 98 = 78. Value: 0.95454545454545454546 [Distance: 21]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'bsia' = 'Africa'. Value: 0.85105422657811593269 [Sum: 5.71385281385281385283]
 							 							* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'bsia' = 'Oceania'. Value: 0.85141509433962264151 [Sum: 5.73015873015873015875]
 						 						* 98 = 79. Value: 0.95238095238095238096 [Distance: 20]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'bsia' = 'Antarctica'. Value: 0.89745323416723011044 [Sum: 8.75164835164835164837]
 					 					* 98 = 65. Value: 0.97142857142857142858 [Distance: 34]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'bsia' = 'SouthAAmerica'. Value: 0.92676923076923076924 [Sum: 12.65546218487394957985]
 				 				* 98 = 83. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [62] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 62 != 0. Value: 0E-20 [Distance: 0]
 			 			* [62] != [22]. Value: 0E-20 [Best: 0E-20]
 				 				* 62 != 22. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 62, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy FOREIGN KEY[countrycode]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [''] = ['']. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate NOT NULL(language). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(isofficial). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(percentage). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(isofficial)" on table "countrylanguage"
-- * Success: false
-- * Time: 16835ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(30, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'gsib', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('', '', NULL, 0);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(isofficial). Value: 0.27707454289732770746 [Sum: 0.38326848249027237355]
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [30]. Value: 0.49206349206349206350 [Sum: 0.96875000000000000000]
 				 				* 0 = 30. Value: 0.96875000000000000000 [Distance: 31]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [22]. Value: 0.48936170212765957447 [Sum: 0.95833333333333333334]
 				 				* 0 = 22. Value: 0.95833333333333333334 [Distance: 23]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'gsib', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.38326848249027237355 [Sum: 0.62145110410094637225]
 		 		* Value: 0.62145110410094637225 [Best: 0.62145110410094637225]
 			 			* Value: 0.62145110410094637225 [Best: 0.62145110410094637225]
 				 				* Value: 0.62145110410094637225 [Best: 0.62145110410094637225]
 					 					* Value: 0.62145110410094637225 [Best: 0.62145110410094637225]
 						 						* Value: 0.62145110410094637225 [Best: 0.62145110410094637225]
 							 							* Value: 0.62145110410094637225 [Best: 0.62145110410094637225]
 								 								* 'gsib' = 'Asia'. Value: 0.62145110410094637225 [Sum: 1.64166666666666666667]
 									 									* 103 = 65. Value: 0.97500000000000000000 [Distance: 39]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 98 = 97. Value: 0.66666666666666666667 [Distance: 2]
 								 								* 'gsib' = 'Europe'. Value: 0.84766887213417448839 [Sum: 5.56464646464646464648]
 									 									* 103 = 69. Value: 0.97222222222222222223 [Distance: 35]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 98 = 111. Value: 0.93333333333333333334 [Distance: 14]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'gsib' = 'NorthAAmerica'. Value: 0.92676882867111313847 [Sum: 12.65538720538720538722]
 								 								* 103 = 78. Value: 0.96296296296296296297 [Distance: 26]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 98 = 116. Value: 0.95000000000000000000 [Distance: 19]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'gsib' = 'Africa'. Value: 0.85088677184922995821 [Sum: 5.70631313131313131314]
 							 							* 103 = 65. Value: 0.97500000000000000000 [Distance: 39]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 98 = 105. Value: 0.88888888888888888889 [Distance: 8]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'gsib' = 'Oceania'. Value: 0.86497403346797461051 [Sum: 6.40598290598290598293]
 						 						* 103 = 79. Value: 0.96153846153846153847 [Distance: 25]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 98 = 97. Value: 0.66666666666666666667 [Distance: 2]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'gsib' = 'Antarctica'. Value: 0.90404808140096830600 [Sum: 9.42188644688644688646]
 					 					* 103 = 65. Value: 0.97500000000000000000 [Distance: 39]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 98 = 97. Value: 0.66666666666666666667 [Distance: 2]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'gsib' = 'SouthAAmerica'. Value: 0.92682810940939831167 [Sum: 12.66645021645021645023]
 				 				* 103 = 83. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 98 = 116. Value: 0.95000000000000000000 [Distance: 19]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [30] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* 30 != 0. Value: 0E-20 [Distance: 0]
 			 			* [30] != [22]. Value: 0E-20 [Best: 0E-20]
 				 				* 30 != 22. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 30, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[countrycode, language]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[countrycode, language]. Value: 0
 	 	* Satisfy FOREIGN KEY[countrycode]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [''] = ['']. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(language). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate NOT NULL(isofficial). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(percentage). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0*/ 

-- Negating "NOT NULL(percentage)" on table "countrylanguage"
-- * Success: false
-- * Time: 17246ms 
-- INSERT INTO city(id, name, countrycode, district, population) VALUES(-24, '', '', '', 0);
-- INSERT INTO country(code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) VALUES('', '', 'ssia', '', 0, 0, 0, 0, 0, 0, '', '', '', 0, '');
-- INSERT INTO countrylanguage(countrycode, language, isofficial, percentage) VALUES('', '', FALSE, NULL);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(percentage). Value: 0.24878048780487804879 [Sum: 0.33116883116883116884]
 	 	* Satisfy PRIMARY KEY[code]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[code]. Value: 0
 	 	* Satisfy FOREIGN KEY[capital]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [-24]. Value: 0.49019607843137254903 [Sum: 0.96153846153846153847]
 				 				* 0 = -24. Value: 0.96153846153846153847 [Distance: 25]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [22]. Value: 0.48936170212765957447 [Sum: 0.95833333333333333334]
 				 				* 0 = 22. Value: 0.95833333333333333334 [Distance: 23]
 	 	* Satisfy NOT NULL(code). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(continent). Value: 0E-20 [Sum: 0]
 		 		* 'ssia', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(region). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(surfacearea). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(localname). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(governmentform). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(code2). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy CHECK[(((((((continent = 'Asia') OR (continent = 'Europe')) OR (continent = 'NorthAAmerica')) OR (continent = 'Africa')) OR (continent = 'Oceania')) OR (continent = 'Antarctica')) OR (continent = 'SouthAAmerica'))]. Value: 0.33116883116883116884 [Sum: 0.49514563106796116506]
 		 		* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 			 			* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 				 				* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 					 					* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 						 						* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 							 							* Value: 0.49514563106796116506 [Best: 0.49514563106796116506]
 								 								* 'ssia' = 'Asia'. Value: 0.49514563106796116506 [Sum: 0.98076923076923076924]
 									 									* 115 = 65. Value: 0.98076923076923076924 [Distance: 51]
 									 									* 115 = 115. Value: 0E-20 [Distance: 0]
 									 									* 105 = 105. Value: 0E-20 [Distance: 0]
 									 									* 97 = 97. Value: 0E-20 [Distance: 0]
 								 								* 'ssia' = 'Europe'. Value: 0.84792626728110599079 [Sum: 5.57575757575757575758]
 									 									* 115 = 69. Value: 0.97916666666666666667 [Distance: 47]
 									 									* 115 = 117. Value: 0.75000000000000000000 [Distance: 3]
 									 									* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 									 									* 97 = 111. Value: 0.93750000000000000000 [Distance: 15]
 									 									* Size difference penalty (1). Value: 1
 									 									* Size difference penalty (2). Value: 1
 							 							* 'ssia' = 'NorthAAmerica'. Value: 0.92684263736799152223 [Sum: 12.66916416916416916419]
 								 								* 115 = 78. Value: 0.97435897435897435898 [Distance: 38]
 								 								* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 								 								* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 								 								* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 								 								* Size difference penalty (1). Value: 1
 								 								* Size difference penalty (2). Value: 1
 								 								* Size difference penalty (3). Value: 1
 								 								* Size difference penalty (4). Value: 1
 								 								* Size difference penalty (5). Value: 1
 								 								* Size difference penalty (6). Value: 1
 								 								* Size difference penalty (7). Value: 1
 								 								* Size difference penalty (8). Value: 1
 								 								* Size difference penalty (9). Value: 1
 						 						* 'ssia' = 'Africa'. Value: 0.85126115974690127417 [Sum: 5.72319347319347319349]
 							 							* 115 = 65. Value: 0.98076923076923076924 [Distance: 51]
 							 							* 115 = 102. Value: 0.93333333333333333334 [Distance: 14]
 							 							* 105 = 114. Value: 0.90909090909090909091 [Distance: 10]
 							 							* 97 = 105. Value: 0.90000000000000000000 [Distance: 9]
 							 							* Size difference penalty (1). Value: 1
 							 							* Size difference penalty (2). Value: 1
 					 					* 'ssia' = 'Oceania'. Value: 0.85188393243828497185 [Sum: 5.75146198830409356727]
 						 						* 115 = 79. Value: 0.97368421052631578948 [Distance: 37]
 						 						* 115 = 99. Value: 0.94444444444444444445 [Distance: 17]
 						 						* 105 = 101. Value: 0.83333333333333333334 [Distance: 5]
 						 						* 97 = 97. Value: 0E-20 [Distance: 0]
 						 						* Size difference penalty (1). Value: 1
 						 						* Size difference penalty (2). Value: 1
 						 						* Size difference penalty (3). Value: 1
 				 				* 'ssia' = 'Antarctica'. Value: 0.89755136504362510555 [Sum: 8.76098901098901098903]
 					 					* 115 = 65. Value: 0.98076923076923076924 [Distance: 51]
 					 					* 115 = 110. Value: 0.85714285714285714286 [Distance: 6]
 					 					* 105 = 116. Value: 0.92307692307692307693 [Distance: 12]
 					 					* 97 = 97. Value: 0E-20 [Distance: 0]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 			 			* 'ssia' = 'SouthAAmerica'. Value: 0.92692661958857844643 [Sum: 12.68487394957983193279]
 				 				* 115 = 83. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 115 = 111. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 105 = 117. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 116. Value: 0.95238095238095238096 [Distance: 20]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 				 				* Size difference penalty (3). Value: 1
 				 				* Size difference penalty (4). Value: 1
 				 				* Size difference penalty (5). Value: 1
 				 				* Size difference penalty (6). Value: 1
 				 				* Size difference penalty (7). Value: 1
 				 				* Size difference penalty (8). Value: 1
 				 				* Size difference penalty (9). Value: 1
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-24] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -24 != 0. Value: 0E-20 [Distance: 0]
 			 			* [-24] != [22]. Value: 0E-20 [Best: 0E-20]
 				 				* -24 != 22. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* -24, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(district). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(population). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy PRIMARY KEY[countrycode, language]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[countrycode, language]. Value: 0
 	 	* Satisfy FOREIGN KEY[countrycode]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [''] = ['']. Value: 0E-20 [Sum: 0E-20]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 	 	* Satisfy NOT NULL(countrycode). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(language). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(isofficial). Value: 0E-20 [Sum: 0]
 		 		* FALSE, allowNull: false. Value: 0
 	 	* Violate NOT NULL(percentage). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0*/ 

