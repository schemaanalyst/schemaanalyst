package org.schemaanalyst.sqlrepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableDependencyOrderer {

    private Schema schema;
    private List<Table> order;

    public List<Table> order(List<Table> tables, Schema schema) {
        this.schema = schema;
        order = new ArrayList<>();
        for (Table table : tables) {
            orderFrom(table, new ArrayList<Table>());
        }
        return order;
    }

    public List<Table> reverseOrder(List<Table> tables, Schema schema) {
    	order(tables, schema);
    	Collections.reverse(order);
    	return order;
    }
    
    protected void orderFrom(Table currentTable, List<Table> runningList) {
        List<Table> references = schema.getConnectedTables(currentTable);

        for (Table referencedTable : references) {
            if (!referencedTable.equals(currentTable) && !order.contains(referencedTable)) {

                if (runningList.contains(referencedTable)) {
                    throw new SQLRepresentationException(
                            "Cannot compute ordered table list due to a circular dependency between table "
                            + "\"" + currentTable + "\" and \"" + referencedTable + "\"");
                }

                List<Table> appendedRunningList = new ArrayList<>();
                appendedRunningList.addAll(runningList);
                appendedRunningList.add(currentTable);

                orderFrom(referencedTable, appendedRunningList);
            }
        }

        if (!order.contains(currentTable)) {
            order.add(currentTable);
        }
    }
}
