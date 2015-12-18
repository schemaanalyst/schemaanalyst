package paper.ineffectivemutants.manualevaluation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class ManualAnalysisTestSuite {

    protected static final int SUCCESS = 0;
    protected static final boolean QUIET = false;

    protected static Connection connection;
    protected static Statement statement;

    protected int getFirstMutantNumber() {
        return 1;
    }

    protected abstract int getMutantNumberBeingEvaluated();

    protected abstract int getLastMutantNumber();

    public abstract void createOriginalSchema() throws SQLException;

    public abstract void createMutantSchema() throws SQLException;

    public abstract void createOtherMutantSchema(int number) throws SQLException;

    public boolean doInsert(String insertStatement) {
        try {
            statement.executeUpdate(insertStatement);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean insertToMutant(String... insertStatements) throws SQLException {
        createMutantSchema();
        for (String insertStatement : insertStatements) {
            if (!doInsert(insertStatement)) {
                return false;
            }
        }
        return true;
    }

    public boolean originalAndMutantHaveDifferentBehavior(String... insertStatements) throws SQLException {
        List<Boolean> originalSchemaResults = new ArrayList<>();
        List<Boolean> mutantSchemaResults = new ArrayList<>();

        createOriginalSchema();
        for (String insertStatement : insertStatements) {
            originalSchemaResults.add(doInsert(insertStatement));
        }

        createMutantSchema();
        for (String insertStatement : insertStatements) {
            mutantSchemaResults.add(doInsert(insertStatement));
        }

        if (!QUIET) {
            System.out.println("Orig/mutant: " + originalSchemaResults + "/" + mutantSchemaResults);
        }

        for (int i = 0; i < insertStatements.length; i++) {
            if (originalSchemaResults.get(i) != mutantSchemaResults.get(i)) {
                return true;
            }
        }

        return false;
    }

    public int mutantAndOtherMutantsHaveDifferentBehavior(String... insertStatements) throws SQLException {
        return mutantAndOtherMutantsHaveDifferentBehavior(
                getFirstMutantNumber(), getLastMutantNumber(), insertStatements);
    }

    public int mutantAndOtherMutantsHaveDifferentBehaviorToLastFrom(
            int mutantNumber, String... insertStatements) throws SQLException {
        return mutantAndOtherMutantsHaveDifferentBehavior(
                mutantNumber, getLastMutantNumber(), insertStatements);
    }

    public int mutantAndOtherMutantsHaveDifferentBehaviorFromFirstTo(
            int mutantNumber, String... insertStatements) throws SQLException {
        return mutantAndOtherMutantsHaveDifferentBehavior(
                getFirstMutantNumber(), mutantNumber, insertStatements);
    }

    public int mutantAndOtherMutantsHaveDifferentBehavior(
            int mutantNumber, String... insertStatements) throws SQLException {
        return mutantAndOtherMutantsHaveDifferentBehavior(
                mutantNumber, mutantNumber, insertStatements);
    }

    public int mutantAndOtherMutantsHaveDifferentBehavior(
            int firstMutantNumber, int lastMutantNumber, String... insertStatements) throws SQLException {
        List<Boolean> mutantSchemaResults = new ArrayList<>();
        createMutantSchema();
        for (String insertStatement : insertStatements) {
            mutantSchemaResults.add(doInsert(insertStatement));
        }

        for (int mutantNumber = firstMutantNumber; mutantNumber <= lastMutantNumber; mutantNumber++) {
            if (mutantNumber == getMutantNumberBeingEvaluated()) {
                continue;
            }

            List<Boolean> otherMutantSchemaResults = new ArrayList<>();
            createOtherMutantSchema(mutantNumber);

            for (String insertStatement : insertStatements) {
                otherMutantSchemaResults.add(doInsert(insertStatement));
            }

            if (!QUIET) {
                System.out.println("Mutant/mutant" + mutantNumber + ": " +
                        mutantSchemaResults + "/" + otherMutantSchemaResults);
            }

            boolean different = false;
            for (int j = 0; j < insertStatements.length; j++) {
                if (mutantSchemaResults.get(j) != otherMutantSchemaResults.get(j)) {
                    different = true;
                }
            }

            if (!different) return mutantNumber;
        }

        return SUCCESS;
    }
}
