package org.schemaanalyst.testgeneration.criterion.predicate;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by phil on 18/07/2014.
 */
public class AndPredicate extends ComposedPredicate {

    public String toString() {
        return StringUtils.join(predicates, " \u2227 ");
    }
}
