package org.schemaanalyst.test.mutation.artefactsupplier;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.schemaanalyst.mutation.artefactsupplier.SchemaPrimaryKeySupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.util.Pair;

import static org.junit.Assert.*;

public class TestSchemaPrimaryKeySupplier {

    @Test
    public void testNoTables() {
        Schema schema = new Schema("test");
        SchemaPrimaryKeySupplier spks = new SchemaPrimaryKeySupplier(schema);
        
        assertNull(
                "If there are no tables in the schema getNextComponent should return null",
                spks.getNextComponent());

        // need to get an artefact copy before the component
        spks.getArtefactCopy();
        
        assertNull(
                "If there are no tables in the schema getComponentCopy should return null",
                spks.getComponentCopy());
    }
    
    @Ignore
    @Test
    public void testNoPKs() {
        Schema schema = new Schema("test");
        schema.createTable("test");
        SchemaPrimaryKeySupplier spks = new SchemaPrimaryKeySupplier(schema);
        
        assertNull(
                "If there are no PKs in the schema getNextComponent should return null",
                spks.getNextComponent());
        
        // need to get an artefact copy before the component
        spks.getArtefactCopy();
        
        assertNull(
                "If there are no PKs in the schema getComponentCopy should return null",
                spks.getComponentCopy());
    }
    
    @Ignore
    @Test
    public void testOnePK() {
        Schema schema = new Schema("schema");
        Table table = schema.createTable("table");
        Column column1 = table.createColumn("column1", new IntDataType());
        Column column2 = table.createColumn("column2", new IntDataType());
        PrimaryKeyConstraint pk = table.createPrimaryKeyConstraint(column1);
        
        SchemaPrimaryKeySupplier spks = new SchemaPrimaryKeySupplier(schema);

        Pair<List<Column>> firstComponent = spks.getNextComponent();
        
        assertNotNull(
                "If there is one PKs in the schema getNextComponent should not return null",
                firstComponent);
        
        assertEquals(
                "The first component column fields should be equal to that of the PK",
                pk.getColumns(), firstComponent.getFirst()); 
        
        assertEquals(
                "The remaing column fields should be the remaining column fields",
                column2, firstComponent.getSecond().get(0));
        
    }
}
