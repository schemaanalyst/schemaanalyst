package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ElementNullifier;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.CheckConstraintSupplier;
import org.schemaanalyst.mutation.supplier.schema.CheckExpressionSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by removing each 
 * {@link CheckConstraint} in turn.
 * </p>
 * 
 * @author Phil McMinn
 *
 */
public class CCNullifier implements MutantProducer<Schema> {

	private Schema schema;

	public CCNullifier(Schema schema) {
		this.schema = schema;
	}

    @Override
	public List<Mutant<Schema>> mutate() {
		List<Mutant<Schema>> mutants = new ArrayList<>();

		Supplier<Schema, Expression> supplier = SupplyChain.chain(
				new CheckConstraintSupplier(), 
				new CheckExpressionSupplier());
		
		supplier.initialise(schema);

		ElementNullifier<Schema, Expression> nullifier = new ElementNullifier<>(
				supplier);
		mutants.addAll(nullifier.mutate());

        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }
        
		return mutants;
	}
}
