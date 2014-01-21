package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.predicate.Clause;
import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.function.DistinctFunction;
import org.schemaanalyst.coverage.predicate.function.ExpressionFunction;
import org.schemaanalyst.coverage.predicate.function.MatchesFunction;
import org.schemaanalyst.coverage.predicate.function.NotNullFunction;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
class DefaultPredicateGenerator {


    class FunctionSetter implements ConstraintVisitor {

        @Override
        public void visit(CheckConstraint constraint) {
            clause.setFunctions(
                    new ExpressionFunction(constraint.getTable(), constraint.getExpression())
            );
        }

        @Override
        public void visit(ForeignKeyConstraint constraint) {
            clause.setFunctions(
                    new MatchesFunction(
                            constraint.getTable(), constraint.getColumns(),
                            constraint.getReferenceTable(), constraint.getReferenceColumns())
            );
        }

        @Override
        public void visit(NotNullConstraint constraint) {
            clause.setFunctions(
                    new NotNullFunction(constraint.getTable(), constraint.getColumn())
            );
        }

        @Override
        public void visit(PrimaryKeyConstraint constraint) {
            clause.setFunctions(
                    new DistinctFunction(constraint.getTable(), constraint.getColumns())
            );
        }

        @Override
        public void visit(UniqueConstraint constraint) {
            clause.setFunctions(
                    new DistinctFunction(constraint.getTable(), constraint.getColumns())
            );
        }
    }

    Clause clause;

    Predicate generatePredicate(Schema schema, Table table) {
        FunctionSetter functionSetter = new FunctionSetter();
        Predicate predicate = new Predicate();
        List<Constraint> constraints = schema.getConstraints(table);

        for (Constraint constraint : constraints) {
            clause = new Clause();
            constraint.accept(functionSetter);
            predicate.addClause(constraint, clause);
        }

        return predicate;
    }
}
