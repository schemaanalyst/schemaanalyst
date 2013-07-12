package org.schemaanalyst.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.schemaanalyst.configuration.FolderConfiguration;
import org.schemaanalyst.javawriter.SchemaJavaWriter;
import org.schemaanalyst.sqlrepresentation.Schema;

public class SchemaSQLToJava {

    public static File fileForCaseStudyJavaSrc(String name) {
        return new File(FolderConfiguration.casestudy_src_dir + "/" + name + ".java");
    }

    public static void parse(String name, String dbmsName) throws ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            FileNotFoundException {

        Schema parsedSchema = SchemaSQLParser.parse(name, dbmsName);
        String javaCode = (new SchemaJavaWriter(parsedSchema)).writeSchema("parsedcasestudy");

        PrintWriter out = new PrintWriter(fileForCaseStudyJavaSrc(name));
        out.println(javaCode);
        out.close();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println(
                    "Usage: java "
                    + SchemaSQLToJava.class.getName()
                    + " schema_sql_file database");
            System.exit(1);
        }
        parse(args[0], args[1]);
    }
}
