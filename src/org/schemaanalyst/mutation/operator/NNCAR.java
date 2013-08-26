package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;

public class NNCAR implements MutantProducer<Schema> {

	private Schema schema;

	public NNCAR(Schema schema) {
		this.schema = schema;
	}

	public List<Mutant<Schema>> mutate() {
		// Create the collection in which to store created mutants.
		List<Mutant<Schema>> mutants = new ArrayList<>();

		for (Table table : schema.getTables()) {
			for (Column column : table.getColumns()) {

				// create a NOT NULL constraint on the column
				Schema dupAddSchema = schema.duplicate();
				Table dupAddTable = dupAddSchema.getTable(table.getName());
				Column dupAddColumn = dupAddTable.getColumn(column.getName());
				dupAddSchema.createNotNullConstraint(dupAddTable, dupAddColumn);
				mutants.add(new Mutant<>(dupAddSchema,
						"Added NOT NULL to column " + dupAddColumn
								+ " in table " + dupAddTable));

				// remove a NOT NULL constraint on the column
				Schema dupRemoveSchema = schema.duplicate();
				Table dupRemoveTable = dupRemoveSchema
						.getTable(table.getName());
				Column dupRemoveColumn = dupRemoveTable.getColumn(column
						.getName());
				dupRemoveSchema.removeNotNullConstraint(new NotNullConstraint(
						dupRemoveTable, dupRemoveColumn));
				mutants.add(new Mutant<>(dupRemoveSchema,
						"Added NOT NULL to column " + dupRemoveColumn
								+ " in table " + dupRemoveTable));
			}
		}

		return mutants;
	}
}
