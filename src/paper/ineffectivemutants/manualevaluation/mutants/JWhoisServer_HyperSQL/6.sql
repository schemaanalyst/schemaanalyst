-- 6
-- PKCColumnA
-- ListElementAdder with ChainedSupplier with PrimaryKeyConstraintSupplier and PrimaryKeyColumnsWithAlternativesSupplier - Added holder

CREATE TABLE "domain" (
	"domain_key"	INT	NOT NULL,
	"domain"	VARCHAR(255)	NOT NULL,
	"registered_date"	TIMESTAMP	NOT NULL,
	"registerexpire_date"	TIMESTAMP	NOT NULL,
	"changed"	TIMESTAMP	NOT NULL,
	"remarks"	VARCHAR(255),
	"holder"	INT	NOT NULL,
	"admin_c"	INT	NOT NULL,
	"tech_c"	INT	NOT NULL,
	"zone_c"	INT	NOT NULL,
	"mntnr_fkey"	INT	NOT NULL,
	"publicviewabledata"	SMALLINT	NOT NULL,
	"disabled"	SMALLINT	NOT NULL,
	PRIMARY KEY ("domain_key", "holder")
)

CREATE TABLE "mntnr" (
	"mntnr_key"	INT	PRIMARY KEY	NOT NULL,
	"login"	VARCHAR(255)	NOT NULL,
	"password"	VARCHAR(255)	NOT NULL,
	"name"	VARCHAR(255)	NOT NULL,
	"address"	VARCHAR(255)	NOT NULL,
	"pcode"	VARCHAR(20)	NOT NULL,
	"city"	VARCHAR(255)	NOT NULL,
	"country_fkey"	INT	NOT NULL,
	"phone"	VARCHAR(100)	NOT NULL,
	"fax"	VARCHAR(100),
	"email"	VARCHAR(255)	NOT NULL,
	"remarks"	VARCHAR(255),
	"changed"	TIMESTAMP	NOT NULL,
	"disabled"	SMALLINT	NOT NULL
)

CREATE TABLE "person" (
	"person_key"	INT	PRIMARY KEY	NOT NULL,
	"type_fkey"	INT	NOT NULL,
	"name"	VARCHAR(255)	NOT NULL,
	"address"	VARCHAR(255)	NOT NULL,
	"pcode"	VARCHAR(20)	NOT NULL,
	"city"	VARCHAR(255)	NOT NULL,
	"country_fkey"	INT	NOT NULL,
	"phone"	VARCHAR(100)	NOT NULL,
	"fax"	VARCHAR(100),
	"email"	VARCHAR(255)	NOT NULL,
	"remarks"	VARCHAR(255),
	"changed"	TIMESTAMP	NOT NULL,
	"mntnr_fkey"	INT	NOT NULL,
	"disabled"	SMALLINT	NOT NULL
)

CREATE TABLE "type" (
	"type_key"	INT	PRIMARY KEY	NOT NULL,
	"type"	VARCHAR(100)	NOT NULL
)

CREATE TABLE "nameserver" (
	"nameserver_key"	INT	PRIMARY KEY	NOT NULL,
	"nameserver"	VARCHAR(255)	NOT NULL,
	"domain_fkey"	INT	NOT NULL
)

CREATE TABLE "country" (
	"country_key"	INT	PRIMARY KEY	NOT NULL,
	"short"	VARCHAR(2)	NOT NULL,
	"country"	VARCHAR(255)	NOT NULL
)

