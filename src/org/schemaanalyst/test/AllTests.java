package org.schemaanalyst.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.schemaanalyst.test.data.TestStringValue.class,
	org.schemaanalyst.test.data.TestValueEquality.class,
	org.schemaanalyst.test.datageneration.search.TestAlternatingValueSearch.class,
	org.schemaanalyst.test.datageneration.search.TestSearchEvaluation.class,
	org.schemaanalyst.test.datageneration.search.directedrandom.TestExpressionConstraintHandler.class,
	org.schemaanalyst.test.datageneration.search.directedrandom.TestNullConstraintHandler.class,
	org.schemaanalyst.test.datageneration.search.objective.TestDistanceObjectiveValue.class,
	org.schemaanalyst.test.datageneration.search.objective.TestObjectiveValue.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestExpressionConstraintObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestNullConstraintObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestReferenceConstraintObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestUniqueConstraintObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestAndExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestBetweenExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestInExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestNullExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestOrExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestRelationalExpressionObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestBooleanValueRelationalObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestCompoundValueRelationalObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestMultiValueEqualsObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestNullValueObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestNumericValueRelationalObjectiveFunction.class,
	org.schemaanalyst.test.logic.TestRelationalOperator.class,
	org.schemaanalyst.test.mutation.TestMutationReport.class,
	org.schemaanalyst.test.mutation.TestMutationScoreCalculation.class,
	org.schemaanalyst.test.mutation.TestSQLInsertRecord.class,
	org.schemaanalyst.test.sqlrepresentation.expression.TestExpression.class,
	org.schemaanalyst.test.util.runner.TestRunner.class
})

public class AllTests {}

