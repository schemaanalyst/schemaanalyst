package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.CharColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.SmallIntColumnType;

public class RiskIt extends Schema {

    static final long serialVersionUID = 7931979200641606242L;
	
    @SuppressWarnings("unused")
	public RiskIt() {
		super("RiskIt");
		
		/*

		  CREATE TABLE ziptable 
		  ( 
		  ZIP char(5), 
		  CITY char(20), 
		  STATENAME char(20), 
		  COUNTY char(20) 
		  );

		*/

		Table ziptable = createTable( "ziptable");
		
		Column ZIP = ziptable.addColumn("ZIP", new CharColumnType(5));
		Column CITY = ziptable.addColumn("CITY", new CharColumnType(20));
		Column STATENAME = ziptable.addColumn("STATENAME", new CharColumnType(20));
		Column COUNTY = ziptable.addColumn("COUNTY", new CharColumnType(20));

		/*
		  
		  CREATE TABLE userrecord
		  ( 
		  NAME char(50), 
		  ZIP char(5), 
		  SSN int NOT NULL, 
		  AGE int, 
		  SEX char(50), 
		  MARITAL char(50), 
		  RACE char(50), 
		  TAXSTAT char(50), 
		  DETAIL char(100), 
		  HOUSEHOLDDETAIL char(100), 
		  FATHERORIGIN char(50), 
		  MOTHERORIGIN char(50), 
		  BIRTHCOUNTRY char(50), 
		  CITIZENSHIP char(50), 
		  PRIMARY KEY (SSN) 
		  );

		 */

		Table userrecordTable = createTable("userrecord");

		Column NAMEUser = userrecordTable.addColumn("NAME", new CharColumnType(50));
		Column ZIPUser = userrecordTable.addColumn("ZIP", new CharColumnType(5));
		Column SSN = userrecordTable.addColumn("SSN", new IntegerColumnType());
		SSN.setNotNull();
		SSN.setPrimaryKey();

		Column SEXUser = userrecordTable.addColumn("SEX", new CharColumnType(50));
		Column MARITAL = userrecordTable.addColumn("MARITAL", new CharColumnType(50));
		Column RACE = userrecordTable.addColumn("RACE", new CharColumnType(50));
		Column TAXSTAT = userrecordTable.addColumn("TAXSTAT", new CharColumnType(50));
		Column DETAIL = userrecordTable.addColumn("DETAIL", new CharColumnType(100));
		Column HOUSEHOLDDETAIL = userrecordTable.addColumn("HOUSEHOLDDETAIL", new CharColumnType(100));
		Column FATHERORIGIN = userrecordTable.addColumn("FATHERORIGIN", new CharColumnType(50));
		Column MOTHERORIGIN = userrecordTable.addColumn("MOTHERORIGIN", new CharColumnType(50));
		Column BIRTHCOUNTRY = userrecordTable.addColumn("BIRTHCOUNTRY", new CharColumnType(50));
		Column CITIZENSHIP = userrecordTable.addColumn("CITIZENSHIP", new CharColumnType(50));
		
		/*
		  
		  CREATE TABLE youth
		  (
		  SSN int NOT NULL, 
		  PARENTS char(50), 
		  PRIMARY KEY (SSN),
		  FOREIGN KEY (SSN) REFERENCES userrecord (SSN)	 
		  );

		 */

		Table youthTable = createTable( "youth");

		Column SSNYouth = youthTable.addColumn("SSN", new IntegerColumnType());
		SSNYouth.setNotNull();
		SSNYouth.setPrimaryKey();
		
		Column PARENTS = youthTable.addColumn("PARENTS", new CharColumnType(50));

		youthTable.addForeignKeyConstraint(userrecordTable, SSNYouth, SSN);

		/*
		  
		  CREATE TABLE industry 
		  ( 
		  INDUSTRYCODE int NOT NULL, 
		  INDUSTRY char(50),
		  STABILITY int, 
		  PRIMARY KEY (INDUSTRYCODE) 
		  );

		 */

		Table industryTable = createTable( "industry");
		
		Column INDUSTRYCODE = industryTable.addColumn("INDUSTRYCODE", new IntegerColumnType());
		INDUSTRYCODE.setNotNull();
		INDUSTRYCODE.setPrimaryKey();

		Column INDUSTRY = industryTable.addColumn("INDUSTRY", new CharColumnType(50));
		Column STABILITYIndustry = industryTable.addColumn("STABILITY", new IntegerColumnType());
		
		/*

		  CREATE TABLE occupation 
		  ( 
		  OCCUPATIONCODE int NOT NULL, 
		  OCCUPATION char(50), 
		  STABILITY int, 
		  PRIMARY KEY (OCCUPATIONCODE) 
		  );

		*/

		Table occupationTable = createTable( "occupation");
		
		Column OCCUPATIONCODE = occupationTable.addColumn("OCCUPATIONCODE", new IntegerColumnType());
		OCCUPATIONCODE.setNotNull();
		OCCUPATIONCODE.setPrimaryKey();

		Column OCCUPATION = occupationTable.addColumn("OCCUPATION", new CharColumnType(50));
		Column STABILITYOccupation = occupationTable.addColumn("STABILITY", new IntegerColumnType());

		/*

		  CREATE TABLE wage 
		  ( 
		  INDUSTRYCODE int NOT NULL, 
		  OCCUPATIONCODE int NOT NULL, 
		  MEANWEEKWAGE int, 
		  PRIMARY KEY (INDUSTRYCODE, OCCUPATIONCODE),
		  FOREIGN KEY (INDUSTRYCODE) REFERENCES industry (INDUSTRYCODE),
		  FOREIGN KEY (OCCUPATIONCODE) REFERENCES occupation (OCCUPATIONCODE) 
		  );

		 */

		Table wageTable = createTable( "wage");

		Column INDUSTRYCODEWage = wageTable.addColumn("INDUSTRYCODE", new IntegerColumnType());
		INDUSTRYCODEWage.setNotNull();

		Column OCCUPATIONCODEWage = wageTable.addColumn("OCCUPATIONCODE", new IntegerColumnType());
		OCCUPATIONCODEWage.setNotNull();
		
		Column MEANWEEKWAGE = wageTable.addColumn("MEANWEEKWAGE", new IntegerColumnType());
		
		wageTable.setPrimaryKeyConstraint(INDUSTRYCODEWage, OCCUPATIONCODEWage);

		wageTable.addForeignKeyConstraint(industryTable, INDUSTRYCODEWage, INDUSTRYCODE);
		wageTable.addForeignKeyConstraint(occupationTable, OCCUPATIONCODEWage, OCCUPATIONCODE);

		/*

		  CREATE TABLE stateabbv 
		  ( 
		  ABBV char(2) NOT NULL, 
		  NAME char(50) NOT NULL
		  );

		 */

		Table stateabbvTable = createTable( "stateabbv");
		
		Column ABBV = stateabbvTable.addColumn("ABBV", new CharColumnType(2));
		ABBV.setNotNull();

		Column NAMEAbbv = stateabbvTable.addColumn("NAME", new CharColumnType(50));
		NAMEAbbv.setNotNull();

		/*

		  CREATE TABLE migration 
		  ( 
		  SSN int NOT NULL, 
		  MIGRATIONCODE char(50),
		  MIGRATIONDISTANCE char(50), 
		  MIGRATIONMOVE char(50),
		  MIGRATIONFROMSUNBELT char(50), 
		  PRIMARY KEY (SSN),
		  FOREIGN KEY (SSN) REFERENCES userrecord (SSN) 
		  );

		 */

		Table migrationTable = createTable( "migration");

		Column SSNMig = migrationTable.addColumn("SSN", new IntegerColumnType());
		SSNMig.setNotNull();
		SSNMig.setPrimaryKey();

		Column MIGRATIONCODE = migrationTable.addColumn("MIGRATIONCODE", new CharColumnType(50));
		Column MIGRATIONDISTANCE = migrationTable.addColumn("MIGRATIONDISTANCE", new CharColumnType(50));
		Column MIGRATIONMOVE = migrationTable.addColumn("MIGRATIONMOVE", new CharColumnType(50));
		Column MIGRATIONFROMSUNBELT = migrationTable.addColumn("MIGRATIONFROMSUNBELT", new CharColumnType(50));

		migrationTable.addForeignKeyConstraint(userrecordTable, SSNMig, SSN);

		/*
		  
		  CREATE TABLE education 
		  ( 
		  SSN int NOT NULL, 
		  EDUCATION char(50),
		  EDUENROLL char(50), 
		  PRIMARY KEY (SSN),
		  FOREIGN KEY (SSN) REFERENCES userrecord (SSN)
		  );

		 */

		Table educationTable = createTable( "education");
		
		Column SSNEduc = educationTable.addColumn("SSN", new IntegerColumnType());
		SSNEduc.setNotNull();
		SSNEduc.setPrimaryKey();

		Column EDUCATION = educationTable.addColumn("EDUCATION", new CharColumnType(50));
		Column EDUENROLL = educationTable.addColumn("EDUENROLL", new CharColumnType(50));

		educationTable.addForeignKeyConstraint(userrecordTable, SSNEduc, SSN);

		/*

		  CREATE TABLE employmentstat
		  ( 
		  SSN int NOT NULL, 
		  UNEMPLOYMENTREASON char(50), 
		  EMPLOYMENTSTAT char(50), 
		  PRIMARY KEY (SSN),
		  FOREIGN KEY (SSN) REFERENCES userrecord (SSN) 
		  );

		 */

		Table employmentstatTable = createTable( "employmentstat");
		
		Column SSNEmpStat = employmentstatTable.addColumn("SSN", new IntegerColumnType());
		SSNEmpStat.setNotNull();
		SSNEmpStat.setPrimaryKey();

		Column UNEMPLOYMENTREASON = employmentstatTable.addColumn("UNEMPLOYMENTREASON", new CharColumnType(50));
                Column EMPLOYMENTSTAT = employmentstatTable.addColumn("EMPLOYMENTSTAT", new CharColumnType(50));
		
		employmentstatTable.addForeignKeyConstraint(userrecordTable, SSNEmpStat, SSN);

		/*

		  CREATE TABLE geo 
		  ( 
		  REGION char(50) NOT NULL, 
		  RESSTATE char(50) NOT NULL, 
		  PRIMARY KEY (RESSTATE) 
		  );

		 */

		Table geoTable = createTable( "geo");

		Column REGION = geoTable.addColumn("REGION", new CharColumnType(50));
		REGION.setNotNull();

		Column RESSTATE = geoTable.addColumn("RESSTATE", new CharColumnType(50));
		RESSTATE.setPrimaryKey();
		RESSTATE.setNotNull();

		/*

		  CREATE TABLE investment 
		  ( 
		  SSN int NOT NULL, 
		  CAPITALGAINS int,
		  CAPITALLOSSES int, 
		  STOCKDIVIDENDS int, 
		  PRIMARY KEY (SSN),
		  FOREIGN KEY (SSN) REFERENCES userrecord (SSN)
		  );

		 */

		Table investmentTable = createTable( "investment");
		
		Column SSNInvest = investmentTable.addColumn("SSN", new IntegerColumnType());
		SSNInvest.setNotNull();
		SSNInvest.setPrimaryKey();

		Column CAPITALGAINS = investmentTable.addColumn("CAPITALGAINS", new IntegerColumnType());
		Column CAPITALLOSSES = investmentTable.addColumn("CAPITALLOSSES", new IntegerColumnType());
		Column STOCKDIVIDENDS = investmentTable.addColumn("STOCKDIVIDENDS", new IntegerColumnType());

		investmentTable.addForeignKeyConstraint(userrecordTable, SSNInvest, SSN);

		/*

		  CREATE TABLE job 
		  ( 
		  SSN int NOT NULL, 
		  WORKCLASS char(50), 
		  INDUSTRYCODE int, 
		  OCCUPATIONCODE int, 
		  UNIONMEMBER char(50), 
		  EMPLOYERSIZE int,
		  WEEKWAGE int, 
		  SELFEMPLOYED smallint, 
		  WORKWEEKS int, 
		  PRIMARY KEY (SSN),
		  FOREIGN KEY (OCCUPATIONCODE) REFERENCES occupation (OCCUPATIONCODE), 
		  FOREIGN KEY (SSN) REFERENCES userrecord (SSN), 
		  FOREIGN KEY (INDUSTRYCODE) REFERENCES industry (INDUSTRYCODE)
		  );

		 */

		Table jobTable = createTable( "job");

		Column SSNJob = jobTable.addColumn("SSN", new IntegerColumnType());
		SSNJob.setNotNull();
		SSNJob.setPrimaryKey();

		Column WORKCLASS = jobTable.addColumn("WORKCLASS", new CharColumnType(50));
		Column INDUSTRYCODEJob = jobTable.addColumn("INDUSTRYCODE", new IntegerColumnType());
		Column OCCUPATIONCODEJob = jobTable.addColumn("OCCUPATIONCODE", new IntegerColumnType());
		Column UNIONMEMBER = jobTable.addColumn("UNIONMEMBER", new CharColumnType(50));
		Column EMPLOYERSIZE = jobTable.addColumn("EMPLOYERSIZE", new IntegerColumnType());
		Column WEEKWAGE = jobTable.addColumn("WEEKWAGE", new IntegerColumnType());
		Column SELFEMPLOYED = jobTable.addColumn("SELFEMPLOYED", new SmallIntColumnType());
		Column WORKWEEKS = jobTable.addColumn("WORKWEEKS", new IntegerColumnType());

		jobTable.addForeignKeyConstraint(occupationTable, OCCUPATIONCODEJob, OCCUPATIONCODE);
		jobTable.addForeignKeyConstraint(userrecordTable, SSNJob, SSN);
		jobTable.addForeignKeyConstraint(industryTable, INDUSTRYCODEJob, INDUSTRYCODE);
		

    }
}
