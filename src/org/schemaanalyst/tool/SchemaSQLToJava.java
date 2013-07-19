package org.schemaanalyst.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.schemaanalyst.javawriter.SchemaJavaWriter;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.RequiredParameters;

@Description("Parses a schema and generates Java code for it.")
@RequiredParameters("schema dbms")
public class SchemaSQLToJava extends SchemaSQLParser {

    public void run(String... args) {
        initialise(args);
        
        // generate and write Java code
        String javaCode = (new SchemaJavaWriter(schemaObject)).writeSchema(schema);            
        File javaFile = new File(folderConfiguration.getCasestudySrcDir() + "/" + schema + ".java");
        try (PrintWriter out = new PrintWriter(javaFile)) {
            out.println(javaCode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String... args) {
        new SchemaSQLToJava().run(args);
    }
}
