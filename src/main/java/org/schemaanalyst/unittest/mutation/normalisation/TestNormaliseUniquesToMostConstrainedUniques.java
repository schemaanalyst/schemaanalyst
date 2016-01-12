package org.schemaanalyst.unittest.mutation.normalisation;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.schemaanalyst.mutation.normalisation.NormaliseUniquesToMostConstrainedUniques;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

/**
 *
 * @author Chris J. Wright
 */
public class TestNormaliseUniquesToMostConstrainedUniques {
    NormaliseUniquesToMostConstrainedUniques norm = new NormaliseUniquesToMostConstrainedUniques();

    private Schema createBaseSchema() {
        Schema s = new Schema("schema");
        Table table = s.createTable("table");
        table.addColumn(new Column("a", new IntDataType()));
        table.addColumn(new Column("b", new IntDataType()));
        table.addColumn(new Column("c", new IntDataType()));
        return s;
    }
    
    private Schema createSchemaWithOneUnique() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createUniqueConstraint(table, table.getColumn("a"));
        return s;
    }
    
    private Schema createSchemaWithTwoUniques() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("b"));
        return s;
    }
    
    private Schema createSchemaWithTwoIntersectingUniques() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("a"), table.getColumn("b"));
        return s;
    }
    
    private Schema createSchemaWithTwoMisorderedIntersectingUniques() {
        // Ordering of unique cols should not matter
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("b"), table.getColumn("a"));
        return s;
    }
    
    private Schema createSchemaWithThreeIntersectingUniques() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("b"));
        s.createUniqueConstraint(table, table.getColumn("a"), table.getColumn("b"));
        return s;
    }
    
    private Schema createSchemaWithTwoIntersectingUniquesAndOneNot() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("a"), table.getColumn("b"));
        s.createUniqueConstraint(table, table.getColumn("b"), table.getColumn("c"));
        return s;
    }
    
    private Schema createSchemaWithTwoPairsOfIntersectingUniques() {
        Schema s = createBaseSchema();
        Table table  = s.getTable("table");
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("a"), table.getColumn("b"));
        s.createUniqueConstraint(table, table.getColumn("b"));
        s.createUniqueConstraint(table, table.getColumn("b"), table.getColumn("c"));
        return s;
    }
    
    private Schema createSchemaWithFKNotDependent() {
        Schema s = createBaseSchema();
        Table table = s.getTable("table");
        Table table2 = s.createTable("table2");
        table2.addColumn(new Column("a", new IntDataType()));
        table2.addColumn(new Column("b", new IntDataType()));
        table2.addColumn(new Column("c", new IntDataType()));
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("a"), table.getColumn("b"));
        s.createForeignKeyConstraint(table2, table2.getColumn("a"), table, table.getColumn("a"));
        return s;
    }
    
    private Schema createSchemaWithFKDependent() {
        Schema s = createBaseSchema();
        Table table = s.getTable("table");
        Table table2 = s.createTable("table2");
        table2.addColumn(new Column("a", new IntDataType()));
        table2.addColumn(new Column("b", new IntDataType()));
        table2.addColumn(new Column("c", new IntDataType()));
        s.createUniqueConstraint(table, table.getColumn("a"));
        s.createUniqueConstraint(table, table.getColumn("a"), table.getColumn("b"));
        List<Column> localCols = Arrays.asList(new Column[]{table2.getColumn("a"), table2.getColumn("b")});
        List<Column> refCols = Arrays.asList(new Column[]{table.getColumn("a"), table.getColumn("b")});
        s.createForeignKeyConstraint(table2, localCols, table, refCols);
        return s;
    }
    
    @Test
    public void testOneUnique() {
        Schema original = createSchemaWithOneUnique();
        Schema normalised = norm.normalise(original.duplicate());
        assertEquals("Normalising schema with one unique should yield one unique",
                1, normalised.getUniqueConstraints().size());
    }
    
    @Test
    public void testTwoUniques() {
        Schema original = createSchemaWithTwoUniques();
        Schema normalised = norm.normalise(original.duplicate());
        assertEquals("Normalising schema with two non-interesting uniques should yield two uniques",
                2, normalised.getUniqueConstraints().size());
    }
    
    @Test
    public void testTwoIntserectingUniques() {
        Schema original = createSchemaWithTwoIntersectingUniques();
        Schema normalised = norm.normalise(original.duplicate());
        assertEquals("Normalising schema with two intersecting uniques should yield one unique",
                1, normalised.getUniqueConstraints().size());
        Table table = normalised.getTable("table");
        List<Column> cols = Arrays.asList(new Column[]{table.getColumn("a")});
        assertEquals("Normalising schema with (a), (a,b) should yield (a)",
                cols, normalised.getUniqueConstraints().get(0).getColumns());
    }
    
    @Test
    public void testTwoMisorderedIntersectingUniques() {
        Schema original = createSchemaWithTwoMisorderedIntersectingUniques();
        Schema normalised = norm.normalise(original.duplicate());
        assertEquals("Normalising schema with two intersecting uniques should yield one unique",
                1, normalised.getUniqueConstraints().size());
        Table table = normalised.getTable("table");
        List<Column> cols = Arrays.asList(new Column[]{table.getColumn("a")});
        assertEquals("Normalising schema with (a), (b,a) should yield (a)",
                cols, normalised.getUniqueConstraints().get(0).getColumns());
    }
    
    @Test
    public void testThreeIntersectingUniques() {
        Schema original = createSchemaWithThreeIntersectingUniques();
        Schema normalised = norm.normalise(original.duplicate());
        assertEquals("Normalising schema with three intersecting uniques should yield two uniques",
                2, normalised.getUniqueConstraints().size());
        Table table = normalised.getTable("table");
        List<Column> cols1 = Arrays.asList(new Column[]{table.getColumn("a")});
        List<Column> cols2 = Arrays.asList(new Column[]{table.getColumn("b")});
        assertEquals("Normalising schema with (a), (b), (a,b) should yield (a), (b)",
                cols1, normalised.getUniqueConstraints().get(0).getColumns());
        assertEquals("Normalising schema with (a), (b), (a,b) should yield (a), (b)",
                cols2, normalised.getUniqueConstraints().get(1).getColumns());
    }
    
    @Test
    public void testTwoIntersectingUniquesAndOneNot() {
        Schema original = createSchemaWithTwoIntersectingUniquesAndOneNot();
        Schema normalised = norm.normalise(original.duplicate());
        assertEquals("Normalising schema with two intersecting uniques and one not should yield two uniques",
                2, normalised.getUniqueConstraints().size());
        Table table = normalised.getTable("table");
        List<Column> cols1 = Arrays.asList(new Column[]{table.getColumn("a")});
        List<Column> cols2 = Arrays.asList(new Column[]{table.getColumn("b"),table.getColumn("c")});
        assertEquals("Normalising schema with (a), (b,a), (b,c) should yield (a), (b,c)",
                cols1, normalised.getUniqueConstraints().get(0).getColumns());
        assertEquals("Normalising schema with (a), (b,a), (b,c) should yield (a), (b,c)",
                cols2, normalised.getUniqueConstraints().get(1).getColumns());
    }
    
    @Test
    public void testTwoPairsOfIntersectingUniques() {
        Schema original = createSchemaWithTwoPairsOfIntersectingUniques();
        Schema normalised = norm.normalise(original.duplicate());
        assertEquals("Normalising schema with two pairs of intersecting uniques should yield two uniques",
                2, normalised.getUniqueConstraints().size());
        Table table = normalised.getTable("table");
        List<Column> cols1 = Arrays.asList(new Column[]{table.getColumn("a")});
        List<Column> cols2 = Arrays.asList(new Column[]{table.getColumn("b")});
        assertEquals("Normalising schema with (a), (b,a), (b), (b,c) should yield (a), (b)",
                cols1, normalised.getUniqueConstraints().get(0).getColumns());
        assertEquals("Normalising schema with (a), (b,a), (b), (b,c) should yield (a), (b)",
                cols2, normalised.getUniqueConstraints().get(1).getColumns());
    }
    
    @Test
    public void testFKNotDependent() {
        Schema original = createSchemaWithFKNotDependent();
        Schema normalised = norm.normalise(original);
        assertEquals("Normalising schema with two uniques and a non-dependent FK should have one unique",
                1, normalised.getUniqueConstraints().size());
        assertEquals("Normalising schema with two uniques and a non-dependent FK should have one FK",
                1, normalised.getForeignKeyConstraints().size());
        Table table = normalised.getTable("table");
        List<Column> cols = Arrays.asList(new Column[]{table.getColumn("a")});
        assertEquals("Normalising schema with (a), (a,b), FK(a->a) should yield (a), FK(a->a)",
                cols, normalised.getUniqueConstraints().get(0).getColumns());
    }
    
    @Test
    public void testFKDependent() {
        Schema original = createSchemaWithFKDependent();
        Schema normalised = norm.normalise(original);
        assertEquals("Normalising schema with two uniques and a dependent FK should have two uniques",
                2, normalised.getUniqueConstraints().size());
        assertEquals("Normalising schema with two uniques and a dependent FK should have one FK",
                1, normalised.getForeignKeyConstraints().size());
        Table table = normalised.getTable("table");
        List<Column> cols1 = Arrays.asList(new Column[]{table.getColumn("a")});
        List<Column> cols2 = Arrays.asList(new Column[]{table.getColumn("a"), table.getColumn("b")});
        assertEquals("Normalising schema with (a), (a,b), FK(a->a) should yield (a), (a,b) FK(a,b->a,b)",
                cols1, normalised.getUniqueConstraints().get(0).getColumns());
        assertEquals("Normalising schema with (a), (a,b), FK(a->a) should yield (a), (a,b) FK(a,b->a,b)",
                cols2, normalised.getUniqueConstraints().get(1).getColumns());
    }
}
