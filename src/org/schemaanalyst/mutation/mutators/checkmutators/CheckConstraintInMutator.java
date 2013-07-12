/*
 */
package org.schemaanalyst.mutation.mutators.checkmutators;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.InCheckCondition;
import org.schemaanalyst.mutation.mutators.Mutator;
import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Creates mutants of 'In ...' type check constraints by removing each value in
 * turn.
 *
 * @author chris
 */
public class CheckConstraintInMutator extends Mutator {

    @Override
    public void produceMutants(Table table, List<Schema> mutants) {
        for (CheckConstraint checkConstraint : table.getCheckConstraints()) {
            if (checkConstraint.getCheckCondition() instanceof InCheckCondition) {
                for (Value value : ((InCheckCondition) checkConstraint.getCheckCondition()).getValues()) {
                    makeMutant(value, table, checkConstraint, mutants);
                }
            }
        }
    }

    /**
     * Produce a mutant with the given value removed.
     *
     * @param value The value to remove
     * @param table The table being mutated
     * @param constraint The constraint being mutated
     * @param mutants The collection of mutants
     */
    private void makeMutant(Value value, Table table, CheckConstraint constraint, List<Schema> mutants) {
        // Make the duplicate
        Schema mutantSchema = table.getSchema().duplicate();
        Table mutantTable = mutantSchema.getTable(table.getName());
        InCheckCondition predicate = (InCheckCondition) constraint.getCheckCondition();
        Column mutantColumn = mutantTable.getColumn(predicate.getColumn().getName());

        // Remove the original constraint
        mutantTable.removeCheckConstraint(constraint);

        // Add the new constraint
        List<Value> mutantValues = new ArrayList<>(predicate.getValues());
        mutantValues.remove(value);
        mutantTable.addCheckConstraint(new InCheckCondition(mutantColumn, mutantValues.toArray(new Value[mutantValues.size()])));

        // Add to mutants
        mutants.add(mutantSchema);
    }
}
