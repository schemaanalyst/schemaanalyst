package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;

/*
 * RiskIt schema.
 * Java code originally generated: 2013/08/15 10:52:16
 *
 */

@SuppressWarnings("serial")
public class RiskIt extends Schema {

	public RiskIt() {
		super("RiskIt");

		Table tableUserrecord = this.createTable("userrecord");
		tableUserrecord.createColumn("NAME", new CharDataType(50));
		tableUserrecord.createColumn("ZIP", new CharDataType(5));
		tableUserrecord.createColumn("SSN", new IntDataType());
		tableUserrecord.createColumn("AGE", new IntDataType());
		tableUserrecord.createColumn("SEX", new CharDataType(50));
		tableUserrecord.createColumn("MARITAL", new CharDataType(50));
		tableUserrecord.createColumn("RACE", new CharDataType(50));
		tableUserrecord.createColumn("TAXSTAT", new CharDataType(50));
		tableUserrecord.createColumn("DETAIL", new CharDataType(100));
		tableUserrecord.createColumn("HOUSEHOLDDETAIL", new CharDataType(100));
		tableUserrecord.createColumn("FATHERORIGIN", new CharDataType(50));
		tableUserrecord.createColumn("MOTHERORIGIN", new CharDataType(50));
		tableUserrecord.createColumn("BIRTHCOUNTRY", new CharDataType(50));
		tableUserrecord.createColumn("CITIZENSHIP", new CharDataType(50));
		tableUserrecord.createPrimaryKeyConstraint(tableUserrecord.getColumn("SSN"));
		tableUserrecord.createNotNullConstraint(tableUserrecord.getColumn("SSN"));

		Table tableEducation = this.createTable("education");
		tableEducation.createColumn("SSN", new IntDataType());
		tableEducation.createColumn("EDUCATION", new CharDataType(50));
		tableEducation.createColumn("EDUENROLL", new CharDataType(50));
		tableEducation.createPrimaryKeyConstraint(tableEducation.getColumn("SSN"));
		tableEducation.createForeignKeyConstraint(tableEducation.getColumn("SSN"), tableUserrecord, tableEducation.getColumn("SSN"));
		tableEducation.createNotNullConstraint(tableEducation.getColumn("SSN"));

		Table tableEmploymentstat = this.createTable("employmentstat");
		tableEmploymentstat.createColumn("SSN", new IntDataType());
		tableEmploymentstat.createColumn("UNEMPLOYMENTREASON", new CharDataType(50));
		tableEmploymentstat.createColumn("EMPLOYMENTSTAT", new CharDataType(50));
		tableEmploymentstat.createPrimaryKeyConstraint(tableEmploymentstat.getColumn("SSN"));
		tableEmploymentstat.createForeignKeyConstraint(tableEmploymentstat.getColumn("SSN"), tableUserrecord, tableEmploymentstat.getColumn("SSN"));
		tableEmploymentstat.createNotNullConstraint(tableEmploymentstat.getColumn("SSN"));

		Table tableGeo = this.createTable("geo");
		tableGeo.createColumn("REGION", new CharDataType(50));
		tableGeo.createColumn("RESSTATE", new CharDataType(50));
		tableGeo.createPrimaryKeyConstraint(tableGeo.getColumn("RESSTATE"));
		tableGeo.createNotNullConstraint(tableGeo.getColumn("REGION"));
		tableGeo.createNotNullConstraint(tableGeo.getColumn("RESSTATE"));

		Table tableIndustry = this.createTable("industry");
		tableIndustry.createColumn("INDUSTRYCODE", new IntDataType());
		tableIndustry.createColumn("INDUSTRY", new CharDataType(50));
		tableIndustry.createColumn("STABILITY", new IntDataType());
		tableIndustry.createPrimaryKeyConstraint(tableIndustry.getColumn("INDUSTRYCODE"));
		tableIndustry.createNotNullConstraint(tableIndustry.getColumn("INDUSTRYCODE"));

		Table tableInvestment = this.createTable("investment");
		tableInvestment.createColumn("SSN", new IntDataType());
		tableInvestment.createColumn("CAPITALGAINS", new IntDataType());
		tableInvestment.createColumn("CAPITALLOSSES", new IntDataType());
		tableInvestment.createColumn("STOCKDIVIDENDS", new IntDataType());
		tableInvestment.createPrimaryKeyConstraint(tableInvestment.getColumn("SSN"));
		tableInvestment.createForeignKeyConstraint(tableInvestment.getColumn("SSN"), tableUserrecord, tableInvestment.getColumn("SSN"));
		tableInvestment.createNotNullConstraint(tableInvestment.getColumn("SSN"));

		Table tableOccupation = this.createTable("occupation");
		tableOccupation.createColumn("OCCUPATIONCODE", new IntDataType());
		tableOccupation.createColumn("OCCUPATION", new CharDataType(50));
		tableOccupation.createColumn("STABILITY", new IntDataType());
		tableOccupation.createPrimaryKeyConstraint(tableOccupation.getColumn("OCCUPATIONCODE"));
		tableOccupation.createNotNullConstraint(tableOccupation.getColumn("OCCUPATIONCODE"));

		Table tableJob = this.createTable("job");
		tableJob.createColumn("SSN", new IntDataType());
		tableJob.createColumn("WORKCLASS", new CharDataType(50));
		tableJob.createColumn("INDUSTRYCODE", new IntDataType());
		tableJob.createColumn("OCCUPATIONCODE", new IntDataType());
		tableJob.createColumn("UNIONMEMBER", new CharDataType(50));
		tableJob.createColumn("EMPLOYERSIZE", new IntDataType());
		tableJob.createColumn("WEEKWAGE", new IntDataType());
		tableJob.createColumn("SELFEMPLOYED", new SmallIntDataType());
		tableJob.createColumn("WORKWEEKS", new IntDataType());
		tableJob.createPrimaryKeyConstraint(tableJob.getColumn("SSN"));
		tableJob.createForeignKeyConstraint(tableJob.getColumn("OCCUPATIONCODE"), tableOccupation, tableJob.getColumn("OCCUPATIONCODE"));
		tableJob.createForeignKeyConstraint(tableJob.getColumn("SSN"), tableUserrecord, tableJob.getColumn("SSN"));
		tableJob.createForeignKeyConstraint(tableJob.getColumn("INDUSTRYCODE"), tableIndustry, tableJob.getColumn("INDUSTRYCODE"));
		tableJob.createNotNullConstraint(tableJob.getColumn("SSN"));

		Table tableMigration = this.createTable("migration");
		tableMigration.createColumn("SSN", new IntDataType());
		tableMigration.createColumn("MIGRATIONCODE", new CharDataType(50));
		tableMigration.createColumn("MIGRATIONDISTANCE", new CharDataType(50));
		tableMigration.createColumn("MIGRATIONMOVE", new CharDataType(50));
		tableMigration.createColumn("MIGRATIONFROMSUNBELT", new CharDataType(50));
		tableMigration.createPrimaryKeyConstraint(tableMigration.getColumn("SSN"));
		tableMigration.createForeignKeyConstraint(tableMigration.getColumn("SSN"), tableUserrecord, tableMigration.getColumn("SSN"));
		tableMigration.createNotNullConstraint(tableMigration.getColumn("SSN"));

		Table tableStateabbv = this.createTable("stateabbv");
		tableStateabbv.createColumn("ABBV", new CharDataType(2));
		tableStateabbv.createColumn("NAME", new CharDataType(50));
		tableStateabbv.createNotNullConstraint(tableStateabbv.getColumn("ABBV"));
		tableStateabbv.createNotNullConstraint(tableStateabbv.getColumn("NAME"));

		Table tableWage = this.createTable("wage");
		tableWage.createColumn("INDUSTRYCODE", new IntDataType());
		tableWage.createColumn("OCCUPATIONCODE", new IntDataType());
		tableWage.createColumn("MEANWEEKWAGE", new IntDataType());
		tableWage.createPrimaryKeyConstraint(tableWage.getColumn("INDUSTRYCODE"), tableWage.getColumn("OCCUPATIONCODE"));
		tableWage.createForeignKeyConstraint(tableWage.getColumn("INDUSTRYCODE"), tableIndustry, tableWage.getColumn("INDUSTRYCODE"));
		tableWage.createForeignKeyConstraint(tableWage.getColumn("OCCUPATIONCODE"), tableOccupation, tableWage.getColumn("OCCUPATIONCODE"));
		tableWage.createNotNullConstraint(tableWage.getColumn("INDUSTRYCODE"));
		tableWage.createNotNullConstraint(tableWage.getColumn("OCCUPATIONCODE"));

		Table tableYouth = this.createTable("youth");
		tableYouth.createColumn("SSN", new IntDataType());
		tableYouth.createColumn("PARENTS", new CharDataType(50));
		tableYouth.createPrimaryKeyConstraint(tableYouth.getColumn("SSN"));
		tableYouth.createForeignKeyConstraint(tableYouth.getColumn("SSN"), tableUserrecord, tableYouth.getColumn("SSN"));
		tableYouth.createNotNullConstraint(tableYouth.getColumn("SSN"));

		Table tableZiptable = this.createTable("ziptable");
		tableZiptable.createColumn("ZIP", new CharDataType(5));
		tableZiptable.createColumn("CITY", new CharDataType(20));
		tableZiptable.createColumn("STATENAME", new CharDataType(20));
		tableZiptable.createColumn("COUNTY", new CharDataType(20));
	}
}

