package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateTimeDataType;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * Factory2000 schema.
 * Java code originally generated: 2014/02/11 15:24:54
 *
 */

@SuppressWarnings("serial")
public class Factory2000 extends Schema {

	public Factory2000() {
		super("Factory2000");

		Table tableEmployees = this.createTable("Employees");
		tableEmployees.createColumn("EmpNo", new TextDataType());
		tableEmployees.createColumn("fName", new TextDataType());
		tableEmployees.createColumn("lName", new TextDataType());
		tableEmployees.createColumn("Rate", new TextDataType());
		tableEmployees.createColumn("MgrNo", new TextDataType());
		this.createUniqueConstraint("Employees_PrimaryKey", tableEmployees, tableEmployees.getColumn("EmpNo"));

		Table tableLabor = this.createTable("Labor");
		tableLabor.createColumn("EmpNo", new TextDataType());
		tableLabor.createColumn("Wono", new TextDataType());
		tableLabor.createColumn("Start", new DateTimeDataType());
		tableLabor.createColumn("End", new DateTimeDataType());
		tableLabor.createColumn("Hours", new RealDataType());
		this.createUniqueConstraint("Labor_TicketNo", tableLabor, tableLabor.getColumn("EmpNo"), tableLabor.getColumn("Wono"));

		Table tableReportingRelationships = this.createTable("Reporting_Relationships");
		tableReportingRelationships.createColumn("EmpNo", new TextDataType());
		tableReportingRelationships.createColumn("MgrNo", new TextDataType());
		tableReportingRelationships.createColumn("Reporting_Relationship", new TextDataType());
		this.createUniqueConstraint("Reporting_Relationships_PrimaryKey", tableReportingRelationships, tableReportingRelationships.getColumn("EmpNo"), tableReportingRelationships.getColumn("MgrNo"));

		Table tableWorkOrders = this.createTable("Work_Orders");
		tableWorkOrders.createColumn("Wono", new TextDataType());
		tableWorkOrders.createColumn("Descr", new TextDataType());
		tableWorkOrders.createColumn("Std", new RealDataType());
		tableWorkOrders.createColumn("Accum", new RealDataType());
		this.createUniqueConstraint("Work_Orders_PrimaryKey", tableWorkOrders, tableWorkOrders.getColumn("Wono"));
	}
}

