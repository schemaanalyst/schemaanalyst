
package org.schemaanalyst.mutation.quasimutant;

import java.util.Iterator;
import java.util.List;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 *
 * @author Chris J. Wright
 */
public class PostgresDBMSRemover extends MutantRemover<Schema> {

    @Override
    public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {
        
        DBMS dbms = DBMSFactory.instantiate("Postgres");
        SQLWriter sqlWriter = dbms.getSQLWriter();
        DatabaseInteractor interactor = dbms.getDatabaseInteractor("Postgres", new DatabaseConfiguration(), new LocationsConfiguration());
        
        for (Iterator<Mutant<Schema>> it = mutants.iterator(); it.hasNext();) {
            Mutant<Schema> mutant = it.next();
            Schema schema = mutant.getMutatedArtefact();
            // Test if the CREATE works
            for (String stmt : sqlWriter.writeCreateTableStatements(schema)) {
                Integer result = interactor.executeUpdate(stmt);
                if (result == -1) {
                    it.remove();
                    break;
                }
            }
            // Clean up the database afterwards
            for (String stmt : sqlWriter.writeDropTableStatements(schema, true)) {
                interactor.executeUpdate(stmt);
            }
        }
        
        return mutants;
    }
    
}
