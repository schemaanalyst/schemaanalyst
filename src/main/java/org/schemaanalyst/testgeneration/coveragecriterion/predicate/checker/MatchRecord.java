package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 11/03/2014.
 */
public class MatchRecord {

    private Row originalRow;
    private List<Row> modifiableComparisons;
    private List<Row> unmodifiableComparisons;

    public MatchRecord(Row originalRow) {
        this.originalRow = originalRow;
        modifiableComparisons = new ArrayList<>();
        unmodifiableComparisons = new ArrayList<>();
    }

    public Row getOriginalRow() {
        return originalRow;
    }

    public List<Row> getModifiableComparisons() {
        return new ArrayList<>(modifiableComparisons);
    }

    public List<Row> getUnmodifiableComparisons() {
        return new ArrayList<>(unmodifiableComparisons);
    }

    public void addModifiableComparison(Row row) {
        modifiableComparisons.add(row);
    }

    public void addUnmodifiableComparison(Row row) {
        unmodifiableComparisons.add(row);
    }

    public int getNumComparisonRows() {
        return modifiableComparisons.size() + unmodifiableComparisons.size();
    }

    public Row getComparisonRow(int index) {
        if (isModifiableIndex(index)) {
            return modifiableComparisons.get(index);
        } else if (isUnmodifiableIndex(index)) {
            return unmodifiableComparisons.get(index - modifiableComparisons.size());
        }

        throw new CheckerException("No comparison row at index " + index);
    }

    private boolean isModifiableIndex(int index) {
        return index >= 0 && index < modifiableComparisons.size();
    }

    private boolean isUnmodifiableIndex(int index) {
        return index < modifiableComparisons.size() + unmodifiableComparisons.size();
    }

    public boolean isModifiableRow(int index) {
        if (isModifiableIndex(index)) {
            return true;
        } else if (isUnmodifiableIndex(index)) {
            return false;
        }

        throw new CheckerException("No comparison row at index " + index);
    }
}
