package org.schemaanalyst.mutation.analysis.executor.testcase;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.testgeneration.TestCase;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.ConstraintSupplier;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.ConstraintSupplierFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.Predicate;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateChecker;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.checker.PredicateCheckerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 28/01/2015.
 */
public class VirtualTestCaseExecutor {

    private Schema schema;
    private ConstraintSupplier constraintSupplier;

    /**
     * Constructor
     * @param schema The schema
     * @param dbms The underlying DBMS on which is schema is intended to be instantiated on
     */
    public VirtualTestCaseExecutor(Schema schema, DBMS dbms) {
        this.schema = schema;
        this.constraintSupplier = ConstraintSupplierFactory.instantiateConstraintSupplier(dbms);
    }

    /**
     * Executes test case
     * @param testCase
     * @return A {@link VirtualTestCaseResult} detailing the statement acceptance.
     */
    public VirtualTestCaseResult executeTestCase(TestCase testCase) {
        Data state = testCase.getState();
        Data data = testCase.getData();

        List<Boolean> results = executeInserts(state, new Data());
        results.addAll(executeInserts(data, state));

        return new VirtualTestCaseResult(results);
    }
    
    /**
     * Executes test case
     * @param testCase
     * @return A list of booleans corresponding to whether the nth statement was accepted or rejected by the DBMS
     */
    public List<Boolean> executeTestCaseBoolean(TestCase testCase) {
        Data state = testCase.getState();
        Data data = testCase.getData();

        List<Boolean> results = executeInserts(state, new Data());
        results.addAll(executeInserts(data, state));

        return results;
    }

    private List<Boolean> executeInserts(Data data, Data state) {
        List<Boolean> results = new ArrayList<>();
        Data runningState = state.duplicate();
        for (Table table : data.getTables()) {
            for (Row row : data.getRows(table)) {
                boolean result = executeInsert(row, runningState);
                if (result) {
                    runningState.addRow(table, row);
                }
                results.add(result);
            }
        }
        return results;
    }

    private boolean executeInsert(Row row, Data runningState) {
        Table table = row.getTable();
        Data data = new Data();
        data.addRow(table, row);
        Predicate predicate = PredicateGenerator.generatePredicate(constraintSupplier.getConstraints(schema, table));
        PredicateChecker predicateChecker = PredicateCheckerFactory.instantiate(predicate, true, data, runningState);
        return predicateChecker.check();
    }
}
