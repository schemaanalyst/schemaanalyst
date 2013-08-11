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
	datasrc_id	CHAR(6)	NOT NULL,
	authors	VARCHAR(100),
	title	VARCHAR(100)	NOT NULL,
	year	INT,
	journal	VARCHAR(100),
	vol_city	VARCHAR(100),
	issue_state	VARCHAR(100),
	start_page	VARCHAR(100),
	end_page	VARCHAR(100)
);
CREATE TABLE datsrcln (
	ndb_no	CHAR(5)	NOT NULL,
	nutr_no	CHAR(3)	NOT NULL,
	datasrc_id	CHAR(6)	NOT NULL
);
CREATE TABLE deriv_cd (
	deriv_cd	VARCHAR(100)	NOT NULL,
	derivcd_desc	VARCHAR(100)	NOT NULL
);
CREATE TABLE fd_group (
	fdgrp_cd	CHAR(4)	NOT NULL,
	fddrp_desc	VARCHAR(100)	NOT NULL
);
CREATE TABLE food_des (
	ndb_no	CHAR(5)	NOT NULL,
	fdgrp_cd	CHAR(4)	NOT NULL,
	long_desc	VARCHAR(100)	NOT NULL,
	shrt_desc	VARCHAR(100)	NOT NULL,
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
	ndb_no	CHAR(5)	NOT NULL,
	footnt_no	CHAR(4)	NOT NULL,
	footnt_typ	CHAR(1)	NOT NULL,
	nutr_no	CHAR(3),
	footnt_txt	VARCHAR(100)	NOT NULL
);
CREATE TABLE nut_data (
	ndb_no	CHAR(5)	NOT NULL,
	nutr_no	CHAR(3)	NOT NULL,
	nutr_val	INT	NOT NULL,
	num_data_pts	INT	NOT NULL,
	std_error	INT,
	src_cd	INT	NOT NULL,
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
	nutr_no	CHAR(3)	NOT NULL,
	units	VARCHAR(100)	NOT NULL,
	tagname	VARCHAR(100),
	nutrdesc	VARCHAR(100),
	num_dec	SMALLINT,
	sr_order	INT
);
CREATE TABLE src_cd (
	src_cd	INT	NOT NULL,
	srccd_desc	VARCHAR(100)	NOT NULL
);
CREATE TABLE weight (
	ndb_no	CHAR(5)	NOT NULL,
	seq	CHAR(2)	NOT NULL,
	amount	INT	NOT NULL,
	msre_desc	VARCHAR(100)	NOT NULL,
	gm_wgt	INT	NOT NULL,
	num_data_pts	INT,
	std_dev	INT
);
-- Coverage: 62/62 (100.00000%) 
-- Time to generate: 29ms 

-- Satisfying all constraints
-- * Success: true
-- * Time: 5ms 
INSERT INTO data_src(datasrc_id, authors, title, year, journal, vol_city, issue_state, start_page, end_page) VALUES('', '', '', 0, '', '', '', '', '');
INSERT INTO data_src(datasrc_id, authors, title, year, journal, vol_city, issue_state, start_page, end_page) VALUES('', '', '', 0, '', '', '', '', '');
INSERT INTO datsrcln(ndb_no, nutr_no, datasrc_id) VALUES('', '', '');
INSERT INTO datsrcln(ndb_no, nutr_no, datasrc_id) VALUES('', '', '');
INSERT INTO deriv_cd(deriv_cd, derivcd_desc) VALUES('', '');
INSERT INTO deriv_cd(deriv_cd, derivcd_desc) VALUES('', '');
INSERT INTO fd_group(fdgrp_cd, fddrp_desc) VALUES('', '');
INSERT INTO fd_group(fdgrp_cd, fddrp_desc) VALUES('', '');
INSERT INTO food_des(ndb_no, fdgrp_cd, long_desc, shrt_desc, comname, manufacname, survey, ref_desc, refuse, sciname, n_factor, pro_factor, fat_factor, cho_factor) VALUES('', '', '', '', '', '', '', '', 0, '', 0, 0, 0, 0);
INSERT INTO food_des(ndb_no, fdgrp_cd, long_desc, shrt_desc, comname, manufacname, survey, ref_desc, refuse, sciname, n_factor, pro_factor, fat_factor, cho_factor) VALUES('', '', '', '', '', '', '', '', 0, '', 0, 0, 0, 0);
INSERT INTO footnote(ndb_no, footnt_no, footnt_typ, nutr_no, footnt_txt) VALUES('', '', '', '', '');
INSERT INTO footnote(ndb_no, footnt_no, footnt_typ, nutr_no, footnt_txt) VALUES('', '', '', '', '');
INSERT INTO nut_data(ndb_no, nutr_no, nutr_val, num_data_pts, std_error, src_cd, deriv_cd, ref_ndb_no, add_nutr_mark, num_studies, min, max, df, low_eb, up_eb, stat_cmt, cc) VALUES('', '', 0, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, '', '');
INSERT INTO nut_data(ndb_no, nutr_no, nutr_val, num_data_pts, std_error, src_cd, deriv_cd, ref_ndb_no, add_nutr_mark, num_studies, min, max, df, low_eb, up_eb, stat_cmt, cc) VALUES('', '', 0, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, '', '');
INSERT INTO nutr_def(nutr_no, units, tagname, nutrdesc, num_dec, sr_order) VALUES('', '', '', '', 0, 0);
INSERT INTO nutr_def(nutr_no, units, tagname, nutrdesc, num_dec, sr_order) VALUES('', '', '', '', 0, 0);
INSERT INTO src_cd(src_cd, srccd_desc) VALUES(0, '');
INSERT INTO src_cd(src_cd, srccd_desc) VALUES(0, '');
INSERT INTO weight(ndb_no, seq, amount, msre_desc, gm_wgt, num_data_pts, std_dev) VALUES('', '', 0, '', 0, 0, 0);
INSERT INTO weight(ndb_no, seq, amount, msre_desc, gm_wgt, num_data_pts, std_dev) VALUES('', '', 0, '', 0, 0, 0);
-- * Number of objective function evaluations: 1
-- * Number of restarts: 0

