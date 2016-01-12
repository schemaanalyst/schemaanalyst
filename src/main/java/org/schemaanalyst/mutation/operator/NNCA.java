package org.schemaanalyst.mutation.operator;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link MutantProducer} that mutates {@link Schema}s by adding a
 * {@link NotNullConstraint} to each column in turn.
 * </p>
 *
 * @author Chris J. Wright
 *
 */
public class NNCA implements MutantProducer<Schema> {

    private Schema schema;

    public NNCA(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();

        // Generate the mutants
        for (Table table : schema.getTables()) {
            for (Column column : table.getColumns()) {
                if (!schema.isNotNull(table, column)) {
                    // create a NOT NULL constraint on the column
                    Schema dupAddSchema = schema.duplicate();
                    Table dupAddTable = dupAddSchema.getTable(table.getName());
                    Column dupAddColumn = dupAddTable.getColumn(column.getName());
                    dupAddSchema.createNotNullConstraint(dupAddTable, dupAddColumn);
                    mutants.add(new Mutant<>(dupAddSchema,
                            "Added NOT NULL to column " + dupAddColumn
                            + " in table " + dupAddTable));
                }
            }
        }

        // Set the producer information in each mutant
        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }

        return mutants;
    }
}
