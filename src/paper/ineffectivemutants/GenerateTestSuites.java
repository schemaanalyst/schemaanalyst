package paper.ineffectivemutants;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.hypersql.HyperSQLDBMS;
import org.schemaanalyst.dbms.postgres.PostgresDBMS;
import org.schemaanalyst.dbms.sqlite.SQLiteDBMS;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.IndentableStringBuilder;
import org.schemaanalyst.util.random.SimpleRandom;
import parsedcasestudy.NistWeather;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 17/12/2015.
 */
public class GenerateTestSuites {

    public static final String BASE_DIR_NAME =
            new LocationsConfiguration().getSrcDir() + "/paper/ineffectivemutants/manualevaluation/";
    public static final String MUTATION_PIPELINE = "AllOperatorsWithImpaired";

    public static void main(String[] args) {
        new GenerateTestSuites();
    }

    public GenerateTestSuites() {
        generateTestSuite(new NistWeather(), "HyperSQL", 7);
        generateTestSuite(new NistWeather(), "HyperSQL", 8);
    }

    private void generateTestSuite() {
        // Select a DBMS / SQL writer
        List<String> dbmses = DBMSFactory.getDBMSChoices();
        String dbmsName = dbmses.get(randomIndex(dbmses));
        DBMS dbms = DBMSFactory.instantiate(dbmsName);

        // Select a schema
        Schema schema = Schemas.schemas[randomIndex(Schemas.schemas)];

        // Get mutants
        List<Mutant<Schema>> mutants = generateMutants(schema, dbmsName);

        // Select a mutant at random
        int mutantIndex = randomIndex(mutants);
        Mutant<Schema> selectedMutant = mutants.get(mutantIndex);
        int mutantNumber = mutantIndex + 1;
        writeTestSuiteAndSchemas(dbms, schema, mutants, selectedMutant, mutantNumber);
    }

    private void generateTestSuite(Schema schema, String dbmsName, int mutantNumber) {
        DBMS dbms = DBMSFactory.instantiate(dbmsName);
        List<Mutant<Schema>> mutants = generateMutants(schema, dbmsName);
        Mutant<Schema> selectedMutant = mutants.get(mutantNumber - 1);
        writeTestSuiteAndSchemas(dbms, schema, mutants, selectedMutant, mutantNumber);
    }

    private List<Mutant<Schema>> generateMutants(Schema schema, String dbms) {
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.instantiate(MUTATION_PIPELINE, schema, dbms);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        return pipeline.mutate();
    }

    private int randomIndex(Object[] array) {
        return randomInt(array.length);
    }

    private int randomIndex(List<?> list) {
        return randomInt(list.size());
    }

    private int randomInt(int max) {
        return new SimpleRandom(System.currentTimeMillis()).nextInt(max);
    }

    private boolean writeTestSuiteAndSchemas(
            DBMS dbms, Schema schema, List<Mutant<Schema>> mutants, Mutant<Schema> selectedMutant, int mutantNumber) {
        SQLWriter sqlWriter = dbms.getSQLWriter();
        IndentableStringBuilder code = new IndentableStringBuilder();

        String packageName = "paper.ineffectivemutants.manualevaluation";
        String className = schema.getName() + "_" + dbms.getName() + "_" + mutantNumber;

        String toDoFileName = BASE_DIR_NAME + className + ".java";
        File file = new File(toDoFileName);

        if (file.exists()) {
            System.out.println(file + " exists -- delete it to regenerate it");
            return false;
        } else {
            String[] suffixes = {"NORMAL", "EQUIVALENT", "REDUNDANT", "IMPAIRED"};
            for (String suffix : suffixes) {
                File completeFile = new File(BASE_DIR_NAME + "classified/" + className + "_" + suffix + ".java");
                if (completeFile.exists()) {
                    System.out.println(className + " is COMPLETE! (Classification: " + suffix +
                            "). Delete it if you really want to regenerate it)");
                    return false;
                }
            }
        }

        writeClassHeader(code, packageName, className);
        writeBeforeClassMethod(code, dbms);
        writeAfterClassMethod(code);

        writeTestInfoMethods(code, dbms, schema, mutantNumber, mutants.size());

        writeDropTablesMethod(code, sqlWriter, schema);

        writeStubs(code);
        writeClassFooter(code);

        writeOutputToFile(file, code.toString());
        System.out.println("Code generated and successfully written to " + file);

        generateSchemaDirectory(schema, dbms, mutants);

        return true;
    }

