package casestudy;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Schema;
import org.schemaanalyst.schema.Table;
import org.schemaanalyst.schema.columntype.IntegerColumnType;
import org.schemaanalyst.schema.columntype.VarCharColumnType;

public class FrenchTowns extends Schema {

    static final long serialVersionUID = 5735412282111372032L;
	
	@SuppressWarnings("unused")
	public FrenchTowns() {
		super("FrenchTowns");
		
		/*

		  CREATE TABLE Regions (
		  id int UNIQUE NOT NULL,
		  code VARCHAR(4) UNIQUE NOT NULL, 
		  capital VARCHAR(10) NOT NULL, 
		  name varchar(100) UNIQUE NOT NULL
		  );

		*/

		Table regionsTable = createTable( "Regions");

		Column idRegions = regionsTable.addColumn("id" , new IntegerColumnType());
		Column code = regionsTable.addColumn("code" , new VarCharColumnType(4));
		Column capital = regionsTable.addColumn("capital" , new VarCharColumnType(10));
		Column name = regionsTable.addColumn("name" , new VarCharColumnType(100));

		idRegions.setUnique();
		idRegions.setNotNull();
		
		code.setUnique();
		code.setNotNull();
		
		capital.setNotNull();

		name.setUnique();
		name.setNotNull();

		/*

		  CREATE TABLE Departments (
		  id int UNIQUE NOT NULL,
		  code VARCHAR(4) UNIQUE NOT NULL, 
		  capital VARCHAR(10) UNIQUE NOT NULL, 
		  region VARCHAR(4) NOT NULL REFERENCES Regions (code),
		  name varchar(100) UNIQUE NOT NULL
		  );

		 */
		
		Table departmentsTable = createTable( "Departments");
		
		Column idDepartments = departmentsTable.addColumn("id" , new IntegerColumnType());
		Column codeDepartments = departmentsTable.addColumn("code" , new VarCharColumnType(4));
		Column capitalDepartments = departmentsTable.addColumn("capital" , new VarCharColumnType(4));
		Column region = departmentsTable.addColumn("region" , new VarCharColumnType(4));
		Column nameColumn = departmentsTable.addColumn("name" , new VarCharColumnType(100));

		idDepartments.setUnique();
		idDepartments.setNotNull();

		codeDepartments.setUnique();
		codeDepartments.setNotNull();
		
		capitalDepartments.setUnique();
		capitalDepartments.setNotNull();

		region.setNotNull();
		nameColumn.setUnique();
		nameColumn.setNotNull();

		departmentsTable.addForeignKeyConstraint(regionsTable, region, code);

		/*

		  CREATE TABLE Towns (
		  id int UNIQUE NOT NULL,
		  code VARCHAR(10) NOT NULL, 
		  article varchar(100),
		  name varchar(100) NOT NULL, 
		  department VARCHAR(4) NOT NULL REFERENCES Departments (code),
		  UNIQUE (code, department)
		  );

		 */

		Table townsTable = createTable( "Towns");

		Column idTowns = townsTable.addColumn("id" , new IntegerColumnType());
		Column codeTowns = townsTable.addColumn("code", new VarCharColumnType(10));
		Column articleTowns = townsTable.addColumn("article" , new VarCharColumnType(100));
		Column nameTowns = townsTable.addColumn("name" , new VarCharColumnType(100));
		Column departmentTowns = townsTable.addColumn("department" , new VarCharColumnType(4));

		townsTable.addForeignKeyConstraint(departmentsTable, departmentTowns, codeDepartments);

                townsTable.addUniqueConstraint(codeTowns, departmentTowns);
                
		idTowns.setNotNull();
		
		codeTowns.setNotNull();
		nameTowns.setNotNull();

		departmentTowns.setNotNull();

	}
}
