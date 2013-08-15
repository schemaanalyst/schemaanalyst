package parsedcasestudy;

import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.BooleanDataType;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.DecimalDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;

/*
 * iTrust schema.
 * Java code originally generated: 2013/08/15 10:52:25
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
		tableUsers.createPrimaryKeyConstraint(tableUsers.getColumn("MID"));
		tableUsers.createNotNullConstraint(tableUsers.getColumn("Role"));
		tableUsers.createCheckConstraint(new InExpression(new ColumnExpression(tableUsers, tableUsers.getColumn("Role")), new ListExpression(new ConstantExpression(new StringValue("patient")), new ConstantExpression(new StringValue("admin")), new ConstantExpression(new StringValue("hcp")), new ConstantExpression(new StringValue("uap")), new ConstantExpression(new StringValue("er")), new ConstantExpression(new StringValue("tester")), new ConstantExpression(new StringValue("pha")), new ConstantExpression(new StringValue("lt"))), false));

		Table tableHospitals = this.createTable("Hospitals");
		tableHospitals.createColumn("HospitalID", new VarCharDataType(10));
		tableHospitals.createColumn("HospitalName", new VarCharDataType(30));
		tableHospitals.createPrimaryKeyConstraint(tableHospitals.getColumn("HospitalID"));
		tableHospitals.createNotNullConstraint(tableHospitals.getColumn("HospitalName"));

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
		tablePersonnel.createPrimaryKeyConstraint(tablePersonnel.getColumn("MID"));
		tablePersonnel.createNotNullConstraint(tablePersonnel.getColumn("role"));
		tablePersonnel.createNotNullConstraint(tablePersonnel.getColumn("enabled"));
		tablePersonnel.createNotNullConstraint(tablePersonnel.getColumn("lastName"));
		tablePersonnel.createNotNullConstraint(tablePersonnel.getColumn("firstName"));
		tablePersonnel.createNotNullConstraint(tablePersonnel.getColumn("address1"));
		tablePersonnel.createNotNullConstraint(tablePersonnel.getColumn("address2"));
		tablePersonnel.createNotNullConstraint(tablePersonnel.getColumn("city"));
		tablePersonnel.createNotNullConstraint(tablePersonnel.getColumn("state"));
		tablePersonnel.createCheckConstraint(new InExpression(new ColumnExpression(tablePersonnel, tablePersonnel.getColumn("role")), new ListExpression(new ConstantExpression(new StringValue("admin")), new ConstantExpression(new StringValue("hcp")), new ConstantExpression(new StringValue("uap")), new ConstantExpression(new StringValue("er")), new ConstantExpression(new StringValue("tester")), new ConstantExpression(new StringValue("pha")), new ConstantExpression(new StringValue("lt"))), false));
		tablePersonnel.createCheckConstraint(new InExpression(new ColumnExpression(tablePersonnel, tablePersonnel.getColumn("state")), new ListExpression(new ConstantExpression(new StringValue("")), new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));

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
		tablePatients.createPrimaryKeyConstraint(tablePatients.getColumn("MID"));
		tablePatients.createCheckConstraint(new InExpression(new ColumnExpression(tablePatients, tablePatients.getColumn("state")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));
		tablePatients.createCheckConstraint(new InExpression(new ColumnExpression(tablePatients, tablePatients.getColumn("ICState")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));

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
		tableHistorypatients.createPrimaryKeyConstraint(tableHistorypatients.getColumn("ID"));
		tableHistorypatients.createNotNullConstraint(tableHistorypatients.getColumn("changeDate"));
		tableHistorypatients.createNotNullConstraint(tableHistorypatients.getColumn("changeMID"));
		tableHistorypatients.createNotNullConstraint(tableHistorypatients.getColumn("MID"));
		tableHistorypatients.createCheckConstraint(new InExpression(new ColumnExpression(tableHistorypatients, tableHistorypatients.getColumn("state")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));
		tableHistorypatients.createCheckConstraint(new InExpression(new ColumnExpression(tableHistorypatients, tableHistorypatients.getColumn("ICState")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));

		Table tableLoginfailures = this.createTable("LoginFailures");
		tableLoginfailures.createColumn("ipaddress", new VarCharDataType(100));
		tableLoginfailures.createColumn("failureCount", new IntDataType());
		tableLoginfailures.createColumn("lastFailure", new TimestampDataType());
		tableLoginfailures.createPrimaryKeyConstraint(tableLoginfailures.getColumn("ipaddress"));
		tableLoginfailures.createNotNullConstraint(tableLoginfailures.getColumn("ipaddress"));
		tableLoginfailures.createNotNullConstraint(tableLoginfailures.getColumn("failureCount"));
		tableLoginfailures.createNotNullConstraint(tableLoginfailures.getColumn("lastFailure"));

		Table tableResetpasswordfailures = this.createTable("ResetPasswordFailures");
		tableResetpasswordfailures.createColumn("ipaddress", new VarCharDataType(128));
		tableResetpasswordfailures.createColumn("failureCount", new IntDataType());
		tableResetpasswordfailures.createColumn("lastFailure", new TimestampDataType());
		tableResetpasswordfailures.createPrimaryKeyConstraint(tableResetpasswordfailures.getColumn("ipaddress"));
		tableResetpasswordfailures.createNotNullConstraint(tableResetpasswordfailures.getColumn("ipaddress"));
		tableResetpasswordfailures.createNotNullConstraint(tableResetpasswordfailures.getColumn("failureCount"));
		tableResetpasswordfailures.createNotNullConstraint(tableResetpasswordfailures.getColumn("lastFailure"));

		Table tableIcdcodes = this.createTable("icdcodes");
		tableIcdcodes.createColumn("Code", new NumericDataType(5, 2));
		tableIcdcodes.createColumn("Description", new VarCharDataType(50));
		tableIcdcodes.createColumn("Chronic", new VarCharDataType(3));
		tableIcdcodes.createPrimaryKeyConstraint(tableIcdcodes.getColumn("Code"));
		tableIcdcodes.createNotNullConstraint(tableIcdcodes.getColumn("Code"));
		tableIcdcodes.createNotNullConstraint(tableIcdcodes.getColumn("Description"));
		tableIcdcodes.createNotNullConstraint(tableIcdcodes.getColumn("Chronic"));
		tableIcdcodes.createCheckConstraint(new InExpression(new ColumnExpression(tableIcdcodes, tableIcdcodes.getColumn("Chronic")), new ListExpression(new ConstantExpression(new StringValue("no")), new ConstantExpression(new StringValue("yes"))), false));

		Table tableCptcodes = this.createTable("CPTCodes");
		tableCptcodes.createColumn("Code", new VarCharDataType(5));
		tableCptcodes.createColumn("Description", new VarCharDataType(30));
		tableCptcodes.createColumn("Attribute", new VarCharDataType(30));
		tableCptcodes.createPrimaryKeyConstraint(tableCptcodes.getColumn("Code"));
		tableCptcodes.createNotNullConstraint(tableCptcodes.getColumn("Code"));
		tableCptcodes.createNotNullConstraint(tableCptcodes.getColumn("Description"));

		Table tableDrugreactionoverridecodes = this.createTable("DrugReactionOverrideCodes");
		tableDrugreactionoverridecodes.createColumn("Code", new VarCharDataType(5));
		tableDrugreactionoverridecodes.createColumn("Description", new VarCharDataType(80));
		tableDrugreactionoverridecodes.createPrimaryKeyConstraint(tableDrugreactionoverridecodes.getColumn("Code"));
		tableDrugreactionoverridecodes.createNotNullConstraint(tableDrugreactionoverridecodes.getColumn("Code"));
		tableDrugreactionoverridecodes.createNotNullConstraint(tableDrugreactionoverridecodes.getColumn("Description"));

		Table tableNdcodes = this.createTable("NDCodes");
		tableNdcodes.createColumn("Code", new VarCharDataType(10));
		tableNdcodes.createColumn("Description", new VarCharDataType(100));
		tableNdcodes.createPrimaryKeyConstraint(tableNdcodes.getColumn("Code"));
		tableNdcodes.createNotNullConstraint(tableNdcodes.getColumn("Code"));
		tableNdcodes.createNotNullConstraint(tableNdcodes.getColumn("Description"));

		Table tableDruginteractions = this.createTable("DrugInteractions");
		tableDruginteractions.createColumn("FirstDrug", new VarCharDataType(9));
		tableDruginteractions.createColumn("SecondDrug", new VarCharDataType(9));
		tableDruginteractions.createColumn("Description", new VarCharDataType(100));
		tableDruginteractions.createPrimaryKeyConstraint(tableDruginteractions.getColumn("FirstDrug"), tableDruginteractions.getColumn("SecondDrug"));
		tableDruginteractions.createNotNullConstraint(tableDruginteractions.getColumn("FirstDrug"));
		tableDruginteractions.createNotNullConstraint(tableDruginteractions.getColumn("SecondDrug"));
		tableDruginteractions.createNotNullConstraint(tableDruginteractions.getColumn("Description"));

		Table tableTransactionlog = this.createTable("TransactionLog");
		tableTransactionlog.createColumn("transactionID", new IntDataType());
		tableTransactionlog.createColumn("loggedInMID", new IntDataType());
		tableTransactionlog.createColumn("secondaryMID", new IntDataType());
		tableTransactionlog.createColumn("transactionCode", new IntDataType());
		tableTransactionlog.createColumn("timeLogged", new TimestampDataType());
		tableTransactionlog.createColumn("addedInfo", new VarCharDataType(255));
		tableTransactionlog.createPrimaryKeyConstraint(tableTransactionlog.getColumn("transactionID"));
		tableTransactionlog.createNotNullConstraint(tableTransactionlog.getColumn("transactionID"));
		tableTransactionlog.createNotNullConstraint(tableTransactionlog.getColumn("loggedInMID"));
		tableTransactionlog.createNotNullConstraint(tableTransactionlog.getColumn("secondaryMID"));
		tableTransactionlog.createNotNullConstraint(tableTransactionlog.getColumn("transactionCode"));
		tableTransactionlog.createNotNullConstraint(tableTransactionlog.getColumn("timeLogged"));

		Table tableHcprelations = this.createTable("HCPRelations");
		tableHcprelations.createColumn("HCP", new IntDataType());
		tableHcprelations.createColumn("UAP", new IntDataType());
		tableHcprelations.createPrimaryKeyConstraint(tableHcprelations.getColumn("HCP"), tableHcprelations.getColumn("UAP"));
		tableHcprelations.createNotNullConstraint(tableHcprelations.getColumn("HCP"));
		tableHcprelations.createNotNullConstraint(tableHcprelations.getColumn("UAP"));

		Table tablePersonalrelations = this.createTable("PersonalRelations");
		tablePersonalrelations.createColumn("PatientID", new IntDataType());
		tablePersonalrelations.createColumn("RelativeID", new IntDataType());
		tablePersonalrelations.createColumn("RelativeType", new VarCharDataType(35));
		tablePersonalrelations.createNotNullConstraint(tablePersonalrelations.getColumn("PatientID"));
		tablePersonalrelations.createNotNullConstraint(tablePersonalrelations.getColumn("RelativeID"));
		tablePersonalrelations.createNotNullConstraint(tablePersonalrelations.getColumn("RelativeType"));

		Table tableRepresentatives = this.createTable("Representatives");
		tableRepresentatives.createColumn("representerMID", new IntDataType());
		tableRepresentatives.createColumn("representeeMID", new IntDataType());
		tableRepresentatives.createPrimaryKeyConstraint(tableRepresentatives.getColumn("representerMID"), tableRepresentatives.getColumn("representeeMID"));

		Table tableHcpassignedhos = this.createTable("HCPAssignedHos");
		tableHcpassignedhos.createColumn("hosID", new VarCharDataType(10));
		tableHcpassignedhos.createColumn("HCPID", new IntDataType());
		tableHcpassignedhos.createPrimaryKeyConstraint(tableHcpassignedhos.getColumn("hosID"), tableHcpassignedhos.getColumn("HCPID"));
		tableHcpassignedhos.createNotNullConstraint(tableHcpassignedhos.getColumn("hosID"));
		tableHcpassignedhos.createNotNullConstraint(tableHcpassignedhos.getColumn("HCPID"));

		Table tableDeclaredhcp = this.createTable("DeclaredHCP");
		tableDeclaredhcp.createColumn("PatientID", new IntDataType());
		tableDeclaredhcp.createColumn("HCPID", new IntDataType());
		tableDeclaredhcp.createPrimaryKeyConstraint(tableDeclaredhcp.getColumn("PatientID"), tableDeclaredhcp.getColumn("HCPID"));
		tableDeclaredhcp.createNotNullConstraint(tableDeclaredhcp.getColumn("PatientID"));
		tableDeclaredhcp.createNotNullConstraint(tableDeclaredhcp.getColumn("HCPID"));

		Table tableOfficevisits = this.createTable("OfficeVisits");
		tableOfficevisits.createColumn("ID", new IntDataType());
		tableOfficevisits.createColumn("visitDate", new DateDataType());
		tableOfficevisits.createColumn("HCPID", new IntDataType());
		tableOfficevisits.createColumn("notes", new VarCharDataType(50));
		tableOfficevisits.createColumn("PatientID", new IntDataType());
		tableOfficevisits.createColumn("HospitalID", new VarCharDataType(10));
		tableOfficevisits.createPrimaryKeyConstraint(tableOfficevisits.getColumn("ID"));

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
		tablePersonalhealthinformation.createNotNullConstraint(tablePersonalhealthinformation.getColumn("PatientID"));
		tablePersonalhealthinformation.createNotNullConstraint(tablePersonalhealthinformation.getColumn("Smoker"));
		tablePersonalhealthinformation.createNotNullConstraint(tablePersonalhealthinformation.getColumn("SmokingStatus"));
		tablePersonalhealthinformation.createNotNullConstraint(tablePersonalhealthinformation.getColumn("AsOfDate"));

		Table tablePersonalallergies = this.createTable("PersonalAllergies");
		tablePersonalallergies.createColumn("PatientID", new IntDataType());
		tablePersonalallergies.createColumn("Allergy", new VarCharDataType(50));
		tablePersonalallergies.createNotNullConstraint(tablePersonalallergies.getColumn("PatientID"));
		tablePersonalallergies.createNotNullConstraint(tablePersonalallergies.getColumn("Allergy"));

		Table tableAllergies = this.createTable("Allergies");
		tableAllergies.createColumn("ID", new IntDataType());
		tableAllergies.createColumn("PatientID", new IntDataType());
		tableAllergies.createColumn("Description", new VarCharDataType(50));
		tableAllergies.createColumn("FirstFound", new TimestampDataType());
		tableAllergies.createPrimaryKeyConstraint(tableAllergies.getColumn("ID"));
		tableAllergies.createNotNullConstraint(tableAllergies.getColumn("PatientID"));
		tableAllergies.createNotNullConstraint(tableAllergies.getColumn("Description"));
		tableAllergies.createNotNullConstraint(tableAllergies.getColumn("FirstFound"));

		Table tableOvprocedure = this.createTable("OVProcedure");
		tableOvprocedure.createColumn("ID", new IntDataType());
		tableOvprocedure.createColumn("VisitID", new IntDataType());
		tableOvprocedure.createColumn("CPTCode", new VarCharDataType(5));
		tableOvprocedure.createColumn("HCPID", new VarCharDataType(10));
		tableOvprocedure.createPrimaryKeyConstraint(tableOvprocedure.getColumn("ID"));
		tableOvprocedure.createNotNullConstraint(tableOvprocedure.getColumn("VisitID"));
		tableOvprocedure.createNotNullConstraint(tableOvprocedure.getColumn("CPTCode"));
		tableOvprocedure.createNotNullConstraint(tableOvprocedure.getColumn("HCPID"));

		Table tableOvmedication = this.createTable("OVMedication");
		tableOvmedication.createColumn("ID", new IntDataType());
		tableOvmedication.createColumn("VisitID", new IntDataType());
		tableOvmedication.createColumn("NDCode", new VarCharDataType(9));
		tableOvmedication.createColumn("StartDate", new DateDataType());
		tableOvmedication.createColumn("EndDate", new DateDataType());
		tableOvmedication.createColumn("Dosage", new IntDataType());
		tableOvmedication.createColumn("Instructions", new VarCharDataType(500));
		tableOvmedication.createPrimaryKeyConstraint(tableOvmedication.getColumn("ID"));
		tableOvmedication.createNotNullConstraint(tableOvmedication.getColumn("VisitID"));
		tableOvmedication.createNotNullConstraint(tableOvmedication.getColumn("NDCode"));

		Table tableOvreactionoverride = this.createTable("OVReactionOverride");
		tableOvreactionoverride.createColumn("ID", new IntDataType());
		tableOvreactionoverride.createColumn("OVMedicationID", new IntDataType());
		tableOvreactionoverride.createColumn("OverrideCode", new VarCharDataType(5));
		tableOvreactionoverride.createColumn("OverrideComment", new VarCharDataType(255));
		tableOvreactionoverride.createPrimaryKeyConstraint(tableOvreactionoverride.getColumn("ID"));
		tableOvreactionoverride.createForeignKeyConstraint(tableOvreactionoverride.getColumn("OVMedicationID"), tableOvmedication, tableOvreactionoverride.getColumn("ID"));
		tableOvreactionoverride.createNotNullConstraint(tableOvreactionoverride.getColumn("OVMedicationID"));

		Table tableOvdiagnosis = this.createTable("OVDiagnosis");
		tableOvdiagnosis.createColumn("ID", new IntDataType());
		tableOvdiagnosis.createColumn("VisitID", new IntDataType());
		tableOvdiagnosis.createColumn("ICDCode", new DecimalDataType(5, 2));
		tableOvdiagnosis.createPrimaryKeyConstraint(tableOvdiagnosis.getColumn("ID"));
		tableOvdiagnosis.createNotNullConstraint(tableOvdiagnosis.getColumn("VisitID"));
		tableOvdiagnosis.createNotNullConstraint(tableOvdiagnosis.getColumn("ICDCode"));

		Table tableGlobalvariables = this.createTable("GlobalVariables");
		tableGlobalvariables.createColumn("Name", new VarCharDataType(20));
		tableGlobalvariables.createColumn("Value", new VarCharDataType(20));
		tableGlobalvariables.createPrimaryKeyConstraint(tableGlobalvariables.getColumn("Name"));

		Table tableFakeemail = this.createTable("FakeEmail");
		tableFakeemail.createColumn("ID", new IntDataType());
		tableFakeemail.createColumn("ToAddr", new VarCharDataType(100));
		tableFakeemail.createColumn("FromAddr", new VarCharDataType(100));
		tableFakeemail.createColumn("Subject", new VarCharDataType(500));
		tableFakeemail.createColumn("Body", new VarCharDataType(2000));
		tableFakeemail.createColumn("AddedDate", new TimestampDataType());
		tableFakeemail.createPrimaryKeyConstraint(tableFakeemail.getColumn("ID"));
		tableFakeemail.createNotNullConstraint(tableFakeemail.getColumn("AddedDate"));

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
		tableReportrequests.createPrimaryKeyConstraint(tableReportrequests.getColumn("ID"));

		Table tableOvsurvey = this.createTable("OVSurvey");
		tableOvsurvey.createColumn("VisitID", new IntDataType());
		tableOvsurvey.createColumn("SurveyDate", new TimestampDataType());
		tableOvsurvey.createColumn("WaitingRoomMinutes", new IntDataType());
		tableOvsurvey.createColumn("ExamRoomMinutes", new IntDataType());
		tableOvsurvey.createColumn("VisitSatisfaction", new IntDataType());
		tableOvsurvey.createColumn("TreatmentSatisfaction", new IntDataType());
		tableOvsurvey.createPrimaryKeyConstraint(tableOvsurvey.getColumn("VisitID"));
		tableOvsurvey.createNotNullConstraint(tableOvsurvey.getColumn("SurveyDate"));

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
		tableLabprocedure.createPrimaryKeyConstraint(tableLabprocedure.getColumn("LaboratoryProcedureID"));
		tableLabprocedure.createNotNullConstraint(tableLabprocedure.getColumn("ViewedByPatient"));
		tableLabprocedure.createNotNullConstraint(tableLabprocedure.getColumn("UpdatedDate"));

		Table tableMessage = this.createTable("message");
		tableMessage.createColumn("message_id", new IntDataType());
		tableMessage.createColumn("parent_msg_id", new IntDataType());
		tableMessage.createColumn("from_id", new IntDataType());
		tableMessage.createColumn("to_id", new IntDataType());
		tableMessage.createColumn("sent_date", new TimestampDataType());
		tableMessage.createColumn("message", new VarCharDataType(50));
		tableMessage.createColumn("subject", new VarCharDataType(50));
		tableMessage.createColumn("been_read", new IntDataType());
		tableMessage.createNotNullConstraint(tableMessage.getColumn("from_id"));
		tableMessage.createNotNullConstraint(tableMessage.getColumn("to_id"));
		tableMessage.createNotNullConstraint(tableMessage.getColumn("sent_date"));

		Table tableAppointment = this.createTable("Appointment");
		tableAppointment.createColumn("appt_id", new IntDataType());
		tableAppointment.createColumn("doctor_id", new IntDataType());
		tableAppointment.createColumn("patient_id", new IntDataType());
		tableAppointment.createColumn("sched_date", new TimestampDataType());
		tableAppointment.createColumn("appt_type", new VarCharDataType(30));
		tableAppointment.createColumn("comment", new VarCharDataType(50));
		tableAppointment.createPrimaryKeyConstraint(tableAppointment.getColumn("appt_id"));
		tableAppointment.createNotNullConstraint(tableAppointment.getColumn("doctor_id"));
		tableAppointment.createNotNullConstraint(tableAppointment.getColumn("patient_id"));
		tableAppointment.createNotNullConstraint(tableAppointment.getColumn("sched_date"));
		tableAppointment.createNotNullConstraint(tableAppointment.getColumn("appt_type"));

		Table tableAppointmenttype = this.createTable("AppointmentType");
		tableAppointmenttype.createColumn("apptType_id", new IntDataType());
		tableAppointmenttype.createColumn("appt_type", new VarCharDataType(30));
		tableAppointmenttype.createColumn("duration", new IntDataType());
		tableAppointmenttype.createPrimaryKeyConstraint(tableAppointmenttype.getColumn("apptType_id"));
		tableAppointmenttype.createNotNullConstraint(tableAppointmenttype.getColumn("appt_type"));
		tableAppointmenttype.createNotNullConstraint(tableAppointmenttype.getColumn("duration"));

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
		tableReferrals.createPrimaryKeyConstraint(tableReferrals.getColumn("id"));
		tableReferrals.createNotNullConstraint(tableReferrals.getColumn("PatientID"));
		tableReferrals.createNotNullConstraint(tableReferrals.getColumn("SenderID"));
		tableReferrals.createNotNullConstraint(tableReferrals.getColumn("ReceiverID"));
		tableReferrals.createNotNullConstraint(tableReferrals.getColumn("OVID"));
		tableReferrals.createNotNullConstraint(tableReferrals.getColumn("viewed_by_patient"));
		tableReferrals.createNotNullConstraint(tableReferrals.getColumn("viewed_by_HCP"));
		tableReferrals.createNotNullConstraint(tableReferrals.getColumn("TimeStamp"));

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
		tableRemotemonitoringdata.createPrimaryKeyConstraint(tableRemotemonitoringdata.getColumn("id"));
		tableRemotemonitoringdata.createNotNullConstraint(tableRemotemonitoringdata.getColumn("PatientID"));
		tableRemotemonitoringdata.createNotNullConstraint(tableRemotemonitoringdata.getColumn("timeLogged"));

		Table tableRemotemonitoringlists = this.createTable("RemoteMonitoringLists");
		tableRemotemonitoringlists.createColumn("PatientMID", new IntDataType());
		tableRemotemonitoringlists.createColumn("HCPMID", new IntDataType());
		tableRemotemonitoringlists.createColumn("SystolicBloodPressure", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("DiastolicBloodPressure", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("GlucoseLevel", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("Height", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("Weight", new BooleanDataType());
		tableRemotemonitoringlists.createColumn("PedometerReading", new BooleanDataType());
		tableRemotemonitoringlists.createPrimaryKeyConstraint(tableRemotemonitoringlists.getColumn("PatientMID"), tableRemotemonitoringlists.getColumn("HCPMID"));

		Table tableAdverseevents = this.createTable("AdverseEvents");
		tableAdverseevents.createColumn("id", new IntDataType());
		tableAdverseevents.createColumn("Status", new VarCharDataType(10));
		tableAdverseevents.createColumn("PatientMID", new IntDataType());
		tableAdverseevents.createColumn("PresImmu", new VarCharDataType(50));
		tableAdverseevents.createColumn("Code", new VarCharDataType(20));
		tableAdverseevents.createColumn("Comment", new VarCharDataType(2000));
		tableAdverseevents.createColumn("Prescriber", new IntDataType());
		tableAdverseevents.createColumn("TimeLogged", new TimestampDataType());
		tableAdverseevents.createPrimaryKeyConstraint(tableAdverseevents.getColumn("id"));

		Table tableProfilephotos = this.createTable("ProfilePhotos");
		tableProfilephotos.createColumn("MID", new IntDataType());
		tableProfilephotos.createColumn("Photo", new VarCharDataType(50));
		tableProfilephotos.createColumn("UpdatedDate", new TimestampDataType());
		tableProfilephotos.createPrimaryKeyConstraint(tableProfilephotos.getColumn("MID"));
		tableProfilephotos.createNotNullConstraint(tableProfilephotos.getColumn("UpdatedDate"));

		Table tablePatientspecificinstructions = this.createTable("PatientSpecificInstructions");
		tablePatientspecificinstructions.createColumn("id", new IntDataType());
		tablePatientspecificinstructions.createColumn("VisitID", new IntDataType());
		tablePatientspecificinstructions.createColumn("Modified", new TimestampDataType());
		tablePatientspecificinstructions.createColumn("Name", new VarCharDataType(100));
		tablePatientspecificinstructions.createColumn("URL", new VarCharDataType(250));
		tablePatientspecificinstructions.createColumn("Comment", new VarCharDataType(500));
		tablePatientspecificinstructions.createPrimaryKeyConstraint(tablePatientspecificinstructions.getColumn("id"));
		tablePatientspecificinstructions.createNotNullConstraint(tablePatientspecificinstructions.getColumn("Modified"));

		Table tableReferralmessage = this.createTable("ReferralMessage");
		tableReferralmessage.createColumn("messageID", new IntDataType());
		tableReferralmessage.createColumn("referralID", new IntDataType());
		tableReferralmessage.createPrimaryKeyConstraint(tableReferralmessage.getColumn("messageID"), tableReferralmessage.getColumn("referralID"));
		tableReferralmessage.createNotNullConstraint(tableReferralmessage.getColumn("messageID"));
		tableReferralmessage.createNotNullConstraint(tableReferralmessage.getColumn("referralID"));
	}
}

