package casestudy;

import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class Cloc extends Schema {

    static final long serialVersionUID = 8043309076732264842L;    
	
	public Cloc() {
		super("Cloc");
		
		/*
		  CREATE TABLE metadata 
		  (          
			  timestamp varchar(50),    
			  Project   varchar(50),    
			  elapsed_s int
		  );
		*/		
		
		Table metadataTable = createTable("metadata");
		
		metadataTable.addColumn("timestamp", new VarCharColumnType(50));
		metadataTable.addColumn("Project", new VarCharColumnType(50));
		metadataTable.addColumn("elapsed_s", new IntColumnType());		
		
		/*

		  CREATE TABLE t        
		  (
			  Project   varchar(50),  
			  Language  varchar(50),  
			  File      varchar(50),  
			  nBlank    integer,  
			  nComment  integer,  
			  nCode     integer,  
			  nScaled   int   
		  );

		*/

		Table tTable = createTable("t");
		
		tTable.addColumn("Project", new VarCharColumnType(50));
		tTable.addColumn("Language", new VarCharColumnType(50));
		tTable.addColumn("File", new VarCharColumnType(50));
		
		tTable.addColumn("nBlank", new IntegerColumnType());		
		tTable.addColumn("nComment", new IntegerColumnType());		
		tTable.addColumn("nCode", new IntegerColumnType());		
		tTable.addColumn("nScale", new IntColumnType());		
	}
}
