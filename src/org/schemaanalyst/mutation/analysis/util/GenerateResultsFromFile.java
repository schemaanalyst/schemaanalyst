/*
 */
package org.schemaanalyst.mutation.analysis.util;

import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.util.tuple.MixedPair;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * {@link Runner} for acquiring data from a file, testing it against a 
 * non-mutant schema and persisting the results to file.
 * </p>
 * 
 * @author Chris J. Wright
 */
@RequiredParameters("casestudy input")
public class GenerateResultsFromFile extends GenerateResults {

    private final static Logger LOGGER = Logger.getLogger(GenerateResultsFromFile.class.getName());
    @Parameter
    String input;

    @Override
    public List<MixedPair<String,Boolean>> getInserts() {
        List<MixedPair<String,Boolean>> result = new ArrayList<>();
        File inputFile = new File(input);
        try (BufferedReader reader = Files.newBufferedReader(inputFile.toPath(), Charset.forName("UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                MixedPair<String, Boolean> pair = new MixedPair<>(line,null);
                result.add(pair);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to read input file", ex);
        }
        return result;
    }

    @Override
    protected void validateParameters() {
        Path inputPath = new File(input).toPath();
        if (!Files.isReadable(inputPath)) {
            exitWithArgumentException(String.format("input path '%s' is not readable", inputPath));
        }
    }

    public static void main(String[] args) {
        new GenerateResultsFromFile().run(args);
    }
}
