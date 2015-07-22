/*
 */
package org.schemaanalyst.mutation.pipeline;

import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceChecker;
import org.schemaanalyst.mutation.operator.CCNullifier;
import org.schemaanalyst.mutation.operator.NNCAR;
import org.schemaanalyst.mutation.operator.PKCColumnARE;
import org.schemaanalyst.mutation.operator.UCColumnARE;
import org.schemaanalyst.mutation.quasimutant.PostgresRemover;
import org.schemaanalyst.mutation.quasimutant.SQLiteRemover;
import org.schemaanalyst.mutation.redundancy.EquivalentMutantRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnNotNullRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyUniqueOverlapConstraintRemover;
import org.schemaanalyst.mutation.redundancy.RedundantMutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A {@link MutationPipeline} that implements the pipeline used in the original
 * Mutation 2013 paper, including removers for removing equivalent mutants and
 * Postgres quasi-mutants.
 * </p>
 *
 * @author Chris J. Wright
 */
public class Mutation2013Pipeline extends MutationPipeline<Schema> {
    
    private static final Logger LOGGER = Logger.getLogger(Mutation2013Pipeline.class.getName());

    public Mutation2013Pipeline(Schema schema) {
        addProducer(new CCNullifier(schema));
        addProducer(new PKCColumnARE(schema));
        addProducer(new NNCAR(schema));
        addProducer(new UCColumnARE(schema));
        
        addRemover(new EquivalentMutantRemover<>(new SchemaEquivalenceChecker(), schema));
        addRemover(new RedundantMutantRemover<>(new SchemaEquivalenceChecker()));
    }
    
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
