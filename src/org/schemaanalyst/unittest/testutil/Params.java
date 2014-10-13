package org.schemaanalyst.unittest.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Params {

    public static final Integer[] EMPTY_I = d();
    public static final int[] EMPTY_i = i();
    
    public static final Integer NULL = null;
    
    /**
     * For specifying values for a Data object easily with JUnitParams, using
     * nested calls to r() 
     * @param rows The Row values, specified using r()
     * @return An array of values for passing to a Data object
     */
    public static Integer[] d(Integer[]... rows) {
        if (rows.length > 0) {
            int rowLength = rows[0].length;
            List<Integer> dataValues = new ArrayList<>();
            
            for (Integer[] row : rows) {
                if (row.length != rowLength) {
                    throw new RuntimeException("Row length mismatch for " + row);
                }
                dataValues.addAll(Arrays.asList(row));
            }
            
            return dataValues.toArray(new Integer[dataValues.size()]);            
        } else {
            return new Integer[0];
        }
    }          
    
    /**
     * For specifying Integer column values as part of a call to d()
     * for specifying values for a Data object easily with JUnitParams 
     * @param columnValues The column values
     * @return An array of colum values
     */
    public static Integer[] r(Integer... columnValues) {
        return columnValues;
    }
    
    /**
     * Returns an array of Integer values, useful for specifying
     * values using JUnitParams.
     * @param values The values to put in the array.
     * @return The array of values.
     */
    public static Integer[] I(Integer... values) {
        return values;
    }
    
    /**
     * Returns an array of int values, useful for specifying
     * values using JUnitParams.
     * @param values The values to put in the array.
     * @return The array of values.
     */
    public static int[] i(int... values) {
        return values;
    }
}
