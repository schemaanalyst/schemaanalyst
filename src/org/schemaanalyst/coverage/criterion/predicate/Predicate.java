package org.schemaanalyst.coverage.criterion.predicate;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.coverage.criterion.clause.Clause;
import org.schemaanalyst.coverage.criterion.clause.ClauseFactory;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class Predicate {

    private String purpose;
    private LinkedHashSet<Clause> clauses;

    public Predicate() {
        this(null);
    }

    public Predicate(String purpose) {
        this.purpose = purpose;
        this.clauses = new LinkedHashSet<>();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void addClause(Clause clause) {
        if (clause instanceof NullClause) {
            setColumnNullStatus((NullClause) clause);
        }
        clauses.add(clause);
    }

    public void addClauses(Predicate predicate) {
        for (Clause clause : predicate.getClauses()) {
            addClause(clause);
        }
    }

    public void setColumnNullStatus(NullClause nullClause) {
        setColumnNullStatus(nullClause.getTable(), nullClause.getColumn(), nullClause.getSatisfy());
    }

    public void setColumnNullStatus(Table table, Column column, boolean isNull) {
        List<NullClause> nullClauses = new ArrayList<>();
        for (Clause clause : clauses) {
            if (clause instanceof NullClause) {
                nullClauses.add((NullClause) clause);
            }
        }

        for (NullClause nullClause : nullClauses) {
            boolean sameTable = nullClause.getTable().equals(table);
            boolean sameColumn = nullClause.getColumn().equals(column);
            if (sameTable && sameColumn) {
                clauses.remove(nullClause);
            }
        }

        clauses.add(new NullClause(table, column, isNull));
    }

    public void setColumnUniqueStatus(Table table, Column column, boolean isUnique) {
        List<MatchClause> matchClauses = new ArrayList<>();
        for (Clause clause : clauses) {
            if (clause instanceof MatchClause) {
                matchClauses.add((MatchClause) clause);
            }
        }

        for (MatchClause matchClause : matchClauses) {
            boolean sameTable =
                    matchClause.getTable().equals(table) &&
                            matchClause.getReferenceTable().equals(table);

            if (sameTable) {
                List<Column> equalCols = matchClause.getMatchingColumns();
                List<Column> notEqualCols = matchClause.getNonMatchingColumns();

                boolean foundCol = equalCols.remove(column);
                foundCol = foundCol || notEqualCols.remove(column);

                if (foundCol) {
                    clauses.remove(matchClause);

                    if (equalCols.size() > 0 || notEqualCols.size() > 0) {
                        clauses.add(new MatchClause(
                                table, equalCols, notEqualCols,
                                matchClause.getMode(), matchClause.requiresComparisonRow()));
                    }
                }
            }
        }

        if (isUnique) {
            clauses.add(ClauseFactory.unique(table, column, true));
        } else {
            clauses.add(ClauseFactory.notUnique(table, column));
        }
    }

    public List<Clause> getClauses() {
        return new ArrayList<>(clauses);
    }

    public boolean hasClause(Clause clause) {
        return clauses.contains(clause);
    }

    public Predicate duplicate() {
        Predicate duplicate = new Predicate(purpose);
        for (Clause clause : clauses) {
            duplicate.addClause(clause.duplicate());
        }
        return duplicate;
    }

    public String toString() {
        return StringUtils.join(clauses, " \u2227 ");
    }
}
