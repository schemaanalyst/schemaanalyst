package paper.util;

import org.schemaanalyst.sqlrepresentation.Schema;

import java.util.HashSet;
import java.util.Set;

public class SchemaStatsTable {

    protected String columnSeparator;
    protected String endOfLine;

    public SchemaStatsTable(String columnSeparator, String endOfLine) {
        this.columnSeparator = columnSeparator;
        this.endOfLine = endOfLine;
    }

    public String write(Schema... schemas) {
        StringBuffer table = new StringBuffer();

        int totalNumTables = 0, totalNumColumns = 0, // totalUniqueColumnTypes = 0,
                totalNumChecks = 0, totalNumForeignKeys = 0, totalNumNotNulls = 0, totalNumPrimaryKeys = 0, totalNumUniques = 0;

        writeHeader(table);

        Set<Class<?>> allColumnTypes = new HashSet<>();
        for (Schema schema : schemas) {
            SchemaStats stats = new SchemaStats(schema);
            int numTables = stats.getNumTables();
            int numColumns = stats.getNumColumns();
            //int numUniqueColumnTypes = stats.getNumUniqueColumnTypes();
            int numChecks = stats.getNumChecks();
            int numForeignKeys = stats.getNumForeignKeys();
            int numNotNulls = stats.getNumNotNulls();
            int numPrimaryKeys = stats.getNumPrimaryKeys();
            int numUniques = stats.getNumUniques();

            writeRow(table, schema.getName(), numTables, numColumns,// numUniqueColumnTypes,
                    numChecks, numForeignKeys, numNotNulls, numPrimaryKeys, numUniques);

            allColumnTypes.addAll(stats.getColumnTypes());

            totalNumTables += numTables;
            totalNumColumns += numColumns;
            totalNumChecks += numChecks;
            totalNumForeignKeys += numForeignKeys;
            totalNumNotNulls += numNotNulls;
            totalNumPrimaryKeys += numPrimaryKeys;
            totalNumUniques += numUniques;
        }

        //totalUniqueColumnTypes = allColumnTypes.size();

        writeFooter(table, totalNumTables, totalNumColumns, //totalUniqueColumnTypes,
                totalNumChecks, totalNumForeignKeys, totalNumNotNulls, totalNumPrimaryKeys, totalNumUniques);

        return table.toString();
    }

    protected void writeHeader(StringBuffer table) {
        table.append("Schema");
        table.append(columnSeparator);

        table.append("Tables");
        table.append(columnSeparator);

        table.append("Cols");
        table.append(columnSeparator);

//        table.append("Col. types");
//        table.append(columnSeparator);

        table.append("Checks");
        table.append(columnSeparator);

        table.append("FKs");
        table.append(columnSeparator);

        table.append("Not nulls");
        table.append(columnSeparator);

        table.append("PKs");
        table.append(columnSeparator);

        table.append("Uniques");
        table.append(columnSeparator);

        table.append("Constraints");

        table.append(endOfLine);
    }

    protected void writeRow(StringBuffer table,
            String name, int numTables, int numColumns, //int numUniqueColumnTypes,
            int numChecks, int numForeignKeys, int numNotNulls, int numPrimaryKeys, int numUniques) {

        table.append(name);
        table.append(columnSeparator);

        table.append(numTables);
        table.append(columnSeparator);

        table.append(numColumns);
        table.append(columnSeparator);

        //table.append(numUniqueColumnTypes);
        //table.append(columnSeparator);

        table.append(numChecks);
        table.append(columnSeparator);

        table.append(numForeignKeys);
        table.append(columnSeparator);

        table.append(numNotNulls);
        table.append(columnSeparator);

        table.append(numPrimaryKeys);
        table.append(columnSeparator);

        table.append(numUniques);
        table.append(columnSeparator);

        table.append(numChecks + numForeignKeys + numNotNulls + numPrimaryKeys + numUniques);

        table.append(endOfLine);
    }

    protected void writeFooter(StringBuffer table,
            int totalNumTables, int totalNumColumns, //int totalNumColumnTypes,
            int totalNumChecks, int totalNumForeignKeys, int totalNumNotNulls,
            int totalNumPrimaryKeys, int totalNumUniques) {
        writeRow(table, "Total", totalNumTables, totalNumColumns, //totalNumColumnTypes, 
                totalNumChecks, totalNumForeignKeys, totalNumNotNulls, totalNumPrimaryKeys, totalNumUniques);
    }
}
