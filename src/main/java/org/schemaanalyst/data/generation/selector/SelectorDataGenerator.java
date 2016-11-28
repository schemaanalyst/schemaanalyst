package org.schemaanalyst.data.generation.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.ValueLibrary;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellinitialization.SelectorCellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.SelectorCellValueGenerator;
import org.schemaanalyst.data.generation.cellvaluegeneration.ValueInitializationProfile;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.predicate.PredicateObjectiveFunctionFactory;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.util.DataMapper;
import org.schemaanalyst.sqlrepresentation.*;
import org.schemaanalyst.util.random.*;

/**
 * Created by Abdullah on 24/08/2016.
 *
 *
 */
public class SelectorDataGenerator extends DataGenerator {
	protected SelectorCellValueGenerator selectorCellValueGenerator;
	protected int maxEvaluations;
	protected ObjectiveFunction<Data> objectiveFunction;
	private Random random;
    protected CellInitializer cellInitializer;
    private long randomSeed;
    private Schema schema;


	public SelectorDataGenerator(int maxEvaluations, long randomSeed) {
		this.maxEvaluations = maxEvaluations;
		this.randomSeed = randomSeed;
	}
	
	public SelectorDataGenerator(int maxEvaluations,
			SelectorCellValueGenerator selectorCellValueGenerator,
            CellInitializer cellInitializer, long randomSeed) {
        this.maxEvaluations = maxEvaluations;
        this.selectorCellValueGenerator = selectorCellValueGenerator;
        this.cellInitializer = cellInitializer;
		this.randomSeed = randomSeed;
	}

	public DataGenerationReport generateData(Data data, Data state, Predicate predicate) {
		random = new SimpleRandom(randomSeed);
		//Data readableValues = this.generateReadableValues();
		selectorCellValueGenerator = new SelectorCellValueGenerator(random, ValueInitializationProfile.SMALL, 0.1, makeValueLibrary(state), 0.25);
		cellInitializer = new SelectorCellInitializer(selectorCellValueGenerator);

		initialize(data, state, predicate);
		//this.selctorGenerator(data, state);

		boolean success = objectiveFunction.evaluate(data).isOptimal();
		int evaluations = 1;
		while (!success && evaluations < maxEvaluations) {
			attemptFix_orginal(data);
			evaluations++;
			success = objectiveFunction.evaluate(data).isOptimal();
		}
		/*
	    if (!success && evaluations == maxEvaluations) {
			System.out.println("=======================================================================================================");
			System.out.println(data);
			System.out.println(state);
			System.out.println("=======================================================================================================");
		}
		*/
		return new DataGenerationReport(success, evaluations);
	}

	protected void initialize(Data data, Data state, Predicate predicate) {
        cellInitializer.initialize(data);
        //System.out.println(data);
		//this.selctorGenerator(data, state, 10);
		objectiveFunction = PredicateObjectiveFunctionFactory.createObjectiveFunction(predicate, state);
	}
	
    protected void attemptFix_orginal(Data data) {
        for (Cell cell : data.getCells()) {
            selectorCellValueGenerator.generateCellValue(cell);
        }
    }
    
	protected void attemptFix(Data data) {
		//Generate new row out of the old data and generate random cells if you can
		for (Table table : data.getTables()) {
			Row row  = data.getRows(table).get(ThreadLocalRandom.current().nextInt(data.getRows(table).size()));
			List<Cell> new_cells = new ArrayList<Cell>();
			for (Cell cell : row.getCells()) {
				RandomReadableValueGenerator vf = new RandomReadableValueGenerator();
				Value val = vf.createValue(cell.getColumn().getDataType(), cell.getValue());
				Cell new_cell = new Cell(cell.getColumn(), new ValueFactory());
				new_cell.setValue(val);
				boolean setNewCell = ThreadLocalRandom.current().nextInt(5) == 0;
				if (setNewCell) {
					boolean setToNull = ThreadLocalRandom.current().nextInt(5) == 0;
					if (setToNull) {
						new_cell.setNull(true);
					}
				} else {
					new_cell = cell;
				}
				new_cells.add(new_cell);
			}
			
			Row new_row = new Row(table, new_cells);
			data.addRow(table, new_row);
		}
	}

	protected void selctorGenerator(Data data, Data state) {
		for (Table table : state.getTables()) {
			List<Row> new_rows = new ArrayList<Row>();
			for (int i = 0; i < state.getNumRows(table); i++) {
				List<Cell> new_cells = new ArrayList<Cell>();
				for (Column col : table.getColumns()) {
					List<Cell> cells = state.getCells(table, col);
					int index = ThreadLocalRandom.current().nextInt(cells.size());
					Cell old_cell = cells.get(index);
					Cell new_cell = new Cell(col, new ValueFactory());
					new_cell.setValue(old_cell.getValue());
					/*
					boolean setToNull = ThreadLocalRandom.current().nextInt(5) == 0;
					if (setToNull) {
						new_cell.setNull(true);
						new_cells.add(new_cell);
					} else {
						new_cells.add(new_cell);
					}
					*/
					new_cells.add(new_cell);
				}
				new_rows.add(new Row(table, new_cells));
			}

			data.addRows(table, new_rows);
		}
	}
	
    private static ValueLibrary makeValueLibrary(Data state) {
    	ValueLibrary vallib = new ValueLibrary();
    	for (Cell cell : state.getCells()) {
    		if (!cell.isNull())
    			vallib.addValue(cell.getValue());
    	}
    	return vallib;
    }
    
    public void setSchema(Schema schema) {
    	this.schema = schema;
    }
    
    private Data generateReadableValues() {
		DataMapper mapper = new DataMapper();
		mapper.connectDB(this.schema);
		mapper.mapData();
		return mapper.getData();
    }

}
