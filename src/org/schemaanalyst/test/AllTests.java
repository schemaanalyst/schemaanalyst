package org.schemaanalyst.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.schemaanalyst.test.collection.TestLinkedHashBacking.class,
	org.schemaanalyst.test.collection.TestName.class,
	org.schemaanalyst.test.collection.TestNamedEntityInsertOrderedSet.class,
	org.schemaanalyst.test.data.TestStringValue.class,
	org.schemaanalyst.test.data.TestValueEquality.class,
	org.schemaanalyst.test.datageneration.search.TestAlternatingValueSearch.class,
	org.schemaanalyst.test.datageneration.search.TestSearchEvaluation.class,
	org.schemaanalyst.test.datageneration.search.directedrandom.data.TestExpressionColumnHandler.class,
	org.schemaanalyst.test.datageneration.search.directedrandom.data.TestNullColumnHandler.class,
	org.schemaanalyst.test.datageneration.search.directedrandom.data.TestReferenceColumnHandler.class,
	org.schemaanalyst.test.datageneration.search.directedrandom.data.TestUniqueColumnHandler.class,
	org.schemaanalyst.test.datageneration.search.objective.TestDistanceObjectiveValue.class,
	org.schemaanalyst.test.datageneration.search.objective.TestObjectiveValue.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestExpressionColumnObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestNullColumnObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestReferenceColumnObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.data.TestUniqueColumnObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestAndExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestBetweenExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestInExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestNullExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestOrExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.row.TestRelationalExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestEqualsMultiValueObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestNullValueObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestRelationalBooleanValueObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestRelationalCompoundValueObjectiveFunction.class,
	org.schemaanalyst.test.datageneration.search.objective.value.TestRelationalNumericValueObjectiveFunction.class,
	org.schemaanalyst.test.logic.TestRelationalOperator.class,
	org.schemaanalyst.test.mutation.artefactsupplier.TestSchemaPrimaryKeySupplier.class,
	org.schemaanalyst.test.sqlrepresentation.TestColumn.class,
	org.schemaanalyst.test.sqlrepresentation.TestSchema.class,
	org.schemaanalyst.test.sqlrepresentation.TestTable.class,
	org.schemaanalyst.test.sqlrepresentation.constraint.TestConstraints.class,
	org.schemaanalyst.test.sqlrepresentation.datatype.TestDataTypes.class,
	org.schemaanalyst.test.sqlrepresentation.expression.TestExpression.class,
	org.schemaanalyst.test.sqlrepresentation.expression.TestExpressions.class,
	org.schemaanalyst.test.util.runner.TestRunner.class,
	org.schemaanalyst.test.util.sql.TestSQLRepairer.class
})

public class AllTests {}

