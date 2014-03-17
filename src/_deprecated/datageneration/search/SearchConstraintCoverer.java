package _deprecated.datageneration.search;

import org.schemaanalyst.data.Data;
import _deprecated.datageneration.ConstraintGoal;
import _deprecated.datageneration.DataGenerator;
import _deprecated.datageneration.TestCase;
import _deprecated.datageneration.TestSuite;
import _deprecated.datageneration.search.objective.constraint.SchemaConstraintSystemObjectiveFunction;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

import java.util.List;

public class SearchConstraintCoverer extends DataGenerator<ConstraintGoal> {

    protected Schema schema;
    protected Data state;
    protected Search<Data> search;
    protected DBMS dbms;
    protected int satisfyRows, negateRows;

    public SearchConstraintCoverer(Search<Data> search,
                                   Schema schema,
                                   DBMS dbms,
                                   int satisfyRows,
                                   int negateRows) {
        this.search = search;
        this.schema = schema;
        this.dbms = dbms;
        this.satisfyRows = satisfyRows;
        this.negateRows = negateRows;
        this.state = new Data();
    }

    @Override
    public TestSuite<ConstraintGoal> generate() {
        TestSuite<ConstraintGoal> testSuite = new TestSuite<>("Constraint coverage for " + schema);

        testSuite.addTestCase(satisfyAllConstraints());

        for (Constraint constraint : schema.getConstraints()) {
        	testSuite.addTestCase(negateConstraint(constraint));
        }

        return testSuite;
    }

    protected TestCase<ConstraintGoal> satisfyAllConstraints() {
        return generateData(new ConstraintGoal(null, true), schema.getTables(), satisfyRows);
    }

    protected TestCase<ConstraintGoal> negateConstraint(Constraint constraint) {
        Table constraintTable = constraint.getTable();
        List<Table> tables = schema.getConnectedTables(constraintTable);
        tables.add(constraintTable);

        return generateData(new ConstraintGoal(constraint, false), tables, negateRows);
    }

    protected TestCase<ConstraintGoal> generateData(ConstraintGoal constraintGoal, List<Table> tables, int numRows) {
    	Constraint constraint = constraintGoal.getConstraint();
    	
        // create the appropriate data object
        Data data = new Data();
        data.addRows(tables, numRows, dbms.getValueFactory());

        // initialise everything needed for the generation and perform it
        search.setObjectiveFunction(new SchemaConstraintSystemObjectiveFunction(schema, state, constraint));
        TestCase<ConstraintGoal> testCase = performSearch(constraintGoal, data);

        // add rows to the state
        if (testCase.getNumCoveredElements() != 0) {
            for (Table table : tables) {
                if (constraint == null || !constraint.getTable().equals(table)) {
                    state.addRows(table, data.getRows(table));
                }
            }
        }

        testCase.setNumEvaluations(search.getNumEvaluations());
        testCase.setNumRestarts(search.getNumRestarts());
        
        return testCase;
    }

    protected TestCase<ConstraintGoal> performSearch(ConstraintGoal constraintGoal, Data data) {
        // set up the test case and start the timer
        SearchTestCase<ConstraintGoal> testCase = new SearchTestCase<>(constraintGoal.toString());
        testCase.startTimer();

        // do the generation
        search.initialize();
        search.search(data);

        // end the timer and add the necessary info to the report
        testCase.endTimer();
        testCase.setData(search.getBestCandidateSolution());        
        testCase.setNumEvaluations(search.getEvaluationsCounter().counter);
        testCase.setNumRestarts(search.getNumRestarts());
        testCase.setBestObjectiveValue(search.getBestObjectiveValue());

        if (search.getBestObjectiveValue().isOptimal()) {
        	testCase.addCoveredElement(constraintGoal);
        }
        
        return testCase;
    }
}
