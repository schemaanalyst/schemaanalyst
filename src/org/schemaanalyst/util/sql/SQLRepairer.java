package org.schemaanalyst.util.sql;

/**
 * This class can repair an input SQL file so that it can be parsed by the SQL parser.
 *
 * @author Gregory M. Kapfhammer
 */
public class SQLRepairer {

	/**
	 * This method removes the single quotes from an SQL string.
	 *
	 * @param sql The String potentially with single quote marks.
	 * @return The String without the single quote marks.
	:*/
	public static String deleteSingleQuotes(String sql) {
		return sql.replaceAll("'", "");
	}

	/**
	 * This method removes any spaces from an SQL string.
	 *
	 * @param sql The string that may have spaces.
	 * @return The string without any spaces.
	 */
	public static String deleteSpaces(String sql) {
		return sql.replaceAll("\\s", "");
	}

}
