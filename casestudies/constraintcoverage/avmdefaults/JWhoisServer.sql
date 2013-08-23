/****************************************
 * Constraint coverage for JWhoisServer *
 ****************************************/
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS nameserver;
DROP TABLE IF EXISTS type;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS mntnr;
DROP TABLE IF EXISTS domain;
CREATE TABLE domain (
	domain_key	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	domain	VARCHAR(255)	CONSTRAINT null NOT NULL,
	registered_date	TIMESTAMP	CONSTRAINT null NOT NULL,
	registerexpire_date	TIMESTAMP	CONSTRAINT null NOT NULL,
	changed	TIMESTAMP	CONSTRAINT null NOT NULL,
	remarks	VARCHAR(255),
	holder	INT	CONSTRAINT null NOT NULL,
	admin_c	INT	CONSTRAINT null NOT NULL,
	tech_c	INT	CONSTRAINT null NOT NULL,
	zone_c	INT	CONSTRAINT null NOT NULL,
	mntnr_fkey	INT	CONSTRAINT null NOT NULL,
	publicviewabledata	SMALLINT	CONSTRAINT null NOT NULL,
	disabled	SMALLINT	CONSTRAINT null NOT NULL
);
CREATE TABLE mntnr (
	mntnr_key	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	login	VARCHAR(255)	CONSTRAINT null NOT NULL,
	password	VARCHAR(255)	CONSTRAINT null NOT NULL,
	name	VARCHAR(255)	CONSTRAINT null NOT NULL,
	address	VARCHAR(255)	CONSTRAINT null NOT NULL,
	pcode	VARCHAR(20)	CONSTRAINT null NOT NULL,
	city	VARCHAR(255)	CONSTRAINT null NOT NULL,
	country_fkey	INT	CONSTRAINT null NOT NULL,
	phone	VARCHAR(100)	CONSTRAINT null NOT NULL,
	fax	VARCHAR(100),
	email	VARCHAR(255)	CONSTRAINT null NOT NULL,
	remarks	VARCHAR(255),
	changed	TIMESTAMP	CONSTRAINT null NOT NULL,
	disabled	SMALLINT	CONSTRAINT null NOT NULL
);
CREATE TABLE person (
	person_key	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	type_fkey	INT	CONSTRAINT null NOT NULL,
	name	VARCHAR(255)	CONSTRAINT null NOT NULL,
	address	VARCHAR(255)	CONSTRAINT null NOT NULL,
	pcode	VARCHAR(20)	CONSTRAINT null NOT NULL,
	city	VARCHAR(255)	CONSTRAINT null NOT NULL,
	country_fkey	INT	CONSTRAINT null NOT NULL,
	phone	VARCHAR(100)	CONSTRAINT null NOT NULL,
	fax	VARCHAR(100),
	email	VARCHAR(255)	CONSTRAINT null NOT NULL,
	remarks	VARCHAR(255),
	changed	TIMESTAMP	CONSTRAINT null NOT NULL,
	mntnr_fkey	INT	CONSTRAINT null NOT NULL,
	disabled	SMALLINT	CONSTRAINT null NOT NULL
);
CREATE TABLE type (
	type_key	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	type	VARCHAR(100)	CONSTRAINT null NOT NULL
);
CREATE TABLE nameserver (
	nameserver_key	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	nameserver	VARCHAR(255)	CONSTRAINT null NOT NULL,
	domain_fkey	INT	CONSTRAINT null NOT NULL
);
CREATE TABLE country (
	country_key	INT	CONSTRAINT null PRIMARY KEY	CONSTRAINT null NOT NULL,
	short	VARCHAR(2)	CONSTRAINT null NOT NULL,
	country	VARCHAR(255)	CONSTRAINT null NOT NULL
);
-- Coverage: 100/100 (100.00000%) 
-- Time to generate: 1019ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 838ms 
-- * Number of objective function evaluations: 250
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{domain_key}" on table "domain"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{mntnr_key}" on table "mntnr"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{person_key}" on table "person"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{type_key}" on table "type"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{nameserver_key}" on table "nameserver"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "PRIMARY KEY{country_key}" on table "country"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(domain_key)" on table "domain"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(domain)" on table "domain"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(registered_date)" on table "domain"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(registerexpire_date)" on table "domain"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 13
-- * Number of restarts: 0

-- Negating "NOT NULL(changed)" on table "domain"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 16
-- * Number of restarts: 0

-- Negating "NOT NULL(holder)" on table "domain"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 21
-- * Number of restarts: 0

-- Negating "NOT NULL(admin_c)" on table "domain"
-- * Success: true
-- * Time: 8ms 
-- * Number of objective function evaluations: 24
-- * Number of restarts: 0

-- Negating "NOT NULL(tech_c)" on table "domain"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 27
-- * Number of restarts: 0

-- Negating "NOT NULL(zone_c)" on table "domain"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 30
-- * Number of restarts: 0

-- Negating "NOT NULL(mntnr_fkey)" on table "domain"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 33
-- * Number of restarts: 0

-- Negating "NOT NULL(publicviewabledata)" on table "domain"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 36
-- * Number of restarts: 0

-- Negating "NOT NULL(disabled)" on table "domain"
-- * Success: true
-- * Time: 9ms 
-- * Number of objective function evaluations: 39
-- * Number of restarts: 0

-- Negating "NOT NULL(mntnr_key)" on table "mntnr"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(login)" on table "mntnr"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(password)" on table "mntnr"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "mntnr"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 12
-- * Number of restarts: 0

-- Negating "NOT NULL(address)" on table "mntnr"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 14
-- * Number of restarts: 0

-- Negating "NOT NULL(pcode)" on table "mntnr"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 16
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "mntnr"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 18
-- * Number of restarts: 0

-- Negating "NOT NULL(country_fkey)" on table "mntnr"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 20
-- * Number of restarts: 0

-- Negating "NOT NULL(phone)" on table "mntnr"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 23
-- * Number of restarts: 0

-- Negating "NOT NULL(email)" on table "mntnr"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 27
-- * Number of restarts: 0

-- Negating "NOT NULL(changed)" on table "mntnr"
-- * Success: true
-- * Time: 11ms 
-- * Number of objective function evaluations: 31
-- * Number of restarts: 0

-- Negating "NOT NULL(disabled)" on table "mntnr"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 34
-- * Number of restarts: 0

-- Negating "NOT NULL(person_key)" on table "person"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(type_fkey)" on table "person"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "person"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

-- Negating "NOT NULL(address)" on table "person"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 13
-- * Number of restarts: 0

-- Negating "NOT NULL(pcode)" on table "person"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "person"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 17
-- * Number of restarts: 0

-- Negating "NOT NULL(country_fkey)" on table "person"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 19
-- * Number of restarts: 0

-- Negating "NOT NULL(phone)" on table "person"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 22
-- * Number of restarts: 0

-- Negating "NOT NULL(email)" on table "person"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 26
-- * Number of restarts: 0

-- Negating "NOT NULL(changed)" on table "person"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 30
-- * Number of restarts: 0

-- Negating "NOT NULL(mntnr_fkey)" on table "person"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 33
-- * Number of restarts: 0

-- Negating "NOT NULL(disabled)" on table "person"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 36
-- * Number of restarts: 0

-- Negating "NOT NULL(type_key)" on table "type"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(type)" on table "type"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(nameserver_key)" on table "nameserver"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(nameserver)" on table "nameserver"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(domain_fkey)" on table "nameserver"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(country_key)" on table "country"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(short)" on table "country"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(country)" on table "country"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

