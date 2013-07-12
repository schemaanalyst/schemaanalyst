package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * NistDML183Varchars schema.
 * Java code originally generated: 2013/07/11 14:10:17
 *
 */
@SuppressWarnings("serial")
public class NistDML183Varchars extends Schema {

    public NistDML183Varchars() {
        super("NistDML183Varchars");

        Table tableT = this.createTable("T");
        tableT.addColumn("A", new VarCharDataType(1));
        tableT.addColumn("B", new VarCharDataType(1));
        tableT.addColumn("C", new VarCharDataType(1));
        tableT.addUniqueConstraint("UniqueOnColsAandB", tableT.getColumn("A"), tableT.getColumn("B"));

        Table tableS = this.createTable("S");
        tableS.addColumn("X", new VarCharDataType(1));
        tableS.addColumn("Y", new VarCharDataType(1));
        tableS.addColumn("Z", new VarCharDataType(1));
        tableS.addForeignKeyConstraint("RefToColsAandB", tableS.getColumn("X"), tableS.getColumn("Y"), tableT, tableT.getColumn("A"), tableT.getColumn("B"));
    }
}
