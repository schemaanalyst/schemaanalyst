package org.schemaanalyst.data.generation.search;

import _deprecated.datageneration.search.datainitialization.DataInitialiser;
import org.schemaanalyst.data.*;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.util.random.Random;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class AlternatingValueSearch extends Search {

    private static final int ACCELERATION_BASE = 2;

    private DataInitialiser startInitialiser;
    private DataInitialiser restartInitialiser;

    private Data data;
    private List<Cell> cells;
    private ObjectiveValue lastObjectiveValue;

    public AlternatingValueSearch(Random random,
                                  int maxEvaluations,
                                  ValueLibrary valueLibrary,
                                  DataInitialiser startInitializer,
                                  DataInitialiser restartInitializer) {
        super(random, maxEvaluations, valueLibrary);
        this.startInitialiser = startInitializer;
        this.restartInitialiser = restartInitializer;
    }

    @Override
    public void search(Data data) {
        // set up
        this.data = data;
        cells = data.getCells();

        // start
        startInitialiser.initialize(data);
        lastObjectiveValue = null;
        evaluate();

        // main loop
        while (!terminationCriterionSatisfied()) {

            alternateThroughValues();

            if (!terminationCriterionSatisfied()) {
                numRestarts ++;
            }

            if (!terminationCriterionSatisfied()) {
                restartInitialiser.initialize(data);
                lastObjectiveValue = null;
                evaluate();
            }
        }
    }

    private boolean terminationCriterionSatisfied() {
        return numEvaluations >= maxEvaluations;
    }

    protected boolean evaluate() {
        ObjectiveValue nextObjVal = evaluate(data);
        boolean improvement = (lastObjectiveValue == null || nextObjVal.betterThan(lastObjectiveValue));

        if (improvement) {
            lastObjectiveValue = nextObjVal;
        }

        return improvement;
    }

    protected void alternateThroughValues() {
        int numValues = cells.size();
        int valuesWithoutImprovement = 0;
        Iterator<Cell> iterator = cells.iterator();

        while (valuesWithoutImprovement < numValues && !terminationCriterionSatisfied()) {

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
        boolean improvement = invertNullMove(cell);

        if (!cell.isNull()) {
            if (valueSearch(cell.getValue())) {
                improvement = true;
            }
        }

        return improvement;
    }

    protected boolean invertNullMove(Cell cell) {
        boolean improvement = false;

        if (!terminationCriterionSatisfied()) {
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

        if (!terminationCriterionSatisfied()) {
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

        while (iterationImprovement && !terminationCriterionSatisfied()) {
            iterationImprovement = false;

            for (int i = 0; i < length && !terminationCriterionSatisfied(); i++) {
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

        while (iterationImprovement && !terminationCriterionSatisfied()) {
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

        if (!terminationCriterionSatisfied()) {
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

        if (!terminationCriterionSatisfied()) {
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

        if (!terminationCriterionSatisfied()) {
            improvement = compoundValueSearch(value);
        }

        return improvement;
    }

    protected boolean numericValueSearch(NumericValue value) {

        boolean improvement = false;
        boolean directionalImprovement = true;

        while (directionalImprovement && !terminationCriterionSatisfied()) {
            directionalImprovement = false;

            int[] directions = {1, -1};
            for (int direction : directions) {
                int step = 0;

                boolean moveIsImprovement = true;

                while (moveIsImprovement && !terminationCriterionSatisfied()) {
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
}