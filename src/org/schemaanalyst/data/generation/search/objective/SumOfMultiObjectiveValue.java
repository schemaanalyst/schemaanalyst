package org.schemaanalyst.data.generation.search.objective;

import java.math.BigDecimal;

public class SumOfMultiObjectiveValue extends MultiObjectiveValue {

    protected BigDecimal sum;

    public SumOfMultiObjectiveValue() {
        super();
    }

    public SumOfMultiObjectiveValue(String description) {
        super(description);
    }

    @Override
    protected void computeValue() {
        sum = BigDecimal.ZERO;
        for (ObjectiveValue objVal : objVals) {
            sum = sum.add(objVal.getValue());
        }

        normalizeAndSetValue(sum);
        computeValue = false;
    }

    @Override
    public void appendToStringBuilder(StringBuilder sb, String indent) {
        if (computeValue) {
            computeValue();
        }

        super.appendToStringBuilder(sb, indent);

        sb.append(" [Sum: ");
        sb.append(sum);
        sb.append("]");

        appendObjectiveValuesToStringBuilder(sb, indent + "\t");
    }
}
