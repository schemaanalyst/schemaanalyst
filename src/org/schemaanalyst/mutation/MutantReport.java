package org.schemaanalyst.mutation;

import java.util.List;
import java.util.ArrayList;

public class MutantReport {

    /** The starting value for the numerical identifiers */
    private static final int START_NUMERICAL_IDENTIFIER = 1;

    /** The static variable to initialize the numericalIdentifiers */
    private static int nextNumericalIdentifier = START_NUMERICAL_IDENTIFIER;

    /** A numerical identifier for this report */
    private int numericalIdentifier;

    /** The flag which indicates whether or not the mutant was killed */
    private boolean killed;

    /** The flag to indicate whether or not this mutant was still born */
    private boolean stillBorn;

    private boolean intersection;

    /** The description of the mutant that was executed */
    private String description;

    /** The record of the MUTANT CREATE TABLEs for a schema */
    private List<SQLExecutionRecord> createTableStatements;

    /** The record of the MUTANT statements for a MUTANT schema */
    private List<MutantRecord> mutantStatements;

    public MutantReport() {
	createTableStatements = new ArrayList();
	mutantStatements = new ArrayList();
	numericalIdentifier = nextNumericalIdentifier;
	nextNumericalIdentifier++;
	killed=false;
	stillBorn=false;
	intersection=false;
	description="";
    }

    /** Add a CREATE TABLE statement to the list */
    public void addCreateTableStatement(SQLExecutionRecord statement) {
	createTableStatements.add(statement);
    }

    /** Return the list of CREATE TABLE statements */
    public List<SQLExecutionRecord> getCreateTableStatements() {
	return createTableStatements;
    }

    /** Add a MUTANT statement to the list */
    public void addMutantStatement(MutantRecord statement) {
	mutantStatements.add(statement);
    }
    
    /** Return the list of MUTANT statements */
    public List<MutantRecord> getMutantStatements() {
	return mutantStatements;
    }
    
    /** Return the numerical identifier for this mutant report */
    public int getNumericalIdentifier() {
	return numericalIdentifier;
    }

    /** Reinitialize the numerical identifier to the starting value */
    public static void reinitializeNumericalIdentifier() {
	nextNumericalIdentifier = START_NUMERICAL_IDENTIFIER;
    }

    /** Indicate that the mutant was killed */
    public void killMutant() {
	killed = true;
    }

    /** Determine whether or not the mutant was killed */
    public boolean didKillMutant() {
	return killed;
    }    

    /** Indicate that the mutant was born still */
    public void bornStill() {
	stillBorn = true;
    }

    /** Determine whether or not the mutant was born still */
    public boolean wasBornStill() {
	return stillBorn;
    }    

    public void computeIntersection() {
	
	// System.out.println("CI");

	// System.out.println("stillborn = " + stillBorn);
	// System.out.println("killed = " + killed);

	if( stillBorn && killed ) {
	    intersection = true;
	}
	else {
	    intersection = false;
	}

	// System.out.println("== " + intersection);

    }

    public boolean wasIntersection() {
	return intersection;
    }

    /** Set the description for this mutant */
    public void setDescription(String description) {
	this.description = description;
    }

    /** Return the description for this mutant */
    public String getDescription() {
	return description;
    }

    public String toString() {
	return "MUTANT NUMBER: " + numericalIdentifier + "\nKilled? " + killed +
	    "\nStill born? " + stillBorn +
	    "\nDESCRIPTION: " + description +
	    "\nCREATE TABLES: \n" + createTableStatements + 
	    "\nMUTANTS: \n" + MutationUtilities.convertListToString(mutantStatements);
    }

}