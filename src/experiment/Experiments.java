package experiment;

import plume.*;

import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.FileParameters;

import experiment.RunExperiment;
import experiment.GlobalExperimentParameters;
import experiment.ExperimentConfiguration;
import experiment.ExperimentalResults;
import experiment.mutation2013.GenerateData;
import experiment.mutation2013.MutationAnalysis;

import org.schemaanalyst.SchemaAnalyst;

@RunWith(JUnitParamsRunner.class)
public class Experiments {

    // define a convenience method for manually specifying the parameters -- only needed for manual specification and testing
    public static Object[] $(Object... params) {return params;}

    // the required method for supplying the parameters to the experimentation framework
    private Object[] parametersForExperiments() {
	// IMPORTANT NOTE: The below three lines of code are only executed one time at the start of experiment execution

	// set the project in the ExperimentConfiguration from the property given by the JVM argument
	ExperimentConfiguration.project = System.getProperty("project");

	// create the ExperimentalResults for the first time and save them to the file system
	ExperimentalResults experimentalResults = new ExperimentalResults();
	experimentalResults.save();

	// load the local experiments from the file system and give them to JUnitParams in the required format
	return ExperimentProvider.getLocalExperimentParameters();
    }
	
    @Test 
    @Parameters
    public void experiments(String datagenerator, String database, String satisfyrows, String negaterows,
			    String maxevaluations, String type, String naiverandomrowspertable, 
			    String naiverandommaxtriespertable, String trial) {
	// debugging information about the configuration of the experiment
	// System.out.println("Data Generator: " + datagenerator);
	// System.out.println("Database: " + database);
	// System.out.println("Satisfy rows: " + satisfyrows);
	// System.out.println("Negate rows: " + negaterows);
	// System.out.println("Max evaluations: " + maxevaluations);
	// System.out.println("Type: " + type);
	// System.out.println("Naiverandomrowspertable: " + naiverandomrowspertable);
	// System.out.println("Naiverandommaxtriespertable: " + naiverandommaxtriespertable);
	// System.out.println("Trial: " + trial);

	// extract all of the database configurations
	ExperimentConfiguration configuration = new ExperimentConfiguration();
	configuration.project = System.getProperty("project");

	// the final list of parameters (global plus local)
	ArrayList<String> parameters = new ArrayList<String>();

	// local parameters that change on a per-experiment basis
	ArrayList<String> localParameters = new ArrayList<String>();
	localParameters.add(datagenerator);
	localParameters.add(database);
	localParameters.add(satisfyrows);
	localParameters.add(negaterows);
	localParameters.add(maxevaluations);
	localParameters.add(naiverandomrowspertable);
	localParameters.add(naiverandommaxtriespertable);
	localParameters.add(type);
	localParameters.add(ExperimentUtilities.convertTrialToParameter(trial));
	
	// global parameters that only change for a person running the experiments
	List<String> globalParameters = ExperimentProvider.getGlobalExperimentParameters();
	
	// add the local and global parameters together
	parameters.addAll(localParameters);
	parameters.addAll(globalParameters);

        // Parse additional runtime parameters
        boolean mutation2013_datageneration = false;
        boolean mutation2013_execution = false;
        for (String global : globalParameters) {
            if (global.startsWith("--mutation2013_datageneration=")) {
                mutation2013_datageneration = Boolean.parseBoolean(global.replace("--mutation2013_datageneration=", ""));
            } else if (global.startsWith("--mutation2013_execution=")) {
                mutation2013_execution = Boolean.parseBoolean(global.replace("--mutation2013_execution=", ""));
            }
        }
        
	// run the specified experiment (called repeatedly based on the full list of parameters)
        Class targetClass;
        if (mutation2013_datageneration) {
            targetClass = GenerateData.class;
        } else if (mutation2013_execution) {
            targetClass = MutationAnalysis.class;
        } else {
            targetClass = SchemaAnalyst.class;
        }
        
        RunExperiment.runExperimentInSeparateJavaVirtualMachine(targetClass, parameters);

    }
}