    private void writeClassHeader(IndentableStringBuilder code, String packageName, String className) {
        code.appendln("package " + packageName + ";");
        code.appendln();
        code.appendln("import org.junit.After;");
        code.appendln("import org.junit.AfterClass;");
        code.appendln("import org.junit.BeforeClass;");
        code.appendln("import org.junit.Test;");
        code.appendln("import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;");
        code.appendln("");
        code.appendln("import java.sql.DriverManager;");
        code.appendln("import java.sql.SQLException;");
        code.appendln("");
        code.appendln("import static org.junit.Assert.*;");
        code.appendln();
        code.appendln("public class " + className + " extends ManualAnalysisTestSuite {");
        code.appendln(1);
    }

    private void writeBeforeClassMethod(IndentableStringBuilder code, DBMS dbms) {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        String jdbcClass = "", connectionURL = "";

        if (dbms instanceof HyperSQLDBMS) {
            jdbcClass = databaseConfiguration.getHsqldbDriver();
            connectionURL = "jdbc:hsqldb:mem:/database;hsqldb.write_delay=false";
        } else if (dbms instanceof PostgresDBMS) {
            jdbcClass = databaseConfiguration.getPostgresDriver();
            connectionURL = "jdbc:postgresql://"
                    + databaseConfiguration.getPostgresHost() + ":"
                    + databaseConfiguration.getPostgresPort() + "/"
                    + databaseConfiguration.getPostgresDatabase();
        } else if (dbms instanceof SQLiteDBMS) {
            jdbcClass = databaseConfiguration.getSqliteDriver();
            connectionURL = "jdbc:sqlite:manualanalysis";
        }

        code.appendln("@BeforeClass");
        code.appendln("public static void initialise() throws ClassNotFoundException, SQLException {");

        // DBMS specific code for creating a connection
        code.appendln(2, "// load the JDBC driver and create the connection and statement object used by this test suite");
        code.appendln("Class.forName(\"" + jdbcClass + "\");");

        if (dbms instanceof HyperSQLDBMS || dbms instanceof SQLiteDBMS) {
            code.appendln("connection = DriverManager.getConnection(\"" + connectionURL + "\");");
        }

        if (dbms instanceof PostgresDBMS) {
            code.appendln("connection = DriverManager.getConnection(\"" + connectionURL + "\", \"" +
                    databaseConfiguration.getPostgresUsername() + "\", \"" +
                    databaseConfiguration.getPostgresPassword() + "\");");
        }
        code.appendln();

        if (dbms instanceof HyperSQLDBMS || dbms instanceof PostgresDBMS) {
            code.appendln("// tell " + dbms.getName() + " to always persist the data right away");
            code.appendln("connection.setAutoCommit(true);");
        }

        code.appendln("// create the statement");
        code.appendln("statement = connection.createStatement();");

        if (dbms instanceof SQLiteDBMS) {
            code.appendln();
            code.appendln("// enable FOREIGN KEY support");
            code.appendln(writeExecuteUpdate(code, "PRAGMA foreign_keys = ON") + ";");
        }

        code.appendln(1, "}");
    }

    private void writeAfterClassMethod(IndentableStringBuilder code) {
        code.appendln(1);
        code.appendln("@AfterClass");
        code.appendln("public static void close() throws SQLException {");
        code.appendln(2, "if (connection != null) {");
        code.appendln(3, "connection.close();");
        code.appendln(2, "}");
        code.appendln(1, "}");
    }

    private void writeTestInfoMethods(
            IndentableStringBuilder code, DBMS dbms, Schema schema, int mutantNumber, int lastMutantNumber) {
        code.appendln("protected String getSchemaName() {");
        code.appendln("    return \"" + schema.getName() + "\";");
        code.appendln("}");
        code.appendln("");
        code.appendln("protected String getDBMSName() {");
        code.appendln("    return \"" + dbms.getName() + "\";");
        code.appendln("}");
        code.appendln("");
        code.appendln("protected int getMutantNumberBeingEvaluated() {");
        code.appendln("    return " + mutantNumber + ";");
        code.appendln("}");
        code.appendln("");
        code.appendln("protected int getLastMutantNumber() {");
        code.appendln("    return " + lastMutantNumber + ";");
        code.appendln("}");
    }

    private void writeDropTablesMethod(IndentableStringBuilder code, SQLWriter sqlWriter, Schema schema) {
        code.appendln(1);
        code.appendln("@After");
        code.appendln("public void dropTables() throws SQLException {");

        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        for (String statement : dropTableStatements) {
            code.appendln(2, writeExecuteUpdate(code, statement) + ";");
        }
        code.appendln(1, "}");
    }

