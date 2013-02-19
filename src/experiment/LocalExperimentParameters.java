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

public class LocalExperimentParameters implements Parameters {

    /**
       ORIGINAL PARAMETERS THAT I FIRST USED DURING TESTING AND PRELIMINARY EXPERIMENTS:
 
      <arg value="--datagenerator=alternatingvalue_defaults" /> -- DONE 
      <arg value="--satisfyrows=2" />  -- DONE
      <arg value="--negaterows=1" />  -- DONE
      <arg value="--maxevaluations=100000" />  -- DONE
      <arg value="--type=org.schemaanalyst.database.postgres.Postgres" /> -- DONE
      <arg value="--database=casestudy.BankAccount" /> -- DONE
      <arg value="--scriptfile=casestudy.BankAccount.postgres.sql" />
      <arg value="--mutantscriptfile=casestudy.BankAccount.mutant.sql" />
      <arg value="--mutationreport_mrp=casestudy.BankAccount.postgres.mrp" />
      <arg value="--mutationreport_txt=casestudy.BankAccount.postgres.txt" />
    */

    /** 
      ADDITIONAL PARAMETERS THAT I LATER ADDED TO CONTROL OTHER ALGORITHMS:

      <arg value="--naiverandom_rowspertable=50" />
      <arg value="--naiverandom_maxtriespertable=1000" />
     */

    /** All of the parameters that must be directly specified */
    /** The order specified here matches the order in Experiments.experiments */
    private ArrayList<String> datagenerators;
    private ArrayList<String> databases;    
    private ArrayList<String> satisfyrows;
    private ArrayList<String> negaterows;
    private ArrayList<String> maxevaluations;
    private ArrayList<String> types;
    private ArrayList<String> scriptfiles;
    private ArrayList<String> naiverandomrowspertables;
    private ArrayList<String> naiverandommaxtriespertables;
    private int trials;
    
    /** The PrintWriter that can save the report on the file system */
    private static PrintWriter reportOutputSer;

    public LocalExperimentParameters() {
	datagenerators = new ArrayList<String>();
	databases = new ArrayList<String>();
	satisfyrows = new ArrayList<String>();
	negaterows = new ArrayList<String>();
	maxevaluations = new ArrayList<String>();
	types = new ArrayList<String>();
	naiverandomrowspertables = new ArrayList<String>();
	naiverandommaxtriespertables = new ArrayList<String>();
    }

