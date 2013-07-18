package org.schemaanalyst.datageneration.search;

import java.util.HashMap;
import java.util.Map;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.SimpleConstraintCoverageReport;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class NaiveRandomConstraintCoverer extends DataGenerator {

    protected Schema schema;
    protected ConstraintEvaluator constraintEvaluator;
    protected DBMS dbms;
    protected CellRandomiser randomizer;
    protected int rowsPerTable, maxTriesPerTable;
    protected Map<Table, Integer> rowsAddedForEachTable;

    public NaiveRandomConstraintCoverer(Schema schema,
                                        DBMS dbms,
                                        CellRandomiser randomizer,
                                        int rowsPerTable,
                                        int maxTriesPerTable) {
        this.schema = schema;
        this.dbms = dbms;
        this.randomizer = randomizer;
        this.rowsPerTable = rowsPerTable;
        this.maxTriesPerTable = maxTriesPerTable;

        rowsAddedForEachTable = new HashMap<>();
        constraintEvaluator = new ConstraintEvaluator();
    }

    public SimpleConstraintCoverageReport generate() {
        constraintEvaluator.initialize(schema);
        int totalNumTries = 0;

        for (Table table : schema.getTables()) {
            int successfulRows = 0;
            int numTries = 0;

            while (successfulRows < rowsPerTable && numTries < maxTriesPerTable) {

                Data data = new Data();
                Row row = data.addRow(table, dbms.getValueFactory());
                randomizer.randomizeCells(row.getCells());

                boolean rowAdded = constraintEvaluator.evaluate(row, table);

                if (rowAdded) {
                    successfulRows++;
                }
                numTries++;
                totalNumTries++;
            }

            rowsAddedForEachTable.put(table, successfulRows);
        }

        SimpleConstraintCoverageReport report = constraintEvaluator.getCoverageReport();
        report.setNumAttempts(totalNumTries);
        return report;
    }
}
