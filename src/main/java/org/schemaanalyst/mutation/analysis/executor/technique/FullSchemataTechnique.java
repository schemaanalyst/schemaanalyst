package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestSuite;

import java.util.List;

/**
 * <p>
 * The 'Full Schemata' mutation analysis approach.</p>
 *
 * @author Chris J. Wright
 */
public class FullSchemataTechnique extends AbstractSchemataTechnique {

    public FullSchemataTechnique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions, String dataGenerator, String criterion, long randomseed) {
        super(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions, dataGenerator, criterion, randomseed);
    }

    @Override
    public AnalysisResult analyse(TestSuiteResult originalResults) {
        // Get normal results
        AnalysisResult result = new AnalysisResult();

        // Get mutant results with schemata changes
        doSchemataSteps();
        databaseInteractor.executeUpdate(dropStmt);
        databaseInteractor.executeUpdate(createStmt);
        int mutantId = 0;
        for (Mutant<Schema> mutant : mutants) {
            String schemataPrefix = "mutant_" + (mutantId + 1) + "_";
            TestSuiteResult mutantResults = executeTestSuiteSchemata(mutant.getMutatedArtefact(), testSuite, schemataPrefix, originalResults);
            if (!originalResults.equals(mutantResults)) {
                result.addKilled(mutant);
            } else {
                result.addLive(mutant);
            }
            mutantId++;
        }
        databaseInteractor.executeUpdate(dropStmt);
        return result;
    }

}
