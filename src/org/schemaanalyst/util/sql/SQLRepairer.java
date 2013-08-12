package org.schemaanalyst.util.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	/**
	 * This method removes spaces from names between single quotes from a SQL string.
	 *
	 * @param sql The string that may have spaced names between single quotes.
	 * @return The string that does not contain spacing between single quotes.
	 */
	public static String deleteSpacesInsideSingleQuotes(String sql) {
		// prepare the string buffer and regular expressions for removing white space between single quotes
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("\'([^\']*)\'");
		Matcher matcher = p.matcher(sql);
		
		// iterate through all of the possible matches, replacing as we go
		while(matcher.find()) { 
			matcher.appendReplacement(sb, matcher.group().replaceAll("\\s+", "_")); 
		}
		
		// get the last part of the string that the matcher will not find
		int lastIndexOfSingleQuote = sql.lastIndexOf("'");
		sb.append(sql.substring(lastIndexOfSingleQuote+1));

		// return the fully transformed string
		return sb.toString();
	}

	/**
	 * Read in an entire file into a List of strings.
	 *
	 * @param file The file that contains the list of strings.
	 * @return The list of strings.
	 */
	public static List<String> readLines(File file) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
