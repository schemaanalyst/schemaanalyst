-- 25
-- PKCColumnR
-- ListElementRemover with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnSupplier - Removed linkid

CREATE TABLE "categorydef" (
	"categoryid"	DECIMAL(2, 0)	PRIMARY KEY	NOT NULL,
	"name"	VARCHAR(32),
	"pos"	CHAR(1)
)

CREATE TABLE "lexlinkref" (
	"synset1id"	DECIMAL(9, 0)	NOT NULL,
	"word1id"	DECIMAL(6, 0)	NOT NULL,
	"synset2id"	DECIMAL(9, 0)	NOT NULL,
	"word2id"	DECIMAL(6, 0)	NOT NULL,
	"linkid"	DECIMAL(2, 0)	NOT NULL,
	PRIMARY KEY ("word1id", "synset1id", "word2id", "synset2id", "linkid")
)

CREATE TABLE "linkdef" (
	"linkid"	DECIMAL(2, 0)	PRIMARY KEY	NOT NULL,
	"name"	VARCHAR(50),
	"recurses"	CHAR(1)	NOT NULL
)

CREATE TABLE "sample" (
	"synsetid"	DECIMAL(9, 0)	NOT NULL,
	"sampleid"	DECIMAL(2, 0)	NOT NULL,
	"sample"	TEXT	NOT NULL,
	PRIMARY KEY ("synsetid", "sampleid")
)

CREATE TABLE "semlinkref" (
	"synset1id"	DECIMAL(9, 0)	NOT NULL,
	"synset2id"	DECIMAL(9, 0)	NOT NULL,
	"linkid"	DECIMAL(2, 0)	NOT NULL,
	PRIMARY KEY ("synset1id", "synset2id")
)

CREATE TABLE "sense" (
	"wordid"	DECIMAL(6, 0)	NOT NULL,
	"casedwordid"	DECIMAL(6, 0),
	"synsetid"	DECIMAL(9, 0)	NOT NULL,
	"rank"	DECIMAL(2, 0)	NOT NULL,
	"lexid"	DECIMAL(2, 0)	NOT NULL,
	"tagcount"	DECIMAL(5, 0),
	PRIMARY KEY ("synsetid", "wordid")
)

CREATE TABLE "synset" (
	"synsetid"	DECIMAL(9, 0)	PRIMARY KEY	NOT NULL,
	"pos"	CHAR(1),
	"categoryid"	DECIMAL(2, 0)	NOT NULL,
	"definition"	TEXT
)

CREATE TABLE "word" (
	"wordid"	DECIMAL(6, 0)	PRIMARY KEY	NOT NULL,
	"lemma"	VARCHAR(80)	UNIQUE	NOT NULL
)

