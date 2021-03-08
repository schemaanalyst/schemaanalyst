
package org.schemaanalyst.mutation.analysis.executor.alters.technique;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.technique.Technique;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Chris J. Wright
 */
public class AltersTechniqueFactory {
    
    public static Technique instantiate(String name, Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, String dataGenerator, String criterion, long randomseed) {
        Class<AltersTechniqueFactory> c = AltersTechniqueFactory.class;
        Method[] methods = c.getMethods();
        
        for (Method m : methods) {
            if (m.getName().equals(name)) {
                try {
                    return (Technique) m.invoke(null, schema, mutants, testSuite, dbms, databaseInteractor, dataGenerator, criterion, randomseed);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("Unknown technique \"" + name + "\"");
    }
    
    public static Technique alters(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, String dataGenerator, String criterion, long randomseed) {
        return new AltersTechnique(schema, mutants, testSuite, dbms, databaseInteractor, dataGenerator, criterion, randomseed);
    }
    
    public static Technique altersMinimal(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, String dataGenerator, String criterion, long randomseed) {
        return new AltersMinimalTechnique(schema, mutants, testSuite, dbms, databaseInteractor, dataGenerator, criterion, randomseed);
    }
    
}
