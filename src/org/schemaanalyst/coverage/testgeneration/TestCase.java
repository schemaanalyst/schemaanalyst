package org.schemaanalyst.coverage.testgeneration;

import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.data.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class TestCase extends Statistics {

    private Data data;
    private List<Predicate> predicates;

    public TestCase(Data data) {
        this.data = data;
        predicates = new ArrayList<>();
    }

    public void addPredicate(Predicate predicate) {
        predicates.add(predicate);
    }

    public List<Predicate> getPredicates() {
        return new ArrayList<>(predicates);
    }

    public Data getData() {
        return data;
    }
}
