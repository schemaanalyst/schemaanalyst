/*
 */
package org.schemaanalyst.mutation.analysis.util;

import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;
import parsedcasestudy.*;

import java.util.ArrayList;

/**
 * <p>
 *
 * </p>
 *
 * @author Chris J. Wright
 */
@RequiredParameters("technique")
public class DatabaseInteractorTimer extends Runner {

    @Parameter(value = "Database insertion technique (basic,schemata,batch,transaction)")
    protected String technique;
    @Parameter(value = "Scaling factor")
    protected int scalingFactor = 1;

    @Override
    protected void task() {
        DBMS dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());
        SQLWriter sqlWriter = dbms.getSQLWriter();
        DatabaseInteractor databaseInteractor = dbms.getDatabaseInteractor("test", databaseConfiguration, locationsConfiguration);
        
        Schema[] schemas = {new Cloc(), new NistDML181(), new NistDML183(), new RiskIt(), new UnixUsage(), new BookTown(), new CoffeeOrders(), new CustomerOrder(), new DellStore(), new Employee()};
        Schema[] scaledSchemas = new Schema[schemas.length * scalingFactor];
        for (int i = 0; i < schemas.length*scalingFactor; i++) {
            Schema duplicate = schemas[i%schemas.length].duplicate();
            duplicate.setName(duplicate.getName()+"_"+i);
            scaledSchemas[i] = duplicate;
        }
        StopWatch watch = new StopWatch();
        watch.start();
        switch (technique) {
            case "basic":
                basic(scaledSchemas, databaseInteractor, sqlWriter);
                break;
            case "schemata":
                schemata(scaledSchemas, databaseInteractor, sqlWriter);
                break;
            case "batch":
                batch(scaledSchemas, databaseInteractor, sqlWriter);
                break;
            case "transaction":
                transaction(scaledSchemas, databaseInteractor, sqlWriter);
                break;
        }
        watch.stop();
        CSVResult result = new CSVResult();
        result.addValue("technique", technique);
        result.addValue("time", watch.getTime());
        result.addValue("scaling", scalingFactor);
        CSVFileWriter writer = new CSVFileWriter("interactorresult.dat",",");
        writer.write(result);
    }
    
    private void basic(Schema[] schemas, DatabaseInteractor databaseInteractor, SQLWriter sqlWriter) {
        for (Schema schema : schemas) {
            for (String create : sqlWriter.writeCreateTableStatements(schema)) {
                databaseInteractor.executeUpdate(create);
            }
        }
        for (Schema schema : schemas) {
            for (String drop : sqlWriter.writeDropTableStatements(schema)) {
                databaseInteractor.executeUpdate(drop);
            }
        }
    }
    
    private void schemata(Schema[] schemas, DatabaseInteractor databaseInteractor, SQLWriter sqlWriter) {
        StringBuilder dropBuilder = new StringBuilder();
        StringBuilder createBuilder = new StringBuilder();
        for (Schema schema : schemas) {
            for (String create : sqlWriter.writeCreateTableStatements(schema)) {
                createBuilder.append(create);
                createBuilder.append(";");
                createBuilder.append(System.lineSeparator());
            }
            for (String drop : sqlWriter.writeDropTableStatements(schema)) {
                dropBuilder.append(drop);
                dropBuilder.append(";");
                dropBuilder.append(System.lineSeparator());
            }
        }
        databaseInteractor.executeUpdate(createBuilder.toString());
        databaseInteractor.executeUpdate(dropBuilder.toString());
    }
    
    private void batch(Schema[] schemas, DatabaseInteractor databaseInteractor, SQLWriter sqlWriter) {
        ArrayList<String> creates = new ArrayList<>();
        ArrayList<String> drops = new ArrayList<>();
        for (Schema schema : schemas) {
            for (String create : sqlWriter.writeCreateTableStatements(schema)) {
                creates.add(create);
            }
            for (String drop : sqlWriter.writeDropTableStatements(schema)) {
                drops.add(drop);
            }
        }
        databaseInteractor.executeUpdatesAsBatch(creates);
        databaseInteractor.executeUpdatesAsBatch(drops);        
    }
    
    private void transaction(Schema[] schemas, DatabaseInteractor databaseInteractor, SQLWriter sqlWriter) {
        ArrayList<String> creates = new ArrayList<>();
        ArrayList<String> drops = new ArrayList<>();
        for (Schema schema : schemas) {
            for (String create : sqlWriter.writeCreateTableStatements(schema)) {
                creates.add(create);
            }
            for (String drop : sqlWriter.writeDropTableStatements(schema)) {
                drops.add(drop);
            }
        }
        databaseInteractor.executeUpdatesAsTransaction(creates);
        databaseInteractor.executeUpdatesAsTransaction(drops);        
    }

    @Override
    protected void validateParameters() {
    }

    public static void main(String[] args) {
        new DatabaseInteractorTimer().run(args);
    }
}
