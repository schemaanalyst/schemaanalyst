package org.schemaanalyst.data.generation;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueLibrary;
import org.schemaanalyst.data.ValueMiner;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellinitialization.DefaultCellInitializer;
import org.schemaanalyst.data.generation.cellinitialization.RandomCellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.cellvaluegeneration.ValueInitializationProfile;
import org.schemaanalyst.data.generation.directedrandom.DirectedRandomDataGenerator;
import org.schemaanalyst.data.generation.random.RandomDataGenerator;
import org.schemaanalyst.data.generation.search.AlternatingValueSearch;
import org.schemaanalyst.data.generation.search.Search;
import org.schemaanalyst.data.generation.search.SearchBasedDataGenerator;
import org.schemaanalyst.data.generation.search.termination.CombinedTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.OptimumTerminationCriterion;
import org.schemaanalyst.data.generation.search.termination.TerminationCriterion;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by phil on 14/03/2014.
 */
public class DataGeneratorFactory {

    public static DataGenerator instantiate(String dataGeneratorName,
                                            long randomSeed,
                                            int maxEvaluations) {
        return instantiate(dataGeneratorName, randomSeed, maxEvaluations, null);
    }

    @SuppressWarnings("unchecked")
    public static DataGenerator instantiate(String dataGeneratorName,
                                            long randomSeed,
                                            int maxEvaluations,
                                            Schema schema) {
        Class<DataGeneratorFactory> c = DataGeneratorFactory.class;
        Method methods[] = c.getMethods();

        String instantiatingMethodName = dataGeneratorName + "Generator";

        for (Method m : methods) {
            if (m.getName().equals(instantiatingMethodName)) {
                try {
                    Object[] args = {randomSeed, maxEvaluations, schema};
                    return (DataGenerator) m.invoke(null, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new DataGenerationException("Unknown data generator \"" + dataGeneratorName + "\"");
    }

    private static Random makeRandomNumberGenerator(long seed) {
        return new SimpleRandom(seed);
    }

    private static RandomCellValueGenerator makeRandomCellValueGenerator(Random random, Schema schema) {
        return new RandomCellValueGenerator(
                random,
                ValueInitializationProfile.SMALL,
                0.1,
                makeValueLibrary(schema),
                0.25);
    }

    private static ValueLibrary makeValueLibrary(Schema schema) {
        return (schema == null)
                ? new ValueLibrary()
                : new ValueMiner().mine(schema);
    }

    public static SearchBasedDataGenerator makeAlternatingValueSearch(
            int maxEvaluations,
            Random random,
            CellInitializer startInitializer,
            CellInitializer restartInitializer) {

        Search<Data> search = new AlternatingValueSearch(
                random,
                startInitializer,
                restartInitializer);

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(search.getEvaluationsCounter(), maxEvaluations),
                new OptimumTerminationCriterion<>(search));

        search.setTerminationCriterion(terminationCriterion);

        return new SearchBasedDataGenerator(search);
    }

    public static SearchBasedDataGenerator avsDefaultsGenerator(long randomSeed, int maxEvaluations, Schema schema) {
        Random random = makeRandomNumberGenerator(randomSeed);
        RandomCellValueGenerator randomCellValueGenerator = makeRandomCellValueGenerator(random, schema);

        return makeAlternatingValueSearch(
                maxEvaluations,                random,
                new DefaultCellInitializer(),
                new RandomCellInitializer(randomCellValueGenerator));
    }

    public static SearchBasedDataGenerator avsRandomStartGenerator(long randomSeed, int maxEvaluations, Schema schema) {
        Random random = makeRandomNumberGenerator(randomSeed);
        RandomCellValueGenerator randomCellValueGenerator = makeRandomCellValueGenerator(random, schema);
        RandomCellInitializer randomCellInitializer = new RandomCellInitializer(randomCellValueGenerator);

        return makeAlternatingValueSearch(
                maxEvaluations,
                random,
                randomCellInitializer,
                randomCellInitializer);
    }

    public static DirectedRandomDataGenerator directedRandomGenerator(long randomSeed, int maxEvaluations, Schema schema) {
        Random random = new SimpleRandom(randomSeed);
        return new DirectedRandomDataGenerator(
                random,
                new RandomCellValueGenerator(
                        random,
                        ValueInitializationProfile.SMALL,
                        0.1,
                        makeValueLibrary(schema),
                        0.25),
                maxEvaluations);
    }

    public static RandomDataGenerator randomGenerator(long randomSeed, int maxEvaluations, Schema schema) {
        Random random = new SimpleRandom(randomSeed);
        return new RandomDataGenerator(
                random,
                new RandomCellValueGenerator(
                        random,
                        ValueInitializationProfile.SMALL,
                        0.1,
                        makeValueLibrary(schema),
                        0.25),
                maxEvaluations);
    }
}
