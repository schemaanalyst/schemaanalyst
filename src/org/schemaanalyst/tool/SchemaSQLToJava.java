package org.schemaanalyst.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.schemaanalyst.javawriter.SchemaJavaWriter;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.runner.Runner;

@RequiredParameters("schema_name dbms")
public class SchemaSQLToJava extends Runner {

    @Parameter("The name of the schema to be processed")
    private String schema_name;
    
    @Parameter("The DBMS whose dialect of SQL is to be used")
    private String dbms;

    public SchemaSQLToJava(String... args) {
        super(args);
    }
   
    public void run() {
        try {
            Schema parsedSchema = SchemaSQLParser.parse(schema_name, dbms, folderConfiguration.getSchemaSrcDir());
            String javaCode = (new SchemaJavaWriter(parsedSchema)).writeSchema("parsedcasestudy");            
            File javaFile = new File(folderConfiguration.getCasestudySrcDir() + "/" + schema_name + ".java");
            try (PrintWriter out = new PrintWriter(javaFile)) {
                out.println(javaCode);
            }
        } catch (ClassNotFoundException | FileNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        new SchemaSQLToJava(args).run();
    }
}
