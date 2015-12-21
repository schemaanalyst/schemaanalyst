-- 91
-- UCColumnA
-- Added UNIQUE to column creation_date in table comments

CREATE TABLE "comments" (
	"id"	INT	NOT NULL,
	"post_id"	INT,
	"score"	INT,
	"text"	TEXT,
	"creation_date"	DATE	UNIQUE,
	"user_id"	INT
)

CREATE TABLE "posts" (
	"id"	INT	NOT NULL,
	"post_type_id"	SMALLINT,
	"parent_id"	INT,
	"accepted_answer_id"	INT,
	"creation_date"	DATE,
	"score"	SMALLINT,
	"view_count"	INT,
	"body"	TEXT,
	"owner_user_id"	INT,
	"last_editor_user_id"	INT,
	"last_editor_display_name"	TEXT,
	"last_edit_date"	DATE,
	"last_activity_date"	DATE,
	"community_owned_date"	DATE,
	"closed_date"	DATE,
	"title"	TEXT,
	"tags"	TEXT,
	"answer_count"	SMALLINT,
	"comment_count"	SMALLINT,
	"favorite_count"	INT
)

CREATE TABLE "users" (
	"id"	INT	NOT NULL,
	"reputation"	INT,
	"creation_date"	DATE,
	"display_name"	TEXT,
	"email_hash"	TEXT,
	"last_access_date"	DATE,
	"website_url"	TEXT,
	"location"	TEXT,
	"age"	SMALLINT,
	"about_me"	TEXT,
	"views"	INT,
	"up_votes"	INT,
	"down_votes"	INT
)

CREATE TABLE "votes" (
	"id"	INT	NOT NULL,
	"post_id"	INT	NOT NULL,
	"vote_type_id"	SMALLINT,
	"creation_date"	DATE
)

