package org.schemaanalyst.datageneration.search;

import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.ConstraintCoverageReport;
import org.schemaanalyst.datageneration.DataGenerator;
import org.schemaanalyst.datageneration.search.objective.constraint.SchemaConstraintSystemObjectiveFunction;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;

public class SearchConstraintCoverer extends DataGenerator {

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
    public ConstraintCoverageReport generate() {
        ConstraintCoverageReport report = new ConstraintCoverageReport(schema);

        SearchConstraintGoalReport goalReport = satisfyAllConstraints();
        report.addGoalReport(goalReport);

        for (Constraint constraint : schema.getConstraints()) {
            goalReport = negateConstraint(constraint);
            report.addGoalReport(goalReport);
        }

        return report;
    }

    protected SearchConstraintGoalReport satisfyAllConstraints() {
        return generateData(null, schema.getTablesInOrder(), satisfyRows);
    }

    protected SearchConstraintGoalReport negateConstraint(Constraint constraint) {
        Table constraintTable = constraint.getTable();
        List<Table> tables = constraintTable.getConnectedTables();
        tables.add(constraintTable);

        return generateData(constraint, tables, negateRows);
    }

    protected SearchConstraintGoalReport generateData(Constraint constraint, List<Table> tables, int numRows) {
        // create the appropriate data object
        Data data = new Data();
        data.addRows(tables, numRows, dbms.getValueFactory());

        // initialise everything needed for the search and perform it
        search.setObjectiveFunction(new SchemaConstraintSystemObjectiveFunction(schema, state, constraint));
        SearchConstraintGoalReport goalReport = performSearch(constraint, data);

        // add rows to the state
        if (goalReport.wasSuccess()) {
            for (Table table : tables) {
                if (constraint == null || !constraint.getTable().equals(table)) {
                    state.addRows(table, data.getRows(table));
                }
            }
        }

        return goalReport;
    }

    protected SearchConstraintGoalReport performSearch(Constraint constraint,
            Data data) {
        // create the report and start the clock ...
        SearchConstraintGoalReport goalReport = new SearchConstraintGoalReport(constraint);
        goalReport.startTimer();

        // do the search
        search.initialize();
        search.search(data);

        // end the timer and add the necessary info to the report
        goalReport.endTimer();
        goalReport.setData(search.getBestCandidateSolution());
        goalReport.setSuccess(search.getBestObjectiveValue().isOptimal());
        goalReport.setNumEvaluations(search.getEvaluationsCounter().counter);
        goalReport.setNumRestarts(search.getNumRestarts());
        goalReport.setBestObjectiveValue(search.getBestObjectiveValue());

        return goalReport;
    }
}
