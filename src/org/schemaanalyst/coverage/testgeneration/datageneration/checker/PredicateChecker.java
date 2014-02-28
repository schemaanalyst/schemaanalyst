package org.schemaanalyst.coverage.testgeneration.datageneration.checker;

import org.schemaanalyst.coverage.criterion.clause.*;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.data.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 28/02/2014.
 */
public class PredicateChecker extends Checker {

    private Predicate predicate;
    private Data data, state;
    private List<ClauseChecker> clauseCheckers;

    public PredicateChecker(Predicate predicate, Data data, Data state) {
        this.predicate = predicate;
        this.data = data;
        this.state = state;

        clauseCheckers = new ArrayList<>();
        for (Clause clause : predicate.getClauses()) {
            clause.accept(new ClauseVisitor() {
                @Override
                public void visit(ExpressionClause clause) {
                    clauseCheckers.add(
                            new ExpressionClauseChecker(
                                    clause, true, PredicateChecker.this.data));
                }

                @Override
                public void visit(MatchClause clause) {
                    clauseCheckers.add(
                            new MatchClauseChecker(
                                    clause, true,
                                    PredicateChecker.this.data, PredicateChecker.this.state));
                }

                @Override
                public void visit(NullClause clause) {
                    clauseCheckers.add(
                            new NullClauseChecker(
                                    clause, PredicateChecker.this.data));
                }
            });
        }
    }

    public List<ClauseChecker> getClauseCheckers() {
        return new ArrayList<>(clauseCheckers);
    }

    @Override
    public boolean check() {

        boolean result = true;
        for (Checker clauseChecker : clauseCheckers) {
            if (!clauseChecker.check()) {
                result = false;
            }
        }

        return result;
    }
}
