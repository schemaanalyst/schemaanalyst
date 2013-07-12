package experiment.test;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

import experiment.ExperimentalResults;
import experiment.ExperimentalResult;
import experiment.ExperimentConfiguration;
import experiment.ExperimentUtilities;

public class TestExperimentalResults {

    @Test
    public void initialConditionsOfExperimentalResults() {
        ExperimentalResults eresults = new ExperimentalResults();
        List<String> header = eresults.getHeader();
        ExperimentalResult result = eresults.getExperimentalResult();
        List<String> myresult = result.getResults();
        int expectedLength = 0;
        assertEquals("The header should be empty",
                expectedLength,
                header.size());
        assertEquals("The experimental results should be empty",
                expectedLength,
                myresult.size());
    }

    @Test
    public void addAnExperimentResultAndCheckLocation() {
        ExperimentalResult eresult = new ExperimentalResult();
        eresult.addResult(0, "score0");
        eresult.addResult(1, "score1");
        List<String> results = eresult.getResults();
        assertEquals("score0", results.get(0));
        assertEquals("score1", results.get(1));
    }

    @Test
    public void createExperimentalResultsAtCorrectIndex() {
        ExperimentalResults eresults = new ExperimentalResults();
        eresults.addResult("executiontime", "20");
        eresults.addResult("mutationscorenumerator", "30");
        ExperimentalResult eresult = eresults.getExperimentalResult();
        List<String> results = eresult.getResults();
        assertEquals("20", results.get(0));
        assertEquals("30", results.get(1));
    }

    @Test
    public void checkWroteHeaderFlag() {
        // load the configuration for the experiments
        ExperimentConfiguration.project = System.getProperty("project");
        ExperimentalResults eresults = new ExperimentalResults();
        assertEquals(false, eresults.wroteHeader());
        eresults.writeResults();
        assertEquals(true, eresults.wroteHeader());
    }

    @Test
    public void checkSaveAndRetrieveExperimentalResults() {
        ExperimentalResults eresults = new ExperimentalResults();
        // load the configuration for the experiments
        ExperimentConfiguration.project = System.getProperty("project");
        eresults.save();
        ExperimentalResults eresultsRestored = ExperimentalResults.retrieve();
        assertEquals(eresults.wroteHeader(), eresultsRestored.wroteHeader());

        eresults.writeResults();
        eresults.save();
        eresultsRestored = ExperimentalResults.retrieve();
        assertEquals(eresults.wroteHeader(), eresultsRestored.wroteHeader());
    }

    @Test
    public void ensureThatResetWorksCorrectly() {
        ExperimentalResults eresults = new ExperimentalResults();
        eresults.addResult("executiontime", "20");
        eresults.addResult("mutationscorenumerator", "30");
        ExperimentalResult eresult = eresults.getExperimentalResult();
        List<String> results = eresult.getResults();
        assertEquals("20", results.get(0));
        assertEquals("30", results.get(1));
        eresults.reset();
        eresult = eresults.getExperimentalResult();
        results = eresult.getResults();
        assertEquals(0, results.size());
        assertEquals(0, eresults.getHeader().size());
    }

    @Test
    public void testCorrectConversionOfListStringToString() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("first");
        list.add("second");
        list.add("20");
        String expectedListString = new String("first, second, 20");
        String actualListString = ExperimentUtilities.convertListToCsv(list);
        assertEquals(expectedListString, actualListString);
    }

    @Test
    public void testConversionOfTrialValueToParameter() {
        String trial = ((new StringBuffer()).append(20)).toString();
        String trialParameter = new String("--trial=20");
        assertEquals(trialParameter, ExperimentUtilities.convertTrialToParameter(trial));
    }

    @Test
    public void testConversionOfScriptfileValueToParameter() {
        String trial = ((new StringBuffer()).append("casestudy.BankAccount.postgres.sql")).toString();
        String trialParameter = new String("--scriptfile=casestudy.BankAccount.postgres.sql");
        assertEquals(trialParameter, ExperimentUtilities.convertScriptfilelToParameter(trial));
    }
}
