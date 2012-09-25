package org.schemaanalyst.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.schemaanalyst.test.data.*;
import org.schemaanalyst.test.datageneration.analyst.*;
import org.schemaanalyst.test.datageneration.domainspecific.*;
import org.schemaanalyst.test.datageneration.search.*;
import org.schemaanalyst.test.datageneration.search.objective.*;
import org.schemaanalyst.test.mutation.*;

import experiment.test.TestExperimentalResults;

/**
 * AllTests JUnit Suite
 * @author Phil McMinn
 *
 */
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
    // org.schemaanalyst.test.data.value
	TestRow3VL.class,
	TestStringValue.class,
	TestValueEquality.class,
	
    // org.schemaanalyst.test.datageneration.search
    TestAlternatingValueSearch.class,
    TestSearchEvaluation.class,

    // org.schemaanalyst.test.datageneration.analyst    
    TestBetweenAnalyst.class,
    TestInAnalyst.class,    
    TestNotNullAnalyst.class,
    TestReferenceAnalyst.class,
    TestRelationalPredicateAnalyst.class,
    TestUniqueAnalyst.class,
    
    // org.schemaanalyst.test.datageneration.domainspecific
    TestNotNullHandler.class,
    TestReferenceHandler.class,
    TestUniqueHandler.class,
    
    // org.schemaanalyst.test.datageneration.search.objective
    TestDistanceObjectiveValue.class,
    TestObjectiveValue.class,
    
    TestBetweenCheckPredicateObjectiveFunction.class,        
    TestReferenceObjectiveFunction.class,
    TestInCheckPredicateObjectiveFunction.class,
    TestRelationalPredicateObjectiveFunction.class,
    TestUniqueObjectiveFunction.class,
    
    TestBooleanValueObjectiveFunction.class,
    TestCompoundValueObjectiveFunction.class,
    TestNullColumnObjectiveFunction.class,
    TestNullValueObjectiveFunction.class,
    TestNumericalValueObjectiveFunction.class,
    TestRowObjectiveFunction.class,
    
    // org.schemaanalyst.test.mutation
    TestFileFinderAndLineExtractor.class,
    TestSQLInsertRecord.class,	
    TestMutationReport.class,
    TestMutationScoreCalculation.class,

})

public class AllTests {
}