-- Negating "NOT NULL(datasrc_id)" on table "data_src"
-- * Success: true
-- * Time: 0ms 
INSERT INTO data_src(datasrc_id, authors, title, year, journal, vol_city, issue_state, start_page, end_page) VALUES(NULL, '', '', 0, '', '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(title)" on table "data_src"
-- * Success: true
-- * Time: 1ms 
INSERT INTO data_src(datasrc_id, authors, title, year, journal, vol_city, issue_state, start_page, end_page) VALUES('', '', NULL, 0, '', '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "datsrcln"
-- * Success: true
-- * Time: 1ms 
INSERT INTO datsrcln(ndb_no, nutr_no, datasrc_id) VALUES(NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(nutr_no)" on table "datsrcln"
-- * Success: true
-- * Time: 0ms 
INSERT INTO datsrcln(ndb_no, nutr_no, datasrc_id) VALUES('', NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(datasrc_id)" on table "datsrcln"
-- * Success: true
-- * Time: 1ms 
INSERT INTO datsrcln(ndb_no, nutr_no, datasrc_id) VALUES('', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(deriv_cd)" on table "deriv_cd"
-- * Success: true
-- * Time: 0ms 
INSERT INTO deriv_cd(deriv_cd, derivcd_desc) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(derivcd_desc)" on table "deriv_cd"
-- * Success: true
-- * Time: 1ms 
INSERT INTO deriv_cd(deriv_cd, derivcd_desc) VALUES('', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(fdgrp_cd)" on table "fd_group"
-- * Success: true
-- * Time: 0ms 
INSERT INTO fd_group(fdgrp_cd, fddrp_desc) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(fddrp_desc)" on table "fd_group"
-- * Success: true
-- * Time: 1ms 
INSERT INTO fd_group(fdgrp_cd, fddrp_desc) VALUES('', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "food_des"
-- * Success: true
-- * Time: 0ms 
INSERT INTO food_des(ndb_no, fdgrp_cd, long_desc, shrt_desc, comname, manufacname, survey, ref_desc, refuse, sciname, n_factor, pro_factor, fat_factor, cho_factor) VALUES(NULL, '', '', '', '', '', '', '', 0, '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(fdgrp_cd)" on table "food_des"
-- * Success: true
-- * Time: 0ms 
INSERT INTO food_des(ndb_no, fdgrp_cd, long_desc, shrt_desc, comname, manufacname, survey, ref_desc, refuse, sciname, n_factor, pro_factor, fat_factor, cho_factor) VALUES('', NULL, '', '', '', '', '', '', 0, '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(long_desc)" on table "food_des"
-- * Success: true
-- * Time: 1ms 
INSERT INTO food_des(ndb_no, fdgrp_cd, long_desc, shrt_desc, comname, manufacname, survey, ref_desc, refuse, sciname, n_factor, pro_factor, fat_factor, cho_factor) VALUES('', '', NULL, '', '', '', '', '', 0, '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(shrt_desc)" on table "food_des"
-- * Success: true
-- * Time: 1ms 
INSERT INTO food_des(ndb_no, fdgrp_cd, long_desc, shrt_desc, comname, manufacname, survey, ref_desc, refuse, sciname, n_factor, pro_factor, fat_factor, cho_factor) VALUES('', '', '', NULL, '', '', '', '', 0, '', 0, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "footnote"
-- * Success: true
-- * Time: 0ms 
INSERT INTO footnote(ndb_no, footnt_no, footnt_typ, nutr_no, footnt_txt) VALUES(NULL, '', '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(footnt_no)" on table "footnote"
-- * Success: true
-- * Time: 1ms 
INSERT INTO footnote(ndb_no, footnt_no, footnt_typ, nutr_no, footnt_txt) VALUES('', NULL, '', '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(footnt_typ)" on table "footnote"
-- * Success: true
-- * Time: 1ms 
INSERT INTO footnote(ndb_no, footnt_no, footnt_typ, nutr_no, footnt_txt) VALUES('', '', NULL, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(footnt_txt)" on table "footnote"
-- * Success: true
-- * Time: 1ms 
INSERT INTO footnote(ndb_no, footnt_no, footnt_typ, nutr_no, footnt_txt) VALUES('', '', '', '', NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "nut_data"
-- * Success: true
-- * Time: 1ms 
INSERT INTO nut_data(ndb_no, nutr_no, nutr_val, num_data_pts, std_error, src_cd, deriv_cd, ref_ndb_no, add_nutr_mark, num_studies, min, max, df, low_eb, up_eb, stat_cmt, cc) VALUES(NULL, '', 0, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(nutr_no)" on table "nut_data"
-- * Success: true
-- * Time: 1ms 
INSERT INTO nut_data(ndb_no, nutr_no, nutr_val, num_data_pts, std_error, src_cd, deriv_cd, ref_ndb_no, add_nutr_mark, num_studies, min, max, df, low_eb, up_eb, stat_cmt, cc) VALUES('', NULL, 0, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(nutr_val)" on table "nut_data"
-- * Success: true
-- * Time: 1ms 
INSERT INTO nut_data(ndb_no, nutr_no, nutr_val, num_data_pts, std_error, src_cd, deriv_cd, ref_ndb_no, add_nutr_mark, num_studies, min, max, df, low_eb, up_eb, stat_cmt, cc) VALUES('', '', NULL, 0, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(num_data_pts)" on table "nut_data"
-- * Success: true
-- * Time: 1ms 
INSERT INTO nut_data(ndb_no, nutr_no, nutr_val, num_data_pts, std_error, src_cd, deriv_cd, ref_ndb_no, add_nutr_mark, num_studies, min, max, df, low_eb, up_eb, stat_cmt, cc) VALUES('', '', 0, NULL, 0, 0, '', '', '', 0, 0, 0, 0, 0, 0, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(src_cd)" on table "nut_data"
-- * Success: true
-- * Time: 1ms 
INSERT INTO nut_data(ndb_no, nutr_no, nutr_val, num_data_pts, std_error, src_cd, deriv_cd, ref_ndb_no, add_nutr_mark, num_studies, min, max, df, low_eb, up_eb, stat_cmt, cc) VALUES('', '', 0, 0, 0, NULL, '', '', '', 0, 0, 0, 0, 0, 0, '', '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(nutr_no)" on table "nutr_def"
-- * Success: true
-- * Time: 1ms 
INSERT INTO nutr_def(nutr_no, units, tagname, nutrdesc, num_dec, sr_order) VALUES(NULL, '', '', '', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(units)" on table "nutr_def"
-- * Success: true
-- * Time: 0ms 
INSERT INTO nutr_def(nutr_no, units, tagname, nutrdesc, num_dec, sr_order) VALUES('', NULL, '', '', 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(src_cd)" on table "src_cd"
-- * Success: true
-- * Time: 1ms 
INSERT INTO src_cd(src_cd, srccd_desc) VALUES(NULL, '');
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(srccd_desc)" on table "src_cd"
-- * Success: true
-- * Time: 0ms 
INSERT INTO src_cd(src_cd, srccd_desc) VALUES(0, NULL);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(ndb_no)" on table "weight"
-- * Success: true
-- * Time: 2ms 
INSERT INTO weight(ndb_no, seq, amount, msre_desc, gm_wgt, num_data_pts, std_dev) VALUES(NULL, '', 0, '', 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(seq)" on table "weight"
-- * Success: true
-- * Time: 1ms 
INSERT INTO weight(ndb_no, seq, amount, msre_desc, gm_wgt, num_data_pts, std_dev) VALUES('', NULL, 0, '', 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(amount)" on table "weight"
-- * Success: true
-- * Time: 2ms 
INSERT INTO weight(ndb_no, seq, amount, msre_desc, gm_wgt, num_data_pts, std_dev) VALUES('', '', NULL, '', 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(msre_desc)" on table "weight"
-- * Success: true
-- * Time: 1ms 
INSERT INTO weight(ndb_no, seq, amount, msre_desc, gm_wgt, num_data_pts, std_dev) VALUES('', '', 0, NULL, 0, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

-- Negating "NOT NULL(gm_wgt)" on table "weight"
-- * Success: true
-- * Time: 1ms 
INSERT INTO weight(ndb_no, seq, amount, msre_desc, gm_wgt, num_data_pts, std_dev) VALUES('', '', 0, '', NULL, 0, 0);
-- * Number of objective function evaluations: 2
-- * Number of restarts: 0

