package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.CharColumnType;

public class NistDML183 extends Schema {

    static final long serialVersionUID = 810761207803357885L;
	
	@SuppressWarnings("unused")
	public NistDML183() {
		super("NistDML183");
		
		/*
		  
		  CREATE TABLE T 
		  (
		  A CHAR, B CHAR, C CHAR,
		  CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
		  );

		  NOTE: for Postgres, a CHAR is the same as CHAR(1)!

		*/

		Table tTable = createTable( "T");

		Column a = tTable.addColumn("A" , new CharColumnType(1));
		Column b = tTable.addColumn("B" , new CharColumnType(1));
		Column c = tTable.addColumn("C" , new CharColumnType(1));
		
		tTable.addUniqueConstraint(a, b);		   

		/*

		  CREATE TABLE S 
		  (
		  X CHAR, Y CHAR, Z CHAR,
		  CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y)
		  REFERENCES T (A, B)
		  );

		 */

		Table sTable = createTable( "S");
		
		Column x = sTable.addColumn("X" , new CharColumnType(1));
		Column y = sTable.addColumn("Y" , new CharColumnType(1));
		Column z = sTable.addColumn("Z" , new CharColumnType(1));

		sTable.addForeignKeyConstraint(tTable, x, y, a, b);
	}
}
