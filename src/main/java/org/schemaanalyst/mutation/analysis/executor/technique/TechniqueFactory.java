
package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Chris J. Wright
 */
public class TechniqueFactory {
    public static Technique instantiate(String name, Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        Class<TechniqueFactory> c = TechniqueFactory.class;
        Method[] methods = c.getMethods();
        
        for (Method m : methods) {
            if (m.getName().equals(name)) {
                try {
                    return (Technique) m.invoke(null, schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("Unknown technique \"" + name + "\"");
    }
    
    public static Technique original (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new OriginalTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique fullSchemata (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new FullSchemataTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique minimalSchemata (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new MinimalSchemataTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique minimalSchemata2 (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new MinimalSchemata2Technique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique upFrontSchemata (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new UpFrontSchemataTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique justInTimeSchemata (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new JustInTimeSchemataTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique parallelMinimalSchemata (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new ParallelMinimalSchemataTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique partialParallelMinimalSchemata (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new PartialParallelMinimalSchemataTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique minimalMinimalSchemata (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new MinimalMinimalSchemataTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique minimalMinimalSchemata2 (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new MinimalMinimalSchemata2Technique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique mutantTiming (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new MutantTimingTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique checks (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new ChecksTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
    
    public static Technique dummy (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, boolean useTransactions) {
        return new DummyTechnique(schema, mutants, testSuite, dbms, databaseInteractor, useTransactions);
    }
}
