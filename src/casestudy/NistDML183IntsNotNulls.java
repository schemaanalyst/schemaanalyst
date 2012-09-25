package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;

public class NistDML183IntsNotNulls extends Schema {

    static final long serialVersionUID = -8495866472648525553L;
	
	public NistDML183IntsNotNulls() {
		super("NistDML183IntsNotNulls");
		
		/*
		  
		  CREATE TABLE T 
		  (
		  A CHAR, B CHAR, C CHAR,
		  CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
		  );

		  NOTE: Add NOT NULLs for everyone.
		  
		  NOTE: Uses INTs instead of CHAR for everyone.

		  NOTE: for Postgres, a CHAR is the same as CHAR(1)!

		*/

		Table tTable = createTable( "T");

		Column a = tTable.addColumn("A" , new IntColumnType());
		Column b = tTable.addColumn("B" , new IntColumnType());
		Column c = tTable.addColumn("C" , new IntColumnType());

		a.setNotNull();
		b.setNotNull();
		c.setNotNull();

		tTable.addUniqueConstraint(a, b);		   

		/*

		  CREATE TABLE S 
		  (
		  X CHAR, Y CHAR, Z CHAR,
		  CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y)
		  REFERENCES T (A, B)
		  );

		  Change everthing to INTs.

		  Add NOT NULLs for everyone.

		 */

		Table sTable = createTable( "S");
		
		Column x = sTable.addColumn("X" , new IntColumnType());
		Column y = sTable.addColumn("Y" , new IntColumnType());
		Column z = sTable.addColumn("Z" , new IntColumnType());

		x.setNotNull();
		y.setNotNull();
		z.setNotNull();
		sTable.addForeignKeyConstraint(tTable, x, y, a, b);
	}
}
