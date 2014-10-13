package org.schemaanalyst.unittest.testutil.mock;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Row;

import java.util.ArrayList;

public class MockRow extends Row {
    
    public MockRow() {
        super(new ArrayList<Cell>());
    }
}
