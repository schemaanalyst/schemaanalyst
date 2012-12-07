package experiment;

import java.lang.Class;
import java.util.List;
import java.io.File;
import java.io.PrintStream;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.DemuxOutputStream;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Echo;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Commandline.Argument;

import java.util.List;
import java.util.Arrays;

public class RunExperiment {
    /** Run a specified experiment in a separate Java virtual machine */
    public static int runExperimentInSeparateJavaVirtualMachine(Class experimentClass, List<String> arguments) {
	// the return code for running this experiment in a separate java virtual machine
	int returnCode = -1;

	// construct the project
	Project project = new Project();
        project.setBaseDir(new File(System.getProperty("user.dir")));
        project.init();

	// construct the logger and attach to the project
        DefaultLogger logger = new DefaultLogger();
        project.addBuildListener(logger);
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        System.setOut(new PrintStream(new DemuxOutputStream(project, false)));
        System.setErr(new PrintStream(new DemuxOutputStream(project, true)));
        project.fireBuildStarted();
	
	// configure the various aspects of this project and start the new experiment JVM
	Throwable caught = null;
        try {
	    // output debugging information through echo -- should be configured!!
	    Echo echo = new Echo();
	    echo.setTaskName("Echo");
	    echo.setProject(project);
	    echo.init();
	    echo.setMessage("Launching Some Class");
	    echo.execute();

	    // construct the java virtual machine task
	    Java javaTask = new Java();
	    
	    // add some vm args -- these should be configured!!
	    Argument jvmArgs = javaTask.createJvmarg();
	    jvmArgs.setLine("-Xms1024m -Xmx1024m");
	    
	    // added some args for the class to launch
	    for(String currentArgument : arguments) {
		Argument taskArgs = javaTask.createArg();
		taskArgs.setLine(currentArgument);
	    }
	    
	    // configure the java task 
	    javaTask.setTaskName("runexperiment");
	    javaTask.setProject(project);
	    javaTask.setFork(true);
	    javaTask.setFailonerror(true);
	    javaTask.setClassname(experimentClass.getName());
	    javaTask.setClasspath(Path.systemClasspath);
            
	    // initialize the javaTask and then execute it
	    javaTask.init();
	    returnCode = javaTask.executeJava();
	    //System.out.println("java task return code: " + returnCode);
        } 
	
	catch (BuildException e) {
                caught = e;
        }
        
	// finished running the java virtual machine in the project
	project.log("finished");
        project.fireBuildFinished(caught);
	return returnCode;
    }
}