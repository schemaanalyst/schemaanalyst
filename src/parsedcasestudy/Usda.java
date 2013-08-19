package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Usda schema.
 * Java code originally generated: 2013/08/17 00:31:07
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
		this.createNotNullConstraint(tableDataSrc, tableDataSrc.getColumn("datasrc_id"));
		this.createNotNullConstraint(tableDataSrc, tableDataSrc.getColumn("title"));

		Table tableDatsrcln = this.createTable("datsrcln");
		tableDatsrcln.createColumn("ndb_no", new CharDataType(5));
		tableDatsrcln.createColumn("nutr_no", new CharDataType(3));
		tableDatsrcln.createColumn("datasrc_id", new CharDataType(6));
		this.createNotNullConstraint(tableDatsrcln, tableDatsrcln.getColumn("ndb_no"));
		this.createNotNullConstraint(tableDatsrcln, tableDatsrcln.getColumn("nutr_no"));
		this.createNotNullConstraint(tableDatsrcln, tableDatsrcln.getColumn("datasrc_id"));

		Table tableDerivCd = this.createTable("deriv_cd");
		tableDerivCd.createColumn("deriv_cd", new VarCharDataType(100));
		tableDerivCd.createColumn("derivcd_desc", new VarCharDataType(100));
		this.createNotNullConstraint(tableDerivCd, tableDerivCd.getColumn("deriv_cd"));
		this.createNotNullConstraint(tableDerivCd, tableDerivCd.getColumn("derivcd_desc"));

		Table tableFdGroup = this.createTable("fd_group");
		tableFdGroup.createColumn("fdgrp_cd", new CharDataType(4));
		tableFdGroup.createColumn("fddrp_desc", new VarCharDataType(100));
		this.createNotNullConstraint(tableFdGroup, tableFdGroup.getColumn("fdgrp_cd"));
		this.createNotNullConstraint(tableFdGroup, tableFdGroup.getColumn("fddrp_desc"));

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
		this.createNotNullConstraint(tableFoodDes, tableFoodDes.getColumn("ndb_no"));
		this.createNotNullConstraint(tableFoodDes, tableFoodDes.getColumn("fdgrp_cd"));
		this.createNotNullConstraint(tableFoodDes, tableFoodDes.getColumn("long_desc"));
		this.createNotNullConstraint(tableFoodDes, tableFoodDes.getColumn("shrt_desc"));

		Table tableFootnote = this.createTable("footnote");
		tableFootnote.createColumn("ndb_no", new CharDataType(5));
		tableFootnote.createColumn("footnt_no", new CharDataType(4));
		tableFootnote.createColumn("footnt_typ", new CharDataType(1));
		tableFootnote.createColumn("nutr_no", new CharDataType(3));
		tableFootnote.createColumn("footnt_txt", new VarCharDataType(100));
		this.createNotNullConstraint(tableFootnote, tableFootnote.getColumn("ndb_no"));
		this.createNotNullConstraint(tableFootnote, tableFootnote.getColumn("footnt_no"));
		this.createNotNullConstraint(tableFootnote, tableFootnote.getColumn("footnt_typ"));
		this.createNotNullConstraint(tableFootnote, tableFootnote.getColumn("footnt_txt"));

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
		this.createNotNullConstraint(tableNutData, tableNutData.getColumn("ndb_no"));
		this.createNotNullConstraint(tableNutData, tableNutData.getColumn("nutr_no"));
		this.createNotNullConstraint(tableNutData, tableNutData.getColumn("nutr_val"));
		this.createNotNullConstraint(tableNutData, tableNutData.getColumn("num_data_pts"));
		this.createNotNullConstraint(tableNutData, tableNutData.getColumn("src_cd"));

		Table tableNutrDef = this.createTable("nutr_def");
		tableNutrDef.createColumn("nutr_no", new CharDataType(3));
		tableNutrDef.createColumn("units", new VarCharDataType(100));
		tableNutrDef.createColumn("tagname", new VarCharDataType(100));
		tableNutrDef.createColumn("nutrdesc", new VarCharDataType(100));
		tableNutrDef.createColumn("num_dec", new SmallIntDataType());
		tableNutrDef.createColumn("sr_order", new IntDataType());
		this.createNotNullConstraint(tableNutrDef, tableNutrDef.getColumn("nutr_no"));
		this.createNotNullConstraint(tableNutrDef, tableNutrDef.getColumn("units"));

		Table tableSrcCd = this.createTable("src_cd");
		tableSrcCd.createColumn("src_cd", new IntDataType());
		tableSrcCd.createColumn("srccd_desc", new VarCharDataType(100));
		this.createNotNullConstraint(tableSrcCd, tableSrcCd.getColumn("src_cd"));
		this.createNotNullConstraint(tableSrcCd, tableSrcCd.getColumn("srccd_desc"));

		Table tableWeight = this.createTable("weight");
		tableWeight.createColumn("ndb_no", new CharDataType(5));
		tableWeight.createColumn("seq", new CharDataType(2));
		tableWeight.createColumn("amount", new IntDataType());
		tableWeight.createColumn("msre_desc", new VarCharDataType(100));
		tableWeight.createColumn("gm_wgt", new IntDataType());
		tableWeight.createColumn("num_data_pts", new IntDataType());
		tableWeight.createColumn("std_dev", new IntDataType());
		this.createNotNullConstraint(tableWeight, tableWeight.getColumn("ndb_no"));
		this.createNotNullConstraint(tableWeight, tableWeight.getColumn("seq"));
		this.createNotNullConstraint(tableWeight, tableWeight.getColumn("amount"));
		this.createNotNullConstraint(tableWeight, tableWeight.getColumn("msre_desc"));
		this.createNotNullConstraint(tableWeight, tableWeight.getColumn("gm_wgt"));
	}
}

