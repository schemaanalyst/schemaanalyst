package org.schemaanalyst.coverage.requirements;

import org.schemaanalyst.coverage.predicate.Predicate;
import org.schemaanalyst.coverage.predicate.function.DistinctFunction;
import org.schemaanalyst.coverage.predicate.function.MatchesFunction;
import org.schemaanalyst.coverage.requirements.DefaultPredicateGenerator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 21/01/2014.
 */
public class UniqueColumn {

    private Schema schema;
    private Table table;

    public UniqueColumn(Schema schema, Table table) {
        this.schema = schema;
        this.table = table;
    }

    public LinkedHashSet<Predicate> generateRequirements() {
        DefaultPredicateGenerator predicateGenerator = new DefaultPredicateGenerator();
        LinkedHashSet<Predicate> predicates = new LinkedHashSet<>();

        List<Column> columns = table.getColumns();
        for (Column column : columns) {

            // (1) Unique column entry
            Predicate predicate1 = predicateGenerator.generatePredicate(schema, table);
            predicate1.addClause(new DistinctFunction(table, column));
            predicates.add(predicate1);

            // (2) Non-unique column entry
            Predicate predicate2 = predicateGenerator.generatePredicate(schema, table);
            predicate2.addClause(new MatchesFunction(table, column));
            predicates.add(predicate2);
        }

        return predicates;
    }

}
