/*
 */
package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementAdder;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.ForeignKeyColumnPairsWithAlternativesSupplier;
import org.schemaanalyst.mutation.supplier.schema.ForeignKeyConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by adding column 
 * pairs to foreign keys with other columns that have matching types.
 * </p>
 *
 * @author Chris J. Wright
 */
public class FKCColumnPairA implements MutantProducer<Schema> {

    private Schema schema;
    private boolean sameColumnTypes;

    public FKCColumnPairA(Schema schema) {
        this(schema, false);
    }

    public FKCColumnPairA(Schema schema, boolean sameColumnTypes) {
        this.schema = schema;
        this.sameColumnTypes = sameColumnTypes;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        Supplier<Schema, Pair<List<Pair<Column>>>> supplier =
                SupplyChain.chain(
                        new ForeignKeyConstraintSupplier(),
                        new ForeignKeyColumnPairsWithAlternativesSupplier(sameColumnTypes));
        supplier.initialise(schema);
        ListElementAdder<Schema, Pair<Column>> columnExchanger = new ListElementAdder<>(supplier);
        mutants.addAll(columnExchanger.mutate());
        
        // Set the producer information in each mutant
        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }
        
        return mutants;
    }
}
