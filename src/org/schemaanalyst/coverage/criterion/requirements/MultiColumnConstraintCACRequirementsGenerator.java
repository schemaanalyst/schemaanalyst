package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
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
public class MultiColumnConstraintCACRequirementsGenerator extends RequirementsGenerator {

    private Table referenceTable;
    private List<Column> columns, referenceColumns;

    public MultiColumnConstraintCACRequirementsGenerator(Schema schema, Table table, PrimaryKeyConstraint constraint) {
        super(schema, table, constraint);
        this.columns = constraint.getColumns();
        this.referenceTable = table;
        this.referenceColumns = columns;
    }

    public MultiColumnConstraintCACRequirementsGenerator(Schema schema, Table table, UniqueConstraint constraint) {
        super(schema, table, constraint);
        this.columns = constraint.getColumns();
        this.referenceTable = table;
        this.referenceColumns = columns;
    }

    public MultiColumnConstraintCACRequirementsGenerator(Schema schema, Table table, ForeignKeyConstraint constraint) {
        super(schema, table, constraint);
        this.columns = constraint.getColumns();
        this.referenceTable = constraint.getReferenceTable();
        this.referenceColumns = constraint.getReferenceColumns();
    }

    @Override
    public List<Predicate> generateRequirements() {
        boolean requiresComparisonRow = table.equals(referenceTable);
        List<Predicate> requirements = new ArrayList<>();

        addEqualsOnceRequirements(requirements, requiresComparisonRow);
        addAllNotEqualRequirement(requirements, requiresComparisonRow);
        addNullOnceRequirements(requirements);

        return requirements;
    }

    private void addEqualsOnceRequirements(List<Predicate> requirements, boolean requiresComparisonRow) {
        Iterator<Column> colsIt = columns.iterator();
        Iterator<Column> refColsIt = referenceColumns.iterator();

        while (colsIt.hasNext()) {
            Column col = colsIt.next();
            Column refCol = refColsIt.next();

            List<Column> remainingCols = new ArrayList<>(columns);
            remainingCols.remove(col);

            List<Column> refRemainingCols = new ArrayList<>(columns);
            refRemainingCols.remove(refCol);

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

            // NOT NULL clauses are only added to original columns,
            // as we are not in control of column values of the reference row.
            addNotNulls(predicate, table, columns);
            requirements.add(predicate);
        }
    }

    private void addAllNotEqualRequirement(List<Predicate> requirements, boolean requiresComparisonRow) {
        Predicate predicate = generatePredicate("Test all columns are not equal for " + table + "'s " + constraint);

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

        requirements.add(predicate);
    }

    private void addNullOnceRequirements(List<Predicate> requirements) {
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

            requirements.add(predicate);
        }
    }

    private void addNotNulls(Predicate predicate, Table table, List<Column> columns) {
        for (Column column : columns) {
            predicate.setColumnNullStatus(table, column, false);
        }
    }
}
