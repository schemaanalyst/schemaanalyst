package org.schemaanalyst.data.generation.fixer;

import org.schemaanalyst.testgeneration.coveragecriterion.clause.*;
import org.schemaanalyst.data.generation.checker.*;
import org.schemaanalyst.data.generation.valuegeneration.CellValueGenerator;
import org.schemaanalyst.util.random.Random;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 28/02/2014.
 */
public class PredicateFixer extends Fixer {

    private List<Fixer> fixers;

    public PredicateFixer(PredicateChecker predicateChecker,
                          Random random,
                          CellValueGenerator cellValueGenerator) {

        class FixerInstantiator implements ClauseVisitor {

            Random random;
            CellValueGenerator cellValueGenerator;
            ClauseChecker clauseChecker;
            Fixer fixer;

            FixerInstantiator(Random random, CellValueGenerator cellValueGenerator) {
                this.random = random;
                this.cellValueGenerator = cellValueGenerator;
            }

            Fixer instantiateFixer(ClauseChecker clauseChecker) {
                fixer = null;
                this.clauseChecker = clauseChecker;
                clauseChecker.getClause().accept(this);
                return fixer;
            }

            @Override
            public void visit(ExpressionClause clause) {
                ExpressionClauseChecker expressionClauseChecker = (ExpressionClauseChecker) clauseChecker;
                fixer = new ExpressionClauseFixer(expressionClauseChecker, cellValueGenerator);
            }

            @Override
            public void visit(MatchClause clause) {
                MatchClauseChecker matchClauseChecker = (MatchClauseChecker) clauseChecker;
                fixer = new MatchClauseFixer(matchClauseChecker, random, cellValueGenerator);
            }

            @Override
            public void visit(NullClause clause) {
                NullClauseChecker nullClauseChecker = (NullClauseChecker) clauseChecker;
                fixer = new NullClauseFixer(nullClauseChecker);
            }
        }

        fixers = new ArrayList<>();
        List<ClauseChecker<? extends Clause>> clauseCheckers = predicateChecker.getClauseCheckers();
        FixerInstantiator clauseFixerInstantiator = new FixerInstantiator(random, cellValueGenerator);

        for (ClauseChecker clauseChecker : clauseCheckers) {
            fixers.add(clauseFixerInstantiator.instantiateFixer(clauseChecker));
        }
    }

    public List<Fixer> getFixers() {
        return new ArrayList<>(fixers);
    }

    @Override
    public void attemptFix() {
        for (Fixer fixer : fixers) {
            fixer.attemptFix();
        }
    }
}
