package org.schemaanalyst.testgeneration.coveragecriterion.predicate;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Table;

/**
 * Created by phil on 18/07/2014.
 */
public class OrPredicate extends ComposedPredicate {

    public OrPredicate(Table table) {
        super(table);
    }

    public String toString() {
        return "(" + StringUtils.join(predicates, " \u2228 ") + ")";
    }
}
