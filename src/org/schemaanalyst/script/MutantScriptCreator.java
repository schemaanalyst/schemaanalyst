package org.schemaanalyst.script;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

import org.schemaanalyst.configuration.Configuration;

public class MutantScriptCreator extends ScriptCreator {

    public void configure() {
	try {
	    // create the scripts directory for storing the automatically
	    // generated scripts for satisfying and violating the schema
	    File scriptsDirectory = new File(Configuration.project +
					     "MutantScripts/");
	    
	    // if the Scripts/ directory does not exist, then create it
	    if (!scriptsDirectory.exists()) {
		scriptsDirectory.mkdir();  
	    }
	    
	    // create a PrintWriter associated with the text file
	    scriptOutput = new PrintWriter(Configuration.project +
					   "MutantScripts/" +
					   Configuration.mutantscriptfile);
	}

	catch(FileNotFoundException e) {
	    e.printStackTrace();
	}
    }
}