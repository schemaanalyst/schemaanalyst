package org.schemaanalyst.datageneration.search;

import java.util.HashMap;
import java.util.Map;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.SimpleConstraintCoverageReport;
import org.schemaanalyst.datageneration.cellrandomization.CellRandomizer;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;

public class NaiveRandomConstraintCoverer extends DataGenerator {

	protected Schema schema;
	protected ConstraintEvaluator constraintEvaluator;
	protected ValueFactory valueFactory;
	protected CellRandomizer randomizer;
	protected int rowsPerTable, maxTriesPerTable;	
	protected Map<Table, Integer> rowsAddedForEachTable;
	
	public NaiveRandomConstraintCoverer(Schema schema,
										ValueFactory valueFactory,
										CellRandomizer randomizer,
										int rowsPerTable, 
										int maxTriesPerTable) {
		this.schema = schema;
		this.valueFactory = valueFactory;
		this.randomizer = randomizer;
		this.rowsPerTable = rowsPerTable;
		this.maxTriesPerTable = maxTriesPerTable;
		
		rowsAddedForEachTable = new HashMap<>();
		constraintEvaluator = new ConstraintEvaluator();
	}

	public SimpleConstraintCoverageReport generate() {
		constraintEvaluator.initialize(schema);
		int totalNumTries = 0;
		
		for (Table table : schema.getTables()) {
			int successfulRows = 0;
			int numTries = 0;
					
			while (successfulRows < rowsPerTable && numTries < maxTriesPerTable) {
			
				Data data = new Data();
				Row row = data.addRow(table, valueFactory);
				randomizer.randomizeCells(row.getCells());
				
				boolean rowAdded = constraintEvaluator.evaluate(row, table);
				
				if (rowAdded) {
					successfulRows ++;
				}
				numTries ++;
				totalNumTries ++;
			}
			
			rowsAddedForEachTable.put(table, successfulRows);
		}
		
		SimpleConstraintCoverageReport report = constraintEvaluator.getCoverageReport();
		report.setNumAttempts(totalNumTries);
		return report;
	}
}
