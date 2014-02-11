package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * AdmissionsPatientRepaired schema.
 * Java code originally generated: 2014/02/11 15:10:59
 *
 */

@SuppressWarnings("serial")
public class AdmissionsPatient extends Schema {

	public AdmissionsPatient() {
		super("AdmissionsPatientRepaired");

		Table tableAdmissions = this.createTable("Admissions");
		tableAdmissions.createColumn("AdmitNo", new IntDataType());
		tableAdmissions.createColumn("PatNo", new TextDataType());
		tableAdmissions.createColumn("Diag_Code", new TextDataType());
		tableAdmissions.createColumn("Admit_Date", new DateTimeDataType());
		tableAdmissions.createColumn("Dischg_Date", new DateTimeDataType());
		tableAdmissions.createColumn("CoPay", new TextDataType());
		this.createUniqueConstraint("Admissions_PrimaryKey", tableAdmissions, tableAdmissions.getColumn("AdmitNo"));

		Table tableDiagnostics = this.createTable("Diagnostics");
		tableDiagnostics.createColumn("DiagNo", new TextDataType());
		tableDiagnostics.createColumn("Descr", new TextDataType());
		tableDiagnostics.createColumn("Cost", new TextDataType());
		this.createUniqueConstraint("Diagnostics_PrimaryKey", tableDiagnostics, tableDiagnostics.getColumn("DiagNo"));

		Table tablePatients = this.createTable("Patients");
		tablePatients.createColumn("PatNo", new TextDataType());
		tablePatients.createColumn("fName", new TextDataType());
		tablePatients.createColumn("lName", new TextDataType());
		tablePatients.createColumn("BirthDate", new DateTimeDataType());
		tablePatients.createColumn("Address", new TextDataType());
		tablePatients.createColumn("City", new TextDataType());
		tablePatients.createColumn("State", new TextDataType());
		tablePatients.createColumn("Zip", new TextDataType());
		this.createUniqueConstraint("Patients_PrimaryKey", tablePatients, tablePatients.getColumn("PatNo"));
	}
}

