package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.CharColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.SmallIntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class Usda extends Schema {

    static final long serialVersionUID = 7983320670663364323L;
	
	@SuppressWarnings("unused")
	public Usda() {
		super("Usda");
		
		/*
		  
		  CREATE TABLE data_src (
		  datasrc_id character(6) NOT NULL,
		  authors varchar(100),
		  title varchar(100) NOT NULL,
		  "year" integer,
		  journal varchar(100),
		  vol_city varchar(100),
		  issue_state varchar(100),
		  start_page varchar(100),
		  end_page varchar(100)
		  );

		*/		
		
		Table dataSrcTable = createTable( "data_src");
		
		Column dataSrcId = dataSrcTable.addColumn("datasrc_id", new CharColumnType(6));
		dataSrcId.setNotNull();

		Column authors =  dataSrcTable.addColumn("authors", new VarCharColumnType(100));
		Column title = dataSrcTable.addColumn("title", new VarCharColumnType(100));
                title.setNotNull();
                
		Column year = dataSrcTable.addColumn("year", new IntegerColumnType());
		Column journal = dataSrcTable.addColumn("journal", new VarCharColumnType(100));
		Column volCity = dataSrcTable.addColumn("vol_city", new VarCharColumnType(100));
		Column issueState = dataSrcTable.addColumn("issue_state", new VarCharColumnType(100));
		Column startPage = dataSrcTable.addColumn("start_page", new VarCharColumnType(100));
		Column endPage = dataSrcTable.addColumn("end_page", new VarCharColumnType(100));

		/*

		  CREATE TABLE datsrcln (
		  ndb_no character(5) NOT NULL,
		  nutr_no character(3) NOT NULL,
		  datasrc_id character(6) NOT NULL
		  );

		 */

		Table dataSrcLnTable = createTable( "datasrcln");
		
		Column ndbNo = dataSrcLnTable.addColumn("ndb_no", new CharColumnType(5));
		Column nutrNo = dataSrcLnTable.addColumn("nutr_no", new CharColumnType(3));
		Column dataSrcIdLnTable = dataSrcLnTable.addColumn("datasrc_id", new CharColumnType(6));

		ndbNo.setNotNull();
		nutrNo.setNotNull();
		dataSrcIdLnTable.setNotNull();

		/*

		  CREATE TABLE deriv_cd (
		  deriv_cd varchar(100) NOT NULL,
		  derivcd_desc varchar(100) NOT NULL
		  );

		 */

		Table derivCdTable = createTable( "deriv_cd");
		
		Column derivCd = derivCdTable.addColumn("deriv_cd", new VarCharColumnType(100));
		Column derivCdDesc = derivCdTable.addColumn("derivcd_desc", new VarCharColumnType(100));

		derivCd.setNotNull();
		derivCdDesc.setNotNull();

		/*

		  CREATE TABLE fd_group (
		  fdgrp_cd character(4) NOT NULL,
		  fddrp_desc varchar(100) NOT NULL
		  );

		 */

		Table fdGroupTable = createTable( "fd_group");
		
		Column fdGrpCd = fdGroupTable.addColumn("fdgrp_cd", new CharColumnType(4));
		Column fdDrpDesc = fdGroupTable.addColumn("fddrp_desc", new CharColumnType(100));

		fdGrpCd.setNotNull();
		fdDrpDesc.setNotNull();

		/*

		  CREATE TABLE food_des (
		  ndb_no character(5) NOT NULL,
		  fdgrp_cd character(4) NOT NULL,
		  long_desc varchar(100) NOT NULL,
		  shrt_desc varchar(100) NOT NULL,
		  comname varchar(100),
		  manufacname varchar(100),
		  survey character(1),
		  ref_desc varchar(100),
		  refuse integer,
		  sciname varchar(100),
		  n_factor int,
		  pro_factor int,
		  fat_factor int,
		  cho_factor int
		  );

		 */

		Table foodDesTable = createTable( "food_des");

		Column ndbNoFoodDes = foodDesTable.addColumn("ndb_no", new CharColumnType(5));
		ndbNoFoodDes.setNotNull();
		
		Column fdGrpCdDesTable = foodDesTable.addColumn("fdgrp_cd", new CharColumnType(4));
		fdGrpCdDesTable.setNotNull();

		Column longDesc = foodDesTable.addColumn("long_desc", new VarCharColumnType(100));
		longDesc.setNotNull();
		
		Column shrtDesc = foodDesTable.addColumn("shrt_desc", new VarCharColumnType(100));
		shrtDesc.setNotNull();

		Column comName = foodDesTable.addColumn("comname", new VarCharColumnType(100));
		Column manufacName = foodDesTable.addColumn("manufacname", new VarCharColumnType(100));
		Column survey = foodDesTable.addColumn("survey", new CharColumnType(1));
		Column refDesc = foodDesTable.addColumn("ref_desc", new VarCharColumnType(100));
		Column refuse = foodDesTable.addColumn("refuse", new IntColumnType());
		Column sciname = foodDesTable.addColumn("sciname", new VarCharColumnType(100));
		Column nFactor = foodDesTable.addColumn("n_factor", new IntColumnType());
		Column proFactor = foodDesTable.addColumn("pro_factor", new IntColumnType());
		Column fatFactor = foodDesTable.addColumn("fat_factor", new IntColumnType());
		Column choFactor = foodDesTable.addColumn("cho_factor", new IntColumnType());

		/*

		  CREATE TABLE footnote (
		  ndb_no character(5) NOT NULL,
		  footnt_no character(4) NOT NULL,
		  footnt_typ character(1) NOT NULL,
		  nutr_no character(3),
		  footnt_txt varchar(100) NOT NULL
		  );

		 */

		Table footnoteTable = createTable( "footnote");
                
                Column ndbNoFootnote = footnoteTable.addColumn("ndb_no", new CharColumnType(5));
                ndbNoFootnote.setNotNull();         
                
                Column footNtNo = footnoteTable.addColumn("footnt_no", new CharColumnType(4));
                footNtNo.setNotNull();
                
                Column footNtTyp = footnoteTable.addColumn("footnt_typ", new CharColumnType(1));
                footNtTyp.setNotNull();
                
                Column nutrNoFootnote = footnoteTable.addColumn("nutr_no", new CharColumnType(3));
                
                Column footNtTxt = footnoteTable.addColumn("footnt_txt", new VarCharColumnType(100));	

		/*
		  
		  CREATE TABLE nut_data (
		  ndb_no character(5) NOT NULL,
		  nutr_no character(3) NOT NULL,
		  nutr_val int NOT NULL,
		  num_data_pts int NOT NULL,
		  std_error int,
		  src_cd integer NOT NULL,
		  deriv_cd varchar(100),
		  ref_ndb_no character(5),
		  add_nutr_mark character(1),
		  num_studies integer,
		  min int,
		  max int,
		  df integer,
		  low_eb int,
		  up_eb int,
		  stat_cmt varchar(100),
		  cc character(1)
		  );

		 */

		Table nutDataTable = createTable( "nut_data");

		Column ndbNoNutData = nutDataTable.addColumn("ndb_no", new CharColumnType(5));
		ndbNoNutData.setNotNull();

		Column nutrNoNutData = nutDataTable.addColumn("nutr_no", new CharColumnType(3));
		nutrNoNutData.setNotNull();

		Column nutrVal = nutDataTable.addColumn("nutr_val", new IntColumnType());
		nutrVal.setNotNull();

		Column numDataPoints = nutDataTable.addColumn("num_data_pts", new IntColumnType());
		numDataPoints.setNotNull();

		Column stdError = nutDataTable.addColumn("std_error", new IntColumnType());
		Column srcCd = nutDataTable.addColumn("src_cd", new IntegerColumnType());
		srcCd.setNotNull();

		Column derivCdNut = nutDataTable.addColumn("deriv_cd", new VarCharColumnType(100));
		Column refNdbNo = nutDataTable.addColumn("ref_ndb_no", new CharColumnType(5));
		Column addNutrMark = nutDataTable.addColumn("add_nutr_mark", new CharColumnType(1));
		Column numStudies = nutDataTable.addColumn("num_studies", new IntegerColumnType());
		Column minNut = nutDataTable.addColumn("minv", new IntColumnType());
		Column maxNut = nutDataTable.addColumn("maxv", new IntColumnType());
		Column df = nutDataTable.addColumn("df", new IntegerColumnType());
		Column lowEb = nutDataTable.addColumn("low_eb", new IntColumnType());
		Column upEb = nutDataTable.addColumn("up_eb", new IntColumnType());
		Column statCmt = nutDataTable.addColumn("stat_cmt", new VarCharColumnType(100));
		Column cc = nutDataTable.addColumn("cc", new CharColumnType(1));

		/*

		  CREATE TABLE nutr_def (
		  nutr_no character(3) NOT NULL,
		  units varchar(100) NOT NULL,
		  tagname varchar(100),
		  nutrdesc varchar(100),
		  num_dec smallint,
		  sr_order integer
		  );

		 */

		Table nutrDefTable = createTable( "nutr_def");
		
		Column nutrNoDef = nutrDefTable.addColumn("nutr_no", new CharColumnType(3));
		nutrNoDef.setNotNull();
		
		Column units = nutrDefTable.addColumn("units", new VarCharColumnType(100));
		units.setNotNull();

		Column tagname = nutrDefTable.addColumn("tagname", new VarCharColumnType(100));
		Column nutrdesc = nutrDefTable.addColumn("nutrdesc", new VarCharColumnType(100));
		Column numDec = nutrDefTable.addColumn("num_dec", new SmallIntColumnType());
		Column srOrder = nutrDefTable.addColumn("sr_order", new IntegerColumnType());

		/*

		  CREATE TABLE src_cd (
		  src_cd integer NOT NULL,
		  srccd_desc varchar(100) NOT NULL
		  );

		 */

		Table srcCdTable = createTable( "src_cd");

		Column srcCdspecial = srcCdTable.addColumn("src_cd", new IntegerColumnType());
		srcCdspecial.setNotNull();
		
		Column srcCdDesc = srcCdTable.addColumn("srccd_desec", new VarCharColumnType(100));
		srcCdDesc.setNotNull();

		/*

		  CREATE TABLE weight (
		  ndb_no character(5) NOT NULL,
		  seq character(2) NOT NULL,
		  amount int NOT NULL,
		  msre_desc varchar(100) NOT NULL,
		  gm_wgt int NOT NULL,
		  num_data_pts integer,
		  std_dev int
		  );

		 */

		Table weightTable = createTable( "weight");

		Column ndbNoWeight = weightTable.addColumn("ndb_no", new CharColumnType(5));
		Column seq = weightTable.addColumn("seq", new CharColumnType(2));
		Column amount = weightTable.addColumn("amount", new IntColumnType());
		Column msreDesc = weightTable.addColumn("msrd_desc", new VarCharColumnType(100));
		Column gmWgt = weightTable.addColumn("gm_wgt", new IntColumnType());
		Column numDataPointsWeight = weightTable.addColumn("num_data_pts", new IntegerColumnType());
		Column stdDev = weightTable.addColumn("std_dev", new IntColumnType());

		ndbNoWeight.setNotNull();
		seq.setNotNull();
		amount.setNotNull();
		msreDesc.setNotNull();
		gmWgt.setNotNull();

	}
}
