package paper.ineffectivemutants;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
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
import parsedcasestudy.BrowserCookies;

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

    final int NUM_TEST_SUITES = 1;
    final String MUTATION_PIPELINE = "AllOperatorsWithImpaired";

    public static void main(String[] args) {
        new GenerateTestSuites();
    }

    public GenerateTestSuites() {
        for (int i = 1; i <= NUM_TEST_SUITES; i++) {
            generateTestSuite(i);
        }
    }

    private void generateTestSuite(int number) {
        // Select a DBMS / SQL writer
        List<String> dbmses = DBMSFactory.getDBMSChoices();
        String dbmsName = dbmses.get(randomIndex(dbmses));
        // for testing:
        dbmsName = "SQLite";

        DBMS dbms = DBMSFactory.instantiate(dbmsName);

        // Select a schema
        Schema schema = Schemas.schemas[randomIndex(Schemas.schemas)];
        // for testing:
        schema = new BrowserCookies();

        // Get mutants
        List<Mutant<Schema>> mutants = generateMutants(schema, dbmsName);

        // Select a mutant at random
        int mutantIndex = randomIndex(mutants);
        mutantIndex = 96;
        Mutant<Schema> selectedMutant = mutants.get(mutantIndex);
        int mutantNumber = mutantIndex + 1;

        writeTestSuite(number, dbms, schema, mutants, selectedMutant, mutantNumber);
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

    private boolean writeTestSuite(int number, DBMS dbms, Schema schema, List<Mutant<Schema>> mutants, Mutant<Schema> selectedMutant, int mutantNumber) {

        SQLWriter sqlWriter = dbms.getSQLWriter();
        IndentableStringBuilder code = new IndentableStringBuilder();

        String packageName = "paper.ineffectivemutants.manualevaluation.todo";
        String className = schema.getName() + "_" + dbms.getName() + "_" + mutantNumber;

        String baseDirName = new LocationsConfiguration().getSrcDir() + "/paper/ineffectivemutants/manualevaluation/";
        String toDoFileName = baseDirName + "todo/" + className + ".java";
        File file = new File(toDoFileName);

        if (file.exists()) {
            System.out.println(file + " exists -- delete it to regenerate it");
            return false;
        } else {
            String[] suffixes = {"NORMAL", "EQUIVALENT", "REDUNDANT", "IMPAIRED"};
            for (String suffix : suffixes) {
                File completeFile = new File(baseDirName + "complete/" + className + "_" + suffix + ".java");
                System.out.println(completeFile);
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

        // to remove in time...
        writeOriginalSchemaMethod(code, schema, sqlWriter);
        writeMutantSchemaMethod(code, selectedMutant, sqlWriter);
        writeOtherMutantSchemaMethod(code, mutants, mutantNumber, sqlWriter);

        writeStubs(code);
        writeClassFooter(code);

        System.out.println(code);

        try {
            PrintWriter out = new PrintWriter(file);
            out.println(code);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void writeClassHeader(IndentableStringBuilder code, String packageName, String className) {
        code.appendln("package " + packageName + ";");
        code.appendln();
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
        String jdbcClass = "", connectionURL = "";

        if (dbms instanceof HyperSQLDBMS) {
            jdbcClass = "org.hsqldb.jdbc.JDBCDriver";
            connectionURL = "jdbc:hsqldb:mem:/database;hsqldb.write_delay=false";
        } else if (dbms instanceof PostgresDBMS) {
            // TODO -- complete!!!

        } else if (dbms instanceof SQLiteDBMS) {
            jdbcClass = "org.sqlite.JDBC";
            connectionURL = "jdbc:sqlite:manualanalysis";
        }

        code.appendln("@BeforeClass");
        code.appendln("public static void initialise() throws ClassNotFoundException, SQLException {");

        // DBMS specific code for creating a connection
        code.appendln(2, "// load the JDBC driver and create the connection and statement object used by this test suite");
        code.appendln("Class.forName(\"" + jdbcClass + "\");");
        code.appendln("connection = DriverManager.getConnection(\"" + connectionURL + "\");");
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
        code.appendln("public void dropTables() throws SQLException {");

        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(schema, true);
        for (String statement : dropTableStatements) {
            code.appendln(2, writeExecuteUpdate(code, statement) + ";");
        }
        code.appendln(1, "}");
    }

    private void writeOriginalSchemaMethod(IndentableStringBuilder code, Schema schema, SQLWriter sqlWriter) {
        code.appendln(1);
        code.appendln("public void createOriginalSchema() throws SQLException {");
        code.appendln(2, "dropTables();");
        code.appendln(2);
        writeSchema(code, 2, schema, sqlWriter);
        code.appendln(1, "}");
    }

    private void writeMutantSchemaMethod(IndentableStringBuilder code, Mutant<Schema> selectedMutant, SQLWriter sqlWriter) {
        code.appendln(1);
        code.appendln("public void createMutantSchema() throws SQLException {");
        code.appendln(2, "dropTables();");
        code.appendln(2);
        writeMutant(code, 2, selectedMutant, sqlWriter);
        code.appendln(1, "}");
    }

    private void writeOtherMutantSchemaMethod(IndentableStringBuilder code, List<Mutant<Schema>> mutants, int mutantNumber, SQLWriter sqlWriter) {
        code.appendln(1);
        code.appendln("public void createOtherMutantSchema(int number) throws SQLException {");
        code.appendln(2, "dropTables();");
        code.appendln(2);

        boolean first = true;
        int number = 1;
        for (Mutant<Schema> mutant : mutants) {
            if (number != mutantNumber) {
                String line = "if (number == " + number + ") {";
                if (first) {
                    first = false;
                } else {
                    line = "} else " + line;
                }
                code.appendln(2, line);
                writeMutant(code, 3, mutant, sqlWriter);
            }
            number++;
        }

        code.appendln(2, "} else {");
        code.appendln(3, "fail(\"No such mutant number -- \" + number);");
        code.appendln(2, "}");
        code.appendln(1, "}");
    }

    private void writeMutant(IndentableStringBuilder code, int level, Mutant<Schema> mutant, SQLWriter sqlWriter) {
        code.appendln(level, "//" + mutant.getIdentifier());
        code.appendln(level, "//" + mutant.getSimpleDescription());
        code.appendln(level, "//" + mutant.getDescription());
        writeSchema(code, level, mutant.getMutatedArtefact(), sqlWriter);
    }

    private void writeSchema(IndentableStringBuilder code, int level, Schema schema, SQLWriter sqlWriter) {
        List<String> createTableStatements = sqlWriter.writeCreateTableStatements(schema);
        for (String statement : createTableStatements) {
            code.appendln(level, writeExecuteUpdate(code, statement) + ";");
        }
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
        code.appendln("// String statement1 = \"INSERT INTO [table] VALUES([...])\"");
        code.appendln("// String statement2 = \"INSERT INTO [table] VALUES([...])\"");
        code.appendln("// String statement3 = \"INSERT INTO [table] VALUES([...])\"");
        code.appendln("// String statement4 = \"INSERT INTO [table] VALUES([...])\"");
        code.appendln("// String statement5 = \"INSERT INTO [table] VALUES([...])\"");
        code.appendln();
        code.appendln();
        code.appendln("@Test");
        code.appendln("public void notImpaired() throws SQLException {");
        code.appendln("    // ... or maybe it is ...");
        code.appendln("}");
        code.appendln();
        code.appendln("@Test");
        code.appendln("public void notEquivalent() throws SQLException {");
        code.appendln("    // ... or maybe it is ...");
        code.appendln("}");
        code.appendln();
        code.appendln("@Test");
        code.appendln("public void notRedundant() throws SQLException {");
        code.appendln("    // ... or maybe it is ...");
        code.appendln("}");
        code.appendln();
        code.appendln("// ENTER END VERDICT (delete as appropriate): impaired/equivalent/redundant/normal");
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
