package org.schemaanalyst.testgeneration.coveragecriterion_old;

import org.schemaanalyst.testgeneration.CoverageException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Instantiates a {@link org.schemaanalyst.testgeneration.coveragecriterion_old.CoverageCriterion} for use in data generation.
 *
 * @author Phil
 */
public class CoverageCriterionFactory {

    /**
     * Instantiate a {@link org.schemaanalyst.testgeneration.coveragecriterion_old.CoverageCriterion} instance given the name.
     *
     * @param criterionName The name
     * @return The criterion
     */
    @SuppressWarnings("unchecked")
    public static CoverageCriterion instantiate(String criterionName) {
        Class<CoverageCriterionFactory> c = CoverageCriterionFactory.class;
        Method methods[] = c.getMethods();

        for (Method m : methods) {
            if (m.getName().equals(criterionName)) {
                try {
                    return (CoverageCriterion) m.invoke(null);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new CoverageException("Unknown criterion \"" + criterionName + "\"");
    }

    /**
     * Returns a list of all {@link org.schemaanalyst.testgeneration.coveragecriterion_old.CoverageCriterion}.
     *
     * @return All criteria
     */
    public static List<CoverageCriterion> allCriteria() {
        List<CoverageCriterion> criteria = new ArrayList<>();
        Class<CoverageCriterionFactory> c = CoverageCriterionFactory.class;
        Method methods[] = c.getMethods();

        for (Method m : methods) {
            if (m.getName().endsWith("Coverage")) {
                try {
                    criteria.add((CoverageCriterion) m.invoke(null));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Collections.sort(criteria, new Comparator<CoverageCriterion>() {
            @Override
            public int compare(CoverageCriterion c1, CoverageCriterion c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });

        return criteria;
    }

    public static CoverageCriterion amplifiedConstraintCACCoverage() {
        return new AmplifiedConstraintCACCoverage();
    }

    public static CoverageCriterion amplifiedConstraintCACWithNullAndUniqueColumnCACCoverage() {
        return new MultiCoverageCriterion(
                new AmplifiedConstraintCACCoverage(),
                new NullColumnCACCoverage(),
                new UniqueColumnCACCoverage()
        );
    }

    public static CoverageCriterion amplifiedConstraintCACWithNullAndUniqueColumnCoverage() {
        return new MultiCoverageCriterion(
                new AmplifiedConstraintCACCoverage(),
                new NullColumnCoverage(),
                new UniqueColumnCoverage()
        );
    }

    public static CoverageCriterion constraintCACCoverage() {
        return new ConstraintCACCoverage();
    }

    public static CoverageCriterion constraintCACWithNullAndUniqueColumnCACCoverage() {
        return new MultiCoverageCriterion(
                new ConstraintCACCoverage(),
                new NullColumnCACCoverage(),
                new UniqueColumnCACCoverage()
        );
    }

    public static CoverageCriterion nullAndUniqueColumnCACCoverage() {
        return new MultiCoverageCriterion(
                new NullColumnCACCoverage(),
                new UniqueColumnCACCoverage()
        );
    }

    public static CoverageCriterion constraintCACWithNullAndUniqueColumnCoverage() {
        return new MultiCoverageCriterion(
                new ConstraintCACCoverage(),
                new NullColumnCoverage(),
                new UniqueColumnCoverage()
        );
    }

    public static CoverageCriterion constraintCoverage() {
        return new ConstraintCoverage();
    }

    public static CoverageCriterion nullColumnCoverage() {
        return new NullColumnCoverage();
    }

    public static CoverageCriterion nullColumnCACCoverage() {
        return new NullColumnCACCoverage();
    }

    public static CoverageCriterion uniqueColumnCoverage() {
        return new UniqueColumnCoverage();
    }

    public static CoverageCriterion uniqueColumnCACCoverage() {
        return new UniqueColumnCACCoverage();
    }
}
