package originalcasestudy;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

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

		Column idRegions = regionsTable.addColumn("id" , new IntDataType());
		Column code = regionsTable.addColumn("code" , new VarCharDataType(4));
		Column capital = regionsTable.addColumn("capital" , new VarCharDataType(10));
		Column name = regionsTable.addColumn("name" , new VarCharDataType(100));

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
		
		Column idDepartments = departmentsTable.addColumn("id" , new IntDataType());
		Column codeDepartments = departmentsTable.addColumn("code" , new VarCharDataType(4));
		Column capitalDepartments = departmentsTable.addColumn("capital" , new VarCharDataType(4));
		Column region = departmentsTable.addColumn("region" , new VarCharDataType(4));
		Column nameColumn = departmentsTable.addColumn("name" , new VarCharDataType(100));

		idDepartments.setUnique();
		idDepartments.setNotNull();

		codeDepartments.setUnique();
		codeDepartments.setNotNull();
		
		capitalDepartments.setUnique();
		capitalDepartments.setNotNull();

		region.setNotNull();
		nameColumn.setUnique();
		nameColumn.setNotNull();

		departmentsTable.addForeignKeyConstraint(region, regionsTable, code);

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

		Column idTowns = townsTable.addColumn("id" , new IntDataType());
		Column codeTowns = townsTable.addColumn("code", new VarCharDataType(10));
		Column articleTowns = townsTable.addColumn("article" , new VarCharDataType(100));
		Column nameTowns = townsTable.addColumn("name" , new VarCharDataType(100));
		Column departmentTowns = townsTable.addColumn("department" , new VarCharDataType(4));

		townsTable.addForeignKeyConstraint(departmentTowns, departmentsTable, codeDepartments);

                townsTable.addUniqueConstraint(codeTowns, departmentTowns);
                
		idTowns.setNotNull();
		
		codeTowns.setNotNull();
		nameTowns.setNotNull();

		departmentTowns.setNotNull();

	}
}
