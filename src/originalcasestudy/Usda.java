package originalcasestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

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

        Table dataSrcTable = createTable("data_src");

        Column dataSrcId = dataSrcTable.addColumn("datasrc_id", new CharDataType(6));
        dataSrcId.setNotNull();

        Column authors = dataSrcTable.addColumn("authors", new VarCharDataType(100));
        Column title = dataSrcTable.addColumn("title", new VarCharDataType(100));
        title.setNotNull();

        Column year = dataSrcTable.addColumn("year", new IntDataType());
        Column journal = dataSrcTable.addColumn("journal", new VarCharDataType(100));
        Column volCity = dataSrcTable.addColumn("vol_city", new VarCharDataType(100));
        Column issueState = dataSrcTable.addColumn("issue_state", new VarCharDataType(100));
        Column startPage = dataSrcTable.addColumn("start_page", new VarCharDataType(100));
        Column endPage = dataSrcTable.addColumn("end_page", new VarCharDataType(100));

        /*

         CREATE TABLE datsrcln (
         ndb_no character(5) NOT NULL,
         nutr_no character(3) NOT NULL,
         datasrc_id character(6) NOT NULL
         );

         */

        Table dataSrcLnTable = createTable("datasrcln");

        Column ndbNo = dataSrcLnTable.addColumn("ndb_no", new CharDataType(5));
        Column nutrNo = dataSrcLnTable.addColumn("nutr_no", new CharDataType(3));
        Column dataSrcIdLnTable = dataSrcLnTable.addColumn("datasrc_id", new CharDataType(6));

        ndbNo.setNotNull();
        nutrNo.setNotNull();
        dataSrcIdLnTable.setNotNull();

        /*

         CREATE TABLE deriv_cd (
         deriv_cd varchar(100) NOT NULL,
         derivcd_desc varchar(100) NOT NULL
         );

         */

        Table derivCdTable = createTable("deriv_cd");

        Column derivCd = derivCdTable.addColumn("deriv_cd", new VarCharDataType(100));
        Column derivCdDesc = derivCdTable.addColumn("derivcd_desc", new VarCharDataType(100));

        derivCd.setNotNull();
        derivCdDesc.setNotNull();

        /*

         CREATE TABLE fd_group (
         fdgrp_cd character(4) NOT NULL,
         fddrp_desc varchar(100) NOT NULL
         );

         */

        Table fdGroupTable = createTable("fd_group");

        Column fdGrpCd = fdGroupTable.addColumn("fdgrp_cd", new CharDataType(4));
        Column fdDrpDesc = fdGroupTable.addColumn("fddrp_desc", new CharDataType(100));

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

        Table foodDesTable = createTable("food_des");

        Column ndbNoFoodDes = foodDesTable.addColumn("ndb_no", new CharDataType(5));
        ndbNoFoodDes.setNotNull();

        Column fdGrpCdDesTable = foodDesTable.addColumn("fdgrp_cd", new CharDataType(4));
        fdGrpCdDesTable.setNotNull();

        Column longDesc = foodDesTable.addColumn("long_desc", new VarCharDataType(100));
        longDesc.setNotNull();

        Column shrtDesc = foodDesTable.addColumn("shrt_desc", new VarCharDataType(100));
        shrtDesc.setNotNull();

        Column comName = foodDesTable.addColumn("comname", new VarCharDataType(100));
        Column manufacName = foodDesTable.addColumn("manufacname", new VarCharDataType(100));
        Column survey = foodDesTable.addColumn("survey", new CharDataType(1));
        Column refDesc = foodDesTable.addColumn("ref_desc", new VarCharDataType(100));
        Column refuse = foodDesTable.addColumn("refuse", new IntDataType());
        Column sciname = foodDesTable.addColumn("sciname", new VarCharDataType(100));
        Column nFactor = foodDesTable.addColumn("n_factor", new IntDataType());
        Column proFactor = foodDesTable.addColumn("pro_factor", new IntDataType());
        Column fatFactor = foodDesTable.addColumn("fat_factor", new IntDataType());
        Column choFactor = foodDesTable.addColumn("cho_factor", new IntDataType());

        /*

         CREATE TABLE footnote (
         ndb_no character(5) NOT NULL,
         footnt_no character(4) NOT NULL,
         footnt_typ character(1) NOT NULL,
         nutr_no character(3),
         footnt_txt varchar(100) NOT NULL
         );

         */

        Table footnoteTable = createTable("footnote");

        Column ndbNoFootnote = footnoteTable.addColumn("ndb_no", new CharDataType(5));
        ndbNoFootnote.setNotNull();

        Column footNtNo = footnoteTable.addColumn("footnt_no", new CharDataType(4));
        footNtNo.setNotNull();

        Column footNtTyp = footnoteTable.addColumn("footnt_typ", new CharDataType(1));
        footNtTyp.setNotNull();

        Column nutrNoFootnote = footnoteTable.addColumn("nutr_no", new CharDataType(3));

        Column footNtTxt = footnoteTable.addColumn("footnt_txt", new VarCharDataType(100));

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

        Table nutDataTable = createTable("nut_data");

        Column ndbNoNutData = nutDataTable.addColumn("ndb_no", new CharDataType(5));
        ndbNoNutData.setNotNull();

        Column nutrNoNutData = nutDataTable.addColumn("nutr_no", new CharDataType(3));
        nutrNoNutData.setNotNull();

        Column nutrVal = nutDataTable.addColumn("nutr_val", new IntDataType());
        nutrVal.setNotNull();

        Column numDataPoints = nutDataTable.addColumn("num_data_pts", new IntDataType());
        numDataPoints.setNotNull();

        Column stdError = nutDataTable.addColumn("std_error", new IntDataType());
        Column srcCd = nutDataTable.addColumn("src_cd", new IntDataType());
        srcCd.setNotNull();

        Column derivCdNut = nutDataTable.addColumn("deriv_cd", new VarCharDataType(100));
        Column refNdbNo = nutDataTable.addColumn("ref_ndb_no", new CharDataType(5));
        Column addNutrMark = nutDataTable.addColumn("add_nutr_mark", new CharDataType(1));
        Column numStudies = nutDataTable.addColumn("num_studies", new IntDataType());
        Column minNut = nutDataTable.addColumn("minv", new IntDataType());
        Column maxNut = nutDataTable.addColumn("maxv", new IntDataType());
        Column df = nutDataTable.addColumn("df", new IntDataType());
        Column lowEb = nutDataTable.addColumn("low_eb", new IntDataType());
        Column upEb = nutDataTable.addColumn("up_eb", new IntDataType());
        Column statCmt = nutDataTable.addColumn("stat_cmt", new VarCharDataType(100));
        Column cc = nutDataTable.addColumn("cc", new CharDataType(1));

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

        Table nutrDefTable = createTable("nutr_def");

        Column nutrNoDef = nutrDefTable.addColumn("nutr_no", new CharDataType(3));
        nutrNoDef.setNotNull();

        Column units = nutrDefTable.addColumn("units", new VarCharDataType(100));
        units.setNotNull();

        Column tagname = nutrDefTable.addColumn("tagname", new VarCharDataType(100));
        Column nutrdesc = nutrDefTable.addColumn("nutrdesc", new VarCharDataType(100));
        Column numDec = nutrDefTable.addColumn("num_dec", new SmallIntDataType());
        Column srOrder = nutrDefTable.addColumn("sr_order", new IntDataType());

        /*

         CREATE TABLE src_cd (
         src_cd integer NOT NULL,
         srccd_desc varchar(100) NOT NULL
         );

         */

        Table srcCdTable = createTable("src_cd");

        Column srcCdspecial = srcCdTable.addColumn("src_cd", new IntDataType());
        srcCdspecial.setNotNull();

        Column srcCdDesc = srcCdTable.addColumn("srccd_desec", new VarCharDataType(100));
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

        Table weightTable = createTable("weight");

        Column ndbNoWeight = weightTable.addColumn("ndb_no", new CharDataType(5));
        Column seq = weightTable.addColumn("seq", new CharDataType(2));
        Column amount = weightTable.addColumn("amount", new IntDataType());
        Column msreDesc = weightTable.addColumn("msrd_desc", new VarCharDataType(100));
        Column gmWgt = weightTable.addColumn("gm_wgt", new IntDataType());
        Column numDataPointsWeight = weightTable.addColumn("num_data_pts", new IntDataType());
        Column stdDev = weightTable.addColumn("std_dev", new IntDataType());

        ndbNoWeight.setNotNull();
        seq.setNotNull();
        amount.setNotNull();
        msreDesc.setNotNull();
        gmWgt.setNotNull();

    }
}
