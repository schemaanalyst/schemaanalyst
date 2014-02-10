package org.schemaanalyst.coverage.criterion.clause;

import org.apache.commons.lang3.StringUtils;
import org.schemaanalyst.sqlrepresentation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 09/02/2014.
 */
public class OrClause extends Clause {

    private List<Clause> subclauses;

    public OrClause(Table table, Clause... subclauses) {
        this(table, Arrays.asList(subclauses));
    }

    public OrClause(Table table, List<Clause> subclauses) {
        super(table);
        this.subclauses = new ArrayList<>(subclauses);
    }

    @Override
    public boolean requiresComparisonRow() {
        for (Clause clause : subclauses) {
            if (clause.requiresComparisonRow()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "or";
    }

    @Override
    protected String paramsToString() {
        return StringUtils.join(subclauses, ",");
    }

    @Override
    public void accept(ClauseVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Clause duplicate() {
        List<Clause> duplicatedSubclauses = new ArrayList<>();
        for (Clause clause : subclauses) {
            duplicatedSubclauses.add(clause.duplicate());
        }
        return new OrClause(table, subclauses);
    }
}
