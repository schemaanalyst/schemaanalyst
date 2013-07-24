package org.schemaanalyst.datageneration.search.objective.constraint.checkcondition;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.BetweenCheckCondition;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.CheckConditionVisitor;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.InCheckCondition;
import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.RelationalCheckCondition;
import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Table;

public class CheckConditionObjectiveFunctionFactory {

    protected CheckConstraint checkConstraint;
    protected Data state;
    protected boolean goalIsToSatisfy, allowNull;
    protected String description;

    public CheckConditionObjectiveFunctionFactory(CheckConstraint checkConstraint,
            Data state,
            boolean goalIsToSatisfy,
            boolean allowNull,
            String description) {
        this.checkConstraint = checkConstraint;
        this.state = state;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;
        this.description = description;
    }

    public ObjectiveFunction<Data> create() {

        class CheckConditionDispatcher implements CheckConditionVisitor {

            ObjectiveFunction<Data> objFun;
            Table table;
            String description;
            boolean allowNull;

            ObjectiveFunction<Data> dispatch() {
                table = checkConstraint.getTable();
                checkConstraint.getCheckCondition().accept(this);
                return objFun;
            }

            @Override
            public void visit(BetweenCheckCondition predicate) {
                objFun = new BetweenCheckConditionObjectiveFunction(
                        predicate, table, state, description, goalIsToSatisfy, allowNull);
            }

            @Override
            public void visit(InCheckCondition predicate) {
                objFun = new InCheckConditionObjectiveFunction(
                        predicate, table, state, description, goalIsToSatisfy, allowNull);
            }

            @Override
            public void visit(RelationalCheckCondition predicate) {
                objFun = new RelationalCheckConditionObjectiveFunction(
                        predicate, table, state, description, goalIsToSatisfy, allowNull);
            }
        }

        return (new CheckConditionDispatcher()).dispatch();
    }
}
