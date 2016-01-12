package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.UniqueColumnSupplier;
import org.schemaanalyst.mutation.supplier.schema.UniqueConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by removing
 * {@link Column}s in {@link UniqueConstraint}s.
 * </p>
 *
 * @author Chris J. Wright
 *
 */
public class UCColumnR implements MutantProducer<Schema> {

    private Schema schema;

    public UCColumnR(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();

        // Create the mutants
        Supplier<Schema, List<Column>> columnsSupplier = SupplyChain.chain(
                new UniqueConstraintSupplier(),
                new UniqueColumnSupplier());
        columnsSupplier.initialise(schema);
        ListElementRemover<Schema, Column> columnRemover = new ListElementRemover<>(
                columnsSupplier);
        mutants.addAll(columnRemover.mutate());

        // Set the producer information in each mutant
        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }

        return mutants;
    }
}
