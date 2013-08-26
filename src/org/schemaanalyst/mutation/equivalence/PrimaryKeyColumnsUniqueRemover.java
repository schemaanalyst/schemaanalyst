package org.schemaanalyst.mutation.equivalence;

import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

public class PrimaryKeyColumnsUniqueRemover extends EquivalenceReducer<Schema> {

	@Override
	public List<Mutant<Schema>> reduce(List<Mutant<Schema>> mutants) {

		for (Mutant<Schema> mutant : mutants) {
			Schema schema = mutant.getMutatedArtefact();
			List<PrimaryKeyConstraint> primaryKeyConstraints = schema
					.getPrimaryKeyConstraints();
			
			for (PrimaryKeyConstraint primaryKey : primaryKeyConstraints) {
				schema.removeUniqueConstraint(
						new UniqueConstraint(primaryKey.getTable(), primaryKey.getColumns()));
			}
		}

		return mutants;
	}

}
