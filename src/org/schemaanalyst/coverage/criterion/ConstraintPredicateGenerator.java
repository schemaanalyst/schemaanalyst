package org.schemaanalyst.coverage.criterion;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.*;

/**
 * Created by phil on 02/02/2014.
 */
public class ConstraintPredicateGenerator {

    class ClauseGenerator implements ConstraintVisitor {

        Predicate predicate;

        void addClauses(Predicate predicate, Constraint constraint) {
            this.predicate = predicate;
            constraint.accept(this);
        }

        @Override
        public void visit(CheckConstraint constraint) {
            predicate.addClause(
                    expression(constraint.getTable(), constraint.getExpression())
            );
        }

        @Override
        public void visit(ForeignKeyConstraint constraint) {
            predicate.addClause(
                    references(
                            constraint.getTable(),
                            constraint.getColumns(),
                            constraint.getReferenceTable(),
                            constraint.getReferenceColumns())
            );
        }

        @Override
        public void visit(NotNullConstraint constraint) {
            predicate.addClause(
                    isNotNull(constraint.getTable(), constraint.getColumn())
            );
        }

        @Override
        public void visit(PrimaryKeyConstraint constraint) {
            predicate.addClause(
                    unique(constraint.getTable(), constraint.getColumns(), false)
            );
        }

        @Override
        public void visit(UniqueConstraint constraint) {
            predicate.addClause(
                    unique(constraint.getTable(), constraint.getColumns(), false)
            );
        }
    }

    public Predicate generate(Schema schema, Table table, String purpose) {
        return generate(schema, table, null, purpose);
    }

    public Predicate generate(Schema schema, Table table, Constraint constraint, String purpose) {
        ClauseGenerator clauseGenerator = new ClauseGenerator();
        Predicate predicate = new Predicate(purpose);
        List<Constraint> constraints = schema.getConstraints(table);

        for (Constraint tableConstraint : constraints) {
            if (!tableConstraint.equals(constraint)) {
                clauseGenerator.addClauses(predicate, tableConstraint);
            }
        }

        return predicate;
    }
}
