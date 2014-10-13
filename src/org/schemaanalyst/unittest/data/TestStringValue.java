package org.schemaanalyst.unittest.data;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.StringValue;

import static org.junit.Assert.assertEquals;

public class TestStringValue {

    @Test
    public void simpleConstruction() {
        String testString = "test";
        StringValue stringValue = new StringValue(testString);

        assertEquals("StringValue should be \"" + testString + "\"",
                testString,
                stringValue.get());
    }

    @Test
    public void constructionWithLength() {
        int length = 2;
        String testString = "test";
        StringValue stringValue = new StringValue(testString, length);

        String expectedString = testString.substring(0, length);

        assertEquals("StringValue should be \"" + expectedString + "\"",
                expectedString,
                stringValue.get());
    }

    @Test
    public void addCharacter() {
        StringValue stringValue = new StringValue();
        stringValue.addCharacter(new NumericValue(StringValue.LOWER_Z_CHAR));

        String expectedString = "z";
        assertEquals("StringValue should be \"" + expectedString + "\"",
                expectedString,
                stringValue.get());


        stringValue.addCharacter();
        expectedString = "za";
        assertEquals("StringValue should be \"" + expectedString + "\"",
                expectedString,
                stringValue.get());
    }

    @Test
    public void addCharacterLengthLimit() {
        StringValue stringValue = new StringValue(1);

        // add two characters but only the first should be added
        String expectedString = "a";
        stringValue.addCharacter();
        stringValue.addCharacter();

        assertEquals("StringValue should be \"" + expectedString + "\"",
                expectedString,
                stringValue.get());
    }

    @Test
    public void removeCharacter() {
        StringValue stringValue = new StringValue();

        stringValue.addCharacter();
        stringValue.addCharacter();
        stringValue.removeCharacter();

        String expectedString = "a";
        assertEquals("StringValue should be \"" + expectedString + "\"",
                expectedString,
                stringValue.get());
    }

    @Test
    public void removeCharacterEmptyString() {
        StringValue stringValue = new StringValue();

        stringValue.removeCharacter();

        String expectedString = "";
        assertEquals("StringValue should be \"" + expectedString + "\"",
                expectedString,
                stringValue.get());
    }
}
