package org.schemaanalyst.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.schemaanalyst.util.IndentableStringBuilder;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.Runner;

@Description("Generates a complete test suite from individual test classes")
public class GenerateTestSuite extends Runner {

    private static final String JAVA_FILE_SUFFIX = ".java";
    private static final String CLASS_SUFFIX = ".class";
    
    @Parameter("The name of the class name to put the test suite")
    private String classname = "AllTests";
    
    @Override
    protected void task() {
        String testSrcDir = locationsConfiguration.getTestSrcDir();
        String testPackage = locationsConfiguration.getTestPackage();
        List<File> classFiles = new ArrayList<File>();
        
        Deque<File> dirs = new ArrayDeque<File>();         
        dirs.push(new File(testSrcDir));
        
        while (dirs.size() > 0) {
            File dir = dirs.pop();
            String[] entries = dir.list();
            for (String entry : entries) {
                File entryFile = new File(dir.getPath() + File.separator + entry);
                if (entryFile.isDirectory()) {
                    dirs.push(entryFile);
                } else {
                    String fileOnly = entryFile.getName();
                    if (fileOnly.startsWith("Test") && fileOnly.endsWith(JAVA_FILE_SUFFIX)) {
                        classFiles.add(entryFile);
                    }
                }                
            }
        }
        
        List<String> classNames = new ArrayList<String>();
        for (File classFile : classFiles) {
            String path = classFile.getPath().substring(testSrcDir.length());
            String className = testPackage + path.replace("/", ".");
            className = className.substring(0, className.length() - JAVA_FILE_SUFFIX.length());
            className += CLASS_SUFFIX;
            classNames.add(className);
        }
        Collections.sort(classNames);
        
        IndentableStringBuilder javaCode = new IndentableStringBuilder();
        javaCode.appendln("package " + testPackage + ";");
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
        
        String testSuiteFile = testSrcDir + File.separator + classname + JAVA_FILE_SUFFIX;
        try (PrintWriter fileOut = new PrintWriter(testSuiteFile)) {
            fileOut.println(javaCode);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }                
    }

    @Override
    protected void validateParameters() {
        check(classname.length() > 0, "The test suite class name cannot be empty");        
    }

    public static void main(String[] args) {
        new GenerateTestSuite().run();
    }
}
