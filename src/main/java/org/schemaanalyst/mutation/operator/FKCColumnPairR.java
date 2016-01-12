package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.ForeignKeyColumnSupplier;
import org.schemaanalyst.mutation.supplier.schema.ForeignKeyConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.tuple.Pair;

import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by removing pairs of 
 * {@link Column}s from each foreign key in turn.
 * </p>
 * 
 * @author Phil McMinn
 *
 */
public class FKCColumnPairR implements MutantProducer<Schema> {

    private Schema schema;

    public FKCColumnPairR(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {

        Supplier<Schema, List<Pair<Column>>> supplier = SupplyChain.chain(
                new ForeignKeyConstraintSupplier(),
                new ForeignKeyColumnSupplier());
        
        supplier.initialise(schema);
        ListElementRemover<Schema, Pair<Column>> mutator = new ListElementRemover<>(
                supplier);
        
        List<Mutant<Schema>> mutants = mutator.mutate();
        
        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }
        
        return mutants;
    }

}
