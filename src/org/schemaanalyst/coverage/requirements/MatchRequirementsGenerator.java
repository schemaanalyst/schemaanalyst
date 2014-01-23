package org.schemaanalyst.coverage.requirements;

import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.TestRequirements;
import org.schemaanalyst.coverage.predicate.function.MatchFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.*;

/**
 * Created by phil on 22/01/2014.
 */
public class MatchRequirementsGenerator extends RequirementsGenerator {

    private Table referenceTable;
    private List<Column> columns, referenceColumns;

    public MatchRequirementsGenerator(Schema schema, Table table, Constraint constraint, List<Column> columns) {
        this(schema, table, constraint, columns, table, columns);
    }

    public MatchRequirementsGenerator(Schema schema, Table table, Constraint constraint,
                                      List<Column> columns, Table referenceTable, List<Column> referenceColumns) {
        super(schema, table, constraint);
        this.columns = columns;
        this.referenceTable = referenceTable;
        this.referenceColumns = referenceColumns;
    }

    @Override
    public TestRequirements generateRequirements() {
        TestRequirements requirements = new TestRequirements();

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

            // generate new predicate
            Predicate predicate = generatePredicate("-- Test " + col + " not equal for " + constraint);
            predicate.addClause(
                    constraint,
                    new MatchFunction(
                            table,
                            Arrays.asList(col),
                            remainingCols,
                            referenceTable,
                            Arrays.asList(refCol),
                            refRemainingCols)
            );

            // add new clause
            requirements.add(predicate);
        }

        // (2) generate test requirement where there is a collision of values

        // generate predicate and remove old clause for underpinning constraint
        Predicate predicate = generatePredicate("-- Test all columns equal for " + constraint);

        // generate new clause
        predicate.addClause(
                constraint,
                new MatchFunction(
                        table,
                        new ArrayList<Column>(),
                        columns,
                        referenceTable,
                        new ArrayList<Column>(),
                        referenceColumns)
        );

        // add new clause
        requirements.add(predicate);

        return requirements;
    }
}
