package experiment;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver; 

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import experiment.util.XMLFormatter;
import org.schemaanalyst.configuration.FolderConfiguration;


public class ExperimentalResults {
    
    private static final String DEFAULT_LOCATION = FolderConfiguration.results_dir + File.separator;

    /** The header with the name of all of the parameters and measured variables */
    private ArrayList<String> header;

    /** The listing of the ExperimentalResult objects */
    private ExperimentalResult experimentalResult;

    /** Flag indicating whether or not the header has been written */
    private boolean wroteHeader;

    /** Flag for the second header */
    private boolean wroteCountHeader;

    /** Flag for the third header */
    private boolean wroteInsertCountHeader;

    /** Flag for the fourth header */
    private boolean wroteExecutionTimeHeader;

    /** The name of the experimental results which will be concatenated with the standard data file name*/
    private String name;

    /** The PrintWriter that can save the report on the file system, in serialized format */
    private static PrintWriter reportOutputSer;

    /** The PrintWriter that can save the report on the file system, in CSV data format, non-appending */
    private static PrintWriter dataOutputHeader;

    /** The PrintWriter that can save the report on the file system, in CSV data format, appending */
    private static FileWriter dataOutput;

    /** Boolean flag to indicate that you should append */
    private static final boolean PLEASE_APPEND = true;

    /** Initialize the experimental results */
    public ExperimentalResults() {
	this("");
    }

    /** Initialize the experimental results with the provided name for the file */
    public ExperimentalResults(String name) {
	header = new ArrayList<>();
	experimentalResult = new ExperimentalResult();
	wroteHeader = false;
	wroteCountHeader = false;
	wroteInsertCountHeader = false;
	wroteExecutionTimeHeader = false;
	this.name = name;
    }

    /** Set the name for the experimental results */
    public void setName(String name) {
	this.name = name;
    }

    /** Get the name for the experimental results */
    public String getName() {
	return name;
    }

    /** Reset this line of the experiment results -- called from one JVM execution to the next */
    public void reset() {
	header = new ArrayList<String>();
	experimentalResult = new ExperimentalResult();
    }

    /** Reset the head and associated flag for the experimental results */
    public void headerReset() {
	wroteHeader = false;
    }

    /** Reset the head and associated flag for the experimental results */
    public void countHeaderReset() {
	wroteCountHeader = false;
    }

    /** Reset the head and associated flag for the experimental results */
    public void insertCountHeaderReset() {
	wroteInsertCountHeader = false;
    }

   /** Reset the head and associated flag for the experimental results */
    public void executionTimeHeaderReset() {
	wroteExecutionTimeHeader = false;
    }

    /** Get the status for the writing of the count header */
    public boolean wroteCountHeader() {
	return wroteCountHeader;
    }

    /** Get the status for the writing of the count header */
    public boolean wroteInsertCountHeader() {
	return wroteInsertCountHeader;
    }

    /** Get the status for the writing of the count header */
    public boolean wroteExecutionTimeHeader() {
	return wroteExecutionTimeHeader;
    }

    /** Indicate that the count header was written to the file system */
    public void didWriteCountHeader() {
	wroteCountHeader = true;
    }

    /** Indicate that the count header was written to the file system */
    public void didWriteInsertCountHeader() {
	wroteInsertCountHeader = true;
    }

    /** Indicate that the count header was written to the file system */
    public void didWriteExecutionTimeHeader() {
	wroteExecutionTimeHeader = true;
    }

    /** Add the result to the experimental results */
    public void addResult(String headerName, String value) {
	// add the headerName to the header and get the index at which it was stored
	header.add(headerName);
	int headerIndex = header.indexOf(headerName);
	
	// add the current value into the experimentalResult at the header's index
	experimentalResult.addResult(headerIndex, value);
    }

    /** Return the header that will be written to the file */
    public List<String> getHeader() {
	return header;
    }
    
    /** Return the experimental result object (there is just one) */
    public ExperimentalResult getExperimentalResult() {
	return experimentalResult; 
    }

    /** Determine whether or not the header has been written */
    public boolean wroteHeader() {
	return wroteHeader;
    }

