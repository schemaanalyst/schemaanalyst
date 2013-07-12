package org.schemaanalyst.test.mutation;

import java.util.List;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

import org.schemaanalyst.mutation.SpyLogParser;

public class TestFileFinderAndLineExtractor {

    @Test
    public void findLinesFromFileThatDoesNotExist() {
        try {
            SpyLogParser.createParseableLines("");
            fail("SpyLogParser should not be able to find a null file.");
        } catch (java.io.FileNotFoundException e) {
        }
    }

    @Test
    public void findLinesFromFileWithFiveLines() {
        try {
            int expectedLength = 5;
            List actualList = SpyLogParser.createParseableLines("TestFiles/fivelinefile");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());

        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void findLinesFromFileWithFiveLinesDBMonster() {
        try {
            int expectedLength = 5;
            List actualList = SpyLogParser.createParseableLines("TestFiles/fivelines.dbmonster");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());

        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void findLinesFromFileWithFiveLinesSchemaAnalyst() {
        try {
            int expectedLength = 5;
            List actualList = SpyLogParser.createParseableLines("TestFiles/fivelines.schemaanalyst");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());

        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void findLinesFromFileWithCreateTableSchemaAnalyst() {
        try {
            int expectedLength = 7;
            List actualList = SpyLogParser.createParseableLines("TestFiles/createtable.schemaanalyst");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());

        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void findAndCheckLinesFromFileWithFiveLines() {
        try {
            int expectedLength = 5;
            List actualList = SpyLogParser.createParseableLines("TestFiles/fivelinefile");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());
            assertEquals(actualList, Arrays.asList("a", "b", "c", "d", "e"));
        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void findLinesFromFileWithTenLines() {
        try {
            int expectedLength = 10;
            List actualList = SpyLogParser.createParseableLines("TestFiles/tenlinefile");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());

        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void findAndCheckLinesFromFileWithTenLines() {
        try {
            int expectedLength = 10;
            List actualList = SpyLogParser.createParseableLines("TestFiles/tenlinefile");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());
            assertEquals(actualList, Arrays.asList("a", "b", "c", "d", "e", "1", "2", "3", "4", "5"));
        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void createComponentsOfSimpleAndShortLine() {
        List actualComponents = SpyLogParser.createComponentsOfLines("a|b");
        int expectedLength = 2;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertEquals(actualComponents, Arrays.asList("a", "b"));
    }

    @Test
    public void createComponentsOfSimpleAndMediumLine() {
        List actualComponents = SpyLogParser.createComponentsOfLines("a|b|c|d|e|f|g|h|i|j");
        int expectedLength = 10;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertEquals(actualComponents, Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));
    }

    @Test
    public void createComponentsOfRealDBMonsterLine() {
        List actualComponents = SpyLogParser.createComponentsOfLines("1342027938135|4|1|statement|INSERT INTO userinfo (card_number, pin_number, user_name, acct_lock) VALUES (?, ?, ?, ?)|INSERT INTO userinfo (card_number, pin_number, user_name, acct_lock) VALUES ('1', '74', 'patt', '4')");
        int expectedLength = 6;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertTrue(actualComponents.contains("1342027938135"));
    }

    @Test
    public void createComponentsOfRealSchemaAnalystLine() {
        List actualComponents = SpyLogParser.createComponentsOfLines("1342465430141|7|0|statement||INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, 0, '', 0)");
        int expectedLength = 5;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertTrue(actualComponents.contains("1342465430141"));
    }

    @Test
    public void getRelevantComponentOfSimpleAndShortLine() {
        List actualComponents = SpyLogParser.createComponentsOfLines("a|b");
        int expectedLength = 2;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertEquals(actualComponents, Arrays.asList("a", "b"));

        String expectedRelevant = "b";
        String actualRelevant = SpyLogParser.getRelevantComponent(actualComponents);
        assertEquals("Expected component should be " + expectedRelevant,
                expectedRelevant,
                actualRelevant);
    }

    @Test
    public void getRelevantComponentOfSimpleAndMediumLine() {
        List actualComponents = SpyLogParser.createComponentsOfLines("a|b|c|d|e|f|g|h|i|j");
        int expectedLength = 10;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertEquals(actualComponents, Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

        String expectedRelevant = "j";
        String actualRelevant = SpyLogParser.getRelevantComponent(actualComponents);
        assertEquals("Expected component should be " + expectedRelevant,
                expectedRelevant,
                actualRelevant);
    }

    @Test
    public void getRelevantComponentOfRealDBMonsterLine() {
        List actualComponents = SpyLogParser.createComponentsOfLines("1342027938135|4|1|statement|INSERT INTO userinfo (card_number, pin_number, user_name, acct_lock) VALUES (?, ?, ?, ?)|INSERT INTO userinfo (card_number, pin_number, user_name, acct_lock) VALUES ('1', '74', 'patt', '4')");
        int expectedLength = 6;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertTrue(actualComponents.contains("1342027938135"));

        String expectedRelevant = "INSERT INTO userinfo (card_number, pin_number, user_name, acct_lock) VALUES ('1', '74', 'patt', '4')";
        String actualRelevant = SpyLogParser.getRelevantComponent(actualComponents);
        assertEquals("Expected component should be " + expectedRelevant,
                expectedRelevant,
                actualRelevant);
    }

    @Test
    public void getRelevantComponentOfRealSchemaAnalystLine() {
        List actualComponents = SpyLogParser.createComponentsOfLines("1342465430141|7|0|statement||INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, 0, '', 0)");
        int expectedLength = 5;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertTrue(actualComponents.contains("1342465430141"));

        String expectedRelevant = "INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, 0, '', 0)";
        String actualRelevant = SpyLogParser.getRelevantComponent(actualComponents);
        assertEquals("Expected component should be " + expectedRelevant,
                expectedRelevant,
                actualRelevant);

    }

    @Test
    public void getRelevantComponentOfRealDBMonsterLineCheckImportantSQL() {
        List actualComponents = SpyLogParser.createComponentsOfLines("1342027938135|4|1|statement|INSERT INTO userinfo (card_number, pin_number, user_name, acct_lock) VALUES (?, ?, ?, ?)|INSERT INTO userinfo (card_number, pin_number, user_name, acct_lock) VALUES ('1', '74', 'patt', '4')");
        int expectedLength = 6;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertTrue(actualComponents.contains("1342027938135"));

        String expectedRelevant = "INSERT INTO userinfo (card_number, pin_number, user_name, acct_lock) VALUES ('1', '74', 'patt', '4')";
        String actualRelevant = SpyLogParser.getRelevantComponent(actualComponents);
        assertEquals("Expected component should be " + expectedRelevant,
                expectedRelevant,
                actualRelevant);
        assertTrue(SpyLogParser.isImportantSQL(actualRelevant));
        assertTrue(!SpyLogParser.isImportantSQL("1342027938135"));
    }

    @Test
    public void findRelevantLinesFromFileWithFiveLinesDBMonster() {
        try {
            int expectedLength = 5;
            List actualList = SpyLogParser.createParseableLines("TestFiles/fivelines.dbmonster");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());

            int expectedRelevantLength = 5;
            List actualRelevantList = SpyLogParser.createRelevantComponents("TestFiles/fivelines.dbmonster");
            assertEquals("Expected relevant length should be " + expectedRelevantLength,
                    expectedRelevantLength,
                    actualRelevantList.size());
        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void findRelevantLinesFromFileWithFiveLinesSchemaAnalyst() {
        try {
            int expectedLength = 5;
            List actualList = SpyLogParser.createParseableLines("TestFiles/fivelines.schemaanalyst");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());

            int expectedRelevantLength = 1;
            List actualRelevantList = SpyLogParser.createRelevantComponents("TestFiles/fivelines.schemaanalyst");
            assertEquals("Expected relevant length should be " + expectedRelevantLength,
                    expectedRelevantLength,
                    actualRelevantList.size());
        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void findRelevantLinesFromCompleteDBMonsterLogFile() {
        try {
            int expectedLength = 3070;
            List actualList = SpyLogParser.createParseableLines("TestFiles/dbmonster-spy.log");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());

            int expectedRelevantLength = 3070;
            List actualRelevantList = SpyLogParser.createRelevantComponents("TestFiles/dbmonster-spy.log");
            assertEquals("Expected relevant length should be " + expectedRelevantLength,
                    expectedRelevantLength,
                    actualRelevantList.size());
        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void findRelevantLinesFromCompleteSchemaAnalystLogFile() {
        try {
            int expectedLength = 715;
            List actualList = SpyLogParser.createParseableLines("TestFiles/schemaanalyst-spy.log");
            assertEquals("Expected length should be " + expectedLength,
                    expectedLength,
                    actualList.size());

            int expectedRelevantLength = 176;
            List actualRelevantList = SpyLogParser.createRelevantComponents("TestFiles/schemaanalyst-spy.log");
            assertEquals("Expected relevant length should be " + expectedRelevantLength,
                    expectedRelevantLength,
                    actualRelevantList.size());
        } catch (java.io.FileNotFoundException e) {
            fail("Should not fail in finding this file!");
        }
    }

    @Test
    public void checkWeFoundAnInsertStatement() {
        String insertStatement = "INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, 0, '', 0)";
        assertTrue(SpyLogParser.isInsertStatement(insertStatement));
        assertTrue(!SpyLogParser.isSelectStatement(insertStatement));
    }

    @Test
    public void checkWeFoundASelectStatement() {
        String selectStatement = "SELECT count(*) FROM userinfo";
        assertTrue(!SpyLogParser.isInsertStatement(selectStatement));
        assertTrue(SpyLogParser.isSelectStatement(selectStatement));
    }

    @Test
    public void testReplaceEmptyStringWithNullRealSchemaAnalystLine() {
        List actualComponents = SpyLogParser.createComponentsOfLines("1342465430141|7|0|statement||INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, 0, '', 0)");
        int expectedLength = 5;

        assertEquals("Expected length should be " + expectedLength,
                expectedLength,
                actualComponents.size());
        assertTrue(actualComponents.contains("1342465430141"));

        String expectedRelevant = "INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, 0, '', 0)";
        String actualRelevant = SpyLogParser.getRelevantComponent(actualComponents);
        assertEquals("Expected component should be " + expectedRelevant,
                expectedRelevant,
                actualRelevant);

        String expectedRelevantTransformed = "INSERT INTO UserInfo(card_number, pin_number, user_name, acct_lock) VALUES(-1, 0, NULL, 0)";
        String actualRelevantTransformed = SpyLogParser.replaceEmptyStringWithNull(expectedRelevant);
        assertEquals("Expected component should be " + expectedRelevantTransformed,
                expectedRelevantTransformed,
                actualRelevantTransformed);
    }

    @Test
    public void testReplaceEmptyStringWithNullRealDBMonsterLine() {
        String expectedRelevant = "INSERT INTO account (id, account_name, balance, card_number) VALUES ('25', '', '106', '42')";
        String expectedRelevantTransformed = "INSERT INTO account (id, account_name, balance, card_number) VALUES ('25', NULL, '106', '42')";
        String actualRelevantTransformed = SpyLogParser.replaceEmptyStringWithNull(expectedRelevant);
        assertEquals("Expected component should be " + expectedRelevantTransformed,
                expectedRelevantTransformed,
                actualRelevantTransformed);
    }
}
