package casestudy;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.RelationalCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.DateColumnType;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

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
                
                Column examEKey = exam.addColumn("ekey", new IntegerColumnType());
                exam.setPrimaryKeyConstraint(examEKey);
                
                Column examFn = exam.addColumn("fn", new VarCharColumnType(15));
                
                Column examLn = exam.addColumn("ln", new VarCharColumnType(30));
                
                Column examExam = exam.addColumn("exam", new IntegerColumnType());
                
                Column examScore = exam.addColumn("score", new IntColumnType());
                
                Column examTimeEnter = exam.addColumn("timeEnter", new DateColumnType());
                
                exam.addCheckConstraint(new RelationalCheckPredicate(examScore, RelationalOperator.GREATER_OR_EQUALS, 0));
                exam.addCheckConstraint(new RelationalCheckPredicate(examScore, RelationalOperator.LESS_OR_EQUALS, 100));
                
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
                
                Column examLogLKey = examLog.addColumn("lkey", new IntegerColumnType());
                examLog.setPrimaryKeyConstraint(examLogLKey);
                
                Column examLogEKey = examLog.addColumn("ekey", new IntegerColumnType());
                
                Column examLogEKeyOld = examLog.addColumn("ekeyOLD", new IntegerColumnType());
                
                Column examLogFnNew = examLog.addColumn("fnNEW", new VarCharColumnType(15));
                
                Column examLogFnOld = examLog.addColumn("fnOLD", new VarCharColumnType(15));
                
                Column examLogLnNew = examLog.addColumn("lnNEW", new VarCharColumnType(30));
                
                Column examLogLnOLD = examLog.addColumn("lnOLD", new VarCharColumnType(30));
                
                Column examLogExamNEW = examLog.addColumn("examNEW", new IntegerColumnType());
                
                Column examLogExamOLD = examLog.addColumn("examOLD", new IntegerColumnType());
                
                Column examLogScoreNEW = examLog.addColumn("scoreNEW", new IntColumnType());
                                
                Column examLogScoreOLD = examLog.addColumn("scoreOLD", new IntColumnType());
                
                Column examLogSqlAction = examLog.addColumn("sqlAction", new VarCharColumnType(15));
                
                Column examLogExamTimeEnter = examLog.addColumn("examtimeEnter", new DateColumnType());
                
                Column examLogExamTimeUpdate = examLog.addColumn("examtimeUpdate", new DateColumnType());
                
                Column examLogTimeEnter = examLog.addColumn("timeEnter", new DateColumnType());
                
                examLog.addForeignKeyConstraint(exam, examLogEKey, examEKey);
                
                examLog.addCheckConstraint(new RelationalCheckPredicate(examLogScoreNEW, RelationalOperator.GREATER_OR_EQUALS, 0));            
                examLog.addCheckConstraint(new RelationalCheckPredicate(examLogScoreNEW, RelationalOperator.LESS_OR_EQUALS, 100));
                examLog.addCheckConstraint(new RelationalCheckPredicate(examLogScoreOLD, RelationalOperator.GREATER_OR_EQUALS, 0));            
                examLog.addCheckConstraint(new RelationalCheckPredicate(examLogScoreOLD, RelationalOperator.LESS_OR_EQUALS, 100));
               
	}
}
