package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * Flav_R03_1Repaired schema.
 * Java code originally generated: 2013/12/13 10:00:47
 *
 */

@SuppressWarnings("serial")
public class Flav_R03_1Repaired extends Schema {

	public Flav_R03_1Repaired() {
		super("Flav_R03_1Repaired");

		Table tableDataSrc = this.createTable("DATA_SRC");
		tableDataSrc.createColumn("DataSrc_ID", new TextDataType());
		tableDataSrc.createColumn("Authors", new TextDataType());
		tableDataSrc.createColumn("Title", new TextDataType());
		tableDataSrc.createColumn("Jorunal", new TextDataType());
		tableDataSrc.createColumn("Year", new TextDataType());
		tableDataSrc.createColumn("Volume", new TextDataType());
		tableDataSrc.createColumn("Issue", new TextDataType());
		tableDataSrc.createColumn("Start_Page", new TextDataType());
		tableDataSrc.createColumn("Emd_Page", new TextDataType());
		this.createUniqueConstraint("DATA_SRC_PrimaryKey", tableDataSrc, tableDataSrc.getColumn("DataSrc_ID"));

		Table tableDatsrcln = this.createTable("DATSRCLN");
		tableDatsrcln.createColumn("NDB_No", new TextDataType());
		tableDatsrcln.createColumn("Nutr_no", new TextDataType());
		tableDatsrcln.createColumn("DataSrc_ID", new TextDataType());
		this.createUniqueConstraint("DATSRCLN_PrimaryKey", tableDatsrcln, tableDatsrcln.getColumn("NDB_No"), tableDatsrcln.getColumn("Nutr_no"), tableDatsrcln.getColumn("DataSrc_ID"));

		Table tableFdGroup = this.createTable("FD_GROUP");
		tableFdGroup.createColumn("FdGrp_CD", new TextDataType());
		tableFdGroup.createColumn("FdGrp_Desc", new TextDataType());
		this.createUniqueConstraint("FD_GROUP_PrimaryKey", tableFdGroup, tableFdGroup.getColumn("FdGrp_CD"));

		Table tableFlavDat = this.createTable("FLAV_DAT");
		tableFlavDat.createColumn("NDB_No", new TextDataType());
		tableFlavDat.createColumn("Nutr_no", new TextDataType());
		tableFlavDat.createColumn("Nutrient_name", new TextDataType());
		tableFlavDat.createColumn("Flav_Val", new DecimalDataType());
		tableFlavDat.createColumn("Se", new DecimalDataType());
		tableFlavDat.createColumn("n", new IntDataType());
		tableFlavDat.createColumn("Min", new DecimalDataType());
		tableFlavDat.createColumn("CC", new TextDataType());
		tableFlavDat.createColumn("Max", new DecimalDataType());
		this.createUniqueConstraint("FLAV_DAT_PrimaryKey", tableFlavDat, tableFlavDat.getColumn("NDB_No"), tableFlavDat.getColumn("Nutr_no"));

		Table tableFlavInd = this.createTable("FLAV_IND");
		tableFlavInd.createColumn("NDB_No", new TextDataType());
		tableFlavInd.createColumn("DataSrc_ID", new TextDataType());
		tableFlavInd.createColumn("Food_No", new TextDataType());
		tableFlavInd.createColumn("FoodIndiv_Desc", new TextDataType());
		tableFlavInd.createColumn("Method", new TextDataType());
		tableFlavInd.createColumn("Cmpd_Name", new TextDataType());
		tableFlavInd.createColumn("Rptd_CmpdVal", new DecimalDataType());
		tableFlavInd.createColumn("Rptd_Std_Dev", new TextDataType());
		tableFlavInd.createColumn("Num_Data_Pts", new DecimalDataType());
		tableFlavInd.createColumn("LT", new TextDataType());
		tableFlavInd.createColumn("Rptd_Units", new TextDataType());
		tableFlavInd.createColumn("Fresh_Dry_Wt", new TextDataType());
		tableFlavInd.createColumn("Quant_Std", new TextDataType());
		tableFlavInd.createColumn("Conv_Factor_G", new DecimalDataType());
		tableFlavInd.createColumn("Conv_Factor_M", new TextDataType());
		tableFlavInd.createColumn("Conv_Factor_SpGr", new DecimalDataType());
		tableFlavInd.createColumn("Cmpd_Val", new DecimalDataType());
		tableFlavInd.createColumn("Cmpt_StdDev", new TextDataType());

		Table tableFoodDes = this.createTable("FOOD_DES");
		tableFoodDes.createColumn("NDB_No", new TextDataType());
		tableFoodDes.createColumn("FdGrp_Cd", new TextDataType());
		tableFoodDes.createColumn("Long_Desc", new TextDataType());
		tableFoodDes.createColumn("SciName", new TextDataType());
		this.createUniqueConstraint("FOOD_DES_PrimaryKey", tableFoodDes, tableFoodDes.getColumn("NDB_No"));

		Table tableNutrDef = this.createTable("NUTR_DEF");
		tableNutrDef.createColumn("Nutrr_no", new TextDataType());
		tableNutrDef.createColumn("Nutrient_name", new TextDataType());
		tableNutrDef.createColumn("Flav_Class", new TextDataType());
		tableNutrDef.createColumn("Unit", new TextDataType());
		tableNutrDef.createColumn("Tagname", new TextDataType());
		this.createUniqueConstraint("NUTR_DEF_PrimaryKey", tableNutrDef, tableNutrDef.getColumn("Nutrr_no"));
	}
}

