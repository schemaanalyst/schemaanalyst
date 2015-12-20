package paper.ineffectivemutants.manualevaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public abstract class ManualAnalysisTestSuite {

    protected static final String BASE_DIR = "src/paper/ineffectivemutants/manualevaluation/mutants/";

    protected static final int SUCCESS = 0;
    protected static final boolean QUIET = false;
    protected static final boolean EXPLAIN_INSERT_FAILURE = false;

    protected static Connection connection;
    protected static Statement statement;

    protected int getFirstMutantNumber() {
        return 1;
    }
    protected abstract int getMutantNumberBeingEvaluated();
    protected abstract int getLastMutantNumber();

    protected abstract String getSchemaName();
    protected abstract String getDBMSName();
    protected abstract void dropTables() throws SQLException;

    protected boolean doInsert(String insertStatement) {
        try {
            statement.executeUpdate(insertStatement);
            return true;
        } catch (SQLException e) {
            if (EXPLAIN_INSERT_FAILURE) {
                e.printStackTrace();
            }
            return false;
        }
    }

    protected boolean insertToMutant(String... insertStatements) throws SQLException {
        createMutantSchema();
        for (String insertStatement : insertStatements) {
            if (!doInsert(insertStatement)) {
                return false;
            }
        }
        return true;
    }

    protected boolean originalAndMutantHaveDifferentBehavior(String... insertStatements) throws SQLException {
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

    protected int mutantAndOtherMutantsHaveDifferentBehavior(String... insertStatements) throws SQLException {
        return mutantAndOtherMutantsHaveDifferentBehavior(
                getFirstMutantNumber(), getLastMutantNumber(), insertStatements);
    }

    protected int mutantAndOtherMutantsHaveDifferentBehaviorFrom(
            int mutantNumber, String... insertStatements) throws SQLException {
        return mutantAndOtherMutantsHaveDifferentBehavior(
                mutantNumber, getLastMutantNumber(), insertStatements);
    }

    protected int mutantAndOtherMutantsHaveDifferentBehaviorTo(
            int mutantNumber, String... insertStatements) throws SQLException {
        return mutantAndOtherMutantsHaveDifferentBehavior(
                getFirstMutantNumber(), mutantNumber, insertStatements);
    }

    protected int mutantAndOtherMutantsHaveDifferentBehavior(
            int mutantNumber, String... insertStatements) throws SQLException {
        return mutantAndOtherMutantsHaveDifferentBehavior(
                mutantNumber, mutantNumber, insertStatements);
    }

    protected int mutantAndOtherMutantsHaveDifferentBehavior(
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

    protected void createOriginalSchema() throws SQLException {
        dropTables();
        loadSchema(0);
    }

    protected void createMutantSchema() throws SQLException {
        dropTables();
        loadSchema(getMutantNumberBeingEvaluated());
    }

    protected void createOtherMutantSchema(int number) throws SQLException {
        dropTables();
        loadSchema(number);
    }

    protected void loadSchema(int number) {
        File file = new File(BASE_DIR + getSchemaName() + "_" + getDBMSName() + "/" + number + ".sql");
        if (!file.exists()) {
            fail("Could not create schema -- file does not exist: " + file);
        }

        String command = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while( (line = reader.readLine() ) != null) {
                if (line.startsWith("--")) {
                    continue;
                }
                if (line.trim().equals("")) {
                    if (!command.equals("")) {
                        statement.executeUpdate(command);
                        command = "";
                    }
                } else {
                    command += line;
                }
            }

        } catch (IOException e) {
            fail("Could not load schema from file: " + file);
            e.printStackTrace();
        } catch (SQLException e) {
            fail("Could not create schema from file: " + file + "\n with SQL " + command);
            e.printStackTrace();
        }
    }
}
