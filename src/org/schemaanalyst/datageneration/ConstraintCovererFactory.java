package org.schemaanalyst.datageneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiserFactory;
import org.schemaanalyst.datageneration.search.*;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
	
	@SuppressWarnings("unchecked")
	public static DataGenerator<ConstraintGoal> instantiate(String name,
                                            				Schema schema,
                                            				DBMS dbms,
                                            				String cellRandomisationProfile,
                                            				long seed,
                                            				int maxEvaluations) {
        return instantiate(name, schema, dbms, cellRandomisationProfile, seed, maxEvaluations, DEFAULT_NUM_SATISFY_ROWS, DEFAULT_NUM_NEGATE_ROWS);
    }
    
    @SuppressWarnings("unchecked")
	public static DataGenerator<ConstraintGoal> instantiate(String name,
                                            				Schema schema,
                                            				DBMS dbms,
                                            				String cellRandomisationProfile,
                                            				long seed,
                                            				int maxEvaluations,
                                                            int satisfyRows,
                                                            int negateRows) {
        //TODO: Convert this method to use String switch rather than reflection
        
        // Emit warning message for ignored parameters
        if (name.equals("naiveRandom")) {
            Logger logger = Logger.getLogger(ConstraintCovererFactory.class.getName());
            logger.warning("Values for 'satisfyRows' and 'negateRows' are ignored when using 'naiveRandom'");
        }
        
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
                Object[] args = {schema, dbms, cellRandomisationProfile, seed, maxEvaluations, satisfyRows, negateRows};
                try {
					return (DataGenerator<ConstraintGoal>) m.invoke(null, args);
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
                                            				int maxEvaluations,
                                                            int satisfyRows,
                                                            int negateRows) {
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
        		                           satisfyRows, 
        		                           negateRows);
    }

    public static SearchConstraintCoverer alternatingValueDefaults(Schema schema,
                                            				DBMS dbms,
                                            				String cellRandomisationProfile,
                                            				long seed,
                                            				int maxEvaluations,
                                                            int satisfyRows,
                                                            int negateRows) {
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
        		                           satisfyRows, 
        		                           negateRows);
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
                                                    int maxEvaluations,
                                                    int satisfyRows,
                                                    int negateRows) {
    	Random random = new SimpleRandom(seed);    
    	CellRandomiser cellRandomiser = CellRandomiserFactory.instantiate(cellRandomisationProfile, random);
    	
        Search<Data> rs = new RandomSearch(cellRandomiser);

        TerminationCriterion terminationCriterion = new CombinedTerminationCriterion(
                new CounterTerminationCriterion(rs.getEvaluationsCounter(),
                maxEvaluations),
                new OptimumTerminationCriterion<>(rs));

        rs.setTerminationCriterion(terminationCriterion);

        return new SearchConstraintCoverer(rs, schema, dbms, 
        								   satisfyRows, 
        								   negateRows);
    }
    
    public static SearchConstraintCoverer directedRandom(Schema schema,
                                            				DBMS dbms,
                                            				String cellRandomisationProfile,
                                            				long seed,
                                            				int maxEvaluations,
                                                            int satisfyRows,
                                                            int negateRows) {
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
                                           satisfyRows, 
                                           negateRows);
    }
}
