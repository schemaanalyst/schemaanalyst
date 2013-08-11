package org.schemaanalyst.datageneration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiserFactory;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.AlternatingValueSearch;
import org.schemaanalyst.datageneration.search.DirectedRandomSearch;
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
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;

public class ConstraintCovererFactory {

	// defaults to all techniques but Naive Random
	public static final int DEFAULT_NUM_SATISFY_ROWS = 2;
	public static final int DEFAULT_NUM_NEGATE_ROWS  = 1;
	
	// defaults specific to Naive Random
	public static final int DEFAULT_NAIVE_RND_NUM_ROWS_PER_TABLE  = 50;
	public static final int DEFAULT_NAIVE_RND_MAX_TRIES_PER_TABLE = 1000;
	
    /*****************************************************************************************************************************/    
    /*                                                                                                                           */
    /*  FACTORY METHODS                                                                                                          */
    /*                                                                                                                           */
    /*****************************************************************************************************************************/	
	
	public static DataGenerator instantiate(String name,
                                            Schema schema,
                                            DBMS dbms,
                                            String cellRandomisationProfile,
                                            long seed,
                                            int maxEvaluations) {

        // get hold of the method objects of this class 
        Class<ConstraintCovererFactory> clazz = ConstraintCovererFactory.class;
        Method methods[] = clazz.getMethods();

        // get the name to match a method name by lowercasing the first letter
        char characters[] = name.toCharArray();
        characters[0] = Character.toLowerCase(characters[0]);
        name = new String(characters);

        // find the method for instantiating the right data generator
        for (Method m : methods) {
            if (m.getName().equals(name)) {                
                Object[] args = {schema, dbms, cellRandomisationProfile, seed, maxEvaluations};
                try {
					return (DataGenerator) m.invoke(null, args);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}   
            }
        }

        throw new UnknownDataGeneratorException("Unknown data generator \"" + name + "\"");
    }
	
    // TODO: automatically generate this list!	
    public static List<String> getConstraintCovererChoices() {
        List<String> choices = new ArrayList<>();
        choices.add("AlternatingValue");
        choices.add("AlternatingValueDefaults");
        choices.add("Random");
        choices.add("DirectedRandom");
        choices.add("NaiveRandom");        
        return choices;
    }	
	
    
    /*****************************************************************************************************************************/    
    /*                                                                                                                           */
    /*  ALTERNATING VARIABLE SEARCHES                                                                                            */
    /*                                                                                                                           */
    /*****************************************************************************************************************************/
    
    public static SearchConstraintCoverer alternatingValue(Schema schema,
                                                           DBMS dbms,
                                                           String cellRandomisationProfile,
                                                           long seed,
                                                           int maxEvaluations) {
    	Random random = new SimpleRandom(seed);    
    	CellRandomiser cellRandomiser = CellRandomiserFactory.instantiate(cellRandomisationProfile, random);
    	
        Search<Data> avs = new AlternatingValueSearch(
        		random, 
        		new RandomDataInitializer(cellRandomiser),
                new RandomDataInitializer(cellRandomiser));

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(avs.getEvaluationsCounter(), maxEvaluations),
                new OptimumTerminationCriterion<>(avs));

        avs.setTerminationCriterion(terminationCriterion);

        return new SearchConstraintCoverer(avs, schema, dbms, 
        		                           DEFAULT_NUM_SATISFY_ROWS, 
        		                           DEFAULT_NUM_NEGATE_ROWS);
    }

    public static SearchConstraintCoverer alternatingValueDefaults(Schema schema,
                                                                   DBMS dbms,
                                                                   String cellRandomisationProfile,
                                                                   long seed,
                                                                   int maxEvaluations) {
    	Random random = new SimpleRandom(seed);    
    	CellRandomiser cellRandomiser = CellRandomiserFactory.instantiate(cellRandomisationProfile, random);
    	
    	Search<Data> avs = new AlternatingValueSearch(random,
                                                         new NoDataInitialization(),
                                                         new RandomDataInitializer(cellRandomiser));

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(avs.getEvaluationsCounter(),
                maxEvaluations),
                new OptimumTerminationCriterion<>(avs));

        avs.setTerminationCriterion(terminationCriterion);

        return new SearchConstraintCoverer(avs, schema, dbms, 
        		                           DEFAULT_NUM_SATISFY_ROWS, 
        		                           DEFAULT_NUM_NEGATE_ROWS);
    }

    
    /*****************************************************************************************************************************/    
    /*                                                                                                                           */
    /*  RANDOM SEARCHES                                                                                                          */
    /*                                                                                                                           */
    /*****************************************************************************************************************************/
    
    public static SearchConstraintCoverer random(Schema schema,
                                                 DBMS dbms,
                                                 String cellRandomisationProfile,
                                                 long seed,
                                                 int maxEvaluations) {
    	Random random = new SimpleRandom(seed);    
    	CellRandomiser cellRandomiser = CellRandomiserFactory.instantiate(cellRandomisationProfile, random);
    	
        Search<Data> rs = new RandomSearch(cellRandomiser);

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(rs.getEvaluationsCounter(),
                maxEvaluations),
                new OptimumTerminationCriterion<>(rs));

        rs.setTerminationCriterion(terminationCriterion);

        return new SearchConstraintCoverer(rs, schema, dbms, 
        								   DEFAULT_NUM_SATISFY_ROWS, 
        								   DEFAULT_NUM_NEGATE_ROWS);
    }
    
    public static SearchConstraintCoverer directedRandom(Schema schema,
                                                         DBMS dbms,
                                                         String cellRandomisationProfile,
                                                         long seed,
                                                         int maxEvaluations) {
        Random random = new SimpleRandom(seed);    
        CellRandomiser cellRandomiser = CellRandomiserFactory.instantiate(cellRandomisationProfile, random);

        Search<Data> drs = new DirectedRandomSearch(
                random,
                new NoDataInitialization(),
                cellRandomiser);

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(
                        drs.getEvaluationsCounter(),
                        maxEvaluations),
                        new OptimumTerminationCriterion<>(drs));

        drs.setTerminationCriterion(terminationCriterion);

        return new SearchConstraintCoverer(drs, schema, dbms, 
                                           DEFAULT_NUM_SATISFY_ROWS, 
                                           DEFAULT_NUM_NEGATE_ROWS);
}    
    
    public static NaiveRandomConstraintCoverer naiveRandom(Schema schema,
                                                           DBMS dbms,
                                                           String cellRandomisationProfile,
                                                           long seed,
                                                           int maxEvaluations) {
    	Random random = new SimpleRandom(seed);    
    	CellRandomiser cellRandomiser = CellRandomiserFactory.instantiate(cellRandomisationProfile, random);
    	
        return new NaiveRandomConstraintCoverer(schema, dbms, cellRandomiser,
                                                DEFAULT_NAIVE_RND_NUM_ROWS_PER_TABLE, 
                                                maxEvaluations);
    }

}
