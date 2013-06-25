package casestudy;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.checkcondition.RelationalCheckCondition;
import org.schemaanalyst.sqlrepresentation.datatype.DateDataType;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

public class Examination extends Schema {

    static final long serialVersionUID = 3237592635586364244L;
	
	@SuppressWarnings("unused")
	public Examination() {
		super("Examination");
		
		/*
		  
		  CREATE TABLE Exam 
		  (
		  ekey      INTEGER PRIMARY KEY,
		  fn        VARCHAR(15),
		  ln        VARCHAR(30),
		  exam      INTEGER,
		  score     int,
		  timeEnter DATE,
		  CHECK (score >= 0),	 
		  CHECK (score <= 100)	 
		  );

		*/

                Table exam = createTable("Exam");
                
                Column examEKey = exam.addColumn("ekey", new IntDataType());
                exam.setPrimaryKeyConstraint(examEKey);
                
                Column examFn = exam.addColumn("fn", new VarCharDataType(15));
                
                Column examLn = exam.addColumn("ln", new VarCharDataType(30));
                
                Column examExam = exam.addColumn("exam", new IntDataType());
                
                Column examScore = exam.addColumn("score", new IntDataType());
                
                Column examTimeEnter = exam.addColumn("timeEnter", new DateDataType());
                
                exam.addCheckConstraint(new RelationalCheckCondition(examScore, RelationalOperator.GREATER_OR_EQUALS, 0));
                exam.addCheckConstraint(new RelationalCheckCondition(examScore, RelationalOperator.LESS_OR_EQUALS, 100));
                
		/*

		  CREATE TABLE Examlog 
		  (
		  lkey INTEGER PRIMARY KEY,
		  ekey INTEGER,
		  ekeyOLD INTEGER,
		  fnNEW   VARCHAR(15),
		  fnOLD   VARCHAR(15),
		  lnNEW   VARCHAR(30),
		  lnOLD   VARCHAR(30),
		  examNEW INTEGER,
		  examOLD INTEGER,
		  scoreNEW int,
		  scoreOLD int,
		  sqlAction VARCHAR(15),
		  examtimeEnter    DATE,
		  examtimeUpdate   DATE,
		  timeEnter        DATE,
		  FOREIGN KEY(ekey) REFERENCES Exam(ekey), 
		  CHECK (scoreNEW >= 0),	 
		  CHECK (scoreNEW <= 100),
		  CHECK (scoreOLD >= 0),	 
		  CHECK (scoreOLD <= 100)	 	 
		  );
		  
		*/
                
                Table examLog = createTable("ExamLog");
                
                Column examLogLKey = examLog.addColumn("lkey", new IntDataType());
                examLog.setPrimaryKeyConstraint(examLogLKey);
                
                Column examLogEKey = examLog.addColumn("ekey", new IntDataType());
                
                Column examLogEKeyOld = examLog.addColumn("ekeyOLD", new IntDataType());
                
                Column examLogFnNew = examLog.addColumn("fnNEW", new VarCharDataType(15));
                
                Column examLogFnOld = examLog.addColumn("fnOLD", new VarCharDataType(15));
                
                Column examLogLnNew = examLog.addColumn("lnNEW", new VarCharDataType(30));
                
                Column examLogLnOLD = examLog.addColumn("lnOLD", new VarCharDataType(30));
                
                Column examLogExamNEW = examLog.addColumn("examNEW", new IntDataType());
                
                Column examLogExamOLD = examLog.addColumn("examOLD", new IntDataType());
                
                Column examLogScoreNEW = examLog.addColumn("scoreNEW", new IntDataType());
                                
                Column examLogScoreOLD = examLog.addColumn("scoreOLD", new IntDataType());
                
                Column examLogSqlAction = examLog.addColumn("sqlAction", new VarCharDataType(15));
                
                Column examLogExamTimeEnter = examLog.addColumn("examtimeEnter", new DateDataType());
                
                Column examLogExamTimeUpdate = examLog.addColumn("examtimeUpdate", new DateDataType());
                
                Column examLogTimeEnter = examLog.addColumn("timeEnter", new DateDataType());
                
                examLog.addForeignKeyConstraint(examLogEKey, exam, examEKey);
                
                examLog.addCheckConstraint(new RelationalCheckCondition(examLogScoreNEW, RelationalOperator.GREATER_OR_EQUALS, 0));            
                examLog.addCheckConstraint(new RelationalCheckCondition(examLogScoreNEW, RelationalOperator.LESS_OR_EQUALS, 100));
                examLog.addCheckConstraint(new RelationalCheckCondition(examLogScoreOLD, RelationalOperator.GREATER_OR_EQUALS, 0));            
                examLog.addCheckConstraint(new RelationalCheckCondition(examLogScoreOLD, RelationalOperator.LESS_OR_EQUALS, 100));
               
	}
}
