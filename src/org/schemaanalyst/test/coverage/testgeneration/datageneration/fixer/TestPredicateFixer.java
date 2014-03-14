package org.schemaanalyst.test.coverage.testgeneration.datageneration.fixer;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.generation.directedrandom.*;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.checker.*;
import org.schemaanalyst.logic.predicate.clause.Clause;
import org.schemaanalyst.logic.predicate.clause.ClauseFactory;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 01/03/2014.
 */
public class TestPredicateFixer {

    public class MockPredicateChecker extends PredicateChecker {
        public MockPredicateChecker(List<ClauseChecker> clauseCheckers) {
            super(new Predicate(), null, null);
            this.clauseCheckers = clauseCheckers;
        }
    }

    @Test
    public void testInstantiation() {

        ExpressionClauseChecker expressionClauseChecker = new ExpressionClauseChecker(
                ClauseFactory.expression(new Table("dummy"), new ConstantExpression(new NumericValue(10))),
                false, null);

        MatchClauseChecker matchClauseChecker = new MatchClauseChecker(
                ClauseFactory.unique(new Table("dummy"), new Column("dummy", new IntDataType()), true),
                false, null);

        NullClauseChecker nullClauseChecker = new NullClauseChecker(
                ClauseFactory.isNull(new Table("dummy"), new Column("dummy", new IntDataType())),
                null);

        PredicateChecker predicateChecker = new MockPredicateChecker(
                Arrays.asList(expressionClauseChecker, matchClauseChecker, nullClauseChecker));

        PredicateFixer predicateFixer = new PredicateFixer(predicateChecker, null, null);
        List<Fixer> fixers = predicateFixer.getFixers();

        assertTrue(fixers.get(0) instanceof ExpressionClauseFixer);
        assertTrue(fixers.get(1) instanceof MatchClauseFixer);
        assertTrue(fixers.get(2) instanceof NullClauseFixer);
    }
}
