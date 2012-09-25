package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;

public class NistDML183Ints extends Schema {

    static final long serialVersionUID = -755191113081122410L;
	
	@SuppressWarnings("unused")
	public NistDML183Ints() {
		super("NistDML183Ints");
		
		/*
		  
		  CREATE TABLE T 
		  (
		  A CHAR, B CHAR, C CHAR,
		  CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
		  );

		  NOTE: Uses INTs instead of CHAR for everyone.

		*/

		Table tTable = createTable( "T");

		Column a = tTable.addColumn("A" , new IntColumnType());
		Column b = tTable.addColumn("B" , new IntColumnType());
		Column c = tTable.addColumn("C" , new IntColumnType());

		tTable.addUniqueConstraint(a, b);		   

		/*

		  CREATE TABLE S 
		  (
		  X CHAR, Y CHAR, Z CHAR,
		  CONSTRAINT RefToColsAandB FOREIGN KEY (X, Y)
		  REFERENCES T (A, B)
		  );

		  Change everthing to INTs.

		 */

		Table sTable = createTable( "S");
		
		Column x = sTable.addColumn("X" , new IntColumnType());
		Column y = sTable.addColumn("Y" , new IntColumnType());
		Column z = sTable.addColumn("Z" , new IntColumnType());

                sTable.addForeignKeyConstraint(tTable, x, y, a, b);
	}
}
