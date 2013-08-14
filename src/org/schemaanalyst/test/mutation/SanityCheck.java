package org.schemaanalyst.test.mutation;

import org.schemaanalyst.mutation2.artefactsuppliers.SchemaPrimaryKeySupplier;
import org.schemaanalyst.mutation2.mutators.ListMutator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;

import parsedcasestudy.BookTown;

public class SanityCheck {

	public static void main(String[] args) {
		SchemaPrimaryKeySupplier s = new SchemaPrimaryKeySupplier(new BookTown());
		ListMutator<Schema, Column> m = new ListMutator<>(s);
		
	}
	
}
