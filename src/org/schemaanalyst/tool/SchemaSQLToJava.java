package org.schemaanalyst.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.schemaanalyst.javawriter.SchemaJavaWriter;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.runner.Option;
import org.schemaanalyst.util.runner.RequiredOptions;
import org.schemaanalyst.util.runner.Runner;

@RequiredOptions("schema_name dbms")
public class SchemaSQLToJava extends Runner {

    @Option("The name of the schema to be processed")
    private String schema_name;
    
    @Option("The DBMS whose dialect of SQL is to be used")
    private String dbms;

    public SchemaSQLToJava(String... args) {
        super(args);
    }
   
    public void run() {
        try {
            Schema parsedSchema = SchemaSQLParser.parse(schema_name, dbms, folderConfiguration.getCasestudySrcDir());
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
