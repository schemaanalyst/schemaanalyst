package org.schemaanalyst.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.schemaanalyst.javawriter.SchemaJavaWriter;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.util.runner.Option;
import org.schemaanalyst.util.runner.Runner;

public class SchemaSQLToJava extends Runner {

    @Option("Input SQL file")
    protected String schemaSqlFile;
    @Option("DBMS dialect")
    protected String dbmsName;

    public File fileForCaseStudyJavaSrc(String name) {
        return new File(folderConfiguration.getCasestudySrcDir() + "/" + name + ".java");
    }

    public void parse(String name, String dbmsName){
        try {
            Schema parsedSchema = SchemaSQLParser.parse(name, dbmsName, folderConfiguration.getCasestudySrcDir());
            String javaCode = (new SchemaJavaWriter(parsedSchema)).writeSchema("parsedcasestudy");
            try (PrintWriter out = new PrintWriter(fileForCaseStudyJavaSrc(name))) {
                out.println(javaCode);
            }
        } catch (ClassNotFoundException | FileNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        parse(schemaSqlFile, dbmsName);
    }

    public static void main(String[] args) throws Exception {
        SchemaSQLToJava tool = new SchemaSQLToJava();
        tool.run();
    }
}
