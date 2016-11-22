package org.schemaanalyst.schema.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CheckParser {
	private String sqlSchema;
	private List<String> checkStatments;
	
	private static Pattern PATTERN = Pattern.compile(".*\\(([^\\)]+)\\)");
	
	public CheckParser(String tableSqlCreateStatement) {
		this.sqlSchema = tableSqlCreateStatement;
	}
	
	private void parseCheckStatment() {
		List<String> statments = Arrays.asList(sqlSchema.split(","));
		checkStatments = new ArrayList<String>();
		for (String statment : statments) {
			if (statment.contains("CHECK")){
				Matcher m = PATTERN.matcher(statment.trim());
				if(m.find()){
					checkStatments.add(m.group(1));
				}
			}
		}
	}
	
	public void printCheckStatments() {
		this.parseCheckStatment();
		int i = 1;
		for (String stm : checkStatments) {
			if (stm.contains("OR") || stm.contains("or")) {
				System.out.println(i + " OR expersion ==> " + stm);
				for (String condition : stm.split("((OR))"))
					System.out.println(condition.trim());
			} else if (stm.contains("and") || stm.contains("AND")) {
				System.out.println(i + " AND expersion ==> " + stm.split("AND"));
			} else {
				System.out.println(i + " " + stm);
			}
			i++;
		}
	}
	
}
