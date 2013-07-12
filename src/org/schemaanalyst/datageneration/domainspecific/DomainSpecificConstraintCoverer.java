package org.schemaanalyst.datageneration.domainspecific;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.datageneration.ConstraintCoverageReport;
import org.schemaanalyst.datageneration.ConstraintGoalReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.cellrandomisation.CellRandomiser;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.util.random.Random;

// NOTES: much is similar to SearchConstraintCoverer -- candidate for later refactoring
public class DomainSpecificConstraintCoverer extends DataGenerator {

    protected Schema schema;
    protected Data state;
    protected ValueFactory valueFactory;
    protected int satisfyRows, negateRows;
    protected int maxEvaluations;
    protected CellRandomiser cellRandomizer;
    protected Random random;
    protected ConstraintGoalReport goalReport;

    public DomainSpecificConstraintCoverer(Schema schema,
            ValueFactory valueFactory,
            int satisfyRows,
            int negateRows,
            int maxEvaluations,
            CellRandomiser cellRandomizer,
            Random random) {
        this.schema = schema;
        this.valueFactory = valueFactory;
        this.satisfyRows = satisfyRows;
        this.negateRows = negateRows;
        this.maxEvaluations = maxEvaluations;
        this.state = new Data();
        this.cellRandomizer = cellRandomizer;
        this.random = random;
    }

    public ConstraintCoverageReport generate() {
        ConstraintCoverageReport report = new ConstraintCoverageReport(schema);

        goalReport = new ConstraintGoalReport(null);
        generateData(ConstraintProblem.satisfyAll(schema), satisfyRows);
        report.addGoalReport(goalReport);

        for (Constraint constraint : schema.getConstraints()) {
            goalReport = new ConstraintGoalReport(constraint);
            generateData(ConstraintProblem.negateOne(schema, constraint), negateRows);
            report.addGoalReport(goalReport);
        }

        return report;
    }

    protected void generateData(ConstraintProblem constraintProblem, int numRows) {
        // obtain the list of tables involved in the problem
        List<Table> tables = constraintProblem.getTables();

        // create the appropriate data object
        Data data = new Data();
        data.addRows(tables, numRows, valueFactory);

        // perform the search
        perform(constraintProblem, data);

        // add rows to the state
        if (goalReport.wasSuccess()) {
            for (Table table : constraintProblem.getTablesForConstraintsToSatisfy()) {
                state.addRows(table, data.getRows(table));
            }
        }
    }

    protected void perform(ConstraintProblem constraintProblem, Data data) {
        // create the report and start the clock ...
        goalReport.startTimer();

        List<ConstraintHandler<?>> handlers = createConstraintHandlers(constraintProblem);

        int attempts = 0;
        boolean goalAcheived = false;

        while (!goalAcheived && attempts < maxEvaluations) {
            goalAcheived = true;

            for (ConstraintHandler<?> handler : handlers) {

                boolean isFulfilled = handler.isFulfilled(state, data);
                if (!isFulfilled) {
                    goalAcheived = false;
                    handler.attempt(state, data);
                    attempts++;
                }
            }
        }

        // end the timer and add the necessary info to the report
        goalReport.endTimer();
        goalReport.setData(data);
        goalReport.setSuccess(goalAcheived);
    }

    protected List<ConstraintHandler<?>> createConstraintHandlers(ConstraintProblem constraintProblem) {

        List<ConstraintHandler<?>> handlers = new ArrayList<>();


        for (Constraint constraint : constraintProblem.getConstraintsToSatisfy()) {
            handlers.add(new ConstraintHandlerFactory(constraint,
                    true,
                    cellRandomizer,
                    random).create());
        }

        for (Constraint constraint : constraintProblem.getConstraintsToNegate()) {
            handlers.add(new ConstraintHandlerFactory(constraint,
                    false,
                    cellRandomizer,
                    random).create());
        }

        return handlers;
    }
}
