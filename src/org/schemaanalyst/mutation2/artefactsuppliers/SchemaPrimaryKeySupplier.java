package org.schemaanalyst.mutation2.artefactsuppliers;

import java.util.List;

import org.schemaanalyst.mutation2.ArtefactSupplier;
import org.schemaanalyst.mutation2.MutationException;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class SchemaPrimaryKeySupplier extends ArtefactSupplier<Schema, List<Column>>{

	private int tableIndex;
	
	public SchemaPrimaryKeySupplier(Schema schema) {
		super(schema);
		tableIndex = 0;
	}
	
	@Override
	public List<Column> getNextComponent() {
		List<Column> columns = getComponent(originalArtefact);
		tableIndex ++;
		return columns;
	}

	protected List<Column> getComponent(Schema schema) {
		PrimaryKeyConstraint pk = getPrimaryKeyConstraintUsingTableIndex(schema);			
		if (pk == null) {
			return null;
		}
		
		return pk.getColumns();
	}
	
	@Override
	public List<Column> getComponentCopy() {
		PrimaryKeyConstraint pk = getPrimaryKeyConstraintUsingTableIndex(currentCopy);
		if (pk == null) {
			throw new MutationException("Error putting Primary Key columns back into copy -- no primary key exists");
		}		
		return pk.getColumns();
	}	
		
	@Override
	public void putComponentBack(List<Column> columns) {
		PrimaryKeyConstraint pk = getPrimaryKeyConstraintUsingTableIndex(currentCopy);
		if (pk == null) {
			throw new MutationException("Error putting Primary Key columns back into copy -- no primary key exists");
		}
		
		pk.setColumns(columns);
	}

	private PrimaryKeyConstraint getPrimaryKeyConstraintUsingTableIndex(Schema schema) {
		List<Table> tables = schema.getTables();		
		if (tableIndex < tables.size()) {
			Table currentTable = tables.get(tableIndex);
			return currentTable.getPrimaryKeyConstraint();
		} else {
			return null;
		}
	}	
}
