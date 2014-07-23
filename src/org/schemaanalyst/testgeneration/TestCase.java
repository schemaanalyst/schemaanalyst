package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.logic.predicate.Predicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestCase {

    private Data data, state;
    private Predicate originalPredicate;
    private List<Predicate> additionalPredicates;
    private DataGenerationReport dataGenerationReport;
    private List<Boolean> dbmsResults;

    public TestCase(Data data, Data state, Predicate predicate, DataGenerationReport dataGenerationReport) {
        this.data = data;
        this.state = state;
        this.dbmsResults = new ArrayList<>();
        this.originalPredicate = predicate;
        this.dataGenerationReport = dataGenerationReport;

        this.additionalPredicates = new ArrayList<>();
    }

    public Data getData() {
        return data;
    }

    public Data getState() {
        return state;
    }

    public Predicate getOriginalPredicate() {
        return originalPredicate;
    }

    public boolean satisfiesOriginalPredicate() {
        return dataGenerationReport.isSuccess();
    }

    public void addPredicate(Predicate predicate) {
        additionalPredicates.add(predicate);
    }

    public List<Predicate> getPredicates() {
        List<Predicate> allPredicates = new ArrayList<>();
        allPredicates.add(originalPredicate);
        allPredicates.addAll(additionalPredicates);
        return allPredicates;
    }

    public int getNumSatisfiedPredicates() {
        return (satisfiesOriginalPredicate() ? 1 : 0) + additionalPredicates.size();
    }

    public void setDBMSResults(List<Boolean> dbmsResults) {
        this.dbmsResults = new ArrayList<>(dbmsResults);
    }

    public List<Boolean> getDBMSResults() {
        return new ArrayList<>(dbmsResults);
    }

    public int getNumInserts() {
        return state.getNumRows() + data.getNumRows();
    }

    public DataGenerationReport getDataGenerationReport() {
        return dataGenerationReport;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        // print purposes and predicates attempted/satisfied by this test case
        boolean first = true;
        for (Predicate predicate : getPredicates()) {
            if (first) {
                first = false;
            } else {
                sb.append("\n");
            }

            // details about the purpose of the predicate
            sb.append("PURPOSE:\t");
            List<String> purposes = predicate.getPurposes();
            if (purposes.size() == 1) {
                sb.append(purposes.get(0));
            } else {
                for (String purpose : purposes) {
                    sb.append("\n\t* " + purpose);
                }
            }

            // the actual predicate
            sb.append("\nPREDICATE:\t" + predicate);
        }

        // details about the contents of the state
        Data state = getState();
        if (state.getCells().size() > 0) {
            sb.append("\nSTATE:\n" + getState());
        }

        // details about the contents of the test case data
        sb.append("\nDATA:\n" + getData());

        if (dbmsResults.size() > 0) {
            sb.append("\nRESULTS:\n " + dbmsResults);
        }

        return sb.toString();
    }
}
