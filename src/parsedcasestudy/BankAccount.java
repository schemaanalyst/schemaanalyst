package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * BankAccount schema.
 * Java code originally generated: 2013/07/11 13:20:58
 *
 */
@SuppressWarnings("serial")
public class BankAccount extends Schema {

    public BankAccount() {
        super("BankAccount");

        Table tableUserInfo = this.createTable("UserInfo");
        tableUserInfo.addColumn("card_number", new IntDataType());
        tableUserInfo.addColumn("pin_number", new IntDataType());
        tableUserInfo.addColumn("user_name", new VarCharDataType(50));
        tableUserInfo.addColumn("acct_lock", new IntDataType());
        tableUserInfo.setPrimaryKeyConstraint(tableUserInfo.getColumn("card_number"));
        tableUserInfo.addNotNullConstraint(tableUserInfo.getColumn("pin_number"));
        tableUserInfo.addNotNullConstraint(tableUserInfo.getColumn("user_name"));

        Table tableAccount = this.createTable("Account");
        tableAccount.addColumn("id", new IntDataType());
        tableAccount.addColumn("account_name", new VarCharDataType(50));
        tableAccount.addColumn("user_name", new VarCharDataType(50));
        tableAccount.addColumn("balance", new IntDataType());
        tableAccount.addColumn("card_number", new IntDataType());
        tableAccount.setPrimaryKeyConstraint(tableAccount.getColumn("id"));
        tableAccount.addForeignKeyConstraint(tableAccount.getColumn("card_number"), tableUserInfo, tableUserInfo.getColumn("card_number"));
        tableAccount.addNotNullConstraint(tableAccount.getColumn("account_name"));
        tableAccount.addNotNullConstraint(tableAccount.getColumn("user_name"));
        tableAccount.addNotNullConstraint(tableAccount.getColumn("card_number"));
    }
}
