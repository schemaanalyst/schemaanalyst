package org.schemaanalyst.coverage.requirements;

import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.TestRequirements;
import org.schemaanalyst.coverage.predicate.function.DistinctFunction;
import org.schemaanalyst.coverage.predicate.function.MatchesFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by phil on 22/01/2014.
 */
public class ConstrainedCrossTableRequirementsGenerator extends RequirementsGenerator {

    private Table otherTable;
    private List<Column> columns, otherColumns;

    public ConstrainedCrossTableRequirementsGenerator(Schema schema, Table table, Constraint constraint, List<Column> columns) {
        this(schema, table, constraint, columns, table, columns);
    }

    public ConstrainedCrossTableRequirementsGenerator(Schema schema, Table table, Constraint constraint,
                                                      List<Column> columns, Table otherTable, List<Column> otherColumns) {
        super(schema, table, constraint);
        this.columns = columns;
        this.otherTable = otherTable;
        this.otherColumns = otherColumns;
    }

    @Override
    public TestRequirements generateRequirements() {
        TestRequirements requirements = new TestRequirements();

        // (1) generate test requirements where each individual column is distinct once
        Iterator<Column> colsIt = columns.iterator();
        Iterator<Column> otherColsIt = otherColumns.iterator();

        while (colsIt.hasNext()) {
            Column column = colsIt.next();
            Column otherColumn = otherColsIt.next();

            List<Column> remainingColumns = new ArrayList<>(columns);
            remainingColumns.remove(column);

            List<Column> otherRemainingCols = new ArrayList<>(columns);
            otherRemainingCols.remove(otherColumn);

            // generate new predicate
            Predicate predicate = generatePredicate("-- Test " + column + " distinct for " + constraint);
            predicate.addClause(
                    constraint, new DistinctFunction(table, column, otherTable, otherColumn)
            );
            predicate.addClause(
                    constraint, new MatchesFunction(table, remainingColumns, otherTable, otherRemainingCols)
            );

            // add new clause
            requirements.add(predicate);
        }

        // (2) generate test requirement where there is a collision of values

        // generate predicate and remove old clause for underpinning constraint
        Predicate predicate = generatePredicate("-- Test all columns matching for " + constraint);

        // generate new clause
        predicate.addClause(constraint, new MatchesFunction(table, columns));

        // add new clause
        requirements.add(predicate);

        return requirements;
    }
}
