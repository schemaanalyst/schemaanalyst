package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by phil on 23/07/2014.
 */
public class TestRequirement {

    private List<TestRequirementDescriptor> descriptors;
    private Predicate predicate;

    public TestRequirement(Predicate predicate) {
        descriptors = new ArrayList<>();
        this.predicate = predicate;
    }

    public TestRequirement(TestRequirementDescriptor descriptor, Predicate predicate) {
        this(predicate);
        descriptors.add(descriptor);
    }

    public TestRequirement(String id, String description, Predicate predicate) {
        this(new TestRequirementDescriptor(id, description), predicate);
    }

    public void addDescriptor(TestRequirementDescriptor descriptor) {
        descriptors.add(descriptor);
    }

    public void addDescriptors(List<TestRequirementDescriptor> descriptors) {
        this.descriptors.addAll(descriptors);
    }

    public List<TestRequirementDescriptor> getDescriptors() {
        return new ArrayList<>(descriptors);
    }

    public Predicate getPredicate() {
        return predicate;
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
        return str;
    }
}
