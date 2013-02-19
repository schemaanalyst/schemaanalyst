package experiment;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Calendar;

import experiment.GlobalExperimentParameters;
import experiment.LocalExperimentParameters;

public class ExperimentProvider {

    /** Load the global parameters from the file system and return to the JUnitParams-driven experimentation framework */
    public static List<String> getGlobalExperimentParameters() {
	// load the global experiment parameters from the file system
	GlobalExperimentParameters globalParametersContainer = GlobalExperimentParameters.retrieve();
	
	// extract the list of the parameters and return them for inclusion in the main global listing
	List<String> globalParameters = globalParametersContainer.getParameters();
	return globalParameters;
    }
    
    /** Load the local parameters from the file system and return to the JUnitParams-driven experimentation framework */
    public static Object[] getLocalExperimentParameters() {
	// extract all of the database configurations
	ExperimentConfiguration configuration = new ExperimentConfiguration();
	configuration.project = System.getProperty("project");

	// load the parameters from the file system
	LocalExperimentParameters localExperimentParameters = LocalExperimentParameters.retrieve();
	List<String> datagenerators = localExperimentParameters.getDatagenerators();
	List<String> databases = localExperimentParameters.getDatabases();
	List<String> satisfyrowss = localExperimentParameters.getSatisfyrows();
	List<String> negaterowss = localExperimentParameters.getNegaterows();
	List<String> maxevaluationss = localExperimentParameters.getMaxevaluations();
	List<String> types = localExperimentParameters.getTypes();
	List<String> naiverandomrowspertables = localExperimentParameters.getNaiverandomrowspertables();
	List<String> naiverandommaxtriespertables = localExperimentParameters.getNaiverandommaxtriespertables();
	int trials = localExperimentParameters.getTrials();

	// debugging information about the experiment parameters
	System.out.println("datagenerators: " + datagenerators);
	System.out.println("databases: " + databases);
	System.out.println("satisfyrowss: " + satisfyrowss);	
	System.out.println("negaterowss: " + negaterowss);	
	System.out.println("maxevaluations: " + maxevaluationss);	
	System.out.println("types: " + types);	
	System.out.println("naiverandomrowspertables: " + naiverandomrowspertables);
	System.out.println("naiverandommaxtriespertable: " + naiverandommaxtriespertables);
	System.out.println("trials: " + trials);

	// declare the allParameters that is in the format required by JUnitParams
	ArrayList<Object[]> allParameters = new ArrayList<Object[]>();

	// declare the currentParameterList which is like a "row" in the table of parameters
	ArrayList<String> currentParameterList = new ArrayList<String>();

	// NOTE: of necessity, this code is hard-coded for the local parameters that we used in the experiments; 
	// there is no way to avoid this given the way that JUnitParams passes the parameters to the test methods
	
	// NOTE: this code will need to be changed whenever the number and/or type of local parameters changes 

	// ALGORITHMS iteration
	for(String datagenerator : datagenerators) {	
	    // add DATAGENERATOR to the "row" in the table
	    currentParameterList.add(datagenerator); 
	    // DATABASES iteration
	    for(String database : databases) {
		// add DATABASE to the "row" in the table
		currentParameterList.add(database); 
		// SATISFYROWSS iteration
		for(String satisfyrows : satisfyrowss) {		
		    // add SATISFYROW to the "row" in the table
		    currentParameterList.add(satisfyrows); 
		    // NEGATEROWS iteration
		    for(String negaterows : negaterowss) {
			// add NEGATEROWS to the "row" in the table
			currentParameterList.add(negaterows); 
			// MAXEVLAUATIONSS iteration
			for(String maxevaluations : maxevaluationss) {
			    // add MAXEVALUATIONS to the "row" in the table
			    currentParameterList.add(maxevaluations); 
			    // TYPES iteration
			    for(String type : types) {
				// add TYPE to the "row" in the table
				currentParameterList.add(type);
				// NAIVERANDOMROWSPERTABLE iteration
				for(String naiverandomrowspertable : naiverandomrowspertables) {
				    // add the NAIVERANDOMROWSPERTABLE to the "row" in the table
				    currentParameterList.add(naiverandomrowspertable);
				    // NAIVERANDOMMAXTRIESPERTABLE iteration
				    for(String naiverandommaxtriespertable : naiverandommaxtriespertables) {
					// add the NAIVERANDOMMAXTRIESPERTABLE to the "row" in the table
					currentParameterList.add(naiverandommaxtriespertable);
					// TRIALS iteration 
					for(int currentTrial = 1; currentTrial <= trials; currentTrial++ ) {
					    // add TRIAL to the "row" in the table
					    String currentTrialString = Integer.toString(currentTrial); 
					    currentParameterList.add(currentTrialString); 				    

					    // FINISHED enumerating everything for a row; innermost location for local parameters
					    // add this new row -- AS AN ARRAY -- to the listing of the parameters
					    allParameters.add(currentParameterList.toArray());

					    // REMOVE the TRIAL that is now not needed
					    currentParameterList.remove(currentTrialString);			
					}
					
					// REMOVE the NAIVERANDOMMAXTRIESPERTABLE that is now not needed
					currentParameterList.remove(naiverandommaxtriespertable);
				    }
				    
				    // REMOVE the NAIVERANDOMROWSPERTABLE that is now not needed
				    currentParameterList.remove(naiverandomrowspertable);
				}

				// REMOVE the TYPE that is now not needed
				currentParameterList.remove(type);
			    }

			    // REMOVE the MAXEVALUATIONS that is now not needed
			    currentParameterList.remove(maxevaluations);
			}

			// REMOVE the NEGATEROWS that is now not needed
			currentParameterList.remove(negaterows);
		    }

		    // REMOVE the SATISFYROWS that is now not needed
		    currentParameterList.remove(satisfyrows);
		}
		// REMOVE the DATABASE that is now not needed
		currentParameterList.remove(database);
	    }		    
	    // RE-CREATE the parameter listing
	    currentParameterList = new ArrayList<String>();
	}
	
	// debugging information and then return the local experiment parameters
	System.out.println("ExperimentProvider -- all of the parameters: " + allParameters.toArray());
	return allParameters.toArray();
    }
}