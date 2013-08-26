package org.schemaanalyst.mutation.equivalence;

import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

public class PrimaryKeyColumnNotNullRemover extends EquivalenceReducer<Schema> {

	@Override
	public List<Mutant<Schema>> reduce(List<Mutant<Schema>> mutants) {
		
		for (Mutant<Schema> mutant : mutants) {
			Schema schema = mutant.getMutatedArtefact();
			List<PrimaryKeyConstraint> primaryKeyConstraints = schema.getPrimaryKeyConstraints();
			for (PrimaryKeyConstraint primaryKey : primaryKeyConstraints) {
				Table table = primaryKey.getTable();
				for (Column column : primaryKey.getColumns()) {
					schema.removeNotNullConstraint(new NotNullConstraint(table, column));
				}
			}
		}		
		return mutants;
	}	
}
