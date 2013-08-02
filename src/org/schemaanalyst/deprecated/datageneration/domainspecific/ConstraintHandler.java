package org.schemaanalyst.deprecated.datageneration.domainspecific;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.deprecated.datageneration.analyst.ConstraintAnalyst;

public abstract class ConstraintHandler<A extends ConstraintAnalyst> {

    protected A analyst;
    protected boolean goalIsToSatisfy;
    protected Data state, data;

    public ConstraintHandler(A analyst,
            boolean goalIsToSatisfy) {

        this.analyst = analyst;
        this.goalIsToSatisfy = goalIsToSatisfy;
    }

    public boolean isFulfilled(Data state, Data data) {
        return analyst.isSatisfied(state, data) == goalIsToSatisfy;
    }

    public boolean attempt(Data state, Data data) {
        this.state = state;
        this.data = data;

        performAttempt();

        return isFulfilled(state, data);
    }

    protected void performAttempt() {
        if (goalIsToSatisfy) {
            attemptToSatisfy();
        } else {
            attemptToFalsify();
        }
    }

    protected abstract void attemptToSatisfy();

    protected abstract void attemptToFalsify();
}
