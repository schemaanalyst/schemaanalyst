/*
 */
package org.schemaanalyst.mutation.operator;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.MutationPipeline;
import org.schemaanalyst.mutation.mutator.RelationalOperatorExchanger;
import org.schemaanalyst.mutation.supplier.ChainedSupplier;
import org.schemaanalyst.mutation.supplier.expression.RelationalExpressionOperatorSupplier;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import deprecated.mutation.supplier.schema.CheckExpressionSupplier;

/**
 *
 * @author Chris J. Wright
 */
public class CCRelationalExpressionOperatorE extends MutationPipeline<Schema> {

    private Schema schema;

    public CCRelationalExpressionOperatorE(Schema schema) {
        this.schema = schema;
    }

    @Override
    public List<Mutant<Schema>> mutate() {
        List<Mutant<Schema>> mutants = new ArrayList<>();

        ChainedSupplier<Schema, Expression, RelationalOperator> supplier =
                new ChainedSupplier<>(
                new CheckExpressionSupplier(),
                new RelationalExpressionOperatorSupplier());
        supplier.initialise(schema);

        RelationalOperatorExchanger<Schema> exchanger =
                new RelationalOperatorExchanger<>(supplier);
        mutants.addAll(exchanger.mutate());

        return mutants;
    }
}
