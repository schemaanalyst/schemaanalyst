package org.schemaanalyst.mutation;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;

public class SpyLogParser {

    /** Read in a file from filename and return a List of lines that can be parsed further */
    public static List<String> createParseableLines(String filename) throws FileNotFoundException {
	ArrayList<String> lines = new ArrayList<String>();
	Scanner s = new Scanner(new File(filename));
	while (s.hasNextLine()) {
	    String line = s.nextLine();
	    lines.add(line);
	}
	return lines;
    }

    /** Parse out the components of a line of the log file. */
    public static List<String> createComponentsOfLines(String line) {
	ArrayList<String> components = new ArrayList<String>();
	StringTokenizer tokenizer = new StringTokenizer(line, "|");
	while(tokenizer.hasMoreTokens()) {
	    components.add(tokenizer.nextToken());
	}	   
	return components;
    }

    /** Extract the relevant line from a given list of components; the last one! */
    public static String getRelevantComponent(List<String> components) {
	return components.get(components.size()-1);
    }

    /** Determine if the relevant component is an important SQL String; we are looking for INSERTS now. */
    public static boolean isImportantSQL(String component) {
	if(component.contains("INSERT") || component.contains("insert") || 
	   component.contains("SELECT") || component.contains("select")) {
	    return true;
	}
	return false;
    }

    /** Determine if this is a select statement */
    public static boolean isSelectStatement(String component) {
 	if(component.contains("SELECT") || component.contains("select")) {
	    return true;
	}
	return false;
    }

    /** Determine if this is an insert statement */
    public static boolean isInsertStatement(String component) {
	if(component.contains("INSERT") || component.contains("insert") ) {
	    return true;
	}
	return false;
    }

    /** Replace the '' values with NULL for a fair comparison of DBMonster */
    public static String replaceEmptyStringWithNull(String component) {
	return component.replace("\'\'", "NULL");
    }

    /** Create relevant components from a specified filename */
    public static List<String> createRelevantComponents(String filename) throws FileNotFoundException {
	ArrayList<String> relevantComponents = new ArrayList<String>();
	List<String> parseableLines = createParseableLines(filename);
	for(String parseableLine : parseableLines) {
	    List<String> componentsOfLine = createComponentsOfLines(parseableLine);
	    String relevantComponent = getRelevantComponent(componentsOfLine);
	    if(isImportantSQL(relevantComponent)) {
		relevantComponents.add(relevantComponent);
	    }
	}
	return relevantComponents;
    }

}