    /** Write out this experimental result to the file system */
    public void writeResults() {
	// write out the header to the data file
	if(!wroteHeader) {
	    //System.out.println("Writing the header!");
	    writeHeader();
	    writeData();
	}
	
	// the header has already been written, just write the data to the file
	else {
	    //System.out.println("Just writing the data!");
	    writeData();
	}
    }

    /** Write out the header to the CSV file. */
    public void writeHeader() {
	//System.out.println("Header being written for the first time!");
	    
	// convert the header to a string that can be written to a file
	String headerAsString = ExperimentUtilities.convertListToCsv(header);
	
	try {
	    // write the header to the file for the very first time
	    dataOutputHeader = new PrintWriter(DEFAULT_LOCATION + "experimentalresults.dat");
	}
	    
	catch(FileNotFoundException e) {
	    e.printStackTrace();
	}

	// write out the header, which does not need to be written again
	print(dataOutputHeader, headerAsString);
	
	// we have written the results for the first time, so the header has now been written
	wroteHeader = true;
    }

    /** Toggle the boolean variable indicating whether or not the header has been written */
    public void goWriteHeader() {
	wroteHeader=true;
    }

    /** Write out the data using the appending approach */
    private void writeData() {
	//System.out.println("Just writing out the data to the file!");

	String experimentResultAsString = ExperimentUtilities.convertListToCsv(experimentalResult.getResults());
	
	try {
	    // write the header to the file for the very first time
	    dataOutput = new FileWriter(DEFAULT_LOCATION + "experimentalresults.dat", PLEASE_APPEND);
	}

	catch(IOException e) {
	    e.printStackTrace();
	}	 
	
	// write out the header, which does not need to be written again
	print(dataOutput, experimentResultAsString);
    }

    /** Save the ExperimentalResults to a file, for use by the next JVM invocation */
    public void save() {
	try {
	    // create the scripts directory for storing the automatically
	    // generated scripts for satisfying and violating the schema
	    File experimentsDirectory = new File(DEFAULT_LOCATION);
	    
	    // if the Scripts/ directory does not exist, then create it
	    if (!experimentsDirectory.exists()) {
		//System.out.println("Had to make directory!");
		experimentsDirectory.mkdir();  
	    }	    

	    // configure the output serializer correctly
	    reportOutputSer = new PrintWriter(DEFAULT_LOCATION + "experimentalresults.xml");
	}
	
	catch(FileNotFoundException e) {
	    e.printStackTrace();
	}

	// create the XStream object and then serialize the file
	XStream xstream = new XStream(new StaxDriver());
	String xml = xstream.toXML(this);
	String formattedXml = XMLFormatter.format(xml);
	print(reportOutputSer, formattedXml);
    }

    /** Retrieve the parameters from the file system */
    public static ExperimentalResults retrieve() {
	ExperimentalResults restoredParameters = null;
	try {
	    // create the scripts directory for storing the automatically
	    // generated scripts for satisfying and violating the schema
	    File experimentsDirectory = new File(DEFAULT_LOCATION);
	    
	    // if the Scripts/ directory does not exist, then create it
	    if (!experimentsDirectory.exists()) {
		//System.out.println("Had to make directory!");
		experimentsDirectory.mkdir();  
	    }	    

	    // create the file that stores the XML
	    File parameters = new File(DEFAULT_LOCATION + "experimentalresults.xml");
	    
	    // read in the contents of the file to a string
	    String xml = new Scanner(parameters).useDelimiter("\\A").next();

	    // reload the parameters object and return it
	    XStream xstream = new XStream(new StaxDriver());
	    restoredParameters = (ExperimentalResults)xstream.fromXML(xml);
	}
	
	catch(IOException e) {
	    e.printStackTrace();
	}

	return restoredParameters;
    }

    /** Print out a specified line with a specified PrintWriter */
    private static void print(PrintWriter writer, String line) {
	writer.println(line);
	writer.flush();
    }

    /** Print out a specified line with a specified FileWriter */
    private static void print(FileWriter writer, String line) {
	try {
	    writer.write(line);
	    writer.write("\n");
	    writer.flush();
	}
	catch(IOException e) {
	    e.printStackTrace();
	}
    }
}