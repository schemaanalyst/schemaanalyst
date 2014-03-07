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
    protected List<ClauseChecker<? extends Clause>> clauseCheckers;

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

    public List<ClauseChecker<? extends Clause>> getClauseCheckers() {
        return new ArrayList<>(clauseCheckers);
    }

    @Override
    public boolean check() {

        boolean result = true;
        for (ClauseChecker clauseChecker : clauseCheckers) {
            if (!clauseChecker.check()) {
                result = false;
            }
        }

        return result;
    }

    public String getDump() {
        String dump = "";
        for (ClauseChecker clauseChecker : clauseCheckers) {
            dump += clauseChecker.getInfo();
        }
        return dump;
    }
}
