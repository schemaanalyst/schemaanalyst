package org.schemaanalyst.faultlocalization;

import org.schemaanalyst.sqlrepresentation.Schema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.schemaanalyst.util.java.JavaUtils.JAVA_FILE_SUFFIX;

public class CreateSchemas {

	public static Schema schemaObject;
	public static String PACKAGE = "defective";
	public static String DIRECTORY = "src/defective";

	public static void CreateDefectiveSchemas(String operator, Schema schema, int num) {
		schemaObject = schema;
		String javaCode = (new SchemaWriter(schemaObject)
				.writeSchema(PACKAGE, operator, num));
		File javaFile = new File(DIRECTORY + "/" + operator + schema + num + JAVA_FILE_SUFFIX);
		try (PrintWriter fileOut = new PrintWriter(javaFile)) {
			fileOut.println(javaCode);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Java code for schema written to "
				+ javaFile.getAbsolutePath());
	}

}

// Schema schemaObject = args[0]
// String javaCode = (new SchemaJavaWriter(schemaObject))
// .writeSchema(locationsConfiguration.getCaseStudyPackage());
// File javaFile = new File(locationsConfiguration.getCaseStudySrcDir()
// + "/" + schema + JAVA_FILE_SUFFIX);
// try (PrintWriter fileOut = new PrintWriter(javaFile)) {
// fileOut.println(javaCode);
// } catch (FileNotFoundException e) {
// throw new RuntimeException(e);
// }
// out.println("Java code for schema written to "
// + javaFile.getAbsolutePath());
// }
