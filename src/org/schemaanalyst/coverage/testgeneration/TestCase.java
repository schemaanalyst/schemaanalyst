package org.schemaanalyst.coverage.testgeneration;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestCase {

    private Data data, state;
    private List<Boolean> dbmsResults;
    private List<Predicate> predicates;
    private HashMap<String, Object> info;

    public TestCase(Data data, Data state, Predicate... predicates) {
        this.data = data;
        this.state = state;
        this.dbmsResults = new ArrayList<>();
        this.predicates = Arrays.asList(predicates);
        this.info = new HashMap<>();
    }

    public Data getData() {
        return data;
    }

    public Data getState() {
        return state;
    }

    public void addPredicate(Predicate predicate) {
        predicates.add(predicate);
    }

    public List<Predicate> getPredicates() {
        return new ArrayList<>(predicates);
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

    public String toString() {
        return StringUtils.join(predicates, "\n") + "\n" + data;
    }
}
