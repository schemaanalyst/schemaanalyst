
package org.schemaanalyst.mutation.analysis.executor.technique;

import java.util.List;
import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 * <p>Abstract parent class of techniques performing mutation analysis.</p>
 * 
 * @author Chris J. Wright
 */
public abstract class Technique {
    
    protected Schema schema;
    protected List<Mutant<Schema>> mutants;
    protected TestSuite testSuite;
    protected DBMS dbms;
    protected DatabaseInteractor databaseInteractor;

    public Technique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor) {
        this.schema = schema;
        this.mutants = mutants;
        this.testSuite = testSuite;
        this.dbms = dbms;
        this.databaseInteractor = databaseInteractor;
    }
    
    public abstract AnalysisResult analyse();
    
}
