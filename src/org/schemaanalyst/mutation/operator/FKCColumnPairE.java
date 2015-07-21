/*
 */
package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementExchanger;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.ForeignKeyColumnPairWithAlternativesSupplier;
import org.schemaanalyst.mutation.supplier.schema.ForeignKeyConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by replacing column 
 * pairs in foreign keys with other columns that have matching types.
 * </p>
 * 
 * <p>
 * Note that this means a relationship between {@code a int} and {@code b int} 
 * may be replaced with a relationship between {@code a varchar} and {@code b 
 * varchar}, for example.
 * </p>
 *
 * @author Chris J. Wright
 */
public class FKCColumnPairE implements MutantProducer<Schema> {

    private Schema schema;
    private boolean sameColumnTypes;

    public FKCColumnPairE(Schema schema) {
        this(schema, false);
    }

    public FKCColumnPairE(Schema schema, boolean sameColumnTypes) {
        this.schema = schema;
        this.sameColumnTypes = sameColumnTypes;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        Supplier<Schema, Pair<List<Pair<Column>>>> supplier =
                SupplyChain.chain(
                        new ForeignKeyConstraintSupplier(),
                        new ForeignKeyColumnPairWithAlternativesSupplier(sameColumnTypes));
        supplier.initialise(schema);
        ListElementExchanger<Schema, Pair<Column>> columnExchanger = new ListElementExchanger<>(supplier);
        mutants.addAll(columnExchanger.mutate());
        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }
        return mutants;
    }
}
