package parsedcasestudy;

import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.*;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

/*
 * iTrust schema.
 * Java code originally generated: 2013/08/17 00:31:10
 *
 */

@SuppressWarnings("serial")
public class iTrust extends Schema {

	public iTrust() {
		super("iTrust");

		Table tableUsers = this.createTable("Users");
		tableUsers.createColumn("MID", new IntDataType());
		tableUsers.createColumn("Password", new VarCharDataType(20));
		tableUsers.createColumn("Role", new VarCharDataType(20));
		tableUsers.createColumn("sQuestion", new VarCharDataType(100));
		tableUsers.createColumn("sAnswer", new VarCharDataType(30));
		this.createPrimaryKeyConstraint(tableUsers, tableUsers.getColumn("MID"));
		this.createCheckConstraint(tableUsers, new InExpression(new ColumnExpression(tableUsers, tableUsers.getColumn("Role")), new ListExpression(new ConstantExpression(new StringValue("patient")), new ConstantExpression(new StringValue("admin")), new ConstantExpression(new StringValue("hcp")), new ConstantExpression(new StringValue("uap")), new ConstantExpression(new StringValue("er")), new ConstantExpression(new StringValue("tester")), new ConstantExpression(new StringValue("pha")), new ConstantExpression(new StringValue("lt"))), false));
		this.createNotNullConstraint(tableUsers, tableUsers.getColumn("Role"));

		Table tableHospitals = this.createTable("Hospitals");
		tableHospitals.createColumn("HospitalID", new VarCharDataType(10));
		tableHospitals.createColumn("HospitalName", new VarCharDataType(30));
		this.createPrimaryKeyConstraint(tableHospitals, tableHospitals.getColumn("HospitalID"));
		this.createNotNullConstraint(tableHospitals, tableHospitals.getColumn("HospitalName"));

		Table tablePersonnel = this.createTable("Personnel");
		tablePersonnel.createColumn("MID", new IntDataType());
		tablePersonnel.createColumn("AMID", new IntDataType());
		tablePersonnel.createColumn("role", new VarCharDataType(20));
		tablePersonnel.createColumn("enabled", new IntDataType());
		tablePersonnel.createColumn("lastName", new VarCharDataType(20));
		tablePersonnel.createColumn("firstName", new VarCharDataType(20));
		tablePersonnel.createColumn("address1", new VarCharDataType(20));
		tablePersonnel.createColumn("address2", new VarCharDataType(20));
		tablePersonnel.createColumn("city", new VarCharDataType(15));
		tablePersonnel.createColumn("state", new VarCharDataType(2));
		tablePersonnel.createColumn("zip", new VarCharDataType(10));
		tablePersonnel.createColumn("zip1", new VarCharDataType(5));
		tablePersonnel.createColumn("zip2", new VarCharDataType(4));
		tablePersonnel.createColumn("phone", new VarCharDataType(12));
		tablePersonnel.createColumn("phone1", new VarCharDataType(3));
		tablePersonnel.createColumn("phone2", new VarCharDataType(3));
		tablePersonnel.createColumn("phone3", new VarCharDataType(4));
		tablePersonnel.createColumn("specialty", new VarCharDataType(40));
		tablePersonnel.createColumn("email", new VarCharDataType(55));
		tablePersonnel.createColumn("MessageFilter", new VarCharDataType(60));
		this.createPrimaryKeyConstraint(tablePersonnel, tablePersonnel.getColumn("MID"));
		this.createCheckConstraint(tablePersonnel, new InExpression(new ColumnExpression(tablePersonnel, tablePersonnel.getColumn("role")), new ListExpression(new ConstantExpression(new StringValue("admin")), new ConstantExpression(new StringValue("hcp")), new ConstantExpression(new StringValue("uap")), new ConstantExpression(new StringValue("er")), new ConstantExpression(new StringValue("tester")), new ConstantExpression(new StringValue("pha")), new ConstantExpression(new StringValue("lt"))), false));
		this.createCheckConstraint(tablePersonnel, new InExpression(new ColumnExpression(tablePersonnel, tablePersonnel.getColumn("state")), new ListExpression(new ConstantExpression(new StringValue("")), new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));
		this.createNotNullConstraint(tablePersonnel, tablePersonnel.getColumn("role"));
		this.createNotNullConstraint(tablePersonnel, tablePersonnel.getColumn("enabled"));
		this.createNotNullConstraint(tablePersonnel, tablePersonnel.getColumn("lastName"));
		this.createNotNullConstraint(tablePersonnel, tablePersonnel.getColumn("firstName"));
		this.createNotNullConstraint(tablePersonnel, tablePersonnel.getColumn("address1"));
		this.createNotNullConstraint(tablePersonnel, tablePersonnel.getColumn("address2"));
		this.createNotNullConstraint(tablePersonnel, tablePersonnel.getColumn("city"));
		this.createNotNullConstraint(tablePersonnel, tablePersonnel.getColumn("state"));

		Table tablePatients = this.createTable("Patients");
		tablePatients.createColumn("MID", new IntDataType());
		tablePatients.createColumn("lastName", new VarCharDataType(20));
		tablePatients.createColumn("firstName", new VarCharDataType(20));
		tablePatients.createColumn("email", new VarCharDataType(55));
		tablePatients.createColumn("address1", new VarCharDataType(20));
		tablePatients.createColumn("address2", new VarCharDataType(20));
		tablePatients.createColumn("city", new VarCharDataType(15));
		tablePatients.createColumn("state", new VarCharDataType(2));
		tablePatients.createColumn("zip1", new VarCharDataType(5));
		tablePatients.createColumn("zip2", new VarCharDataType(4));
		tablePatients.createColumn("phone1", new VarCharDataType(3));
		tablePatients.createColumn("phone2", new VarCharDataType(3));
		tablePatients.createColumn("phone3", new VarCharDataType(4));
		tablePatients.createColumn("eName", new VarCharDataType(40));
		tablePatients.createColumn("ePhone1", new VarCharDataType(3));
		tablePatients.createColumn("ePhone2", new VarCharDataType(3));
		tablePatients.createColumn("ePhone3", new VarCharDataType(4));
		tablePatients.createColumn("iCName", new VarCharDataType(20));
		tablePatients.createColumn("iCAddress1", new VarCharDataType(20));
		tablePatients.createColumn("iCAddress2", new VarCharDataType(20));
		tablePatients.createColumn("iCCity", new VarCharDataType(15));
		tablePatients.createColumn("ICState", new VarCharDataType(2));
		tablePatients.createColumn("iCZip1", new VarCharDataType(5));
		tablePatients.createColumn("iCZip2", new VarCharDataType(4));
		tablePatients.createColumn("iCPhone1", new VarCharDataType(3));
		tablePatients.createColumn("iCPhone2", new VarCharDataType(3));
		tablePatients.createColumn("iCPhone3", new VarCharDataType(4));
		tablePatients.createColumn("iCID", new VarCharDataType(20));
		tablePatients.createColumn("DateOfBirth", new DateDataType());
		tablePatients.createColumn("DateOfDeath", new DateDataType());
		tablePatients.createColumn("CauseOfDeath", new VarCharDataType(10));
		tablePatients.createColumn("MotherMID", new IntDataType());
		tablePatients.createColumn("FatherMID", new IntDataType());
		tablePatients.createColumn("BloodType", new VarCharDataType(3));
		tablePatients.createColumn("Ethnicity", new VarCharDataType(20));
		tablePatients.createColumn("Gender", new VarCharDataType(13));
		tablePatients.createColumn("TopicalNotes", new VarCharDataType(200));
		tablePatients.createColumn("CreditCardType", new VarCharDataType(20));
		tablePatients.createColumn("CreditCardNumber", new VarCharDataType(19));
		tablePatients.createColumn("MessageFilter", new VarCharDataType(60));
		tablePatients.createColumn("DirectionsToHome", new VarCharDataType(512));
		tablePatients.createColumn("Religion", new VarCharDataType(64));
		tablePatients.createColumn("Language", new VarCharDataType(32));
		tablePatients.createColumn("SpiritualPractices", new VarCharDataType(100));
		tablePatients.createColumn("AlternateName", new VarCharDataType(32));
		this.createPrimaryKeyConstraint(tablePatients, tablePatients.getColumn("MID"));
		this.createCheckConstraint(tablePatients, new InExpression(new ColumnExpression(tablePatients, tablePatients.getColumn("state")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));
		this.createCheckConstraint(tablePatients, new InExpression(new ColumnExpression(tablePatients, tablePatients.getColumn("ICState")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));

		Table tableHistorypatients = this.createTable("HistoryPatients");
		tableHistorypatients.createColumn("ID", new IntDataType());
		tableHistorypatients.createColumn("changeDate", new DateDataType());
		tableHistorypatients.createColumn("changeMID", new IntDataType());
		tableHistorypatients.createColumn("MID", new IntDataType());
		tableHistorypatients.createColumn("lastName", new VarCharDataType(20));
		tableHistorypatients.createColumn("firstName", new VarCharDataType(20));
		tableHistorypatients.createColumn("email", new VarCharDataType(55));
		tableHistorypatients.createColumn("address1", new VarCharDataType(20));
		tableHistorypatients.createColumn("address2", new VarCharDataType(20));
		tableHistorypatients.createColumn("city", new VarCharDataType(15));
		tableHistorypatients.createColumn("state", new CharDataType(2));
		tableHistorypatients.createColumn("zip1", new VarCharDataType(5));
		tableHistorypatients.createColumn("zip2", new VarCharDataType(4));
		tableHistorypatients.createColumn("phone1", new VarCharDataType(3));
		tableHistorypatients.createColumn("phone2", new VarCharDataType(3));
		tableHistorypatients.createColumn("phone3", new VarCharDataType(4));
		tableHistorypatients.createColumn("eName", new VarCharDataType(40));
		tableHistorypatients.createColumn("ePhone1", new VarCharDataType(3));
		tableHistorypatients.createColumn("ePhone2", new VarCharDataType(3));
		tableHistorypatients.createColumn("ePhone3", new VarCharDataType(4));
		tableHistorypatients.createColumn("iCName", new VarCharDataType(20));
		tableHistorypatients.createColumn("iCAddress1", new VarCharDataType(20));
		tableHistorypatients.createColumn("iCAddress2", new VarCharDataType(20));
		tableHistorypatients.createColumn("iCCity", new VarCharDataType(15));
		tableHistorypatients.createColumn("ICState", new VarCharDataType(2));
		tableHistorypatients.createColumn("iCZip1", new VarCharDataType(5));
		tableHistorypatients.createColumn("iCZip2", new VarCharDataType(4));
		tableHistorypatients.createColumn("iCPhone1", new VarCharDataType(3));
		tableHistorypatients.createColumn("iCPhone2", new VarCharDataType(3));
		tableHistorypatients.createColumn("iCPhone3", new VarCharDataType(4));
		tableHistorypatients.createColumn("iCID", new VarCharDataType(20));
		tableHistorypatients.createColumn("DateOfBirth", new DateDataType());
		tableHistorypatients.createColumn("DateOfDeath", new DateDataType());
		tableHistorypatients.createColumn("CauseOfDeath", new VarCharDataType(10));
		tableHistorypatients.createColumn("MotherMID", new IntDataType());
		tableHistorypatients.createColumn("FatherMID", new IntDataType());
		tableHistorypatients.createColumn("BloodType", new VarCharDataType(3));
		tableHistorypatients.createColumn("Ethnicity", new VarCharDataType(20));
		tableHistorypatients.createColumn("Gender", new VarCharDataType(13));
		tableHistorypatients.createColumn("TopicalNotes", new VarCharDataType(200));
		tableHistorypatients.createColumn("CreditCardType", new VarCharDataType(20));
		tableHistorypatients.createColumn("CreditCardNumber", new VarCharDataType(19));
		tableHistorypatients.createColumn("MessageFilter", new VarCharDataType(60));
		tableHistorypatients.createColumn("DirectionsToHome", new VarCharDataType(100));
		tableHistorypatients.createColumn("Religion", new VarCharDataType(64));
		tableHistorypatients.createColumn("Language", new VarCharDataType(32));
		tableHistorypatients.createColumn("SpiritualPractices", new VarCharDataType(100));
		tableHistorypatients.createColumn("AlternateName", new VarCharDataType(32));
		this.createPrimaryKeyConstraint(tableHistorypatients, tableHistorypatients.getColumn("ID"));
		this.createCheckConstraint(tableHistorypatients, new InExpression(new ColumnExpression(tableHistorypatients, tableHistorypatients.getColumn("state")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));
		this.createCheckConstraint(tableHistorypatients, new InExpression(new ColumnExpression(tableHistorypatients, tableHistorypatients.getColumn("ICState")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));
		this.createNotNullConstraint(tableHistorypatients, tableHistorypatients.getColumn("changeDate"));
		this.createNotNullConstraint(tableHistorypatients, tableHistorypatients.getColumn("changeMID"));
		this.createNotNullConstraint(tableHistorypatients, tableHistorypatients.getColumn("MID"));

		Table tableLoginfailures = this.createTable("LoginFailures");
		tableLoginfailures.createColumn("ipaddress", new VarCharDataType(100));
		tableLoginfailures.createColumn("failureCount", new IntDataType());
		tableLoginfailures.createColumn("lastFailure", new TimestampDataType());
		this.createPrimaryKeyConstraint(tableLoginfailures, tableLoginfailures.getColumn("ipaddress"));
		this.createNotNullConstraint(tableLoginfailures, tableLoginfailures.getColumn("ipaddress"));
		this.createNotNullConstraint(tableLoginfailures, tableLoginfailures.getColumn("failureCount"));
		this.createNotNullConstraint(tableLoginfailures, tableLoginfailures.getColumn("lastFailure"));

		Table tableResetpasswordfailures = this.createTable("ResetPasswordFailures");
		tableResetpasswordfailures.createColumn("ipaddress", new VarCharDataType(128));
		tableResetpasswordfailures.createColumn("failureCount", new IntDataType());
		tableResetpasswordfailures.createColumn("lastFailure", new TimestampDataType());
		this.createPrimaryKeyConstraint(tableResetpasswordfailures, tableResetpasswordfailures.getColumn("ipaddress"));
		this.createNotNullConstraint(tableResetpasswordfailures, tableResetpasswordfailures.getColumn("ipaddress"));
		this.createNotNullConstraint(tableResetpasswordfailures, tableResetpasswordfailures.getColumn("failureCount"));
		this.createNotNullConstraint(tableResetpasswordfailures, tableResetpasswordfailures.getColumn("lastFailure"));

		Table tableIcdcodes = this.createTable("icdcodes");
		tableIcdcodes.createColumn("Code", new NumericDataType(5, 2));
		tableIcdcodes.createColumn("Description", new VarCharDataType(50));
		tableIcdcodes.createColumn("Chronic", new VarCharDataType(3));
		this.createPrimaryKeyConstraint(tableIcdcodes, tableIcdcodes.getColumn("Code"));
		this.createCheckConstraint(tableIcdcodes, new InExpression(new ColumnExpression(tableIcdcodes, tableIcdcodes.getColumn("Chronic")), new ListExpression(new ConstantExpression(new StringValue("no")), new ConstantExpression(new StringValue("yes"))), false));
		this.createNotNullConstraint(tableIcdcodes, tableIcdcodes.getColumn("Code"));
		this.createNotNullConstraint(tableIcdcodes, tableIcdcodes.getColumn("Description"));
		this.createNotNullConstraint(tableIcdcodes, tableIcdcodes.getColumn("Chronic"));

		Table tableCptcodes = this.createTable("CPTCodes");
		tableCptcodes.createColumn("Code", new VarCharDataType(5));
		tableCptcodes.createColumn("Description", new VarCharDataType(30));
		tableCptcodes.createColumn("Attribute", new VarCharDataType(30));
		this.createPrimaryKeyConstraint(tableCptcodes, tableCptcodes.getColumn("Code"));
		this.createNotNullConstraint(tableCptcodes, tableCptcodes.getColumn("Code"));
		this.createNotNullConstraint(tableCptcodes, tableCptcodes.getColumn("Description"));

		Table tableDrugreactionoverridecodes = this.createTable("DrugReactionOverrideCodes");
		tableDrugreactionoverridecodes.createColumn("Code", new VarCharDataType(5));
		tableDrugreactionoverridecodes.createColumn("Description", new VarCharDataType(80));
		this.createPrimaryKeyConstraint(tableDrugreactionoverridecodes, tableDrugreactionoverridecodes.getColumn("Code"));
		this.createNotNullConstraint(tableDrugreactionoverridecodes, tableDrugreactionoverridecodes.getColumn("Code"));
		this.createNotNullConstraint(tableDrugreactionoverridecodes, tableDrugreactionoverridecodes.getColumn("Description"));

		Table tableNdcodes = this.createTable("NDCodes");
		tableNdcodes.createColumn("Code", new VarCharDataType(10));
		tableNdcodes.createColumn("Description", new VarCharDataType(100));
		this.createPrimaryKeyConstraint(tableNdcodes, tableNdcodes.getColumn("Code"));
		this.createNotNullConstraint(tableNdcodes, tableNdcodes.getColumn("Code"));
		this.createNotNullConstraint(tableNdcodes, tableNdcodes.getColumn("Description"));

		Table tableDruginteractions = this.createTable("DrugInteractions");
		tableDruginteractions.createColumn("FirstDrug", new VarCharDataType(9));
		tableDruginteractions.createColumn("SecondDrug", new VarCharDataType(9));
		tableDruginteractions.createColumn("Description", new VarCharDataType(100));
		this.createPrimaryKeyConstraint(tableDruginteractions, tableDruginteractions.getColumn("FirstDrug"), tableDruginteractions.getColumn("SecondDrug"));
		this.createNotNullConstraint(tableDruginteractions, tableDruginteractions.getColumn("FirstDrug"));
		this.createNotNullConstraint(tableDruginteractions, tableDruginteractions.getColumn("SecondDrug"));
		this.createNotNullConstraint(tableDruginteractions, tableDruginteractions.getColumn("Description"));

		Table tableTransactionlog = this.createTable("TransactionLog");
		tableTransactionlog.createColumn("transactionID", new IntDataType());
		tableTransactionlog.createColumn("loggedInMID", new IntDataType());
		tableTransactionlog.createColumn("secondaryMID", new IntDataType());
		tableTransactionlog.createColumn("transactionCode", new IntDataType());
		tableTransactionlog.createColumn("timeLogged", new TimestampDataType());
		tableTransactionlog.createColumn("addedInfo", new VarCharDataType(255));
		this.createPrimaryKeyConstraint(tableTransactionlog, tableTransactionlog.getColumn("transactionID"));
		this.createNotNullConstraint(tableTransactionlog, tableTransactionlog.getColumn("transactionID"));
		this.createNotNullConstraint(tableTransactionlog, tableTransactionlog.getColumn("loggedInMID"));
		this.createNotNullConstraint(tableTransactionlog, tableTransactionlog.getColumn("secondaryMID"));
		this.createNotNullConstraint(tableTransactionlog, tableTransactionlog.getColumn("transactionCode"));
		this.createNotNullConstraint(tableTransactionlog, tableTransactionlog.getColumn("timeLogged"));

		Table tableHcprelations = this.createTable("HCPRelations");
		tableHcprelations.createColumn("HCP", new IntDataType());
		tableHcprelations.createColumn("UAP", new IntDataType());
		this.createPrimaryKeyConstraint(tableHcprelations, tableHcprelations.getColumn("HCP"), tableHcprelations.getColumn("UAP"));
		this.createNotNullConstraint(tableHcprelations, tableHcprelations.getColumn("HCP"));
		this.createNotNullConstraint(tableHcprelations, tableHcprelations.getColumn("UAP"));

		Table tablePersonalrelations = this.createTable("PersonalRelations");
		tablePersonalrelations.createColumn("PatientID", new IntDataType());
		tablePersonalrelations.createColumn("RelativeID", new IntDataType());
		tablePersonalrelations.createColumn("RelativeType", new VarCharDataType(35));
		this.createNotNullConstraint(tablePersonalrelations, tablePersonalrelations.getColumn("PatientID"));
		this.createNotNullConstraint(tablePersonalrelations, tablePersonalrelations.getColumn("RelativeID"));
		this.createNotNullConstraint(tablePersonalrelations, tablePersonalrelations.getColumn("RelativeType"));

		Table tableRepresentatives = this.createTable("Representatives");
		tableRepresentatives.createColumn("representerMID", new IntDataType());
		tableRepresentatives.createColumn("representeeMID", new IntDataType());
		this.createPrimaryKeyConstraint(tableRepresentatives, tableRepresentatives.getColumn("representerMID"), tableRepresentatives.getColumn("representeeMID"));

		Table tableHcpassignedhos = this.createTable("HCPAssignedHos");
		tableHcpassignedhos.createColumn("hosID", new VarCharDataType(10));
		tableHcpassignedhos.createColumn("HCPID", new IntDataType());
		this.createPrimaryKeyConstraint(tableHcpassignedhos, tableHcpassignedhos.getColumn("hosID"), tableHcpassignedhos.getColumn("HCPID"));
		this.createNotNullConstraint(tableHcpassignedhos, tableHcpassignedhos.getColumn("hosID"));
		this.createNotNullConstraint(tableHcpassignedhos, tableHcpassignedhos.getColumn("HCPID"));

		Table tableDeclaredhcp = this.createTable("DeclaredHCP");
		tableDeclaredhcp.createColumn("PatientID", new IntDataType());
		tableDeclaredhcp.createColumn("HCPID", new IntDataType());
		this.createPrimaryKeyConstraint(tableDeclaredhcp, tableDeclaredhcp.getColumn("PatientID"), tableDeclaredhcp.getColumn("HCPID"));
		this.createNotNullConstraint(tableDeclaredhcp, tableDeclaredhcp.getColumn("PatientID"));
		this.createNotNullConstraint(tableDeclaredhcp, tableDeclaredhcp.getColumn("HCPID"));

		Table tableOfficevisits = this.createTable("OfficeVisits");
		tableOfficevisits.createColumn("ID", new IntDataType());
		tableOfficevisits.createColumn("visitDate", new DateDataType());
		tableOfficevisits.createColumn("HCPID", new IntDataType());
		tableOfficevisits.createColumn("notes", new VarCharDataType(50));
		tableOfficevisits.createColumn("PatientID", new IntDataType());
		tableOfficevisits.createColumn("HospitalID", new VarCharDataType(10));
		this.createPrimaryKeyConstraint(tableOfficevisits, tableOfficevisits.getColumn("ID"));

		Table tablePersonalhealthinformation = this.createTable("PersonalHealthInformation");
		tablePersonalhealthinformation.createColumn("PatientID", new IntDataType());
		tablePersonalhealthinformation.createColumn("Height", new IntDataType());
		tablePersonalhealthinformation.createColumn("Weight", new IntDataType());
		tablePersonalhealthinformation.createColumn("Smoker", new IntDataType());
		tablePersonalhealthinformation.createColumn("SmokingStatus", new IntDataType());
		tablePersonalhealthinformation.createColumn("BloodPressureN", new IntDataType());
		tablePersonalhealthinformation.createColumn("BloodPressureD", new IntDataType());
		tablePersonalhealthinformation.createColumn("CholesterolHDL", new IntDataType());
		tablePersonalhealthinformation.createColumn("CholesterolLDL", new IntDataType());
		tablePersonalhealthinformation.createColumn("CholesterolTri", new IntDataType());
		tablePersonalhealthinformation.createColumn("HCPID", new IntDataType());
		tablePersonalhealthinformation.createColumn("AsOfDate", new TimestampDataType());
		this.createNotNullConstraint(tablePersonalhealthinformation, tablePersonalhealthinformation.getColumn("PatientID"));
		this.createNotNullConstraint(tablePersonalhealthinformation, tablePersonalhealthinformation.getColumn("Smoker"));
		this.createNotNullConstraint(tablePersonalhealthinformation, tablePersonalhealthinformation.getColumn("SmokingStatus"));
		this.createNotNullConstraint(tablePersonalhealthinformation, tablePersonalhealthinformation.getColumn("AsOfDate"));

		Table tablePersonalallergies = this.createTable("PersonalAllergies");
		tablePersonalallergies.createColumn("PatientID", new IntDataType());
		tablePersonalallergies.createColumn("Allergy", new VarCharDataType(50));
		this.createNotNullConstraint(tablePersonalallergies, tablePersonalallergies.getColumn("PatientID"));
		this.createNotNullConstraint(tablePersonalallergies, tablePersonalallergies.getColumn("Allergy"));

		Table tableAllergies = this.createTable("Allergies");
		tableAllergies.createColumn("ID", new IntDataType());
		tableAllergies.createColumn("PatientID", new IntDataType());
		tableAllergies.createColumn("Description", new VarCharDataType(50));
		tableAllergies.createColumn("FirstFound", new TimestampDataType());
		this.createPrimaryKeyConstraint(tableAllergies, tableAllergies.getColumn("ID"));
		this.createNotNullConstraint(tableAllergies, tableAllergies.getColumn("PatientID"));
		this.createNotNullConstraint(tableAllergies, tableAllergies.getColumn("Description"));
		this.createNotNullConstraint(tableAllergies, tableAllergies.getColumn("FirstFound"));

		Table tableOvprocedure = this.createTable("OVProcedure");
		tableOvprocedure.createColumn("ID", new IntDataType());
		tableOvprocedure.createColumn("VisitID", new IntDataType());
		tableOvprocedure.createColumn("CPTCode", new VarCharDataType(5));
		tableOvprocedure.createColumn("HCPID", new VarCharDataType(10));
		this.createPrimaryKeyConstraint(tableOvprocedure, tableOvprocedure.getColumn("ID"));
		this.createNotNullConstraint(tableOvprocedure, tableOvprocedure.getColumn("VisitID"));
		this.createNotNullConstraint(tableOvprocedure, tableOvprocedure.getColumn("CPTCode"));
		this.createNotNullConstraint(tableOvprocedure, tableOvprocedure.getColumn("HCPID"));

		Table tableOvmedication = this.createTable("OVMedication");
		tableOvmedication.createColumn("ID", new IntDataType());
		tableOvmedication.createColumn("VisitID", new IntDataType());
		tableOvmedication.createColumn("NDCode", new VarCharDataType(9));
		tableOvmedication.createColumn("StartDate", new DateDataType());
		tableOvmedication.createColumn("EndDate", new DateDataType());
		tableOvmedication.createColumn("Dosage", new IntDataType());
		tableOvmedication.createColumn("Instructions", new VarCharDataType(500));
		this.createPrimaryKeyConstraint(tableOvmedication, tableOvmedication.getColumn("ID"));
		this.createNotNullConstraint(tableOvmedication, tableOvmedication.getColumn("VisitID"));
		this.createNotNullConstraint(tableOvmedication, tableOvmedication.getColumn("NDCode"));

		Table tableOvreactionoverride = this.createTable("OVReactionOverride");
		tableOvreactionoverride.createColumn("ID", new IntDataType());
		tableOvreactionoverride.createColumn("OVMedicationID", new IntDataType());
		tableOvreactionoverride.createColumn("OverrideCode", new VarCharDataType(5));
		tableOvreactionoverride.createColumn("OverrideComment", new VarCharDataType(255));
		this.createPrimaryKeyConstraint(tableOvreactionoverride, tableOvreactionoverride.getColumn("ID"));
		this.createForeignKeyConstraint(tableOvreactionoverride, tableOvreactionoverride.getColumn("OVMedicationID"), tableOvmedication, tableOvmedication.getColumn("ID"));
		this.createNotNullConstraint(tableOvreactionoverride, tableOvreactionoverride.getColumn("OVMedicationID"));

		Table tableOvdiagnosis = this.createTable("OVDiagnosis");
		tableOvdiagnosis.createColumn("ID", new IntDataType());
		tableOvdiagnosis.createColumn("VisitID", new IntDataType());
		tableOvdiagnosis.createColumn("ICDCode", new DecimalDataType(5, 2));
		this.createPrimaryKeyConstraint(tableOvdiagnosis, tableOvdiagnosis.getColumn("ID"));
		this.createNotNullConstraint(tableOvdiagnosis, tableOvdiagnosis.getColumn("VisitID"));
		this.createNotNullConstraint(tableOvdiagnosis, tableOvdiagnosis.getColumn("ICDCode"));

		Table tableGlobalvariables = this.createTable("GlobalVariables");
		tableGlobalvariables.createColumn("Name", new VarCharDataType(20));
		tableGlobalvariables.createColumn("Value", new VarCharDataType(20));
		this.createPrimaryKeyConstraint(tableGlobalvariables, tableGlobalvariables.getColumn("Name"));

		Table tableFakeemail = this.createTable("FakeEmail");
		tableFakeemail.createColumn("ID", new IntDataType());
		tableFakeemail.createColumn("ToAddr", new VarCharDataType(100));
		tableFakeemail.createColumn("FromAddr", new VarCharDataType(100));
		tableFakeemail.createColumn("Subject", new VarCharDataType(500));
		tableFakeemail.createColumn("Body", new VarCharDataType(2000));
		tableFakeemail.createColumn("AddedDate", new TimestampDataType());
		this.createPrimaryKeyConstraint(tableFakeemail, tableFakeemail.getColumn("ID"));
		this.createNotNullConstraint(tableFakeemail, tableFakeemail.getColumn("AddedDate"));

		Table tableReportrequests = this.createTable("ReportRequests");
		tableReportrequests.createColumn("ID", new IntDataType());
		tableReportrequests.createColumn("RequesterMID", new IntDataType());
		tableReportrequests.createColumn("PatientMID", new IntDataType());
		tableReportrequests.createColumn("ApproverMID", new IntDataType());
		tableReportrequests.createColumn("RequestedDate", new TimestampDataType());
		tableReportrequests.createColumn("ApprovedDate", new TimestampDataType());
		tableReportrequests.createColumn("ViewedDate", new TimestampDataType());
		tableReportrequests.createColumn("Status", new VarCharDataType(30));
		tableReportrequests.createColumn("Comment", new VarCharDataType(50));
		this.createPrimaryKeyConstraint(tableReportrequests, tableReportrequests.getColumn("ID"));

		Table tableOvsurvey = this.createTable("OVSurvey");
		tableOvsurvey.createColumn("VisitID", new IntDataType());
		tableOvsurvey.createColumn("SurveyDate", new TimestampDataType());
		tableOvsurvey.createColumn("WaitingRoomMinutes", new IntDataType());
		tableOvsurvey.createColumn("ExamRoomMinutes", new IntDataType());
		tableOvsurvey.createColumn("VisitSatisfaction", new IntDataType());
		tableOvsurvey.createColumn("TreatmentSatisfaction", new IntDataType());
		this.createPrimaryKeyConstraint(tableOvsurvey, tableOvsurvey.getColumn("VisitID"));
		this.createNotNullConstraint(tableOvsurvey, tableOvsurvey.getColumn("SurveyDate"));

		Table tableLoinc = this.createTable("LOINC");
		tableLoinc.createColumn("LaboratoryProcedureCode", new VarCharDataType(7));
		tableLoinc.createColumn("Component", new VarCharDataType(100));
		tableLoinc.createColumn("KindOfProperty", new VarCharDataType(100));
		tableLoinc.createColumn("TimeAspect", new VarCharDataType(100));
		tableLoinc.createColumn("System", new VarCharDataType(100));
		tableLoinc.createColumn("ScaleType", new VarCharDataType(100));
		tableLoinc.createColumn("MethodType", new VarCharDataType(100));

		Table tableLabprocedure = this.createTable("LabProcedure");
		tableLabprocedure.createColumn("LaboratoryProcedureID", new IntDataType());
		tableLabprocedure.createColumn("PatientMID", new IntDataType());
		tableLabprocedure.createColumn("LaboratoryProcedureCode", new VarCharDataType(7));
		tableLabprocedure.createColumn("Rights", new VarCharDataType(10));
		tableLabprocedure.createColumn("Status", new VarCharDataType(20));
		tableLabprocedure.createColumn("Commentary", new VarCharDataType(50));
		tableLabprocedure.createColumn("Results", new VarCharDataType(50));
		tableLabprocedure.createColumn("NumericalResults", new VarCharDataType(20));
		tableLabprocedure.createColumn("NumericalResultsUnit", new VarCharDataType(20));
		tableLabprocedure.createColumn("UpperBound", new VarCharDataType(20));
		tableLabprocedure.createColumn("LowerBound", new VarCharDataType(20));
		tableLabprocedure.createColumn("OfficeVisitID", new IntDataType());
		tableLabprocedure.createColumn("LabTechID", new IntDataType());
		tableLabprocedure.createColumn("PriorityCode", new IntDataType());
		tableLabprocedure.createColumn("ViewedByPatient", new BooleanDataType());
		tableLabprocedure.createColumn("UpdatedDate", new TimestampDataType());
		this.createPrimaryKeyConstraint(tableLabprocedure, tableLabprocedure.getColumn("LaboratoryProcedureID"));
		this.createNotNullConstraint(tableLabprocedure, tableLabprocedure.getColumn("ViewedByPatient"));
		this.createNotNullConstraint(tableLabprocedure, tableLabprocedure.getColumn("UpdatedDate"));

		Table tableMessage = this.createTable("message");
		tableMessage.createColumn("message_id", new IntDataType());
		tableMessage.createColumn("parent_msg_id", new IntDataType());
		tableMessage.createColumn("from_id", new IntDataType());
		tableMessage.createColumn("to_id", new IntDataType());
		tableMessage.createColumn("sent_date", new TimestampDataType());
		tableMessage.createColumn("message", new VarCharDataType(50));
		tableMessage.createColumn("subject", new VarCharDataType(50));
		tableMessage.createColumn("been_read", new IntDataType());
		this.createNotNullConstraint(tableMessage, tableMessage.getColumn("from_id"));
		this.createNotNullConstraint(tableMessage, tableMessage.getColumn("to_id"));
		this.createNotNullConstraint(tableMessage, tableMessage.getColumn("sent_date"));

		Table tableAppointment = this.createTable("Appointment");
		tableAppointment.createColumn("appt_id", new IntDataType());
		tableAppointment.createColumn("doctor_id", new IntDataType());
		tableAppointment.createColumn("patient_id", new IntDataType());
		tableAppointment.createColumn("sched_date", new TimestampDataType());
		tableAppointment.createColumn("appt_type", new VarCharDataType(30));
		tableAppointment.createColumn("comment", new VarCharDataType(50));
		this.createPrimaryKeyConstraint(tableAppointment, tableAppointment.getColumn("appt_id"));
		this.createNotNullConstraint(tableAppointment, tableAppointment.getColumn("doctor_id"));
		this.createNotNullConstraint(tableAppointment, tableAppointment.getColumn("patient_id"));
		this.createNotNullConstraint(tableAppointment, tableAppointment.getColumn("sched_date"));
		this.createNotNullConstraint(tableAppointment, tableAppointment.getColumn("appt_type"));

		Table tableAppointmenttype = this.createTable("AppointmentType");
		tableAppointmenttype.createColumn("apptType_id", new IntDataType());
		tableAppointmenttype.createColumn("appt_type", new VarCharDataType(30));
		tableAppointmenttype.createColumn("duration", new IntDataType());
		this.createPrimaryKeyConstraint(tableAppointmenttype, tableAppointmenttype.getColumn("apptType_id"));
		this.createNotNullConstraint(tableAppointmenttype, tableAppointmenttype.getColumn("appt_type"));
		this.createNotNullConstraint(tableAppointmenttype, tableAppointmenttype.getColumn("duration"));

		Table tableReferrals = this.createTable("referrals");
		tableReferrals.createColumn("id", new IntDataType());
		tableReferrals.createColumn("PatientID", new IntDataType());
		tableReferrals.createColumn("SenderID", new IntDataType());
		tableReferrals.createColumn("ReceiverID", new IntDataType());
		tableReferrals.createColumn("ReferralDetails", new VarCharDataType(50));
		tableReferrals.createColumn("OVID", new IntDataType());
		tableReferrals.createColumn("viewed_by_patient", new BooleanDataType());
		tableReferrals.createColumn("viewed_by_HCP", new BooleanDataType());
		tableReferrals.createColumn("TimeStamp", new TimestampDataType());
		tableReferrals.createColumn("PriorityCode", new IntDataType());
		this.createPrimaryKeyConstraint(tableReferrals, tableReferrals.getColumn("id"));
		this.createNotNullConstraint(tableReferrals, tableReferrals.getColumn("PatientID"));
		this.createNotNullConstraint(tableReferrals, tableReferrals.getColumn("SenderID"));
		this.createNotNullConstraint(tableReferrals, tableReferrals.getColumn("ReceiverID"));
		this.createNotNullConstraint(tableReferrals, tableReferrals.getColumn("OVID"));
		this.createNotNullConstraint(tableReferrals, tableReferrals.getColumn("viewed_by_patient"));
		this.createNotNullConstraint(tableReferrals, tableReferrals.getColumn("viewed_by_HCP"));
		this.createNotNullConstraint(tableReferrals, tableReferrals.getColumn("TimeStamp"));

		Table tableRemotemonitoringdata = this.createTable("RemoteMonitoringData");
		tableRemotemonitoringdata.createColumn("id", new IntDataType());
		tableRemotemonitoringdata.createColumn("PatientID", new IntDataType());
		tableRemotemonitoringdata.createColumn("systolicBloodPressure", new IntDataType());
		tableRemotemonitoringdata.createColumn("diastolicBloodPressure", new IntDataType());
		tableRemotemonitoringdata.createColumn("glucoseLevel", new IntDataType());
		tableRemotemonitoringdata.createColumn("height", new IntDataType());
		tableRemotemonitoringdata.createColumn("weight", new IntDataType());
		tableRemotemonitoringdata.createColumn("pedometerReading", new IntDataType());
		tableRemotemonitoringdata.createColumn("timeLogged", new TimestampDataType());
		tableRemotemonitoringdata.createColumn("ReporterRole", new VarCharDataType(50));
		tableRemotemonitoringdata.createColumn("ReporterID", new IntDataType());
		this.createPrimaryKeyConstraint(tableRemotemonitoringdata, tableRemotemonitoringdata.getColumn("id"));
		this.createNotNullConstraint(tableRemotemonitoringdata, tableRemotemonitoringdata.getColumn("PatientID"));
		this.createNotNullConstraint(tableRemotemonitoringdata, tableRemotemonitoringdata.getColumn("timeLogged"));

		Table tableRemotemonitoringlists = this.createTable("RemoteMonitoringLists");
		tableRemotemonitoringlists.createColumn("PatientMID", new IntDataType());
		tableRemotemonitoringlists.createColumn("HCPMID", new IntDataType());
		tableRemotemonitoringlists.createColumn("SystolicBloodPressure", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("DiastolicBloodPressure", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("GlucoseLevel", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("Height", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("Weight", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("PedometerReading", new BooleanDataType());
		this.createPrimaryKeyConstraint(tableRemotemonitoringlists, tableRemotemonitoringlists.getColumn("PatientMID"), tableRemotemonitoringlists.getColumn("HCPMID"));

		Table tableAdverseevents = this.createTable("AdverseEvents");
		tableAdverseevents.createColumn("id", new IntDataType());
		tableAdverseevents.createColumn("Status", new VarCharDataType(10));
		tableAdverseevents.createColumn("PatientMID", new IntDataType());
		tableAdverseevents.createColumn("PresImmu", new VarCharDataType(50));
		tableAdverseevents.createColumn("Code", new VarCharDataType(20));
		tableAdverseevents.createColumn("Comment", new VarCharDataType(2000));
		tableAdverseevents.createColumn("Prescriber", new IntDataType());
		tableAdverseevents.createColumn("TimeLogged", new TimestampDataType());
		this.createPrimaryKeyConstraint(tableAdverseevents, tableAdverseevents.getColumn("id"));

		Table tableProfilephotos = this.createTable("ProfilePhotos");
		tableProfilephotos.createColumn("MID", new IntDataType());
		tableProfilephotos.createColumn("Photo", new VarCharDataType(50));
		tableProfilephotos.createColumn("UpdatedDate", new TimestampDataType());
		this.createPrimaryKeyConstraint(tableProfilephotos, tableProfilephotos.getColumn("MID"));
		this.createNotNullConstraint(tableProfilephotos, tableProfilephotos.getColumn("UpdatedDate"));

		Table tablePatientspecificinstructions = this.createTable("PatientSpecificInstructions");
		tablePatientspecificinstructions.createColumn("id", new IntDataType());
		tablePatientspecificinstructions.createColumn("VisitID", new IntDataType());
		tablePatientspecificinstructions.createColumn("Modified", new TimestampDataType());
		tablePatientspecificinstructions.createColumn("Name", new VarCharDataType(100));
		tablePatientspecificinstructions.createColumn("URL", new VarCharDataType(250));
		tablePatientspecificinstructions.createColumn("Comment", new VarCharDataType(500));
		this.createPrimaryKeyConstraint(tablePatientspecificinstructions, tablePatientspecificinstructions.getColumn("id"));
		this.createNotNullConstraint(tablePatientspecificinstructions, tablePatientspecificinstructions.getColumn("Modified"));

		Table tableReferralmessage = this.createTable("ReferralMessage");
		tableReferralmessage.createColumn("messageID", new IntDataType());
		tableReferralmessage.createColumn("referralID", new IntDataType());
		this.createPrimaryKeyConstraint(tableReferralmessage, tableReferralmessage.getColumn("messageID"), tableReferralmessage.getColumn("referralID"));
		this.createNotNullConstraint(tableReferralmessage, tableReferralmessage.getColumn("messageID"));
		this.createNotNullConstraint(tableReferralmessage, tableReferralmessage.getColumn("referralID"));
	}
}

