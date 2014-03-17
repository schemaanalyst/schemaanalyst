package _deprecated.datageneration.search.objective.constraint;

import org.schemaanalyst.data.Data;
import _deprecated.datageneration.search.objective.ObjectiveFunction;
import _deprecated.datageneration.search.objective.data.ExpressionColumnObjectiveFunction;
import _deprecated.datageneration.search.objective.data.NullColumnObjectiveFunction;
import _deprecated.datageneration.search.objective.data.ReferenceColumnObjectiveFunction;
import _deprecated.datageneration.search.objective.data.UniqueColumnObjectiveFunction;
import org.schemaanalyst.sqlrepresentation.constraint.*;

public class ConstraintObjectiveFunctionFactory {

    protected Constraint constraint;
    protected Data state;
    protected boolean goalIsToSatisfy, allowNull;

    public ConstraintObjectiveFunctionFactory(Constraint constraint,
                                              Data state,
                                              boolean goalIsToSatisfy,
                                              boolean allowNull) {
        this.constraint = constraint;
        this.state = state;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;
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

        boolean constraintAllowNull = allowNull && goalIsToSatisfy;
        
        return new ExpressionColumnObjectiveFunction(
                    checkConstraint.getTable(),
                    checkConstraint.getExpression(),
                    makeDescription(),
                    goalIsToSatisfy,
                    constraintAllowNull);
    }

    protected ObjectiveFunction<Data> createForPrimaryKeyConstraint(PrimaryKeyConstraint primaryKeyConstraint) {
        
        // make it impossible for the constraint to satisfied or negated using NULLs
        // normally NULLs are not allowed only when PKs are to be satisfied, 
        // but SQLite allows NULLs for PK values while other DBMSs do not.
        boolean constraintAllowNull = false;
        
        return new UniqueColumnObjectiveFunction(
                primaryKeyConstraint.getTable(),
                primaryKeyConstraint.getColumns(),
                state,
                makeDescription(),
                goalIsToSatisfy,
                constraintAllowNull);  
    }

    protected ObjectiveFunction<Data> createForForeignKeyConstraint(ForeignKeyConstraint foreignKeyConstraint) {

        boolean constraintAllowNull = allowNull && goalIsToSatisfy;
        
        return new ReferenceColumnObjectiveFunction(
                foreignKeyConstraint.getTable(),
                foreignKeyConstraint.getColumns(),
                foreignKeyConstraint.getReferenceTable(),
                foreignKeyConstraint.getReferenceColumns(),
                state,
                makeDescription(),
                goalIsToSatisfy,
                constraintAllowNull);
    }

    protected ObjectiveFunction<Data> createForNotNullConstraint(NotNullConstraint notNullConstraint) {

        return new NullColumnObjectiveFunction(
                notNullConstraint.getTable(),
                notNullConstraint.getColumn(),
                makeDescription(),
                !goalIsToSatisfy);
    }

    protected ObjectiveFunction<Data> createForUniqueConstraint(UniqueConstraint uniqueConstraint) {

        boolean constraintAllowNull = allowNull && goalIsToSatisfy;
        
        return new UniqueColumnObjectiveFunction(
                uniqueConstraint.getTable(),
                uniqueConstraint.getColumns(),
                state,
                makeDescription(),
                goalIsToSatisfy,
                constraintAllowNull);
    }

    protected String makeDescription() {
        return ((goalIsToSatisfy) ? "Satisfy" : "Violate") + " " + constraint.toString();
    }
}
