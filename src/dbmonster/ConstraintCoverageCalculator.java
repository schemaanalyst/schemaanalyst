package dbmonster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.ValueVisitor;
import org.schemaanalyst.datageneration.SimpleConstraintCoverageReport;
import org.schemaanalyst.datageneration.search.ConstraintEvaluator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import casestudy.BankAccount;
import casestudy.BookTown;
import casestudy.Cloc;
import casestudy.CoffeeOrders;
import casestudy.CustomerOrder;
import casestudy.DellStore;
import casestudy.Employee;
import casestudy.Examination;
import casestudy.Flights;
import casestudy.FrenchTowns;
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

public class ConstraintCoverageCalculator extends ConstraintEvaluator {

	protected static final String LINE_START = "INSERT INTO ",
								  END_COLS = ") VALUES (",
								  END_VALUES = ")";
								
	protected static final String FILE_SUFFIX = ".sql";
	
	protected String fileName;
	protected ValueFactory valueFactory;
	protected boolean treatEmptyStringAsNull;
	protected int total, covered;
	
	protected static final boolean debug = false;
	
	public ConstraintCoverageCalculator(String fileName, boolean treatEmptyStringAsNull) {
		this.fileName = fileName;
		this.treatEmptyStringAsNull = treatEmptyStringAsNull;
		
		// get the case study name
		String caseStudyName = fileName.substring(fileName.lastIndexOf("/")+1);
		caseStudyName = caseStudyName.substring(0, caseStudyName.lastIndexOf("-"));
		String schemaClassName = "casestudy." + caseStudyName;
		
		try {
			schema =  (Schema) Class.forName(schemaClassName).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("ERROR: Cannot instantiate schema -- class name tried was "+schemaClassName);
		}
		
		valueFactory = new ValueFactory();		
	}
	
	public void compute() throws IOException {
		initialize(schema);
		state = new Data();
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		
		while (line != null) {
			parseLine(line);
			line = br.readLine();
		}
		
		br.close();
	}
	
	protected void parseLine(String line) {
		
		if (line.startsWith(LINE_START)) {			
			if (debug) {
				System.out.println("\n" + line);
			}
			
			String remainder = line.substring(LINE_START.length());
			
			// parse table name
			int endTable = remainder.indexOf(" ");
			String tableName = remainder.substring(0, endTable);
		
			// parse columns 
			remainder = remainder.substring(endTable + 2);
			int endCols = remainder.indexOf(END_COLS);
			String colsString = remainder.substring(0, endCols);
			String[] cols = colsString.split(", ");
			
			// parse values
			remainder = remainder.substring(endCols + END_COLS.length());
			String valuesString = remainder.substring(0, remainder.length() - END_VALUES.length());
			String[] values = valuesString.split(", ");
			
			// check the row
			checkRow(line, tableName, cols, values);		
		}
	}
	
	protected void checkRow(String line, String tableName, String[] cols, String[] values) {

		Table table = schema.getTable(tableName);
		if (table == null) {
			throw new RuntimeException("ERROR: Could not find table "+tableName+" in schema "+schema.getName());
		}

		Data data = new Data();
		Row row = data.addRow(table, valueFactory);
		
		try {
			insertValuesIntoRow(tableName, cols, values, table, row);
			
			if (debug) {
				System.out.println("Checking row "+row);
			}
			if (evaluate(row, table)) {
				if (debug) {
					System.out.println("Row inserted");
				}
			} else {
				if (debug) {
					System.out.println("Row not inserted");
				}
			}
		} catch (ConstraintCoverageCalculatorParseException e) {
			// System.out.println("Ignored row as a result of: "+e.getMessage());
		}
	}

	protected void insertValuesIntoRow(String tableName, 
									   String[] cols, 
									   String[] values, 
									   Table table, 
									   Row row) throws ConstraintCoverageCalculatorParseException {
		for (int i=0; i < cols.length; i++) {
			String columnName = cols[i];
			Column column = table.getColumn(columnName);
			if (column == null) {
				throw new RuntimeException(
							"ERROR: Could not find column "
							+ columnName + " in table " + tableName 
							+ " in schema " + schema.getName());
			}	
			
			Cell cell = row.getCell(column);
			String value = values[i].trim();
			if (treatEmptyStringAsNull && value.equals("")) {
				cell.setNull(true);
			} else {
				setCellValue(cell, value.substring(1, value.length()-1));
			}
		}
	}
	
