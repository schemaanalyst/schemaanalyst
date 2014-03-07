package org.schemaanalyst.coverage.criterion;

import org.schemaanalyst.coverage.criterion.requirements.Requirements;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 03/02/2014.
 */
public class MultiCriterion extends Criterion {

    private List<Criterion> criteria;

    public MultiCriterion(Criterion... criteria) {
        super(null);
        this.criteria = Arrays.asList(criteria);
    }

    public String getName() {
        if (name == null) {
            name = "";
            boolean first = true;
            for (Criterion criterion : criteria) {
                if (first) {
                    first = false;
                } else {
                    name += " + ";
                }
                name += criterion.getName();
            }
        }
        return name;
    }

    @Override
    public Requirements generateRequirements(Schema schema, Table table) {
        Requirements requirements = new Requirements();

        for (Criterion criterion : criteria) {
            requirements.addPredicates(criterion.generateRequirements(schema, table));
        }

        return requirements;
    }
}
