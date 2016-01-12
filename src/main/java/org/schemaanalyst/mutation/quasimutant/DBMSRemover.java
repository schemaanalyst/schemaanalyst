
package org.schemaanalyst.mutation.quasimutant;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.DataCapturer;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Chris J. Wright
 */
public class DBMSRemover extends MutantRemover<Schema> {

    @Override
    public List<Mutant<Schema>> removeMutants(List<Mutant<Schema>> mutants) {
        
        // Load the relevant database configuration and obtain the interactor
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        LocationsConfiguration locationsConfiguration = new LocationsConfiguration();
        DBMS dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());
        SQLWriter sqlWriter = dbms.getSQLWriter();
        DatabaseInteractor interactor = dbms.getDatabaseInteractor("DBMSRemover", databaseConfiguration, locationsConfiguration);
        
        // Test the mutants
        for (Iterator<Mutant<Schema>> it = mutants.iterator(); it.hasNext();) {
            Mutant<Schema> mutant = it.next();
            Schema schema = mutant.getMutatedArtefact();
            // Test if the CREATE works
            for (String stmt : sqlWriter.writeCreateTableStatements(schema)) {
                Integer result = interactor.executeUpdate(stmt);
                if (result == -1) {
                    it.remove();
                    DataCapturer.capture("removedmutants", "quasimutant", schema + "-" + mutant.getSimpleDescription());
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
