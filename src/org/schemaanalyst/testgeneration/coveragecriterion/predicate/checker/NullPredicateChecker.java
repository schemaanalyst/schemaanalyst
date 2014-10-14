package org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.NullPredicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 27/02/2014.
 */
public class NullPredicateChecker extends PredicateChecker {

    private NullPredicate nullPredicate;
    private Data data;
    protected List<Cell> nonComplyingCells;

    public NullPredicateChecker(NullPredicate nullPredicate, Data data) {
        this.nullPredicate = nullPredicate;
        this.data = data;
    }

    @Override
    public NullPredicate getPredicate() {
        return nullPredicate;
    }

    public List<Cell> getNonComplyingCells() {
        return nonComplyingCells;
    }

    @Override
    public boolean check() {
        nonComplyingCells = new ArrayList<>();

        List<Row> rows = data.getRows(nullPredicate.getTable());
        if (rows.size() > 0) {
            for (Row row : rows) {
                Cell cell = row.getCell(nullPredicate.getColumn());

                if (nullPredicate.getTruthValue() != cell.isNull()) {
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
        String dump = "Null clause: " + nullPredicate + "\n";
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
