package paper.ineffectivemutants;

import org.schemaanalyst.dbms.hypersql.HyperSQLSQLWriter;
import org.schemaanalyst.dbms.postgres.PostgresSQLWriter;
import org.schemaanalyst.dbms.sqlite.SQLiteSQLWriter;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Writes the original schema file to the mutant directories
 * so that they can be compared with mutants.
 *
 * Created by phil on 16/12/2015.
 */
public class OutputOriginalSchemas {

    private static String BASE_DIR = "/Users/phil/Projects/schemaanalyst/ineffective-mutants/_data/_normalisation-reruns/mutants/";

    private static String MUTANT_DIR_SUFFIX = "-minus-stillborn/";

    private static String FILE = "0-original";

    private static String getSQL(SQLWriter sqlWriter, Schema schema) {
        List<String> createTableStatements = sqlWriter.writeCreateTableStatements(schema);
        String sql = "";
        for (String statement : createTableStatements) {
            sql += statement;
        }
        return sql;
    }

    private static void writeMutant(SQLWriter sqlWriter, Schema schema, String dbms) throws FileNotFoundException {
        String schemaName = schema.getClass().getSimpleName();

        String sql = "Original schema\n\n" + getSQL(sqlWriter, schema);

        String file = BASE_DIR + dbms + MUTANT_DIR_SUFFIX + schemaName + "/" + FILE;

        System.out.println("Wrote: " + file);
        PrintWriter out = new PrintWriter(file);
        out.println(sql);
        out.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        for (Schema schema : Schemas.schemas) {
            SQLWriter sqlWriter = new PostgresSQLWriter();
            writeMutant(sqlWriter, schema, "postgres");

            sqlWriter = new HyperSQLSQLWriter();
            writeMutant(sqlWriter, schema, "hypersql");

            sqlWriter = new SQLiteSQLWriter();
            writeMutant(sqlWriter, schema, "sqlite");
        }
    }
}
