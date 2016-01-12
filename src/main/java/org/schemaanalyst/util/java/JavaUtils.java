package org.schemaanalyst.util.java;

import java.io.File;

public class JavaUtils {
	
    public static final String PACKAGE_SEPARATOR = ".";
    public static final String JAVA_FILE_SUFFIX = ".java";
    public static final String CLASS_SUFFIX = ".class";

    public static String fileNameToClassName(String fileName) {
        return fileNameToClassName(fileName, false);
    }

    public static String fileNameToClassName(String fileName,
            boolean addClassSuffix) {
        String className = fileName.replace(File.separator, PACKAGE_SEPARATOR);
        className = className.substring(0, className.length()
                - JAVA_FILE_SUFFIX.length());
        if (addClassSuffix) {
            className += CLASS_SUFFIX;
        }
        return className;
    }
    
    public static String packageToFileName(String packageName) {
        return packageName.replace(PACKAGE_SEPARATOR, File.separator);
    }
}
