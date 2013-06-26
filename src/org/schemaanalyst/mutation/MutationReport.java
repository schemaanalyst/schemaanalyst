package org.schemaanalyst.mutation;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver; 

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

import org.schemaanalyst.configuration.Configuration;
import org.schemaanalyst.configuration.FolderConfiguration;

public class MutationReport {
    
    public static final String DEFAULT_LOCATION = FolderConfiguration.results_dir + File.separator;

    /** The report of the ORIGINAL, NON-MUTANT schema */
    private SQLExecutionReport originalReport;

    /** The report of the MUTANTS */
    private List<MutantReport> mutantReports;

    /** The scores associated with this mutation report */
    private MutationReportScores mutationReportScores;

    /** The file connected to the report on the file system and used for reloading */
    private static File report;

    /** The PrintWriter that can save the report on the file system */
    private static PrintWriter reportOutputSer;

    /** The PrintWriter that can save the report on the file system */
    private static PrintWriter reportOutputText;

    public MutationReport() {
	mutantReports = new ArrayList<>();
	mutationReportScores = new MutationReportScores();
    }

    /** Set the SQL Execution report for the ORIGINAL, NON-MUTANT schema */
    public void setOriginalReport(SQLExecutionReport report) {
	originalReport = report;
    }

    /** Return the SQL Execution report for the ORIGINAL, NON-MUTANT schema */
    public SQLExecutionReport getOriginalReport() {
	return originalReport;
    }

    /** Add a MUTANT report to the list */
    public void addMutantReport(MutantReport report) {
	mutantReports.add(report);
    }

    /** Return the list of the MUTANT reports */
    public List<MutantReport> getMutantReports() {
	return mutantReports;
    }

    /** Return the scores for this mutation report */
    public MutationReportScores getScores() {
	return mutationReportScores;
    }

    /** Calculate the mutation score for this report */
    public void calculateMutationScoresAndStatistics() {
	// the count of the number of kills for this mutant report (the NUMERATOR)
	int currentMutantReportKills = 0;
	
	// the total number of mutants for this report (the DENOMINATOR)
	int totalMutantReports = mutantReports.size();

	// the running count for the total number of inserts that could kill (the NUMERATOR)
	int totalMutantReportsCouldKill = 0;

	// the running count for the total number of kills (the DENOMINATOR)
	int totalMutantReportKills = 0;

	// the number of mutants that were satisfying and then killed
	int insertSatisfyAndKill = 0;

	// the number of mutants that were negating and then killed
	int insertNegateAndKill = 0;

	// the number of mutants that were satisfying and then killed
	int insertSatisfyAndNotKill = 0;

	// the number of mutants that were negating and then killed
	int insertNegateAndNotKill = 0;

	// go through each of the mutant reports
	for(MutantReport currentMutantReport : mutantReports) {
	    // the count of the number of kills inside this mutant report
	    int insideCurrentMutantReportKills = 0;

	    // go through each of the mutant statements
	    for(MutantRecord currentMutantStatement : currentMutantReport.getMutantStatements()) {
		// this insert killed the current mutant schema
		if(currentMutantStatement.didKillMutant() || currentMutantReport.wasBornStill()) {
		    insideCurrentMutantReportKills++;

		    // this mutant was satisfying and it killed
		    if(currentMutantStatement.isTryingToSatisfy()) {
			insertSatisfyAndKill++;
		    }
		    // this mutant was negating and it killed
		    if(currentMutantStatement.isTryingToNegate()) {
			insertNegateAndKill++;
		    }
		}
		// this insert did not kill the current mutant schema
		else {
		    // this mutant was satisfying and it did not kill
		    if(currentMutantStatement.isTryingToSatisfy()) {
			insertSatisfyAndNotKill++;
		    }
		    // this mutant was negating and it did not kill
		    if(currentMutantStatement.isTryingToNegate()) {
			insertNegateAndNotKill++;
		    }
		}
	    }
	    // if at least one of the statements kills the mutant schema, then increment the outer kill value
	    if( insideCurrentMutantReportKills > 0) {
		currentMutantReportKills++;
		currentMutantReport.killMutant();
	    }

	    currentMutantReport.computeIntersection();

	    // increment the variables for the running totals
	    totalMutantReportKills += insideCurrentMutantReportKills;
	    totalMutantReportsCouldKill += currentMutantReport.getMutantStatements().size();

	    // reinitialize the inside current mutation report kill counter back to zero
	    insideCurrentMutantReportKills = 0;
	}

	if(Configuration.debug) {
	    System.out.println("Standard Numerator: " + currentMutantReportKills);
	    System.out.println("Standard Denominator: " + totalMutantReports);
	    
	    System.out.println("Weighted Numerator: " + totalMutantReportKills);
	    System.out.println("Weighted Denominator: " + totalMutantReportsCouldKill);
	}

	// calculate the final mutation score 
	//mutationScore = (double)currentMutantReportKills / (double)totalMutantReports;
	MutationReportScore mutationScore = new MutationReportScore("mutationScore", 
								    currentMutantReportKills,
								    totalMutantReports);
	mutationReportScores.add(mutationScore);

	// calculate the weighted mutation score 
	// weightedMutationScore = (double)totalMutantReportKills / (double)totalMutantReportsCouldKill;
	MutationReportScore weightedMutationScore = new MutationReportScore("weightedMutationScore", 
									    totalMutantReportKills,
									    totalMutantReportsCouldKill);
	mutationReportScores.add(weightedMutationScore);

	// calculate the satisfy and kill ratio
	// satisfyAndKillRatio = (double)insertSatisfyAndKill / 
	//     ((double)insertSatisfyAndKill + (double)insertSatisfyAndNotKill);
	MutationReportScore satisfyAndKillScore = new MutationReportScore("satisfyAndKillScore", 
									  insertSatisfyAndKill,
									  (insertSatisfyAndKill+insertSatisfyAndNotKill));
	mutationReportScores.add(satisfyAndKillScore);

	// calculate the satisfy and not kill ratio
	// satisfyAndNotKillRatio = (double)insertSatisfyAndNotKill / 
	//     ((double)insertSatisfyAndKill + (double)insertSatisfyAndNotKill);
	MutationReportScore satisfyAndNotKillScore = new MutationReportScore("satisfyAndNotKillScore", 
									  insertSatisfyAndNotKill,
									  (insertSatisfyAndKill+insertSatisfyAndNotKill));
	mutationReportScores.add(satisfyAndNotKillScore);

	// calculate the negate and kill ratio
	// negateAndKillRatio = (double)insertNegateAndKill / 
	//     ((double)insertNegateAndKill + (double)insertNegateAndNotKill);
	MutationReportScore negateAndKillScore = new MutationReportScore("negateAndKillScore", 
									  insertNegateAndKill,
									  (insertNegateAndKill+insertNegateAndNotKill));
	mutationReportScores.add(negateAndKillScore);
	
	// calculate the negate and not kill ratio
	// negateAndNotKillRatio = (double)insertNegateAndNotKill / 
	//     ((double)insertNegateAndKill + (double)insertNegateAndNotKill);
	MutationReportScore negateAndNotKillScore = new MutationReportScore("negateAndNotKillScore", 
									    insertNegateAndNotKill,
									    (insertNegateAndKill+insertNegateAndNotKill));
	mutationReportScores.add(negateAndNotKillScore);
    }

