/********************************
 * Constraint coverage for Usda *
 ********************************/
DROP TABLE IF EXISTS weight;
DROP TABLE IF EXISTS src_cd;
DROP TABLE IF EXISTS nutr_def;
DROP TABLE IF EXISTS nut_data;
DROP TABLE IF EXISTS footnote;
DROP TABLE IF EXISTS food_des;
DROP TABLE IF EXISTS fd_group;
DROP TABLE IF EXISTS deriv_cd;
DROP TABLE IF EXISTS datsrcln;
DROP TABLE IF EXISTS data_src;
CREATE TABLE data_src (
	datasrc_id	CHAR(6)	CONSTRAINT null NOT NULL,
	authors	VARCHAR(100),
	title	VARCHAR(100)	CONSTRAINT null NOT NULL,
	year	INT,
	journal	VARCHAR(100),
	vol_city	VARCHAR(100),
	issue_state	VARCHAR(100),
	start_page	VARCHAR(100),
	end_page	VARCHAR(100)
);
CREATE TABLE datsrcln (
	ndb_no	CHAR(5)	CONSTRAINT null NOT NULL,
	nutr_no	CHAR(3)	CONSTRAINT null NOT NULL,
	datasrc_id	CHAR(6)	CONSTRAINT null NOT NULL
);
CREATE TABLE deriv_cd (
	deriv_cd	VARCHAR(100)	CONSTRAINT null NOT NULL,
	derivcd_desc	VARCHAR(100)	CONSTRAINT null NOT NULL
);
CREATE TABLE fd_group (
	fdgrp_cd	CHAR(4)	CONSTRAINT null NOT NULL,
	fddrp_desc	VARCHAR(100)	CONSTRAINT null NOT NULL
);
CREATE TABLE food_des (
	ndb_no	CHAR(5)	CONSTRAINT null NOT NULL,
	fdgrp_cd	CHAR(4)	CONSTRAINT null NOT NULL,
	long_desc	VARCHAR(100)	CONSTRAINT null NOT NULL,
	shrt_desc	VARCHAR(100)	CONSTRAINT null NOT NULL,
	comname	VARCHAR(100),
	manufacname	VARCHAR(100),
	survey	CHAR(1),
	ref_desc	VARCHAR(100),
	refuse	INT,
	sciname	VARCHAR(100),
	n_factor	INT,
	pro_factor	INT,
	fat_factor	INT,
	cho_factor	INT
);
CREATE TABLE footnote (
	ndb_no	CHAR(5)	CONSTRAINT null NOT NULL,
	footnt_no	CHAR(4)	CONSTRAINT null NOT NULL,
	footnt_typ	CHAR(1)	CONSTRAINT null NOT NULL,
	nutr_no	CHAR(3),
	footnt_txt	VARCHAR(100)	CONSTRAINT null NOT NULL
);
CREATE TABLE nut_data (
	ndb_no	CHAR(5)	CONSTRAINT null NOT NULL,
	nutr_no	CHAR(3)	CONSTRAINT null NOT NULL,
	nutr_val	INT	CONSTRAINT null NOT NULL,
	num_data_pts	INT	CONSTRAINT null NOT NULL,
	std_error	INT,
	src_cd	INT	CONSTRAINT null NOT NULL,
	deriv_cd	VARCHAR(100),
	ref_ndb_no	CHAR(5),
	add_nutr_mark	CHAR(1),
	num_studies	INT,
	min	INT,
	max	INT,
	df	INT,
	low_eb	INT,
	up_eb	INT,
	stat_cmt	VARCHAR(100),
	cc	CHAR(1)
);
CREATE TABLE nutr_def (
	nutr_no	CHAR(3)	CONSTRAINT null NOT NULL,
	units	VARCHAR(100)	CONSTRAINT null NOT NULL,
	tagname	VARCHAR(100),
	nutrdesc	VARCHAR(100),
	num_dec	SMALLINT,
	sr_order	INT
);
CREATE TABLE src_cd (
	src_cd	INT	CONSTRAINT null NOT NULL,
	srccd_desc	VARCHAR(100)	CONSTRAINT null NOT NULL
);
CREATE TABLE weight (
	ndb_no	CHAR(5)	CONSTRAINT null NOT NULL,
	seq	CHAR(2)	CONSTRAINT null NOT NULL,
	amount	INT	CONSTRAINT null NOT NULL,
	msre_desc	VARCHAR(100)	CONSTRAINT null NOT NULL,
	gm_wgt	INT	CONSTRAINT null NOT NULL,
	num_data_pts	INT,
	std_dev	INT
);
-- Coverage: 62/62 (100.00000%) 
-- Time to generate: 89ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 12ms 
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(datasrc_id)" on table "data_src"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(title)" on table "data_src"
-- * Success: true
-- * Time: 4ms 
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "datsrcln"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(nutr_no)" on table "datsrcln"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(datasrc_id)" on table "datsrcln"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "NOT NULL(deriv_cd)" on table "deriv_cd"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(derivcd_desc)" on table "deriv_cd"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(fdgrp_cd)" on table "fd_group"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(fddrp_desc)" on table "fd_group"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "food_des"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(fdgrp_cd)" on table "food_des"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(long_desc)" on table "food_des"
-- * Success: true
-- * Time: 7ms 
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "NOT NULL(shrt_desc)" on table "food_des"
-- * Success: true
-- * Time: 8ms 
-- * Number of objective function evaluations: 8
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "footnote"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(footnt_no)" on table "footnote"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(footnt_typ)" on table "footnote"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "NOT NULL(footnt_txt)" on table "footnote"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 10
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "nut_data"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(nutr_no)" on table "nut_data"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(nutr_val)" on table "nut_data"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "NOT NULL(num_data_pts)" on table "nut_data"
-- * Success: true
-- * Time: 5ms 
-- * Number of objective function evaluations: 9
-- * Number of restarts: 0

-- Negating "NOT NULL(src_cd)" on table "nut_data"
-- * Success: true
-- * Time: 6ms 
-- * Number of objective function evaluations: 15
-- * Number of restarts: 0

-- Negating "NOT NULL(nutr_no)" on table "nutr_def"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(units)" on table "nutr_def"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(src_cd)" on table "src_cd"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(srccd_desc)" on table "src_cd"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 5
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "weight"
-- * Success: true
-- * Time: 0ms 
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(seq)" on table "weight"
-- * Success: true
-- * Time: 1ms 
-- * Number of objective function evaluations: 4
-- * Number of restarts: 0

-- Negating "NOT NULL(amount)" on table "weight"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 6
-- * Number of restarts: 0

-- Negating "NOT NULL(msre_desc)" on table "weight"
-- * Success: true
-- * Time: 2ms 
-- * Number of objective function evaluations: 9
-- * Number of restarts: 0

-- Negating "NOT NULL(gm_wgt)" on table "weight"
-- * Success: true
-- * Time: 3ms 
-- * Number of objective function evaluations: 11
-- * Number of restarts: 0

