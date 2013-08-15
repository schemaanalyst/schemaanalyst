package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Usda schema.
 * Java code originally generated: 2013/08/15 10:52:22
 *
 */

@SuppressWarnings("serial")
public class Usda extends Schema {

	public Usda() {
		super("Usda");

		Table tableDataSrc = this.createTable("data_src");
		tableDataSrc.createColumn("datasrc_id", new CharDataType(6));
		tableDataSrc.createColumn("authors", new VarCharDataType(100));
		tableDataSrc.createColumn("title", new VarCharDataType(100));
		tableDataSrc.createColumn("year", new IntDataType());
		tableDataSrc.createColumn("journal", new VarCharDataType(100));
		tableDataSrc.createColumn("vol_city", new VarCharDataType(100));
		tableDataSrc.createColumn("issue_state", new VarCharDataType(100));
		tableDataSrc.createColumn("start_page", new VarCharDataType(100));
		tableDataSrc.createColumn("end_page", new VarCharDataType(100));
		tableDataSrc.createNotNullConstraint(tableDataSrc.getColumn("datasrc_id"));
		tableDataSrc.createNotNullConstraint(tableDataSrc.getColumn("title"));

		Table tableDatsrcln = this.createTable("datsrcln");
		tableDatsrcln.createColumn("ndb_no", new CharDataType(5));
		tableDatsrcln.createColumn("nutr_no", new CharDataType(3));
		tableDatsrcln.createColumn("datasrc_id", new CharDataType(6));
		tableDatsrcln.createNotNullConstraint(tableDatsrcln.getColumn("ndb_no"));
		tableDatsrcln.createNotNullConstraint(tableDatsrcln.getColumn("nutr_no"));
		tableDatsrcln.createNotNullConstraint(tableDatsrcln.getColumn("datasrc_id"));

		Table tableDerivCd = this.createTable("deriv_cd");
		tableDerivCd.createColumn("deriv_cd", new VarCharDataType(100));
		tableDerivCd.createColumn("derivcd_desc", new VarCharDataType(100));
		tableDerivCd.createNotNullConstraint(tableDerivCd.getColumn("deriv_cd"));
		tableDerivCd.createNotNullConstraint(tableDerivCd.getColumn("derivcd_desc"));

		Table tableFdGroup = this.createTable("fd_group");
		tableFdGroup.createColumn("fdgrp_cd", new CharDataType(4));
		tableFdGroup.createColumn("fddrp_desc", new VarCharDataType(100));
		tableFdGroup.createNotNullConstraint(tableFdGroup.getColumn("fdgrp_cd"));
		tableFdGroup.createNotNullConstraint(tableFdGroup.getColumn("fddrp_desc"));

		Table tableFoodDes = this.createTable("food_des");
		tableFoodDes.createColumn("ndb_no", new CharDataType(5));
		tableFoodDes.createColumn("fdgrp_cd", new CharDataType(4));
		tableFoodDes.createColumn("long_desc", new VarCharDataType(100));
		tableFoodDes.createColumn("shrt_desc", new VarCharDataType(100));
		tableFoodDes.createColumn("comname", new VarCharDataType(100));
		tableFoodDes.createColumn("manufacname", new VarCharDataType(100));
		tableFoodDes.createColumn("survey", new CharDataType(1));
		tableFoodDes.createColumn("ref_desc", new VarCharDataType(100));
		tableFoodDes.createColumn("refuse", new IntDataType());
		tableFoodDes.createColumn("sciname", new VarCharDataType(100));
		tableFoodDes.createColumn("n_factor", new IntDataType());
		tableFoodDes.createColumn("pro_factor", new IntDataType());
		tableFoodDes.createColumn("fat_factor", new IntDataType());
		tableFoodDes.createColumn("cho_factor", new IntDataType());
		tableFoodDes.createNotNullConstraint(tableFoodDes.getColumn("ndb_no"));
		tableFoodDes.createNotNullConstraint(tableFoodDes.getColumn("fdgrp_cd"));
		tableFoodDes.createNotNullConstraint(tableFoodDes.getColumn("long_desc"));
		tableFoodDes.createNotNullConstraint(tableFoodDes.getColumn("shrt_desc"));

		Table tableFootnote = this.createTable("footnote");
		tableFootnote.createColumn("ndb_no", new CharDataType(5));
		tableFootnote.createColumn("footnt_no", new CharDataType(4));
		tableFootnote.createColumn("footnt_typ", new CharDataType(1));
		tableFootnote.createColumn("nutr_no", new CharDataType(3));
		tableFootnote.createColumn("footnt_txt", new VarCharDataType(100));
		tableFootnote.createNotNullConstraint(tableFootnote.getColumn("ndb_no"));
		tableFootnote.createNotNullConstraint(tableFootnote.getColumn("footnt_no"));
		tableFootnote.createNotNullConstraint(tableFootnote.getColumn("footnt_typ"));
		tableFootnote.createNotNullConstraint(tableFootnote.getColumn("footnt_txt"));

		Table tableNutData = this.createTable("nut_data");
		tableNutData.createColumn("ndb_no", new CharDataType(5));
		tableNutData.createColumn("nutr_no", new CharDataType(3));
		tableNutData.createColumn("nutr_val", new IntDataType());
		tableNutData.createColumn("num_data_pts", new IntDataType());
		tableNutData.createColumn("std_error", new IntDataType());
		tableNutData.createColumn("src_cd", new IntDataType());
		tableNutData.createColumn("deriv_cd", new VarCharDataType(100));
		tableNutData.createColumn("ref_ndb_no", new CharDataType(5));
		tableNutData.createColumn("add_nutr_mark", new CharDataType(1));
		tableNutData.createColumn("num_studies", new IntDataType());
		tableNutData.createColumn("min", new IntDataType());
		tableNutData.createColumn("max", new IntDataType());
		tableNutData.createColumn("df", new IntDataType());
		tableNutData.createColumn("low_eb", new IntDataType());
		tableNutData.createColumn("up_eb", new IntDataType());
		tableNutData.createColumn("stat_cmt", new VarCharDataType(100));
		tableNutData.createColumn("cc", new CharDataType(1));
		tableNutData.createNotNullConstraint(tableNutData.getColumn("ndb_no"));
		tableNutData.createNotNullConstraint(tableNutData.getColumn("nutr_no"));
		tableNutData.createNotNullConstraint(tableNutData.getColumn("nutr_val"));
		tableNutData.createNotNullConstraint(tableNutData.getColumn("num_data_pts"));
		tableNutData.createNotNullConstraint(tableNutData.getColumn("src_cd"));

		Table tableNutrDef = this.createTable("nutr_def");
		tableNutrDef.createColumn("nutr_no", new CharDataType(3));
		tableNutrDef.createColumn("units", new VarCharDataType(100));
		tableNutrDef.createColumn("tagname", new VarCharDataType(100));
		tableNutrDef.createColumn("nutrdesc", new VarCharDataType(100));
		tableNutrDef.createColumn("num_dec", new SmallIntDataType());
		tableNutrDef.createColumn("sr_order", new IntDataType());
		tableNutrDef.createNotNullConstraint(tableNutrDef.getColumn("nutr_no"));
		tableNutrDef.createNotNullConstraint(tableNutrDef.getColumn("units"));

		Table tableSrcCd = this.createTable("src_cd");
		tableSrcCd.createColumn("src_cd", new IntDataType());
		tableSrcCd.createColumn("srccd_desc", new VarCharDataType(100));
		tableSrcCd.createNotNullConstraint(tableSrcCd.getColumn("src_cd"));
		tableSrcCd.createNotNullConstraint(tableSrcCd.getColumn("srccd_desc"));

		Table tableWeight = this.createTable("weight");
		tableWeight.createColumn("ndb_no", new CharDataType(5));
		tableWeight.createColumn("seq", new CharDataType(2));
		tableWeight.createColumn("amount", new IntDataType());
		tableWeight.createColumn("msre_desc", new VarCharDataType(100));
		tableWeight.createColumn("gm_wgt", new IntDataType());
		tableWeight.createColumn("num_data_pts", new IntDataType());
		tableWeight.createColumn("std_dev", new IntDataType());
		tableWeight.createNotNullConstraint(tableWeight.getColumn("ndb_no"));
		tableWeight.createNotNullConstraint(tableWeight.getColumn("seq"));
		tableWeight.createNotNullConstraint(tableWeight.getColumn("amount"));
		tableWeight.createNotNullConstraint(tableWeight.getColumn("msre_desc"));
		tableWeight.createNotNullConstraint(tableWeight.getColumn("gm_wgt"));
	}
}

