package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;

import java.util.Arrays;
import java.util.List;

import static org.schemaanalyst.testgeneration.coveragecriterion.clause.ClauseFactory.*;

/**
 * Created by phil on 02/02/2014.
 */
public class ConstraintPredicateGenerator {

    private Schema schema;
    private Table table;
    private Constraint ignoreConstraint;
    private Predicate predicate;

    public ConstraintPredicateGenerator(Schema schema, Table table) {
        this(schema, table, null);
    }

    public ConstraintPredicateGenerator(Schema schema, Table table, Constraint ignoreConstraint) {
        this.schema = schema;
        this.table = table;
        this.ignoreConstraint = ignoreConstraint;
    }

    public Predicate generate(String purpose) {
        return generate(Arrays.asList(purpose));
    }

    public Predicate generate(List<String> purposes) {
        predicate = new Predicate(purposes);
        List<Constraint> constraints = schema.getConstraints(table);

        for (Constraint constraint : constraints) {
            if (!constraint.equals(ignoreConstraint)) {
                addClausesForConstraint(constraint);
            }
        }

        return predicate;
    }

    private void addClausesForConstraint(Constraint constraint) {
        constraint.accept(
            new ConstraintVisitor() {
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
                    Table table = constraint.getTable();
                    List<Column> columns = constraint.getColumns();
                    predicate.addClause(
                            unique(table, columns, false)
                    );
                    for (Column column : columns) {
                        predicate.setColumnNullStatus(table, column, false);
                    }
                }

                @Override
                public void visit(UniqueConstraint constraint) {
                    predicate.addClause(
                            unique(constraint.getTable(), constraint.getColumns(), false)
                    );
                }
            }
        );
    }

}