    /** Set the default parameters for the local experiments */
    public void setDefaultParameters() {
	// add all of the data generators
	datagenerators.add("--datagenerator=alternatingvalue_defaults");
	datagenerators.add("--datagenerator=alternatingvalue");
	datagenerators.add("--datagenerator=random");
	datagenerators.add("--datagenerator=naiverandom");

	// add the case study applications 
// -rw-rw-r-- 1 gkapfham  2283 Aug 23 00:01 BankAccount.java
// -rw-rw-r-- 1 gkapfham 15424 Aug 23 00:01 BookTown.java
// -rw-rw-r-- 1 gkapfham   816 Jul 16 10:02 BooleanExample.java
// -rw-rw-r-- 1 gkapfham  1508 Aug  8 10:30 Cloc.java
// -rw-rw-r-- 1 gkapfham  4359 Aug  8 10:30 CoffeeOrders.java
// -rw-rw-r-- 1 gkapfham  9200 Aug  9 07:16 CustomerOrder.java
// -rw-rw-r-- 1 gkapfham  9329 Aug  9 07:16 DellStore.java
// -rw-rw-r-- 1 gkapfham  1673 Aug  8 10:30 Employee.java
// -rw-rw-r-- 1 gkapfham  5013 Aug  9 07:16 Examination.java
// -rw-rw-r-- 1 gkapfham  3269 Sep  6 13:23 Flights.java
// -rw-rw-r-- 1 gkapfham  3285 Aug  9 07:16 FrenchTowns.java
// -rw-rw-r-- 1 gkapfham  1118 Jul 16 10:02 Inventory.java
// -rw-rw-r-- 1 gkapfham   952 Jul 16 10:02 Iso3166.java
// -rw-rw-r-- 1 gkapfham 74721 Aug  7 11:04 ITrust.java
// -rw-rw-r-- 1 gkapfham  9035 Jul 16 10:02 JWhoisServer.java
// -rw-rw-r-- 1 gkapfham  1823 Aug  9 07:16 NistDML181.java
// -rw-rw-r-- 1 gkapfham  1955 Aug  9 07:16 NistDML181NotNulls.java
// -rw-rw-r-- 1 gkapfham  4827 Jul 16 10:02 NistDML182.java
// -rw-rw-r-- 1 gkapfham  5744 Jul 16 10:02 NistDML182NotNulls.java
// -rw-rw-r-- 1 gkapfham  1291 Aug  8 10:30 NistDML183Ints.java
// -rw-rw-r-- 1 gkapfham  1514 Aug  9 07:16 NistDML183IntsNotNulls.java
// -rw-rw-r-- 1 gkapfham  1253 Aug  8 10:30 NistDML183.java
// -rw-rw-r-- 1 gkapfham  1428 Aug  9 07:16 NistDML183NotNulls.java
// -rw-rw-r-- 1 gkapfham  2641 Aug  9 07:16 NistWeather.java
// -rw-rw-r-- 1 gkapfham  1057 Jul 31 05:50 NistXTS748.java
// -rw-rw-r-- 1 gkapfham  2056 Jul 31 05:50 NistXTS749.java
// -rw-rw-r-- 1 gkapfham 35919 Aug  8 10:30 Pagila.java
// -rw-rw-r-- 1 gkapfham 37784 Aug 23 00:01 PagilaPrime.java
// -rw-rw-r-- 1 gkapfham  1495 Aug  8 10:30 Person.java
// -rw-rw-r-- 1 gkapfham  2954 Jul 31 05:50 Products.java
// -rw-rw-r-- 1 gkapfham 10540 Aug  9 07:16 RiskIt.java
// drwxrwxr-x 2 gkapfham  4096 Sep  6 09:52 runner
// -rw-rw-r-- 1 gkapfham  2282 Aug  8 10:30 StudentResidence.java
// drwxrwxr-x 2 gkapfham  4096 Jul 16 10:02 test
// -rw-rw-r-- 1 gkapfham  6994 Aug  9 07:16 UnixUsage.java
// -rw-rw-r-- 1 gkapfham  9947 Aug  8 10:30 Usda.java
// -rw-rw-r-- 1 gkapfham  5288 Aug  9 07:16 World.java

	databases.add("--database=casestudy.BankAccount");
	databases.add("--database=casestudy.BookTown");
	databases.add("--database=casestudy.BooleanExample");
	databases.add("--database=casestudy.Cloc");
	databases.add("--database=casestudy.CoffeeOrders");
	databases.add("--database=casestudy.CustomerOrder");
	databases.add("--database=casestudy.DellStore");
	databases.add("--database=casestudy.Employee");
	databases.add("--database=casestudy.Examination");
	databases.add("--database=casestudy.Flights");
	databases.add("--database=casestudy.FrenchTowns");
	databases.add("--database=casestudy.Inventory");
	databases.add("--database=casestudy.Iso3166");
	databases.add("--database=casestudy.ITrust");
	databases.add("--database=casestudy.JWhoisServer");
	databases.add("--database=casestudy.NistDML181");
	databases.add("--database=casestudy.NistDML182");
	databases.add("--database=casestudy.NistDML183");
	databases.add("--database=casestudy.NistWeather");
	databases.add("--database=casestudy.NistXTS748");
	databases.add("--database=casestudy.NistXTS749");
	databases.add("--database=casestudy.Person");
	databases.add("--database=casestudy.Products");
	databases.add("--database=casestudy.RiskIt");
	databases.add("--database=casestudy.StudentResidence");
	databases.add("--database=casestudy.UnixUsage");
	databases.add("--database=casestudy.Usda");
	databases.add("--database=casestudy.World");

	// add the satisfyrowss
	satisfyrows.add("--satisfyrows=2");

	// add the negaterowss
	negaterows.add("--negaterows=1");

	// add the maximum number of evaluations
	maxevaluations.add("--maxevaluations=100000");

	// add the types of the database
	types.add("--type=org.schemaanalyst.database.postgres.Postgres");
	types.add("--type=org.schemaanalyst.database.sqlite.SQLite");
	types.add("--type=org.schemaanalyst.database.hsqldb.Hsqldb");

	// add the naiverandom rows per table
	naiverandomrowspertables.add("--naiverandom_rowspertable=10");
	
	// add the naiverandom maxtries per table
	naiverandommaxtriespertables.add("--naiverandom_maxtriespertable=1000");

	// add the number of trials for running the experiment
	trials = 10;

    }

