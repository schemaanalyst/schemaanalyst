package org.schemaanalyst.faultlocalization;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.util.ExperimentTimer;
import org.schemaanalyst.mutation.equivalence.ChangedConstraintFinder;
import org.schemaanalyst.mutation.equivalence.ChangedTableFinder;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestCaseExecutor;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.Runner;
import parsedcasestudy.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class FaultLocalizationExperiment extends Runner {
    
	static String mutationPipeline = "ProgrammaticDBMSRemovers:CCNullifier,CCInExpressionRHSListExpressionElementR,"
			+ "CCRelationalExpressionOperatorE,FKCColumnPairA,FKCColumnPairR,FKCColumnPairE,PKCColumnA,PKCColumnR,"
			+ "PKCColumnE,UCColumnA,UCColumnR,UCColumnE";
	
    Schema[] schemas = {
    		new BankAccount(),
            new CoffeeOrders(),
            new Flav_R03_1Repaired(),
            new Flights(),
            new GeoMetaDB(),
            new IsoFlav_R2Repaired(),
            new JWhoisServer(),
            new NistDML182(),
            new NistDML183(),
            new NistWeather(),
            new Products(),
            new RiskIt(),
            new StackOverflow(),
            new WordNet()
    };
    
    protected static FileWriter dataWriter;

    @Parameter("The name of the schema to use.")
    protected String schema;

    protected String criterion = "amplifiedConstraintCACWithNullAndUniqueColumnCACCoverage";

    @Parameter("The name of the data generator to use.")
    protected String datagenerator = "avsDefaults";

    @Parameter("The name of the DBMS to use.")
    protected String dbms;

    @Parameter("The class name of the generated test suite (if not specified class name will be 'TestSchema' where schema is the name of the schema under test")
    protected String classname = "";

    @Parameter("The package into which the built test suite is placed")
    protected String packagename = "generatedtest";
    
    @Parameter("If the tables should be dropped first.")
    protected boolean dropfirst=true;
    
    protected TestSuite originalTestSuite;
    
    protected TestSuite tempTestSuite;
    
    protected DBMS dbmsObject;
    
    protected DatabaseInteractor databaseInteractor;
    
    int counter = 0;
    
    int timesNoScore = 0;
    
    List<Constraint> origConstraints;
    
    boolean origConstraint;
    
    @Parameter("The schema to run")
    protected String schemaName;
    
    @Parameter("Name of the file")
    protected String fileName;
    
//    protected TestSuiteGenerator testSuiteGenerator;
    
    @Parameter("Which mutation analysis technique to use.")
    protected String technique = "original";
    
    @Override
    protected void task() {
    	createFileWriter(locationsConfiguration.getResultsDir() + File.separator + schemaName + ".dat");
    	try {
			dataWriter.append("Schema,");
			dataWriter.append("Fault Operator,");
			dataWriter.append("Distance Function,");
			dataWriter.append("Score\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
//        for (Schema schema : schemas) {
        	System.out.println(schemaName);
        	instantiateDBMS();
        	String casestudy = "parsedcasestudy." + schemaName;
            Schema schemaObject = instantiateSchema(casestudy);
//            String filename = "src/"+ packagename + "/Test" + schema.getName() + ".java";
//            File f = new File(filename);
//            if(f.exists()){
//            	System.out.println(filename + " already exists");
//            }else{
            	tempTestSuite = GenerateTestData(schemaObject);
            	originalTestSuite = GenerateTestData(schemaObject);
//            }
            MutationPipeline<Schema> pipeline= instantiatePipeline(schemaObject, mutationPipeline);

            List<Mutant<Schema>> mutatedSchema = pipeline.mutate();
            
            for(int i = 0; i < mutatedSchema.size(); i++){
            	Constraint originalConstraint = null;
            	Table mutatedTable = null;
            	String mutationOperator = mutatedSchema.get(i).getSimpleDescription();
            	if(mutationOperator.equals("CCNullifier") || mutationOperator.equals("CCRelationalExpressionOperatorE") ||
            			mutationOperator.equals("CCInExpressionRHSListExpressionElementR")){
            		
            		originalConstraint = CompareSchema.CompareCheckConstraints(schemaObject, mutatedSchema.get(i).getMutatedArtefact());
            		mutatedTable = ChangedTableFinder.getDifferentTable(schemaObject, mutatedSchema.get(i).getMutatedArtefact());
            		doExpt(mutatedSchema.get(i).getMutatedArtefact(), mutatedSchema.get(i).getDescription(), originalConstraint, mutatedTable);
            	}
            	if(mutationOperator.equals("FKCColumnPairR") || mutationOperator.equals("FKCColumnPairE") ||
            			mutationOperator.equals("FKCColumnPairA")){
            		originalConstraint = CompareSchema.CompareForeignKeyConstraints(schemaObject, mutatedSchema.get(i).getMutatedArtefact());
            		mutatedTable = ChangedTableFinder.getDifferentTable(schemaObject, mutatedSchema.get(i).getMutatedArtefact());
            		doExpt(mutatedSchema.get(i).getMutatedArtefact(), mutatedSchema.get(i).getDescription(), originalConstraint, mutatedTable);
            	}
            	if(mutationOperator.equals("PKCColumnR") || mutationOperator.equals("PKCColumnE") ||
            			mutationOperator.equals("PKCColumnA")){
            		originalConstraint = CompareSchema.ComparePrimaryKeyConstraints(schemaObject, mutatedSchema.get(i).getMutatedArtefact());
            		mutatedTable = ChangedTableFinder.getDifferentTable(schemaObject, mutatedSchema.get(i).getMutatedArtefact());
//            		System.out.println(originalConstraint);
//            		System.out.println(mutatedSchema.get(i).getDescription());
            		
            		doExpt(mutatedSchema.get(i).getMutatedArtefact(), mutatedSchema.get(i).getDescription(), originalConstraint, mutatedTable);
            	}
            	if(mutationOperator.equals("NNCA") || mutationOperator.equals("NNCR")){
//            		do nothing
            	}
            	if(mutationOperator.equals("UCColumnA") || mutationOperator.equals("UCColumnR") ||
            			mutationOperator.equals("UCColumnE")){
            		//should be which constraint was mutated
            		originalConstraint = CompareSchema.CompareUniqueConstraints(schemaObject, mutatedSchema.get(i).getMutatedArtefact());
            		mutatedTable = ChangedTableFinder.getDifferentTable(schemaObject, mutatedSchema.get(i).getMutatedArtefact());
//            		System.out.println(originalConstraint);
            		//
//            		System.out.println(mutatedSchema.get(i).getDescription());
            		doExpt(mutatedSchema.get(i).getMutatedArtefact(), mutatedSchema.get(i).getDescription(), originalConstraint, mutatedTable);
            	}
            	try {
					dataWriter.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            closeStream();
            }
        
//            System.out.println();
//        }
    
//pass it which constraint has the seeded fault
    void doExpt(Schema schema, String mutant, Constraint originalConstraint, Table mutatedTable) {
    	ExperimentTimer timer = new ExperimentTimer();
    	counter++;
    	ArrayList<ResultMatrixRow> matrixRows = new ArrayList<ResultMatrixRow>();
    	String casestudy = "parsedcasestudy." + schema;
    	Schema schemaObject = instantiateSchema(casestudy);
    	MutationPipeline<Schema> pipeline = instantiatePipeline(schemaObject, mutationPipeline);
    	timer.start(ExperimentTimer.TimingPoint.MUTATION_TIME);
    	List<Mutant<Schema>> mutatedSchema = pipeline.mutate();
    	timer.stop(ExperimentTimer.TimingPoint.MUTATION_TIME);    	
    	System.out.println("RUN " + counter + " OUT OF: " + mutatedSchema.size());

    	int totalFailed = 0;
    	int totalPassed = 0;
    	List<TestCase> cases = originalTestSuite.getTestCases();
    	for(int i = 0; i < cases.size(); i++){
    		for(int j = 0; j < cases.get(i).getDBMSResults().size(); j++){
    			if(cases.get(i).getDBMSResults().get(j)){
    				totalPassed++;
    			}else{
    				totalFailed++;
    			}
    		}
    	}
        Constraint c = null;
        //EACH RUN OF FOR loop corresponds to one row in the matrix
		for(int id = 0; id < mutatedSchema.size(); id++){
			int passed = 0;
            int failed = 0;
        	if(mutatedSchema.get(id).getSimpleDescription().equals("CCNullifier") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("CCRelationalExpressionOperatorE")||
        					mutatedSchema.get(id).getSimpleDescription().equals("CCInExpressionRHSListExpressionElementR")){
        		c = ChangedConstraintFinder.getDifferentConstraint(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	if(mutatedSchema.get(id).getSimpleDescription().equals("FKCColumnPairR") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("FKCColumnPairE") ||
        				mutatedSchema.get(id).getSimpleDescription().equals("FKCColumnPairA")){
        		c = ChangedConstraintFinder.getDifferentConstraint(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	if(mutatedSchema.get(id).getSimpleDescription().equals("PKCColumnR") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("PKCColumnE") ||
        					mutatedSchema.get(id).getSimpleDescription().equals("PKCColumnA")){
        		c = ChangedConstraintFinder.getDifferentConstraint(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	if(mutatedSchema.get(id).getSimpleDescription().equals("NNCA") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("NNCR")){
        		c = CompareSchema.CompareNotNullConstraints(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	if(mutatedSchema.get(id).getSimpleDescription().equals("UCColumnA") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("UCColumnR") ||
        				mutatedSchema.get(id).getSimpleDescription().equals("UCColumnE")){
        		c = ChangedConstraintFinder.getDifferentConstraint(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	
        	// Drop existing tables
//            timer.start(ExperimentTimer.TimingPoint.DROPS_TIME);
        	boolean quasiMutant = false;

        	TestCaseExecutor executor = new TestCaseExecutor(
                    mutatedSchema.get(id).getMutatedArtefact(),
                    dbmsObject,
                    new DatabaseConfiguration(),
                    new LocationsConfiguration());
            executor.execute(tempTestSuite);

            ResultMatrixRow r;
//            	timer.start(ExperimentTimer.TimingPoint.INSERTS_TIME);
                 List<TestCase> mutatedCases = tempTestSuite.getTestCases();
                 
                 //if it passed and failed = pass
                 //if it failed and now passes = fail
                 for(int k = 0; k < mutatedCases.size(); k++){
             		for(int j = 0; j < mutatedCases.get(k).getDBMSResults().size(); j++){
             			//if original was true
             			if(cases.get(k).getDBMSResults().get(j)){
             				//if theyre the same
             				if(mutatedCases.get(k).getDBMSResults().get(j).equals(cases.get(k).getDBMSResults().get(j))){
             					
             				}else{
             					passed++;
             				}
             			}else{
             				
             			}
             			if(mutatedCases.get(k).getDBMSResults().get(j).equals(cases.get(k).getDBMSResults().get(j))){
             				
             			}else{
             				failed++;
             			}
             		}
             	}
             	boolean isFault = false;
             	Table changedTable = ChangedTableFinder.getDifferentTable(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
             	if(mutatedSchema.get(id).getDescription().equals(mutant.toString()) &&
             			changedTable.equals(mutatedTable)){
             		isFault = true;
             	}
             	if(c.equals(originalConstraint) && !originalConstraint.toString().equals("Empty Constraint")){
             		
             	}else{
             		r = new ResultMatrixRow(c, mutatedSchema.get(id).getSimpleDescription(), totalFailed, totalPassed, passed, failed, isFault);
             		matrixRows.add(r);
             	}
                
            }
		boolean hasScore = false;
		for(int i = 0; i < matrixRows.size(); i++){
			if(matrixRows.get(i).isFault()){
	    		hasScore = true;       		    		
	    	}
		}
		if(hasScore){
			ResultMatrix matrix = new ResultMatrix(schemaObject, matrixRows);
			ProcessMatrix.Process(schemaObject, matrix);
    		
    			try
        		{
        		    dataWriter.append(schemaObject.getName() + ",");
        		    for(int i = 0; i < matrixRows.size(); i++){
        		    	if(matrixRows.get(i).isFault()){
                		    dataWriter.append(matrixRows.get(i).getMutant() + ",");
                		    dataWriter.append("Ochiai,");
        		    		System.out.println("ochiai score: " + matrixRows.get(i).getScoreOchiai());
        		    		dataWriter.append(matrixRows.get(i).getScoreOchiai() + "\n");        		    		
        		    	}
        		    }
        		    
        		    dataWriter.append(schemaObject.getName() + ",");
        		    for(int i = 0; i < matrixRows.size(); i++){
        		    	if(matrixRows.get(i).isFault()){
                		    dataWriter.append(matrixRows.get(i).getMutant() + ",");
                		    dataWriter.append("Tarantula,");
        		    		System.out.println("tarantula score: " + matrixRows.get(i).getScoreTarantula());
        		    		dataWriter.append(matrixRows.get(i).getScoreTarantula() + "\n");        		    		
        		    	}
        		    }
        		    
        		    dataWriter.append(schemaObject.getName() + ",");
        		    for(int i = 0; i < matrixRows.size(); i++){
        		    	if(matrixRows.get(i).isFault()){
                		    dataWriter.append(matrixRows.get(i).getMutant() + ",");
                		    dataWriter.append("Jaccard,");
        		    		System.out.println("jaccard score: " + matrixRows.get(i).getScoreJaccard());
        		    		dataWriter.append(matrixRows.get(i).getScoreJaccard() + "\n");        		    		
        		    	}
        		    }
        		    
        		    
        		}
        		catch(IOException e)
        		{
        		     e.printStackTrace();
        		} 
//    			closeStream();
    		}
    		
		}
    
    	protected static void createFileWriter(String fileName){
    		try {
    			dataWriter = new FileWriter(fileName);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    
    	protected static void closeStream(){
    		try {
				dataWriter.flush();
				dataWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}    

    private Schema instantiateSchema(String s) {
        try {
            return (Schema) Class.forName(s).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
    
    private MutationPipeline<Schema> instantiatePipeline(Schema schema, String pipe){
    	MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(pipe, schema, databaseConfiguration.getDbms());
            return pipeline;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private void instantiateDBMS(){
    	dbmsObject = DBMSFactory.instantiate(databaseConfiguration.getDbms());
    }
    
    private TestSuite GenerateTestData(Schema s){
        /*
    	Schema schemaObject = s;
    	CoverageCriterion criterionObject = CoverageCriterionFactory.instantiate(criterion);
        DataGenerator dataGeneratorObject = DataGeneratorFactory.instantiate(datagenerator, 0L, 10000, schemaObject);
        // generate the test suite
        TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator(
                schemaObject,
                criterionObject,
                dbmsObject.getValueFactory(),
                dataGeneratorObject);
        originalTestSuite = testSuiteGenerator.generate();
        
        
        // execute each test case to see what the DBMS result is for each row generated (accept / row)
        TestCaseExecutor executor = new TestCaseExecutor(
                schemaObject,
                dbmsObject,
                new DatabaseConfiguration(),
                new LocationsConfiguration());
        executor.execute(originalTestSuite);
        // write JUnit test suite to file
        classname = "Test" + schemaObject.getName(); 
        String javaCode = new TestSuiteJavaWriter(schemaObject, dbmsObject, originalTestSuite)
                .writeTestSuite(packagename, classname);

        File javaFile = new File(locationsConfiguration.getSrcDir()
                + "/" + packagename + "/" + classname + JAVA_FILE_SUFFIX);
        try (PrintWriter fileOut = new PrintWriter(javaFile)) {
            fileOut.println(javaCode);
            return originalTestSuite;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        */
        return null;
    }
    
    @Override
    protected void validateParameters() {
        // nothing to validate
    }

    public static void main(String[] args) {
        new FaultLocalizationExperiment().run(args);        
    }
}
