package org.schemaanalyst.test.testgeneration.coveragecriterion.requirements;

import org.junit.Before;
import org.junit.Test;
import org.schemaanalyst.logic.predicate.Predicate;
import org.schemaanalyst.logic.predicate.clause.MatchClause;
import org.schemaanalyst.logic.predicate.clause.NullClause;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.test.testutil.mock.SimpleSchema;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.MultiColumnConstraintRequirementsGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion_old.requirements.Requirements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
    public void testGeneratedMultiColumnConstraintRequirements() {
        PrimaryKeyConstraint pk =
                schema.createPrimaryKeyConstraint(tab1, Arrays.asList(tab1Col1, tab1Col2));

        MultiColumnConstraintRequirementsGenerator reqGen
                = new MultiColumnConstraintRequirementsGenerator(schema, pk);

        Requirements requirements = reqGen.generateRequirements();
        assertEquals("Number of requirements should be equal to 3", 3, requirements.size());

        List<Predicate> predicates = requirements.getPredicates();

        // at least one column not equal
        MatchClause matchClause1 = new MatchClause(
                tab1, new ArrayList<Column>(), Arrays.asList(tab1Col1, tab1Col2),
                MatchClause.Mode.OR, true);
        checkMatchClauseRequirement(predicates.get(0), matchClause1);

        // all columns equal
        MatchClause matchClause2 = new MatchClause(
                tab1, Arrays.asList(tab1Col1, tab1Col2), new ArrayList<Column>(),
                MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicates.get(1), matchClause2);

        // null requirement
        checkNullRequirement(predicates.get(2));
    }

    @Test
    public void testGeneratedForeignKeyRequirements() {
        ForeignKeyConstraint fk =
                schema.createForeignKeyConstraint(
                        tab1, Arrays.asList(tab1Col1, tab1Col2), tab2, Arrays.asList(tab2Col1, tab2Col2));

        MultiColumnConstraintRequirementsGenerator reqGen
                = new MultiColumnConstraintRequirementsGenerator(schema, fk);

        Requirements requirements = reqGen.generateRequirements();
        assertEquals("Number of requirements should be equal to 3", 3, requirements.size());

        List<Predicate> predicates = requirements.getPredicates();

        // at least one column not equal
        MatchClause matchClause1 = new MatchClause(
                tab1, new ArrayList<Column>(), Arrays.asList(tab1Col1, tab1Col2),
                tab2, new ArrayList<Column>(), Arrays.asList(tab2Col1, tab2Col2),
                MatchClause.Mode.OR, true);
        checkMatchClauseRequirement(predicates.get(0), matchClause1);

        MatchClause matchClause2 = new MatchClause(
                tab1, Arrays.asList(tab1Col1, tab1Col2), new ArrayList<Column>(),
                tab2, Arrays.asList(tab2Col1, tab2Col2), new ArrayList<Column>(),
                MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicates.get(1), matchClause2);

        // null requirement
        checkNullRequirement(predicates.get(2));
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
