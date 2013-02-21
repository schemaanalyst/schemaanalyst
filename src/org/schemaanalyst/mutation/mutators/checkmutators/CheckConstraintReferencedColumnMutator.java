/*
 */
package org.schemaanalyst.mutation.mutators.checkmutators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.mutation.mutators.Mutator;
import org.schemaanalyst.schema.BetweenCheckPredicate;
import org.schemaanalyst.schema.CheckConstraint;
import org.schemaanalyst.schema.CheckPredicate;
import org.schemaanalyst.schema.CheckPredicateVisitor;
import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.InCheckPredicate;
import org.schemaanalyst.schema.Operand;
import org.schemaanalyst.schema.RelationalCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;

/**
 * Mutates all types of check constraint by replacing columns
 *
 * @author chris
 */
public class CheckConstraintReferencedColumnMutator extends Mutator {
    
    private TypeCompatibility typeCompatibility;
    
    public CheckConstraintReferencedColumnMutator() {
        this(TypeCompatibility.RELAXED);
    }
    
    public CheckConstraintReferencedColumnMutator(TypeCompatibility typeCompatibility) {
        this.typeCompatibility = typeCompatibility;
    }

    @Override
    public void produceMutants(Table table, List<Schema> mutants) {
        for (CheckConstraint checkConstraint : table.getCheckConstraints()) {
            Visitor v = new Visitor(typeCompatibility, table, checkConstraint);
            mutants.addAll(v.createMutants());
        }
    }

    /**
     * A visitor implementation for mutating check predicates
     */
    private class Visitor implements CheckPredicateVisitor {

        TypeCompatibility typeCompatibility;
        Table table;
        List<Schema> mutants;
        CheckConstraint constraint;

        /**
         * Constructor.
         * 
         * @param table The table to mutate
         * @param constraint The constraint to mutate
         */
        public Visitor(TypeCompatibility typeCompatibility, Table table, CheckConstraint constraint) {
            this.typeCompatibility = typeCompatibility;
            this.table = table;
            this.constraint = constraint;
        }

        /**
         * Create the mutants for the given constraint.
         * 
         * @return The mutants created.
         */
        public List<Schema> createMutants() {
            mutants = new ArrayList<>();
            constraint.getPredicate().accept(this);
            return mutants;
        }

        /**
         * Mutates a given BetweenCheckPredicate constraint.
         * 
         * @param predicate The predicate of the constraint
         */
        @Override
        public void visit(BetweenCheckPredicate predicate) {
            // BetweenCheckPredicate(<Column>, Operand, Operand)
            new ColumnReplacer(typeCompatibility) {
                @Override
                protected Schema createMutant(Column column, Column replacement, CheckPredicate checkPredicate) {
                    BetweenCheckPredicate predicate = (BetweenCheckPredicate) checkPredicate;
                    mutantTable.addCheckConstraint(new BetweenCheckPredicate(
                            replacement,
                            predicate.getLower(),
                            predicate.getUpper()));
                    mutant.addComment("Mutant with BetweenCheckPredicate constraint \"" + predicate + "\"");
                    mutant.addComment("table=" + mutantTable);
                    return mutant;
                }
            }.createMutants(predicate.getColumn());
            // BetweenCheckPredicate(Column, <Operand>, Operand)
            if (predicate.getLower() instanceof Column) {
                new ColumnReplacer(typeCompatibility) {
                    @Override
                    protected Schema createMutant(Column column, Column replacement, CheckPredicate checkPredicate) {
                        BetweenCheckPredicate predicate = (BetweenCheckPredicate) checkPredicate;
                        mutantTable.addCheckConstraint(new BetweenCheckPredicate(
                                predicate.getColumn(),
                                replacement,
                                predicate.getUpper()));
                        mutant.addComment("Mutant with BetweenCheckPredicate constraint \"" + predicate + "\"");
                        mutant.addComment("table=" + mutantTable);
                        return mutant;
                    }
                }.createMutants((Column) predicate.getLower());
            }
            // BetweenCheckPredicate(Column, Operand, <Operand>)
            if (predicate.getUpper() instanceof Column) {
                new ColumnReplacer(typeCompatibility) {
                    @Override
                    protected Schema createMutant(Column column, Column replacement, CheckPredicate checkPredicate) {
                        BetweenCheckPredicate predicate = (BetweenCheckPredicate) checkPredicate;
                        mutantTable.addCheckConstraint(new BetweenCheckPredicate(
                                predicate.getColumn(),
                                predicate.getLower(),
                                replacement));
                        mutant.addComment("Mutant with BetweenCheckPredicate constraint \"" + predicate + "\"");
                        mutant.addComment("table=" + mutantTable);
                        return mutant;
                    }
                }.createMutants((Column) predicate.getUpper());
            }
        }

        /**
         * Mutates a given InCheckPredicate constraint.
         *
         * @param predicate The predicate of the constraint
         */
        @Override
        public void visit(InCheckPredicate predicate) {
            // InCheckPredicate(<Column>,values)
            new ColumnReplacer(typeCompatibility) {
                @Override
                protected Schema createMutant(Column column, Column replacement, CheckPredicate checkPredicate) {
                    InCheckPredicate predicate = (InCheckPredicate) checkPredicate;
                    mutantTable.addCheckConstraint(new InCheckPredicate(
                            replacement,
                            predicate.getValues().toArray(new Value[0])));
                    mutant.addComment("Mutant with InCheckPredicate constraint \"" + predicate + "\"");
                    mutant.addComment("table=" + mutantTable);
                    return mutant;
                }
            }.createMutants(predicate.getColumn());
        }

