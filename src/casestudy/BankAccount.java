package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

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
		
		Column userInfoCardNumber = userInfo.addColumn("card_number", new IntColumnType());
		// GMK NOTE: Postgres, Hsqldb, and Derby do not 
		// handle easily out of the box 
		//userInfoCardNumber.addAttribute(new Identity());
		userInfoCardNumber.setPrimaryKey();
		
		Column pinNumber = userInfo.addColumn("pin_number", new IntColumnType());
		pinNumber.setNotNull();
		
		Column userName = userInfo.addColumn("user_name", new VarCharColumnType(50));
		userName.setNotNull();
		
		userInfo.addColumn("acct_lock", new IntColumnType());
		
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
		
		Column id = account.addColumn("id", new IntColumnType());
		
		// GMK NOTE: Postgres, Hsqldb, and Derby do not 
		// handle easily out of the box 
		//id.addAttribute(new Identity());
		id.setPrimaryKey();
		
		Column accountName = account.addColumn("account_name", new VarCharColumnType(50));
		accountName.setNotNull();
		
        Column accountUserName = account.addColumn("user_name", new VarCharColumnType(50));
        accountUserName.setNotNull();
                
		Column balance = account.addColumn("balance", new IntColumnType());
		
		// PSM: commented this out as we're not using
		//balance.setDefault(0);
		
		Column accountCardNumber = account.addColumn("card_number", new IntColumnType());
		accountCardNumber.setNotNull();
		accountCardNumber.setForeignKey(userInfo, userInfoCardNumber);
	}
}
