package org.schemaanalyst.testgeneration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;

public class ReduecTestCase {

	public void reduceData(Data data, Data state, Schema schema) {
		List<Table> tables = data.getTables();

		for (Table table : tables) {
			List<ForeignKeyConstraint> fks = schema.getForeignKeyConstraints(table);
			Table previousTab = new Table("");
			List<Column> previousCol = new ArrayList<>();
			for (int i = 0; i < fks.size(); i++) {
				// a hack for self-ref
				ForeignKeyConstraint fk = fks.get(i);
				if (!fk.getTable().equals(fk.getReferenceTable())) {
					if (!previousTab.equals(fk.getReferenceTable()) && !fk.getReferenceColumns().equals(previousCol)) {
						// Getting Values rather than just rows
						List<Value> childValues = new ArrayList<>();
						List<Value> parentValues = new ArrayList<>();
						List<Value> parentStateValues = new ArrayList<>();

						// Getting the values rather than just the names as the
						// column names differ
						for (Row row : data.getRows(table, fk.getColumns())) {
							for (Cell cell : row.getCells()) {
								childValues.add(cell.getValue());
							}
						}

						for (Row row : data.getRows(fk.getReferenceTable(), fk.getReferenceColumns())) {
							for (Cell cell : row.getCells()) {
								parentValues.add(cell.getValue());
							}
						}

						for (Row row : state.getRows(fk.getReferenceTable(), fk.getReferenceColumns())) {
							for (Cell cell : row.getCells()) {
								parentStateValues.add(cell.getValue());
							}
						}
						
						boolean aretheValueEqual = CollectionUtils.isEqualCollection(childValues, parentValues);

						if (fks.size() > i+1 && !aretheValueEqual) {
							if (fk.getReferenceTable().equals(fks.get(i+1).getReferenceTable())) {
								ForeignKeyConstraint fk2 = fks.get(i+1);
								List<Value> childValues2 = new ArrayList<>();
								// column names differ
								for (Row row : data.getRows(fk2.getTable(), fk2.getColumns())) {
									for (Cell cell : row.getCells()) {
										childValues2.add(cell.getValue());
									}
								}
								aretheValueEqual = CollectionUtils.isEqualCollection(childValues2, parentValues);
							}
						}

						if (!aretheValueEqual) {
							data.removeTable(fk.getReferenceTable());
						}
					}

					previousTab = fk.getReferenceTable();
					previousCol = fk.getReferenceColumns();
				}
			}
		}

	}
}