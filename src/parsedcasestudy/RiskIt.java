package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;

/*
 * RiskIt schema.
 * Java code originally generated: 2013/08/17 00:31:02
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
		this.createPrimaryKeyConstraint(tableUserrecord, tableUserrecord.getColumn("SSN"));
		this.createNotNullConstraint(tableUserrecord, tableUserrecord.getColumn("SSN"));

		Table tableEducation = this.createTable("education");
		tableEducation.createColumn("SSN", new IntDataType());
		tableEducation.createColumn("EDUCATION", new CharDataType(50));
		tableEducation.createColumn("EDUENROLL", new CharDataType(50));
		this.createPrimaryKeyConstraint(tableEducation, tableEducation.getColumn("SSN"));
		this.createForeignKeyConstraint(tableEducation, tableEducation.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		this.createNotNullConstraint(tableEducation, tableEducation.getColumn("SSN"));

		Table tableEmploymentstat = this.createTable("employmentstat");
		tableEmploymentstat.createColumn("SSN", new IntDataType());
		tableEmploymentstat.createColumn("UNEMPLOYMENTREASON", new CharDataType(50));
		tableEmploymentstat.createColumn("EMPLOYMENTSTAT", new CharDataType(50));
		this.createPrimaryKeyConstraint(tableEmploymentstat, tableEmploymentstat.getColumn("SSN"));
		this.createForeignKeyConstraint(tableEmploymentstat, tableEmploymentstat.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		this.createNotNullConstraint(tableEmploymentstat, tableEmploymentstat.getColumn("SSN"));

		Table tableGeo = this.createTable("geo");
		tableGeo.createColumn("REGION", new CharDataType(50));
		tableGeo.createColumn("RESSTATE", new CharDataType(50));
		this.createPrimaryKeyConstraint(tableGeo, tableGeo.getColumn("RESSTATE"));
		this.createNotNullConstraint(tableGeo, tableGeo.getColumn("REGION"));
		this.createNotNullConstraint(tableGeo, tableGeo.getColumn("RESSTATE"));

		Table tableIndustry = this.createTable("industry");
		tableIndustry.createColumn("INDUSTRYCODE", new IntDataType());
		tableIndustry.createColumn("INDUSTRY", new CharDataType(50));
		tableIndustry.createColumn("STABILITY", new IntDataType());
		this.createPrimaryKeyConstraint(tableIndustry, tableIndustry.getColumn("INDUSTRYCODE"));
		this.createNotNullConstraint(tableIndustry, tableIndustry.getColumn("INDUSTRYCODE"));

		Table tableInvestment = this.createTable("investment");
		tableInvestment.createColumn("SSN", new IntDataType());
		tableInvestment.createColumn("CAPITALGAINS", new IntDataType());
		tableInvestment.createColumn("CAPITALLOSSES", new IntDataType());
		tableInvestment.createColumn("STOCKDIVIDENDS", new IntDataType());
		this.createPrimaryKeyConstraint(tableInvestment, tableInvestment.getColumn("SSN"));
		this.createForeignKeyConstraint(tableInvestment, tableInvestment.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		this.createNotNullConstraint(tableInvestment, tableInvestment.getColumn("SSN"));

		Table tableOccupation = this.createTable("occupation");
		tableOccupation.createColumn("OCCUPATIONCODE", new IntDataType());
		tableOccupation.createColumn("OCCUPATION", new CharDataType(50));
		tableOccupation.createColumn("STABILITY", new IntDataType());
		this.createPrimaryKeyConstraint(tableOccupation, tableOccupation.getColumn("OCCUPATIONCODE"));
		this.createNotNullConstraint(tableOccupation, tableOccupation.getColumn("OCCUPATIONCODE"));

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
		this.createPrimaryKeyConstraint(tableJob, tableJob.getColumn("SSN"));
		this.createForeignKeyConstraint(tableJob, tableJob.getColumn("OCCUPATIONCODE"), tableOccupation, tableOccupation.getColumn("OCCUPATIONCODE"));
		this.createForeignKeyConstraint(tableJob, tableJob.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		this.createForeignKeyConstraint(tableJob, tableJob.getColumn("INDUSTRYCODE"), tableIndustry, tableIndustry.getColumn("INDUSTRYCODE"));
		this.createNotNullConstraint(tableJob, tableJob.getColumn("SSN"));

		Table tableMigration = this.createTable("migration");
		tableMigration.createColumn("SSN", new IntDataType());
		tableMigration.createColumn("MIGRATIONCODE", new CharDataType(50));
		tableMigration.createColumn("MIGRATIONDISTANCE", new CharDataType(50));
		tableMigration.createColumn("MIGRATIONMOVE", new CharDataType(50));
		tableMigration.createColumn("MIGRATIONFROMSUNBELT", new CharDataType(50));
		this.createPrimaryKeyConstraint(tableMigration, tableMigration.getColumn("SSN"));
		this.createForeignKeyConstraint(tableMigration, tableMigration.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		this.createNotNullConstraint(tableMigration, tableMigration.getColumn("SSN"));

		Table tableStateabbv = this.createTable("stateabbv");
		tableStateabbv.createColumn("ABBV", new CharDataType(2));
		tableStateabbv.createColumn("NAME", new CharDataType(50));
		this.createNotNullConstraint(tableStateabbv, tableStateabbv.getColumn("ABBV"));
		this.createNotNullConstraint(tableStateabbv, tableStateabbv.getColumn("NAME"));

		Table tableWage = this.createTable("wage");
		tableWage.createColumn("INDUSTRYCODE", new IntDataType());
		tableWage.createColumn("OCCUPATIONCODE", new IntDataType());
		tableWage.createColumn("MEANWEEKWAGE", new IntDataType());
		this.createPrimaryKeyConstraint(tableWage, tableWage.getColumn("INDUSTRYCODE"), tableWage.getColumn("OCCUPATIONCODE"));
		this.createForeignKeyConstraint(tableWage, tableWage.getColumn("INDUSTRYCODE"), tableIndustry, tableIndustry.getColumn("INDUSTRYCODE"));
		this.createForeignKeyConstraint(tableWage, tableWage.getColumn("OCCUPATIONCODE"), tableOccupation, tableOccupation.getColumn("OCCUPATIONCODE"));
		this.createNotNullConstraint(tableWage, tableWage.getColumn("INDUSTRYCODE"));
		this.createNotNullConstraint(tableWage, tableWage.getColumn("OCCUPATIONCODE"));

		Table tableYouth = this.createTable("youth");
		tableYouth.createColumn("SSN", new IntDataType());
		tableYouth.createColumn("PARENTS", new CharDataType(50));
		this.createPrimaryKeyConstraint(tableYouth, tableYouth.getColumn("SSN"));
		this.createForeignKeyConstraint(tableYouth, tableYouth.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		this.createNotNullConstraint(tableYouth, tableYouth.getColumn("SSN"));

		Table tableZiptable = this.createTable("ziptable");
		tableZiptable.createColumn("ZIP", new CharDataType(5));
		tableZiptable.createColumn("CITY", new CharDataType(20));
		tableZiptable.createColumn("STATENAME", new CharDataType(20));
		tableZiptable.createColumn("COUNTY", new CharDataType(20));
	}
}