	protected void setCellValue(Cell cell, 
								String valueString) throws ConstraintCoverageCalculatorParseException {
		
		class ValueDispatcher implements ValueVisitor {

			String valueString;
			String fail;
			
			public void visit(BooleanValue value) {
				if (valueString.equalsIgnoreCase("true")) {
					value.set(true);
				} else if (valueString.equalsIgnoreCase("false")) {
					value.set(false);
				} else {
					fail = "Could not parse BooleanValue \"" + valueString + "\"";
				}
			}
			
			public void visit(DateValue value)  {
				String[] parts = valueString.split("-");
				if (parts.length == 3) {
					value.getYear().set(Integer.parseInt(parts[0]));
					value.getMonth().set(Integer.parseInt(parts[1]));
					value.getDay().set(Integer.parseInt(parts[2]));
				} else {
					fail = "Could not parse DateValue \"" + valueString + "\"";
				}
			}
			
			public void visit(DateTimeValue value) {
				throw new RuntimeException("ERROR: Don't know how to parse DateTimeValue yet!");
			}
			
			public void visit(NumericValue value) {
				if (valueString.equals("")) {
					fail = "Could not parse NumericValue \"" + valueString + "\"";
				} else { 
					value.set(new BigDecimal(valueString));
				}
			}
			
			public void visit(StringValue value) {
				value.set(valueString);
			}
			
			public void visit(TimeValue value) {				
				String[] parts = valueString.split(":");
				if (parts.length == 3) {
					value.getHour().set(Integer.parseInt(parts[0]));
					value.getMinute().set(Integer.parseInt(parts[1]));
					value.getSecond().set(Integer.parseInt(parts[2]));
				} else {
					fail = "Could not parse DateValue \"" + valueString + "\"";
				}
			}
			
			public void visit(TimestampValue value) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS z");
				
				try {
					Date parsedDate = dateFormat.parse(valueString + " UTC");
					int milliseconds = (int) parsedDate.getTime() / 1000;
					value.set(milliseconds);
				} catch (ParseException e) {
					fail = "Could not parse TimestampValue \"" + valueString + "\"";
				}				
			}
		}		
		
		if (treatEmptyStringAsNull && valueString.equals("")) {
			cell.setNull(true);
		} else {			
			ValueDispatcher vd = new ValueDispatcher();
			vd.valueString = valueString;
			cell.getValue().accept(vd);
			if (vd.fail != null) {
				throw new ConstraintCoverageCalculatorParseException(vd.fail);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Schema[] schemas = {		
				new NistDML182()
			};
		
		for (Schema schema : schemas) {
		
		int totalCovered = 0;
		int totalNumGoals = 0;
		
		for (int i=1; i <= 30; i++) {
			System.out.print(i+" ");
			ConstraintCoverageCalculator calc = new ConstraintCoverageCalculator(
					"./sql/"+schema.getName()+"-"+i+".sql",
					false);
			calc.compute();
			
			SimpleConstraintCoverageReport report = calc.getCoverageReport();
			
			totalCovered += report.getTotalCovered();
			totalNumGoals += report.getNumGoals();
		}
		
		System.out.println("\n"+schema.getName() + " " + totalCovered + " " +  totalNumGoals + "  " + (totalCovered / (double) totalNumGoals));
		}
		
		//System.out.println("\nCoverage: " + report.getTotalCovered() + " / " + report.getNumGoals()+ " (" + report.getCoveragePercentage() + "%)");
		//System.out.println("Satisfied constraints: " + report.getSatisfiedConstraints());
		//System.out.println("Falsified constraints: " + report.getNegatedConstraints());
	}
}

@SuppressWarnings("serial")
class ConstraintCoverageCalculatorParseException extends Exception {
	
	public ConstraintCoverageCalculatorParseException(String message) {
		super(message);
	}
}
