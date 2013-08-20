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
import org.schemaanalyst.mutation.supplier.CheckExpressionSupplier;
import org.schemaanalyst.mutation.supplier.RelationalExpressionOperatorSupplier;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.expression.ColumnExpression;
import org.schemaanalyst.sqlrepresentation.expression.Expression;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;
import org.schemaanalyst.sqlwriter.SQLWriter;

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
    
    public static void main(String[] args) {
        Column c_a = new Column("a", new IntDataType());
        Column c_b = new Column("b", new IntDataType());
        Table t_one = new Table("one");
        t_one.addColumn(c_a);
        t_one.addColumn(c_b);
        
//        Column c_c = new Column("c", new IntDataType());
//        Column c_d = new Column("d", new IntDataType());
//        Table t_two = new Table("two");
//        t_two.addColumn(c_c);
//        t_two.addColumn(c_d);
        
        Schema s = new Schema("schema");
        s.addTable(t_one);
//        s.addTable(t_two);
        s.addCheckConstraint(new CheckConstraint(t_one,
                new RelationalExpression(
                    new ColumnExpression(t_one, c_a),
                    RelationalOperator.EQUALS,
                    new ColumnExpression(t_one, c_b))
                ));
        
        SQLWriter writer = new SQLWriter();
        for (Mutant<Schema> mutant : new CCRelationalExpressionOperatorE(s).mutate()) {
            for (String stmt : writer.writeCreateTableStatements(mutant.getMutatedArtefact())) {
                System.out.println(stmt);
            }
        }
    }
}
