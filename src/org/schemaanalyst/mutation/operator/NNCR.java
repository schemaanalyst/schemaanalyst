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
 * A {@link MutantProducer} that mutates {@link Schema}s by removing a
 * {@link NotNullConstraint} to each column in turn.
 * </p>
 *
 * @author Chris J. Wright
 *
 */
public class NNCR implements MutantProducer<Schema> {

    private Schema schema;

    public NNCR(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();

        // Generate the mutants
        for (Table table : schema.getTables()) {
            for (Column column : table.getColumns()) {
                if (schema.isNotNull(table, column)) {
                    // remove a NOT NULL constraint on the column
                    Schema dupRemoveSchema = schema.duplicate();
                    Table dupRemoveTable = dupRemoveSchema
                            .getTable(table.getName());
                    Column dupRemoveColumn = dupRemoveTable.getColumn(column
                            .getName());
                    dupRemoveSchema.removeNotNullConstraint(new NotNullConstraint(
                            dupRemoveTable, dupRemoveColumn));
                    mutants.add(new Mutant<>(dupRemoveSchema,
                            "Removed NOT NULL to column " + dupRemoveColumn
                                    + " in table " + dupRemoveTable));
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
