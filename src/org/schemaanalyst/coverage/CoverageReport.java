package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.criterion.predicate.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 24/02/2014.
 */
public class CoverageReport {

    private List<Predicate> covered;
    private List<Predicate> uncovered;

    public CoverageReport() {
        covered = new ArrayList<>();
        uncovered = new ArrayList<>();
    }

    public void addCovered(Predicate predicate) {
        covered.add(predicate);
    }

    public void addUncovered(Predicate predicate) {
        uncovered.add(predicate);
    }

    public List<Predicate> getCovered() {
        return new ArrayList<>(covered);
    }

    public List<Predicate> getUncovered() {
        return new ArrayList<>(uncovered);
    }

    public double getCoverage() {
        return covered.size() / (double) (covered.size() + uncovered.size());
    }
}
