package org.schemaanalyst.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.schemaanalyst.test.data.TestRow3VL.class,
	org.schemaanalyst.test.data.TestStringValue.class,
	org.schemaanalyst.test.data.TestValueEquality.class,
	org.schemaanalyst.test.datageneration.search.TestAlternatingValueSearch.class,
	org.schemaanalyst.test.datageneration.search.TestSearchEvaluation.class,
	org.schemaanalyst.test.datageneration.search.domainspecific.TestNotNullHandler.class,
	org.schemaanalyst.test.datageneration.search.domainspecific.TestReferenceHandler.class,
	org.schemaanalyst.test.datageneration.search.domainspecific.TestUniqueHandler.class,
	org.schemaanalyst.test.datageneration.search.objective.TestDistanceObjectiveValue.class,
	org.schemaanalyst.test.datageneration.search.objective.TestObjectiveValue.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestNullColumnObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestReferenceObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestUniqueObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestAndExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestBetweenExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestInExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestNullExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestOrExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestRelationalExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestBooleanValueRelationalObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestCompoundValueRelationalObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestMultiValueObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestNullValueObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestNumericValueRelationalObjectiveFunction.class,
	org.schemaanalyst.test.logic.TestRelationalOperator.class,
	org.schemaanalyst.test.mutation.TestMutationReport.class,
	org.schemaanalyst.test.mutation.TestMutationScoreCalculation.class,
	org.schemaanalyst.test.mutation.TestSQLInsertRecord.class,
	org.schemaanalyst.test.util.runner.TestRunner.class
})

public class AllTests {}

