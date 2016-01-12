package org.schemaanalyst.faultlocalization;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CreateDefectiveSchemas {

	public static void createDefects(String casestudy, String pipe, String dbms){
		// TODO Auto-generated method stub
        Schema schema;
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(pipe, schema, dbms);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        List<Mutant<Schema>> mutatedSchema = pipeline.mutate();
        int counter = 0;
        String firstOp = mutatedSchema.get(0).getSimpleDescription();
        for(int i = 0; i < mutatedSchema.size(); i++){
        	String operator = mutatedSchema.get(i).getSimpleDescription();
        	if(i < 1){
        		CreateSchemas.CreateDefectiveSchemas(operator, mutatedSchema.get(i).getMutatedArtefact(), counter);
        		counter++;
        	}else{
        		if(operator.equals(mutatedSchema.get(i-1).getSimpleDescription())){
        			CreateSchemas.CreateDefectiveSchemas(operator, mutatedSchema.get(i).getMutatedArtefact(), counter);
        			counter++;
        		}
        		else{
        			counter=0;
        			CreateSchemas.CreateDefectiveSchemas(operator, mutatedSchema.get(i).getMutatedArtefact(), counter);
        			counter++;
        		}
        	}
        	
        }
	}
}
