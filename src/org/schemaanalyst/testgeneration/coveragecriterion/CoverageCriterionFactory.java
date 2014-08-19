package org.schemaanalyst.testgeneration.coveragecriterion;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.column.ANCC;
import org.schemaanalyst.testgeneration.coveragecriterion.column.AUCC;
import org.schemaanalyst.testgeneration.coveragecriterion.column.NCC;
import org.schemaanalyst.testgeneration.coveragecriterion.column.UCC;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
    public static CoverageCriterion instantiateSchemaCriterion(String criterionName, Schema schema, DBMS dbms) {
        TestRequirementIDGenerator testRequirementIDGenerator = new TestRequirementIDGenerator(TABLE);
        ConstraintSupplier constraintSupplier = ConstraintSupplierFactory.instantiateConstraintSupplier(dbms);

        String[] names = criterionName.split("\\+");
        if (names.length == 0) {
            return instantiateSchemaCriterionFromName(criterionName, schema, testRequirementIDGenerator, constraintSupplier);
        } else {
            List<CoverageCriterion> criteria = new ArrayList<>();
            for (String name : names) {
                criteria.add(instantiateSchemaCriterionFromName(name, schema, testRequirementIDGenerator, constraintSupplier));
            }
            return new MultiCoverageCriteria(criteria);
        }
    }

    private static CoverageCriterion instantiateSchemaCriterionFromName(
            String criterionName, Schema schema,
            TestRequirementIDGenerator testRequirementIDGenerator,
            ConstraintSupplier constraintSupplier) {
        Class<CoverageCriterionFactory> c = CoverageCriterionFactory.class;
        Method methods[] = c.getMethods();
        for (Method m : methods) {
            if (m.getName().equals("criterion" + criterionName)) {
                try {
                    Object[] args = {schema, testRequirementIDGenerator, constraintSupplier};
                    return (CoverageCriterion) m.invoke(null, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new CoverageCriterionException("Unknown criterion \"" + criterionName + "\"");
    }

    public static CoverageCriterion criterionAPC(
            Schema schema, TestRequirementIDGenerator testRequirementIDGenerator,ConstraintSupplier constraintSupplier) {
        return new APC(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public static CoverageCriterion criterionICC(
            Schema schema, TestRequirementIDGenerator testRequirementIDGenerator,ConstraintSupplier constraintSupplier) {
        return new ICC(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public static CoverageCriterion criterionAICC(
            Schema schema, TestRequirementIDGenerator testRequirementIDGenerator,ConstraintSupplier constraintSupplier) {
        return new AICC(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public static CoverageCriterion criterionCondAICC(
            Schema schema, TestRequirementIDGenerator testRequirementIDGenerator,ConstraintSupplier constraintSupplier) {
        return new CondAICC(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public static CoverageCriterion criterionClauseAICC(
            Schema schema, TestRequirementIDGenerator testRequirementIDGenerator,ConstraintSupplier constraintSupplier) {
        return new ClauseAICC(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public static CoverageCriterion criterionNCC(
            Schema schema, TestRequirementIDGenerator testRequirementIDGenerator,ConstraintSupplier constraintSupplier) {
        return new NCC(schema, testRequirementIDGenerator);
    }

    public static CoverageCriterion criterionANCC(
            Schema schema, TestRequirementIDGenerator testRequirementIDGenerator,ConstraintSupplier constraintSupplier) {
        return new ANCC(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public static CoverageCriterion criterionUCC(
            Schema schema, TestRequirementIDGenerator testRequirementIDGenerator,ConstraintSupplier constraintSupplier) {
        return new UCC(schema, testRequirementIDGenerator);
    }

    public static CoverageCriterion criterionAUCC(
            Schema schema, TestRequirementIDGenerator testRequirementIDGenerator,ConstraintSupplier constraintSupplier) {
        return new AUCC(schema, testRequirementIDGenerator, constraintSupplier);
    }
}
