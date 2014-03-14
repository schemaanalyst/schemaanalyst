package org.schemaanalyst.logic.predicate.checker;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.clause.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 28/02/2014.
 */
public class PredicateChecker extends Checker {

    private Predicate predicate;
    private Data data, state;
    protected List<ClauseChecker> clauseCheckers;

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
        for (ClauseChecker clauseChecker : clauseCheckers) {
            if (!clauseChecker.check()) {
                result = false;
            }
        }

        return result;
    }

    public String getInfo() {
        String info = "";
        for (ClauseChecker clauseChecker : clauseCheckers) {
            info += clauseChecker.getInfo();
        }
        return info;
    }
}
