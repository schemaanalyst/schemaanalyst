package org.schemaanalyst.mutation.analysis.util;

import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chris J. Wright
 */
@Description("Runs mutant generation and outputs a breakdown of the number of "
        + "mutants produced per operator.")
@RequiredParameters("casestudy dbms")
public class AnalysePipeline extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;

    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "AllOperatorsWithRemovers";
    
    @Parameter(value = "The DBMS to use.", choicesMethod = "org.schemaanalyst.dbms.DBMSFactory.getDBMSChoices")
    protected String dbms;

    protected Schema schema;
    protected MutationPipeline<Schema> pipeline;

    /**
     * The folder to write the results.
     */
    @Parameter("The folder to write the results.")
    protected String outputfolder; // Default in initialise

    @Override
    protected void task() {
        initialise();
        List<CSVResult> results = new ArrayList<>();
        List<Mutant<Schema>> mutants = pipeline.mutate();
        calculateProduced(results);
        calculateRemoved(results);
        calculateRetained(mutants, results);
        CSVFileWriter writer = new CSVFileWriter(outputfolder + "analysepipeline.dat", ",");
        writer.write(results);
    }

    private void calculateProduced(List<CSVResult> results) {
        for (Map.Entry<Class, Integer> entry : pipeline.getProducerCounts().entrySet()) {
            final Class clazz = entry.getKey();
            final StopWatch stopWatch = pipeline.getProducerTimings().get(clazz);
            CSVResult result = initialiseResult();
            result.addValue("type", "produced");
            result.addValue("operator", clazz.getSimpleName());
            result.addValue("count", entry.getValue());
            result.addValue("timetaken", stopWatch.getTime());
            results.add(result);
        }
    }

    private void calculateRemoved(List<CSVResult> results) {
        for (Map.Entry<Class, Integer> entry : pipeline.getRemoverCounts().entrySet()) {
            final Class clazz = entry.getKey();
            final StopWatch stopWatch = pipeline.getRemoverTimings().get(clazz);
            CSVResult result = initialiseResult();
            result.addValue("type", "removed");
            result.addValue("operator", clazz.getSimpleName());
            result.addValue("count", entry.getValue());
            result.addValue("timetaken", stopWatch.getTime());
            results.add(result);
        }
    }

    private void calculateRetained(List<Mutant<Schema>> mutants, List<CSVResult> results) {
        HashMap<String, Integer> producerMap = new HashMap<>();
        for (Mutant<Schema> mutant : mutants) {
            String opName = mutant.getSimpleDescription();
            int count = producerMap.containsKey(opName) ? producerMap.get(opName) : 0;
            producerMap.put(opName, count + 1);
        }
        for (Map.Entry<String, Integer> entry : producerMap.entrySet()) {
            CSVResult result = initialiseResult();
            result.addValue("type", "retained");
            result.addValue("operator", entry.getKey());
            result.addValue("count", entry.getValue());
            result.addValue("timetaken", "NA");
            results.add(result);
        }
    }

    protected void initialise() {
        if (outputfolder == null) {
            outputfolder = locationsConfiguration.getResultsDir() + File.separator;
        }
        schema = initialiseSchema();
        pipeline = initialisePipeline();
    }

    private CSVResult initialiseResult() {
        CSVResult csvResult = new CSVResult();
        csvResult.addValue("dbms", dbms);
        csvResult.addValue("casestudy", casestudy);
        csvResult.addValue("pipeline", mutationPipeline);
        return csvResult;
    }

    private Schema initialiseSchema() {
        try {
            return (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private MutationPipeline<Schema> initialisePipeline() {
        try {
            return MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema, dbms);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void validateParameters() {
        // No validation needed
    }

    public static void main(String[] args) {
        new AnalysePipeline().run(args);
    }
}
