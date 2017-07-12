package org.schemaanalyst.data.generation.search;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.CompoundValue;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.ValueVisitor;
import org.schemaanalyst.data.generation.cellinitialization.CellInitializer;
import org.schemaanalyst.data.generation.cellvaluegeneration.RandomCellValueGenerator;
import org.schemaanalyst.data.generation.directedrandom.PredicateFixer;
import org.schemaanalyst.data.generation.directedrandom.PredicateFixerFactory;
import org.schemaanalyst.data.generation.search.objective.ObjectiveFunction;
import org.schemaanalyst.data.generation.search.objective.ObjectiveValue;
import org.schemaanalyst.data.generation.search.objective.predicate.ComposedPredicateObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateCheckerFactory;
import org.schemaanalyst.util.random.Random;

public class SwitchAlternatingValueSearch extends AlternatingValueSearch {

	private PredicateChecker predicateChecker;
	private PredicateFixer predicateFixer;
	protected RandomCellValueGenerator randomCellValueGenerator;
	private int stringValueStateIndex = 0;
	private int numericValueStateIndex = 0;

	public SwitchAlternatingValueSearch(Random random, CellInitializer startInitializer,
			CellInitializer restartInitializer, RandomCellValueGenerator randomCellValueGenerator) {
		super(random, startInitializer, restartInitializer);
		this.randomCellValueGenerator = randomCellValueGenerator;
	}

	@Override
	public void search(Data data) {
		// set up
		this.data = data;
		cells = data.getCells();

		// start
		startInitialiser.initialize(data);
		lastObjVal = null;
		evaluate();

		// main loop
		while (!terminationCriterion.satisfied()) {
			stringValueStateIndex = 0;
			numericValueStateIndex = 0;
			alternateThroughValues();

			if (!terminationCriterion.satisfied()) {
				restartsCounter.increment();
			}

			if (!terminationCriterion.satisfied()) {
				restartInitialiser.initialize(data);
				lastObjVal = null;
				evaluate();
			}
		}
	}
	
	@Override
    protected boolean valueSearch(Cell cell) {
        boolean improvement = invertNullMove(cell);

        if (!cell.isNull()) {
        	if (valueSearch(cell.getValue())) {
                improvement = true;
            }
        }

        return improvement;
    }

	@Override
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

	protected boolean switchValueSearch(Value value) {
		boolean improvement = false;
		boolean iterationImprovement = true;

		// Try to copy if there is a need to copy
		// Get state data
		// Then copy corresponding (Same data value) values
		// if improvement == keep
		// not improvement == revert

		// Check objective function is composed
		if (this.objFun instanceof ComposedPredicateObjectiveFunction) {
			// Get state
			Data state = ((ComposedPredicateObjectiveFunction) this.objFun).state;
			List<Cell> stateCells = state.getCells();
			Iterator<Cell> iterator = cells.iterator();

			while (iterationImprovement && !terminationCriterion.satisfied()) {
				iterationImprovement = false;
				Value originalValue = value.duplicate();
				Value newvalue = iterator.next().getValue().duplicate();
				value = newvalue;

				improvement = evaluate();
				if (!improvement) {
					value = originalValue;
				}

			}

		}

		return improvement;
	}
	
