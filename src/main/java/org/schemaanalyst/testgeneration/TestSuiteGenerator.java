package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;
import org.schemaanalyst.testgeneration.languagemodel.LMValueGenerator;
import org.schemaanalyst.testgeneration.languagemodel.LangModel;
import org.schemaanalyst.testgeneration.languagemodel.RandomValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by phil on 24/01/2014.
 */
public class TestSuiteGenerator {

	private final static Logger LOGGER = Logger.getLogger(TestSuiteGenerator.class.getName());

	private Schema schema;
	private TestRequirements testRequirements;
	private ValueFactory valueFactory;
	private DataGenerator dataGenerator;
	private HashMap<Table, Data> initialTableData;
	private TestSuite testSuite;
	private TestSuiteGenerationReport testSuiteGenerationReport;
	
	// For the Language Model
	private LangModel lm;
	private long seed;
	private String datagen = "";
	private boolean readability;
	
	// reduction
	private boolean fullreduce = false;


	public TestSuiteGenerator(Schema schema, TestRequirements testRequirements, ValueFactory valueFactory,
			DataGenerator dataGenerator) {
		this.schema = schema;
		this.testRequirements = testRequirements;
		this.valueFactory = valueFactory;
		this.dataGenerator = dataGenerator;

		initialTableData = new HashMap<>();
	}
	
	// Another Constructor to initilize the LM and AVM
	public TestSuiteGenerator(Schema schema, TestRequirements testRequirements, ValueFactory valueFactory,
			DataGenerator dataGenerator, String datagen, long seed, boolean readability, boolean fullreduce) {
		this.schema = schema;
		this.testRequirements = testRequirements;
		this.valueFactory = valueFactory;
		this.dataGenerator = dataGenerator;
		this.datagen = datagen;
		this.seed = seed;
		this.readability = readability;
		this.fullreduce = fullreduce;

		initialTableData = new HashMap<>();
	}

	public TestSuiteGenerator(Schema schema, TestRequirements testRequirements, ValueFactory valueFactory,
			DataGenerator dataGenerator, boolean fullreduce) {
		this.schema = schema;
		this.testRequirements = testRequirements;
		this.valueFactory = valueFactory;
		this.dataGenerator = dataGenerator;
		this.fullreduce = fullreduce;

		initialTableData = new HashMap<>();
	}

