/*****************************************
 * Constraint coverage for TweetComplete *
 *****************************************/
DROP TABLE IF EXISTS Expanded_URLS;
DROP TABLE IF EXISTS Tweets;
CREATE TABLE Tweets (
	tweet_id	INT	CONSTRAINT null PRIMARY KEY,
	in_reply_to_status_id	INT,
	in_reply_to_user_id	INT,
	retweeted_status_id	INT,
	retweeted_status_user_id	INT,
	timestamp	DATETIME,
	source	TEXT,
	text	VARCHAR(140)
);
CREATE TABLE Expanded_URLS (
	tweet_id	INT	CONSTRAINT null  REFERENCES Tweets (tweet_id),
	expanded_url	TEXT,
	CONSTRAINT null PRIMARY KEY (tweet_id, expanded_url)
);
-- Coverage: 6/6 (100.00000%) 
-- Time to generate: 142ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 97ms 
-- * Number of objective function evaluations: 70
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{tweet_id}" on table "Tweets"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{tweet_id, expanded_url}" on table "Expanded_URLS"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "FOREIGN KEY{tweet_id}" on table "Expanded_URLS"
-- * Success: true
-- * Time: 39ms 
-- * Number of objective function evaluations: 41
-- * Number of restarts: 0

