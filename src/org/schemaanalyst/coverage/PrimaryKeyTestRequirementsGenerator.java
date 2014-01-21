package org.schemaanalyst.coverage;

import org.schemaanalyst.coverage.predicate.Clause;
import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.function.DistinctFunction;
import org.schemaanalyst.coverage.predicate.function.MatchesFunction;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class PrimaryKeyTestRequirementsGenerator extends TestRequirementsGenerator {

    private Schema schema;
    private Table table;

    public PrimaryKeyTestRequirementsGenerator(Schema schema, Table table) {
        this.schema = schema;
        this.table = table;
    }

    public LinkedHashSet<Predicate> generateRequirements() {
        LinkedHashSet<Predicate> predicates = new LinkedHashSet<>();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            List<Column> columns = primaryKeyConstraint.getColumns();
            DefaultPredicateGenerator predicateGenerator = new DefaultPredicateGenerator();

            // generate test requirements where each individual column is unique once
            for (Column column : columns) {
                List<Column> remainingColumns = new ArrayList<>(columns);
                remainingColumns.remove(column);

                Predicate predicate = predicateGenerator.generatePredicate(schema, table);
                Clause clause = predicate.getClause(primaryKeyConstraint);

                clause.setFunctions(
                        new DistinctFunction(table, column),
                        new MatchesFunction(table, remainingColumns)
                );
                predicates.add(predicate);
            }

            // generate test requirement where there is a collision of values
            Predicate predicate = predicateGenerator.generatePredicate(schema, table);
            Clause clause = predicate.getClause(primaryKeyConstraint);
            clause.setFunctions(
                    new MatchesFunction(table, columns)
            );
            predicates.add(predicate);
        }

        return predicates;
    }
}
