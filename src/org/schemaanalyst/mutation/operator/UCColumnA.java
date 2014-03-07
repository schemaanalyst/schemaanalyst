package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.mutator.ListElementAdder;
import org.schemaanalyst.mutation.supplier.Supplier;
import org.schemaanalyst.mutation.supplier.SupplyChain;
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
 * A {@link MutantProducer} that mutates {@link Schema}s by adding
 * {@link Column}s in {@link UniqueConstraint}s.
 * </p>
 *
 * @author Chris J. Wright
 *
 */
public class UCColumnA implements MutantProducer<Schema> {

    private Schema schema;

    public UCColumnA(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();

        // Generate the mutants with new Uniques
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

        // Generate the mutants with new columns added to Uniques
        Supplier<Schema, Pair<List<Column>>> columnsWithAlternativesSupplier
                = SupplyChain.chain(
                        new UniqueConstraintSupplier(),
                        new UniqueColumnsWithAlternativesSupplier());
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
