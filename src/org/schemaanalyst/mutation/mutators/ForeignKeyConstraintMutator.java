package org.schemaanalyst.mutation.mutators;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class ForeignKeyConstraintMutator extends Mutator {
	
	public void produceMutants(Table table, List<Schema> mutants) {
		for (ForeignKeyConstraint foreignKey : table.getForeignKeyConstraints()) {
			for (Column column : foreignKey.getColumns()) {
				mutants.add(makeMutant(table, foreignKey, column));
			}
		}
	}
	
	protected Schema makeMutant(Table table, ForeignKeyConstraint foreignKey, Column column) {
		
		Schema mutant = table.getSchema().duplicate();
		mutant.addComment("Mutant with foreign key column \"" + column + "\" removed from existing clause on table \"" + table + "\"");
		mutant.addComment("(Foreign Key, 2)");
                mutant.addComment("table="+table);
		
		Table mutantTable = mutant.getTable(table.getName());
		
		for (ForeignKeyConstraint mutantForeignKey : mutantTable.getForeignKeyConstraints()) {
			if (mutantForeignKey.equals(foreignKey)) {
				
				mutantForeignKey.removeColumnPair(column);
				
				if (mutantForeignKey.getNumColumns() == 0) {
					mutantTable.removeForeignKeyConstraint(mutantForeignKey);
				}
				
				break;
			}
		}		
		
		return mutant;
	}	
}