	public TestSuite generate() {
		LOGGER.fine("Generating test suite for " + schema);

		testSuite = new TestSuite();
		testSuiteGenerationReport = new TestSuiteGenerationReport();
		generateInitialTableData();
		generateTestCases();
		
		// Language Model
		if (datagen.toLowerCase().contains("langmodel") || this.readability) {
			try {
				lm = new LangModel("ukwac_char_lm");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Check if we are using the LM or not
		if (datagen.toLowerCase().contains("langmodel")) {
			this.convertingLMValuesToTestSuite();
			// this.langModelTestSuite();
		}
		// Calculate The test suite readability using a Language Model
		if (datagen.toLowerCase().contains("langmodel") || this.readability) {
			this.calculateReadabilityOfTestSuite();
		}
		
		return testSuite;
	}

	public TestSuiteGenerationReport getTestSuiteGenerationReport() {
		return testSuiteGenerationReport;
	}

	protected void generateInitialTableData() {
		for (Table table : schema.getTablesInOrder()) {

			ComposedPredicate acceptancePredicate = PredicateGenerator.generatePredicate(schema.getConstraints(table));

			// add not null predicates
			List<Column> notNullColumns = table.getColumns();

			/*
			 * // NOTE: selecting individual columns like this will cause AUCC
			 * test requirements to fail.
			 * 
			 * List<Column> notNullColumns = new ArrayList<>();
			 * PrimaryKeyConstraint primaryKeyConstraint =
			 * schema.getPrimaryKeyConstraint(table); if (primaryKeyConstraint
			 * != null) { // TODO: some check as to whether it's been added
			 * already ...
			 * notNullColumns.addAll(primaryKeyConstraint.getColumns()); } for
			 * (UniqueConstraint uniqueConstraint :
			 * schema.getUniqueConstraints(table)) {
			 * notNullColumns.addAll(uniqueConstraint.getColumns()); } for
			 * (ForeignKeyConstraint foreignKeyConstraint :
			 * schema.getForeignKeyConstraints(table)) {
			 * notNullColumns.addAll(foreignKeyConstraint.getColumns()); }
			 */
			AndPredicate predicate = new AndPredicate();
			predicate.addPredicate(acceptancePredicate);
			PredicateGenerator.addNullPredicates(predicate, table, notNullColumns, false);

			LOGGER.fine("\nGENERATING INITIAL TABLE DATA FOR " + table);
			LOGGER.fine("--- Predicate is " + predicate);

			// add referenced tables to the state
			Data state = new Data();
			boolean haveLinkedData = addInitialTableDataToState(state, table);
			if (haveLinkedData) {
				// generate the row
				Data data = new Data();
				data.addRow(table, valueFactory);
				DataGenerationReport dataGenerationReport = dataGenerator.generateData(data, state, predicate);
				if (dataGenerationReport.isSuccess()) {
					LOGGER.fine(
							"--- Success, generated in " + dataGenerationReport.getNumEvaluations() + " evaluations");
					LOGGER.fine("--- Data is: \n" + data);
					initialTableData.put(table, data);
				} else {
					LOGGER.fine("--- Failed");
				}

				testSuiteGenerationReport.addInitialTableDataResult(table,
						new DataGenerationResult(data, state, dataGenerationReport));
			} else {
				// there was no linked data generated to add to the state, so
				// generated of this row failed by default
				testSuiteGenerationReport.addInitialTableDataResult(table, null);
			}
		}
	}

	protected void generateTestCases() {
		int noneRemovedInsertsCount = 0;
		int reducedInsertsCount = 0;
		for (TestRequirement testRequirement : testRequirements.getTestRequirements()) {

			Predicate predicate = testRequirement.getPredicate();
			Table table = getTestRequirementTable(testRequirement);

			LOGGER.fine("\nGENERATING TEST CASE");
			for (TestRequirementDescriptor testRequirementDescriptor : testRequirement.getDescriptors()) {
				LOGGER.fine(testRequirementDescriptor.toString());
			}
			LOGGER.fine("--- Predicate is " + predicate);

			Data state = new Data();
			Data data = new Data();
			predicate = addAdditionalRows(state, data, predicate, table, testRequirement.getRequiresComparisonRow());

			if (predicate != null) {
				data.addRow(table, valueFactory);

				LOGGER.fine("--- Pre-reduced predicate is " + predicate);
				predicate = predicate.reduce();
				LOGGER.fine("--- Reduced predicate is " + predicate);

				DataGenerationReport dataGenerationReport = dataGenerator.generateData(data, state, predicate);
				if (dataGenerationReport.isSuccess()) {
					noneRemovedInsertsCount = noneRemovedInsertsCount + data.getNumRows() + state.getNumRows();
					testSuite.addGeneratedInserts(noneRemovedInsertsCount);
					if (fullreduce) {
						ReduecTestCase reduction = new ReduecTestCase();
						reduction.reduceData(data, state, schema);
						reducedInsertsCount = reducedInsertsCount + data.getNumRows() + state.getNumRows();
					}
					TestCase testCase = new TestCase(testRequirement, data, state);
					testSuite.addTestCase(testCase);
					testSuite.addReducedInsertsCount(reducedInsertsCount);
					noneRemovedInsertsCount = 0;
					reducedInsertsCount = 0;
					LOGGER.fine(
							"--- SUCCESS, generated in " + dataGenerationReport.getNumEvaluations() + " evaluations");
					LOGGER.fine("--- Data is \n" + data);
				} else {
					LOGGER.fine("--- FAILED");
				}

				testSuiteGenerationReport.addTestRequirementResult(testRequirement,
						new DataGenerationResult(data, state, dataGenerationReport));
			} else {
				testSuiteGenerationReport.addTestRequirementResult(testRequirement, null);
			}
		}
	}

	protected Table getTestRequirementTable(TestRequirement testRequirement) {
		Set<Table> tables = testRequirement.getTables();
		if (tables.size() != 1) {
			throw new TestGenerationException(
					"Test requirement  should have predicates involving exactly one table, has " + tables.size()
							+ ". Test requirement is: \n" + testRequirement);
		}
		return tables.iterator().next();
	}

	protected Predicate addAdditionalRows(Data state, Data data, Predicate predicate, Table table,
			boolean requiresComparisonRow) {
		LOGGER.fine("--- adding additional rows");

		boolean haveLinkedData = addInitialTableDataToState(state, table);
		if (!haveLinkedData) {
			return null;
		}

		if (requiresComparisonRow) { // if (getRequiresComparisonRow(predicate))
										// {
			Data comparisonRow = initialTableData.get(table);
			if (comparisonRow == null) {
				LOGGER.fine("--- could not add comparison row, data generation FAILED");
				return null;
			}

			LOGGER.fine("--- added comparison row");
			state.appendData(comparisonRow);

			predicate = addLinkedTableRowsToData(data, predicate, table);
		}
		return predicate;
	}

	protected boolean addInitialTableDataToState(Data state, Table table) {
		LOGGER.fine("--- adding initial data to state for linked tables");

		// add rows for tables linked via foreign keys to the state
		List<Table> linkedTables = schema.getConnectedTables(table);
		for (Table linkedTable : linkedTables) {

			// a row should always have been previously-generated
			// for a linked table
			if (!linkedTable.equals(table)) {
				Data initialData = initialTableData.get(linkedTable);

				// cannot generate data in this instance
				if (initialData == null) {
					return false;
				}
				state.appendData(initialData);
			}
		}
		return true;
	}

	protected Predicate addLinkedTableRowsToData(Data data, Predicate predicate, Table table) {

		for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {

			Table refTable = foreignKeyConstraint.getReferenceTable();
			if (!refTable.equals(table)) {

				boolean refColsUnique = areRefColsUnique(predicate, foreignKeyConstraint);

				if (refColsUnique) {
					LOGGER.fine("--- foreign key columns are unique in " + table);

					// append the predicate with the acceptance predicate of the
					// original
					AndPredicate newPredicate = new AndPredicate();
					newPredicate.addPredicate(predicate);
					newPredicate.addPredicate(PredicateGenerator.generatePredicate(schema.getConstraints(refTable)));
					predicate = newPredicate;

					LOGGER.fine("--- new predicate is " + predicate);

					LOGGER.fine("--- adding foreign key row for " + refTable);
					predicate = addLinkedTableRowsToData(data, predicate, refTable);
					data.addRow(refTable, valueFactory);
				}
			}
		}

		return predicate;
	}

	protected boolean areRefColsUnique(Predicate predicate, ForeignKeyConstraint foreignKeyConstraint) {
		// Table table = foreignKeyConstraint.getTable();
		final List<Column> uniqueColumns = new ArrayList<>();

		/*
		 * if (schema.hasPrimaryKeyConstraint(table)) {
		 * uniqueColumns.addAll(schema.getPrimaryKeyConstraint(table).getColumns
		 * ()); }
		 * 
		 * for (UniqueConstraint uniqueConstraint :
		 * schema.getUniqueConstraints(table)) {
		 * uniqueColumns.addAll(uniqueConstraint.getColumns()); }
		 */

		predicate.accept(new PredicateAdaptor() {
			@Override
			public void visit(MatchPredicate predicate) {
				if (predicate.tableIsRefTable()) {
					uniqueColumns.addAll(predicate.getColumns());
				}
			}
		});

		for (Column column : foreignKeyConstraint.getColumns()) {
			if (!uniqueColumns.contains(column)) {
				return false;
			}
		}

		return true;
	}
	
	// ========= Language Model Methods =========
	protected double langModelScore(String string) {
		try {
			return lm.score(string, false);
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return 0;
		}
	}

	private void convertingLMValuesToTestSuite() {
		LMValueGenerator randWords = new LMValueGenerator(this.seed);
		List<RandomValue> generateListOfString = randWords.generateListOfString(lm);
		HashMap<String, StringValue> stringMaps = new HashMap<>();
		List<CheckConstraint> checks = schema.getCheckConstraints();

		for (TestCase tc : testSuite.getTestCases()) {
			List<Cell> allCells = new ArrayList<Cell>();
			allCells.addAll(tc.getState().getCells());
			allCells.addAll(tc.getData().getCells());
			for (Cell cell : allCells) {
				if (cell.getValueInstance() instanceof StringValue) {
					if (!cell.isNull() && cell.getValue() != null) {
						StringValue oldStringValue = (StringValue) cell.getValue();
						boolean isItInTheMap = stringMaps.containsKey(oldStringValue.get());
						boolean isItInTheMapAsAValue = stringMaps.containsValue(oldStringValue);
						boolean inCheck = false;
						for (CheckConstraint c : checks) {
							if (c.getExpression().getColumnsInvolved().contains(cell.getColumn()))
								inCheck = true;
						}
						if (!inCheck) {
							StringValue newString = null;
							// is it in the map as key
							if (isItInTheMap) {
								// yes
								newString = stringMaps.get(oldStringValue.get());
								cell.setValue(newString);
							} else {
								// No
								// Is it as a value?
								if (!isItInTheMapAsAValue) {
									// yes
									newString = new StringValue(
											generateListOfString.get(generateListOfString.size() - 1).getValue());
									StringValue oldString = (StringValue) cell.getValue();
									if (oldString.getMaxLength() != -1) {
										if (newString.get().length() > oldString.getMaxLength()) {
											int end1 = newString.get().length() - oldString.getMaxLength();
											int end = newString.get().length() - end1;
											newString.set(newString.get().substring(0, end));
										}
									}
									stringMaps.put(oldString.get(), newString);
									cell.setValue(newString);
									generateListOfString.remove(generateListOfString.size() - 1);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void calculateReadabilityOfTestSuite() {
		for (TestCase tc : testSuite.getTestCases()) {
			// Scoring Readable strings and VarChars
			double readableScore = 0;
			// double readableData = 0;
			List<Cell> allCells = new ArrayList<Cell>();
			allCells.addAll(tc.getData().getCells());
			allCells.addAll(tc.getState().getCells());
			for (Cell cell : allCells) {
				if (cell.getValueInstance() instanceof StringValue) {
					if (!cell.isNull() && !cell.getValue().toString().equals("''")) {
						String value = cell.getValue().toString();
						readableScore += this.langModelScore(value.replaceAll("\'", ""));
						// int l = cell.getValue().toString().length();
						testSuite.addlengthOfStrings(cell.getValue().toString().length());
					}

					if (cell.getValue().toString().equals("''")) {
						testSuite.addnumberOfEmptyStrings(1);
					}
				}
			}
			// Adding the total score of T-suffiecnt and test data
			// together
			testSuite.addReadableScore(readableScore);

		}
	}
}