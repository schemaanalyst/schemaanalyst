package org.schemaanalyst.test.coverage.criterion.requirements;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.coverage.criterion.clause.MatchClause;
import org.schemaanalyst.coverage.criterion.clause.NullClause;
import org.schemaanalyst.coverage.criterion.predicate.Predicate;
import org.schemaanalyst.coverage.criterion.requirements.MultiColumnConstraintRequirementsGenerator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.test.testutil.mock.SimpleSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phil on 24/02/2014.
 */
public class TestMultiColumnConstraintRequirementsGenerator {

    private Schema schema;
    private Table tab1, tab2;
    private Column tab1Col1, tab1Col2, tab2Col1, tab2Col2;

    @Before
    public void loadSchema() {
        schema = new SimpleSchema();
        tab1 = schema.getTable("Tab1");
        tab1Col1 = tab1.getColumn("Tab1Col1");
        tab1Col2 = tab1.getColumn("Tab1Col2");

        tab2 = schema.getTable("Tab2");
        tab2Col1 = tab2.getColumn("Tab2Col1");
        tab2Col2 = tab2.getColumn("Tab2Col2");
    }

    @Test
    public void testGeneratedPrimaryKeyRequirements() {
        PrimaryKeyConstraint pk =
                schema.createPrimaryKeyConstraint(tab1, Arrays.asList(tab1Col1, tab1Col2));

        MultiColumnConstraintRequirementsGenerator reqGen
                = new MultiColumnConstraintRequirementsGenerator(schema, pk);

        checkPrimaryKeyGeneratedRequirements(reqGen);
    }

    @Test
    public void testGeneratedForeignKeyRequirements() {
        ForeignKeyConstraint fk =
                schema.createForeignKeyConstraint(
                        tab1, Arrays.asList(tab1Col1, tab1Col2), tab2, Arrays.asList(tab2Col1, tab2Col2));

        MultiColumnConstraintRequirementsGenerator reqGen
                = new MultiColumnConstraintRequirementsGenerator(schema, fk);

        checkForeignKeyGeneratedRequirements(reqGen);
    }

    protected void checkPrimaryKeyGeneratedRequirements(MultiColumnConstraintRequirementsGenerator reqGen) {
        List<Predicate> requirements = reqGen.generateRequirements();
        assertEquals("Number of requirements should be equal to 3", 3, requirements.size());

        // NOTE THAT ORDER IS IMPORTANT HERE!
        checkOneColumnNotEqualPrimaryKeyRequirement(requirements.get(0));
        checkAllColumnsEqualPrimaryKeyRequirement(requirements.get(1));
        checkNullRequirement(requirements.get(2));
    }

    protected void checkOneColumnNotEqualPrimaryKeyRequirement(Predicate predicate) {
        MatchClause matchClause = new MatchClause(tab1, new ArrayList<Column>(), Arrays.asList(tab1Col1, tab1Col2), MatchClause.Mode.OR, true);
        checkMatchClauseRequirement(predicate, matchClause);
    }

    protected void checkAllColumnsEqualPrimaryKeyRequirement(Predicate predicate) {
        MatchClause matchClause = new MatchClause(tab1, Arrays.asList(tab1Col1, tab1Col2), new ArrayList<Column>(), MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicate, matchClause);
    }

    protected void checkForeignKeyGeneratedRequirements(MultiColumnConstraintRequirementsGenerator reqGen) {
        List<Predicate> requirements = reqGen.generateRequirements();
        assertEquals("Number of requirements should be equal to 3", 3, requirements.size());

        // NOTE THAT ORDER IS IMPORTANT HERE!
        checkOneColumnNotEqualForeignKeyRequirement(requirements.get(0));
        checkAllColumnsEqualForeignKeyRequirement(requirements.get(1));
        checkNullRequirement(requirements.get(2));
    }

    protected void checkOneColumnNotEqualForeignKeyRequirement(Predicate predicate) {
        MatchClause matchClause = new MatchClause(
                tab1, new ArrayList<Column>(), Arrays.asList(tab1Col1, tab1Col2),
                tab2, new ArrayList<Column>(), Arrays.asList(tab2Col1, tab2Col2),
                MatchClause.Mode.OR, true);
        checkMatchClauseRequirement(predicate, matchClause);
    }

    protected void checkAllColumnsEqualForeignKeyRequirement(Predicate predicate) {
        MatchClause matchClause = new MatchClause(
                tab1, Arrays.asList(tab1Col1, tab1Col2), new ArrayList<Column>(),
                tab2, Arrays.asList(tab2Col1, tab2Col2), new ArrayList<Column>(),
                MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicate, matchClause);
    }

    protected void checkMatchClauseRequirement(Predicate predicate, MatchClause matchClause) {
        assertTrue(predicate.hasClause(matchClause));
        NullClause notNullClauseCol1 = new NullClause(tab1, tab1Col1, false);
        NullClause notNullClauseCol2 = new NullClause(tab1, tab1Col2, false);

        assertTrue(predicate.hasClause(notNullClauseCol1));
        assertTrue(predicate.hasClause(notNullClauseCol2));
    }

    protected void checkNullRequirement(Predicate predicate) {

        NullClause nullClauseCol1 = new NullClause(tab1, tab1Col1, true);
        NullClause notNullClauseCol1 = new NullClause(tab1, tab1Col1, false);

        NullClause nullClauseCol2 = new NullClause(tab1, tab1Col2, true);
        NullClause notNullClauseCol2 = new NullClause(tab1, tab1Col2, false);

        assertTrue(predicate.hasClause(nullClauseCol1));
        assertFalse(predicate.hasClause(notNullClauseCol1));
        assertFalse(predicate.hasClause(nullClauseCol2));
        assertFalse(predicate.hasClause(notNullClauseCol2));
    }
}
