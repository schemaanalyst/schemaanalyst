package org.schemaanalyst.data.generation.dravs;

import org.schemaanalyst.data.*;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.search.Search;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.util.random.Random;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class AlternatingValueSearchInner extends SearchMini<Data> {

    protected static final int ACCELERATION_BASE = 2;
    protected Random random;
    protected CellInitializer startInitialiser;
    protected CellInitializer restartInitialiser;
    protected Data subsetData; // protected so that test class can access
    protected Data mainData;


	protected List<Cell> cells;
    protected ObjectiveValue lastObjVal;

    public AlternatingValueSearchInner(Random random,
                                  CellInitializer startInitializer,
                                  CellInitializer restartInitializer) {
        super(new Data.Duplicator());
        this.random = random;
        this.startInitialiser = startInitializer;
        this.restartInitialiser = restartInitializer;
        //this.mainData = mainData;
    }
    
    public void setMainData(Data mainData) {
		this.mainData = mainData;
	}

    @Override
    public void search(Data data, PredicateChecker checker, List<Cell> cells) {
        // set up
        this.subsetData = data;
        //cells = data.getCells();
    	this.cells = cells;

        // start
        // startInitialiser.initialize(data);
        lastObjVal = null;
    	evaluate();


        if (!checker.check() && terminationCriterion.satisfied()) {
        	System.err.println("TEST");
        }

        // main loop
        while (!terminationCriterion.satisfied()) {

            alternateThroughValues();
            
            if (checker.check()) {
            	break;
            }

            if (!terminationCriterion.satisfied()) {
                restartsCounter.increment();
            }

            if (!terminationCriterion.satisfied()) {
                restartInitialiser.initialize(subsetData);
                lastObjVal = null;
                evaluate();
            }
        }
    }
    
    @Override
    public void search(List<Cell> cells, PredicateChecker checker) {
        // set up
        //this.subsetData = data;
        //cells = data.getCells();
    	this.cells = cells;

        // start
        // startInitialiser.initialize(data);
        lastObjVal = null;
    	evaluate();


        if (!checker.check() && terminationCriterion.satisfied()) {
        	System.err.println("TEST");
            restartInitialiser.initialize(cells);
        }

        // main loop
        while (!terminationCriterion.satisfied()) {

            alternateThroughValues();
            
            if (checker.check()) {
            	break;
            }

            if (!terminationCriterion.satisfied()) {
                restartsCounter.increment();
            }

            if (!terminationCriterion.satisfied()) {
                //restartInitialiser.initialize(subsetData);
                restartInitialiser.initialize(cells);
                lastObjVal = null;
                evaluate();
            }
        }
    }
    
    @Override
    public void search(Cell cell, PredicateChecker checker) {
        // set up
        //this.subsetData = data;
        //cells = data.getCells();
    	cells = new ArrayList<Cell>();
    	this.cells.add(cell);

        // start
        // startInitialiser.initialize(data);
        lastObjVal = null;
        evaluate();

        // main loop
        while (!terminationCriterion.satisfied()) {

            alternateThroughValues();
            
            if (checker.check()) {
            	break;
            }

            if (!terminationCriterion.satisfied()) {
                restartsCounter.increment();
            }

            if (!terminationCriterion.satisfied()) {
                //restartInitialiser.initialize(subsetData);
                restartInitialiser.initialize(cells);
                lastObjVal = null;
                evaluate();
            }
        }
        //this.cells.clear();
    }
    
    @Override
    public void search(Data data, PredicateChecker checker, Cell cell) {
        // set up
        this.subsetData = data;
        //cells = data.getCells();
    	this.cells.add(cell);

        // start
        // startInitialiser.initialize(data);
        lastObjVal = null;
        evaluate();


        if (checker.check())
        	evaluate();	

        // main loop
        while (!terminationCriterion.satisfied()) {

            alternateThroughValues();
            
            if (checker.check()) {
            	break;
            }

            if (!terminationCriterion.satisfied()) {
                restartsCounter.increment();
            }

            if (!terminationCriterion.satisfied()) {
                restartInitialiser.initialize(subsetData);
                lastObjVal = null;
                evaluate();
            }
        }
    }

    protected boolean evaluate() {
        ObjectiveValue nextObjVal = evaluate(mainData);

        boolean improvement = (lastObjVal == null || nextObjVal.betterThan(lastObjVal));

        if (improvement) {
            lastObjVal = nextObjVal;
        }

        return improvement;
    }

    protected void alternateThroughValues() {
        int numValues = cells.size();
        int valuesWithoutImprovement = 0;
        Iterator<Cell> iterator = cells.iterator();

        while (valuesWithoutImprovement < numValues && !terminationCriterion.satisfied()) {

            // restart the iteration process through the columns
            if (!iterator.hasNext()) {
                iterator = cells.iterator();
            }

            Cell cell = iterator.next();

            if (valueSearch(cell)) {
                valuesWithoutImprovement = 0;
            } else {
                valuesWithoutImprovement++;
            }
        }
    }

    protected boolean valueSearch(Cell cell) {
        //boolean improvement = invertNullMove(cell);
    	boolean improvement = false;
        if (!cell.isNull()) {
            if (valueSearch(cell.getValue())) {
                improvement = true;
            }
        }

        return improvement;
    }

    protected boolean invertNullMove(Cell cell) {
        boolean improvement = false;

        if (!terminationCriterion.satisfied()) {
            Value originalValue = cell.getValue();
            cell.setNull(originalValue != null);

            improvement = evaluate();
            if (!improvement) {
                cell.setValue(originalValue);
            }
        }

        return improvement;
    }

    protected boolean valueSearch(Value value) {
        class ValueDispatcher implements ValueVisitor {

            boolean improvement;

            public boolean improvement(Value value) {
                value.accept(this);
                return improvement;
            }

            @Override
            public void visit(BooleanValue value) {
                improvement = booleanValueSearch(value);
            }

            @Override
            public void visit(DateValue value) {
                improvement = dateValueSearch(value);
            }

            @Override
            public void visit(DateTimeValue value) {
                improvement = dateTimeValueSearch(value);
            }

            @Override
            public void visit(NumericValue value) {
                improvement = numericValueSearch(value);
            }

            @Override
            public void visit(StringValue value) {
                improvement = stringValueSearch(value);
            }

            @Override
            public void visit(TimeValue value) {
                improvement = timeValueSearch(value);
            }

            @Override
            public void visit(TimestampValue value) {
                improvement = timestampValueSearch(value);
            }
        }

        return (new ValueDispatcher()).improvement(value);
    }

    protected boolean booleanValueSearch(BooleanValue value) {
        boolean improvement = false;

        if (!terminationCriterion.satisfied()) {
            boolean originalValue = value.get();
            value.set(!originalValue);

            improvement = evaluate();
            if (!improvement) {
                value.set(originalValue);
            }
        }

        return improvement;
    }

    protected boolean compoundValueSearch(CompoundValue value) {
        boolean improvement = false;
        boolean iterationImprovement = true;
        List<Value> elements = value.getElements();
        int length = elements.size();

        while (iterationImprovement && !terminationCriterion.satisfied()) {
            iterationImprovement = false;

            for (int i = 0; i < length && !terminationCriterion.satisfied(); i++) {
                if (valueSearch(elements.get(i))) {
                    improvement = true;
                    iterationImprovement = true;
                }
            }
        }

        return improvement;
    }

    protected boolean dateValueSearch(DateValue value) {
        return compoundValueSearch((CompoundValue) value);
    }

    protected boolean dateTimeValueSearch(DateTimeValue value) {
        return compoundValueSearch((CompoundValue) value);
    }

    protected boolean stringValueSearch(StringValue value) {
        boolean improvement = false;
        boolean iterationImprovement = true;

        while (iterationImprovement && !terminationCriterion.satisfied()) {
            iterationImprovement = false;

            if (removeCharacterMove(value)) {
                improvement = true;
                iterationImprovement = true;
            }

            if (addCharacterMove(value)) {
                improvement = true;
                iterationImprovement = true;
            }

            if (changeCharactersMove(value)) {
                improvement = true;
                iterationImprovement = true;
            }
        }

        return improvement;
    }

    protected boolean addCharacterMove(StringValue value) {
        boolean improvement = false;

        if (!terminationCriterion.satisfied()) {
            if (value.addCharacter()) {
                improvement = evaluate();
                if (!improvement) {
                    value.removeCharacter();
                }
            }
        }
        return improvement;
    }

    protected boolean removeCharacterMove(StringValue value) {
        boolean improvement = false;

        if (!terminationCriterion.satisfied()) {
            int numElements = value.getLength();

            if (numElements > 0) {
                // save last character first
                NumericValue lastCharacter = value.getCharacter(value.getLength() - 1);

                if (value.removeCharacter()) {
                    improvement = evaluate();

                    if (!improvement) {
                        value.addCharacter(lastCharacter);
                    }
                }
            }
        }
        return improvement;
    }

    protected boolean changeCharactersMove(StringValue value) {
        boolean improvement = false;

        if (!terminationCriterion.satisfied()) {
            improvement = compoundValueSearch(value);
        }

        return improvement;
    }

    protected boolean numericValueSearch(NumericValue value) {

        boolean improvement = false;
        boolean directionalImprovement = true;

        while (directionalImprovement && !terminationCriterion.satisfied()) {
            directionalImprovement = false;

            int[] directions = {1, -1};
            for (int direction : directions) {
                int step = 0;

                boolean moveIsImprovement = true;

                while (moveIsImprovement && !terminationCriterion.satisfied()) {
                    moveIsImprovement = numericMove(value, direction, step);

                    if (moveIsImprovement) {
                        improvement = true;
                        directionalImprovement = true;
                    }

                    step++;
                }
            }
        }
        return improvement;
    }

    protected boolean numericMove(NumericValue value, int direction, int step) {
        BigDecimal originalValue = value.get();

        BigDecimal newValue = calculateNewNumericValue(direction, step, originalValue);
        value.set(newValue);

        // is it an improvement?
        boolean improvement = evaluate();
        if (!improvement) {
            value.set(originalValue);
        }

        return improvement;
    }

    protected BigDecimal calculateNewNumericValue(int direction, int step, BigDecimal originalValue) {
        BigDecimal stepSize = new BigDecimal(ACCELERATION_BASE).pow(step);
        BigDecimal scaledStepSize = stepSize.scaleByPowerOfTen(-originalValue.scale());
        BigDecimal move = new BigDecimal(direction).multiply(scaledStepSize);

        return originalValue.add(move);
    }

    protected boolean timeValueSearch(TimeValue value) {
        return compoundValueSearch(value);
    }

    protected boolean timestampValueSearch(TimestampValue value) {
        return numericValueSearch(value);
    }

	@Override
	public void search(Data candidateSolution) {
		// TODO Auto-generated method stub
		
	}
}
