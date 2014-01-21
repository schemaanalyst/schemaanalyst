package org.schemaanalyst.coverage.predicate;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.coverage.predicate.function.Function;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class Clause {

    private Constraint relatedConstraint;
    private Function function;

    public Clause(Constraint relatedConstraint, Function function) {
        this.relatedConstraint = relatedConstraint;
        this.function = function;
    }

    public Constraint getRelatedConstraint() {
        return relatedConstraint;
    }

    public String toString() {
        String str = "";
        if (relatedConstraint != null) {
            str += "{" + relatedConstraint + " ";
        }
        str += function.toString();

        if (relatedConstraint != null) {
            str += "}";
        }
        return str;
    }
}