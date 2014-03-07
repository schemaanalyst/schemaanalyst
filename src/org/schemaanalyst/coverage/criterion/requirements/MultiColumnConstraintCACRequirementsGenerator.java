package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.MultiColumnConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phil on 22/01/2014.
 */
public class MultiColumnConstraintCACRequirementsGenerator extends ConstraintRequirementsGenerator {

    private Table referenceTable;
    private List<Column> columns, referenceColumns;

    /**
     * The constructor to use with FOREIGN KEY constraints
     *
     * @param schema     a schema
     * @param constraint the FOREIGN KEY constraint
     */
    public MultiColumnConstraintCACRequirementsGenerator(Schema schema, ForeignKeyConstraint constraint) {
        super(schema, constraint);
        this.columns = constraint.getColumns();
        this.referenceTable = constraint.getReferenceTable();
        this.referenceColumns = constraint.getReferenceColumns();
    }

    /**
     * The constructor to use with PRIMARY KEY and UNIQUE constraints
     *
     * @param schema     a schema
     * @param constraint the PRIMARY KEY or UNIQUE constraint
     */
    public MultiColumnConstraintCACRequirementsGenerator(Schema schema, MultiColumnConstraint constraint) {
        super(schema, constraint);
        this.columns = constraint.getColumns();
        this.referenceTable = table;
        this.referenceColumns = columns;
    }

    @Override
    public Requirements generateRequirements() {
        boolean requiresComparisonRow = table.equals(referenceTable);
        Requirements requirements = new Requirements();

        addNotEqualsOnceRequirements(requirements, requiresComparisonRow);
        addAllEqualRequirement(requirements, requiresComparisonRow);
        addNullOnceRequirements(requirements);

        return requirements;
    }

    private void addNotEqualsOnceRequirements(Requirements requirements, boolean requiresComparisonRow) {
        Iterator<Column> colsIt = columns.iterator();
        Iterator<Column> refColsIt = referenceColumns.iterator();

        while (colsIt.hasNext()) {
            Column col = colsIt.next();
            Column refCol = refColsIt.next();

            List<Column> remainingCols = new ArrayList<>(columns);
            remainingCols.remove(col);

            List<Column> refRemainingCols = new ArrayList<>(referenceColumns);
            refRemainingCols.remove(refCol);

            Predicate predicate = generatePredicate("Test all equal except " + col + " for " + table + "'s " + constraint);
            predicate.addClause(
                    new MatchClause(
                            table,
                            remainingCols,
                            Arrays.asList(col),
                            referenceTable,
                            refRemainingCols,
                            Arrays.asList(refCol),
                            MatchClause.Mode.AND,
                            requiresComparisonRow)
            );

            // NOT NULL clauses are only added to original columns,
            // as we are not in control of column values of the reference row.
            addNotNulls(predicate, table, columns);
            requirements.addPredicate(predicate);
        }
    }

    private void addAllEqualRequirement(Requirements requirements, boolean requiresComparisonRow) {
        Predicate predicate = generatePredicate("Test all columns equal for " + table + "'s " + constraint);

        predicate.addClause(
                new MatchClause(
                        table,
                        columns,
                        new ArrayList<Column>(),
                        referenceTable,
                        referenceColumns,
                        new ArrayList<Column>(),
                        MatchClause.Mode.AND,
                        requiresComparisonRow)
        );

        // NOT NULL clauses are only added to original columns,
        // as we are not in control of column values of the reference row.
        addNotNulls(predicate, table, columns);

        requirements.addPredicate(predicate);
    }

    private void addNullOnceRequirements(Requirements requirements) {
        // NULL-ONCE specifies that the original columns should be
        // NULL although technically it could be original and/or
        // reference column. However, test generation is concentrated
        // on rows of the original table, not the reference, which is
        // already generated (with NOT NULL values) and out of our control.
        // So we demand the change in the original row only.

        for (Column col : columns) {

            Predicate predicate = generatePredicate("Test " + col + " is NULL for " + table + "'s " + constraint);
            predicate.setColumnNullStatus(table, col, true);

            List<Column> remainingCols = new ArrayList<>(columns);
            remainingCols.remove(col);
            addNotNulls(predicate, table, remainingCols);

            requirements.addPredicate(predicate);
        }
    }

    private void addNotNulls(Predicate predicate, Table table, List<Column> columns) {
        for (Column column : columns) {
            predicate.setColumnNullStatus(table, column, false);
        }
    }
}
