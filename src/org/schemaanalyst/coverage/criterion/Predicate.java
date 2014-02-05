package org.schemaanalyst.coverage.criterion;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.coverage.criterion.clause.Clause;
import org.schemaanalyst.coverage.criterion.clause.ClauseFactory;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by phil on 19/01/2014.
 */
public class Predicate {

    private String purpose;
    private LinkedHashSet<Clause> clauses;

    public Predicate(String purpose) {
        this.purpose = purpose;
        this.clauses = new LinkedHashSet<>();
    }

    public void addClause(Clause clause) {
        clauses.add(clause);
    }

    public void addClauses(Predicate predicate) {
        clauses.addAll(predicate.clauses);
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

            List<Column> columns = matchClause.getColumns();
            List<Column> referenceColumns = matchClause.getReferenceColumns();

            // if (matchClause.isAndMode()) {
                // AND-move -- remove exact clause or reduce it

                // TO DO ... not sure if this condition will ever be needed.

            //} else {
            //    // OR-mode -- remove exact clause
            boolean sameColumn = columns.size() == 1 && columns.get(0).equals(column) &&
                    referenceColumns.size() == 1 && referenceColumns.get(0).equals(column);

            if (sameTable && sameColumn) {
                clauses.remove(matchClause);
            }
            // }
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

    public String getPurpose() {
        return purpose;
    }

    public String toString() {
        return StringUtils.join(clauses, " \u2227 ");
    }
}
