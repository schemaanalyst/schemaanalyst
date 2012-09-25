package org.schemaanalyst.mutation;

import java.util.List;

import org.schemaanalyst.schema.CheckConstraint;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;

public class CheckConstraintMutator extends Mutator {
			
	public void produceMutants(Table table, List<Schema> mutants) {
		for (CheckConstraint checkConstraint : table.getCheckConstraints()) {
			mutants.add(makeMutant(table, checkConstraint));
		}
	}
	
	protected Schema makeMutant(Table table, CheckConstraint checkConstraint) {
		Schema mutant = table.getSchema().duplicate();	
		mutant.addComment("Mutant with check constraint \"" + checkConstraint + "\" removed from table \"" + table + "\"");
		mutant.addComment("(Check, 1)");

		Table mutantTable = mutant.getTable(table.getName());
		mutantTable.removeCheckConstraint(checkConstraint);
		
		return mutant;
	}
}
