package originalcasestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

public class BankAccount extends Schema {

    private static final long serialVersionUID = 954684181133043531L;

    public BankAccount() {
        super("BankAccount");

        /*
         CREATE TABLE UserInfo 
         (
         card_number int IDENTITY(1,1) PRIMARY KEY, 
         pin_number int not null, 
         user_name varchar(50) not null, 
         acct_lock int
         );
         */

        Table userInfo = createTable("UserInfo");

        Column userInfoCardNumber = userInfo.addColumn("card_number", new IntDataType());
        // GMK NOTE: Postgres, Hsqldb, and Derby do not 
        // handle easily out of the box 
        //userInfoCardNumber.addAttribute(new Identity());
        userInfoCardNumber.setPrimaryKey();

        Column pinNumber = userInfo.addColumn("pin_number", new IntDataType());
        pinNumber.setNotNull();

        Column userName = userInfo.addColumn("user_name", new VarCharDataType(50));
        userName.setNotNull();

        userInfo.addColumn("acct_lock", new IntDataType());

        /*
         CREATE TABLE Account 
         (
         id int IDENTITY(1,1) PRIMARY KEY, 
         account_name varchar(50) not null, 
         user_name varchar(50) not null, 
         balance int default 0, 
         card_number int not null, 
         foreign key(card_number) references UserInfo(card_number)
         );
         */

        Table account = createTable("Account");

        Column id = account.addColumn("id", new IntDataType());

        // GMK NOTE: Postgres, Hsqldb, and Derby do not 
        // handle easily out of the box 
        //id.addAttribute(new Identity());
        id.setPrimaryKey();

        Column accountName = account.addColumn("account_name", new VarCharDataType(50));
        accountName.setNotNull();

        Column accountUserName = account.addColumn("user_name", new VarCharDataType(50));
        accountUserName.setNotNull();

        account.addColumn("balance", new IntDataType());

        Column accountCardNumber = account.addColumn("card_number", new IntDataType());
        accountCardNumber.setNotNull();
        accountCardNumber.setForeignKey(userInfo, userInfoCardNumber);
    }
}
