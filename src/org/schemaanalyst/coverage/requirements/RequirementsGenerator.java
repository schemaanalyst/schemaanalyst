package org.schemaanalyst.coverage.requirements;

import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.TestRequirements;
import org.schemaanalyst.coverage.predicate.function.UniqueFunction;
import org.schemaanalyst.coverage.predicate.function.ExpressionFunction;
import org.schemaanalyst.coverage.predicate.function.MatchFunction;
import org.schemaanalyst.coverage.predicate.function.NotNullFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 20/01/2014.
 */
public abstract class RequirementsGenerator {

    class ClauseGenerator implements ConstraintVisitor {

        Predicate predicate;

        void addClauses(Predicate predicate, Constraint constraint) {
            this.predicate = predicate;
            constraint.accept(this);
        }

        @Override
        public void visit(CheckConstraint constraint) {
            predicate.addClause(
                    constraint, new ExpressionFunction(constraint.getTable(), constraint.getExpression())
            );
        }

        @Override
        public void visit(ForeignKeyConstraint constraint) {
            predicate.addClause(
                    constraint,
                    new MatchFunction(
                        constraint.getTable(), constraint.getColumns(), new ArrayList<Column>(),
                        constraint.getReferenceTable(), constraint.getReferenceColumns(), new ArrayList<Column>()
                    )
            );
        }

        @Override
        public void visit(NotNullConstraint constraint) {
            predicate.addClause(
                    constraint, new NotNullFunction(constraint.getTable(), constraint.getColumn())
            );
        }

        @Override
        public void visit(PrimaryKeyConstraint constraint) {
            predicate.addClause(
                    constraint, new UniqueFunction(constraint.getTable(), constraint.getColumns())
            );
        }

        @Override
        public void visit(UniqueConstraint constraint) {
            predicate.addClause(
                    constraint, new UniqueFunction(constraint.getTable(), constraint.getColumns())
            );
        }
    }

    protected Schema schema;
    protected Table table;
    protected Constraint constraint;

    public RequirementsGenerator(Schema schema, Table table) {
        this(schema, table, null);
    }

    public RequirementsGenerator(Schema schema, Table table, Constraint constraint) {
        this.schema = schema;
        this.table = table;
        this.constraint = constraint;
    }

    protected Predicate generatePredicate(String purpose) {
        ClauseGenerator clauseGenerator = new ClauseGenerator();
        Predicate predicate = new Predicate(purpose);
        List<Constraint> constraints = schema.getConstraints(table);

        for (Constraint constraint : constraints) {
            clauseGenerator.addClauses(predicate, constraint);
        }

        if (constraint != null) {
            predicate.removeClause(constraint);
        }

        return predicate;
    }

    public abstract TestRequirements generateRequirements();
}
