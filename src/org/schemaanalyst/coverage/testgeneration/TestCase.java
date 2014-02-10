package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.data.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestCase {

    private Data data, state;
    private Predicate originalPredicate;
    private boolean satisfiesOriginalPredicate;

    private List<Predicate> additionalPredicates;
    private List<Boolean> dbmsResults;

    private HashMap<String, Object> info;

    public TestCase(Data data, Data state, Predicate predicate, boolean satisfies) {
        this.data = data;
        this.state = state;
        this.dbmsResults = new ArrayList<>();
        this.originalPredicate = predicate;
        this.satisfiesOriginalPredicate = satisfies;

        this.additionalPredicates = new ArrayList<>();
        this.info = new HashMap<>();
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
        return satisfiesOriginalPredicate;
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
        return (satisfiesOriginalPredicate ? 1 : 0) + additionalPredicates.size();
    }

    public void setDBMSResults(List<Boolean> dbmsResults) {
        this.dbmsResults = new ArrayList<>(dbmsResults);
    }

    public List<Boolean> getDBMSResults() {
        return new ArrayList<>(dbmsResults);
    }

    public void addInfo(String key, Object infoObject) {
        info.put(key, infoObject);
    }

    public Object getInfo(String key) {
        return info.get(key);
    }

    public int getNumInserts() {
        return state.getNumRows() + data.getNumRows();
    }
}
