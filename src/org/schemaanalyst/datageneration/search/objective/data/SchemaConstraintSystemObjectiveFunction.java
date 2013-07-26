package org.schemaanalyst.datageneration.search.objective.data;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;

public class SchemaConstraintSystemObjectiveFunction extends ObjectiveFunction<Data> {

    protected Data state, data;
    protected String description;
    protected List<ObjectiveFunction<Data>> objFuns;

    public SchemaConstraintSystemObjectiveFunction(Schema schema) {
        this(schema, null, null);
    }

    public SchemaConstraintSystemObjectiveFunction(Schema schema, Data state, Constraint constraintToInvalidate) {
        this.state = state;
        objFuns = new ArrayList<>();

        boolean considerNull = constraintToInvalidate != null;

        for (Constraint constraint : schema.getConstraints()) {
            boolean satisfyConstraint = !constraint.equals(constraintToInvalidate);

            // sticking plaster
            if ((constraintToInvalidate instanceof NotNullConstraint)
                    && (constraint instanceof PrimaryKeyConstraint)) {

                PrimaryKeyConstraint primaryKey = (PrimaryKeyConstraint) constraint;
                NotNullConstraint notNull = (NotNullConstraint) constraintToInvalidate;

                if (primaryKey.getColumns().contains(notNull.getColumn())) {
                    continue;
                }
            }

            ConstraintObjectiveFunctionFactory factory = new ConstraintObjectiveFunctionFactory(
                    constraint, state, satisfyConstraint, considerNull);

            objFuns.add(factory.create());
        }

        description = constraintToInvalidate == null
                ? "Satisfy all constraints"
                : "Satisfy all except " + constraintToInvalidate;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

        for (ObjectiveFunction<Data> objFun : objFuns) {
            objVal.add(objFun.evaluate(data));
        }

        return objVal;
    }
}
