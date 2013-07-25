package org.schemaanalyst.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.schemaanalyst.javawriter.SchemaJavaWriter;
import org.schemaanalyst.util.runner.Description;

@Description("Parses a schema and generates Java code for it.")
public class SchemaSQLToJava extends SchemaSQLParser {
    
    @Override
    protected void task() {
        String javaCode = (new SchemaJavaWriter(schemaObject)).writeSchema(locationsConfiguration.getCaseStudyPackage());            
        File javaFile = new File(locationsConfiguration.getCaseStudySrcDir() + "/" + schema + ".java");
        try (PrintWriter fileOut = new PrintWriter(javaFile)) {
            fileOut.println(javaCode);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        out.println(javaCode);
        out.println("[Code written to " + javaFile + "]");
    }
    
    public static void main(String... args) {
        new SchemaSQLToJava().run(args);
    }
}
