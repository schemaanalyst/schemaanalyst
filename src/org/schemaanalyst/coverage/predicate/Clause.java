package org.schemaanalyst.coverage.predicate;

import org.schemaanalyst.coverage.predicate.function.Function;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class Clause {

    private List<Function> functions;

    public Clause() {
        functions = Collections.emptyList();
    }

    public void setFunctions(Function... functions) {
        this.functions = Arrays.asList(functions);
    }

    public String toString() {
        return "";
    }
}