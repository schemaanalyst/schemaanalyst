package org.schemaanalyst.coverage.testgeneration;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestCase extends StatisticStore {

    private Data data;
    private Data state;
    private List<Predicate> predicates;

    public TestCase(Data data, Data state, Predicate... predicates) {
        this.data = data;
        this.state = state;
        this.predicates = Arrays.asList(predicates);
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

    public String toString() {
        return StringUtils.join(predicates, "\n") + "\n" + data;
    }
}
