package org.schemaanalyst.mutation2.artefactsuppliers;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.mutation2.ArtefactSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
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
			boolean isAlternative = true;
			for (Column pkColumn : pkColumns) {
				if (tableColumn.getName().equals(pkColumn.getName())) {
					isAlternative = false;
				}
			}
			if (isAlternative) {
				alternatives.add(tableColumn);
			}
		}
		
		return new Pair<>(pk.getColumns(), table.getColumns());
	}
	

	@Override
	public void putComponentBack(Pair<List<Column>> columnListPair) {
		// the alternatives (columnListPair.getSecond()) are ignored here ...
		List<Column> pkColumns = columnListPair.getFirst();
		
		Table table = tables.get(tableIndex);
		PrimaryKeyConstraint pk = table.getPrimaryKeyConstraint();
		if (pk == null) {
			table.setPrimaryKeyConstraint(pkColumns);
		} else {
			pk.setColumns(pkColumns);
		}
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
