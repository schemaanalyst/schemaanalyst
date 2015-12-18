-- 160
-- UCColumnA
-- Added UNIQUE to column sciname in table food_des

CREATE TABLE "data_src" (
	"datasrc_id"	CHAR(6)	NOT NULL,
	"authors"	VARCHAR(100),
	"title"	VARCHAR(100)	NOT NULL,
	"year"	INT,
	"journal"	VARCHAR(100),
	"vol_city"	VARCHAR(100),
	"issue_state"	VARCHAR(100),
	"start_page"	VARCHAR(100),
	"end_page"	VARCHAR(100)
)

CREATE TABLE "datsrcln" (
	"ndb_no"	CHAR(5)	NOT NULL,
	"nutr_no"	CHAR(3)	NOT NULL,
	"datasrc_id"	CHAR(6)	NOT NULL
)

CREATE TABLE "deriv_cd" (
	"deriv_cd"	VARCHAR(100)	NOT NULL,
	"derivcd_desc"	VARCHAR(100)	NOT NULL
)

CREATE TABLE "fd_group" (
	"fdgrp_cd"	CHAR(4)	NOT NULL,
	"fddrp_desc"	VARCHAR(100)	NOT NULL
)

CREATE TABLE "food_des" (
	"ndb_no"	CHAR(5)	NOT NULL,
	"fdgrp_cd"	CHAR(4)	NOT NULL,
	"long_desc"	VARCHAR(100)	NOT NULL,
	"shrt_desc"	VARCHAR(100)	NOT NULL,
	"comname"	VARCHAR(100),
	"manufacname"	VARCHAR(100),
	"survey"	CHAR(1),
	"ref_desc"	VARCHAR(100),
	"refuse"	INT,
	"sciname"	VARCHAR(100)	UNIQUE,
	"n_factor"	INT,
	"pro_factor"	INT,
	"fat_factor"	INT,
	"cho_factor"	INT
)

CREATE TABLE "footnote" (
	"ndb_no"	CHAR(5)	NOT NULL,
	"footnt_no"	CHAR(4)	NOT NULL,
	"footnt_typ"	CHAR(1)	NOT NULL,
	"nutr_no"	CHAR(3),
	"footnt_txt"	VARCHAR(100)	NOT NULL
)

CREATE TABLE "nut_data" (
	"ndb_no"	CHAR(5)	NOT NULL,
	"nutr_no"	CHAR(3)	NOT NULL,
	"nutr_val"	INT	NOT NULL,
	"num_data_pts"	INT	NOT NULL,
	"std_error"	INT,
	"src_cd"	INT	NOT NULL,
	"deriv_cd"	VARCHAR(100),
	"ref_ndb_no"	CHAR(5),
	"add_nutr_mark"	CHAR(1),
	"num_studies"	INT,
	"min"	INT,
	"max"	INT,
	"df"	INT,
	"low_eb"	INT,
	"up_eb"	INT,
	"stat_cmt"	VARCHAR(100),
	"cc"	CHAR(1)
)

CREATE TABLE "nutr_def" (
	"nutr_no"	CHAR(3)	NOT NULL,
	"units"	VARCHAR(100)	NOT NULL,
	"tagname"	VARCHAR(100),
	"nutrdesc"	VARCHAR(100),
	"num_dec"	SMALLINT,
	"sr_order"	INT
)

CREATE TABLE "src_cd" (
	"src_cd"	INT	NOT NULL,
	"srccd_desc"	VARCHAR(100)	NOT NULL
)

CREATE TABLE "weight" (
	"ndb_no"	CHAR(5)	NOT NULL,
	"seq"	CHAR(2)	NOT NULL,
	"amount"	INT	NOT NULL,
	"msre_desc"	VARCHAR(100)	NOT NULL,
	"gm_wgt"	INT	NOT NULL,
	"num_data_pts"	INT,
	"std_dev"	INT
)

