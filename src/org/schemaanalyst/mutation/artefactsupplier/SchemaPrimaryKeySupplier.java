package org.schemaanalyst.mutation.artefactsupplier;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.util.Pair;

public class SchemaPrimaryKeySupplier extends ArtefactSupplier<Schema, Pair<List<Column>>>{

	private List<Table> tables;	
	private int tableIndex;
	
	public SchemaPrimaryKeySupplier(Schema schema) {
		super(schema);
		tableIndex = 0;
		tables = schema.getTables();
	}
	
	@Override
	public Pair<List<Column>> getNextComponent() {
		Pair<List<Column>> component = getComponent(originalArtefact);
		tableIndex ++;
		return component;
	}
	
	@Override
	public Pair<List<Column>> getComponentCopy() {
		return getComponent(currentCopy);
	}	
			
	private Pair<List<Column>> getComponent(Schema schema) {
		Table table;
		if (tableIndex < tables.size()) {
			table = tables.get(tableIndex);
		} else {
			return null;
		}
		
		PrimaryKeyConstraint pk = table.getPrimaryKeyConstraint();
	
		List<Column> pkColumns = (pk == null) ? new ArrayList<Column>() : pk.getColumns();
		List<Column> alternatives = new ArrayList<>();
		
		for (Column tableColumn : table.getColumns()) {
			if (!pkColumns.contains(tableColumn)) {
				alternatives.add(tableColumn);
			}
		}
		
		return new Pair<>(pkColumns, table.getColumns());
	}

	@Override
	public void putComponentBack(Pair<List<Column>> columnListPair) {
		// the alternatives (columnListPair.getSecond()) are ignored here ...
		List<Column> pkColumns = columnListPair.getFirst();
		
		Table table = tables.get(tableIndex);
		
		if (pkColumns.size() == 0) {
		    table.removePrimaryKeyConstraint();
		} else {
		    if (table.hasPrimaryKeyConstraint()) {
		        PrimaryKeyConstraint pk = table.getPrimaryKeyConstraint();
		        pk.setColumns(pkColumns);
		    } else {
		        table.createPrimaryKeyConstraint(pkColumns);
		    }
		}
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
