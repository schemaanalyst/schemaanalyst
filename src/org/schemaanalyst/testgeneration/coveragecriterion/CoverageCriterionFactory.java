package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator.IDType.TABLE;

/**
 * Created by phil on 23/07/2014.
 */
public class CoverageCriterionFactory {

    /**
     * Instantiate an Integrity Constraint Coverage Criterion given the name.
     *
     * @param criterionName The name
     * @param schema The schema
     * @return The criterion
     */
    @SuppressWarnings("unchecked")
    public static CoverageCriterion schemaCriterion(String criterionName, Schema schema) {
        Class<CoverageCriterionFactory> c = CoverageCriterionFactory.class;
        Method methods[] = c.getMethods();

        for (Method m : methods) {
            if (m.getName().equals("criterion" + criterionName)) {
                try {
                    Object[] args = {schema};
                    return (CoverageCriterion) m.invoke(null, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new CoverageCriterionException("Unknown criterion \"" + criterionName + "\"");
    }

    public static CoverageCriterion criterionAPC(Schema schema) {
        return new APC(schema, new TestRequirementIDGenerator(TABLE), new ICMinimalConstraintSupplier());
    }

    public static CoverageCriterion criterionICC(Schema schema) {
        return new ICC(schema, new TestRequirementIDGenerator(TABLE), new ICMinimalConstraintSupplier());
    }

    public static CoverageCriterion criterionAICC(Schema schema) {
        return new AICC(schema, new TestRequirementIDGenerator(TABLE), new ICMinimalConstraintSupplier());
    }

    public static CoverageCriterion criterionCondAICC(Schema schema) {
        return new CondAICC(schema, new TestRequirementIDGenerator(TABLE), new ICMinimalConstraintSupplier());
    }

    public static CoverageCriterion criterionClauseAICC(Schema schema) {
        return new ClauseAICC(schema, new TestRequirementIDGenerator(TABLE), new ICMinimalConstraintSupplier());
    }
}
