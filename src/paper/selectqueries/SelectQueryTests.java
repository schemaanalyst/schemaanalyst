package paper.selectqueries;

import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.DataGeneratorFactory;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.SelectQuery;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.TestSuite;
import org.schemaanalyst.testgeneration.TestSuiteGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementID;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.ICMinimalConstraintSupplier;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.AndPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ComposedPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.ExpressionPredicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import parsedcasestudy.FrenchTowns;

/**
 * Created by phil on 10/11/2014.
 */
public class SelectQueryTests {

    public static void main(String... args) {

        Schema schema = new FrenchTowns();
        DataGenerator dg = DataGeneratorFactory.avsDefaultsGenerator(0, 100000, schema);
        ValueFactory vf = new ValueFactory();

        Table table = schema.getTable("Regions");

        SelectQuery sq = new SelectQuery(table,
                new RelationalExpression(
                        new ColumnExpression(table, table.getColumn("id")),
                        RelationalOperator.EQUALS,
                        new ConstantExpression(new NumericValue(10))
                )
        );

        Predicate predicate = PredicateGenerator.generatePredicate((new ICMinimalConstraintSupplier()).getConstraints(schema, table));

        ComposedPredicate topLevelPredicate = new AndPredicate();
        topLevelPredicate.addPredicate(new ExpressionPredicate(table, sq.getWhereClause(), true));
        topLevelPredicate.addPredicate(predicate);

        TestRequirements tr = new TestRequirements();
        tr.addTestRequirement(new TestRequirement(
                new TestRequirementDescriptor(new TestRequirementID(1, "test"),"test"),
                topLevelPredicate,
                true, // expected result (whether data should go into the database)
                false // requires comparison row?
        ));


        TestSuiteGenerator tsg = new TestSuiteGenerator(schema, tr, vf, dg);

        TestSuite ts = tsg.generate();
        for (TestCase tc : ts.getTestCases()) {
            System.out.println(tc.getData());
        }

    }

}
