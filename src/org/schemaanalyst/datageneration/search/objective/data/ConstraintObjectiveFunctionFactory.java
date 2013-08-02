package org.schemaanalyst.datageneration.search.objective.data;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;

public class ConstraintObjectiveFunctionFactory {

    protected Constraint constraint;
    protected Data state;
    protected boolean goalIsToSatisfy, nullAdmissableForSatisfy;

    public ConstraintObjectiveFunctionFactory(Constraint constraint,
                                              Data state,
                                              boolean goalIsToSatisfy,
                                              boolean nullAdmissableForSatisfy) {
        this.constraint = constraint;
        this.state = state;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.nullAdmissableForSatisfy = nullAdmissableForSatisfy;
    }

    public ObjectiveFunction<Data> create() {

        class ConstraintDispatcher implements ConstraintVisitor {

            ObjectiveFunction<Data> objFun;

            ObjectiveFunction<Data> dispatch() {
                constraint.accept(this);
                return objFun;
            }

            @Override
            public void visit(CheckConstraint constraint) {
                objFun = createForCheckConstraint(constraint);
            }

            @Override
            public void visit(ForeignKeyConstraint constraint) {
                objFun = createForForeignKeyConstraint(constraint);
            }

            @Override
            public void visit(NotNullConstraint constraint) {
                objFun = createForNotNullConstraint(constraint);
            }

            @Override
            public void visit(PrimaryKeyConstraint constraint) {
                objFun = createForPrimaryKeyConstraint(constraint);
            }

            @Override
            public void visit(UniqueConstraint constraint) {
                objFun = createForUniqueConstraint(constraint);
            }
        }

        return (new ConstraintDispatcher()).dispatch();
    }

    protected ObjectiveFunction<Data> createForCheckConstraint(CheckConstraint checkConstraint) {

        return new ExpressionConstraintObjectiveFunction(
                    checkConstraint.getExpression(),
                    makeDescription(),
                    goalIsToSatisfy,
                    nullAdmissableForSatisfy);
    }

    protected ObjectiveFunction<Data> createForPrimaryKeyConstraint(PrimaryKeyConstraint primaryKeyConstraint) {
        
        return new UniqueConstraintObjectiveFunction(
                primaryKeyConstraint.getColumns(),
                state,
                makeDescription(),
                goalIsToSatisfy,
                !goalIsToSatisfy); // make it impossible for the constraint to satisfied or negated using NULLs 
    }

    protected ObjectiveFunction<Data> createForForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) {

        return new ReferenceConstraintObjectiveFunction(
                foreignKeyConstraint.getColumns(),
                foreignKeyConstraint.getReferenceColumns(),
                state,
                makeDescription(),
                goalIsToSatisfy,
                nullAdmissableForSatisfy);
    }

    protected ObjectiveFunction<Data> createForNotNullConstraint(NotNullConstraint notNullConstraint) {

        return new NullConstraintObjectiveFunction(
                notNullConstraint.getColumn(),
                makeDescription(),
                !goalIsToSatisfy);
    }

    protected ObjectiveFunction<Data> createForUniqueConstraint(UniqueConstraint uniqueConstraint) {

        return new UniqueConstraintObjectiveFunction(
                uniqueConstraint.getColumns(),
                state,
                makeDescription(),
                goalIsToSatisfy,
                nullAdmissableForSatisfy);
    }

    protected String makeDescription() {
        return ((goalIsToSatisfy) ? "Satisfy" : "Violate") + " " + constraint.toString();
    }
}
