package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementAdder;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.PrimaryKeyColumnsWithAlternativesSupplier;
import org.schemaanalyst.mutation.supplier.schema.PrimaryKeyConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by adding columns in
 * each {@link PrimaryKeyConstraint} in turn.
 * </p>
 *
 * @author Chris J. Wright
 *
 */
public class PKCColumnA implements MutantProducer<Schema> {

    private Schema schema;

    public PKCColumnA(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();

        // Generate the mutants
        Supplier<Schema, Pair<List<Column>>> columnsWithAlternativesSupplier
                = SupplyChain.chain(
                        new PrimaryKeyConstraintSupplier(),
                        new PrimaryKeyColumnsWithAlternativesSupplier());
        columnsWithAlternativesSupplier.initialise(schema);
        ListElementAdder<Schema, Column> columnAdder = new ListElementAdder<>(
                columnsWithAlternativesSupplier);
        mutants.addAll(columnAdder.mutate());

        // Set the producer information in each mutant
        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }

        return mutants;
    }
}
