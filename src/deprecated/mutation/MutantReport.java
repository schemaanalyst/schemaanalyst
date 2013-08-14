package deprecated.mutation;

import java.util.List;
import java.util.ArrayList;
import org.schemaanalyst.util.StringUtils;

public class MutantReport {

    /**
     * The starting value for the numerical identifiers
     */
    private static final int START_NUMERICAL_IDENTIFIER = 1;
    /**
     * The static variable to initialize the numericalIdentifiers
     */
    private static int nextNumericalIdentifier = START_NUMERICAL_IDENTIFIER;
    /**
     * A numerical identifier for this report
     */
    private int numericalIdentifier;
    /**
     * The flag which indicates whether or not the mutant was killed
     */
    private boolean killed;
    /**
     * The flag to indicate whether or not this mutant was still born
     */
    private boolean stillBorn;
    /**
     * The flag to indicate whether or not this mutant was still born and killed
     * (intersected)
     */
    private boolean intersected;
    /**
     * The description of the mutant that was executed
     */
    private String description;
    /**
     * The record of the MUTANT CREATE TABLEs for a schema
     */
    private List<SQLExecutionRecord> createTableStatements;
    /**
     * The record of the MUTANT statements for a MUTANT schema
     */
    private List<MutantRecord> mutantStatements;

    public MutantReport() {
        createTableStatements = new ArrayList<>();
        mutantStatements = new ArrayList<>();
        numericalIdentifier = nextNumericalIdentifier;
        nextNumericalIdentifier++;
        killed = false;
        stillBorn = false;
        intersected = false;
        description = "";
    }

    /**
     * Add a CREATE TABLE statement to the list
     */
    public void addCreateTableStatement(SQLExecutionRecord statement) {
        createTableStatements.add(statement);
    }

    /**
     * Return the list of CREATE TABLE statements
     */
    public List<SQLExecutionRecord> getCreateTableStatements() {
        return createTableStatements;
    }

    /**
     * Add a MUTANT statement to the list
     */
    public void addMutantStatement(MutantRecord statement) {
        mutantStatements.add(statement);
    }

    /**
     * Return the list of MUTANT statements
     */
    public List<MutantRecord> getMutantStatements() {
        return mutantStatements;
    }

    /**
     * Return the numerical identifier for this mutant report
     */
    public int getNumericalIdentifier() {
        return numericalIdentifier;
    }

    /**
     * Reinitialize the numerical identifier to the starting value
     */
    public static void reinitializeNumericalIdentifier() {
        nextNumericalIdentifier = START_NUMERICAL_IDENTIFIER;
    }

    /**
     * Indicate whether the mutant was killed
     */
    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    /**
     * Determine whether or not the mutant was killed
     */
    public boolean isKilled() {
        return killed;
    }

    /**
     * Indicate whether the mutant was still born
     */
    public void setStillBorn(boolean stillBorn) {
        this.stillBorn = stillBorn;
    }

    /**
     * Determine whether or not the mutant was still born
     */
    public boolean isStillBorn() {
        return stillBorn;
    }

    /**
     * Determine whether this mutant was stillborn and killed
     */
    public void computeIntersected() {
        intersected = stillBorn && killed;
    }

    /**
     * Determine whether or not the mutant was stillborn and killed
     * (intersected)
     */
    public boolean isIntersected() {
        return intersected;
    }

    /**
     * Set the description for this mutant
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the description for this mutant
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "MUTANT NUMBER: " + numericalIdentifier + "\nKilled? " + killed
                + "\nStill born? " + stillBorn
                + "\nDESCRIPTION: " + description
                + "\nCREATE TABLES: \n" + createTableStatements
                + "\nMUTANTS: \n" + StringUtils.implode(mutantStatements, "\n")
                + "\n";
    }
}