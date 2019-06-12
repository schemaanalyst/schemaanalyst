package org.schemaanalyst.testgeneration;

import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

public class StateGenerator extends TestSuiteGenerator {

	public StateGenerator(Schema schema, TestRequirements testRequirements, ValueFactory valueFactory,
			DataGenerator dataGenerator) {
		super(schema, testRequirements, valueFactory, dataGenerator);
	}

	public TestSuite generateStateData() {
		testSuite = new TestSuite();
		testSuiteGenerationReport = new TestSuiteGenerationReport();
		generateInitialTableData();
        generateTestCases();
		return testSuite;
	}

	protected void generateInitialTableData() {
		for (Table table : schema.getTablesInOrder()) {

			ComposedPredicate acceptancePredicate = PredicateGenerator.generatePredicate(schema.getConstraints(table));

			// add not null predicates
			List<Column> notNullColumns = table.getColumns();

			AndPredicate predicate = new AndPredicate();
			predicate.addPredicate(acceptancePredicate);
			PredicateGenerator.addNullPredicates(predicate, table, notNullColumns, false);

			// add referenced tables to the state
			Data state = new Data();
			boolean haveLinkedData = addInitialTableDataToState(state, table);
			if (haveLinkedData) {
				// generate the row
				Data data = new Data();
				data.addRow(table, valueFactory);
				DataGenerationReport dataGenerationReport = dataGenerator.generateData(data, state, predicate);
				if (dataGenerationReport.isSuccess()) {
					initialTableData.put(table, data);
				} else {
				}

				testSuiteGenerationReport.addInitialTableDataResult(table,
						new DataGenerationResult(data, state, dataGenerationReport));
			} else {
				// there was no linked data generated to add to the state, so generated of this
				// row failed by default
				testSuiteGenerationReport.addInitialTableDataResult(table, null);
			}
		}
	}

	protected void generateTestCases() {
		for (TestRequirement testRequirement : testRequirements.getTestRequirements()) {
			Predicate predicate = testRequirement.getPredicate();
            Table table = getTestRequirementTable(testRequirement);

			Data state = new Data();
			Data data = new Data();
            predicate = addAdditionalRows(state, data, predicate, table, testRequirement.getRequiresComparisonRow());


			if (predicate != null) {
				predicate = predicate.reduce();

				TestCase testCase = new TestCase(testRequirement, data, state);
				testSuite.addTestCase(testCase);

			} else {
				testSuiteGenerationReport.addTestRequirementResult(testRequirement, null);
			}
		}
	}

}
