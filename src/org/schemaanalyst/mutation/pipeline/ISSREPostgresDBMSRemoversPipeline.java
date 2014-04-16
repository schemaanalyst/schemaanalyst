
package org.schemaanalyst.mutation.pipeline;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.schemaanalyst.mutation.equivalence.SchemaEquivalenceChecker;
import org.schemaanalyst.mutation.operator.CCInExpressionRHSListExpressionElementR;
import org.schemaanalyst.mutation.operator.CCNullifier;
import org.schemaanalyst.mutation.operator.CCRelationalExpressionOperatorE;
import org.schemaanalyst.mutation.operator.FKCColumnPairA;
import org.schemaanalyst.mutation.operator.FKCColumnPairE;
import org.schemaanalyst.mutation.operator.FKCColumnPairR;
import org.schemaanalyst.mutation.operator.NNCA;
import org.schemaanalyst.mutation.operator.NNCR;
import org.schemaanalyst.mutation.operator.PKCColumnA;
import org.schemaanalyst.mutation.operator.PKCColumnE;
import org.schemaanalyst.mutation.operator.PKCColumnR;
import org.schemaanalyst.mutation.operator.UCColumnA;
import org.schemaanalyst.mutation.operator.UCColumnE;
import org.schemaanalyst.mutation.operator.UCColumnR;
import org.schemaanalyst.mutation.quasimutant.PostgresDBMSRemover;
import org.schemaanalyst.mutation.quasimutant.PostgresRemover;
import org.schemaanalyst.mutation.quasimutant.SQLiteRemover;
import org.schemaanalyst.mutation.redundancy.RedundantMutantRemover;
import org.schemaanalyst.mutation.redundancy.EquivalentMutantRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnNotNullRemover;
import org.schemaanalyst.mutation.redundancy.PrimaryKeyColumnsUniqueRemover;
import org.schemaanalyst.sqlrepresentation.Schema;

/**
 *
 * @author Chris J. Wright
 */
public class ISSREPostgresDBMSRemoversPipeline extends MutationPipeline<Schema> {

    private static final Logger LOGGER = Logger.getLogger(AllOperatorsWithRemoversPipeline.class.getName());

    public ISSREPostgresDBMSRemoversPipeline(Schema schema) {
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

        addRemover(new PrimaryKeyColumnsUniqueRemover());
        addRemover(new EquivalentMutantRemover<>(new SchemaEquivalenceChecker(), schema));
        addRemover(new RedundantMutantRemover<>(new SchemaEquivalenceChecker()));
    }

    public void addDBMSSpecificRemovers(String dbms) {
        switch (dbms) {
            case "Postgres":
                addRemoverToFront(new PostgresDBMSRemover());
                addRemoverToFront(new PrimaryKeyColumnNotNullRemover());
                break;
            case "SQLite":
                addRemoverToFront(new SQLiteRemover());
                break;
            case "HyperSQL":
                addRemover(new PostgresRemover());
                break;
            default:
                LOGGER.log(Level.WARNING, "Unknown DBMS name in pipeline");
        }
    }
}
