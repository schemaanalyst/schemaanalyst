package org.schemaanalyst.datageneration.cellrandomisation;

import org.schemaanalyst.util.random.Random;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CellRandomiserFactory {

    public static CellRandomiser instantiate(String name, Random random) {

        // get hold of the method objects of this class 
        Class<CellRandomiserFactory> clazz = CellRandomiserFactory.class;
        Method methods[] = clazz.getMethods();

        // get the name to match a method name by lowercasing the first letter
        char characters[] = name.toCharArray();
        characters[0] = Character.toLowerCase(characters[0]);
        name = new String(characters);

        // find the method for instantiating the right data generator
        for (Method m : methods) {
            if (m.getName().equals(name)) {
                try {
                    Object[] args = {random};
                    return (CellRandomiser) m.invoke(null, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new UnknownCellRandomiserException("Unknown cell randomiser \"" + name + "\"");
    }
    
    public static List<String> getCellRandomiserChoices() {
        List<String> choices = new ArrayList<>();
        choices.add("Small");
        choices.add("Large");
        return choices;
    }

    public static CellRandomiser small(Random random) {
        return new CellRandomiser(
                random,
                0.1, // nullProbability

                1990, // yearMin
                2020, // yearMax 

                1, // monthMin
                12, // monthMax, 

                1, // dayMin
                31, // dayMax,

                0, // hourMin
                23, // hourMax

                0, // minuteMin
                59, // minuteMax

                0, // secondMin
                59, // secondMax

                new BigDecimal(-100), // numericMin
                new BigDecimal(100), // numericMax

                10, // stringLengthMax
                97, // characterMin
                122, // characterMax

                631152000, // timestampMin
                1577836800 // timestampMax		
                );
    }

    public static CellRandomiser large(Random random) {
        return new CellRandomiser(
                random,
                0.1, // nullProbability

                0, // yearMin
                5000, // yearMax 

                1, // monthMin
                12, // monthMax, 

                1, // dayMin
                31, // dayMax,

                0, // hourMin
                23, // hourMax

                0, // minuteMin
                59, // minuteMax

                0, // secondMin
                59, // secondMax

                new BigDecimal(-10000), // numericMin
                new BigDecimal(10000), // numericMax

                20, // stringLengthMax
                32, // characterMin
                126, // characterMax

                -2147483648, // timestampMin
                2147483647 // timestampMax		
                );
    }
}
