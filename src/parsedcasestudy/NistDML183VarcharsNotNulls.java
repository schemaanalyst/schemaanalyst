package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * NistDML183VarcharsNotNulls schema.
 * Java code originally generated: 2013/07/11 14:10:21
 *
 */
@SuppressWarnings("serial")
public class NistDML183VarcharsNotNulls extends Schema {

    public NistDML183VarcharsNotNulls() {
        super("NistDML183VarcharsNotNulls");

        Table tableT = this.createTable("T");
        tableT.addColumn("A", new VarCharDataType(10));
        tableT.addColumn("B", new VarCharDataType(10));
        tableT.addColumn("C", new VarCharDataType(10));
        tableT.addNotNullConstraint(tableT.getColumn("A"));
        tableT.addNotNullConstraint(tableT.getColumn("B"));
        tableT.addNotNullConstraint(tableT.getColumn("C"));
        tableT.addUniqueConstraint("UniqueOnColsAandB", tableT.getColumn("A"), tableT.getColumn("B"));

        Table tableS = this.createTable("S");
        tableS.addColumn("X", new VarCharDataType(10));
        tableS.addColumn("Y", new VarCharDataType(10));
        tableS.addColumn("Z", new VarCharDataType(10));
        tableS.addForeignKeyConstraint("RefToColsAandB", tableS.getColumn("X"), tableS.getColumn("Y"), tableT, tableT.getColumn("A"), tableT.getColumn("B"));
        tableS.addNotNullConstraint(tableS.getColumn("X"));
        tableS.addNotNullConstraint(tableS.getColumn("Y"));
        tableS.addNotNullConstraint(tableS.getColumn("Z"));
    }
}
