package experiment;

import java.util.List;

public class ExperimentUtilities {

    /** Convert a List into a CSV line for a data file */
    public static String convertListToCsv(List<String> dataList) {
	StringBuffer values = new StringBuffer();
	boolean firstTime = true;
	for(String data: dataList) {
	    if(firstTime) {
		values.append(data);
		firstTime=false;
	    }
	    else {
		values.append(", ");
		values.append(data);
	    }
	}
	return values.toString();
    }

    /** Convert a single number into a trials parameter for passing to the main running program */
    public static String convertTrialToParameter(String trial) {
	StringBuffer trialParameter = new StringBuffer();
	trialParameter.append("--trial=");
	trialParameter.append(trial);
	return trialParameter.toString();
    }

    /** Convert a scriptfile to a parameter */
    public static String convertScriptfilelToParameter(String scriptfile) {
	StringBuffer scriptfileParameter = new StringBuffer();
	scriptfileParameter.append("--scriptfile=");
	scriptfileParameter.append(scriptfile);
	return scriptfileParameter.toString();
    }
}