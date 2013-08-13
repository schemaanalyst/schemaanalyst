package org.schemaanalyst.datageneration.search;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.datageneration.search.datainitialization.DataInitialiser;
import org.schemaanalyst.datageneration.search.handler.ColumnHandler;
import org.schemaanalyst.datageneration.search.handler.ExpressionColumnHandler;
import org.schemaanalyst.datageneration.search.handler.NullColumnHandler;
import org.schemaanalyst.datageneration.search.handler.ReferenceColumnHandler;
import org.schemaanalyst.datageneration.search.handler.UniqueColumnHandler;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.constraint.SchemaConstraintSystemObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.data.ExpressionColumnObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.data.NullColumnObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.data.ReferenceColumnObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.data.UniqueColumnObjectiveFunction;
import org.schemaanalyst.util.random.Random;

public class DirectedRandomSearch extends Search<Data> {

    private Random random;
    private DataInitialiser startInitializer;
    private CellRandomiser cellRandomiser;
    
    private List<ColumnHandler<?>> handlers; 
    
    public DirectedRandomSearch(Random random,
            DataInitialiser startInitialiser,
            CellRandomiser cellRandomiser) {
        this.random = random;
        this.startInitializer = startInitialiser;
        this.cellRandomiser = cellRandomiser;
    }

    @Override
    public void search(Data data) {

        // start
        startInitializer.initialize(data);
        evaluate(data);

        // main loop
        while (!terminationCriterion.satisfied()) {
            
            adapt(data);
            evaluate(data);
        }
    }
    
    @Override
    public void setObjectiveFunction(ObjectiveFunction<Data> objectiveFunction) {
        super.setObjectiveFunction(objectiveFunction);
        
        handlers = new ArrayList<>();
        
        // TODO: refactor -- for obvious reasons ...
        SchemaConstraintSystemObjectiveFunction constraintSysObjFun = (SchemaConstraintSystemObjectiveFunction) objectiveFunction;
        List<ObjectiveFunction<Data>> subObjFuns = constraintSysObjFun.getSubObjectiveFunctions();
        for (ObjectiveFunction<Data> objFun : subObjFuns) {
            
            if (objFun instanceof ExpressionColumnObjectiveFunction) {
                handlers.add(new ExpressionColumnHandler((ExpressionColumnObjectiveFunction) objFun, cellRandomiser));
            }
            
            if (objFun instanceof NullColumnObjectiveFunction) {
                handlers.add(new NullColumnHandler((NullColumnObjectiveFunction) objFun));
            }
            
            if (objFun instanceof ReferenceColumnObjectiveFunction) {
                handlers.add(new ReferenceColumnHandler((ReferenceColumnObjectiveFunction) objFun, random, cellRandomiser));
            }
            
            if (objFun instanceof UniqueColumnObjectiveFunction) {
                handlers.add(new UniqueColumnHandler((UniqueColumnObjectiveFunction) objFun, random, cellRandomiser));
            }
        }
    }
    
    private void adapt(Data data) {
        for (ColumnHandler<?> handler : handlers) {
            handler.attemptToFindSolution(data);
        }
    }
}
