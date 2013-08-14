/*
 */
package deprecated.mutation.mutators.expression;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.schemaanalyst.data.NumericValue;
import org.schemaanalyst.data.Value;
import org.schemaanalyst.sqlrepresentation.expression.ConstantExpression;

/**
 *
 * @author Chris J. Wright
 */
public class UnaryOperatorInsertionMutator extends AbstractMutator {

    @Override
    public void visit(ConstantExpression expression) {
        super.visit(expression);
        Value value = expression.getValue();
        if (value instanceof NumericValue) {
            NumericValue numeric = (NumericValue) value;
            BigDecimal decimal = numeric.get();
            mutants.add(new ConstantExpression(new NumericValue(decimal.negate())));
            mutants.add(new ConstantExpression(new NumericValue(decimal.add(new BigDecimal(BigInteger.ONE)))));
            mutants.add(new ConstantExpression(new NumericValue(decimal.subtract(new BigDecimal(BigInteger.ONE)))));
        }
    }
    
}
