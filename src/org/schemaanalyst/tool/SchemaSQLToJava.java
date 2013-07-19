package org.schemaanalyst.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.schemaanalyst.javawriter.SchemaJavaWriter;
import org.schemaanalyst.util.runner.Description;

@Description("Parses a schema and generates Java code for it.")
public class SchemaSQLToJava extends SchemaSQLParser {
     
    
    
    public void run(String... args) {
        initialise(args);
        
        // TODO: "parsedcasestudy" should be a configuration value
        String javaCode = (new SchemaJavaWriter(schemaObject)).writeSchema("parsedcasestudy");            
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
