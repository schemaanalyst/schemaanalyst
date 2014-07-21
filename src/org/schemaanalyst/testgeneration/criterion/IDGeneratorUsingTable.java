package org.schemaanalyst.testgeneration.criterion;

import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Created by phil on 18/07/2014.
 */
public class IDGeneratorUsingTable implements IDGenerator {

    private Table table;
    private int id;

    public IDGeneratorUsingTable(Table table) {
        this.table = table;
        id = 0;
    }

    public String nextID() {
        id ++;
        return id + "-" + table;
    }
}
