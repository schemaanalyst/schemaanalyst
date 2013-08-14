/*
 */
package deprecated.mutation.analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

/**
 *
 * @author Chris J. Wright
 */
@RequiredParameters("input output")
public class PostgresLogToInsertFile extends Runner {

    private final static String INSERT_PREFIX = "LOG:  execute <unnamed>: INSERT";
    private final static String ERROR_STATEMENT_PREFIX = "STATEMENT:  INSERT";
    private final static String ERROR_PREFIX = "ERROR:";
    @Parameter
    String input;
    @Parameter
    String output;

    @Override
    protected void task() {
        File inputFile = new File(input);
        File outputFile = new File(output);

        try (BufferedReader reader = Files.newBufferedReader(inputFile.toPath(), Charset.forName("UTF-8")); BufferedWriter writer = Files.newBufferedWriter(outputFile.toPath(), Charset.forName("UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(INSERT_PREFIX)) {
                    String insert = line.substring(line.indexOf("INSERT"));
                    writer.write(insert);
                    writer.newLine();
                } else if (line.startsWith(ERROR_PREFIX)) {
                    String detail = reader.readLine();
                    String statement = reader.readLine();
                    if (statement.startsWith(ERROR_STATEMENT_PREFIX)) {
                        String insert = statement.substring(statement.indexOf("INSERT"));
                        writer.write(insert);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed while parsing log file", ex);
        }
    }

    @Override
    protected void validateParameters() {
        Path inputPath = new File(input).toPath();
        if (!Files.isReadable(inputPath)) {
            exitWithArgumentException(String.format("input path '%s' is not readable", inputPath));
        }
    }

    public static void main(String[] args) {
        new PostgresLogToInsertFile().run(args);
    }
}
