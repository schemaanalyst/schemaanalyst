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


/**
 * Created by phil on 22/07/2014.
 */
public class ClauseAICC extends CondAICC {

    public ClauseAICC(Schema schema,
                      TestRequirementIDGenerator testRequirementIDGenerator,
                      ConstraintSupplier constraintSupplier) {
        super(schema, testRequirementIDGenerator, constraintSupplier);
    }

    protected void generateForeignKeyConstraintRequirements(ForeignKeyConstraint foreignKeyConstraint, boolean truthValue) {
        String descMsg = generateMsg(foreignKeyConstraint);

        if (truthValue) {
            generateOneNullRequirements(foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns(), descMsg);
            generateOneMatchRequirements(
                    foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns(),
                    foreignKeyConstraint.getReferenceTable(), foreignKeyConstraint.getReferenceColumns(), descMsg);
        } else {
            super.generateForeignKeyConstraintRequirements(foreignKeyConstraint, false);
        }
    }

    protected void generatePrimaryKeyConstraintRequirements(PrimaryKeyConstraint primaryKeyConstraint, boolean truthValue) {
        String descMsg = generateMsg(primaryKeyConstraint);

        if (truthValue) {
            generateOneMatchRequirements(primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns(), descMsg);
        } else {
            generateOneNullRequirements(primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns(), descMsg);
            super.generatePrimaryKeyConstraintRequirements(primaryKeyConstraint, false);
        }
    }

    protected void generateUniqueConstraintRequirements(UniqueConstraint uniqueConstraint, boolean truthValue) {
        String descMsg = generateMsg(uniqueConstraint);

        if (truthValue) {
            generateOneNullRequirements(uniqueConstraint.getTable(), uniqueConstraint.getColumns(), descMsg);
            generateOneMatchRequirements(uniqueConstraint.getTable(), uniqueConstraint.getColumns(), descMsg);
        } else {
            super.generateUniqueConstraintRequirements(uniqueConstraint, false);
        }
    }

    protected void generateOneNullRequirements(Table table, List<Column> columns, String descMsg) {
        for (Column majorColumn : columns) {
            AndPredicate predicate = new AndPredicate();
            predicate.addPredicate(new NullPredicate(table, majorColumn, true));
            for (Column minorColumn : columns) {
                if (!minorColumn.equals(majorColumn)) {
                    predicate.addPredicate(new NullPredicate(table, minorColumn, false));
                }
            }
            testRequirements.addTestRequirement(
                    testRequirementIDGenerator.nextID(),
                    descMsg + " " + majorColumn + " is NULL",
                    predicate);
        }
    }

    protected void generateOneMatchRequirements(Table table, List<Column> columns, String descMsg) {
        generateOneMatchRequirements(table, columns, table, columns, descMsg);
    }

    private void generateOneMatchRequirements(Table table, List<Column> columns, Table refTable, List<Column> refColumns, String descMsg) {
        Iterator<Column> colsIt = columns.iterator();
        Iterator<Column> refColsIt = refColumns.iterator();

        while (colsIt.hasNext()) {
            Column col = colsIt.next();
            Column refCol = refColsIt.next();

            List<Column> remainingCols = new ArrayList<>(columns);
            remainingCols.remove(col);

            List<Column> refRemainingCols = new ArrayList<>(refColumns);
            refRemainingCols.remove(refCol);

            testRequirements.addTestRequirement(
                    testRequirementIDGenerator.nextID(),
                    descMsg + " all equal except " + col,
                    new MatchPredicate(
                            table,
                            remainingCols,
                            Arrays.asList(col),
                            refTable,
                            refRemainingCols,
                            Arrays.asList(refCol),
                            MatchPredicate.Mode.AND));
        }
    }

}
