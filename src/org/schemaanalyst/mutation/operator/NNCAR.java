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
 * A {@link MutantProducer} that mutates {@link Schema}s by adding and removing 
 * a {@link NotNullConstraint} to each column in turn.
 * </p>
 * 
 * @author Phil McMinn
 *
 */
public class NNCAR implements MutantProducer<Schema> {

    private Schema schema;

    public NNCAR(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        // Create the collection in which to store created mutants.
        List<Mutant<Schema>> mutants = new ArrayList<>();

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
                } else {
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
        
        for (Mutant<Schema> mutant : mutants) {
            mutant.setMutantProducer(this);
        }

        return mutants;
    }
}