    /** Return the data generators */
    public List<String> getDatagenerators() {
	return datagenerators;
    }

    /** Return the databases */
    public List<String> getDatabases() {
	return databases;
    }

    /** Return the types */
    public List<String> getTypes() {
	return types;
    }

    /** Return the scriptfiles */
    public List<String> getScriptfiles() {
	return scriptfiles;
    }
    
    /** Return the satisfyrows */
    public List<String> getSatisfyrows() {
	return satisfyrows;
    }

    /** Return the negaterows */
    public List<String> getNegaterows() {
	return negaterows;
    }    

    /** Return the maximum number of evaluations */
    public List<String> getMaxevaluations() {
	return maxevaluations;
    }

    /** Return the naive random rows per tables */
    public List<String> getNaiverandomrowspertables() {
	return naiverandomrowspertables;
    }

    /** Return the naive random max tries per tables */
    public List<String> getNaiverandommaxtriespertables() {
	return naiverandommaxtriespertables;
    }

    /** Return the number of trials for running the experiments */
    public int getTrials() {
	return trials;
    }    

    /** Save all of the parameters to a file, for later editing and usage */
    public static void save(LocalExperimentParameters localparameters) {
	try {
	    // configure the output serializer correctly
	    reportOutputSer = new PrintWriter(ExperimentConfiguration.project +
					      "/" + "Experiments/" +
					      "LocalExperimentParameters.xml");
	} 
	
	catch(FileNotFoundException e) {
	    e.printStackTrace();
	}

	// create the XStream object and then serialize the file
	XStream xstream = new XStream(new StaxDriver());
	String xml = xstream.toXML(localparameters);
	String formattedXml = FormatExperimentParameters.format(xml);
	print(reportOutputSer, formattedXml);
    }

    /** Retrieve the parameters from the file system */
    public static LocalExperimentParameters retrieve() {
	LocalExperimentParameters restoredParameters = null;
	try {
	    // create the file that stores the XML
	    File parameters = new File(ExperimentConfiguration.project +
				       "/" + "Experiments/" +
				       "LocalExperimentParameters.xml");
	    
	    // read in the contents of the file to a string
	    String xml = new Scanner(parameters).useDelimiter("\\A").next();

	    // reload the parameters object and return it
	    XStream xstream = new XStream(new StaxDriver());
	    restoredParameters = (LocalExperimentParameters)xstream.fromXML(xml);
	}
	
	catch(FileNotFoundException e) {
	    e.printStackTrace();
	}
	return restoredParameters;
    }

    /** Return a string representation of the parameters */
    public String toString() {
	return "";
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
	Options options = new Options("LocalExperimentParameters [options]", configuration);
	options.parse_or_usage(args);

	LocalExperimentParameters parameters = new LocalExperimentParameters();
	parameters.setDefaultParameters();
	LocalExperimentParameters.save(parameters);
    }
}