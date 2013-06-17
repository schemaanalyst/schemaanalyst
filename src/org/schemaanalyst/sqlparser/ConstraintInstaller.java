package org.schemaanalyst.sqlparser;

import java.util.ArrayList;
import java.util.List;

import gudusoft.gsqlparser.EConstraintType;
import gudusoft.gsqlparser.nodes.TConstraint;
import gudusoft.gsqlparser.nodes.TObjectName;
import gudusoft.gsqlparser.nodes.TObjectNameList;

import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.expression.Expression;

class ConstraintInstaller {

	Schema schema;
	Table currentTable;
	Column currentColumn;
	
	public static void install(Schema schema, Table currentTable, Column currentColumn, TConstraint node) {
		new ConstraintInstaller(schema, currentTable, currentColumn).performInstallation(node);
	}
	
	ConstraintInstaller(Schema schema, Table currentTable, Column currentColumn) {
		this.schema = schema;
		this.currentTable = currentTable;
		this.currentColumn = currentColumn;		
	}
	
	void performInstallation(TConstraint node) {
    	EConstraintType constraintType = node.getConstraint_type(); 

    	if (constraintType == EConstraintType.check) {
    		installCheckConstraint(node);
    	} else if (constraintType == EConstraintType.foreign_key) {
    		installForeignKeyConstraint(node);
    	} else if (constraintType == EConstraintType.notnull) {
    		installNotNullConstraint(node);    	
    	} else if (constraintType == EConstraintType.primary_key) {
    		installPrimaryKeyConstraint(node);
    	} else if (constraintType == EConstraintType.unique) {
    		installUniqueConstraint(node);
    	} 
	}
		
	String getConstraintName(TConstraint node) {
		String constraintName = null;
		TObjectName constraintNameObject = node.getConstraintName();
		if (constraintNameObject != null) {
			constraintName = constraintNameObject.toString();
		}
		return constraintName;
	}
	
	List<Column> getConstraintColumns(TConstraint node) {
		List<Column> columns = new ArrayList<>();
    	
    	TObjectNameList nodeColumns = node.getColumnList();
    	if (nodeColumns == null) {
    		if (currentColumn != null) {
    			columns.add(currentColumn);
    		} else {
    			throw new ConstraintInstallationException("No columns on which to define constraint!");
    		}
    	} else {
    		for (int i=0; i < nodeColumns.size(); i++) {
    			String columnName = NameSanitizer.sanitize(nodeColumns.getObjectName(i));
    			Column column = currentTable.getColumn(columnName);
    			columns.add(column);
    		}
    	}		
		
		return columns;
	}
	
	void installCheckConstraint(TConstraint node) {
		Expression expression = ExpressionMapper.map(currentTable, node.getCheckCondition());
		currentTable.addCheckConstraint(getConstraintName(node), expression);
	}
	
	void installNotNullConstraint(TConstraint node) {
		List<Column> columns = getConstraintColumns(node);
		
		if (columns.size() > 1) {
			throw new ConstraintInstallationException("Cannot make more than one column NOT NULL at a time");
		}
		
		Column column = columns.get(0);
		currentTable.addNotNullConstraint(getConstraintName(node), column);
	}
	
	void installPrimaryKeyConstraint(TConstraint node) {
		Column[] columns = getConstraintColumns(node).toArray(new Column[0]);
		currentTable.setPrimaryKeyConstraint(getConstraintName(node), columns);
	}
	
	void installForeignKeyConstraint(TConstraint node) {
		String referenceTableName = node.getReferencedObject().toString();
		Table referenceTable = schema.getTable(referenceTableName);
		
		List<Column> columns = getConstraintColumns(node);
		
		TObjectNameList referenceColumnList = node.getReferencedColumnList();
		for (int i=0; i < referenceColumnList.size(); i++) {
			String columnName = referenceColumnList.getObjectName(i).toString();
			Column column = referenceTable.getColumn(columnName);
			columns.add(column);
		}
		
		currentTable.addForeignKeyConstraint(getConstraintName(node), referenceTable, columns.toArray(new Column[0]));
	}
	
	void installUniqueConstraint(TConstraint node) {
		Column[] columns = getConstraintColumns(node).toArray(new Column[0]);
		currentTable.addUniqueConstraint(getConstraintName(node), columns);		
	}
}
