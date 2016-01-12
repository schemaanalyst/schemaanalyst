package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementExchanger;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.UniqueColumnsWithAlternativesSupplier;
import org.schemaanalyst.mutation.supplier.schema.UniqueConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by exchanging
 * {@link Column}s in {@link UniqueConstraint}s.
 * </p>
 *
 * @author Chris J. Wright
 *
 */
public class UCColumnE implements MutantProducer<Schema> {

    private Schema schema;

    public UCColumnE(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();

        // Generate the mutants
        Supplier<Schema, Pair<List<Column>>> columnsWithAlternativesSupplier
                = SupplyChain.chain(
                        new UniqueConstraintSupplier(),
                        new UniqueColumnsWithAlternativesSupplier());
        columnsWithAlternativesSupplier.initialise(schema);
        ListElementExchanger<Schema, Column> columnExchanger = new ListElementExchanger<>(
                columnsWithAlternativesSupplier);
        mutants.addAll(columnExchanger.mutate());

        // Set the producer information in each mutant
        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }

        return mutants;
    }
}
