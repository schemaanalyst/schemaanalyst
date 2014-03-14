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
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.MultiColumnConstraintCACRequirementsGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.requirements.Requirements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 24/02/2014.
 */
public class TestMultiColumnConstraintCACRequirementsGenerator {

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

        MultiColumnConstraintCACRequirementsGenerator reqGen
                = new MultiColumnConstraintCACRequirementsGenerator(schema, pk);

        Requirements requirements = reqGen.generateRequirements();
        assertEquals("Number of requirements should be equal to 5", 5, requirements.size());

        List<Predicate> predicates = requirements.getPredicates();

        // first column not equal
        MatchClause matchClause1 = new MatchClause(
                tab1, Arrays.asList(tab1Col2), Arrays.asList(tab1Col1),
                MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicates.get(0), matchClause1);

        // second column not equal
        MatchClause matchClause2 = new MatchClause(
                tab1, Arrays.asList(tab1Col1), Arrays.asList(tab1Col2),
                MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicates.get(1), matchClause2);

        // all columns equal
        MatchClause matchClause = new MatchClause(
                tab1, Arrays.asList(tab1Col1, tab1Col2), new ArrayList<Column>(),
                MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicates.get(2), matchClause);

        // first column null
        checkNullClauseRequirement(predicates.get(3), tab1Col1, tab1Col2);

        // second column null
        checkNullClauseRequirement(predicates.get(4), tab1Col2, tab1Col1);
    }

    @Test
    public void testGeneratedForeignKeyRequirements() {
        ForeignKeyConstraint fk =
                schema.createForeignKeyConstraint(
                        tab1, Arrays.asList(tab1Col1, tab1Col2), tab2, Arrays.asList(tab2Col1, tab2Col2));

        MultiColumnConstraintCACRequirementsGenerator reqGen
                = new MultiColumnConstraintCACRequirementsGenerator(schema, fk);

        Requirements requirements = reqGen.generateRequirements();
        assertEquals("Number of requirements should be equal to 5", 5, requirements.size());

        List<Predicate> predicates = requirements.getPredicates();

        // first column not equal
        MatchClause matchClause1 = new MatchClause(
                tab1, Arrays.asList(tab1Col2), Arrays.asList(tab1Col1),
                tab2, Arrays.asList(tab2Col2), Arrays.asList(tab2Col1),
                MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicates.get(0), matchClause1);

        // second column not equal
        MatchClause matchClause2 = new MatchClause(
                tab1, Arrays.asList(tab1Col1), Arrays.asList(tab1Col2),
                tab2, Arrays.asList(tab2Col1), Arrays.asList(tab2Col2),
                MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicates.get(1), matchClause2);

        // all columns equal
        MatchClause matchClause = new MatchClause(
                tab1, Arrays.asList(tab1Col1, tab1Col2), new ArrayList<Column>(),
                tab2, Arrays.asList(tab2Col1, tab2Col2), new ArrayList<Column>(),
                MatchClause.Mode.AND, true);
        checkMatchClauseRequirement(predicates.get(2), matchClause);

        // first column null
        checkNullClauseRequirement(predicates.get(3), tab1Col1, tab1Col2);

        // second column null
        checkNullClauseRequirement(predicates.get(4), tab1Col2, tab1Col1);
    }

    protected void checkMatchClauseRequirement(Predicate predicate, MatchClause matchClause) {
        assertTrue(predicate.hasClause(matchClause));
        NullClause notNullClauseCol1 = new NullClause(tab1, tab1Col1, false);
        NullClause notNullClauseCol2 = new NullClause(tab1, tab1Col2, false);
        assertTrue(predicate.hasClause(notNullClauseCol1));
        assertTrue(predicate.hasClause(notNullClauseCol2));
    }

    protected void checkNullClauseRequirement(Predicate predicate, Column nullColumn, Column notNullColumn) {
        NullClause nullClause= new NullClause(tab1, nullColumn, true);
        NullClause notNullClause = new NullClause(tab1, notNullColumn, false);
        assertTrue(predicate.hasClause(nullClause));
        assertTrue(predicate.hasClause(notNullClause));
    }

}
