package org.schemaanalyst.mutation.pipeline;

import org.schemaanalyst.mutation.operator.*;
import org.schemaanalyst.mutation.quasimutant.HyperSQLRemover;
import org.schemaanalyst.mutation.quasimutant.PostgresRemover;
import org.schemaanalyst.sqlrepresentation.Schema;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A mutation pipeline with all operators except FKA. This has the following
 * properties:
 * <p><ul>
 * <li> Includes all mutation operators except FKA
 * <li> Stillborn mutants are removed
 * <li> Impaired mutants are retained
 * </ul><p>
 * 
 * @author Chris J. Wright
 */
public class AllOperatorsNoFKAWithImpairedPipeline extends MutationPipeline<Schema> {

    private static final Logger LOGGER = Logger.getLogger(AllOperatorsNoFKAWithImpairedPipeline.class.getName());
    private final Schema schema;

    public AllOperatorsNoFKAWithImpairedPipeline(Schema schema) {
        this.schema = schema;
        
        addProducer(new CCNullifier(schema));
        addProducer(new CCInExpressionRHSListExpressionElementR(schema));
        addProducer(new CCRelationalExpressionOperatorE(schema));
        addProducer(new FKCColumnPairR(schema));
        addProducer(new FKCColumnPairE(schema));
        addProducer(new PKCColumnA(schema));
        addProducer(new PKCColumnR(schema));
        addProducer(new PKCColumnE(schema));
        addProducer(new NNCA(schema));
        addProducer(new NNCR(schema));
        addProducer(new UCColumnA(schema));
        addProducer(new UCColumnR(schema));
        addProducer(new UCColumnE(schema));
    }

    public void addDBMSSpecificRemovers(String dbms) {
        switch (dbms) {
            case "Postgres":
                addRemoverToFront(new PostgresRemover());
                break;
            case "SQLite":
                break;
            case "HyperSQL":
                addRemoverToFront(new HyperSQLRemover());
                break;
            default:
                LOGGER.log(Level.WARNING, "Unknown DBMS name in pipeline");
        }
    }

}
