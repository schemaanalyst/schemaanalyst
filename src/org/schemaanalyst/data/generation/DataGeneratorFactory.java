package org.schemaanalyst.data.generation;

import org.schemaanalyst._deprecated.datageneration.search.Search;
import org.schemaanalyst._deprecated.datageneration.search.SearchFactory;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueLibrary;
import org.schemaanalyst.data.generation.directedrandom.DirectedRandomDataGenerator;
import org.schemaanalyst.data.generation.search.RandomDataGenerator;
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
        Search<Data> search = SearchFactory.avsDefaults(randomSeed, maxEvaluations);
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
                500);
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
                500);
    }

}
