package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.sqlrepresentation.expression.*;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionException;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator.*;

/**
 * Created by phil on 22/07/2014.
 */
public class ClauseAICC extends CondAICC {

    public ClauseAICC(Schema schema,
                      TestRequirementIDGenerator testRequirementIDGenerator,
                      ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public String getName() {
        return "Clause-AICC";
    }

    protected void generateCheckConstraintRequirements(CheckConstraint constraint, boolean truthValue) {
        if (truthValue) {
            generateExpressionRequirements(constraint, null, true);
            generateExpressionRequirements(constraint, true, true);
        } else {
            generateExpressionRequirements(constraint, false, false);
        }
    }

    protected void generateForeignKeyConstraintRequirements(ForeignKeyConstraint constraint, boolean truthValue) {
        if (truthValue) {
            generateOneNullRequirements(constraint, true);
            generateAllMatchRequirement(constraint, true);
        } else {
            generateAllButOneMatchRequirements(constraint, false);
        }
    }

    protected void generatePrimaryKeyConstraintRequirements(PrimaryKeyConstraint constraint, boolean truthValue) {
        if (truthValue) {
            generateAllButOneMatchRequirements(constraint, true);
        } else {
            generateOneNullRequirements(constraint, false);
            generateAllMatchRequirement(constraint, false);
        }
    }

    protected void generateUniqueConstraintRequirements(UniqueConstraint constraint, boolean truthValue) {
        if (truthValue) {
            generateOneNullRequirements(constraint, true);
            generateAllButOneMatchRequirements(constraint, true);
        } else {
            generateAllMatchRequirement(constraint, false);
        }
    }

    protected void generateOneNullRequirements(MultiColumnConstraint constraint, boolean result) {
        Table table = constraint.getTable();
        List<Column> columns = constraint.getColumns();

        for (Column majorColumn : columns) {
            AndPredicate predicate = new AndPredicate();
            predicate.addPredicate(new NullPredicate(table, majorColumn, true));
            for (Column minorColumn : columns) {
                if (!minorColumn.equals(majorColumn)) {
                    predicate.addPredicate(new NullPredicate(table, minorColumn, false));
                }
            }
            String msgSuffix = " - " + majorColumn + " is NULL";
            generateTestRequirement(constraint, msgSuffix, predicate, result);
        }
    }

    protected void generateAllMatchRequirement(MultiColumnConstraint constraint, boolean result) {
        generateTestRequirement(
                constraint,
                " - all cols equal",
                generateMultiColumnConstraintConditionPredicate(constraint, true, false),
                result);
    }

    private void generateAllButOneMatchRequirements(MultiColumnConstraint constraint, boolean result) {
        Table table = constraint.getTable();
        List<Column> columns = constraint.getColumns();

        Table refTable = table;
        List<Column> refColumns = columns;
        if (constraint instanceof ForeignKeyConstraint) {
            ForeignKeyConstraint foreignKeyConstraint = (ForeignKeyConstraint) constraint;
            refTable = foreignKeyConstraint.getReferenceTable();
            refColumns = foreignKeyConstraint.getReferenceColumns();
        }

        Iterator<Column> colsIt = columns.iterator();
        Iterator<Column> refColsIt = refColumns.iterator();

        while (colsIt.hasNext()) {
            Column col = colsIt.next();
            Column refCol = refColsIt.next();

            List<Column> remainingCols = new ArrayList<>(columns);
            remainingCols.remove(col);

            List<Column> refRemainingCols = new ArrayList<>(refColumns);
            refRemainingCols.remove(refCol);

            ComposedPredicate predicate = new AndPredicate();

            Predicate matchPredicate = new MatchPredicate(
                    table,
                    remainingCols,
                    Arrays.asList(col),
                    refTable,
                    refRemainingCols,
                    Arrays.asList(refCol),
                    MatchPredicate.Mode.AND);

            predicate.addPredicate(matchPredicate);
            addNullPredicates(predicate, table, columns, false);

            String msgSuffix = " - all cols equal except " + col;

            generateTestRequirement(constraint, msgSuffix, predicate, result);
        }
    }

    public void generateExpressionRequirements(final CheckConstraint constraint, final Boolean truthValue, final boolean result) {

        constraint.getExpression().accept(new ExpressionVisitor() {
            public void visit(AndExpression expression) {
                generateAndExpressionRequirements(constraint, expression, truthValue, result);
            }

            public void visit(BetweenExpression expression) {
                generateBetweenExpressionRequirements(constraint, expression, truthValue, result);
            }

            @Override
            public void visit(ColumnExpression expression) {
                throw new CoverageCriterionException("Cannot form test requirements for ColumnExpressions");
            }

            @Override
            public void visit(ConstantExpression expression) {
                throw new CoverageCriterionException("Cannot form test requirements for ConstantExpressions");
            }

            public void visit(InExpression expression) {
                generateInExpressionRequirements(constraint, expression, truthValue, result);
            }

            @Override
            public void visit(ListExpression expression) {
                throw new CoverageCriterionException("Cannot form test requirements for ListExpressions");
            }

            @Override
            public void visit(NullExpression expression) {
                throw new CoverageCriterionException("Test requirements for NullExpressions not implemented yet");
            }

            public void visit(OrExpression expression) {
                generateOrExpressionRequirements(constraint, expression, truthValue, result);
            }

            public void visit(ParenthesisedExpression expression) {
                expression.getSubexpression().accept(this);
            }

            public void visit(RelationalExpression expression) {
                generateRelationalExpressionRequirement(constraint, expression, truthValue, result);
            }
        });
    }

    public void generateAndExpressionRequirements(Constraint constraint, AndExpression expression, Boolean truthValue, boolean result) {
        Table table = constraint.getTable();
        List<Column> columnsNotInNullExpressions = columnsNotInNullExpressions(expression);
        List<Expression> subexpressions = expression.getSubexpressions();

        if (truthValue == null) {
            for (int i = 0; i < subexpressions.size(); i++) {
                boolean infeasible = false;
                AndPredicate predicate = new AndPredicate();
                for (int j = 0; j < subexpressions.size(); j++) {
                    Expression subexpression = subexpressions.get(j);
                    if (i == j) {
                        List<Column> subexpressionColumnsNotInNullExpressions = columnsNotInNullExpressions(subexpression);
                        if (subexpressionColumnsNotInNullExpressions.size() > 0) {
                            OrPredicate orPredicate = new OrPredicate();
                            addNullPredicates(orPredicate, table, subexpressionColumnsNotInNullExpressions, true);
                            predicate.addPredicate(orPredicate);
                        } else {
                            infeasible = true;
                        }
                    } else {
                        addNullPredicates(predicate, table, subexpression.getColumnsInvolved(), false);
                        predicate.addPredicate(new ExpressionPredicate(table, subexpression, true));
                    }
                }

                if (infeasible) {
                    // predicate impossible to generate, generate something infeasible for accounting purposes
                    predicate = generateDummyInfeasiblePredicate(table);
                }
                String msgSuffix = subexpressions.get(i) + " is U";
                generateTestRequirement(constraint, msgSuffix, predicate, result);
            }
        } else {
            if (truthValue == true) {
                AndPredicate predicate = new AndPredicate();
                for (Expression subexpression : subexpressions) {
                    predicate.addPredicate(new ExpressionPredicate(table, subexpression, true));
                }
                addNullPredicates(predicate, table, columnsNotInNullExpressions, false);
                String msgSuffix = " - all subexpressions of "+ expression + " are T";
                generateTestRequirement(constraint, msgSuffix, predicate, result);
            } else if (truthValue == false) {
                for (int i = 0; i < subexpressions.size(); i++) {
                    AndPredicate predicate = new AndPredicate();
                    for (int j = 0; j < subexpressions.size(); j++) {
                        Expression subexpression = subexpressions.get(j);
                        boolean truthValueOfExpression = (i != j);
                        predicate.addPredicate(new ExpressionPredicate(table, subexpression, truthValueOfExpression));
                    }
                    addNullPredicates(predicate, table, columnsNotInNullExpressions, false);
                    String msgSuffix = subexpressions.get(i) + " is F";
                    generateTestRequirement(constraint, msgSuffix, predicate, result);
                }
            }
        }
    }

    public void generateBetweenExpressionRequirements(Constraint constraint, BetweenExpression expression, Boolean truthValue, boolean result) {
        // X BETWEEN Y AND Z is transformed to
        // X >= Y AND X <=Z

        Table table = constraint.getTable();

        Expression subject = expression.getSubject();
        Expression lhs = expression.getLHS();
        Expression rhs = expression.getRHS();
        List<Column> columns = expression.getColumnsInvolved();

        if (truthValue == null) {
            // Technically the subconditions of the null condition evaluating to unknown
            // individually is infeasible. We just mandate the "subject" field is NULL.
            OrPredicate predicate = new OrPredicate();
            addNullPredicates(predicate, table, subject.getColumnsInvolved(), true);
            String msgSuffix = " - subject of " + expression + " is U";
            generateTestRequirement(constraint, msgSuffix, predicate, result);
        } else {
            if (truthValue == true) {
                // this is just the between predicate as it was ...
                AndPredicate predicate = new AndPredicate();
                predicate.addPredicate(new ExpressionPredicate(table, expression, true));
                addNullPredicates(predicate, table, columns, false);
                String msgSuffix = " - all subexpressions of "+ expression + " are T";
                generateTestRequirement(constraint, msgSuffix, predicate, result);
            } else {
                // make each of the clauses individually false
                Expression lhsRelationalExpression = new RelationalExpression(subject, RelationalOperator.GREATER_OR_EQUALS, lhs);
                Expression rhsRelationalExpression = new RelationalExpression(subject, RelationalOperator.LESS_OR_EQUALS, rhs);

                // 1) LHS is false
                AndPredicate predicate = new AndPredicate();
                predicate.addPredicate(new ExpressionPredicate(table, lhsRelationalExpression, false));
                predicate.addPredicate(new ExpressionPredicate(table, rhsRelationalExpression, true));
                addNullPredicates(predicate, table, columns, false);
                String msgSuffix = " - LHS subexpression "+ lhs + " is F";
                generateTestRequirement(constraint, msgSuffix, predicate, result);

                // 2) RHS if false
                predicate = new AndPredicate();
                predicate.addPredicate(new ExpressionPredicate(table, lhsRelationalExpression, true));
                predicate.addPredicate(new ExpressionPredicate(table, rhsRelationalExpression, false));
                addNullPredicates(predicate, table, columns, false);
                msgSuffix = " - RHS subexpression "+ rhs + " is F";
                generateTestRequirement(constraint, msgSuffix, predicate, result);
            }
        }
    }

    public void generateInExpressionRequirements(Constraint constraint, InExpression expression, Boolean truthValue, boolean result) {
        // X IN (A, B, C ...) is transformed to
        // (X = A) OR (X = B) OR (X = C) ...

        Table table = constraint.getTable();
        Expression lhs = expression.getLHS();
        List<Expression> subexpressions = expression.getRHS().getSubexpressions();
        List<Column> columns = expression.getColumnsInvolved();

        if (truthValue == null) {
            // Technically the subconditions of the null condition evaluating to unknown
            // individually is infeasible. We just mandate the "subject" field is NULL.
            OrPredicate predicate = new OrPredicate();
            addNullPredicates(predicate, table, lhs.getColumnsInvolved(), true);
            String msgSuffix = "Subject of " + expression + " is U";
            generateTestRequirement(constraint, msgSuffix, predicate, result);
        } else {
            if (truthValue == true) {
                for (Expression subexpression : subexpressions) {
                    AndPredicate predicate = new AndPredicate();
                    Expression equalsExpression = new RelationalExpression(lhs, RelationalOperator.EQUALS, subexpression);
                    predicate.addPredicate(new ExpressionPredicate(table, equalsExpression, true));
                    addNullPredicates(predicate, table, columns, false);
                    String msgSuffix = subexpression + " is T";
                    generateTestRequirement(constraint, msgSuffix, predicate, result);
                }
            } else if (truthValue == false) {
                AndPredicate predicate = new AndPredicate();
                for (Expression subexpression : subexpressions) {
                    Expression notEqualsExpression = new RelationalExpression(lhs, RelationalOperator.NOT_EQUALS, subexpression);
                    predicate.addPredicate(new ExpressionPredicate(table, notEqualsExpression, true));
                }
                addNullPredicates(predicate, table, columns, false);
                String msgSuffix = " - all subexpressions of "+ expression + " are F";
                generateTestRequirement(constraint, msgSuffix, predicate, result);
            }
        }
    }

    public void generateOrExpressionRequirements(Constraint constraint, OrExpression expression, Boolean truthValue, boolean result) {
        Table table = constraint.getTable();
        List<Expression> subexpressions = expression.getSubexpressions();

        if (truthValue == null) {
            for (int i = 0; i < subexpressions.size(); i++) {
                AndPredicate predicate = new AndPredicate();
                for (int j = 0; j < subexpressions.size(); j++) {
                    Expression subexpression = subexpressions.get(j);
                    if (i == j) {
                        OrPredicate orPredicate = new OrPredicate();
                        addNullPredicates(orPredicate, table, subexpression.getColumnsInvolved(), true);
                        predicate.addPredicate(orPredicate);
                    } else {
                        addNullPredicates(predicate, table, subexpression.getColumnsInvolved(), false);
                        predicate.addPredicate(new ExpressionPredicate(table, subexpression, true));
                    }
                }
                String msgSuffix = " - " + subexpressions.get(i) + " is U";
                generateTestRequirement(constraint, msgSuffix, predicate, result);
            }
        } else {
            if (truthValue == true) {
                for (int i = 0; i < subexpressions.size(); i++) {
                    AndPredicate predicate = new AndPredicate();
                    for (int j = 0; j < subexpressions.size(); j++) {
                        Expression subexpression = subexpressions.get(j);
                        boolean truthValueOfExpression = (i == j);
                        predicate.addPredicate(new ExpressionPredicate(table, subexpression, truthValueOfExpression));
                    }
                    addNullPredicates(predicate, table, expression.getColumnsInvolved(), false);
                    String msgSuffix = " - " + subexpressions.get(i) + " is T";
                    generateTestRequirement(constraint, msgSuffix, predicate, result);
                }
            } else if (truthValue == false) {
                AndPredicate predicate = new AndPredicate();
                for (Expression subexpression : subexpressions) {
                    predicate.addPredicate(new ExpressionPredicate(table, subexpression, false));
                }
                addNullPredicates(predicate, table, expression.getColumnsInvolved(), false);
                String msgSuffix = " - all subexpressions of "+ expression + " are F";
                generateTestRequirement(constraint, msgSuffix, predicate, result);
            }
        }
    }

    public void generateRelationalExpressionRequirement(Constraint constraint, RelationalExpression expression, Boolean truthValue, boolean result) {
        Table table = constraint.getTable();

        ComposedPredicate predicate = (truthValue == null)
                ? new OrPredicate()
                : new AndPredicate();

        if (truthValue != null) {
            ExpressionPredicate expressionPredicate = new ExpressionPredicate(table, expression, truthValue);
            predicate.addPredicate(expressionPredicate);
        }

        addNullPredicates(
                predicate,
                table,
                expression.getColumnsInvolved(),
                truthValue == null);

        String msgSuffix = " - " + expression + " is " + ((truthValue == null) ? "U" : ((truthValue) ? "T" : "F"));
        generateTestRequirement(constraint, msgSuffix, predicate, result);
    }
}
