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
-- Coverage: 6/6 (100.00000%) 
-- Time to generate: 108ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 72ms 
INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(1, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(0, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
INSERT INTO Expanded_URLS(tweet_id, expanded_url) VALUES(1, '');
INSERT INTO Expanded_URLS(tweet_id, expanded_url) VALUES(0, '');
-- * Number of objective function evaluations: 70
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[tweet_id]" on table "Tweets"
-- * Success: true
-- * Time: 0ms 
INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(0, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[tweet_id, expanded_url]" on table "Expanded_URLS"
-- * Success: true
-- * Time: 4ms 
INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(-1, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
INSERT INTO Expanded_URLS(tweet_id, expanded_url) VALUES(0, '');
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY[tweet_id]" on table "Expanded_URLS"
-- * Success: true
-- * Time: 32ms 
INSERT INTO Tweets(tweet_id, in_reply_to_status_id, in_reply_to_user_id, retweeted_status_id, retweeted_status_user_id, timestamp, source, text) VALUES(3, 0, 0, 0, 0, '1000-01-01 00:00:00', '', '');
INSERT INTO Expanded_URLS(tweet_id, expanded_url) VALUES(-3, '');
-- * Number of objective function evaluations: 41
-- * Number of restarts: 0

