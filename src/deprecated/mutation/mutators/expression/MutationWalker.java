/*
 */
package deprecated.mutation.mutators.expression;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.AndExpression;
import org.schemaanalyst.sqlrepresentation.expression.BetweenExpression;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.InExpression;
import org.schemaanalyst.sqlrepresentation.expression.ListExpression;
import org.schemaanalyst.sqlrepresentation.expression.NullExpression;
import org.schemaanalyst.sqlrepresentation.expression.OrExpression;
import org.schemaanalyst.sqlrepresentation.expression.ParenthesisedExpression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

/**
 *
 * @author Chris J. Wright
 */
public class MutationWalker extends ExpressionWalker {

    private AbstractMutator mutator;
    private Table table;
    private List<Schema> mutants;
    private int checkNumber;

    public MutationWalker(AbstractMutator mutator) {
        this.mutator = mutator;
    }

    public void produceMutants(Table table, List<Schema> mutants) {
        this.table = table;
        this.mutants = mutants;
        List<CheckConstraint> checkConstraints = table.getCheckConstraints();
        for (int i = 0; i < checkConstraints.size(); i++) {
            checkNumber = i;
            exprStack = new ArrayDeque<>();
            checkConstraints.get(i).getExpression().accept(this);
        }
    }

    private void createMutant(Expression mutantExpr) {
        // Make a duplicate
        Schema schema = table.getSchema();
        Schema mSchema = schema.duplicate();
        Table mTable = mSchema.getTable(table.getName());

        // Get the same constraint in the mutant
        CheckConstraint mCheck = mTable.getCheckConstraints().get(checkNumber);
        if (exprStack.isEmpty()) {
            // Easy case - mutated top level
            mCheck.setExpression(mutantExpr);
        } else {
            // Hard case - mutated nested level
            Iterator<Integer> iter = exprStack.descendingIterator();
            int lastIndex = iter.next();
            Expression parentExpr = mCheck.getExpression();
            Expression childExpr = parentExpr.getSubexpression(lastIndex);
            while (iter.hasNext()) {
                parentExpr = childExpr;
                lastIndex = iter.next();
                childExpr = parentExpr.getSubexpression(lastIndex);
            }
            List<Expression> subExprs = new ArrayList<>(parentExpr.getSubexpressions());
            subExprs.set(lastIndex, mutantExpr);
            parentExpr.setSubexpressions(subExprs);
        }
        mutants.add(mSchema);
    }

    @Override
    public void visit(AndExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }

    @Override
    public void visit(BetweenExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }

    @Override
    public void visit(ColumnExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }

    @Override
    public void visit(ConstantExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }

    @Override
    public void visit(InExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }

    @Override
    public void visit(ListExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }

    @Override
    public void visit(NullExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }

    @Override
    public void visit(OrExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }

    @Override
    public void visit(ParenthesisedExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }

    @Override
    public void visit(RelationalExpression expression) {
        mutator.visit(expression);
        for (Expression mutant : mutator.getMutants()) {
            createMutant(mutant);
        }
        mutator.resetMutants();
        super.visit(expression);
    }
}
