package org.schemaanalyst.sqlparser;

import java.util.ArrayList;
import java.util.List;

import gudusoft.gsqlparser.EConstraintType;
import gudusoft.gsqlparser.EDataType;
import gudusoft.gsqlparser.nodes.TColumnDefinition;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TConstraintList;
import gudusoft.gsqlparser.nodes.TObjectNameList;
import gudusoft.gsqlparser.nodes.TParseTreeVisitor;
import gudusoft.gsqlparser.stmt.TCreateTableSqlStatement;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.ColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

class SchemaParseTreeVisitor extends TParseTreeVisitor {

	final static boolean DEBUG = true;
	
	Schema schema;
	Table currentTable;
	Column currentColumn;
	
	SchemaParseTreeVisitor(Schema schema) {
		this.schema = schema;
	}
	
    public void preVisit(TCreateTableSqlStatement node) {
    	currentTable = schema.createTable(node.getTableName().toString());
    	
    	if (DEBUG) System.out.println("table: " + node.getTableName().toString());
    }

    public void postVisit(TColumnDefinition node) {
    	String name = node.getColumnName().toString();
    	
    	// need to return to this ...
    	ColumnType type = null;
    	EDataType dataType = node.getDatatype().getDataType();
    	if (dataType == EDataType.int_t) {
    		type = new IntColumnType();
    	} else if (dataType == EDataType.varchar_t) {
    		type = new VarCharColumnType(50);
    	}
    	
    	currentColumn = currentTable.addColumn(name, type);
    	
    	if (DEBUG) {
    		System.out.println("column: " + name);
    		System.out.println("\t" + dataType);
    	}
    	    	
    	TConstraintList	list = node.getConstraints();
    	if (list != null) {
	    	for (int i=0 ; i < list.size(); i++) {
	    		list.getElement(i).accept(this);
	    	}
    	}
    }

    public void postVisit(TConstraint node) {
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
    		
    		if (DEBUG) {
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
    	}
    	
    	
    	
    	// debug
        System.out.println("constraint");
    }	
	
}
