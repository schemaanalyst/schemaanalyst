package org.schemaanalyst.script;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

import org.schemaanalyst.deprecated.configuration.Configuration;

public class ScriptCreator {

    private static class ScriptCreatorHolder {

        public static ScriptCreator scriptCreator = new ScriptCreator();
    }

    public static ScriptCreator getScriptCreator() {
        return ScriptCreatorHolder.scriptCreator;
    }
    protected PrintWriter scriptOutput;

    public void configure() {
        try {
            // create the scripts directory for storing the automatically
            // generated scripts for satisfying and violating the schema
            File scriptsDirectory = new File(Configuration.project
                    + "Scripts/");

            // if the Scripts/ directory does not exist, then create it
            if (!scriptsDirectory.exists()) {
                scriptsDirectory.mkdir();
            }

            // create a PrintWriter associated with the text file
            scriptOutput = new PrintWriter(Configuration.project
                    + "Scripts/"
                    + Configuration.scriptfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void print(String line) {
        scriptOutput.println(line);
        scriptOutput.flush();
    }

    public void close() {
        scriptOutput.close();
    }
}