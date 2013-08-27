package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.expression.InExpressionRHSListExpressionSubexpressionsSupplier;
import org.schemaanalyst.mutation.supplier.expression.InExpressionRHSListExpressionSupplier;
import org.schemaanalyst.mutation.supplier.schema.CheckConstraintSupplier;
import org.schemaanalyst.mutation.supplier.schema.CheckExpressionSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

/**
 * 
 * @author Phil McMinn
 *
 */
public class CCInExpressionRHSListExpressionElementR implements
		MutantProducer<Schema> {

	private Schema schema;

	public CCInExpressionRHSListExpressionElementR(Schema schema) {
		this.schema = schema;
	}

	public List<Mutant<Schema>> mutate() {
		List<Mutant<Schema>> mutants = new ArrayList<>();

		Supplier<Schema, List<Expression>> supplier = SupplyChain.chain(
				new CheckConstraintSupplier(), 
				new CheckExpressionSupplier(),
				new InExpressionRHSListExpressionSupplier(),
				new InExpressionRHSListExpressionSubexpressionsSupplier());

		supplier.initialise(schema);

		ListElementRemover<Schema, Expression> inExpressionListElementRemover = new ListElementRemover<>(
				supplier);
		mutants.addAll(inExpressionListElementRemover.mutate());

		return mutants;
	}
}