package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phil on 22/01/2014.
 */
public class MatchRequirementsGenerator extends RequirementsGenerator {

    private Table referenceTable;
    private List<Column> columns, referenceColumns;

    public MatchRequirementsGenerator(Schema schema, Table table, PrimaryKeyConstraint constraint) {
        super(schema, table, constraint);
        this.columns = constraint.getColumns();
        this.referenceTable = table;
        this.referenceColumns = columns;
    }

    public MatchRequirementsGenerator(Schema schema, Table table, UniqueConstraint constraint) {
        super(schema, table, constraint);
        this.columns = constraint.getColumns();
        this.referenceTable = table;
        this.referenceColumns = columns;
    }

    public MatchRequirementsGenerator(Schema schema, Table table, ForeignKeyConstraint constraint) {
        super(schema, table, constraint);
        this.columns = constraint.getColumns();
        this.referenceTable = constraint.getReferenceTable();
        this.referenceColumns = constraint.getReferenceColumns();
    }

    @Override
    public Requirements generateRequirements() {
        boolean requiresComparisonRow = table.equals(referenceTable);
        Requirements requirements = new Requirements();

        // (1) generate test requirements where each individual column is distinct once
        Iterator<Column> colsIt = columns.iterator();
        Iterator<Column> refColsIt = referenceColumns.iterator();

        while (colsIt.hasNext()) {
            Column col = colsIt.next();
            Column refCol = refColsIt.next();

            List<Column> remainingCols = new ArrayList<>(columns);
            remainingCols.remove(col);

            List<Column> refRemainingCols = new ArrayList<>(columns);
            refRemainingCols.remove(refCol);

            // generate new clause
            Predicate predicate = generatePredicate("Test " + col + " only equal for " + table + "'s " + constraint);
            predicate.addClause(
                    new MatchClause(
                            table,
                            Arrays.asList(col),
                            remainingCols,
                            referenceTable,
                            Arrays.asList(refCol),
                            refRemainingCols,
                            MatchClause.Mode.AND,
                            requiresComparisonRow)
            );

            // add new clause
            requirements.add(predicate);
        }

        // (2) generate test requirement where there is a collision of values

        // generate clause and remove old clause for underpinning constraint
        Predicate predicate = generatePredicate("Test all columns not equal for " + table + "'s " + constraint);

        // generate new clause
        predicate.addClause(
                new MatchClause(
                        table,
                        new ArrayList<Column>(),
                        columns,
                        referenceTable,
                        new ArrayList<Column>(),
                        referenceColumns,
                        MatchClause.Mode.AND,
                        requiresComparisonRow)
        );

        // add new clause
        requirements.add(predicate);

        return requirements;
    }
}
