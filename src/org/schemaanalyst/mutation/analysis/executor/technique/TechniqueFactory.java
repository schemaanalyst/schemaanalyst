
package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.coverage.testgeneration.TestSuite;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.sqlrepresentation.Schema;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Chris J. Wright
 */
public class TechniqueFactory {
    public static Technique instantiate(String name, Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor) {
        Class<TechniqueFactory> c = TechniqueFactory.class;
        Method[] methods = c.getMethods();
        
        for (Method m : methods) {
            if (m.getName().equals(name)) {
                try {
                    return (Technique) m.invoke(null, schema, mutants, testSuite, dbms, databaseInteractor);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("Unknown technique \"" + name + "\"");
    }
    
    public static Technique original (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor) {
        return new OriginalTechnique(schema, mutants, testSuite, dbms, databaseInteractor);
    }
    
    public static Technique fullSchemata (Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor) {
        return new FullSchemataTechnique(schema, mutants, testSuite, dbms, databaseInteractor);
    }
}
