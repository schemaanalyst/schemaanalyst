package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;

/*
 * RiskIt schema.
 * Java code originally generated: 2013/07/11 14:11:03
 *
 */

@SuppressWarnings("serial")
public class RiskIt extends Schema {

	public RiskIt() {
		super("RiskIt");

		Table tableUserrecord = this.createTable("userrecord");
		tableUserrecord.addColumn("NAME", new CharDataType(50));
		tableUserrecord.addColumn("ZIP", new CharDataType(5));
		tableUserrecord.addColumn("SSN", new IntDataType());
		tableUserrecord.addColumn("AGE", new IntDataType());
		tableUserrecord.addColumn("SEX", new CharDataType(50));
		tableUserrecord.addColumn("MARITAL", new CharDataType(50));
		tableUserrecord.addColumn("RACE", new CharDataType(50));
		tableUserrecord.addColumn("TAXSTAT", new CharDataType(50));
		tableUserrecord.addColumn("DETAIL", new CharDataType(100));
		tableUserrecord.addColumn("HOUSEHOLDDETAIL", new CharDataType(100));
		tableUserrecord.addColumn("FATHERORIGIN", new CharDataType(50));
		tableUserrecord.addColumn("MOTHERORIGIN", new CharDataType(50));
		tableUserrecord.addColumn("BIRTHCOUNTRY", new CharDataType(50));
		tableUserrecord.addColumn("CITIZENSHIP", new CharDataType(50));
		tableUserrecord.setPrimaryKeyConstraint(tableUserrecord.getColumn("SSN"));
		tableUserrecord.addNotNullConstraint(tableUserrecord.getColumn("SSN"));

		Table tableEducation = this.createTable("education");
		tableEducation.addColumn("SSN", new IntDataType());
		tableEducation.addColumn("EDUCATION", new CharDataType(50));
		tableEducation.addColumn("EDUENROLL", new CharDataType(50));
		tableEducation.setPrimaryKeyConstraint(tableEducation.getColumn("SSN"));
		tableEducation.addForeignKeyConstraint(tableEducation.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		tableEducation.addNotNullConstraint(tableEducation.getColumn("SSN"));

		Table tableEmploymentstat = this.createTable("employmentstat");
		tableEmploymentstat.addColumn("SSN", new IntDataType());
		tableEmploymentstat.addColumn("UNEMPLOYMENTREASON", new CharDataType(50));
		tableEmploymentstat.addColumn("EMPLOYMENTSTAT", new CharDataType(50));
		tableEmploymentstat.setPrimaryKeyConstraint(tableEmploymentstat.getColumn("SSN"));
		tableEmploymentstat.addForeignKeyConstraint(tableEmploymentstat.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		tableEmploymentstat.addNotNullConstraint(tableEmploymentstat.getColumn("SSN"));

		Table tableGeo = this.createTable("geo");
		tableGeo.addColumn("REGION", new CharDataType(50));
		tableGeo.addColumn("RESSTATE", new CharDataType(50));
		tableGeo.setPrimaryKeyConstraint(tableGeo.getColumn("RESSTATE"));
		tableGeo.addNotNullConstraint(tableGeo.getColumn("REGION"));
		tableGeo.addNotNullConstraint(tableGeo.getColumn("RESSTATE"));

		Table tableIndustry = this.createTable("industry");
		tableIndustry.addColumn("INDUSTRYCODE", new IntDataType());
		tableIndustry.addColumn("INDUSTRY", new CharDataType(50));
		tableIndustry.addColumn("STABILITY", new IntDataType());
		tableIndustry.setPrimaryKeyConstraint(tableIndustry.getColumn("INDUSTRYCODE"));
		tableIndustry.addNotNullConstraint(tableIndustry.getColumn("INDUSTRYCODE"));

		Table tableInvestment = this.createTable("investment");
		tableInvestment.addColumn("SSN", new IntDataType());
		tableInvestment.addColumn("CAPITALGAINS", new IntDataType());
		tableInvestment.addColumn("CAPITALLOSSES", new IntDataType());
		tableInvestment.addColumn("STOCKDIVIDENDS", new IntDataType());
		tableInvestment.setPrimaryKeyConstraint(tableInvestment.getColumn("SSN"));
		tableInvestment.addForeignKeyConstraint(tableInvestment.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		tableInvestment.addNotNullConstraint(tableInvestment.getColumn("SSN"));

		Table tableOccupation = this.createTable("occupation");
		tableOccupation.addColumn("OCCUPATIONCODE", new IntDataType());
		tableOccupation.addColumn("OCCUPATION", new CharDataType(50));
		tableOccupation.addColumn("STABILITY", new IntDataType());
		tableOccupation.setPrimaryKeyConstraint(tableOccupation.getColumn("OCCUPATIONCODE"));
		tableOccupation.addNotNullConstraint(tableOccupation.getColumn("OCCUPATIONCODE"));

		Table tableJob = this.createTable("job");
		tableJob.addColumn("SSN", new IntDataType());
		tableJob.addColumn("WORKCLASS", new CharDataType(50));
		tableJob.addColumn("INDUSTRYCODE", new IntDataType());
		tableJob.addColumn("OCCUPATIONCODE", new IntDataType());
		tableJob.addColumn("UNIONMEMBER", new CharDataType(50));
		tableJob.addColumn("EMPLOYERSIZE", new IntDataType());
		tableJob.addColumn("WEEKWAGE", new IntDataType());
		tableJob.addColumn("SELFEMPLOYED", new SmallIntDataType());
		tableJob.addColumn("WORKWEEKS", new IntDataType());
		tableJob.setPrimaryKeyConstraint(tableJob.getColumn("SSN"));
		tableJob.addForeignKeyConstraint(tableJob.getColumn("OCCUPATIONCODE"), tableOccupation, tableOccupation.getColumn("OCCUPATIONCODE"));
		tableJob.addForeignKeyConstraint(tableJob.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		tableJob.addForeignKeyConstraint(tableJob.getColumn("INDUSTRYCODE"), tableIndustry, tableIndustry.getColumn("INDUSTRYCODE"));
		tableJob.addNotNullConstraint(tableJob.getColumn("SSN"));

		Table tableMigration = this.createTable("migration");
		tableMigration.addColumn("SSN", new IntDataType());
		tableMigration.addColumn("MIGRATIONCODE", new CharDataType(50));
		tableMigration.addColumn("MIGRATIONDISTANCE", new CharDataType(50));
		tableMigration.addColumn("MIGRATIONMOVE", new CharDataType(50));
		tableMigration.addColumn("MIGRATIONFROMSUNBELT", new CharDataType(50));
		tableMigration.setPrimaryKeyConstraint(tableMigration.getColumn("SSN"));
		tableMigration.addForeignKeyConstraint(tableMigration.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		tableMigration.addNotNullConstraint(tableMigration.getColumn("SSN"));

		Table tableStateabbv = this.createTable("stateabbv");
		tableStateabbv.addColumn("ABBV", new CharDataType(2));
		tableStateabbv.addColumn("NAME", new CharDataType(50));
		tableStateabbv.addNotNullConstraint(tableStateabbv.getColumn("ABBV"));
		tableStateabbv.addNotNullConstraint(tableStateabbv.getColumn("NAME"));

		Table tableWage = this.createTable("wage");
		tableWage.addColumn("INDUSTRYCODE", new IntDataType());
		tableWage.addColumn("OCCUPATIONCODE", new IntDataType());
		tableWage.addColumn("MEANWEEKWAGE", new IntDataType());
		tableWage.setPrimaryKeyConstraint(tableWage.getColumn("INDUSTRYCODE"), tableWage.getColumn("OCCUPATIONCODE"));
		tableWage.addForeignKeyConstraint(tableWage.getColumn("INDUSTRYCODE"), tableIndustry, tableIndustry.getColumn("INDUSTRYCODE"));
		tableWage.addForeignKeyConstraint(tableWage.getColumn("OCCUPATIONCODE"), tableOccupation, tableOccupation.getColumn("OCCUPATIONCODE"));
		tableWage.addNotNullConstraint(tableWage.getColumn("INDUSTRYCODE"));
		tableWage.addNotNullConstraint(tableWage.getColumn("OCCUPATIONCODE"));

		Table tableYouth = this.createTable("youth");
		tableYouth.addColumn("SSN", new IntDataType());
		tableYouth.addColumn("PARENTS", new CharDataType(50));
		tableYouth.setPrimaryKeyConstraint(tableYouth.getColumn("SSN"));
		tableYouth.addForeignKeyConstraint(tableYouth.getColumn("SSN"), tableUserrecord, tableUserrecord.getColumn("SSN"));
		tableYouth.addNotNullConstraint(tableYouth.getColumn("SSN"));

		Table tableZiptable = this.createTable("ziptable");
		tableZiptable.addColumn("ZIP", new CharDataType(5));
		tableZiptable.addColumn("CITY", new CharDataType(20));
		tableZiptable.addColumn("STATENAME", new CharDataType(20));
		tableZiptable.addColumn("COUNTY", new CharDataType(20));
	}
}

