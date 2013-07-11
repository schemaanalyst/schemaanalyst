package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Usda schema.
 * Java code originally generated: 2013/07/11 20:36:20
 *
 */

@SuppressWarnings("serial")
public class Usda extends Schema {

	public Usda() {
		super("Usda");

		Table tableDataSrc = this.createTable("data_src");
		tableDataSrc.addColumn("datasrc_id", new CharDataType(6));
		tableDataSrc.addColumn("authors", new VarCharDataType(100));
		tableDataSrc.addColumn("title", new VarCharDataType(100));
		tableDataSrc.addColumn("year", new IntDataType());
		tableDataSrc.addColumn("journal", new VarCharDataType(100));
		tableDataSrc.addColumn("vol_city", new VarCharDataType(100));
		tableDataSrc.addColumn("issue_state", new VarCharDataType(100));
		tableDataSrc.addColumn("start_page", new VarCharDataType(100));
		tableDataSrc.addColumn("end_page", new VarCharDataType(100));
		tableDataSrc.addNotNullConstraint(tableDataSrc.getColumn("datasrc_id"));
		tableDataSrc.addNotNullConstraint(tableDataSrc.getColumn("title"));

		Table tableDatsrcln = this.createTable("datsrcln");
		tableDatsrcln.addColumn("ndb_no", new CharDataType(5));
		tableDatsrcln.addColumn("nutr_no", new CharDataType(3));
		tableDatsrcln.addColumn("datasrc_id", new CharDataType(6));
		tableDatsrcln.addNotNullConstraint(tableDatsrcln.getColumn("ndb_no"));
		tableDatsrcln.addNotNullConstraint(tableDatsrcln.getColumn("nutr_no"));
		tableDatsrcln.addNotNullConstraint(tableDatsrcln.getColumn("datasrc_id"));

		Table tableDerivCd = this.createTable("deriv_cd");
		tableDerivCd.addColumn("deriv_cd", new VarCharDataType(100));
		tableDerivCd.addColumn("derivcd_desc", new VarCharDataType(100));
		tableDerivCd.addNotNullConstraint(tableDerivCd.getColumn("deriv_cd"));
		tableDerivCd.addNotNullConstraint(tableDerivCd.getColumn("derivcd_desc"));

		Table tableFdGroup = this.createTable("fd_group");
		tableFdGroup.addColumn("fdgrp_cd", new CharDataType(4));
		tableFdGroup.addColumn("fddrp_desc", new VarCharDataType(100));
		tableFdGroup.addNotNullConstraint(tableFdGroup.getColumn("fdgrp_cd"));
		tableFdGroup.addNotNullConstraint(tableFdGroup.getColumn("fddrp_desc"));

		Table tableFoodDes = this.createTable("food_des");
		tableFoodDes.addColumn("ndb_no", new CharDataType(5));
		tableFoodDes.addColumn("fdgrp_cd", new CharDataType(4));
		tableFoodDes.addColumn("long_desc", new VarCharDataType(100));
		tableFoodDes.addColumn("shrt_desc", new VarCharDataType(100));
		tableFoodDes.addColumn("comname", new VarCharDataType(100));
		tableFoodDes.addColumn("manufacname", new VarCharDataType(100));
		tableFoodDes.addColumn("survey", new CharDataType(1));
		tableFoodDes.addColumn("ref_desc", new VarCharDataType(100));
		tableFoodDes.addColumn("refuse", new IntDataType());
		tableFoodDes.addColumn("sciname", new VarCharDataType(100));
		tableFoodDes.addColumn("n_factor", new IntDataType());
		tableFoodDes.addColumn("pro_factor", new IntDataType());
		tableFoodDes.addColumn("fat_factor", new IntDataType());
		tableFoodDes.addColumn("cho_factor", new IntDataType());
		tableFoodDes.addNotNullConstraint(tableFoodDes.getColumn("ndb_no"));
		tableFoodDes.addNotNullConstraint(tableFoodDes.getColumn("fdgrp_cd"));
		tableFoodDes.addNotNullConstraint(tableFoodDes.getColumn("long_desc"));
		tableFoodDes.addNotNullConstraint(tableFoodDes.getColumn("shrt_desc"));

		Table tableFootnote = this.createTable("footnote");
		tableFootnote.addColumn("ndb_no", new CharDataType(5));
		tableFootnote.addColumn("footnt_no", new CharDataType(4));
		tableFootnote.addColumn("footnt_typ", new CharDataType(1));
		tableFootnote.addColumn("nutr_no", new CharDataType(3));
		tableFootnote.addColumn("footnt_txt", new VarCharDataType(100));
		tableFootnote.addNotNullConstraint(tableFootnote.getColumn("ndb_no"));
		tableFootnote.addNotNullConstraint(tableFootnote.getColumn("footnt_no"));
		tableFootnote.addNotNullConstraint(tableFootnote.getColumn("footnt_typ"));
		tableFootnote.addNotNullConstraint(tableFootnote.getColumn("footnt_txt"));

		Table tableNutData = this.createTable("nut_data");
		tableNutData.addColumn("ndb_no", new CharDataType(5));
		tableNutData.addColumn("nutr_no", new CharDataType(3));
		tableNutData.addColumn("nutr_val", new IntDataType());
		tableNutData.addColumn("num_data_pts", new IntDataType());
		tableNutData.addColumn("std_error", new IntDataType());
		tableNutData.addColumn("src_cd", new IntDataType());
		tableNutData.addColumn("deriv_cd", new VarCharDataType(100));
		tableNutData.addColumn("ref_ndb_no", new CharDataType(5));
		tableNutData.addColumn("add_nutr_mark", new CharDataType(1));
		tableNutData.addColumn("num_studies", new IntDataType());
		tableNutData.addColumn("min", new IntDataType());
		tableNutData.addColumn("max", new IntDataType());
		tableNutData.addColumn("df", new IntDataType());
		tableNutData.addColumn("low_eb", new IntDataType());
		tableNutData.addColumn("up_eb", new IntDataType());
		tableNutData.addColumn("stat_cmt", new VarCharDataType(100));
		tableNutData.addColumn("cc", new CharDataType(1));
		tableNutData.addNotNullConstraint(tableNutData.getColumn("ndb_no"));
		tableNutData.addNotNullConstraint(tableNutData.getColumn("nutr_no"));
		tableNutData.addNotNullConstraint(tableNutData.getColumn("nutr_val"));
		tableNutData.addNotNullConstraint(tableNutData.getColumn("num_data_pts"));
		tableNutData.addNotNullConstraint(tableNutData.getColumn("src_cd"));

		Table tableNutrDef = this.createTable("nutr_def");
		tableNutrDef.addColumn("nutr_no", new CharDataType(3));
		tableNutrDef.addColumn("units", new VarCharDataType(100));
		tableNutrDef.addColumn("tagname", new VarCharDataType(100));
		tableNutrDef.addColumn("nutrdesc", new VarCharDataType(100));
		tableNutrDef.addColumn("num_dec", new SmallIntDataType());
		tableNutrDef.addColumn("sr_order", new IntDataType());
		tableNutrDef.addNotNullConstraint(tableNutrDef.getColumn("nutr_no"));
		tableNutrDef.addNotNullConstraint(tableNutrDef.getColumn("units"));

		Table tableSrcCd = this.createTable("src_cd");
		tableSrcCd.addColumn("src_cd", new IntDataType());
		tableSrcCd.addColumn("srccd_desc", new VarCharDataType(100));
		tableSrcCd.addNotNullConstraint(tableSrcCd.getColumn("src_cd"));
		tableSrcCd.addNotNullConstraint(tableSrcCd.getColumn("srccd_desc"));

		Table tableWeight = this.createTable("weight");
		tableWeight.addColumn("ndb_no", new CharDataType(5));
		tableWeight.addColumn("seq", new CharDataType(2));
		tableWeight.addColumn("amount", new IntDataType());
		tableWeight.addColumn("msre_desc", new VarCharDataType(100));
		tableWeight.addColumn("gm_wgt", new IntDataType());
		tableWeight.addColumn("num_data_pts", new IntDataType());
		tableWeight.addColumn("std_dev", new IntDataType());
		tableWeight.addNotNullConstraint(tableWeight.getColumn("ndb_no"));
		tableWeight.addNotNullConstraint(tableWeight.getColumn("seq"));
		tableWeight.addNotNullConstraint(tableWeight.getColumn("amount"));
		tableWeight.addNotNullConstraint(tableWeight.getColumn("msre_desc"));
		tableWeight.addNotNullConstraint(tableWeight.getColumn("gm_wgt"));
	}
}

