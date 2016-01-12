package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * IsoFlav_R2Repaired schema.
 * Java code originally generated: 2013/12/13 10:00:51
 *
 */

@SuppressWarnings("serial")
public class IsoFlav_R2Repaired extends Schema {

	public IsoFlav_R2Repaired() {
		super("IsoFlav_R2");

		Table tableDataSrc = this.createTable("DATA_SRC");
		tableDataSrc.createColumn("DataSrc_ID", new DecimalDataType());
		tableDataSrc.createColumn("Authors", new TextDataType());
		tableDataSrc.createColumn("Title", new TextDataType());
		tableDataSrc.createColumn("Year", new DecimalDataType());
		tableDataSrc.createColumn("Journal", new TextDataType());
		tableDataSrc.createColumn("Vol", new TextDataType());
		tableDataSrc.createColumn("Start_Page", new TextDataType());
		tableDataSrc.createColumn("End_Page", new TextDataType());
		this.createUniqueConstraint("DATA_SRC_PrimaryKey", tableDataSrc, tableDataSrc.getColumn("DataSrc_ID"));

		Table tableDatsrcln = this.createTable("DATSRCLN");
		tableDatsrcln.createColumn("NDB_No", new TextDataType());
		tableDatsrcln.createColumn("Nutr_No", new TextDataType());
		tableDatsrcln.createColumn("DataSrc_ID", new DecimalDataType());

		Table tableFoodDes = this.createTable("FOOD_DES");
		tableFoodDes.createColumn("NDB_No", new TextDataType());
		tableFoodDes.createColumn("FdGrp_Cd", new TextDataType());
		tableFoodDes.createColumn("Long_Desc", new TextDataType());
		this.createUniqueConstraint("FOOD_DES_PrimaryKey", tableFoodDes, tableFoodDes.getColumn("NDB_No"));

		Table tableIsflDat = this.createTable("ISFL_DAT");
		tableIsflDat.createColumn("NDB_No", new TextDataType());
		tableIsflDat.createColumn("Nutr_No", new TextDataType());
		tableIsflDat.createColumn("Isfl_Val", new DecimalDataType());
		tableIsflDat.createColumn("SD", new DecimalDataType());
		tableIsflDat.createColumn("n", new DecimalDataType());
		tableIsflDat.createColumn("Min", new DecimalDataType());
		tableIsflDat.createColumn("Max", new DecimalDataType());
		tableIsflDat.createColumn("CC", new TextDataType());
		tableIsflDat.createColumn("DataSrc_ID", new TextDataType());
		this.createUniqueConstraint("ISFL_DAT_PrimaryKey", tableIsflDat, tableIsflDat.getColumn("NDB_No"), tableIsflDat.getColumn("Nutr_No"));

		Table tableNutrDef = this.createTable("NUTR_DEF");
		tableNutrDef.createColumn("Nutr_no", new TextDataType());
		tableNutrDef.createColumn("NutrDesc", new TextDataType());
		tableNutrDef.createColumn("Unit", new TextDataType());
		this.createUniqueConstraint("NUTR_DEF_PrimaryKey", tableNutrDef, tableNutrDef.getColumn("Nutr_no"));

		Table tableSybnDtl = this.createTable("SYBN_DTL");
		tableSybnDtl.createColumn("NDB_No", new TextDataType());
		tableSybnDtl.createColumn("Nutr_No", new TextDataType());
		tableSybnDtl.createColumn("DataSrc_ID", new DecimalDataType());
		tableSybnDtl.createColumn("FoodNo", new TextDataType());
		tableSybnDtl.createColumn("Food_Detail_Desc", new TextDataType());
		tableSybnDtl.createColumn("Nutr_Val", new DecimalDataType());
		tableSybnDtl.createColumn("Std_Dev", new DecimalDataType());
		tableSybnDtl.createColumn("Num_Data_Pts", new DecimalDataType());
		tableSybnDtl.createColumn("Sam_Hand_Rtg", new DecimalDataType());
		tableSybnDtl.createColumn("AnalMeth_Rtg", new DecimalDataType());
		tableSybnDtl.createColumn("SampPlan_Rtg", new DecimalDataType());
		tableSybnDtl.createColumn("AnalQC_Rtg", new DecimalDataType());
		tableSybnDtl.createColumn("NumSamp_QC", new DecimalDataType());
		tableSybnDtl.createColumn("CC", new TextDataType());
		this.createUniqueConstraint("SYBN_DTL_PrimaryKey", tableSybnDtl, tableSybnDtl.getColumn("NDB_No"), tableSybnDtl.getColumn("DataSrc_ID"), tableSybnDtl.getColumn("FoodNo"), tableSybnDtl.getColumn("Nutr_No"));
	}
}

