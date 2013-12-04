package org.schemaanalyst.mutation.pipeline;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.mutation.equivalence.*;
import org.schemaanalyst.mutation.operator.*;
import org.schemaanalyst.mutation.quasimutant.*;
import org.schemaanalyst.mutation.redundancy.*;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 *
 * @author Chris J. Wright
 */
public class AllOperatorsWithRemoversPipeline extends MutationPipeline<Schema>{
    
    private static final Logger LOGGER = Logger.getLogger(AllOperatorsWithRemoversPipeline.class.getName());

    public AllOperatorsWithRemoversPipeline(Schema schema) {
        addProducer(new CCNullifier(schema));
        addProducer(new CCInExpressionRHSListExpressionElementR(schema));
        addProducer(new CCRelationalExpressionOperatorE(schema));
        addProducer(new FKCColumnPairA(schema));
        addProducer(new FKCColumnPairR(schema));
        addProducer(new FKCColumnPairE(schema));
        // Implement FKCColumnE ('one sided')?
        addProducer(new PKCColumnA(schema));
        addProducer(new PKCColumnR(schema));
        addProducer(new PKCColumnE(schema));
        addProducer(new NNCA(schema));
        addProducer(new NNCR(schema));
        addProducer(new UCColumnA(schema));
        addProducer(new UCColumnR(schema));
        addProducer(new UCColumnE(schema));

        addRemover(new PrimaryKeyColumnNotNullRemover());
        addRemover(new PrimaryKeyColumnsUniqueRemover());
        addRemover(new MutantEquivalentToOriginalRemover<>(new SchemaEquivalenceChecker(), schema));
        addRemover(new MutantEquivalentToMutantRemover<>(new SchemaEquivalenceChecker()));
    }
    
    public void addDBMSSpecificRemovers(String dbms) {
        switch (dbms) {
            case "Postgres":
                addRemoverToFront(new PostgresRemover());
                break;
            case "SQLite":
                addRemoverToFront(new SQLiteRemover());
                break;
            default:
                LOGGER.log(Level.WARNING, "Unknown DBMS name in pipeline");
        }
    }
    
}
