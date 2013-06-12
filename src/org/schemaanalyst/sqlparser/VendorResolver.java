package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.EDbVendor;

import org.schemaanalyst.database.Database;
import org.schemaanalyst.database.DatabaseVisitor;
import org.schemaanalyst.database.derby.Derby;
import org.schemaanalyst.database.derby.DerbyNetwork;
import org.schemaanalyst.database.hsqldb.Hsqldb;
import org.schemaanalyst.database.mysql.MySQL;
import org.schemaanalyst.database.postgres.Postgres;
import org.schemaanalyst.database.sqlite.SQLite;

class VendorResolver {
	
	static EDbVendor resolve(Database database) {
		
		class VendorResolverVisitor implements DatabaseVisitor {
	
			EDbVendor vendor;
						
			public void visit(Derby database) {
				vendor = EDbVendor.dbvgeneric;
			}
			
			public void visit(DerbyNetwork database) {
				vendor = EDbVendor.dbvgeneric;
			}
			
			public void visit(Hsqldb database) {
				vendor = EDbVendor.dbvgeneric;
			}

			public void visit(MySQL database) {
				vendor = EDbVendor.dbvmysql;
			}			
			
			public void visit(Postgres database) {
				vendor = EDbVendor.dbvpostgresql;
			}
			
			public void visit(SQLite database) {
				vendor = EDbVendor.dbvgeneric;
			}			
		}
		
		VendorResolverVisitor vrv = new VendorResolverVisitor();
		database.accept(vrv);
		return vrv.vendor;
	}
}
