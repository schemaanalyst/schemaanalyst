package paper.mutation2013;

import paper.icst2013.*;

import org.schemaanalyst.sqlrepresentation.Schema;

import originalcasestudy.BankAccount;
import originalcasestudy.BookTown;
import originalcasestudy.BooleanExample;
import originalcasestudy.Cloc;
import originalcasestudy.CoffeeOrders;
import originalcasestudy.CustomerOrder;
import originalcasestudy.DellStore;
import originalcasestudy.Employee;
import originalcasestudy.Examination;
import originalcasestudy.Flights;
import originalcasestudy.FrenchTowns;
import originalcasestudy.ITrust;
import originalcasestudy.Inventory;
import originalcasestudy.Iso3166;
import originalcasestudy.JWhoisServer;
import originalcasestudy.NistDML181;
import originalcasestudy.NistDML182;
import originalcasestudy.NistDML183;
import originalcasestudy.NistWeather;
import originalcasestudy.NistXTS748;
import originalcasestudy.NistXTS749;
import originalcasestudy.Person;
import originalcasestudy.Products;
import originalcasestudy.RiskIt;
import originalcasestudy.StudentResidence;
import originalcasestudy.UnixUsage;
import originalcasestudy.Usda;
import originalcasestudy.World;
import paper.util.SchemaStatsTable;

public class LatexSchemaStatsTable extends SchemaStatsTable {
	
	public static Schema[] schemas = {
		new Cloc(),
                new JWhoisServer(),
		new NistDML182(),
		new NistDML183(),
		new RiskIt(),
		new UnixUsage(),
	};	
	
	public LatexSchemaStatsTable() {
		super(" & ", " \\\\\n");
	}
	
	protected void writeHeader(StringBuffer table) {	
		table.append("%!TEX root=mut13-schemata.tex\n");
	}
	
	protected void writeFooter(StringBuffer table,
			   int totalNumTables, int totalNumColumns, // int totalUniqueColumnTypes,
			   int totalNumChecks, int totalNumForeignKeys, int totalNumNotNulls, 
			   int totalNumPrimaryKeys, int totalNumUniques) {
		table.append("\\midrule \n");
		
		writeRow(table, "{\\bf Total}", totalNumTables, totalNumColumns, // totalUniqueColumnTypes, 
				 totalNumChecks, totalNumForeignKeys, totalNumNotNulls, totalNumPrimaryKeys, totalNumUniques);		
	}	
	
	public static void main(String[] args) {
		LatexSchemaStatsTable table = new LatexSchemaStatsTable();
		System.out.println(table.write(schemas));
	}
}
