package org.schemaanalyst.coverage.requirements;

import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.function.DistinctFunction;
import org.schemaanalyst.coverage.predicate.function.MatchesFunction;
import org.schemaanalyst.coverage.requirements.DefaultPredicateGenerator;
import org.schemaanalyst.coverage.requirements.RequirementsGenerator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 21/01/2014.
 */
public class UsefulUnique extends RequirementsGenerator {

    private Schema schema;
    private Table table;

    public UsefulUnique(Schema schema, Table table) {
        this.schema = schema;
        this.table = table;
    }

    public LinkedHashSet<Predicate> generateRequirements() {
        LinkedHashSet<Predicate> predicates = new LinkedHashSet<>();

        if (schema.hasPrimaryKeyConstraint(table)) {
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            List<Column> columns = primaryKeyConstraint.getColumns();
            DefaultPredicateGenerator predicateGenerator = new DefaultPredicateGenerator();

            // (1) generate test requirements where each individual column is unique once

            for (Column column : columns) {
                List<Column> remainingColumns = new ArrayList<>(columns);
                remainingColumns.remove(column);

                // generate predicate and remove old clause for PK
                Predicate predicate = predicateGenerator.generatePredicate(schema, table);
                predicate.removeClause(primaryKeyConstraint);

                // generate new clause
                predicate.addClause(primaryKeyConstraint, new DistinctFunction(table, column));
                predicate.addClause(primaryKeyConstraint, new MatchesFunction(table, remainingColumns));

                // add new clause
                predicates.add(predicate);
            }

            // (2) generate test requirement where there is a collision of values

            // generate predicate and remove old clause for PK
            Predicate predicate = predicateGenerator.generatePredicate(schema, table);
            predicate.removeClause(primaryKeyConstraint);

            // generate new clause
            predicate.addClause(primaryKeyConstraint, new MatchesFunction(table, columns));

            // add new clause
            predicates.add(predicate);
        }

        return predicates;
    }
}

