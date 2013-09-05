package org.schemaanalyst.mutation.redundancy;

import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

/**
 * If a <tt>UNIQUE</tt> constraint column is added, and the constraint shares 
 * the same columns as a table's <tt>PRIMARY KEY</tt> constraint, the resulting
 * mutant will be impossible to kill.  {@link PrimaryKeyColumnsUniqueRemover}
 * removes {@link org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint}s
 * from schema mutants that share the same columns as the table's
 *  {@link org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint}.
 * 
 * @author Phil McMinn
 *
 */

public class PrimaryKeyColumnsUniqueRemover extends RedundantMutantRemover<Schema> {

	@Override
	public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {

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
