package org.schemaanalyst.test.testutil.assertion;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.data.Data;

import static org.junit.Assert.assertArrayEquals;
import static org.schemaanalyst.test.testutil.DataUtils.dataToIntegerValues;

public class DataAssert {

    public static void assertDataEquals(Integer[] expectedValues, Data data) {
        Integer[] actualValues = dataToIntegerValues(data);
        assertArrayEquals("Data values should be [" + StringUtils.join(expectedValues, ", ") 
                + "] but were [" + StringUtils.join(actualValues, ", ") + "]",
                expectedValues, actualValues);
    }    
}
