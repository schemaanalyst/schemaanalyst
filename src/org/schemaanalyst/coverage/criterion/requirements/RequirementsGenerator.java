package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.*;

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

        for (Constraint tableConstraint : constraints) {
            if (!tableConstraint.equals(constraint)) {
                clauseGenerator.addClauses(predicate, tableConstraint);
            }
        }

        return predicate;
    }

    public abstract Requirements generateRequirements();
}
