package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.PrimaryKeyColumnSupplier;
import org.schemaanalyst.mutation.supplier.schema.PrimaryKeyConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by removing columns in
 * each {@link PrimaryKeyConstraint} in turn.
 * </p>
 *
 * @author Chris J. Wright
 *
 */
public class PKCColumnR implements MutantProducer<Schema> {

    private Schema schema;

    public PKCColumnR(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();

        // Generate the mutants
        Supplier<Schema, List<Column>> columnsSupplier = SupplyChain.chain(
                new PrimaryKeyConstraintSupplier(),
                new PrimaryKeyColumnSupplier());
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
