package org.schemaanalyst.unittest.testutil.mock;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

/**
 * Created by phil on 24/02/2014.
 */
public class SimpleSchema extends Schema {

    public SimpleSchema() {
        super("SimpleSchema");

        Table tab1 = createTable("Tab1");
        tab1.createColumn("Tab1Col1", new IntDataType());
        tab1.createColumn("Tab1Col2", new IntDataType());
        tab1.createColumn("Tab1Col3", new IntDataType());
        tab1.createColumn("Tab1Col4", new IntDataType());
        tab1.createColumn("Tab1Col5", new IntDataType());

        Table tab2 = createTable("Tab2");
        tab2.createColumn("Tab2Col1", new IntDataType());
        tab2.createColumn("Tab2Col2", new IntDataType());
        tab2.createColumn("Tab2Col3", new IntDataType());
        tab2.createColumn("Tab2Col4", new IntDataType());
        tab2.createColumn("Tab2Col5", new IntDataType());
    }
}
