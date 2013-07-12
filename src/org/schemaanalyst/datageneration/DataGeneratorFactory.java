package org.schemaanalyst.datageneration;

import java.lang.reflect.Method;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.domainspecific.DomainSpecificConstraintCoverer;
import org.schemaanalyst.datageneration.search.AlternatingValueSearch;
import org.schemaanalyst.datageneration.search.NaiveRandomConstraintCoverer;
import org.schemaanalyst.datageneration.search.RandomSearch;
import org.schemaanalyst.datageneration.search.Search;
import org.schemaanalyst.datageneration.search.SearchConstraintCoverer;
import org.schemaanalyst.datageneration.search.datainitialization.NoDataInitialization;
import org.schemaanalyst.datageneration.search.datainitialization.RandomDataInitializer;
import org.schemaanalyst.datageneration.search.termination.CombinedTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.OptimumTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.TerminationCriterion;
import org.schemaanalyst.deprecated.Configuration;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.random.Random;

public class DataGeneratorFactory {

    public static DataGenerator instantiate(String name,
            Schema schema,
            ValueFactory valueFactory,
            Random random,
            CellRandomiser cellRandomiser) {

        // get hold of the method objects of this class 
        Class<DataGeneratorFactory> clazz = DataGeneratorFactory.class;
        Method methods[] = clazz.getMethods();

        // get the name to match a method name by lowercasing the first letter
        char characters[] = name.toCharArray();
        characters[0] = Character.toLowerCase(characters[0]);
        name = new String(characters);

        // find the method for instantiating the right data generator
        for (Method m : methods) {
            if (m.getName().equals(name)) {
                try {
                    Object[] args = {schema, valueFactory, random, cellRandomiser};
                    return (DataGenerator) m.invoke(null, args);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new RuntimeException("Unknown data generator \"" + name + "\"");
    }

    public static SearchConstraintCoverer alternatingValue(Schema schema,
            ValueFactory valueFactory,
            Random random,
            CellRandomiser cellRandomiser) {

        Search<Data> search = new AlternatingValueSearch(
                random,
                new RandomDataInitializer(cellRandomiser),
                new RandomDataInitializer(cellRandomiser));

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(search.getEvaluationsCounter(),
                Configuration.maxevaluations),
                new OptimumTerminationCriterion<>(search));

        search.setTerminationCriterion(terminationCriterion);

        return new SearchConstraintCoverer(search,
                schema,
                valueFactory,
                Configuration.satisfyrows,
                Configuration.negaterows);
    }

    public static SearchConstraintCoverer alternatingValueDefaults(Schema schema,
            ValueFactory valueFactory,
            Random random,
            CellRandomiser cellRandomiser) {
        Search<Data> search = new AlternatingValueSearch(
                random,
                new NoDataInitialization(),
                new RandomDataInitializer(cellRandomiser));

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(search.getEvaluationsCounter(),
                Configuration.maxevaluations),
                new OptimumTerminationCriterion<>(search));

        search.setTerminationCriterion(terminationCriterion);

        return new SearchConstraintCoverer(search,
                schema,
                valueFactory,
                Configuration.satisfyrows,
                Configuration.negaterows);
    }

    public static SearchConstraintCoverer random(Schema schema,
            ValueFactory valueFactory,
            Random random,
            CellRandomiser cellRandomiser) {

        Search<Data> search = new RandomSearch(cellRandomiser);

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(search.getEvaluationsCounter(),
                Configuration.maxevaluations),
                new OptimumTerminationCriterion<>(search));

        search.setTerminationCriterion(terminationCriterion);

        return new SearchConstraintCoverer(
                search,
                schema,
                valueFactory,
                Configuration.satisfyrows,
                Configuration.negaterows);
    }

    public static NaiveRandomConstraintCoverer naiveRandom(Schema schema,
            ValueFactory valueFactory,
            Random random,
            CellRandomiser cellRandomiser) {
        return new NaiveRandomConstraintCoverer(schema,
                valueFactory,
                cellRandomiser,
                Configuration.naiverandom_rowspertable,
                Configuration.naiverandom_maxtriespertable);
    }

    public static DomainSpecificConstraintCoverer domainSpecific(Schema schema,
            ValueFactory valueFactory,
            Random random,
            CellRandomiser cellRandomiser) {
        return new DomainSpecificConstraintCoverer(
                schema,
                valueFactory,
                Configuration.satisfyrows,
                Configuration.negaterows,
                Configuration.maxevaluations,
                cellRandomiser,
                random);
    }
}
