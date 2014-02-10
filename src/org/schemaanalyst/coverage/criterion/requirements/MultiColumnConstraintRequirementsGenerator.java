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
public class MultiColumnConstraintRequirementsGenerator extends RequirementsGenerator {

    private List<Column> cols, refCols;
    private Table refTable;
    private boolean requiresComparisonRow;
    private List<Column> emptyList;

    public MultiColumnConstraintRequirementsGenerator(Schema schema, Table table, MultiColumnConstraint constraint) {
        super(schema, table, constraint);
        cols = constraint.getColumns();
        if (constraint instanceof ForeignKeyConstraint) {
            ForeignKeyConstraint foreignKeyConstraint = (ForeignKeyConstraint) constraint;
            refCols = foreignKeyConstraint.getReferenceColumns();
            refTable = foreignKeyConstraint.getTable();
            requiresComparisonRow = false;
        } else {
            refCols = cols;
            refTable = table;
            requiresComparisonRow = true;
        }
        emptyList = new ArrayList<>();
    }

    @Override
    public List<Predicate> generateRequirements() {
        List<Predicate> requirements = new ArrayList<>();

        addOneColumnNotEqualRequirement(requirements);
        addAllColumnsEqualRequirement(requirements);

        return requirements;
    }

    private void addOneColumnNotEqualRequirement(List<Predicate> requirements) {
        Predicate predicate = generatePredicate("Test at least one column not equal for " + constraint);
        predicate.addClause(
                new MatchClause(
                        table, emptyList, cols,
                        refTable, emptyList, refCols,
                        MatchClause.Mode.OR, requiresComparisonRow)
        );
        requirements.add(predicate);
    }

    private void addAllColumnsEqualRequirement(List<Predicate> requirements) {
        Predicate predicate = generatePredicate("Test all columns equal for " + constraint);
        predicate.addClause(
                new MatchClause(
                        table, cols, emptyList,
                        refTable, refCols, emptyList,
                        MatchClause.Mode.AND, requiresComparisonRow)
        );
        requirements.add(predicate);
    }
}
