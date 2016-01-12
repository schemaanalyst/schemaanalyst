package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * GeoMetadb schema.
 * Java code originally generated: 2013/12/13 10:00:48
 *
 */

@SuppressWarnings("serial")
public class GeoMetaDB extends Schema {

	public GeoMetaDB() {
		super("GeoMetaDB");

		Table tableGds = this.createTable("gds");
		tableGds.createColumn("ID", new RealDataType());
		tableGds.createColumn("gds", new TextDataType());
		tableGds.createColumn("title", new TextDataType());
		tableGds.createColumn("description", new TextDataType());
		tableGds.createColumn("type", new TextDataType());
		tableGds.createColumn("pubmed_id", new TextDataType());
		tableGds.createColumn("gpl", new TextDataType());
		tableGds.createColumn("platform_organism", new TextDataType());
		tableGds.createColumn("platform_technology_type", new TextDataType());
		tableGds.createColumn("feature_count", new IntDataType());
		tableGds.createColumn("sample_organism", new TextDataType());
		tableGds.createColumn("sample_type", new TextDataType());
		tableGds.createColumn("channel_count", new TextDataType());
		tableGds.createColumn("sample_count", new IntDataType());
		tableGds.createColumn("value_type", new TextDataType());
		tableGds.createColumn("gse", new TextDataType());
		tableGds.createColumn("order", new TextDataType());
		tableGds.createColumn("update_date", new TextDataType());

		Table tableGdsSubset = this.createTable("gds_subset");
		tableGdsSubset.createColumn("ID", new RealDataType());
		tableGdsSubset.createColumn("Name", new TextDataType());
		tableGdsSubset.createColumn("gds", new TextDataType());
		tableGdsSubset.createColumn("description", new TextDataType());
		tableGdsSubset.createColumn("sample_id", new TextDataType());
		tableGdsSubset.createColumn("type", new TextDataType());

		Table tableGeoconvert = this.createTable("geoConvert");
		tableGeoconvert.createColumn("from_acc", new TextDataType());
		tableGeoconvert.createColumn("to_acc", new TextDataType());
		tableGeoconvert.createColumn("to_type", new TextDataType());

		Table tableGeodbColumnDesc = this.createTable("geodb_column_desc");
		tableGeodbColumnDesc.createColumn("TableName", new TextDataType());
		tableGeodbColumnDesc.createColumn("FieldName", new TextDataType());
		tableGeodbColumnDesc.createColumn("Description", new TextDataType());

		Table tableGpl = this.createTable("gpl");
		tableGpl.createColumn("ID", new RealDataType());
		tableGpl.createColumn("title", new TextDataType());
		tableGpl.createColumn("gpl", new TextDataType());
		tableGpl.createColumn("status", new TextDataType());
		tableGpl.createColumn("submission_date", new TextDataType());
		tableGpl.createColumn("last_update_date", new TextDataType());
		tableGpl.createColumn("technology", new TextDataType());
		tableGpl.createColumn("distribution", new TextDataType());
		tableGpl.createColumn("organism", new TextDataType());
		tableGpl.createColumn("manufacturer", new TextDataType());
		tableGpl.createColumn("manufacture_protocol", new TextDataType());
		tableGpl.createColumn("coating", new TextDataType());
		tableGpl.createColumn("catalog_number", new TextDataType());
		tableGpl.createColumn("support", new TextDataType());
		tableGpl.createColumn("description", new TextDataType());
		tableGpl.createColumn("web_link", new TextDataType());
		tableGpl.createColumn("contact", new TextDataType());
		tableGpl.createColumn("data_row_count", new RealDataType());
		tableGpl.createColumn("supplementary_file", new TextDataType());
		tableGpl.createColumn("bioc_package", new TextDataType());

		Table tableGse = this.createTable("gse");
		tableGse.createColumn("ID", new RealDataType());
		tableGse.createColumn("title", new TextDataType());
		tableGse.createColumn("gse", new TextDataType());
		tableGse.createColumn("status", new TextDataType());
		tableGse.createColumn("submission_date", new TextDataType());
		tableGse.createColumn("last_update_date", new TextDataType());
		tableGse.createColumn("pubmed_id", new IntDataType());
		tableGse.createColumn("summary", new TextDataType());
		tableGse.createColumn("type", new TextDataType());
		tableGse.createColumn("contributor", new TextDataType());
		tableGse.createColumn("web_link", new TextDataType());
		tableGse.createColumn("overall_design", new TextDataType());
		tableGse.createColumn("repeats", new TextDataType());
		tableGse.createColumn("repeats_sample_list", new TextDataType());
		tableGse.createColumn("variable", new TextDataType());
		tableGse.createColumn("variable_description", new TextDataType());
		tableGse.createColumn("contact", new TextDataType());
		tableGse.createColumn("supplementary_file", new TextDataType());

		Table tableGseGpl = this.createTable("gse_gpl");
		tableGseGpl.createColumn("gse", new TextDataType());
		tableGseGpl.createColumn("gpl", new TextDataType());

		Table tableGseGsm = this.createTable("gse_gsm");
		tableGseGsm.createColumn("gse", new TextDataType());
		tableGseGsm.createColumn("gsm", new TextDataType());

		Table tableGsm = this.createTable("gsm");
		tableGsm.createColumn("ID", new RealDataType());
		tableGsm.createColumn("title", new TextDataType());
		tableGsm.createColumn("gsm", new TextDataType());
		tableGsm.createColumn("series_id", new TextDataType());
		tableGsm.createColumn("gpl", new TextDataType());
		tableGsm.createColumn("status", new TextDataType());
		tableGsm.createColumn("submission_date", new TextDataType());
		tableGsm.createColumn("last_update_date", new TextDataType());
		tableGsm.createColumn("type", new TextDataType());
		tableGsm.createColumn("source_name_ch1", new TextDataType());
		tableGsm.createColumn("organism_ch1", new TextDataType());
		tableGsm.createColumn("characteristics_ch1", new TextDataType());
		tableGsm.createColumn("molecule_ch1", new TextDataType());
		tableGsm.createColumn("label_ch1", new TextDataType());
		tableGsm.createColumn("treatment_protocol_ch1", new TextDataType());
		tableGsm.createColumn("extract_protocol_ch1", new TextDataType());
		tableGsm.createColumn("label_protocol_ch1", new TextDataType());
		tableGsm.createColumn("source_name_ch2", new TextDataType());
		tableGsm.createColumn("organism_ch2", new TextDataType());
		tableGsm.createColumn("characteristics_ch2", new TextDataType());
		tableGsm.createColumn("molecule_ch2", new TextDataType());
		tableGsm.createColumn("label_ch2", new TextDataType());
		tableGsm.createColumn("treatment_protocol_ch2", new TextDataType());
		tableGsm.createColumn("extract_protocol_ch2", new TextDataType());
		tableGsm.createColumn("label_protocol_ch2", new TextDataType());
		tableGsm.createColumn("hyb_protocol", new TextDataType());
		tableGsm.createColumn("description", new TextDataType());
		tableGsm.createColumn("data_processing", new TextDataType());
		tableGsm.createColumn("contact", new TextDataType());
		tableGsm.createColumn("supplementary_file", new TextDataType());
		tableGsm.createColumn("data_row_count", new RealDataType());
		tableGsm.createColumn("channel_count", new RealDataType());

		Table tableMetainfo = this.createTable("metaInfo");
		tableMetainfo.createColumn("name", new VarCharDataType(50));
		tableMetainfo.createColumn("value", new VarCharDataType(50));

		Table tableSmatrix = this.createTable("sMatrix");
		tableSmatrix.createColumn("ID", new IntDataType());
		tableSmatrix.createColumn("sMatrix", new TextDataType());
		tableSmatrix.createColumn("gse", new TextDataType());
		tableSmatrix.createColumn("gpl", new TextDataType());
		tableSmatrix.createColumn("GSM_Count", new IntDataType());
		tableSmatrix.createColumn("Last_Update_Date", new TextDataType());
	}
}

