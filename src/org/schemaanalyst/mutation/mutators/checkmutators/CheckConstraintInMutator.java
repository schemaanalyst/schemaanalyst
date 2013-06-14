/*
 */
package org.schemaanalyst.mutation.mutators.checkmutators;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.mutation.mutators.Mutator;
import org.schemaanalyst.representation.CheckConstraint;
import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.expression.InExpression;

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
            if (checkConstraint.getExpression() instanceof InExpression) {
                for (Value value : ((InExpression) checkConstraint.getExpression()).getValues()) {
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
        InExpression predicate = (InExpression) constraint.getExpression();
        Column mutantColumn = mutantTable.getColumn(predicate.getColumn().getName());

        // Remove the original constraint
        mutantTable.removeCheckConstraint(constraint);

        // Add the new constraint
        List<Value> mutantValues = new ArrayList<>(predicate.getValues());
        mutantValues.remove(value);
        mutantTable.addCheckConstraint(new InExpression(mutantColumn, mutantValues.toArray(new Value[mutantValues.size()])));

        // Add to mutants
        mutants.add(mutantSchema);
    }
}
