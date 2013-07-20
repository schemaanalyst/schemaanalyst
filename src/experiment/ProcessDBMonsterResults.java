package experiment;

import plume.*;

import com.rits.cloning.Cloner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import experiment.ExperimentUtilities;

import org.schemaanalyst.mutation.MutationUtilities;
import org.schemaanalyst.deprecated.configuration.Configuration;

public class ProcessDBMonsterResults {

    /**
     * The PrintWriter that can save the report on the file system, in CSV data
     * format, non-appending
     */
    private static PrintWriter dataOutputHeader;
    /**
     * The PrintWriter that can save the report on the file system, in CSV data
     * format, appending
     */
    private static FileWriter dataOutput;
    /**
     * Boolean flag to indicate that you should append
     */
    private static final boolean PLEASE_APPEND = true;
    private static final double CONVERT_TO_SECONDS = 1000000000.0;

    /**
     * Process all of the files in a current directory
     */
    public static List<String> process(File directory) {
        ArrayList<String> data = new ArrayList<String>();
        String filecontents = "";

        File files[] = directory.listFiles();
        for (File file : files) {

            System.out.println(file.getName());

            if (file.length() > 0) {

                try {
                    filecontents = new Scanner(file).useDelimiter("\\A").next();
                } catch (FileNotFoundException e) {
                    System.out.println("Problem finding file");
                    e.printStackTrace();
                }

                // extract the important data from the file
                List<String> importantnumbers = extract(filecontents);

                if (Configuration.debug) {
                    System.out.println(file);
                    System.out.println(filecontents);
                    System.out.println(importantnumbers);
                }

                // add the other important labels to a list
                ArrayList<String> row = new ArrayList<String>();
                row.add("dbmonster");
                row.add(MutationUtilities.removePrefixAndSuffixFromCaseStudyName(file.getName()));
                row.add("org.schemaanalyst.database.postgres.Postgres");

                // declare a variable that corresponds to the current trial
                int trial = Configuration.trialstart;

                // go through all of the important numbers
                for (String importantnumber : importantnumbers) {

                    // create the cloner that we will use to make the deep-copy duplicates of current experimentalResults
                    Cloner cloner = new Cloner();

                    // create a deep-copy clone of the experimentalResults for storing this redundant, but with new, line of data
                    ArrayList<String> currentRow = cloner.deepClone(row);

                    // add the trial number to the row and go to the next row
                    currentRow.add(Integer.toString(trial));
                    trial++;

                    // add the current data value to the row -- dividing by 1000 to convert milliseconds to 
                    // seconds, since this is the unit of measurement used in the other graphs
                    currentRow.add(Double.toString(((Double.parseDouble(importantnumber)) / CONVERT_TO_SECONDS)));

                    // convert the important numbers to a CSV listing	    
                    String rowAsString = ExperimentUtilities.convertListToCsv(currentRow);

                    try {

                        // create the scripts directory for storing the automatically
                        // generated scripts for satisfying and violating the schema
                        File reportsDirectory = new File(Configuration.project
                                + "/" + "Experiments/DBMonsterResults/");

                        // if the Scripts/ directory does not exist, then create it
                        if (!reportsDirectory.exists()) {
                            System.out.println("Had to make directory!");
                            reportsDirectory.mkdir();
                        }

                        // write the header to the file for the very first time
                        dataOutput = new FileWriter(Configuration.project
                                + "/" + "Experiments/DBMonsterResults/ExecutionTime"
                                + "ExperimentalResults.dat", PLEASE_APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // write out the data
                    print(dataOutput, rowAsString);
                }
            }
        }
        return data;
    }

    /**
     * Extract the really important numbers from the file
     */
    public static List<String> extract(String rawdata) {
        // declare the ArrayList that will store the refined data
        ArrayList<String> refineddata = new ArrayList<String>();

        // declare a pattern that matches the part of the string (a number followed by ms)
        Pattern timingValueOnly = Pattern.compile("\\d+ ns");
        Matcher makeTimingMatch = timingValueOnly.matcher(rawdata);

        // iterate through all of the matches that we have found
        while (makeTimingMatch.find()) {
            // extract the currently matching string
            String timingvalue = makeTimingMatch.group();
            if (Configuration.debug) {
                System.out.println(timingvalue);
            }
            // declare a new pattern that only matches the number inside of the current string 
            Pattern intsOnly = Pattern.compile("\\d+");
            Matcher makeIntMatch = intsOnly.matcher(timingvalue);
            // iterate through all of the matches that we have found
            while (makeIntMatch.find()) {
                // extract the currently matching string
                String intvalue = makeIntMatch.group();
                if (Configuration.debug) {
                    System.out.println(intvalue);
                }
                refineddata.add(intvalue);
            }
        }

        // returned the refined data that contains only the numbers
        return refineddata;
    }

    /**
     * Write the header to the data file
     */
    public static void writeHeader() {
        // convert the header to a string that can be written to a file
        ArrayList<String> header = new ArrayList<String>();
        header.add("datagenerator");
        header.add("database");
        header.add("type");
        header.add("trial");
        header.add("datagenerationtime");
        String headerAsString = ExperimentUtilities.convertListToCsv(header);

        try {

            // create the scripts directory for storing the automatically
            // generated scripts for satisfying and violating the schema
            File reportsDirectory = new File(Configuration.project
                    + "/" + "Experiments/DBMonsterResults/");

            // if the Scripts/ directory does not exist, then create it
            if (!reportsDirectory.exists()) {
                System.out.println("Had to make directory!");
                reportsDirectory.mkdir();
            }

            // write the header to the file for the very first time
            dataOutputHeader = new PrintWriter(Configuration.project
                    + "/" + "Experiments/DBMonsterResults/ExecutionTime"
                    + "ExperimentalResults.dat");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // write out the header, which does not need to be written again
        print(dataOutputHeader, headerAsString);

    }

    /**
     * Print out a specified line with a specified PrintWriter
     */
    private static void print(PrintWriter writer, String line) {
        writer.println(line);
        writer.flush();
    }

    /**
     * Print out a specified line with a specified FileWriter
     */
    private static void print(FileWriter writer, String line) {
        try {
            writer.write(line);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // extract all of the database configurations
        Configuration configuration = new Configuration();
        Options options = new Options("ProcesDBMonsterResults [options]", configuration);
        options.parse_or_usage(args);

        // print the debugging information
        if (Configuration.debug) {
            System.out.println(options.settings());
        }

        // print the help screen to see the commands
        if (Configuration.help) {
            options.print_usage();
        }

        // write the header with the attribute names to the file, not appending, but rather
        // writing over the data that current exists in the file from a previous run
        writeHeader();

        // create a file pointing to the directory that contains the DBMonster results
        File directory = new File(Configuration.project + "DBMonsterExperimentData");

        // call the main processing method
        List<String> results = process(directory);
        System.out.println(results);
    }
}