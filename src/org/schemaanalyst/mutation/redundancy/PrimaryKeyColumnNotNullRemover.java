package org.schemaanalyst.mutation.redundancy;

import java.util.List;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

/**
 * Most DBMSs automatically add <tt>NOT NULL</tt> constraints to
 * <tt>PRIMARY KEY</tt> columns.  This {@link RedundantMutantRemover} removes
 * those <tt>NOT NULL</tt> constraints -- if they are the result of a 
 * mutation, the mutant will be impossible to kill.
 * 
 * @author Phil McMinn
 *
 */
public class PrimaryKeyColumnNotNullRemover extends RedundantMutantRemover<Schema> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {
		
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
