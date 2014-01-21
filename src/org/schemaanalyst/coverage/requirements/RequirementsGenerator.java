package org.schemaanalyst.coverage.requirements;

import org.schemaanalyst.coverage.predicate.Predicate;

import java.util.LinkedHashSet;

/**
 * Created by phil on 20/01/2014.
 */
public abstract class RequirementsGenerator {

    public abstract LinkedHashSet<Predicate> generateRequirements();
}
