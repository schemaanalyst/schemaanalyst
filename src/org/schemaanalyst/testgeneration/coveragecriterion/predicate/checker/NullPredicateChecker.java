package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.logic.predicate.clause.NullClause;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014.
 */
public class NullPredicateChecker extends PredicateChecker {

    private NullClause nullClause;
    private Data data;
    protected List<Cell> nonComplyingCells;

    public NullPredicateChecker(NullClause nullClause, Data data) {
        this.nullClause = nullClause;
        this.data = data;
    }

    public NullClause getClause() {
        return nullClause;
    }

    public List<Cell> getNonComplyingCells() {
        return nonComplyingCells;
    }

    @Override
    public boolean check() {
        nonComplyingCells = new ArrayList<>();

        List<Row> rows = data.getRows(nullClause.getTable());
        if (rows.size() > 0) {
            for (Row row : rows) {
                Cell cell = row.getCell(nullClause.getColumn());

                if (nullClause.getSatisfy() != cell.isNull()) {
                    nonComplyingCells.add(cell);
                }
            }
            return (nonComplyingCells.size() == 0);
        }

        return false;
    }

    @Override
    public String getInfo() {
        boolean check = check();
        String dump = "Null clause: " + nullClause + "\n";
        dump += "\t* Success: " + check + "\n";
        if (!check) {
            dump += "\t* Non-complying cells:\n";
            for (Cell cell : nonComplyingCells) {
                dump += "\t\t* " + cell + "\n";
            }
        }
        return dump;
    }
}
