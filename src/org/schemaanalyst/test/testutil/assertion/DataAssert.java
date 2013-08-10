package org.schemaanalyst.test.testutil.assertion;

import static org.junit.Assert.assertArrayEquals;
import static org.schemaanalyst.test.testutil.DataUtils.dataToIntegerValues;

import org.schemaanalyst.data.Data;
import static org.schemaanalyst.util.StringUtils.implode;

public class DataAssert {

    public static void assertDataEquals(Integer[] expectedValues, Data data) {
        Integer[] actualValues = dataToIntegerValues(data);
        assertArrayEquals("Data values should be [" + implode(expectedValues, ", ") 
                + "] but were [" + implode(actualValues, ", ") + "]",
                expectedValues, actualValues);
    }    
}
