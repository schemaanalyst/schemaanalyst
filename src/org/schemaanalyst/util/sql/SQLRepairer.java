package org.schemaanalyst.util.sql;

/**
 * This class can repair an input SQL file so that it can be parsed by the SQL parser.
 *
 * @author Gregory M. Kapfhammer
 */
public class SQLRepairer {
	public static String repair(String sql) {
		String repairedSql = "";
		return repairedSql;
	}

	/**
	 * This method removes the single quotes from an SQL string.
	 *
	 * @param sql The String potentially with single quote marks.
	 * @return The String without the single quote marks.
	 */
	public static String deleteSingleQuotes(String sql) {
		return sql.replaceAll("'", "");
	}

}
