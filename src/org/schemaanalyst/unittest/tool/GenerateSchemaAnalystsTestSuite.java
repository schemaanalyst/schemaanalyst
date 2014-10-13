package org.schemaanalyst.unittest.tool;

import org.schemaanalyst.util.IndentableStringBuilder;
import org.schemaanalyst.util.file.FileSystemWalker;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.Runner;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.schemaanalyst.util.java.JavaUtils.JAVA_FILE_SUFFIX;
import static org.schemaanalyst.util.java.JavaUtils.fileNameToClassName;

@Description("Generates a complete test suite from individual test classes")
public class GenerateSchemaAnalystsTestSuite extends Runner {

    @Parameter("The name of the class name to put the test suite")
    private String classname = "AllTests";

    private List<String> getTestClasses(String srcDir, String packageName) {
        List<File> javaFiles = FileSystemWalker.filter(srcDir, new FileFilter() {			
			@Override
			public boolean accept(File file) {
				String name = file.getName();
				return (name.startsWith("Test") && name.endsWith(JAVA_FILE_SUFFIX));
			}
		}); 
        
       List<String> classNames = new ArrayList<>();
        for (File javaFile : javaFiles) {
            String path = javaFile.getPath().substring(srcDir.length());
            String className = packageName + fileNameToClassName(path, true);
            classNames.add(className);
        }
        Collections.sort(classNames);
        return classNames;
    }
    
    
    @Override
    protected void task() {
        String srcDir = locationsConfiguration.getTestSrcDir();
        String packageName = locationsConfiguration.getTestPackage();
            	
        List<String> classNames = getTestClasses(srcDir, packageName);

        IndentableStringBuilder javaCode = new IndentableStringBuilder();
        javaCode.appendln("package " + packageName + ";");
        javaCode.appendln();
        javaCode.appendln("import org.junit.runner.RunWith;");
        javaCode.appendln("import org.junit.runners.Suite;");
        javaCode.appendln();
        javaCode.appendln("@RunWith(Suite.class)");
        javaCode.appendln("@Suite.SuiteClasses({");
        boolean first = true;
        for (String className : classNames) {
            if (first) {
                first = false;
            } else {
                javaCode.appendln(0, ",");
            }
            javaCode.append(1, className);
        }
        javaCode.appendln(0);
        javaCode.appendln("})");
        javaCode.appendln();
        javaCode.appendln("public class " + classname + " {}");

        File testSuiteFile = new File(srcDir + File.separator + classname
                + JAVA_FILE_SUFFIX);
        try (PrintWriter fileOut = new PrintWriter(testSuiteFile)) {
            fileOut.println(javaCode);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        out.println("Written test suite to " + testSuiteFile.getAbsolutePath());
    }

    @Override
    protected void validateParameters() {
        check(classname.length() > 0,
                "The test suite class name cannot be empty");
    }

    public static void main(String[] args) {
        new GenerateSchemaAnalystsTestSuite().run(args);
    }
}
