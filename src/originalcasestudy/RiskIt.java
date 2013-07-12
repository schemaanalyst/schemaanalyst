package originalcasestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;

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

        Table ziptable = createTable("ziptable");

        Column ZIP = ziptable.addColumn("ZIP", new CharDataType(5));
        Column CITY = ziptable.addColumn("CITY", new CharDataType(20));
        Column STATENAME = ziptable.addColumn("STATENAME", new CharDataType(20));
        Column COUNTY = ziptable.addColumn("COUNTY", new CharDataType(20));

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

        Column NAMEUser = userrecordTable.addColumn("NAME", new CharDataType(50));
        Column ZIPUser = userrecordTable.addColumn("ZIP", new CharDataType(5));
        Column SSN = userrecordTable.addColumn("SSN", new IntDataType());
        SSN.setNotNull();
        SSN.setPrimaryKey();

        Column SEXUser = userrecordTable.addColumn("SEX", new CharDataType(50));
        Column MARITAL = userrecordTable.addColumn("MARITAL", new CharDataType(50));
        Column RACE = userrecordTable.addColumn("RACE", new CharDataType(50));
        Column TAXSTAT = userrecordTable.addColumn("TAXSTAT", new CharDataType(50));
        Column DETAIL = userrecordTable.addColumn("DETAIL", new CharDataType(100));
        Column HOUSEHOLDDETAIL = userrecordTable.addColumn("HOUSEHOLDDETAIL", new CharDataType(100));
        Column FATHERORIGIN = userrecordTable.addColumn("FATHERORIGIN", new CharDataType(50));
        Column MOTHERORIGIN = userrecordTable.addColumn("MOTHERORIGIN", new CharDataType(50));
        Column BIRTHCOUNTRY = userrecordTable.addColumn("BIRTHCOUNTRY", new CharDataType(50));
        Column CITIZENSHIP = userrecordTable.addColumn("CITIZENSHIP", new CharDataType(50));

        /*
		  
         CREATE TABLE youth
         (
         SSN int NOT NULL, 
         PARENTS char(50), 
         PRIMARY KEY (SSN),
         FOREIGN KEY (SSN) REFERENCES userrecord (SSN)	 
         );

         */

        Table youthTable = createTable("youth");

        Column SSNYouth = youthTable.addColumn("SSN", new IntDataType());
        SSNYouth.setNotNull();
        SSNYouth.setPrimaryKey();

        Column PARENTS = youthTable.addColumn("PARENTS", new CharDataType(50));

        youthTable.addForeignKeyConstraint(SSNYouth, userrecordTable, SSN);

        /*
		  
         CREATE TABLE industry 
         ( 
         INDUSTRYCODE int NOT NULL, 
         INDUSTRY char(50),
         STABILITY int, 
         PRIMARY KEY (INDUSTRYCODE) 
         );

         */

        Table industryTable = createTable("industry");

        Column INDUSTRYCODE = industryTable.addColumn("INDUSTRYCODE", new IntDataType());
        INDUSTRYCODE.setNotNull();
        INDUSTRYCODE.setPrimaryKey();

        Column INDUSTRY = industryTable.addColumn("INDUSTRY", new CharDataType(50));
        Column STABILITYIndustry = industryTable.addColumn("STABILITY", new IntDataType());

        /*

         CREATE TABLE occupation 
         ( 
         OCCUPATIONCODE int NOT NULL, 
         OCCUPATION char(50), 
         STABILITY int, 
         PRIMARY KEY (OCCUPATIONCODE) 
         );

         */

        Table occupationTable = createTable("occupation");

        Column OCCUPATIONCODE = occupationTable.addColumn("OCCUPATIONCODE", new IntDataType());
        OCCUPATIONCODE.setNotNull();
        OCCUPATIONCODE.setPrimaryKey();

        Column OCCUPATION = occupationTable.addColumn("OCCUPATION", new CharDataType(50));
        Column STABILITYOccupation = occupationTable.addColumn("STABILITY", new IntDataType());

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

        Table wageTable = createTable("wage");

        Column INDUSTRYCODEWage = wageTable.addColumn("INDUSTRYCODE", new IntDataType());
        INDUSTRYCODEWage.setNotNull();

        Column OCCUPATIONCODEWage = wageTable.addColumn("OCCUPATIONCODE", new IntDataType());
        OCCUPATIONCODEWage.setNotNull();

        Column MEANWEEKWAGE = wageTable.addColumn("MEANWEEKWAGE", new IntDataType());

        wageTable.setPrimaryKeyConstraint(INDUSTRYCODEWage, OCCUPATIONCODEWage);

        wageTable.addForeignKeyConstraint(INDUSTRYCODEWage, industryTable, INDUSTRYCODE);
        wageTable.addForeignKeyConstraint(OCCUPATIONCODEWage, occupationTable, OCCUPATIONCODE);

        /*

         CREATE TABLE stateabbv 
         ( 
         ABBV char(2) NOT NULL, 
         NAME char(50) NOT NULL
         );

         */

        Table stateabbvTable = createTable("stateabbv");

        Column ABBV = stateabbvTable.addColumn("ABBV", new CharDataType(2));
        ABBV.setNotNull();

        Column NAMEAbbv = stateabbvTable.addColumn("NAME", new CharDataType(50));
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

        Table migrationTable = createTable("migration");

        Column SSNMig = migrationTable.addColumn("SSN", new IntDataType());
        SSNMig.setNotNull();
        SSNMig.setPrimaryKey();

        Column MIGRATIONCODE = migrationTable.addColumn("MIGRATIONCODE", new CharDataType(50));
        Column MIGRATIONDISTANCE = migrationTable.addColumn("MIGRATIONDISTANCE", new CharDataType(50));
        Column MIGRATIONMOVE = migrationTable.addColumn("MIGRATIONMOVE", new CharDataType(50));
        Column MIGRATIONFROMSUNBELT = migrationTable.addColumn("MIGRATIONFROMSUNBELT", new CharDataType(50));

        migrationTable.addForeignKeyConstraint(SSNMig, userrecordTable, SSN);

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

        Table educationTable = createTable("education");

        Column SSNEduc = educationTable.addColumn("SSN", new IntDataType());
        SSNEduc.setNotNull();
        SSNEduc.setPrimaryKey();

        Column EDUCATION = educationTable.addColumn("EDUCATION", new CharDataType(50));
        Column EDUENROLL = educationTable.addColumn("EDUENROLL", new CharDataType(50));

        educationTable.addForeignKeyConstraint(SSNEduc, userrecordTable, SSN);

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

        Table employmentstatTable = createTable("employmentstat");

        Column SSNEmpStat = employmentstatTable.addColumn("SSN", new IntDataType());
        SSNEmpStat.setNotNull();
        SSNEmpStat.setPrimaryKey();

        Column UNEMPLOYMENTREASON = employmentstatTable.addColumn("UNEMPLOYMENTREASON", new CharDataType(50));
        Column EMPLOYMENTSTAT = employmentstatTable.addColumn("EMPLOYMENTSTAT", new CharDataType(50));

        employmentstatTable.addForeignKeyConstraint(SSNEmpStat, userrecordTable, SSN);

        /*

         CREATE TABLE geo 
         ( 
         REGION char(50) NOT NULL, 
         RESSTATE char(50) NOT NULL, 
         PRIMARY KEY (RESSTATE) 
         );

         */

        Table geoTable = createTable("geo");

        Column REGION = geoTable.addColumn("REGION", new CharDataType(50));
        REGION.setNotNull();

        Column RESSTATE = geoTable.addColumn("RESSTATE", new CharDataType(50));
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

        Table investmentTable = createTable("investment");

        Column SSNInvest = investmentTable.addColumn("SSN", new IntDataType());
        SSNInvest.setNotNull();
        SSNInvest.setPrimaryKey();

        Column CAPITALGAINS = investmentTable.addColumn("CAPITALGAINS", new IntDataType());
        Column CAPITALLOSSES = investmentTable.addColumn("CAPITALLOSSES", new IntDataType());
        Column STOCKDIVIDENDS = investmentTable.addColumn("STOCKDIVIDENDS", new IntDataType());

        investmentTable.addForeignKeyConstraint(SSNInvest, userrecordTable, SSN);

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

        Table jobTable = createTable("job");

        Column SSNJob = jobTable.addColumn("SSN", new IntDataType());
        SSNJob.setNotNull();
        SSNJob.setPrimaryKey();

        Column WORKCLASS = jobTable.addColumn("WORKCLASS", new CharDataType(50));
        Column INDUSTRYCODEJob = jobTable.addColumn("INDUSTRYCODE", new IntDataType());
        Column OCCUPATIONCODEJob = jobTable.addColumn("OCCUPATIONCODE", new IntDataType());
        Column UNIONMEMBER = jobTable.addColumn("UNIONMEMBER", new CharDataType(50));
        Column EMPLOYERSIZE = jobTable.addColumn("EMPLOYERSIZE", new IntDataType());
        Column WEEKWAGE = jobTable.addColumn("WEEKWAGE", new IntDataType());
        Column SELFEMPLOYED = jobTable.addColumn("SELFEMPLOYED", new SmallIntDataType());
        Column WORKWEEKS = jobTable.addColumn("WORKWEEKS", new IntDataType());

        jobTable.addForeignKeyConstraint(OCCUPATIONCODEJob, occupationTable, OCCUPATIONCODE);
        jobTable.addForeignKeyConstraint(SSNJob, userrecordTable, SSN);
        jobTable.addForeignKeyConstraint(INDUSTRYCODEJob, industryTable, INDUSTRYCODE);


    }
}
