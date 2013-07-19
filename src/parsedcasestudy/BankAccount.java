package BankAccount;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * BankAccount schema.
 * Java code originally generated: 2013/07/19 13:47:34
 *
 */

@SuppressWarnings("serial")
public class BankAccount extends Schema {

	public BankAccount() {
		super("BankAccount");

		Table tableUserinfo = this.createTable("UserInfo");
		tableUserinfo.addColumn("card_number", new IntDataType());
		tableUserinfo.addColumn("pin_number", new IntDataType());
		tableUserinfo.addColumn("user_name", new VarCharDataType(50));
		tableUserinfo.addColumn("acct_lock", new IntDataType());
		tableUserinfo.setPrimaryKeyConstraint(tableUserinfo.getColumn("card_number"));
		tableUserinfo.addNotNullConstraint(tableUserinfo.getColumn("pin_number"));
		tableUserinfo.addNotNullConstraint(tableUserinfo.getColumn("user_name"));

		Table tableAccount = this.createTable("Account");
		tableAccount.addColumn("id", new IntDataType());
		tableAccount.addColumn("account_name", new VarCharDataType(50));
		tableAccount.addColumn("user_name", new VarCharDataType(50));
		tableAccount.addColumn("balance", new IntDataType());
		tableAccount.addColumn("card_number", new IntDataType());
		tableAccount.setPrimaryKeyConstraint(tableAccount.getColumn("id"));
		tableAccount.addForeignKeyConstraint(tableAccount.getColumn("card_number"), tableUserinfo, tableUserinfo.getColumn("card_number"));
		tableAccount.addNotNullConstraint(tableAccount.getColumn("account_name"));
		tableAccount.addNotNullConstraint(tableAccount.getColumn("user_name"));
		tableAccount.addNotNullConstraint(tableAccount.getColumn("card_number"));
	}
}

