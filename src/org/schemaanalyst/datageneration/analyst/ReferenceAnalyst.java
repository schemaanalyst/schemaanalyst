package org.schemaanalyst.datageneration.analyst;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.sqlrepresentation.Column;

public class ReferenceAnalyst extends ConstraintAnalyst {

    protected List<Column> columns;
    protected List<Column> referenceColumns;
    protected boolean satisfyOnNull;
    protected List<Row> referencingRows,
            nonReferencingRows,
            referenceRows;

    public ReferenceAnalyst(List<Column> columns,
            List<Column> referenceColumns,
            boolean satisfyOnNull) {

        this.columns = columns;
        this.referenceColumns = referenceColumns;
        this.satisfyOnNull = satisfyOnNull;
    }

    public boolean isSatisfied(Data state, Data data) {
        // initialize data rows
        List<Row> dataRows = data.getRows(columns);

        // add all reference rows from data and the state
        referenceRows = data.getRows(referenceColumns);
        if (state != null) {
            referenceRows.addAll(state.getRows(referenceColumns));
        }

        // initialize referencing and non-referencing row lists
        referencingRows = new ArrayList<>();
        nonReferencingRows = new ArrayList<>();

        boolean isSatisfying = true;

        for (Row dataRow : dataRows) {
            if (!isRowSatisfying(dataRow, referenceRows)) {
                isSatisfying = false;
            }
        }

        return isSatisfying;
    }

    protected boolean isRowSatisfying(Row dataRow, List<Row> referenceRows) {

        for (Row referenceRow : referenceRows) {

            Boolean rowEquals = dataRow.valuesEqual3VL(referenceRow);

            if (Boolean.TRUE.equals(rowEquals) || (rowEquals == null && satisfyOnNull)) {
                referencingRows.add(dataRow);
                return true;
            }
        }

        nonReferencingRows.add(dataRow);
        return false;
    }

    public List<Row> getReferencingRows() {
        return referencingRows;
    }

    public List<Row> getNonReferencingRows() {
        return nonReferencingRows;
    }

    public List<Row> getReferenceRows() {
        return referenceRows;
    }
}
