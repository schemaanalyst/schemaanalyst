package org.schemaanalyst.mutation.analysis.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

/**
 * Prints the detailed description of mutants according to a .dat file.
 * 
 * @author Chris J. Wright
 */
@RequiredParameters("file mutationPipeline")
public class MutantFromDatReporter extends Runner {
    
    @Parameter("The file to read from")
    protected String file;
    
    /**
     * The mutation pipeline to use to generate mutants.
     */
    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "AllOperatorsWithClassifiers";
    
    private static final Logger LOGGER = Logger.getLogger(MutantFromDatReporter.class.getName());

    @Override
    protected void task() {
        String dbms = determineDBMS(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip header row
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                int split = line.indexOf(',');
                String casestudy = line.substring(0, split).replaceAll("\"", "");
                String ids = line.substring(split + 1, line.length()).replaceAll("\"", "");
                
                String path = file.replaceAll(".dat", "") + "/" + casestudy + "/";
                new File(path).mkdirs();
                MutantReporter reporter = new MutantReporter();
                reporter.mutationPipeline = this.mutationPipeline;
                reporter.reportToFile(casestudy, dbms, ids, path);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String determineDBMS(String file) {
        String name;
        if (file.toLowerCase().contains("sqlite")) {
            name = "SQLite";
        } else if (file.toLowerCase().contains("postgres")) {
            name = "Postgres";
        } else if (file.toLowerCase().contains("hypersql")) {
            name = "HyperSQL";
        } else {
            LOGGER.warning("Unable to detect DBMS from filename. Using configuration value");
            name = databaseConfiguration.getDbms();
        }
        return name;
    }
    
    @Override
    protected void validateParameters() {
        // Do nothing
    }
    
    public static void main(String[] args) {
        new MutantFromDatReporter().run(args);
    }
    
}
