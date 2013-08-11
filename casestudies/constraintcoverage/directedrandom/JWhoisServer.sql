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
	domain_key	INT	PRIMARY KEY	NOT NULL,
	domain	VARCHAR(255)	NOT NULL,
	registered_date	TIMESTAMP	NOT NULL,
	registerexpire_date	TIMESTAMP	NOT NULL,
	changed	TIMESTAMP	NOT NULL,
	remarks	VARCHAR(255),
	holder	INT	NOT NULL,
	admin_c	INT	NOT NULL,
	tech_c	INT	NOT NULL,
	zone_c	INT	NOT NULL,
	mntnr_fkey	INT	NOT NULL,
	publicviewabledata	SMALLINT	NOT NULL,
	disabled	SMALLINT	NOT NULL
);
CREATE TABLE mntnr (
	mntnr_key	INT	PRIMARY KEY	NOT NULL,
	login	VARCHAR(255)	NOT NULL,
	password	VARCHAR(255)	NOT NULL,
	name	VARCHAR(255)	NOT NULL,
	address	VARCHAR(255)	NOT NULL,
	pcode	VARCHAR(20)	NOT NULL,
	city	VARCHAR(255)	NOT NULL,
	country_fkey	INT	NOT NULL,
	phone	VARCHAR(100)	NOT NULL,
	fax	VARCHAR(100),
	email	VARCHAR(255)	NOT NULL,
	remarks	VARCHAR(255),
	changed	TIMESTAMP	NOT NULL,
	disabled	SMALLINT	NOT NULL
);
CREATE TABLE person (
	person_key	INT	PRIMARY KEY	NOT NULL,
	type_fkey	INT	NOT NULL,
	name	VARCHAR(255)	NOT NULL,
	address	VARCHAR(255)	NOT NULL,
	pcode	VARCHAR(20)	NOT NULL,
	city	VARCHAR(255)	NOT NULL,
	country_fkey	INT	NOT NULL,
	phone	VARCHAR(100)	NOT NULL,
	fax	VARCHAR(100),
	email	VARCHAR(255)	NOT NULL,
	remarks	VARCHAR(255),
	changed	TIMESTAMP	NOT NULL,
	mntnr_fkey	INT	NOT NULL,
	disabled	SMALLINT	NOT NULL
);
CREATE TABLE type (
	type_key	INT	PRIMARY KEY	NOT NULL,
	type	VARCHAR(100)	NOT NULL
);
CREATE TABLE nameserver (
	nameserver_key	INT	PRIMARY KEY	NOT NULL,
	nameserver	VARCHAR(255)	NOT NULL,
	domain_fkey	INT	NOT NULL
);
CREATE TABLE country (
	country_key	INT	PRIMARY KEY	NOT NULL,
	short	VARCHAR(2)	NOT NULL,
	country	VARCHAR(255)	NOT NULL
);
-- Coverage: 100/100 (100.00000%) 
-- Time to generate: 199ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 40ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(0, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, 0, 0, 0, 0);
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(-52, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, 0, 0, 0, 0);
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(0, '', '', '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0);
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(10, '', '', '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0);
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(0, 0, '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(-34, 0, '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
INSERT INTO type(type_key, type) VALUES(0, '');
INSERT INTO type(type_key, type) VALUES(96, '');
INSERT INTO nameserver(nameserver_key, nameserver, domain_fkey) VALUES(0, '', 0);
INSERT INTO nameserver(nameserver_key, nameserver, domain_fkey) VALUES(88, '', 0);
INSERT INTO country(country_key, short, country) VALUES(0, '', '');
INSERT INTO country(country_key, short, country) VALUES(-75, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[domain_key]" on table "domain"
-- * Success: true
-- * Time: 2ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(0, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(domain_key)" on table "domain"
-- * Success: true
-- * Time: 6ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(NULL, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(domain)" on table "domain"
-- * Success: true
-- * Time: 8ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(-96, NULL, '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(registered_date)" on table "domain"
-- * Success: true
-- * Time: 6ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(92, '', NULL, '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(registerexpire_date)" on table "domain"
-- * Success: true
-- * Time: 6ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(25, '', '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00', '', 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(changed)" on table "domain"
-- * Success: true
-- * Time: 7ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(55, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', NULL, '', 0, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(holder)" on table "domain"
-- * Success: true
-- * Time: 6ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(-3, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', NULL, 0, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(admin_c)" on table "domain"
-- * Success: true
-- * Time: 5ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(46, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, NULL, 0, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(tech_c)" on table "domain"
-- * Success: true
-- * Time: 5ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(67, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, NULL, 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(zone_c)" on table "domain"
-- * Success: true
-- * Time: 5ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(79, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, NULL, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(mntnr_fkey)" on table "domain"
-- * Success: true
-- * Time: 4ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(-84, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, 0, NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(publicviewabledata)" on table "domain"
-- * Success: true
-- * Time: 5ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(44, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, 0, 0, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(disabled)" on table "domain"
-- * Success: true
-- * Time: 4ms 
INSERT INTO domain(domain_key, domain, registered_date, registerexpire_date, changed, remarks, holder, admin_c, tech_c, zone_c, mntnr_fkey, publicviewabledata, disabled) VALUES(-72, '', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '1970-01-01 00:00:00', '', 0, 0, 0, 0, 0, 0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[mntnr_key]" on table "mntnr"
-- * Success: true
-- * Time: 2ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(0, '', '', '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(mntnr_key)" on table "mntnr"
-- * Success: true
-- * Time: 3ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(NULL, '', '', '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(login)" on table "mntnr"
-- * Success: true
-- * Time: 3ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(-100, NULL, '', '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(password)" on table "mntnr"
-- * Success: true
-- * Time: 12ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(-33, '', NULL, '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "mntnr"
-- * Success: true
-- * Time: 2ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(94, '', '', NULL, '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(address)" on table "mntnr"
-- * Success: true
-- * Time: 4ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(22, '', '', '', NULL, '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(pcode)" on table "mntnr"
-- * Success: true
-- * Time: 4ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(-57, '', '', '', '', NULL, '', 0, '', '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "mntnr"
-- * Success: true
-- * Time: 2ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(-99, '', '', '', '', '', NULL, 0, '', '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(country_fkey)" on table "mntnr"
-- * Success: true
-- * Time: 3ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(54, '', '', '', '', '', '', NULL, '', '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(phone)" on table "mntnr"
-- * Success: true
-- * Time: 2ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(-58, '', '', '', '', '', '', 0, NULL, '', '', '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(email)" on table "mntnr"
-- * Success: true
-- * Time: 3ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(89, '', '', '', '', '', '', 0, '', '', NULL, '', '1970-01-01 00:00:00', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(changed)" on table "mntnr"
-- * Success: true
-- * Time: 3ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(70, '', '', '', '', '', '', 0, '', '', '', '', NULL, 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(disabled)" on table "mntnr"
-- * Success: true
-- * Time: 3ms 
INSERT INTO mntnr(mntnr_key, login, password, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, disabled) VALUES(98, '', '', '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[person_key]" on table "person"
-- * Success: true
-- * Time: 1ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(0, 0, '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(person_key)" on table "person"
-- * Success: true
-- * Time: 2ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(NULL, 0, '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(type_fkey)" on table "person"
-- * Success: true
-- * Time: 3ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(-66, NULL, '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(name)" on table "person"
-- * Success: true
-- * Time: 2ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(44, 0, NULL, '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(address)" on table "person"
-- * Success: true
-- * Time: 3ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(60, 0, '', NULL, '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(pcode)" on table "person"
-- * Success: true
-- * Time: 2ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(-8, 0, '', '', NULL, '', 0, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(city)" on table "person"
-- * Success: true
-- * Time: 3ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(93, 0, '', '', '', NULL, 0, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 3
-- * Number of restarts: 0

-- Negating "NOT NULL(country_fkey)" on table "person"
-- * Success: true
-- * Time: 2ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(-91, 0, '', '', '', '', NULL, '', '', '', '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(phone)" on table "person"
-- * Success: true
-- * Time: 6ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(94, 0, '', '', '', '', 0, NULL, '', '', '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(email)" on table "person"
-- * Success: true
-- * Time: 3ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(53, 0, '', '', '', '', 0, '', '', NULL, '', '1970-01-01 00:00:00', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(changed)" on table "person"
-- * Success: true
-- * Time: 2ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(-49, 0, '', '', '', '', 0, '', '', '', '', NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(mntnr_fkey)" on table "person"
-- * Success: true
-- * Time: 2ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(69, 0, '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(disabled)" on table "person"
-- * Success: true
-- * Time: 7ms 
INSERT INTO person(person_key, type_fkey, name, address, pcode, city, country_fkey, phone, fax, email, remarks, changed, mntnr_fkey, disabled) VALUES(-83, 0, '', '', '', '', 0, '', '', '', '', '1970-01-01 00:00:00', 0, NULL);
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[type_key]" on table "type"
-- * Success: true
-- * Time: 0ms 
INSERT INTO type(type_key, type) VALUES(0, '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(type_key)" on table "type"
-- * Success: true
-- * Time: 1ms 
INSERT INTO type(type_key, type) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(type)" on table "type"
-- * Success: true
-- * Time: 1ms 
INSERT INTO type(type_key, type) VALUES(-100, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[nameserver_key]" on table "nameserver"
-- * Success: true
-- * Time: 0ms 
INSERT INTO nameserver(nameserver_key, nameserver, domain_fkey) VALUES(0, '', 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(nameserver_key)" on table "nameserver"
-- * Success: true
-- * Time: 1ms 
INSERT INTO nameserver(nameserver_key, nameserver, domain_fkey) VALUES(NULL, '', 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(nameserver)" on table "nameserver"
-- * Success: true
-- * Time: 0ms 
INSERT INTO nameserver(nameserver_key, nameserver, domain_fkey) VALUES(6, NULL, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(domain_fkey)" on table "nameserver"
-- * Success: true
-- * Time: 0ms 
INSERT INTO nameserver(nameserver_key, nameserver, domain_fkey) VALUES(-45, '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "PRIMARY KEY[country_key]" on table "country"
-- * Success: true
-- * Time: 1ms 
INSERT INTO country(country_key, short, country) VALUES(0, '', '');
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(country_key)" on table "country"
-- * Success: true
-- * Time: 0ms 
INSERT INTO country(country_key, short, country) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(short)" on table "country"
-- * Success: true
-- * Time: 1ms 
INSERT INTO country(country_key, short, country) VALUES(21, NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(country)" on table "country"
-- * Success: true
-- * Time: 1ms 
INSERT INTO country(country_key, short, country) VALUES(-92, '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

