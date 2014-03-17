package org.schemaanalyst.test.logic.predicate.checker;

import org.junit.Test;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.checker.*;
import org.schemaanalyst.logic.predicate.clause.ClauseFactory;
import org.schemaanalyst.logic.predicate.clause.ExpressionClause;
import org.schemaanalyst.logic.predicate.clause.MatchClause;
import org.schemaanalyst.logic.predicate.clause.NullClause;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 01/03/2014.
 */
public class TestPredicateChecker {

    @Test
    public void testInstantiation() {

        ExpressionClause expressionClause =
                ClauseFactory.expression(new Table("dummy"), new ConstantExpression(new NumericValue(10)));

        MatchClause matchClause =
                ClauseFactory.unique(new Table("dummy"), new Column("dummy", new IntDataType()), true);

        NullClause nullClause =
                ClauseFactory.isNull(new Table("dummy"), new Column("dummy", new IntDataType()));

        Predicate predicate = new Predicate();
        predicate.addClause(expressionClause);
        predicate.addClause(matchClause);
        predicate.addClause(nullClause);

        PredicateChecker predicateChecker = new PredicateChecker(predicate, null, null);
        List<ClauseChecker> checkers = predicateChecker.getClauseCheckers();

        assertTrue(checkers.get(0) instanceof ExpressionClauseChecker);
        assertTrue(checkers.get(1) instanceof MatchClauseChecker);
        assertTrue(checkers.get(2) instanceof NullClauseChecker);
    }
}
