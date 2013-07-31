package parsedcasestudy;

import java.util.Arrays;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * NistDML182NotNulls schema.
 * Java code originally generated: 2013/07/31 19:56:20
 *
 */

@SuppressWarnings("serial")
public class NistDML182NotNulls extends Schema {

	public NistDML182NotNulls() {
		super("NistDML182NotNulls");

		Table tableIdCodes = this.createTable("ID_CODES");
		tableIdCodes.addColumn("CODE1", new IntDataType());
		tableIdCodes.addColumn("CODE2", new IntDataType());
		tableIdCodes.addColumn("CODE3", new IntDataType());
		tableIdCodes.addColumn("CODE4", new IntDataType());
		tableIdCodes.addColumn("CODE5", new IntDataType());
		tableIdCodes.addColumn("CODE6", new IntDataType());
		tableIdCodes.addColumn("CODE7", new IntDataType());
		tableIdCodes.addColumn("CODE8", new IntDataType());
		tableIdCodes.addColumn("CODE9", new IntDataType());
		tableIdCodes.addColumn("CODE10", new IntDataType());
		tableIdCodes.addColumn("CODE11", new IntDataType());
		tableIdCodes.addColumn("CODE12", new IntDataType());
		tableIdCodes.addColumn("CODE13", new IntDataType());
		tableIdCodes.addColumn("CODE14", new IntDataType());
		tableIdCodes.addColumn("CODE15", new IntDataType());
		tableIdCodes.setPrimaryKeyConstraint(tableIdCodes.getColumn("CODE1"), tableIdCodes.getColumn("CODE2"), tableIdCodes.getColumn("CODE3"), tableIdCodes.getColumn("CODE4"), tableIdCodes.getColumn("CODE5"), tableIdCodes.getColumn("CODE6"), tableIdCodes.getColumn("CODE7"), tableIdCodes.getColumn("CODE8"), tableIdCodes.getColumn("CODE9"), tableIdCodes.getColumn("CODE10"), tableIdCodes.getColumn("CODE11"), tableIdCodes.getColumn("CODE12"), tableIdCodes.getColumn("CODE13"), tableIdCodes.getColumn("CODE14"), tableIdCodes.getColumn("CODE15"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE1"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE2"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE3"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE4"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE5"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE6"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE7"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE8"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE9"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE10"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE11"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE12"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE13"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE14"));
		tableIdCodes.addNotNullConstraint(tableIdCodes.getColumn("CODE15"));

		Table tableOrders = this.createTable("ORDERS");
		tableOrders.addColumn("CODE1", new IntDataType());
		tableOrders.addColumn("CODE2", new IntDataType());
		tableOrders.addColumn("CODE3", new IntDataType());
		tableOrders.addColumn("CODE4", new IntDataType());
		tableOrders.addColumn("CODE5", new IntDataType());
		tableOrders.addColumn("CODE6", new IntDataType());
		tableOrders.addColumn("CODE7", new IntDataType());
		tableOrders.addColumn("CODE8", new IntDataType());
		tableOrders.addColumn("CODE9", new IntDataType());
		tableOrders.addColumn("CODE10", new IntDataType());
		tableOrders.addColumn("CODE11", new IntDataType());
		tableOrders.addColumn("CODE12", new IntDataType());
		tableOrders.addColumn("CODE13", new IntDataType());
		tableOrders.addColumn("CODE14", new IntDataType());
		tableOrders.addColumn("CODE15", new IntDataType());
		tableOrders.addColumn("TITLE", new VarCharDataType(80));
		tableOrders.addColumn("COST", new NumericDataType(5, 2));
		tableOrders.addForeignKeyConstraint(Arrays.asList(tableOrders.getColumn("CODE1"), tableOrders.getColumn("CODE2"), tableOrders.getColumn("CODE3"), tableOrders.getColumn("CODE4"), tableOrders.getColumn("CODE5"), tableOrders.getColumn("CODE6"), tableOrders.getColumn("CODE7"), tableOrders.getColumn("CODE8"), tableOrders.getColumn("CODE9"), tableOrders.getColumn("CODE10"), tableOrders.getColumn("CODE11"), tableOrders.getColumn("CODE12"), tableOrders.getColumn("CODE13"), tableOrders.getColumn("CODE14"), tableOrders.getColumn("CODE15")), tableIdCodes, Arrays.asList(tableIdCodes.getColumn("CODE1"), tableIdCodes.getColumn("CODE2"), tableIdCodes.getColumn("CODE3"), tableIdCodes.getColumn("CODE4"), tableIdCodes.getColumn("CODE5"), tableIdCodes.getColumn("CODE6"), tableIdCodes.getColumn("CODE7"), tableIdCodes.getColumn("CODE8"), tableIdCodes.getColumn("CODE9"), tableIdCodes.getColumn("CODE10"), tableIdCodes.getColumn("CODE11"), tableIdCodes.getColumn("CODE12"), tableIdCodes.getColumn("CODE13"), tableIdCodes.getColumn("CODE14"), tableIdCodes.getColumn("CODE15")));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE1"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE2"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE3"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE4"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE5"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE6"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE7"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE8"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE9"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE10"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE11"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE12"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE13"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE14"));
		tableOrders.addNotNullConstraint(tableOrders.getColumn("CODE15"));
	}
}

