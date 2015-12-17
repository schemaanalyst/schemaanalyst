package paper.qsic2014jv;

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
        for (int i=1; i <= NUM_TEST_SUITES; i++) {
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
        mutantIndex = 97;
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

        String packageName = "paper.qsic2014jv.manualevaluation";
        String className = "Test" + dbms.getName() + schema.getName() + mutantNumber;

        String dirName = new LocationsConfiguration().getSrcDir() + "/paper/qsic2014jv/manualevaluation/";
        String fileName = dirName + className + ".java";
        String doneFileName = dirName + className + "_DONE.java";

        File file = new File(fileName);
        File doneFile = new File(doneFileName);

        if (file.exists()) {
            System.out.println(file + " exists -- delete it to regenerate it");
            return false;
        } else if (doneFile.exists()) {
            System.out.println(doneFile + " is DONE! (Delete it if you really want to regenerate it)");
            return false;
        }

        writeClassHeader(code, packageName, className, dbms, schema, mutantNumber, mutants.size());
        writeBeforeClassMethod(code, dbms);
        writeDropTablesMethod(code, sqlWriter, schema);
        writeOriginalSchemaMethod(code, schema, sqlWriter);
        writeMutantSchemaMethod(code, selectedMutant, sqlWriter);
        writeStubs(code);
        writeOtherMutantSchemaMethod(code, mutants, mutantNumber, sqlWriter);
        writeHelperMethods(code);
        writeAfterClassMethod(code);
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

    private void writeClassHeader(IndentableStringBuilder code, String packageName, String className, DBMS dbms, Schema schema, int mutantNumber, int lastMutantNumber) {
        code.appendln("package " + packageName + ";");
        code.appendln();
        code.appendln("import java.sql.Connection;");
        code.appendln("import java.sql.DriverManager;");
        code.appendln("import java.sql.SQLException;");
        code.appendln("import java.sql.Statement;");
        code.appendln("import java.util.ArrayList;");
        code.appendln("import java.util.List;");
        code.appendln();
        code.appendln("import org.junit.AfterClass;");
        code.appendln("import org.junit.BeforeClass;");
        code.appendln("import org.junit.Test;");
        code.appendln();
        code.appendln("import static org.junit.Assert.*;");
        code.appendln();
        code.appendln("public class " + className + " {");
        code.appendln(1);
        code.appendln("private static final int SUCCESS = 0;");
        code.appendln("private static final boolean QUIET = false;");
        code.appendln();
        code.appendln("private static final int FIRST_MUTANT_NUMBER = 1;");
        code.appendln("private static final int MUTANT_BEING_EVALUATED = " + mutantNumber + ";");
        code.appendln("private static final int LAST_MUTANT_NUMBER = " + lastMutantNumber + ";");
        code.appendln();

        if (dbms instanceof HyperSQLDBMS) {
            code.appendln("private static final String JDBC_CLASS = \"org.hsqldb.jdbc.JDBCDriver\";");
            code.appendln("private static final String CONNECTION_URL = \"jdbc:hsqldb:mem:/database;hsqldb.write_delay=false\";");
        } else if (dbms instanceof PostgresDBMS) {
            // TODO -- complete!!!
            code.appendln("// TODO -- write the code for Postgres");
        } if (dbms instanceof SQLiteDBMS) {
            code.appendln("private static final String JDBC_CLASS = \"org.sqlite.JDBC\";");
            code.appendln("private static final String CONNECTION_URL = \"jdbc:sqlite:" + schema.getName() + "\";");
        }

        code.appendln();
        code.appendln("private static Connection connection;");
        code.appendln("private static Statement statement;");
    }

    private void writeBeforeClassMethod(IndentableStringBuilder code, DBMS dbms) {
        code.appendln(1);
        code.appendln("@BeforeClass");
        code.appendln("public static void initialise() throws ClassNotFoundException, SQLException {");

        // DBMS specific code for creating a connection
        code.appendln(2, "// load the JDBC driver and create the connection and statement object used by this test suite");
        code.appendln("Class.forName(JDBC_CLASS);");
        code.appendln("connection = DriverManager.getConnection(CONNECTION_URL);");
        code.appendln("statement = connection.createStatement();");

        if (dbms instanceof SQLiteDBMS) {
            code.appendln();
            code.appendln("// enable FOREIGN KEY support");
            code.appendln(writeExecuteUpdate(code, "PRAGMA foreign_keys = ON") + ";");
        }

        code.appendln(1, "}");
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
            number ++;
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

    private void writeHelperMethods(IndentableStringBuilder code) {
        code.appendln(1, "public boolean doInsert(String insertStatement) {");
        code.appendln(2, "try {");
        code.appendln(3, "statement.executeUpdate(insertStatement);");
        code.appendln(3, "return true;");
        code.appendln(2, "} catch (SQLException e) {");
        code.appendln(3, "return false;");
        code.appendln(2, "}");
        code.appendln(1, "}");
        code.appendln();
        code.appendln("public boolean insertToMutant(String insertStatement) throws SQLException {");
        code.appendln("    createMutantSchema();");
        code.appendln("    return doInsert(insertStatement);");
        code.appendln("}");
        code.appendln();
        code.appendln("public boolean originalAndMutantHaveDifferentBehavior(String... insertStatements) throws SQLException {");
        code.appendln("    List<Boolean> originalSchemaResults = new ArrayList<>();");
        code.appendln("    List<Boolean> mutantSchemaResults = new ArrayList<>();");
        code.appendln();
        code.appendln("    createOriginalSchema();");
        code.appendln("    for (String insertStatement : insertStatements) {");
        code.appendln("        originalSchemaResults.add(doInsert(insertStatement));");
        code.appendln("    }");
        code.appendln();
        code.appendln("    createMutantSchema();");
        code.appendln("    for (String insertStatement : insertStatements) {");
        code.appendln("        mutantSchemaResults.add(doInsert(insertStatement));");
        code.appendln("    }");
        code.appendln();
        code.appendln("    if (!QUIET) {");
        code.appendln("        System.out.println(\"Orig/mutant: \" + originalSchemaResults + \"/\" + mutantSchemaResults);");
        code.appendln("    }");
        code.appendln();
        code.appendln("    for (int i=0; i < insertStatements.length; i++) {");
        code.appendln("        if (originalSchemaResults.get(i) != mutantSchemaResults.get(i)) {");
        code.appendln("            return true;");
        code.appendln("        }");
        code.appendln("    }");
        code.appendln();
        code.appendln("    return false;");
        code.appendln("}");
        code.appendln();
        code.appendln("public int mutantAndOtherMutantsHaveDifferentBehavior(String... insertStatements) throws SQLException {");
        code.appendln("    return mutantAndOtherMutantsHaveDifferentBehavior(FIRST_MUTANT_NUMBER, LAST_MUTANT_NUMBER, insertStatements);");
        code.appendln("}");
        code.appendln();
        code.appendln("public int mutantAndOtherMutantsHaveDifferentBehaviorToLastFrom(int mutantNumber, String... insertStatements) throws SQLException {");
        code.appendln("    return mutantAndOtherMutantsHaveDifferentBehavior(mutantNumber, LAST_MUTANT_NUMBER, insertStatements);");
        code.appendln("}");
        code.appendln();
        code.appendln("public int mutantAndOtherMutantsHaveDifferentBehaviorFromFirstTo(int mutantNumber, String... insertStatements) throws SQLException {");
        code.appendln("    return mutantAndOtherMutantsHaveDifferentBehavior(FIRST_MUTANT_NUMBER, mutantNumber, insertStatements);");
        code.appendln("}");
        code.appendln();
        code.appendln("public int mutantAndOtherMutantsHaveDifferentBehavior(int mutantNumber, String... insertStatements) throws SQLException {");
        code.appendln("    return mutantAndOtherMutantsHaveDifferentBehavior(mutantNumber, mutantNumber, insertStatements);");
        code.appendln("}");
        code.appendln();
        code.appendln("public int mutantAndOtherMutantsHaveDifferentBehavior(int firstMutantNumber, int lastMutantNumber, String... insertStatements) throws SQLException {");
        code.appendln("    List<Boolean> mutantSchemaResults = new ArrayList<>();");
        code.appendln("    createMutantSchema();");
        code.appendln("    for (String insertStatement : insertStatements) {");
        code.appendln("        mutantSchemaResults.add(doInsert(insertStatement));");
        code.appendln("    }");
        code.appendln();
        code.appendln("    for (int i=firstMutantNumber; i <= lastMutantNumber; i++) {");
        code.appendln("        if (i == MUTANT_BEING_EVALUATED) {");
        code.appendln("            continue;");
        code.appendln("        }");
        code.appendln();
        code.appendln("        List<Boolean> otherMutantSchemaResults = new ArrayList<>();");
        code.appendln("        createOtherMutantSchema(i);");
        code.appendln();
        code.appendln("        for (String insertStatement : insertStatements) {");
        code.appendln("            otherMutantSchemaResults.add(doInsert(insertStatement));");
        code.appendln("        }");
        code.appendln();
        code.appendln("        if (!QUIET) {");
        code.appendln("            System.out.println(\"Mutant/mutant\" + i + \": \" + mutantSchemaResults + \"/\" + otherMutantSchemaResults);");
        code.appendln("        }");
        code.appendln();
        code.appendln("        boolean different = false;");
        code.appendln("        for (int j=0; j < insertStatements.length; j++) {");
        code.appendln("            if (mutantSchemaResults.get(j) != otherMutantSchemaResults.get(j)) {");
        code.appendln("                different = true;");
        code.appendln("            }");
        code.appendln("        }");
        code.appendln();
        code.appendln("        if (!different) {");
        code.appendln("            return i;");
        code.appendln("        }");
        code.appendln("    }");
        code.appendln();
        code.appendln("    return SUCCESS;");
        code.appendln("}");
        code.appendln();
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

    private void writeAfterClassMethod(IndentableStringBuilder code) {
        code.appendln(1);
        code.appendln("@AfterClass");
        code.appendln("public static void close() throws SQLException {");
        code.appendln(2, "if (connection != null) {");
        code.appendln(3, "connection.close();");
        code.appendln(2, "}");
        code.appendln(1, "}");
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
        code.appendln("/*****************************/");
        code.appendln("/***  END MANUAL ANALYSIS  ***/");
        code.appendln("/*****************************/");
        code.appendln();
    }

    private void writeClassFooter(IndentableStringBuilder code) {
        code.appendln(0, "}");
    }
}
