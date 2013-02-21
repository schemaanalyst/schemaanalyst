/*
 */
package org.schemaanalyst.mutation.mutators.checkmutators;

import java.util.ArrayList;
import java.util.List;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.mutators.Mutator;
import org.schemaanalyst.schema.BetweenCheckPredicate;
import org.schemaanalyst.schema.CheckConstraint;
import org.schemaanalyst.schema.CheckPredicateVisitor;
import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.InCheckPredicate;
import org.schemaanalyst.schema.Operand;
import org.schemaanalyst.schema.RelationalCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;

/**
 * Mutates literal values in all types of check constraint. Replaces 'x' with
 * '-x', 'x-1' and 'x+1' in numeric values.
 *
 * @author chris
 */
public class CheckConstraintUnaryOperatorInsertionMutator extends Mutator {

    @Override
    public void produceMutants(Table table, List<Schema> mutants) {
        for (CheckConstraint checkConstraint : table.getCheckConstraints()) {
            Visitor v = new Visitor(table, checkConstraint);
            mutants.addAll(v.createMutants());
        }
    }

    /**
     * A visitor implementation for mutating check predicates
     */
    private class Visitor implements CheckPredicateVisitor {

        Table table;
        List<Schema> mutants;
        CheckConstraint constraint;

        /**
         * Constructor.
         *
         * @param table The table to mutate
         * @param constraint The constraint to mutate
         */
        public Visitor(Table table, CheckConstraint constraint) {
            this.table = table;
            this.constraint = constraint;
        }

        /**
         * Create the mutants for the given constraint.
         *
         * @return The mutants created
         */
        public List<Schema> createMutants() {
            mutants = new ArrayList<>();
            constraint.getPredicate().accept(this);
            return mutants;
        }

        /**
         * Mutants a given BetweenCheckPredicate constraint.
         *
         * @param predicate The predicate of the constraint
         */
        @Override
        public void visit(BetweenCheckPredicate predicate) {
            Operand lower = predicate.getLower();
            if (lower instanceof NumericValue) {
                int value = ((NumericValue) lower).get().intValue();
                createBetweenMutant(-value, predicate.getColumn(), predicate.getUpper());
                createBetweenMutant(value + 1, predicate.getColumn(), predicate.getUpper());
                createBetweenMutant(value - 1, predicate.getColumn(), predicate.getUpper());
            }
            Operand upper = predicate.getUpper();
            if (upper instanceof NumericValue) {
                int value = ((NumericValue) upper).get().intValue();
                createBetweenMutant(predicate.getLower(), predicate.getColumn(), -value);
                createBetweenMutant(predicate.getLower(), predicate.getColumn(), value + 1);
                createBetweenMutant(predicate.getLower(), predicate.getColumn(), value - 1);
            }
        }

        /**
         * Does nothing. An InCheckPredicate does not usually contain numeric
         * values
         *
         * @param predicate The predicate of the constraint
         */
        @Override
        public void visit(InCheckPredicate predicate) {
            // Do nothing
        }

        /**
         * Mutates a given RelationalCheckPredicate.
         *
         * @param predicate The predicate of the constraint
         */
        @Override
        public void visit(RelationalCheckPredicate predicate) {
            Operand lhs = predicate.getLHS();
            Operand rhs = predicate.getRHS();
            RelationalOperator op = predicate.getOperator();
            if (lhs instanceof NumericValue) {
                int value = ((NumericValue) lhs).get().intValue();
                createRelationalMutant(-value, op, rhs);
                createRelationalMutant(value + 1, op, rhs);
                createRelationalMutant(value - 1, op, rhs);
            }
            if (rhs instanceof NumericValue) {
                int value = ((NumericValue) rhs).get().intValue();
                createRelationalMutant(lhs, op, -value);
                createRelationalMutant(lhs, op, value + 1);
                createRelationalMutant(lhs, op, value - 1);
            }
        }

        /**
         * Creates a mutant.
         *
         * @param number New number
         * @param column Original column
         * @param operand Original RHS
         */
        private void createBetweenMutant(int number, Column column, Operand operand) {
            Table mutantTable = getDuplicateTable();
            mutantTable.addCheckConstraint(new BetweenCheckPredicate(
                    mutantTable.getColumn(column.getName()),
                    new NumericValue(number),
                    operand));
            mutants.add(mutantTable.getSchema());
        }

        /**
         * Creates a mutant.
         *
         * @param operand Original LHS
         * @param column Original column
         * @param number New number
         */
        private void createBetweenMutant(Operand operand, Column column, int number) {
            Table mutantTable = getDuplicateTable();
            mutantTable.addCheckConstraint(new BetweenCheckPredicate(
                    mutantTable.getColumn(column.getName()),
                    operand,
                    new NumericValue(number)));
            mutants.add(mutantTable.getSchema());
        }

        /**
         * Creates a mutant.
         *
         * @param number New number
         * @param op Original operator
         * @param rhs Original RHS
         */
        private void createRelationalMutant(int number, RelationalOperator op, Operand rhs) {
            Table mutantTable = getDuplicateTable();
            mutantTable.addCheckConstraint(new RelationalCheckPredicate(
                    new NumericValue(number), op, rhs));
            mutants.add(mutantTable.getSchema());
        }

        /**
         * Creates a mutant.
         *
         * @param lhs Original LHS
         * @param op Original operator
         * @param number New number
         */
        private void createRelationalMutant(Operand lhs, RelationalOperator op, int number) {
            Table mutantTable = getDuplicateTable();
            mutantTable.addCheckConstraint(new RelationalCheckPredicate(
                    lhs, op, new NumericValue(number)));
            mutants.add(mutantTable.getSchema());
        }

        /**
         * Creates a duplicate of the visited schema, and returns the table
         * being mutated.
         *
         * @return The table being mutated, from the duplicate schema
         */
        private Table getDuplicateTable() {
            Schema mutantSchema = table.getSchema().duplicate();
            Table mutantTable = mutantSchema.getTable(table.getName());
            mutantTable.removeCheckConstraint(constraint);
            return mutantTable;
        }
    }
}
