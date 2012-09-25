package org.schemaanalyst.datageneration.search;

import java.lang.reflect.Method;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizationProfiles;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizer;
import org.schemaanalyst.datageneration.search.datainitialization.RandomDataInitializer;
import org.schemaanalyst.datageneration.search.datainitialization.NoDataInitialization;
import org.schemaanalyst.datageneration.search.termination.CombinedTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.CounterTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.OptimumTerminationCriterion;
import org.schemaanalyst.datageneration.search.termination.TerminationCriterion;
import org.schemaanalyst.util.random.Random;
import org.schemaanalyst.util.random.SimpleRandom;

public class SearchFactory {
	
	@SuppressWarnings("unchecked")
	public static Search<Data> instantiate(String searchName, long randomSeed, int maxEvaluations) {
    	Class<SearchFactory> c = SearchFactory.class;
    	Method methods[] = c.getMethods();
    	
    	for (Method m: methods) {
    		if (m.getName().equals(searchName)) {
    			try {
    				Object[] args = {randomSeed, maxEvaluations};
    				return (Search<Data>) m.invoke(null, args);
    			} catch (Exception e) {
    				throw new RuntimeException(e);
    			}
    		}
    	}
    	
    	throw new SearchException("Unknown search \""+searchName+"\"");	
	}
	
	public static Search<Data> regularAlternatingValue(long randomSeed, int maxEvaluations) {
		Random random = new SimpleRandom(randomSeed);
		CellRandomizer randomizer = CellRandomizationProfiles.small(random);
		
		Search<Data> search = new AlternatingValueSearch(
									random,
									new NoDataInitialization(), 
									// new RandomDataInitializer(randomizer),
									new RandomDataInitializer(randomizer));
		
		TerminationCriterion terminationCriterion 
				= new CombinedTerminationCriterion(
						new CounterTerminationCriterion(search.getEvaluationsCounter(), maxEvaluations),
						new OptimumTerminationCriterion<>(search));		
		
		search.setTerminationCriterion(terminationCriterion);
		
		return search;
	}	
	
	public static Search<Data> regularRandom(long randomSeed, int maxEvaluations) {
		Random random = new SimpleRandom(randomSeed);
		CellRandomizer profile = CellRandomizationProfiles.small(random);
		RandomSearch search = new RandomSearch(profile);
		
		TerminationCriterion terminationCriterion 
		= new CombinedTerminationCriterion(
				new CounterTerminationCriterion(search.getEvaluationsCounter(), maxEvaluations),
				new OptimumTerminationCriterion<>(search));		

		search.setTerminationCriterion(terminationCriterion);

		return search;
	}
}
