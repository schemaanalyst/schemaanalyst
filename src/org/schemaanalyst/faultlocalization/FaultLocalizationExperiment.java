package org.schemaanalyst.faultlocalization;

import static org.schemaanalyst.util.java.JavaUtils.JAVA_FILE_SUFFIX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.technique.AnalysisResult;
import org.schemaanalyst.mutation.analysis.executor.technique.Technique;
import org.schemaanalyst.mutation.analysis.executor.technique.TechniqueFactory;
import org.schemaanalyst.mutation.analysis.result.SQLInsertRecord;
import org.schemaanalyst.mutation.analysis.util.ExperimentTimer;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.testgeneration.*;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.Runner;

import parsedcasestudy.ArtistTerm;
import parsedcasestudy.BankAccount;
import parsedcasestudy.BookTown;
import parsedcasestudy.Cloc;
import parsedcasestudy.CoffeeOrders;
import parsedcasestudy.CustomerOrder;
import parsedcasestudy.DellStore;
import parsedcasestudy.Flav_R03_1Repaired;
import parsedcasestudy.Flights;
import parsedcasestudy.FrenchTowns;
import parsedcasestudy.GeoMetaDB;
import parsedcasestudy.Inventory;
import parsedcasestudy.Iso3166;
import parsedcasestudy.IsoFlav_R2Repaired;
import parsedcasestudy.JWhoisServer;
import parsedcasestudy.NistDML181;
import parsedcasestudy.NistDML182;
import parsedcasestudy.NistDML183;
import parsedcasestudy.NistWeather;
import parsedcasestudy.NistXTS748;
import parsedcasestudy.NistXTS749;
import parsedcasestudy.Person;
import parsedcasestudy.Products;
import parsedcasestudy.RiskIt;
import parsedcasestudy.StackOverflow;
import parsedcasestudy.StudentResidence;
import parsedcasestudy.UnixUsage;
import parsedcasestudy.Usda;
import parsedcasestudy.WordNet;


public class FaultLocalizationExperiment extends Runner {
    
	static String mutationPipeline = "ProgrammaticDBMSRemovers:CCNullifier,CCInExpressionRHSListExpressionElementR,"
			+ "CCRelationalExpressionOperatorE,FKCColumnPairA,FKCColumnPairR,FKCColumnPairE,PKCColumnA,PKCColumnR,"
			+ "PKCColumnE,NNCA,NNCR,UCColumnA,UCColumnR,UCColumnE";
	
	static String smallCC = "ProgrammaticDBMSRemovers:FKCColumnPairA,FKCColumnPairR,FKCColumnPairE";
	
	static String noCC = "ProgrammaticDBMSRemovers:FKCColumnPairA,FKCColumnPairR,FKCColumnPairE,"
			+ "PKCColumnA,PKCColumnR,PKCColumnE,NNCA,NNCR,UCColumnA,UCColumnR,UCColumnE";
	
	static String smallFK = "ProgrammaticDBMSRemovers:CCNullifier,CCInExpressionRHSListExpressionElementR";
	
	static String noFK = "ProgrammaticDBMSRemovers:CCNullifier,CCInExpressionRHSListExpressionElementR,CCRelationalExpressionOperatorE,"
			+ "PKCColumnA,PKCColumnR,PKCColumnE,NNCA,NNCR,UCColumnA,UCColumnR,UCColumnE";
	
	static String smallPK = "ProgrammaticDBMSRemovers:CCNullifier";
	
	static String noPK = "ProgrammaticDBMSRemovers:CCNullifier,CCInExpressionRHSListExpressionElementR,"
			+ "CCRelationalExpressionOperatorE,FKCColumnPairA,FKCColumnPairR,FKCColumnPairE,"
			+ "NNCA,NNCR,UCColumnA,UCColumnR,UCColumnE";
	
	static String smallNN = "ProgrammaticDBMSRemovers:CCNullifier";
	
	static String noNN = "ProgrammaticDBMSRemovers:CCNullifier,CCInExpressionRHSListExpressionElementR,"
			+ "CCRelationalExpressionOperatorE,FKCColumnPairA,FKCColumnPairR,FKCColumnPairE,PKCColumnA,"
			+ "PKCColumnR,PKCColumnE,UCColumnA,UCColumnR,UCColumnE";
	
	static String smallUC = "ProgrammaticDBMSRemovers:CCNullifier";
	
