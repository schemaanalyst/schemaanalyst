package org.schemaanalyst.deprecated.datageneration.analyst;

import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;

public class ConstraintAnalystFactory {

    public ConstraintAnalyst create(Constraint constraint) {
        return create(constraint, false);
    }

    public ConstraintAnalyst create(Constraint constraint,
            boolean considerNull) {

        class ConstraintAnalystCreator implements ConstraintVisitor {

            ConstraintAnalyst analyst;
            boolean considerNull;

            public ConstraintAnalyst createFor(Constraint constraint,
                    boolean considerNull) {
                this.considerNull = considerNull;
                constraint.accept(this);
                return analyst;
            }

            @Override
            public void visit(CheckConstraint constraint) {
            }

            @Override
            public void visit(ForeignKeyConstraint foreignKey) {
                analyst = new ReferenceAnalyst(foreignKey.getColumns(),
                        foreignKey.getReferenceColumns(),
                        considerNull);
            }

            @Override
            public void visit(NotNullConstraint notNull) {
                analyst = new NotNullAnalyst(notNull.getColumn());
            }

            @Override
            public void visit(PrimaryKeyConstraint primaryKey) {
                // NULL should never satisfy a primary key constraint
                // hence can never consider NULL.

                analyst = new UniqueAnalyst(primaryKey.getColumns(),
                        false);
            }

            @Override
            public void visit(UniqueConstraint unique) {
                analyst = new UniqueAnalyst(unique.getColumns(),
                        considerNull);
            }
        }

        return new ConstraintAnalystCreator().createFor(constraint, considerNull);
    }
}
