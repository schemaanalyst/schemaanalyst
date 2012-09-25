package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.RelationalCheckPredicate;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class StudentResidence extends Schema {

	private static final long serialVersionUID = -2821897632006616153L;
	
	public StudentResidence() {

		super("StudentResidence");
		
		/*
                    CREATE TABLE Residence 
                    (
                          name VARCHAR(50) PRIMARY KEY NOT NULL,
                          capacity INT NOT NULL,
                          CHECK (capacity > 1),	
                          CHECK (capacity <= 10)
                    );
		*/		
		
		Table residenceTable = createTable("Residence");
		
		Column nameColumn     = residenceTable.addColumn("name", new VarCharColumnType(50));
		nameColumn.setPrimaryKey();
                nameColumn.setNotNull();
		Column capacityColumn = residenceTable.addColumn("capacity", new IntColumnType());
                capacityColumn.setNotNull();
		
		residenceTable.addCheckConstraint(new RelationalCheckPredicate(capacityColumn, ">", 1));
		residenceTable.addCheckConstraint(new RelationalCheckPredicate(capacityColumn, "<=", 10));
		
		/*
                    CREATE TABLE Student 
                    ( 
                      id INT PRIMARY KEY, 
                      firstName VARCHAR(50), 
                      lastName VARCHAR(50), 
                      residence VARCHAR(50), 
                      FOREIGN KEY(residence) REFERENCES Residence(name), 
                      CHECK (id >= 0)	 
                    );
		*/		
		
		Table studentTable = createTable("Student");
		
		Column idColumn = studentTable.addColumn("id", new IntColumnType());		
		idColumn.setPrimaryKey();
		
		studentTable.addColumn("firstName", new VarCharColumnType(50));
		
		studentTable.addColumn("lastName",  new VarCharColumnType(50));
		
		Column residenceColumn = studentTable.addColumn("residence", new VarCharColumnType(50));
		residenceColumn.setForeignKey(residenceTable, nameColumn);
		
		studentTable.addCheckConstraint(new RelationalCheckPredicate(idColumn, ">=", 0));		
	}
}
