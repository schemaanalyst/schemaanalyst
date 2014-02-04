package org.schemaanalyst.coverage.criterion;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 03/02/2014.
 */
public class MultiCriterion extends Criterion {

    private List<Criterion> criteria;

    public MultiCriterion(Criterion... criteria) {
        this.criteria = Arrays.asList(criteria);
    }

    @Override
    public List<Predicate> generateRequirements(Schema schema, Table table) {
        List<Predicate> requirements = new ArrayList<>();

        for (Criterion criterion : criteria) {
            requirements.addAll(criterion.generateRequirements(schema, table));
        }

        return requirements;
    }
}
