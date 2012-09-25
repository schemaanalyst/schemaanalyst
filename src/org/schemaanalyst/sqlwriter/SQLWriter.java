package org.schemaanalyst.sqlwriter;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.schema.CheckConstraint;
import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.ForeignKeyConstraint;
import org.schemaanalyst.schema.PrimaryKeyConstraint;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.UniqueConstraint;
import org.schemaanalyst.schema.attribute.Attribute;

public class SQLWriter {

	protected AttributeSQLWriter attributeSQLWriter;
	protected ColumnTypeSQLWriter columnTypeSQLWriter;
	protected ConstraintSQLWriter constraintSQLWriter;
	protected OperandSQLWriter operandSQLWriter;
	protected CheckPredicateSQLWriter predicateSQLWriter;
	protected CellSQLWriter cellSQLWriter;
	protected ValueSQLWriter valueSQLWriter;
	
	public SQLWriter() {
	    instanitateSubWriters();
	    setupSubWriters();
	}
	
	protected void instanitateSubWriters() {
		attributeSQLWriter = new AttributeSQLWriter();
		columnTypeSQLWriter = new ColumnTypeSQLWriter();
		constraintSQLWriter = new ConstraintSQLWriter();
		operandSQLWriter = new OperandSQLWriter();
		predicateSQLWriter = new CheckPredicateSQLWriter();
		cellSQLWriter = new CellSQLWriter();
		valueSQLWriter = new ValueSQLWriter();
	}
	
	protected void setupSubWriters() {
		attributeSQLWriter.setOperandSQLWriter(operandSQLWriter);
		cellSQLWriter.setValueSQLWriter(valueSQLWriter);
		constraintSQLWriter.setPredicateSQLWriter(predicateSQLWriter);
		operandSQLWriter.setValueSQLWriter(valueSQLWriter);
		predicateSQLWriter.setOperandSQLWriter(operandSQLWriter);
	}
	
	public List<String> writeComments(List<String> comments) {
		List<String> statements = new ArrayList<String>();
		
		for (String comment : comments) {
			statements.add(writeComment(comment));
		}
		
		return statements;
	}
	
	public String writeComment(String comment) {
		return "-- "+comment;
	}
	
	public List<String> writeCreateTableStatements(Schema schema) {
		List<String> statements = new ArrayList<String>();
		
		List<Table> tables = schema.getTables();
		for (Table table : tables) {
			statements.add(writeCreateTableStatement(table));
		}				
		return statements;
	}	
	
	public String writeCreateTableStatement(Table table) {
					
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ");
		sql.append(table.getName());
		sql.append(" ( \n");
		
		boolean first = true;
		for (Column column : table.getColumns()) {
			if (first) first = false;
			else sql.append(", \n");
			
			// write column name
			sql.append("\t"); sql.append(column.getName()); 
			
			// write column type			
			sql.append("\t"); 
			if( columnTypeSQLWriter == null ) { System.out.println("ctsw is null!"); }
			sql.append(columnTypeSQLWriter.writeColumnType(column));
			
			// write column attributes
			List<Attribute> columnAttributes = column.getAttributes();
			if (columnAttributes.size() > 0) {
				sql.append("\t");
				
				for (Attribute columnAttribute : columnAttributes) {
					sql.append(" "); 
					sql.append(attributeSQLWriter.writeAttribute(columnAttribute)); 
				}
			}
			
			// write NOT NULLs
			if (column.isNotNull()) {
				sql.append("\tNOT NULL");
			}			
		}
		
		// write primary key
		PrimaryKeyConstraint primaryKey = table.getPrimaryKeyConstraint();
		if (primaryKey != null) {
			sql.append(",\n\t"); 
			sql.append(constraintSQLWriter.writeConstraint(primaryKey));
		}
		
		// write foreign keys
		for (ForeignKeyConstraint foreignKey : table.getForeignKeyConstraints()) {
			sql.append(",\n\t"); 
			sql.append(constraintSQLWriter.writeConstraint(foreignKey));	
		}
		
		// write uniques
		for (UniqueConstraint unique : table.getUniqueConstraints()) {
			sql.append(",\n\t"); 
			sql.append(constraintSQLWriter.writeConstraint(unique));	
		}		

		// write checks
		for (CheckConstraint check : table.getCheckConstraints()) {
			sql.append(",\n\t"); 
			sql.append(constraintSQLWriter.writeConstraint(check));	
		}				
		
		sql.append("\n)");
		return sql.toString();
	}	
	
	public String writeInsertStatement(Table table, List<Column> columns, List<String> values) {
		return "INSERT INTO " + table.getName()+
		       "("+SQLWriter.writeColumnList(columns)+") "+
		       "VALUES("+SQLWriter.writeSeparatedList(values)+")";
	}
	
	public String writeInsertStatement(Row row) {
		Table table = row.getTable();
		List<Column> columns = table.getColumns();
		List<String> valueStrings = new ArrayList<String>();
		
		for (Cell cell : row.getCells()) {
			String string = cellSQLWriter.writeCell(cell);
			valueStrings.add(string);
		}
		
		return writeInsertStatement(table, columns, valueStrings);
	}

	public List<String> writeInsertStatements(Data data) {
		List<String> statements = new ArrayList<String>();
		
		List<Table> tables = data.getTables();
		tables = Schema.orderByDependencies(tables);

		for (Table table : tables) {
		    List<Row> rows = data.getRows(table);
			if (rows != null) {
				for (Row row : data.getRows(table)) {
				    statements.add(writeInsertStatement(row));
				}
			}
		}
		return statements;
	}
	
    public List<String> writeDropTableStatements(Schema schema) {
    	return writeDropTableStatements(schema, false);
    }
    
    public List<String> writeDropTableStatements(Schema schema, boolean addIfExists) {
    	List<String> statements = new ArrayList<String>();
    	
    	List<Table> tables = schema.getTablesInReverseOrder();
    	for (Table table : tables) {
    		statements.add(writeDropTableStatement(table, addIfExists));
    	}
    	return statements;
    }	
	
	public String writeDropTableStatement(Table table) {
		return writeDropTableStatement(table, false);
	}
	
    public String writeDropTableStatement(Table table, boolean addIfExists) {
		String sql = "DROP TABLE ";
		if (addIfExists) {
			sql += "IF EXISTS "; 
		}
		sql += table.getName();
		return sql;
    }

	public static String writeSeparatedList(List<String> values) {
		StringBuilder sql = new StringBuilder();
		boolean first = true;
		for (String value : values) {
			if (first) first = false;
			else sql.append(", ");				
			sql.append(value);
		}
		return sql.toString();		
	}

	public static String writeColumnList(List<Column> columns) {
		List<String> columnStrings = new ArrayList<String>();
		for (Column column : columns) {
			columnStrings.add(column.getName());
		}
		return SQLWriter.writeSeparatedList(columnStrings);
	}	
}
