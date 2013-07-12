package experiment;

import experiment.util.XMLSerialiser;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

import org.schemaanalyst.configuration.FolderConfiguration;

public class GlobalExperimentParameters extends Parameters {

    private static final String DEFAULT_LOCATION =
            FolderConfiguration.config_dir + File.separator + "globalexperimentparameters.xml";
    private ArrayList<String> parameters;

    /**
     * Default constructor.
     */
    public GlobalExperimentParameters() {
        parameters = new ArrayList<>();
    }

    /**
     * Set the default values.
     */
    @Override
    public void setDefaultParameters() {
        parameters.add("--standalone=false");
        parameters.add("--debug=false");
        parameters.add("--foreignkeys=true");
        parameters.add("--script=false");
        parameters.add("--wantmutationreport_mrp=false");
        parameters.add("--wantmutationreport_txt=false");
        parameters.add("--onlymutationsummary=false");
        parameters.add("--project=" + System.getProperty("user.dir") + "/");
        parameters.add("--host=localhost");
        parameters.add("--port=5432");
        parameters.add("--spy=false");
        parameters.add("--mutation2013_datageneration=false");
        parameters.add("--mutation2013_execution=false");
    }

    /**
     * Return the parameters.
     *
     * @return The parameters.
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Save all of the parameters to a file, in a pretty printed XML format.
     *
     * @param globalparameters The parameters object.
     * @param location The file location.
     */
    public static void saveToXml(GlobalExperimentParameters globalparameters, String location) {
        XMLSerialiser.save(globalparameters, location);
    }

    /**
     * Save all of the parameters to a file, in a pretty printed XML format. The
     * file location is determined using the DEFAULT_LOCATION variable.
     *
     * @param globalparameters The parameters object.
     */
    public static void saveToXml(GlobalExperimentParameters globalparameters) {
        XMLSerialiser.save(globalparameters, DEFAULT_LOCATION);
    }

    /**
     * Load a parameters object from a file, formatted in XML.
     *
     * @param location The file path.
     * @return The parameters object.
     */
    public static GlobalExperimentParameters loadFromXML(String location) {
        return XMLSerialiser.<GlobalExperimentParameters>load(location);
    }

    /**
     * Load a parameters object from a file, formatted in XML.
     *
     * @return The parameters object.
     */
    public static GlobalExperimentParameters loadFromXML() {
        return XMLSerialiser.<GlobalExperimentParameters>load(DEFAULT_LOCATION);
    }

    /**
     * Write out the default parameters to the file system
     */
    public static void main(String[] args) {
        GlobalExperimentParameters parameters = new GlobalExperimentParameters();
        parameters.setDefaultParameters();
        GlobalExperimentParameters.saveToXml(parameters);
    }
}