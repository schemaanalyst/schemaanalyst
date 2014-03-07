package org.schemaanalyst.coverage.criterion.requirements;

import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.MultiColumnConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 05/02/2014.
 */
public class MultiColumnConstraintRequirementsGenerator extends ConstraintRequirementsGenerator {

    private List<Column> cols, refCols;
    private Table refTable;
    private boolean requiresComparisonRow;
    private List<Column> emptyList;

    public MultiColumnConstraintRequirementsGenerator(Schema schema, ForeignKeyConstraint constraint) {
        this(schema, constraint, true);
    }

    public MultiColumnConstraintRequirementsGenerator(Schema schema, ForeignKeyConstraint constraint, boolean generateFullPredicate) {
        super(schema, constraint, generateFullPredicate);
        cols = constraint.getColumns();
        refCols = constraint.getReferenceColumns();
        refTable = constraint.getReferenceTable();
        requiresComparisonRow = false;
        emptyList = new ArrayList<>();
    }

    public MultiColumnConstraintRequirementsGenerator(Schema schema, MultiColumnConstraint constraint) {
        this(schema, constraint, true);
    }

    public MultiColumnConstraintRequirementsGenerator(Schema schema, MultiColumnConstraint constraint, boolean generateFullPredicate) {
        super(schema, constraint, generateFullPredicate);
        cols = constraint.getColumns();
        refCols = cols;
        refTable = table;
        requiresComparisonRow = true;
        emptyList = new ArrayList<>();
    }

    @Override
    public List<Predicate> generateRequirements() {
        List<Predicate> requirements = new ArrayList<>();

        addOneColumnNotEqualRequirement(requirements);
        addAllColumnsEqualRequirement(requirements);
        addOneColumnNullRequirement(requirements);

        return requirements;
    }

    private void addOneColumnNotEqualRequirement(List<Predicate> requirements) {
        Predicate predicate = generatePredicate("Test at least one column not equal for " + table + "'s " + constraint);

        // add the clause such that (at least) one of the columns is not equal
        predicate.addClause(
                new MatchClause(
                        table, emptyList, cols,
                        refTable, emptyList, refCols,
                        MatchClause.Mode.OR, requiresComparisonRow)
        );

        // each of the columns should be NOT NULL
        // (otherwise the outcome of the predicate would not be TRUE/FALSE, it would be NULL).
        ensureColumnsAreNotNull(predicate);
        requirements.add(predicate);
    }

    private void addAllColumnsEqualRequirement(List<Predicate> requirements) {
        Predicate predicate = generatePredicate("Test all columns equal for " + table + "'s " + constraint);

        // add the clause such that all of the columns are equal
        predicate.addClause(
                new MatchClause(
                        table, cols, emptyList,
                        refTable, refCols, emptyList,
                        MatchClause.Mode.AND, requiresComparisonRow)
        );

        // each of the columns should be NOT NULL
        // (otherwise the outcome of the predicate would not be TRUE/FALSE, it would be NULL).
        ensureColumnsAreNotNull(predicate);
        requirements.add(predicate);
    }

    private void ensureColumnsAreNotNull(Predicate predicate) {
        for (Column col : cols) {
            predicate.setColumnNullStatus(table, col, false);
        }
    }

    private void addOneColumnNullRequirement(List<Predicate> requirements) {
        // We achieve the requirement by stating that the first column should be NULL.
        // In principle it could be any, but we should pick one.
        // We do not need to say anything definite about the NULL status of other columns
        Column firstColumn = cols.get(0);

        Predicate predicate = generatePredicate(
                "Test one column IS NULL (" + firstColumn + ") for " + table + "'s " + constraint);

        predicate.setColumnNullStatus(table, firstColumn, true);

        requirements.add(predicate);
    }
}
