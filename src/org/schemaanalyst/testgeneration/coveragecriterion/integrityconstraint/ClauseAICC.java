package org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator.addNullPredicates;
import static org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator.generateMultiColumnConstraintConditionPredicate;


/**
 * Created by phil on 22/07/2014.
 */
public class ClauseAICC extends CondAICC {

    public ClauseAICC(Schema schema,
                      TestRequirementIDGenerator testRequirementIDGenerator,
                      ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator, constraintSupplier);
    }

    public String getName() {
        return "Clause-AICC";
    }

    protected void generateForeignKeyConstraintRequirements(ForeignKeyConstraint constraint, boolean truthValue) {
        if (truthValue) {
            generateOneNullRequirements(constraint);
            generateAllButOneMatchRequirements(constraint);
        } else {
            generateAllMatchRequirement(constraint);
        }
    }

    protected void generatePrimaryKeyConstraintRequirements(PrimaryKeyConstraint constraint, boolean truthValue) {
        if (truthValue) {
            generateAllButOneMatchRequirements(constraint);
        } else {
            generateOneNullRequirements(constraint);
            generateAllMatchRequirement(constraint);
        }
    }

    protected void generateUniqueConstraintRequirements(UniqueConstraint constraint, boolean truthValue) {
        if (truthValue) {
            generateOneNullRequirements(constraint);
            generateAllButOneMatchRequirements(constraint);
        } else {
            generateAllMatchRequirement(constraint);
        }
    }

    protected void generateOneNullRequirements(MultiColumnConstraint constraint) {
        Table table = constraint.getTable();
        List<Column> columns = constraint.getColumns();

        for (Column majorColumn : columns) {
            AndPredicate predicate = new AndPredicate();
            predicate.addPredicate(new NullPredicate(table, majorColumn, true));
            for (Column minorColumn : columns) {
                if (!minorColumn.equals(majorColumn)) {
                    predicate.addPredicate(new NullPredicate(table, minorColumn, false));
                }
            }
            String msgSuffix = " - " + majorColumn + " is NULL";
            generateTestRequirement(constraint, msgSuffix, predicate);
        }
    }

    protected void generateAllMatchRequirement(MultiColumnConstraint constraint) {
        generateTestRequirement(
                constraint,
                " - all cols equal",
                generateMultiColumnConstraintConditionPredicate(constraint, true, false));
    }

    private void generateAllButOneMatchRequirements(MultiColumnConstraint constraint) {
        Table table = constraint.getTable();
        List<Column> columns = constraint.getColumns();

        Table refTable = table;
        List<Column> refColumns = columns;
        if (constraint instanceof ForeignKeyConstraint) {
            ForeignKeyConstraint foreignKeyConstraint = (ForeignKeyConstraint) constraint;
            refTable = foreignKeyConstraint.getReferenceTable();
            refColumns = foreignKeyConstraint.getReferenceColumns();
        }

        Iterator<Column> colsIt = columns.iterator();
        Iterator<Column> refColsIt = refColumns.iterator();

        while (colsIt.hasNext()) {
            Column col = colsIt.next();
            Column refCol = refColsIt.next();

            List<Column> remainingCols = new ArrayList<>(columns);
            remainingCols.remove(col);

            List<Column> refRemainingCols = new ArrayList<>(refColumns);
            refRemainingCols.remove(refCol);

            ComposedPredicate predicate = new AndPredicate();

            Predicate matchPredicate = new MatchPredicate(
                    table,
                    remainingCols,
                    Arrays.asList(col),
                    refTable,
                    refRemainingCols,
                    Arrays.asList(refCol),
                    MatchPredicate.Mode.AND);

            predicate.addPredicate(matchPredicate);
            addNullPredicates(predicate, table, columns, false);

            String msgSuffix = " - all cols equal except " + col;

            generateTestRequirement(constraint, msgSuffix, predicate);
        }
    }

}
