package org.schemaanalyst.testgeneration.criterion.integrityconstraint;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.*;
import org.schemaanalyst.testgeneration.criterion.predicate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * Created by phil on 22/07/2014.
 */
public class ClauseAICC extends CondAICC {

    public ClauseAICC(Schema schema) {
        super(schema);
    }

    protected void generateForeignKeyConstraintRequirements(ForeignKeyConstraint foreignKeyConstraint, boolean truthValue) {
        String idMessage = generateMessage(foreignKeyConstraint);

        if (truthValue) {
            generateOneNullRequirements(foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns(), idMessage);
            generateOneMatchRequirements(
                    foreignKeyConstraint.getTable(), foreignKeyConstraint.getColumns(),
                    foreignKeyConstraint.getReferenceTable(), foreignKeyConstraint.getReferenceColumns(), idMessage);
        } else {
            super.generateForeignKeyConstraintRequirements(foreignKeyConstraint, false);
        }
    }

    protected void generatePrimaryKeyConstraintRequirements(PrimaryKeyConstraint primaryKeyConstraint, boolean truthValue) {
        String idMessage = generateMessage(primaryKeyConstraint);

        if (truthValue) {
            generateOneMatchRequirements(primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns(), idMessage);
        } else {
            generateOneNullRequirements(primaryKeyConstraint.getTable(), primaryKeyConstraint.getColumns(), idMessage);
            super.generatePrimaryKeyConstraintRequirements(primaryKeyConstraint, false);
        }
    }

    protected void generateUniqueConstraintRequirements(UniqueConstraint uniqueConstraint, boolean truthValue) {
        String idMessage = generateMessage(uniqueConstraint);

        if (truthValue) {
            generateOneNullRequirements(uniqueConstraint.getTable(), uniqueConstraint.getColumns(), idMessage);
            generateOneMatchRequirements(uniqueConstraint.getTable(), uniqueConstraint.getColumns(), idMessage);
        } else {
            super.generateUniqueConstraintRequirements(uniqueConstraint, false);
        }
    }

    protected void generateOneNullRequirements(Table table, List<Column> columns, String idMessage) {
        for (Column majorColumn : columns) {
            AndPredicate predicate = new AndPredicate();
            predicate.addPredicate(new NullPredicate(table, majorColumn, true));
            for (Column minorColumn : columns) {
                if (!minorColumn.equals(majorColumn)) {
                    predicate.addPredicate(new NullPredicate(table, minorColumn, false));
                }
            }
            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " " + majorColumn + " is NULL",
                    predicate);
        }
    }

    protected void generateOneMatchRequirements(Table table, List<Column> columns, String idMessage) {
        generateOneMatchRequirements(table, columns, table, columns, idMessage);
    }

    private void generateOneMatchRequirements(Table table, List<Column> columns, Table refTable, List<Column> refColumns, String idMessage) {
        Iterator<Column> colsIt = columns.iterator();
        Iterator<Column> refColsIt = refColumns.iterator();

        while (colsIt.hasNext()) {
            Column col = colsIt.next();
            Column refCol = refColsIt.next();

            List<Column> remainingCols = new ArrayList<>(columns);
            remainingCols.remove(col);

            List<Column> refRemainingCols = new ArrayList<>(refColumns);
            refRemainingCols.remove(refCol);

            addRequirement(
                    idGenerator.nextID(),
                    idMessage + " all equal except " + col,
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
