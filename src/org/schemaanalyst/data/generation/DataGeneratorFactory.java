package org.schemaanalyst.data.generation;

import _deprecated.datageneration.cellrandomisation.CellRandomiserFactory;
import _deprecated.datageneration.search.datainitialization.NoDataInitialization;
import _deprecated.datageneration.search.datainitialization.RandomDataInitializer;
import org.schemaanalyst.data.ValueLibrary;
import org.schemaanalyst.data.generation.directedrandom.DirectedRandomDataGenerator;
import org.schemaanalyst.data.generation.search.AlternatingValueSearch;
import org.schemaanalyst.data.generation.search.RandomDataGenerator;
import org.schemaanalyst.data.generation.search.Search;
import org.schemaanalyst.data.generation.search.SearchBasedDataGenerator;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by phil on 14/03/2014.
 */
public class DataGeneratorFactory {

    @SuppressWarnings("unchecked")
    public static DataGenerator instantiate(String dataGeneratorName,
                                            long randomSeed,
                                            int maxEvaluations,
                                            ValueLibrary valueLibrary) {
        Class<DataGeneratorFactory> c = DataGeneratorFactory.class;
        Method methods[] = c.getMethods();

        for (Method m : methods) {
            if (m.getName().equals(dataGeneratorName)) {
                try {
                    Object[] args = {randomSeed, maxEvaluations, valueLibrary};
                    return (DataGenerator) m.invoke(null, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new DataGenerationException("Unknown data generator \"" + dataGeneratorName + "\"");
    }

    public static SearchBasedDataGenerator avsDefaults(long randomSeed, int maxEvaluations, ValueLibrary valueLibrary) {
        Random random = new SimpleRandom(randomSeed);

        Search search = new AlternatingValueSearch(
                random,
                maxEvaluations,
                valueLibrary,
                new NoDataInitialization(),
                new RandomDataInitializer(CellRandomiserFactory.small(random)));

        return new SearchBasedDataGenerator(search);
    }

    public static DirectedRandomDataGenerator directedRandom(long randomSeed, int maxEvaluations, ValueLibrary valueLibrary) {
        Random random = new SimpleRandom(randomSeed);
        return new DirectedRandomDataGenerator(
                random,
                new CellValueGenerator(
                        random,
                        ValueInitializationProfile.SMALL,
                        0.1,
                        valueLibrary,
                        0.25),
                maxEvaluations);
    }

    public static RandomDataGenerator random(long randomSeed, int maxEvaluations, ValueLibrary valueLibrary) {
        Random random = new SimpleRandom(randomSeed);
        return new RandomDataGenerator(
                random,
                new CellValueGenerator(
                        random,
                        ValueInitializationProfile.SMALL,
                        0.1,
                        valueLibrary,
                        0.25),
                maxEvaluations);
    }
}
