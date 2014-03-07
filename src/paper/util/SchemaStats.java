package paper.util;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SchemaStats {

    protected int numTables, numColumns, numUniqueColumnTypes;
    protected int numChecks, numForeignKeys, numNotNulls, numPrimaryKeys, numUniques;
    protected Set<Class<?>> columnTypes;

    public SchemaStats(Schema schema) {

        List<Table> tables = schema.getTablesInOrder();
        numTables = tables.size();

        for (Table table : tables) {
            columnTypes = new HashSet<>();
            List<Column> columns = table.getColumns();

            for (Column column : columns) {
                columnTypes.add(column.getDataType().getClass());
            }

            numColumns += columns.size();
            numUniqueColumnTypes = columnTypes.size();
            numChecks += schema.getCheckConstraints(table).size();
            numForeignKeys += schema.getForeignKeyConstraints(table).size();
            numNotNulls += schema.getNotNullConstraints(table).size();

            if (schema.getPrimaryKeyConstraint(table) != null) {
                numPrimaryKeys++;
            }

            numUniques += schema.getUniqueConstraints(table).size();
        }
    }

    public int getNumTables() {
        return numTables;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumUniqueColumnTypes() {
        return numUniqueColumnTypes;
    }

    public int getNumChecks() {
        return numChecks;
    }

    public int getNumForeignKeys() {
        return numForeignKeys;
    }

    public int getNumNotNulls() {
        return numNotNulls;
    }

    public int getNumPrimaryKeys() {
        return numPrimaryKeys;
    }

    public int getNumUniques() {
        return numUniques;
    }

    public Set<Class<?>> getColumnTypes() {
        return columnTypes;
    }
}
