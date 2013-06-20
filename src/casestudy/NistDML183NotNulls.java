package casestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;

public class NistDML183NotNulls extends Schema {

    static final long serialVersionUID = -7030438231918624311L;
	
	public NistDML183NotNulls() {
		super("NistDML183NotNulls");
		
		/*
		  
		  CREATE TABLE T 
		  (
		  A CHAR, B CHAR, C CHAR,
		  CONSTRAINT UniqueOnColsAandB UNIQUE (A, B)
		  );

		  NOTE: Add NOT NULLs for everyone.

		  NOTE: for Postgres, a CHAR is the same as CHAR(1)!

		*/

		Table tTable = createTable("T");

		Column a = tTable.addColumn("A" , new CharDataType(1));
		Column b = tTable.addColumn("B" , new CharDataType(1));
		Column c = tTable.addColumn("C" , new CharDataType(1));

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

		  Add NOT NULLs for everyone.

		 */

		Table sTable = createTable("S");
		
		Column x = sTable.addColumn("X" , new CharDataType(1));
		Column y = sTable.addColumn("Y" , new CharDataType(1));
		Column z = sTable.addColumn("Z" , new CharDataType(1));

		x.setNotNull();
		y.setNotNull();
		z.setNotNull();

		sTable.addForeignKeyConstraint(tTable, x, y, a, b);
	}
}
