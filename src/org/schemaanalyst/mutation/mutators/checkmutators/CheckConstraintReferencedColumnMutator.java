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
import org.schemaanalyst.representation.CheckConstraint;
import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.expression.BetweenExpression;
import org.schemaanalyst.representation.expression.Expression;
import org.schemaanalyst.representation.expression.ExpressionVisitor;
import org.schemaanalyst.representation.expression.InExpression;
import org.schemaanalyst.representation.expression.Operand;
import org.schemaanalyst.representation.expression.RelationalExpression;

/**
 * Mutates all types of check constraint by replacing columns
 *
 * @author chris
 */
public class CheckConstraintReferencedColumnMutator extends Mutator {
    
    private TypeCompatibility typeCompatibility;
    
    /**
     * Default constructor. Uses 'relaxed' type compatibility, where columns
     * must be of a similar type.
     */
    public CheckConstraintReferencedColumnMutator() {
        this(TypeCompatibility.RELAXED);
    }
    
    /**
     * Constructor. Uses the given type compatibility to determine which columns
     * to use for mutation.
     * @param typeCompatibility 
     */
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
    private class Visitor implements ExpressionVisitor {

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
            constraint.getExpression().accept(this);
            return mutants;
        }

        /**
         * Mutates a given BetweenCheckPredicate constraint.
         * 
         * @param predicate The predicate of the constraint
         */
        @Override
        public void visit(BetweenExpression predicate) {
            // BetweenCheckPredicate(<Column>, Operand, Operand)
            new ColumnReplacer(typeCompatibility) {
                @Override
                protected Schema createMutant(Column column, Column replacement, Expression checkPredicate) {
                    BetweenExpression predicate = (BetweenExpression) checkPredicate;
                    mutantTable.addCheckConstraint(new BetweenExpression(
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
                    protected Schema createMutant(Column column, Column replacement, Expression checkPredicate) {
                        BetweenExpression predicate = (BetweenExpression) checkPredicate;
                        mutantTable.addCheckConstraint(new BetweenExpression(
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
                    protected Schema createMutant(Column column, Column replacement, Expression checkPredicate) {
                        BetweenExpression predicate = (BetweenExpression) checkPredicate;
                        mutantTable.addCheckConstraint(new BetweenExpression(
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
        public void visit(InExpression predicate) {
            // InCheckPredicate(<Column>,values)
            new ColumnReplacer(typeCompatibility) {
                @Override
                protected Schema createMutant(Column column, Column replacement, Expression checkPredicate) {
                    InExpression predicate = (InExpression) checkPredicate;
                    mutantTable.addCheckConstraint(new InExpression(
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
        public void visit(RelationalExpression predicate) {
            // RelationalCheckPredicate(<Column>,Operator,Operand)
            Operand lhs = predicate.getLHS();
            if (lhs instanceof Column) {
                new ColumnReplacer(typeCompatibility) {
                    @Override
                    protected Schema createMutant(Column column, Column replacement, Expression checkPredicate) {
                        RelationalExpression predicate = (RelationalExpression) checkPredicate;
                        mutantTable.addCheckConstraint(new RelationalExpression(
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
                    protected Schema createMutant(Column column, Column replacement, Expression checkPredicate) {
                        RelationalExpression predicate = (RelationalExpression) checkPredicate;
                        mutantTable.addCheckConstraint(new RelationalExpression(
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
            protected abstract Schema createMutant(Column column, Column replacement, Expression checkPredicate);

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
                        mutants.add(createMutant(column, replacement, constraint.getExpression()));
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
         * Columns must be of the same type.
         */
        STRICT {
            @Override
            public boolean check(Column column, Column replacement) {
                return column.getType().getClass().equals(replacement.getType().getClass());
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
                    org.schemaanalyst.representation.datatype.BigIntDataType.class,
                    org.schemaanalyst.representation.datatype.DecimalDataType.class,
                    org.schemaanalyst.representation.datatype.DoubleDataType.class,
                    org.schemaanalyst.representation.datatype.FloatDataType.class,
                    org.schemaanalyst.representation.datatype.IntDataType.class,
                    org.schemaanalyst.representation.datatype.MediumIntDataType.class,
                    org.schemaanalyst.representation.datatype.NumericDataType.class,
                    org.schemaanalyst.representation.datatype.RealDataType.class,
                    org.schemaanalyst.representation.datatype.SmallIntDataType.class,
                    org.schemaanalyst.representation.datatype.TinyIntDataType.class
                })));
        private static final Set<Class> STRING_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new Class[]{
                    org.schemaanalyst.representation.datatype.CharDataType.class,
                    org.schemaanalyst.representation.datatype.VarCharDataType.class,
                    org.schemaanalyst.representation.datatype.TextDataType.class
                })));
    };
}