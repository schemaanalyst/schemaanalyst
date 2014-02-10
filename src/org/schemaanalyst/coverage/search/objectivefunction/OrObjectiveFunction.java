package org.schemaanalyst.coverage.search.objectivefunction;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 09/02/2014.
 */
public class OrObjectiveFunction extends ObjectiveFunction<Data> {

    private List<ObjectiveFunction<Data>> objectiveFunctions;

    public OrObjectiveFunction(List<ObjectiveFunction<Data>> objectiveFunctions) {
        this.objectiveFunctions = new ArrayList<>(objectiveFunctions);
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        BestOfMultiObjectiveValue objVal = new BestOfMultiObjectiveValue("Or");
        for (ObjectiveFunction<Data> objFun : objectiveFunctions) {
            objVal.add(objFun.evaluate(data));
        }
        return objVal;
    }
}