	static String noUC = "ProgrammaticDBMSRemovers:CCNullifier,CCInExpressionRHSListExpressionElementR,CCRelationalExpressionOperatorE,"
			+ "FKCColumnPairA,FKCColumnPairR,FKCColumnPairE,PKCColumnA,PKCColumnR,PKCColumnE";

	
    Schema[] schemas = {
            new ArtistTerm(),
    		new BankAccount(),
            new BookTown(),
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
//	Schema[] schemas = {
//			new ArtistTerm()
//	};

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
    
//    protected TestSuiteGenerator testSuiteGenerator;
    
    @Parameter("Which mutation analysis technique to use.")
    protected String technique = "original";
    
    @Override
    protected void task() {
        for (Schema schema : schemas) {
        	counter = 0;
        	System.out.println("SCHEMA:" + schema.getName());
        	instantiateDBMS();
        	String casestudy = "parsedcasestudy." + schema.getName();
        	//CreateDefectiveSchemas.createDefects(casestudy, mutationPipeline, databaseConfiguration.getDbms());
            Schema schemaObject = instantiateSchema(casestudy);
            String filename = "src/"+ packagename + "/Test" + schema.getName() + ".java";
            File f = new File(filename);
            if(f.exists()){
            	System.out.println(filename + " already exists");
            }else{
            	tempTestSuite = GenerateTestData(schemaObject);
            	originalTestSuite = GenerateTestData(schemaObject);
            }
            MutationPipeline<Schema> pipeline= instantiatePipeline(schema, mutationPipeline);
            List<Mutant<Schema>> mutatedSchema = pipeline.mutate();
//            System.out.println("MUTATEDSIZE: " + mutatedSchema.size());
            for(int i = 0; i < mutatedSchema.size(); i++){
            	String mutationOperator = mutatedSchema.get(i).getSimpleDescription();
            	if(mutationOperator.equals("CCNullifier") || mutationOperator.equals("CCRelationalExpressionOperatorE") ||
            			mutationOperator.equals("CCInExpressionRHSListExpressionElementR")){
//            		System.out.println("CC");
            		doExpt(mutatedSchema.get(i).getMutatedArtefact(), noCC);
            	}
            	if(mutationOperator.equals("FKCColumnPairR") || mutationOperator.equals("FKCColumnPairE") ||
            			mutationOperator.equals("FKCColumnPairA")){
//            		System.out.println("FK");
            		doExpt(mutatedSchema.get(i).getMutatedArtefact(), noFK);
            	}
            	if(mutationOperator.equals("PKCColumnR") || mutationOperator.equals("PKCColumnE") ||
            			mutationOperator.equals("PKCColumnA")){
//            		System.out.println("PK");
            		doExpt(mutatedSchema.get(i).getMutatedArtefact(), noPK);
            	}
            	if(mutationOperator.equals("NNCA") || mutationOperator.equals("NNCR")){
//            		System.out.println("NN");
            		doExpt(mutatedSchema.get(i).getMutatedArtefact(), noNN);
            	}
            	if(mutationOperator.equals("UCColumnA") || mutationOperator.equals("UCColumnR") ||
            			mutationOperator.equals("UCColumnE")){
//            		System.out.println("UC");
            		doExpt(mutatedSchema.get(i).getMutatedArtefact(), noUC);
            	}
            }
//            System.out.println("matrixrows " + matrixRows.size());
//            ResultMatrix matrix = new ResultMatrix(schema, matrixRows);
//            System.out.println(matrix.size());
//            for(int i= 0; i < matrix.size(); i++){
//            	System.out.println(matrix.getRow(i).getDBConstraint());
//            }
//            ProcessMatrix.Process(schema, matrix);
            

//    		System.out.println("OCHIAI:");
//    		for(int i = 0; i < ochiai.size(); i++){
////    			String oc = ochiai.getRow(i).getDBConstraint().getName();
////    			System.out.println("oc : " + oc);
//    			double score = ochiai.getRow(i).getOchiaiScore();
//    			System.out.println("score : " + score);
//    			System.out.println(ochiai.getRow(i).getDBConstraint() + ", Mutant " + ochiai.getRow(i).getMutant() + " score: " +score );
//    		}
//    		System.out.println();
//    		System.out.println();
//    		System.out.println();
//    		System.out.println("TARANTULA");
//    		for(int i = 0; i < ochiai.size(); i++){
////    			String oc = tarantula.getRow(i).getDBConstraint().getName();
//    			double score = ochiai.getRow(i).getTarantulaScore();
//    			System.out.println(tarantula.getRow(i).getDBConstraint() + ", Mutant " + tarantula.getRow(i).getMutant() + " score: " +score );
//    		}
//    		System.out.println();
//    		System.out.println();
//    		System.out.println();
//    		System.out.println("JACCARD");
//    		for(int i = 0; i < ochiai.size(); i++){
////    			String oc = jaccard.getRow(i).getDBConstraint().getName();
//    			double score = jaccard.getRow(i).getJaccardScore();
//    			System.out.println(jaccard.getRow(i).getDBConstraint() + ", Mutant " + jaccard.getRow(i).getMutant() + " score: " +score );
//    		}
            }
        
            System.out.println();
        }
    

    void doExpt(Schema schema, String pipe) {
    	ExperimentTimer timer = new ExperimentTimer();
    	counter++;
    	ArrayList<ResultMatrixRow> matrixRows = new ArrayList<ResultMatrixRow>();
    	System.out.println("MATRIX ROWS SIZE: " + matrixRows.size());
    	String casestudy = "parsedcasestudy." + schema;
    	Schema schemaObject = instantiateSchema(casestudy);
    	MutationPipeline<Schema> pipeline= instantiatePipeline(schemaObject, pipe);
    	timer.start(ExperimentTimer.TimingPoint.MUTATION_TIME);
    	List<Mutant<Schema>> mutatedSchema = pipeline.mutate();
    	timer.stop(ExperimentTimer.TimingPoint.MUTATION_TIME);
    	
    	System.out.println("RUN " + counter + " OUT OF: " + mutatedSchema.size());
//    	int totalFailed = testSuiteGenerator.getFailedTestCases().size();
//    	int totalPassed = t.getNumTestCases() - totalFailed;
    	int totalFailed = 0;
    	int totalPassed = 0;
    	

    	
    	List<TestCase> cases = originalTestSuite.getTestCases();
    	for(int i = 0; i < cases.size(); i++){
//    		Gets whole test case
//    		System.out.println("testcase " + cases.get(i));
    		//gets num inserts
//    		System.out.println("getNumInserts: " + cases.get(i).getNumInserts());
    		
    		//gets result
//    		System.out.println("getDBMSResults" + cases.get(i).getDBMSResults());
    		//gets total passes and fails
    		for(int j = 0; j < cases.get(i).getDBMSResults().size(); j++){
    			if(cases.get(i).getDBMSResults().get(j)){
    				totalPassed++;
    			}else{
    				totalFailed++;
    			}
    		}
    	}
//    	System.out.println("totalpassed: " + totalPassed);
//		System.out.println("totalfailed: " + totalFailed);
//    	System.out.println("mut size " + mutatedSchema.size());
        Constraint c = null;
		for(int id = 0; id < mutatedSchema.size(); id++){
			
			int passed = 0;
            int failed = 0;
        	if(mutatedSchema.get(id).getSimpleDescription().equals("CCNullifier") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("CCRelationalExpressionOperatorE")||
        					mutatedSchema.get(id).getSimpleDescription().equals("CCInExpressionRHSListExpressionElementR")){
        		c = CompareSchema.CompareCheckConstraints(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	if(mutatedSchema.get(id).getSimpleDescription().equals("FKCColumnPairR") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("FKCColumnPairE") ||
        				mutatedSchema.get(id).getSimpleDescription().equals("FKCColumnPairA")){
        		c = CompareSchema.CompareForeignKeyConstraints(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	if(mutatedSchema.get(id).getSimpleDescription().equals("PKCColumnR") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("PKCColumnE") ||
        					mutatedSchema.get(id).getSimpleDescription().equals("PKCColumnA")){
        		c = CompareSchema.ComparePrimaryKeyConstraints(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	if(mutatedSchema.get(id).getSimpleDescription().equals("NNCA") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("NNCR")){
        		c = CompareSchema.CompareNotNullConstraints(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	if(mutatedSchema.get(id).getSimpleDescription().equals("UCColumnA") ||
        			mutatedSchema.get(id).getSimpleDescription().equals("UCColumnR") ||
        				mutatedSchema.get(id).getSimpleDescription().equals("UCColumnE")){
        		c = CompareSchema.CompareUniqueConstraints(schemaObject, mutatedSchema.get(id).getMutatedArtefact());
        	}
        	
//        	resultOchiai.addValue("Mutant Operator", mutatedSchema.get(id).getSimpleDescription());
//        	resultJaccard.addValue("Mutant Operator", mutatedSchema.get(id).getSimpleDescription());
//        	resultTarantula.addValue("Mutant Operator", mutatedSchema.get(id).getSimpleDescription());

        	
        	// Drop existing tables
//            timer.start(ExperimentTimer.TimingPoint.DROPS_TIME);
        	boolean quasiMutant = false;
            
            //insert the test data
//            Technique mutTechnique = instantiateTechnique(schemaObject, mutatedSchema, t, dbmsObject, databaseInteractor);
//            AnalysisResult analysisResult = mutTechnique.analyse();
        	
            timer.start(ExperimentTimer.TimingPoint.INSERTS_TIME);
            TestCaseExecutor executor = new TestCaseExecutor(
                    mutatedSchema.get(id).getMutatedArtefact(),
                    dbmsObject,
                    new DatabaseConfiguration(),
                    new LocationsConfiguration());
            executor.execute(tempTestSuite);
            timer.stop(ExperimentTimer.TimingPoint.INSERTS_TIME);
            
        	List<TestCase> mutatedCases = tempTestSuite.getTestCases();
        	for(int i = 0; i < mutatedCases.size(); i++){
        		for(int j = 0; j < mutatedCases.get(i).getDBMSResults().size(); j++){
        			if(mutatedCases.get(i).getDBMSResults().get(j).equals(cases.get(i).getDBMSResults().get(j))){
        				passed++;
        			}else{
        				failed++;
        			}
        		}
        	}
            
//            System.out.println("Passed: " + passed);
//            System.out.println("Failed: " + failed);
            String mutantDescription = mutatedSchema.get(id).getDescription();
//            System.out.println("Mutant description " + mutantDescription);
            ResultMatrixRow r = new ResultMatrixRow(c, mutantDescription, totalFailed, totalPassed, passed, failed, false);
            matrixRows.add(r);
//            System.out.println("size " + matrixRows.size());
//            System.out.println("**************************END*************");
		}
        ResultMatrix matrix = new ResultMatrix(schema, matrixRows);
//      System.out.println(matrix.size());
//      for(int i= 0; i < matrix.size(); i++){
//      	System.out.println(matrix.getRow(i).getDBConstraint());
//      }
        ProcessMatrix.Process(schema, matrix);

        ResultMatrix ochiai = ProcessMatrix.OchiaiRanked;
		ResultMatrix tarantula = ProcessMatrix.TarantulaRanked;
		ResultMatrix jaccard = ProcessMatrix.JaccardRanked;
		System.out.println("Ochiai");
		for(int i = 0; i < ochiai.size(); i++){
			System.out.println("Constraint " + ochiai.getRow(i).getDBConstraint());
			System.out.println("Score " + ochiai.getRow(i).getOchiaiScore());
		}
		System.out.println("Tarantula");
		for(int i = 0; i < tarantula.size(); i++){
			System.out.println("Constraint " + tarantula.getRow(i).getDBConstraint());
			System.out.println("Score " + tarantula.getRow(i).getTarantulaScore());
		}
		System.out.println("Jaccard");
		for(int i = 0; i < jaccard.size(); i++){
			System.out.println("Constraint " + jaccard.getRow(i).getDBConstraint());
			System.out.println("Score " + jaccard.getRow(i).getJaccardScore());
		}
    	CSVResult resultOchiai = new CSVResult();
        resultOchiai.addValue("schema", schema.getName());
        resultOchiai.addValue("matrix", "Ochiai");
        for(int i = 0; i < matrixRows.size(); i++){
        	resultOchiai.addValue("pipeline", pipe);
        	resultOchiai.addValue(i + " original constraint", ochiai.getRow(i).getDBConstraint());
        	resultOchiai.addValue(i + " mutant" , ochiai.getRow(i).getMutant());
        	resultOchiai.addValue(i + " score", ochiai.getRow(i).getOchiaiScore());
        }
        
        new CSVFileWriter("src/resultFiles/" + schema.getName() + "Ochiai" + counter + ".dat").write(resultOchiai);
        
        CSVResult resultTarantula = new CSVResult();
        resultTarantula.addValue("schema", schema.getName());
        resultTarantula.addValue("matrix", "tarantula");
        for(int i = 0; i < matrixRows.size(); i++){
        	resultTarantula.addValue("pipeline", pipe);
        	resultTarantula.addValue(i + " original constraint", tarantula.getRow(i).getDBConstraint());
        	resultTarantula.addValue(i + " mutant" , tarantula.getRow(i).getMutant());
        	resultTarantula.addValue(i + " score", tarantula.getRow(i).getJaccardScore());
        }
        new CSVFileWriter("src/resultFiles/" + schema.getName() + "Tarantula" + counter + ".dat").write(resultTarantula);

        CSVResult resultJaccard = new CSVResult();
        resultJaccard.addValue("schema", schema.getName());
        resultJaccard.addValue("matrix", "jaccard");
        for(int i = 0; i < matrixRows.size(); i++){
        	resultJaccard.addValue("pipeline", pipe);
        	resultJaccard.addValue(i + " original constraint", jaccard.getRow(i).getDBConstraint());
        	resultJaccard.addValue(i + " mutant" , jaccard.getRow(i).getMutant());
        	resultJaccard.addValue(i + " score", jaccard.getRow(i).getJaccardScore());
        }
        
        new CSVFileWriter("src/resultFiles/" + schema.getName() + "Jaccard" + counter + ".dat").write(resultJaccard);
		
        
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
    
    private Technique instantiateTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor) {
        return TechniqueFactory.instantiate(technique, schema, mutants, testSuite, dbms, databaseInteractor);
    }
    
    private TestSuite GenerateTestData(Schema s){
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
    }
    
    @Override
    protected void validateParameters() {
        // nothing to validate
    }

    public static void main(String[] args) {
        new FaultLocalizationExperiment().run(args);
    }
}
