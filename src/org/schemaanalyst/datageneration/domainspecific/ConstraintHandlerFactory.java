package org.schemaanalyst.datageneration.domainspecific;

import org.schemaanalyst.datageneration.analyst.ConstraintAnalyst;
import org.schemaanalyst.datageneration.analyst.NotNullAnalyst;
import org.schemaanalyst.datageneration.analyst.ReferenceAnalyst;
import org.schemaanalyst.datageneration.analyst.UniqueAnalyst;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;
import org.schemaanalyst.util.random.Random;

public class ConstraintHandlerFactory {

    protected Constraint constraint;
    protected boolean goalIsToSatisfy;
    protected CellRandomiser cellRandomizer;
    protected Random random;

    public ConstraintHandlerFactory(Constraint constraint,
            boolean goalIsToSatisfy,
            CellRandomiser cellRandomizer,
            Random random) {
        this.constraint = constraint;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.cellRandomizer = cellRandomizer;
        this.random = random;
    }

    public ConstraintHandler<? extends ConstraintAnalyst> create() {

        class ConstraintDispatcher implements ConstraintVisitor {

            ConstraintHandler<?> constraintHandler;

            ConstraintHandler<?> dispatch(Constraint constraint) {
                constraint.accept(this);
                return constraintHandler;
            }

            @Override
            public void visit(CheckConstraint constraint) {
                constraintHandler = create(constraint);
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                constraintHandler = create(constraint);
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                constraintHandler = create(constraint);
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                constraintHandler = create(constraint);
            }

            @Override
            public void visit(UniqueConstraint constraint) {
                constraintHandler = create(constraint);
            }
        }

        return new ConstraintDispatcher().dispatch(constraint);
    }

    protected ConstraintHandler<?> create(CheckConstraint checkConstraint) {
        return null;
    }

    protected ConstraintHandler<?> create(ForeignKeyConstraint foreignKeyConstraint) {
        boolean allowNull = goalIsToSatisfy;

        return new ReferenceHandler(new ReferenceAnalyst(foreignKeyConstraint.getColumns(),
                foreignKeyConstraint.getReferenceColumns(),
                allowNull),
                goalIsToSatisfy,
                allowNull,
                cellRandomizer,
                random);
    }

    protected ConstraintHandler<?> create(NotNullConstraint notNullConstraint) {
        return new NotNullHandler(new NotNullAnalyst(notNullConstraint.getColumn()),
                goalIsToSatisfy);

    }

    protected ConstraintHandler<?> create(PrimaryKeyConstraint primaryKeyConstraint) {
        boolean allowNull = !goalIsToSatisfy;

        return new UniqueHandler(new UniqueAnalyst(primaryKeyConstraint.getColumns(),
                allowNull),
                goalIsToSatisfy,
                allowNull,
                cellRandomizer,
                random);
    }

    protected ConstraintHandler<?> create(UniqueConstraint uniqueConstraint) {
        boolean allowNull = goalIsToSatisfy;

        return new UniqueHandler(new UniqueAnalyst(uniqueConstraint.getColumns(),
                allowNull),
                goalIsToSatisfy,
                allowNull,
                cellRandomizer,
                random);
    }
}