    private void generateSchemaDirectory(Schema schema, DBMS dbms, List<Mutant<Schema>> mutants) {
        String dirName = BASE_DIR_NAME + "mutants/" + schema.getName() + "_" + dbms.getName() + "/";
        File dir = new File(dirName);

        if (dir.exists()) {
            System.out.println("Schema directory already exists for " + schema.getName() + " and " + dbms.getName());
            return;
        }

        if (!dir.mkdirs()) {
            System.out.println("Failed to create directory " + dir);
        }

        SQLWriter sqlWriter = dbms.getSQLWriter();
        String output = "-- Original schema\n";
        output += writeSchema(schema, sqlWriter);
        writeOutputToFile(dirName + "0.sql", output);
        for (Mutant<Schema> mutant : mutants) {
            output = writeMutantSchema(mutant, sqlWriter);
            writeOutputToFile(dirName + mutant.getIdentifier() + ".sql", output);
        }

        System.out.println("Successfully created mutants directory");
    }

    private void writeOutputToFile(String fileName, String output) {
        writeOutputToFile(new File(fileName), output);
    }

    private void writeOutputToFile(File file, String output) {
        try {
            PrintWriter out = new PrintWriter(file);
            out.println(output);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String writeSchema(Schema schema, SQLWriter sqlWriter) {
        String output = "";
        List<String> statements = sqlWriter.writeCreateTableStatements(schema);
        for (String statement : statements) {
            output += "\n";
            output += statement + "\n";
        }
        return output;
    }

    private String writeMutantSchema(Mutant<Schema> mutant, SQLWriter sqlWriter) {
        String output = "";
        output += "-- " + mutant.getIdentifier() + "\n";
        output += "-- " + mutant.getSimpleDescription() + "\n";
        output += "-- " + mutant.getDescription() + "\n";
        output += writeSchema(mutant.getMutatedArtefact(), sqlWriter);
        return output;
    }

    private String writeExecuteUpdate(IndentableStringBuilder code, String sqlStatement) {
        return "statement.executeUpdate(" + formatSQLStatement(code, sqlStatement) + ")";
    }

    private String formatSQLStatement(IndentableStringBuilder code, String sqlStatement) {
        int indentLevel = code.getIndentLevel();
        if (sqlStatement.contains("\n")) {
            String indent = StringUtils.repeat("\t", indentLevel + 1);

            List<String> substatements = new ArrayList<>();
            for (String substring : StringUtils.split(sqlStatement, "\n")) {
                substatements.add("\n" + indent + formatOneLineStatement(substring));
            }
            return StringUtils.join(substatements, " + ");
        } else {
            return formatOneLineStatement(sqlStatement);
        }
    }

    private String formatOneLineStatement(String sqlStatement) {
        String tabsToSpaces = sqlStatement.replace("\t", "    ");
        return "\"" + StringEscapeUtils.escapeJava(tabsToSpaces) + "\"";
    }

    private void writeStubs(IndentableStringBuilder code) {
        code.appendln();
        code.appendln("/*****************************/");
        code.appendln("/*** BEGIN MANUAL ANALYSIS ***/");
        code.appendln("/*****************************/");
        code.appendln();
        code.appendln("String statement1 = \"INSERT INTO \\\"\\\" VALUES( )\";");
        code.appendln();
        code.appendln("@Test");
        code.appendln("public void notImpaired() throws SQLException {");
        code.appendln("    assertTrue(insertToMutant(statement1));");
        code.appendln("}");
        code.appendln();
        code.appendln("@Test");
        code.appendln("public void notEquivalent() throws SQLException {");
        code.appendln("    assertTrue(originalAndMutantHaveDifferentBehavior(statement1));");
        code.appendln("}");
        code.appendln();
        code.appendln("@Test");
        code.appendln("public void notRedundant() throws SQLException {");
        code.appendln("    assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement1), SUCCESS);");
        code.appendln("}");
        code.appendln();
        code.appendln("// ENTER END VERDICT (delete as appropriate): normal/equivalent/redundant/impaired");
        code.appendln();
        code.appendln("/*****************************/");
        code.appendln("/***  END MANUAL ANALYSIS  ***/");
        code.appendln("/*****************************/");
        code.appendln();
    }

    private void writeClassFooter(IndentableStringBuilder code) {
        code.appendln(0, "}");
    }
}
