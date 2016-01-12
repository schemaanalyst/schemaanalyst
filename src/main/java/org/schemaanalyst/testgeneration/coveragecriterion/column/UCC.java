package org.schemaanalyst.testgeneration.coveragecriterion.column;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementIDGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.MatchPredicate;

import java.util.Arrays;
import java.util.List;

import static org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator.addNullPredicate;

/**
 * Created by phil on 18/08/2014.
 */
public class UCC extends ColumnCriterion {

    public UCC(Schema schema, TestRequirementIDGenerator testRequirementIDGenerator) {
        super(schema, testRequirementIDGenerator);
    }

    public String getName() {
        return "UCC";
    }

    protected MatchPredicate generateMatchPredicate(Table table, Column column, boolean truthValue) {
        List<Column> equalsCols = (truthValue) ? MatchPredicate.EMPTY_COLUMN_LIST : Arrays.asList(column);
        List<Column> notEqualsCols = (truthValue) ? Arrays.asList(column) : MatchPredicate.EMPTY_COLUMN_LIST;
        return new MatchPredicate(table, equalsCols, notEqualsCols, MatchPredicate.Mode.AND);
    }

    protected void generateRequirement(Table table, Column column, boolean truthValue) {

        AndPredicate predicate = new AndPredicate();
        predicate.addPredicate(generateMatchPredicate(table, column, truthValue));
        addNullPredicate(predicate, table, column, false);

        testRequirements.addTestRequirement(
                new TestRequirement(
                        new TestRequirementDescriptor(
                                testRequirementIDGenerator.nextID(),
                                column + " is " + ((truthValue) ? "UNIQUE" : "NOT UNIQUE")),
                        predicate,
                        null,
                        true
                )
        );
    }
}
