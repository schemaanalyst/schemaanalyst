package paper.mutation2013;

import paper.icst2013.*;

import org.schemaanalyst.representation.Schema;

import casestudy.BankAccount;
import casestudy.BookTown;
import casestudy.BooleanExample;
import casestudy.Cloc;
import casestudy.CoffeeOrders;
import casestudy.CustomerOrder;
import casestudy.DellStore;
import casestudy.Employee;
import casestudy.Examination;
import casestudy.Flights;
import casestudy.FrenchTowns;
import casestudy.ITrust;
import casestudy.Inventory;
import casestudy.Iso3166;
import casestudy.JWhoisServer;
import casestudy.NistDML181;
import casestudy.NistDML182;
import casestudy.NistDML183;
import casestudy.NistWeather;
import casestudy.NistXTS748;
import casestudy.NistXTS749;
import casestudy.Person;
import casestudy.Products;
import casestudy.RiskIt;
import casestudy.StudentResidence;
import casestudy.UnixUsage;
import casestudy.Usda;
import casestudy.World;
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
