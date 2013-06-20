package casestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.checkcondition.RelationalCheckCondition;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;

public class NistXTS748 extends Schema {

    static final long serialVersionUID = 2709418941193334851L;
	
	public NistXTS748() {
		super("NistXTS748");
		
		/*
		  
		  CREATE TABLE TEST12549
		  (
		  TNUM1 NUMERIC(5)
		  CONSTRAINT CND12549A NOT NULL,
		  TNUM2 NUMERIC(5)
		  CONSTRAINT CND12549B UNIQUE,
		  TNUM3 NUMERIC(5)
		  CONSTRAINT CND12549C CHECK(TNUM3 > 0)
		  );
		  
		*/

		Table test12549 = createTable( "TEST12549");
		
		Column tnum1 = test12549 .addColumn("TNUM1" , new NumericDataType(5));
		Column tnum2 = test12549 .addColumn("TNUM2" , new NumericDataType(5));
		Column tnum3 = test12549 .addColumn("TNUM3" , new NumericDataType(5));

		tnum1.setNotNull();
		tnum2.setUnique();
		
		test12549.addCheckConstraint(new RelationalCheckCondition(tnum3, ">", 0));
		
	}
}
