package org.schemaanalyst.mutation.mutators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;

public class UniqueConstraintMutator extends Mutator {

    private Schema originalSchema;
    private Table table;
    private Set<Set<Column>> columnSets;
    private List<Schema> mutants;

    public void produceMutants(Table table, List<Schema> mutants) {
        originalSchema = table.getSchema();
        columnSets = new HashSet<>();

        this.table = table;
        this.mutants = mutants;

        for (UniqueConstraint uniqueConstraint : table.getUniqueConstraints()) {
            registerColumnSet(uniqueConstraint);
        }

        for (UniqueConstraint uniqueConstraint : table.getUniqueConstraints()) {
            for (Column column : table.getColumns()) {
                makeAddColumnMutant(uniqueConstraint, column);
            }
        }

        for (UniqueConstraint uniqueConstraint : table.getUniqueConstraints()) {
            for (Column column : uniqueConstraint.getColumns()) {
                makeRemoveColumnMutant(uniqueConstraint, column);
            }
        }

        for (Column column : table.getColumns()) {
            makeNewUniqueConstraintMutant(column);
        }
    }

    private Set<Column> makeColumnSet(UniqueConstraint uniqueConstraint) {
        Set<Column> columnSet = new HashSet<>();
        for (Column column : uniqueConstraint.getColumns()) {
            columnSet.add(column);
        }
        return columnSet;
    }

    private void registerColumnSet(UniqueConstraint uniqueConstraint) {
        columnSets.add(makeColumnSet(uniqueConstraint));
    }

    private void makeAddColumnMutant(UniqueConstraint uniqueConstraint, Column column) {
        Set<Column> columnSet = makeColumnSet(uniqueConstraint);
        columnSet.add(column);

        if (!columnSets.contains(columnSet)) {
            Schema mutant = originalSchema.duplicate();
            mutant.addComment("Unique constraint mutant with column \"" + column + "\" added to existing clause");
            mutant.addComment("(Unique, 4)");
            mutant.addComment("table=" + table);

            Table mutantTable = mutant.getTable(table.getName());
            for (UniqueConstraint mutantUnique : mutantTable.getUniqueConstraints()) {
                if (mutantUnique.equals(uniqueConstraint)) {
                    mutantUnique.addColumn(mutantTable.getColumn(column.getName()));
                    break;
                }
            }

            mutants.add(mutant);
        }
    }

    private void makeRemoveColumnMutant(UniqueConstraint uniqueConstraint, Column column) {
        Set<Column> columnSet = makeColumnSet(uniqueConstraint);
        columnSet.remove(column);

        if (!columnSets.contains(columnSet)) {
            Schema mutant = originalSchema.duplicate();
            mutant.addComment("Unique constraint mutant with column \"" + column + "\" removed from existing clause");
            mutant.addComment("(Unique, 4)");
            mutant.addComment("table=" + table);

            Table mutantTable = mutant.getTable(table.getName());
            for (UniqueConstraint mutantUniqueConstraint : mutantTable.getUniqueConstraints()) {
                if (mutantUniqueConstraint.equals(uniqueConstraint)) {
                    mutantUniqueConstraint.removeColumn(mutantTable.getColumn(column.getName()));

                    if (mutantUniqueConstraint.getNumColumns() == 0) {
                        mutantTable.removeUniqueConstraint(mutantUniqueConstraint);
                    }

                    break;
                }
            }

            mutants.add(mutant);
        }
    }

    private void makeNewUniqueConstraintMutant(Column column) {
        Set<Column> columnSet = new HashSet<>();
        columnSet.add(column);

        if (!columnSets.contains(columnSet)) {
            Schema mutant = originalSchema.duplicate();
            mutant.addComment("Unique constraint mutant with column \"" + column + "\" added");
            mutant.addComment("(Unique, 4)");
            mutant.addComment("table=" + table);

            Table mutantTable = mutant.getTable(table.getName());
            mutantTable.addUniqueConstraint(mutantTable.getColumn(column.getName()));
            mutants.add(mutant);
        }
    }
}
