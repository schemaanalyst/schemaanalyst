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
 * Java code originally generated: 2013/07/11 14:08:50
 *
 */

@SuppressWarnings("serial")
public class iTrust extends Schema {

	public iTrust() {
		super("iTrust");

		Table tableUsers = this.createTable("Users");
		tableUsers.addColumn("MID", new IntDataType());
		tableUsers.addColumn("Password", new VarCharDataType(20));
		tableUsers.addColumn("Role", new VarCharDataType(20));
		tableUsers.addColumn("sQuestion", new VarCharDataType(100));
		tableUsers.addColumn("sAnswer", new VarCharDataType(30));
		tableUsers.setPrimaryKeyConstraint(tableUsers.getColumn("MID"));
		tableUsers.addNotNullConstraint(tableUsers.getColumn("Role"));
		tableUsers.addCheckConstraint(new InExpression(new ColumnExpression(tableUsers.getColumn("Role")), new ListExpression(new ConstantExpression(new StringValue("patient")), new ConstantExpression(new StringValue("admin")), new ConstantExpression(new StringValue("hcp")), new ConstantExpression(new StringValue("uap")), new ConstantExpression(new StringValue("er")), new ConstantExpression(new StringValue("tester")), new ConstantExpression(new StringValue("pha")), new ConstantExpression(new StringValue("lt"))), false));

		Table tableHospitals = this.createTable("Hospitals");
		tableHospitals.addColumn("HospitalID", new VarCharDataType(10));
		tableHospitals.addColumn("HospitalName", new VarCharDataType(30));
		tableHospitals.setPrimaryKeyConstraint(tableHospitals.getColumn("HospitalID"));
		tableHospitals.addNotNullConstraint(tableHospitals.getColumn("HospitalName"));

		Table tablePersonnel = this.createTable("Personnel");
		tablePersonnel.addColumn("MID", new IntDataType());
		tablePersonnel.addColumn("AMID", new IntDataType());
		tablePersonnel.addColumn("role", new VarCharDataType(20));
		tablePersonnel.addColumn("enabled", new IntDataType());
		tablePersonnel.addColumn("lastName", new VarCharDataType(20));
		tablePersonnel.addColumn("firstName", new VarCharDataType(20));
		tablePersonnel.addColumn("address1", new VarCharDataType(20));
		tablePersonnel.addColumn("address2", new VarCharDataType(20));
		tablePersonnel.addColumn("city", new VarCharDataType(15));
		tablePersonnel.addColumn("state", new VarCharDataType(2));
		tablePersonnel.addColumn("zip", new VarCharDataType(10));
		tablePersonnel.addColumn("zip1", new VarCharDataType(5));
		tablePersonnel.addColumn("zip2", new VarCharDataType(4));
		tablePersonnel.addColumn("phone", new VarCharDataType(12));
		tablePersonnel.addColumn("phone1", new VarCharDataType(3));
		tablePersonnel.addColumn("phone2", new VarCharDataType(3));
		tablePersonnel.addColumn("phone3", new VarCharDataType(4));
		tablePersonnel.addColumn("specialty", new VarCharDataType(40));
		tablePersonnel.addColumn("email", new VarCharDataType(55));
		tablePersonnel.addColumn("MessageFilter", new VarCharDataType(60));
		tablePersonnel.setPrimaryKeyConstraint(tablePersonnel.getColumn("MID"));
		tablePersonnel.addNotNullConstraint(tablePersonnel.getColumn("role"));
		tablePersonnel.addNotNullConstraint(tablePersonnel.getColumn("enabled"));
		tablePersonnel.addNotNullConstraint(tablePersonnel.getColumn("lastName"));
		tablePersonnel.addNotNullConstraint(tablePersonnel.getColumn("firstName"));
		tablePersonnel.addNotNullConstraint(tablePersonnel.getColumn("address1"));
		tablePersonnel.addNotNullConstraint(tablePersonnel.getColumn("address2"));
		tablePersonnel.addNotNullConstraint(tablePersonnel.getColumn("city"));
		tablePersonnel.addNotNullConstraint(tablePersonnel.getColumn("state"));
		tablePersonnel.addCheckConstraint(new InExpression(new ColumnExpression(tablePersonnel.getColumn("role")), new ListExpression(new ConstantExpression(new StringValue("admin")), new ConstantExpression(new StringValue("hcp")), new ConstantExpression(new StringValue("uap")), new ConstantExpression(new StringValue("er")), new ConstantExpression(new StringValue("tester")), new ConstantExpression(new StringValue("pha")), new ConstantExpression(new StringValue("lt"))), false));
		tablePersonnel.addCheckConstraint(new InExpression(new ColumnExpression(tablePersonnel.getColumn("state")), new ListExpression(new ConstantExpression(new StringValue("")), new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));

		Table tablePatients = this.createTable("Patients");
		tablePatients.addColumn("MID", new IntDataType());
		tablePatients.addColumn("lastName", new VarCharDataType(20));
		tablePatients.addColumn("firstName", new VarCharDataType(20));
		tablePatients.addColumn("email", new VarCharDataType(55));
		tablePatients.addColumn("address1", new VarCharDataType(20));
		tablePatients.addColumn("address2", new VarCharDataType(20));
		tablePatients.addColumn("city", new VarCharDataType(15));
		tablePatients.addColumn("state", new VarCharDataType(2));
		tablePatients.addColumn("zip1", new VarCharDataType(5));
		tablePatients.addColumn("zip2", new VarCharDataType(4));
		tablePatients.addColumn("phone1", new VarCharDataType(3));
		tablePatients.addColumn("phone2", new VarCharDataType(3));
		tablePatients.addColumn("phone3", new VarCharDataType(4));
		tablePatients.addColumn("eName", new VarCharDataType(40));
		tablePatients.addColumn("ePhone1", new VarCharDataType(3));
		tablePatients.addColumn("ePhone2", new VarCharDataType(3));
		tablePatients.addColumn("ePhone3", new VarCharDataType(4));
		tablePatients.addColumn("iCName", new VarCharDataType(20));
		tablePatients.addColumn("iCAddress1", new VarCharDataType(20));
		tablePatients.addColumn("iCAddress2", new VarCharDataType(20));
		tablePatients.addColumn("iCCity", new VarCharDataType(15));
		tablePatients.addColumn("ICState", new VarCharDataType(2));
		tablePatients.addColumn("iCZip1", new VarCharDataType(5));
		tablePatients.addColumn("iCZip2", new VarCharDataType(4));
		tablePatients.addColumn("iCPhone1", new VarCharDataType(3));
		tablePatients.addColumn("iCPhone2", new VarCharDataType(3));
		tablePatients.addColumn("iCPhone3", new VarCharDataType(4));
		tablePatients.addColumn("iCID", new VarCharDataType(20));
		tablePatients.addColumn("DateOfBirth", new DateDataType());
		tablePatients.addColumn("DateOfDeath", new DateDataType());
		tablePatients.addColumn("CauseOfDeath", new VarCharDataType(10));
		tablePatients.addColumn("MotherMID", new IntDataType());
		tablePatients.addColumn("FatherMID", new IntDataType());
		tablePatients.addColumn("BloodType", new VarCharDataType(3));
		tablePatients.addColumn("Ethnicity", new VarCharDataType(20));
		tablePatients.addColumn("Gender", new VarCharDataType(13));
		tablePatients.addColumn("TopicalNotes", new VarCharDataType(200));
		tablePatients.addColumn("CreditCardType", new VarCharDataType(20));
		tablePatients.addColumn("CreditCardNumber", new VarCharDataType(19));
		tablePatients.addColumn("MessageFilter", new VarCharDataType(60));
		tablePatients.addColumn("DirectionsToHome", new VarCharDataType(512));
		tablePatients.addColumn("Religion", new VarCharDataType(64));
		tablePatients.addColumn("Language", new VarCharDataType(32));
		tablePatients.addColumn("SpiritualPractices", new VarCharDataType(100));
		tablePatients.addColumn("AlternateName", new VarCharDataType(32));
		tablePatients.setPrimaryKeyConstraint(tablePatients.getColumn("MID"));
		tablePatients.addCheckConstraint(new InExpression(new ColumnExpression(tablePatients.getColumn("state")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));
		tablePatients.addCheckConstraint(new InExpression(new ColumnExpression(tablePatients.getColumn("ICState")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));

		Table tableHistorypatients = this.createTable("HistoryPatients");
		tableHistorypatients.addColumn("ID", new IntDataType());
		tableHistorypatients.addColumn("changeDate", new DateDataType());
		tableHistorypatients.addColumn("changeMID", new IntDataType());
		tableHistorypatients.addColumn("MID", new IntDataType());
		tableHistorypatients.addColumn("lastName", new VarCharDataType(20));
		tableHistorypatients.addColumn("firstName", new VarCharDataType(20));
		tableHistorypatients.addColumn("email", new VarCharDataType(55));
		tableHistorypatients.addColumn("address1", new VarCharDataType(20));
		tableHistorypatients.addColumn("address2", new VarCharDataType(20));
		tableHistorypatients.addColumn("city", new VarCharDataType(15));
		tableHistorypatients.addColumn("state", new CharDataType(2));
		tableHistorypatients.addColumn("zip1", new VarCharDataType(5));
		tableHistorypatients.addColumn("zip2", new VarCharDataType(4));
		tableHistorypatients.addColumn("phone1", new VarCharDataType(3));
		tableHistorypatients.addColumn("phone2", new VarCharDataType(3));
		tableHistorypatients.addColumn("phone3", new VarCharDataType(4));
		tableHistorypatients.addColumn("eName", new VarCharDataType(40));
		tableHistorypatients.addColumn("ePhone1", new VarCharDataType(3));
		tableHistorypatients.addColumn("ePhone2", new VarCharDataType(3));
		tableHistorypatients.addColumn("ePhone3", new VarCharDataType(4));
		tableHistorypatients.addColumn("iCName", new VarCharDataType(20));
		tableHistorypatients.addColumn("iCAddress1", new VarCharDataType(20));
		tableHistorypatients.addColumn("iCAddress2", new VarCharDataType(20));
		tableHistorypatients.addColumn("iCCity", new VarCharDataType(15));
		tableHistorypatients.addColumn("ICState", new VarCharDataType(2));
		tableHistorypatients.addColumn("iCZip1", new VarCharDataType(5));
		tableHistorypatients.addColumn("iCZip2", new VarCharDataType(4));
		tableHistorypatients.addColumn("iCPhone1", new VarCharDataType(3));
		tableHistorypatients.addColumn("iCPhone2", new VarCharDataType(3));
		tableHistorypatients.addColumn("iCPhone3", new VarCharDataType(4));
		tableHistorypatients.addColumn("iCID", new VarCharDataType(20));
		tableHistorypatients.addColumn("DateOfBirth", new DateDataType());
		tableHistorypatients.addColumn("DateOfDeath", new DateDataType());
		tableHistorypatients.addColumn("CauseOfDeath", new VarCharDataType(10));
		tableHistorypatients.addColumn("MotherMID", new IntDataType());
		tableHistorypatients.addColumn("FatherMID", new IntDataType());
		tableHistorypatients.addColumn("BloodType", new VarCharDataType(3));
		tableHistorypatients.addColumn("Ethnicity", new VarCharDataType(20));
		tableHistorypatients.addColumn("Gender", new VarCharDataType(13));
		tableHistorypatients.addColumn("TopicalNotes", new VarCharDataType(200));
		tableHistorypatients.addColumn("CreditCardType", new VarCharDataType(20));
		tableHistorypatients.addColumn("CreditCardNumber", new VarCharDataType(19));
		tableHistorypatients.addColumn("MessageFilter", new VarCharDataType(60));
		tableHistorypatients.addColumn("DirectionsToHome", new VarCharDataType(100));
		tableHistorypatients.addColumn("Religion", new VarCharDataType(64));
		tableHistorypatients.addColumn("Language", new VarCharDataType(32));
		tableHistorypatients.addColumn("SpiritualPractices", new VarCharDataType(100));
		tableHistorypatients.addColumn("AlternateName", new VarCharDataType(32));
		tableHistorypatients.setPrimaryKeyConstraint(tableHistorypatients.getColumn("ID"));
		tableHistorypatients.addNotNullConstraint(tableHistorypatients.getColumn("changeDate"));
		tableHistorypatients.addNotNullConstraint(tableHistorypatients.getColumn("changeMID"));
		tableHistorypatients.addNotNullConstraint(tableHistorypatients.getColumn("MID"));
		tableHistorypatients.addCheckConstraint(new InExpression(new ColumnExpression(tableHistorypatients.getColumn("state")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));
		tableHistorypatients.addCheckConstraint(new InExpression(new ColumnExpression(tableHistorypatients.getColumn("ICState")), new ListExpression(new ConstantExpression(new StringValue("AK")), new ConstantExpression(new StringValue("AL")), new ConstantExpression(new StringValue("AR")), new ConstantExpression(new StringValue("AZ")), new ConstantExpression(new StringValue("CA")), new ConstantExpression(new StringValue("CO")), new ConstantExpression(new StringValue("CT")), new ConstantExpression(new StringValue("DE")), new ConstantExpression(new StringValue("DC")), new ConstantExpression(new StringValue("FL")), new ConstantExpression(new StringValue("GA")), new ConstantExpression(new StringValue("HI")), new ConstantExpression(new StringValue("IA")), new ConstantExpression(new StringValue("ID")), new ConstantExpression(new StringValue("IL")), new ConstantExpression(new StringValue("IN")), new ConstantExpression(new StringValue("KS")), new ConstantExpression(new StringValue("KY")), new ConstantExpression(new StringValue("LA")), new ConstantExpression(new StringValue("MA")), new ConstantExpression(new StringValue("MD")), new ConstantExpression(new StringValue("ME")), new ConstantExpression(new StringValue("MI")), new ConstantExpression(new StringValue("MN")), new ConstantExpression(new StringValue("MO")), new ConstantExpression(new StringValue("MS")), new ConstantExpression(new StringValue("MT")), new ConstantExpression(new StringValue("NC")), new ConstantExpression(new StringValue("ND")), new ConstantExpression(new StringValue("NE")), new ConstantExpression(new StringValue("NH")), new ConstantExpression(new StringValue("NJ")), new ConstantExpression(new StringValue("NM")), new ConstantExpression(new StringValue("NV")), new ConstantExpression(new StringValue("NY")), new ConstantExpression(new StringValue("OH")), new ConstantExpression(new StringValue("OK")), new ConstantExpression(new StringValue("OR")), new ConstantExpression(new StringValue("PA")), new ConstantExpression(new StringValue("RI")), new ConstantExpression(new StringValue("SC")), new ConstantExpression(new StringValue("SD")), new ConstantExpression(new StringValue("TN")), new ConstantExpression(new StringValue("TX")), new ConstantExpression(new StringValue("UT")), new ConstantExpression(new StringValue("VA")), new ConstantExpression(new StringValue("VT")), new ConstantExpression(new StringValue("WA")), new ConstantExpression(new StringValue("WI")), new ConstantExpression(new StringValue("WV")), new ConstantExpression(new StringValue("WY"))), false));

		Table tableLoginfailures = this.createTable("LoginFailures");
		tableLoginfailures.addColumn("ipaddress", new VarCharDataType(100));
		tableLoginfailures.addColumn("failureCount", new IntDataType());
		tableLoginfailures.addColumn("lastFailure", new TimestampDataType());
		tableLoginfailures.setPrimaryKeyConstraint(tableLoginfailures.getColumn("ipaddress"));
		tableLoginfailures.addNotNullConstraint(tableLoginfailures.getColumn("ipaddress"));
		tableLoginfailures.addNotNullConstraint(tableLoginfailures.getColumn("failureCount"));
		tableLoginfailures.addNotNullConstraint(tableLoginfailures.getColumn("lastFailure"));

		Table tableResetpasswordfailures = this.createTable("ResetPasswordFailures");
		tableResetpasswordfailures.addColumn("ipaddress", new VarCharDataType(128));
		tableResetpasswordfailures.addColumn("failureCount", new IntDataType());
		tableResetpasswordfailures.addColumn("lastFailure", new TimestampDataType());
		tableResetpasswordfailures.setPrimaryKeyConstraint(tableResetpasswordfailures.getColumn("ipaddress"));
		tableResetpasswordfailures.addNotNullConstraint(tableResetpasswordfailures.getColumn("ipaddress"));
		tableResetpasswordfailures.addNotNullConstraint(tableResetpasswordfailures.getColumn("failureCount"));
		tableResetpasswordfailures.addNotNullConstraint(tableResetpasswordfailures.getColumn("lastFailure"));

		Table tableIcdcodes = this.createTable("icdcodes");
		tableIcdcodes.addColumn("Code", new NumericDataType(5, 2));
		tableIcdcodes.addColumn("Description", new VarCharDataType(50));
		tableIcdcodes.addColumn("Chronic", new VarCharDataType(3));
		tableIcdcodes.setPrimaryKeyConstraint(tableIcdcodes.getColumn("Code"));
		tableIcdcodes.addNotNullConstraint(tableIcdcodes.getColumn("Code"));
		tableIcdcodes.addNotNullConstraint(tableIcdcodes.getColumn("Description"));
		tableIcdcodes.addNotNullConstraint(tableIcdcodes.getColumn("Chronic"));
		tableIcdcodes.addCheckConstraint(new InExpression(new ColumnExpression(tableIcdcodes.getColumn("Chronic")), new ListExpression(new ConstantExpression(new StringValue("no")), new ConstantExpression(new StringValue("yes"))), false));

		Table tableCptcodes = this.createTable("CPTCodes");
		tableCptcodes.addColumn("Code", new VarCharDataType(5));
		tableCptcodes.addColumn("Description", new VarCharDataType(30));
		tableCptcodes.addColumn("Attribute", new VarCharDataType(30));
		tableCptcodes.setPrimaryKeyConstraint(tableCptcodes.getColumn("Code"));
		tableCptcodes.addNotNullConstraint(tableCptcodes.getColumn("Code"));
		tableCptcodes.addNotNullConstraint(tableCptcodes.getColumn("Description"));

		Table tableDrugreactionoverridecodes = this.createTable("DrugReactionOverrideCodes");
		tableDrugreactionoverridecodes.addColumn("Code", new VarCharDataType(5));
		tableDrugreactionoverridecodes.addColumn("Description", new VarCharDataType(80));
		tableDrugreactionoverridecodes.setPrimaryKeyConstraint(tableDrugreactionoverridecodes.getColumn("Code"));
		tableDrugreactionoverridecodes.addNotNullConstraint(tableDrugreactionoverridecodes.getColumn("Code"));
		tableDrugreactionoverridecodes.addNotNullConstraint(tableDrugreactionoverridecodes.getColumn("Description"));

		Table tableNdcodes = this.createTable("NDCodes");
		tableNdcodes.addColumn("Code", new VarCharDataType(10));
		tableNdcodes.addColumn("Description", new VarCharDataType(100));
		tableNdcodes.setPrimaryKeyConstraint(tableNdcodes.getColumn("Code"));
		tableNdcodes.addNotNullConstraint(tableNdcodes.getColumn("Code"));
		tableNdcodes.addNotNullConstraint(tableNdcodes.getColumn("Description"));

		Table tableDruginteractions = this.createTable("DrugInteractions");
		tableDruginteractions.addColumn("FirstDrug", new VarCharDataType(9));
		tableDruginteractions.addColumn("SecondDrug", new VarCharDataType(9));
		tableDruginteractions.addColumn("Description", new VarCharDataType(100));
		tableDruginteractions.setPrimaryKeyConstraint(tableDruginteractions.getColumn("FirstDrug"), tableDruginteractions.getColumn("SecondDrug"));
		tableDruginteractions.addNotNullConstraint(tableDruginteractions.getColumn("FirstDrug"));
		tableDruginteractions.addNotNullConstraint(tableDruginteractions.getColumn("SecondDrug"));
		tableDruginteractions.addNotNullConstraint(tableDruginteractions.getColumn("Description"));

		Table tableTransactionlog = this.createTable("TransactionLog");
		tableTransactionlog.addColumn("transactionID", new IntDataType());
		tableTransactionlog.addColumn("loggedInMID", new IntDataType());
		tableTransactionlog.addColumn("secondaryMID", new IntDataType());
		tableTransactionlog.addColumn("transactionCode", new IntDataType());
		tableTransactionlog.addColumn("timeLogged", new TimestampDataType());
		tableTransactionlog.addColumn("addedInfo", new VarCharDataType(255));
		tableTransactionlog.setPrimaryKeyConstraint(tableTransactionlog.getColumn("transactionID"));
		tableTransactionlog.addNotNullConstraint(tableTransactionlog.getColumn("transactionID"));
		tableTransactionlog.addNotNullConstraint(tableTransactionlog.getColumn("loggedInMID"));
		tableTransactionlog.addNotNullConstraint(tableTransactionlog.getColumn("secondaryMID"));
		tableTransactionlog.addNotNullConstraint(tableTransactionlog.getColumn("transactionCode"));
		tableTransactionlog.addNotNullConstraint(tableTransactionlog.getColumn("timeLogged"));

		Table tableHcprelations = this.createTable("HCPRelations");
		tableHcprelations.addColumn("HCP", new IntDataType());
		tableHcprelations.addColumn("UAP", new IntDataType());
		tableHcprelations.setPrimaryKeyConstraint(tableHcprelations.getColumn("HCP"), tableHcprelations.getColumn("UAP"));
		tableHcprelations.addNotNullConstraint(tableHcprelations.getColumn("HCP"));
		tableHcprelations.addNotNullConstraint(tableHcprelations.getColumn("UAP"));

		Table tablePersonalrelations = this.createTable("PersonalRelations");
		tablePersonalrelations.addColumn("PatientID", new IntDataType());
		tablePersonalrelations.addColumn("RelativeID", new IntDataType());
		tablePersonalrelations.addColumn("RelativeType", new VarCharDataType(35));
		tablePersonalrelations.addNotNullConstraint(tablePersonalrelations.getColumn("PatientID"));
		tablePersonalrelations.addNotNullConstraint(tablePersonalrelations.getColumn("RelativeID"));
		tablePersonalrelations.addNotNullConstraint(tablePersonalrelations.getColumn("RelativeType"));

		Table tableRepresentatives = this.createTable("Representatives");
		tableRepresentatives.addColumn("representerMID", new IntDataType());
		tableRepresentatives.addColumn("representeeMID", new IntDataType());
		tableRepresentatives.setPrimaryKeyConstraint(tableRepresentatives.getColumn("representerMID"), tableRepresentatives.getColumn("representeeMID"));

		Table tableHcpassignedhos = this.createTable("HCPAssignedHos");
		tableHcpassignedhos.addColumn("hosID", new VarCharDataType(10));
		tableHcpassignedhos.addColumn("HCPID", new IntDataType());
		tableHcpassignedhos.setPrimaryKeyConstraint(tableHcpassignedhos.getColumn("hosID"), tableHcpassignedhos.getColumn("HCPID"));
		tableHcpassignedhos.addNotNullConstraint(tableHcpassignedhos.getColumn("hosID"));
		tableHcpassignedhos.addNotNullConstraint(tableHcpassignedhos.getColumn("HCPID"));

		Table tableDeclaredhcp = this.createTable("DeclaredHCP");
		tableDeclaredhcp.addColumn("PatientID", new IntDataType());
		tableDeclaredhcp.addColumn("HCPID", new IntDataType());
		tableDeclaredhcp.setPrimaryKeyConstraint(tableDeclaredhcp.getColumn("PatientID"), tableDeclaredhcp.getColumn("HCPID"));
		tableDeclaredhcp.addNotNullConstraint(tableDeclaredhcp.getColumn("PatientID"));
		tableDeclaredhcp.addNotNullConstraint(tableDeclaredhcp.getColumn("HCPID"));

		Table tableOfficevisits = this.createTable("OfficeVisits");
		tableOfficevisits.addColumn("ID", new IntDataType());
		tableOfficevisits.addColumn("visitDate", new DateDataType());
		tableOfficevisits.addColumn("HCPID", new IntDataType());
		tableOfficevisits.addColumn("notes", new VarCharDataType(50));
		tableOfficevisits.addColumn("PatientID", new IntDataType());
		tableOfficevisits.addColumn("HospitalID", new VarCharDataType(10));
		tableOfficevisits.setPrimaryKeyConstraint(tableOfficevisits.getColumn("ID"));

		Table tablePersonalhealthinformation = this.createTable("PersonalHealthInformation");
		tablePersonalhealthinformation.addColumn("PatientID", new IntDataType());
		tablePersonalhealthinformation.addColumn("Height", new IntDataType());
		tablePersonalhealthinformation.addColumn("Weight", new IntDataType());
		tablePersonalhealthinformation.addColumn("Smoker", new IntDataType());
		tablePersonalhealthinformation.addColumn("SmokingStatus", new IntDataType());
		tablePersonalhealthinformation.addColumn("BloodPressureN", new IntDataType());
		tablePersonalhealthinformation.addColumn("BloodPressureD", new IntDataType());
		tablePersonalhealthinformation.addColumn("CholesterolHDL", new IntDataType());
		tablePersonalhealthinformation.addColumn("CholesterolLDL", new IntDataType());
		tablePersonalhealthinformation.addColumn("CholesterolTri", new IntDataType());
		tablePersonalhealthinformation.addColumn("HCPID", new IntDataType());
		tablePersonalhealthinformation.addColumn("AsOfDate", new TimestampDataType());
		tablePersonalhealthinformation.addNotNullConstraint(tablePersonalhealthinformation.getColumn("PatientID"));
		tablePersonalhealthinformation.addNotNullConstraint(tablePersonalhealthinformation.getColumn("Smoker"));
		tablePersonalhealthinformation.addNotNullConstraint(tablePersonalhealthinformation.getColumn("SmokingStatus"));
		tablePersonalhealthinformation.addNotNullConstraint(tablePersonalhealthinformation.getColumn("AsOfDate"));

		Table tablePersonalallergies = this.createTable("PersonalAllergies");
		tablePersonalallergies.addColumn("PatientID", new IntDataType());
		tablePersonalallergies.addColumn("Allergy", new VarCharDataType(50));
		tablePersonalallergies.addNotNullConstraint(tablePersonalallergies.getColumn("PatientID"));
		tablePersonalallergies.addNotNullConstraint(tablePersonalallergies.getColumn("Allergy"));

		Table tableAllergies = this.createTable("Allergies");
		tableAllergies.addColumn("ID", new IntDataType());
		tableAllergies.addColumn("PatientID", new IntDataType());
		tableAllergies.addColumn("Description", new VarCharDataType(50));
		tableAllergies.addColumn("FirstFound", new TimestampDataType());
		tableAllergies.setPrimaryKeyConstraint(tableAllergies.getColumn("ID"));
		tableAllergies.addNotNullConstraint(tableAllergies.getColumn("PatientID"));
		tableAllergies.addNotNullConstraint(tableAllergies.getColumn("Description"));
		tableAllergies.addNotNullConstraint(tableAllergies.getColumn("FirstFound"));

		Table tableOvprocedure = this.createTable("OVProcedure");
		tableOvprocedure.addColumn("ID", new IntDataType());
		tableOvprocedure.addColumn("VisitID", new IntDataType());
		tableOvprocedure.addColumn("CPTCode", new VarCharDataType(5));
		tableOvprocedure.addColumn("HCPID", new VarCharDataType(10));
		tableOvprocedure.setPrimaryKeyConstraint(tableOvprocedure.getColumn("ID"));
		tableOvprocedure.addNotNullConstraint(tableOvprocedure.getColumn("VisitID"));
		tableOvprocedure.addNotNullConstraint(tableOvprocedure.getColumn("CPTCode"));
		tableOvprocedure.addNotNullConstraint(tableOvprocedure.getColumn("HCPID"));

		Table tableOvmedication = this.createTable("OVMedication");
		tableOvmedication.addColumn("ID", new IntDataType());
		tableOvmedication.addColumn("VisitID", new IntDataType());
		tableOvmedication.addColumn("NDCode", new VarCharDataType(9));
		tableOvmedication.addColumn("StartDate", new DateDataType());
		tableOvmedication.addColumn("EndDate", new DateDataType());
		tableOvmedication.addColumn("Dosage", new IntDataType());
		tableOvmedication.addColumn("Instructions", new VarCharDataType(500));
		tableOvmedication.setPrimaryKeyConstraint(tableOvmedication.getColumn("ID"));
		tableOvmedication.addNotNullConstraint(tableOvmedication.getColumn("VisitID"));
		tableOvmedication.addNotNullConstraint(tableOvmedication.getColumn("NDCode"));

		Table tableOvreactionoverride = this.createTable("OVReactionOverride");
		tableOvreactionoverride.addColumn("ID", new IntDataType());
		tableOvreactionoverride.addColumn("OVMedicationID", new IntDataType());
		tableOvreactionoverride.addColumn("OverrideCode", new VarCharDataType(5));
		tableOvreactionoverride.addColumn("OverrideComment", new VarCharDataType(255));
		tableOvreactionoverride.setPrimaryKeyConstraint(tableOvreactionoverride.getColumn("ID"));
		tableOvreactionoverride.addForeignKeyConstraint(tableOvreactionoverride.getColumn("OVMedicationID"), tableOvmedication, tableOvmedication.getColumn("ID"));
		tableOvreactionoverride.addNotNullConstraint(tableOvreactionoverride.getColumn("OVMedicationID"));

		Table tableOvdiagnosis = this.createTable("OVDiagnosis");
		tableOvdiagnosis.addColumn("ID", new IntDataType());
		tableOvdiagnosis.addColumn("VisitID", new IntDataType());
		tableOvdiagnosis.addColumn("ICDCode", new DecimalDataType(5, 2));
		tableOvdiagnosis.setPrimaryKeyConstraint(tableOvdiagnosis.getColumn("ID"));
		tableOvdiagnosis.addNotNullConstraint(tableOvdiagnosis.getColumn("VisitID"));
		tableOvdiagnosis.addNotNullConstraint(tableOvdiagnosis.getColumn("ICDCode"));

		Table tableGlobalvariables = this.createTable("GlobalVariables");
		tableGlobalvariables.addColumn("Name", new VarCharDataType(20));
		tableGlobalvariables.addColumn("Value", new VarCharDataType(20));
		tableGlobalvariables.setPrimaryKeyConstraint(tableGlobalvariables.getColumn("Name"));

		Table tableFakeemail = this.createTable("FakeEmail");
		tableFakeemail.addColumn("ID", new IntDataType());
		tableFakeemail.addColumn("ToAddr", new VarCharDataType(100));
		tableFakeemail.addColumn("FromAddr", new VarCharDataType(100));
		tableFakeemail.addColumn("Subject", new VarCharDataType(500));
		tableFakeemail.addColumn("Body", new VarCharDataType(2000));
		tableFakeemail.addColumn("AddedDate", new TimestampDataType());
		tableFakeemail.setPrimaryKeyConstraint(tableFakeemail.getColumn("ID"));
		tableFakeemail.addNotNullConstraint(tableFakeemail.getColumn("AddedDate"));

		Table tableReportrequests = this.createTable("ReportRequests");
		tableReportrequests.addColumn("ID", new IntDataType());
		tableReportrequests.addColumn("RequesterMID", new IntDataType());
		tableReportrequests.addColumn("PatientMID", new IntDataType());
		tableReportrequests.addColumn("ApproverMID", new IntDataType());
		tableReportrequests.addColumn("RequestedDate", new TimestampDataType());
		tableReportrequests.addColumn("ApprovedDate", new TimestampDataType());
		tableReportrequests.addColumn("ViewedDate", new TimestampDataType());
		tableReportrequests.addColumn("Status", new VarCharDataType(30));
		tableReportrequests.addColumn("Comment", new VarCharDataType(50));
		tableReportrequests.setPrimaryKeyConstraint(tableReportrequests.getColumn("ID"));

		Table tableOvsurvey = this.createTable("OVSurvey");
		tableOvsurvey.addColumn("VisitID", new IntDataType());
		tableOvsurvey.addColumn("SurveyDate", new TimestampDataType());
		tableOvsurvey.addColumn("WaitingRoomMinutes", new IntDataType());
		tableOvsurvey.addColumn("ExamRoomMinutes", new IntDataType());
		tableOvsurvey.addColumn("VisitSatisfaction", new IntDataType());
		tableOvsurvey.addColumn("TreatmentSatisfaction", new IntDataType());
		tableOvsurvey.setPrimaryKeyConstraint(tableOvsurvey.getColumn("VisitID"));
		tableOvsurvey.addNotNullConstraint(tableOvsurvey.getColumn("SurveyDate"));

		Table tableLoinc = this.createTable("LOINC");
		tableLoinc.addColumn("LaboratoryProcedureCode", new VarCharDataType(7));
		tableLoinc.addColumn("Component", new VarCharDataType(100));
		tableLoinc.addColumn("KindOfProperty", new VarCharDataType(100));
		tableLoinc.addColumn("TimeAspect", new VarCharDataType(100));
		tableLoinc.addColumn("System", new VarCharDataType(100));
		tableLoinc.addColumn("ScaleType", new VarCharDataType(100));
		tableLoinc.addColumn("MethodType", new VarCharDataType(100));

		Table tableLabprocedure = this.createTable("LabProcedure");
		tableLabprocedure.addColumn("LaboratoryProcedureID", new IntDataType());
		tableLabprocedure.addColumn("PatientMID", new IntDataType());
		tableLabprocedure.addColumn("LaboratoryProcedureCode", new VarCharDataType(7));
		tableLabprocedure.addColumn("Rights", new VarCharDataType(10));
		tableLabprocedure.addColumn("Status", new VarCharDataType(20));
		tableLabprocedure.addColumn("Commentary", new VarCharDataType(50));
		tableLabprocedure.addColumn("Results", new VarCharDataType(50));
		tableLabprocedure.addColumn("NumericalResults", new VarCharDataType(20));
		tableLabprocedure.addColumn("NumericalResultsUnit", new VarCharDataType(20));
		tableLabprocedure.addColumn("UpperBound", new VarCharDataType(20));
		tableLabprocedure.addColumn("LowerBound", new VarCharDataType(20));
		tableLabprocedure.addColumn("OfficeVisitID", new IntDataType());
		tableLabprocedure.addColumn("LabTechID", new IntDataType());
		tableLabprocedure.addColumn("PriorityCode", new IntDataType());
		tableLabprocedure.addColumn("ViewedByPatient", new BooleanDataType());
		tableLabprocedure.addColumn("UpdatedDate", new TimestampDataType());
		tableLabprocedure.setPrimaryKeyConstraint(tableLabprocedure.getColumn("LaboratoryProcedureID"));
		tableLabprocedure.addNotNullConstraint(tableLabprocedure.getColumn("ViewedByPatient"));
		tableLabprocedure.addNotNullConstraint(tableLabprocedure.getColumn("UpdatedDate"));

		Table tableMessage = this.createTable("message");
		tableMessage.addColumn("message_id", new IntDataType());
		tableMessage.addColumn("parent_msg_id", new IntDataType());
		tableMessage.addColumn("from_id", new IntDataType());
		tableMessage.addColumn("to_id", new IntDataType());
		tableMessage.addColumn("sent_date", new TimestampDataType());
		tableMessage.addColumn("message", new VarCharDataType(50));
		tableMessage.addColumn("subject", new VarCharDataType(50));
		tableMessage.addColumn("been_read", new IntDataType());
		tableMessage.addNotNullConstraint(tableMessage.getColumn("from_id"));
		tableMessage.addNotNullConstraint(tableMessage.getColumn("to_id"));
		tableMessage.addNotNullConstraint(tableMessage.getColumn("sent_date"));

		Table tableAppointment = this.createTable("Appointment");
		tableAppointment.addColumn("appt_id", new IntDataType());
		tableAppointment.addColumn("doctor_id", new IntDataType());
		tableAppointment.addColumn("patient_id", new IntDataType());
		tableAppointment.addColumn("sched_date", new TimestampDataType());
		tableAppointment.addColumn("appt_type", new VarCharDataType(30));
		tableAppointment.addColumn("comment", new VarCharDataType(50));
		tableAppointment.setPrimaryKeyConstraint(tableAppointment.getColumn("appt_id"));
		tableAppointment.addNotNullConstraint(tableAppointment.getColumn("doctor_id"));
		tableAppointment.addNotNullConstraint(tableAppointment.getColumn("patient_id"));
		tableAppointment.addNotNullConstraint(tableAppointment.getColumn("sched_date"));
		tableAppointment.addNotNullConstraint(tableAppointment.getColumn("appt_type"));

		Table tableAppointmenttype = this.createTable("AppointmentType");
		tableAppointmenttype.addColumn("apptType_id", new IntDataType());
		tableAppointmenttype.addColumn("appt_type", new VarCharDataType(30));
		tableAppointmenttype.addColumn("duration", new IntDataType());
		tableAppointmenttype.setPrimaryKeyConstraint(tableAppointmenttype.getColumn("apptType_id"));
		tableAppointmenttype.addNotNullConstraint(tableAppointmenttype.getColumn("appt_type"));
		tableAppointmenttype.addNotNullConstraint(tableAppointmenttype.getColumn("duration"));

		Table tableReferrals = this.createTable("referrals");
		tableReferrals.addColumn("id", new IntDataType());
		tableReferrals.addColumn("PatientID", new IntDataType());
		tableReferrals.addColumn("SenderID", new IntDataType());
		tableReferrals.addColumn("ReceiverID", new IntDataType());
		tableReferrals.addColumn("ReferralDetails", new VarCharDataType(50));
		tableReferrals.addColumn("OVID", new IntDataType());
		tableReferrals.addColumn("viewed_by_patient", new BooleanDataType());
		tableReferrals.addColumn("viewed_by_HCP", new BooleanDataType());
		tableReferrals.addColumn("TimeStamp", new TimestampDataType());
		tableReferrals.addColumn("PriorityCode", new IntDataType());
		tableReferrals.setPrimaryKeyConstraint(tableReferrals.getColumn("id"));
		tableReferrals.addNotNullConstraint(tableReferrals.getColumn("PatientID"));
		tableReferrals.addNotNullConstraint(tableReferrals.getColumn("SenderID"));
		tableReferrals.addNotNullConstraint(tableReferrals.getColumn("ReceiverID"));
		tableReferrals.addNotNullConstraint(tableReferrals.getColumn("OVID"));
		tableReferrals.addNotNullConstraint(tableReferrals.getColumn("viewed_by_patient"));
		tableReferrals.addNotNullConstraint(tableReferrals.getColumn("viewed_by_HCP"));
		tableReferrals.addNotNullConstraint(tableReferrals.getColumn("TimeStamp"));

		Table tableRemotemonitoringdata = this.createTable("RemoteMonitoringData");
		tableRemotemonitoringdata.addColumn("id", new IntDataType());
		tableRemotemonitoringdata.addColumn("PatientID", new IntDataType());
		tableRemotemonitoringdata.addColumn("systolicBloodPressure", new IntDataType());
		tableRemotemonitoringdata.addColumn("diastolicBloodPressure", new IntDataType());
		tableRemotemonitoringdata.addColumn("glucoseLevel", new IntDataType());
		tableRemotemonitoringdata.addColumn("height", new IntDataType());
		tableRemotemonitoringdata.addColumn("weight", new IntDataType());
		tableRemotemonitoringdata.addColumn("pedometerReading", new IntDataType());
		tableRemotemonitoringdata.addColumn("timeLogged", new TimestampDataType());
		tableRemotemonitoringdata.addColumn("ReporterRole", new VarCharDataType(50));
		tableRemotemonitoringdata.addColumn("ReporterID", new IntDataType());
		tableRemotemonitoringdata.setPrimaryKeyConstraint(tableRemotemonitoringdata.getColumn("id"));
		tableRemotemonitoringdata.addNotNullConstraint(tableRemotemonitoringdata.getColumn("PatientID"));
		tableRemotemonitoringdata.addNotNullConstraint(tableRemotemonitoringdata.getColumn("timeLogged"));

		Table tableRemotemonitoringlists = this.createTable("RemoteMonitoringLists");
		tableRemotemonitoringlists.addColumn("PatientMID", new IntDataType());
		tableRemotemonitoringlists.addColumn("HCPMID", new IntDataType());
		tableRemotemonitoringlists.addColumn("SystolicBloodPressure", new BooleanDataType());
		tableRemotemonitoringlists.addColumn("DiastolicBloodPressure", new BooleanDataType());
		tableRemotemonitoringlists.addColumn("GlucoseLevel", new BooleanDataType());
		tableRemotemonitoringlists.addColumn("Height", new BooleanDataType());
		tableRemotemonitoringlists.addColumn("Weight", new BooleanDataType());
		tableRemotemonitoringlists.addColumn("PedometerReading", new BooleanDataType());
		tableRemotemonitoringlists.setPrimaryKeyConstraint(tableRemotemonitoringlists.getColumn("PatientMID"), tableRemotemonitoringlists.getColumn("HCPMID"));

		Table tableAdverseevents = this.createTable("AdverseEvents");
		tableAdverseevents.addColumn("id", new IntDataType());
		tableAdverseevents.addColumn("Status", new VarCharDataType(10));
		tableAdverseevents.addColumn("PatientMID", new IntDataType());
		tableAdverseevents.addColumn("PresImmu", new VarCharDataType(50));
		tableAdverseevents.addColumn("Code", new VarCharDataType(20));
		tableAdverseevents.addColumn("Comment", new VarCharDataType(2000));
		tableAdverseevents.addColumn("Prescriber", new IntDataType());
		tableAdverseevents.addColumn("TimeLogged", new TimestampDataType());
		tableAdverseevents.setPrimaryKeyConstraint(tableAdverseevents.getColumn("id"));

		Table tableProfilephotos = this.createTable("ProfilePhotos");
		tableProfilephotos.addColumn("MID", new IntDataType());
		tableProfilephotos.addColumn("Photo", new VarCharDataType(50));
		tableProfilephotos.addColumn("UpdatedDate", new TimestampDataType());
		tableProfilephotos.setPrimaryKeyConstraint(tableProfilephotos.getColumn("MID"));
		tableProfilephotos.addNotNullConstraint(tableProfilephotos.getColumn("UpdatedDate"));

		Table tablePatientspecificinstructions = this.createTable("PatientSpecificInstructions");
		tablePatientspecificinstructions.addColumn("id", new IntDataType());
		tablePatientspecificinstructions.addColumn("VisitID", new IntDataType());
		tablePatientspecificinstructions.addColumn("Modified", new TimestampDataType());
		tablePatientspecificinstructions.addColumn("Name", new VarCharDataType(100));
		tablePatientspecificinstructions.addColumn("URL", new VarCharDataType(250));
		tablePatientspecificinstructions.addColumn("Comment", new VarCharDataType(500));
		tablePatientspecificinstructions.setPrimaryKeyConstraint(tablePatientspecificinstructions.getColumn("id"));
		tablePatientspecificinstructions.addNotNullConstraint(tablePatientspecificinstructions.getColumn("Modified"));

		Table tableReferralmessage = this.createTable("ReferralMessage");
		tableReferralmessage.addColumn("messageID", new IntDataType());
		tableReferralmessage.addColumn("referralID", new IntDataType());
		tableReferralmessage.setPrimaryKeyConstraint(tableReferralmessage.getColumn("messageID"), tableReferralmessage.getColumn("referralID"));
		tableReferralmessage.addNotNullConstraint(tableReferralmessage.getColumn("messageID"));
		tableReferralmessage.addNotNullConstraint(tableReferralmessage.getColumn("referralID"));
	}
}

