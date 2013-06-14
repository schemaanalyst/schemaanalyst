package casestudy;

import org.schemaanalyst.representation.Column;
import org.schemaanalyst.representation.Schema;
import org.schemaanalyst.representation.Table;
import org.schemaanalyst.representation.datatype.IntDataType;
import org.schemaanalyst.representation.datatype.NumericDataType;
import org.schemaanalyst.representation.datatype.VarCharDataType;

public class NistDML182 extends Schema {

	private static final long serialVersionUID = -7458808787149996085L;

	public NistDML182() {
		super("NistDML182");
	
		/*
		  CREATE TABLE ID_CODES (
				     CODE1 INT,
				     CODE2 INT,
				     CODE3 INT,
				     CODE4 INT,
				     CODE5 INT,
				     CODE6 INT,
				     CODE7 INT,
				     CODE8 INT,
				     CODE9 INT,
				     CODE10 INT,
				     CODE11 INT,
				     CODE12 INT,
				     CODE13 INT,
				     CODE14 INT,
				     CODE15 INT,
				     PRIMARY KEY (CODE1, CODE2, CODE3, CODE4, CODE5,
				       CODE6, CODE7, CODE8, CODE9, CODE10,
				       CODE11, CODE12, CODE13, CODE14, CODE15));	
		*/
		
		Table idCodes = createTable("ID_CODES");
		Column idCodesCode1  = idCodes.addColumn("CODE1", new IntDataType());
		Column idCodesCode2  = idCodes.addColumn("CODE2", new IntDataType());
		Column idCodesCode3  = idCodes.addColumn("CODE3", new IntDataType());
		Column idCodesCode4  = idCodes.addColumn("CODE4", new IntDataType());
		Column idCodesCode5  = idCodes.addColumn("CODE5", new IntDataType());
		Column idCodesCode6  = idCodes.addColumn("CODE6", new IntDataType());
		Column idCodesCode7  = idCodes.addColumn("CODE7", new IntDataType());
		Column idCodesCode8  = idCodes.addColumn("CODE8", new IntDataType());
		Column idCodesCode9  = idCodes.addColumn("CODE9", new IntDataType());
		Column idCodesCode10 = idCodes.addColumn("CODE10", new IntDataType());
		Column idCodesCode11 = idCodes.addColumn("CODE11", new IntDataType());
		Column idCodesCode12 = idCodes.addColumn("CODE12", new IntDataType());
		Column idCodesCode13 = idCodes.addColumn("CODE13", new IntDataType());
		Column idCodesCode14 = idCodes.addColumn("CODE14", new IntDataType());
		Column idCodesCode15 = idCodes.addColumn("CODE15", new IntDataType());
		
		idCodes.setPrimaryKeyConstraint(idCodesCode1, idCodesCode2, idCodesCode3, idCodesCode4, idCodesCode5, idCodesCode6, idCodesCode7, 
							  idCodesCode8, idCodesCode9, idCodesCode10, idCodesCode11, idCodesCode12, idCodesCode13, idCodesCode14, 
							  idCodesCode15);
		
		/*
		  CREATE TABLE ORDERS (
				     CODE1 INT,
				     CODE2 INT,
				     CODE3 INT,
				     CODE4 INT,
				     CODE5 INT,
				     CODE6 INT,
				     CODE7 INT,
				     CODE8 INT,
				     CODE9 INT,
				     CODE10 INT,
				     CODE11 INT,
				     CODE12 INT,
				     CODE13 INT,
				     CODE14 INT,
				     CODE15 INT,
				     TITLE VARCHAR (80),
				     COST NUMERIC(5,2),
				     FOREIGN KEY (CODE1, CODE2, CODE3, CODE4, CODE5,
				       CODE6, CODE7, CODE8, CODE9, CODE10,
				       CODE11, CODE12, CODE13, CODE14, CODE15)
				     REFERENCES ID_CODES);		
		*/
		
		Table orders = createTable("ORDERS");
		Column ordersCode1  = orders.addColumn("CODE1", new IntDataType());
		Column ordersCode2  = orders.addColumn("CODE2", new IntDataType());
		Column ordersCode3  = orders.addColumn("CODE3", new IntDataType());
		Column ordersCode4  = orders.addColumn("CODE4", new IntDataType());
		Column ordersCode5  = orders.addColumn("CODE5", new IntDataType());
		Column ordersCode6  = orders.addColumn("CODE6", new IntDataType());
		Column ordersCode7  = orders.addColumn("CODE7", new IntDataType());
		Column ordersCode8  = orders.addColumn("CODE8", new IntDataType());
		Column ordersCode9  = orders.addColumn("CODE9", new IntDataType());
		Column ordersCode10 = orders.addColumn("CODE10", new IntDataType());
		Column ordersCode11 = orders.addColumn("CODE11", new IntDataType());
		Column ordersCode12 = orders.addColumn("CODE12", new IntDataType());
		Column ordersCode13 = orders.addColumn("CODE13", new IntDataType());
		Column ordersCode14 = orders.addColumn("CODE14", new IntDataType());
		Column ordersCode15 = orders.addColumn("CODE15", new IntDataType());
		
		orders.addColumn("title", new VarCharDataType(80));
		orders.addColumn("cost", new NumericDataType(5,2));
			
		orders.addForeignKeyConstraint(idCodes,
							 ordersCode1, ordersCode2, ordersCode3, 
						     ordersCode4, ordersCode5, ordersCode6, 
						     ordersCode7, ordersCode8, ordersCode9, 
						     ordersCode10, ordersCode11, ordersCode12, 
						     ordersCode13, ordersCode14, ordersCode15,
						     idCodesCode1, idCodesCode2, idCodesCode3, 
							 idCodesCode4, idCodesCode5, idCodesCode6, 
							 idCodesCode7, idCodesCode8, idCodesCode9, 
							 idCodesCode10, idCodesCode11, idCodesCode12, 
							 idCodesCode13, idCodesCode14, idCodesCode15);
	}	
}
