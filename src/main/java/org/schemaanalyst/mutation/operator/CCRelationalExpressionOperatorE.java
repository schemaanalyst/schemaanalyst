/*
 */
package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.RelationalOperatorExchanger;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.expression.RelationalExpressionOperatorSupplier;
import org.schemaanalyst.mutation.supplier.expression.RelationalExpressionSupplier;
import org.schemaanalyst.mutation.supplier.schema.CheckConstraintSupplier;
import org.schemaanalyst.mutation.supplier.schema.CheckExpressionSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by replacing each 
 * {@link RelationalOperator} in the expression of each {@link CheckConstraint}
 * in turn.
 * </p>
 * 
 * @author Chris J. Wright
 */
public class CCRelationalExpressionOperatorE implements MutantProducer<Schema> {

	private Schema schema;

	public CCRelationalExpressionOperatorE(Schema schema) {
		this.schema = schema;
	}

	@Override
	public List<Mutant<Schema>> mutate() {
		List<Mutant<Schema>> mutants = new ArrayList<>();

		Supplier<Schema, RelationalOperator> supplier = SupplyChain.chain(
				new CheckConstraintSupplier(), 
				new CheckExpressionSupplier(),
				new RelationalExpressionSupplier(),
				new RelationalExpressionOperatorSupplier());

		supplier.initialise(schema);

		RelationalOperatorExchanger<Schema> exchanger = new RelationalOperatorExchanger<>(
				supplier);
		mutants.addAll(exchanger.mutate());

        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }
        
		return mutants;
	}
}
