package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * H1EFileFY2007Repaired schema.
 * Java code originally generated: 2013/12/13 10:00:49
 *
 */

@SuppressWarnings("serial")
public class H1EFileFY2007Repaired extends Schema {

	public H1EFileFY2007Repaired() {
		super("H1EFileFY2007Repaired");

		Table tableEfileFy2007Data = this.createTable("EFILE_FY2007_DATA");
		tableEfileFy2007Data.createColumn("Submitted_Date", new DateTimeDataType());
		tableEfileFy2007Data.createColumn("Case Number", new TextDataType());
		tableEfileFy2007Data.createColumn("Program Designation", new TextDataType());
		tableEfileFy2007Data.createColumn("Employer_Name", new TextDataType());
		tableEfileFy2007Data.createColumn("Address_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Address_2", new TextDataType());
		tableEfileFy2007Data.createColumn("City", new TextDataType());
		tableEfileFy2007Data.createColumn("State", new TextDataType());
		tableEfileFy2007Data.createColumn("Zip_Code", new TextDataType());
		tableEfileFy2007Data.createColumn("Nbr_Immigrants", new IntDataType());
		tableEfileFy2007Data.createColumn("Begin_Date", new DateTimeDataType());
		tableEfileFy2007Data.createColumn("End_Date", new DateTimeDataType());
		tableEfileFy2007Data.createColumn("Job_Title", new TextDataType());
		tableEfileFy2007Data.createColumn("DOL_Decision_Date", new DateTimeDataType());
		tableEfileFy2007Data.createColumn("Certified_Begin_Date", new DateTimeDataType());
		tableEfileFy2007Data.createColumn("Certified_End_Date", new DateTimeDataType());
		tableEfileFy2007Data.createColumn("Occupation_Code", new TextDataType());
		tableEfileFy2007Data.createColumn("Case_Status", new TextDataType());
		tableEfileFy2007Data.createColumn("Wage_Rate_From_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Wage_Rate_Per_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Wage_Rate_To_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Part_Time_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Work_City_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Work_State_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Prevailing_Wage_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Prevailing_Wage_Source_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Year_Source_Published_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Other_Wage_Source_1", new TextDataType());
		tableEfileFy2007Data.createColumn("Wage_Rate_From_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Wage_Rate_Per_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Wage_Rate_To_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Part_Time_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Work_City_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Work_State_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Prevailing_Wage_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Prevailing_Wage_Source_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Year_Source_Published_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Other_Wage_Source_2", new TextDataType());
		tableEfileFy2007Data.createColumn("Withdrawn", new TextDataType());
	}
}

