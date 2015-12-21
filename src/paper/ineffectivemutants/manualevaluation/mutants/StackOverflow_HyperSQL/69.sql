-- 69
-- NNCA
-- Added NOT NULL to column creation_date in table users

CREATE TABLE "comments" (
	"id"	INT	NOT NULL,
	"post_id"	INT,
	"score"	INT,
	"text"	LONGVARCHAR,
	"creation_date"	DATE,
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
	"body"	LONGVARCHAR,
	"owner_user_id"	INT,
	"last_editor_user_id"	INT,
	"last_editor_display_name"	LONGVARCHAR,
	"last_edit_date"	DATE,
	"last_activity_date"	DATE,
	"community_owned_date"	DATE,
	"closed_date"	DATE,
	"title"	LONGVARCHAR,
	"tags"	LONGVARCHAR,
	"answer_count"	SMALLINT,
	"comment_count"	SMALLINT,
	"favorite_count"	INT
)

CREATE TABLE "users" (
	"id"	INT	NOT NULL,
	"reputation"	INT,
	"creation_date"	DATE	NOT NULL,
	"display_name"	LONGVARCHAR,
	"email_hash"	LONGVARCHAR,
	"last_access_date"	DATE,
	"website_url"	LONGVARCHAR,
	"location"	LONGVARCHAR,
	"age"	SMALLINT,
	"about_me"	LONGVARCHAR,
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