	protected boolean switchIdeaByPredicate(NumericValue value) {
		boolean improvement = false;
		
		
    	if (this.objFun instanceof ComposedPredicateObjectiveFunction) {
    		// Get Predicates and states
    		ComposedPredicate predicate = (ComposedPredicate) ((ComposedPredicateObjectiveFunction) this.objFun).predicate;
    		Data state = ((ComposedPredicateObjectiveFunction) this.objFun).state;
    		
    		for (Predicate p : predicate.getSubPredicates()) {
    			if (p instanceof ComposedPredicate) {
    				for (Predicate p1 : ((ComposedPredicate) p).getSubPredicates()) {
    	    			if (p1 instanceof ComposedPredicate) {
    	    				for (Predicate p2 : ((ComposedPredicate) p1).getSubPredicates()) {
    	    	    			System.out.println(p2);
    	    				}
    	    			} else {
    	    				System.out.println(p1);
    	    			}
    				}
    			} else {
    				System.out.println(p);
    			}
    		}
    		
    		// iterate through predicates
    		System.out.println(predicate.getSubPredicates());
    	}
		
		return improvement;
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

			if (copyStringValueMoveIndex(value)) {
				improvement = true;
				iterationImprovement = true;
			}

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

	protected boolean copyStringValueMove(StringValue value) {
		boolean improvement = false;
		boolean copyImprove = true;

		// Try to copy if there is a need to copy
		// Get state data
		// Then copy corresponding (Same data value) values
		// if improvement == keep
		// not improvement == revert

		// Check objective function is composed
		if (this.objFun instanceof ComposedPredicateObjectiveFunction) {
			// Get state
			Data state = ((ComposedPredicateObjectiveFunction) this.objFun).state;
			List<Cell> stateCells = state.getCells();
			while (copyImprove && !terminationCriterion.satisfied()) {
				copyImprove = false;
				for (Cell c : stateCells) {
					if (c.getValueInstance() instanceof StringValue) {
						StringValue originalValue = value.duplicate();
						StringValue stateValue = ((StringValue) c.getValue()).duplicate();
						value.set(stateValue.get());
						improvement = evaluate();
						
						System.err.println("String Copying N0 Eva = " + this.getNumEvaluations() + " improvement = " + improvement);
						
						if (!improvement) {
							value.set(originalValue.get());
							improvement = false;
						} else {
							break;
						}
					}
				}
			}

		}

		return improvement;

	}
	
	protected boolean copyStringValueMoveIndex(StringValue value) {
		boolean improvement = false;
		boolean copyImprove = true;

		// Try to copy if there is a need to copy
		// Get state data
		// Then copy corresponding (Same data value) values
		// if improvement == keep
		// not improvement == revert

		// Check objective function is composed
		if (this.objFun instanceof ComposedPredicateObjectiveFunction) {
			// Get state
			Data state = ((ComposedPredicateObjectiveFunction) this.objFun).state;
			List<Cell> stateCells = state.getCells();
			while (copyImprove && !terminationCriterion.satisfied()) {
				copyImprove = false;
				for (int i = 0;stringValueStateIndex < state.getCells().size(); stringValueStateIndex++) {
					if (state.getCells().get(stringValueStateIndex).getValueInstance() instanceof StringValue) {
						StringValue originalValue = value.duplicate();
						StringValue stateValue = ((StringValue) state.getCells().get(stringValueStateIndex).getValue()).duplicate();
						value.set(stateValue.get());
						improvement = evaluate();
						
						System.err.println("String Copying N0 Eva = " + this.getNumEvaluations() + " improvement = " + improvement);
						
						if (!improvement) {
							value.set(originalValue.get());
							improvement = false;
							break;
						} else {
							break;
						}
					}
				}
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
		
		switchIdeaByPredicate(value);

		while (directionalImprovement && !terminationCriterion.satisfied()) {
			directionalImprovement = false;

			if (numericCopyMoveIndex(value)) {
				improvement = true;
			}

			if (!improvement) {
				int[] directions = { 1, -1 };
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
		}
		return improvement;
	}

	protected boolean numericCopyMove(NumericValue value) {

		boolean improvement = false;
		boolean copyImprove = true;


		// Try to copy if there is a need to copy
		// Get state data
		// Then copy corresponding (Same data value) values
		// if improvement == keep
		// not improvement == revert

		// Check objective function is composed
		if (this.objFun instanceof ComposedPredicateObjectiveFunction) {
			// Get state
			Data state = ((ComposedPredicateObjectiveFunction) this.objFun).state;
			List<Cell> stateCells = state.getCells();

			while (copyImprove && !terminationCriterion.satisfied()) {
				copyImprove = false;
				for (Cell c : stateCells) {
					if (c.getValueInstance() instanceof NumericValue) {
						BigDecimal originalValue = value.get();
						BigDecimal stateValue = ((NumericValue) c.getValue()).get();
						value.set(stateValue);
						improvement = evaluate();
						System.err.println("Numeric Copying N0 Eva = " + this.getNumEvaluations() + " Value: " + value.get() + " improvement = " + improvement);

						if (!improvement) {
							value.set(originalValue);
							improvement = false;
						} else {
							break;
						}
					}
				}
			}

		}

		return improvement;

	}
	
	protected boolean numericCopyMoveIndex(NumericValue value) {

		boolean improvement = false;
		boolean copyImprove = true;


		// Try to copy if there is a need to copy
		// Get state data
		// Then copy corresponding (Same data value) values
		// if improvement == keep
		// not improvement == revert

		// Check objective function is composed
		if (this.objFun instanceof ComposedPredicateObjectiveFunction) {
			// Get state
			Data state = ((ComposedPredicateObjectiveFunction) this.objFun).state;
			List<Cell> stateCells = state.getCells();

			while (copyImprove && !terminationCriterion.satisfied()) {
				copyImprove = false;
				for (int i = 0;numericValueStateIndex < state.getCells().size(); i++)  {
					
					if (state.getCells().get(numericValueStateIndex).getValueInstance() instanceof NumericValue) {
						BigDecimal originalValue = value.get();
						BigDecimal stateValue = ((NumericValue) state.getCells().get(numericValueStateIndex).getValue()).get();
						numericValueStateIndex++;
						System.out.println("DATA:");
						System.out.println(data);
						System.out.println("STATE:");
						System.out.println(state);
						value.set(stateValue);
						improvement = evaluate();
						System.err.println("Numeric Copying No Eva = " + this.getNumEvaluations() + " Value: " + value.get() + " improvement = " + improvement);

						if (!improvement) {
							value.set(originalValue);
							improvement = false;
							//break;
						} else {
							break;
						}
					}
					numericValueStateIndex++;
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
