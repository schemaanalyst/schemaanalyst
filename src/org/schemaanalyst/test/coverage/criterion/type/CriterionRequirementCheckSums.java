package org.schemaanalyst.test.coverage.criterion.type;

import org.junit.Test;
import org.schemaanalyst.coverage.criterion.Criterion;
import org.schemaanalyst.coverage.criterion.types.*;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import parsedcasestudy.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by phil on 24/02/2014.
 */
public class CriterionRequirementCheckSums {

    @Test
    public void checkSumWithSchemas() {
        checkSumWithSchemaForCriteria(new BankAccount());
        checkSumWithSchemaForCriteria(new Cloc());
        checkSumWithSchemaForCriteria(new CoffeeOrders());
        checkSumWithSchemaForCriteria(new DellStore());
        checkSumWithSchemaForCriteria(new FrenchTowns());
        checkSumWithSchemaForCriteria(new NistDML181());
        checkSumWithSchemaForCriteria(new NistDML182());
        checkSumWithSchemaForCriteria(new NistDML183());
    }

    protected void checkSumWithSchemaForCriteria(Schema schema) {
        amplifiedConstraintCACCoverage(schema);
        constraintCACCoverage(schema);
        constraintCoverage(schema);
        nullColumnCACCoverage(schema);
        nullColumnCoverage(schema);
        uniqueColumnCACCoverage(schema);
        uniqueColumnCoverage(schema);
    }

    protected void amplifiedConstraintCACCoverage(Schema schema) {
        int sum = 0;

        for (PrimaryKeyConstraint pk : schema.getPrimaryKeyConstraints()) {
            sum += (pk.getNumColumns() * 2) + 1;
        }

        for (ForeignKeyConstraint fk : schema.getForeignKeyConstraints()) {
            sum += (fk.getNumColumns() * 2) + 1;
        }

        for (UniqueConstraint uc : schema.getUniqueConstraints()) {
            sum += (uc.getNumColumns() * 2) + 1;
        }

        for (NotNullConstraint nn : schema.getNotNullConstraints()) {
            sum += 2;
        }

        for (CheckConstraint cc : schema.getCheckConstraints()) {
            // harder to go figure ...
        }

        assertCheckSum(schema, new AmplifiedConstraintCACCoverage(), sum);
    }

    protected void constraintCACCoverage(Schema schema) {
        // All constraints are made TRUE, FALSE and NULL except NOT NULL constraints
        // which are just TRUE and FALSE.

        int sum = (schema.getConstraints().size() * 3) - schema.getNotNullConstraints().size();

        assertCheckSum(schema, new ConstraintCACCoverage(), sum);
    }

    protected void constraintCoverage(Schema schema) {
        // All constraints are made TRUE, FALSE and NULL except NOT NULL constraints
        // which are just TRUE and FALSE.

        int sum = (schema.getConstraints().size() * 3) - schema.getNotNullConstraints().size();

        assertCheckSum(schema, new ConstraintCoverage(), sum);
    }

    protected void nullColumnCACCoverage(Schema schema) {
        assertCheckSum(schema, new NullColumnCACCoverage(), numCols(schema) * 2);
    }

    protected void nullColumnCoverage(Schema schema) {
        assertCheckSum(schema, new NullColumnCoverage(), numCols(schema) * 2);
    }

    protected void uniqueColumnCACCoverage(Schema schema) {
        assertCheckSum(schema, new UniqueColumnCACCoverage(), numCols(schema) * 2);
    }

    protected void uniqueColumnCoverage(Schema schema) {
        assertCheckSum(schema, new UniqueColumnCoverage(), numCols(schema) * 2);
    }

    private int numCols(Schema schema) {
        int numCols = 0;

        for (Table table : schema.getTables()) {
            numCols += table.getColumns().size();
        }

        return numCols;
    }

    private void assertCheckSum(Schema schema, Criterion criterion, int sum) {
        assertEquals(sum, criterion.generateRequirements(schema).size());
    }
}
