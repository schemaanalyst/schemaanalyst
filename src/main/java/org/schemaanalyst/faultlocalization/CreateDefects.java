package org.schemaanalyst.faultlocalization;

import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.Runner;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Logger;

public class CreateDefects extends Runner{
	
	private final static Logger LOGGER = Logger.getLogger(CreateDefects.class.getName());
    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;
    
    @Parameter("The mutation operator")
    protected String mutation;
    
    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "ICST2013";
    
	@Override
	protected void task() {
		// TODO Auto-generated method stub
        Schema schema;
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema, databaseConfiguration.getDbms());
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

	@Override
	protected void validateParameters() {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
        new CreateDefects().run(args);
    }
}
