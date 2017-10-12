package org.schemaanalyst.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.schemaanalyst.data.BooleanValue;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.DateTimeValue;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.TimeValue;
import org.schemaanalyst.data.TimestampValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.RealDataType;
import org.schemaanalyst.util.tuple.Pair;

import com.google.common.base.Joiner;

public class DataMapper {
	private Connection c = null;
	private Schema schema;
	private List<Table> tables = new ArrayList<Table>();
	private Data data = new Data();

	public void connectDB(Schema schema) {
		this.schema = schema;
		String name_of_db = "casestudies/schemawithdata/" +schema.getName()+".db";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+name_of_db);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	@SuppressWarnings("deprecation")
	public void mapData() {
		Statement stmt = null;
		try {
			tables = schema.getTablesInReverseOrder();

			boolean connectedTable = false;

			
			for (Table table : tables) {
				List<Column> schema_columns = table.getColumns();
				String columns = Joiner.on(",").join(schema_columns);
				//System.out.println(columns);
				
				// Getting list of FKs
				List<ForeignKeyConstraint> fks = schema.getForeignKeyConstraints(table);
				List<String> fk_cells = new ArrayList<String>();
				
				if (fks.size() > 0) {
					connectedTable = true;
				}

				if (connectedTable) {
					String aStm = "SELECT "+ columns +" FROM "+ table.toString() +" ORDER BY RANDOM() LIMIT 1;";
					fk_cells.add(aStm);
				} else {
					// Get former tables info
					if (data.getTables().size() != 0) {
					for (Table t : data.getTables()) {
						// Get table info from schema
						Table tbl = schema.getTable(t.getName());
						// get Foreign Keys for this table
						List<ForeignKeyConstraint> forKeys = schema.getForeignKeyConstraints(tbl);
						// for each foreign key make a statement.
						if (forKeys.size() > 0) {
							for (ForeignKeyConstraint f : forKeys) {
								// Get it if it matches the table
								if (tbl.equals(f.getTable()) && table.equals(f.getReferenceTable())) {
	
									for (Pair<Column> c : f.getColumnPairs()) {

										for (Cell cl : data.getCells(tbl, c.getFirst())) {
											String condition = c.getSecond().getName() + " = " + cl.getValue(); 
											if (cl.getValue() instanceof StringValue) {
												StringValue newVal = (StringValue)cl.getValue();
												condition = c.getSecond().getName() + " = '" + newVal.get() + "'"; 
											}
											String aStm = "SELECT "+ columns +" FROM "+ table.toString() +" WHERE " + condition + " LIMIT 1;";
											fk_cells.add(aStm);
										}
									}
								}
							}
						} else {
							String aStm = "SELECT "+ columns +" FROM "+ table.toString() +" ORDER BY RANDOM() LIMIT 1;";
							fk_cells.add(aStm);
						}
						
					}
					} else {
						String aStm = "SELECT "+ columns +" FROM "+ table.toString() +" ORDER BY RANDOM() LIMIT 1;";
						fk_cells.add(aStm);
					}
				}

				// Mapper
				for (String stm : fk_cells) {
					c.setAutoCommit(false);
					//System.out.println(stm);
					stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery(stm);
					while (rs.next()) {
						List<Cell> cells = new ArrayList<Cell>();
						for (Column column_name: schema_columns) {
							
							Value value = null;
							
							Object cell_val = rs.getObject(column_name.toString());

							if (cell_val instanceof String || cell_val instanceof Character) {
								StringValue stringvalue = new StringValue();
								stringvalue.setCharacterRange(32, 126);
								stringvalue.set(rs.getString(column_name.toString()));
								value = stringvalue;
							} else if(cell_val instanceof Integer)
								value = new NumericValue(rs.getInt(column_name.toString()));
							else if(cell_val instanceof Double && column_name.getDataType() instanceof RealDataType)
								value = new NumericValue(new BigDecimal(rs.getFloat(column_name.toString())));
							else if(cell_val instanceof Long)
								value = new NumericValue(new BigDecimal(rs.getLong(column_name.toString())));
							else if(cell_val instanceof Double &&  !(column_name.getDataType() instanceof RealDataType))
								value = new NumericValue(rs.getBigDecimal(column_name.toString()));
							else if(cell_val instanceof Date)
								value = new DateTimeValue(rs.getDate(column_name.toString()).getYear(), rs.getDate(column_name.toString()).getMonth(), rs.getDate(column_name.toString()).getDay(), rs.getDate(column_name.toString()).getHours(), rs.getDate(column_name.toString()).getMinutes(), rs.getDate(column_name.toString()).getSeconds());
							else if(cell_val instanceof Time)
								value = new TimeValue(rs.getTime(column_name.toString()).getHours(), rs.getTime(column_name.toString()).getMinutes(), rs.getTime(column_name.toString()).getSeconds());
							else if(cell_val instanceof Timestamp)
								value = new TimestampValue(rs.getTimestamp(column_name.toString()).hashCode());
							else if(cell_val instanceof Boolean)
								value = new BooleanValue(rs.getBoolean(column_name.toString()));
							else if(rs.getObject(column_name.toString()) == null)
								value = null;

							Cell cell = null;
							if (value == null) {
								cell = new Cell(column_name, new ValueFactory());
								cell.setNull(true);
								cells.add(cell);
							} else {
								cell = new Cell(column_name, new ValueFactory());
								cell.setValue(value);
								cells.add(cell);
							}
						}
						Row nw_row = new Row(table, cells);
						if(!this.checkDuplicate(table, nw_row)) 
							data.addRow(table, nw_row);
					}
				}
				connectedTable = false;
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public boolean checkDuplicate(Table table, Row row) {
		boolean itIsAduplicate = false;
		if (data.getNumRows(table) > 0) {
			for (Row r : data.getRows(table)) {
				List<Value> startingValues = new ArrayList<Value>();
				
				for (Cell c : row.getCells()) {
					startingValues.add(c.getValue());
				}
				
				List<Value> comparableValues = new ArrayList<Value>();

				for (Cell c : r.getCells()) {
					comparableValues.add(c.getValue());
				}
				
				if (startingValues.equals(comparableValues)) {
					itIsAduplicate = true;
				}
			}
		}
		
		return itIsAduplicate;
		
	}
	
	
	
	public static Data removeDataDuplicate(Data data) {
		Data newData = new Data();
		
		for (Table table : data.getTables()) {
			List<Row> rows = data.getRows(table);
			Set<Row> s = new LinkedHashSet<>(rows);
			List<Row> newList = new ArrayList<Row>(s);
			newData.addRows(table, newList);
		}

		return newData;
	}
	
	public  boolean equalLists(List<Boolean> a, List<Boolean> b){     
	    // Check for sizes and nulls
	    if (a == null && b == null) return false;
	    if (a.size() == 0 && b.size() == 0) return false;
		System.out.println("HERE");
	    if ((a.size() != b.size()) || (a == null && b!= null) || (a != null && b== null)){
	        return false;
	    }
	    // Sort and compare the two lists
	    int numberOfATrue = 0;
	    int numberOfBTrue = 0;
	    int numberOfAFalse = 0;
	    int numberOfBFalse = 0;

	    for (Boolean bo : a)
	    	if (bo == true)
	    		numberOfATrue++;
	    	else
	    		numberOfAFalse++;
	    
	    for (Boolean bo : b)
	    	if (bo == true)
	    		numberOfBTrue++;
	    	else
	    		numberOfBFalse++;

	    return numberOfATrue == numberOfBTrue && numberOfAFalse == numberOfBFalse;
	}
	
	public int getNumberOfTables() {
		return data.getTables().size();
	}
	
	public List<Row> getTableRows(int index) {
		return data.getRows(data.getTables().get(index));
	}
	
	// List<Column>
	public List<Column> returnNotNullColumns(int table_index) {
		List<Column> nulls = new ArrayList<Column>();
		Table table = data.getTables().get(table_index);
		for (Column col : table.getColumns()) {
			if (schema.isNotNull(table, col)) {
				System.out.println(col);
				nulls.add(col);
			}
		}
		
		return nulls;
		
	}
	
	public List<Column> returnUniqueColumns(int table_index) {
		List<Column> nulls = new ArrayList<Column>();
		Table table = data.getTables().get(table_index);
		for (Column col : table.getColumns()) {
			if (schema.isUnique(table, col)) {
				System.out.println(col);
				nulls.add(col);
			}
		}
		
		return nulls;
		
	}
	
	public List<Row> getTableColumnData(Table table, Column column) {
		return data.getRows(table, column);
	}
	
	public Table getTable(String table) {
		Table tab = null;
		for (Table tbl : data.getTables())
			if (tbl.toString().equals(table))
				tab = tbl;
		return tab;
	}
	
	public Column getColumn(Table table, String col) {
		Column column = null;
		for (Table tbl : data.getTables())
			if (tbl.equals(table) && tbl.hasColumn(col))
				column = tbl.getColumn(col);
		return column;
	}
	
	public Data getData() {
		return this.data;
	}
	
	public Schema getSchema() {
		return this.schema;
	}

	public Data mapStateData(Table table) {
		Data thisData = new Data();
		Statement stmt = null;
		List<Table> tbls = schema.getConnectedTables(table);
		tbls.add(table);
		try {
			boolean connectedTable = false;
			for (Table tbl : tbls) {
				List<Column> schema_columns = tbl.getColumns();
				String columns = Joiner.on(",").join(schema_columns);
				
				List<String> lstOfSelectStms = this.getSelectStatements(thisData, tbl, connectedTable, columns);
				// Mapper
				for (String stm : lstOfSelectStms) {
					c.setAutoCommit(false);
					//System.out.println(stm);
					stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery(stm);
					while (rs.next()) {
						List<Cell> cells = new ArrayList<Cell>();
						for (Column column_name: schema_columns) {
							
							Value value = null;
							
							Object cell_val = rs.getObject(column_name.toString());
	
							if (cell_val instanceof String || cell_val instanceof Character) {
								StringValue stringvalue = new StringValue();
								stringvalue.setCharacterRange(32, 126);
								stringvalue.set(rs.getString(column_name.toString()));
								value = stringvalue;
							} else if(cell_val instanceof Integer)
								value = new NumericValue(rs.getInt(column_name.toString()));
							else if(cell_val instanceof Double && column_name.getDataType() instanceof RealDataType)
								value = new NumericValue(new BigDecimal(rs.getFloat(column_name.toString())));
							else if(cell_val instanceof Long)
								value = new NumericValue(new BigDecimal(rs.getLong(column_name.toString())));
							else if(cell_val instanceof Double &&  !(column_name.getDataType() instanceof RealDataType))
								value = new NumericValue(rs.getBigDecimal(column_name.toString()));
							else if(cell_val instanceof Date)
								value = new DateTimeValue(rs.getDate(column_name.toString()).getYear(), rs.getDate(column_name.toString()).getMonth(), rs.getDate(column_name.toString()).getDay(), rs.getDate(column_name.toString()).getHours(), rs.getDate(column_name.toString()).getMinutes(), rs.getDate(column_name.toString()).getSeconds());
							else if(cell_val instanceof Time)
								value = new TimeValue(rs.getTime(column_name.toString()).getHours(), rs.getTime(column_name.toString()).getMinutes(), rs.getTime(column_name.toString()).getSeconds());
							else if(cell_val instanceof Timestamp)
								value = new TimestampValue(rs.getTimestamp(column_name.toString()).hashCode());
							else if(cell_val instanceof Boolean)
								value = new BooleanValue(rs.getBoolean(column_name.toString()));
							else if(rs.getObject(column_name.toString()) == null)
								value = null;
	
							Cell cell = null;
							if (value == null) {
								cell = new Cell(column_name, new ValueFactory());
								cell.setNull(true);
								cells.add(cell);
							} else {
								cell = new Cell(column_name, new ValueFactory());
								cell.setValue(value);
								cells.add(cell);
							}
						}
						Row nw_row = new Row(tbl, cells);
						if(!this.checkDuplicate(tbl, nw_row)) 
							thisData.addRow(tbl, nw_row);
					}
				}
				connectedTable = false;
			}
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return thisData;
	}

	
	public List<String> getSelectStatements(Data thisData, Table table, boolean connectedTable, String columns) {
		// Getting list of FKs
		List<ForeignKeyConstraint> fks = schema.getForeignKeyConstraints(table);
		List<String> lstOfSelectStms = new ArrayList<String>();
		if (fks.size() > 0) {
			connectedTable = true;
		}
		if (connectedTable) {
			String aStm = "SELECT "+ columns +" FROM "+ table.toString() +" ORDER BY RANDOM() LIMIT 1;";
			lstOfSelectStms.add(aStm);
		} else {
			// Get former tables info
			if (thisData.getTables().size() != 0) {
				for (Table t : thisData.getTables()) {
					// Get table info from schema
					Table tbl = schema.getTable(t.getName());
					// get Foreign Keys for this table
					List<ForeignKeyConstraint> forKeys = schema.getForeignKeyConstraints(tbl);
					// for each foreign key make a statement.
					if (forKeys.size() > 0) {
						for (ForeignKeyConstraint f : forKeys) {
							if (tbl.equals(f.getTable()) && table.equals(f.getReferenceTable())) {
								for (Pair<Column> c : f.getColumnPairs()) {
									for (Cell cl : thisData.getCells(tbl, c.getFirst())) {
										String condition = c.getSecond().getName() + " = " + cl.getValue(); 
										if (cl.getValue() instanceof StringValue) {
											StringValue newVal = (StringValue)cl.getValue();
											condition = c.getSecond().getName() + " = '" + newVal.get() + "'"; 
										}
										String aStm = "SELECT "+ columns +" FROM "+ table.toString() +" WHERE " + condition + ";";
										lstOfSelectStms.add(aStm);								}
								}
							}
						}
					} else {
						String aStm = "SELECT "+ columns +" FROM "+ table.toString() +" ORDER BY RANDOM() LIMIT 1;";
						lstOfSelectStms.add(aStm);
					}
				}
			} else {
				String aStm = "SELECT "+ columns +" FROM "+ table.toString() +" ORDER BY RANDOM() LIMIT 1;";
				lstOfSelectStms.add(aStm);
			}
		}
		return lstOfSelectStms;
	}
	
	public Data returnPerfectState(Table table) {
		Data newData = new Data();		
		List<Table> tbls = schema.getConnectedTables(table);		
		
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(data.getRows(table).size());
		
		Row firstRow = data.getRows(table).get(index);

		for (ForeignKeyConstraint fk : schema.getForeignKeyConstraints(table)) {
			for (Pair<Column> pair_c : fk.getColumnPairs()) {
				for (Table tbl : tbls) {
					if (tbl.hasColumn(pair_c.getFirst())) {
						Column refColunm = tbl.getColumn(pair_c.getSecond().toString());
						for (Row row : data.getRows(tbl)) {
							if (row.getCell(refColunm).getValue().equals(firstRow.getCell(pair_c.getSecond()).getValue())) {
								newData.addRow(tbl, row);
							}
						}
					}
				}
			}
		}
		newData.addRow(table, firstRow);
		return newData;
		
	}
	
	public Data returnStatedTable(Table table) {
		Data dataA = new Data();
		dataA.addRows(table, newData.getRows(table));

		return dataA;
	}
	

	public Data returnPerfectRandomState(Table table) {
		Data newData = new Data();
		Random randomGenerator = new Random();
				
		int index = randomGenerator.nextInt(data.getRows(table).size());
		Row firstRow = data.getRows(table).get(index);
		newData.addRow(table, firstRow);

		return newData;
		
	}

	private Data newData;
	public void returnPerfectoState(Table table) {
		newData = new Data();
		
		List<Table> tbls = schema.getConnectedTables(table);
		
		Random randomGenerator = new Random();
		if (data.getRows(table).size() > 0) {
			int index = randomGenerator.nextInt(data.getRows(table).size());
			Row firstRow = data.getRows(table).get(index);
			List<ForeignKeyConstraint> keys = schema.getForeignKeyConstraints(table);
			for (ForeignKeyConstraint fk : schema.getForeignKeyConstraints(table)) {
				int size = fk.getReferenceColumns().size();
				int counter = 0;
				// mutlti FKs from another table
				if (size > 1) {
					Data tmpData = new Data();
					for (Pair<Column> pair_c : fk.getColumnPairs()) {
						for (Table tbl : tbls) {
							if (tbl.hasColumn(pair_c.getSecond())) {
								Column refColunm = tbl.getColumn(pair_c.getSecond().toString());
								//List<Row> rows = data.getRows(tbl);
								if (counter == 0) {
									for (Row row : data.getRows(tbl)) {
										//Value v1 = row.getCell(refColunm).getValue();
										//Value v2 = firstRow.getCell(pair_c.getFirst()).getValue();
										if (row.getCell(refColunm).getValue().equals(firstRow.getCell(pair_c.getFirst()).getValue())) {
											tmpData.addRow(tbl, row);
										}
									}
									counter++;
								} else {
									for (Row row : tmpData.getRows(tbl)) {
										//Value v1 = row.getCell(refColunm).getValue();
										//Value v2 = firstRow.getCell(pair_c.getFirst()).getValue();
										if (row.getCell(refColunm).getValue().equals(firstRow.getCell(pair_c.getFirst()).getValue())) {
											newData.addRow(tbl, row);
										}
									}
									counter++;
								}
							}
						}
					}
					
				} else {
					for (Pair<Column> pair_c : fk.getColumnPairs()) {
						for (Table tbl : tbls) {
							if (tbl.hasColumn(pair_c.getSecond())) {
								Column refColunm = tbl.getColumn(pair_c.getSecond().toString());
								//List<Row> rows = data.getRows(tbl);
								for (Row row : data.getRows(tbl)) {
									//Value v1 = row.getCell(refColunm).getValue();
									//Value v2 = firstRow.getCell(pair_c.getFirst()).getValue();
									if (row.getCell(refColunm).getValue().equals(firstRow.getCell(pair_c.getFirst()).getValue())) {
										newData.addRow(tbl, row);
									}
								}
							}
						}
					}
				}
			}
			newData.addRow(table, firstRow);
		}
		//return newData;
	}
	
	public Data returnState(Table table) {
		Data returnedData = new Data();
		
		for (Table tbl : newData.getTables()) {
			if (!(table.toString().equals(tbl.toString()))) {
				returnedData.addRows(tbl, newData.getRows(tbl));
				//System.out.println("TABLE > " + table + " != STATE TABLE > " + tbl);
			}
		}
		//newData.addRow(table, firstRow);
		return returnedData;
	}
	
	public Data returnTableState(Table table) {
		Data returnedData = new Data();
		returnedData.addRows(table, newData.getRows(table));

		//newData.addRow(table, firstRow);
		return returnedData;
	}
	
	public Data returnData(Table table) {
		Data returnedData = new Data();
		
		for (Table tbl : newData.getTables()) {
			if (table.toString().equals(tbl.toString())) {
				returnedData.addRows(tbl, newData.getRows(tbl));
				//System.out.println("TABLE > " + table + " == DATA TABLE > " + tbl);
			}
		}
		
		if (returnedData.getCells().isEmpty()) {
			if (data.getNumRows(table) > 0) {
				Random randomGenerator = new Random();
				int index = randomGenerator.nextInt(data.getRows(table).size());
				returnedData.addRow(table, data.getRows(table).get(index));
			}
		}
		//newData.addRow(table, firstRow);
		//System.out.println("RETURED STATE === > " + new);
		return returnedData;
	}
}
