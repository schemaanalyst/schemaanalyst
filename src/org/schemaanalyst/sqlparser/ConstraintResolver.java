package org.schemaanalyst.sqlparser;

import java.util.ArrayList;
import java.util.List;

import gudusoft.gsqlparser.EConstraintType;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TObjectNameList;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Constraint;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;

class ConstraintResolver {

	Schema schema;
	
	ConstraintResolver(Schema schema) {
		this.schema = schema;
	}
	
	Constraint resolve(Table currentTable, Column currentColumn, TConstraint node) {
		
List<Column> columnList = new ArrayList<>();
    	
    	TObjectNameList nodeColumns = node.getColumnList();
    	if (nodeColumns == null) {
    		columnList.add(currentColumn);
    	} else {
    		for (int i=0; i < nodeColumns.size(); i++) {
    			String columnName = nodeColumns.getObjectName(i).toString();
    			Column column = currentTable.getColumn(columnName);
    			columnList.add(column);
    		}
    	}
    	
    	Column[] columns = columnList.toArray(new Column[0]);
    	
    	EConstraintType constraintType = node.getConstraint_type(); 
    	
    	if (constraintType == EConstraintType.notnull) {
    		for (Column column : columnList) {
    			currentTable.addNotNullConstraint(column);
    		}
    	} else if (constraintType == EConstraintType.primary_key) {
    		currentTable.setPrimaryKeyConstraint(columns);
    	} else if (constraintType == EConstraintType.foreign_key) {
    		
    		
    			String referencedTableName = node.getReferencedObject().toString();
    			Table referenceTable = schema.getTable(referencedTableName);
    			
    			TObjectNameList referencedColumnList = node.getReferencedColumnList();
    			for (int i=0; i < referencedColumnList.size(); i++) {
    				String columnName = referencedColumnList.getObjectName(i).toString();
    				Column column = referenceTable.getColumn(columnName);
    				columnList.add(column);
    			}
    			
    			currentTable.addForeignKeyConstraint(referenceTable, columnList.toArray(new Column[0]));
    			
    			System.out.println("Referenced table:");
    			System.out.println(referenceTable);     			
    			System.out.println("Referenced columns:");
    			System.out.println(node.getReferencedColumnList()); 
    			System.out.println("Column list:");
    			System.out.println(columns);
    		
    	}
    	
    	
    	
    	// debug
        System.out.println("constraint");
        
        return null;
		
	}
	
	
}
