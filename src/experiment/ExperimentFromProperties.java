/*
 */
package experiment;

import java.io.File;
import java.io.FileFilter;
import org.javarunner.PropertiesRunner;

/**
 *
 * @author chris
 */
public class ExperimentFromProperties {
    
    public static void main(String[] args) {
        if (args == null || args.length == 0 || args[0] == null) {
            throw new RuntimeException("ExperimentFromProperties requires a "
                    + "uniqueid value to be provided (-Duniqueid=#)");
        }
        PropertiesRunner.startJVMFromProperties("ExperimentTasks/"+args[0]);
    }
    
}
