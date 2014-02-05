package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
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

            Predicate predicate = predicateGenerator.generate(
                    "Test " + col + " only equal for " + table + "'s " + constraint);
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
            addNotNulls(predicate);
            requirements.add(predicate);
        }
    }

    private void addAllNotEqualRequirement(List<Predicate> requirements, boolean requiresComparisonRow) {
        Predicate predicate = predicateGenerator.generate(
                "Test all columns are not equal for " + table + "'s " + constraint);

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
        addNotNulls(predicate);
        requirements.add(predicate);
    }

    private void addNullOnceRequirements(List<Predicate> requirements) {
        Iterator<Column> colsIt = columns.iterator();
        Iterator<Column> refColsIt = referenceColumns.iterator();

        while (colsIt.hasNext()) {
            Column col = colsIt.next();
            Column refCol = refColsIt.next();

            List<Column> remainingCols = new ArrayList<>(columns);
            remainingCols.remove(col);

            List<Column> refRemainingCols = new ArrayList<>(columns);
            refRemainingCols.remove(refCol);

            Predicate predicate = predicateGenerator.generate(
                    "Test " + col + " only NULL for " + table + "'s " + constraint);

            // TO DO: this should be an OR-NULL:
            predicate.setColumnNullStatus(table, col, true);
            predicate.setColumnNullStatus(table, refCol, true);

            addNotNulls(predicate, table, remainingCols);
            addNotNulls(predicate, referenceTable, refRemainingCols);

            requirements.add(predicate);
        }
    }

    private void addNotNulls(Predicate predicate) {
        addNotNulls(predicate, table, columns);
        addNotNulls(predicate, referenceTable, referenceColumns);
    }

    private void addNotNulls(Predicate predicate, Table table, List<Column> columns) {
        for (Column column : columns) {
            predicate.setColumnNullStatus(table, column, false);
        }
    }
}
