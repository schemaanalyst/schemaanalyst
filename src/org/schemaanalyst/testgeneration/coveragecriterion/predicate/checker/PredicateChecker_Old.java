package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.clause.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 28/02/2014.
 */
public class PredicateChecker_Old extends Checker {

    private Predicate predicate;
    private Data data, state;
    protected List<PredicateChecker> clauseCheckers;

    public PredicateChecker_Old(Predicate predicate, Data data, Data state) {
        this.predicate = predicate;
        this.data = data;
        this.state = state;

        clauseCheckers = new ArrayList<>();
        /*
        for (Clause clause : predicate.getClauses()) {
            clause.accept(new ClauseVisitor() {
                @Override
                public void visit(ExpressionClause clause) {
                    clauseCheckers.add(
                            new ExpressionPredicateChecker(
                                    clause, true, PredicateChecker_Old.this.data));
                }

                @Override
                public void visit(MatchClause clause) {
                    clauseCheckers.add(
                            new MatchPredicateChecker(
                                    clause, true,
                                    PredicateChecker_Old.this.data, PredicateChecker_Old.this.state));
                }

                @Override
                public void visit(NullClause clause) {
                    clauseCheckers.add(
                            new NullPredicateChecker(
                                    clause, PredicateChecker_Old.this.data));
                }
            });
        }
        */
    }

    public List<PredicateChecker> getClauseCheckers() {
        return new ArrayList<>(clauseCheckers);
    }

    @Override
    public boolean check() {

        boolean result = true;
        for (PredicateChecker clauseChecker : clauseCheckers) {
            if (!clauseChecker.check()) {
                result = false;
            }
        }

        return result;
    }

    public String getInfo() {
        String info = "";
        for (PredicateChecker clauseChecker : clauseCheckers) {
            info += clauseChecker.getInfo();
        }
        return info;
    }
}
