package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

import java.io.Serializable;
import java.util.*;

/**
 * Created by phil on 23/07/2014.
 */
public class TestRequirement implements Comparable<TestRequirement>, Serializable {

    private static final long serialVersionUID = 6777745015314686157L;

    // NB if any changes are made to these fields, check the algorithm for reduce() in
    // TestRequirements, which compares predicates only and not other fields
    private List<TestRequirementDescriptor> descriptors;
    private Predicate predicate;
    private Boolean result;
    private boolean requiresComparisonRow;

    public TestRequirement(TestRequirementDescriptor descriptor,
                           Predicate predicate,
                           Boolean result,
                           boolean requiresComparisonRow) {
        descriptors = new ArrayList<>();
        descriptors.add(descriptor);
        this.predicate = predicate;
        this.result = result;
        this.requiresComparisonRow = requiresComparisonRow;
    }

    public void addDescriptor(TestRequirementDescriptor descriptor) {
        descriptors.add(descriptor);
        Collections.sort(descriptors);
    }

    public void addDescriptors(List<TestRequirementDescriptor> descriptorsToAdd) {
        for (TestRequirementDescriptor descriptor : descriptorsToAdd) {
            addDescriptor(descriptor);
        }
    }

    public List<TestRequirementDescriptor> getDescriptors() {
        return new ArrayList<>(descriptors);
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public Boolean getResult() {
        return result;
    }

    public void setRequiresComparisonRow(boolean requiresComparisonRow) {
        this.requiresComparisonRow = requiresComparisonRow;
    }

    public boolean getRequiresComparisonRow() {
        return requiresComparisonRow;
    }

    public Set<Table> getTables() {
        return new PredicateAdaptor() {
            Set<Table> tables = new HashSet<>();

            Set<Table> getTables(Predicate predicate) {
                tables = new HashSet<>();
                predicate.accept(this);
                return tables;
            }

            @Override
            public void visit(ExpressionPredicate predicate) {
                tables.add(predicate.getTable());
            }

            @Override
            public void visit(MatchPredicate predicate) {
                tables.add(predicate.getTable());
            }

            @Override
            public void visit(NullPredicate predicate) {
                tables.add(predicate.getTable());
            }
        }.getTables(predicate);
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean reduce) {
        String str = "";
        for (TestRequirementDescriptor trd : descriptors) {
            str += trd + "\n";
        }

        Predicate p = predicate;
        if (reduce) {
            p = p.reduce();
        }
        str += p;

        str += "\nRequires comparison row: " + requiresComparisonRow;

        str += "\nResult: " + result;

        return str;
    }

    @Override
    public int compareTo(TestRequirement other) {
        if (descriptors.size() == 0) {
            if (other.descriptors.size() > 0) {
                // other has descriptors
                return -1;
            } else {
                // neither have descriptors
                return 0;
            }
        } else if (other.descriptors.size() == 0) {
            // this has descriptors, others does not
            return 1;
        }

        return descriptors.get(0).compareTo(other.descriptors.get(0));
    }
}