        /**
         * Mutates a given RelationalCheckPredicate constraint.
         *
         * @param predicate The predicate of the constraint
         */
        @Override
        public void visit(RelationalCheckPredicate predicate) {
            // RelationalCheckPredicate(<Column>,Operator,Operand)
            Operand lhs = predicate.getLHS();
            if (lhs instanceof Column) {
                new ColumnReplacer(typeCompatibility) {
                    @Override
                    protected Schema createMutant(Column column, Column replacement, CheckPredicate checkPredicate) {
                        RelationalCheckPredicate predicate = (RelationalCheckPredicate) checkPredicate;
                        mutantTable.addCheckConstraint(new RelationalCheckPredicate(
                                replacement,
                                predicate.getOperator(),
                                predicate.getRHS()));
                        mutant.addComment("Mutant with RelationalCheckPredicate constraint \"" + predicate + "\"");
                        mutant.addComment("table= " + mutantTable);
                        return mutant;
                    }
                }.createMutants((Column) lhs);
            }
            // RelationalCheckPredicate(Operand,Operator,<Column>)
            Operand rhs = predicate.getRHS();
            if (rhs instanceof Column) {
                new ColumnReplacer(typeCompatibility) {
                    @Override
                    protected Schema createMutant(Column column, Column replacement, CheckPredicate checkPredicate) {
                        RelationalCheckPredicate predicate = (RelationalCheckPredicate) checkPredicate;
                        mutantTable.addCheckConstraint(new RelationalCheckPredicate(
                                predicate.getLHS(),
                                predicate.getOperator(),
                                replacement));
                        mutant.addComment("Mutant with RelationalCheckPredicate constraint \"" + predicate + "\"");
                        mutant.addComment("table= " + mutantTable);
                        return mutant;
                    }
                }.createMutants((Column) rhs);
            }
        }

        /**
         * Implements basic logic for finding a possible replacement column and
         * creating mutants.
         */
        private abstract class ColumnReplacer {

            Schema mutant;
            Table mutantTable;
            TypeCompatibility typeCompatibility;

            /**
             * Constructor.
             *
             * @param typeCompatibility Defines how strictly to check
             * replacement columns for type compatibility.
             */
            public ColumnReplacer(TypeCompatibility typeCompatibility) {
                this.typeCompatibility = typeCompatibility;
            }

            /**
             * Creates a mutant, replacing 'column' with 'replacement' in the
             * given predicate.
             *
             * @param column The current column
             * @param replacement The replacement column
             * @param checkPredicate The check predicate
             * @return The mutant schema
             */
            protected abstract Schema createMutant(Column column, Column replacement, CheckPredicate checkPredicate);

            /**
             * Searches for suitable replacement columns, and invokes
             * 'createMutant' in compatible cases.
             *
             * @param column The current column.
             */
            public void createMutants(Column column) {
                for (Column replacement : table.getColumns()) {
                    if (replacement != column && compatibleTypes(column, replacement)) {
                        mutant = table.getSchema().duplicate();
                        mutantTable = mutant.getTable(table.getName());
                        mutantTable.removeCheckConstraint(constraint);
                        mutants.add(createMutant(column, replacement, constraint.getPredicate()));
                    }
                }
            }

            private boolean compatibleTypes(Column column, Column replacement) {
                return typeCompatibility.check(column, replacement);
            }
        }
    }

    /**
     * Represents how strictly column types must match for replacement.
     */
    public enum TypeCompatibility {

        /**
         * Columns must be of the same type, or compatible subtype.
         */
        STRICT {
            @Override
            public boolean check(Column column, Column replacement) {
                return column.getType().getClass().isAssignableFrom(replacement.getType().getClass());
            }
        },
        /**
         * Columns must be of similar types (numerical or character-based).
         */
        RELAXED {
            @Override
            public boolean check(Column column, Column replacement) {
                Class classA = column.getType().getClass();
                Class classB = replacement.getType().getClass();
                if (NUMERIC_TYPES.contains(classA) && NUMERIC_TYPES.contains(classB)) {
                    return true;
                } else if (STRING_TYPES.contains(classA) && STRING_TYPES.contains(classB)) {
                    return true;
                } else {
                    return STRICT.check(column, replacement);
                }
            }
        },
        /**
         * All columns are considered suitable as replacements.
         */
        NONE {
            @Override
            public boolean check(Column column, Column replacement) {
                return true;
            }
        };

        public abstract boolean check(Column column, Column replacement);
        private static final Set<Class> NUMERIC_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new Class[]{
                    org.schemaanalyst.schema.columntype.BigIntColumnType.class,
                    org.schemaanalyst.schema.columntype.DecimalColumnType.class,
                    org.schemaanalyst.schema.columntype.DoubleColumnType.class,
                    org.schemaanalyst.schema.columntype.FloatColumnType.class,
                    org.schemaanalyst.schema.columntype.IntColumnType.class,
                    org.schemaanalyst.schema.columntype.IntegerColumnType.class,
                    org.schemaanalyst.schema.columntype.MediumIntColumnType.class,
                    org.schemaanalyst.schema.columntype.NumericColumnType.class,
                    org.schemaanalyst.schema.columntype.RealColumnType.class,
                    org.schemaanalyst.schema.columntype.SmallIntColumnType.class,
                    org.schemaanalyst.schema.columntype.TinyIntColumnType.class
                })));
        private static final Set<Class> STRING_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new Class[]{
                    org.schemaanalyst.schema.columntype.CharColumnType.class,
                    org.schemaanalyst.schema.columntype.VarCharColumnType.class,
                    org.schemaanalyst.schema.columntype.TextColumnType.class
                })));
    };
}