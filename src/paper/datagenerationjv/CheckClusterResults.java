package paper.datagenerationjv;

import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.testgeneration.TestSuite;

import java.io.*;

/**
 * Created by phil on 10/09/2014.
 */
public class CheckClusterResults {

    private static String[] schemaNames = {
            "BankAccount",
            "BookTown",
            "Cloc",
            "CoffeeOrders",
            "CustomerOrder",
            "DellStore",
            "Employee",
            "Examination",
            "Flights",
            "FrenchTowns",
            "Inventory",
            "Iso3166",
            "JWhoisServer",
            "NistDML181",
            "NistDML182",
            "NistDML183",
            "NistWeather",
            "NistXTS748",
            "NistXTS749",
            "Person",
            "Products",
            "RiskIt",
            "StudentResidence",
            "UnixUsage",
            "Usda",
            "BrowserCookies",
            "StackOverflow",
            "ArtistSimilarity",
            "ArtistTerm",
            "MozillaExtensions",
            "MozillaPermissions",
            "iTrust"
    };

    private static String[] dbmsNames = {
            //"HyperSQL",
            "SQLite",
            //"Postgres"
    };

    private static String[] dataGeneratorNames = {
            "random"
    };

    private static String[] coverageNames = {
            //"NCC",
            //"ANCC",
            "UCC",
            //"AUCC"
    };

    public static void makeSubmitScript() {

        LocationsConfiguration locationsConfiguration = new LocationsConfiguration();
        String resultsDir = locationsConfiguration.getResultsDir();

        for (String schemaName : schemaNames) {
            for (String coverageName : coverageNames) {
                for (String dataGeneratorName : dataGeneratorNames) {
                    for (String dbmsName : dbmsNames) {
                        for (int i=1; i <=30; i++) {
                            String fileName = resultsDir + "/" + schemaName + "-" + coverageName + "-" + dataGeneratorName + "-" + dbmsName + "-" + i;

                            File dataFile = new File(fileName + ".txt");
                            File testSuiteFile = new File(fileName + ".testSuite");

                            boolean rerun = false;

                            if (!dataFile.exists() || !testSuiteFile.exists()) {
                                rerun = true;
                            }

                            if (testSuiteFile.exists()) {
                                try {
                                    FileInputStream fis = new FileInputStream(testSuiteFile);
                                    ObjectInputStream in = new ObjectInputStream(fis);
                                    TestSuite testSuite = (TestSuite) in.readObject();
                                    in.close();
                                }
                                catch(IOException | ClassNotFoundException e) {
                                    rerun = true;
                                }
                            }

                            if (rerun) {
                                //System.out.println("qsub -v SCHEMA_NAME="+schemaName+",CRITERION_NAME="+coverageName+",DATA_GENERATOR_NAME="+dataGeneratorName+",DBMS_NAME="+dbmsName+",TRIAL="+i+" -l h_rt='08:00:00' ../sa-expts/postgres-expt.sh");
                                System.out.println("qsub -v SCHEMA_NAME="+schemaName+",CRITERION_NAME="+coverageName+",DATA_GENERATOR_NAME="+dataGeneratorName+",DBMS_NAME="+dbmsName+",TRIAL="+i+" -l h_rt='08:00:00' ../sa-expts/expt.sh");
                                //System.out.println("java -Xmx3G -cp build:lib/* paper.datagenerationjv.RunCoverageExptCluster "+schemaName+" "+coverageName+" "+dataGeneratorName+" "+dbmsName+" "+i);
                                //RunCoverageExptCluster rce = new RunCoverageExptCluster();
                                //rce.runExpt(schemaName,coverageName,dataGeneratorName,dbmsName,i);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void makeSQLStatements() {

        LocationsConfiguration locationsConfiguration = new LocationsConfiguration();
        String resultsDir = locationsConfiguration.getResultsDir();

        for (String schemaName : schemaNames) {
            for (String coverageName : coverageNames) {
                for (String dataGeneratorName : dataGeneratorNames) {
                    for (String dbmsName : dbmsNames) {
                        for (int i=1; i <=30; i++) {
                            String fileName = resultsDir + "/" + schemaName + "-" + coverageName + "-" + dataGeneratorName + "-" + dbmsName + "-" + i;
                            File dataFile = new File(fileName + ".txt");

                            if (dataFile.exists()) {

                                try(BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
                                    StringBuilder sb = new StringBuilder();
                                    String line = br.readLine();

                                    while (line != null) {
                                        sb.append(line);
                                        sb.append(System.lineSeparator());
                                        line = br.readLine();
                                    }
                                    String data = sb.toString().trim();

                                    String sql = "INSERT INTO test_generation_runs VALUES (" + data + ", NULL, NULL);";

                                    System.out.println(sql);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }  else {
                                //System.out.println(dataFile + " is missing");
                                //System.exit(1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        //makeSubmitScript();
        makeSQLStatements();
    }
}
