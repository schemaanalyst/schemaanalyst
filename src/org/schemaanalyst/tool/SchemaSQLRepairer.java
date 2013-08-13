package org.schemaanalyst.tool;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.sql.SQLRepairer;

@Description("Repairs a schema SQL file and then writes it back to the console.")
@RequiredParameters("schemaInput schemaOutput")
public class SchemaSQLRepairer extends Runner {

    @Parameter("The name of the schema to repair.  The SQL file must be placed " +
               "in the schemas subdirectory of casestudies")
    protected String schemaInput;
 
 	@Parameter("The name of the schema that is the result of repair.  The SQL file will be placed " +
               "in the schemas subdirectory of casestudies")
    protected String schemaOutput;
   
	/**
	 * This method will repair a schema so that it does not contain single quotes or spaces in single quotes.
	 *
	 */
    protected void repairSchema() {
    	// get the file; assuming that it is like other sql files and just stored in the casestudies directory
        File sqlFileInput = new File(locationsConfiguration.getSchemaSrcDir() + File.separator + schemaInput + ".sql");
	
    	// convert the file to an ArrayList of strings that we can manage line by line
    	List<String> sqlLines = SQLRepairer.readLines(sqlFileInput);

		// iterate through all of the lines of SQL and repair them so that they can be parsed correctly
		ArrayList<String> sqlLinesRepaired = new ArrayList<String>();
    	for(String sqlLine : sqlLines) {
			String sqlLineNoWhiteSpace = SQLRepairer.deleteSpacesInsideSingleQuotes(sqlLine);
			String sqlLineNoWhiteSpaceNoSingleQuotes = SQLRepairer.deleteSingleQuotes(sqlLineNoWhiteSpace);
			String sqlLineNoWhiteSpaceNoSingleQuotesNoDouble = SQLRepairer.replaceDoubleWithReal(sqlLineNoWhiteSpaceNoSingleQuotes); 
			String sqlRemovedDashes = SQLRepairer.removeDashes(sqlLineNoWhiteSpaceNoSingleQuotesNoDouble);
			String sqlRemovedCurlyBraces = SQLRepairer.removeCurlyBraces(sqlRemovedDashes);
			sqlLinesRepaired.add(sqlRemovedCurlyBraces);
		}

	  	// get the file; assuming that it will be stored in the casestudies directory
        File sqlFileOutput = new File(locationsConfiguration.getSchemaSrcDir() + File.separator + schemaOutput + ".sql");
		try {
			// write out the sql lines that have all been repaired
			PrintWriter writer = new PrintWriter(sqlFileOutput);
			for(String sqlLineRepaired : sqlLinesRepaired) {
				writer.println(sqlLineRepaired);
			}
			writer.close();
		}
		catch(IOException e) {
		 	throw new RuntimeException(e);
		}
    }
    
    @Override
    protected void initialise(String... args) {
        super.initialise(args);        
    }
    
    @Override
    protected void task() {
    	repairSchema();
    }

    @Override
    protected void validateParameters() {
        // nothing to do here
    }
    
    public static void main(String... args) {
        new SchemaSQLRepairer().run(args);
    }
}
