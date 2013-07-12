package originalcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

public class Cloc extends Schema {

    static final long serialVersionUID = 8043309076732264842L;

    public Cloc() {
        super("Cloc");

        /*
         CREATE TABLE metadata 
         (          
         timestamp varchar(50),    
         Project   varchar(50),    
         elapsed_s int
         );
         */

        Table metadataTable = createTable("metadata");

        metadataTable.addColumn("timestamp", new VarCharDataType(50));
        metadataTable.addColumn("Project", new VarCharDataType(50));
        metadataTable.addColumn("elapsed_s", new IntDataType());

        /*

         CREATE TABLE t        
         (
         Project   varchar(50),  
         Language  varchar(50),  
         File      varchar(50),  
         nBlank    integer,  
         nComment  integer,  
         nCode     integer,  
         nScaled   int   
         );

         */

        Table tTable = createTable("t");

        tTable.addColumn("Project", new VarCharDataType(50));
        tTable.addColumn("Language", new VarCharDataType(50));
        tTable.addColumn("File", new VarCharDataType(50));

        tTable.addColumn("nBlank", new IntDataType());
        tTable.addColumn("nComment", new IntDataType());
        tTable.addColumn("nCode", new IntDataType());
        tTable.addColumn("nScale", new IntDataType());
    }
}
