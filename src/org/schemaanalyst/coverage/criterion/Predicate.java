package org.schemaanalyst.coverage.criterion;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.coverage.criterion.clause.Clause;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class Predicate {

    private String purpose;
    private LinkedHashSet<Clause> clauses;

    public Predicate(String purpose) {
        this.purpose = purpose;
        this.clauses = new LinkedHashSet<>();
    }

    public void addClause(Clause clause) {
        clauses.add(clause);
    }

    public void addClauses(Predicate predicate) {
        clauses.addAll(predicate.clauses);
    }

    public List<Clause> getClauses() {
        return new ArrayList<>(clauses);
    }

    public String getPurpose() {
        return purpose;
    }

    public String toString() {
        return StringUtils.join(clauses, " \u2227 ");
    }
}
