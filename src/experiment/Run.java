package experiment;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/** This class is the stand-in for running SchemaAnalyst itself. */
public class Run {
	
    public static void call(String argument1, String argument2) {
	ArrayList<String> arguments = new ArrayList<String>();
	arguments.add(argument1);
	arguments.add(argument2);
	String[] argumentsAsArray = arguments.toArray(new String[arguments.size()]);
	Run.main(argumentsAsArray);
    }

    public static void main(String[] args) {
	List<String> arguments = Arrays.asList(args);
	System.out.println("arguments: " + arguments);
	if(arguments.contains("fail")) {
	    System.out.println("I am going to fail!!");
	    System.exit(1);
	}
	else if(arguments.contains("runtime")) {
	    System.out.println("Runtime exception!!");
	    throw new RuntimeException("Bad thing!");
	}
	else if(arguments.contains("sleep")) {
	    try {
		//Pause for 1 second
		Thread.sleep(1000);
	    }
	    catch(InterruptedException e) {
		
	    }
	}
    }
}

