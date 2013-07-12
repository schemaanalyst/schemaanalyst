package originalcasestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

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

        Table tTable = createTable("T");

        Column a = tTable.addColumn("A", new IntDataType());
        Column b = tTable.addColumn("B", new IntDataType());
        Column c = tTable.addColumn("C", new IntDataType());

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

        Table sTable = createTable("S");

        Column x = sTable.addColumn("X", new IntDataType());
        Column y = sTable.addColumn("Y", new IntDataType());
        Column z = sTable.addColumn("Z", new IntDataType());

        sTable.addForeignKeyConstraint(x, y, tTable, a, b);
    }
}
