package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * BankAccount schema.
 * Java code originally generated: 2013/08/17 00:30:25
 *
 */

@SuppressWarnings("serial")
public class BankAccount extends Schema {

	public BankAccount() {
		super("BankAccount");

		Table tableUserinfo = this.createTable("UserInfo");
		tableUserinfo.createColumn("card_number", new IntDataType());
		tableUserinfo.createColumn("pin_number", new IntDataType());
		tableUserinfo.createColumn("user_name", new VarCharDataType(50));
		tableUserinfo.createColumn("acct_lock", new IntDataType());
		this.createPrimaryKeyConstraint(tableUserinfo, tableUserinfo.getColumn("card_number"));
		this.createNotNullConstraint(tableUserinfo, tableUserinfo.getColumn("pin_number"));
		this.createNotNullConstraint(tableUserinfo, tableUserinfo.getColumn("user_name"));

		Table tableAccount = this.createTable("Account");
		tableAccount.createColumn("id", new IntDataType());
		tableAccount.createColumn("account_name", new VarCharDataType(50));
		tableAccount.createColumn("user_name", new VarCharDataType(50));
		tableAccount.createColumn("balance", new IntDataType());
		tableAccount.createColumn("card_number", new IntDataType());
		this.createPrimaryKeyConstraint(tableAccount, tableAccount.getColumn("id"));
		this.createForeignKeyConstraint(tableAccount, tableAccount.getColumn("card_number"), tableUserinfo, tableUserinfo.getColumn("card_number"));
		this.createNotNullConstraint(tableAccount, tableAccount.getColumn("account_name"));
		this.createNotNullConstraint(tableAccount, tableAccount.getColumn("user_name"));
		this.createNotNullConstraint(tableAccount, tableAccount.getColumn("card_number"));
	}
}

