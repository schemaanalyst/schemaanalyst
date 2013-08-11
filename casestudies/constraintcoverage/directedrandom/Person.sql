/**********************************
 * Constraint coverage for Person *
 **********************************/
DROP TABLE IF EXISTS person;
CREATE TABLE person (
	id	INT	PRIMARY KEY	NOT NULL,
	last_name	VARCHAR(45)	NOT NULL,
	first_name	VARCHAR(45)	NOT NULL,
	gender	VARCHAR(6)	NOT NULL,
	date_of_birth	DATE	NOT NULL,
	CHECK (gender IN ('Male', 'Female', 'Uknown'))
);
-- Coverage: 2/14 (14.28571%) 
-- Time to generate: 43435ms 

-- Satisfying all constraints
-- * Success: false
-- * Time: 12771ms 
-- INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(0, '', '', 'lale', '1000-01-01');
-- INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(-52, '', '', 'djrorn', '1000-01-01');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all constraints. Value: 0.35792698821025822911 [Sum: 0.55745527632839953147]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Sum: 0E-20]
 			 			* [-52] != [0]. Value: 0E-20 [Best: 0E-20]
 				 				* -52 != 0. Value: 0E-20 [Distance: 0]
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 		 		* -52, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(last_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(first_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(gender). Value: 0E-20 [Sum: 0]
 		 		* 'lale', allowNull: false. Value: 0
 		 		* 'djrorn', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(date_of_birth). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy CHECK[gender IN ('Male', 'Female', 'Uknown')]. Value: 0.55745527632839953147 [Sum: 1.25965862094894352960]
 		 		* gender IN ('Male', 'Female', 'Uknown') goalIsToSatisfy: true allowNull: false. Value: 0.49230769230769230770 [Best: 0.49230769230769230770]
 			 			* 'lale' = 'Male'. Value: 0.49230769230769230770 [Sum: 0.96969696969696969697]
 				 				* 108 = 77. Value: 0.96969696969696969697 [Distance: 32]
 				 				* 97 = 97. Value: 0E-20 [Distance: 0]
 				 				* 108 = 108. Value: 0E-20 [Distance: 0]
 				 				* 101 = 101. Value: 0E-20 [Distance: 0]
 			 			* 'lale' = 'Female'. Value: 0.84147952443857331573 [Sum: 5.30833333333333333335]
 				 				* 108 = 70. Value: 0.97500000000000000000 [Distance: 39]
 				 				* 97 = 101. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 108 = 109. Value: 0.66666666666666666667 [Distance: 2]
 				 				* 101 = 97. Value: 0.83333333333333333334 [Distance: 5]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* 'lale' = 'Uknown'. Value: 0.84717269485481406012 [Sum: 5.54333333333333333334]
 				 				* 108 = 85. Value: 0.96000000000000000000 [Distance: 24]
 				 				* 97 = 107. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 108 = 110. Value: 0.75000000000000000000 [Distance: 3]
 				 				* 101 = 111. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 		 		* gender IN ('Male', 'Female', 'Uknown') goalIsToSatisfy: true allowNull: false. Value: 0.76735092864125122190 [Best: 0.76735092864125122190]
 			 			* 'djrorn' = 'Male'. Value: 0.84986692750392393258 [Sum: 5.66075757575757575758]
 				 				* 100 = 77. Value: 0.96000000000000000000 [Distance: 24]
 				 				* 106 = 97. Value: 0.90909090909090909091 [Distance: 10]
 				 				* 114 = 108. Value: 0.87500000000000000000 [Distance: 7]
 				 				* 111 = 101. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* 'djrorn' = 'Female'. Value: 0.84386287307521703315 [Sum: 5.40462662337662337663]
 				 				* 100 = 70. Value: 0.96875000000000000000 [Distance: 31]
 				 				* 106 = 101. Value: 0.85714285714285714286 [Distance: 6]
 				 				* 114 = 109. Value: 0.85714285714285714286 [Distance: 6]
 				 				* 111 = 97. Value: 0.93750000000000000000 [Distance: 15]
 				 				* 114 = 108. Value: 0.87500000000000000000 [Distance: 7]
 				 				* 110 = 101. Value: 0.90909090909090909091 [Distance: 10]
 			 			* 'djrorn' = 'Uknown'. Value: 0.76735092864125122190 [Sum: 3.29831932773109243699]
 				 				* 100 = 85. Value: 0.94117647058823529412 [Distance: 16]
 				 				* 106 = 107. Value: 0.66666666666666666667 [Distance: 2]
 				 				* 114 = 110. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 111 = 111. Value: 0E-20 [Distance: 0]
 				 				* 114 = 119. Value: 0.85714285714285714286 [Distance: 6]
 				 				* 110 = 110. Value: 0E-20 [Distance: 0]*/ 

-- Negating "PRIMARY KEY[id]" on table "person"
-- * Success: false
-- * Time: 6938ms 
-- INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(0, '', '', 'balf', '1000-01-01');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[id]. Value: 0.46872166817769718949 [Sum: 0.88225255972696245735]
 	 	* Violate PRIMARY KEY[id]. Value: 0.50000000000000000000 [Sum: 1]
 		 		* Violate PRIMARY KEY[id]. Value: 1
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(last_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(first_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(gender). Value: 0E-20 [Sum: 0]
 		 		* 'balf', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(date_of_birth). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy CHECK[gender IN ('Male', 'Female', 'Uknown')]. Value: 0.38225255972696245735 [Sum: 0.61878453038674033150]
 		 		* gender IN ('Male', 'Female', 'Uknown') goalIsToSatisfy: true allowNull: true. Value: 0.61878453038674033150 [Best: 0.61878453038674033150]
 			 			* 'balf' = 'Male'. Value: 0.61878453038674033150 [Sum: 1.62318840579710144928]
 				 				* 98 = 77. Value: 0.95652173913043478261 [Distance: 22]
 				 				* 97 = 97. Value: 0E-20 [Distance: 0]
 				 				* 108 = 108. Value: 0E-20 [Distance: 0]
 				 				* 102 = 101. Value: 0.66666666666666666667 [Distance: 2]
 			 			* 'balf' = 'Female'. Value: 0.84186746987951807229 [Sum: 5.32380952380952380954]
 				 				* 98 = 70. Value: 0.96666666666666666667 [Distance: 29]
 				 				* 97 = 101. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 108 = 109. Value: 0.66666666666666666667 [Distance: 2]
 				 				* 102 = 97. Value: 0.85714285714285714286 [Distance: 6]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* 'balf' = 'Uknown'. Value: 0.84636871508379888269 [Sum: 5.50909090909090909092]
 				 				* 98 = 85. Value: 0.93333333333333333334 [Distance: 14]
 				 				* 97 = 107. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 108 = 110. Value: 0.75000000000000000000 [Distance: 3]
 				 				* 102 = 111. Value: 0.90909090909090909091 [Distance: 10]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1*/ 

-- Negating "NOT NULL(id)" on table "person"
-- * Success: false
-- * Time: 6200ms 
-- INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(NULL, '', '', 'pake', '1000-01-01');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(id). Value: 0.27701674277016742771 [Sum: 0.38315789473684210527]
 	 	* Violate NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(last_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(first_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(gender). Value: 0E-20 [Sum: 0]
 		 		* 'pake', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(date_of_birth). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy CHECK[gender IN ('Male', 'Female', 'Uknown')]. Value: 0.38315789473684210527 [Sum: 0.62116040955631399318]
 		 		* gender IN ('Male', 'Female', 'Uknown') goalIsToSatisfy: true allowNull: true. Value: 0.62116040955631399318 [Best: 0.62116040955631399318]
 			 			* 'pake' = 'Male'. Value: 0.62116040955631399318 [Sum: 1.63963963963963963965]
 				 				* 112 = 77. Value: 0.97297297297297297298 [Distance: 36]
 				 				* 97 = 97. Value: 0E-20 [Distance: 0]
 				 				* 107 = 108. Value: 0.66666666666666666667 [Distance: 2]
 				 				* 101 = 101. Value: 0E-20 [Distance: 0]
 			 			* 'pake' = 'Female'. Value: 0.84360189573459715640 [Sum: 5.39393939393939393941]
 				 				* 112 = 70. Value: 0.97727272727272727273 [Distance: 43]
 				 				* 97 = 101. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 107 = 109. Value: 0.75000000000000000000 [Distance: 3]
 				 				* 101 = 97. Value: 0.83333333333333333334 [Distance: 5]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* 'pake' = 'Uknown'. Value: 0.84845845671485803867 [Sum: 5.59885057471264367817]
 				 				* 112 = 85. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 107. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 107 = 110. Value: 0.80000000000000000000 [Distance: 4]
 				 				* 101 = 111. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1*/ 

-- Negating "NOT NULL(last_name)" on table "person"
-- * Success: false
-- * Time: 6163ms 
-- INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(0, NULL, '', 'aald', '1000-01-01');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(last_name). Value: 0.27648578811369509045 [Sum: 0.38214285714285714286]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Violate NOT NULL(last_name). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(first_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(gender). Value: 0E-20 [Sum: 0]
 		 		* 'aald', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(date_of_birth). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy CHECK[gender IN ('Male', 'Female', 'Uknown')]. Value: 0.38214285714285714286 [Sum: 0.61849710982658959538]
 		 		* gender IN ('Male', 'Female', 'Uknown') goalIsToSatisfy: true allowNull: true. Value: 0.61849710982658959538 [Best: 0.61849710982658959538]
 			 			* 'aald' = 'Male'. Value: 0.61849710982658959538 [Sum: 1.62121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 97. Value: 0E-20 [Distance: 0]
 				 				* 108 = 108. Value: 0E-20 [Distance: 0]
 				 				* 100 = 101. Value: 0.66666666666666666667 [Distance: 2]
 			 			* 'aald' = 'Female'. Value: 0.84039625756741882224 [Sum: 5.26551724137931034484]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 101. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 108 = 109. Value: 0.66666666666666666667 [Distance: 2]
 				 				* 100 = 97. Value: 0.80000000000000000000 [Distance: 4]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* 'aald' = 'Uknown'. Value: 0.84658611969654397303 [Sum: 5.51831501831501831503]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 107. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 108 = 110. Value: 0.75000000000000000000 [Distance: 3]
 				 				* 100 = 111. Value: 0.92307692307692307693 [Distance: 12]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1*/ 

-- Negating "NOT NULL(first_name)" on table "person"
-- * Success: false
-- * Time: 6116ms 
-- INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(0, '', NULL, 'malg', '1000-01-01');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(first_name). Value: 0.27923627684964200478 [Sum: 0.38741721854304635762]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(last_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Violate NOT NULL(first_name). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy NOT NULL(gender). Value: 0E-20 [Sum: 0]
 		 		* 'malg', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(date_of_birth). Value: 0E-20 [Sum: 0]
 		 		* '1000-01-01', allowNull: false. Value: 0
 	 	* Satisfy CHECK[gender IN ('Male', 'Female', 'Uknown')]. Value: 0.38741721854304635762 [Sum: 0.63243243243243243244]
 		 		* gender IN ('Male', 'Female', 'Uknown') goalIsToSatisfy: true allowNull: true. Value: 0.63243243243243243244 [Best: 0.63243243243243243244]
 			 			* 'malg' = 'Male'. Value: 0.63243243243243243244 [Sum: 1.72058823529411764706]
 				 				* 109 = 77. Value: 0.97058823529411764706 [Distance: 33]
 				 				* 97 = 97. Value: 0E-20 [Distance: 0]
 				 				* 108 = 108. Value: 0E-20 [Distance: 0]
 				 				* 103 = 101. Value: 0.75000000000000000000 [Distance: 3]
 			 			* 'malg' = 'Female'. Value: 0.84253480556889102257 [Sum: 5.35060975609756097562]
 				 				* 109 = 70. Value: 0.97560975609756097561 [Distance: 40]
 				 				* 97 = 101. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 108 = 109. Value: 0.66666666666666666667 [Distance: 2]
 				 				* 103 = 97. Value: 0.87500000000000000000 [Distance: 7]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* 'malg' = 'Uknown'. Value: 0.84681853888452474470 [Sum: 5.52820512820512820514]
 				 				* 109 = 85. Value: 0.96153846153846153847 [Distance: 25]
 				 				* 97 = 107. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 108 = 110. Value: 0.75000000000000000000 [Distance: 3]
 				 				* 103 = 111. Value: 0.90000000000000000000 [Distance: 9]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1*/ 

-- Negating "NOT NULL(gender)" on table "person"
-- * Success: true
-- * Time: 0ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(0, '', '', NULL, '1000-01-01');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(date_of_birth)" on table "person"
-- * Success: false
-- * Time: 5247ms 
-- INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(0, '', '', 'aame', NULL);
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except NOT NULL(date_of_birth). Value: 0.27648578811369509045 [Sum: 0.38214285714285714286]
 	 	* Satisfy PRIMARY KEY[id]. Value: 0E-20 [Sum: 0]
 		 		* Satisfy PRIMARY KEY[id]. Value: 0
 	 	* Satisfy NOT NULL(id). Value: 0E-20 [Sum: 0]
 		 		* 0, allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(last_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(first_name). Value: 0E-20 [Sum: 0]
 		 		* '', allowNull: false. Value: 0
 	 	* Satisfy NOT NULL(gender). Value: 0E-20 [Sum: 0]
 		 		* 'aame', allowNull: false. Value: 0
 	 	* Violate NOT NULL(date_of_birth). Value: 0E-20 [Sum: 0]
 		 		* null, allowNull: true. Value: 0
 	 	* Satisfy CHECK[gender IN ('Male', 'Female', 'Uknown')]. Value: 0.38214285714285714286 [Sum: 0.61849710982658959538]
 		 		* gender IN ('Male', 'Female', 'Uknown') goalIsToSatisfy: true allowNull: true. Value: 0.61849710982658959538 [Best: 0.61849710982658959538]
 			 			* 'aame' = 'Male'. Value: 0.61849710982658959538 [Sum: 1.62121212121212121213]
 				 				* 97 = 77. Value: 0.95454545454545454546 [Distance: 21]
 				 				* 97 = 97. Value: 0E-20 [Distance: 0]
 				 				* 109 = 108. Value: 0.66666666666666666667 [Distance: 2]
 				 				* 101 = 101. Value: 0E-20 [Distance: 0]
 			 			* 'aame' = 'Female'. Value: 0.82244897959183673470 [Sum: 4.63218390804597701151]
 				 				* 97 = 70. Value: 0.96551724137931034483 [Distance: 28]
 				 				* 97 = 101. Value: 0.83333333333333333334 [Distance: 5]
 				 				* 109 = 109. Value: 0E-20 [Distance: 0]
 				 				* 101 = 97. Value: 0.83333333333333333334 [Distance: 5]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1
 			 			* 'aame' = 'Uknown'. Value: 0.84444444444444444445 [Sum: 5.42857142857142857144]
 				 				* 97 = 85. Value: 0.92857142857142857143 [Distance: 13]
 				 				* 97 = 107. Value: 0.91666666666666666667 [Distance: 11]
 				 				* 109 = 110. Value: 0.66666666666666666667 [Distance: 2]
 				 				* 101 = 111. Value: 0.91666666666666666667 [Distance: 11]
 				 				* Size difference penalty (1). Value: 1
 				 				* Size difference penalty (2). Value: 1*/ 

-- Negating "CHECK[gender IN ('Male', 'Female', 'Uknown')]" on table "person"
-- * Success: true
-- * Time: 0ms 
INSERT INTO person(id, last_name, first_name, gender, date_of_birth) VALUES(0, '', '', '', '1000-01-01');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

