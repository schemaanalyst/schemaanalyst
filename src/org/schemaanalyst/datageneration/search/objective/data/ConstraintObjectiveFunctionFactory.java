package org.schemaanalyst.datageneration.search.objective.data;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.deprecated.datageneration.objective.constraint.checkcondition.CheckConditionObjectiveFunctionFactory;
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
    protected boolean goalIsToSatisfy, considerNull;

    public ConstraintObjectiveFunctionFactory(Constraint constraint,
                                              Data state,
                                              boolean goalIsToSatisfy,
                                              boolean considerNull) {
        this.constraint = constraint;
        this.state = state;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.considerNull = considerNull;
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

        boolean allowNull = considerNull && goalIsToSatisfy;

        if (checkConstraint.getCheckCondition() != null) {
            return (new CheckConditionObjectiveFunctionFactory(
                    checkConstraint,
                    state,
                    goalIsToSatisfy,
                    allowNull,
                    makeDescription())).create();
        } else {
            return new ExpressionObjectiveFunction(
                    checkConstraint.getExpression(),
                    checkConstraint.getTable(),
                    makeDescription(),
                    goalIsToSatisfy,
                    allowNull);
        }
    }

    protected ObjectiveFunction<Data> createForPrimaryKeyConstraint(PrimaryKeyConstraint primaryKeyConstraint) {

        boolean allowNull = false; //considerNull && !goalIsToSatisfy;

        return new UniqueObjectiveFunction(
                primaryKeyConstraint.getColumns(),
                state,
                makeDescription(),
                goalIsToSatisfy,
                allowNull);
    }

    protected ObjectiveFunction<Data> createForForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) {

        boolean allowNull = considerNull && goalIsToSatisfy;

        return new ReferenceObjectiveFunction(
                foreignKeyConstraint.getColumns(),
                foreignKeyConstraint.getReferenceColumns(),
                state,
                makeDescription(),
                goalIsToSatisfy,
                allowNull);
    }

    protected ObjectiveFunction<Data> createForNotNullConstraint(NotNullConstraint notNullConstraint) {

        return new NullColumnObjectiveFunction(
                notNullConstraint.getColumn(),
                makeDescription(),
                !goalIsToSatisfy);
    }

    protected ObjectiveFunction<Data> createForUniqueConstraint(UniqueConstraint uniqueConstraint) {

        boolean allowNull = considerNull && goalIsToSatisfy;

        return new UniqueObjectiveFunction(
                uniqueConstraint.getColumns(),
                state,
                makeDescription(),
                goalIsToSatisfy,
                allowNull);
    }

    protected String makeDescription() {
        return ((goalIsToSatisfy) ? "Satisfy" : "Violate") + " " + constraint.toString();
    }
}
