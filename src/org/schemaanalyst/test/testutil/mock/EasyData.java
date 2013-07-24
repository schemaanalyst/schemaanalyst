package org.schemaanalyst.test.testutil.mock;

import java.util.List;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.DateValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.ValueVisitor;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class EasyData extends Data {

    protected Schema schema;

    public EasyData(Schema schema) {
        this.schema = schema;
    }

    public void addRow(String tableName, String rowData, ValueFactory valueFactory) {

        class SetValueFromString implements ValueVisitor {

            String string;

            public void set(Cell cell, String string) {
                if (string.equals("NULL")) {
                    cell.setNull(true);
                } else {
                    this.string = string;
                    cell.setNull(false);
                    cell.getValue().accept(this);
                }
            }

            @Override
            public void visit(BooleanValue value) {
                // to complete...
            }

            @Override
            public void visit(DateValue value) {
                // to complete...
            }

            @Override
            public void visit(DateTimeValue value) {
                // to complete...
            }

            @Override
            public void visit(NumericValue value) {
                value.set(string);
            }

            @Override
            public void visit(StringValue value) {
                // remove quotes
                int endIndex = Math.max(1, string.length() - 1);
                String stringValue = string.substring(1, endIndex);

                value.set(stringValue);
            }

            @Override
            public void visit(TimeValue value) {
                // to complete...				
            }

            @Override
            public void visit(TimestampValue value) {
                // to complete...				
            }
        }

        Table table = schema.getTable(tableName);
        Row row = super.addRow(table, valueFactory);

        List<Cell> cells = row.getCells();
        String[] valueStrings = rowData.split(",");

        SetValueFromString svfs = new SetValueFromString();

        for (int i = 0; i < valueStrings.length; i++) {
            svfs.set(cells.get(i), valueStrings[i].trim());
        }
    }
}
