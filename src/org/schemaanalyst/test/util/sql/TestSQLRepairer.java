package org.schemaanalyst.test.util.sql;

import org.junit.Test;
import org.schemaanalyst.util.sql.SQLRepairer;

import static org.junit.Assert.*;

public class TestSQLRepairer {

	@Test
	public void singleQuotesAreRemoved() {
		String example = "'hello'";
		String returnedExample = SQLRepairer.deleteSingleQuotes(example); 

		String expectedExample = "hello";
		assertEquals("Expected string should be " + expectedExample,
				expectedExample, returnedExample);
	}

	@Test
	public void singleSpacesAreRemoved() {
		String example = "hel lo";
		String returnedExample = SQLRepairer.deleteSpaces(example); 

		String expectedExample = "hello";
		assertEquals("Expected string should be " + expectedExample,
				expectedExample, returnedExample);
	}

	@Test
	public void spacesAreRemovedInsideOfSingleQuotes() {
		String example = "\'hel lo\'";
		String returnedExample = SQLRepairer.deleteSpaces(example); 

		String expectedExample = "\'hello\'";
		assertEquals("Expected string should be " + expectedExample,
				expectedExample, returnedExample);
	}

	@Test
	public void spacesAreRemovedInsideOfSingleQuotesRealExample() {
		String example = "CREATE TABLE 'NUTR_DEF' ('Nutrr_no' TEXT, 'Nutrient name' TEXT, 'Flav_Class' TEXT, 'Unit' TEXT, 'Tagname' TEXT);";
		String returnedExample = SQLRepairer.deleteSpacesInsideSingleQuotes(example); 

		String expectedExample = "CREATE TABLE 'NUTR_DEF' ('Nutrr_no' TEXT, 'Nutrient_name' TEXT, 'Flav_Class' TEXT, 'Unit' TEXT, 'Tagname' TEXT);";

		assertEquals("Expected string should be " + expectedExample,
				expectedExample, returnedExample);
	}

	@Test
	public void spacesAreRemovedInsideOfSingleQuotesAndThenSingleQuotesAreRemovedRealExample() {
		String example = "CREATE TABLE 'NUTR_DEF' ('Nutrr_no' TEXT, 'Nutrient name' TEXT, 'Flav_Class' TEXT, 'Unit' TEXT, 'Tagname' TEXT);";
		String returnedExample = SQLRepairer.deleteSpacesInsideSingleQuotes(example); 
		returnedExample = SQLRepairer.deleteSingleQuotes(returnedExample);

		String expectedExample = "CREATE TABLE NUTR_DEF (Nutrr_no TEXT, Nutrient_name TEXT, Flav_Class TEXT, Unit TEXT, Tagname TEXT);";

		assertEquals("Expected string should be " + expectedExample,
				expectedExample, returnedExample);
	}

	@Test
	public void doubleReplacedWithDecimal() {
		String example = "DOUBLE";
		String returnedExample = SQLRepairer.replaceDoubleWithDecimal(example); 

		String expectedExample = "DECIMAL";
		assertEquals("Expected string should be " + expectedExample,
				expectedExample, returnedExample);
	}

	@Test
	public void doubleReplacedWithDecimalWhenExtraWordsPresent() {
		String example = "DOUBLE CREATE table";
		String returnedExample = SQLRepairer.replaceDoubleWithDecimal(example); 

		String expectedExample = "DECIMAL CREATE table";
		assertEquals("Expected string should be " + expectedExample,
				expectedExample, returnedExample);
	}

}
