package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementAdder;
import org.schemaanalyst.mutation.mutator.ListElementExchanger;
import org.schemaanalyst.mutation.mutator.ListElementRemover;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
import org.schemaanalyst.mutation.supplier.schema.UniqueColumnSupplier;
import org.schemaanalyst.mutation.supplier.schema.UniqueColumnsWithAlternativesSupplier;
import org.schemaanalyst.mutation.supplier.schema.UniqueConstraintSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by adding, removing 
 * and exchanging {@link Column}s in {@link UniqueConstraint}s.
 * </p>
 * 
 * @author Phil McMinn
 *
 */
public class UCColumnARE implements MutantProducer<Schema> {

    private Schema schema;

    public UCColumnARE(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        // Create the collection in which to store created mutants.
        List<Mutant<Schema>> mutants = new ArrayList<>();

        for (Table table : schema.getTables()) {
            for (Column column : table.getColumns()) {
                if (!schema.isUnique(table, column)) {
                    // create a UNIQUE constraint on the column
                    Schema dupAddSchema = schema.duplicate();
                    Table dupAddTable = dupAddSchema.getTable(table.getName());
                    Column dupAddColumn = dupAddTable.getColumn(column.getName());
                    dupAddSchema.createUniqueConstraint(dupAddTable, dupAddColumn);
                    mutants.add(new Mutant<>(dupAddSchema,
                            "Added UNIQUE to column " + dupAddColumn
                            + " in table " + dupAddTable));
                }
            }
        }

        Supplier<Schema, List<Column>> columnsSupplier = SupplyChain.chain(
                new UniqueConstraintSupplier(),
                new UniqueColumnSupplier());
        columnsSupplier.initialise(schema);
        ListElementRemover<Schema, Column> columnRemover = new ListElementRemover<>(
                columnsSupplier);
        mutants.addAll(columnRemover.mutate());

        Supplier<Schema, Pair<List<Column>>> columnsWithAlternativesSupplier =
                SupplyChain.chain(
                new UniqueConstraintSupplier(),
                new UniqueColumnsWithAlternativesSupplier());
        columnsWithAlternativesSupplier.initialise(schema);
        ListElementAdder<Schema, Column> columnAdder = new ListElementAdder<>(
                columnsWithAlternativesSupplier);
        mutants.addAll(columnAdder.mutate());

        // restart the process
        columnsWithAlternativesSupplier.initialise(schema);

        ListElementExchanger<Schema, Column> columnExchanger = new ListElementExchanger<>(
                columnsWithAlternativesSupplier);
        mutants.addAll(columnExchanger.mutate());

        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }
        
        return mutants;
    }
}
