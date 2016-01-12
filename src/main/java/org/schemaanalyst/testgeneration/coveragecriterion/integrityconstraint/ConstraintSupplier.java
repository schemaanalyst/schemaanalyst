package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.List;

/**
 * Created by phil on 27/07/2014.
 */
public class ConstraintSupplier {

    public List<Constraint> getConstraints(Schema schema, Table table) {
        return schema.getConstraints(table);
    }

}
