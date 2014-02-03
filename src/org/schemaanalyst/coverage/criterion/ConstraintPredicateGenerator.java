package org.schemaanalyst.coverage.criterion;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.List;

import static org.schemaanalyst.coverage.criterion.clause.ClauseFactory.*;

/**
 * Created by phil on 02/02/2014.
 */
public class ConstraintPredicateGenerator implements ConstraintVisitor {

    private Schema schema;
    private Table table;
    private Constraint constraint;
    protected Predicate predicate;

    public ConstraintPredicateGenerator(Schema schema, Table table) {
        this(schema, table, null);
    }

    public ConstraintPredicateGenerator(Schema schema, Table table, Constraint constraint) {
        this.schema = schema;
        this.table = table;
        this.constraint = constraint;
    }

    public Predicate generate(String purpose) {
        predicate = new Predicate(purpose);
        List<Constraint> constraints = schema.getConstraints(table);

        for (Constraint tableConstraint : constraints) {
            if (!tableConstraint.equals(constraint)) {
                tableConstraint.accept(this);
            }
        }

        return predicate;
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
