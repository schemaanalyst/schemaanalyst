package experiment;

import plume.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver; 

import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import experiment.Parameters;
import experiment.FormatExperimentParameters;

public class GlobalExperimentParameters implements Parameters {

    /** 
      <arg value="--debug=false" /> 
      <arg value="--foreignkeys=true" /> 
      <arg value="--script=true" /> 
      <arg value="--wantmutationreport_mrp=false" /> 
      <arg value="--wantmutationreport_txt=true" /> 
      <arg value="--onlymutationsummary=false" /> 
      <arg value="--project=/home/gkapfham/working/research/projects/dbatdg/" />
      <arg value="--host=localhost" />
      <arg value="--port=5432" />
    */
    private ArrayList<String> parameters;

    /** The PrintWriter that can save the report on the file system */
    private static PrintWriter reportOutputSer;

    public GlobalExperimentParameters() {
	parameters = new ArrayList<String>();
    }

    /** Set the default parameters for the global experiment parameters */
    public void setDefaultParameters() {
	parameters.add("--standalone=false");
	parameters.add("--debug=false");
	parameters.add("--foreignkeys=true");
	parameters.add("--script=false");
	parameters.add("--wantmutationreport_mrp=false");
	parameters.add("--wantmutationreport_txt=false");
	parameters.add("--onlymutationsummary=false");
	parameters.add("--project="+System.getProperty("user.dir")+"/");
	parameters.add("--host=localhost");
	parameters.add("--port=5432");
	parameters.add("--spy=false");
        parameters.add("--mutations2013_datageneration=false");
        parameters.add("--mutation2013_execution=false");
    }

    /** Return all of the global parameters */
    public List<String> getParameters() {
	return parameters;
    }

    /** Save all of the parameters to a file, for later editing and usage */
    public static void save(GlobalExperimentParameters globalparameters) {
	try {
	    // configure the output serializer correctly
	    reportOutputSer = new PrintWriter(ExperimentConfiguration.project +
					      "/" + "Experiments/" +
					      "GlobalExperimentParameters.xml");
	}
	
	catch(FileNotFoundException e) {
	    e.printStackTrace();
	}

	// create the XStream object and then serialize the file
	XStream xstream = new XStream(new StaxDriver());
	String xml = xstream.toXML(globalparameters);
	String formattedXml = FormatExperimentParameters.format(xml);
	print(reportOutputSer, formattedXml);
    }

    /** Retrieve the parameters from the file system */
    public static GlobalExperimentParameters retrieve() {
	GlobalExperimentParameters restoredParameters = null;
	try {
	    // create the file that stores the XML
	    File parameters = new File(ExperimentConfiguration.project +
				       "/" + "Experiments/" +
				       "GlobalExperimentParameters.xml");
	    
	    // read in the contents of the file to a string
	    String xml = new Scanner(parameters).useDelimiter("\\A").next();

	    // reload the parameters object and return it
	    XStream xstream = new XStream(new StaxDriver());
	    restoredParameters = (GlobalExperimentParameters)xstream.fromXML(xml);
	}
	
	catch(FileNotFoundException e) {
	    e.printStackTrace();
	}
	return restoredParameters;
    }

    /** Return a string representation of the parameters */
    public String toString() {
	return Arrays.toString(parameters.toArray(new String[parameters.size()]));
    }

    /** Print out a specified line with a specified PrintWriter */
    private static void print(PrintWriter writer, String line) {
	writer.println(line);
	writer.flush();
    }

    /** Write out the default parameters to the file system*/
    public static void main(String[] args) {
	// extract all of the database configurations
	ExperimentConfiguration configuration = new ExperimentConfiguration();
	Options options = new Options("GlobalExperimentParameters [options]", configuration);
	options.parse_or_usage(args);

	GlobalExperimentParameters parameters = new GlobalExperimentParameters();
	parameters.setDefaultParameters();
	GlobalExperimentParameters.save(parameters);
    }
}