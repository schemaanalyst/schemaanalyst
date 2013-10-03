/*
 */
package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;
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

/**
 * <p>
 *
 * </p>
 *
 * @author Chris J. Wright
 */
public class FKCColumnPairE implements MutantProducer<Schema> {

    private Schema schema;

    public FKCColumnPairE(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();
        Supplier<Schema, Pair<List<Pair<Column>>>> supplier =
                SupplyChain.chain(
                        new ForeignKeyConstraintSupplier(),
                        new ForeignKeyColumnPairWithAlternativesSupplier());
        supplier.initialise(schema);
        ListElementExchanger<Schema, Pair<Column>> columnExchanger = new ListElementExchanger<>(supplier);
        mutants.addAll(columnExchanger.mutate());
        return mutants;
    }
}
