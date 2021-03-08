package org.schemaanalyst.unittest.reduction;

import static org.junit.Assert.*;
import static org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator.IDType.TABLE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.reduction.ReductionFactory;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.testgeneration.ReduecTestCase;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementID;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.OrPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;

public class TestReductionTechniques {

	/*
	 * 		r1	r2	r3	r4	r5	r6
	 * t1	X	X	X
	 * t2	X			X
	 * t3		X			X
	 * t4			X			X
	 * t5					X
	 * 
	 * r1 = C1 NN
	 * r2 = C2 NN
	 * r3 = C3 NN
	 * r4 = C4 NN 
	 * r5 = C5 NN
	 * r6 = C6 NN
	 */
	
	DBMS dbmsObject = DBMSFactory.instantiate("SQLite");
	ValueFactory valueFactory = dbmsObject.getValueFactory();
    
    TestSuite testSuite = new TestSuite();
    TestRequirements alltheTRs = new TestRequirements();
    
    public class TestSchema extends Schema {

        public TestSchema() {
            super("TestSchema");

            Table table = this.createTable("table");
            table.createColumn("column1", new IntDataType());
            table.createColumn("column2", new IntDataType());
            table.createColumn("column3", new IntDataType());
            table.createColumn("column4", new IntDataType());
            table.createColumn("column5", new IntDataType());
            table.createColumn("column6", new IntDataType());
            
    		this.createNotNullConstraint(table, table.getColumn("column1"));
    		this.createNotNullConstraint(table, table.getColumn("column2"));
    		this.createNotNullConstraint(table, table.getColumn("column3"));
    		this.createNotNullConstraint(table, table.getColumn("column4"));
    		this.createNotNullConstraint(table, table.getColumn("column5"));
    		this.createNotNullConstraint(table, table.getColumn("column6"));


        }
    }

	TestSchema testSchema = new TestSchema();
	DataGenerator dataGenerator = DataGeneratorFactory.instantiate("avsDefaults", 1L, 100000, testSchema);
	
    public void generateTestSuite() {
		// N + NN
        NullPredicate c1NN = new NullPredicate(testSchema.getTable("table"), testSchema.getTable("table").getColumn("column1"), true);
        NullPredicate c2NN = new NullPredicate(testSchema.getTable("table"), testSchema.getTable("table").getColumn("column2"), true);
        NullPredicate c3NN = new NullPredicate(testSchema.getTable("table"), testSchema.getTable("table").getColumn("column3"), true);
        NullPredicate c4NN = new NullPredicate(testSchema.getTable("table"), testSchema.getTable("table").getColumn("column4"), true);
        NullPredicate c5NN = new NullPredicate(testSchema.getTable("table"), testSchema.getTable("table").getColumn("column5"), true);
        NullPredicate c6NN = new NullPredicate(testSchema.getTable("table"), testSchema.getTable("table").getColumn("column6"), true);
        
        // Test cases
        // t6 is only c6NN
        //OrPredicate t1 = new OrPredicate();
        AndPredicate t1 = new AndPredicate();
        t1.addPredicate(c1NN);
        t1.addPredicate(c2NN);
        t1.addPredicate(c3NN);
        
        AndPredicate t2 = new AndPredicate();
        t2.addPredicate(c1NN);
        t2.addPredicate(c4NN);
        
        AndPredicate t3 = new AndPredicate();
        t3.addPredicate(c2NN);
        t3.addPredicate(c5NN);
        
        AndPredicate t4 = new AndPredicate();
        t4.addPredicate(c3NN);
        t4.addPredicate(c6NN);
        
        TestRequirementIDGenerator testRequirementIDGenerator = new TestRequirementIDGenerator(TABLE);
        testRequirementIDGenerator.reset(TABLE, "A");
                
        // Real Test Requirements
        TestRequirementDescriptor td1 = new TestRequirementDescriptor(testRequirementIDGenerator.nextID(), "T1");
        TestRequirement tr1 = new TestRequirement(td1, t1, false, false);
        alltheTRs.addTestRequirement(tr1);
        
        TestRequirementDescriptor td2 = new TestRequirementDescriptor(testRequirementIDGenerator.nextID(), "T2");
        TestRequirement tr2 = new TestRequirement(td2, t2, false, false);
        alltheTRs.addTestRequirement(tr2);

        TestRequirementDescriptor td3 = new TestRequirementDescriptor(testRequirementIDGenerator.nextID(), "T3");
        TestRequirement tr3 = new TestRequirement(td3, t3, false, false);
        alltheTRs.addTestRequirement(tr3);

        TestRequirementDescriptor td4 = new TestRequirementDescriptor(testRequirementIDGenerator.nextID(), "T4");
        TestRequirement tr4 = new TestRequirement(td4, t4, false, false);
        alltheTRs.addTestRequirement(tr4);

        TestRequirementDescriptor td5 = new TestRequirementDescriptor(testRequirementIDGenerator.nextID(), "T5");
        TestRequirement tr5 = new TestRequirement(td5, c5NN, false, false);
        alltheTRs.addTestRequirement(tr5);
        
        alltheTRs.reduce();

        // Generate Test Data
        
        for (TestRequirement testRequirement : alltheTRs.getTestRequirements()) {
			Predicate predicate = testRequirement.getPredicate();


			Data state = new Data();
			Data data = new Data();

			if (predicate != null) {
				data.addRow(testSchema.getTable("table"), valueFactory);
				predicate = predicate.reduce();

				DataGenerationReport dataGenerationReport = dataGenerator.generateData(data, state, predicate);
				if (dataGenerationReport.isSuccess()) {
					TestCase testCase = new TestCase(testRequirement, data, state);
					testSuite.addTestCase(testCase);					
				}
			}
        }
    }
    
	@Test
	public void testAddtionalGreedy() {
		this.generateTestSuite();
		ReductionFactory reduce = new ReductionFactory();
		List<TestRequirement> failed = new ArrayList<>();
		TestSuite reducedTestSuite = reduce.reduceTestSuite(testSuite, alltheTRs, testSchema, failed, 5, 1L, "additionalGreedy");	

		assertEquals(4, reducedTestSuite.getTestCases().size());
	}
	
	@Test
	public void testHGS() {
		this.generateTestSuite();
		ReductionFactory reduce = new ReductionFactory();
		List<TestRequirement> failed = new ArrayList<>();
		TestSuite reducedTestSuite = reduce.reduceTestSuite(testSuite, alltheTRs, testSchema, failed, 5, 1L, "HGS");
		// note that the AND predicate does not allow the 3 test case reduction (need to see an alternative IC to test with it)
		assertEquals(4, reducedTestSuite.getTestCases().size());
	}

}
