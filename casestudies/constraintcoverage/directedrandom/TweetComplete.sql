/*****************************************
 * Constraint coverage for TweetComplete *
 *****************************************/
DROP TABLE IF EXISTS Expanded_URLS;
DROP TABLE IF EXISTS Tweets;
CREATE TABLE Tweets (
	tweet_id	INT	PRIMARY KEY,
	in_reply_to_status_id	INT,
	in_reply_to_user_id	INT,
	retweeted_status_id	INT,
	retweeted_status_user_id	INT,
	timestamp	DATETIME,
	source	TEXT,
	text	VARCHAR(140)
);
CREATE TABLE Expanded_URLS (
	tweet_id	INT	 REFERENCES Tweets (tweet_id),
	expanded_url	TEXT,
	PRIMARY KEY (tweet_id, expanded_url)
);
-- Coverage: 5/6 (83.33333%) 
-- Time to generate: 5488ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 17ms 
INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(0, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(-52, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
INSERT INTO Expanded_URLS(tweet_id, expanded_url) VALUES(0, '');
INSERT INTO Expanded_URLS(tweet_id, expanded_url) VALUES(0, 'gpyaehj');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[tweet_id]" on table "Tweets"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(0, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[tweet_id, expanded_url]" on table "Expanded_URLS"
-- * Success: false
-- * Time: 5469ms 
-- INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(0, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
-- INSERT INTO Expanded_URLS(tweet_id, expanded_url) VALUES(0, '');
-- * Number of objective function evaluations: 100000
-- * Number of restarts: 0
/* Objective value:
 * Satisfy all except PRIMARY KEY[tweet_id, expanded_url]. Value: 0.20000000000000000001 [Sum: 0.25000000000000000001]
 	 	* Satisfy PRIMARY KEY[tweet_id]. Value: 0.25000000000000000001 [Sum: 0.33333333333333333334]
 		 		* No rows to compare with - evaluating NULL. Value: 0.33333333333333333334 [Sum: 0.50000000000000000000]
 			 			* [0] != [0]. Value: 0.50000000000000000000 [Best: 0.50000000000000000000]
 				 				* 0 != 0. Value: 0.50000000000000000000 [Distance: 1]
 			 			* [0] != [-52]. Value: 0E-20 [Best: 0E-20]
 				 				* 0 != -52. Value: 0E-20 [Distance: 0]
 	 	* Violate PRIMARY KEY[tweet_id, expanded_url]. Value: 0E-20 [Sum: 0E-20]
 		 		* No rows to compare with - evaluating NULL. Value: 0E-20 [Best: 0E-20]
 			 			* [0, ''] = [0, '']. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 				 				* '' = ''. Value: 0E-20 [Sum: 0]
 			 			* [0, ''] = [0, 'gpyaehj']. Value: 0.46666666666666666667 [Sum: 0.87500000000000000000]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 				 				* '' = 'gpyaehj'. Value: 0.87500000000000000000 [Sum: 7]
 					 					* Size difference penalty (1). Value: 1
 					 					* Size difference penalty (2). Value: 1
 					 					* Size difference penalty (3). Value: 1
 					 					* Size difference penalty (4). Value: 1
 					 					* Size difference penalty (5). Value: 1
 					 					* Size difference penalty (6). Value: 1
 					 					* Size difference penalty (7). Value: 1
 	 	* Satisfy FOREIGN KEY[tweet_id]. Value: 0E-20 [Sum: 0E-20]
 		 		* Evaluating row with reference rows. Value: 0E-20 [Best: 0E-20]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [0]. Value: 0E-20 [Sum: 0E-20]
 				 				* 0 = 0. Value: 0E-20 [Distance: 0]
 			 			* [0] = [-52]. Value: 0.49532710280373831776 [Sum: 0.98148148148148148149]
 				 				* 0 = -52. Value: 0.98148148148148148149 [Distance: 53]*/ 

-- Negating "FOREIGN KEY[tweet_id]" on table "Expanded_URLS"
-- * Success: true
-- * Time: 1ms 
INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(-31, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
INSERT INTO Expanded_URLS(tweet_id, expanded_url) VALUES(-20, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

