package experiment;

import org.schemaanalyst.util.xml.XMLSerialiser;
import java.util.List;
import java.util.ArrayList;
import java.io.File;


public class LocalExperimentParameters extends Parameters {

    private static final String DEFAULT_LOCATION =
            "config" + File.separator + "localexperimentparameters.xml";
    private ArrayList<String> datagenerators;
    private ArrayList<String> databases;
    private ArrayList<String> satisfyrows;
    private ArrayList<String> negaterows;
    private ArrayList<String> maxevaluations;
    private ArrayList<String> types;
    private ArrayList<String> naiverandomrowspertables;
    private ArrayList<String> naiverandommaxtriespertables;
    private int trials;

    /**
     * Default constructor.
     */
    public LocalExperimentParameters() {
        datagenerators = new ArrayList<>();
        databases = new ArrayList<>();
        satisfyrows = new ArrayList<>();
        negaterows = new ArrayList<>();
        maxevaluations = new ArrayList<>();
        types = new ArrayList<>();
        naiverandomrowspertables = new ArrayList<>();
        naiverandommaxtriespertables = new ArrayList<>();
    }

    /**
     * Set the default values.
     */
    @Override
    public void setDefaultParameters() {
        // data generators
        datagenerators.add("--datagenerator=alternatingvalue_defaults");
        datagenerators.add("--datagenerator=alternatingvalue");
        datagenerators.add("--datagenerator=random");
        datagenerators.add("--datagenerator=naiverandom");

        // case studies
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

    /**
     * Return the data generators
     */
    public List<String> getDatagenerators() {
        return datagenerators;
    }

    /**
     * Return the databases
     */
    public List<String> getDatabases() {
        return databases;
    }

    /**
     * Return the types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * Return the satisfyrows
     */
    public List<String> getSatisfyrows() {
        return satisfyrows;
    }

    /**
     * Return the negaterows
     */
    public List<String> getNegaterows() {
        return negaterows;
    }

    /**
     * Return the maximum number of evaluations
     */
    public List<String> getMaxevaluations() {
        return maxevaluations;
    }

    /**
     * Return the naive random rows per tables
     */
    public List<String> getNaiverandomrowspertables() {
        return naiverandomrowspertables;
    }

    /**
     * Return the naive random max tries per tables
     */
    public List<String> getNaiverandommaxtriespertables() {
        return naiverandommaxtriespertables;
    }

    /**
     * Return the number of trials for running the experiments
     */
    public int getTrials() {
        return trials;
    }

    /**
     * Save all of the parameters to a file, in a pretty printed XML format.
     *
     * @param localparameters The parameters object.
     * @param location The file location.
     */
    public static void saveToXml(LocalExperimentParameters localparameters, String location) {
        XMLSerialiser.save(localparameters, location);
    }

    /**
     * Save all of the parameters to a file, in a pretty printed XML format. The
     * file location is determined using the DEFAULT_LOCATION variable.
     *
     * @param localparameters The parameters object.
     */
    public static void saveToXml(LocalExperimentParameters localparameters) {
        XMLSerialiser.save(localparameters, DEFAULT_LOCATION);
    }

    /**
     * Load a parameters object from a file, formatted in XML.
     *
     * @param location The file path.
     * @return The parameters object.
     */
    public static LocalExperimentParameters loadFromXML(String location) {
        return XMLSerialiser.<LocalExperimentParameters>load(location);
    }

    /**
     * Load a parameters object from a file, formatted in XML.
     *
     * @return The parameters object.
     */
    public static LocalExperimentParameters loadFromXML() {
        return XMLSerialiser.<LocalExperimentParameters>load(DEFAULT_LOCATION);
    }

    public static void main(String[] args) {
        LocalExperimentParameters parameters = new LocalExperimentParameters();
        parameters.setDefaultParameters();
        LocalExperimentParameters.saveToXml(parameters);
    }
}