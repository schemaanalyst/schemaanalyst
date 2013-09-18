package org.schemaanalyst.javawriter;

import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.IndentableStringBuilder;

import parsedcasestudy.Inventory;

public class TestSuiteJavaWriter {

	private Schema schema;
	private DBMS dbms;
	private List<Data> data; 
	
	public TestSuiteJavaWriter(Schema schema, DBMS dbms, List<Data> data) {
		this.schema = schema;
		this.dbms = dbms;
		this.data = data;
	}
	
	public void writeTestSuite() {
		// initialise	
        JavaWriter javaWriter = new JavaWriter();
        IndentableStringBuilder code = new IndentableStringBuilder();
		
		code.appendln("public class Test" + schema.getName() + "With" + dbms + "{");
		
		System.out.println(code);
	}
	
	public static void main(String[] args) {
		new TestSuiteJavaWriter(new Inventory(), new SQLiteDBMS(), null);
	}
}
