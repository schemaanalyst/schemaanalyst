package org.schemaanalyst.util.file;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class FileSystemWalker {

	public static List<File> filter(String rootDir, FileFilter filter) {
		return filter(new File(rootDir), filter);
	}
	
	public static List<File> filter(File rootDir, FileFilter filter) {
		Deque<File> dirs = new ArrayDeque<>();
        dirs.push(rootDir);
		
        List<File> files = new ArrayList<>();

        while (dirs.size() > 0) {
            // pop the first directory from the stack
        	File dir = dirs.pop();

            // get the entries in this directory
            String[] entries = dir.list();
            for (String entry : entries) {
            	
            	// get the full name of the file
                File file = new File(dir.getPath() + File.separator
                        + entry);
                
                // if it's a directory, push it to the stack
                if (file.isDirectory()) {
                    dirs.push(file);
                } else {
                    if (filter.accept(file)) {
                    	files.add(file);
                    }
                }
            }
        }				
		return files;
	}		
}
