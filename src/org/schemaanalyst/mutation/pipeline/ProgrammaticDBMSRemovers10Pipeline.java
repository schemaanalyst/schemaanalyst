/*
 */
package org.schemaanalyst.mutation.pipeline;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.schemaanalyst.mutation.MutantProducer;
import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceChecker;
import org.schemaanalyst.mutation.quasimutant.PostgresRemover;
import org.schemaanalyst.mutation.quasimutant.SQLiteRemover;
import org.schemaanalyst.mutation.reduction.PercentageSamplingRemover;
import org.schemaanalyst.mutation.redundancy.EquivalentMutantRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnNotNullRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyUniqueOverlapConstraintRemover;
import org.schemaanalyst.mutation.redundancy.RedundantMutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A {@link MutationPipeline} that can be built programmatically.
 * </p>
 *
 * @author Chris J. Wright
 */
public class ProgrammaticDBMSRemovers10Pipeline extends MutationPipeline<Schema> {
    
    private static final Logger LOGGER = Logger.getLogger(ProgrammaticDBMSRemovers10Pipeline.class.getName());
    private final Schema schema;
    
    public ProgrammaticDBMSRemovers10Pipeline(Schema schema, String producers) {
        this.schema = schema;
        addProducers(producers);
        addRemover(new EquivalentMutantRemover<>(new SchemaEquivalenceChecker(), schema));
        addRemover(new RedundantMutantRemover<>(new SchemaEquivalenceChecker()));
        addRemover(new PercentageSamplingRemover<Schema>(0.1));
    }
    
    private void addProducers(String producers) {
        String[] splitProducers = producers.split(",");
        for (String producer : splitProducers) {
            try {
                MutantProducer<Schema> producerInstance = instantiateProducer(producer);
                addProducer(producerInstance);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                LOGGER.log(Level.SEVERE, "Failure to instantiate producer: '"+producer+"'", ex);
            }
        }
    }
    
    private MutantProducer<Schema> instantiateProducer(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String className;
        if (name.contains(".")) {
            className = name;
        } else {
            className = "org.schemaanalyst.mutation.operator." + name;
        }
        Class<MutantProducer<Schema>> producerClass = (Class<MutantProducer<Schema>>)Class.forName(className);
        Constructor<MutantProducer<Schema>> constructor = ConstructorUtils.getMatchingAccessibleConstructor(producerClass, schema.getClass());
        MutantProducer<Schema> instance = constructor.newInstance(schema);
        return instance;
    };
    
    public void addDBMSSpecificRemovers(String dbms) {
        switch (dbms) {
            case "Postgres":
                addRemoverToFront(new PostgresRemover());
                addRemoverToFront(new PrimaryKeyUniqueOverlapConstraintRemover());
                addRemoverToFront(new PrimaryKeyColumnNotNullRemover());
                break;
            case "SQLite":
                addRemoverToFront(new SQLiteRemover());
                break;
            default:
                LOGGER.log(Level.WARNING, "Unknown DBMS name in pipeline");
        }
    }
}
