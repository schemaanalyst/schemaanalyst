package casestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

public class UnixUsage extends Schema {

    static final long serialVersionUID = -226561854042478092L;
	
    @SuppressWarnings("unused")
	public UnixUsage() {
		super("UnixUsage");
		
		/*

		  CREATE TABLE DEPT_INFO 
		  ( 
		  DEPT_ID INTEGER NOT NULL, 
		  DEPT_NAME VARCHAR(50), 
		  PRIMARY KEY (DEPT_ID) 
		  );

		 */

		Table DEPT_INFOTable = createTable( "DEPT_INFO");

		Column DEPT_ID = DEPT_INFOTable.addColumn("DEPT_ID", new IntDataType());
		DEPT_ID.setNotNull();
		DEPT_ID.setPrimaryKey();
		
		Column DEPT_NAME = DEPT_INFOTable.addColumn("DEPT_NAME", new VarCharDataType(50));
	      
		/*
		  
		  CREATE TABLE COURSE_INFO 
		  ( 
		  COURSE_ID INTEGER NOT NULL, 
		  COURSE_NAME VARCHAR(50), 
		  OFFERED_DEPT INTEGER, 
		  GRADUATE_LEVEL SMALLINT, 
		  PRIMARY KEY (COURSE_ID),
		  FOREIGN KEY (OFFERED_DEPT) REFERENCES DEPT_INFO (DEPT_ID) 
		  );

		*/

		Table COURSE_INFOTable = createTable( "COURSE_INFO");

		Column COURSE_ID = COURSE_INFOTable.addColumn("COURSE_ID", new IntDataType());
		COURSE_ID.setNotNull();
		COURSE_ID.setPrimaryKey();

		Column COURSE_NAME = COURSE_INFOTable.addColumn("COURSE_NAME", new VarCharDataType(50));
		Column OFFERED_DEPT = COURSE_INFOTable.addColumn("OFFERED_DEPT", new IntDataType());
		Column GRADUATE_LEVEL = COURSE_INFOTable.addColumn("GRADUATE_LEVEL", new SmallIntDataType());
		
		OFFERED_DEPT.setForeignKey(DEPT_INFOTable, DEPT_ID);
		
		/*

		  CREATE TABLE OFFICE_INFO 
		  ( 
		  OFFICE_ID INTEGER NOT NULL, 
		  OFFICE_NAME VARCHAR(50), 
		  HAS_PRINTER SMALLINT, 
		  PRIMARY KEY (OFFICE_ID) 
		  );

		 */

		Table OFFICE_INFOTable = createTable( "OFFICE_INFO");
		
		Column OFFICE_ID = OFFICE_INFOTable.addColumn("OFFICE_ID", new IntDataType());
		OFFICE_ID.setNotNull();

		Column OFFICE_NAME = OFFICE_INFOTable.addColumn("OFFICE_NAME", new VarCharDataType(50));
		Column HAS_PRINTER = OFFICE_INFOTable.addColumn("HAS_PRINTER", new SmallIntDataType());
		
		OFFICE_ID.setPrimaryKey();

		/*
		  
		  CREATE TABLE RACE_INFO 
		  ( 
		  RACE_CODE INTEGER NOT NULL, 
		  RACE VARCHAR(50),
		  PRIMARY KEY (RACE_CODE) 
		  );

		 */

		Table RACE_INFOTable = createTable( "RACE_INFO");
		
		Column RACE_CODE = RACE_INFOTable.addColumn("RACE_CODE", new IntDataType());
		RACE_CODE.setNotNull();
		RACE_CODE.setPrimaryKey();
		
		Column RACE = RACE_INFOTable.addColumn("RACE", new VarCharDataType(50));
		
		/*
		  
		  CREATE TABLE USER_INFO 
		  ( 
		  USER_ID VARCHAR(50) NOT NULL, 
		  FIRST_NAME VARCHAR(50), 
		  LAST_NAME VARCHAR(50), 
		  SEX VARCHAR(1), 
		  DEPT_ID INTEGER,
		  OFFICE_ID INTEGER, 
		  GRADUATE SMALLINT, 
		  RACE INTEGER, 
		  PASSWORD VARCHAR(50) NOT NULL, 
		  YEARS_USING_UNIX INTEGER, 
		  ENROLL_DATE DATE,
		  PRIMARY KEY (USER_ID), 
		  FOREIGN KEY (DEPT_ID) REFERENCES DEPT_INFO (DEPT_ID),
		  FOREIGN KEY (OFFICE_ID) REFERENCES OFFICE_INFO (OFFICE_ID),
		  FOREIGN KEY (RACE) REFERENCES RACE_INFO (RACE_CODE)
		  );

		 */

		Table USER_INFOTable = createTable( "USER_INFO");
		
		Column USER_IDInfo = USER_INFOTable.addColumn("USER_ID", new VarCharDataType(50));
		USER_IDInfo.setNotNull();

		Column FIRST_NAME = USER_INFOTable.addColumn("FIRST_NAME", new VarCharDataType(50));
		Column LAST_NAME = USER_INFOTable.addColumn("LAST_NAME", new VarCharDataType(50));
		Column SEX = USER_INFOTable.addColumn("SEX", new VarCharDataType(1));
		Column DEPT_IDInfo = USER_INFOTable.addColumn("DEPT_ID", new IntDataType()); 
		Column OFFICE_IDInfo = USER_INFOTable.addColumn("OFFICE_ID", new IntDataType()); 
		Column GRADUATE = USER_INFOTable.addColumn("GRADUATE", new SmallIntDataType());
		Column RACEInfo = USER_INFOTable.addColumn("RACE", new IntDataType());
		
		Column PASSWORD = USER_INFOTable.addColumn("PASSWORD", new VarCharDataType(50));
		PASSWORD.setNotNull();
		
		Column YEARS_USING_UNIX = USER_INFOTable.addColumn("YEARS_USING_UNIX", new IntDataType());
		Column ENROLL_DATE = USER_INFOTable.addColumn("ENROLL_DATE", new DateDataType());

		USER_IDInfo.setPrimaryKey();
		USER_INFOTable.addForeignKeyConstraint(DEPT_IDInfo, DEPT_INFOTable, DEPT_ID);
		USER_INFOTable.addForeignKeyConstraint(OFFICE_IDInfo, OFFICE_INFOTable, OFFICE_ID);
		USER_INFOTable.addForeignKeyConstraint(RACEInfo, RACE_INFOTable, RACE_CODE);

		/*
		  
		  CREATE TABLE TRANSCRIPT 
		  ( 
		  USER_ID VARCHAR(50) NOT NULL, 
		  COURSE_ID INTEGER NOT NULL, 
		  SCORE INTEGER, 
		  PRIMARY KEY (USER_ID, COURSE_ID),
		  FOREIGN KEY (USER_ID) REFERENCES USER_INFO (USER_ID),
		  FOREIGN KEY (COURSE_ID) REFERENCES COURSE_INFO (COURSE_ID)	 
		  );
		  
		 */

		Table TRANSCRIPTTable = createTable( "TRANSCRIPT");

		Column USER_ID = TRANSCRIPTTable.addColumn("USER_ID", new VarCharDataType(50));
		USER_ID.setNotNull();

		Column COURSE_IDTrans = TRANSCRIPTTable.addColumn("COURSE_ID", new IntDataType());
		COURSE_ID.setNotNull();

		Column SCORE = TRANSCRIPTTable.addColumn("SCORE", new IntDataType());
		
                TRANSCRIPTTable.setPrimaryKeyConstraint(USER_ID, COURSE_IDTrans);
		TRANSCRIPTTable.addForeignKeyConstraint(USER_ID, USER_INFOTable, USER_IDInfo);
		TRANSCRIPTTable.addForeignKeyConstraint(COURSE_IDTrans, COURSE_INFOTable, COURSE_ID);
	
		/*
		  
		  CREATE TABLE UNIX_COMMAND 
		  ( 
		  UNIX_COMMAND VARCHAR(50) NOT NULL,
		  CATEGORY VARCHAR(50), 
		  PRIMARY KEY (UNIX_COMMAND) 
		  );

		 */
		
		Table UNIX_COMMANDTable = createTable( "UNIX_COMMAND");

		Column UNIX_COMMAND = UNIX_COMMANDTable.addColumn("UNIX_COMMAND", new VarCharDataType(50));
		UNIX_COMMAND.setNotNull();
		UNIX_COMMAND.setPrimaryKey();

		Column CATEGORY = UNIX_COMMANDTable.addColumn("CATEGORY", new VarCharDataType(50));
		
		/*

		  CREATE TABLE USAGE_HISTORY 
		  ( 
		  USER_ID VARCHAR(50) NOT NULL, 
		  SESSION_ID INTEGER, 
		  LINE_NO INTEGER, 
		  COMMAND_SEQ INTEGER, 
		  COMMAND VARCHAR(50),
		  FOREIGN KEY (USER_ID) REFERENCES USER_INFO (USER_ID) 
		  );

		 */

		Table USAGE_HISTORYTable = createTable( "USAGE_HISTORY");

		Column USER_IDUsage = USAGE_HISTORYTable.addColumn("USER_ID", new VarCharDataType(50));
		USER_IDUsage.setNotNull();

		Column SESSION_ID = USAGE_HISTORYTable.addColumn("SESSION_ID", new IntDataType());
		Column LINE_NO = USAGE_HISTORYTable.addColumn("LINE_NO", new IntDataType());
		Column COMMAND_SEQ = USAGE_HISTORYTable.addColumn("COMMAND_SEQ", new IntDataType());
		Column COMMAND = USAGE_HISTORYTable.addColumn("COMMAND", new VarCharDataType(50));
		
		USAGE_HISTORYTable.addForeignKeyConstraint(USER_IDUsage, USER_INFOTable, USER_IDInfo);
	     
	}
}
