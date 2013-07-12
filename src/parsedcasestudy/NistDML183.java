package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;

/*
 * NistDML183 schema.
 * Java code originally generated: 2013/07/11 14:09:55
 *
 */
@SuppressWarnings("serial")
public class NistDML183 extends Schema {

    public NistDML183() {
        super("NistDML183");

        Table tableT = this.createTable("T");
        tableT.addColumn("A", new CharDataType());
        tableT.addColumn("B", new CharDataType());
        tableT.addColumn("C", new CharDataType());
        tableT.addUniqueConstraint("UniqueOnColsAandB", tableT.getColumn("A"), tableT.getColumn("B"));

        Table tableS = this.createTable("S");
        tableS.addColumn("X", new CharDataType());
        tableS.addColumn("Y", new CharDataType());
        tableS.addColumn("Z", new CharDataType());
        tableS.addForeignKeyConstraint("RefToColsAandB", tableS.getColumn("X"), tableS.getColumn("Y"), tableT, tableT.getColumn("A"), tableT.getColumn("B"));
    }
}