    /** Configure the report to perform the output */
    public static void configureForReportSaving() {
	try {
	    // create the scripts directory for storing the automatically
	    // generated scripts for satisfying and violating the schema
	    File reportsDirectory = new File(DEFAULT_LOCATION);
	    
	    // if the Scripts/ directory does not exist, then create it
	    if (!reportsDirectory.exists()) {
		System.out.println("Had to make directory!");
		reportsDirectory.mkdir();  
	    }
	    
	    // create a PrintWriter associated with the serialization file
	    if(Configuration.wantmutationreport_mrp) {
		reportOutputSer = new PrintWriter(FolderConfiguration.results_dir + Configuration.mutationreport_mrp);
	    }

	    // create the PrintWriter associated with the text file
	    if(Configuration.wantmutationreport_txt) {
		reportOutputText = new PrintWriter(FolderConfiguration.results_dir + Configuration.mutationreport_txt);
	    }
	}

	catch(FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    /** Initialize the file for reloading the report.  NOTE: for some
	reason, this could not go into the same method as was used for
	report loading.  It made reading into a blank file. */
    public static void configureForReportLoading() {
	// create the File associated with the serialized file; this can be
	// used during the reloading process
	report = new File(FolderConfiguration.results_dir + Configuration.mutationreport_mrp);
    }

    /** Save the MutationReport to the file system using XStream */
    public static void save(MutationReport report) {
	// write out the XML file to the file system in the right location;
	// this will always write out the entire report, there is no way to abbreviate
	if(Configuration.wantmutationreport_mrp ) {
	    // configuration the XStream serializer
	    XStream xstream = new XStream(new StaxDriver());
	    xstream.alias("MutationReport", org.schemaanalyst.mutation.MutationReport.class);
	    xstream.alias("SQLExecutionRecord", org.schemaanalyst.mutation.SQLExecutionRecord.class);
	    xstream.alias("SQLInsertRecord", org.schemaanalyst.mutation.SQLInsertRecord.class);
	    xstream.alias("MutantReport", org.schemaanalyst.mutation.MutantReport.class);	
	    xstream.alias("MutantRecord", org.schemaanalyst.mutation.MutantRecord.class);	
	    
	    // write out the serialized-in-XML file to the file system in the right location
	    String xml = xstream.toXML(report);
	    print(reportOutputSer, xml);
	}

	// write out the plain text file to the file system in the right location	
	if(Configuration.wantmutationreport_txt) {
	    // just write out the summary of the mutation report
	    if(Configuration.onlymutationsummary) {
		print(reportOutputText, report.toStringAbbreviated());
	    }
	    // write out the complete report
	    else {
		print(reportOutputText, report.toString());
	    }
	}
    }

    /** Print out a specified line with a specified PrintWriter */
    public static void print(PrintWriter writer, String line) {
	writer.println(line);
	writer.flush();
    }

    /* Close the report output file that is used for serialization */
    public static void close() {
	if(Configuration.wantmutationreport_mrp) {
	    reportOutputSer.close();
	}
	if(Configuration.wantmutationreport_txt) {
	    reportOutputText.close();
	}
    }

    /* Load the specified default mutation report */
    public static MutationReport loadSpecifiedDefault() {
	String text = null;
	try {
	    text = new Scanner( report ).useDelimiter("\\A").next();
	}

	catch(FileNotFoundException e) {
	    System.out.println("File not found.");
	    e.printStackTrace();
	}

	return load(text);
    }

    /* Load a mutation report so it can be reused or tested */
    public static MutationReport load(String filecontents) {
	XStream xstream = new XStream(new StaxDriver());
	xstream.alias("MutationReport", org.schemaanalyst.mutation.MutationReport.class);
	xstream.alias("SQLExecutionRecord", org.schemaanalyst.mutation.SQLExecutionRecord.class);
	xstream.alias("SQLInsertRecord", org.schemaanalyst.mutation.SQLInsertRecord.class);
	xstream.alias("MutantReport", org.schemaanalyst.mutation.MutantReport.class);	
	xstream.alias("MutantRecord", org.schemaanalyst.mutation.MutantRecord.class);	
	MutationReport reloadedReport = (MutationReport)xstream.fromXML(filecontents);
	return reloadedReport;
    }

    /** */

    /** Create a summary of the killing status for the MutantReports */
    public String createMutantReportSummary() {
	
	ArrayList<String> killed = new ArrayList<String>();
	ArrayList<String> stillborn = new ArrayList<String>();
	ArrayList<String> intersection = new ArrayList<String>();

	StringBuilder builder = new StringBuilder();
	
	//builder.append("mutant, killed, notkilled \n");
	for(MutantReport currentMutantReport : mutantReports) {

	    if( currentMutantReport.didKillMutant() ) {
		killed.add(Integer.toString(currentMutantReport.getNumericalIdentifier()));
	    }

	    if( currentMutantReport.wasBornStill() ) {
		stillborn.add(Integer.toString(currentMutantReport.getNumericalIdentifier()));
	    }

	    if( currentMutantReport.wasIntersection() ) {
		intersection.add(Integer.toString(currentMutantReport.getNumericalIdentifier()));
	    }

	    builder.append("Mutant " + currentMutantReport.getNumericalIdentifier() +
	    		   " - Was it Killed? " + currentMutantReport.didKillMutant() + 
	    		   " - Was it Still Born? " + currentMutantReport.wasBornStill() + 
	    		   " - Was it Intersected? " + currentMutantReport.wasIntersection() + 
			   " - Purpose? " + currentMutantReport.getDescription() + "\n");

	    builder.append("" + currentMutantReport.getNumericalIdentifier() + "," +
	    		   currentMutantReport.didKillMutant() + "," +
	    		   currentMutantReport.wasBornStill() + 
			   currentMutantReport.wasIntersection() + "\n");
	    // }

	}

	builder.append("killed=c(" + killed + ")\n");
	builder.append("quasi=c(" + stillborn + ")\n");
	builder.append("intersection=c(" + intersection + ")\n");

	return builder.toString();
    }

    /** Create a summary of the killing count status for the MutantReports */
    public MutationTypeStatusSummary createMutationTypeStatusSummary() {
	MutationTypeStatusSummary mutationTypeStatusSummary = new MutationTypeStatusSummary();
	// go through all of the mutantReports and update the type status summary
	for(MutantReport currentMutantReport : mutantReports) {
	    mutationTypeStatusSummary.process(currentMutantReport);
	}

	// System.out.println("Type Summary: " + mutationTypeStatusSummary);

	return mutationTypeStatusSummary;
    }

    public String toString() {
	return "ORIGINAL REPORT: \n" + originalReport + "\n" + 
	    "SCORES: \n" + mutationReportScores + "\n\n" +
	    "MUTANT SUMMARY: \n" + createMutantReportSummary() + "\n" +
	    "MUTANT REPORTS: \n" + mutantReports; 
    }

    public String toStringAbbreviated() {
	return "ORIGINAL REPORT: \n" + originalReport + "\n" + 
	    "SCORES: \n" + mutationReportScores + "\n\n" +
	    "MUTANT SUMMARY: \n" + createMutantReportSummary() + "\n";
    }
}